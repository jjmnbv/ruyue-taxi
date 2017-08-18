package com.szyciov.dto.pubVehInsur;

public class PubVehInsurQueryDto {
	
	/**
	 * 保险id
	 */
	private String id;
	
	/**
	 * 车辆ID
	 */
	private String vehicleid;
	
	/**
	 * 车牌号码
	 */
	private String fullplateno;
	/**
	 * 发动机ID
	 */
	private String engineid;
	
	/**
	 * vin车辆识别码
	 */
	private String vin;
	
	/**
	 * 保险公司
	 */
	private String insurcom;
	
	/**
	 * 保险类型名称
	 */
    private String insurtypename;
    
    /**
     * 保险类型
     */
    private String insurtype;
    /**
     * 保险号
     */
    private String insurnum;
    
    /**
     * 保险金额
     */
    private String insurcount;
    
    /**
     * 保险有效期
     */
    private String insurvalidate;
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVehicleid() {
		return vehicleid;
	}

	public void setVehicleid(String vehicleid) {
		this.vehicleid = vehicleid;
	}

	public String getFullplateno() {
		return fullplateno;
	}

	public void setFullplateno(String fullplateno) {
		this.fullplateno = fullplateno;
	}

	public String getEngineid() {
		return engineid;
	}

	public void setEngineid(String engineid) {
		this.engineid = engineid;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getInsurcom() {
		return insurcom;
	}

	public void setInsurcom(String insurcom) {
		this.insurcom = insurcom;
	}

	public String getInsurtypename() {
		return insurtypename;
	}

	public void setInsurtypename(String insurtypename) {
		this.insurtypename = insurtypename;
	}
    
	public String getInsurtype() {
		return insurtype;
	}

	public void setInsurtype(String insurtype) {
		this.insurtype = insurtype;
	}

	public String getInsurnum() {
		return insurnum;
	}

	public void setInsurnum(String insurnum) {
		this.insurnum = insurnum;
	}

	public String getInsurcount() {
		return insurcount;
	}

	public void setInsurcount(String insurcount) {
		this.insurcount = insurcount;
	}

	public String getInsurvalidate() {
		return insurvalidate;
	}

	public void setInsurvalidate(String insurvalidate) {
		this.insurvalidate = insurvalidate;
	}
    
    
}
