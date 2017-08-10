package com.szyciov.op.entity;

import java.util.Date;

public class OpUser {
	
	private String id;
	
	/**
	 * 部门id
	 */
	private String deptid;
	
	/**
	 * 用户类型
	 */
	private String usertype;
	
	/**
	 * 帐号
	 */
	private String account;
	
	/**
	 * 电话
	 */
	private String telphone;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 昵称
	 */
	private String nickname;
	
	/**
	 * 用户密码
	 */
	private String userpassword;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 特殊状态
	 */
	private String specialstate;
	
	/**
	 * 头像(小)
	 */
	private String headportraitmin;
	
	/**
	 * 头像(大)
	 */
	private String headportraitmax;
	
	/**
	 * 注册时间
	 */
	private Date registertime;
	
	/**
	 * 禁用状态
	 */
	private String disablestate;
	
	/**
	 * 禁用历史
	 */
	private String disablehis;
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	/**
	 * 更新时间
	 */
	private Date updatetime;
	
	/**
	 * 数据状态
	 */
	private Integer status;
	
	/**
	 * 角色类型
	 */
	private String roletype;
	
	/**
	 * 角色类型名称
	 */
	private String roletypecaption;
	
	/**
	 * 角色名称
	 */
	private String rolename;

	/**
	 * 角色id
	 */
	private String roleid;
	
	/**
	 * 登陆错误次数
	 */
	private int logontimes;
	
	/**
	 * 运管的id
	 */
	private String operateid;
	
	/**
	 * 密码过期时间
	 */
	private Date expirestime;
	
	public String getOperateid() {
		return operateid;
	}

	public void setOperateid(String operateid) {
		this.operateid = operateid;
	}

	public int getLogontimes() {
		return logontimes;
	}

	public void setLogontimes(int logontimes) {
		this.logontimes = logontimes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSpecialstate() {
		return specialstate;
	}

	public void setSpecialstate(String specialstate) {
		this.specialstate = specialstate;
	}

	public String getHeadportraitmin() {
		return headportraitmin;
	}

	public void setHeadportraitmin(String headportraitmin) {
		this.headportraitmin = headportraitmin;
	}

	public String getHeadportraitmax() {
		return headportraitmax;
	}

	public void setHeadportraitmax(String headportraitmax) {
		this.headportraitmax = headportraitmax;
	}

	public Date getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	public String getDisablestate() {
		return disablestate;
	}

	public void setDisablestate(String disablestate) {
		this.disablestate = disablestate;
	}

	public String getDisablehis() {
		return disablehis;
	}

	public void setDisablehis(String disablehis) {
		this.disablehis = disablehis;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRoletypecaption() {
		return roletypecaption;
	}

	public void setRoletypecaption(String roletypecaption) {
		this.roletypecaption = roletypecaption;
	}

	public Date getExpirestime() {
		return expirestime;
	}

	public void setExpirestime(Date expirestime) {
		this.expirestime = expirestime;
	}
}
