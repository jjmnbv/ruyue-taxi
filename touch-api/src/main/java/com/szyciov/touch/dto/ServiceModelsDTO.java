package com.szyciov.touch.dto;

/**
 * 服务车型实体
 * @author xuxxtr
 *
 */
public class ServiceModelsDTO {

	/**
	 * 车型ID
	 */
	private String modelsId;
	
	/**
	 * 车型名称
	 */
	private String modelsName;
	
	/**
	 * 车型图片
	 */
	private String modelsLogo;

	
	
	public ServiceModelsDTO() {
		super();
	}

	public String getModelsId() {
		return modelsId;
	}

	public void setModelsId(String modelsId) {
		this.modelsId = modelsId;
	}

	public String getModelsName() {
		return modelsName;
	}

	public void setModelsName(String modelsName) {
		this.modelsName = modelsName;
	}

	public String getModelsLogo() {
		return modelsLogo;
	}

	public void setModelsLogo(String modelsLogo) {
		this.modelsLogo = modelsLogo;
	}

	@Override
	public String toString() {
		return "ServiceModelsDTO [modelsId=" + modelsId + ", modelsName=" + modelsName + ", modelsLogo=" + modelsLogo
				+ "]";
	}

}
