package com.szyciov.touch.dto;

/**
 * 附近车辆实体
 * @author xuxxtr
 *
 */
public class AroundVehicleDTO {

	/**
	 * 车辆经度
	 */
	private Double longitude;
	
	/**
	 * 车辆纬度
	 */
	private Double latitude;

	
	
	public AroundVehicleDTO() {
		super();
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "AroundVehicleDTO [longitude=" + longitude + ", latitude=" + latitude + "]";
	}

}
