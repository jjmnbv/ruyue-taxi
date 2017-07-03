package com.szyciov.op.entity;

import java.util.Date;
import java.util.List;

import com.szyciov.lease.entity.PubVehcline;

/**
 * 运营端车型信息表
 */
public class OpVehiclemodels {
	
	private String Id;
	
	/**
	 * 所属租赁公司
	 */
	private String leasescompanyid;
	
	/**
	 * 车型LOGO
	 */
	private String logo;
	
	/**
	 * 车型名称
	 */
	private String name;
	
	/**
	 * 车型级别
	 */
	private Integer level;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	/**
	 * 更新时间
	 */
	private Date updatetime;
	
	/**
	 * 禁用状态
	 */
	private String modelstatus;
	
	/**
	 * 创建人
	 */
	private String creater;
	
	/**
	 * 更新人
	 */
	private String updater;
	
	/**
	 * 数据状态
	 */
	private Integer status;
	
	/**
	 * 车系
	 */
	private String brandCars;

	
	/*************************************************附加字段********************************************************/
	/**
	 * 起步价
	 */
	private double startprice;
	/**
	 * 里程价
	 */
	private double rangeprice;
	/**
	 * 时长价
	 */
	private double timeprice;
	
	private List<PubVehcline> vehclines;
	
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		this.Id = id;
	}

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getBrandCars() {
		return brandCars;
	}

	public void setBrandCars(String brandCars) {
		this.brandCars = brandCars;
	}

	/**  
	 * 获取  
	 * @return startprice   
	 */
	public double getStartprice() {
		return startprice;
	}
	

	/**  
	 * 设置  
	 * @param startprice   
	 */
	public void setStartprice(double startprice) {
		this.startprice = startprice;
	}
	

	/**  
	 * 获取里程价  
	 * @return rangeprice 里程价  
	 */
	public double getRangeprice() {
		return rangeprice;
	}
	

	/**  
	 * 设置里程价  
	 * @param rangeprice 里程价  
	 */
	public void setRangeprice(double rangeprice) {
		this.rangeprice = rangeprice;
	}
	

	/**  
	 * 获取时长价  
	 * @return timeprice 时长价  
	 */
	public double getTimeprice() {
		return timeprice;
	}
	

	/**  
	 * 设置时长价  
	 * @param timeprice 时长价  
	 */
	public void setTimeprice(double timeprice) {
		this.timeprice = timeprice;
	}

	public List<PubVehcline> getVehclines() {
		return vehclines;
	}

	public void setVehclines(List<PubVehcline> vehclines) {
		this.vehclines = vehclines;
	}

	public String getModelstatus() {
		return modelstatus;
	}

	public void setModelstatus(String modelstatus) {
		this.modelstatus = modelstatus;
	}
	
}
