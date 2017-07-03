package com.szyciov.driver.service;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.annotation.NeedRelease;
import com.szyciov.annotation.ValidateRule;
import com.szyciov.driver.base.BaseService;
import com.szyciov.driver.dao.TaxiMainFuncDao;
import com.szyciov.driver.dao.TaxiOrderFuncDao;
import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.driver.entity.DriverMessage;
import com.szyciov.driver.entity.OrderStatistics;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.enums.AlarmProcessEnum;
import com.szyciov.driver.enums.DriverMessageEnum;
import com.szyciov.driver.enums.OrdersortColumn;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.enums.PayUtilEnum;
import com.szyciov.driver.enums.PurseEnum;
import com.szyciov.driver.param.DriverMessageParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.driver.param.PurseParam;
import com.szyciov.driver.param.UploadOrdinatesParam;
import com.szyciov.driver.util.PayUtil;
import com.szyciov.dto.pubAlarmprocess.SavePubAlarmprocessDto;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.DataStatus;
import com.szyciov.entity.NewsState;
import com.szyciov.entity.OrderSource4WithdrawNO;
import com.szyciov.entity.PayMethod;
import com.szyciov.entity.PubDriverTradingrecord;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserType;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.lease.entity.PubDriverAccount;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpUserNews;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PubDriverExpenses;
import com.szyciov.org.entity.PubWithdraw;
import com.szyciov.param.OrderApiParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.BankUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
  * @ClassName TaxiMainFuncService
  * @author Efy Shu
  * @Description 出租车侧边栏功能Service
  * @date 2017年3月23日 09:34:23
  */ 
@Service("TaxiMainFuncService")
public class TaxiMainFuncService extends BaseService{
	//使用ThreadLocal,避免并发时出现数据被篡改的问题
	/**当前订单*/
	@NeedRelease
	private ThreadLocal<AbstractOrder> order = new ThreadLocal<AbstractOrder>();
	/**支付信息(在线支付需要此信息进行支付)*/
	@NeedRelease
	private ThreadLocal<JSONObject> payinfo = new ThreadLocal<JSONObject>();
	/**运管平台信息*/
	@NeedRelease
	private ThreadLocal<OpPlatformInfo> platformInfo = new ThreadLocal<OpPlatformInfo>();
	/**租赁公司信息*/
	@NeedRelease
	private ThreadLocal<LeLeasescompany> leasescompany = new ThreadLocal<LeLeasescompany>();
	/**用户钱包*/
	@NeedRelease
	private ThreadLocal<PubDriverAccount> purse = new ThreadLocal<PubDriverAccount>();
	/**当前订单用车类型*/
	@NeedRelease
	private ThreadLocal<OrderEnum> usetype = new ThreadLocal<OrderEnum>();
	/**当前订单订单类型*/
	@NeedRelease
	private ThreadLocal<OrderEnum> ordertype = new ThreadLocal<OrderEnum>();


	
	/**
	  *依赖
	  */
	private TaxiMainFuncDao taximainfuncdao;
	private TaxiOrderFuncDao taxiorderfuncdao;
	
	/**
	  *依赖注入
	  */
	@Resource(name="TaxiMainFuncDao")
	public void setTaxiMainFuncDao(TaxiMainFuncDao taximainfuncdao){
		this.taximainfuncdao=taximainfuncdao;
	}
	@Resource(name="TaxiOrderFuncDao")
	public void setTaxiOrderFuncDao(TaxiOrderFuncDao taxiorderfuncdao){
		this.taxiorderfuncdao=taxiorderfuncdao;
	}

	/**
	 * 一键报警
	 * @param param
	 * @return
	 */
	public JSONObject callPolice(UploadOrdinatesParam param){
		//如果是候客报警,不检查订单号
		String[] require = new String[]{ "lat","lng","type" };
		//如果是行程中报警,检查订单号
		if(!AlarmProcessEnum.ALARMTYPE_WAITING.code.equals(param.getType()+"")){
			require = new String[]{"usetype","ordertype","orderno","lat","lng","type"};
		}
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		JSONObject result = new JSONObject();
		if(!doCallPolice(param)) {
			return errorResult.get();
		}
		return result;
	}
	
	/**
	 * 我的钱包
	 * @return
	 */
	public JSONObject myPurse(PurseParam param){
		String[] require = new String[]{};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		
		JSONObject result = new JSONObject();
		doGetPurse();
		if(purse.get() == null) {
			return errorResult.get();
		}
		
		result.put("balance", StringUtil.formatNum(purse.get().getBalance(), 1));
		
		return result;
	}
	
	/**
	 * 钱包明细
	 * @return
	 */
	public JSONObject purseDetail(PurseParam param){
		String[] require = new String[]{};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		JSONObject result = new JSONObject();
		List<PubDriverExpenses> purseDetail = doGetPurseExpenses(param);
		JSONArray list = new JSONArray();
		for(PubDriverExpenses pde : purseDetail){
			JSONObject trans = new JSONObject();
			trans.put("action", PurseEnum.getTradeType(pde).msg);
			trans.put("amount", StringUtil.formatNum(pde.getAmount(), 1));
			trans.put("transtype", PurseEnum.getTransSign(pde).code);
			trans.put("transtime", pde.getExpensetime());
			//如果是获取交易明细,还需添加交易渠道
			if(PurseEnum.DETAILTYPE_ALLTRANS.code.equals(param.getDetailtype())){
				//提现类型的明细渠道显示银行卡
				if(PurseEnum.TRADETYPE_DEPOSIT.equals(PurseEnum.getTradeType(pde))){
					trans.put("channel", "银行卡");
				//订单收入的明细渠道显示余额
				}else if (PurseEnum.TRADETYPE_ORDER.equals(PurseEnum.getTradeType(pde))) {
					trans.put("channel", "余额");
				}else{
					trans.put("channel", PurseEnum.getExpenseType(pde).msg);
				}
			}
			list.add(trans);
		}
		result.put("count", list.size());
		result.put("list", list);
		
		
		
		return result;
	}
	
	/**
	 * 校验银行卡信息
	 * @param param
	 * @return
	 */
	public JSONObject checkBankCard(PurseParam param){
		String[] require = new String[]{
			"name","cardno"
		};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		
		JSONObject result = new JSONObject();
		result.put("bankname", doCheckBankCard(param));
		
		return result;
	}
	
	/**
	 * 充值
	 * @param param
	 * @return
	 */
	public JSONObject recharge(PurseParam param){
		String[] require = new String[]{
			"amount","paymethod"
		};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		//充值失败,返回错误信息
		if(!doRecharge(param)) {
			return errorResult.get();
		}
		JSONObject result = new JSONObject();
		result.put("payinfo", payinfo.get());
		
		return result;
	}

    /**
     * 检核是否可以提现申请
     * @param param
     * @return
     */
    public JSONObject ckeckEnchashment(PurseParam param){
        String[] require = new String[]{ "hasnotpayorder" };
        if(!checkeParam(param,getExceptElement(param, require))) {
            return errorResult.get();
        }

        JSONObject result = new JSONObject();
        return result;
    }



    /**
	 * 提现申请
	 * @param param
	 * @return
	 */
	public JSONObject enchashment(PurseParam param){
		String[] require = new String[]{
			"wdamount","cardno","password"
		};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		
		JSONObject result = new JSONObject();
		if(!doEnchashment(param)) {
			return errorResult.get();
		}
		
		return result;
	}
	
	/**
	 * 出租车我的贡献
	 * @param param
	 * @return
	 */
	public JSONObject taxiOrderStatistics(PurseParam param){
		String[] require = new String[]{
			"statis"
		};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		
		JSONObject result = new JSONObject();
		List<OrderStatistics> list = dotaxiOrderStatistics(param);
		result.put("count", list.size());
		result.put("list", list);
		
		return result;
	}
	
	/**
	 * 获取银行卡信息
	 * @return
	 */
	public JSONObject getBankInfo(PurseParam param){
		String[] require = new String[]{ };
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		JSONObject result = doGetBankInfo(param);
		
		return result;
	}
	
	/**
	 * 添加银行卡
	 * @deprecated
	 * @param param
	 * @return
	 */
	public JSONObject addBankCard(PurseParam param){
		String[] require = new String[]{ };
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		JSONObject result = new JSONObject();
		
		return result;
	}
	
	/**
	 * 获取支付渠道
	 * @return
	 */
	public JSONObject getPayMethods(PurseParam param){
		String[] require = new String[]{"type"};
		//如果是订单付款,还需校验订单号
		if(PayUtilEnum.PAYORDER.code.equals(param.getType())){
			require = new String[]{"type","usetype","ordertype","orderno"};
		}
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		
		JSONObject result = new JSONObject();
		JSONArray channel = doGetPayMethods(param);
		result.put("channel", channel);
		
		return result;
	}
	
	/**
	 * 出租车获取消息列表
	 * @return
	 */
	public JSONObject taxiPollMessage(DriverMessageParam param){
		String[] require = new String[]{ };
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		JSONObject result = doTaxiPollMessage(param);
		
		return result;
	}
	
	/**
	 * 更改司机密码(目前只能更改提现密码)
	 * @param param
	 * @return
	 */
	public JSONObject taxiResetPassword(PurseParam param){
		String[] require = new String[]{};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		if(!doTaxiResetPassword(param)) return errorResult.get();
		JSONObject result = new JSONObject();
		
		return result;
	}
	
	/**
	 * 微信支付回调
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void weChatCallback(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PayUtil pu = new PayUtil();
		Map<String, Object> result = pu.checkWeChatStatus(request);
		//如果检查状态失败则返回失败
		if(result == null) {
			response.getWriter().write(PayUtilEnum.WECHATFAILED.msg);
			return;
		}
		String out_trade_no = (String)result.get("out_trade_no");
		String tradeno = (String)result.get("tradeno");
		PubDriverTradingrecord record = new PubDriverTradingrecord();
		record.setOuttradeno(out_trade_no);
		record = taximainfuncdao.getDriverTradeRecord(record);
		//如果无此记录,则返回失败
		if(record == null) {
			response.getWriter().write(PayUtilEnum.WECHATFAILED.msg);
			return;
		}
		//改写记录为成功
		record.setTradingstatus(Integer.valueOf(PayUtilEnum.TRADING_SUCCESS.code));
		record.setTradeno(tradeno);
		taximainfuncdao.saveDriverTradeRecord(record);
		//如果回调失败,则返回失败
		if(!doPayCallback(record)) {
			response.getWriter().write(PayUtilEnum.WECHATFAILED.msg);
			return;
		}
		//返回成功
		response.getWriter().write(PayUtilEnum.WECHATSUCCESS.msg);
		return;
	}
	
	/**
	 * 支付宝支付回调
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void aliPayCallback(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PayUtil pu = new PayUtil();
		Map<String, Object> result = pu.checkAliPayStatus(request);
		//如果检查状态失败则返回失败
		if(result == null) {
			response.getWriter().write(PayUtilEnum.WECHATFAILED.msg);
			return;
		}
		String out_trade_no = (String)result.get("out_trade_no");
		String tradeno = (String)result.get("tradeno");
		PubDriverTradingrecord record = new PubDriverTradingrecord();
		record.setOuttradeno(out_trade_no);
		record = taximainfuncdao.getDriverTradeRecord(record);
		//如果无此记录,则返回失败
		if(record == null) {
			response.getWriter().write(PayUtilEnum.WECHATFAILED.msg);
			return;
		}
		//改写记录为成功
		record.setTradingstatus(Integer.valueOf(PayUtilEnum.TRADING_SUCCESS.code));
		record.setTradeno(tradeno);
		taximainfuncdao.saveDriverTradeRecord(record);
		//如果回调失败,则返回失败
		if(!doPayCallback(record)) {
			response.getWriter().write(PayUtilEnum.WECHATFAILED.msg);
			return;
		}
		//返回成功
		response.getWriter().write(PayUtilEnum.WECHATSUCCESS.msg);
		return;
	}
	/**********************************************************内部方法***************************************************************/
	/**
	 * 出租车重置密码
	 * @param param
	 * @return
	 */
	private boolean doTaxiResetPassword(PurseParam param){
		logger.info("出租车重置密码...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		String newsPwd = StringUtil.getRandomStr(6, StringUtil.HASNUM);
		String encodePass= PasswordEncoder.encode(newsPwd);
		//先保存数据再发送短信
		driver.get().setWithdrawpwd(encodePass);
		accdao.updatePubDriver(driver.get());
//		accdao.saveOrUpdateSmsToken(smsToken);
		List<String> userids = new ArrayList<>();
		userids.add(driver.get().getPhone());
		DriverInfo driverInfo = accdao.getDriverInfoById(driver.get().getId());
//		LeLeasescompany companyinfo = accdao.getCompanyById(driver.get().getLeasescompanyid());
//		String companyname = companyinfo==null?"车企":companyinfo.getShortName();
		//String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.driver.service.accountservice.resetpassword", 
		//		newsPwd,driverInfo.getContact(),companyname);
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.driver.service.accountservice.resetpassword", 
						newsPwd,driverInfo.getContact());
		UserMessage um = new UserMessage(userids,content,UserMessage.CHANGEPASSWORD);
		MessageUtil.sendMessage(um);
		logger.info("出租车重置密码完成");
		return true;
	}
	/**
	 * 出租车获取消息逻辑
	 * @param param
	 * @return
	 */
	private JSONObject doTaxiPollMessage(DriverMessageParam param){
		logger.info("出租车获取消息开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		List<DriverMessage> messages = new ArrayList<>();
		JSONObject result = new JSONObject();
		if(DriverMessageEnum.TYPE_ORDER.code.equals(param.getType()+"")){
			messages = doGetTaxiOrderMessageList(param);
		}else{
			messages = doGetTaxiSystemMessageList(param);
		}
		
		if(messages == null){
			messages = new ArrayList<>(); 
		}
		result.put("count", messages.size());
		result.put("news", messages);
		logger.info("出租车获取消息完成");
		return result;
	}
	
	/**
	 * 出租车获取订单消息
	 * @param param
	 * @return
	 */
	private List<DriverMessage> doGetTaxiOrderMessageList(DriverMessageParam param){
		logger.info("出租车获取订单消息...");
		List<DriverMessage> messages = new ArrayList<>();
		String key = "DriverGrabMessage_" + driver.get().getId() + "_"+driver.get().getPhone()+"_*";
		Set<String> keys = JedisUtil.getKeys(key);
		if (keys.isEmpty()){
		    return messages;
        }

        boolean hasIsNow = false; // 是否有未完成今天的预约用车
        List<OpTaxiOrder> list =  this.taxiorderfuncdao.taxiTodayYuyueOrder(driver.get().getId());
        if(list.size() > 0){
            hasIsNow = true;
        }

        String fileserver = SystemConfig.getSystemProperty("fileserver");

        Calendar toromorrow = Calendar.getInstance();
        toromorrow.set(Calendar.HOUR_OF_DAY, 0);
        toromorrow.set(Calendar.MINUTE, 0);
        toromorrow.set(Calendar.SECOND, 0);
        toromorrow.add(Calendar.DATE, 1);

        Calendar today = Calendar.getInstance();
        toromorrow.set(Calendar.HOUR_OF_DAY, 0);
        toromorrow.set(Calendar.MINUTE, 0);
        toromorrow.set(Calendar.SECOND, 0);

		for(String k : keys){
			String value = JedisUtil.getString(k);
			if(value == null){
			    continue;
            }
			DriverMessage message = StringUtil.parseJSONToBean(value, DriverMessage.class);
            String usenow = message.getOrderinfo().get("usenow") == null ? "" : message.getOrderinfo().getString("usenow");
            Date usetime = new Date(message.getOrderinfo().getLong("usetimeLong"));

            // 当天有预约用车的司机不显示当天预约订单
            if (!("预约用车".equals(usenow) && hasIsNow
                    && (today.getTime().before(usetime) && toromorrow.getTime().after(usetime))))
            {
                String userid = message.getOrderinfo().getString("userid");
                String usetype = message.getOrderinfo().getString("usetype");

                if ("2".equals(usetype)) {
                    PeUser pe = this.taxiorderfuncdao.getPeUserInfoById(userid);

                    if (pe != null) {
                        message.getOrderinfo().put("headimage", fileserver + pe.getHeadportraitmin());
                    }
                }
                messages.add(message);
            }
        }

        if (messages.size() > 0) {
        	sortMessageList(messages);
        }

		logger.info("出租车获取订单消息完成");
		return messages;
	}


    /**
	 * 消息排序(抢单时限越少的在上面)
	 * @param list
	 * @return
	 */
	private void sortMessageList(List<DriverMessage> list){
		list.sort(new Comparator<DriverMessage>() {
            @Override
            public int compare(DriverMessage o1, DriverMessage o2) {
                if (o1.getOrderinfo().getLong("grabendtime") > o2.getOrderinfo().getLong("grabendtime")) {
                    return 1;
                }
                if (o1.getOrderinfo().getLong("grabendtime") < o2.getOrderinfo().getLong("grabendtime")) {
                    return -1;
                }
                return 0;
            }
        });
	}
	
	/**
	 * 出租车获取系统消息
	 * @param param
	 * @return
	 */
	private List<DriverMessage> doGetTaxiSystemMessageList(DriverMessageParam param){
		logger.info("出租车获取系统消息...");
		List<PubDriverNews> news = taximainfuncdao.getDriverMessageByType(param);
		List<DriverMessage> messages = new ArrayList<>();
		for(PubDriverNews n : news){
		    try {
                DriverMessage dm = convertDriverNewsToMessage(n);
                messages.add(dm);
            }catch (Exception ex){
		        logger.error("", ex);
            }
		}
		logger.info("出租车获取系统消息完成");
		return messages;
	}
	
	/**
	 * 第三方支付回调逻辑
	 * @param record
	 * @return
	 */
	private boolean doPayCallback(PubDriverTradingrecord record){
		logger.info("第三方支付回调逻辑开始...");
		logger.info("使用参数:" + JSONObject.fromObject(record));
		driver.set(accdao.getPubDriverById(record.getDriverid()));
		if(driver.get() == null){
			logger.info(record.getDriverid() + "不存在该司机,回调失败");
			return false;
		}
		//回调时需要钱包信息
		doGetPurse();
		if(PayUtilEnum.RECHARGE.code.equals(record.getType() + "")){
			doRechargeCallback(record);  //充值回调
		}else if (PayUtilEnum.PAYORDER.code.equals(record.getType() + "")) {
			doPayOrderCallback(record);  //订单支付回调
		}else{
			logger.info("第三方支付回调失败,支付类型错误");
			return false;
		}
		logger.info("第三方支付回调完成");
		return true;
	}
	
	/**
	 * 充值回调
	 * @param record
	 * @return
	 */
	private boolean doRechargeCallback(PubDriverTradingrecord record){
		logger.info("充值回调...");
		purse.get().setBalance(purse.get().getBalance() + record.getAmount());
		taximainfuncdao.saveOrUpdatePubDriverAccount(purse.get());
		
		//还需要添加支付明细
		PurseParam param = new PurseParam();
		param.setAmount(record.getAmount());
		param.setDetailtype(PurseEnum.DETAILTYPE_BALANCE.code);
		param.setPaymethod(record.getPaymenttype() + "");
		doSavePurseDetail(param,PurseEnum.TRADETYPE_RECHARGE);
        doSaveSystemnews(param);
		logger.info("充值回调完成");
		return true;
	}

    /**
     * 保存交易明细
     * 标题：“司机充值成功”
     * 内容：“{0}，充值金额：{1}元，支付渠道：{2}，充值时间：{3}。”
     * 0：司机姓名+手机号码
     * 1：充值金额
     * 2：充值时的支付渠道，如微信支付、支付宝支付
     * 3：取充值成功的时间，格式为 yyyy-mm-dd hh:mm
     * @param param    x
     * @return
     */
    private boolean doSaveSystemnews(PurseParam param){
        try{
            String paymethod = "";
            if("1".equals(param.getPaymethod())){
                paymethod = "微信";
            }else if("2".equals(param.getPaymethod())){
                paymethod = "支付宝";
            }

            String date = StringUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");

            if ("1".equals(driver.get().getPlatformtype())) {
                // 租赁端
                List<String> list = this.taximainfuncdao.getLeUserIds(driver.get().getLeasescompanyid());
                LeUserNews news;
                for(String userId : list){
                    news = new LeUserNews();
                    news.setId(GUIDGenerator.newGUID());
                    news.setCreateTime(new Date());
                    news.setUpdateTime(new Date());
                    news.setStatus(1);
                    news.setUserId(userId);
                    news.setNewsState("0");
                    news.setType("1");

                    JSONObject content = new JSONObject();
                    content.put("title", NewsState.LEASERECHARGE.msg);
                    content.put("type", NewsState.LEASERECHARGE.code);
                    content.put("content",
                            String.format("%s，充值金额：%s元，支付渠道：%s，充值时间：%s。",
                                    driver.get().getName() + driver.get().getPhone(),
                                    param.getAmount(),
                                    paymethod,
                                    date));

                    news.setContent(content.toString());
                    this.taximainfuncdao.saveLeUserNews(news);
                }
            }else{
                // 运管端
                List<String> list = this.taximainfuncdao.getOpUserIds();
                OpUserNews news;
                for(String userId : list){
                    news = new OpUserNews();
                    news.setId(GUIDGenerator.newGUID());
                    news.setCreateTime(new Date());
                    news.setUpdateTime(new Date());
                    news.setStatus(1);
                    news.setUserId(userId);
                    news.setNewsState("0");
                    news.setType("1");

                    JSONObject content = new JSONObject();
                    content.put("title", NewsState.OPERATERECHARGE.msg);
                    content.put("type", NewsState.OPERATERECHARGE.code);
                    content.put("content",
                            String.format("%s，充值金额：%s元，支付渠道：%s，充值时间：%s。",
                                    driver.get().getName() + driver.get().getPhone(),
                                    param.getAmount(),
                                    paymethod,
                                    date));

                    news.setContent(content.toString());
                    this.taximainfuncdao.saveOpUsersNews(news);
                }
            }
        }catch (Exception ex){
            logger.error("",ex);
        }

        return true;
    }

	
	/**
	 * 订单支付回调
	 * @return
	 */
	private boolean doPayOrderCallback(PubDriverTradingrecord record){
		logger.info("订单支付回调...");
		//订单支付则改变订单状态
		OrderApiParam oap = new OrderApiParam();
		oap.setOrderno(record.getOrderno());
		OpTaxiOrder taxiOrder = taxiorderfuncdao.getOpTaxiOrder(oap);
		//支付完成,改变订单状态,司机需要支付只可能是线下支付
		//如果调度费不为0
		if(taxiOrder.getSchedulefee() > 0){
			//如果订单状态为都未付,则改为乘客未支付
			if(PayState.ALLNOPAY.state.equals(taxiOrder.getPaymentstatus())){
				taxiOrder.setPaymentstatus(PayState.PASSENGERNOPAY.state);
				taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.NOTPAY.state));
			//否则改为已付结
			}else{
				taxiOrder.setPaymentstatus(PayState.PAYOVER.state);
				taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.PAYOVER.state));
			}
		//如果不存在调度费改为已结算
		}else{
			taxiOrder.setPaymentstatus(PayState.STATEMENTED.state);
			taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.STATEMENTED.state));
		}
		taxiOrder.setCompletetime(new Date());    //订单完结时间
		taxiOrder.setSettlementtime(new Date());  //订单结算时间
		taxiorderfuncdao.taxiPayOrder(taxiOrder);
		
		//还需要添加支付明细
		PurseParam param = new PurseParam();
		param.setAmount(record.getAmount());
		param.setDetailtype(PurseEnum.DETAILTYPE_ALLTRANS.code);
		param.setPaymethod(record.getPaymenttype() + "");
		doSavePurseDetail(param,PurseEnum.TRADETYPE_SETTLE);
		logger.info("订单支付回调完成");
		return true;
	}
	
	/**
	 * 获取支付渠道逻辑
	 * @param param
	 * @return
	 */
	private JSONArray doGetPayMethods(PurseParam param){
		logger.info("获取支付渠道开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONArray channel = new JSONArray();
		JSONObject alipay = new JSONObject();
		JSONObject weixin = new JSONObject();
		JSONObject purse  = new JSONObject();

		//订单付款有余额渠道
		if(PayUtilEnum.PAYORDER.code.equals(param.getType())){
			//订单所属平台必须与司机所属平台相同才能用余额支付
			if((OrderEnum.USETYPE_PERSONAL.equals(usetype.get()) && 
				PlatformTypeByDb.OPERATING.code.equals(driver.get().getPlatformtype())) || 
				(!OrderEnum.USETYPE_PERSONAL.equals(usetype.get()) && 
						PlatformTypeByDb.LEASE.code.equals(driver.get().getPlatformtype()))){
				purse.put("channel", PayMethod.BALANCE.code);
				//如果是订单付款,校验订单金额
				purse.put("canuse", checkBalanceEnough(order.get().getOrderamount()));
				channel.add(purse);
			}
		}
		//检查微信账户
		if(checkWeChatAccount()){  //账户可用就添加
			weixin.put("channel", PayMethod.WECHAT.code);
			weixin.put("canuse", true);
			channel.add(weixin);
		}
		//检查支付宝账户
		if(checkAliPayAccount()){  //账户可用就添加
			alipay.put("channel", PayMethod.ALIPAY.code);
			alipay.put("canuse", true);
			channel.add(alipay);
		}
		logger.info("获取支付渠道完成");
		return channel;
	}
	/**
	 * 获取银行卡信息逻辑
	 * @param param
	 * @return
	 */
	private JSONObject doGetBankInfo(PurseParam param){
		logger.info("获取银行卡信息开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject addButton = new JSONObject();
		PubWithdraw pwd = new PubWithdraw();
		pwd.setUsertype(UserType.WITHDRAW_DRIVER.code);
		pwd.setUserid(driver.get().getId());
		pwd = taximainfuncdao.getLastBankCard(pwd);
		if(pwd != null){
			JSONObject cardInfo = new JSONObject();
			cardInfo.put("cardno", pwd.getCreditcardnum());
			cardInfo.put("bank", pwd.getBankname());
			cardInfo.put("name", pwd.getCreditcardname());
			cardInfo.put("type", 0);
			array.add(cardInfo);
		}
		addButton.put("cardno", "");
		addButton.put("bank", "");
		addButton.put("name", "");
		addButton.put("type", 1);
		array.add(addButton);
		result.put("count", array.size());
		result.put("info", array);
		logger.info("获取银行卡信息完成");
		return result;
	}
	/**
	 * 出租车我的贡献逻辑
	 * @param param
	 * @return
	 */
	private List<OrderStatistics> dotaxiOrderStatistics(PurseParam param){
		logger.info("获取出租车我的贡献开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		List<OrderStatistics> list = taximainfuncdao.getTaxiOrderStatistics(param);
		if(list == null) return new ArrayList<>();
		logger.info("获取出租车我的贡献完成");
		return list;
	}
	/**
	 * 充值逻辑
	 * @param param
	 * @return
	 */
	private boolean doRecharge(PurseParam param){
		//需调用支付接口,待实现
		logger.info("充值开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		if(PayMethod.WECHAT.code.equals(param.getPaymethod())){             //如果是微信支付
			if(!doWeChatPay(param)) return false;
		}else if (PayMethod.ALIPAY.code.equals(param.getPaymethod())) {     //如果是支付宝支付
			if(!doAliPay(param)) return false;
		}
		logger.info("充值完成");
		return true;
	}
	
	/**
	 * 微信支付
	 * @return
	 */
	private boolean doWeChatPay(PurseParam param){
		logger.info("使用微信支付...");
		PayUtil pu = new PayUtil(param.getIpaddr(),PayUtilEnum.RECHARGE);
		String appid = null,privateKey = null,shno = null;
		//根据司机平台不同获取不同的收款信息
		if(PlatformTypeByDb.LEASE.code.equals(driver.get().getPlatformtype())){
			appid = leasescompany.get().getDriverwechatappid();
			privateKey = leasescompany.get().getDriverwechatsecretkey();
			shno = leasescompany.get().getDriverwechatmerchantno();
		}else{
			appid = platformInfo.get().getDriverwechatappid();
			privateKey = platformInfo.get().getDriverwechatsecretkey();
			shno = platformInfo.get().getDriverwechatmerchantno();
		}
		payinfo.set(pu.createWeChatPay(param.getAmount(), appid, privateKey, shno));
		if(payinfo.get() == null){
			logger.info("使用微信支付失败");
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "支付失败");
			payinfo.set(new JSONObject());
			return false;
		}
		String sign = payinfo.get().getJSONObject("payorderinfo").getString("sign");
		//保存第三方支付记录,回调时使用
		doSaveTradeRecord(sign,param);
		logger.info("使用微信支付完成");
		return true;
	}
	
	/**
	 * 支付宝支付
	 * @return
	 */
	private boolean doAliPay(PurseParam param){
		logger.info("使用支付宝支付...");
		PayUtil pu = new PayUtil(param.getIpaddr(),PayUtilEnum.RECHARGE);
		String appid = null,publicKey = null,privateKey = null;
		//根据司机平台不同获取不同的收款信息
		if(PlatformTypeByDb.LEASE.code.equals(driver.get().getPlatformtype())){
			appid = leasescompany.get().getDriveralipayappid();
			publicKey = leasescompany.get().getDriveralipaypublickey();
			privateKey = leasescompany.get().getDriveralipayprivatekey();
		}else{
			appid = platformInfo.get().getDriveralipayappid();
			publicKey = platformInfo.get().getDriveralipaypublickey();
			privateKey = platformInfo.get().getDriveralipayprivatekey();
		}
		payinfo.set(pu.createAliPay(param.getAmount(), appid, privateKey));
		if(payinfo.get() == null){
			logger.info("使用支付宝支付失败");
			payinfo.set(new JSONObject());
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "支付失败");
			return false;
		}
		//保存第三方支付记录,回调时使用
		doSaveTradeRecord(publicKey,param);
		logger.info("使用支付宝支付完成");
		return true;
	}
	
	/**
	 * 保存第三方支付记录,回调时使用
	 * @param publicKey 支付宝验证公钥
	 * @param param
	 * @return
	 */
	private boolean doSaveTradeRecord(String publicKey,PurseParam param){
		logger.info("保存第三方交易记录开始...");
		logger.info("使用参数:" + publicKey);
		PubDriverTradingrecord tradingrecord = new PubDriverTradingrecord();
		tradingrecord.setOuttradeno(payinfo.get().getString("out_trade_no"));
		tradingrecord.setLeasescompanyid(driver.get().getLeasescompanyid());
		tradingrecord.setDriverid(driver.get().getId());
		tradingrecord.setType(Integer.valueOf(PayUtilEnum.RECHARGE.code));
		tradingrecord.setPaymenttype(Integer.valueOf(param.getPaymethod()));
		tradingrecord.setValidatekey(publicKey);
		tradingrecord.setTradingstatus(Integer.valueOf(PayUtilEnum.TRADING_PROCESSING.code));
		tradingrecord.setOrderno(param.getOrderno());
		tradingrecord.setAmount(param.getAmount());
		tradingrecord.setStatus(DataStatus.OK.code);
		taximainfuncdao.saveDriverTradeRecord(tradingrecord);
		logger.info("保存第三方交易记录完成");
		return true;
	}
	
	/**
	 * 提现申请逻辑
	 * @param param
	 * @return
	 */
	private boolean doEnchashment(PurseParam param){
		logger.info("提现申请开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		logger.info("更改账户余额...");
		purse.get().setBalance(purse.get().getBalance() - param.getAmount());
		taximainfuncdao.saveOrUpdatePubDriverAccount(purse.get());
		//保存交易明细
		param.setDetailtype(PurseEnum.DETAILTYPE_BALANCE.code);
		param.setPaymethod(PayMethod.BALANCE.code);
		if(!doSavePurseDetail(param,PurseEnum.TRADETYPE_DEPOSIT)) return false;
		//保存提现申请
		if(!doSaveWithDraw(param)) return false;
		logger.info("提现申请完成");
		return true;
	}
	
	/**
	 * 保存提现记录
	 * @param param
	 * @return
	 */
	private boolean doSaveWithDraw(PurseParam param){
		logger.info("保存提现申请开始...");
		PubWithdraw pwd = new PubWithdraw();
		pwd.setApplytime(new Date());
		pwd.setBankname(doCheckBankCard(param));
		pwd.setLeasescompanyid(driver.get().getLeasescompanyid());
		pwd.setUsertype(UserType.WITHDRAW_DRIVER.code);
		pwd.setUserid(driver.get().getId());
		pwd.setCreditcardname(driver.get().getName());
		pwd.setPlatformtype(driver.get().getPlatformtype());
		pwd.setUuid(getWithDrawNO());
		pwd.setCreditcardnum(param.getCardno());
		pwd.setCreater(driver.get().getId());
		pwd.setAmount(new BigDecimal(param.getAmount()));
		pwd.setId(GUIDGenerator.newGUID());
		pwd.setProcessstatus(PurseEnum.OPERATERESULT_SUCCESS.code);
		pwd.setStatus(DataStatus.OK.code);
		taximainfuncdao.savePubWithDraw(pwd);
		logger.info("保存提现完成");
		return true;
	}
	
	/**
	 * 保存交易明细
	 * @param param       保证detailtype和paymethod有值
	 * @param tradetype  交易类型
	 * @return
	 */
	private boolean doSavePurseDetail(PurseParam param,PurseEnum tradetype){
		logger.info("保存交易明细开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param) + "-->" + tradetype.code);
		PubDriverExpenses pde = new PubDriverExpenses();
		pde.setAmount(param.getAmount());
		pde.setBalance(purse.get().getBalance());
		pde.setCreater(driver.get().getId());
		pde.setCreatetime(new Date());
		pde.setDetailtype(param.getDetailtype());
		pde.setDriverid(driver.get().getId());
		pde.setExpensetime(new Date());
		pde.setExpensetype(param.getPaymethod());
		pde.setId(GUIDGenerator.newGUID());
		pde.setPlatformtype(driver.get().getPlatformtype());
		pde.setStatus(DataStatus.OK.code);
		pde.setTradetype(tradetype.code);
		pde.setRemark(tradetype.msg);
		pde.setOperateresult(PurseEnum.OPERATERESULT_SUCCESS.code);
		taximainfuncdao.savePubDriverExpenses(pde);

		logger.info("保存交易明细完成");
		return true;
	}
	
	/**
	 * 钱包明细逻辑
	 * @param param x
	 * @return
	 */
	private List<PubDriverExpenses> doGetPurseExpenses(PurseParam param){
		logger.info("获取钱包明细开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		List<PubDriverExpenses> purseDetail = taximainfuncdao.getPurseExpenses(param);
		logger.info("获取钱包明细完成");
		return purseDetail;
	}
	
	/**
	 * 我的钱包逻辑
	 * @return
	 */
	private PubDriverAccount doGetPurse(){
		logger.info("获取钱包余额开始...");
		logger.info("使用参数:无");
		purse.set(taximainfuncdao.getPurse(driver.get()));
		if(purse.get() == null){  //钱包获取失败返回0.0
			purse.set(new PubDriverAccount());
			purse.get().setId(GUIDGenerator.newGUID());
			purse.get().setDriverid(driver.get().getId());
//			logger.info("获取钱包余额失败");
//			errorResult.get().put("status", Retcode.FAILED.code);
//			errorResult.get().put("message", "获取钱包余额失败");
//			return purse.get();
		}
		logger.info("获取钱包余额完成");
		return purse.get();
	}
	
	/**
	 * 校验银行卡逻辑
	 * @param param
	 * @return
	 */
	private String doCheckBankCard(PurseParam param){
		logger.info("校验银行卡信息开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		String bankname = BankUtil.getBankName(param.getCardno());
		logger.info("校验银行卡信息完成");
		return bankname;
	}
	
	/**
	 * 一键报警处理逻辑
	 * @param param
	 * @return
	 */
	private boolean doCallPolice(UploadOrdinatesParam param){
		logger.info("报警申请开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		//如果是个人用车就是运管平台,非个人用车就是租赁平台
		PlatformTypeByDb platform = null;
		//候客报警用司机所属平台,行程中报警用订单所属平台
		if(AlarmProcessEnum.ALARMTYPE_WAITING.code.equals(param.getType()+"")){
			platform = PlatformTypeByDb.OPERATING.code.equals(driver.get().getPlatformtype()) ? PlatformTypeByDb.OPERATING : PlatformTypeByDb.LEASE;
		}else{
			platform =  OrderEnum.USETYPE_PERSONAL.equals(usetype.get()) ?PlatformTypeByDb.OPERATING :PlatformTypeByDb.LEASE;
		}
		//如果是个人用车就是个人用户,非个人用车就是机构用户
		UserType usertype = OrderEnum.USETYPE_PERSONAL.equals(usetype.get()) ?UserType.PERSONAL :UserType.ORGUSER;
		SavePubAlarmprocessDto dto = new SavePubAlarmprocessDto();
		dto.setAlarmsource(AlarmProcessEnum.ALARMSOURCE_DRIVER.code);
		dto.setAlarmtime(StringUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		dto.setAlarmtype(param.getType()+"");
		dto.setCreater(driver.get().getId());
		dto.setDriverid(driver.get().getId());
		dto.setLat(param.getLat()+"");
		dto.setLng(param.getLng()+"");
		dto.setLeasecompanyid(driver.get().getLeasescompanyid());
		dto.setOrderno(param.getOrderno());
		dto.setPlatformtype(AlarmProcessEnum.ALARMTYPE_WAITING.code.equals(param.getType()+"") ? driver.get().getPlatformtype() : platform.code);
		dto.setUserid(order.get()==null?null:order.get().getUserid());
		dto.setUsertype(order.get()==null?null:usertype.code);
		dto.setOrdertype(order.get()==null?null:ordertype.get().code);
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
			(PlatformTypeByDb.OPERATING.equals(platform) ? operateApiUrl : leaseApiUrl) + "/pubAlarmprocess/apply", 
			HttpMethod.POST, 
			param.getToken(), 
			dto, 
			JSONObject.class
		);
		if(result.getInt("status") != Retcode.OK.code){
			logger.error("报警申请失败:"+result);
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "报警申请失败");
			return false;
		}
		logger.info("报警申请完成");
		return true;
	}
	
	/**
	 * 获取提现编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getWithDrawNO(){
        Map<String, Object> resultMap = templateHelper.dealRequestWithFullUrlToken(
                SystemConfig.getSystemProperty("carserviceApi") + "/PubWithDraw/GetPubWithDrawNo",
                HttpMethod.POST, null, OrderSource4WithdrawNO.DRIVER, Map.class);
        String uuid = (String) resultMap.get("pubWithDrawNo");
        return uuid;

    }
	
	/**
	 * 将PubdriverNews转换为DriverMessage
	 * @param news
	 * @return
	 */
	private DriverMessage convertDriverNewsToMessage(PubDriverNews news){
		JSONObject newsContent = JSONObject.fromObject(news.getContent());
		int newsType = newsContent.getInt("type");
		DriverMessage dm = new DriverMessage();
		dm.setNewsid(news.getId());
		dm.setCreatetime(news.getCreatetime());
		dm.setReaded(NewsState.isReaded(news.getNewsstate()));
		dm.setTitle(newsContent.get("title") == null ? "" : newsContent.getString("title"));
		dm.setContent(newsContent.get("content") == null ? "" : newsContent.getString("content"));
		dm.setNewstype(NewsState.convertNewsType(newsType));
		//如果存在订单信息
		JSONObject orderinfo = new JSONObject();
		if(newsContent.has("orderid")){
			orderinfo.put("orderno", newsContent.getString("orderid"));
			orderinfo.put("usetype", newsContent.getString("usetype"));
			orderinfo.put("ordertype", newsContent.getString("ordertype"));
			if(OrderEnum.getOrderType(newsContent.getString("ordertype")) == null){
				orderinfo.put("type", "");
			}else{
				orderinfo.put("type",OrderEnum.getOrderType(newsContent.getString("ordertype")).msg);
			}
			orderinfo.put("onaddr", newsContent.getString("onaddr"));
			orderinfo.put("offaddr", newsContent.getString("offaddr"));
			orderinfo.put("usetime", StringUtil.parseDate(newsContent.getString("usetime"), "yyyy-MM-dd HH:mm"));
			orderinfo.put("usenow", newsContent.getString("usenow")+"用车");
			orderinfo.put("lasttime", newsContent.getString("lasttime"));//剩余时间
			//下面的字段都是在任务消息里才会有
			orderinfo.put("grabendtime", 0);
			orderinfo.put("headimage", "");
			orderinfo.put("remark", "");
		}else{
			orderinfo.put("orderno", "");
			orderinfo.put("usetype", "");
			orderinfo.put("ordertype", "");
			orderinfo.put("type", "");
			orderinfo.put("onaddr", "");
			orderinfo.put("offaddr", "");
			orderinfo.put("usetime", 0);
			orderinfo.put("usenow", "");
			orderinfo.put("lasttime", "");//剩余时间
			//下面的字段都是在任务消息里才会有
			orderinfo.put("grabendtime", 0);
			orderinfo.put("headimage", "");
			orderinfo.put("remark", "");
		}
		//如果是提现消息
		JSONObject takecashinfo = new JSONObject();
		if(DriverMessageEnum.NEWS_TYPE_WITHDRAW.code.equals(NewsState.convertNewsType(newsType))){
		    String creditcardnum = newsContent.get("creditcardnum") == null ? "" : newsContent.getString("creditcardnum");
		    String lastThreeNo = "";
            if (creditcardnum.length() > 3) {
                lastThreeNo = "(" + creditcardnum.substring(creditcardnum.length() - 4) + ")";
            }
            String bank = newsContent.get("bankname") == null ? "" : newsContent.getString("bankname");
            takecashinfo.put("remark", newsContent.get("remark") == null ? "" : newsContent.getString("remark"));
			takecashinfo.put("amount", newsContent.get("amount") == null ? 0 : newsContent.getDouble("amount"));
            takecashinfo.put("bank", bank + lastThreeNo);
            takecashinfo.put("applytime", StringUtil.parseDate(newsContent.getString("applytime"), "yyyy-MM-dd HH:mm"));
		}else{
			takecashinfo.put("remark", "");
			takecashinfo.put("bank", "");
			takecashinfo.put("amount", 0);
			takecashinfo.put("applytime", 0);
		}
		dm.setOrderinfo(orderinfo);
		dm.setTakecashinfo(takecashinfo);
		return dm;
	}
	/*********************************************************校验方法****************************************************************/
	/**
	 * 校验订单号
	 * @param orderno
	 * @return
	 */
	@ValidateRule(msg="订单号格式不正确")
	private boolean checkOrderNO(String orderno){
		order.set(null);
		//根据平台不同获取不同平台订单
		if(OrderEnum.USETYPE_PERSONAL.equals(usetype.get())){
			order.set(taxiorderfuncdao.getOpTaxiOrder(orderno));
//		}else{
//			order.set(ordDao.getOrgTaxiOrder(orderno));
		}
		if(order.get() == null){
			errorResult.get().put("status", Retcode.ORDERNOTEXIT.code);
			errorResult.get().put("message", Retcode.ORDERNOTEXIT.msg);
		}
		return order.get() != null;
	}
	
	/**
	 * 校验持卡人
	 * @param name
	 * @return
	 */
	@ValidateRule(msg="持卡人姓名不能为空")
	private boolean checkCardPerson(String name){
		if(name == null || name.trim().isEmpty()) return false;
		if(!driver.get().getName().equals(name)){
			errorResult.get().put("status", Retcode.CARDPERSONINVALID.code);
			errorResult.get().put("message", Retcode.CARDPERSONINVALID.msg);
			return false;
		}
		return true;
	}
	
	/**
	 * 校验报警类型
	 * @param type
	 * @return
	 */
	@ValidateRule(msg="报警类型不正确")
	private boolean checkAlarmType(int type){
		return  AlarmProcessEnum.ALARMTYPE_WAITING.code.equals(type+"") ? true :
					 AlarmProcessEnum.ALARMTYPE_INSERVICE.code.equals(type+"") ? true : false;
	}
	
	/*
	 * 校验支付方式是否正确
	 * @param orderstate
	 * @param order
	 * @return
	 */
	@ValidateRule(msg="支付渠道不正确")
	private boolean checkPayMethod(String paymethod){
		boolean flag;
		//检查是否在支付渠道范围内
		flag =  PayMethod.BALANCE.code.equals(paymethod) ? true : 
					PayMethod.WECHAT.code.equals(paymethod) ? true : 
					PayMethod.ALIPAY.code.equals(paymethod) ? true : false;
		//余额支付或不在范围内直接返回
		if(PayMethod.BALANCE.code.equals(paymethod) || !flag) return flag;
		//第三方支付还需检查商户是否开通(充值时根据司机归属)
		if(PlatformTypeByDb.LEASE.code.equals(driver.get().getPlatformtype())){
			leasescompany.set(accdao.getCompanyById(driver.get().getLeasescompanyid()));
			if(leasescompany.get() == null) return false;
			flag =  PayMethod.WECHAT.code.equals(paymethod) ? 
						PurseEnum.ACCOUNT_ON.code.equals(leasescompany.get().getDriverwechatstatus()) : 
						PayMethod.ALIPAY.code.equals(paymethod) ? 
						PurseEnum.ACCOUNT_ON.code.equals(leasescompany.get().getDriveralipaystatus()) : false;
		}else if (PlatformTypeByDb.OPERATING.code.equals(driver.get().getPlatformtype())) {
			platformInfo.set(accdao.getPlatformInfo());
			if(platformInfo.get() == null) return false;
			flag =  PayMethod.WECHAT.code.equals(paymethod) ? 
						PurseEnum.ACCOUNT_ON.code.equals(platformInfo.get().getDriverwechatstatus()) : 
						PayMethod.ALIPAY.code.equals(paymethod) ? 
						PurseEnum.ACCOUNT_ON.code.equals(platformInfo.get().getDriveralipaystatus()) : false;
		}
		if(!flag){ //如果这里失败表示,第三方支付账户没有开通
			String pm = PayMethod.WECHAT.code.equals(paymethod) ? "微信" : "支付宝";
			errorResult.get().put("status", Retcode.NOPAYCHANNEL.code);
			errorResult.get().put("message", "暂不支持"+ pm +"充值");
		}
		return flag;
	}
	
	@ValidateRule(msg="支付渠道类型不正确")
	private boolean checkChannelType(String type){
		return PayUtilEnum.PAYORDER.code.equals(type) ? true : 
					PayUtilEnum.RECHARGE.code.equals(type) ? true : false;
	}
	
	@ValidateRule(msg="金额不正确")
	private boolean checkAmount(double amount){
		return amount > 0 && (amount+"").matches("\\d+\\.\\d{1}");
	}
	
	@ValidateRule(msg="统计类型不正确")
	private boolean checkStatis(int statis){
		return PurseEnum.ORDERSTATIS_MONTH.code.equals(statis+"") ? true : 
					PurseEnum.ORDERSTATIS_YEAR.code.equals(statis+"") ? true : false;
	}
	
	@ValidateRule(msg="提现密码错误")
	private boolean checkWithDrawPwd(String password){
		String encodePass= PasswordEncoder.encode(password);
        if(driver.get().getWdpwdchangestate() == null || "".equals(driver.get().getWdpwdchangestate())
                || driver.get().getWdpwdchangestate().equals("0")){ //未修改比较登录密码
			if(!encodePass.equals(driver.get().getUserpassword())){
				errorResult.get().put("status", Retcode.PASSWORDWRONG.code);
				errorResult.get().put("message", "密码不正确");
				return false;
			}
		}else{
			if(!encodePass.equals(driver.get().getWithdrawpwd())){
				errorResult.get().put("status", Retcode.PASSWORDWRONG.code);
				errorResult.get().put("message", "密码不正确");
				return false;
			}
		}
		return true;
	}
	
	@ValidateRule(msg="今日提现额度已达到上限")
	private boolean checkAmount4WithDraw(double amount){
		logger.info("检查今日额度...");
		//获取今日限额
		PubWithdraw pwd = new PubWithdraw();
		pwd.setUserid(driver.get().getId());
		pwd.setUsertype(UserType.WITHDRAW_DRIVER.code);
		double limit = taximainfuncdao.getTodayWithDrawLimit(pwd);
		if(limit + amount > 500){
			return false;
		}
		logger.info("检查今日额度完成");
		return true;
	}
	
	@ValidateRule(msg="钱包余额不足")
	private boolean checkBalanceEnough(double amount){
		doGetPurse();
//		if(purse.get() == null) return false;
		logger.info("检查钱包余额...");
		if(purse.get().getBalance() < amount){
			logger.info("钱包余额不足");
			errorResult.get().put("status", Retcode.NOTENOUGHBALANCE.code);
			errorResult.get().put("message", Retcode.NOTENOUGHBALANCE.msg);
			return false;
		}
		logger.info("检查钱包余额完成");
		return true;
	}
	
	@ValidateRule(msg="未开通支付宝收款账户")
	private boolean checkAliPayAccount(){
		logger.info("检查支付宝收款账户...");
		boolean flag = false;
		//如果是订单检查账户,则根据订单归属
		if(usetype.get() != null && ordertype != null){
			if(usetype.get() != null && !OrderEnum.USETYPE_PERSONAL.equals(usetype.get())){
				leasescompany.set(accdao.getCompanyById(order.get().getCompanyid()));
				if(leasescompany.get() == null) return false;
				flag =  PurseEnum.ACCOUNT_ON.code.equals(leasescompany.get().getDriveralipaystatus());
			}else if (usetype.get() != null && OrderEnum.USETYPE_PERSONAL.equals(usetype.get())) {
				platformInfo.set(accdao.getPlatformInfo());
				if(platformInfo.get() == null) return false;
				flag =  PurseEnum.ACCOUNT_ON.code.equals(platformInfo.get().getDriveralipaystatus());
			}
		//否则根据司机归属
		}else{
			if(PlatformTypeByDb.LEASE.code.equals(driver.get().getPlatformtype())){
				leasescompany.set(accdao.getCompanyById(driver.get().getLeasescompanyid()));
				if(leasescompany.get() == null) return false;
				flag =  PurseEnum.ACCOUNT_ON.code.equals(leasescompany.get().getDriveralipaystatus());
			}else if (PlatformTypeByDb.OPERATING.code.equals(driver.get().getPlatformtype())) {
				platformInfo.set(accdao.getPlatformInfo());
				if(platformInfo.get() == null) return false;
				flag =  PurseEnum.ACCOUNT_ON.code.equals(platformInfo.get().getDriveralipaystatus());
			}
		}
		logger.info("检查支付宝收款账户完成");
		return flag;
	}
	
	@ValidateRule(msg="未开通微信收款账户")
	private boolean checkWeChatAccount(){
		logger.info("检查微信收款账户...");
		boolean flag = false;
		//如果是订单检查账户,则根据订单归属
		if(usetype.get() != null && ordertype != null){
			if(usetype.get() != null && !OrderEnum.USETYPE_PERSONAL.equals(usetype.get())){
				leasescompany.set(accdao.getCompanyById(order.get().getCompanyid()));
				if(leasescompany.get() == null) return false;
				flag =  PurseEnum.ACCOUNT_ON.code.equals(leasescompany.get().getDriverwechatstatus());
			}else if (usetype.get() != null && OrderEnum.USETYPE_PERSONAL.equals(usetype.get())) {
				platformInfo.set(accdao.getPlatformInfo());
				if(platformInfo.get() == null) return false;
				flag =  PurseEnum.ACCOUNT_ON.code.equals(platformInfo.get().getDriverwechatstatus());
			}
		//否则根据司机归属
		}else{
			if(PlatformTypeByDb.LEASE.code.equals(driver.get().getPlatformtype())){
				leasescompany.set(accdao.getCompanyById(driver.get().getLeasescompanyid()));
				if(leasescompany.get() == null) return false;
				flag =  PurseEnum.ACCOUNT_ON.code.equals(leasescompany.get().getDriverwechatstatus());
			}else if (PlatformTypeByDb.OPERATING.code.equals(driver.get().getPlatformtype())) {
				platformInfo.set(accdao.getPlatformInfo());
				if(platformInfo.get() == null) return false;
				flag =  PurseEnum.ACCOUNT_ON.code.equals(platformInfo.get().getDriverwechatstatus());
			}
		}

		logger.info("检查微信收款账户完成");
		return flag;
	}
	
	/**
	 * 检查未付结订单是否超限(出租车需要检查)
	 * @param notpayorderlimit
	 * @return
	 */
	@ValidateRule(msg="未付结订单超限")
	private boolean checkNotPayOrderLimit(boolean notpayorderlimit){
		//网约车不检查
		if(!DriverEnum.DRIVER_TYPE_TAXI.code.equals(driver.get().getVehicletype() + "")) return true;
		logger.info("检查司机未付结订单是否超限...");
		OrderListParam olp = new OrderListParam();
		olp.setType(2);
		olp.setDriverid(driver.get().getId());
		List<OpTaxiOrder> list = taxiorderfuncdao.getTaxiOrderList(olp);
		if(list != null && !list.isEmpty()){
			double totalAmount = 0;
			for(OpTaxiOrder oo : list){
				totalAmount += oo.getOrderamount();
			}
			//如果金额>=200或者未付订单数>=10则提示超限
			if(totalAmount >= 200 || list.size() >= 10){
				logger.info("未付结订单超限");
				errorResult.get().put("status", Retcode.NOPAYORDEROUTOFLIMIT.code);
				errorResult.get().put("message", Retcode.NOPAYORDEROUTOFLIMIT.msg);
				Collections.reverse(list);
				OpTaxiOrder o = list.get(0);
				errorResult.get().put("orderno", o.getOrderno());
				errorResult.get().put("usetype", o.getUsetype());
				errorResult.get().put("ordertype", o.getOrdertype());
				return false;
			}
		}
		logger.info("检查司机未付结订单是否超限完成");
		return true;
	}

    /**
     * 检查是否存在未付结订单(出租车需要检查)
     * @param hasnotpayorder
     * @return
     */
    @ValidateRule(msg="您有未结算订单，请结算完成后再提现")
    private boolean checkNotPayOrder(boolean hasnotpayorder){
    	//网约车不检查
    	if(!DriverEnum.DRIVER_TYPE_TAXI.code.equals(driver.get().getVehicletype() + "")) return true;
        logger.info("检查司机是否存在未付结订单...");
        OrderListParam olp = new OrderListParam();
        olp.setType(2);
        olp.setDriverid(driver.get().getId());
        List<OpTaxiOrder> list = taxiorderfuncdao.getTaxiOrderList(olp);
        if(list != null && !list.isEmpty()){
            Collections.reverse(list);
            OpTaxiOrder o = list.get(0);
            logger.info("存在未付结订单:" + o.getOrderno());
            errorResult.get().put("status", Retcode.DRIVERNOPAYORDER.code);
            errorResult.get().put("message", Retcode.DRIVERNOPAYORDER.msg);
            errorResult.get().put("orderno", o.getOrderno());
            errorResult.get().put("usetype", o.getUsetype());
            errorResult.get().put("ordertype", o.getOrdertype());
            return false;
        }
        logger.info("检查司机是否存在未付结订单完成");
        return true;
    }
    
    /**
     * 校验usetype
     * @param usetype
     * @return
     */
    @ValidateRule(msg="用车类型错误")
    private boolean checkUseType(String usetype){
    	this.usetype.set(OrderEnum.getUseType(usetype));
    	return this.usetype.get() != null;
    }
    
    /**
     * 校验ordertype
     * @param ordertype
     * @return
     */
    @ValidateRule(msg="订单类型错误")
    private boolean checkOrderType(String ordertype){
    	this.ordertype.set(OrderEnum.getOrderType(ordertype));
    	return this.ordertype.get() != null;
    }
}
