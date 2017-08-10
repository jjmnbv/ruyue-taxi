package com.szyciov.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.szyciov.driver.entity.OrderInfo;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.WindowVisualEnum;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.audience.AudienceType;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 推送消息实体工厂类
 * @author admin
 *
 */
public class PushObjFactory {
	
	/**
	 * 提示信息
	 */
	public static final String HINT = "hint";
	
	/**
	 * 挤下线信息机构app
	 */
	public static final String OFFLINE_ORG = "offline";
	
	/**
	 * 挤下线信息司机app
	 */
	public static final String OFFLINE_DRIVER = "offline";
	
	/**
	 * 挤下线信息个人app
	 */
	public static final String OFFLINE_PERSON = "personoffline";
	
	/**
	 * 取消订单信息
	 */
	public static final String CANCELORDER = "cancelorder";
	
	/**
	 * 订单司机变更
	 */
	public static final String CHANGEORER = "changeorder";
	
	/**
	 * 派单失败
	 */
	public static final String SENDORDERFAIL = "sendorderfail";
	
	/**
	 * 人工派单信息
	 */
	public static final String TASKORDER = "taskorder";
	
	/**
	 * 行程提醒信息
	 */
	public static final String REMINDORDER = "remindorder";
	
	/**
	 * 抢单信息
	 */
	public static final String GRABORDER = "graborder";
	
	/**
	 * 静默推送
	 */
	public static final String SILENCEORDER = "silenceorder";
	
	/**
	 * 订单复合消息
	 */
	public static final String ORDERREVIEW = "orderreview";
	
	/**
	 * 解绑司机车辆
	 */
	public static final String UNBINDCAR = "unbindcar";
	
	/**
	 * 绑定司机车辆
	 */
	public static final String BINDCAR = "bindcar";
	
	/**
	 * 司机被删除
	 */
	public static final String DELETEDRIVER = "deleteDriver";
	
	/**
	 * 退款
	 */
	public static final String REFUND = "refund";

	/**
	 * 司机已接单
	 */
	public static final String HINT_HAVETAKEORDER = "0";

	/**
	 * 司机已出发
	 */
	public static final String HINT_ONTHEWAY = "1";

	/**
	 * 司机已到达
	 */
	public static final String HINT_COME = "2";

	/**
	 * 司机已接到乘客
	 */
	public static final String HINT_TAKEPASSENGER = "3";

	/**
	 * 司机已经开始服务
	 */
	public static final String HINT_BSERVICE = "4";

	/**
	 * 司机服务已结束
	 */
	public static final String HINT_ENDSERVICE = "5";
	
	/**
	 * 订单已失效(被抢,变为人工)
	 */
	public static final String HINT_ORDERISGONE = "6";
	/**
	 * 确认提现
	 * @return
	 */
	public static final String HINT_CASHOK = "cashok";
	
	/**
	 * 拒绝提现
	 * @return
	 */
	public static final String HINT_CASHREJECT = "cashreject";

	/**
	 * 交接班类型--交班中
	 */
	public static final String Hint_SHIFT_PROCESSING = "shiftProcessing";
	
	/**
	 * 交接班类型---交班成功
	 * @return
	 */
	public static final String HINT_SHIFT_PENDINGED = "shiftPendinged";


	/**
	 * 交接班类型---接班成功
	 * @return
	 */
	public static final String HINT_SHIFT_PROCESSED = "shiftProcessed";

	/**
	 * 交接班类型--交班失败
	 */
	public static final String HINT_SHIFT_FAILED = "shiftFailed";


	/**
	 * 接班司机解绑
	 */
	public static final String HINT_SHIFT_DRIVER_UNBIND = "shiftDriverUnBind";
	
	/**
	 * 交接班类型--交班取消
	 */
	public static final String HINT_SHIFT_CANCEL = "shiftCancel";

	/**
	 * 交接班类型---车辆回收
	 * @return
	 */
	public static final String HINT_SHIFT_RECYCLE = "shiftCashreject";




	/**
	 * 拒绝提现
	 * @return
	 */
	
	private static Options getPushOptions(){
		Options.Builder builder = Options.newBuilder();
		String apns = SystemConfig.getSystemProperty("apns");
		if("false".equalsIgnoreCase(apns)){
			builder.setApnsProduction(false);
		}else{
			builder.setApnsProduction(true);
		}
		//离线保存时间
		builder.setTimeToLive(60);
		return builder.build();
	}
	
	private static Audience getAudience(List<String> userids,List<String> tag_ands){
		Audience.Builder builder = Audience.newBuilder();
		if(userids!=null&&userids.size()>0){
			builder.addAudienceTarget(
					AudienceTarget.newBuilder()
					.setAudienceType(AudienceType.ALIAS)
	                .addAudienceTargetValues(userids).build()
			);
		}
		
		if(tag_ands!=null&&tag_ands.size()>0){
			tag_ands.remove("0");
			tag_ands.remove("1");
			if(tag_ands.size()>0){
				builder.addAudienceTarget(
						AudienceTarget.newBuilder()
								.setAudienceType(AudienceType.TAG_AND)
								.addAudienceTargetValues(tag_ands).build()
				);
			}
		}
		return builder.build();
	}
	
	/**
	 * 为app端推送一个交接班消息
	 * @param content
	 * @param hinttype
	 * @param userids
	 * @return
	 */
	public static PushPayload creatShiftObj4IOS(String content,String drivername,String applytype,long applystarttime,long applyendtime,String hinttype,List<String> userids){
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(getAudience(userids, null))
				.setNotification(Notification.newBuilder()
	                    .addPlatformNotification(IosNotification.newBuilder()
	                            .setAlert(content)
	                            .addExtra("type", null2Blank(hinttype))
	                            .addExtra("content", null2Blank(content))
	                            .addExtra("applytype", null2Blank(applytype))
	                            .addExtra("applystarttime", applystarttime)
	                            .addExtra("applyendtime", applyendtime)
	                            .addExtra("drivername", drivername)
	                            .build())
	                    .build())
				.setOptions(getPushOptions())
				.build();
	}
	
	/**
	 * 为app端推送一个交接班消息
	 * @param content
	 * @param userids
	 * @return
	 */
	public static PushPayload creatShiftObj4Android(String content,String drivername,String applytype,long applystarttime,long applyendtime,String hinttype,List<String> userids){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(getAudience(userids, null))
				.setMessage(Message.newBuilder()
						.setContentType("text")
						.setMsgContent(content)
						.setTitle(content)
						//消息类型
						.addExtra("type", null2Blank(hinttype))
						.addExtra("applytype", null2Blank(applytype))
						.addExtra("applystarttime", applystarttime)
						.addExtra("applyendtime", applyendtime)
						.addExtra("drivername", drivername)
						.build())
				.setOptions(getPushOptions())
				.build();
	}
	
	/**
	 * 为app端推送一个hint消息
	 * @param content
	 * @param hinttype
	 * @param userids
	 * @param tag_ands
	 * @return
	 */
	public static PushPayload creatHintObj4IOS(String content,String hinttype,List<String> userids,List<String> tag_ands){
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(getAudience(userids, tag_ands))
				.setNotification(Notification.newBuilder()
	                    .addPlatformNotification(IosNotification.newBuilder()
	                            .setAlert(null2Blank(content))
	                            .addExtra("type", null2Blank(hinttype))
	                            .addExtra("content", null2Blank(content))
	                            .build())
	                    .build())
				.setOptions(getPushOptions())
				.build();
	}
	
	/**
	 * 为app端推送一个hint消息
	 * @param content
	 * @param userids
	 * @return
	 */
	public static PushPayload creatHintObj4Android(String content,String hinttype,List<String> userids,List<String> tag_ands){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(getAudience(userids, tag_ands))
				.setMessage(Message.newBuilder()
						.setContentType("text")
						.setMsgContent(null2Blank(content))
						.setTitle(null2Blank(content))
						//消息类型
						.addExtra("type", null2Blank(hinttype))
						.addExtra("content", null2Blank(content))
						.build())
				.setOptions(getPushOptions())
				.build();
	}




	/**
	 * 为app端推送一个hint消息
	 * @param content
	 * @param hinttype
	 * @param userids
	 * @return
	 */
	public static PushPayload creatProcessed4Android(String content,String hinttype,List<String> userids){
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(getAudience(userids, null))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder()
								.setAlert(content)
								.addExtra("type", null2Blank(hinttype))
								.addExtra("content", null2Blank(content))
								.build())
						.build())
				.setOptions(getPushOptions())
				.build();
	}

	/**
	 * 为app端推送一个hint消息
	 * @param content
	 * @param userids
	 * @return
	 */
	public static PushPayload creatProcessed4Ios(String content,String hinttype,List<String> userids){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(getAudience(userids, null))
				.setMessage(Message.newBuilder()
						.setContentType("text")
						.setMsgContent(content)
						.setTitle(content)
						//消息类型
						.addExtra("type", null2Blank(hinttype))
						.addExtra("content", null2Blank(content))
						.build())
				.setOptions(getPushOptions())
				.build();
	}
	
	/**
	 * 为app端推送一个hint消息
	 * 主要就是司机服务中，开始接乘客这些节目的提示信息
	 * @param content
	 * @param userids
	 * @return
	 */
	public static PushPayload creatHintObj4IOS(String content,String hinttype,String orderno,List<String> userids,List<String> tag_ands){
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(getAudience(userids, tag_ands))
				.setNotification(Notification.newBuilder()
	                    .addPlatformNotification(IosNotification.newBuilder()
	                            .setAlert(content)
	                            .addExtra("type", null2Blank(hinttype))
	    						.addExtra("orderno", null2Blank(orderno))
	                            .build())
//	                    .addPlatformNotification(AndroidNotification.newBuilder()
//	                    		.setAlert(content)
//	                    		.addExtra("type", hinttype)
//	    						.addExtra("orderno", orderno)
//	                    		.build())
	                    .build())
//				.setMessage(Message.newBuilder()
//						.setContentType("text")
//						.setMsgContent(content)
//						.setTitle(content)
//						//消息类型
//						.addExtra("type", hinttype)
//						.addExtra("orderno", orderno)
//						.build())
				.setOptions(getPushOptions())
				.build();
	}
	
	/**
	 * 为app端推送一个hint消息
	 * 主要就是司机服务中，开始接乘客这些节目的提示信息
	 * @param content
	 * @param userids
	 * @return
	 */
	public static PushPayload creatHintObj4Android(String content,String hinttype,String orderno,List<String> userids,List<String> tag_ands){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(getAudience(userids, tag_ands))
//				.setNotification(Notification.newBuilder()
//	                    .addPlatformNotification(IosNotification.newBuilder()
//	                            .setAlert(content)
//	                            .addExtra("type", hinttype)
//	    						.addExtra("orderno", orderno)
//	                            .build())
//	                    .addPlatformNotification(AndroidNotification.newBuilder()
//	                    		.setAlert(content)
//	                    		.addExtra("type", hinttype)
//	    						.addExtra("orderno", orderno)
//	                    		.build())
//	                    .build())
				.setMessage(Message.newBuilder()
						.setContentType("text")
						.setMsgContent(content)
						.setTitle(content)
						//消息类型
						.addExtra("type", null2Blank(hinttype))
						.addExtra("orderno", null2Blank(orderno))
						.build())
				.setOptions(getPushOptions())
				.build();
	}
	
	public static PushPayload creatHintOrderStatus4IOS(String content,String hinttype,String orderno,String paymethod,List<String> userids,List<String> tag_ands,String usetype,String ordertype){
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(getAudience(userids, tag_ands))
				.setNotification(Notification.newBuilder()
	                    .addPlatformNotification(IosNotification.newBuilder()
	                            .setAlert(content)
	                            .addExtra("type", null2Blank(hinttype))
	    						.addExtra("orderno", null2Blank(orderno))
	    						.addExtra("paymethod", null2Blank(paymethod))
	    						.addExtra("usetype", null2Blank(usetype))
	    						.addExtra("ordertype", null2Blank(ordertype))
	                            .build())
//	                    .addPlatformNotification(AndroidNotification.newBuilder()
//	                    		.setAlert(content)
//	                    		.addExtra("type", hinttype)
//	    						.addExtra("orderno", orderno)
//	    						.addExtra("paymethod", paymethod)
//	                    		.build())
	                    .build())
//				.setMessage(Message.newBuilder()
//						.setContentType("text")
//						.setMsgContent(content)
//						.setTitle(content)
//						//消息类型
//						.addExtra("type", hinttype)
//						.addExtra("orderno", orderno)
//						.addExtra("paymethod", paymethod)
//						.build())
				.setOptions(getPushOptions())
				.build();
	}
	
	public static PushPayload creatHintOrderStatus4Android(String content,String hinttype,String orderno,String paymethod,List<String> userids,List<String> tag_ands,String usetype,String ordertype){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(getAudience(userids, tag_ands))
//				.setNotification(Notification.newBuilder()
//	                    .addPlatformNotification(IosNotification.newBuilder()
//	                            .setAlert(content)
//	                            .addExtra("type", hinttype)
//	    						.addExtra("orderno", orderno)
//	    						.addExtra("paymethod", paymethod)
//	                            .build())
//	                    .addPlatformNotification(AndroidNotification.newBuilder()
//	                    		.setAlert(content)
//	                    		.addExtra("type", hinttype)
//	    						.addExtra("orderno", orderno)
//	    						.addExtra("paymethod", paymethod)
//	                    		.build())
//	                    .build())
				.setMessage(Message.newBuilder()
						.setContentType("text")
						.setMsgContent(content)
						.setTitle(content)
						//消息类型
						.addExtra("type", null2Blank(hinttype))
						.addExtra("orderno", null2Blank(orderno))
						.addExtra("paymethod", null2Blank(paymethod))
						.addExtra("usetype", null2Blank(usetype))
						.addExtra("ordertype", null2Blank(ordertype))
						.build())
				.setOptions(getPushOptions())
				.build();
	}
	
	/**
	 * 挤下线通知
	 * @param content
	 * @param userid
	 * @return
	 */
	public static PushPayload createOfflineObj4IOS(String content,List<String> userids,String offline_type,List<String> tag_ands){
		String alert = "挤下线";
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(getAudience(userids, tag_ands))
				.setNotification(Notification.newBuilder()
	                    .addPlatformNotification(IosNotification.newBuilder()
	                            .setAlert(alert)
	                            .addExtra("type", null2Blank(offline_type))
	                            .build())
//	                    .addPlatformNotification(AndroidNotification.newBuilder()
//	                    		.setAlert(alert)
//	                    		.addExtra("type", offline_type)
//	                    		.build())
	                    .build())
//				.setMessage(Message.newBuilder()
//						.setContentType("text")
//						.setMsgContent(content)
//						.setTitle(alert)
//						//消息类型
//						.addExtra("type", offline_type)
//						.build())
				.setOptions(getPushOptions())
				.build();
	}
	
	/**
	 * 挤下线通知
	 * @param content
	 * @param userid
	 * @return
	 */
	public static PushPayload createOfflineObj4Android(String content,List<String> userids,String offline_type,List<String> tag_ands){
		String alert = "挤下线";
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(getAudience(userids, tag_ands))
//				.setNotification(Notification.newBuilder()
//	                    .addPlatformNotification(IosNotification.newBuilder()
//	                            .setAlert(alert)
//	                            .addExtra("type", offline_type)
//	                            .build())
//	                    .addPlatformNotification(AndroidNotification.newBuilder()
//	                    		.setAlert(alert)
//	                    		.addExtra("type", offline_type)
//	                    		.build())
//	                    .build())
				.setMessage(Message.newBuilder()
						.setContentType("text")
						.setMsgContent(content)
						.setTitle(alert)
						//消息类型
						.addExtra("type", null2Blank(offline_type))
						.build())
				.setOptions(getPushOptions())
				.build();
	}
	
	/**
	 * 取消订单的提示pushobj对象
	 * @return
	 */
	public static PushPayload createCancelOrderObj4IOS(OrderInfo orderinfo,List<String> userids,List<String> tag_ands){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "订单取消";
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, tag_ands))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(alert)
		                            //消息类型
									.addExtra("type", CANCELORDER)
									.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
									.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
									.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
									.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
									.addExtra("ordertype", null2Blank(orderinfo.getType()))
									.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
									.addExtra("estimatedtime", orderinfo.getEstimatedtime())
									.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
									.addExtra("estimatedcost", orderinfo.getEstimatedcost())
									.addExtra("isusenow", orderinfo.isIsusenow())
									.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 取消订单的提示pushobj对象
	 * @return
	 */
	public static PushPayload createCancelOrderObj4Android(OrderInfo orderinfo,List<String> userids,List<String> tag_ands){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "订单取消";
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, tag_ands))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent("订单取消")
							.setTitle(alert)
							//消息类型
							.addExtra("type", CANCELORDER)
							.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
							.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
							.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
							.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
							.addExtra("ordertype", null2Blank(orderinfo.getType()))
							.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
							.addExtra("estimatedtime", orderinfo.getEstimatedtime())
							.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
							.addExtra("estimatedcost", orderinfo.getEstimatedcost())
							.addExtra("isusenow", orderinfo.isIsusenow())
							.addExtra("lasttime",null2Blank( orderinfo.getLasttime()))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 派单失败的提示pushobj对象
	 * @return
	 */
	public static PushPayload createSendOrderFailObj4IOS(OrderInfo orderinfo,List<String> userids,List<String> tag_ands){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "派单失败";
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, tag_ands))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(alert)
		                            //消息类型
									.addExtra("type", SENDORDERFAIL)
									.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
									.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
									.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
									.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
									.addExtra("ordertype", null2Blank(orderinfo.getType()))
									.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
									.addExtra("estimatedtime", orderinfo.getEstimatedtime())
									.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
									.addExtra("estimatedcost", orderinfo.getEstimatedcost())
									.addExtra("isusenow", orderinfo.isIsusenow())
									.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 派单失败的提示pushobj对象
	 * @return
	 */
	public static PushPayload createSendOrderFailObj4Android(OrderInfo orderinfo,List<String> userids,List<String> tag_ands){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "派单失败";
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, tag_ands))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent("您有一个订单超时，已自动关闭")
							.setTitle("提示")
							//消息类型
							.addExtra("type", SENDORDERFAIL)
							.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
							.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
							.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
							.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
							.addExtra("ordertype", null2Blank(orderinfo.getType()))
							.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
							.addExtra("estimatedtime", orderinfo.getEstimatedtime())
							.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
							.addExtra("estimatedcost", orderinfo.getEstimatedcost())
							.addExtra("isusenow", orderinfo.isIsusenow())
							.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 创建一个订单变更的pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createChangeOrderObj4IOS(OrderInfo orderinfo,List<String> userids){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "订单司机变更";
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, null))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(alert)
		                            //消息类型
									.addExtra("type", CHANGEORER)
									.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
									.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
									.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
									.addExtra("onlat", orderinfo.getOnlat())
									.addExtra("onlng", orderinfo.getOnlng())
									.addExtra("offlat", orderinfo.getOfflat())
									.addExtra("offlng", orderinfo.getOfflng())
									.addExtra("estimatedtime", orderinfo.getEstimatedtime())
									.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
									.addExtra("estimatedcost", orderinfo.getEstimatedcost())
									.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
									.addExtra("ordertype", null2Blank(orderinfo.getType()))
									.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
									.addExtra("isusenow", orderinfo.isIsusenow())
									.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
									.addExtra("remark", null2Blank(orderinfo.getRemark()))
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 创建一个订单变更的pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createChangeOrderObj4Android(OrderInfo orderinfo,List<String> userids){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "订单司机变更";
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, null))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent("订单司机变更")
							.setTitle(alert)
							//消息类型
							.addExtra("type", CHANGEORER)
							.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
							.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
							.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
							.addExtra("onlat", orderinfo.getOnlat())
							.addExtra("onlng", orderinfo.getOnlng())
							.addExtra("offlat", orderinfo.getOfflat())
							.addExtra("offlng", orderinfo.getOfflng())
							.addExtra("estimatedtime", orderinfo.getEstimatedtime())
							.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
							.addExtra("estimatedcost", orderinfo.getEstimatedcost())
							.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
							.addExtra("ordertype", null2Blank(orderinfo.getType()))
							.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
							.addExtra("isusenow", orderinfo.isIsusenow())
							.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
							.addExtra("remark", null2Blank(orderinfo.getRemark()))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 创建人工派单任务的pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createTaskOrderObj4IOS(OrderInfo orderinfo,List<String> userids){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "订单任务";
		String remark =  StringUtils.isBlank(orderinfo.getRemark())?"":orderinfo.getRemark();
		String orderstyle = StringUtils.isBlank(orderinfo.getOrderstyle())?(OrderEnum.ORDERTYPE_TAXI.code.equals(orderinfo.getType())?"1":"0"):orderinfo.getOrderstyle();
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, null))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(alert)
		                            //设置后台语音
		                            .setSound("sound.m4r")
		                            //消息类型
									.addExtra("type", TASKORDER)
									.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
									.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
									.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
									.addExtra("onlat", orderinfo.getOnlat())
									.addExtra("onlng", orderinfo.getOnlng())
									.addExtra("offlat", orderinfo.getOfflat())
									.addExtra("offlng", orderinfo.getOfflng())
									.addExtra("estimatedtime", orderinfo.getEstimatedtime())
									.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
									.addExtra("estimatedcost", orderinfo.getEstimatedcost())
									.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
									.addExtra("ordertype", null2Blank(orderinfo.getType()))
									.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
									.addExtra("isusenow", orderinfo.isIsusenow())
									.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
									.addExtra("orderstyle",orderstyle)
									.addExtra("remark",remark)
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 创建人工派单任务的pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createTaskOrderObj4Android(OrderInfo orderinfo,List<String> userids){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "订单任务";
		String remark =  StringUtils.isBlank(orderinfo.getRemark())?"":orderinfo.getRemark();
		String orderstyle = StringUtils.isBlank(orderinfo.getOrderstyle())?(OrderEnum.ORDERTYPE_TAXI.code.equals(orderinfo.getUsetype())?"1":"0"):orderinfo.getOrderstyle();
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, null))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent("订单任务")
							.setTitle(alert)
							//消息类型
							.addExtra("type", TASKORDER)
							.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
							.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
							.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
							.addExtra("onlat", orderinfo.getOnlat())
							.addExtra("onlng", orderinfo.getOnlng())
							.addExtra("offlat", orderinfo.getOfflat())
							.addExtra("offlng", orderinfo.getOfflng())
							.addExtra("estimatedtime", orderinfo.getEstimatedtime())
							.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
							.addExtra("estimatedcost", orderinfo.getEstimatedcost())
							.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
							.addExtra("ordertype", null2Blank(orderinfo.getType()))
							.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
							.addExtra("isusenow", orderinfo.isIsusenow())
							.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
							.addExtra("orderstyle",null2Blank(orderstyle))
							.addExtra("remark",null2Blank(remark))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 创建一个行程提醒的pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createRemindOrderObj4IOS(OrderInfo orderinfo,List<String> userids){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "行程提醒";
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, null))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(alert)
		                            //消息类型
									.addExtra("type", REMINDORDER)
									.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
									.addExtra("ordertype", null2Blank(orderinfo.getType()))
									.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
									.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
									.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
									.addExtra("onlat", orderinfo.getOnlat())
									.addExtra("onlng", orderinfo.getOnlng())
									.addExtra("offlat", orderinfo.getOfflat())
									.addExtra("offlng", orderinfo.getOfflng())
									.addExtra("estimatedtime", orderinfo.getEstimatedtime())
									.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
									.addExtra("estimatedcost", orderinfo.getEstimatedcost())
									.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
									.addExtra("isusenow", orderinfo.isIsusenow())
									.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 创建一个行程提醒的pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createRemindOrderObj4Android(OrderInfo orderinfo,List<String> userids){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "行程提醒";
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, null))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent("行程提醒")
							.setTitle(alert)
							//消息类型
							.addExtra("type", REMINDORDER)
							.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
							.addExtra("ordertype", null2Blank(orderinfo.getType()))
							.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
							.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
							.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
							.addExtra("onlat", orderinfo.getOnlat())
							.addExtra("onlng", orderinfo.getOnlng())
							.addExtra("offlat", orderinfo.getOfflat())
							.addExtra("offlng", orderinfo.getOfflng())
							.addExtra("estimatedtime", orderinfo.getEstimatedtime())
							.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
							.addExtra("estimatedcost", orderinfo.getEstimatedcost())
							.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
							.addExtra("isusenow", orderinfo.isIsusenow())
							.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 强派单pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createSilenceOrderObj4IOS(OrderInfo orderinfo,List<String> userids){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "新订单来了";
		//不存在就是网约车订单之前的网约车是没有此属性的
		String orderstyle = StringUtils.isBlank(orderinfo.getOrderstyle())?(OrderEnum.ORDERTYPE_TAXI.code.equals(orderinfo.getUsetype())?"1":"0"):orderinfo.getOrderstyle();
		String remark =  StringUtils.isBlank(orderinfo.getRemark())?"":orderinfo.getRemark();
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, null))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(alert)
		                            //消息类型
									.addExtra("type", SILENCEORDER)
									.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
									//抢单时间
									.addExtra("grabtime", orderinfo.getGrabtime())
									.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
									.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
									.addExtra("onlat", orderinfo.getOnlat())
									.addExtra("onlng", orderinfo.getOnlng())
									.addExtra("offlat", orderinfo.getOfflat())
									.addExtra("offlng", orderinfo.getOfflng())
									.addExtra("usetime", format.format(orderinfo.getUsetime()))
									.addExtra("ordertype", null2Blank(orderinfo.getType()))
									.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
									.addExtra("isusenow", orderinfo.isIsusenow())
									.addExtra("estimatedtime", orderinfo.getEstimatedtime())
									.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
									.addExtra("estimatedcost", orderinfo.getEstimatedcost())
									.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
									.addExtra("remark",null2Blank(remark))
									.addExtra("orderstyle", null2Blank(orderstyle))
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 强派单pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createSilenceOrderObj4Android(OrderInfo orderinfo,List<String> userids){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "新订单来了";
		//不存在就是网约车订单之前的网约车是没有此属性的
		String orderstyle = StringUtils.isBlank(orderinfo.getOrderstyle())?(OrderEnum.ORDERTYPE_TAXI.code.equals(orderinfo.getUsetype())?"1":"0"):orderinfo.getOrderstyle();
		String remark =  StringUtils.isBlank(orderinfo.getRemark())?"":orderinfo.getRemark();
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, null))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent("新订单消息")
							.setTitle(alert)
							//消息类型
							.addExtra("type", SILENCEORDER)
							.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
							//抢单时间
							.addExtra("grabtime", orderinfo.getGrabtime())
							.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
							.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
							.addExtra("onlat", orderinfo.getOnlat())
							.addExtra("onlng", orderinfo.getOnlng())
							.addExtra("offlat", orderinfo.getOfflat())
							.addExtra("offlng", orderinfo.getOfflng())
							.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
							.addExtra("ordertype", null2Blank(orderinfo.getType()))
							.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
							.addExtra("isusenow", orderinfo.isIsusenow())
							.addExtra("estimatedtime", orderinfo.getEstimatedtime())
							.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
							.addExtra("estimatedcost", orderinfo.getEstimatedcost())
							.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
							.addExtra("remark",null2Blank(remark))
							.addExtra("orderstyle", null2Blank(orderstyle))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 抢单pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createGrabOrderObj4IOS(OrderInfo orderinfo,List<String> userids){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "新订单来了";
		//不存在就是网约车订单之前的网约车是没有此属性的
		String orderstyle = StringUtils.isBlank(orderinfo.getOrderstyle())?(OrderEnum.ORDERTYPE_TAXI.code.equals(orderinfo.getUsetype())?"1":"0"):orderinfo.getOrderstyle();
		String remark =  StringUtils.isBlank(orderinfo.getRemark())?"":orderinfo.getRemark();
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, null))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(alert)
									//抢单消息声音
									.setSound("sound.m4r")
		                            //消息类型
									.addExtra("type", GRABORDER)
									.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
									//抢单时间
									.addExtra("grabtime", orderinfo.getGrabtime())
									.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
									.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
									.addExtra("onlat", orderinfo.getOnlat())
									.addExtra("onlng", orderinfo.getOnlng())
									.addExtra("offlat", orderinfo.getOfflat())
									.addExtra("offlng", orderinfo.getOfflng())
									.addExtra("usetime", format.format(orderinfo.getUsetime()))
									.addExtra("ordertype", null2Blank(orderinfo.getType()))
									.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
									.addExtra("isusenow", orderinfo.isIsusenow())
									.addExtra("estimatedtime", orderinfo.getEstimatedtime())
									.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
									.addExtra("estimatedcost", orderinfo.getEstimatedcost())
									.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
									.addExtra("remark",null2Blank(remark))
									.addExtra("alwayshowdialog",orderinfo.isAlwayshowdialog())
									.addExtra("orderstyle", null2Blank(orderstyle))
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 抢单pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createGrabOrderObj4Android(OrderInfo orderinfo,List<String> userids){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String alert = "新订单来了";
		//不存在就是网约车订单之前的网约车是没有此属性的
		String orderstyle = StringUtils.isBlank(orderinfo.getOrderstyle())?(OrderEnum.ORDERTYPE_TAXI.code.equals(orderinfo.getUsetype())?"1":"0"):orderinfo.getOrderstyle();
		String remark =  StringUtils.isBlank(orderinfo.getRemark())?"":orderinfo.getRemark();
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, null))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent("新订单消息")
							.setTitle(alert)
							//消息类型
							.addExtra("type", GRABORDER)
							.addExtra("orderno", null2Blank(orderinfo.getOrderno()))
							//抢单时间
							.addExtra("grabtime", orderinfo.getGrabtime())
							.addExtra("onaddress", null2Blank(orderinfo.getOnaddress()))
							.addExtra("offaddress", null2Blank(orderinfo.getOffaddress()))
							.addExtra("onlat", orderinfo.getOnlat())
							.addExtra("onlng", orderinfo.getOnlng())
							.addExtra("offlat", orderinfo.getOfflat())
							.addExtra("offlng", orderinfo.getOfflng())
							.addExtra("usetime", null2Blank(format.format(orderinfo.getUsetime())))
							.addExtra("ordertype", null2Blank(orderinfo.getType()))
							.addExtra("usetype", null2Blank(orderinfo.getUsetype()))
							.addExtra("isusenow", orderinfo.isIsusenow())
							.addExtra("estimatedtime", orderinfo.getEstimatedtime())
							.addExtra("estimatedmileage", orderinfo.getEstimatedmileage())
							.addExtra("estimatedcost", orderinfo.getEstimatedcost())
							.addExtra("lasttime", null2Blank(orderinfo.getLasttime()))
							.addExtra("remark",null2Blank(remark))
							.addExtra("alwayshowdialog",orderinfo.isAlwayshowdialog())
							.addExtra("orderstyle", null2Blank(orderstyle))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 解绑车辆pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createUnBindCarObj4IOS(List<String> userids,String content,String visualtype){
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, null))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(content)
		                            //消息类型
									.addExtra("type", UNBINDCAR)
									.addExtra("content", null2Blank(content))
									.addExtra("visualtype", null2Default(visualtype))
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 解绑车辆pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createUnBindCarObj4Android(List<String> userids,String content,String visualtype){
		String alert = "车辆被解绑";
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, null))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent("车辆被解绑")
							.setTitle(alert)
							//消息类型
							.addExtra("type", UNBINDCAR)
							.addExtra("content", null2Blank(content))
							.addExtra("visualtype", null2Default(visualtype))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 绑定车辆pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createBindCarObj4IOS(List<String> userids,String content){
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, null))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(content)
		                            //消息类型
									.addExtra("type", BINDCAR)
									.addExtra("content", null2Blank(content))
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 绑定车辆pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createBindCarObj4Android(List<String> userids,String content){
		String alert = "车辆被绑定";
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, null))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent("车辆被绑定")
							.setTitle(alert)
							//消息类型
							.addExtra("type", BINDCAR)
							.addExtra("content", null2Blank(content))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 删除司机pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createDeleteDriver4IOS(List<String> userids,String content){
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, null))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(content)
		                            //消息类型
									.addExtra("type", DELETEDRIVER)
									.addExtra("content", null2Blank(content))
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}


	
	/**
	 * 删除司机pushobj
	 * @param order
	 * @param userids
	 * @return
	 */
	public static PushPayload createDeleteDriver4Android(List<String> userids,String content){
		String alert = "您已被删除";
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, null))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent("您已被删除")
							.setTitle(alert)
							//消息类型
							.addExtra("type", DELETEDRIVER)
							.addExtra("content", null2Blank(content))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 订单复核通知
	 * @param reviewinfo
	 * @param userids
	 * @return
	 */
	public static PushPayload createOrderReviewObj4IOS(Map<String,Object> reviewinfo,List<String> userids){
		String alert = "您收到一条订单复核反馈：";
		String orderno = (String) reviewinfo.get("orderno");
		String content = (String) reviewinfo.get("content");
		String usetime = (String) reviewinfo.get("usetime");
		String ordertype = (String) reviewinfo.get("ordertype");
		String onaddress = (String) reviewinfo.get("onaddress");
		String offaddress = (String) reviewinfo.get("offaddress");
		String usetype= (String) reviewinfo.get("usetype");
		return PushPayload.newBuilder()
					.setPlatform(Platform.ios())
					.setAudience(getAudience(userids, null))
					.setNotification(Notification.newBuilder()
		                    .addPlatformNotification(IosNotification.newBuilder()
		                            .setAlert(alert+content)
		                            //消息类型
									.addExtra("type", ORDERREVIEW)
									.addExtra("orderno", null2Blank(orderno))
									.addExtra("content", null2Blank(content))
									.addExtra("usetime", null2Blank(usetime))
									.addExtra("ordertype", null2Blank(ordertype))
									.addExtra("onaddress", null2Blank(onaddress))
									.addExtra("offaddress", null2Blank(offaddress))
									.addExtra("usetype", null2Blank(usetype))
		                            .build())
		                    .build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 订单复核通知
	 * @param reviewinfo
	 * @param userids
	 * @return
	 */
	public static PushPayload createOrderReviewObj4Android(Map<String,Object> reviewinfo,List<String> userids){
		String orderno = (String) reviewinfo.get("orderno");
		String content = (String) reviewinfo.get("content");
		String usetime = (String) reviewinfo.get("usetime");
		String ordertype = (String) reviewinfo.get("ordertype");
		String onaddress = (String) reviewinfo.get("onaddress");
		String offaddress = (String) reviewinfo.get("offaddress");
		String usetype = (String) reviewinfo.get("usetype");
		return PushPayload.newBuilder()
					.setPlatform(Platform.android())
					.setAudience(getAudience(userids, null))
					.setMessage(Message.newBuilder()
							.setContentType("text")
							.setMsgContent(content)
							.setTitle("订单复核")
							//消息类型
							.addExtra("type", ORDERREVIEW)
							.addExtra("orderno", null2Blank(orderno))
							.addExtra("content", null2Blank(content))
							.addExtra("usetime", null2Blank(usetime))
							.addExtra("ordertype", null2Blank(ordertype))
							.addExtra("onaddress", null2Blank(onaddress))
							.addExtra("offaddress", null2Blank(offaddress))
							.addExtra("usetype", null2Blank(usetype))
							.build())
					.setOptions(getPushOptions())
					.build();
	}
	
	/**
	 * 空置当“”空串处理
	 * @param value
	 * @return
	 */
	private static String null2Blank(String value){
		if(StringUtils.isBlank(value)){
			return "";
		}
		return value;
	}
	
	/**
	 * 空值当弹窗处理
	 * @param value
	 * @return
	 */
	private static String null2Default(String value){
		if(StringUtils.isBlank(value)){
			return WindowVisualEnum.TANCHUANG.code;
		}
		return value;
	}
	
	public static void main(String[] args){
		List<String> s = new ArrayList<String>();
		s.add("15623695619");
		List<String> ss = new ArrayList<String>();
		ss.add("3");
		ss.add("0");
		ss.add("1");
		ss.add("2");
		ss.remove("0");
		ss.remove("1");
		System.out.print(ss.get(1));
//		PushPayload pload = creatHintObj4IOS("您收到一笔退款，请前往账户余额中查看！", PushObjFactory.REFUND, "YCxxxxxxx", s, ss);
//		System.out.println(pload.toJSON());
//		PushResult rr = AppMessageUtil.send(pload,AppMessageUtil.APPTYPE_DRIVER);
//		System.out.println(rr.isResultOK());
//		List<String> userids = new ArrayList<String>();
//		userids.add("15623695619");
//		List<String> tag_ands = new ArrayList<String>();
//		tag_ands.add("1");
//		PushPayload pload = creatHintObj("司机已接单",PushObjFactory.HINT_HAVETAKEORDER,"xxxxxxx", userids,null);
//		tag_ands.add("userotkenxxxxxxx");
//		PushPayload pload2 = createOfflineObj("你被挤下线了", tag_ands);
//		System.out.println(pload.toJSON());
//		System.out.println(pload2.toJSON());
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		System.out.println(format.format(new Date()));
	}



	/**
	 * 为app端推送一个hint消息
	 * @param content
	 * @param hinttype
	 * @param userids
	 * @return
	 */
	public static PushPayload creatPending4Android(String content,String hinttype,String operation,List<String> userids){
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(getAudience(userids, null))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder()
								.setAlert(content)
								.addExtra("type", null2Blank(hinttype))
								.addExtra("operation", null2Blank(operation))
								.addExtra("content", null2Blank(content))
								.build())
						.build())
				.setOptions(getPushOptions())
				.build();
	}

	/**
	 * 为app端推送一个hint消息
	 * @param content
	 * @param userids
	 * @return
	 */
	public static PushPayload creatPending4Ios(String content,String hinttype,String operation,List<String> userids){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(getAudience(userids, null))
				.setMessage(Message.newBuilder()
						.setContentType("text")
						.setMsgContent(content)
						.setTitle(content)
						//消息类型
						.addExtra("type", null2Blank(hinttype))
						.addExtra("operation", null2Blank(operation))
						.addExtra("content", null2Blank(content))
						.build())
				.setOptions(getPushOptions())
				.build();
	}



	/**
	 * 交接班---交班成功
	 * @param content
	 * @param userids
	 * @return
	 */
	public static PushPayload createShiftProcessed4IOS(List<String> userids,String content){
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(getAudience(userids, null))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder()
								.setAlert(content)
								//消息类型
								.addExtra("type", HINT_SHIFT_PROCESSED)
								.addExtra("content", null2Blank(content))
								.build())
						.build())
				.setOptions(getPushOptions())
				.build();
	}



	/**
	 * 交接班---交班成功
	 * @param content
	 * @param userids
	 * @return
	 */
	public static PushPayload createShiftProcessed4Android(List<String> userids,String content){
		String alert = "您已被删除";
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(getAudience(userids, null))
				.setMessage(Message.newBuilder()
						.setContentType("text")
						.setMsgContent("交接班成功")
						.setTitle(alert)
						//消息类型
						.addExtra("type", HINT_SHIFT_PROCESSED)
						.addExtra("content", null2Blank(content))
						.build())
				.setOptions(getPushOptions())
				.build();
	}


	/**
	 * 交接班---车辆回收
	 * @param content
	 * @param userids
	 * @return
	 */
	public static PushPayload createShiftRecycle4IOS(List<String> userids,String content){
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(getAudience(userids, null))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder()
								.setAlert(content)
								//消息类型
								.addExtra("type", HINT_SHIFT_RECYCLE)
								.addExtra("content", null2Blank(content))
								.build())
						.build())
				.setOptions(getPushOptions())
				.build();
	}



	/**
	 * 交接班---车辆回收
	 * @param content
	 * @param userids
	 * @return
	 */
	public static PushPayload createShiftRecycle4Android(List<String> userids,String content){
		String alert = "您已被删除";
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(getAudience(userids, null))
				.setMessage(Message.newBuilder()
						.setContentType("text")
						.setMsgContent("车辆回收成功")
						.setTitle(alert)
						//消息类型
						.addExtra("type", HINT_SHIFT_RECYCLE)
						.addExtra("content", null2Blank(content))
						.build())
				.setOptions(getPushOptions())
				.build();
	}
}
