package com.szyciov.op.param;

public class VehicleMonitorParam extends BaseParam {
	/**
	 * 关联ID
	 */
	private String vehcId;
	/**
	 * 关联类型 必须(1_车辆;)默认车辆
	 */
	private Integer relationType;
	/**
	 * 设备ID
	 */
	private String eqpId;
	/**
	 * 设备IMEI 模糊匹配
	 */
	private String imei;
	/**
	 * 所属部门
	 */
	private String departmentId;
	/**
	 * 纠偏选项 int 1_不纠偏;2_百度纠偏;默认不纠偏
	 */
	private Integer processOption;
	/**
	 * 用户 所属单位ID
	 */
	private String organizationId;

	public String getVehcId() {
		return vehcId;
	}

	public void setVehcId(String vehcId) {
		this.vehcId = vehcId;
	}

	public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	public String getEqpId() {
		return eqpId;
	}

	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getProcessOption() {
		return processOption;
	}

	public void setProcessOption(Integer processOption) {
		this.processOption = processOption;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
}
