package api.basic;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.18	个体驾驶员合同信息*(GTJSYHT)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class GTJSYHTApi extends BasicApi {
    public GTJSYHTApi(){
        super();
        setCommand("GTJSYHT");
    }
//    行政区划编号
    private String address;
//    驾驶员姓名
    private String driverName;
//    电话
    private String driTel;
//    性别
    private String gender;
//    birthday
    private String birthday;
//    民族
    private String driverNation;
//    身份证类别
    private String idType;
//    证件号码
    private String idNo;
//    网络预约出租汽车驾驶员证编号
    private String driCertNo;
//    车牌号码
    private String vehicleNo;
//    车辆识别VIN码
    private String vin;
//    网络预约出租汽车运输证号
    private String vehCertNo;
//    类型
    private String type;
//    上传扫描件类型
    private String contractType;
//    上传扫描件
    private String contractPhoto;
//    合同状态
    private String contractStatus;
//    合同签订时间
    private String signTime;
//    合同生效时间
    private String validTime;
//    合同失效时间
    private String invalidTime;

    @JsonIgnore
    private Integer state;

}
