package com.szyciov.driver.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.annotation.NeedRelease;
import com.szyciov.annotation.ValidateRule;
import com.szyciov.driver.base.BaseService;
import com.szyciov.driver.dao.TaxiMainFuncDao;
import com.szyciov.driver.dao.TaxiOrderFuncDao;
import com.szyciov.driver.enums.OrderListEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.OrdersortColumn;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.enums.PayUtilEnum;
import com.szyciov.driver.enums.PurseEnum;
import com.szyciov.driver.model.PassengersPayDetail;
import com.szyciov.driver.param.OrderLineParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.driver.param.PurseParam;
import com.szyciov.driver.util.PayUtil;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.DataStatus;
import com.szyciov.entity.OrderReviewParam;
import com.szyciov.entity.PayMethod;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubDriverTradingrecord;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.PubDriverAccount;
import com.szyciov.message.TaxiOrderMessage;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserExpenses;
import com.szyciov.op.entity.PubDriverExpenses;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
  * @ClassName TaxiOrderFuncService
  * @author Efy Shu
  * @Description 出租车我的订单功能Service
  * @date 2017年3月27日 14:45:27
  */ 
@Service("TaxiOrderFuncService")
public class TaxiOrderFuncService extends BaseService{
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
	/**当前订单用车类型*/
	@NeedRelease
	private ThreadLocal<OrderEnum> usetype = new ThreadLocal<OrderEnum>();
	/**当前订单订单类型*/
	@NeedRelease
	private ThreadLocal<OrderEnum> ordertype = new ThreadLocal<OrderEnum>();
	
	/**
	  *依赖
	  */
	private TaxiOrderFuncDao taxiorderfuncdao;
	private TaxiMainFuncDao taximainfuncdao;
    
	/**
	  *依赖注入
	  */
	@Resource(name="TaxiOrderFuncDao")
	public void setTaxiOrderFuncDao(TaxiOrderFuncDao taxiorderfuncdao){
		this.taxiorderfuncdao=taxiorderfuncdao;
	}
	@Resource(name="TaxiMainFuncDao")
	public void setTaxiMainFuncDao(TaxiMainFuncDao taximainfuncdao){
		this.taximainfuncdao=taximainfuncdao;
	}
	
	/**
	 * 出租车订单列表
	 * @param param
	 * @return
	 */
	public JSONObject taxiGetOrderList(OrderListParam param){
		String[] require = new String[]{};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		JSONObject result = doTaxiGetOrderList(param);
		
		return result;
	}
	
	/**
	 * 出租车今日订单列表
	 * @param param
	 * @return
	 */
	public JSONObject taxiGetTodayOrderList(OrderListParam param){
		//设置今日范围
		Date starttime = new Date();
		Date endtime = StringUtil.getTomorrow(starttime);
		param.setToday(true);
		param.setStarttime(StringUtil.formatDate(starttime,StringUtil.TIME_WITH_DAY));  //今天
		param.setEndtime(StringUtil.formatDate(endtime,StringUtil.TIME_WITH_DAY)); //明天
		String[] require = new String[]{};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		JSONObject result = doTaxiGetOrderList(param);
		
		return result;
	}
	
	/**
	 * 出租车获取服务中订单
	 * @param param
	 * @return
	 */
	public JSONObject taxiGetCurrentOrder(OrderListParam param){
		String[] require = new String[]{};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		JSONObject result = doTaxiGetCurrentOrder(param);
		
		return result;
	}
	
	/**
	 * 出租车订单详情
	 * @param param
	 * @return
	 */
	public JSONObject taxiGetOrderDetail(OrderApiParam param){
		String[] require = new String[]{"usetype","ordertype","orderno"};
		if(!checkeParam(param,getExceptElement(param, require)) || order.get() == null) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		JSONObject result = doTaxiGetOrderDetail(param);
		
		return result;
	}
	
	/**
	 * 转为线上支付
	 * @param param
	 * @return
	 */
	public JSONObject changeToOnlinePay(OrderApiParam param){
		String[] require = new String[]{"usetype","ordertype","orderno"};
		if(!checkeParam(param,getExceptElement(param, require)) || order.get() == null) {
			return errorResult.get();
		}
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		if(!doChangeToOnlinePay(param)) return errorResult.get();
		
		JSONObject result = new JSONObject();
		
		return result;
	}
	
	/**
	 * 订单确费
	 * @return
	 */
	public JSONObject confirmCost(OrderApiParam param){
		String[] require = new String[]{"usetype","ordertype","orderno","orderstatus","rangecost","paymethod"};
		if(!checkeParam(param,getExceptElement(param, require)) || order.get() == null) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		//执行订单确费逻辑,失败返回失败信息
		if(!doConfirmCost(param)) return errorResult.get();
		
		JSONObject result = new JSONObject();
		
		return result;
	}
	
	/**
	 * 订单付结
	 * @param param
	 * @return
	 */
	public JSONObject payOrder(PurseParam param){
		String[] require = new String[]{"usetype","ordertype","orderno","paymethod","paystate"};
		if(!checkeParam(param,getExceptElement(param, require)) || order.get() == null) {
			return errorResult.get();
		}

		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		//保存订单金额
		param.setAmount(order.get().getOrderamount());
		//付结失败,返回错误信息
		if(!doPayOrder(param)) return errorResult.get();
		JSONObject result = new JSONObject();
		result.put("payinfo", payinfo.get());
		
		return result;
	}
	
	/**
	 * 出租车抢单
	 * @param param
	 * @return
	 */
	public JSONObject taxiTakingOrder(OrderApiParam param){
		//这里用orderid是为了使用不同的校验规则
		String[] require = new String[]{"usetype","ordertype","orderid","orderstatus","cantakesign"};
		if(!checkeParam(param,getExceptElement(param, require)) || order.get() == null) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		//执行抢单逻辑失败,返回错误信息
		if(!doTaxiTakingOrder(param)) {
			return errorResult.get();
		}
		JSONObject result = new JSONObject();
		result.put("orderno", order.get().getOrderno());
		result.put("isusenow", order.get().isIsusenow());
		result.put("usetype", order.get().getUsetype());
		result.put("ordertype", order.get().getOrdertype());
		return result;
	}
	
	/**
	 * 出租车改变订单状态
	 * @param param
	 * @return
	 */
	public JSONObject taxiChangeOrderState(OrderApiParam param){
		String[] require = new String[]{"usetype","ordertype","orderno","orderstatus"};
		//如果是出发接人,需要检查是否存在更早的未出行订单
		if(OrderState.START.state.equals(param.getOrderstate())){
			require = new String[]{"usetype","ordertype","orderno","orderstatus","hasearlierorder","notpayorderlimit"};
		}
		if(!checkeParam(param,getExceptElement(param, require)) || order.get() == null) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		//执行改变订单状态逻辑失败,返回错误信息
		if(!doTaxiChangeOrderState(param)) {
			return errorResult.get();
		}
				
		JSONObject result = new JSONObject();
		result.put("orderstatus", order.get().getOrderstatus());
		result.put("orderno", order.get().getOrderno());
		result.put("usetype", order.get().getUsetype());
		result.put("ordertype", order.get().getOrdertype());
		
		
		return result;
	}
	
	/**
	 * 出租车行程提醒
	 * @param param
	 * @return
	 */
	public JSONObject taxiOrderRemind(OrderApiParam param){
		String[] require = new String[]{"usetype","ordertype","orderno"};
		if(!checkeParam(param,getExceptElement(param, require)) || order.get() == null) {
			return errorResult.get();
		}
		
		JSONObject result = new JSONObject();
		if(!doTaxiOrderRemind(param)) {
			return errorResult.get();
		}
		return result;
	}
	
	/**
	 * 出租车获取轨迹线
	 * @param param
	 * @return
	 */
	public JSONObject taxiGetOrderLine(OrderLineParam param){
		String[] require = new String[]{"usetype","ordertype","orderno"};
		if(!checkeParam(param,getExceptElement(param, require)) || order.get() == null) {
			return errorResult.get();
		}
		
		JSONObject result = doTaxiGetOrderLine(param);
		
		return result;
	}
	
	/**
	 * 出租车订单复核
	 * @param param
	 * @return
	 */
	public JSONObject taxiApplyForReview(OrderReviewParam param){
		String[] require = new String[]{"usetype","ordertype","orderno"};
		if(!checkeParam(param,getExceptElement(param, require)) || order.get() == null) {
			return errorResult.get();
		}
		
		JSONObject result = new JSONObject();
		if(!doTaxiApplyForReview(param)) {
			return errorResult.get();
		}
		
		return result;
	}
	
	
	/**********************************************************内部方法***************************************************************/
	/**
	 * 转为线上支付逻辑
	 * @param param
	 * @return
	 */
	private boolean doChangeToOnlinePay(OrderApiParam param){
		logger.info("出租车转为线上支付...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		//个人订单就是运管平台
		if(OrderEnum.USETYPE_PERSONAL.code.equals(param.getUsetype())){
			OpTaxiOrder taxiOrder = (OpTaxiOrder) order.get();
			taxiOrder.setPaymentmethod(PayMethod.ONLINE.code);
			taxiorderfuncdao.updateTaxiOrder(taxiOrder);
		//否则租赁平台
//		}else{
//			OrgTaxiOrder taxiOrder = (OrgTaxiOrder) order.get();
//			taxiOrder.setPaymentmethod(PayMethod.ONLINE.code);
//			taxiorderfuncdao.updateTaxiOrder(taxiOrder);
		}
		logger.info("出租车转为线上支付完成");
		return true;
	}
	
	/**
	 * 出租车改变行程提醒逻辑
	 * @param param
	 * @return
	 */
	private boolean doTaxiOrderRemind(OrderApiParam param){
		logger.info("出租车改变行程提醒...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		if(!param.isRemind()){
			JSONObject result = new JSONObject();
			result = templateHelper.dealRequestWithFullUrlToken(
					carserviceApiUrl+"/OrderApi/CancelOrderRemind", 
					HttpMethod.POST, 
					null,
					param,
					JSONObject.class);
			if(result.getInt("status") != Retcode.OK.code){
				logger.info("出租车取消行程提醒失败");
				errorResult.set(result);
				return false;
			}
		}
		logger.info("出租车改变行程提醒完成");
		return true;
	}
	
	/**
	 * 出租车获取订单轨迹
	 * @param param
	 * @return
	 */
	private JSONObject doTaxiGetOrderLine(OrderLineParam param){
		logger.info("出租车获取订单轨迹...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		param.setStartDate(order.get().getStarttime());
		param.setEndDate(order.get().getEndtime());
		JSONObject result = new JSONObject();
		result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl + "/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}",
				HttpMethod.GET, 
				param.getToken(), 
				null, 
				JSONObject.class,
				param.getOrderno(),
				param.getOrdertype(),
				param.getUsetype());
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info("出租车获取订单轨迹失败");
			errorResult.set(result);
			return result;
		}
		result.put("message",Retcode.OK.msg);
		result.remove("duration");
		logger.info("出租车获取订单轨迹完成");
		return result;
	}
	
	/**
	 * 出租车订单复核
	 * @param param
	 * @return
	 */
	private boolean doTaxiApplyForReview(OrderReviewParam param){
		logger.info("出租车订单复核...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		//个人订单查运管表
		if(OrderEnum.USETYPE_PERSONAL.code.equals(usetype.get().code)){
			OpTaxiOrder taxiOrder = (OpTaxiOrder) order.get();
			taxiOrder.setReviewperson(OrderEnum.REVIEWPERSON_DRIVER.code);
			taxiOrder.setReviewstatus(OrderEnum.REVIEWSTATUS_WAITREVIEW.code);
			taxiOrder.setOrderreason(param.getReason());
			taxiorderfuncdao.updateTaxiOrder(taxiOrder);
//		}else{
//			OrgTaxiOrder taxiOrder = (OrgTaxiOrder) order.get();
//			taxiOrder.setReviewperson(OrderEnum.REVIEWPERSON_DRIVER.code);
//			taxiOrder.setReviewstatus(OrderEnum.REVIEWSTATUS_WAITREVIEW.code);
//			taxiOrder.setOrderreason(param.getReason());
//			taxiorderfuncdao.updateOrgTaxiOrder(taxiOrder);
		}
		
		logger.info("出租车订单复核完成");
		return true;
	}
	
	/**
	 * 改变订单状态逻辑
	 * @param param
	 * @return
	 */
	private boolean doTaxiChangeOrderState(OrderApiParam param){
		logger.info("改变订单状态开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		OrderState paramState = OrderState.getByCode(param.getOrderstate());
		boolean flag = false;
		if(OrderState.START.equals(paramState)){  //开始服务
			flag =  doDriverStart(param);
		}else if (OrderState.ARRIVAL.equals(paramState)) {  //已抵达
			flag =  doDriverArrival(param);
		}else if (OrderState.INSERVICE.equals(paramState)) {  //开始服务
			flag =  doDriverInservice(param);
		}else if (OrderState.WAITMONEY.equals(paramState)) {  //待确费
			flag =  doDriverWaitMoney(param);
		}
		if(!flag){
			logger.info("改变订单状态失败...");
			//特殊返回码需要直接返回
			if (!errorResult.get().isEmpty() && errorResult.get().getInt("status") != Retcode.FAILED.code) return false;
			errorResult.get().put("status",Retcode.FAILED.code);
			errorResult.get().put("message","改变订单状态失败");
			return false;
		}
		order.get().setOrderstatus(paramState.state);
		//推送给相关人
		sendMessage4Order(null);
		logger.info("改变订单状态完成");
		return flag;
	}
	
	/**
	 * 执行司机已出发流程
	 * @return
	 */
	private boolean doDriverStart(OrderApiParam param){
		logger.info("司机出发流程开始...");
		OpTaxiOrder taxiOrder = (OpTaxiOrder) order.get();
		//解析司机当前地址
		JSONObject result = getAddress(param);
		//如果地址解析失败,返回失败
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info(result.toString());
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "地址解析失败");
			return false;
		}
		//改变订单属性
		String departurecity = result.getString("city");
		String departureaddress = result.getString("address");
		taxiOrder.setOrderstatus(OrderState.START.state);
		taxiOrder.setDeparturetime(new Date());
		taxiOrder.setDeparturelat(driver.get().getLat());
		taxiOrder.setDeparturelng(driver.get().getLng());
		taxiOrder.setDeparturecity(departurecity);
		taxiOrder.setDepartureaddress(departureaddress);
		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.START.state));
		//更新订单
		taxiorderfuncdao.updateTaxiOrder(taxiOrder);
		//司机状态变更为服务中
		driver.get().setWorkstatus(DriverEnum.WORK_STATUS_SERVICE.code);
		accdao.updatePubDriver(driver.get());
		logger.info("订单:"+ order.get().getOrderno() +",司机已出发.");
		return true;
	}
	
	/**
	 * 执行司机已抵达流程
	 * @return
	 */
	private boolean doDriverArrival(OrderApiParam param){
		logger.info("司机抵达流程开始...");
		//解析司机当前地址
		JSONObject result = getAddress(param);
		//如果地址解析失败,返回失败
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info(result.toString());
			return false;
		}
		//改变订单属性
		String departurecity = result.getString("city");
		String departureaddress = result.getString("address");
		OpTaxiOrder taxiOrder = (OpTaxiOrder) order.get();
		taxiOrder.setOrderstatus(OrderState.ARRIVAL.state);
		taxiOrder.setArrivaltime(new Date());
		taxiOrder.setArrivallat(driver.get().getLat());
		taxiOrder.setArrivallng(driver.get().getLng());
		taxiOrder.setArrivalcity(departurecity);
		taxiOrder.setArrivaladdress(departureaddress);
		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.ARRIVAL.state));
		//更新订单
		taxiorderfuncdao.updateTaxiOrder(taxiOrder);
		logger.info("订单:"+ order.get().getOrderno() +",司机已抵达.");
		return true;
	}
	/**
	 * 执行司机开始服务流程
	 * @return
	 */
	private boolean doDriverInservice(OrderApiParam param){
		logger.info("司机开始服务流程开始...");
		//解析司机当前地址
		JSONObject result = getAddress(param);
		//如果地址解析失败,返回失败
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info(result.toString());
			return false;
		}
		OpTaxiOrder taxiOrder = (OpTaxiOrder) order.get();
		taxiOrder.setOrderstatus(OrderState.INSERVICE.state);
		taxiOrder.setStarttime(new Date());
		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.INSERVICE.state));
		// 新增 开始服务地址城市  开始服务地址  开始服务地址经度 开始服务地址纬度
		taxiOrder.setStartcity(result.getString("city"));
		taxiOrder.setStartaddress(result.getString("address"));
		taxiOrder.setStartlng(driver.get().getLng());
		taxiOrder.setStartllat(driver.get().getLat());
		//更新订单
		taxiorderfuncdao.updateTaxiOrder(taxiOrder);
		saveOrUpdateInserviceOrder(taxiOrder,driver.get(),false);
		logger.info("订单:"+ order.get().getOrderno() +",司机开始服务.");
		return true;
	}
	/**
	 * 执行司机服务结束流程(服务结束进入待确费流程)
	 * @return
	 */
	private boolean doDriverWaitMoney(OrderApiParam param){
		logger.info("司机服务结束流程开始...");
		//解析司机当前地址
		JSONObject result = getAddress(param);
		//如果地址解析失败,返回失败
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info(result.toString());
			return false;
		}
		OpTaxiOrder taxiOrder = (OpTaxiOrder) order.get();
		taxiOrder.setOrderstatus(OrderState.WAITMONEY.state);
		taxiOrder.setEndtime(new Date());
		String priceCopy = taxiOrder.getPricecopy();

		if(priceCopy != null && !priceCopy.isEmpty()){
            JSONObject priceCopyObj = JSONObject.fromObject(priceCopy);
            if(priceCopyObj.get("mileage") != null){
                taxiOrder.setMileage(priceCopyObj.getDouble("mileage"));
            }
        }

		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.WAITMONEY.state));
		// 新增 开始服务地址城市  开始服务地址  开始服务地址经度 开始服务地址纬度
		taxiOrder.setEndcity(result.getString("city"));
		taxiOrder.setEndaddress(result.getString("address"));
		taxiOrder.setEndlng(driver.get().getLng());
		taxiOrder.setEndllat(driver.get().getLat());
		//更新订单
		taxiorderfuncdao.updateTaxiOrder(taxiOrder);
		saveOrUpdateInserviceOrder(taxiOrder,driver.get(),true);
		logger.info("订单:"+ order.get().getOrderno() +",司机服务结束,待确费.");
		return true;
	}
	
	/**
	 * 出租车抢单逻辑
	 * @param param
	 * @return
	 */
	private boolean doTaxiTakingOrder(OrderApiParam param){
		logger.info("订单抢单开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		OpTaxiOrder taxiOrder = (OpTaxiOrder) order.get();
		taxiOrder.setOrderstatus(OrderState.WAITSTART.state);
		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.WAITSTART.state));
		taxiOrder.setOrdertime(new Date());
		taxiOrder.setPaymentstatus(PayState.ALLNOPAY.state);
		taxiOrder.setCompanyid(driver.get().getLeasescompanyid());
		taxiOrder.setDriverid(driver.get().getId());
		taxiOrder.setVehicleid(driver.get().getVehicleid());
		taxiOrder.setPlateno(driver.get().getPlateno());
		taxiOrder.setVehcbrandname(driver.get().getVehcbrandname());
		taxiOrder.setVehclinename(driver.get().getVehclinename());
		taxiOrder.setBelongleasecompany(driver.get().getBelongleasecompany());
		taxiorderfuncdao.taxiTakingOrder(taxiOrder);
		//抢单是一个事务方法,无法得知影响行数
		//因此需要再查一次库,判断状态是否改变
		order.set(taxiorderfuncdao.getOpTaxiOrder(param));
		//反查订单为当前司机,则表示抢单成功,否则抢单失败
		if(order.get() == null){
			logger.info("抢单失败,订单已被抢");
			errorResult.get().put("status", Retcode.ORDERISGONE.code);
			errorResult.get().put("message", Retcode.ORDERISGONE.msg);
			return false;
		}else{//抢单成功需要解锁该订单所占用的司机资源
			//发送推送和短信给司机&乘客&下单人(如果与乘车人不同)
			if(!sendMessage4Order(null)) return false;
			removeDriverMessage(param);
		}
		logger.info("订单抢单完成");
		return true;
	}
	
	/**
	 * 抢单成功清除redis中的抢单信息
	 * @return
	 */
	private boolean removeDriverMessage(OrderApiParam param){
		String key = "DriverGrabMessage_*_*_" + param.getOrderno();
		Set<String> keys = JedisUtil.getKeys(key);
		List<String> phones = new ArrayList<>();
		for(String k : keys){
			JedisUtil.delKey(k);
			//剔除当前司机
			if(driver.get().getPhone().equals(k.split("_")[2])) continue;
			phones.add(k.split("_")[2]);
		}
		if(!phones.isEmpty()){
			//给被删除消息的司机发送通知
			sendMessage4Order(phones);
		}
		return true;
	}
	
	/**
	 * 保存司机服务中订单
	 * @param oid
	 * @param pd
	 */
	private void saveOrUpdateInserviceOrder(OpTaxiOrder order,PubDriver pd,boolean isupdate){
		String key = "OBD_MILGES_CURRENT_ORDER_"+pd.getId()+"_"+order.getOrderno();
		try{
			JSONObject json = new JSONObject();
			json.put("orderno", order.getOrderno());
			json.put("driverid", pd.getId());
			json.put("vehicleid", pd.getVehicleid());
			json.put("starttime", StringUtil.formatDate(order.getStarttime(), StringUtil.DATE_TIME_FORMAT));
			json.put("orderstatus", order.getOrderstatus());
			if(isupdate){
				int second = 60 * 10; //十分钟
				json.put("endtime", StringUtil.formatDate(order.getEndtime(), StringUtil.DATE_TIME_FORMAT));
				JedisUtil.delKey(key);
				JedisUtil.setString(key, json.toString(),second);
			}else{
				JedisUtil.setString(key, json.toString());
			}
		}catch(Exception e){
			logger.error("保存OBD轨迹失败",e);
			JedisUtil.delKey(key);
		}
	}
	
	/**
	 * 订单付结逻辑
	 * @param param
	 * @return
	 */
	private boolean doPayOrder(PurseParam param){
		logger.info("订单付结开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		if(PayMethod.BALANCE.code.equals(param.getPaymethod())){            //如果是余额支付
			if(!doBalancePay(param)) return false;
		}else if(PayMethod.WECHAT.code.equals(param.getPaymethod())){    //如果是微信支付
			if(!doWeChatPay(param)) return false;
		}else if (PayMethod.ALIPAY.code.equals(param.getPaymethod())) {     //如果是支付宝支付
			if(!doAliPay(param)) return false;
		}
		logger.info("订单付结完成");
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
		tradingrecord.setType(Integer.valueOf(PayUtilEnum.PAYORDER.code));
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
	 * 余额支付
	 * @param param
	 * @return
	 */
	private boolean doBalancePay(PurseParam param){
		logger.info("使用余额支付...");
		PubDriverAccount purse = taximainfuncdao.getPurse(driver.get());
		OpTaxiOrder taxiOrder = (OpTaxiOrder)order.get();
		if(purse.getBalance() < taxiOrder.getOrderamount()){ //余额不足打回
			logger.info("订单付结失败,余额不足");
			errorResult.get().put("status", Retcode.NOTENOUGHBALANCE.code);
			errorResult.get().put("message", Retcode.NOTENOUGHBALANCE.msg);
			return false;
		}
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
		//更新司机余额
		purse.setBalance(purse.getBalance() - taxiOrder.getOrderamount());
		taximainfuncdao.saveOrUpdatePubDriverAccount(purse);
		//还需要添加支付明细
		param.setAmount(taxiOrder.getOrderamount());
		param.setDetailtype(PurseEnum.DETAILTYPE_BALANCE.code);
		param.setPaymethod(PurseEnum.EXPENSETYPE_BALANCE.code);
		doSavePurseDetail(purse,param,PurseEnum.TRADETYPE_SETTLE);
		
		payinfo.set(new JSONObject());
		logger.info("余额支付完成");
		return true;
	}
	
	/**
	 * 微信支付
	 * @return
	 */
	private boolean doWeChatPay(PurseParam param){
		logger.info("使用微信支付...");
		PayUtil pu = new PayUtil(param.getIpaddr(),PayUtilEnum.PAYORDER,order.get().getOrderno());
		String appid = null,privateKey = null,shno = null;
		//根据订单归属平台不同获取不同的收款信息
		if(usetype.get() != null && !OrderEnum.USETYPE_PERSONAL.equals(usetype.get())){
			appid = leasescompany.get().getDriverwechatappid();
			privateKey = leasescompany.get().getDriverwechatsecretkey();
			shno = leasescompany.get().getDriverwechatmerchantno();
		}else if(usetype.get() != null && OrderEnum.USETYPE_PERSONAL.equals(usetype.get())){
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
		PayUtil pu = new PayUtil(param.getIpaddr(),PayUtilEnum.PAYORDER,order.get().getOrderno());
		String appid = null,publicKey = null,privateKey = null;
		//根据订单归属平台不同获取不同的收款信息
		if(usetype.get() != null && !OrderEnum.USETYPE_PERSONAL.equals(usetype.get())){
			appid = leasescompany.get().getDriveralipayappid();
			publicKey = leasescompany.get().getDriveralipaypublickey();
			privateKey = leasescompany.get().getDriveralipayprivatekey();
		}else if(usetype.get() != null && OrderEnum.USETYPE_PERSONAL.equals(usetype.get())){
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
	 * 订单确费逻辑
	 * @param param
	 * @return
	 */
	private boolean doConfirmCost(OrderApiParam param){
		logger.info("订单确费开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		OpTaxiOrder taxiOrder = (OpTaxiOrder)order.get();
		taxiOrder.setShouldpayamount(param.getRangecost());
		taxiOrder.setActualpayamount(param.getRangecost());
		taxiOrder.setOriginalorderamount(param.getRangecost());
		taxiOrder.setOrderamount(param.getRangecost());
		taxiOrder.setPaymentmethod(param.getPaymethod());
		taxiOrder.setPaymenttime(new Date());
		taxiOrder.setOrderstatus(OrderState.SERVICEDONE.state); //确费流程结束后改为完成
		//改变订单支付状态(逻辑太长,拆分)
		changeOrderPayStatus(taxiOrder,param);
		//暂时屏蔽返点
//		if(changeOrderPayStatus(taxiOrder,param)) doAwardPoint(taxiOrder,param);
		taxiorderfuncdao.updateTaxiOrder(taxiOrder);
		//更新司机状态为空闲
		driver.get().setWorkstatus(DriverEnum.WORK_STATUS_LEISURE.code);
		//累积完成订单数+1
		driver.get().setOrdercount(driver.get().getOrdercount() + 1);
		accdao.updatePubDriver(driver.get());

		if(PayMethod.OFFLINE.code.equals(param.getPaymethod())){
            savePeUserExpenses(param);
        }

		//推送给相关人
		sendMessage4Order(null);
		logger.info("订单确费完成");
		return true;
	}

	/**
	 * 出租车订单详情逻辑
	 * @return
	 */
	private JSONObject doTaxiGetOrderDetail(OrderApiParam param){
		logger.info("查询出租车订单详情...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONObject result = new JSONObject();
		OpTaxiOrder taxiOrder = (OpTaxiOrder)order.get();
		JSONObject temp = convertOrder(taxiOrder);
		temp.put("onlat", taxiOrder.getOnaddrlat());
		temp.put("onlng", taxiOrder.getOnaddrlng());
		temp.put("offlat", taxiOrder.getOffaddrlat());
		temp.put("offlng", taxiOrder.getOffaddrlng());
		result.put("order",temp);
		logger.info("查询出租车订单详情完成");
		return result;
	}
	
	/**
	 * 出租车订单列表逻辑
	 * @param param
	 * @return
	 */
	private JSONObject doTaxiGetOrderList(OrderListParam param){
		logger.info("查询出租车订单列表...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONObject result = new JSONObject();
		List<OpTaxiOrder> list = taxiorderfuncdao.getTaxiOrderList(param);
		JSONArray array = new JSONArray();
		for(OpTaxiOrder oo : list){
			array.add(convertOrder(oo));
		}
		int totalcount = taxiorderfuncdao.getTaxiOrderListTotalCount(param);
		result.put("totalcount", totalcount);
		result.put("count", array.size());
		result.put("orders", array);
		logger.info("查询出租车订单列表完成");
		return result;
	}
	
	/**
	 * 出租车获取服务中订单
	 * @param param
	 * @return
	 */
	private JSONObject doTaxiGetCurrentOrder(OrderListParam param){
		logger.info("查询出租车服务中订单...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		List<OpTaxiOrder> list = taxiorderfuncdao.getTaxiOrderList(param);
		if(list !=null && !list.isEmpty()) {
			order.set(list.get(0));
		}else {
			order.remove();
		}
		//准备返回参数
		JSONObject result = new JSONObject();
		String orderno = "",orderstatus = "",paymethod = "",usetype = "", ordertype = "";
		if(order.get() != null){
			OpTaxiOrder taxiOrder = (OpTaxiOrder)order.get();
			orderno = taxiOrder.getOrderno();
			orderstatus = taxiOrder.getOrderstatus();
			paymethod = taxiOrder.getPaymentmethod();
			usetype = taxiOrder.getUsetype();
			ordertype = taxiOrder.getOrdertype();
		}
		result.put("orderno", orderno);
		result.put("orderstatus", orderstatus);
		result.put("paymethod", paymethod);
		result.put("usetype", usetype);
		result.put("ordertype", ordertype);
		logger.info("查询出租车服务中订单完成");
		return result;
	}
	
	/**
	 * 保存交易明细
	 * @param param       保证detailtype和paymethod有值
	 * @param tradetype  交易类型
	 * @return
	 */
	private boolean doSavePurseDetail(PubDriverAccount purse,PurseParam param,PurseEnum tradetype){
		logger.info("保存交易明细开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param) + "-->" + tradetype.code);
		PubDriverExpenses pde = new PubDriverExpenses();
		pde.setAmount(param.getAmount());
		pde.setBalance(purse.getBalance());
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
		pde.setOperateresult(PurseEnum.OPERATERESULT_SUCCESS.code);
		taximainfuncdao.savePubDriverExpenses(pde);

		logger.info("保存交易明细完成");
		return true;
	}
	
	/**
	 * 积分返点
	 * @param oid
	 */
	@SuppressWarnings("unused")
	private void doAwardPoint(OpTaxiOrder order,OrderApiParam param){
		try{
			Map<String, Object> map = new HashMap<>();
			map.put("usertype", "1");
			PeUser user = accdao.getPeUserById(order.getUserid());
			map.put("userphone", user.getAccount());
			map.put("passengerphone", order.getPassengerphone());
			map.put("money", order.getOrderamount());
			JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl + "/AwardPassendger/AwardPoint", 
				HttpMethod.POST, 
				param.getToken(), 
				map, 
				JSONObject.class
			);
			logger.info("积分返点结果:"+result.toString());
		}catch(Exception e){
			logger.error("积分返点出错.",e);
		}
	}
	
	/**
	 * 解析司机当前位置
	 * @param param
	 * @return
	 */
	private JSONObject getAddress(OrderApiParam param){
		//解析司机当前地址
		BaiduApiQueryParam baqp = new BaiduApiQueryParam();
		baqp.setOrderStartLat(driver.get().getLat());
		baqp.setOrderStartLng(driver.get().getLng());
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
			carserviceApiUrl + "/BaiduApi/GetAddress", 
			HttpMethod.POST, 
			param.getToken(), 
			baqp, 
			JSONObject.class
		);
		return result;
	}
	
	/**
	 * 转换Order为返回对象
	 * @param oo
	 * @return
	 */
	private JSONObject convertOrder(OpTaxiOrder oo){
		Map<String,Object> od = new HashMap<>();
		if(oo == null) return JSONObject.fromObject(od);
		String passengers = (oo.getPassengers() == null || oo.getPassengers().trim().isEmpty()) ? "佚名" : oo.getPassengers();
		od.put("orderno", oo.getOrderno());
		od.put("onaddress", oo.getOnaddress());
		od.put("offaddress", oo.getOffaddress());
		od.put("usetime", oo.getUsetime());
		od.put("passengers", passengers);
		od.put("passengerphone", oo.getPassengerphone());
		od.put("passengericonmin", oo.getPassengericonmin() == null ? "" : fileServer + oo.getPassengericonmin());
		od.put("passengericonmax", oo.getPassengericonmax() == null ? "" : fileServer + oo.getPassengericonmax());
		od.put("lasttime", StringUtil.formatOrderStatus(oo.getUsetime(),oo.getOrderstatus()));
		od.put("rangecost", StringUtil.formatNum(oo.getOrderamount(), 1)+"");
		od.put("remark", oo.getTripremark());
		od.put("status", oo.getOrderstatus());
		od.put("paymethod", oo.getPaymentmethod());
		od.put("paystatus", StringUtil.formatTaxiPayStatus(oo.getPaymentstatus(), oo.getOrderstatus()));
		od.put("isusenow", oo.isIsusenow());
		od.put("canceltime", oo.getCanceltime());
		od.put("contact", oo.getContact());
		od.put("usetype", oo.getUsetype());
		od.put("ordertype", oo.getOrdertype());
		return JSONObject.fromObject(od);
	}
	
	/**
	 * 保存乘客付现明细
	 * @param param
	 */
	private void savePeUserExpenses(OrderApiParam param){
        logger.info("保存乘客付现明细开始...");
        // 保存乘客付现明细
        String orderNo = param.getOrderno();
        PassengersPayDetail order = taximainfuncdao.getOpTaxiOrderByOrderNo(orderNo);

        if(order != null){
            PeUserExpenses peExpenses =  new PeUserExpenses();
            peExpenses.setId(GUIDGenerator.newGUID());
            peExpenses.setUserid(order.getUserId());
            peExpenses.setAmount(new BigDecimal(param.getRangecost()));
            peExpenses.setBalance(new BigDecimal(order.getBalance()));
            peExpenses.setCreater(driver.get().getId());
            peExpenses.setCreatetime(new Date());
            peExpenses.setDetailtype(PurseEnum.DETAILTYPE_ALLTRANS.code);
            peExpenses.setTradetype(PurseEnum.TRADETYPE_SETTLE.code);
            peExpenses.setExpensetime(new Date());
            peExpenses.setExpensetype(PurseEnum.TRADETYPE_CASH.code);
            peExpenses.setRemark("");
            peExpenses.setUpdater(driver.get().getId());
            peExpenses.setUpdatetime(new Date());
            peExpenses.setStatus(DataStatus.OK.code+"");
            peExpenses.setOperateresult(PurseEnum.OPERATERESULT_SUCCESS.code);
            taximainfuncdao.savePeUserExpenses(peExpenses);
        }
        logger.info("保存乘客付现明细结束...");
    }
	
	/**
	 * 根据订单信息改写支付状态
	 * @param taxiOrder
	 * @param param
	 * @return 是否需要返点
	 */
	private boolean changeOrderPayStatus(OpTaxiOrder taxiOrder,OrderApiParam param){
		//如果是线下支付
		if(PayMethod.OFFLINE.code.equals(param.getPaymethod())){
			//如果调度费不为0,改为乘客未付
			if(taxiOrder.getSchedulefee() > 0){
				taxiOrder.setPaymentstatus(PayState.PASSENGERNOPAY.state);
				taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.NOPAYEND.state));
			//如果调度费为0,改为已结算(司机已收现,不再需要转给公司)
			}else{
				taxiOrder.setPaymentstatus(PayState.STATEMENTED.state);
				taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.STATEMENTED.state));
				return true;
			}
		//如果是线上支付
		}else{ 
			//如果行程费和调度费都为0,则表示免单,改为已支付
			if (param.getRangecost() == 0 && taxiOrder.getSchedulefee() == 0) {
				taxiOrder.setPaymentstatus(PayState.PAYED.state);
				taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.PAYED.state));
			//如果是线上支付,订单支付状态需要改为未支付
			}else{
				taxiOrder.setPaymentstatus(PayState.NOTPAY.state);
				taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.NOTPAY.state));
			}
		}
		return false;
	}
	
	/**
	 * 订单状态变更给司机和乘客发送推送
	 */
	private boolean sendMessage4Order(List<String> phones){
		String messagetype = null;
		if(OrderState.WAITSTART.state.equals(order.get().getOrderstatus()) && phones == null){
			messagetype = TaxiOrderMessage.TAKEORDER;
		}else if(!OrderState.WAITSTART.state.equals(order.get().getOrderstatus()) && phones == null){
			messagetype = TaxiOrderMessage.TAXI_ORDERHINT;
		}else{
			messagetype = TaxiOrderMessage.TAXI_DRIVERMESSAGE;
		}
		TaxiOrderMessage om = null;
		//如果推送手机号为空,表示是抢单推送,推送给相关人
		if(phones == null){
			om = new TaxiOrderMessage(order.get(),messagetype);
			//推送给其他之前推送过抢单消息的司机
		}else{
			om = new TaxiOrderMessage(messagetype,phones);
		}
		MessageUtil.sendMessage(om);
		return true;
	}
	/**********************************************************校验方法***************************************************************/
	/**
	 * 校验订单列表类型
	 * @param type
	 * @return
	 */
	@ValidateRule(msg="订单列表类型不正确")
	private boolean checkOrderListType(int type){
		return OrderListEnum.DEFAULT.state == type ? true :
					OrderListEnum.CURRENT.state == type ? true : 
					OrderListEnum.WAITPAY.state == type ? true : 
					OrderListEnum.COMPLETE.state == type ? true : false;
	}
	
	/**
	 * 校验司机是否可以抢单(已经有即刻单不能再抢,预约订单不能抢同一天的)
	 * @return
	 */
	@ValidateRule(msg="可抢订单数已超限")
	private boolean checkDriverCanTakeOrder(boolean cantakesign){
		OrderListParam param = new OrderListParam();
		param.setDriverid(driver.get().getId());
		param.setType(OrderListEnum.CURRENT.state);
		List<OpTaxiOrder> list = taxiorderfuncdao.getTaxiOrderList(param);
		for(OpTaxiOrder taxiOrder : list){
			Date takeOrderDate = StringUtil.getToday(order.get().getUsetime());
			Date exitOrderDate = StringUtil.getToday(taxiOrder.getUsetime());
			//如果存在即刻单并且要抢订单也是即刻 或 存在与要抢订单是同一天的预约订单,就不能抢
			if((taxiOrder.isIsusenow() && order.get().isIsusenow()) 
				|| (!taxiOrder.isIsusenow()&&!order.get().isIsusenow()&&takeOrderDate.getTime() == exitOrderDate.getTime())){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 校验订单号格式(抢单不传入司机id)
	 * @param orderno
	 * @return
	 */
	@ValidateRule(msg="订单号格式不正确")
	private boolean checkOrder4Taking(String orderno){
		if(orderno == null || orderno.trim().isEmpty()) return false;
		//判断是否传入了用车类型和订单类型
		if(usetype.get() == null || ordertype.get() == null) {
			errorResult.get().put("status", Retcode.ORDERNOTEXIT.code);
			errorResult.get().put("message", "订单信息不足");
			return false;
		}
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
	 * 校验订单号格式
	 * @param orderno
	 * @return
	 */
	@ValidateRule(msg="订单号格式不正确")
	private boolean checkOrderNO(String orderno){
		order.set(null);
		//根据平台不同获取不同平台订单
		if(OrderEnum.USETYPE_PERSONAL.equals(usetype.get())){
			OrderApiParam oap = new OrderApiParam();
			oap.setOrderno(orderno);
			oap.setDriverid(driver.get().getId());
			order.set(taxiorderfuncdao.getOpTaxiOrder(oap));	  //目前只有运管端有出租车
//		}else{
//			OrderApiParam oap = new OrderApiParam();
//			oap.setOrderno(orderno);
//			oap.setDriverid(driver.get().getId());
//			order.set(taxiorderfuncdao.getOrgTaxiOrder(oap));	//目前只有运管端有出租车
		}
		if(order.get() == null){
			errorResult.get().put("status", Retcode.ORDERNOTEXIT.code);
			errorResult.get().put("message", Retcode.ORDERNOTEXIT.msg);
		}
		return order.get() != null;
	}
	
	@ValidateRule(msg="金额不正确")
	private boolean checkAmount(double amount){
		return amount >= 0 && (amount+"").matches("\\d+\\.\\d{1}");
	}
	
	/**
	 * 校验订单当前状态,与入参是否相符
	 * @param orderstatus
	 * @param order
	 * @return
	 */
	@ValidateRule(msg="订单状态不正确")
	private boolean checkOrderStatus(String orderstatus){
		boolean flag;
		OrderState paramState = OrderState.getByCode(orderstatus);
		OrderState orderState = OrderState.getByCode(order.get().getOrderstatus());
		//如果订单是取消状态,则直接返回订单已取消
		if(OrderState.CANCEL.equals(orderState)){
			errorResult.get().put("status", Retcode.ORDERISCANCEL.code);
			errorResult.get().put("message", Retcode.ORDERISCANCEL.msg);
			return false;
		}
		//如果订单不是待接单状态,前端仍然执行抢单,则提示订单已被抢
		if (OrderState.WAITSTART.equals(paramState) && !OrderState.WAITTAKE.equals(orderState)) {
			errorResult.get().put("status", Retcode.ORDERISGONE.code);
			errorResult.get().put("message", Retcode.ORDERISGONE.msg);
			return false;
		}
		//校验申请状态是否符合订单流程
		flag  = OrderState.WAITSTART.equals(paramState) ? OrderState.WAITTAKE.equals(orderState) : 
					OrderState.START.equals(paramState) ? OrderState.WAITSTART.equals(orderState) : 
					OrderState.ARRIVAL.equals(paramState) ? OrderState.START.equals(orderState) : 
					OrderState.INSERVICE.equals(paramState) ? OrderState.ARRIVAL.equals(orderState) : 
					OrderState.WAITMONEY.equals(paramState) ? OrderState.INSERVICE.equals(orderState) : 
					OrderState.SERVICEDONE.equals(paramState) ? OrderState.WAITMONEY.equals(orderState) : false;
		//其他失败,返回默认提示
		return flag;
	}
	
	/**
	 * 校验支付方式是否正确
	 * @param paymethod
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
		//第三方支付还需检查商户是否开通(订单支付根据订单归属而非司机归属)
		if(usetype.get() != null && !OrderEnum.USETYPE_PERSONAL.equals(usetype.get())){
			leasescompany.set(accdao.getCompanyById(order.get().getCompanyid()));
			if(leasescompany.get() == null) return false;
			flag =  PayMethod.WECHAT.code.equals(paymethod) ? 
						PurseEnum.ACCOUNT_ON.code.equals(leasescompany.get().getDriverwechatstatus()) : 
						PayMethod.ALIPAY.code.equals(paymethod) ? 
						PurseEnum.ACCOUNT_ON.code.equals(leasescompany.get().getDriveralipaystatus()) : false;
			
		}else if (usetype.get() != null && OrderEnum.USETYPE_PERSONAL.equals(usetype.get())) {
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
			errorResult.get().put("message", "暂不支持"+ pm +"支付");
		}
		return flag;
	}
	
	/**
	 * 检查订单付款方式(线上|线下)
	 * @param paymethod
	 * @return
	 */
	@ValidateRule(msg="订单付款方式不正确")
	private boolean checkOrderPayMethod(String paymethod){
		if(paymethod == null || paymethod.trim().isEmpty()) return false;
		return PayMethod.ONLINE.code.equals(paymethod) ? true : 
					PayMethod.OFFLINE.code.equals(paymethod) ? true : false;	
	}
	
	/**
	 * 检查订单是否已经支付
	 * @param paystate
	 * @return
	 */
	@ValidateRule(msg="订单已支付,不能重复支付")
	private boolean checkPayState(String paystate){
		//已付结或乘客未付则打回(只会在订单完成时,支付状态才可能会改变)
		if(PayState.PAYOVER.state.equals(order.get().getPaymentstatus()) || 
			PayState.PASSENGERNOPAY.state.equals(order.get().getPaymentstatus())){
			errorResult.get().put("status", Retcode.DUPLICATEPAY.code);
			errorResult.get().put("message", "订单已支付,不能重复支付");
			return false;
		}
		return true;
	}
	
	/**
	 * 检查是否存在未付结订单(出租车需要检查)
	 * @param hasnotpayorder
	 * @return
	 */
	@ValidateRule(msg="存在未付结订单")
	private boolean checkNotPayOrder(boolean hasnotpayorder){
		logger.info("检查司机是否存在未付结订单...");
		//网约车不检查
		if(!DriverEnum.DRIVER_TYPE_TAXI.code.equals(driver.get().getVehicletype() + "")) return true;
		OrderListParam olp = new OrderListParam();
		olp.setType(2);
		olp.setDriverid(driver.get().getId());
		List<OpTaxiOrder> list = taxiorderfuncdao.getTaxiOrderList(olp);
		if(list != null && !list.isEmpty()){
			Collections.reverse(list);
			OpTaxiOrder o = list.get(0);
			logger.info("存在未付结订单:" + o.getOrderno());
			errorResult.get().put("status", Retcode.HASEARLIERORDER.code);
			errorResult.get().put("message", Retcode.HASEARLIERORDER.msg);
			errorResult.get().put("orderno", o.getOrderno());
			errorResult.get().put("usetype", o.getUsetype());
			errorResult.get().put("ordertype", o.getOrdertype());
			return false;
		}
		logger.info("检查司机是否存在未付结订单完成");
		return true;
	}
	
	/**
	 * 检查未付结订单是否超限(出租车需要检查)
	 * @param notpayorderlimit
	 * @return
	 */
	@ValidateRule(msg="未付结订单超限")
	private boolean checkNotPayOrderLimit(boolean notpayorderlimit){
		logger.info("检查司机未付结订单是否超限...");
		//网约车不检查
		if(!DriverEnum.DRIVER_TYPE_TAXI.code.equals(driver.get().getVehicletype() + "")) return true;
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
	 * 检查司机是否有更早未出行订单(出发接人不能跳单)
	 * @return
	 */
	@ValidateRule(msg="存在更早未出行订单")
	private boolean checkHasEarlierOrder(boolean hasEarLierOrder){
		logger.info("检查是否有更早未出行订单...");
		OrderListParam olp = new OrderListParam();
		olp.setType(1);
		olp.setDriverid(driver.get().getId());
		List<OpTaxiOrder> list = taxiorderfuncdao.getTaxiOrderList(olp);
		OpTaxiOrder taxiOrder = (OpTaxiOrder) order.get();
		if(list != null && !list.isEmpty()){
			for(OpTaxiOrder o : list){
				if(!taxiOrder.getOrderno().equals(o.getOrderno()) && o.getUsetime().before(taxiOrder.getUsetime())){
					errorResult.get().put("status", Retcode.HASEARLIERORDER.code);
					errorResult.get().put("message", Retcode.HASEARLIERORDER.msg);
					errorResult.get().put("orderno", o.getOrderno());
					errorResult.get().put("usetype", o.getUsetype());
					errorResult.get().put("ordertype", o.getOrdertype());
					return false;
				}
			}
		}
		logger.info("检查是否有更早未出行订单完成");
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
