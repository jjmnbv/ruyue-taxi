package com.szyciov.operate.service;

import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.enums.PurseEnum;
import com.szyciov.entity.UserNews;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.SystemNewsEnum;
import com.szyciov.enums.WithdrawEnum;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.message.CashMessage;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.*;
import com.szyciov.op.param.PubDriverAccountQueryParam;
import com.szyciov.op.param.cashManager.CashManageQueryParam;
import com.szyciov.op.vo.cashManager.CashManagerExcelVo;
import com.szyciov.op.vo.cashManager.CashManagerIndexVo;
import com.szyciov.operate.dao.OpCashManageDao;
import com.szyciov.operate.dao.OpUserAccountDao;
import com.szyciov.operate.dao.PubDriverAccountDao;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OpCashManageService {

	private Logger logger = LoggerFactory.getLogger(OpCashManageService.class);

	@Autowired
	private OpCashManageDao leCashManageDao;

	@Autowired
	private PubDriverAccountDao accountDao;

	@Autowired
	private OpUserAccountDao opUserAccountDao;

	@Autowired
	private OpInformationSetService opInformationSetService;

	private TemplateHelper templateHelper = new TemplateHelper();


	public List<Map<String, Object>> getAccounts(Map<String,String> params){
		return leCashManageDao.getAccounts(params);
	}

	public List<Map<String, Object>> getNames(Map<String, String> params) {
		return leCashManageDao.getNames(params);
	}

	public PageBean getCashByQuery(CashManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<CashManagerIndexVo> list = listCashByQuery(queryParam);
		int iTotalRecords = getListCashCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	private int getListCashCountByQuery(CashManageQueryParam queryParam) {
		return leCashManageDao.getListCashCountByQuery(queryParam);
	}

	private List<CashManagerIndexVo> listCashByQuery(CashManageQueryParam queryParam) {
		return leCashManageDao.listCashByQuery(queryParam);
	}

	/**
	 * 提现拒绝
	 * @param params
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class})
	public synchronized Map<String, Object> cashReject(Map<String, String> params)throws Exception {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", "success");
		res.put("message", "更新数据成功");
		try{
			CashManageQueryParam param = new CashManageQueryParam();
			param.setId(params.get("id"));
			param.setPlatformtype(PlatformTypeByDb.OPERATING.code);
			if(leCashManageDao.getListCashCountByQuery(param)>0){
				leCashManageDao.cashReject(params);
				String processid = params.get("id");
				Map<String,Object> userinfo = leCashManageDao.getUserInfoByProcessId(processid);

				if(userinfo!=null){
					userinfo.put("oper", params.get("processuserid"));

					//用户类型
					String usertype = (String) userinfo.get("usertype");
					//用户ID
					String userid = (String) userinfo.get("userid");
					//电话
					String account = (String) userinfo.get("account");
					//金额
					double amount = Double.parseDouble(String.valueOf(userinfo.get("amount")));
					//银行名称
					String bankname = (String) userinfo.get("bankname");
					//卡号？
					String creditcardnum = (String) userinfo.get("creditcardnum");
					//申请时间
					Date applytimeobj = (Date)userinfo.get("applytime");
					String applytimeStr  = DateUtil.format(applytimeobj);

					//发送app类型
					String sendAppType = AppMessageUtil.APPTYPE_DRIVER;
					//获取运管端平台信息
					OpPlatformInfo opPlatformInfo = opInformationSetService.getOpPlatformInfo();
					//平台名称
					String companyname = opPlatformInfo.getCompanyshortname();
					//平台电话
					String servicesphone = opPlatformInfo.getServcieTel();

					//拒绝原因
					String reson = "提现不予受理，如有疑问，请联系"+servicesphone;

					List<String> tag_ands = null;

					if(StringUtils.isNotBlank(account)){
						String content = "";
						//如果是司机，则还需发送推送
						if(WithdrawEnum.USER_TYPE_DRIVER.code.equalsIgnoreCase(usertype)){
							//恢复金额，添加记录
							leCashManageDao.recoverMoney4Driver(userinfo);
							//保存司机记录
							this.saveDriverExpenses(userinfo);

							sendAppType = AppMessageUtil.APPTYPE_DRIVER;
							//获取推送内容
							//content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.send2driver4Reject", amount, applytimeStr, servicesphone, companyname);
							content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.send2driver4Reject", amount, applytimeStr, servicesphone);
							//保存司机系统消息
							this.saveDriverNews("31",userid,companyname,amount,bankname,creditcardnum,applytimeStr,reson);

						}else if(WithdrawEnum.USER_TYPE_USER.code.equals(usertype)){
							//恢复金额，添加记录
							leCashManageDao.recoverMoney4PeUser(userinfo);
							//保存用户消费记录
							this.savePeUserExpenses(userinfo);

							sendAppType = AppMessageUtil.APPTYPE_PASSENGER;

							//获取推送内容
							//content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.send2passenger4Reject", amount, applytimeStr,servicesphone,companyname);
							content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.send2passenger4Reject", amount, applytimeStr,servicesphone);
							//添加乘客系统消息
							this.saveUserNews(UserNewsParam.PE_USERNEW_TABNAME,"32",userid,companyname,amount,bankname,creditcardnum,applytimeStr,reson);
							tag_ands = new ArrayList<String>();
							tag_ands.add("1");
						}

						this.sendMessage(account,content,sendAppType, UserMessage.CASHREJECT,tag_ands);
					}
				}
			}else{
				res.put("status", "success");
				res.put("message", "记录已被处理！");
			}
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message", "系统繁忙，请稍后再试！");
			logger.error("拒绝提现异常：param{}",params,e);
			throw e;
		}

		return res;
	}


	/**
	 * 保存司机余额明细
	 * @param userinfo
	 */
	private void saveDriverExpenses(Map<String,Object> userinfo){
		String driverId = (String)userinfo.get("userid");
		String oper = (String)userinfo.get("oper");
		PubDriverAccountQueryParam param = new PubDriverAccountQueryParam();
		param.setQueryDriver(driverId);
		param.setiDisplayLength(10);
		List<PubDriverAccount> list = accountDao.getOrgDriverAccountListByQuery(param);
		if(list!=null&&list.size()>0){
			PubDriverAccount account = list.get(0);
			PubDriverExpenses expenses = new PubDriverExpenses();
			expenses.setId(GUIDGenerator.newGUID());
			expenses.setDriverid(driverId);
			expenses.setAmount(((BigDecimal) userinfo.get("amount")).doubleValue());
			expenses.setBalance(account.getBalance());
			expenses.setCreater(oper);
			expenses.setCreatetime(new Date());
			expenses.setDetailtype(PurseEnum.DETAILTYPE_BALANCE.code);
			expenses.setTradetype(PurseEnum.TRADETYPE_REFUND.code);
			expenses.setExpensetype(PurseEnum.EXPENSETYPE_WITHDRAW.code);
			expenses.setExpensetime(new Date());
			expenses.setRemark("提现受理失败，返还提现申请金额");
			expenses.setUpdater(oper);
			expenses.setUpdatetime(new Date());
			expenses.setStatus(1);
			expenses.setOperateresult(PurseEnum.OPERATERESULT_FAILED.code);
			expenses.setPlatformtype(PlatformTypeByDb.OPERATING.code);
			accountDao.savePubDriverExpenses(expenses);
		}
	}



	/**
	 * 保存乘客余额明细
	 * @param userinfo
	 */
	private void savePeUserExpenses(Map<String,Object> userinfo){
		String userId = (String)userinfo.get("userid");
		String oper = (String)userinfo.get("oper");
		OrganUserAccountQueryParam param = new OrganUserAccountQueryParam();
		param.setUserId(userId);
		param.setiDisplayLength(10);
		List<PeUser> list = opUserAccountDao.getOpUserAccountListByQuery(param);
		if(list!=null&&list.size()>0){
			PeUser account = list.get(0);
			PeUserExpenses expenses = new PeUserExpenses();
			expenses.setId(GUIDGenerator.newGUID());
			expenses.setUserid(userId);
			expenses.setAmount((BigDecimal) userinfo.get("amount"));
			expenses.setBalance(account.getBalance());
			expenses.setCreater(oper);
			expenses.setCreatetime(new Date());
			expenses.setDetailtype(PurseEnum.DETAILTYPE_BALANCE.code);
			expenses.setTradetype(PurseEnum.TRADETYPE_DEPOSIT.code);
//			expenses.setExpensetype(PurseEnum.TRADETYPE_DEPOSIT.code);
			expenses.setExpensetime(new Date());
			expenses.setRemark("提现受理失败，返还提现申请金额");
			expenses.setUpdater(oper);
			expenses.setUpdatetime(new Date());
			expenses.setStatus("1");
			expenses.setOperateresult(PurseEnum.OPERATERESULT_FAILED.code);
			accountDao.savePeUserExpenses(expenses);
		}
	}

	/**
	 * 确认提现
	 * @param params
	 * @return
	 */
	public Map<String, Object> cashOk(Map<String, String> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", "success");
		res.put("message", "更新数据成功");
		try{
			leCashManageDao.cashOk(params);
			//确认提现操作发送短信和推送给提现的人
			String processid = params.get("id");
			Map<String,Object> userinfo = leCashManageDao.getUserInfoByProcessId(processid);
			if(userinfo!=null){
				//用户类型
				String usertype = (String) userinfo.get("usertype");
				//用户ID
				String userid = (String) userinfo.get("userid");
				//电话
				String account = (String) userinfo.get("account");
				//金额
				double amount = Double.parseDouble(String.valueOf(userinfo.get("amount")));
				//银行名称
				String bankname = (String) userinfo.get("bankname");
				//卡号？
				String creditcardnum = (String) userinfo.get("creditcardnum");
				//申请时间
				Date applytimeobj = (Date)userinfo.get("applytime");
				String applytimeStr  = DateUtil.format(applytimeobj);

				//发送app类型
				String sendAppType = AppMessageUtil.APPTYPE_DRIVER;
				//获取运管端平台信息
				OpPlatformInfo opPlatformInfo = opInformationSetService.getOpPlatformInfo();
				//平台名称
				String companyname = opPlatformInfo.getCompanyshortname();
				//平台电话
				String servicesphone = opPlatformInfo.getServcieTel();
				List<String> tag_ands = null;

				String content = "";
				if(StringUtils.isNotBlank(account)) {
					//发送乘客端
					if (WithdrawEnum.USER_TYPE_USER.code.equalsIgnoreCase(usertype)) {

						sendAppType =  AppMessageUtil.APPTYPE_PASSENGER;
						//获取推送内容
						//content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.send2passenger4OK", amount  , applytimeStr,servicesphone,companyname);
						content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.send2passenger4OK", amount  , applytimeStr,servicesphone);
						//添加乘客系统消息
						this.saveUserNews(UserNewsParam.PE_USERNEW_TABNAME,"29",userid,companyname,amount,bankname,creditcardnum,applytimeStr);
						tag_ands = new ArrayList<String>();
						tag_ands.add("1");
						//发送司机端
					} else if (WithdrawEnum.USER_TYPE_DRIVER.code.equalsIgnoreCase(usertype)) {

						sendAppType =  AppMessageUtil.APPTYPE_DRIVER;
						//获取推送内容
						//content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.send2driver4OK", amount  , applytimeStr, servicesphone, companyname);
						content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.send2driver4OK", amount  , applytimeStr, servicesphone);
						//保存司机系统消息
						this.saveDriverNews("28",userid,companyname,amount,bankname,creditcardnum,applytimeStr);
					}

					this.sendMessage(account,content,sendAppType, UserMessage.CASHOK,tag_ands);
				}

			}
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message","系统繁忙，请稍后再试！");
			logger.error("确认提现异常：param{}",params,e);
		}
		return res;
	}


	/**
	 * 发送消息
	 * @param account
	 * @param content
	 */
	private void sendMessage(String account,String content,String sendAppType,String cashtype,List<String> tag_ands){
		List<String> userids = new ArrayList<String>();
		userids.add(account);
		String hintCode = PushObjFactory.HINT_CASHOK;
		CashMessage message = new CashMessage();
		message.setSMSParam(content);
        if(UserMessage.CASHREJECT.equals(cashtype)){
            content = "抱歉，您的提现申请审核未通过";
			hintCode = PushObjFactory.HINT_CASHREJECT;
        }
        message.setPushParam(sendAppType,content, tag_ands,userids,hintCode);
        MessageUtil.sendMessage(message);
	}



	/**
	 * 保存用户消息
	 * @param tbName		用户消息表
	 * @param messageType	消息类型
	 * @param newsparams	消息参数
	 */
	private void saveUserNews(String tbName,String messageType,Object... newsparams){

		UserNewsParam userNewsParam = new UserNewsParam();
		UserNews userNews = new UserNews();
		userNews.setId(GUIDGenerator.newGUID());
		userNews.setType(SystemNewsEnum.NEWS_TYPE_SYSTEM.code);
		userNews.setNewsstate(SystemNewsEnum.NEWS_STATE_UNREAD.code);
		userNews.setUserid((String)newsparams[0]);
		userNews.setCreatetime(new Date());
		userNews.setUpdatetime(new Date());
		userNews.setStatus(1);
		//添加系统消息
		JSONObject newscontent = new JSONObject();
		newscontent.put("type", messageType);

		newscontent.put("account4company", newsparams[1]);
		newscontent.put("amount", newsparams[2]);
		newscontent.put("bankname", newsparams[3]);
		newscontent.put("creditcardnum", newsparams[4]);
		newscontent.put("applytime", newsparams[5]);
		newscontent.put("title", "提现通知");
		//拒绝提现需加上
		if("32".equals(messageType)) {
			newscontent.put("processreason", newsparams[6]);
		}

		userNews.setContent(newscontent.toString());

		userNewsParam.setUserNews(userNews);

		userNewsParam.setUserNewsTbName(tbName);

		templateHelper.dealRequestWithTokenCarserviceApiUrl("/UserNews/addUserNews", HttpMethod.POST, null,
				userNewsParam,JSONObject.class);

	}

	/**
	 * 保存司机系统消息
	 * @param newsparams
	 */
	private void saveDriverNews(String type ,Object... newsparams){
		PubDriverNews driverNews = new PubDriverNews();
		driverNews.setId(GUIDGenerator.newGUID());
		driverNews.setType(SystemNewsEnum.NEWS_TYPE_SYSTEM.code);
		driverNews.setNewsstate(SystemNewsEnum.NEWS_STATE_UNREAD.code);
		driverNews.setUserid((String)newsparams[0]);
		driverNews.setCreatetime(new Date());
		driverNews.setUpdatetime(new Date());
		driverNews.setStatus(1);

		//添加系统消息
		JSONObject newscontent = new JSONObject();
		newscontent.put("type", type);
		newscontent.put("account4company", newsparams[1]);
		newscontent.put("amount", newsparams[2]);
		newscontent.put("bankname", newsparams[3]);
		newscontent.put("creditcardnum", newsparams[4]);
		newscontent.put("applytime", newsparams[5]);
		newscontent.put("content", newsparams[5]);
		newscontent.put("title", "提现通知");
		//拒绝提现需加上
		if("31".equals(type)) {
			newscontent.put("remark", newsparams[6]);
		}else{
			newscontent.put("remark", "提现成功");
		}
		driverNews.setContent(newscontent.toString());

		templateHelper.dealRequestWithTokenCarserviceApiUrl("/DriverNews/addDriverNews", HttpMethod.POST, null,
				driverNews,JSONObject.class);
	}


	public List<CashManagerExcelVo> listExportData(CashManageQueryParam queryParam) {
		return leCashManageDao.listExportData(queryParam);
	}


	public Map<String, Object> getListExportDataCount(CashManageQueryParam queryParam) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", "success");
		try{
			res.put("count",leCashManageDao.getListExportDataCount(queryParam));
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message","判断导出数据时异常");
		}
		return res;
	}

}
