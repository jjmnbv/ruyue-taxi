package com.szyciov.supervision.api.operation;

import com.szyciov.supervision.enums.CommandEnum;

import lombok.Data;

/**
 * 3.4.1	车辆营运上线(CLYYSX) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
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

}
