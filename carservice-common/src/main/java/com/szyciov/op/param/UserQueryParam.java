package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class UserQueryParam extends QueryParam {

	/**
	 * 昵称姓名
	 */
	private String nickname;
	
	/**
	 * 手机号
	 */
	private String telphone;
	
	/**
	 * 账号禁用状态
	 */
	private String disablestate;
	
	/**
	 * 账号角色类型
	 */
	private String roletype;
	
	/**
	 * 当前登录者的userid
	 */
	private String cuserid;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getDisablestate() {
		return disablestate;
	}

	public void setDisablestate(String disablestate) {
		this.disablestate = disablestate;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public String getCuserid() {
		return cuserid;
	}

	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
}
