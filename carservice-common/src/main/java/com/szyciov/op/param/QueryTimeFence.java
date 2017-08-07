package com.szyciov.op.param;

/**
 * 查询时间栅栏列表 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryTimeFence {
	/**
	 * 栅栏Id
	 */
	private String id;
	/**
	 * 栅栏名称
	 */
	private String name;
	/**
	 * 受控设备数
	 */
	private Integer count;
	/**
	 * 栅栏允许开始时间(单位分钟)
	 */
	private String startTime;
	/**
	 * 栅栏允许结束时间(单位分钟)
	 */
	private String endTime;
	/**
	 * 允许运行时间
	 */
	private String allowRunTime;
	/**
	 * 监控周期
	 */
	private String monitorperiod;
	/**
	 * 启用状态
	 */
	private Integer switchState;
	/**
	 * 最后操作人
	 */
	private String operateStaff;
	/**
	 * 最后操作时间
	 */
	private String operateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getAllowRunTime() {
		return allowRunTime;
	}

	public void setAllowRunTime(String allowRunTime) {
		this.allowRunTime = allowRunTime;
	}

	public String getMonitorperiod() {
		return monitorperiod;
	}

	public void setMonitorperiod(String monitorperiod) {
		this.monitorperiod = monitorperiod;
	}

	public Integer getSwitchState() {
		return switchState;
	}

	public void setSwitchState(Integer switchState) {
		this.switchState = switchState;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOperateStaff() {
		return operateStaff;
	}

	public void setOperateStaff(String operateStaff) {
		this.operateStaff = operateStaff;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

}
