package com.szyciov.dto.pubOrderCancelRule;

import com.szyciov.param.QueryParam;

public class PubOrderCancelRule extends QueryParam{
	private String id; 
	private String citycode;//城市code
	private String cartype;//业务类型 0-网约车 1-出租车
	private String cancelcount;//免责取消时间(分)
	private String latecount;//免责迟到时间(分)
	private String watingcount;//免责等待时间(分)
	private String price;//取消金额
	private String platformtype;//系统类型(0-运管端 1-租赁端)
	private String leasescompanyid;//租赁公司ID
	private String createtime;//创建时间
	private String updatetime;//更新时间
	private String creater;//创建人
	private String updater;//更新人
	private String status;//数据状态
	private String rulestatus;//规则状态
	private String ifhistory;//是否有历史记录
	private String operationtype;//操作类型(0-启用 1-禁用 2-修改)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getCartype() {
		return cartype;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public String getCancelcount() {
		return cancelcount;
	}
	public void setCancelcount(String cancelcount) {
		this.cancelcount = cancelcount;
	}
	public String getLatecount() {
		return latecount;
	}
	public void setLatecount(String latecount) {
		this.latecount = latecount;
	}
	public String getWatingcount() {
		return watingcount;
	}
	public void setWatingcount(String watingcount) {
		this.watingcount = watingcount;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRulestatus() {
		return rulestatus;
	}
	public void setRulestatus(String rulestatus) {
		this.rulestatus = rulestatus;
	}
	
	public String getIfhistory() {
		return ifhistory;
	}
	public void setIfhistory(String ifhistory) {
		this.ifhistory = ifhistory;
	}
	
	public String getOperationtype() {
		return operationtype;
	}
	public void setOperationtype(String operationtype) {
		this.operationtype = operationtype;
	}
	@Override
	public String toString() {
		return "PubOrderCancelRule [id=" + id + ", citycode=" + citycode + ", cartype=" + cartype + ", cancelcount="
				+ cancelcount + ", latecount=" + latecount + ", watingcount=" + watingcount + ", price=" + price
				+ ", platformtype=" + platformtype + ", leasescompanyid=" + leasescompanyid + ", createtime="
				+ createtime + ", updatetime=" + updatetime + ", creater=" + creater + ", updater=" + updater
				+ ", status=" + status + ", rulestatus=" + rulestatus + "]";
	}
	

}
