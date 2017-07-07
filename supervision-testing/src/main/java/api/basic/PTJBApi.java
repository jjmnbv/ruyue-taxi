package api.basic;

import api.BaseApi;
import lombok.Data;

/**
 * 3.2.1	网约车平台公司基本信息(PTJB)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class PTJBApi extends BasicApi{
    public PTJBApi() {
        super();
        this.setCommand("PTJB");
    }

//    企业名称
    private String companyName;
//  统一社会信用代码
    private String identifier;
//    紧急联系电话
    private String contactPhone;
//    注册地行政区划代码
    private String address;
//    经营范围
    private String businessScope;
//    通信地址
    private String contactAddress;
//      经营业户经济类型
    private String economicType;
//    注册资本
    private String regCapital;
//    法定代表人姓名
    private String legalName;
//  法人代表身份证号
    private String legalId;
//  法人电话
    private String legalPhone;
//   法人代表身份证扫描件文件
    private String legalPhoto;
//  营业执照副本扫描件文件
    private String identifierPhoto;
//    广州通信地址
    private String gzAd;
//    负责人姓名(广州)
    private String responsible;
//    负责人电话(广州)
    private String responsibleWay;
//    母公司名称
    private String parentCompany;
//    母公司通信地址
    private String parentAd;
//    邮政编码（广州）
    private String postCode;
//    服务项目
    private String serviceItem;
//    质量标准和承诺
    private String servicePromise;


}
