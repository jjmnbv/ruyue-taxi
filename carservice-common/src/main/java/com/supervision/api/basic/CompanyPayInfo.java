package com.supervision.api.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 3.2.3网约车平台公司支付信息(PTZF)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPayInfo extends BasicApi {


//    银行或者非银行支付机构名称
    private String payName;
//    非银行支付机构支付业务许可证编号
    private String payId;
//  支付业务类型
    private String payType;
//  业务覆盖范围
    private String payScope;
//    备付金存管银行
    private String prepareBank;
//    与银行、非银行支付机构的结算周期
    private Integer countDate;


//


}
