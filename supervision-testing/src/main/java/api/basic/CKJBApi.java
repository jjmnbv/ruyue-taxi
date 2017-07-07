package api.basic;

import lombok.Data;

/**
 * 3.2.14	网约车乘客基本信息(CKJB)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class CKJBApi extends BasicApi {
    public CKJBApi(){
        super();
        setCommand("CKJB");
    }
//    注册时间
    private String registerDate;
//    乘客电话
    private String passengerPhone;
//    乘客称谓
    private String passengerName;
//    乘客性别
    private String passengerSex;



}
