package com.szyciov.carservice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.AwardPassengerDao;
import com.szyciov.carservice.dao.DriverDao;
import com.szyciov.carservice.dao.OrderApiDao;
import com.szyciov.carservice.dao.PubInfoDao;
import com.szyciov.carservice.util.MessageConnection;
import com.szyciov.carservice.util.OrderMessageUtil;
import com.szyciov.carservice.util.OrderRedisMessageFactory;
import com.szyciov.carservice.util.OrderRedisMessageFactory.RedisMessageType;
import com.szyciov.carservice.util.sendservice.FactoryProducer;
import com.szyciov.carservice.util.sendservice.factory.SendServiceFactory;
import com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.entity.OrderInfoMessage;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.enums.DriverState;
import com.szyciov.driver.enums.MessageType;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.OrdersortColumn;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.enums.PurseEnum;
import com.szyciov.driver.enums.ReviewState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.driver.param.OrderStatisticsParam;
import com.szyciov.dto.coupon.CouponInfoDTO;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.CommonRabbitData;
import com.szyciov.entity.DataStatus;
import com.szyciov.entity.NewsState;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.OrderMessageFactory;
import com.szyciov.entity.OrderMessageFactory.OrderMessageType;
import com.szyciov.entity.OrderNO;
import com.szyciov.entity.OrderReviewParam;
import com.szyciov.entity.OrderSource4OrderNO;
import com.szyciov.entity.PayMethod;
import com.szyciov.entity.PlatformType;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubJpushlog;
import com.szyciov.entity.PubOrderCancel;
import com.szyciov.entity.PubOrderCancelRule;
import com.szyciov.entity.PubSendrules;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.TaxiOrderCost;
import com.szyciov.entity.UserNews;
import com.szyciov.enums.CommonRabbitEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.OrderVarietyEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.PubJpushLogEnum;
import com.szyciov.enums.PubOrdercancelEnum;
import com.szyciov.enums.PubPremiumRuleEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.enums.SendRulesEnum;
import com.szyciov.enums.VehicleEnum;
import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgSendrecord;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.message.OrderMessage;
import com.szyciov.message.TaxiOrderMessage;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxisendrules;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserExpenses;
import com.szyciov.op.entity.PeUseraccount;
import com.szyciov.op.entity.PubVehicle;
import com.szyciov.op.param.PeUserParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.OrgUserParam;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.PremiumRuleParam;
import com.szyciov.param.PubPushLogParam;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.ResultData;
import com.szyciov.util.SMMessageUtil;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("OrderApiService")
public class OrderApiService {
	private Logger logger = LoggerFactory.getLogger(OrderApiService.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	private OrderApiDao dao;
	private PubInfoDao infoDao;
	private BaiduApiService baiduApi;
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private DriverDao driverDao;

	private AwardPassengerDao awardDao;
	
	@Autowired
    private UserNewsService userNewsService;
	
	private OrderCancelRuleService orderCancelRuleService;
	
    @Autowired
    private OpOrderApiService opOrderApiService;

    @Autowired
    private OpTaxiOrderManageService opTaxiOrderManageService;

    private RabbitService rabbitService;
    
    private PremiumRuleService premiumRuleService;
    
    @Resource(name="PremiumRuleService")
    public void setPremiumRuleService(PremiumRuleService premiumRuleService) {
    	this.premiumRuleService = premiumRuleService;
    }
    
    @Resource(name="OrderCancelRuleService")
	public void setOrderCancelRuleService(OrderCancelRuleService orderCancelRuleService) {
		this.orderCancelRuleService = orderCancelRuleService;
	}
	

	@Resource(name="AwardPassengerDao")
	public void setAwardDao(AwardPassengerDao awardDao) {
		this.awardDao = awardDao;
	}
	
	@Resource(name="RabbitService")
	public void setRabbitService(RabbitService rabbitService) {
		this.rabbitService = rabbitService;
	}
    
	@Resource(name="OrderApiDao")
	public void setOrderApiDao(OrderApiDao dao) {
 		this.dao = dao;
	}
	
	@Resource(name="PubInfoDao")
	public void setInfoDao(PubInfoDao infoDao) {
		this.infoDao = infoDao;
	}
	
	@Resource(name="baiduApiService")
	public void setInfoDao(BaiduApiService baiduApi) {
		this.baiduApi = baiduApi;
	}
	
	@Resource(name="threadPoolTaskExecutor")
	public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

	private MileageApiService mileageApiService;
    @Resource(name = "MileageApiService")
    public void setMileageApiService(MileageApiService mileageApiService) {
        this.mileageApiService = mileageApiService;
    }

    /**
	 * 检查参数有效性,主要检测订单号
	 * @return
	 */
	private OrderInfoDetail checkParam(OrderApiParam param,JSONObject result){
		result.clear();
		OrderInfoDetail oid = null;
		if(param.getOrderno() == null){
			result.put("status", Retcode.ORDERNOTEXIT.code);
			result.put("message", Retcode.ORDERNOTEXIT.msg);
			return oid;
		}
		//根据订单号设置订单来源属性
		param.setOrderprop(OrderVarietyEnum.getOrderVariety(param.getUsetype(), param.getOrdertype()).icode);
		oid = dao.getOrderInfoById(param);
		if(oid == null){
			result.put("status", Retcode.ORDERNOTEXIT.code);
			result.put("message", Retcode.ORDERNOTEXIT.msg);
		}else{
			result.put("order", oid);
			result.put("status", Retcode.OK.code);
			result.put("message", Retcode.OK.msg);
		}
		return oid;
	}
	
	/**
	 * 检查运管端出租车下单权限
	 * @param orderInfo
	 * @return
	 */
	private boolean checkPermission(OpTaxiOrder orderInfo,PeUser user,OpTaxisendrules sendRule,TaxiOrderCost oc,JSONObject result) {
		if(orderInfo == null || sendRule == null || user == null || oc == null) return false;
		//验证用车时间
		if(!checkUnderTimeValid(orderInfo, result)) return false;
		//校验用户是否能够下单
		if(!checkUserCanOrder(user,result)) return false;
//		//扣除个人账户余额
//		if(!takeOffBalance(user,oc.getCost(),result)) return false;
		return true;
	}
	
	/**
	 * 检查下单权限-运管平台
	 * @param orderInfo
	 * @return
	 */
	private boolean checkPermission(OpOrder orderInfo,PeUser user,PubSendrules sendRule,OrderCost oc,JSONObject result){
		if(orderInfo == null || sendRule == null || user == null || oc == null) return false;
		//验证用车时间
		if(!checkUnderTimeValid(orderInfo, result)) return false;
		//校验用户是否能够下单
		if(!checkUserCanOrder(user,result)) return false;
		//扣除个人账户余额
//		if(!takeOffBalance(user,oc.getCost(),result)) return false;
		return true;
	}
	
	/**
	 * 检查下单权限-租赁平台
	 * @param orderInfo
	 * @return
	 */
	private boolean checkPermission(OrgOrder orderInfo,OrgUser user,PubSendrules sendRule,OrderCost oc,JSONObject result){
		if(orderInfo == null || sendRule == null || user == null || oc == null) return false;
		//验证机构用户是否存在用车规则
        if(!checkOrgUsecarrules(orderInfo, result)) return false;
        //验证用车时间
		if(!checkUnderTimeValid(orderInfo, result)) return false;
		//是否有未支付订单的校验
		if(!checkUserCanOrder(user,orderInfo,result)) return false;
		//扣除机构账户余额
		if(!takeOffBalance(orderInfo,oc.getCost(),result)) return false;
		return true;
	}
	
	/**
	 * 获取派单规则
	 * @param orderinfo
	 * @param result
	 * @return
	 */
	private OpTaxisendrules getSendRules(OpTaxiOrder orderInfo,JSONObject result){
		OpTaxisendrules taxisendrules = new OpTaxisendrules();
		taxisendrules.setCity(orderInfo.getOncity());
		List<OpTaxisendrules> taxisendrulesList = dao.getOpTaxiSendrulesByCity(taxisendrules);
		OpTaxisendrules instantlySendrules = null;               //即刻派单规则
		OpTaxisendrules appointmentSendrules = null;        //预约派单规则
		for (OpTaxisendrules opTaxisendrules : taxisendrulesList) {
			if(SendRulesEnum.USETYPE_APPOINTMENT.code.equals(opTaxisendrules.getUsetype())) { //查询预约派单规则
				appointmentSendrules = opTaxisendrules;
			} else if(SendRulesEnum.USETYPE_INSTANTLY.code.equals(opTaxisendrules.getUsetype())) {
				instantlySendrules = opTaxisendrules;
			}
		}
		//先按照预约规则查一次,如果没有预约规则按即刻规则处理,如果即刻规则也没有就失败
		if(appointmentSendrules == null){
			orderInfo.setIsusenow(true);
			taxisendrules = instantlySendrules;
		//如果预约规则存在,则判断订单是否为即刻订单,如果是即刻订单,取即刻规则,没有即刻规则,就用预约规则
		}else{
			//计算公式:用车时间-当前时间<=约车时限(数据库单位:分钟)?即刻:约车
			boolean usenow = true;
			if(null != orderInfo.getUsetime()) {
				usenow = (orderInfo.getUsetime().getTime()-System.currentTimeMillis())/1000<=60*appointmentSendrules.getCarsinterval();
			}
            orderInfo.setIsusenow(usenow);
			taxisendrules = appointmentSendrules;  //即刻单如果没有即刻规则,用预约规则
			if(usenow && instantlySendrules != null){
				taxisendrules = instantlySendrules;
			}else{
				orderInfo.setIsusenow(false);
			}
		}
		if(taxisendrules == null){
			result.clear();
			result.put("status", Retcode.NOSERVICESINCITY.code);
			result.put("message", getNoservicesincityMsg(orderInfo.getOncity(), orderInfo.getOrdertype()));
			logger.info("==================================================");
			logger.info("=                                            获取派单规则失败.                                               =");
			logger.info("==================================================");
		}else{
			try{
				//如果是纯人工派单方式,改变订单字段为人工指派
				int sendType = SendRulesEnum.SENDTYPE_SYSTEM_ONLY.code.equals(taxisendrules.getSendtype()+"") ? 1 : 0;
				orderInfo.setSendordertype(sendType);
				String sUseNow = (orderInfo.isIsusenow()?"即刻":"预约");
				String sOrderType = orderInfo.getOrdertype().equals("1")?"约车": 
													orderInfo.getOrdertype().equals("2")?"接机":
													orderInfo.getOrdertype().equals("3")?"送机":"";
				String sSendType = SendRulesEnum.getSendType(taxisendrules.getSendtype() + "").msg;
				String sSendModel = SendRulesEnum.getSendModel(taxisendrules.getSendmodel() + "").msg;
				logger.info("==================================================");
				logger.info("=                                            获取到的派单规则:                                               =");
				logger.info("=\t\t\t\t\t  用车类型:("+sUseNow+" "+sOrderType+")\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  派单方式:("+sSendType+")\t\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  派单模式:("+sSendModel+")\t\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  初始派单半径:"+taxisendrules.getInitsendradius()+"公里\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  半径递增比:"+taxisendrules.getIncreratio()+"公里\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  最大派单半径:"+taxisendrules.getMaxsendradius()+"公里\t\t\t\t\t\t  =");
				logger.info("==================================================");
			}catch(Exception e){}
		}
		return taxisendrules;
	}
	
	/**
	 * 获取派单规则
	 * @param orderInfo
	 * @param result
	 * @return
	 */
	private PubSendrules getSendRules(AbstractOrder orderInfo,JSONObject result){
		PubSendrules param = new PubSendrules();
		PubSendrules sendRule = new PubSendrules();
		param.setLeasescompanyid(orderInfo.getCompanyid());
		param.setVehicletype(Integer.valueOf(OrderEnum.ORDERSTYPE_CAR.code));
		param.setUsetype(orderInfo.isIsusenow() ? "1" : "0");
		param.setCity(orderInfo.getOncity());
        if(orderInfo instanceof OrgOrder) {
        	param.setPlatformtype(PlatformTypeByDb.LEASE.code);
        } else {
        	param.setPlatformtype(PlatformTypeByDb.OPERATING.code);
        }
		sendRule = dao.getSendRule(param);
		//先按照预约规则查一次,如果没有预约规则按即刻规则处理,如果即刻规则也没有就失败
		if(sendRule == null){
			orderInfo.setIsusenow(true);
			param.setUsetype(orderInfo.isIsusenow() ? "1" : "0");
			sendRule = dao.getSendRule(param);
		//如果预约规则存在,则判断订单是否为即刻订单,如果是即刻订单,取即刻规则,没有即刻规则,就用预约规则
		}else{
			boolean usenow = true;
			//计算公式:用车时间-当前时间<=约车时限(数据库单位:分钟)?即刻:约车
			if(null != orderInfo.getUsetime()) {
				usenow = (orderInfo.getUsetime().getTime()-System.currentTimeMillis())/1000<=60*sendRule.getCarsinterval();
			}
			orderInfo.setIsusenow(usenow);
			if(usenow){
				param.setUsetype(orderInfo.isIsusenow() ? "1" : "0");
				PubSendrules useNowRule = new PubSendrules();
				useNowRule = dao.getSendRule(param);
				if(useNowRule != null)  sendRule = useNowRule;
				else orderInfo.setIsusenow(false);  //即刻单如果没有即刻规则,用预约规则
			}
		}
		if(sendRule == null){
			result.clear();
			result.put("status", Retcode.NOSERVICESINCITY.code);
            result.put("message", getNoservicesincityMsg(orderInfo.getOncity(), orderInfo.getOrdertype()));
			logger.info("==================================================");
			logger.info("=                                            获取派单规则失败.                                               =");
			logger.info("==================================================");
		}else{
			try{
				//如果是纯人工派单方式,改变订单字段为人工指派
				int sendType = SendRulesEnum.SENDTYPE_SYSTEM_ONLY.code.equals(sendRule.getSendtype()+"") ? 1 : 0;
				orderInfo.setSendordertype(sendType);
				String sUseNow = (orderInfo.isIsusenow()?"即刻":"预约");
				String sOrderType = orderInfo.getOrdertype().equals("1")?"约车": 
													orderInfo.getOrdertype().equals("2")?"接机":
													orderInfo.getOrdertype().equals("3")?"送机":"";
				String sSendType = SendRulesEnum.getSendType(sendRule.getSendtype() + "").msg;
				String sSendModel = SendRulesEnum.getSendModel(sendRule.getSendmodel() + "").msg;
				String sUpgrade = SendRulesEnum.getVehicleUpgrade(sendRule.getVehicleupgrade() + "").msg;
				logger.info("==================================================");
				logger.info("=                                            获取到的派单规则:                                               =");
				logger.info("=\t\t\t\t\t  用车类型:("+sUseNow+" "+sOrderType+")\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  派单方式:("+sSendType+")\t\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  派单模式:("+sSendModel+")\t\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  允许升级:("+sUpgrade+")\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  特殊司机派单时长:"+sendRule.getSpecialinterval()+"秒\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  初始派单半径:"+sendRule.getInitsendradius()+"公里\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  半径递增比:"+sendRule.getIncreratio()+"公里\t\t\t\t\t\t  =");
				logger.info("=\t\t\t\t\t  最大派单半径:"+sendRule.getMaxsendradius()+"公里\t\t\t\t\t\t  =");
				logger.info("==================================================");
			}catch(Exception e){}
		}
		return sendRule;
	}
	
	/**
	 * 	验证用车时间
	 * @param orderInfo
	 * @param result
	 * @return
	 */
	private boolean checkUnderTimeValid(AbstractOrder orderInfo,JSONObject result){
		Date nowDate = new Date();
		if(null == orderInfo.getUsetime()) {
			orderInfo.setUsetime(new Date());
		} else if(orderInfo.getUsetime().getTime() < nowDate.getTime()) {
			result.clear();
			result.put("status", Retcode.BEFORENOWTIME.code);
			result.put("message", Retcode.BEFORENOWTIME.msg);
			return false;
		}
		orderInfo.setUndertime(nowDate);
		orderInfo.setCreatetime(nowDate);
		orderInfo.setUpdatetime(nowDate);
		return true;
	}
	
	
	
	/**
	 * 计算预估费用
	 * @param orderInfo
	 * @param result
	 * @return
	 */
	private TaxiOrderCost countCost(OpTaxiOrder orderInfo,JSONObject result){
		TaxiOrderCost oc = new TaxiOrderCost();
		//获取预估费用
		OrderCostParam costParam = new OrderCostParam();
		costParam.setCity(orderInfo.getOncity());
		costParam.setOnaddrlat(orderInfo.getOnaddrlat());
		costParam.setOnaddrlng(orderInfo.getOnaddrlng());
		costParam.setOffaddrlat(orderInfo.getOffaddrlat());
		costParam.setOffaddrlng(orderInfo.getOffaddrlng());
		costParam.setMeterrange(orderInfo.getMeterrange());
		costParam.setSchedulefee(orderInfo.getSchedulefee());
		costParam.setOrdertype(orderInfo.getOrdertype());
		JSONObject json = getOpTaxiOrderCost(costParam);
		if(json.containsKey("status") && json.getInt("status") != Retcode.OK.code){
			result.put("status", json.getInt("status"));
			result.put("message", json.getString("message"));
            return null;
		}else{
			oc = StringUtil.parseJSONToBean(json.getJSONObject("taxiOrderCost").toString(), TaxiOrderCost.class);
			logger.info("==================================================");
			logger.info("=                                       获取到的计费规则:                                                    =");
			logger.info("=\t\t\t\t\t  起步价:"+oc.getStartprice()+"元\t\t\t\t\t\t\t\t  =");
			logger.info("=\t\t\t\t\t  续租价:"+oc.getRenewalprice()+"/公里\t\t\t\t\t\t\t  =");
			logger.info("=\t\t\t\t\t  调度费:"+oc.getSchedulefee()+"元/分钟\t\t\t\t\t\t\t  =");
			logger.info("=\t\t\t\t\t  预估费用:"+oc.getCost()+"元\t\t\t\t\t\t\t  =");
			logger.info("=================================================="); 
		}
		return oc;
	}
	
	/**
	 * 计算预估费用
	 * @param orderInfo
	 * @param result
	 * @return
	 */
	private OrderCost countCost(AbstractOrder orderInfo,JSONObject result){
		OrderCostParam ocp = new OrderCostParam();
		ocp.setHasunit(false);
		String model = null;
		if(orderInfo instanceof OpOrder){
            if(StringUtils.isBlank(orderInfo.getSelectedmodel())) {
                result.put("status", Retcode.NOSERVICESINCITY.code);
                result.put("message", getNoservicesincityMsg(orderInfo.getOncity(), orderInfo.getOrdertype()));
                return null;
            }

			model = ((OpOrder)orderInfo).getPricemodel();
			ocp.setOrderprop(OrderVarietyEnum.OPERATING_NET.icode);
		}else if (orderInfo instanceof OrgOrder) {
			if(!checkUserRule(orderInfo,result)) return null;
			model = ((OrgOrder)orderInfo).getPricemodel();
			ocp.setOrderprop(OrderVarietyEnum.LEASE_NET.icode);
			ocp.setUserid(orderInfo.getUserid());
			ocp.setOrganid(((OrgOrder)orderInfo).getOrganid());
			ocp.setCompanyid(orderInfo.getCompanyid());
			ocp.setUsetype(orderInfo.getUsetype());
			ocp.setRulestype("1");
		}
		if(StringUtils.isNotBlank(model)){
			ocp.setCartype(model);
		}else{
			ocp.setCartype(orderInfo.getSelectedmodel());
		}
		ocp.setCity(orderInfo.getOncity());
		ocp.setOrdertype(orderInfo.getOrdertype());
		ocp.setOnaddrlng(orderInfo.getOnaddrlng());
		ocp.setOnaddrlat(orderInfo.getOnaddrlat());
		ocp.setOffaddrlng(orderInfo.getOffaddrlng());
		ocp.setOffaddrlat(orderInfo.getOffaddrlat());
		ocp.setIsusenow(orderInfo.isIsusenow());
		
		JSONObject json = new JSONObject();
		json = getOrderCost(ocp);
		OrderCost oc = null;
		if(!json.containsKey("status")){
			oc = StringUtil.parseJSONToBean(json.toString(), OrderCost.class);
			String sTimeType = oc.getTimetype() == 0?"总用时":"低速用时";
			logger.info("==================================================");
			logger.info("=                                       获取到的计费规则:                                                    =");
			logger.info("=\t\t\t\t\t  时间类型:("+sTimeType+")\t\t\t\t\t\t\t  =");
			logger.info("=\t\t\t\t\t  起步价:"+oc.getStartprice()+"元\t\t\t\t\t\t\t\t  =");
			logger.info("=\t\t\t\t\t  里程费:"+oc.getRangeprice()+"/公里\t\t\t\t\t\t\t  =");
			logger.info("=\t\t\t\t\t  时长费:"+oc.getTimeprice()+"元/分钟\t\t\t\t\t\t\t  =");
			logger.info("=\t\t\t\t\t  预估费用:"+oc.getCost()+"元\t\t\t\t\t\t\t  =");
			logger.info("=\t\t\t\t\t  预估时长费:"+oc.getTimecost()+"元\t\t\t\t\t\t\t  =");
			logger.info("=\t\t\t\t\t  预估里程费:"+oc.getRangecost()+"元\t\t\t\t\t\t\t  =");
			logger.info("==================================================");
		}else{
			result.put("status", json.getInt("status"));
            if(orderInfo instanceof OrgOrder) {
                result.put("message", getNoOrgVehicleModelMsg(orderInfo.getSelectedmodel()));
            } else {
                result.put("message", getNoServiceMsg(orderInfo.getOncity(), orderInfo.getSelectedmodel()));
            }
		}
		return oc;
	}
	
	/**
	 * 查询客户计费规则
	 * @return
	 */
	private boolean checkUserRule(AbstractOrder orderInfo,JSONObject result){
        Map<String, Object> companyRulesRefParam = new HashMap<String, Object>();
        companyRulesRefParam.put("leasescompanyid", orderInfo.getCompanyid());
        companyRulesRefParam.put("organid", ((OrgOrder) orderInfo).getOrganid());
        List<Map<String, Object>> companyRulesRefList = dao.getLeCompanyRulesRefState(companyRulesRefParam);
        if(null == companyRulesRefList || companyRulesRefList.isEmpty()) {
        	result.clear();
            result.put("status", Retcode.NOSERVICESINCITY.code);
            result.put("message", "当前城市没有可用服务车型");
            return false;
        } else {
            boolean flag = true;
            for (Map<String, Object> companyRulesRef : companyRulesRefList) {
                String rulestate = companyRulesRef.get("rulestate").toString();
                if(rulestate.equals("1")) { //如果存在启用的规则，则退出循环
                    flag = false;
                    break;
                }
            }
            if(flag) {
            	result.clear();
                result.put("status", Retcode.NOSERVICESINCITY.code);
                result.put("message", getOverservicesincityMsg(orderInfo.getOncity(), orderInfo.getOrdertype()));
                return false;
            }
        }
        return true;
	}
	
	/**
	 * 校验用户是否能够下单
	 * @param user
	 * @param pup
	 * @param result
	 * @return
	 */
	private boolean checkUserCanOrder(PeUser user,JSONObject result){
		PeUserParam pup = new PeUserParam();
		pup.setUserid(user.getId());
		if(user.isNotpay()){
			result.clear();
			result.put("status", Retcode.ORDERNOTPAY.code);
			result.put("message", Retcode.ORDERNOTPAY.msg);
			OpOrder order = dao.getNotPayOpOrder(pup);
			if(null != order) {
				result.put("orderno", order.getOrderno());
				result.put("usetype", order.getUsetype());
				result.put("ordertype", order.getOrdertype());
			}
			return false;
		}
		//未完成订单数不能超过5
		if(user.getNotdone() >=1){
			result.clear();
			result.put("status", Retcode.ORDEROUTOFLIMIT.code);
			result.put("message", Retcode.ORDEROUTOFLIMIT.msg);
			return false;
		}
		return true;
	}
	
	/**
	 * 校验用户是否能够下单
	 * @param user
	 * @param oup
	 * @param result
	 * @return
	 */
	private boolean checkUserCanOrder(OrgUser user,OrgOrder orderInfo,JSONObject result){
		OrgUserParam oup = new OrgUserParam();
		if(PayMethod.ORGAN.code.equals(orderInfo.getPaymethod())){  //机构支付,默认支付状态是未结算
			oup.setPaymentstatus(PayState.MENTED.state);
		}
		oup.setUserid(user.getId());
		if(user.isNotpay()){
			result.clear();
			result.put("status", Retcode.ORDERNOTPAY.code);
			result.put("message", Retcode.ORDERNOTPAY.msg);
			OrgOrder order = dao.getNotPayOrgOrder(oup);
			if(null != order) {
				result.put("orderno", order.getOrderno());
				result.put("usetype", order.getUsetype());
				result.put("ordertype", order.getOrdertype());
			}
			return false;
		}
		//下单来源为机构端的订单,未完成订单数限制为5
		if(OrderEnum.ORDERSOURCE_ORGAN.code.equals(orderInfo.getOrdersource()) && user.getNotdone() >= 5){
			result.clear();
			result.put("status", Retcode.ORDEROUTOFLIMIT.code);
			result.put("message", Retcode.ORDEROUTOFLIMIT.msg);
			return false;
		//下单来源不为机构端的订单,未完成订单数限制为1
		}else if(!OrderEnum.ORDERSOURCE_ORGAN.code.equals(orderInfo.getOrdersource()) && user.getNotdone() >= 1){
			result.clear();
			result.put("status", Retcode.ORDEROUTOFLIMIT.code);
			result.put("message", Retcode.ORDEROUTOFLIMIT.msg);
			return false;
		}
		return true;
	}
	
	/**
	 * 获取派单规则
	 * @param gsp
	 * @return
	 */
	public JSONObject getSendRule(GetSendInfoParam gsp){
		JSONObject result = new JSONObject();
		result.put("orderprop", gsp.getOrderprop());
		if(gsp.getOrderprop() == 0 || gsp.getOrderprop() == 1){
			PubSendrules sendRule = new PubSendrules();
	 		sendRule.setLeasescompanyid(gsp.getCompanyid());
	 		sendRule.setVehicletype(Integer.valueOf(OrderEnum.ORDERSTYPE_CAR.code));
	 		sendRule.setUsetype(gsp.getUsetype());
			sendRule.setCity(gsp.getCity());
	        if(gsp.getOrderprop() == 0) {
	        	sendRule.setPlatformtype(PlatformTypeByDb.LEASE.code);
	        } else {
	        	sendRule.setPlatformtype(PlatformTypeByDb.OPERATING.code);
	        }
			sendRule = dao.getSendRule(sendRule);
			result.put("sendrule", sendRule);
		} else {
			OpTaxisendrules taxisendrules = dao.getOpTaxiSendRule(gsp);
			result.put("sendrule", taxisendrules);
		}
		return result;
	}
	
	/**
	 * 获取派单时限
	 * @param param
	 * @return
	 */
	public JSONObject getSendTime(OrderApiParam param){
		JSONObject result = new JSONObject();
		PubSendrules sendRule = null;
		AbstractOrder orderInfo;
		int sendInterval = 0;   //系统派单结束时间
		int labourTime = 0;     //进入人工派单时间点
		boolean specialUser = false;
		if(param.getOrderprop() == 0){
			orderInfo = dao.getOrgOrder(param.getOrderno());
			//获取派单规则
			sendRule = getSendRules(orderInfo,result);
			if(sendRule == null) return result;
			OrgUserParam oup = new OrgUserParam();
			oup.setUserid(orderInfo.getUserid());
			OrgUser user = dao.getOrgUserById(oup);
			specialUser = "1".equals(user.getSpecialState());
		}else{
			orderInfo = dao.getOpOrder(param.getOrderno());
		}
		if(orderInfo == null) return null;
		int[] reInt = getSendTime(sendRule,specialUser);
		sendInterval = reInt[1];
		labourTime = reInt[0];
		result.put("labourTime", labourTime);
		result.put("sendinterval", sendInterval);
		return result;
	}
	
	/**
	 * 获取派单时限
	 * @param sendRule       派单规则
	 * @param specialUser    是否是特殊用户
	 * @param labourTime   进入人工派单时间点
	 * @param sendinterval  系统派单结束时间
	 * @return
	 */
	public int[] getSendTime(OpTaxisendrules sendRule){
//		int tempTime = (int)(System.currentTimeMillis() - oid.getUndertime().getTime()/1000);
		int labourTime, sendinterval = 0;
		//所有派单模式的超时时限都是系统派单时限+人工派单时限+司机抢单时限
		//不存在值的模式,默认是0
		sendinterval = (parseInt(sendRule.getSystemsendinterval())) * 60;
        if(!SendRulesEnum.SENDTYPE_FORCE.code.equals(sendRule.getSendtype())) { //不为强派
            sendinterval += parseInt(sendRule.getDriversendinterval());
        }
        labourTime = sendinterval;
        if(SendRulesEnum.SENDMODEL_SYSTEM_LABOUR.code.equals(sendRule.getSendmodel() + "")) { //系统+人工
            sendinterval += parseInt(sendRule.getPersonsendinterval());
        }

//		if(SendRulesEnum.SENDTYPE_SYSTEM_ONLY.code.equals(sendRule.getSendtype() + "")){
//			//如果是纯人工模式,只需要人工派单时限
//			sendinterval = sendRule.getPersonsendinterval() * 60;
//		}else if(SendRulesEnum.SENDMODEL_SYSTEM.code.equals(sendRule.getSendmodel() + "")){
//			//如果是系统模式,只需要系统派单时限
//			sendinterval = sendRule.getSystemsendinterval() * 60;
//		}else if (SendRulesEnum.SENDMODEL_SYSTEM_LABOUR.code.equals(sendRule.getSendmodel() + "")) {
//			//如果是系统加人工模式,需要加上人工派单时限
//			sendinterval = (sendRule.getSystemsendinterval() + sendRule.getPersonsendinterval()) * 60;
//		}
//		labourTime = sendinterval - parseInt(sendRule.getPersonsendinterval()) * 60;
		int[] reInt = new int[]{labourTime,sendinterval};
		return reInt;
		//总派单时限 - 已经耗费的时间 = 剩余派单时限
		//APP端显示正计时,不需要计算剩余,只用返回总时限
//		sendInterval = sendInterval - tempTime;
	}
	
	private int parseInt(Object value){
		if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
			return 0;
		}
		return Integer.parseInt(String.valueOf(value));
	}
	
	/**
	 * 获取派单时限
	 * @param sendRule       派单规则
	 * @param specialUser    是否是特殊用户
	 * @param labourTime   进入人工派单时间点
	 * @param sendinterval  系统派单结束时间
	 * @return
	 */
	public int[] getSendTime(PubSendrules sendRule,boolean specialUser){
//		int tempTime = (int)(System.currentTimeMillis() - oid.getUndertime().getTime()/1000);
		int labourTime, sendinterval = 0;
		//所有派单模式的超时时限都是系统派单时限+人工派单时限+司机抢单时限
		//不存在值的模式,默认是0
		sendinterval = (sendRule.getPersonsendinterval() + sendRule.getSystemsendinterval()) * 60 + sendRule.getDriversendinterval();
//		if(SendRulesEnum.SENDTYPE_SYSTEM_ONLY.code.equals(sendRule.getSendtype() + "")){
//			//如果是纯人工模式,只需要人工派单时限
//			sendinterval = sendRule.getPersonsendinterval() * 60;
//		}else if(SendRulesEnum.SENDMODEL_SYSTEM.code.equals(sendRule.getSendmodel() + "")){
//			//如果是系统模式,只需要系统派单时限
//			sendinterval = sendRule.getSystemsendinterval() * 60;
//		}else if (SendRulesEnum.SENDMODEL_SYSTEM_LABOUR.code.equals(sendRule.getSendmodel() + "")) {
//			//如果是系统加人工模式,需要加上人工派单时限
//			sendinterval = (sendRule.getSystemsendinterval() + sendRule.getPersonsendinterval()) * 60;
//		}
		if(specialUser) sendinterval += sendRule.getSpecialinterval();
		labourTime = sendinterval - sendRule.getPersonsendinterval() * 60;
		int[] reInt = new int[]{labourTime,sendinterval};
		return reInt;
		//总派单时限 - 已经耗费的时间 = 剩余派单时限
		//APP端显示正计时,不需要计算剩余,只用返回总时限
//		sendInterval = sendInterval - tempTime;
	}
	
	public Map<String, Object> checkOpTaxiAccountRules(OrderCostParam param) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		TaxiOrderCost orderCost = dao.getOpTaxiAccountrulesByCity(param);
		if(null != orderCost) {
			ret.put("status", Retcode.OK.code);
		} else {
			ret.put("status", Retcode.FAILED.code);
		}
		return ret;
	}
	
	/**
	 * 获取实时|总费用
	 * @param oid
	 * @param oap
	 * @return
	 */
	private OrderCost getOrderCostInRealTime(OrderInfoDetail oid,OrderApiParam oap){
		JSONObject price = JSONObject.fromObject(oid.getPricecopy());
		OrderCost oc = StringUtil.parseJSONToBean(price.toString(), OrderCost.class);
		oc.setCost(0.0);
		oc.setMileage(0.0);
		oc.setTimes(0);
		oc.setRangecost(0.0);
		oc.setTimecost(0.0);
		if(oid.getStarttime() != null){
			//已完成则取数据库值(不能直接用订单状态判断是否完成)
			if(oid.getEndtime() != null){
				oc.setMileage(oid.getMileage());
				if(oc.getTimetype() == 1){
					oc.setTimes(oc.getSlowtimes()*60);
				}else{
					oc.setTimes((int)(oid.getEndtime().getTime() - oid.getStarttime().getTime())/1000);
				}
				oid.setTimes(oc.getTimes());
			}else if(OrderState.CANCEL.state.equals(oid.getStatus())){
				return oc;
			}else{
				int duration = 0;
				JSONObject direc = null;
				duration = (int)(System.currentTimeMillis() - oid.getStarttime().getTime())/1000;
				direc = mileageApiService.getOrderMileage(oc.getOrderno(), oid.getType(), oid.getUsetype(), oap.getLog());
				direc.put("lowspeedcost", 0);
				if(!direc.has("distance")){
					oc.setMileage(0.0);
				}else{
					oc.setMileage(direc.getDouble("distance"));
				}
				int slowtimes = 0;
				if(direc.getInt("lowspeedcost") == 0){
					slowtimes = (int)(System.currentTimeMillis() - oid.getStarttime().getTime())/1000;
				}else {
					slowtimes = direc.getInt("lowspeedcost");
				}
				//不足1分钟按1分钟算
				slowtimes = slowtimes%60>0?slowtimes/60+1:slowtimes/60;
				oc.setCosttype(direc.getInt("costtype"));
				oc.setSlowtimes(slowtimes);
				oc.setTimes(duration);
			}
		}
		return oc;
	}
	
	/**
	 * 获取预估费用
	 * @param param
	 * @return
	 */
	private OrderCost getOrderCostInEstimated(OrderCostParam param){
		OrderCost oc = null;
		if(param.getOrderprop() == 0){
			oc = dao.getOrgOrderCost(param);
		}else{
			oc = dao.getOpOrderCost(param);
		}
		if(oc == null) return null;
		if(param.getDistance() != 0 && param.getDuration() != 0){
			oc.setMileage(param.getDistance());
			oc.setTimes(param.getDuration());
		}else{
			BaiduApiQueryParam baqp = new BaiduApiQueryParam();
			baqp.setOrderStartLng(param.getOnaddrlng());
			baqp.setOrderStartLat(param.getOnaddrlat());
			baqp.setOrderEndLng(param.getOffaddrlng());
			baqp.setOrderEndLat(param.getOffaddrlat());
//			baqp.setOrderStartCityName(param.getCity());
//			baqp.setOrderEndCityName(param.getCity());
			Map<String, Object> direc = baiduApi.getMileageInfo(baqp);
			if(direc != null && ((int)direc.get("status")) == Retcode.OK.code){
				//设置时长&里程
				oc.setMileage((int)direc.get("distance"));
				oc.setTimes((int) direc.get("duration"));
			}
		}
		return oc;
	}
	
	/**
	 * 获取出租车订单预估费用
	 * @param param
	 * @return
	 */
	private TaxiOrderCost getOpTaxiOrderCostInEstimated(OrderCostParam param) {
		TaxiOrderCost orderCost = dao.getOpTaxiAccountrulesByCity(param);
		if(null != orderCost) {
			BaiduApiQueryParam baqp = new BaiduApiQueryParam();
			baqp.setOrderStartLng(param.getOnaddrlng());
			baqp.setOrderStartLat(param.getOnaddrlat());
			baqp.setOrderEndLng(param.getOffaddrlng());
			baqp.setOrderEndLat(param.getOffaddrlat());
			Map<String, Object> direc = baiduApi.getMileageInfo(baqp);
			if(direc != null && ((int)direc.get("status")) == Retcode.OK.code){
				orderCost.setMileage((int)direc.get("distance") + param.getMeterrange() * 1000);
				orderCost.setTimes((int) direc.get("duration"));
			}
		} else {
			return null;
		}
		return orderCost;
	}
	
	/**
	 * 更新redis订单号
	 * @return
	 */
	public JSONObject updateNewestOrderNO(){
		OrderSource4OrderNO[] oss = OrderSource4OrderNO.values();
		OrderVarietyEnum[] orderVarietys = OrderVarietyEnum.values();
		for(OrderVarietyEnum orderVariety : orderVarietys) {
			for(OrderSource4OrderNO os : oss){
				JedisUtil.expire(os.code + orderVariety.variety + StringUtil.formatDate(new Date(), "yyMMddHH"), 3600);
			}
		}
		JSONObject result = new JSONObject();
		String orderno = new OrderNO().getOrderNO(oss[(int)(Math.random()*oss.length)],orderVarietys[(int)(Math.random()*orderVarietys.length)]);
		result.put("orderno", orderno);
		return result;
	}
	
	/**
	 * 获取订单号
	 * @param usetype
	 * @param ordersource
	 * @param orderVariety
	 * @return
	 */
	public String getOrderNO(String usetype, String ordersource, OrderVarietyEnum orderVariety) {
		OrderSource4OrderNO os = OrderSource4OrderNO.getOrderSource4OrderNO(usetype, ordersource);
		String orderno = new OrderNO().getOrderNO(os, orderVariety);
		return orderno;
	}
	
	/**
	 * 扣除个人账户余额
	 * @param orderInfo
	 * @param result
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean  takeOffBalance(PeUser user,double cost,JSONObject result){
		PeUseraccount useraccount = new PeUseraccount();
		useraccount.setUserid(user.getId());
		useraccount = dao.getPeUseraccount(useraccount);
		if(null == useraccount || cost > useraccount.getBalance()) {
			result.clear();
			result.put("status", Retcode.NOTENOUGHBALANCE.code);
			result.put("message", Retcode.NOTENOUGHBALANCE.msg);
			return false;
		}else{
//			useraccount.setBalance(useraccount.getBalance() - cost);
//			dao.updatePeBalance(useraccount);
		}
		return true;
	}
	
	/**
	 * 扣除机构账户余额
	 * @param orderInfo
	 * @param result
	 * @return
	 */
	private boolean  takeOffBalance(OrgOrder orderInfo,double cost,JSONObject result){
		//机构支付才扣余额
		if(!orderInfo.getPaymethod().equals(PayMethod.ORGAN.code)) return true;
		OrderCostParam ocp = new OrderCostParam();
		ocp.setUserid(orderInfo.getUserid());
		ocp.setCompanyid(orderInfo.getCompanyid());
		OrgOrganCompanyRef oocr = dao.getOrgBalance(ocp);
		if(cost > oocr.getBalance()){  //判断余额是否足够
			result.clear();
			result.put("status", Retcode.NOTENOUGHBALANCE.code);
			result.put("message", "机构可用额度不足，不可继续下单");
			return false;
		}else{
			oocr.setBalance(oocr.getBalance() - cost);
			dao.updateOrgBalance(oocr);
		}
		return true;
	}
	
	/**
	 * 执行创建订单逻辑
	 * @param orderInfo
	 * @param oc
	 * @return
	 */
	private boolean doCreateOrgOrder(OrgOrder orderInfo,OrderCost oc){
		String orderno = getOrderNO(orderInfo.getUsetype(), orderInfo.getOrdersource(), OrderVarietyEnum.LEASE_NET);
		//新增加实际乘坐车型,计费车型字段
		oc.setOrderno(orderno);//保存orderno
		orderInfo.setOrderno(orderno);
		orderInfo.setFactmodel(orderInfo.getSelectedmodel());
		orderInfo.setPricemodel(orderInfo.getSelectedmodel());
		LeVehiclemodels vehiclemodels = dao.getLeVehiclemodelsById(orderInfo.getSelectedmodel());
		if(null != vehiclemodels) {
			orderInfo.setSelectedmodelname(vehiclemodels.getName());
			orderInfo.setFactmodelname(vehiclemodels.getName());
			orderInfo.setPricemodelname(vehiclemodels.getName());
		}
		int minute = oc.getTimes() % 60 > 0 ? oc.getTimes() / 60 + 1 : oc.getTimes() / 60;
		int expensetype = parseInt(OrderEnum.EXPENSETYPE_SERVICE_DONE.code);
		orderInfo.setEstimatedmileage(StringUtil.formatNum(oc.getMileage()/1000, 1));
		orderInfo.setEstimatedtime((int)StringUtil.formatNum(minute,1));
		orderInfo.setEstimatedcost(oc.getCost());
		orderInfo.setPricecopy(JSONObject.fromObject(oc).toString());
		orderInfo.setPaymentstatus(PayState.MENTED.state);
		orderInfo.setReviewstatus(ReviewState.NOTREVIEW.state);
		orderInfo.setStatus(1);               //数据生效
		orderInfo.setUserhidden("1");   //消息未读
		orderInfo.setExpensetype(expensetype);   //创建订单时默认为1-行程服务
        if("1".equals(orderInfo.getManualDriver())){
            orderInfo.setOrdertime(new Date());
            orderInfo.setOrderstatus(OrderState.WAITSTART.state);
        }else{
        	orderInfo.setDriverid("");
            orderInfo.setOrderstatus(OrderState.WAITTAKE.state);
        }
		dao.createOrgOrder(orderInfo);
		logger.info("==================================================");
		logger.info("=                     下单成功.订单号:" + orderInfo.getOrderno() + "                      =");
		logger.info("==================================================");
		return true;
	}
	
	/**
	 * 执行创建订单逻辑
	 * @param orderInfo
	 * @param oc
	 * @return
	 */
	private boolean doCreateOpOrder(OpOrder orderInfo,OrderCost oc){
		String orderno = getOrderNO(orderInfo.getUsetype(), orderInfo.getOrdersource(), OrderVarietyEnum.OPERATING_NET);
		oc.setOrderno(orderno);//保存orderno
		//新增加实际乘坐车型,计费车型字段
		orderInfo.setOrderno(orderno);
		orderInfo.setFactmodel(orderInfo.getSelectedmodel());
		orderInfo.setPricemodel(orderInfo.getSelectedmodel());
		OpVehiclemodels vehiclemodels = dao.getOpVehiclemodelsById(orderInfo.getSelectedmodel());
		if(null != vehiclemodels) {
			orderInfo.setSelectedmodelname(vehiclemodels.getName());
			orderInfo.setFactmodelname(vehiclemodels.getName());
			orderInfo.setPricemodelname(vehiclemodels.getName());
		}
		int minute = oc.getTimes() % 60 > 0 ? oc.getTimes() / 60 + 1 : oc.getTimes() / 60;
		int expensetype = parseInt(OrderEnum.EXPENSETYPE_SERVICE_DONE.code);
		orderInfo.setEstimatedmileage(StringUtil.formatNum(oc.getMileage()/1000, 1));
		orderInfo.setEstimatedtime((int)StringUtil.formatNum(minute,1));
		orderInfo.setEstimatedcost(oc.getCost());
		orderInfo.setPricecopy(JSONObject.fromObject(oc).toString());
		orderInfo.setPaymentstatus(PayState.NOTPAY.state);
		orderInfo.setReviewstatus(ReviewState.NOTREVIEW.state);
		orderInfo.setStatus(1);               //数据生效
		orderInfo.setUserhidden("1");   //消息未读
		orderInfo.setExpensetype(expensetype);   //创建订单时默认为1-行程服务
		OrderApiParam oap = new OrderApiParam();
		oap.setOrderid(orderInfo.getOrderno());
		oap.setOrderprop(OrderVarietyEnum.OPERATING_NET.icode);

        if("1".equals(orderInfo.getManualDriver())){
            orderInfo.setOrderstatus(OrderState.WAITSTART.state);
            orderInfo.setOrdertime(new Date());
            com.szyciov.op.entity.PubDriver driver = this.opOrderApiService.getOpPubDriver(orderInfo.getDriverid());
            if("1".equals(driver.getPlatformType())){
                orderInfo.setCompanyid(driver.getLeasesCompanyId());
            }
        }else{
        	orderInfo.setDriverid("");
            orderInfo.setOrderstatus(OrderState.WAITTAKE.state);
        }

		dao.createOpOrder(orderInfo);
		logger.info("==================================================");
		logger.info("=                     下单成功.订单号:" + orderInfo.getOrderno() + "                      =");
		logger.info("==================================================");
		return true;
	}
	
	/**
	 * 执行创建订单逻辑
	 * @param orderInfo
	 * @param oc
	 * @return
	 */
	private boolean doCreateOpTaxiOrder(OpTaxiOrder orderInfo,TaxiOrderCost oc){
		String orderno = getOrderNO(orderInfo.getUsetype(), orderInfo.getOrdersource(), OrderVarietyEnum.OPERATING_TAXI);
		oc.setOrderno(orderno);//保存orderno
		//新增加实际乘坐车型,计费车型字段
		orderInfo.setOrderno(orderno);
		OpVehiclemodels vehiclemodels = dao.getOpVehiclemodelsById(orderInfo.getSelectedmodel());
		if(null != vehiclemodels) {
			orderInfo.setSelectedmodelname(vehiclemodels.getName());
			orderInfo.setFactmodelname(vehiclemodels.getName());
			orderInfo.setPricemodelname(vehiclemodels.getName());
		}
		//预估时间向上取整
		int minute = oc.getTimes() % 60 > 0 ? oc.getTimes() / 60 + 1 : oc.getTimes() / 60;
		int expensetype = parseInt(OrderEnum.EXPENSETYPE_SERVICE_DONE.code);
		orderInfo.setEstimatedmileage(StringUtil.formatNum(oc.getMileage()/1000, 1));
		orderInfo.setEstimatedtime((int)StringUtil.formatNum(minute,1));
		orderInfo.setEstimatedcost(oc.getCost());
		orderInfo.setPricecopy(JSONObject.fromObject(oc).toString());
		orderInfo.setPaymentstatus(PayState.PASSENGERNOPAY.state);
		orderInfo.setReviewstatus(ReviewState.NOTREVIEW.state);
		orderInfo.setStatus(1);               //数据生效
		orderInfo.setUserhidden("1");   //消息未读
		orderInfo.setExpensetype(expensetype);   //创建订单时默认为1-行程服务
		OrderApiParam oap = new OrderApiParam();
		oap.setOrderid(orderInfo.getOrderno());
		oap.setOrderprop(OrderVarietyEnum.OPERATING_NET.icode);
		
		if("1".equals(orderInfo.getManualDriver())){
			orderInfo.setOrderstatus(OrderState.WAITSTART.state);
			orderInfo.setOrdertime(new Date());
			com.szyciov.op.entity.PubDriver driver = this.opOrderApiService.getOpPubDriver(orderInfo.getDriverid());
            orderInfo.setBelongleasecompany(driver.getBelongleasecompany());
			if("1".equals(driver.getPlatformType())){
				orderInfo.setCompanyid(driver.getLeasesCompanyId());
			}
		}else{
			orderInfo.setDriverid("");
			orderInfo.setOrderstatus(OrderState.WAITTAKE.state);
		}
		dao.insertOpTaxiOrder(orderInfo);
		logger.info("==================================================");
		logger.info("=                     下单成功.订单号:" + orderInfo.getOrderno() + "                      =");
		logger.info("==================================================");
		return true;
	}
	
	/**
	 * 保存订单派单结束时间节点
	 * @param orderInfo
	 * @param labourTime
	 * @param sendinterval
	 * @return
	 */
	private int[] updateSendTime(OpTaxiOrder orderInfo,OpTaxisendrules sendRule){
		//获取人工派单时间点和订单取消时间点
		int labourTime, sendinterval;
		OrderApiParam oap = new OrderApiParam();
		oap.setOrderid(orderInfo.getOrderno());
		oap.setOrderprop(OrderVarietyEnum.LEASE_NET.icode);
		int[] reInt = getSendTime(sendRule);
		labourTime = reInt[0];
		sendinterval = reInt[1];
		orderInfo.setSystemsendovertime(StringUtil.addDate(orderInfo.getCreatetime(), labourTime));
		orderInfo.setAutocanceltime(StringUtil.addDate(orderInfo.getCreatetime(), sendinterval));
		return reInt;
	}
	
	/**
	 * 保存订单派单结束时间节点
	 * @param orderInfo
	 * @param labourTime
	 * @param sendinterval
	 * @return
	 */
	private int[] updateSendTime(AbstractOrder orderInfo,PubSendrules sendRule,boolean specialUser){
		//获取人工派单时间点和订单取消时间点
		int labourTime, sendinterval;
		OrderApiParam oap = new OrderApiParam();
		oap.setOrderid(orderInfo.getOrderno());
		oap.setOrderprop(OrderVarietyEnum.LEASE_NET.icode);
		int[] reInt = getSendTime(sendRule,specialUser);
		labourTime = reInt[0];
		sendinterval = reInt[1];
		orderInfo.setSystemsendovertime(StringUtil.addDate(orderInfo.getCreatetime(), labourTime));
		orderInfo.setAutocanceltime(StringUtil.addDate(orderInfo.getCreatetime(), sendinterval));
		return reInt;
	}
	
	/**
	 * 获取机构用户
	 * @param userId
	 * @param result
	 * @return
	 */
	private OrgUser getOrgUser(String userId,JSONObject result){
		OrgUser user = null;
		OrgUserParam oup = new OrgUserParam();
		oup.setUserid(userId);
		user = dao.getOrgUserById(oup);
		if(user == null){
			result.put("status", Retcode.FAILED.code);
			result.put("message", "未找到下单人");
		}
		return user;
	}
	
	/**
	 * 获取个人用户
	 * @param userId
	 * @param result
	 * @return
	 */
	private PeUser getPeUser(String userId,JSONObject result){
		PeUser user = null;
		PeUserParam pup = new PeUserParam();
		pup.setUserid(userId);
		user = dao.getPeUserById(pup);
		if(user == null){
			result.put("status", Retcode.FAILED.code);
			result.put("message", "未找到下单人");
		}
		return user;
	}
	
	/**
	 * 根据手机号查询个人用户
	 * @param userPhone
	 * @param result
	 * @return
	 */
	private PeUser getPeUser(String userPhone){
		PeUser user = new PeUser();
		user.setAccount(userPhone);
		user = dao.getPeUserByPhone(user);
//		if(user == null){
//			result.put("status", Retcode.FAILED.code);
//			result.put("message", "未找到下单人");
//		}
		return user;
	}

    /**
     * 验证机构用户是否存在用车规则
     * @param order
     * @param result
     * @return
     */
	private boolean checkOrgUsecarrules(OrgOrder order, JSONObject result) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userid", order.getUserid());
        param.put("leasescompanyid", order.getCompanyid());
        param.put("vehiclemodels", order.getSelectedmodel());
        param.put("usetype", order.getOrdertype());
        Map<String, Object> usecarrules = dao.getOrgUsecarrulesByUser(param);
        if(null == usecarrules || usecarrules.isEmpty()) {
            result.put("status", Retcode.FAILED.code);
            result.put("message", getNoOrgVehicleModelMsg(order.getSelectedmodel()));
            return false;
        }
        return true;
    }
	
	/**
	 * 创建运管端出租车订单
	 * @param orderInfo
	 * @return
	 */
	public JSONObject createOpTaxiOrder(OpTaxiOrder orderInfo) {
		JSONObject result = new JSONObject();
		String manualDriver = orderInfo.getManualDriver();

        if("1".equals(manualDriver) && isDriverOffline(orderInfo.getDriverid())){
            result.put("status", Retcode.MANUAL_DRIVER_OFFLINE.code);
            result.put("message", Retcode.MANUAL_DRIVER_OFFLINE.msg);
            return result;
        }

		PeUser user = null;
		OpTaxisendrules sendRule = null;
		TaxiOrderCost oc = null;
		int labourTime = 0;
		int sendinterval = 0;
		orderInfo.setUsetype(OrderEnum.USETYPE_PERSONAL.code);
		orderInfo.setOrdertype(OrderEnum.ORDERTYPE_TAXI.code);
		orderInfo.setPaymentmethod(PayMethod.OFFLINE.code);
        user = getPeUser(orderInfo.getUserid(),result);      //获取个人用户
        sendRule = getSendRules(orderInfo,result);          //获取派单规则(先获取派单规则,确定订单是否即刻)
		oc = countCost(orderInfo, result);                           //计算预估费用
		if(!checkPermission(orderInfo,user,sendRule,oc,result)) return result;
		if(null == orderInfo.getSchedulefee()) orderInfo.setSchedulefee(0);
		//保存人工派单时间点和订单取消时间点
		int[] reInt = updateSendTime(orderInfo,sendRule);
		labourTime = reInt[0];
		sendinterval = reInt[1];
		
		orderInfo.setCouponprice(oc.getCouponprice());
		orderInfo.setCouponid(oc.getCouponid());
		
		doCreateOpTaxiOrder(orderInfo,oc);
		doLockCoupon(orderInfo);
		checkUserRegisterCity(orderInfo, user.getAccount());  //检查用户最近5单是否同一城市
		
        //推送消息
		if("1".equals(manualDriver)){
            PubVehicle pubVehicle = opTaxiOrderManageService.getPubVehicle(orderInfo.getVehicleid());
            opTaxiOrderManageService.updateOrder(orderInfo,pubVehicle);
            opTaxiOrderManageService.createDriverNews(orderInfo, 0, OrderMessageFactory.OrderMessageType.MANTICORDER);
            TaxiOrderMessage message = new TaxiOrderMessage(orderInfo, TaxiOrderMessage.TAXI_MANTICORDER);
            MessageUtil.sendMessage(message);
            logger.info("订单号：" + orderInfo.getOrderno() + "，人工指派给" + orderInfo.getDriverid() + "司机");
        }else{
        	sendOrder(orderInfo,FactoryProducer.TYPE_TAXI_OP);
        }
		
		result.clear();
        result.put("orderno", orderInfo.getOrderno());
        result.put("usetype", orderInfo.getUsetype());
        result.put("ordertype", orderInfo.getOrdertype());
        result.put("labourTime", labourTime);
        result.put("sendinterval", sendinterval);
        result.put("sendtype", sendRule.getSendtype());
        result.put("sendmodel", sendRule.getSendmodel());
        result.put("isusenow",orderInfo.isIsusenow());
		return result;
	}
	
	/**
	 * 创建个人订单
	 * @param orderInfo
	 * @return
	 */
	public JSONObject createOpOrder(OpOrder orderInfo){
		JSONObject result = new JSONObject();
        String manualDriver = orderInfo.getManualDriver();

        if("1".equals(manualDriver) && isDriverOffline(orderInfo.getDriverid())){
            result.put("status", Retcode.MANUAL_DRIVER_OFFLINE.code);
            result.put("message", Retcode.MANUAL_DRIVER_OFFLINE.msg);
            return result;
        }

		PeUser user = null;
		PubSendrules sendRule = null;
		OrderCost oc = null;
		int labourTime = 0;
		int sendinterval = 0;
		orderInfo.setUsetype(OrderEnum.USETYPE_PERSONAL.code);
        user = getPeUser(orderInfo.getUserid(),result); //获取个人用户
        sendRule = getSendRules(orderInfo,result); //获取派单规则(先获取派单规则,确定订单是否即刻)
        oc = countCost(orderInfo, result); //计算预估费用
		if(!checkPermission(orderInfo, user, sendRule, oc, result)) return result;
        //保存人工派单时间点和订单取消时间点
        int[] reInt = updateSendTime(orderInfo,sendRule,false);
        labourTime = reInt[0];
        sendinterval = reInt[1];
        
		orderInfo.setCouponprice(oc.getCouponprice());
		orderInfo.setCouponid(oc.getCouponid());
		
        
        doCreateOpOrder(orderInfo,oc);   //创建订单
		doLockCoupon(orderInfo);            //锁定优惠券
		checkUserRegisterCity(orderInfo, user.getAccount());  //检查用户最近5单是否同一城市
		
		 //推送消息
		if("1".equals(manualDriver)){
            com.szyciov.op.entity.PubDriver driver = this.opOrderApiService.getOpPubDriver(orderInfo.getDriverid());
            this.opOrderApiService.updateOpOrderVehicleInfo(orderInfo.getOrderno(),driver.getBelongleasecompany());
            this.opOrderApiService.createDriverNews(orderInfo, 0, OrderMessageFactory.OrderMessageType.MANTICORDER);
            OrderMessage message = new OrderMessage(orderInfo, OrderMessage.MANTICORDER);
            MessageUtil.sendMessage(message);
            logger.info("订单号：" + orderInfo.getOrderno() + "，人工指派给" + driver.getId() + "司机");
        }else{
        	sendOrder(orderInfo,FactoryProducer.TYPE_CAR_OP);
        }

		result.clear();
        result.put("orderno", orderInfo.getOrderno());
        result.put("usetype", orderInfo.getUsetype());
        result.put("ordertype", orderInfo.getOrdertype());
        result.put("labourTime", labourTime);
        result.put("sendinterval", sendinterval);
        result.put("sendtype", sendRule.getSendtype());
        result.put("sendmodel", sendRule.getSendmodel());
        result.put("isusenow", orderInfo.isIsusenow());
		return result;
	}

	/**
	 * 创建机构订单
	 * @param orderInfo
	 * @return
	 */
	public JSONObject createOrgOrder(OrgOrder orderInfo){
		JSONObject result = new JSONObject();
        String manualDriver = orderInfo.getManualDriver();

        if("1".equals(manualDriver) && isDriverOffline(orderInfo.getDriverid())){
            result.put("status", Retcode.MANUAL_DRIVER_OFFLINE.code);
            result.put("message", Retcode.MANUAL_DRIVER_OFFLINE.msg);
            return result;
        }

		OrgUser user = null;
		PubSendrules sendRule = null;
		OrderCost oc = null;
		int labourTime = 0, sendinterval = 0;
        user = getOrgUser(orderInfo.getUserid(),result); //获取下单人
        if(orderInfo.getOrganid() == null){   //如果没有organid,就重新获取
            orderInfo.setOrganid(user.getOrganId());
        }
        sendRule = getSendRules(orderInfo, result); //获取派单规则(先获取派单规则,确定订单是否即刻)
        oc = countCost(orderInfo, result); //计算预估费用
		if(!checkPermission(orderInfo,user,sendRule,oc,result)) return result;

		//保存人工派单时间点和订单取消时间点
		boolean specialUser = "1".equals(user.getSpecialState());
		int[] reInt = updateSendTime(orderInfo,sendRule,specialUser);
		labourTime = reInt[0];
		sendinterval = reInt[1];
		
		orderInfo.setCouponprice(oc.getCouponprice());
		orderInfo.setCouponid(oc.getCouponid());
		
		doCreateOrgOrder(orderInfo,oc);  //创建订单
		doLockCoupon(orderInfo);            //锁定优惠券
		checkUserRegisterCity(orderInfo, user.getAccount());  //检查用户最近5单是否同一城市
		
        //推送消息
		if("1".equals(manualDriver)){
            com.szyciov.lease.entity.PubDriver driver = this.dao.getPubDriver(orderInfo.getDriverid());
            //更新车辆基本信息
            updateOrgOrderVehicleInfo(orderInfo.getOrderno(),driver.getBelongleasecompany());
            createDriverNews(orderInfo, 0.0D, OrderMessageFactory.OrderMessageType.MANTICORDER);
            OrderMessage message = new OrderMessage(orderInfo, "人工指派订单");
            MessageUtil.sendMessage(message);
            logger.info("订单号：" + orderInfo.getOrderno() + "，人工指派给" + driver.getId() + "司机");
        }else{
        	sendOrder(orderInfo,FactoryProducer.TYPE_CAR_LE);
        }

		result.clear();
        result.put("orderno", orderInfo.getOrderno());
        result.put("usetype", orderInfo.getUsetype());
        result.put("ordertype", orderInfo.getOrdertype());
        result.put("labourTime", labourTime);
        result.put("sendinterval", sendinterval);
        result.put("sendtype", sendRule.getSendtype());
        result.put("sendmodel", sendRule.getSendmodel());
        result.put("isusenow", orderInfo.isIsusenow());
		return result;
	}

	/**
	 * 检查用户注册城市
	 * 1.如果用户没有注册城市,更新为当前订单的上车城市
	 * 2.如果连续5单为同一城市,更新用户的注册城市为该城市
	 * @param orderInfo
	 * @param userPhone
	 */
	private void checkUserRegisterCity(AbstractOrder orderInfo,String userPhone){
		try{
			PeUser user = getPeUser(userPhone);
			if(null == user.getRegistercity()){
				user.setRegistercity(orderInfo.getOncity());
			}else if(checkUserLastOrders(userPhone)){
				user.setRegistercity(orderInfo.getOncity());
			}
			dao.updatePeUserCity(user);
		}catch(Exception e){
			logger.error("检查用户注册城市出错",e);
		}
	}
		
	/**
	 * 检查用户最近完成的5单是否是同一城市
	 * @param orderInfo
	 * @return
	 */
	private boolean checkUserLastOrders(String userPhone){
		List<OrderInfoDetail> list = dao.getLastOrders(userPhone);
		if(list != null && list.size() > 1) return false;
		return true;
	}
	
    /**
     * 判断司机是否下线
     * @param driverId
     * @return
     */
	private boolean isDriverOffline(String driverId){
        com.szyciov.lease.entity.PubDriver driver = this.dao.getPubDriver(driverId);
        if(DriverState.OFFLINE.code.equals(driver.getWorkStatus())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取订单信息
     * @param oap
     * @return
     */
	public JSONObject getOrderInfo(OrderApiParam oap){
		JSONObject json = new JSONObject();
		checkParam(oap, json);
		return json;
	}
	
	/**
	 * 获取订单费用(预估|实时)
	 * @param param
	 * @return
	 */
	public JSONObject getOrderCost(OrderCostParam param){
		OrderCost oc = null;
		JSONObject result = new JSONObject();
		OrderInfoDetail oid = null;
		//用车时间不为null表示是页面获取预估
		if(param.getUsetime() != null){
			PubSendrules sendRule = new PubSendrules();
	 		sendRule.setLeasescompanyid(param.getCompanyid());
	 		sendRule.setVehicletype(Integer.valueOf(OrderEnum.ORDERSTYPE_CAR.code));
	 		sendRule.setUsetype(SendRulesEnum.USETYPE_APPOINTMENT.code);
			sendRule.setCity(param.getCity());
	        if(param.getOrderprop() == 0) {
	        	sendRule.setPlatformtype(PlatformTypeByDb.LEASE.code);
	        } else {
	        	sendRule.setPlatformtype(PlatformTypeByDb.OPERATING.code);
	        }
			sendRule = dao.getSendRule(sendRule);
            if(null == sendRule) {
                param.setIsusenow(true);
            } else {
                //计算公式:用车时间-当前时间<=约车时限(数据库单位:分钟)?即刻:约车
                boolean usenow = true;
                if(null != param.getUsetime()) {
                    usenow = (param.getUsetime().getTime()-System.currentTimeMillis())/1000<=60*sendRule.getCarsinterval();
                }
                param.setIsusenow(usenow);
            }
		}
		//非预估
		if(param.getOrderno() != null){
			OrderApiParam oap = new OrderApiParam();
			oap.setToken(param.getToken());
			oap.setOrderno(param.getOrderno());
			oap.setUsetype(param.getUsetype());
			oap.setOrdertype(param.getOrdertype());
            oap.setLog(param.getLog());
            if((oid = checkParam(oap,result)) == null) return result;
            param.setIsusenow(oid.isIsusenow());
			oc = getOrderCostInRealTime(oid,oap);
		//预估
		}else{
			oc = getOrderCostInEstimated(param);
		}
		if(oc == null) {
			result.put("status", Retcode.NOSERVICESINCITY.code);
            if(OrderVarietyEnum.LEASE_NET.code.equals(param.getOrderprop())) {
                result.put("message", getOverservicesincityMsg(param.getCity(), param.getOrdertype()));
            } else {
                result.put("message", getNoservicesincityMsg(param.getCity(), param.getOrdertype()));
            }
			logger.info("==================================================");
			logger.info("=                                            获取计费规则失败.                                               =");
			logger.info("==================================================");
			return result;
		}
		//计算价格
		Date starttime = oid != null ? oid.getStarttime() : null;
		doPremium(param,oc,null);                      //获取溢价倍率
		doDiscount(oc,param.getOrderprop() == 0);  //打折
		doGetCanUseCounpon(param,oc,null);   //获取优惠券
		oc = StringUtil.countCost(oc, starttime, oid == null,param.isIsusenow());
		if(param.isHasunit()){
			result = convertOrderCost(oc);
		}else{
			result = JSONObject.fromObject(oc);
		}
		return result;
	}
	
	/**
	 * 计算溢价倍率后金额
	 * @param oid
	 * @param orderCost
	 */
	private void doPremium(OrderCostParam param,OrderCost oc,TaxiOrderCost toc){
		if(OrderEnum.USETYPE_PUBLIC.code.equals(param.getUsetype())){
			return;  //因公用车,无溢价
		}
		try {
			PremiumRuleParam prp = new PremiumRuleParam();
			PubPremiumRuleEnum platform = param.getOrderprop() == 0 ? PubPremiumRuleEnum.PLATFORMTYPE_LEASE
					: PubPremiumRuleEnum.PLATFORMTYPE_OPERATE;
			prp.setCartype(PubPremiumRuleEnum.CARTYPE_NET.code);
			prp.setCitycode(param.getCity());
			prp.setLeasescompanyid(param.getCompanyid());
			prp.setPlatformtype(platform.code);
			prp.setUsetime(param.getUsetime());
			Map<String,Object> result = premiumRuleService.getPremiumrate(prp);
			if((int)result.get("status") != Retcode.OK.code) return;
			if(oc != null){
				oc.setPremiumrate((double)result.get("premiumrate"));
			}else if (toc != null) {
				toc.setPremiumrate((double)result.get("premiumrate"));
			}
		} catch (Exception e) {
			logger.error("计算溢价倍率异常",e);
		}
	}
	
	/**
	 * 锁定优惠券
	 * @param oid
	 * @param oc
	 * @param couponId
	 */
	@SuppressWarnings("unchecked")
	private void doLockCoupon(AbstractOrder order){
		if(order instanceof OrgOrder){
			OrgOrder orgOrder = (OrgOrder)order;
			if(OrderEnum.USETYPE_PUBLIC.code.equals(orgOrder.getUsetype()) 
					&& PayMethod.ORGAN.code.equals(orgOrder.getPaymethod())){
				return;  //因公机构支付不能使用优惠券
			}
		}
		try {
			String url = SystemConfig.getSystemProperty("couponApi") + "/coupon/reserve";
			Map<String,Object> map = new HashMap<>();
			map.put("couponId", order.getCouponid());
			map.put("orderId", order.getOrderno());
			map.put("useType", "2");  //使用类型 1-账单 2-订单
			map.put("money", order.getCouponprice());
			map.put("userId", order.getUserid());
			map.put("city", order.getOncity());
			map.put("version", "V4.0.0");
			String jsonStr = templateHelper.dealRequestWithFullUrlToken(url, HttpMethod.POST, null,map, String.class);
			ResultData couponResult = GsonUtil.fromJson(jsonStr, ResultData.class);
			if(Retcode.OK.code != couponResult.getStatus()) return;
			if("single".equals(couponResult.getDataType())){
				Map<String, Object> coupon = GsonUtil.fromJson(couponResult.getData(), Map.class);
				boolean isReserve = (boolean) coupon.get("isReserve");
				if(!isReserve) {
					logger.info("锁定优惠券失败:" + coupon.get("Msg"));
				}else{
					logger.info("锁定优惠券成功,金额:" + order.getCouponprice() + " " + order.getCouponid());
				}
//			}else{
//				JSONArray dataList = json.getJSONArray("data");
			}
		} catch (Exception e) {
			logger.error("锁定优惠券异常",e);
		}
	}
	
	/**
	 * 获取可用优惠券
	 * @param oid
	 * @param oc
	 */
	private void doGetCanUseCounpon(OrderCostParam param,OrderCost oc,TaxiOrderCost toc){
		if(OrderEnum.USETYPE_PUBLIC.code.equals(param.getUsetype()) 
			&& PayMethod.ORGAN.code.equals(param.getPaymethod())){
			return;  //因公机构支付不能使用优惠券
		}else if (OrderEnum.USETYPE_PERSONAL.code.equals(param.getUsetype())
			&& PayMethod.OFFLINE.code.equals(param.getPaymethod())) {
			return;  //出租车线下支付不能使用优惠券
		}
		try {
			String url = SystemConfig.getSystemProperty("couponApi") + "/coupon/get/max";
			String usertype = param.getOrderprop() == 0 ? "2" : "3";
			String serviceType = param.getOrderprop() == 2 ? "1" : "2";
			Map<String,Object> map = new HashMap<>();
			map.put("serviceType", serviceType);   //使用业务 1-出租车 2-网约车
			map.put("usetId", param.getUserid());
			map.put("companyId", param.getCompanyid());
			map.put("cityCode", param.getCity());
			map.put("version", "V4.0.0");
			map.put("userType", usertype);
			String jsonStr = templateHelper.dealRequestWithFullUrlToken(url, HttpMethod.POST, null,map, String.class);
			ResultData couponResult = GsonUtil.fromJson(jsonStr, ResultData.class);
			if(Retcode.OK.code != couponResult.getStatus()) return;
			if("single".equals(couponResult.getDataType())){
				CouponInfoDTO coupon = GsonUtil.fromJson(couponResult.getData(), CouponInfoDTO.class);
				logger.info("获取到的优惠券信息:" + couponResult.getData());
				if(oc != null) {
					oc.setCouponid(coupon.getId());
					oc.setCouponprice(parseInt(coupon.getMoney().intValue()));
//					oc.setCost(oc.getCost() - oc.getCouponprice());   //所有计算放入StringUtil.countCost方法中
				}else if (toc != null) {
					toc.setCouponid(coupon.getId());
					toc.setCouponprice(parseInt(coupon.getMoney().intValue()));
				}
//			}else{
//				JSONArray dataList = json.getJSONArray("data");
			}
		} catch (Exception e) {
			logger.error("获取优惠券异常",e);
		}
	}
	
	/**
	 * 费用打折
	 * @param oc
	 * @param isorg
	 */
	private void doDiscount(OrderCost oc,boolean isorg){
		oc.setDiscount(1.0);  //取消打折
		return;
//		boolean takeOff = checkCanTakeOff();
//		if(takeOff){  //如果折扣开关开启,使用折扣价
//			double takeOffPoint = getTakeOffPoint(isorg);
//			double amount = Math.round(oc.getCost() * takeOffPoint);
//			oc.setCost(amount);  //所有计算放入StringUtil.countCost方法中
//		}
	}
	
	/**
	 * 格式化OrderCost(带单位)
	 * @param oc
	 * @return
	 */
	private JSONObject convertOrderCost(OrderCost oc){
		JSONObject result = new JSONObject();
		result.put("orderno", oc.getOrderno());
		result.put("cost", StringUtil.formatNum(oc.getCost(), 1)+"元");
		result.put("mileage", StringUtil.formatNum(oc.getMileage()/1000, 1)+"公里");
		result.put("startprice", oc.getStartprice()+"元");
		result.put("rangeprice", oc.getRangeprice()+"元/公里");
		result.put("timeprice", oc.getTimeprice()+"元/分钟");
		result.put("rangecost", oc.getRangecost()+"元");
		result.put("timecost", oc.getTimecost()+"元");
		result.put("nightcost", oc.getNightcost()+"元");
		result.put("deadheadcost", oc.getDeadheadcost()+"元");
		result.put("times", StringUtil.formatCostTimeInSecond(oc.getTimes()));
		result.put("perhour", oc.getPerhour()+"公里/小时");
		result.put("timetype", StringUtil.formatTimeType(oc));
		result.put("slowtimes", StringUtil.formatCostTime(oc.getSlowtimes()));
		result.put("costtype", oc.getCosttype());
		result.put("deadheadmileage", oc.getDeadheadmileage()+"公里");
		result.put("realdeadheadmileage", oc.getRealdeadheadmileage()+"公里");
		result.put("deadheadprice", oc.getDeadheadprice()+"元/公里");
		result.put("nightstarttime", oc.getNightstarttime());
		result.put("nightendtime", oc.getNightendtime());
		result.put("nighteprice", oc.getNighteprice()+"元/公里");
		result.put("premiumrate", oc.getPremiumrate()+"倍");
		result.put("couponprice", oc.getCouponprice()+"元");
		return result;
	}
	
	/**
	 * 获取出租车订单费用
	 * @param param
	 * @return
	 */
	public JSONObject getOpTaxiOrderCost(OrderCostParam param) {
		JSONObject result = new JSONObject();
		result.put("status", Retcode.NOSERVICESINCITY.code);
		result.put("message", getNoservicesincityMsg(param.getCity(), param.getOrdertype()));
		TaxiOrderCost orderCost = null;
		//用车时间不为null表示是页面获取预估
		if(param.getUsetime() != null){
			OpTaxiOrder orderInfo = new OpTaxiOrder();
			orderInfo.setOncity(param.getCity());
			orderInfo.setOrdertype(param.getOrdertype());
			orderInfo.setUsetime(param.getUsetime());
			getSendRules(orderInfo, result);
			param.setIsusenow(orderInfo.isIsusenow());
		}
		//获取预估费用
		if(null == param.getOrderno()) {
			orderCost = getOpTaxiOrderCostInEstimated(param);
			//当前城市有计费规则
			if(null != orderCost) {
				if(!param.isIsusenow()){ //预约单要加预约附加费
					orderCost.setStartprice(orderCost.getStartprice() + orderCost.getReversefee());
				}
				if(null == orderCost.getMileage() || null == orderCost.getTimes()) {
					orderCost.setMileage(0);
					orderCost.setTimes(0);
				}
				if(null == param.getSchedulefee()) {
					orderCost.setSchedulefee(0);
				} else {
					orderCost.setSchedulefee(param.getSchedulefee());
				}
				double startrange = orderCost.getStartrange(); //起租里程
				double standardrange = orderCost.getStandardrange(); //标准里程
				double mileage = orderCost.getMileage()/1000; //预估里程
				double renewalrange = mileage - startrange; //续租价里程
				if(renewalrange < 0) {
					renewalrange = 0;
				}
				double emptyrange = mileage - standardrange; //空驶价里程
				if(emptyrange < 0) {
					emptyrange = 0;
				}
				//行程费
				double rangecost = orderCost.getStartprice() + renewalrange * orderCost.getRenewalprice()
						+ emptyrange * orderCost.getRenewalprice() * orderCost.getEmptytravelrate() / 100
						+ orderCost.getSurcharge();
				orderCost.setRangecost(StringUtil.formatNum(rangecost, 1));
				orderCost.setCost(orderCost.getRangecost() + orderCost.getSchedulefee());
				doGetCanUseCounpon(param, null,orderCost);
				doPremium(param,null,orderCost);
				orderCost.setCost(StringUtil.formatNum(orderCost.getCost() * orderCost.getPremiumrate(), 1));
				result.put("status", Retcode.OK.code);
				result.put("message", Retcode.OK.msg);
				result.put("taxiOrderCost", orderCost);
			}
		}
		return result;
	}
	
	/**
	 * @deprecated (driver-api中使用)
	 * @param param
	 * @return
	 */
	public JSONObject getOrderStatistics(OrderStatisticsParam param){
		JSONObject json = new JSONObject();
		JSONArray statistics = new JSONArray();
		int page = param.getPageNO();
		for(int i=0;i<=3;i++){
			JSONObject statis = new JSONObject();
			statis.put("count", 30+page);
			statis.put("cost", 1960.0D);
			statis.put("year", 2015+i);
			statis.put("month", 7);	
			statistics.add(statis);
		}
		json.put("count", statistics.size());
		json.put("statistics", statistics);
		return json;
	}
	
	/**
	 * 网约车申请复议
	 * @param param
	 * @return
	 */
	public JSONObject orderReview(OrderReviewParam param){
		JSONObject result = new JSONObject();
		OrderApiParam bparam = new OrderApiParam();
		bparam.setToken(param.getToken());
		bparam.setOrderno(param.getOrderno());
		bparam.setOrdertype(param.getOrdertype());
		bparam.setUsetype(param.getUsetype());
		if(checkParam(bparam,result) == null) return result;
		if(param.getReviewtype() == 0){
			OrgOrder order = dao.getOrgOrder(param.getOrderno());
			order.setReviewstatus(ReviewState.WAITFORREVIEW.state);
			order.setReviewperson(param.getReviewperson());
			order.setOrderreason(param.getReason());
			dao.updateOrgOrder(order);
		}else{
			OpOrder order = dao.getOpOrder(param.getOrderno());
			order.setReviewstatus(ReviewState.WAITFORREVIEW.state);
			order.setReviewperson(param.getReviewperson());
			order.setOrderreason(param.getReason());
			dao.updateOpOrder(order);
		}
		result.put("status", Retcode.OK.code);
		result.put("message", "申请复议成功.");
		return result;
	}
	

	/**
	 * 派单实现
	 * @param order
	 * @param vehicleType
	 */
	private void sendOrder(AbstractOrder order,String vehicleType){
		threadPoolTaskExecutor.execute(new Runnable() {
			public void run() {
				SendServiceFactory sendservice = FactoryProducer.getFactory(vehicleType);
				SendRuleHelper senrule = sendservice.createSendRuleFactory().createSendRule(order);
				SendMethodHelper sendmethod = sendservice.createSendMethodFactory().createSendMethod(senrule);
				sendmethod.send(senrule, order);
			}
		});
	}
	
	/**
	 * 创建司机消息
	 * @param order
	 * @param param
	 */
	private void createDriverNews(OrderInfoDetail order,OrderApiParam param){
		//创建消息对象
		AbstractOrder o = null;
		if(order.getOrderprop() == OrderVarietyEnum.LEASE_NET.icode) {
			o = dao.getOrgOrder(order.getOrderno());
		} else if(order.getOrderprop() == OrderVarietyEnum.OPERATING_NET.icode) {
			o = dao.getOpOrder(order.getOrderno());
		} else if(order.getOrderprop() == OrderVarietyEnum.OPERATING_TAXI.icode) {
			o = dao.getOpTaxiOrder(order.getOrderno());
		}
		OrderMessageType msgType = OrderMessageType.getByCancelParty(param.getReqsrc(), order.getOrderprop() == OrderVarietyEnum.LEASE_NET.icode);
		OrderMessageFactory omf = new OrderMessageFactory(o, msgType);
		OrderInfoMessage orderinfo = omf.createMessage();
		//保存消息到数据库,供司机端消息中心访问
		PubDriverNews pdn = new PubDriverNews();
		pdn.setId(GUIDGenerator.newGUID());
		pdn.setUserid(order.getDriverid());
		pdn.setNewsstate(NewsState.UNREAD.code);
		pdn.setType(MessageType.ORDER.type);
		pdn.setContent(orderinfo.toString());
		pdn.setCreatetime(new Date());
		pdn.setUpdatetime(new Date());
		pdn.setStatus(1);
		dao.savePubDriverNews(pdn);
	}
	
	/**
	 * 取消订单处理逻辑
	 * @param order
	 */
	private OrderMessage orderCancel(OrderInfoDetail order,OrderApiParam param,PubDriver pd,JSONObject result){
		//取消需要发消息通知
		OrderMessage cancellorder = null;
		PubOrderCancel  poc = null;
		if(CancelParty.LEASE.code.equals(param.getReqsrc()) 
		|| CancelParty.OPERATOR.code.equals(param.getReqsrc())){  //客服取消
			poc = saveOrUpdateCancelInfo(order,param,null,result);
			if(result != null && !result.isEmpty()) return cancellorder;
		}else{   //乘客(机构端算乘客)取消
			Map<String,Object> map = doCancelPunish(order);
			poc = saveOrUpdateCancelInfo(order,param,map,result);
		}
		if(order.getOrderprop() == OrderVarietyEnum.LEASE_NET.icode){
			cancellorder = doCancelOrgOrder(order,param);
		} else if(order.getOrderprop() == OrderVarietyEnum.OPERATING_NET.icode) {
			cancellorder = doCancelOpOrder(order, param);
		} else {
			cancellorder = doCancelOpTaxiOrder(order, param);
		}
		
		handleDriverStatus(order,param,pd);
		rollbackUserBalance(order,poc);

		param.setRemind(false);   //取消订单同时取消行程提醒
		removeDriverMessage(param,pd);
        removeDriverTravelReminder(order.getOrderno(), order.getUsetype());
		cancelOrderRemind(param);
		
		order.setStatus(OrderState.CANCEL.state);
		order.setCanceltime(new Date());
		order.setCancelparty(param.getReqsrc());
		order.setExpensetype(parseInt(OrderEnum.EXPENSETYPE_CANCEL_PUNISH.code));
		
		result.clear();
		result.put("status", Retcode.OK.code);
		result.put("cancelcost", poc.getCancelamount());    //返回的取消费用(0也可以表示免责取消)
		result.put("cancelnature", poc.getCancelnature());  //返回的取消性质(1-有责,2-免责)
		return cancellorder;
	}

	/**
	 * 保存取消详情
	 * @param order
	 * @param param
	 * @param cancelrule
	 */
	private PubOrderCancel saveOrUpdateCancelInfo(OrderInfoDetail order,OrderApiParam param,Map<String, Object> cancelrule,JSONObject result){
		PubOrderCancel poc = null;
		try {
			int ordertype = order.getOrderprop() == 0 ? 2 : 
										order.getOrderprop() == 1 ? 1 : 
										order.getOrderprop() == 2 ? 3 : 1;
			poc = dao.getOrderCancelInfo(order.getOrderno(),ordertype);
			if(poc == null) {
				poc = new PubOrderCancel();
				poc.setId(GUIDGenerator.newGUID());
				poc.setStatus(DataStatus.OK.code);
			}
			if(CancelParty.PASSENGER.code.equals(param.getReqsrc()) 
					|| CancelParty.ORGAN.code.equals(param.getReqsrc())){  //乘客取消(机构端属于乘客取消)
				int cancelnature = PubOrdercancelEnum.getCancelnature((int)cancelrule.get("pricereason")).code;
				if(cancelrule.get("ordercancelrule").equals(""));
				poc.setOrderno(order.getOrderno());
				poc.setCancelamount((int)cancelrule.get("price"));
				poc.setCancelnature(cancelnature);
				poc.setCancelreason(param.getCancelreason() == null ? 0 : param.getCancelreason());
				poc.setCancelrule((String)cancelrule.get("ordercancelrule"));
			}else{     //客服取消
				if(!poc.getIdentifying().equals(param.getIdentifying())){  //如果唯一标识不相同,则表示被其他客服操作了
					logger.warn("该订单已被其他客服操作:" + poc.getOrderno());
					result.clear();
					result.put("status", Retcode.ANOTHEREXCUTE.code);
					result.put("message", Retcode.ANOTHEREXCUTE.msg);
					return poc;
				}
				poc.setDutyparty(param.getDutyparty());	
				poc.setCancelreason(param.getCancelreason());
                poc.setCanceloperator(param.getCanceloperator());
			}
			poc.setOrderprop(ordertype);
			dao.saveOrUpdateOrderCancelInfo(poc);
			return poc;
		} catch (Exception e) {
			logger.error("保存取消明细异常",e);
			poc = new PubOrderCancel();
			poc.setCancelamount(0);
			poc.setCancelnature(PubOrdercancelEnum.CANCELNATURE_NODUTY.code);
		}
		return poc;
	}
	
	/**
	 * 退回下单人预扣款
	 * @param order
	 */
	private void rollbackUserBalance(OrderInfoDetail order,PubOrderCancel poc){
		if(order.getOrderprop() == OrderVarietyEnum.LEASE_NET.icode){ //如果是机构订单,并且是机构支付,还需退回预扣款
			OrgOrder orgOrder = dao.getOrgOrder(order.getOrderno());
			if(PayMethod.ORGAN.code.equals(orgOrder.getPaymethod())){
				OrderCostParam ocp = new OrderCostParam();
				ocp.setUserid(orgOrder.getUserid());
				ocp.setCompanyid(orgOrder.getCompanyid());
				OrgOrganCompanyRef oocr = dao.getOrgBalance(ocp);
				oocr.setBalance(oocr.getBalance() + orgOrder.getEstimatedcost() - poc.getCancelamount());
				dao.updateOrgBalance(oocr);
			}
		}
	}
	
	/**
	 * 还原司机状态
	 * @param order
	 * @param param
	 * @param pd
	 */
	private void handleDriverStatus(OrderInfoDetail order,OrderApiParam param,PubDriver pd){
		if(pd != null){ //如果已有司机接单
			createDriverNews(order,param); //还需新记录一条司机消息(入库,app需要列出)
			if(DriverState.INSERVICE.code.equals(pd.getWorkstatus())){ //如果司机已经出发
				//还需将司机置回空闲(司机服务中状态才置回空闲,其他状态不变)
				pd.setWorkstatus(DriverState.IDLE.code);
				pd.setUpdateTime(new Date());
				dao.updatePubDriver(pd);
			}
		}
	}
	
	/**
	 * 机构订单取消
	 * @param order
	 * @param param
	 * @return
	 */
	private OrderMessage doCancelOrgOrder(OrderInfoDetail order,OrderApiParam param){
		OrgOrder orgOrder = dao.getOrgOrder(order.getOrderno());
		orgOrder.setPaymentstatus(PayState.PAYED.state);  //取消订单改成已支付
		orgOrder.setOrderstatus(OrderState.CANCEL.state);
		orgOrder.setCanceltime(new Date());
		orgOrder.setCancelparty(param.getReqsrc());
		orgOrder.setExpensetype(parseInt(OrderEnum.EXPENSETYPE_CANCEL_PUNISH.code));
		return new OrderMessage(orgOrder, OrderMessage.CANCELORDER);	
	}
	
	/**
	 * 个人订单取消
	 * @param order
	 * @param param
	 * @return
	 */
	private OrderMessage doCancelOpOrder(OrderInfoDetail order,OrderApiParam param){
		OpOrder opOrder = dao.getOpOrder(order.getOrderno());
		opOrder.setPaymentstatus(PayState.PAYED.state);  //取消订单改成已支付
		opOrder.setOrderstatus(OrderState.CANCEL.state);
		opOrder.setCanceltime(new Date());
		opOrder.setCancelparty(param.getReqsrc());
		opOrder.setExpensetype(parseInt(OrderEnum.EXPENSETYPE_CANCEL_PUNISH.code));
		return new OrderMessage(opOrder, OrderMessage.CANCELORDER);
	}
	
	/**
	 * 个人出租车取消
	 * @param order
	 * @param param
	 * @return
	 */
	private TaxiOrderMessage doCancelOpTaxiOrder(OrderInfoDetail order,OrderApiParam param){
		OpTaxiOrder opTaxiOrder = dao.getOpTaxiOrder(order.getOrderno());
		if(PayMethod.OFFLINE.code.equals(param.getPaymethod())){  
			//如果是线下支付,订单状态如果调度费不为0,改为已付结
			if(opTaxiOrder.getSchedulefee() > 0){
				opTaxiOrder.setPaymentstatus(PayState.PAYOVER.state);
			}else{ //如果不存在调度费,改为已结算
				opTaxiOrder.setPaymentstatus(PayState.STATEMENTED.state);
			}
		}else{  //如果是线上支付,订单支付状态需要改为已支付
			opTaxiOrder.setPaymentstatus(PayState.PAYED.state);
		}
		opTaxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.CANCEL.state));
		opTaxiOrder.setOrderstatus(OrderState.CANCEL.state);
		opTaxiOrder.setCanceltime(new Date());
		opTaxiOrder.setCancelparty(param.getReqsrc());
		opTaxiOrder.setExpensetype(parseInt(OrderEnum.EXPENSETYPE_CANCEL_PUNISH.code));
		return new TaxiOrderMessage(opTaxiOrder, TaxiOrderMessage.TAXI_CANCELORDER);	
	}
	
	/**
	 * 获取取消处罚规则
	 */
	private Map<String, Object> doCancelPunish(OrderInfoDetail order){
		Map<String, String> param = new HashMap<>();
		param.put("orderno", order.getOrderno());
		param.put("ordertype", order.getType());
		param.put("usetype", order.getUsetype());
		return orderCancelRuleService.getCancelPrice(param, true);
	}
	
	/**
	 * 订单完成处理逻辑
	 */
	private OrderMessage orderDone(OrderInfoDetail order,OrderApiParam param,JSONObject result){
		OrderCostParam ocp = new OrderCostParam();
		ocp.setHasunit(false);  //不做换算
		ocp.setToken(param.getToken());
		ocp.setUsetype(param.getUsetype());
		ocp.setOrdertype(param.getOrdertype());
		ocp.setOrderno(param.getOrderno());
		ocp.setLog(true); //订单结束，保存里程计算日志
		ocp.setOrderprop(order.getOrderprop());
		JSONObject cost = getOrderCost(ocp);
		if(cost.has("status")) {
			result.clear();
			result.put("status", cost.getInt("status"));
			result.put("message", cost.getString("message"));
			return null;
		}
		//订单完成时保存费用,耗时
		OrderCost oc = StringUtil.parseJSONToBean(cost.toString(), OrderCost.class);
//		StringUtil.formatOrderCost2Int(oc);   //完成时总费用不保留小数
		order.setPricecopy(cost.toString());     //保存计费副本(其中包含低速时长)
		order.setOrderamount(oc.getCost());
		order.setTimecost(oc.getTimecost());
		order.setRangecost(oc.getRangecost());
		order.setMileage(oc.getMileage());
		order.setTimes(oc.getTimes());
		order.setEndtime(new Date());
		order.setCosttype(oc.getCosttype());
		order.setStatus(OrderState.SERVICEDONE.state);
		//完成还需要发消息通知
		OrderMessage completeOrder = doOrderDone(order, oc,ocp);
		return completeOrder;
	}
	
	/**
	 * 人工派单处理逻辑
	 * @param order
	 * @return
	 */
	public OrderRedisMessageFactory orderMantic(OrderInfoDetail order){
		OrderRedisMessageFactory rmf = null;
		order.setStatus(OrderState.MANTICSEND.state);
		order.setOrdersortcolumn(OrdersortColumn.WAITTAKE.state);
		PubSendrules sendRule = new PubSendrules();
		JSONObject result = new JSONObject();
        if(order.getOrderprop() == 0) {
        	OrgOrder oo = dao.getOrgOrder(order.getOrderno());
        	sendRule = getSendRules(oo, result);
        } else {
        	OpOrder oo = dao.getOpOrder(order.getOrderno());
        	sendRule = getSendRules(oo, result);
        }
        if(sendRule == null) return null;
		if(order.getOrderprop() == 0){
			rmf = new OrderRedisMessageFactory(RedisMessageType.MANTICORDER,PlatformType.LEASE);
			rmf.setOrder(order);
			rmf.setOutTime(sendRule.getPersonsendinterval());				
		}else{
			rmf = new OrderRedisMessageFactory(RedisMessageType.MANTICORDER,PlatformType.OPERATING);
			rmf.setOrder(order);
			rmf.setOutTime(sendRule.getPersonsendinterval());
		}
		return rmf;
	}
	
	/**
	 * 抢单成功处理逻辑
	 * @param order
	 * @return
	 */
	public OrderRedisMessageFactory orderTaken(OrderInfoDetail order){
		OrderRedisMessageFactory rmf = null;
		if(order.getOrderprop() == 0){
			rmf = new OrderRedisMessageFactory(RedisMessageType.TAKINGORDER,PlatformType.LEASE);
			rmf.setOrder(order);
		}else{
			rmf = new OrderRedisMessageFactory(RedisMessageType.TAKINGORDER,PlatformType.OPERATING);
			rmf.setOrder(order);
		}
		rmf.setDriver(driverDao.getDriverInfoById(order.getDriverid()));
		return rmf;
	}
	
	/**
	 * 此方法目前只能更改取消,完成和待人工派单状态(其他订单状态在司机端完成)
	 * @param param
	 * @return
	 */
	public JSONObject changeOrderState(OrderApiParam param){
		JSONObject result = new JSONObject();
		OrderInfoDetail order = null;
		OrderMessage ordermessage = null;
		OrderRedisMessageFactory rmf = null;
		
		if((order = checkParam(param,result)) == null) return result;
		if(!canChangeOrderState(order,param,result)) return result;
        //订单类型 网约车订单
		order.setOrderstyle(VehicleEnum.VEHICLE_TYPE_CAR.code);
		if(OrderState.CANCEL.state.equals(param.getOrderstate())){
			PubDriver pd = null;
			//如果司机已接单
			if(order.getDriverid() != null && !order.getDriverid().isEmpty()){
				pd = dao.getPubDriverById(order.getDriverid());
			}
			ordermessage = orderCancel(order,param,pd,result);
		}else if(OrderState.SERVICEDONE.state.equals(param.getOrderstate())){
			ordermessage = orderDone(order,param,result);
			if(ordermessage == null) return result;
		}else if (OrderState.MANTICSEND.state.equals(param.getOrderstate())) {
			rmf = orderMantic(order);
			removeDriverMessage(param,null);
		}else if (OrderState.WAITSTART.state.equals(param.getOrderstate())) {
            //如果不为出租车订单才弹窗
            if(!OrderEnum.ORDERTYPE_TAXI.code.equals(param.getOrdertype())){
 			    rmf = orderTaken(order);
            }
		}else{
			result.clear();
			result.put("status", Retcode.INVALIDORDERSTATUS.code);
			result.put("message", Retcode.INVALIDORDERSTATUS.msg);
		}
		//所有操作正确才更新和推送
		if(result == null || result.isEmpty() || result.getInt("status") == Retcode.OK.code){
			dao.updateOrderInfo(order);
			if(ordermessage != null) MessageUtil.sendMessage(ordermessage);
			if(rmf != null && param.isRemind()){
				rmf.createMessage(this);
				rmf.sendMessage();
			}
		}
		return result;
	}
	
	/**
	 * 保存个人用户交易记录
	 */
	private void doSavePeUserExpenses(OrderInfoDetail order,PeUser user){
		if(user == null) return;
		PeUserExpenses expenses = new PeUserExpenses();
		expenses.setId(GUIDGenerator.newGUID());
		expenses.setUserid(user.getId());
		expenses.setExpensetype(PurseEnum.EXPENSETYPE_BALANCE.code);
		expenses.setExpensetime(new Date());
		expenses.setAmount(new BigDecimal(order.getOrderamount()));
		expenses.setDetailtype(PurseEnum.DETAILTYPE_ALLTRANS.code);
		expenses.setTradetype(PurseEnum.TRADETYPE_ORDER.code);
		expenses.setOperateresult(PurseEnum.OPERATERESULT_SUCCESS.code);
		expenses.setRemark("【" + PurseEnum.TRADETYPE_ORDER.msg + "】" + "返还积分");
		expenses.setStatus(DataStatus.OK.code+"");
		expenses.setCreater(user.getId());
		expenses.setUpdater(user.getId());
		dao.savePeUserExpenses(expenses);
		logger.info("保存乘车人交易明细完成");
	}
	
	/**
	 * 创建个人用户及钱包
	 * @param oid
	 * @return
	 */
	private Map<String,Object> createPeUser(AbstractOrder order,double awardpoint){
		Map<String, Object> resultMap = new HashMap<>();
		String rawpwd = StringUtil.getRandomStr(6, StringUtil.UPPERCASE_HASNUM);
		PeUser user = new PeUser();
		user.setId(GUIDGenerator.newGUID());
		user.setAccount(order.getPassengerphone());
		user.setUserpassword(PasswordEncoder.encode(rawpwd));
		user.setNickname(order.getPassengers());
		user.setSex("0");
		user.setSpecialstate("0");
		user.setUsetimes(0);
		user.setDisablestate("0");
		user.setDisableHis("0");
		user.setWdpwdchangestate(0);
		user.setStatus(DataStatus.OK.code);
		PeUseraccount useraccount = new PeUseraccount();
		useraccount.setId(GUIDGenerator.newGUID());
		useraccount.setUserid(user.getId());
		useraccount.setBalance(0.0D);
		useraccount.setStatus(DataStatus.OK.code);
		dao.createPeUser(user);
		dao.createPeUserAccount(useraccount);
		sendCreatePeUserMessage(user, rawpwd,awardpoint);
		resultMap.put("user", user);
		resultMap.put("useraccount", useraccount);
		return resultMap;
	}
	
	/**
	 * 给乘车人发送新用户短信
	 * @param user
	 * @param rawpwd
	 * @param awardpoint
	 */
	private void sendCreatePeUserMessage(PeUser user,String rawpwd,double awardpoint){
        //发送短信
//      String content = "欢迎乘坐“如约的士”专车服务，现已为您开通如约账号："+phone+"，密码："+srcpwd+"，诚邀下载app登录乘车。";
      String content = null;
      content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.carservice.service.AwardPassengerService.ensureExistOrg",awardpoint,user.getAccount(),rawpwd);
      List<String> phones = new ArrayList<String>();
      phones.add(user.getAccount());
      UserMessage message = new UserMessage(phones, content, UserMessage.ADDUSER);
      MessageConnection.add(message);
	}
	
	/**
	 * 检查是否能够返积分
	 * @return
	 */
	private boolean checkCanAward(double nowAward){
		PubDictionary awardPower = new PubDictionary();
		awardPower.setType("订单返还开关");
		awardPower = dao.getDicByType(awardPower);
		if(awardPower == null || !"1".equals(awardPower.getValue())) return false;
		double currentAward = getCurrentAward();
		double totalAward = getTotalAward();
		if((currentAward+nowAward) > totalAward) {
			awardDao.stopAward();   //如果返还积分达到上限,发送停止活动短信
			return false;
		}
		return true;
	}
	
	/**
	 * 获取积分返点比例
	 * @return
	 */
	private double getAwardPoint(AbstractOrder order){
		PubDictionary awardPoint = new PubDictionary();
		if(order instanceof OrgOrder){
			awardPoint.setType("机构用户订单支付返还比例");
		}else{
			awardPoint.setType("个人用户订单支付返还比例");
		}
		awardPoint = dao.getDicByType(awardPoint);
		if(awardPoint == null) {
			if(order instanceof OrgOrder) return 0.2D;  //默认20%(机构)
			else return 0.34D;                                            //默认34%(个人)
		}
		return Double.parseDouble(String.valueOf(awardPoint.getValue()));
	}
	
	/**
	 * 获取当前已返还积分
	 * @return
	 */
	private double getCurrentAward(){
		PubDictionary awardPoint = new PubDictionary();
		awardPoint.setType("订单返还总计金额");
		awardPoint = dao.getDicByType(awardPoint);
		if(awardPoint == null) return 0.0D;  //默认20%
		return Double.parseDouble(String.valueOf(awardPoint.getValue()));
	}
	
	/**
	 * 获取积分返还上限
	 * @return
	 */
	private double getTotalAward(){
		PubDictionary awardPoint = new PubDictionary();
		awardPoint.setType("订单返还金额上限");
		awardPoint = dao.getDicByType(awardPoint);
		if(awardPoint == null) return 0.0D;  //默认20%
		return Double.parseDouble(String.valueOf(awardPoint.getValue()));
	}
	
	/**
	 * 检查订单折扣开关
	 * @return
	 */
	private boolean checkCanTakeOff(){
		PubDictionary awardPower = new PubDictionary();
		awardPower.setType("订单折扣开关");
		awardPower = dao.getDicByType(awardPower);
		if(awardPower == null || !"1".equals(awardPower.getValue())) return false;
		return true;
	}
	
	/**
	 * 获取下单人折扣比例
	 * @param oid
	 * @return
	 */
	private double getTakeOffPoint(boolean isorg){
		PubDictionary takeOffPoint = new PubDictionary();
		if(isorg){
			takeOffPoint.setType("机构用户订单支付折扣比例");
		}else{
			takeOffPoint.setType("个人用户订单支付折扣比例");
		}
		takeOffPoint = dao.getDicByType(takeOffPoint);
		if(takeOffPoint == null) return 0.6D;  //默认60%
		return Double.parseDouble(String.valueOf(takeOffPoint.getValue()));
	}
	
	/**
	 * 积分返点
	 * @param oid
	 * @return 是否需要保存明细(null 不保存)
	 */
	private PeUser doAwardPoint(AbstractOrder order){
		PeUser user = new PeUser();
		PeUseraccount userAccount = new PeUseraccount();
		double awardPoint = getAwardPoint(order);        //返点比例
		double currentAward = order.getOriginalorderamount() * awardPoint;
		boolean awardPower = checkCanAward(currentAward);   //返点开关
		if(!awardPower) return null;
		user.setAccount(order.getPassengerphone());
		user = dao.getPeUserByPhone(user);
		if(user == null && order.getPassengerphone() != null && !order.getPassengerphone().trim().isEmpty()) {
			double amount = Math.round(currentAward);
			Map<String, Object> resultMap = createPeUser(order,amount);
			user = (PeUser)resultMap.get("user");
			userAccount = (PeUseraccount)resultMap.get("useraccount");
			userAccount.setBalance(amount);
			dao.updatePeBalance(userAccount);
			logger.info("新用户返点完成");
			return user;
		}else{
			logger.info("乘车人已存在,不返还积分");
			return null;
		}
	}
	
	/**
	 * 机构用户扣款
	 * @param order
	 * @param orgOrder
	 * @param ocp
	 */
	private void takeOffOrgBalance(OrgOrder orgOrder,OrderCostParam ocp){
		ocp.setUserid(orgOrder.getUserid());
		ocp.setCompanyid(orgOrder.getCompanyid());
		OrgOrganCompanyRef oocr = dao.getOrgBalance(ocp);
		//这里要先把预估费用加回来,因为在下单时已经按预估费用扣除了可用余额
		oocr.setBalance(oocr.getBalance() + orgOrder.getEstimatedcost());
		double orgBalance = oocr.getBalance();
		double realCost = orgOrder.getOrderamount();
		oocr.setBalance(orgBalance - realCost);
		dao.updateOrgBalance(oocr);
	}
	
	/**
	 * 机构支付
	 * @param order
	 * @param orgOrder
	 * @param ocp
	 */
	private void doOrgPayOrder(OrderInfoDetail order,OrgOrder orgOrder,OrderCostParam ocp){
		//还需要判断是否选择的机构支付
		if(!PayMethod.ORGAN.code.equals(orgOrder.getPaymethod())) return;
//		if(!order.getUserphone().equals(orgOrder.getPassengerphone())){
//			PeUser user = doAwardPoint(orgOrder);       //完成时积分返积分(必须先返还,扣款后可能使用折扣)
//			takeOffOrgBalance(orgOrder,ocp);                 //机构用户扣款(先扣款,明细中要保存打折后)
//			doSavePeUserExpenses(order,user);                //如果需要返积分则保存明细
//		}else{
//			logger.info("下单人与乘车人相同,不返还积分");
			takeOffOrgBalance(orgOrder,ocp);      //机构用户扣款
//		}
		order.setPaystatus(PayState.MENTED.state);
		order.setCompletetime(new Date());
		orgOrder.setPaymentstatus(PayState.MENTED.state);
		orgOrder.setCompletetime(new Date());
	}
	
	/**
	 * 个人订单计算结束费用
	 * @param order
	 * @param opOrder
	 */
	private void doOpPayOrder(OrderInfoDetail order,OpOrder opOrder){
		if(opOrder.getOrderamount() == 0){    //如果金额为0,直接完成订单,不用支付
			order.setPaystatus(PayState.PAYED.state);
			order.setCompletetime(new Date());
			opOrder.setPaymentstatus(PayState.PAYED.state);
			opOrder.setCompletetime(new Date());
		}else{
			
		}
		//获取费用时已经打折,不需要再次打折
//		boolean takeOff = checkCanTakeOff();
//		if(takeOff){  //如果折扣开关开启,使用折扣价
//			double takeOffPoint = getTakeOffPoint(order);
//			double amount = Math.round(order.getOrderamount() * takeOffPoint);
//			order.setOrderamount(amount);  //折扣价
//			opOrder.setOrderamount(amount);
//			opOrder.setActualpayamount(amount);
//			opOrder.setShouldpayamount(amount);
//		}
	}
	
	/**
	 * 订单结束逻辑
	 * @param order
	 * @param oc
	 * @param ocp
	 * @return
	 */
	private OrderMessage doOrderDone(OrderInfoDetail order,OrderCost oc,OrderCostParam ocp){
		OrderMessage completeOrder = null;
		//订单完成,如果是机构订单状态改为已支付(还需计算机构余额是否足够,已实现)
		if(order.getOrderprop() == 0){
			OrgOrder orgOrder = dao.getOrgOrder(order.getOrderno());
			orgOrder.setOrderamount(oc.getCost());
			orgOrder.setShouldpayamount(oc.getCost());
			orgOrder.setActualpayamount(oc.getCost());
			orgOrder.setOriginalorderamount(StringUtil.formatNum(oc.getCost()/oc.getDiscount(), 1));
			orgOrder.setEndtime(order.getEndtime());
			orgOrder.setUpdatetime(new Date());
			orgOrder.setOrderstatus(OrderState.SERVICEDONE.state);
			orgOrder.setCosttype(oc.getCosttype());
			orgOrder.setExpensetype(parseInt(OrderEnum.EXPENSETYPE_SERVICE_DONE.code));
			doOrgPayOrder(order,orgOrder,ocp);    //机构支付
			dao.updateOrgOrder(orgOrder);
			completeOrder = new OrderMessage(orgOrder, OrderMessage.FINISHORDER);
		}else{
			OpOrder opOrder = dao.getOpOrder(order.getOrderno());
			opOrder.setOrderamount(oc.getCost());
			opOrder.setShouldpayamount(oc.getCost());
			opOrder.setActualpayamount(oc.getCost());
			opOrder.setOriginalorderamount((double)Math.round(oc.getCost()/oc.getDiscount()));
			opOrder.setEndtime(order.getEndtime());
			opOrder.setUpdatetime(new Date());
			opOrder.setOrderstatus(OrderState.SERVICEDONE.state);
			opOrder.setCosttype(oc.getCosttype());
			opOrder.setExpensetype(parseInt(OrderEnum.EXPENSETYPE_SERVICE_DONE.code));
			doOpPayOrder(order,opOrder);      //个人订单值重写费用
			dao.updateOpOrder(opOrder);
			completeOrder = new OrderMessage(opOrder, OrderMessage.FINISHORDER);	
		}
		order.setExpensetype(parseInt(OrderEnum.EXPENSETYPE_SERVICE_DONE.code));
		return completeOrder;
	}
	
	/**
	 * 检查订单能否改变状态
	 * @param oid
	 * @param param
	 * @param result
	 * @return
	 */
	private boolean canChangeOrderState(OrderInfoDetail order,OrderApiParam param,JSONObject result){
		result.clear();
		//如果订单已经是取消状态,则不执行任何操作
		if(OrderState.CANCEL.state.equals(order.getStatus())) {
			result.put("order", order);
			result.put("status", Retcode.ORDERISCANCEL.code);
			result.put("message", Retcode.ORDERISCANCEL.msg);
			return false;
		}
		//如果请求转人工
		if(OrderState.MANTICSEND.state.equals(param.getOrderstatus())){
			if(OrderState.MANTICSEND.state.equals(order.getStatus())){     //订单已经是人工派单状态,则不再更新
				return false;
			}else if(OrderState.WAITTAKE.state.equals(order.getStatus())){  //订单是待接单状态,允许人工干预
				return true;
			}else{  //如果是其他状态,则表示已经接单,不能转人工
				result.put("status", Retcode.ORDERISGONE.code);
				result.put("message", Retcode.ORDERISGONE.msg);
				return false;
			}
		}
		//如果请求取消,并且订单是服务中或已完成状态,则不能更新
		if(OrderState.CANCEL.state.equals(param.getOrderstatus()) && 
			(OrderState.INSERVICE.state.equals(order.getStatus()) 
			|| OrderState.SERVICEDONE.state.equals(order.getStatus()))){
			result.put("status", Retcode.INVALIDORDERSTATUS.code);
			result.put("message", "当前订单不可取消");
			return false;
		}
		//出租车司机出发,抵达时,如果打表里程不为0,不允许取消
		if(OrderEnum.ORDERTYPE_TAXI.code.equals(param.getOrdertype())
			&& order.getMeterrange() > 0
			&& OrderState.START.state.equals(order.getStatus())
			&& OrderState.ARRIVAL.state.equals(order.getStatus())
			){
			result.put("status", Retcode.INVALIDORDERSTATUS.code);
			result.put("message", "当前订单不可取消");
			return false;
		}
		return true;
	}
	
	public List<OrgOrder> getOrgBeDepartureOrder(OrderApiParam param){
		List<OrgOrder> result = dao.getOrgBeDepartureOrder(param);
		return result;
	}
	
	
	public List<OpOrder> getOpBeDepartureOrder(OrderApiParam param){
		List<OpOrder> result = dao.getOpBeDepartureOrder(param);
		return result;
	}

	public List<OpTaxiOrder> getOpTaxiBeDepartureOrder(OrderApiParam param){
		List<OpTaxiOrder> result = dao.getOpTaxiBeDepartureOrder(param);
		return result;
	}

	
	public Map<String, Object> cancelOrderRemind(OrderApiParam param) {
		ObjectMapper objectMapper = new  ObjectMapper();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(!param.isRemind()) {
				Set<String> orderKeys = JedisUtil.getKeys(RedisKeyEnum.MESSAGE_TRAVEL_REMINDER_PREFIX.code + "*" + param.getOrderid());

				for(String orderKey : orderKeys) {
					String orderStr = JedisUtil.getString(orderKey);
					AbstractOrder order = null;

					if(orderKey.indexOf(RedisKeyEnum.MESSAGE_TRAVEL_REMINDER_ORG.code) != -1) {
						order = objectMapper.readValue(orderStr, OrgOrder.class);
					} else if(orderKey.indexOf(RedisKeyEnum.MESSAGE_TRAVEL_REMINDER_OP.code) != -1) {
						order = objectMapper.readValue(orderStr, OpOrder.class);
					} else {
						order = objectMapper.readValue(orderStr, OpTaxiOrder.class);
					}

					if(order != null) {
						order.setLastsendtime(new Date(order.getLastsendtime().getTime() + 1000 * 60 * 60 * 4));

						JedisUtil.setString(orderKey, objectMapper.writeValueAsString(order));
						JedisUtil.expire(orderKey, 1900);
					}
				}

			}

			resultMap.put("status", Retcode.OK.code);
			resultMap.put("message", Retcode.OK.msg);
		} catch (Exception e) {
			resultMap.put("status", Retcode.FAILED.code);
			resultMap.put("message", Retcode.FAILED.msg);
			resultMap.put("error", e.getMessage());
			logger.error("行程提醒取消异常：", e);
		}
		return resultMap;
	}

	public Map<String, Object> cancelOverTimeOrder(OrderApiParam param) {
		int result = 0;
		//机构订单超时处理
		List<OrgOrder> orgOrderList = dao.getOverTimeOrgOrderList();
		if(null != orgOrderList && !orgOrderList.isEmpty()) {
			for (OrgOrder order : orgOrderList) {
                addOrdercancel(order);
				order.setOrderstatus("8");
				order.setExpensetype(Integer.valueOf(OrderEnum.EXPENSETYPE_CANCEL_PUNISH.code));
				order.setCancelparty(CancelParty.SYSTEM.code);
				dao.cancelOverTimeOrgOrder(order);
				//修改机构可用金额
				if(order.getPaymethod().equals("2")) {
					OrgOrganCompanyRef companyRef = new OrgOrganCompanyRef();
					companyRef.setCompanyId(order.getCompanyid());
					companyRef.setOrganId(order.getOrganid());
					companyRef = dao.getOrgOrganCompanyRefByOrgCom(companyRef);
					if(null != companyRef) {
						companyRef.setBalance(companyRef.getBalance() + order.getEstimatedcost());
						dao.updateOrgOrganCompanyRef(companyRef);
					}
				}
                removeDriverTravelReminder(order.getOrderno(), order.getUsetype());
				//发送消息
				OrderMessage message = new OrderMessage(order, OrderMessage.SENDORDERFAIL);
				MessageUtil.sendMessage(message);
			}
			result = orgOrderList.size();
		}
		
		//个人订单超时处理
		List<OpOrder> opOrderList = dao.getOverTimeOpOrderList();
		if(null != opOrderList && !opOrderList.isEmpty()) {
			for (OpOrder order : opOrderList) {
                addOrdercancel(order);
				order.setOrderstatus("8");
				order.setCancelparty(CancelParty.SYSTEM.code);
				order.setExpensetype(Integer.valueOf(OrderEnum.EXPENSETYPE_CANCEL_PUNISH.code));
				dao.cancelOverTimeOpOrder(order);
                removeDriverTravelReminder(order.getOrderno(), order.getUsetype());
				//发送消息
				OrderMessage message = new OrderMessage(order, OrderMessage.SENDORDERFAIL);
				MessageUtil.sendMessage(message);
			}
			result += opOrderList.size();
		}
		
		//运管端出租车订单超时处理
		List<OpTaxiOrder> opTaxiOrderList = dao.getOverTimeOpTaxiOrderList();
		if(null != opTaxiOrderList && !opTaxiOrderList.isEmpty()) {
			for (OpTaxiOrder opTaxiOrder : opTaxiOrderList) {
                addOrdercancel(opTaxiOrder);
				opTaxiOrder.setOrderstatus("8");
				opTaxiOrder.setCancelparty(CancelParty.SYSTEM.code);
				opTaxiOrder.setOrdersortcolumn(OrdersortColumn.CANCEL.state);
				opTaxiOrder.setExpensetype(Integer.valueOf(OrderEnum.EXPENSETYPE_CANCEL_PUNISH.code));
				dao.cancelOverTimeOpTaxiOrder(opTaxiOrder);
                removeDriverTravelReminder(opTaxiOrder.getOrderno(), opTaxiOrder.getUsetype());
				//发送消息
//				OrderMessage message = new TaxiOrderMessage(opTaxiOrder, TaxiOrderMessage.TAXI_SENDORDERFAIL);
//				MessageUtil.sendMessage(message);
			}
			result += opTaxiOrderList.size();
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Retcode.OK.code);
		resultMap.put("message", result);
		
		return resultMap;
	}

    /**
     * 设置订单取消信息
     * @param order
     * @return
     */
    public void addOrdercancel(AbstractOrder order) {
        int ordertype = 1;
        if(order instanceof OpOrder) {
            ordertype = 1;
        } else if(order instanceof OrgOrder) {
            ordertype = 2;
        } else if(order instanceof OpTaxiOrder) {
            ordertype = 3;
        }
        PubOrderCancel orderCancel = dao.getOrderCancelInfo(order.getOrderno(), ordertype);
        if(null == orderCancel) {
            orderCancel = new PubOrderCancel();
            orderCancel.setId(GUIDGenerator.newGUID());
            orderCancel.setOrderno(order.getOrderno());
        }
        orderCancel.setDutyparty(null);
        orderCancel.setCancelamount(0);
        orderCancel.setCancelnature(null);
        orderCancel.setCancelreason(PubOrdercancelEnum.CANCELREASON_SYSTEM_FAIL.code);
        orderCancel.setIdentifying(null);

        //查询订单取消信息
        Map<String, String> param = new HashMap<String, String>();
        param.put("orderno", order.getOrderno());
        param.put("ordertype", order.getOrdertype());
        param.put("usetype", order.getUsetype());
        Map<String, Object> ret = orderCancelRuleService.getCancelPrice(param, true);
        if(null == ret || !ret.get("status").equals(Retcode.OK.code)) {
            return;
        }
        orderCancel.setCancelrule(StringUtil.parseBeanToJSON(ret.get("ordercancelrule")));
        orderCancel.setOrderprop(ordertype);
        dao.saveOrUpdateOrderCancelInfo(orderCancel);
    }

	public OrgOrder getBeArtificialOrgOrder(Map<String, Object> params) {
		String companyId = (String) params.get("companyId");
		Map<String, OrgOrder> orgOrderMap = OrderMessageUtil.getbeArtificialOrgOrderInstance();
		OrgOrder order = null;
		
		for(String key : orgOrderMap.keySet()) {
			if(orgOrderMap.get(key).getCompanyid().equalsIgnoreCase(companyId)) {
				order = orgOrderMap.get(key);
				orgOrderMap.remove(key);
				break;
			}
		}
		
		return order;
	}
	
	public OpOrder getBeArtificialOpOrder(Map<String, Object> params) {
		Map<String, OpOrder> opOrderMap = OrderMessageUtil.getbeArtificialOpOrderInstance();
		OpOrder order = null;
		
		for(String key : opOrderMap.keySet()) {
			order = opOrderMap.get(key);
			opOrderMap.remove(key);
			break;
		}
		
		return order;
	}
	
	/**
	 * 对禁用期限已到的的用户执行启用操作
	 * @return
	 */
	public Map<String, Object> startPeUser() {
		int result = dao.startPeUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Retcode.OK.code);
		resultMap.put("message", result);
		return resultMap;
	}
	
	/**
	 * 添加机构可用额度不足消息
	 * @return
	 */
	public Map<String, Object> createOrganBalanceNews() {
		int ret = 0;
		
		List<Map<String, String>> organList = dao.getOrganForBalance();
		if(null != organList && !organList.isEmpty()) {
			for (Map<String, String> organ : organList) {
				ret += createOrgUserNews(organ.get("organid"), organ.get("shortname"));
			}
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Retcode.OK.code);
		resultMap.put("message", ret);
		return resultMap;
	}
	
	private int createOrgUserNews(String organid, String shortname) {
		List<String> useridList = dao.getOrgUserListByOrgan(organid);
		List<String> userids = new ArrayList<String>();
		if(null != useridList && !useridList.isEmpty()) {
			String content = "(" + shortname + ")账户余额不足，为保证用车需求，请尽快充值。";
			JSONObject json = new JSONObject();
			json.put("title", "账户余额不足");
			json.put("type", 11);
			OrgUser orgUser = null;
			for (String userid : useridList) {
				UserNews userNews = new UserNews();
				userNews.setUserid(userid);
				userNews.setType(UserNews.USER_NEWS_TYPE_SYSTEM);
				json.put("content", content);
				userNews.setContent(json.toString());
				UserNewsParam param = new UserNewsParam();
				param.setUserNewsTbName(UserNewsParam.ORG_USERNEWS_TABNAME);
				param.setUserNews(userNews);
				templateHelper.dealRequestWithFullUrlToken(
						SystemConfig.getSystemProperty("carserviceApi") + "/UserNews/addUserNews", HttpMethod.POST,
						null, param, JSONObject.class);
				
				// 查找机构超管、财务人员、部门管理员的手机号
				orgUser = dao.getOrgUserByUserId(userid);
				userids.add(orgUser.getAccount());
			}
		}
		
		String messageContent = shortname + "的可用额度已低于100，为了不影响员工的用车服务，请及时充值。";
		// 查找字典表中机构财务管理余额不足短信提醒接收号码
		String type = "机构财务管理余额不足短信提醒接收号码";
		List<String> telephonesList = dao.getValueByType(type);
		for (String telephone : telephonesList) {
			if (StringUtils.isNotBlank(telephone)) {
				String[] phones = telephone.split("#");
				for (int i=0;i<phones.length;i++) {
					if (StringUtils.isNotBlank(phones[i])) {
						userids.add(phones[i]);
					}
				}
			}
		}
		// 发送短信
		if (userids != null && userids.size() != 0) {
			SMMessageUtil.send(userids, messageContent);
		}
		
		return useridList.size();
	}
	
    /**
     * 返回订单redis消息Key
     * @param toSystem      发送系统
     * @param payMethod     订单类型 0、1 个人订单，2 机构订单
     * @param organId       机构ID
     * @param compayId      租赁公司ID
     * @param cityCode      上车城市
	 * @param orderStyle	订单类型 网约车/出租车
     * @return
     */
    public List<String> getOrderRedisMessageKeys(String toSystem,String payMethod,String organId,
                                                  String compayId,String cityCode,String orderStyle){
        List<String> roleIds = null;
        if(PlatformType.LEASE.code.equals(toSystem)){
            roleIds = dao.findOrgRoleId(payMethod,organId);
            //添加租赁端超管标识 租赁端超管增加 租赁公司ID标识；
            roleIds.add(RedisKeyEnum.MESSAGE_LEASE_ROLE_ADMIN.code+compayId);
        }else if (PlatformType.OPERATING.code.equals(toSystem)){
            roleIds = dao.findOpRoleId(cityCode,orderStyle);
            //添加运管端超管标识
            roleIds.add(RedisKeyEnum.MESSAGE_OPERATE_ROLE_ADMIN.code);
        }
        return roleIds;
    }

    /**
     * 设置redisList消息
     * @param roleIds

	private void sendOrderMessage(RedisMessage redisMessage,List<String> roleIds) throws Exception {

        if(roleIds!=null){
            for(String roleId:roleIds){
                redisMessage.setKey(roleId);
                RedisListMessage.getInstance().pushMessage(redisMessage);
            }
        }
    }*/

	/**
	 * 创建司机存库消息
	 * @param driverNews
	 */
	public void savePubDriverNews(PubDriverNews driverNews) {
		dao.savePubDriverNews(driverNews);
	}
	
	/**
	 * 取消订单|转人工时,清除redis中的抢单信息
	 * @return
	 */
	private boolean removeDriverMessage(OrderApiParam param,PubDriver driver){
		String key = "DriverGrabMessage_*_*_" + param.getOrderno() + "*";
		Set<String> keys = JedisUtil.getKeys(key);
		List<String> phones = new ArrayList<>();
		for(String k : keys){
			JedisUtil.delKey(k);
			String driverPhone = k.split("_")[2];
			//剔除当前司机
			if(null != driver 
				&& StringUtils.isNotBlank(driver.getPhone())
				&& driver.getPhone().equals(driverPhone)) {
				continue;
			}else{
				phones.add(driverPhone);
			}
		}
		if(!phones.isEmpty()){
			//给被删除消息的司机发送通知
			sendMessage4Order(phones);
		}
		return true;
	}
	
	/**
	 * 订单状态变更给司机和乘客发送推送
	 */
	private boolean sendMessage4Order(List<String> phones){
		String messagetype = TaxiOrderMessage.TAXI_DRIVERMESSAGE;
		TaxiOrderMessage om = null;
		//推送给其他之前推送过抢单消息的司机
		om = new TaxiOrderMessage(messagetype,phones);
		MessageUtil.sendMessage(om);
		return true;
	}
	
	private String getNoservicesincityMsg(String city, String ordertype) {
		StringBuilder sb = new StringBuilder();
		PubCityAddr cityAddr = infoDao.getCityById(city);
		if(null == cityAddr) {
			return Retcode.NOSERVICESINCITY.msg;
		}
		OrderEnum orderEnum = OrderEnum.getOrderType(ordertype);

        sb.append(cityAddr.getCity()).append("已暂停开展");
        if(null != orderEnum) {
            if(OrderEnum.ORDERTYPE_RESERVE == orderEnum) {
                sb.append("专车");
            } else {
                sb.append(orderEnum.msg);
            }
        }
        sb.append("服务");
		return sb.toString();
	}

	private String getOverservicesincityMsg(String city, String ordertype) {
        StringBuilder sb = new StringBuilder();
        PubCityAddr cityAddr = infoDao.getCityById(city);
        if(null == cityAddr) {
            return Retcode.NOSERVICESINCITY.msg;
        }
        OrderEnum orderEnum = OrderEnum.getOrderType(ordertype);

        sb.append(cityAddr.getCity());
        if(null != orderEnum) {
            if(OrderEnum.ORDERTYPE_RESERVE == orderEnum) {
                sb.append("专车");
            } else {
                sb.append(orderEnum.msg);
            }
        }
        sb.append("服务已到期，不可继续下单");
        return sb.toString();
    }

    private String getNoOrgVehicleModelMsg(String vehicleModel) {
        LeVehiclemodels leVehiclemodels = dao.getLeVehiclemodelsById(vehicleModel);
        if(null != leVehiclemodels) {
            return "您不再享有" + leVehiclemodels.getName() + "专车服务，请改乘其他车型";
        } else {
            return "您不再享有专车服务，请改乘其他车型";
        }
    }

    private String getNoServiceMsg(String city, String vehicleModel) {
        PubCityAddr cityAddr = infoDao.getCityById(city);
        if(null == cityAddr) {
            return Retcode.NOSERVICESINCITY.msg;
        }

        OpVehiclemodels vehiclemodels = dao.getOpVehiclemodelsById(vehicleModel);
        if(null == vehiclemodels) {
            return Retcode.NOSERVICESINCITY.msg;
        }
        return cityAddr.getCity() + "不提供" + vehiclemodels.getName() + "车型服务";
    }

    /**
     * 人工派单
     * @param orgOrder
     * @return
     */
    public Map<String, Object> manualSendOrgOrder(OrgOrder orgOrder) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgOrder order = this.dao.getOrgOrder(orgOrder.getOrderno());
            if ("8".equals(order.getOrderstatus())) {
                resultMap.put("status", "fail");
                resultMap.put("message", "人工派单超时，订单已取消");
                return resultMap;
            }
            if (!"1".equals(order.getOrderstatus())) {
                resultMap.put("status", "fail");
                resultMap.put("message", "该订单已被其他客服处理");
                return resultMap;
            }
            String chargemodel = orgOrder.getPricecopy();
            Map<String, Object> pricecopyMap = new HashMap<String, Object>();
            pricecopyMap.put("leasescompanyid", order.getCompanyid());
            pricecopyMap.put("city", order.getOncity());
            pricecopyMap.put("rulestype", order.getOrdertype());
            pricecopyMap.put("type", orgOrder.getUsetype());
            pricecopyMap.put("organid", order.getOrganid());
            if (chargemodel.equals("0")) {
                orgOrder.setPricemodel(order.getSelectedmodel());
                pricecopyMap.put("cartype", order.getSelectedmodel());
                orgOrder.setPricecopy(order.getPricecopy());
            } else {
                orgOrder.setPricemodel(orgOrder.getFactmodel());
                pricecopyMap.put("cartype", orgOrder.getFactmodel());
                orgOrder.setPricecopy(findModelPriceByModels(pricecopyMap, order.getPricecopy()));
            }
            if (StringUtils.isBlank(orgOrder.getPricecopy())) {
                resultMap.put("status", "fail");
                resultMap.put("message", "所指派服务车型计费规则不存在，建议选择“按下单车型计费”或指派其他司机");
                return resultMap;
            }
            Boolean isusenow = getOrderIsUseNow(order);
            if (isusenow == null) {
                orgOrder.setIsusenow(order.isIsusenow());
            }
            orgOrder.setIsusenow(isusenow.booleanValue());
            int orderResult = this.dao.manualSendOrder(orgOrder);
            com.szyciov.lease.entity.PubDriver driver = this.dao.getPubDriver(orgOrder.getDriverid());
            //更新车辆基本信息
            updateOrgOrderVehicleInfo(orgOrder.getOrderno(),driver.getBelongleasecompany());

            OrgSendrecord sendrecord = new OrgSendrecord();
            sendrecord.setId(GUIDGenerator.newGUID());
            sendrecord.setOrderno(orgOrder.getOrderno());
            sendrecord.setOperator(orgOrder.getOperator());
            sendrecord.setChargemodel(chargemodel);
            sendrecord.setReason(orgOrder.getOrderreason());
            sendrecord.setDriverid(orgOrder.getDriverid());
            int recordRsult = this.dao.insertOrgSendrecord(sendrecord);
            order = this.dao.getOrgOrder(orgOrder.getOrderno());

            order.setLastsendtime(StringUtil.addDate(new Date(), 20));
            addDriverTravelReminder(order);

            createDriverNews(order, 0.0D, OrderMessageFactory.OrderMessageType.MANTICORDER);
            if ((orderResult == 1) && (recordRsult == 1)) {
                OrderMessage message = new OrderMessage(order, "人工指派订单");
                MessageUtil.sendMessage(message);
                resultMap.put("status", "success");
                resultMap.put("message", "派单成功");
            } else {
                resultMap.put("status", "fail");
                resultMap.put("message", "派单失败");
            }
        } catch (Exception e) {
            resultMap.put("status", "fail");
            resultMap.put("message", "派单失败");
            resultMap.put("exmessage", e.getMessage());
        }
        return resultMap;
    }

    private String findModelPriceByModels(Map<String, Object> params, String pricecopy) {
        JSONObject json = null;
        if (StringUtils.isBlank(pricecopy)) {
            json = new JSONObject();
            json.put("cost", Integer.valueOf(0));
            json.put("mileage", Integer.valueOf(0));
            json.put("orderno", "");
            json.put("rangecost", Integer.valueOf(0));
            json.put("timecost", Integer.valueOf(0));
            json.put("times", Integer.valueOf(0));
            json.put("slowtimes", Integer.valueOf(0));
        } else {
            json = JSONObject.fromObject(pricecopy);
        }
        List<LeAccountRules> accountRuleList = this.dao.findModelPriceByModels(params);
        if ((accountRuleList != null) && (!accountRuleList.isEmpty())) {
            LeAccountRules accountRules = accountRuleList.get(0);
            json.put("rangeprice", accountRules.getRangePrice());
            json.put("startprice", accountRules.getStartPrice());
            json.put("timeprice", accountRules.getTimePrice());
            json.put("timetype", Integer.valueOf(accountRules.getTimeType()));
            json.put("deadheadmileage", accountRules.getDeadheadmileage());
            json.put("deadheadprice", accountRules.getDeadheadprice());
            json.put("nightstarttime", accountRules.getNightstarttime());
            json.put("nightendtime", accountRules.getNightendtime());
            json.put("nighteprice", accountRules.getNighteprice());
            json.put("perhour", accountRules.getPerhour());
            return json.toString();
        }
        return null;
    }

    private Boolean getOrderIsUseNow(OrgOrder order) {
        boolean isusenow = order.isIsusenow();
        if (isusenow) {
            return Boolean.valueOf(true);
        }
        GetSendInfoParam param = new GetSendInfoParam();
        param.setCompanyid(order.getCompanyid());
        param.setCity(order.getOncity());
        param.setOrderprop(0);
		param.setUsetype(order.getUsetype());

        JSONObject json = getSendRule(param);
        long carsinterval = json.getJSONObject("sendrule").getLong("carsinterval") * 60L * 1000L;
        long usetime = order.getUsetime().getTime();
        long nowtime = System.currentTimeMillis();
        if (usetime - nowtime > carsinterval) {
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    }

    /**
     * 更新订单中车辆相关信息(实际车型名称、计费车型名称、车品牌、车系、车牌号)
     * @param orderno
     */
    private void updateOrgOrderVehicleInfo(String orderno,String belongleasecompany) {
        //查询订单的相关数据
        Map<String, String> orderVehicle = getOrgOrderVehicleByOrder(orderno);

        OrgOrder order = new OrgOrder();
        order.setOrderno(orderno);
        order.setFactmodelname(orderVehicle.get("factmodelname"));
        order.setPricemodelname(orderVehicle.get("pricemodelname"));
        order.setVehcbrandname(orderVehicle.get("vehcbrandname"));
        order.setVehclinename(orderVehicle.get("vehclinename"));
        order.setPlateno(orderVehicle.get("plateno"));
        //更新订单归属车企
        order.setBelongleasecompany(belongleasecompany);
        updateOrgOrderVehicleByOrderno(order);
    }

    public Map<String, String> getOrgOrderVehicleByOrder(String orderno) {
        return this.dao.getOrgOrderVehicleByOrder(orderno);
    }

    public void updateOrgOrderVehicleByOrderno(OrgOrder object) {
        this.dao.updateOrgOrderVehicleByOrderno(object);
    }

    private void createDriverNews(OrgOrder order, double price, OrderMessageFactory.OrderMessageType type) {
        String content = "";
        String title = "";
        String newsType = "";
        if (OrderMessageFactory.OrderMessageType.REVIEWORDER.equals(type)) {
            Double orderamount = order.getShouldpayamount();
            if (orderamount == null) {
                orderamount = Double.valueOf(order.getOrderamount());
            }
            if (price > 0.0D) {
                title = "复核反馈";
                content = "订单复核完成，车费金额应为" + orderamount + "元，差额部分不计入贡献。";
            } else if (price < 0.0D) {
                title = "复核反馈";
                content = "订单复核完成，车费金额应为" + orderamount + "元。";
            } else {
                title = "复核反馈";
                content = "经核实，车费金额正确。";
            }
            newsType = UserNews.USER_NEWS_TYPE_SYSTEM;
        } else if (OrderMessageFactory.OrderMessageType.MANTICORDER.equals(type)) {
            newsType = UserNews.USER_NEWS_TYPE_ORDER;
        }
        OrderMessageFactory factory = new OrderMessageFactory(order, type, title, content);
        OrderInfoMessage orderinfo = factory.createMessage();
        UserNews userNews = new UserNews();
        userNews.setUserid(order.getDriverid());
        userNews.setType(newsType);
        userNews.setContent(orderinfo.toString());
        UserNewsParam param = new UserNewsParam();
        param.setUserNewsTbName(UserNewsParam.DRIVER_USERNEWS_TABNAME);
        param.setUserNews(userNews);
        this.userNewsService.saveUserNews(param);
    }

	
	/**
	 * 获取订单列表
	 * @param param
	 * @return
	 */
	public List<OrderInfoDetail> getOrderInfoList(OrderListParam param){
		return dao.getOrderInfoList(param);
	}

	/**
	 * 获取出租车订单列表(运管端订单)
	 * @param param
	 * @return
	 */
	public List<OrderInfoDetail> listTaxiOrderInfo(OrderListParam param){
		return dao.listTaxiOrderInfo(param);
	}


	/**
	 * 保存司机抢单推送
	 * @param param
	 * @return
	 */
	public JSONObject savePushLog(PubPushLogParam param){
		JSONObject result = new JSONObject();
		logger.info("保存司机抢单推送...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		StringBuffer sb = new StringBuffer();
		sb.append("''");
		for(String phone : param.getDriverphones()) sb.append(",'").append(phone).append("'");
		List<PubDriver> drivers = dao.getPubDriversByPhone(sb.toString());
		int expiration = 90; //90秒
		CommonRabbitData data = new CommonRabbitData();
		data.setType(CommonRabbitEnum.SEND_RULE_PUSH.code);
		data.setExpiration(expiration);
		for (PubDriver driver : drivers) {
			PubJpushlog log = new PubJpushlog();
			log.setId(GUIDGenerator.newGUID());
			log.setOrderno(param.getOrderno());
			log.setDriverid(driver.getId());
			log.setDriverphone(driver.getPhone());
			log.setSendtime(new Date());
			log.setHandtime(new Date());
			log.setRegistrationid(driver.getRegistrationid());
			log.setAndroidmsgid(param.getAndroidmsgid());
			log.setIosmsgid(param.getIosmsgid());
			log.setPushtype(PubJpushLogEnum.DRIVER_TAKEORDER_PUSHTYPE_PUBJPUSHLOG.icode);
			log.setStatus(DataStatus.OK.code);
			dao.savePubJpushlog(log);
			//添加到极光监控队列
			data.setData(log);
			rabbitService.sendCommonMsg(data);
		}
		logger.info("保存司机抢单推送完成");
		return result;
	}

    /**
     * 清除redis中订单的提醒信息
     * @param orderno
     */
    private void removeDriverTravelReminder(String orderno, String usetype) {
        JedisUtil.delKey("DRIVER_TRAVEL_REMINDER_" + orderno + "_" + usetype);
    }

    /**
     * 添加订单提醒信息到redis
     * @param order
     */
    private void addDriverTravelReminder(AbstractOrder order) {
        removeDriverTravelReminder(order.getOrderno(), order.getUsetype());
        JedisUtil.setString("DRIVER_TRAVEL_REMINDER_" + order.getOrderno() + "_" + order.getUsetype(), StringUtil.parseBeanToJSON(order));
    }

}
