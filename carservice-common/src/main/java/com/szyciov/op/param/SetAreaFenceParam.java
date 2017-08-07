package com.szyciov.op.param;

/**
 * 设置区域栅栏相关参数 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SetAreaFenceParam extends BaseParam {
	/**
	 * 栅栏Id
	 */
	private String id;
	/**
	 * 栅栏名称
	 */
	private String name;
	/**
	 * 受控城市Id
	 */
	private String cityId;
	/**
	 * 开关状态 1_开;2_关；新增时，默认关
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
	 * 操作类型 1_新增;2_修改;3_启用/停用
	 */
	private Integer operateType;

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

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
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

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

}
