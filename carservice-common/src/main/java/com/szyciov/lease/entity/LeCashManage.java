package com.szyciov.lease.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LeCashManage {
	
	 private String id;         
	 private String platformtype;
	 private String leasescompanyid;
	 private String usertype;
	 private String userid;
	 private double amount;
	 private String uuid;
	 private String creditcardnum;
	 private String creditcardname;
	 private String bankname;
	 
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	 private Date applytime;
	 private String processstatus;
	 private String processresult;
	 private String processreason;
	 
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	 private Date processtime;
	 private Date createtime;
	 private Date updatetime;
	 private String processusername;
	 private int status;
	 
	 private String account;
	 
	 private String nickname;
	 
	 private int rownum;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlatformtype() {
		return platformtype;
	}
	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}
	public String getLeasescompanyid() {
		return leasescompanyid;
	}
	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCreditcardnum() {
		return creditcardnum;
	}
	public void setCreditcardnum(String creditcardnum) {
		this.creditcardnum = creditcardnum;
	}
	public String getCreditcardname() {
		return creditcardname;
	}
	public void setCreditcardname(String creditcardname) {
		this.creditcardname = creditcardname;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public Date getApplytime() {
		return applytime;
	}
	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}
	public String getProcessstatus() {
		return processstatus;
	}
	public void setProcessstatus(String processstatus) {
		this.processstatus = processstatus;
	}
	public String getProcessresult() {
		return processresult;
	}
	public void setProcessresult(String processresult) {
		this.processresult = processresult;
	}
	public String getProcessreason() {
		return processreason;
	}
	public void setProcessreason(String processreason) {
		this.processreason = processreason;
	}
	public Date getProcesstime() {
		return processtime;
	}
	public void setProcesstime(Date processtime) {
		this.processtime = processtime;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProcessusername() {
		return processusername;
	}
	public void setProcessusername(String processusername) {
		this.processusername = processusername;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
}
