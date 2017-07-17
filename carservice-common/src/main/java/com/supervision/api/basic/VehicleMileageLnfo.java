package com.supervision.api.basic;

import com.supervision.enums.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.9	网约车车辆里程信息(CLLC)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class VehicleMileageLnfo extends BasicApi {
    public VehicleMileageLnfo() {
        super();
        setCommand(CommandEnum.VehicleMileageLnfo);
    }
//    注册地行政区划编号
    private String address;
//    车牌号码
    private String vehicleNo;
//    车牌颜色
    private String plateColor;
//  车辆行驶总里程
    private String totalMile;
    @JsonIgnore
    private Integer state;


}
