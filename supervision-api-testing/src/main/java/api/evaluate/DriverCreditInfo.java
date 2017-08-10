package api.evaluate;

import lombok.Data;

/**
 * 3.6.4	驾驶员信誉信息(JSYXY)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class DriverCreditInfo extends EvaluateApi {
    public DriverCreditInfo() {
        super();
        setCommand("JSYXY");
    }

    /**
     * 注册地行政区划编号	驾驶员在平台的注册地，见GB/T 2260
     */
    private String address;
    /**
     * 机动车驾驶证号
     */
    private String licenseId;
    /**
     * 完成订单次数
     */
    private String orderCount;
    /**
     * 处罚次数
     */
    private String punishCount;
    /**
     * 乘客投诉次数
     */
    private String complaintCount;
    /**
     * 服务质量信誉等级	五分制
     */
    private String level;
    /**
     * 服务质量信誉考核日期	YYYYMMDD
     */
    private String testDate;
    /**
     * 服务质量信誉考核机构
     */
    private String testDepartment;
    /**
     * 驾驶员姓名
     */
    private String driverName;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 网络预约出租汽车运输证号
     */
    private String vehicleCertNo;
    /**
     * 服务质量信誉考核结果
     */
    private String testResult;
}
