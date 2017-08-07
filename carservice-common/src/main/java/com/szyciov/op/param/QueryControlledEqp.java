package com.szyciov.op.param;

/**
 * 受控设备列表返回值 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryControlledEqp {
	/**
	 * 栅栏设备关系ID
	 */
	private String id;
	/**
	 * 栅栏ID
	 */
	private String fenceId;
	/**
	 * 车牌
	 */
	private String plate;
	/**
	 * 设备Id
	 */
	private String eid;
	/**
	 * 设备IMEI
	 */
	private String imei;
	/**
	 * 添加时间
	 */
	private String operateTime;
	/**
	 * 添加人ID
	 */
	private String operateId;
	/**
	 * 添加人
	 */
	private String operateStaff;
	/**
	 * 操作CZ
	 */
	private String cz;

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
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

	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	public String getFenceId() {
		return fenceId;
	}

	public void setFenceId(String fenceId) {
		this.fenceId = fenceId;
	}

}
