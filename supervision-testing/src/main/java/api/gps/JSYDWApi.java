package api.gps;

import lombok.Data;

/**
 * 3.5.1	驾驶员定位信息（来自司机手机app）(JSYDW) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class JSYDWApi extends GpsApi {
    public JSYDWApi() {
        super();
        setCommand("JSYDW");
    }
    /**
     * 行政区划代码
     */
    private String driverRegionCode;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String plateColor;
    /**
     * 车辆注册地行政区划编号（地市级）	见GB/T2260
     */
    private String vehicleRegionCode;
    /**
     * 定位时间	YYYYMMDDHHMMSS
     */
    private String positionTime;
    /**
     * 经度	单位：1*10-6度


     */
    private String longitude;
    /**
     *    纬度	单位：1*10-6度
     */
    private String latitude;
    /**
     * 瞬时速度	单位：km/h
     */
    private String speed;
    /**
     * 方向角	0-359，顺时针方向
     */
    private String direction;
    /**
     * 海拔高度	单位：米
     */
    private String elevation;
    /**
     * 里程	单位：km
     */
    private String mileage;
    /**
     * 坐标加密标识	1：GCJ-02 测绘局标准
     2：WGS84 GPS标准
     3：BD-09 百度标准
     4：CGCS2000 北斗标准
     0：其他

     */
    private String encrypt;
    /**
     * 报警状态	参考JT／T808
     */
    private String warnStatus;
    /**
     * 车辆状态
     */
    private String vehStatus;
    /**
     * 营运状态	1：载客
     2：接单
     3：空驶
     4：停运

     */
    private String bizStatus;
    /**
     * 营运状态	1：载客
     2：接单
     3：空驶
     4：停运
     */
    private String orderId;
    /**
     * 驾驶员姓名
     */
    private String driverName;
    /**
     * 驾驶员标识号
     */
    private String driverId;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 车辆类型
     */
    private String vehicleType;
    /**
     * SJSB:司机上班、SJXB:司机下班、CKSC:乘客上车、CKXC:乘客下车、AUTO:定时上传、DDPD:派单
     */
    private String positionType;
    /**
     * 1:有效
     0:无效

     */
    private Integer validity;

}
