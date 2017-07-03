package com.szyciov.message;

import cn.jpush.api.push.model.PushPayload;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.CancelParty;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.OrderInfoParam;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.PushObjFactory;
import com.szyciov.util.SMMessageUtil;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.SendOrderUtil;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaxiOrderMessage extends OrderMessage {
	
	public static final String TAXI_EXCEPTIONORDER = "出租车订单复核";
	
	public static final String TAXI_NOTREVIEW = "出租车拒绝复核";
	
	public static final String TAXI_CHANGEVEHICLE = "出租车更换车辆";
	
	public static final String TAXI_MANTICORDER = "出租车人工指派订单";
	
	public static final String TAXI_SENDORDERFAIL = "出租车派单失败";
	
	public static final String TAXI_CANCELORDER = "出租车取消订单";
	
	public static final String TAXI_REMINDORDER = "订单行程提醒";
	
	public static final String TAXI_FINISHORDER = "订单行程结束";
	
	public static final String TAXI_TAKEORDER = "已接单";
	
	//订单司机在接乘客路上这种hint提示信息
	public static final String TAXI_ORDERHINT = "hint信息";
	
	public static final String TAXI_DRIVERMESSAGE = "司机hint消息";
	
	private Map<String, Object> extrainfo;
	
	@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT, property = "@id")
	@JsonSubTypes({
		@JsonSubTypes.Type(value = OpTaxiOrder.class, name = "OpTaxiOrder")
	})
	private AbstractOrder order;
	
	private String messagetype;
	
	private List<String> phones;
	
	/**
	 * 需要额外参数的消息构造函数
	 * @param order
	 * @param messagetype
	 * @param extrainfo
	 */
	public TaxiOrderMessage(AbstractOrder order, String messagetype, Map<String,Object> extrainfo){
		this.order = order;
		this.messagetype = messagetype;
		this.extrainfo = extrainfo;
	}
	
	/**
	 * 不需要额外参数的消息构造函数
	 * @param order
	 * @param messagetype
	 */
	public TaxiOrderMessage(AbstractOrder order,String messagetype){
		this.order = order;
		this.messagetype = messagetype;
	}
	
	
	/**
	 * 推送给其他已经推送过抢单信息的司机
	 * @param messagetype
	 * @param hint4driver
	 * @param phones
	 */
	public TaxiOrderMessage(String messagetype,List<String> phones) {
		this.messagetype = messagetype;
		this.phones = phones;
	}

	public TaxiOrderMessage() {
		
	}
	
	@Override
	public void send() {
		if(TAXI_EXCEPTIONORDER.equals(messagetype)) { //出租车订单复核
			taxiExceptionOrder();
		} else if(TAXI_NOTREVIEW.equals(messagetype)) { //出租车拒绝复核
			taxiNotreview();
		} else if(TAXI_CHANGEVEHICLE.equals(messagetype)) { //出租车更换车辆
			taxiChangevehicle();
		} else if(TAXI_MANTICORDER.equals(messagetype)) { //出租车人工指派
			taxiManticorder();
		} else if(TAXI_SENDORDERFAIL.equals(messagetype)) { //出租车派单失败
			taxiSendorderfail();
		} else if(TAXI_CANCELORDER.equals(messagetype)) { //出租车取消订单
			taxiCancelorder();
		}else if(TAXI_TAKEORDER.equals(messagetype)){ //出租车订单已被接走
			taxitakeOrder();
		}else if (TAXI_ORDERHINT.equals(messagetype)) {  //出租车订单流程信息
			taxiOrderhint();
		}else if (TAXI_DRIVERMESSAGE.equals(messagetype)) {
			taxiHint4Driver();
		}
	}
	
	/**
	 * 已接单消息
	 */
	private void taxitakeOrder(){
		//司机已接单消息
		//先发送apphint消息
		//下单来源是乘客，司机接单才推送乘客端
		if (OrderEnum.ORDERSOURCE_PASSENGER_ANDROID.code.equalsIgnoreCase(order.getOrdersource())
				|| OrderEnum.ORDERSOURCE_PASSENGER_IOS.code.equalsIgnoreCase(order.getOrdersource())
				|| OrderEnum.ORDERSOURCE_PASSENGER_WECHAT.code.equalsIgnoreCase(order.getOrdersource())) {
			taxiOrderhint();
		}
		if(order instanceof OrgOrder){
			//机构订单
			logger.info("出租车没有机构订单目前");
		}else{
			//个人订单
			logger.info("开始发送短信给相关用户");
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
			Map<String,Object> carinfo = MessageAPIUtil.getCarInfo(order.getVehicleid());
			Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
			String passengerphone = order.getPassengerphone();
			String onaddress = order.getOnaddress();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String usertimestr = format.format(order.getUsetime());
			Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
			if(userinfo!=null&&driverinfo!=null&&carinfo!=null&&opinfo!=null){
				logger.info("获取到相关信息");
				String driverstr = driverinfo.get("name")+"师傅"+driverinfo.get("phone");
				String carstr = ""+carinfo.get("color")+carinfo.get("vehcbrandname")+carinfo.get("vehclinename")+carinfo.get("plateno");
				String servicephone = (String) opinfo.get("servcietel");
				String companyname = "运管端";
				String userphone = (String) userinfo.get("account");
				if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
					logger.info("乘车人和坐车人不同的消息发送");
					//乘车人不同
					String username = (String) userinfo.get("nickname");
					String passenggercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.passenger", username,usertimestr,onaddress,driverstr,carstr,servicephone);
					List<String> passenggerphones = new ArrayList<String>();
					passenggerphones.add(passengerphone);
					SMMessageUtil.send(passenggerphones, passenggercontent);
					// 发送给下单人
					String usercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.user", order.getPassengers(),usertimestr,driverstr,carstr,servicephone);
					List<String> userphones = new ArrayList<String>();
					userphones.add(userphone);
					SMMessageUtil.send(userphones, usercontent);
				}else{
					logger.info("乘车人和坐车人相同同的消息发送");
					String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.userpassentger", usertimestr,driverstr,carstr,servicephone);
					List<String> phones = new ArrayList<String>();
					phones.add(userphone);
					SMMessageUtil.send(phones, content);
				}
			}else{
				logger.info("发送短信给用户时，没有获取到相关信息，无法发送");
			}
		}
		logger.info("这里是已接单消息，已部分实现");
	}
	
	/**
	 * 出租车订单复核
	 */
	private void taxiExceptionOrder() {
		String reviewperson = order.getReviewperson();
		double price = (double) extrainfo.get("price");
		String paymentstatus = order.getPaymentstatus();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String usertimestr = format.format(order.getUsetime());
		double cost = order.getOrderamount();
		Map<String,Object> reviewinfo = new HashMap<String,Object>();
		reviewinfo.put("orderno", order.getOrderno());
		reviewinfo.put("usetime", usertimestr);
		reviewinfo.put("ordertype", order.getOrdertype());
		reviewinfo.put("onaddress", order.getOnaddress());
		reviewinfo.put("offaddress", order.getOffaddress());
		reviewinfo.put("usetype", order.getUsetype());
		if("1".equalsIgnoreCase(reviewperson)){ //司机申请复核
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
			String driverphone = (String) driverinfo.get("phone");
			List<String> userids = new ArrayList<String>();
			userids.add(driverphone);
			if(price < 0){ //复核金额大于原订单金额
				String content = "行程费金额应为" + cost + "元,差额部分不计入贡献";
				reviewinfo.put("content", content);
				PushPayload payload4ios = PushObjFactory.createOrderReviewObj4IOS(reviewinfo, userids);
				PushPayload payload4android = PushObjFactory.createOrderReviewObj4Android(reviewinfo, userids);
				AppMessageUtil.send(payload4ios,payload4android, AppMessageUtil.APPTYPE_DRIVER);
			}else if(price > 0){ //复核金额小于原订单金额
				String content = "行程费金额应为" + cost + "元";
				reviewinfo.put("content", content);
				PushPayload payload4ios = PushObjFactory.createOrderReviewObj4IOS(reviewinfo, userids);
				PushPayload payload4android = PushObjFactory.createOrderReviewObj4Android(reviewinfo, userids);
				AppMessageUtil.send(payload4ios,payload4android, AppMessageUtil.APPTYPE_DRIVER);
			} else if(price == 0) { //未产生差异金额
				String content = "经核实，车费金额正确。";
				reviewinfo.put("content", content);
				PushPayload payload4ios = PushObjFactory.createOrderReviewObj4IOS(reviewinfo, userids);
				PushPayload payload4android = PushObjFactory.createOrderReviewObj4Android(reviewinfo, userids);
				AppMessageUtil.send(payload4ios,payload4android, AppMessageUtil.APPTYPE_DRIVER);
			}
		}else{//下单人申请复核
			Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
			if(null == opinfo) {
				return;
			}
			Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
			String smscontent = null;
			if(price < 0 && !PayState.NOTPAY.state.equalsIgnoreCase(paymentstatus)){ //乘客少给且已支付
//				smscontent = "您的复核审请已处理完毕，存异金额{0}元已为您免单。如有疑问，请联系{1}（{2}）";
				//smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.exceptionorder.backmoney", Math.abs(price), opinfo.get("servcietel"), opinfo.get("companyshortname"));
				smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.exceptionorder.backmoney", Math.abs(price), opinfo.get("servcietel"));
			} else if(price == 0) { //复核前后金额一样
//				smscontent = "您的复核审请已处理完毕，经核实未产生存异金额。如有疑问，请联系{0}（{1}）";
				//smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.exceptionorder.notreview", opinfo.get("servcietel"), opinfo.get("companyshortname"));
				smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.exceptionorder.notreview", opinfo.get("servcietel"));
			}
			if(null != smscontent) {
				List<String> phones = new ArrayList<String>();
				phones.add((String)userinfo.get("account"));
				SMMessageUtil.send(phones, smscontent);
			}
		}
	}
	
	/**
	 * 不受理复核
	 */
	private void taxiNotreview() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String usertimestr = format.format(order.getUsetime());
		if("1".equalsIgnoreCase(order.getReviewperson())){ //司机申请复核
			//司机复核
			Map<String,Object> reviewinfo = new HashMap<String,Object>();
			reviewinfo.put("orderno", order.getOrderno());
			reviewinfo.put("usetime", usertimestr);
			reviewinfo.put("ordertype", order.getOrdertype());
			reviewinfo.put("onaddress", order.getOnaddress());
			reviewinfo.put("offaddress", order.getOffaddress());
			reviewinfo.put("usetype", order.getUsetype());
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
			String driverphone = (String) driverinfo.get("phone");
			List<String> userids = new ArrayList<String>();
			userids.add(driverphone);
			String content = "经核实，车费金额正确。";
			reviewinfo.put("content", content);
			PushPayload payload4ios = PushObjFactory.createOrderReviewObj4IOS(reviewinfo, userids);
			PushPayload payload4android = PushObjFactory.createOrderReviewObj4Android(reviewinfo, userids);
			AppMessageUtil.send(payload4ios,payload4android, AppMessageUtil.APPTYPE_DRIVER);
		}else{
			Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
			if(opinfo!=null){
				Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
//				String smscontent = "您的复核审请已处理完毕，经核实未产生存异金额。如有疑问，请联系{0}（${1}）";
				//String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.exceptionorder.notreview", opinfo.get("servcietel"), opinfo.get("companyshortname"));
				String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.exceptionorder.notreview", opinfo.get("servcietel"));
				List<String> phones = new ArrayList<String>();
				phones.add((String)userinfo.get("account"));
				SMMessageUtil.send(phones, smscontent);
			}
		}
	}
	
	/**
	 * 出租车人工派单
	 */
	private void taxiManticorder() {
		SendOrderUtil s = new SendOrderUtil();
		OrderInfoParam param = new OrderInfoParam();
		param.setOrder(order);
		OrderInfoDetail orderinfo = s.getOrder(param);
		//只有已接单的订单才需要发送消息
		if(StringUtils.isNotBlank(order.getDriverid())){
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
			//人工派单消息，发送消息给司机端，推送方式
			if(null == driverinfo.get("passworkstatus") || !DriverEnum.PASS_WORK_STATUS_PENDING.code.equals(driverinfo.get("passworkstatus"))) {
				List<String> userids = new ArrayList<String>();
				userids.add((String) driverinfo.get("phone"));
				PushPayload pushload4ios = PushObjFactory.createTaskOrderObj4IOS(orderinfo, userids);
				PushPayload pushload4android = PushObjFactory.createTaskOrderObj4Android(orderinfo, userids);
				AppMessageUtil.send(pushload4ios,pushload4android, AppMessageUtil.APPTYPE_DRIVER);
			}
			//人工派单消息，发送消息给乘客端，短信方式
			Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
			Map<String,Object> carinfo = MessageAPIUtil.getCarInfo(order.getVehicleid());
			Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
			String passengerphone = order.getPassengerphone();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String usertimestr = format.format(order.getUsetime());
			if(userinfo!=null&&driverinfo!=null&&carinfo!=null&&opinfo!=null){
				logger.info("获取到相关信息");
				//给下单人、乘车人发短信
				String carstr = ""+carinfo.get("color")+carinfo.get("vehcbrandname")+carinfo.get("vehclinename")+carinfo.get("plateno");
				String servicephone = (String) opinfo.get("servcietel");
				String companyname = (String) opinfo.get("companyshortname");
				String userphone = (String) userinfo.get("account");
				Set<String> phoneSet = new HashSet<String>();
				phoneSet.add(userphone);
				phoneSet.add(passengerphone);
				List<String> userphones = new ArrayList<String>(phoneSet);
//				String content = "您的用车服务预订成功，用车时间：{0},服务车辆：{1}。如需帮助，请联系{2}（{3}）";
				//String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.manticorder.user", usertimestr, carstr, servicephone, companyname);
				String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.manticorder.user", usertimestr, carstr, servicephone);
				SMMessageUtil.send(userphones, content);
				//给司机发短信
//				String content = "客服为您指派了一个新订单，用车时间：{0}，请登录司机端查看。如有疑问，请联系{1}（{2}）";
				String driverContent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.manticorder.driver", usertimestr, servicephone);
				List<String> dirphone = new ArrayList<String>();
				dirphone.add((String)driverinfo.get("phone"));
				SMMessageUtil.send(dirphone, driverContent);
			}else{
				logger.info("发送短信给用户时，没有获取到相关信息，无法发送");
			}
		}
		//告诉乘客下单的结果，界面变化
		taxiOrderhint();
		logger.info("这里是出租车人工指派消息，已实现");
	}
	
	/**
	 * 出租车更换车辆
	 */
	private void taxiChangevehicle() {
		//订单详细信息
		SendOrderUtil s = new SendOrderUtil();
		OrderInfoParam param = new OrderInfoParam();
		param.setOrder(order);
		OrderInfoDetail orderinfo = s.getOrder(param);
		Map<String, Object> opinfo = MessageAPIUtil.getOpInfo();
		if(null == opinfo) {
			return;
		}
		String servcietel = (String) opinfo.get("servcietel");
		String companyshortname = (String) opinfo.get("companyshortname");
		//发送给旧司机的信息
		if(extrainfo!=null){
			String olddriverid = (String) extrainfo.get("olddriverid");
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(olddriverid);
			if(driverinfo!=null){
				String olddriverphone = (String) driverinfo.get("phone");
//				String content = "您的{0}订单已更换为其他车辆服务。如有疑问，请联系{1}（{2}）";
				String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.changevehicle.olddriver", order.getOrderno(), servcietel);
				List<String> phones = new ArrayList<String>();
				phones.add(olddriverphone);
				SMMessageUtil.send(phones, content);
				
				PushPayload pushload4ios = PushObjFactory.createChangeOrderObj4IOS(orderinfo, phones);
				PushPayload pushload4android = PushObjFactory.createChangeOrderObj4Android(orderinfo, phones);
				AppMessageUtil.send(pushload4ios,pushload4android, AppMessageUtil.APPTYPE_DRIVER);
			}
		}
		
		//发送给新司机
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String usertimestr = format.format(order.getUsetime());
		Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
		if(driverinfo!=null){
//			String content = "客服为您指派了一个新订单，用车时间：{0}，请登录司机端查看。如有疑问，请联系{1}（{2}）";
			String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.changevehicle.newdriver", usertimestr, servcietel);
			List<String> dirphone = new ArrayList<String>();
			dirphone.add((String)driverinfo.get("phone"));
			SMMessageUtil.send(dirphone, content);
			//推送给新司机
			if(null == driverinfo.get("passworkstatus") || !DriverEnum.PASS_WORK_STATUS_PENDING.code.equals(driverinfo.get("passworkstatus"))) {
				List<String> userids = new ArrayList<String>();
				userids.add((String) driverinfo.get("phone"));
				PushPayload pushload4ios = PushObjFactory.createTaskOrderObj4IOS(orderinfo, userids);
				PushPayload pushload4android = PushObjFactory.createTaskOrderObj4Android(orderinfo, userids);
				AppMessageUtil.send(pushload4ios,pushload4android, AppMessageUtil.APPTYPE_DRIVER);
			}
		}else{
			logger.info("发送短信给司机时，没有获取到相关信息，无法发送");
		}
		logger.info("这里是出租车更换车辆消息，已实现");
	}
	
	/**
	 * 出租车派单失败
	 */
	private void taxiSendorderfail() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String usertimestr = format.format(order.getUsetime());
		Map<String, Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
		Map<String, Object> opinfo = MessageAPIUtil.getOpInfo();
		if(null != userinfo && null != opinfo) {
			String userphone = (String) userinfo.get("account");
//			String content = "目前正处于高峰期，您预约{0}的订单暂无人接单。给您带来的不便，深表歉意，如需帮助，请联系{1}（{2}）";
			//String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.sendorderfail.user", usertimestr, opinfo.get("servcietel"), opinfo.get("companyshortname"));
			String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.sendorderfail.user", usertimestr, opinfo.get("servcietel"));
			List<String> phones = new ArrayList<String>();
			phones.add(userphone);
			SMMessageUtil.send(phones, content);
			
			//消息推送
			List<String> tag_ands = new ArrayList<String>();
			tag_ands.add("1");
			SendOrderUtil s = new SendOrderUtil();
			OrderInfoParam param = new OrderInfoParam();
			param.setOrder(order);
			OrderInfoDetail orderinfo = s.getOrder(param);
			
			PushPayload pushload4android = PushObjFactory.createSendOrderFailObj4Android(orderinfo, phones, tag_ands);
			PushPayload pushload4ios = PushObjFactory.createSendOrderFailObj4IOS(orderinfo, phones, tag_ands);
			AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_PASSENGER);
		}
		logger.info("这里是出租车派单失败消息，已实现");
	}
	
	/**
	 * 出租车取消订单
	 */
	private void taxiCancelorder() {
		SendOrderUtil s = new SendOrderUtil();
		OrderInfoParam param = new OrderInfoParam();
		param.setOrder(order);
		OrderInfoDetail orderinfo = s.getOrder(param);
		
		Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
		String driverphone = null;
		if(driverinfo!=null){
			driverphone = (String) driverinfo.get("phone");
		}
		//只有已接单的订单取消才需要发送消息给司机
		if(StringUtils.isNotBlank(driverphone)){
			List<String> userids = new ArrayList<String>();
			userids.add(driverphone);
			PushPayload pushload4ios = PushObjFactory.createCancelOrderObj4IOS(orderinfo, userids,null);
			PushPayload pushload4android = PushObjFactory.createCancelOrderObj4Android(orderinfo, userids,null);
			AppMessageUtil.send(pushload4ios,pushload4android, AppMessageUtil.APPTYPE_DRIVER);
		}
		
		Date usetime = order.getUsetime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String usertimestr = format.format(usetime);
		Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
		if(opinfo!=null){
			String servicephone = (String) opinfo.get("servcietel");
//			String companyshortname = (String) opinfo.get("companyshortname");
			
			String passengerphone = order.getPassengerphone();
			Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
			String userphone = (String) userinfo.get("account");
            String cancelParty = "客服";
            if(CancelParty.PASSENGER.code.equals(order.getCancelparty())) {
                cancelParty = "下单人";
            }
			if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
				//乘车人
				List<String> phones = new ArrayList<String>();
				phones.add(passengerphone);
				String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.cancelorder4passenger", usertimestr, cancelParty, servicephone);
//						SMMessageUtil.send(phones, "您"+usertimestr+"的用车订单已被下单人取消，如有疑问，请联系下单人。如需帮助可联系"+servicephone+"(运管端)");
				SMMessageUtil.send(phones, smscontent);
			}
			//下单人
			List<String> phones = new ArrayList<String>();
			phones.add(userphone);
			String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.cancelorder4orderuser", usertimestr,servicephone);
//					SMMessageUtil.send(phones, "您"+usertimestr+"的用车订单已经取消。如需帮助可联系"+servicephone+"(运管端)");
			SMMessageUtil.send(phones, smscontent);
			if(!CancelParty.PASSENGER.code.equals(order.getCancelparty())){
				//web端取消
				List<String> tag_ands = new ArrayList<String>();
				tag_ands.add("1");
				PushPayload ppushload4ios = PushObjFactory.createCancelOrderObj4IOS(orderinfo, phones,tag_ands);
				PushPayload ppushload4android = PushObjFactory.createCancelOrderObj4Android(orderinfo, phones,tag_ands);
				AppMessageUtil.send(ppushload4ios,ppushload4android, AppMessageUtil.APPTYPE_PASSENGER);
			}
		}
		logger.info("这里是出租车订单取消消息，已实现");
	}
	
	/**
	 * 司机端hint消息(推送订单已失效)
	 */
	private void taxiHint4Driver() {
		String content = "订单已失效";
		List<String> userids = phones;
		String hinttype = PushObjFactory.HINT_ORDERISGONE;
		//暂时tag_and为空,需要指定app端的标签
		PushPayload pushload4ios = PushObjFactory.creatHintObj4IOS(content,hinttype,userids,null);
		PushPayload pushload4android = PushObjFactory.creatHintObj4Android(content,hinttype,userids,null);
		AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
	}
	
	/**
	 * 乘客端hint消息
	 */
	private void taxiOrderhint() {
		List<String> userids = new ArrayList<String>();
		String content = getHint4Order();
		List<String> tag_ands = new ArrayList<String>();
		String paymethod = "0";
		if(order instanceof OrgOrder){
			Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
			String phone = (String) userinfo.get("account");
			userids.add(phone);
			tag_ands.add("0");
			paymethod = ((OrgOrder)order).getPaymethod();
		}else{
			Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
			String phone = (String) userinfo.get("account");
			userids.add(phone);
			tag_ands.add("1");
		}
		String hinttype = getHintType4Message();
		if(userids.size()>0&&StringUtils.isNotBlank(content)&&StringUtils.isNotBlank(hinttype)){
			//todo暂时tag_and为空,需要指定app端的标签
			PushPayload pushload4ios = PushObjFactory.creatHintOrderStatus4IOS(content,hinttype,order.getOrderno(),paymethod,userids,tag_ands,order.getUsetype(),order.getOrdertype());
			PushPayload pushload4android = PushObjFactory.creatHintOrderStatus4Android(content,hinttype,order.getOrderno(),paymethod,userids,tag_ands,order.getUsetype(),order.getOrdertype());
			AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_PASSENGER);
		}
	}
	
	/**
	 * hint提示内容
	 * @return
	 */
	private String getHint4Order(){
		String hint = null;
		switch (order.getOrderstatus()) {
		case "2":
			String hintcontent = "用车订单司机已接单，会准时来接您";
			if(order.isIsusenow()){
				hintcontent = "您的即刻"+hintcontent;
			}else{
				Date usetime = order.getUsetime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				SimpleDateFormat formatday = new SimpleDateFormat("yyyy-MM-dd");
				String timestr = formatday.format(usetime);
				Date servertime = new Date();
				Calendar calender = Calendar.getInstance();
				calender.setTime(servertime);
				SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
				if(timestr.equals(formatday.format(calender.getTime()))){
					//今天
					hintcontent = "今天"+format2.format(usetime)+"的预约"+hintcontent;
				}else{
					calender.add(Calendar.DATE, 1);
					if(timestr.equals(formatday.format(calender.getTime()))){
						//明天
						hintcontent = "明天"+format2.format(usetime)+"的预约"+hintcontent;
					}else{
						calender.add(Calendar.DATE, 1);
						if(timestr.equals(formatday.format(calender.getTime()))){
							//后天
							hintcontent = "后天"+format2.format(usetime)+"的预约"+hintcontent;
						}else{
							//其他时间
							hintcontent = format.format(usetime)+"的预约"+hintcontent;
						}
					}
				}
			}
			hint = hintcontent;
			break;
		case "3":
			hint = "司机已经在接您的路上了";	
			break;
		case "4":
			hint = "司机已经到了接您的地方";
			break;
		case "5":
			hint = "司机已经接到了乘客";	
			break;
		case "6":
			hint = "司机已经开始了服务";	
			break;
		case "7":
		case "9":
			hint = "服务已结束";	
			break;
		default:
			break;
		}
		return hint;
	}
	
	/**
	 * hint消息的类型
	 * @return
	 */
	private String getHintType4Message(){
		String messagetype = null;
		messagetype = OrderState.WAITSTART.state.equals(order.getOrderstatus()) ? PushObjFactory.HINT_HAVETAKEORDER :
									OrderState.START.state.equals(order.getOrderstatus()) ? PushObjFactory.HINT_ONTHEWAY : 
									OrderState.ARRIVAL.state.equals(order.getOrderstatus()) ? PushObjFactory.HINT_COME : 
									OrderState.PICKUP.state.equals(order.getOrderstatus()) ? PushObjFactory.HINT_TAKEPASSENGER : 
									OrderState.INSERVICE.state.equals(order.getOrderstatus()) ? PushObjFactory.HINT_BSERVICE : 
									OrderState.WAITMONEY.state.equals(order.getOrderstatus()) ? PushObjFactory.HINT_ENDSERVICE : 
									OrderState.SERVICEDONE.state.equals(order.getOrderstatus()) ?  PushObjFactory.HINT_ENDSERVICE : null;
		return messagetype;
	}
	
	public Map<String, Object> getExtrainfo() {
		return extrainfo;
	}

	public void setExtrainfo(Map<String, Object> extrainfo) {
		this.extrainfo = extrainfo;
	}

	public AbstractOrder getOrder() {
		return order;
	}

	public void setOrder(AbstractOrder order) {
		this.order = order;
	}

	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	/**  
	 * 获取phones  
	 * @return phones phones  
	 */
	public List<String> getPhones() {
		return phones;
	}
	
	/**  
	 * 设置phones  
	 * @param phones phones  
	 */
	public void setPhones(List<String> phones) {
		this.phones = phones;
	}
	
}
