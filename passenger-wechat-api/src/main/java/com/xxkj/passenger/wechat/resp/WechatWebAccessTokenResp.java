package com.xxkj.passenger.wechat.resp;

import lombok.Data;

@Data
public class WechatWebAccessTokenResp {
	
	private Integer errcode;
	private String errmsg;
	
	private String access_token;
	private Integer expires_in;
	private String refresh_token;
	private String openid;
	private String scope;

}
