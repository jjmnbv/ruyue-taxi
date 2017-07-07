package api.basic;

import lombok.Data;

/**
 * 3.2.12	网约车驾驶员移动终端信息(JSYYDZD)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class JSYYDZDApi extends BasicApi {
    public JSYYDZDApi(){
        super();
        setCommand("JSYYDZD");
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


}
