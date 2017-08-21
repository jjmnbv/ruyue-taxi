package com.szyciov.supervision.api.dto.operation;

import com.supervision.enums.CommandEnum;

/**
 * 3.4.1	车辆营运上线(CLYYSX) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class VehicleOperationOnline extends OperationApi {
    public VehicleOperationOnline(){
        super();
        setCommand(CommandEnum.VehicleOperationOnline);
    }

    /**
     * 机动车驾驶证号
     */
    private String licenseId;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String plateColor;
    /**
     * 上线时间	YYYYMMDDHHMMSS
     */
    private String loginTime;
    /**
     * 上线经度	单位：1*10-6度
     */
    private String longitude;
    /**
     * 上线经度	单位：1*10-6度
     */
    private String latitude;
    /**
     *
     * 坐标加密标识	1：GCJ-02 测绘局标准
     2：WGS84 GPS标准
     3：BD-09 百度标准
     4：CGCS2000 北斗标准
     0：其他

     */
    private String encrypt;
    /**
     * 行政区划编号	见GB/T 2260
     */
    private String address;

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "VehicleOperationOnline{" +
                "licenseId='" + licenseId + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", address='" + address + '\'' +
                "} " + super.toString();
    }
}
