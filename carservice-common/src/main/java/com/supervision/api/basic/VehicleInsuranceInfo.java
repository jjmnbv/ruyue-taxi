package com.supervision.api.basic;

import com.supervision.enums.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.8	网约车车辆保险信息(CLBX)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class VehicleInsuranceInfo extends BasicApi {
    public VehicleInsuranceInfo() {
        super();
        setCommand(CommandEnum.VehicleInsuranceInfo);
    }
//    车辆号牌
    private String vehicleNo;
//    车牌颜色
    private String plateColor;
//    保险公司名称
    private String insurCom;
//    保险号
    private String insurNum;
//      保险类型
    private String insurType;
//    保险金额
    private String insurCount;
//    保险生效时间
    private String insurEff;
//    保险到期时间
    private String insurExp;

    @JsonIgnore
    private Integer state;

}
