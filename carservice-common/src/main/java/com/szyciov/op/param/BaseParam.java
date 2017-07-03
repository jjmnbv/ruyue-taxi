package com.szyciov.op.param;

public class BaseParam {

	/**
	 * 授权码 (接口必须)
	 */
	private String apikey;
	
	/** 授权Id(不需要传) */
	private String authorizationId;

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

    
    /**
     * @return 返回 authorizationId
     */
    
    public String getAuthorizationId() {
        return authorizationId;
    }

    
    /**
    * @param 对authorizationId进行赋值
    */
    
    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    
	
	

}
