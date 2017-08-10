package com.szyciov.entity;

import java.util.Date;
import java.util.List;

public class PubPremiumRule {
	private String id; 
	private String citycode;//城市code
	private String rulename;//规则名称
	private Integer cartype;//业务类型 0-网约车 1-出租车
	private Integer ruletype;//规则类型 0-按星期 1-按日期
	private Integer rulestatus;//规则状态 0-禁用 1-启用
	private Date startdt;//有效期起始时间
	private Date enddt;//有效期结束时间
	private Integer isperpetual;//是否永久 0-非永久 1-永久
	private Integer isoperated;//是否操作过 0-未操作过 1-操作过 (操作过即代表存在历史数据)
	private Integer platformtype;//系统类型 0-运管端 1-租赁端
	private String leasescompanyid;//所属租赁公司
	private Date createtime;//创建时间
	private Date updatetime;//修改时间
	private String creater;//创建人
	private String updater;//更新人
	private Integer status;//数据状态
    private List<PubPremiumRuleWeekdetail> weekdetails; //溢价规则星期明细
    private List<PubPremiumRuleDatedetail> datedetails; //溢价规则日期明细

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
	public Integer getCartype() {
		return cartype;
	}
	public void setCartype(Integer cartype) {
		this.cartype = cartype;
	}
	public Integer getRuletype() {
		return ruletype;
	}
	public void setRuletype(Integer ruletype) {
		this.ruletype = ruletype;
	}
	public Integer getRulestatus() {
		return rulestatus;
	}
	public void setRulestatus(Integer rulestatus) {
		this.rulestatus = rulestatus;
	}
	public Date getStartdt() {
		return startdt;
	}
	public void setStartdt(Date startdt) {
		this.startdt = startdt;
	}
	public Date getEnddt() {
		return enddt;
	}
	public void setEnddt(Date enddt) {
		this.enddt = enddt;
	}
	public Integer getIsperpetual() {
		return isperpetual;
	}
	public void setIsperpetual(Integer isperpetual) {
		this.isperpetual = isperpetual;
	}
	public Integer getIsoperated() {
		return isoperated;
	}
	public void setIsoperated(Integer isoperated) {
		this.isoperated = isoperated;
	}
	public Integer getPlatformtype() {
		return platformtype;
	}
	public void setPlatformtype(Integer platformtype) {
		this.platformtype = platformtype;
	}
	public String getLeasescompanyid() {
		return leasescompanyid;
	}
	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

    public List<PubPremiumRuleWeekdetail> getWeekdetails() {
        return weekdetails;
    }

    public void setWeekdetails(List<PubPremiumRuleWeekdetail> weekdetails) {
        this.weekdetails = weekdetails;
    }

    public List<PubPremiumRuleDatedetail> getDatedetails() {
        return datedetails;
    }

    public void setDatedetails(List<PubPremiumRuleDatedetail> datedetails) {
        this.datedetails = datedetails;
    }
}
