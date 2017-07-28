package com.szyciov.op.param;

/**
 * 查询受控设备参数 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryControlledEqpParam extends BasePageParam {
	/**
	 * 设备和栅栏关系id
	 */
	private String id;
	/**
	 * 车牌
	 */
	private String plate;
	/**
	 * 栅栏Id
	 */
	private String fenceId;
	/**
	 * 栅栏类型 1_时间;2_区域;3_电子
	 */
	private Integer fenceType;
	/**
	 * 设备ID
	 */
	private String eqpId;
	/**
	 * 操作人ID
	 */
	private String operateId;
	/**
	 * 操作人
	 */
	private String operateStaff;
	
	
	
	
	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getFenceId() {
		return fenceId;
	}

	public void setFenceId(String fenceId) {
		this.fenceId = fenceId;
	}

	public Integer getFenceType() {
		return fenceType;
	}

	public void setFenceType(Integer fenceType) {
		this.fenceType = fenceType;
	}

	public String getEqpId() {
		return eqpId;
	}

	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
