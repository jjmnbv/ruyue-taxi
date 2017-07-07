package api.order;

import lombok.Data;

/**
 * 3.3.7	驾驶员上班*(JSYSB) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class JSYSBApi extends OrderApi {


    public JSYSBApi(){
        super();
        setCommand("JSYSB");

    }

    /**
     * identifier
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
     * 上班时间	YYYYMMDDHHMMSS
     */
    private String onWorkTime;


}
