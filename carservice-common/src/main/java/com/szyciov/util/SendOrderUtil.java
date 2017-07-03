package com.szyciov.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.szyciov.driver.entity.DriverMessage;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.DriverMessageEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubSendrules;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.OrderVarietyEnum;
import com.szyciov.enums.SendRulesEnum;
import com.szyciov.enums.VehicleEnum;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxisendrules;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.OrderInfoParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SendOrderUtil {
	private Logger logger = LoggerFactory.getLogger(SendOrderUtil.class);
//	private boolean isTest = false;
	private TemplateHelper carserviceTemplate = new TemplateHelper();
	private PubSendrules sendrule;
	private OpTaxisendrules taxisendrules;
	private List<PubDriver> driverList =  new ArrayList<>();
	
	public SendOrderUtil() {
		try {
			String runmode = SystemConfig.getSystemProperty("runmode","test");
			if("test".equalsIgnoreCase(runmode)){
//				isTest = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendOrder(OrderInfoParam orderInfo) throws Exception{
		getSendRule(orderInfo);
		if(sendrule == null) {
			logger.info("没有获取到派单规则");
			return;
		}
		logger.info("获取到派单规则");
		sendOrderFlow(orderInfo);
	}
	
	/**
	 * 根据订单号获取订单实体
	 * @param orderInfo
	 * @return
	 */
	public OrderInfoDetail getOrder(OrderInfoParam orderInfo){
//		if(orderInfo.getOrder() instanceof OpTaxiOrder && null == taxisendrules) {
//			getSendRule(orderInfo);
//		} else if(sendrule == null) {
//			getSendRule(orderInfo);
//		}
		OrderApiParam oap = new OrderApiParam();
		oap.setOrderid(orderInfo.getOrder().getOrderno());
		oap.setOrderno(orderInfo.getOrder().getOrderno());
		oap.setUsetype(orderInfo.getOrder().getUsetype());
		oap.setOrdertype(orderInfo.getOrder().getOrdertype());
		JSONObject result = carserviceTemplate.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApi") + "/OrderApi/GetOrderInfo", 
				HttpMethod.POST, 
				null, 
				oap, 
				JSONObject.class,
				orderInfo.getOrder().getOrderno());
		if(result.getInt("status") == Retcode.OK.code){
			JSONObject order = result.getJSONObject("order");
			//重新赋值,json在转换时会自动调用get方法
			order.put("times", order.getInt("times")*60);
			removeNullObejct(order);
			OrderInfoDetail oid = StringUtil.parseJSONToBean(order.toString(), OrderInfoDetail.class);
			oid.setUsetime(new Date(order.getLong("usetime")));
			int lasttime = (int)Math.ceil((double)(oid.getUsetime().getTime()-System.currentTimeMillis())/1000/60);
			lasttime = lasttime < 0?0:lasttime;
			oid.setLasttime(StringUtil.formatCostTime(lasttime));
			oid.setIsusenow(oid.isIsusenow());
			if(OrderEnum.ORDERTYPE_TAXI.code.equals(oid.getType())) {
				oid.setOrderstyle("1");
			} else {
				oid.setOrderstyle("0");
			}
			if(orderInfo.getOrder() instanceof OpTaxiOrder) {
//				oid.setGrabtime(taxisendrules.getDriversendinterval());
				String tripremark = oid.getTripremark();
				if(StringUtils.isNotBlank(tripremark)) {
					tripremark = "," + tripremark;
				}
				int meterrange = oid.getMeterrange();
				switch(meterrange) {
					case 0: tripremark = "不愿打表来接" + tripremark;break;
					case 3:
					case 5:
					case 10: tripremark = meterrange + "公里打表来接" + tripremark;break;
					default: tripremark = tripremark.replace(",", "");
				}
				oid.setRemark(tripremark);
			} else {
//				oid.setGrabtime(sendrule.getDriversendinterval());
			}
			orderInfo.setOrderinfo(oid);
			orderInfo.getOrder().setDriverid(oid.getDriverid());
			orderInfo.getOrder().setVehicleid(oid.getVehicleid());
			orderInfo.getOrder().setOrdertime(oid.getOrdertime());
			orderInfo.getOrder().setOrderstatus(oid.getStatus());
			return oid;
		}
		logger.error("获取订单失败");
		return null;
	}
	
	/**
	 * 变更订单状态
	 * @param orderInfo
	 */
	public void changeOrderState(OrderInfoParam orderInfo){
		OrderApiParam oap = new OrderApiParam();
		oap.setOrderno(orderInfo.getOrder().getOrderno());
		if(orderInfo.getOrder() instanceof OrgOrder) {
			oap.setOrderprop(OrderVarietyEnum.LEASE_NET.icode);
		} else if(orderInfo.getOrder() instanceof OpOrder) {
			oap.setOrderprop(OrderVarietyEnum.OPERATING_NET.icode);
		} else {
			oap.setOrderprop(OrderVarietyEnum.OPERATING_TAXI.icode);
		}
		oap.setOrderstate(OrderState.MANTICSEND.state);
		oap.setUsetype(orderInfo.getOrder().getUsetype());
		oap.setOrdertype(orderInfo.getOrder().getOrdertype());
		carserviceTemplate.dealRequestWithToken(
				"/OrderApi/ChangeOrderState", 
				HttpMethod.POST, 
				null,
				oap, 
				JSONObject.class);
	}
	
	/**
	 * 获取派单规则
	 * @param orderInfo
	 * @return
	 */
	public void getSendRule(OrderInfoParam orderInfo){
		JSONObject result = new JSONObject();
		GetSendInfoParam gsp = new GetSendInfoParam();
		gsp.setCompanyid(orderInfo.getOrder().getCompanyid());
		gsp.setCity(orderInfo.getOrder().getOncity());
		gsp.setUsetype(orderInfo.getOrder().getUsetype());
		if(orderInfo.getOrder() instanceof OrgOrder){
			gsp.setOrderprop(0);
		}else if(orderInfo.getOrder() instanceof OpOrder){
			gsp.setOrderprop(1);
		}else if(orderInfo.getOrder() instanceof OpTaxiOrder) {
			gsp.setOrderprop(2);
			gsp.setUsevechiletype(orderInfo.getOrder().isIsusenow() ? "1" : "0");
		}
		result = carserviceTemplate.dealRequestWithToken(
				"/OrderApi/GetSendRule", 
				HttpMethod.POST, 
				null, 
				gsp, 
				JSONObject.class);
		if(result.getInt("status") != Retcode.OK.code) return;
		removeNullObejct(result);
		if(orderInfo.getOrder() instanceof OrgOrder || orderInfo.getOrder() instanceof OpOrder){
			sendrule = StringUtil.parseJSONToBean(result.getJSONObject("sendrule").toString(), PubSendrules.class);
		} else {
			taxisendrules = StringUtil.parseJSONToBean(result.getJSONObject("sendrule").toString(), OpTaxisendrules.class);
		}
	}
	
	/**
	 * 获取特殊司机
	 * @param orderInfo
	 * @return
	 */
	public List<PubDriver> getSPDrivers(OrderInfoParam orderInfo){
		JSONObject result = new JSONObject();
		PubDriverInBoundParam pdbp = new PubDriverInBoundParam();
		//加上下单人,用于获取特殊司机
		pdbp.setUserid(orderInfo.getOrder().getUserid());
		//加上订单号,用于更新订单推送的司机数
		pdbp.setOrderno(orderInfo.getOrder().getOrderno());
		//加上城市和租赁公司验证
		pdbp.setOrganid(orderInfo.getOrderinfo().getOrganid());
		pdbp.setCompanyid(orderInfo.getOrder().getCompanyid());
		pdbp.setCity(orderInfo.getOrder().getOncity());
		pdbp.setAllowgrade(SendRulesEnum.VEHICLEUPGRADE_UPGRADE.code.equals(sendrule.getVehicleupgrade()+""));
		pdbp.setCartype(orderInfo.getOrder().getSelectedmodel());
		pdbp.setUsenow(orderInfo.getOrderinfo().isIsusenow());
		pdbp.setVehicletype(Integer.valueOf(DriverEnum.DRIVER_TYPE_CAR.code));
		pdbp.setOrderno(orderInfo.getOrder().getOrderno());
		pdbp.setOrdertype(orderInfo.getOrder().getOrdertype());
		pdbp.setUsetype(orderInfo.getOrder().getUsetype());
		result = carserviceTemplate.dealRequestWithToken(
				"/PubDriver/GetSpecialDriver", 
				HttpMethod.POST, 
				null, 
				pdbp, 
				JSONObject.class);
		if(result.getInt("status") != Retcode.OK.code) return null;
		List<PubDriver> drivers = new ArrayList<>();
		for(Object o : result.getJSONArray("list")){
			JSONObject driver = (JSONObject)o;
			drivers.add(StringUtil.parseJSONToBean(driver.toString(), PubDriver.class));
		}
		return drivers;
	}
	
	/**
	 * 获取普通司机
	 * @param orderInfo
	 * @return
	 */
	public List<PubDriver> getCMDrivers(OrderInfoParam orderInfo,int times){
		return new ArrayList<>();
//		JSONObject result = new JSONObject();
//		StringBuffer alreadySendDrivers = new StringBuffer();
//		for(PubDriver pd : driverList){
//			alreadySendDrivers.append(",'").append(pd.getId()).append("'");
//		}
//		//上车地址经纬度
//		double lng = orderInfo.getOrder().getOnaddrlng();
//		double lat = orderInfo.getOrder().getOnaddrlat();
//		//即刻和预约首次轮半径不同.需要分别计算
//		double[] range;
//		if(orderInfo.getOrderinfo().isIsusenow()){
//			if(times == 1)
//				range = BaiduUtil.getRange(lng, lat, lsr.getFirstRadiusCur() * 1000);
//			else
//				range = BaiduUtil.getRange(lng, lat, lsr.getSecondRadiusCur() * 1000);
//		}else{
//			if(times == 1)
//				range = BaiduUtil.getRange(lng, lat, lsr.getFirstRadius() * 1000);
//			else
//				range = BaiduUtil.getRange(lng, lat, lsr.getSecondRadius() * 1000);
//		}
//		PubDriverInBoundParam pdbp = new PubDriverInBoundParam(range);
//		//加上订单号,用于更新订单推送的司机数
//		pdbp.setOrderno(orderInfo.getOrder().getOrderno());
//		pdbp.setPushcount(driverList.size());
//		//加上城市和租赁公司验证
//		pdbp.setOrganid(orderInfo.getOrderinfo().getOrganid());
//		pdbp.setCompanyid(orderInfo.getOrder().getCompanyid());
//		pdbp.setCity(orderInfo.getOrder().getOncity());
//		pdbp.setAllowgrade(lsr.getModels().equals("1"));
//		pdbp.setCartype(orderInfo.getOrder().getSelectedmodel());
//		pdbp.setUsenow(orderInfo.getOrderinfo().isIsusenow());
//		pdbp.setVehicletype(Integer.valueOf(DriverEnum.DRIVER_TYPE_CAR.code));
//		//加上下单人，用于筛除司机本人
//        pdbp.setUserid(orderInfo.getOrder().getUserid());	
//		if(alreadySendDrivers.length() > 1) pdbp.setAlreadySendDrivers(alreadySendDrivers.substring(1));
//		String url = "";
//		if(pdbp.getOrganid() == null || pdbp.getOrganid().trim().isEmpty()){
//			url = "/PubDriver/GetOpDriverInBound";
//		}else{
//			url = "/PubDriver/GetLeDriverInBound";
//		}
//		result = carserviceTemplate.dealRequestWithToken(
//				url, 
//				HttpMethod.POST, 
//				null,
//				pdbp, 
//				JSONObject.class);
//		if(result.getInt("status") != Retcode.OK.code) return null;
//		List<PubDriver> cmdrivers = new ArrayList<>();
//		for(Object o : result.getJSONArray("list")){
//			JSONObject driver = (JSONObject)o;
//			cmdrivers.add(StringUtil.parseJSONToBean(driver.toString(), PubDriver.class););
//		}
//		return cmdrivers;
	}
	
	/**
	 * 根据规则获取符合的司机列表
	 * @param orderInfo
	 * @param times
	 * @return
	 */
	public List<PubDriver> getDrivers(OrderInfoParam orderInfo,int times){
		List<PubDriver> drivers = new ArrayList<>();
		//特殊司机派单
		if(times == 0){
			drivers = getSPDrivers(orderInfo);
		}else {
			drivers = getCMDrivers(orderInfo,times);
		}
		if(drivers != null && !drivers.isEmpty()){
			driverList.addAll(drivers);
		}
		return drivers;
	}
	
	
	/**
	 * 派单
	 * @param orderInfo
	 * @param times
	 */
	public void sendMessage(OrderInfoParam orderInfo,int times){
//		OrderInfoDetail order  = getOrder(orderInfo);
//
//		List<PubDriver> drivers = getDrivers(orderInfo, times);
//		List<String> userids = new ArrayList<>();
//		for(PubDriver pd : drivers){
//			userids.add(pd.getPhone());
//		}
//        filterDriver(userids, order);
//        
//		if(userids == null || userids.isEmpty()){
//			logger.info("订单:" + orderInfo.getOrder().getOrderno() + "本轮未抓取到司机.");
//		}else{
//			StringBuffer sb = new StringBuffer();
//			for(String phone : userids){
//				sb.append(phone).append(",");
//			}
//			if(times == 0){
//				logger.info("订单:" + orderInfo.getOrder().getOrderno() + "抓取到的特殊司机:" + sb.toString());
//			}else if(times == 1){
//				logger.info("订单:" + orderInfo.getOrder().getOrderno() + "抓取到的首轮司机:" + sb.toString());
//			}else if (times == 2) {
//				logger.info("订单:" + orderInfo.getOrder().getOrderno() + "抓取到的次轮司机:" + sb.toString());
//			}
//		}
//		if(userids == null || userids.isEmpty()) return;
//		if(isTest){
//			//计算抢单结束时限
//			Date grabEndTime = StringUtil.addDate(orderInfo.getOrder().getUndertime(), lsr.getGeneralInterval() * 60);
//			saveDriverMessage(orderInfo.getOrder(),drivers,grabEndTime);
//		}
//		PushPayload pushobj4ios = PushObjFactory.createGrabOrderObj4IOS(order, userids);
//		PushPayload pushobj4android = PushObjFactory.createGrabOrderObj4Android(order, userids);
//		//直接发送
//		AppMessageUtil.send(pushobj4ios,pushobj4android,AppMessageUtil.APPTYPE_DRIVER);
	}

    /**
     * 过滤掉不符合条件的司机
     * @param order 订单信息
     */
	@SuppressWarnings({ "unchecked", "unused" })
	private void filterDriver(List<String> drivers, OrderInfoDetail order){
	    try{
            JSONObject result = this.carserviceTemplate.dealRequestWithToken(
                    "/PubDriver/GetUnExceptDriver",
                    HttpMethod.POST,
                    null,
                    order,
                    JSONObject.class);

            if(result.getInt("status") != Retcode.OK.code) { // 如果筛选异常， 先不过滤
                return;
            }
            List<String> unexcept = (List<String>)result.get("list");
            drivers.removeAll(unexcept);
            //过滤司机与下单人为同一人
            if(drivers.contains(order.getUserphone()))
                drivers.remove(order.getUserphone());
            
        }catch (Exception e){
	        logger.error("",e);
        }
    }

	/**
	 * 保存司机抢单消息
	 * @param orderinfo
	 */
	@SuppressWarnings("unused")
	private void saveDriverMessage(AbstractOrder orderinfo,List<PubDriver> drivers,Date grabEndTime){
		String remark = "",headimage = "";
		String fileserver = SystemConfig.getSystemProperty("fileserver");
		if(orderinfo instanceof OpTaxiOrder){
//		}else if (orderinfo instanceof OrgTaxiOrder) {
			int meterrange = ((OpTaxiOrder)orderinfo).getMeterrange();
			headimage = ((OpTaxiOrder)orderinfo).getPassengericonmax();
			headimage = (headimage == null || headimage.trim().isEmpty()) ? "" : fileserver + headimage;
			remark = (meterrange == 0 ? "" : "(" + meterrange + "公里打表来接)") + orderinfo.getTripremark();
		}else if (orderinfo instanceof OrgOrder) {
			headimage = "";
			remark = orderinfo.getTripremark();
		}else if (orderinfo instanceof OpOrder) {
			headimage = "";
			remark = orderinfo.getTripremark();
		}
		DriverMessage message = new DriverMessage();
		message.setCreatetime(new Date());
		message.setNewstype(DriverMessageEnum.NEWS_TYPE_TAKEORDER.code);
		JSONObject orderJson = new JSONObject();
		orderJson.put("orderno", orderinfo.getOrderno());
		orderJson.put("usetype", orderinfo.getUsetype());
		orderJson.put("ordertype", orderinfo.getOrdertype());
		orderJson.put("type",OrderEnum.getOrderType(orderinfo.getOrdertype()).msg);
		orderJson.put("onaddr", orderinfo.getOnaddress());
		orderJson.put("offaddr", orderinfo.getOffaddress());
		orderJson.put("usetime", orderinfo.getUsetime());
		orderJson.put("usenow", orderinfo.isIsusenow() ? "即刻用车" : "预约用车");
		orderJson.put("lasttime", StringUtil.formatOrderStatus(orderinfo.getUsetime(), orderinfo.getOrderstatus()));//剩余时间
        orderJson.put("usetimeLong",orderinfo.getUsetime().getTime());
        orderJson.put("userid", orderinfo.getUserid());
		//下面的字段都是在任务消息里才会有
		orderJson.put("grabendtime", grabEndTime.getTime());
		orderJson.put("headimage", headimage);
		orderJson.put("remark", remark);
		JSONObject takecashinfo = new JSONObject();
		takecashinfo.put("remark", "");
		takecashinfo.put("bank", "");
		takecashinfo.put("amount", 0);
		takecashinfo.put("applytime", 0);
		message.setOrderinfo(orderJson);
		message.setTakecashinfo(takecashinfo);
		
		//订单信息转为字符串
		String value = JSONObject.fromObject(message).toString();
		for(PubDriver pd : drivers){
			String key = "DriverGrabMessage_" + pd.getId() + "_" + pd.getPhone()+"_" + orderinfo.getOrderno();
			//抢单结束时间比现在时间晚,才保存
			if(grabEndTime != null && grabEndTime.after(new Date())){
				JedisUtil.setString(key, (int)((grabEndTime.getTime() - System.currentTimeMillis())/1000), value);
			}
		}
	}


	/**
	 * 判断订单状态
	 * <p>如果已接单则不继续派单   返回false 
	 *  <p>如果未接单则继续派单      返回true
	 * @param orderInfo
	 * @return
	 */
	public boolean checkOrderState(OrderInfoParam orderInfo){
		OrderInfoDetail order = getOrder(orderInfo);
		//订单已取消
		if(OrderState.CANCEL.state.equals(order.getStatus()))
			return false;
		//订单已进入人工派单
		if (OrderState.MANTICSEND.state.equals(order.getStatus())) 
			return false;
		//司机不为空
		if(order.getDriverid() != null && !order.getDriverid().isEmpty())
			return false;
		else
			return true;
	}
	
	
	public void removeNullObejct(Object json){
		List<String> rmKeys = new ArrayList<>();
		if(json instanceof JSONObject){
			JSONObject temp = (JSONObject) json;
			for(Object key :temp.keySet()){
				Object o = temp.get(key);
				if(o instanceof JSONArray){
					for(Object arrO : ((JSONArray)o)){
						removeNullObejct(arrO);
					}
				}else if(o instanceof JSONObject){
					JSONObject tempO = (JSONObject)o;
					if(tempO.isNullObject()){
						if(((String)key).toLowerCase().contains("time")){
							//保存key,删除空字段
							rmKeys.add((String)key);
						}else{
							//将null字段替换为空字符串
							temp.put(key,"");
						}
//					}else if (((String)key).toLowerCase().contains("time")) {
//						Date d = new Date(tempO.getLong("time"));
//						String datetime = StringUtil.formatDate(d, "yyyy-MM-dd HH:mm:ss");
//						temp.put(key,datetime);
					}else{
						removeNullObejct(tempO);
					}
				}
			}
			//删除空字段
			for(String key : rmKeys){
				temp.remove(key);
			}
		}else if(json instanceof JSONArray){
			for(Object arrO : ((JSONArray)json)){
				removeNullObejct(arrO);
			}
		}
	}
	
	/**
	 * 转换AbstractOrder成OrderInfoDetail
	 * @param orderinfo
	 * @return
	 */
	private OrderInfoDetail convert2OrderInfoDetail(AbstractOrder orderinfo){
		OrderInfoDetail order = new OrderInfoDetail();
		order.setOrderno(orderinfo.getOrderno());
		order.setType(orderinfo.getOrdertype());
		order.setUsetype(orderinfo.getUsetype());
		order.setCartype(orderinfo.getSelectedmodelname());
		order.setStatus(orderinfo.getOrderstatus());
		order.setCityid(orderinfo.getOncity());
		order.setCompanyid(orderinfo.getCompanyid());
		order.setUsetime(orderinfo.getUsetime());
		order.setPassengers(orderinfo.getPassengers());
		order.setPassengerphone(orderinfo.getPassengerphone());
		order.setOnaddress(orderinfo.getOnaddress());
		order.setOffaddress(orderinfo.getOffaddress());
		order.setOnlat(orderinfo.getOnaddrlat());
		order.setOnlng(orderinfo.getOnaddrlng());
		order.setOfflat(orderinfo.getOffaddrlat());
		order.setOfflng(orderinfo.getOffaddrlng());
		order.setEstimatedtime(orderinfo.getEstimatedtime());
		order.setEstimatedmileage(orderinfo.getEstimatedmileage());
		order.setEstimatedcost(orderinfo.getEstimatedcost());
		order.setIsusenow(orderinfo.isIsusenow());
		order.setRemark(orderinfo.getTripremark());
		order.setGrabtime(0);   //强转模式没有抢单时间
		int times = (int)(orderinfo.getEndtime().getTime() - orderinfo.getStarttime().getTime()) / 1000;
		order.setTimes(times);
		order.setMileage(orderinfo.getMileage());
		order.setOrderamount(orderinfo.getOrderamount());
		//车辆类型 网约车 | 出租车
		if(orderinfo instanceof OpTaxiOrder){
			order.setOrderprop(2);
			order.setOrderstyle(VehicleEnum.VEHICLE_TYPE_TAXI.code);
		}else if (orderinfo instanceof OrgOrder) {
			order.setOrderprop(0);
			order.setOrderstyle(VehicleEnum.VEHICLE_TYPE_CAR.code);
		}else{
			order.setOrderprop(1);
			order.setOrderstyle(VehicleEnum.VEHICLE_TYPE_CAR.code);
		}
		return order;
	}
	
	public void sendOrderFlow(OrderInfoParam orderInfo) throws Exception{
//		if(orderInfo.getOrder() instanceof OrgOrder){
//			//特殊司机派单
//			logger.info("订单:"+orderInfo.getOrder().getOrderno()+"进入特殊司机派单流程");
//			sendMessage(orderInfo,0);
//			if(driverList.size() > 0){
//				Thread.sleep(lsr.getSpecialInterval() * 1000);
//			}
//			if(!checkOrderState(orderInfo)){
//				if (OrderState.CANCEL.state.equals(orderInfo.getOrderinfo().getStatus())) {
//					logger.info("订单:"+orderInfo.getOrder().getOrderno()+"已取消.");
//				}else if (OrderState.MANTICSEND.state.equals(orderInfo.getOrderinfo().getStatus())) {
//					logger.info("订单:"+orderInfo.getOrder().getOrderno()+"已进入人工派单.");
//				}else{
//					logger.info("订单:"+orderInfo.getOrder().getOrderno()+"被特殊司机"+ orderInfo.getOrder().getDriverid() +"抢走");
//				}
//				return;
//			}
//		}
//		//所有派单流程如果规则允许升级车型则不限制级别,否则限制当前车型的司机
//		//首轮派单
//		logger.info("订单:"+orderInfo.getOrder().getOrderno()+"进入首轮派单流程");
//		sendMessage(orderInfo,1);
//		//首轮派单时限
//		Thread.sleep(lsr.getFirstInterval() * 1000);
//		//次轮派单
//		if(checkOrderState(orderInfo)){
//			logger.info("订单:"+orderInfo.getOrder().getOrderno()+"进入次轮派单流程");
//			sendMessage(orderInfo,2);
//		}else{
//			if (OrderState.CANCEL.state.equals(orderInfo.getOrderinfo().getStatus())) {
//				logger.info("订单:"+orderInfo.getOrder().getOrderno()+"已取消.");
//			}else if (OrderState.MANTICSEND.state.equals(orderInfo.getOrderinfo().getStatus())) {
//				logger.info("订单:"+orderInfo.getOrder().getOrderno()+"已进入人工派单.");
//			}else{
//				logger.info("订单:"+orderInfo.getOrder().getOrderno()+"被司机"+ orderInfo.getOrder().getDriverid() +"抢走");
//			}
//			return;
//		}
//		//普通派单时限
//		Thread.sleep(lsr.generalInterval * 1000 - lsr.getFirstInterval() * 1000);
//		//人工派单
//		if(checkOrderState(orderInfo)){
//			//清空已通知司机列表
//			driverList.clear();
//			//更改订单状态为待人工派单
//			changeOrderState(orderInfo);
//			logger.info("订单:"+orderInfo.getOrder().getOrderno()+"进入人工派单流程");
//		}else{
//			if (OrderState.CANCEL.state.equals(orderInfo.getOrderinfo().getStatus())) {
//				logger.info("订单:"+orderInfo.getOrder().getOrderno()+"已取消.");
//			}else if (OrderState.MANTICSEND.state.equals(orderInfo.getOrderinfo().getStatus())) {
//				logger.info("订单:"+orderInfo.getOrder().getOrderno()+"已进入人工派单.");
//			}else{
//				logger.info("订单:"+orderInfo.getOrder().getOrderno()+"被司机"+ orderInfo.getOrder().getDriverid() +"抢走");
//			}
//			return;
//		}
	}
	//=======================Util Method===========================//
	public String formatDate(Date src,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);
		return sdf.format(src);
	}
	
	public String getRandomNumber(int len){
		String str = "0123456789";
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++){
			sb.append(str.charAt((int)(Math.random()*str.length())));
		}
		return sb.toString();
	}
}
