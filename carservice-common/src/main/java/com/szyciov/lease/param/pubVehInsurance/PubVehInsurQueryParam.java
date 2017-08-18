package com.szyciov.lease.param.pubVehInsurance;

import com.szyciov.param.QueryParam;

/**
 * 车辆保险信息查询
 */
public class PubVehInsurQueryParam extends QueryParam {
	/**
	 * 营运类型
	 */
	private String queryVehicleType;
	
	/**
	 * 保险类型
	 */
	private String queryinsurType;
    
    /**
     * 车牌号
     */
	private String queryPlateNo;
	
	/**
	 * 车辆识别码
	 */
	private String queryVin;
	
	/**
	 * 保险号
	 */
	private String queryInsurNum;
	
	/**
	 * 保险公司
	 */
	private String queryInsurCom;

	
	public String getQueryVehicleType() {
		return queryVehicleType;
	}

	public void setQueryVehicleType(String queryVehicleType) {
		this.queryVehicleType = queryVehicleType;
	}

	public String getQueryinsurType() {
		return queryinsurType;
	}

	public void setQueryinsurType(String queryinsurType) {
		this.queryinsurType = queryinsurType;
	}

	public String getQueryPlateNo() {
		return queryPlateNo;
	}

	public void setQueryPlateNo(String queryPlateNo) {
		this.queryPlateNo = queryPlateNo;
	}

	public String getQueryVin() {
		return queryVin;
	}

	public void setQueryVin(String queryVin) {
		this.queryVin = queryVin;
	}

	public String getQueryInsurNum() {
		return queryInsurNum;
	}

	public void setQueryInsurNum(String queryInsurNum) {
		this.queryInsurNum = queryInsurNum;
	}

	public String getQueryInsurCom() {
		return queryInsurCom;
	}

	public void setQueryInsurCom(String queryInsurCom) {
		this.queryInsurCom = queryInsurCom;
	}

	@Override
	public String toString() {
		return "PubVehInsurQueryParam [queryVehicleType=" + queryVehicleType + ", queryinsurType=" + queryinsurType
				+ ", queryPlateNo=" + queryPlateNo + ", queryVin=" + queryVin + ", queryInsurNum=" + queryInsurNum
				+ ", queryInsurCom=" + queryInsurCom + "]";
	}
	
	
}
