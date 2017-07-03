package com.szyciov.entity;

import com.szyciov.driver.enums.DriverMessageEnum;

/**
 * @ClassName NewsState 
 * @author Efy Shu
 * @Description 消息读取状态枚举
 * @date 2016年11月25日 下午4:09:23 
 */
public enum NewsState {
	UNREAD("0","未读"),
	READED("1","已读"),
	
	/**以下消息是司机端会接收的***/
	CANCELORDER_OPRATE("19","运管平台取消"),
	CANCELORDER_LEASE("20","租赁公司取消"),
	CANCELORDER_PASSENGER_ORG("21","机构用户取消"),
	CANCELORDER_PASSENGER_OP("22","个人用户取消"),
	NEWORDER("23","新的任务"),
	CHANGEDRIVER("24","变更司机"),
	REMINDORDER("25","行程提醒"),
	REVIEWORDER("26","订单复核"),
	DRIVERUNBIND_LE("34","租赁解绑车辆"),
	DRIVERBINDED_LE("35","租赁绑定车辆"),
	DRIVERUNBIND_OP("41","运管解绑车辆"),
	DRIVERBINDED_OP("42","运管绑定车辆"),
	REVIEWORDER_MORE_OP("43","运管订单复核(复核后金额少于实际金额)"),
	REVIEWORDER_LESS_OP("44","运管订单复核(复核后金额多于实际金额)"),
	REVIEWORDER_REFUSE_OP("45","运管订单复核(拒绝受理)"),
	DRIVERWITHDRAW_ACCESS_LE("28","租赁司机通过提现"),
	DRIVERWITHDRAW_REFUSE_LE("31","租赁司机拒绝提现"),
	DRIVERWITHDRAW_ACCESS_OP("38","运管司机通过提现"),
	DRIVERWITHDRAW_REFUSE_OP("40","运管司机拒绝提现"),
	/**以下消息是司机端要发送的***/
	LEASERECHARGE("46","租赁司机充值成功"),
	OPERATERECHARGE("47","运管司机充值成功"),
	LEASEWITHDRAW("48","租赁司机提现申请成功"),
	OPERATEWITHDRAW("49","运管司机提现申请成功");
	
	/**
	 * 转换数据库消息类型到司机端返回值消息类型
	 * @return
	 */
	public static String convertNewsType(int state){
		return CANCELORDER_OPRATE.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_CANCELORDER.code : 
					CANCELORDER_LEASE.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_CANCELORDER.code :
					CANCELORDER_PASSENGER_ORG.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_CANCELORDER.code :
					CANCELORDER_PASSENGER_OP.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_CANCELORDER.code :
					NEWORDER.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_NEWORDER.code :
					CHANGEDRIVER.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_NEWORDER.code :
					REMINDORDER.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_REMINDORDER.code : 
					REVIEWORDER.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_REVIEWRESULT.code : 
					DRIVERUNBIND_LE.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_UNBINDCAR.code : 
					DRIVERBINDED_LE.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_BINDCAR.code : 
					DRIVERUNBIND_OP.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_UNBINDCAR.code : 
					DRIVERBINDED_OP.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_BINDCAR.code : 
					LEASERECHARGE.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_WITHDRAW.code : 
					OPERATERECHARGE.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_WITHDRAW.code : 
					LEASEWITHDRAW.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_WITHDRAW.code : 
					OPERATEWITHDRAW.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_WITHDRAW.code : 
					REVIEWORDER_MORE_OP.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_REVIEWRESULT.code : 
					REVIEWORDER_LESS_OP.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_REVIEWRESULT.code : 
					REVIEWORDER_REFUSE_OP.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_REVIEWRESULT.code : 
					DRIVERWITHDRAW_ACCESS_LE.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_WITHDRAW.code : 
					DRIVERWITHDRAW_REFUSE_LE.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_WITHDRAW.code : 
					DRIVERWITHDRAW_ACCESS_OP.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_WITHDRAW.code : 
					DRIVERWITHDRAW_REFUSE_OP.code.equals(state+"") ? DriverMessageEnum.NEWS_TYPE_WITHDRAW.code : 
						
						
					DriverMessageEnum.NEWS_TYPE_CANCELORDER.code;
	}
	
	/**
	 * 检查是否已读
	 * @param newsstate
	 * @return
	 */
	public static boolean isReaded(String newsstate){
		return READED.code.equals(newsstate);
	}
	
	public String code;
	public String msg;
	private NewsState(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
