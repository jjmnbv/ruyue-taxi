package com.szyciov.org.entity;

public class OrgInformation {
	public String organId;
	public String creditcardnum;
	public String creditcardname;
	public String bankname;
	
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
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
	@Override
	public String toString() {
		return "OrgInformation [orgId=" + organId + ", creditcardnum=" + creditcardnum + ", creditcardname="
				+ creditcardname + ", bankname=" + bankname + "]";
	}
	
	
}
