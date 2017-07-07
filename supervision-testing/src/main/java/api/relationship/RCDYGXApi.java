package api.relationship;

import api.evaluate.EvaluateApi;
import lombok.Data;

/**
 * 3.7	人车对应关系信息数据*(RCDYGX)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class RCDYGXApi extends RelationshipApi {
    public RCDYGXApi() {
        super();
        setCommand("RCDYGX");
    }

    /**
     * 注册地行政区划编号	见GB/T 2260
     */
    private String address;
    /**
     * curDriverName
     */
    private String curDriverName;
    /**
     * 当前驾驶员的网络预约出租汽车驾驶员证编号
     */
    private String curDriverCertNo;
    /**
     * 当前车辆车牌号码
     */
    private String curVehicleNo;
    /**
     * 当前车辆的网络预约出租汽车运输证号
     */
    private String curVehicleCertNo;
    /**
     * 当前实际人车对应开始时间
     */
    private String curTimeOn;
    /**
     * 当前实际人车对应结束时间	YYYYMMDDHHMMSS
     */
    private String curTimeOff;
    /**
     * 平台报备驾驶员	驾驶员姓名+身份（主班、替班）
     */
    private String reportDrivers;
    /**
     * 平台报备驾驶员的网络预约出租汽车驾驶员证编号
     */
    private String reportDriverCertNo;
    /**
     * 平台报备车辆车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String reportVehicleNo;
    /**
     * 平台报备车辆的网络预约出租汽车运输证号
     */
    private String reportVehicleCertNo;
    /**
     * 平台报备开始时间	YYYYMMDDHHMMSS
     */
    private String timeFrom;
    /**
     * 平台报备结束时间	YYYYMMDDHHMMSS
     */
    private String timeTo;

}
