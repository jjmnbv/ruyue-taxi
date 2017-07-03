package com.szyciov.touch.dto;

/**
 * 报警实体
 * @author zhu
 *
 */
public class TravelAlarmDTO {

	/**
	 * 报警ID
	 */
	private String alarmId;
	
	/**
	 * 订单ID
	 */
	private String orderId;
	
	/**
	 * 报警时间
	 */
	private String alarmTime;
	
	/**
	 * 报警来源, 1-乘客,2-司机
	 */
	private String alarmSource;
	
	/**
	 * 处理状态, 1-待处理,2-已处理
	 */
	private String processState;
	
	/**
	 * 下单人标识ID
	 */
	private String orderPersonId;
	
	/**
	 * 乘车人手机号
	 */
	private String passengerPhone;
	
	/**
	 * 服务车辆车牌号
	 */
	private String driverPlateNo;
	
	/**
	 * 服务司机姓名
	 */
	private String driverName;
	
	/**
	 * 服务司机手机号码
	 */
	private String driverPhone;
	
	/**
	 * 处理时间
	 */
	private String processTime;
	
	/**
	 * 处理结果, 1-假警,2-涉嫌遇险
	 */
	private String processResult;

	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAlarmSource() {
		return alarmSource;
	}

	public void setAlarmSource(String alarmSource) {
		this.alarmSource = alarmSource;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public String getOrderPersonId() {
		return orderPersonId;
	}

	public void setOrderPersonId(String orderPersonId) {
		this.orderPersonId = orderPersonId;
	}

	public String getPassengerPhone() {
		return passengerPhone;
	}

	public void setPassengerPhone(String passengerPhone) {
		this.passengerPhone = passengerPhone;
	}

	public String getDriverPlateNo() {
		return driverPlateNo;
	}

	public void setDriverPlateNo(String driverPlateNo) {
		this.driverPlateNo = driverPlateNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}
}
