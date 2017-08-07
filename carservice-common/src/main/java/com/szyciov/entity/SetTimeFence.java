package com.szyciov.entity;

/**
 * 时间栅栏实体类 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月29日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SetTimeFence {
	/**
	 * 设置时间栅栏Id
	 */
	private String id;
	/**
	 * 授权Id
	 */
	private String authorizationId;
	/**
	 * 栅栏名称
	 */
	private String entityName;
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
	 * 监控周期(1_周一;2_周二;3_周三;4_周四;5_周五;6_周六;7_周日)
	 */
	private String monitorperiod;
	/**
	 * 开关状态(1_开;2_关)
	 */
	private Integer switchState;
	/**
	 * 最后操作人
	 */
	private String operateId;
	/**
	 * 最后操作人
	 */
	private String operateStaff;
	/**
	 * 最后操作时间
	 */
	private String operateTime;
	/**
	 * 创建时间
	 */
	private Integer dataStatus;

	/**
	 * 创建人
	 */
	private String creater;
	/**
	 * 创建时间
	 */
	private String createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthorizationId() {
		return authorizationId;
	}

	public void setAuthorizationId(String authorizationId) {
		this.authorizationId = authorizationId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
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

	public Integer getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
