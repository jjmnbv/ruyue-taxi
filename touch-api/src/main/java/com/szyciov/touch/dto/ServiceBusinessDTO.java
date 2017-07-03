package com.szyciov.touch.dto;

/**
 * 服务业务实体
 * @author xuxxtr
 *
 */
public class ServiceBusinessDTO {

	/**
	 * 业务ID
	 */
	private String businessId;
	
	/**
	 * 业务名称
	 */
	private String businessName;
	
	/**
	 * 业务图标
	 */
	private String businessLogo;

	public ServiceBusinessDTO() {
		super();
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessLogo() {
		return businessLogo;
	}

	public void setBusinessLogo(String businessLogo) {
		this.businessLogo = businessLogo;
	}

	@Override
	public String toString() {
		return "ServiceBusinessDTO [businessId=" + businessId + ", businessName=" + businessName + ", businessLogo="
				+ businessLogo + "]";
	}

}
