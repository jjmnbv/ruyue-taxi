package api.basic;

import lombok.Data;

/**
 * 3.2.5	网约车平台公司经营许可(CPTJYXK)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class CompanyOperatingPermit extends  BasicApi {
    public CompanyOperatingPermit(){
        super();
        this.setCommand("CPTJYXK");
    }
//    许可地行政区划代码
    private String address;
//    网络预约出租汽车经营许可证号
    private String certificate;
//    经营区域
    private String operationArea;
//    公司名称
    private String ownerName;
//    经营许可证发证机构
    private String organization;
//    经营许可证有效期起
    private String startDate;
//    经营许可证有效期止
    private String stopDate;
//  经营许可证初次发证日期
    private String certifyDate;

}
