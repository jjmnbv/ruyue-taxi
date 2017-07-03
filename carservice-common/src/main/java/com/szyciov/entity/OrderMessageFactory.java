package com.szyciov.entity;

import java.util.Date;

import com.szyciov.driver.entity.OrderInfoMessage;
import com.szyciov.driver.enums.OrderState;

/**
 * @ClassName OrderMessageFactory 
 * @author Efy Shu
 * @Description 消息工厂类
 * @date 2016年11月25日 下午4:40:54 
 */
public class OrderMessageFactory {
	/**
	 * 消息实体
	 */
	private OrderInfoMessage msgDto;
	
	/**
	 * 消息类型
	 */
	private OrderMessageType msgType;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息文本
	 */
	private String content;
	/**
	 * 订单对象
	 */
	private AbstractOrder order;
	
	/**
	 * 
	 * @ClassName ORderMessageType 
	 * @author Efy Shu
	 * @date 2016年11月28日 下午6:45:36
	 */
	public enum OrderMessageType{
		CANCELORDER_OP(19,"取消订单","","createCancelMessage"),
		CANCELORDER_LE(20,"取消订单","","createCancelMessage"),
		CANCELORDER_OU(21,"取消订单","","createCancelMessage"),
		CANCELORDER_PU(22,"取消订单","","createCancelMessage"),
		MANTICORDER(23,"新的任务","","createManticMessage"),
		CHANGEDRIVER(24,"更换司机","","createChangeMessage"),
		REMINDORDER(25,"出发提醒","","createRemindMessage"),
		REVIEWORDER(26,"复核反馈","","createReviewMessage");
		
		public int code;
		public String title;
		public String content;
		public String method;
		
		public static OrderMessageType getByCode(int code){
			return code == CANCELORDER_OP.code ? CANCELORDER_OP : 
						code == CANCELORDER_LE.code ? CANCELORDER_LE : 
						code == CANCELORDER_OU.code ? CANCELORDER_OU : 
						code == CANCELORDER_PU.code ? CANCELORDER_PU : 
						code == MANTICORDER.code ? MANTICORDER : 
						code == CHANGEDRIVER.code ? CHANGEDRIVER : 
						code == REMINDORDER.code ? REMINDORDER : 
						code == REVIEWORDER.code ? REVIEWORDER : CANCELORDER_LE;
		}
		
		public static OrderMessageType getByCancelParty(String cp,boolean isOrg){
			CancelParty cancel = CancelParty.getByCode(cp);
			return CancelParty.PASSENGER.equals(cancel) ? isOrg ? CANCELORDER_OU  : CANCELORDER_PU :
						CancelParty.ORGAN.equals(cancel) ? CANCELORDER_OU : 
						CancelParty.LEASE.equals(cancel) ? CANCELORDER_LE : 
						CancelParty.OPERATOR.equals(cancel) ? CANCELORDER_OP : null;
		}
		private OrderMessageType(int code, String title,String content,String method){
			this.code = code;
			this.title = title;
			this.content = content;
			this.method = method;
		}
	}
	
	/**
	 * 构造
	 */
	public OrderMessageFactory(AbstractOrder order,OrderMessageType msgType) {
		this.order = order;
		this.msgType = msgType;
	}
	
	/**
	 * 构造
	 */
	public OrderMessageFactory(AbstractOrder order,OrderMessageType msgType,String title, String content) {
		this.order = order;
		this.msgType = msgType;
		this.title = title;
		this.content = content;
	}
	
	/**
	 * 创建消息实体
	 * @return
	 */
	public OrderInfoMessage createMessage(){
		try {
			this.getClass().getDeclaredMethod(msgType.method).invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msgDto;
	}
	
	@SuppressWarnings("unused")
	private void createCancelMessage(){
		createRemindMessage();
		msgDto.setLasttime(null);
	}
	
	@SuppressWarnings("unused")
	private void createManticMessage(){
		createRemindMessage();
		msgDto.setLasttime("");
	}
	
	@SuppressWarnings("unused")
	private void createChangeMessage(){
		createRemindMessage();
	}
	
	private void createRemindMessage(){
		int lasttime = (int) (((order.getUsetime().getTime() - System.currentTimeMillis()) / 1000 / 60) / 5) * 5;
		lasttime = lasttime < 0 ? 0 : lasttime;
		msgDto = new OrderInfoMessage();
		msgDto.setType(msgType.code);
		msgDto.setTitle((title == null || title.trim().isEmpty()) ? msgType.title : title);
		msgDto.setContent((content == null || content.trim().isEmpty()) ? msgType.content : content);
		msgDto.setOrderid(order.getOrderno());
		msgDto.setUsenow(order.isIsusenow()?"即刻":"预约");
		msgDto.setOrdertype(order.getOrdertype());
		msgDto.setUsetype(order.getUsetype());
		msgDto.setUsetime(order.getUsetime());
		msgDto.setOnaddr(order.getOnaddress());
		msgDto.setOffaddr(order.getOffaddress());
		msgDto.setStatus(OrderState.getByCode(order.getOrderstatus()).msg);
		msgDto.setLasttime(lasttime + "分钟");
	}
	
	@SuppressWarnings("unused")
	private void createReviewMessage(){
		createRemindMessage();
		msgDto.setLasttime(null);
		msgDto.setReviewtime(new Date());
	}

	/**  
	 * 获取消息实体  
	 * @return msgDto 消息实体  
	 */
	public OrderInfoMessage getMsgDto() {
		return msgDto;
	}
}
