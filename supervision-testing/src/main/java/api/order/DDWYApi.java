package api.order;

import lombok.Data;

/**
 * 3.3.6	订单违约*(DDWY) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class DDWYApi extends OrderApi {
    public DDWYApi(){
        super();
        setCommand("DDWY");
    }
    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;


    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private  String driCertNo;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private  String vehicleNo;
    /**
     * 违约方	0:驾驶员
     1:乘客

     */
    private  String breakPart;
//    违约原因
    private  String breakReason;
    /**
     * 乘客电话
     */
    private  String psgTel;
    /**
     * 订单违约时间	YYYYMMDDHHMMSS
     */
    private  String breakTime;



}
