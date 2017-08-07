package com.szyciov.driver.service;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.driver.base.BaseService;
import com.szyciov.driver.dao.AccountDao;
import com.szyciov.driver.dao.OrderDao;
import com.szyciov.driver.entity.DriverMessage;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.entity.OrderStatistics;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.enums.DriverMessageEnum;
import com.szyciov.driver.enums.DriverState;
import com.szyciov.driver.enums.OrderListEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.ReviewState;
import com.szyciov.driver.param.ChangeOrderStateParam;
import com.szyciov.driver.param.DriverMessageParam;
import com.szyciov.driver.param.NewsParam;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.driver.param.OrderLineParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.driver.param.OrderStatisticsParam;
import com.szyciov.entity.DataStatus;
import com.szyciov.entity.NewsState;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.OrderReviewParam;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubJpushlog;
import com.szyciov.entity.PubUserToken;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.PubJpushLogEnum;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.message.OrderMessage;
import com.szyciov.message.TaxiOrderMessage;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.PubPushLogParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.UserTokenManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("OrderService")
public class OrderService extends BaseService{
	private OrderDao dao;
	private AccountDao accDao;
	private String fileserver = SystemConfig.getSystemProperty("fileserver") + "/";
	
	@Resource(name = "OrderDao")
	public void setDao(OrderDao dao) {
		this.dao = dao;
	}
	
	@Resource(name = "AccountDao")
	public void setDao(AccountDao accDao) {
		this.accDao = accDao;
	}
	
	/**
	 * 检查参数有效性,主要检测订单号
	 * @return 有效则放入OrderInfoDetail
	 */
	private JSONObject checkParam(OrderApiParam param){
		JSONObject json = new JSONObject();
		if(param.getOrderno() == null){
			json.put("status", Retcode.ORDERNOTEXIT.code);
			json.put("message", Retcode.ORDERNOTEXIT.msg);
			return json;
		}
		//如果是个人用车查运管端
		int code = OrderEnum.USETYPE_PERSONAL.code.equals(param.getUsetype()+"") ? 1 : 0;
		param.setOrderprop(code);
		PubDriver pd = accDao.getPubDriverByToken(param.getToken());
		OrderInfoDetail oid = dao.getOrderInfoById(param);
		if(oid == null){
			json.put("status", Retcode.ORDERNOTEXIT.code);
			json.put("message", Retcode.ORDERNOTEXIT.msg);
		}else if (oid.getDriverid() != null && !oid.getDriverid().isEmpty() && !oid.getDriverid().equals(pd.getId())) {
			json.put("status", Retcode.ORDERNOTEXIT.code);
			json.put("message", Retcode.ORDERNOTEXIT.msg);
		}else if (OrderState.CANCEL.state.equals(oid.getStatus())) {
			json.put("status", Retcode.ORDERISCANCEL.code);
			json.put("message", Retcode.ORDERISCANCEL.msg);
		}else{
			json.put("order",oid);
			json.put("status", Retcode.OK.code);
			json.put("message", Retcode.OK.msg);
		}
		return json;
	}
	
	/**
	 * 检查token有效性
	 * @param token
	 * @return 有效则放入PubDriver和PubUserToken
	 */
	private JSONObject checkTokenOld(String token){
		JSONObject json = new JSONObject();
		if (token == null) {
			json.put("status", Retcode.INVALIDTOKEN.code);
			json.put("message", Retcode.INVALIDTOKEN.msg);
			return json;
		}
		String phone = null;
		try {
			phone = UserTokenManager.getUserNameFromToken(token, UserTokenManager.DRIVERUSER);
		} catch (NoSuchAlgorithmException | ParseException e) {
			json.put("status", Retcode.INVALIDTOKEN.code);
			json.put("message", Retcode.INVALIDTOKEN.msg);
			return json;
		}
		if(phone == null){
			json.put("status", Retcode.INVALIDTOKEN.code);
			json.put("message", Retcode.INVALIDTOKEN.msg);
			return json;
		}
		PubDriver driver = accDao.getPubDriverByToken(token);  //校验token唯一
//		PubDriver driver = accDao.getPubDriverByPhone(phone);  //不校验token唯一
		PubUserToken put = accDao.getUserTokenByUser(driver);
		//sql拦截token有效时间(APP的token没有时限)
		//如果token格式正确,但在数据库中没有,就表示被其他人登录,更新了token
		if(put == null){
			driver = accDao.getPubDriverByPhone(phone);
			if (driver == null && accDao.getPubDriverInDel(phone) == null) {
				json.put("status", Retcode.USERNOTEXIST.code);
				json.put("message", Retcode.USERNOTEXIST.msg);
			}else if (driver == null) {   //如果用手机号查不到司机,则表示账号被删除
				json.put("status", Retcode.USERSIGNOFF.code);
				json.put("message", Retcode.USERSIGNOFF.msg);
			}else{
				json.put("status", Retcode.ANOTHERLOGIN.code);
				json.put("message", Retcode.ANOTHERLOGIN.msg);
			}
		}else{
			json.put("driver", driver);
			json.put("token", put);
			json.put("status", Retcode.OK.code);
			json.put("message", Retcode.OK.code);
		}
		return json;
	}
	
	/**
	 * 检查参数中订单状态是否符合条件
	 * @param param
	 * @param oid
	 * @return
	 */
	private JSONObject checkStateInParam(ChangeOrderStateParam param,OrderInfoDetail oid){
		JSONObject result = new JSONObject();
		boolean flag = false;
		OrderState paramState = OrderState.getByCode(param.getOrderstatus());
		OrderState orderState = OrderState.getByCode(oid.getStatus());
		flag  = OrderState.WAITSTART.equals(paramState) ? OrderState.WAITTAKE.equals(orderState) : 
					OrderState.START.equals(paramState) ? OrderState.WAITSTART.equals(orderState) : 
					OrderState.ARRIVAL.equals(paramState) ? OrderState.START.equals(orderState) : 
//					OrderState.PICKUP.equals(paramState) ? OrderState.ARRIVAL.equals(orderState) :  //不存在接到乘客了
					OrderState.INSERVICE.equals(paramState) ? OrderState.ARRIVAL.equals(orderState) : 
					OrderState.SERVICEDONE.equals(paramState) ? OrderState.INSERVICE.equals(orderState) : false;
		if(!flag){
			result.put("status", Retcode.INVALIDORDERSTATUS.code);
			result.put("message", Retcode.INVALIDORDERSTATUS.msg);
		}else{
			result = null;
		}
		return result;
	}
	
	/**
	 * 获取司机消息
	 * @param param
	 * @return
	 */
	public JSONObject pollMessage(NewsParam param){
		String[] require = new String[]{ };
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		//保存司机ID,方便日志输出
		param.setDriverid(driver.get().getId());
		JSONObject result = doPollMessage(param);
		
		return result;
	}
	
	/**
	 * 获取订单列表
	 * @param param
	 * @return
	 */
	public JSONObject getOrderList(OrderListParam param){
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		
		JSONObject result = new JSONObject();
		PubDriver pd = StringUtil.parseJSONToBean(json.getJSONObject("driver").toString(), PubDriver.class);
		param.setDriverid(pd.getId());
		List<OrderInfoDetail> list = dao.getOrderList(param);
		JSONArray orderList = new JSONArray();
		OrderInfoDetail serviceOrder = null;
		for(OrderInfoDetail oid : list){
			if(OrderState.INSERVICE.state.equals(oid.getStatus())){
				serviceOrder = oid;
				continue;
			}
			orderList.add(convertOID(oid,false));
		}
		if(serviceOrder != null){
			orderList.add(0, convertOID(serviceOrder,false));
		}
		result.put("count", list.size());
		result.put("orders", orderList);
		return result;
	}
	
	/**
	 * 获取今日订单
	 * @param param
	 * @return
	 */
	public JSONObject getTodayOrders(OrderListParam param){
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		
		JSONObject result = new JSONObject();
		PubDriver pd = StringUtil.parseJSONToBean(json.getJSONObject("driver").toString(), PubDriver.class);
		Date start = new Date();
		Date end = new Date(start.getTime() + (1000*60*60*24));
		param.setToday(true);
		param.setDriverid(pd.getId());
		param.setStarttime(StringUtil.formatDate(start,"yyyy-MM-dd"));
		param.setEndtime(StringUtil.formatDate(end,"yyyy-MM-dd"));
		List<OrderInfoDetail> list = dao.getOrderList(param);
		JSONArray orderList = new JSONArray();
		OrderInfoDetail serviceOrder = null;
		for(OrderInfoDetail oid : list){
			if(OrderState.INSERVICE.state.equals(oid.getStatus())){
				serviceOrder = oid;
				continue;
			}
			orderList.add(convertOID(oid,false));
		}
		if(serviceOrder != null){
			orderList.add(0, convertOID(serviceOrder,false));
		}
		int totalCount = dao.getOrderTotalCount(param);
		result.put("totalcount", totalCount);
		result.put("count", list.size());
		result.put("orders", orderList);
		return result;
	}
	
	/**
	 * 改变订单状态
	 * @param param
	 * @return
	 */
	public JSONObject changeOrderState(ChangeOrderStateParam param){
		logger.info("订单"+param.getOrderno()+"本次请求参数:" + param.getOrderstatus() + ",时间:" + StringUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		PubDriver pd = StringUtil.parseJSONToBean(json.getJSONObject("driver").toString(), PubDriver.class);
		OrderApiParam oap = new OrderApiParam();
		oap.setOrderid(param.getOrderno());
		oap.setUsetype(param.getUsetype());
		oap.setOrdertype(param.getOrdertype());
		oap.setToken(param.getToken());
		json = checkParam(oap);
		if(json.getInt("status") != Retcode.OK.code) return json;
		
		OrderInfoDetail oid = StringUtil.parseJSONToBean(json.getJSONObject("order").toString(),OrderInfoDetail.class);
		JSONObject result = new JSONObject();
		//检查参数中订单状态是否符合条件
		result = checkStateInParam(param,oid);
		if(null != result) return result;
		
		oap.setOrderstate(param.getOrderstatus());
		oap.setOrderprop(oid.getOrderprop());
		OrderState paramState = OrderState.getByCode(param.getOrderstatus());
		OrderState orderState = OrderState.getByCode(oid.getStatus());
		result  =  OrderState.CANCEL.equals(orderState) ? orderCancel(oid, pd) :                              //订单已取消
						OrderState.WAITSTART.equals(paramState) ? takingOrder(oid, pd,oap) : //抢单
						OrderState.START.equals(paramState) ? orderStart(oid, pd,param) :           //已出发
						OrderState.ARRIVAL.equals(paramState) ? orderArrival(oid, pd,param) :   //已抵达
//						OrderState.PICKUP.equals(paramState) ? orderPickup(oid) :                       //接到乘客(不存在接到乘客了)
						OrderState.INSERVICE.equals(paramState) ? orderInservice(oid,pd, param) :              //服务中
						OrderState.SERVICEDONE.equals(paramState) ? orderEnd(oid, pd, oap, param) : null;  //服务结束:默认返回null
		//异常状态码则直接返回
		if(result != null && result.has("status") && result.getInt("status") != Retcode.OK.code) return result;
		//如果是抢单使用另外的通知
		if(OrderState.WAITSTART.equals(paramState)) return result;
		//司机变更状态需要通知(完成时不重新调用完成推送)
		if(oid.getOrderprop() == 0 && !OrderState.SERVICEDONE.equals(paramState)){
			OrgOrder orgOrder = dao.getOrgOrder(oid.getOrderno());
			OrderMessage ordermessage = new OrderMessage(orgOrder,OrderMessage.ORDERHINT);
			MessageUtil.sendMessage(ordermessage);
		}else if(oid.getOrderprop() == 1 && !OrderState.SERVICEDONE.equals(paramState)){
			OpOrder opOrder = dao.getOpOrder(oid.getOrderno());
			OrderMessage ordermessage = new OrderMessage(opOrder,OrderMessage.ORDERHINT);
			MessageUtil.sendMessage(ordermessage);
		}
		result.put("orderstatus", oid.getStatus());
		result.put("orderno", oid.getOrderno());
		result.put("usetype", oid.getUsetype());
		result.put("ordertype", oid.getType());
		return result;
	}
	
	/**
	 * 保存时间节点司机所在地(先调用此方法再更新订单)
	 * @param oid
	 * @param cosp
	 */
	private void saveLocInTime(OrderInfoDetail oid,ChangeOrderStateParam cosp){
		//如果上传了经纬度,则记录地址信息
		if(cosp.getLng() == 0 || cosp.getLat() == 0) return;
		JSONObject result = new JSONObject();
		BaiduApiQueryParam baqp = new BaiduApiQueryParam();
		baqp.setOrderStartLng(cosp.getLng());
		baqp.setOrderStartLat(cosp.getLat());
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/BaiduApi/GetAddress", 
				HttpMethod.POST, 
				null, 
				baqp, 
				JSONObject.class);
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info("经纬度反查失败,仅保存APP上传经纬度");
			oid.setLng(cosp.getLng());
			oid.setLat(cosp.getLat());
			dao.updateOrder(oid);
			return;
		}
		PubCityAddr pca = dao.getPubCity(result.getString("city"));
		oid.setLng(result.getDouble("lng"));
		oid.setLat(result.getDouble("lat"));
		oid.setCityintime(pca == null ? null : pca.getId());
		oid.setAddressintime(result.getString("address").isEmpty()?null:result.getString("address"));
		dao.updateOrder(oid);
	}
	
	/**
	 * 保存司机服务中订单
	 * @param oid
	 * @param pd
	 */
	private void saveOrUpdateInserviceOrder(OrderInfoDetail oid,PubDriver pd,boolean isupdate){
		String key = "OBD_MILGES_CURRENT_ORDER_"+pd.getId()+"_"+oid.getOrderno();
		try{
			JSONObject json = new JSONObject();
			json.put("orderno", oid.getOrderno());
			json.put("driverid", pd.getId());
			json.put("vehicleid", pd.getVehicleid());
			json.put("starttime", StringUtil.formatDate(oid.getStarttime(), StringUtil.DATE_TIME_FORMAT));
			json.put("orderstatus", oid.getStatus());
			if(isupdate){
				int second = 60 * 10; //十分钟
				json.put("endtime", StringUtil.formatDate(oid.getEndtime(), StringUtil.DATE_TIME_FORMAT));
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
	 * 司机已出发处理逻辑
	 * @param oid
	 * @param pd
	 * @param cosp
	 * @return
	 */
	public JSONObject orderStart(OrderInfoDetail oid,PubDriver pd,ChangeOrderStateParam cosp){
		JSONObject result = new JSONObject();
		OrderListParam olp = new OrderListParam();
		olp.setType(1);
		olp.setDriverid(pd.getId());
		//如果存在需要更早出发的订单,则返回(不能跳单执行)
		List<OrderInfoDetail> list = dao.getOrderList(olp);
		if(list != null && !list.isEmpty()){
			for(OrderInfoDetail o : list){
				if(!o.getOrderno().equals(oid.getOrderno()) && o.getUsetime().before(oid.getUsetime())){
					result.put("status", Retcode.HASEARLIERORDER.code);
					result.put("message", Retcode.HASEARLIERORDER.msg);
					result.put("orderno", o.getOrderno());
					result.put("usetype", o.getUsetype());
					result.put("ordertype", o.getType());
					return result;
				}
			}
		}
		//如果出发接人时,司机已经处于服务中则提示2004
		if(DriverState.INSERVICE.code.equals(pd.getWorkstatus())){
			result.put("status", Retcode.ORDERNOTDONE.code);
			result.put("message", Retcode.ORDERNOTDONE.msg);
			return result;
		}
		if(!OrderState.WAITSTART.state.equals(oid.getStatus())){
			result.put("status", Retcode.INVALIDORDERSTATUS.code);
			result.put("message", Retcode.INVALIDORDERSTATUS.msg);
			return result;
		}
		oid.setStatus(OrderState.START.state);
		oid.setDeparturetime(new Date());
		//无论是否预约用车的单,司机只要出发了就属于服务中
		pd.setWorkstatus(DriverState.INSERVICE.code);
		pd.setUpdateTime(new Date());
		accDao.updatePubDriver(pd);
		saveLocInTime(oid,cosp);
		//更新订单状态
		dao.updateOrder(oid);
		return new JSONObject();
	}
	
	/**
	 * 司机已抵达处理逻辑
	 * @param oid
	 * @param pd
	 * @param cosp
	 * @return
	 */
	public JSONObject orderArrival(OrderInfoDetail oid,PubDriver pd,ChangeOrderStateParam cosp){
		JSONObject result = new JSONObject();
		if(!OrderState.START.state.equals(oid.getStatus())){
			result.put("status", Retcode.INVALIDORDERSTATUS.code);
			result.put("message", Retcode.INVALIDORDERSTATUS.msg);
			return result;
		}
		oid.setStatus(OrderState.ARRIVAL.state);
		oid.setArrivaltime(new Date());
		saveLocInTime(oid,cosp);
		//更新订单状态
		dao.updateOrder(oid);
		return new JSONObject();
	}
	
	/**
	 * 接到乘客处理逻辑(已废弃)
	 * @param oid
	 * @deprecated
	 * @return
	 */
	public JSONObject orderPickup(OrderInfoDetail oid){
		oid.setStatus(OrderState.PICKUP.state);
		//更新订单状态
		dao.updateOrder(oid);
		return new JSONObject();
	}
	
	/**
	 * 开始服务处理逻辑
	 * @param oid
	 * @param cosp
	 * @return
	 */
	public JSONObject orderInservice(OrderInfoDetail oid, PubDriver pd,ChangeOrderStateParam cosp){
		oid.setStarttime(new Date());
		oid.setStatus(OrderState.INSERVICE.state);
		saveLocInTime(oid, cosp);
		//更新订单状态
		dao.updateOrder(oid);
		saveOrUpdateInserviceOrder(oid,pd,false);
		return new JSONObject();
	}
	
	/**
	 * 结束订单处理逻辑
	 * @param oid
	 * @param pd
	 * @param oap
	 * @param cosp
	 * @return
	 */
	public JSONObject orderEnd(OrderInfoDetail oid,PubDriver pd,OrderApiParam oap, ChangeOrderStateParam cosp){
		oid.setStatus(OrderState.SERVICEDONE.state);//不可删除,传参方式重新赋值会改变对象的引用地址
		saveLocInTime(oid,cosp);
		JSONObject result = new JSONObject();
		//订单完成需要进行支付动作,由carservice完成
		result = templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl")+"/OrderApi/ChangeOrderState", 
				HttpMethod.POST, 
				null, 
				oap, 
				JSONObject.class);
		if(Retcode.OK.code != result.getInt("status")) return result;
		oid = dao.getOrderInfoById(oap);

		//司机累计接单量+1(完成时才算)
		pd.setOrdercount(pd.getOrdercount()+1);
		pd.setUpdateTime(new Date());
		pd.setWorkstatus(DriverState.IDLE.code);
		accDao.updatePubDriver(pd);
		saveOrUpdateInserviceOrder(oid,pd,true);
		return result;
	}
	
	/**
	 * 订单已取消处理逻辑
	 * @return
	 */
	public JSONObject orderCancel(OrderInfoDetail oid, PubDriver pd){
		saveDriverHansUp(oid,pd);
		JSONObject result = new JSONObject();
		result.put("status", Retcode.ORDERISCANCEL.code);
		result.put("message", Retcode.ORDERISCANCEL.msg);
		return result;
	}
	
	/**
	 * 抢单时订单已进入人工处理逻辑
	 * @return
	 */
	public JSONObject orderMantic(OrderInfoDetail oid, PubDriver pd){
		saveDriverHansUp(oid, pd);
		JSONObject result = new JSONObject();
		result.put("status", Retcode.FAILED.code);
		result.put("message", "抢单失败.");
		return result;
	}
	
	/**
	 * 订单已被接走处理逻辑
	 * @return
	 */
	public JSONObject orderGone(OrderInfoDetail oid,PubDriver pd){
		saveDriverHansUp(oid, pd);
		JSONObject result = new JSONObject();
		result.put("status", Retcode.ORDERISGONE.code);
		result.put("message", Retcode.ORDERISGONE.msg);
		return result;
	}
	
	/**
	 * 更新订单信息为抢单司机
	 * @param oid
	 * @param pd
	 * @param param
	 * @return
	 */
	public JSONObject orderTaking(OrderInfoDetail oid,PubDriver pd,OrderApiParam param){
		JSONObject result = new JSONObject();
		if(oid.getOrderprop() == 0){
			OrgOrder order = dao.getOrgOrder(oid.getOrderno());
			order.setFactmodel(pd.getOrgcartypeid());  //更新实际车型
			order.setCompanyid(pd.getLeasescompanyid());
			order.setOrdertime(new Date());
			order.setDriverid(pd.getId());
			order.setOrderstatus(OrderState.WAITSTART.state);
			order.setVehicleid(pd.getVehicleid());
			order.setPlateno(pd.getPlateno());
			order.setVehcbrandname(pd.getVehcbrandname());
			order.setVehclinename(pd.getVehclinename());
			order.setBelongleasecompany(pd.getBelongleasecompany());
			dao.updateOrgOrder(order);
		}else{
			OpOrder order = dao.getOpOrder(oid.getOrderno());
			order.setFactmodel(pd.getOpcartypeid());  //更新实际车型
			order.setCompanyid(pd.getLeasescompanyid());
			order.setOrdertime(new Date());
			order.setDriverid(pd.getId());
			order.setOrderstatus(OrderState.WAITSTART.state);
			order.setVehicleid(pd.getVehicleid());
			order.setPlateno(pd.getPlateno());
			order.setVehcbrandname(pd.getVehcbrandname());
			order.setVehclinename(pd.getVehclinename());
			order.setBelongleasecompany(pd.getBelongleasecompany());
			dao.updateOpOrder(order);
		}
		oid.setDriverid(pd.getId());  //设置司机ID,表示该订单抢单成功
		saveDriverHansUp(oid,pd);
		removeDriverMessage(oid,pd,param);
		sendTakingOrderMessage(oid,param);
		result.put("order", convertOID(oid,true));
		return result;
	}
	
	/**
	 * 执行抢单流程
	 * @param oid
	 * @param pd
	 * @param param
	 * @return
	 */
	public JSONObject takingOrder(OrderInfoDetail oid,PubDriver pd,OrderApiParam param){
		JSONObject result = new JSONObject();
		if(!checkDriverCanTakeOrder(pd,oid)) {
			result.put("status", Retcode.ORDERNOTDONE.code);
			result.put("message", Retcode.ORDERNOTDONE.msg);
			return result;
		}
		boolean flag = JedisUtil.lock(oid.getOrderno()); //锁定该订单
		if(!flag) return orderGone(oid,pd); //抢单时首先判断是否订单被锁定,已被锁定
		OrderState orderState = OrderState.getByCode(oid.getStatus());
		result  =  OrderState.MANTICSEND.equals(orderState) ? orderMantic(oid,pd) :             //已进入人工派单环节
						OrderState.WAITTAKE.equals(orderState) ? orderTaking(oid, pd, param) :     //允许接单
						OrderState.CANCEL.equals(orderState) ? orderCancel(oid, pd) : orderGone(oid,pd);  //订单已取消 | 订单被抢
		oid = dao.getOrderInfoById(param);
		JedisUtil.unLock(oid.getOrderno()); //流程执行完成后解锁
		return result;
	}
	
	/**
	 * 保存司机举手记录
	 * @param orderInfoDetail
	 * @param pd
	 */
	public void saveDriverHansUp(OrderInfoDetail oid,PubDriver pd){
		logger.info("保存司机举手记录...");
		logger.info("使用参数:" + JSONObject.fromObject(oid));
		logger.info("使用参数:" + JSONObject.fromObject(pd));
		PubPushLogParam param = new PubPushLogParam();
		param.setOrderno(oid.getOrderno());
		param.setDriverid(pd.getId());
		PubJpushlog jpushlog = dao.getPubJpushlog(param);
		if(jpushlog == null){
			logger.warn("没有找到相应推送记录");
			return;
		}else{
			jpushlog.setHandstate(PubJpushLogEnum.SUCCESS_HANDSTATE_PUBJPUSHLOG.icode);
			//司机抢单成功才更改此字段,否则只保存举手记录
			if(oid.getDriverid() != null && oid.getDriverid().equals(pd.getId())){
				jpushlog.setTakeorderstate(PubJpushLogEnum.SUCCESS_TAKEORDERSTATE_PUBJPUSHLOG.icode);
			}
			dao.saveDriverHansUp(jpushlog);
		}
		logger.info("保存司机举手记录完成");
	}
	
	/**
	 * 获取订单状态
	 * @param param
	 * @return
	 */
	public JSONObject getOrderState(OrderApiParam param){
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		json = checkParam(param);
		if(json.getInt("status") != Retcode.OK.code) return json;
		
		JSONObject result = new JSONObject();
		OrderInfoDetail oid = StringUtil.parseJSONToBean(json.getJSONObject("order").toString(), OrderInfoDetail.class);
		//如果订单已完成,则计算完成时间
		if(OrderState.SERVICEDONE.state.equals(oid.getStatus())){
			oid.setTimes((int) ((oid.getEndtime().getTime() - oid.getStarttime().getTime())/1000));
		//如果订单未完成,则计算累计时间
		}else if(OrderState.INSERVICE.state.equals(oid.getStatus())){
			oid.setTimes((int) ((System.currentTimeMillis() - oid.getStarttime().getTime())/1000));
		}
		result.put("order", convertOID(oid,false));
		return result;
	}
	
	/**
	 * 发送抢单消息
	 * @param oid
	 * @param oap
	 */
	public void sendTakingOrderMessage(OrderInfoDetail oid,OrderApiParam oap){
		//司机抢单成功推送抢单成功消息,不在派单环节中推送
		if(oid.getOrderprop() == 0){
			OrgOrder orderinfo = dao.getOrgOrder(oid.getOrderno());
			OrderMessage om = new OrderMessage(orderinfo,OrderMessage.TAKEORDER);
			MessageUtil.sendMessage(om);
		}else{
			OpOrder orderinfo = dao.getOpOrder(oid.getOrderno());
			OrderMessage om = new OrderMessage(orderinfo,OrderMessage.TAKEORDER);
			MessageUtil.sendMessage(om);
		}
		logger.info("抢单成功已推送至短信和消息平台...");
		oap.setOrderstate(OrderState.WAITSTART.state);
		JSONObject result = new JSONObject();
		result = templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl")+"/OrderApi/ChangeOrderState", 
				HttpMethod.POST, 
				oap.getToken(), 
				oap, 
				JSONObject.class);
		if(result.getInt("status") == Retcode.OK.code){
			logger.info("抢单成功已推送至web平台...");
		}else{
			logger.info("抢单成功推送web平台失败...");
		}
	}
	
	/**
	 * 订单统计
	 * @param param
	 * @return
	 */
	public JSONObject orderStatistics(OrderStatisticsParam param){
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		
		JSONObject result = new JSONObject();
		PubDriver pd = StringUtil.parseJSONToBean(json.getJSONObject("driver").toString(), PubDriver.class);
		param.setDriverid(pd.getId());
		param.setStatus(OrderState.SERVICEDONE.state);
		List<OrderStatistics> list = dao.orderStatistics(param);
		result.put("statistics", list);
		return result;
	}
	
	/**
	 * 获取订单轨迹线
	 * @param param
	 * @return
	 */
	public JSONObject getOrderLine(OrderLineParam param){
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		json = checkParam(param);
		if(json.getInt("status") != Retcode.OK.code) return json;
		
		JSONObject result = new JSONObject();
		OrderInfoDetail oid = StringUtil.parseJSONToBean(json.getJSONObject("order").toString(), OrderInfoDetail.class);
		param.setStartDate(oid.getStarttime());
		param.setEndDate(oid.getEndtime());
		result = templateHelper.dealRequestWithToken(
				"/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}",
				HttpMethod.GET, 
				param.getToken(), 
				null, 
				JSONObject.class,
				param.getOrderno(),
				param.getOrdertype(),
				param.getUsetype());
		if(result.getInt("status") != Retcode.OK.code) return result;
		result.put("message",Retcode.OK.msg);
		result.remove("duration");
		return result;
	}
	
	/**
	 * 申请复核
	 * @param param
	 * @return
	 */
	public JSONObject applyForReview(OrderReviewParam param){
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		json = checkParam(param);
		if(json.getInt("status") != Retcode.OK.code) return json;
		
		if(param.getOrderprop() == 0){
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

		JSONObject result = new JSONObject();
		result.put("status", Retcode.OK.code);
		result.put("message", "申请复议成功.");
		return result;
	}
	
	/**
	 * 获取订单实时费用
	 * @param param
	 * @return
	 */
	public JSONObject getCurrentCost(OrderCostParam param){
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		json = checkParam(param);
		//已取消订单也获取费用
		if(json.getInt("status") != Retcode.OK.code && Retcode.ORDERISCANCEL.code != json.getInt("status")) return json;
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl")+"/OrderApi/GetOrderCost", 
				HttpMethod.POST, 
				param.getToken(), 
				param, 
				JSONObject.class);
		return result;
	}
	
	/**
	 * 获取订单实时费用(不做token校验)
	 * @param param
	 * @return
	 */
	public JSONObject getCurrentCostNoCheck(OrderCostParam param){
		//已取消订单也获取费用
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl")+"/OrderApi/GetOrderCost", 
				HttpMethod.POST, 
				param.getToken(), 
				param, 
				JSONObject.class);
		return result;
	}
	
	/**
	 * 获取司机服务中订单
	 * @param param
	 * @return
	 */
	public JSONObject getCurrentOrder(OrderListParam param){
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		
		JSONObject result = new JSONObject();
		PubDriver pd = StringUtil.parseJSONToBean(json.getJSONObject("driver").toString(), PubDriver.class);
		param.setDriverid(pd.getId());
		param.setType(-1); //只取服务中订单
		List<OrderInfoDetail> list = dao.getOrderList(param);
		if(list !=null && !list.isEmpty()){
			result.put("order", convertOID(list.get(0),false));
		}else{
			result.put("order", new JSONObject());
		}
		return result;
	}
	
	/**
	 * 删除消息
	 * @param param
	 * @return
	 */
	public JSONObject delNews(NewsParam param){
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		
		PubDriver pd = StringUtil.parseJSONToBean(json.getJSONObject("driver").toString(), PubDriver.class);
		param.setDriverid(pd.getId());
		JSONObject result = new JSONObject();
		PubDriverNews pdn = dao.getNewsById(param);
		if(pdn == null) return null;
		pdn.setStatus(DataStatus.DELETE.code);
		dao.updateNews(pdn);
		return result;
	}
	
	/**
	 * 消息标记为已读
	 * @param param
	 * @return
	 */
	public JSONObject readNews(NewsParam param){
		JSONObject json = new JSONObject();
		json = checkTokenOld(param.getToken());
		if(json.getInt("status") != Retcode.OK.code) return json;
		
		PubDriver pd = StringUtil.parseJSONToBean(json.getJSONObject("driver").toString(), PubDriver.class);
		JSONObject result = new JSONObject();
		PubDriverNews pdn;
		if(param.isAllread() || param.getNewsid() == null || param.getNewsid().trim().isEmpty()){
			pdn = new PubDriverNews();
			pdn.setNewsstate("1");
			pdn.setUserid(pd.getId());
			dao.updateNewsAllRead(pdn);
		}else{
			param.setDriverid(pd.getId());
			pdn = dao.getNewsById(param);
			if(pdn == null) return null;
			pdn.setNewsstate("1");
			dao.updateNews(pdn);
		}
		return result;
	}
	
	/**
	 * 行程提醒(只做取消提醒)
	 * @param param
	 * @return
	 */
	public JSONObject orderRemind(OrderApiParam param){
		JSONObject result = new JSONObject();
		if(!param.isRemind()){
			param.setOrderno(param.getOrderid());
			result = templateHelper.dealRequestWithFullUrlToken(
					SystemConfig.getSystemProperty("carserviceApiUrl")+"/OrderApi/CancelOrderRemind", 
					HttpMethod.POST, 
					null, 
					param, 
					JSONObject.class);
		}
		return result;
	}
	
	/**
	 * 网约车获取订单消息
	 * @param param
	 * @return
	 */
	public JSONObject getOrderMessageList(DriverMessageParam param){
		String[] require = new String[]{ };
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		JSONObject result = new JSONObject();
		List<DriverMessage> messages = new ArrayList<>();
		messages = doGetOrderMessageList();
		if(messages == null){
			messages = new ArrayList<>(); 
		}
		result.put("count", messages.size());
		result.put("news", messages);
		return result;
	}

	/**********************************************************内部方法***************************************************************/
	/**
	 * 网约车获取订单消息
	 * @param param
	 * @return
	 */
	private List<DriverMessage> doGetOrderMessageList(){
		logger.info("网约车获取订单消息...");
		List<DriverMessage> messages = new ArrayList<>();
		String key = "DriverGrabMessage_" + driver.get().getId() + "_"+driver.get().getPhone()+"_*";
		Set<String> keys = JedisUtil.getKeys(key);
		if (keys.isEmpty()) return messages;
		for(String k : keys){
			String value = JedisUtil.getString(k);
			if(value == null) continue;
			DriverMessage message = StringUtil.parseJSONToBean(value, DriverMessage.class);
			messages.add(message);
        }
    	sortMessageList(messages);
		logger.info("网约车获取订单消息完成");
		return messages;
	}
	
	/**
	 * 网约车获取系统消息
	 * @param param
	 * @return
	 */
	private List<DriverMessage> doGetSystemMessageList(NewsParam param){
		logger.info("网约车获取系统消息...");
		List<PubDriverNews> news = dao.getNewsByType(param);
		List<DriverMessage> messages = new ArrayList<>();
		for(PubDriverNews n : news){
			DriverMessage dm = convertDriverNewsToMessage(n);
			messages.add(dm);
		}
		logger.info("网约车获取系统消息完成");
		return messages;
	}
	
	/**
	 * 获取司机消息
	 * @param param
	 * @return
	 */
	private JSONObject doPollMessage(NewsParam param){
		logger.info("网约车获取消息开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		List<DriverMessage> messages = new ArrayList<>();
		JSONObject result = new JSONObject();
		if(DriverMessageEnum.TYPE_ORDER.code.equals(param.getType()+"")){
			messages = doGetOrderMessageList();
		}else{
			messages = doGetSystemMessageList(param);
		}
		
		if(messages == null){
			messages = new ArrayList<>(); 
		}
		result.put("count", messages.size());
		result.put("comment", messages);
		logger.info("网约车获取消息完成");
		return result;
	}
	
	/**
	 * 检查司机状态是否能够接单
	 * @param driver
	 * @param order
	 * @param sow
	 * @return
	 */
	private boolean checkDriverState(PubDriver driver){
		String workstatus = driver.getWorkstatus();
		 //司机状态不是空闲
		if(!DriverState.IDLE.code.equals(workstatus)){
			return false;
		} 
		return true;
	}
	
	/**
	 * 校验司机是否可以抢单(已经有即刻单不能再抢,预约订单不能抢同一天的)
	 * @return
	 */
	private boolean checkDriverCanTakeOrder(PubDriver driver,OrderInfoDetail order){
		//检查司机的未出行订单和是否有服务中订单
		OrderListParam olp = new OrderListParam();
		olp.setType(OrderListEnum.CURRENT.state);
		olp.setDriverid(driver.getId());
		List<OrderInfoDetail> currentOrder = dao.getOrderList(olp);
		if(order.isIsusenow()){   //当前是即刻单
			for(OrderInfoDetail o : currentOrder){
				//当前订单预估时长(秒) + 1小时
				int estimatedSecond = (int)order.getEstimatedtime()*60 + 3600;
				//预估结束时间
				Date noTakeStart = StringUtil.addDate(order.getUsetime(), estimatedSecond);
				//存在未开始的即刻单或正在服务的订单
				if(o.isIsusenow() || o.getStarttime() != null){
					return false;
				//当前订单的预估结束时间(用车时间+预估时长+1小时)不在已存在的预约单用车时间之前是不可以接的
				}else if(!noTakeStart.before(o.getUsetime())){
					return false;
				}
			}
		}else{   //当前是预约单
			for(OrderInfoDetail o : currentOrder){
				if(o.getStarttime() != null){  //存在正在服务的订单
					int minute = (int)o.getEstimatedtime();  //预估时间
					//调度时间(当前订单的用车时间,必须晚于服务中订单的预估时间的2倍)
	                Date temptime = StringUtil.addDate(o.getStarttime(), minute*60*2);
	                if(temptime.after(order.getUsetime())){
	                	return false;
	                }
	            //当前订单用车时间不在已存在的即刻单一个小时之后是不可以接的
				}else if(o.isIsusenow() && order.getUsetime().before(StringUtil.addDate(o.getUsetime(), 3600))){
					return false;
				//当前订单的用车时间与已存在的预约单是同一天,不可以接
				}else if (!o.isIsusenow() && StringUtil.getToday(order.getUsetime()).equals(StringUtil.getToday(o.getUsetime()))) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 订单信息单位换算,费用和时长都需要换算成String
	 * @param oid
	 * @return
	 */
	private JSONObject convertOID(OrderInfoDetail oid,boolean isTakeOrder){
		OrderCost oc = StringUtil.parseJSONToBean(oid.getPricecopy(), OrderCost.class);
		if(oid.getCartypelogo() != null && !oid.getCartypelogo().isEmpty()){
			oid.setCartypelogo(fileserver + oid.getCartypelogo());
		}
		if(oid.getPassengericonmin() != null && !oid.getPassengericonmin().isEmpty()){
			oid.setPassengericonmin(fileserver + oid.getPassengericonmin());
		}
		if(oid.getPassengericonmax() != null && !oid.getPassengericonmax().isEmpty()){
			oid.setPassengericonmax(fileserver + oid.getPassengericonmax());
		}
		JSONObject pricecopy = JSONObject.fromObject(oid.getPricecopy());

		int times;
		if(pricecopy.get("timetype")==null){
			times = oid.getTimes();
		}else{
			times = pricecopy.getInt("timetype")==0?oid.getTimes():pricecopy.getInt("slowtimes");
		}

		double rangecost = StringUtil.formatNum(oid.getMileage()/1000, 1)*oid.getRangeprice();
		double timecost = StringUtil.formatNum(oid.getTimes(), 1)*oid.getTimeprice();
		String passengers = (oid.getPassengers() == null || oid.getPassengers().trim().isEmpty()) ? "佚名" : oid.getPassengers();
		//获取订单费用
		JSONObject order = JSONObject.fromObject(oid);
		order.put("passengers", passengers);
		order.put("orderamount", StringUtil.formatNum(oid.getOrderamount(), 1) + "");
		order.put("mileage", StringUtil.formatNum(oid.getMileage()/1000, 1)+"公里");
		order.put("startprice", StringUtil.formatNum(oid.getStartprice(), 1)+"元");
		order.put("rangeprice", StringUtil.formatNum(oid.getRangeprice(), 1)+"元/公里");
		order.put("timeprice", StringUtil.formatNum(oid.getTimeprice(), 1)+"元/分钟");
		order.put("deadheadprice", oc.getDeadheadprice()+"元/公里");
		order.put("deadheadmileage", oc.getDeadheadmileage()+"公里");
		order.put("realdeadheadmileage", oc.getRealdeadheadmileage()+"公里");
		order.put("nighteprice", oc.getNighteprice()+"元/公里");
		order.put("nightstarttime", oc.getNightstarttime());
		order.put("nightendtime", oc.getNightendtime());
		order.put("rangecost", StringUtil.formatNum(rangecost, 1)+"元");
		order.put("timecost", StringUtil.formatNum(timecost, 1)+"元");
		order.put("deadheadcost", oc.getDeadheadcost()+"元");
		order.put("nightcost", oc.getNightcost()+"元");
		order.put("nightrange", (oc.getNightcost() == 0 ? "0" : oc.getMileage()/1000)+"公里");
		order.put("times",   StringUtil.formatCostTime(times));
		if(isTakeOrder){  //剩余时间,抢单成功返回总剩余时长,列表页返回待出发
			int  lasttime = (int) ((oid.getUsetime().getTime()-System.currentTimeMillis())/1000/60);
			lasttime = lasttime < 0 ? 0 : lasttime; // 如果超过了剩余时间则显示0分钟
			oid.setLasttime("剩余"+StringUtil.formatCostTime(lasttime).replace("钟", ""));
		}else{
			order.put("lasttime", StringUtil.formatOrderStatus(oid.getUsetime(),oid.getStatus()));
		}
		order.put("paystatus", StringUtil.formatPayStatus(oid.getPaystatus(),oid.getStatus()));
		order.put("ordertype", oid.getType());
		//去掉不必要字段
		order.remove("pricecopy");
		order.remove("organid");
		order.remove("companyid");
		order.remove("cityid");
		order.remove("driverid");
		order.remove("cancelparty");
		order.remove("vehicleid");
		order.remove("cityintime");
		order.remove("addressintime");
		order.remove("lng");
		order.remove("lat");
		return order;
	}
	
    /**
	 * 消息排序(抢单时限越少的在上面)
	 * @param list
	 * @return
	 */
	private void sortMessageList(List<DriverMessage> list){
		if(list == null || list.isEmpty()) return;
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
		dm.setTitle(newsContent.getString("title"));
		dm.setContent(newsContent.getString("content"));
		dm.setNewstype(NewsState.convertNewsType(newsType));
		//如果存在订单信息
		JSONObject orderinfo = new JSONObject();
		if(newsContent.has("orderid")){
			orderinfo.put("orderno", newsContent.getString("orderid"));
			orderinfo.put("usetype", newsContent.getString("usetype"));
			orderinfo.put("ordertype", newsContent.getString("ordertype"));
			orderinfo.put("type",OrderEnum.getOrderType(newsContent.getString("ordertype")).msg);
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
			takecashinfo.put("remark", newsContent.getString("remark"));
			takecashinfo.put("amount", newsContent.getDouble("amount"));
			takecashinfo.put("bank", newsContent.getString("bank"));
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
	
	/**
	 * 抢单成功清除redis中的抢单信息
	 * @return
	 */
	private boolean removeDriverMessage(OrderInfoDetail oid,PubDriver driver,OrderApiParam param){
		String useday = StringUtil.formatDate(StringUtil.getToday(oid.getUsetime()), StringUtil.TIME_WITH_DAY);
		String key = "DriverGrabMessage_*_*_" + param.getOrderno()+"_*";
		String sameTime = "DriverGrabMessage_"+ driver.getId() +"_"+ driver.getPhone() +"_*_" + useday;
		Set<String> keys = JedisUtil.getKeys(key);
		Set<String> sameKeys = JedisUtil.getKeys(sameTime);
		List<String> phones = new ArrayList<>();
		for(String k : keys){
			JedisUtil.delKey(k);
			//剔除当前司机
			if(driver.getPhone().equals(k.split("_")[2])) continue;
			phones.add(k.split("_")[2]);
		}
		//删除该司机同一天的订单
		for(String k : sameKeys){
			JedisUtil.delKey(k);
		}
		if(!phones.isEmpty()){
			//给被删除消息的司机发送通知
			sendMessage4Order(phones);
		}
		return true;
	}
	
	/**
	 * 给司机推送订单失效(可以共用出租车发送逻辑,type一样即可)
	 */
	private boolean sendMessage4Order(List<String> phones){
		String messagetype = TaxiOrderMessage.TAXI_DRIVERMESSAGE;
		TaxiOrderMessage om = null;
		//推送给其他之前推送过抢单消息的司机
		om = new TaxiOrderMessage(messagetype,phones);
		MessageUtil.sendMessage(om);
		return true;
	}
}
