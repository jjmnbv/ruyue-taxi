package com.szyciov.dto.pubPremiumRule;

import java.util.Date;

import com.szyciov.param.QueryParam;

public class PubPremiumParam extends QueryParam{
	private String id; 
	private String citycode;//城市code
	private String rulename;//规则名称
	private String cartype;//业务类型 0-网约车 1-出租车
	private String ruletype;//规则类型 0-按星期 1-按日期
	private String rulestatus;//规则状态 0-禁用 1-启用
	private String startdt;//有效期起始时间
	private String enddt;//有效期结束时间
	private String isperpetual;//是否永久 0-非永久 1-永久
	private String isoperated;//是否操作过 0-未操作过 1-操作过 (操作过即代表存在历史数据)
	private String platformtype;//系统类型 0-运管端 1-租赁端
	private String leasescompanyid;//所属租赁公司
	private String createtime;//系统类型
	private String updatetime;//系统类型
	private String creater;//系统类型
	private String updater;//更新人
	private String status;//数据状态
	private Date startTime;//开始时间
	private Date endTime;//结束时间
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
	public String getRulename() {
		return rulename;
	}
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	public String getCartype() {
		return cartype;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public String getRuletype() {
		return ruletype;
	}
	public void setRuletype(String ruletype) {
		this.ruletype = ruletype;
	}
	public String getRulestatus() {
		return rulestatus;
	}
	public void setRulestatus(String rulestatus) {
		this.rulestatus = rulestatus;
	}
	public String getStartdt() {
		return startdt;
	}
	public void setStartdt(String startdt) {
		this.startdt = startdt;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getIsperpetual() {
		return isperpetual;
	}
	public void setIsperpetual(String isperpetual) {
		this.isperpetual = isperpetual;
	}
	public String getIsoperated() {
		return isoperated;
	}
	public void setIsoperated(String isoperated) {
		this.isoperated = isoperated;
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
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "PubPremiumParam [id=" + id + ", citycode=" + citycode + ", rulename=" + rulename + ", cartype="
				+ cartype + ", ruletype=" + ruletype + ", rulestatus=" + rulestatus + ", startdt=" + startdt
				+ ", enddt=" + enddt + ", isperpetual=" + isperpetual + ", isoperated=" + isoperated + ", platformtype="
				+ platformtype + ", leasescompanyid=" + leasescompanyid + ", createtime=" + createtime + ", updatetime="
				+ updatetime + ", creater=" + creater + ", updater=" + updater + ", status=" + status + "]";
	}
	
}
