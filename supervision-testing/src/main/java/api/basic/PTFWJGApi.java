package api.basic;

import lombok.Data;

/**
 * 3.2.4	网约车平台公司服务机构(PTFWJG)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class PTFWJGApi extends BasicApi {
    public PTFWJGApi(){
        super();
        this.setCommand("PTFWJG");
    }

//    服务机构名称
    private String serviceName;
//    服务机构代码
    private String serviceNo;
//    服务机构具体地址
    private String detailAddress;
//  服务机构负责人姓名
    private String responsibleName;
//  服务机构管理人姓名
    private String managerName;
//    服务机构管理人联系方式
    private String managerPhone;
//    服务机构紧急联系电话
    private String contactPhone;
//    行政文书送达邮寄地址
    private String mailAddress;
//服务机构设立日期
    private String createDate;



}
