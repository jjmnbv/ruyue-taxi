package api.order;

import lombok.Data;

/**
 * 3.3.8	驾驶员下班*(JSYXB) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class JSYXBApi extends OrderApi {
    public JSYXBApi() {
        super();
        setCommand("JSYXB");

    }

    /**
     * 统一社会信用代码
     */
    private String identifier;
    /**
     * 驾驶员姓名
     */
    private String driverName;
    /**
     * 驾驶员身份证号
     */
    private String driverIDCard;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 驾驶员联系电话
     */
    private String driverPhone;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String plateColor;
    /**
     * 上班/签到时间	YYYYMMDDHHMMSS
     */
    private String onWorkTime;
    /**
     *下班/签退时间	YYYYMMDDHHMMSS
     */
    private String offWorkTime;
    /**
     * 总载客次数
     */
    private String driveCount;
    /**
     * 总载客里程
     */
    private String driveMile;
    /**
     * 总载客时长	单位：分钟
     */
    private String driveTime;
    /**
     * 总应收金额	 单位：元
     */
    private String price;
    /**
     * 总实收金额	 单位：元
     */
    private String factPrice;

}
