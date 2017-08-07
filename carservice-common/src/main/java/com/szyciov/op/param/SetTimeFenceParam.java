package com.szyciov.op.param;

/**
 * 新增/修改/启用(停用)时间栅栏 参数
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  huangyanan
 * @version  [版本号, 2017年3月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SetTimeFenceParam extends BaseParam {
	/**
	 * 栅栏Id
	 */
	private String id;

	/**
	 * 操作类型 1_新增;2_修改;3_启用/停用
	 */
	private Integer operateType;
	/**
	 * 栅栏名称
	 */
	private String name;
	/**
	 * 栅栏允许开始时间
	 */
	private String startTime;
	/**
	 * 栅栏允许结束时间
	 */
	private String endTime;
	/**
	 * 监控周期 1｜2｜3
	 */
	private String monitorperiod;
	/**
	 * 开关状态 1_开;2_关；默认关
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
