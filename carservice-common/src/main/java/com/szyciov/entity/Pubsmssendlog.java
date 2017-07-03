package com.szyciov.entity;

public class Pubsmssendlog {
	 private String id;
	 private String phone;
	 private String content;
	 private String sendstate;
	 private String sendtime;
	 private String createtime;
	 private String updatetime;
	 private String receiptstate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendstate() {
		return sendstate;
	}
	public void setSendstate(String sendstate) {
		this.sendstate = sendstate;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
	public String getReceiptstate() {
		return receiptstate;
	}
	public void setReceiptstate(String receiptstate) {
		this.receiptstate = receiptstate;
	}
	@Override
	public String toString() {
		return "Pubsmssendlog [id=" + id + ", phone=" + phone + ", content=" + content + ", sendstate=" + sendstate
				+ ", sendtime=" + sendtime + ", createtime=" + createtime + ", updatetime=" + updatetime
				+ "]";
	}
}
