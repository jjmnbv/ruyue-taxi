package com.supervision.api.basic;

import com.supervision.enums.CommandEnum;
import com.supervision.enums.InterfaceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.13	网约车驾驶员统计信息(JSYTJ)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class DriverStatistics extends BasicApi {
    public DriverStatistics(){
        super();
        setCommand(CommandEnum.DriverStatistics);
    }
//    注册地行政区划代码
    private String address;
//    机动车驾驶证号
    private String licenseId;
//    统计周期
    private String cycle;
//    完成订单次数
    private Integer orderCount;
//    交通违章次数
    private Integer trafficViolationCount;

//
    private Integer trafficAccidentCount;

//    乘客被投诉次数
    private String complainedCount;
//    接单违约次数
    private String violateRecord;

    @JsonIgnore
    private Integer state;

}
