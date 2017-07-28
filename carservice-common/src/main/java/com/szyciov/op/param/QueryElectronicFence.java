package com.szyciov.op.param;

/**
 * 电子围栏返回实体 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryElectronicFence {
	/**
	 * 电子围栏设置Id
	 */
	private String electronicFenceId;
	/**
	 * 围栏名称
	 */
	private String name;
	/**
	 * 受控设备数
	 */
	private Integer count;
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

	public Integer getSwitchState() {
		return switchState;
	}

	public void setSwitchState(Integer switchState) {
		this.switchState = switchState;
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

	public String getElectronicFenceId() {
		return electronicFenceId;
	}

	public void setElectronicFenceId(String electronicFenceId) {
		this.electronicFenceId = electronicFenceId;
	}

}
