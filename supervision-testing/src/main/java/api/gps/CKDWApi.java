package api.gps;

/**
 * 3.5.2	乘客定位信息（来自乘客手机app）*(CKDW) 实时
 * Created by 林志伟 on 2017/7/7.
 */
public class CKDWApi extends GpsApi {
    public CKDWApi() {
        super();
        setCommand("CKDW");
    }

    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 驾驶员姓名
     */
    private String driverName;
    /**
     * 驾驶员联系电话
     */
    private String driverPhone;
    /**
     * 驾驶员身份证号
     */
    private String driverIDCard;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 乘客电话
     */
    private String passengerPhone;
    /**
     * 车牌号码
     */
    private String vehicleNo;
    /**
     * 车牌颜色
     */
    private String plateColor;
    /**
     * 车辆厂牌
     */
    private String brand;
    /**
     * 坐标加密标识
     */
    private String encrypt;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 瞬时速度
     */
    private String speed;
    /**
     * 报警状态
     */
    private String warnStatus;
    /**
     * 定位时间	YYYYMMDDHHMMSS
     */
    private String positionTime;
    /**
     * 有效性	1:有效
     0:无效

     */
    private Integer validity;
}
