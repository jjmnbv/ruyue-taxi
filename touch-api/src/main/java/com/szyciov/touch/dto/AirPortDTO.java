package com.szyciov.touch.dto;

/**
 * 机场信息实体
 * @author xuxxtr
 *
 */
public class AirPortDTO {

	/**
	 * 机场ID
	 */
	private String airportId;
	
	/**
	 * 机场名称
	 */
	private String airportName;
	
	/**
	 * 机场经度
	 */
	private Double longitude;
	
	/**
	 * 机场纬度
	 */
	private Double latitude;

	public AirPortDTO() {
		super();
	}

	public String getAirportId() {
		return airportId;
	}

	public void setAirportId(String airportId) {
		this.airportId = airportId;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
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
		return "AirPortDTO [airportId=" + airportId + ", airportName=" + airportName + ", longitude=" + longitude
				+ ", latitude=" + latitude + "]";
	}

}
