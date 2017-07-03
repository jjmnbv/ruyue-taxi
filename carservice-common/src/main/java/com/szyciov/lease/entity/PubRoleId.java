package com.szyciov.lease.entity;

public class PubRoleId {
	public String userId;
	public String roleId;
	public String orderno;
	public int keyAll;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public int getKeyAll() {
		return keyAll;
	}
	public void setKeyAll(int keyAll) {
		this.keyAll = keyAll;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "PubRoleId [userId=" + userId + ", roleId=" + roleId + ", orderno=" + orderno + ", keyAll=" + keyAll
				+ "]";
	}
}
