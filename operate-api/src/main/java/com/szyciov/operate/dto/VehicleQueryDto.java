package com.szyciov.operate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.szyciov.enums.DriverEnum;

import java.util.Date;

/**
 * 车辆表
 */
public class VehicleQueryDto {
	/**
	 * 机构用户id
	 */
	public String id;
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	/**
	 * 品牌车系
	 */
	public String vehclineId;
	/**
	 * 从字典表中读取  车牌号码(省)
	 */
	public String plateNoProvince;
	/**
	 * 从字典表中读取（与车牌号省形成多对一关联）  车牌号码(市)
	 */
	public String plateNoCity;
	/**
	 * 车牌号码
	 */
	public String plateNo;
	/**
	 * 车架号
	 */
	public String vin;
	/**
	 * 从字典表中读取 颜色
	 */
	public String color;
	/**
	 * 关联字典表获取数据  所属城市
	 */
	public String city;
	/**
	 * 荷载人数
	 */
	public Integer loads;
	
	/**
	 * 创建时间
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Date createTime;

    private String createTimeStr;
	/**
	 * 更新时间
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Date updateTime;

    private String updateTimeStr;
	/**
	 * 
	 */
	public String creater;
	/**
	 * 
	 */
	public String updater;
	/**
	 * 数据状态
	 */
	public Integer status;
	/**
	 * 组合后的车牌号
	 * */
	public String showPlateNo;
	/**
	 * 品牌车系
	 * */
	public String brandCars;
	/**
	 * 服务车型
	 * */
	public String serviceCars;
	/**
	 * 司机信息
	 * */
	public String driverMessage;
	/**
	 * 当前状态
	 * */
	public String workStatus;
	
	/**
	 * 经营区域
	 * */
	public String  businessScope;
	/**
	 * 
	 * */
	public String setAsDefault;
	
	/**
	 * 经营区域 temp
	 * */
	public String  businessScopeTemp;
	
	/**
	 * 从字典表中读取  车牌号码(省) name
	 */
	public String plateNoProvinceName;
	/**
	 * 从字典表中读取（与车牌号省形成多对一关联）  车牌号码(市) name
	 */
	public String plateNoCityName;
	
	public String cityName;

    /**
     * 对应绑定司机信息
	 */
	private String bindInfoStr;

	/**
	 * 车牌号String
	 */
	private String platenoStr;

	/**
	 * 登记城市str
	 */
	private String cityStr;

	/**
	 * 序号
	 */
	private String rownum;

	/**
	 * 当前司机状态
	 */
	private String driverState;

	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPlateNoProvinceName() {
		return plateNoProvinceName;
	}
	public void setPlateNoProvinceName(String plateNoProvinceName) {
		this.plateNoProvinceName = plateNoProvinceName;
	}
	public String getPlateNoCityName() {
		return plateNoCityName;
	}
	public void setPlateNoCityName(String plateNoCityName) {
		this.plateNoCityName = plateNoCityName;
	}
	public String getBusinessScopeTemp() {
		return businessScopeTemp;
	}
	public void setBusinessScopeTemp(String businessScopeTemp) {
		this.businessScopeTemp = businessScopeTemp;
	}
	public String getSetAsDefault() {
		return setAsDefault;
	}
	public void setSetAsDefault(String setAsDefault) {
		this.setAsDefault = setAsDefault;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getShowPlateNo() {
		return showPlateNo;
	}
	public void setShowPlateNo(String showPlateNo) {
		this.showPlateNo = showPlateNo;
	}
	public String getBrandCars() {
		return brandCars;
	}
	public void setBrandCars(String brandCars) {
		this.brandCars = brandCars;
	}
	public String getServiceCars() {
		return serviceCars;
	}
	public void setServiceCars(String serviceCars) {
		this.serviceCars = serviceCars;
	}
	public String getDriverMessage() {
		return driverMessage;
	}
	public void setDriverMessage(String driverMessage) {
		this.driverMessage = driverMessage;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
	public String getVehclineId() {
		return vehclineId;
	}
	public void setVehclineId(String vehclineId) {
		this.vehclineId = vehclineId;
	}
	public String getPlateNoProvince() {
		return plateNoProvince;
	}
	public void setPlateNoProvince(String plateNoProvince) {
		this.plateNoProvince = plateNoProvince;
	}
	public String getPlateNoCity() {
		return plateNoCity;
	}
	public void setPlateNoCity(String plateNoCity) {
		this.plateNoCity = plateNoCity;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getLoads() {
		return loads;
	}
	public void setLoads(Integer loads) {
		this.loads = loads;
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

	public String getBindInfoStr() {
		return bindInfoStr;
	}

	public void setBindInfoStr(String bindInfoStr) {
		this.bindInfoStr = bindInfoStr;
	}

	public String getPlatenoStr() {
		return platenoStr;
	}

	public void setPlatenoStr(String platenoStr) {
		this.platenoStr = platenoStr;
	}

	public String getCityStr() {
		return cityStr;
	}

	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getDriverState() {
		if(this.driverState!=null) {
			return DriverEnum.getStatusStr(this.driverState);
		}
		return this.driverState;
	}

	public void setDriverState(String driverState) {
		this.driverState = driverState;
	}


    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
