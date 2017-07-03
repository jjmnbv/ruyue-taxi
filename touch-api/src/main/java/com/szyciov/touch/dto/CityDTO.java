package com.szyciov.touch.dto;

/**
 * 城市信息实体
 * @author xuxxtr
 *
 */
public class CityDTO {

	/**
	 * 城市ID
	 */
	private String cityId;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 城市编码
	 */
	private String cityCode;

	public CityDTO() {
		super();
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Override
	public String toString() {
		return "CityDTO [cityId=" + cityId + ", cityName=" + cityName + ", cityCode=" + cityCode + "]";
	}

}
