package com.szyciov.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.szyciov.driver.entity.OrderInfo;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.PayState;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.PayMethod;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.VehicleEnum;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.OrderInfoParam;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.PassengerOrder;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.PushObjFactory;
import com.szyciov.util.SMMessageUtil;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.SendOrderUtil;
import com.szyciov.util.StringUtil;

import cn.jpush.api.push.model.PushPayload;

/**
 * 订单类消息
 * @author admin
 *
 */
public class OrderMessage extends BaseMessage {
	Logger logger = Logger.getLogger(OrderMessage.class);
	
	public static final String SENDORDERFAIL ="派单失败";
	
	public static final String UNDERORDER = "派发定单";
	
	public static final String CANCELORDER = "取消订单";
	
	public static final String REMINDORDER = "订单行程提醒";
	
	public static final String FINISHORDER = "订单行程结束";
	
	public static final String TAKEORDER = "已接单";
	
	public static final String TASKORDER = "新的任务";
	
	public static final String EXCEPTIONORDER = "订单复核";
	
	public static final String CHAGEDRIVER = "更改司机";
	
	public static final String MANTICORDER = "人工指派订单";
	
	public static final String NOTREVIEW = "拒绝复核";
	
	//订单司机在接乘客路上这种hint提示信息
	public static final String ORDERHINT = "hint信息";
	
	@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT, property = "@id")
	@JsonSubTypes({
		@JsonSubTypes.Type(value = OrgOrder.class, name = "OrgOrder"),
		@JsonSubTypes.Type(value = OpOrder.class, name = "OpOrder"),
		@JsonSubTypes.Type(value = OpTaxiOrder.class, name = "OpTaxiOrder"),
		@JsonSubTypes.Type(value = PassengerOrder.class, name = "PassengerOrder")
	})
	private AbstractOrder order;
	
	private String messagetype;

	/**
	 * 额外的参数信息
	 */
	private Map<String, Object> extrainfo;
	
	/**
	 * 此构造函数只用于接受注解用
	 */
	public OrderMessage() {
		
	}
	
	/**
	 * 不需要额外参数的消息构造函数
	 * @param order
	 * @param messagetype
	 */
	public OrderMessage(AbstractOrder order,String messagetype){
		this.order = order;
		this.messagetype = messagetype;
	}
	
	/**
	 * 需要额外参数的消息构造函数
	 * @param order
	 * @param messagetype
	 * @param extrainfo
	 */
	public OrderMessage(AbstractOrder order,String messagetype,Map<String,Object> extrainfo){
		this.order = order;
		this.messagetype = messagetype;
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

	public static String getUnderorder() {
		return UNDERORDER;
	}

	public static String getCancelorder() {
		return CANCELORDER;
	}

	public static String getRemindorder() {
		return REMINDORDER;
	}

	@Override
	public void send() {
		//下面的if都提出单独的方法处理
		if(UNDERORDER.equalsIgnoreCase(messagetype)){
			//待接单，推送司机
			dispatchOrder();
		}else if(CANCELORDER.equalsIgnoreCase(messagetype)){
			//取消订单
			cancelOrder();
		}else if(REMINDORDER.equalsIgnoreCase(messagetype)){
			//行程提醒	
			remindOrder();
		}else if(FINISHORDER.equalsIgnoreCase(messagetype)){
			//行程结束
			finishOrder();
		}else if(TAKEORDER.equalsIgnoreCase(messagetype)){
			//已接单
			takeOrder();
		}else if(TASKORDER.equalsIgnoreCase(messagetype)){
			//新任务推送
			taskOrder();
		}else if(EXCEPTIONORDER.equalsIgnoreCase(messagetype)){
			//订单复核
			exceptionOrder();
		}else if(CHAGEDRIVER.equalsIgnoreCase(messagetype)){
			//更改司机
			changeDriver();
		}else if(MANTICORDER.equalsIgnoreCase(messagetype)){
			//人工指派消息
			manticOrder();
		}else if(NOTREVIEW.equals(messagetype)){
			//未复核
			notreview();
		}else if(ORDERHINT.equals(messagetype)){
			orderhint();
		}else if(SENDORDERFAIL.equals(messagetype)){
			sendOrderFail();
		}
	}
	
	/**
	 * 乘客端派单失败的提示消息
	 */
	private void sendOrderFail() {
		List<String> userids = new ArrayList<String>();
		List<String> tag_ands = new ArrayList<String>();
		String servcietel = null;
		if(order instanceof OrgOrder){
			Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
			String phone = (String) userinfo.get("account");
			userids.add(phone);
			tag_ands.add("0");
			LeasesCompany lecompany = MessageAPIUtil.getLeaseCompanyById(order.getCompanyid());
			servcietel = lecompany.getServicesphone();
		}else{
			Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
			String phone = (String) userinfo.get("account");
			userids.add(phone);
			tag_ands.add("1");
			Map<String, Object> opinfo = MessageAPIUtil.getOpInfo();
			servcietel = opinfo.get("servcietel").toString();
		}
		//发送短信
		String usertimestr = StringUtil.formatDate(order.getUsetime(), StringUtil.TIME_WITH_MINUTE);
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.taxiordermessage.sendorderfail.user", usertimestr, servcietel);
		SMMessageUtil.send(userids, content);
		
		//发送推送
		SendOrderUtil s = new SendOrderUtil();
		OrderInfoParam param = new OrderInfoParam();
		param.setOrder(order);
		OrderInfoDetail orderinfo = s.getOrder(param);
		
		PushPayload pushload4android = PushObjFactory.createSendOrderFailObj4Android(orderinfo, userids, tag_ands);
		PushPayload pushload4ios = PushObjFactory.createSendOrderFailObj4IOS(orderinfo, userids, tag_ands);
		AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_PASSENGER);
	}

	/**
	 * 乘客端hint消息
	 */
	private void orderhint() {
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
	 * hint消息的类型
	 * @return
	 */
	private String getHintType4Message(){
		String messagetype = null;
		switch (order.getOrderstatus()) {
		case "2":
			messagetype = PushObjFactory.HINT_HAVETAKEORDER;
			break;
		case "3":
			messagetype = PushObjFactory.HINT_ONTHEWAY;	
			break;
		case "4":
			messagetype = PushObjFactory.HINT_COME;
			break;
		case "5":
			messagetype = PushObjFactory.HINT_TAKEPASSENGER;	
			break;
		case "6":
			messagetype = PushObjFactory.HINT_BSERVICE;	
			break;
		case "7":
			messagetype = PushObjFactory.HINT_ENDSERVICE;	
			break;
		default:
			break;
		}
		return messagetype;
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
			hint = "服务已结束";	
			break;
		default:
			break;
		}
		return hint;
	}

	/**
	 * 派单消息
	 */
	private void dispatchOrder(){
		//调用派单规则
		SendOrderUtil s = new SendOrderUtil();
		OrderInfoParam param = new OrderInfoParam();
		param.setOrder(order);
		try {
			s.sendOrder(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取消订单消息
	 */
	private void cancelOrder(){
		//我自己知道推送谁
		//调用appmessageutil,smmeesageutil
		OrderInfoDetail orderinfo = convert2OrderInfoDetail(order);
		
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
        String cancelParty = "客服";
        if(CancelParty.PASSENGER.code.equals(order.getCancelparty())) {
            cancelParty = "下单人";
        }
		if(order instanceof OrgOrder){
			//租赁端订单
			LeasesCompany lecompany = MessageAPIUtil.getLeaseCompanyById(order.getCompanyid());
			if(lecompany!=null){
				String servicephone = lecompany.getServicesphone();
//				String companyname = lecompany.getShortname();
				String passengerphone = order.getPassengerphone();
				Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
				String userphone = (String) userinfo.get("account");
				if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
					//不同
					List<String> phones = new ArrayList<String>();
					phones.add(passengerphone);
					String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.cancelorder4passenger", usertimestr, cancelParty, servicephone);
//					SMMessageUtil.send(phones, "您"+usertimestr+"的用车订单已被下单人取消，如有疑问，请联系下单人。如需帮助可联系"+servicephone+"("+companyname+")");
					SMMessageUtil.send(phones, smscontent);
				}
				//下单人
				List<String> phones = new ArrayList<String>();
				phones.add(userphone);
				String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.cancelorder4orderuser", usertimestr,servicephone);
//				SMMessageUtil.send(phones, "您"+usertimestr+"的用车订单已经取消。如需帮助可联系"+servicephone+"("+companyname+")");
				SMMessageUtil.send(phones, smscontent);
				if(!CancelParty.PASSENGER.code.equals(order.getCancelparty())){
					//web端取消
					List<String> tag_ands = new ArrayList<String>();
					tag_ands.add("0");
					PushPayload ppushload4ios = PushObjFactory.createCancelOrderObj4IOS(orderinfo, phones,tag_ands);
					PushPayload ppushload4android = PushObjFactory.createCancelOrderObj4Android(orderinfo, phones,tag_ands);
					AppMessageUtil.send(ppushload4ios,ppushload4android, AppMessageUtil.APPTYPE_PASSENGER);
				}
			}
		}else{
			//运营端
			Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
			if(opinfo!=null){
				String servicephone = (String) opinfo.get("servcietel");
				
				String passengerphone = order.getPassengerphone();
				Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
				String userphone = (String) userinfo.get("account");
				if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
					//乘车人
					List<String> phones = new ArrayList<String>();
					phones.add(passengerphone);
					String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.cancelorder4passenger", usertimestr, cancelParty, servicephone);
//					SMMessageUtil.send(phones, "您"+usertimestr+"的用车订单已被下单人取消，如有疑问，请联系下单人。如需帮助可联系"+servicephone+"(运管端)");
					SMMessageUtil.send(phones, smscontent);
				}
				//下单人
				List<String> phones = new ArrayList<String>();
				phones.add(userphone);
				String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.cancelorder4orderuser", usertimestr,servicephone);
//				SMMessageUtil.send(phones, "您"+usertimestr+"的用车订单已经取消。如需帮助可联系"+servicephone+"(运管端)");
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
		}
		logger.info("这里是取消订单消息，已实现");
	}
	
	/**
	 * 行程提醒消息
	 */
	private void remindOrder(){
		OrderInfoDetail orderinfo = convert2OrderInfoDetail(order);
		Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
		if(driverinfo==null){
			logger.info("订单没有司机，无法发送提醒");
			return ;
		}
		String phone = (String) driverinfo.get("phone");
		List<String> userids = new ArrayList<String>();
		userids.add(phone);
		PushPayload  pushobj4ios = PushObjFactory.createRemindOrderObj4IOS(orderinfo, userids);
		PushPayload  pushobj4android = PushObjFactory.createRemindOrderObj4Android(orderinfo, userids);
		AppMessageUtil.send(pushobj4ios,pushobj4android, AppMessageUtil.APPTYPE_DRIVER);
		logger.info("这里是行程提醒消息，已实现");
	}
	
	/**
	 * 系统强派or人工指派
	 */
	private void taskOrder(){
		OrderInfoDetail orderinfo = convert2OrderInfoDetail(order);
		Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
		if(driverinfo==null){
			logger.info("未找到司机信息,强派司机推送失败");
			return ;
		}
		String phone = (String) driverinfo.get("phone");
		List<String> userids = new ArrayList<String>();
		userids.add(phone);
		PushPayload  pushobj4ios = PushObjFactory.createTaskOrderObj4IOS(orderinfo, userids);
		PushPayload  pushobj4android = PushObjFactory.createTaskOrderObj4Android(orderinfo, userids);
		AppMessageUtil.send(pushobj4ios,pushobj4android, AppMessageUtil.APPTYPE_DRIVER);
		logger.info("这里是强派司机推送，已实现");
	}
	
	/**
	 * 行程结束消息
	 */
	private void finishOrder(){
		/**
		 *    模板：您在{0}的用车服务已完成，行驶{1}分钟，{2}公里，应付{3}元，您可登录客户端评价该订单及查看费用详情。如需帮助可联系{4}，期待下次为您服务(租赁公司简称)
   			  0：用车时间，yyyy-MM-dd HH:mm  1：行程用时  2：行程里程  3：费用  4：客服电话
		 */
//		Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
//		if(driverinfo==null){
//			logger.info("订单没有司机，无法发送提醒");
//			return ;
//		}
//		//先发送apphint消息
//		orderhint();
//		String phone = (String) driverinfo.get("phone");
		
		//先发送apphint消息
		orderhint();
		//订单详细信息(需要重新获取)
		SendOrderUtil sou = new SendOrderUtil();
		OrderInfoParam oip = new OrderInfoParam();
		oip.setOrder(order);
		OrderInfoDetail orderinfo = sou.getOrder(oip);
		List<String> phones = new ArrayList<String>();
		Date usetime = order.getUsetime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String usertimestr = format.format(usetime);
		String content = null;//"您在"+usertimestr+"的用车服务已完成，行驶"+orderinfo.getTimes()+"分钟，"+orderinfo.getMileage()+"公里，应付"+orderinfo.getOrderamount()+"元，您可登录客户端评价该订单及查看费用详情。如需帮助可联系"+leasesCompany.getServicesphone()+"，期待下次为您服务("+leasesCompany.getShortname()+")";
		double mileagem = orderinfo.getMileage();
		double mileage = mileagem/1000;
		mileage = StringUtil.formatNum(mileage, 1);
		if(order instanceof OrgOrder){
			Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
			String userphone = (String) userinfo.get("account");
			phones.add(userphone);
			LeasesCompany leasesCompany = MessageAPIUtil.getLeaseCompanyById(order.getCompanyid());
//			content = "您在"+usertimestr+"的用车服务已完成，行驶"+orderinfo.getTimes()+"分钟，"+mileage+"公里，应付"+orderinfo.getOrderamount()+"元，您可登录客户端评价该订单及查看费用详情。如需帮助可联系"+leasesCompany.getServicesphone()+"，期待下次为您服务("+leasesCompany.getShortname()+")";
			content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.finishorder",usertimestr,orderinfo.getTimes(),mileage,orderinfo.getOrderamount(),leasesCompany.getServicesphone());
		}else{
			Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
			String userphone = (String) userinfo.get("account");
			phones.add(userphone);
			Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
//			content = "您在"+usertimestr+"的用车服务已完成，行驶"+orderinfo.getTimes()+"分钟，"+mileage+"公里，应付"+orderinfo.getOrderamount()+"元，您可登录客户端评价该订单及查看费用详情。如需帮助可联系"+opinfo.get("servcietel")+"，期待下次为您服务(运管端)";
			content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.finishorder",usertimestr,orderinfo.getTimes(),mileage,orderinfo.getOrderamount(),opinfo.get("servcietel"));
		}
		SMMessageUtil.send(phones, content);
		logger.info("这里是行程结束消息，已实现");
	}
	
	/**
	 * 已接单消息
	 */
	private void takeOrder(){
		//司机已接单消息
		//先发送apphint消息
		//下单来源是乘客，司机接单才推送乘客端
		if (OrderEnum.ORDERSOURCE_PASSENGER_ANDROID.code.equalsIgnoreCase(order.getOrdersource())
				|| OrderEnum.ORDERSOURCE_PASSENGER_IOS.code.equalsIgnoreCase(order.getOrdersource())
				|| OrderEnum.ORDERSOURCE_PASSENGER_WECHAT.code.equalsIgnoreCase(order.getOrdersource())) {
			orderhint();
		}
		if(order instanceof OrgOrder){
			//机构订单
			Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
			Map<String,Object> carinfo = MessageAPIUtil.getCarInfo(order.getVehicleid());
			LeasesCompany leasesCompany = MessageAPIUtil.getLeaseCompanyById(order.getCompanyid());
			String passengerphone = order.getPassengerphone();
			String onaddress = order.getOnaddress();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String usertimestr = format.format(order.getUsetime());
			if(userinfo!=null&&driverinfo!=null&&carinfo!=null&&leasesCompany!=null){
				logger.info("获取到相关信息");
				String driverstr = driverinfo.get("name")+"师傅"+driverinfo.get("phone");
				String carstr = ""+carinfo.get("color")+carinfo.get("vehcbrandname")+carinfo.get("vehclinename")+carinfo.get("plateno");
				String servicephone = leasesCompany.getServicesphone();
				String companyname = leasesCompany.getShortname();
				String userphone = (String) userinfo.get("account");
				if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
					logger.info("乘车人和坐车人不同的消息发送");
//					乘车人不同
					String username = (String) userinfo.get("nickname");
//					模板：您好，{0}为您安排专车于{1}去{2}接您，司机为{3}，{4}。如需帮助可联系{5}(租赁公司简称)
//					String passenggercontent = "您好，"+username+"为您安排专车于"+usertimestr+"去"+onaddress+"接您，司机为"+driverstr+"，"+carstr+"。如需帮助可联系"+servicephone+"("+companyname+")";
					String passenggercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.passenger", username,usertimestr,onaddress,driverstr,carstr,servicephone);
					List<String> passenggerphones = new ArrayList<String>();
					passenggerphones.add(passengerphone);
					SMMessageUtil.send(passenggerphones, passenggercontent);
//					 发送给下单人
//					 模板：预订成功，您为{0}预订的{1}的专车，司机为{2}，{3}，如需帮助可联系{4}(租赁公司简称)
//					String usercontent = "预订成功，您为"+order.getPassengers()+"预订的"+usertimestr+"的专车，司机为"+driverstr+"，"+carstr+"，如需帮助可联系"+servicephone+"("+companyname+")";
					String usercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.user", order.getPassengers(),usertimestr,driverstr,carstr,servicephone);
					List<String> userphones = new ArrayList<String>();
					userphones.add(userphone);
					SMMessageUtil.send(userphones, usercontent);
				}else{
					logger.info("乘车人和坐车人相同同的消息发送");
					//认为相同，只发送userphone
//					模板：{0}的用车预订成功，将由{1}为您服务，{2}，如需帮助可联系{3}(租赁公司简称)
//					String content = usertimestr+"的用车预订成功，将由"+driverstr+"为您服务，"+carstr+"，如需帮助可联系"+servicephone+"("+companyname+")";
					String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.userpassentger", usertimestr,driverstr,carstr,servicephone);
					List<String> phones = new ArrayList<String>();
					phones.add(userphone);
					SMMessageUtil.send(phones, content);
				}
			}else{
				logger.info("发送短信给用户时，没有获取到相关信息，无法发送");
			}
		}else{
			logger.info("开始发送短信给相关用户");
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
//					乘车人不同
					String username = (String) userinfo.get("nickname");
//					模板：您好，{0}为您安排专车于{1}去{2}接您，司机为{3}，{4}。如需帮助可联系{5}(租赁公司简称)
//					String passenggercontent = "您好，"+username+"为您安排专车于"+usertimestr+"去"+onaddress+"接您，司机为"+driverstr+"，"+carstr+"。如需帮助可联系"+servicephone+"("+companyname+")";
					String passenggercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.passenger", username,usertimestr,onaddress,driverstr,carstr,servicephone);
					List<String> passenggerphones = new ArrayList<String>();
					passenggerphones.add(passengerphone);
					SMMessageUtil.send(passenggerphones, passenggercontent);
//					 发送给下单人
//					 模板：预订成功，您为{0}预订的{1}的专车，司机为{2}，{3}，如需帮助可联系{4}(租赁公司简称)
//					String usercontent = "预订成功，您为"+order.getPassengers()+"预订的"+usertimestr+"的专车，司机为"+driverstr+"，"+carstr+"，如需帮助可联系"+servicephone+"("+companyname+")";
					String usercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.user", order.getPassengers(),usertimestr,driverstr,carstr,servicephone);
					List<String> userphones = new ArrayList<String>();
					userphones.add(userphone);
					SMMessageUtil.send(userphones, usercontent);
				}else{
					logger.info("乘车人和坐车人相同同的消息发送");
					//认为相同，只发送userphone
//					模板：{0}的用车预订成功，将由{1}为您服务，{2}，如需帮助可联系{3}(租赁公司简称)
//					String content = usertimestr+"的用车预订成功，将由"+driverstr+"为您服务，"+carstr+"，如需帮助可联系"+servicephone+"("+companyname+")";
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
	 * 复核订单消息
	 */
	private void exceptionOrder(){
		String reviewperson = (String) extrainfo.get("reviewperson");
		double price = (double) extrainfo.get("price");
		String paymentstatus = order.getPaymentstatus();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String usertimestr = format.format(order.getUsetime());
		Date starttime = order.getStarttime();
		Date endtime = order.getEndtime();
		long mm = (endtime.getTime()-starttime.getTime())/(1000*60);
		double mileage = order.getMileage()/1000;
		double cost = order.getOrderamount();
		Map<String,Object> reviewinfo = new HashMap<String,Object>();
		reviewinfo.put("orderno", order.getOrderno());
		reviewinfo.put("usetime", usertimestr);
		reviewinfo.put("ordertype", order.getOrdertype());
		reviewinfo.put("onaddress", order.getOnaddress());
		reviewinfo.put("offaddress", order.getOffaddress());
		reviewinfo.put("usetype", order.getUsetype());
		if("1".equalsIgnoreCase(reviewperson)){
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
			String driverphone = (String) driverinfo.get("phone");
			List<String> userids = new ArrayList<String>();
			userids.add(driverphone);
			//司机复合
			if(price<0){
				//差异金额大于0,订单实际金额少了
				String content = "订单复核完成，订单金额应为"+cost+"元，差额部分不记贡献。";
				reviewinfo.put("content", content);
				PushPayload payload4ios = PushObjFactory.createOrderReviewObj4IOS(reviewinfo, userids);
				PushPayload payload4android = PushObjFactory.createOrderReviewObj4Android(reviewinfo, userids);
				AppMessageUtil.send(payload4ios,payload4android, AppMessageUtil.APPTYPE_DRIVER);
			}else{
				//订单实际金额多了
				if(PayState.NOTPAY.state.equalsIgnoreCase(paymentstatus)){
					//未支付
					String content = "订单复核完成，订单金额应为"+cost+"元。";
					reviewinfo.put("content", content);
					PushPayload payload4ios = PushObjFactory.createOrderReviewObj4IOS(reviewinfo, userids);
					PushPayload payload4android = PushObjFactory.createOrderReviewObj4Android(reviewinfo, userids);
					AppMessageUtil.send(payload4ios,payload4android, AppMessageUtil.APPTYPE_DRIVER);
				}else{
					//已支付
					String content = "订单复核完成，订单金额应为"+cost+"元。";
					reviewinfo.put("content", content);
					PushPayload payload4ios = PushObjFactory.createOrderReviewObj4IOS(reviewinfo, userids);
					PushPayload payload4andorid = PushObjFactory.createOrderReviewObj4Android(reviewinfo, userids);
					AppMessageUtil.send(payload4ios,payload4andorid, AppMessageUtil.APPTYPE_DRIVER);
					//退乘客钱
					if(order instanceof OrgOrder){
						//机构订单
						LeasesCompany leasesCompany = MessageAPIUtil.getLeaseCompanyById(order.getCompanyid());
						if(leasesCompany!=null){
							Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
//							String smscontent = "订单复核通知：您在"+usertimestr+"的用车服务复核已完成，复核结果"+mm+"分钟，"+mileage+"公里，应付"+cost+"元，多收金额将返还至您的账户余额。如需帮助可联系"+leasesCompany.getServicesphone()+"，期待下次为您服务("+leasesCompany.getShortname()+")";
							//String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.backmoney", usertimestr,mm,mileage,cost,leasesCompany.getServicesphone(),leasesCompany.getShortname());
							String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.backmoney", usertimestr,mm,mileage,cost,leasesCompany.getServicesphone());
							List<String> phones = new ArrayList<String>();
							phones.add((String)userinfo.get("account"));
							SMMessageUtil.send(phones, smscontent);
							
							List<String> tag_ands = new ArrayList<String>();
							tag_ands.add("0");
							PushPayload payload1 = PushObjFactory.creatHintObj4IOS("您收到一笔退款，请前往账户余额中查看！", PushObjFactory.REFUND, order.getOrderno(), phones, tag_ands);
							PushPayload payload2 = PushObjFactory.creatHintObj4Android("您收到一笔退款，请前往账户余额中查看！", PushObjFactory.REFUND, order.getOrderno(), phones, tag_ands);
							AppMessageUtil.send(payload1,payload2, AppMessageUtil.APPTYPE_PASSENGER);
						}
					}else{
						//个人订单
						Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
						if(opinfo!=null){
							Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
//							String smscontent = "订单复核通知：您在"+usertimestr+"的用车服务复核已完成，复核结果"+mm+"分钟，"+mileage+"公里，应付"+cost+"元，多收金额将返还至您的账户余额。如需帮助可联系"+opinfo.get("servcietel")+"，期待下次为您服务(运管端)";
							//String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.backmoney", usertimestr,mm,mileage,cost,opinfo.get("servcietel"),"运管端");
							String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.backmoney", usertimestr,mm,mileage,cost,opinfo.get("servcietel"));
							List<String> phones = new ArrayList<String>();
							phones.add((String)userinfo.get("account"));
							SMMessageUtil.send(phones, smscontent);
							
							List<String> tag_ands = new ArrayList<String>();
							tag_ands.add("1");
							PushPayload payload1 = PushObjFactory.creatHintObj4IOS("您收到一笔退款，请前往账户余额中查看！", PushObjFactory.REFUND, order.getOrderno(), phones, tag_ands);
							PushPayload payload2 = PushObjFactory.creatHintObj4Android("您收到一笔退款，请前往账户余额中查看！", PushObjFactory.REFUND, order.getOrderno(), phones, tag_ands);
							AppMessageUtil.send(payload1,payload2, AppMessageUtil.APPTYPE_PASSENGER);
						}
					}
				}
			}
		}else{
			//用户复合
			if(price<0){
				//差异金额大于0,订单实际金额少了
				if(PayState.NOTPAY.state.equalsIgnoreCase(paymentstatus)){
					//还未支付,不做任何处理
				}else{
					//已经支付，不做任何处理
				}
			}else{
				//订单实际金额多了
				if(PayState.NOTPAY.state.equalsIgnoreCase(paymentstatus)){
					//还未支付
					if(order instanceof OrgOrder){
						//机构订单
						LeasesCompany leasesCompany = MessageAPIUtil.getLeaseCompanyById(order.getCompanyid());
						if(leasesCompany!=null){
							Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
//							String smscontent = "订单复核通知：您在"+usertimestr+"的用车服务复核已完成，复核结果"+mm+"分钟，"+mileage+"公里，应付"+cost+"元。如需帮助可联系"+leasesCompany.getServicesphone()+"，期待下次为您服务("+leasesCompany.getShortname()+")";
							//String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.shouldpaymoney", usertimestr,mm,mileage,cost,leasesCompany.getServicesphone(),leasesCompany.getShortname());
							String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.shouldpaymoney", usertimestr,mm,mileage,cost,leasesCompany.getServicesphone());
							List<String> phones = new ArrayList<String>();
							phones.add((String)userinfo.get("account"));
							SMMessageUtil.send(phones, smscontent);
						}
					}else{
						//个人订单
						Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
						if(opinfo!=null){
							Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
//							String smscontent = "订单复核通知：您在"+usertimestr+"的用车服务复核已完成，复核结果"+mm+"分钟，"+mileage+"公里，应付"+cost+"元。如需帮助可联系"+opinfo.get("servcietel")+"，期待下次为您服务(运管端)";
							//String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.shouldpaymoney", usertimestr,mm,mileage,cost,opinfo.get("servcietel"),"运管端");
							String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.shouldpaymoney", usertimestr,mm,mileage,cost,opinfo.get("servcietel"));
							List<String> phones = new ArrayList<String>();
							phones.add((String)userinfo.get("account"));
							SMMessageUtil.send(phones, smscontent);
						}
					}
				}else{
					//已经支付
					if(order instanceof OrgOrder){
						//机构订单
						LeasesCompany leasesCompany = MessageAPIUtil.getLeaseCompanyById(order.getCompanyid());
						if(leasesCompany!=null){
							Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
//							String smscontent = "订单复核通知：您在"+usertimestr+"的用车服务复核已完成，复核结果"+mm+"分钟，"+mileage+"公里，应付"+cost+"元，多收金额将返还至您的账户余额。如需帮助可联系"+leasesCompany.getServicesphone()+"，期待下次为您服务("+leasesCompany.getShortname()+")";
							//String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.backmoney", usertimestr,mm,mileage,cost,leasesCompany.getServicesphone(),leasesCompany.getShortname());
							String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.backmoney", usertimestr,mm,mileage,cost,leasesCompany.getServicesphone());
							List<String> phones = new ArrayList<String>();
							phones.add((String)userinfo.get("account"));
							SMMessageUtil.send(phones, smscontent);
							
							List<String> tag_ands = new ArrayList<String>();
							tag_ands.add("0");
							PushPayload payload1 = PushObjFactory.creatHintObj4IOS("您收到一笔退款，请前往账户余额中查看！", PushObjFactory.REFUND, order.getOrderno(), phones, tag_ands);
							PushPayload payload2 = PushObjFactory.creatHintObj4Android("您收到一笔退款，请前往账户余额中查看！", PushObjFactory.REFUND, order.getOrderno(), phones, tag_ands);
							AppMessageUtil.send(payload1,payload2, AppMessageUtil.APPTYPE_PASSENGER);
						}
					}else{
						//个人订单
						Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
						if(opinfo!=null){
							Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
//							String smscontent = "订单复核通知：您在"+usertimestr+"的用车服务复核已完成，复核结果"+mm+"分钟，"+mileage+"公里，应付"+cost+"元，多收金额将返还至您的账户余额。如需帮助可联系"+opinfo.get("servcietel")+"，期待下次为您服务(运管端)";
							//String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.backmoney", usertimestr,mm,mileage,cost,opinfo.get("servcietel"),"运管端");
							String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.exceptionorder.backmoney", usertimestr,mm,mileage,cost,opinfo.get("servcietel"));
							List<String> phones = new ArrayList<String>();
							phones.add((String)userinfo.get("account"));
							SMMessageUtil.send(phones, smscontent);
							
							List<String> tag_ands = new ArrayList<String>();
							tag_ands.add("1");
							PushPayload payload1 = PushObjFactory.creatHintObj4IOS("您收到一笔退款，请前往账户余额中查看！", PushObjFactory.REFUND, order.getOrderno(), phones, tag_ands);
							PushPayload payload2 = PushObjFactory.creatHintObj4Android("您收到一笔退款，请前往账户余额中查看！", PushObjFactory.REFUND, order.getOrderno(), phones, tag_ands);
							AppMessageUtil.send(payload1,payload2, AppMessageUtil.APPTYPE_PASSENGER);
						}
					}
				}
			}
		}
		logger.info("这里是复核订单消息，已实现");
	}
	
	/**
	 * 更改司机消息
	 */
	private void changeDriver(){
		//订单详细信息
		OrderInfoDetail orderinfo = convert2OrderInfoDetail(order);
				
		//发送给旧司机的信息
		if(extrainfo!=null){
			String olddrivername = (String) extrainfo.get("olddrivername");
			String olddriverid = (String) extrainfo.get("olddriverid");
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(olddriverid);
			if(driverinfo!=null){
				String olddriverphone = (String) driverinfo.get("phone");
//				String content = olddrivername+"师傅，您好！您的"+order.getOrderno()+"订单已成功更换为其他司机替您完成行程服务。";
				String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.changedriver.old", olddrivername,order.getOrderno());
				List<String> phones = new ArrayList<String>();
				phones.add(olddriverphone);
				SMMessageUtil.send(phones, content);
				
				PushPayload pushload4ios = PushObjFactory.createChangeOrderObj4IOS(orderinfo, phones);
				PushPayload pushload4android = PushObjFactory.createChangeOrderObj4Android(orderinfo, phones);
				AppMessageUtil.send(pushload4ios,pushload4android, AppMessageUtil.APPTYPE_DRIVER);
			}
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String usertimestr = format.format(order.getUsetime());
		if(order instanceof OrgOrder){
			//机构订单
			Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
			Map<String,Object> carinfo = MessageAPIUtil.getCarInfo(order.getVehicleid());
			LeasesCompany leasesCompany = MessageAPIUtil.getLeaseCompanyById(order.getCompanyid());
			String passengerphone = order.getPassengerphone();
			if(userinfo!=null&&driverinfo!=null&&carinfo!=null&&leasesCompany!=null){
				logger.info("获取到相关信息");
				String driverstr = driverinfo.get("name")+"师傅"+driverinfo.get("phone");
				String carstr = ""+carinfo.get("color")+carinfo.get("vehcbrandname")+carinfo.get("vehclinename")+carinfo.get("plateno");
				String servicephone = leasesCompany.getServicesphone();
				String companyname = leasesCompany.getShortname();
				String userphone = (String) userinfo.get("account");
				String servicename = order.getOrdertype();
				if("1".equals(servicename)){
					servicename = "约车";
				}else if("2".equals(servicename)){
					servicename = "接机";
				}else if("3".equals(servicename)){
					servicename = "送机";
				}
//				模板：您在{0}的{服务类型}用车服务，司机更换为{1}，{2}。不便之处，敬请谅解！如需帮助可联系{3}，祝您用车愉快(租赁公司简称)
//				0：用车时间（格式:yyyy-MM-dd HH:mm）  1：司机姓名+师傅+司机手机号码  2：车辆颜色+品牌车系+车牌号   3：客服电话
//				String passenggercontent = "您在"+usertimestr+"的"+servicename+"用车服务，司机更换为"+driverstr+"，"+carstr+"。不便之处，敬请谅解！如需帮助可联系"+servicephone+"("+companyname+")";
				String passenggercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.changedriver.passenger", usertimestr,servicename,driverstr,carstr,servicephone);
				if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
					logger.info("乘车人和坐车人不同的消息发送");
//					乘车人不同
					List<String> passenggerphones = new ArrayList<String>();
					passenggerphones.add(passengerphone);
					SMMessageUtil.send(passenggerphones, passenggercontent);
//					 发送给下单人
					List<String> userphones = new ArrayList<String>();
					userphones.add(userphone);
					SMMessageUtil.send(userphones, passenggercontent);
				}else{
					logger.info("乘车人和坐车人相同同的消息发送");
					//认为相同，只发送userphone
					List<String> phones = new ArrayList<String>();
					phones.add(userphone);
					SMMessageUtil.send(phones, passenggercontent);
				}
				
				//发送短信给新司机
				//{0}师傅，您好！客服为您指派了一个{1}的新订单，请登录司机端查看已接订单或咨询{2}(租赁公司简称)
				//发送给新的司机
				String drivername = (String) driverinfo.get("name");
//				String content = drivername+"师傅，您好！客服为您指派了一个"+usertimestr+"的新订单，请登录司机端查看已接订单或咨询"+servicephone+"("+companyname+")";
				String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.changedriver.new", drivername,usertimestr,servicephone);
				List<String> dirphone = new ArrayList<String>();
				dirphone.add((String)driverinfo.get("phone"));
				SMMessageUtil.send(dirphone, content);
				//推送给新司机
				List<String> userids = new ArrayList<String>();
				userids.add((String) driverinfo.get("phone"));
				PushPayload pushload4ios = PushObjFactory.createTaskOrderObj4IOS(orderinfo, userids);
				PushPayload pushload4android = PushObjFactory.createTaskOrderObj4Android(orderinfo, userids);
				AppMessageUtil.send(pushload4ios,pushload4android, AppMessageUtil.APPTYPE_DRIVER);
				
			}else{
				logger.info("发送短信给新司机和用户时，没有获取到相关信息，无法发送");
			}
		}else{
			//个人订单
			Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
			Map<String,Object> carinfo = MessageAPIUtil.getCarInfo(order.getVehicleid());
			Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
			String passengerphone = order.getPassengerphone();
			if(userinfo!=null&&driverinfo!=null&&carinfo!=null&&opinfo!=null){
				logger.info("获取到相关信息");
				String driverstr = driverinfo.get("name")+"师傅"+driverinfo.get("phone");
				String carstr = ""+carinfo.get("color")+carinfo.get("vehcbrandname")+carinfo.get("vehclinename")+carinfo.get("plateno");
				String servicephone = (String) opinfo.get("servcietel");
				String companyname = "运管端";
				String userphone = (String) userinfo.get("account");
				String servicename = order.getOrdertype();
				if("1".equals(servicename)){
					servicename = "约车";
				}else if("2".equals(servicename)){
					servicename = "接机";
				}else if("3".equals(servicename)){
					servicename = "送机";
				}
//				模板：您在{0}的{服务类型}用车服务，司机更换为{1}，{2}。不便之处，敬请谅解！如需帮助可联系{3}，祝您用车愉快(租赁公司简称)
//				0：用车时间（格式:yyyy-MM-dd HH:mm）  1：司机姓名+师傅+司机手机号码  2：车辆颜色+品牌车系+车牌号   3：客服电话
//				String passenggercontent = "您在"+usertimestr+"的"+servicename+"用车服务，司机更换为"+driverstr+"，"+carstr+"。不便之处，敬请谅解！如需帮助可联系"+servicephone+"("+companyname+")";
				String passenggercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.changedriver.passenger", usertimestr,servicename,driverstr,carstr,servicephone);
				if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
					logger.info("乘车人和坐车人不同的消息发送");
//					乘车人不同
					List<String> passenggerphones = new ArrayList<String>();
					passenggerphones.add(passengerphone);
					SMMessageUtil.send(passenggerphones, passenggercontent);
//					 发送给下单人
					List<String> userphones = new ArrayList<String>();
					userphones.add(userphone);
					SMMessageUtil.send(userphones, passenggercontent);
				}else{
					logger.info("乘车人和坐车人相同同的消息发送");
					//认为相同，只发送userphone
					List<String> phones = new ArrayList<String>();
					phones.add(userphone);
					SMMessageUtil.send(phones, passenggercontent);
				}
				
				//发送短信给新司机
				//{0}师傅，您好！客服为您指派了一个{1}的新订单，请登录司机端查看已接订单或咨询{2}(租赁公司简称)
				//发送给新的司机
				String drivername = (String) driverinfo.get("name");
//				String content = drivername+"师傅，您好！客服为您指派了一个"+usertimestr+"的新订单，请登录司机端查看已接订单或咨询"+servicephone+"("+companyname+")";
				String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.changedriver.new", drivername,usertimestr,servicephone);
				List<String> dirphone = new ArrayList<String>();
				dirphone.add((String)driverinfo.get("phone"));
				SMMessageUtil.send(dirphone, content);
				//推送给新司机
				List<String> userids = new ArrayList<String>();
				userids.add((String) driverinfo.get("phone"));
				PushPayload pushload4ios = PushObjFactory.createTaskOrderObj4IOS(orderinfo, userids);
				PushPayload pushload4android = PushObjFactory.createTaskOrderObj4Android(orderinfo, userids);
				AppMessageUtil.send(pushload4ios,pushload4android, AppMessageUtil.APPTYPE_DRIVER);
			}else{
				logger.info("发送短信给用户时，没有获取到相关信息，无法发送");
			}
		}
		logger.info("这里是更改司机消息，已实现");
	}
	
	/**
	 * 人工指派消息
	 */
	private void manticOrder(){
		OrderInfoDetail orderinfo = convert2OrderInfoDetail(order);
		//只有已接单的订单才需要发送消息
		if(StringUtils.isNotBlank(order.getDriverid())){
			Map<String,Object> driverinfo = MessageAPIUtil.getDriverInfo(order.getDriverid());
			//人工派单消息，发送消息给司机端，推送方式
			List<String> userids = new ArrayList<String>();
			userids.add((String) driverinfo.get("phone"));
			PushPayload pushload4ios = PushObjFactory.createTaskOrderObj4IOS(orderinfo, userids);
			PushPayload pushload4android = PushObjFactory.createTaskOrderObj4Android(orderinfo, userids);
			AppMessageUtil.send(pushload4ios,pushload4android, AppMessageUtil.APPTYPE_DRIVER);
			//人工派单消息，发送消息给乘客端，短信方式
			if(order instanceof OrgOrder){
				logger.info("开始发送短信给相关用户");
				//机构订单
				Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
				
				Map<String,Object> carinfo = MessageAPIUtil.getCarInfo(order.getVehicleid());
				LeasesCompany leasesCompany = MessageAPIUtil.getLeaseCompanyById(order.getCompanyid());
				String passengerphone = order.getPassengerphone();
				String onaddress = order.getOnaddress();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String usertimestr = format.format(order.getUsetime());
				if(userinfo!=null&&driverinfo!=null&&carinfo!=null&&leasesCompany!=null){
					logger.info("获取到相关信息");
					String driverstr = driverinfo.get("name")+"师傅"+driverinfo.get("phone");
					String carstr = ""+carinfo.get("color")+carinfo.get("vehcbrandname")+carinfo.get("vehclinename")+carinfo.get("plateno");
					String servicephone = leasesCompany.getServicesphone();
					String companyname = leasesCompany.getShortname();
					String userphone = (String) userinfo.get("account");
					if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
						logger.info("乘车人和坐车人不同的消息发送");
//						乘车人不同
						String username = (String) userinfo.get("nickname");
                        if(StringUtils.isBlank(username)) {
                            username = "";
                        }
//						模板：您好，{0}为您安排专车于{1}去{2}接您，司机为{3}，{4}。如需帮助可联系{5}(租赁公司简称)
//						String passenggercontent = "您好，"+username+"为您安排专车于"+usertimestr+"去"+onaddress+"接您，司机为"+driverstr+"，"+carstr+"。如需帮助可联系"+servicephone+"("+companyname+")";
						String passenggercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.passenger", username,usertimestr,onaddress,driverstr,carstr,servicephone);
						List<String> passenggerphones = new ArrayList<String>();
						passenggerphones.add(passengerphone);
						SMMessageUtil.send(passenggerphones, passenggercontent);
//						 发送给下单人
//						模板：预订成功，您为{0}预订的{1}的专车，司机为{2}，{3}，如需帮助可联系{4}(租赁公司简称)
//						String usercontent = "预订成功，您为"+order.getPassengers()+"预订的"+usertimestr+"的专车，司机为"+driverstr+"，"+carstr+"，如需帮助可联系"+servicephone+"("+companyname+")";
						String passengers = "";
                        if(StringUtils.isNotBlank(order.getPassengers())) {
                            passengers = "为" + order.getPassengers();
                        }
                        String usercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.user",passengers, usertimestr,driverstr,carstr,servicephone);
						List<String> userphones = new ArrayList<String>();
						userphones.add(userphone);
						SMMessageUtil.send(userphones, usercontent);
					}else{
						logger.info("乘车人和坐车人相同同的消息发送");
						//认为相同，只发送userphone
//						模板：{0}的用车预订成功，将由{1}为您服务，{2}，如需帮助可联系{3}(租赁公司简称)
//						String content = usertimestr+"的用车预订成功，将由"+driverstr+"为您服务，"+carstr+"，如需帮助可联系"+servicephone+"("+companyname+")";
						String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.userpassentger", usertimestr,driverstr,carstr,servicephone);
						List<String> phones = new ArrayList<String>();
						phones.add(userphone);
						SMMessageUtil.send(phones, content);
					}
					
					//发消息给司机端
					String drivername = (String) driverinfo.get("name");
//					String content = drivername+"师傅，您好！客服为您指派了一个"+usertimestr+"的新订单，请登录司机端查看已接订单或咨询"+servicephone+"("+companyname+")";
					String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.changedriver.new", drivername,usertimestr,servicephone);
					List<String> dirphone = new ArrayList<String>();
					dirphone.add((String)driverinfo.get("phone"));
					SMMessageUtil.send(dirphone, content);
				}else{
					logger.info("发送短信给用户时，没有获取到相关信息，无法发送");
				}
			}else{
				//个人订单
				Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
				
				Map<String,Object> carinfo = MessageAPIUtil.getCarInfo(order.getVehicleid());
				Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
				String passengerphone = order.getPassengerphone();
				String onaddress = order.getOnaddress();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String usertimestr = format.format(order.getUsetime());
				if(userinfo!=null&&driverinfo!=null&&carinfo!=null&&opinfo!=null){
					logger.info("获取到相关信息");
					String driverstr = driverinfo.get("name")+"师傅"+driverinfo.get("phone");
					String carstr = ""+carinfo.get("color")+carinfo.get("vehcbrandname")+carinfo.get("vehclinename")+carinfo.get("plateno");
					String servicephone = (String) opinfo.get("servcietel");
					String companyname = "运管端";
					String userphone = (String) userinfo.get("account");
					if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
						logger.info("乘车人和坐车人不同的消息发送");
//						乘车人不同
						String username = (String) userinfo.get("nickname");
                        if(StringUtils.isBlank(username)) {
                            username = "";
                        }
//						模板：您好，{0}为您安排专车于{1}去{2}接您，司机为{3}，{4}。如需帮助可联系{5}(租赁公司简称)
//						String passenggercontent = "您好，"+username+"为您安排专车于"+usertimestr+"去"+onaddress+"接您，司机为"+driverstr+"，"+carstr+"。如需帮助可联系"+servicephone+"("+companyname+")";
						String passenggercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.passenger", username,usertimestr,onaddress,driverstr,carstr,servicephone);
						List<String> passenggerphones = new ArrayList<String>();
						passenggerphones.add(passengerphone);
						SMMessageUtil.send(passenggerphones, passenggercontent);
                        String passengers = "";
                        if(StringUtils.isNotBlank(order.getPassengers())) {
                            passengers = "为" + order.getPassengers();
                        }
//						 发送给下单人
//						模板：预订成功，您为{0}预订的{1}的专车，司机为{2}，{3}，如需帮助可联系{4}(租赁公司简称)
//						String usercontent = "预订成功，您为"+order.getPassengers()+"预订的"+usertimestr+"的专车，司机为"+driverstr+"，"+carstr+"，如需帮助可联系"+servicephone+"("+companyname+")";
						String usercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.user", passengers,usertimestr,driverstr,carstr,servicephone);
						List<String> userphones = new ArrayList<String>();
						userphones.add(userphone);
						SMMessageUtil.send(userphones, usercontent);
					}else{
						logger.info("乘车人和坐车人相同同的消息发送");
						//认为相同，只发送userphone
//						模板：{0}的用车预订成功，将由{1}为您服务，{2}，如需帮助可联系{3}(租赁公司简称)
//						String content = usertimestr+"的用车预订成功，将由"+driverstr+"为您服务，"+carstr+"，如需帮助可联系"+servicephone+"("+companyname+")";
						String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.userpassentger", usertimestr,driverstr,carstr,servicephone);
						List<String> phones = new ArrayList<String>();
						phones.add(userphone);
						SMMessageUtil.send(phones, content);
					}
					//发消息给司机端
					String drivername = (String) driverinfo.get("name");
//					String content = drivername+"师傅，您好！客服为您指派了一个"+usertimestr+"的新订单，请登录司机端查看已接订单或咨询"+servicephone+"("+companyname+")";
					String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.changedriver.new", drivername,usertimestr,servicephone);
					List<String> dirphone = new ArrayList<String>();
					dirphone.add((String)driverinfo.get("phone"));
					SMMessageUtil.send(dirphone, content);
				}else{
					logger.info("发送短信给用户时，没有获取到相关信息，无法发送");
				}
			}
		}
		//告诉乘客下单的结果，界面变化
		orderhint();
		logger.info("这里是人工指派消息，已实现");
	}
	
	/**
	 * 拒绝复核消息
	 */
	private void notreview() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String usertimestr = format.format(order.getUsetime());
		if("1".equalsIgnoreCase(order.getReviewperson())){
			//司机复核
			Map<String,Object> reviewinfo = new HashMap<String,Object>();
			reviewinfo.put("orderno", order.getOrderno());
			reviewinfo.put("usetime", usertimestr);
			reviewinfo.put("ordertype", order.getOrdertype());
			reviewinfo.put("onaddress", order.getOnaddress());
			reviewinfo.put("offaddress", order.getOffaddress());
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
			//乘客复核
			//经核实，您在{0}的用车车费金额正确。如需帮助可联系{1}，期待下次为您服务(租赁公司简称)
			if(order instanceof OrgOrder){
				LeasesCompany leasesCompany = MessageAPIUtil.getLeaseCompanyById(order.getCompanyid());
				if(leasesCompany!=null){
					Map<String,Object> userinfo = MessageAPIUtil.getOrgUserInfo(order.getUserid());
//					String smscontent = "经核实，您在"+usertimestr+"的用车车费金额正确。如需帮助可联系"+leasesCompany.getServicesphone()+"，期待下次为您服务("+leasesCompany.getShortname()+")";
					//String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.notreview", usertimestr,leasesCompany.getServicesphone(),leasesCompany.getShortname());
					String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.notreview", usertimestr,leasesCompany.getServicesphone());
					List<String> phones = new ArrayList<String>();
					phones.add((String)userinfo.get("account"));
					SMMessageUtil.send(phones, smscontent);
				}
			}else{
				Map<String,Object> opinfo = MessageAPIUtil.getOpInfo();
				if(opinfo!=null){
					Map<String,Object> userinfo = MessageAPIUtil.getOpUserInfo(order.getUserid());
//					String smscontent = "经核实，您在"+usertimestr+"的用车车费金额正确。如需帮助可联系"+opinfo.get("servcietel")+"，期待下次为您服务(运管端)";
					//String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.notreview", usertimestr,opinfo.get("servcietel"),"运管端");
					String smscontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.notreview", usertimestr,opinfo.get("servcietel"));
					List<String> phones = new ArrayList<String>();
					phones.add((String)userinfo.get("account"));
					SMMessageUtil.send(phones, smscontent);
				}
			}
		}
		logger.info("这里是拒绝复核消息，已实现");
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
		order.setOnaddress(orderinfo.getOnaddress());
		order.setOffaddress(orderinfo.getOffaddress());
		order.setOnlat(orderinfo.getOnaddrlat());
		order.setOnlng(orderinfo.getOnaddrlng());
		order.setOfflat(orderinfo.getOffaddrlat());
		order.setOfflng(orderinfo.getOffaddrlng());
		order.setEstimatedtime(orderinfo.getEstimatedtime());
		order.setEstimatedmileage(orderinfo.getEstimatedmileage());
		order.setEstimatedcost(orderinfo.getEstimatedcost());
		if (orderinfo instanceof OrgOrder) {
			order.setPaymethod(((OrgOrder)orderinfo).getPaymethod());
		}else {
			order.setPaymethod(PayMethod.PERSONAL.code);
		}
		order.setOrderamount(orderinfo.getOrderamount());
		order.setUsetime(orderinfo.getUsetime());
		order.setIsusenow(orderinfo.isIsusenow());
		order.setPassengers(orderinfo.getPassengers());
		order.setPassengerphone(orderinfo.getPassengerphone());
		order.setCartype(orderinfo.getSelectedmodelname());
		order.setStatus(orderinfo.getOrderstatus());
		order.setCityid(orderinfo.getOncity());
		order.setCompanyid(orderinfo.getCompanyid());
		order.setOrderprop(0);
		order.setRemark(orderinfo.getTripremark());
		//车辆类型 网约车
		order.setOrderstyle(VehicleEnum.VEHICLE_TYPE_CAR.code);
		//订单剩余时间
		int lasttime = (int)Math.ceil((double)(order.getUsetime().getTime()-System.currentTimeMillis())/1000/60);
		lasttime = lasttime < 0?0:lasttime;
		order.setLasttime(StringUtil.formatCostTime(lasttime));
		return order;
	}
	
	/**
	 * 转换成OrderInfo
	 * @param orderinfo
	 * @return
	 */
	private OrderInfo convert2OrderInfo(AbstractOrder orderinfo){
		OrderInfo order = new OrderInfo();
		order.setOrderno(orderinfo.getOrderno());
		order.setOnaddress(orderinfo.getOnaddress());
		order.setOffaddress(orderinfo.getOffaddress());
		order.setOnlat(orderinfo.getOnaddrlat());
		order.setOnlng(orderinfo.getOnaddrlng());
		order.setOfflat(orderinfo.getOffaddrlat());
		order.setOfflng(orderinfo.getOffaddrlng());
		order.setGrabtime(0);   //强派模式没有抢单时间
		order.setEstimatedtime(orderinfo.getEstimatedtime());
		order.setEstimatedmileage(orderinfo.getEstimatedmileage());
		order.setEstimatedcost(orderinfo.getEstimatedcost());
		order.setUsetime(orderinfo.getUsetime());
		order.setType(orderinfo.getOrdertype());
		order.setUsetype(orderinfo.getUsetype());
		order.setIsusenow(orderinfo.isIsusenow());
		order.setOrderstyle(DriverEnum.DRIVER_TYPE_CAR.code);
		order.setRemark(orderinfo.getTripremark());
		return order;
	}
	public Map<String, Object> getExtrainfo() {
		return extrainfo;
	}

	public void setExtrainfo(Map<String, Object> extrainfo) {
		this.extrainfo = extrainfo;
	}
}
