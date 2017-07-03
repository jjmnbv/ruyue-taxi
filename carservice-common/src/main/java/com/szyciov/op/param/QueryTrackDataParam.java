package com.szyciov.op.param;

/**
 * 设备行程查询参数 <一句话功能简述> <功能详细描述> <一句话功能简述> <功能详细描述>
 * 
 * @author DELL
 * @version [版本号, 2017年3月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryTrackDataParam extends BasePageParam {

	/**
	 * 车牌
	 */
	private String plate;
	/**
	 * 关联ID
	 */
	private String vehcId;
	/**
	 * 关联类型 必须(1_车辆;)默认车辆
	 */
	private Integer relationType;
	/**
	 * 设备IMEI
	 */
	private String imei;
	/**
	 * 所属部门
	 */
	private String departmentId;

	/**
	 * 查询关键字
	 */
	private String keyword;
	/**
	 * 登录用户 所属机构ID
	 */
	private String organizationId;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

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

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
}
