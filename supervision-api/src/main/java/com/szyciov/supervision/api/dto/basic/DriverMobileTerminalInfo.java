package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;

/**
 * 3.2.12	网约车驾驶员移动终端信息(JSYYDZD)
 * Created by 林志伟 on 2017/7/7.
 */

public class DriverMobileTerminalInfo extends BasicApi {
    public DriverMobileTerminalInfo(){
        super();
        setCommand(CommandEnum.DriverMobileTerminalInfo);
    }
    //    注册地行政区划代码
    private String address;
//    机动车驾驶证号
    private String licenseId;
//    驾驶员联系电话
    private String driverPhone;
//    司机手机运营商
    private String netType;
//    司机使用APP版本号
    private String appVersion;
//    使用地图类型
    private String mapType;
//     司机手机型号
    private String mobileModel;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public String getMobileModel() {
        return mobileModel;
    }

    public void setMobileModel(String mobileModel) {
        this.mobileModel = mobileModel;
    }

    @Override
    public String toString() {
        return "DriverMobileTerminalInfo{" +
                "address='" + address + '\'' +
                ", licenseId='" + licenseId + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", netType='" + netType + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", mapType='" + mapType + '\'' +
                ", mobileModel='" + mobileModel + '\'' +
                "} " + super.toString();
    }
}
