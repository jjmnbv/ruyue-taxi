package com.supervision.api.basic;

import com.supervision.enums.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.11	网约车驾驶员培训信息(JSYPX)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class DriverTrainingInfo extends BasicApi {
    public DriverTrainingInfo(){
        super();
        setCommand(CommandEnum.DriverTrainingInfo);
    }
//    注册地行政区划代码
    private String address;
//    机动车驾驶证号
    private String licenseId;
//    courseName
    private String courseName;
//    培训课程日期
    private String courseDate;
//    培训开始时间
    private String startTime;
//    培训结束时间
    private String stopTime;
//    培训时长
    private String duration;
//    培训类型
    private String type;

    @JsonIgnore
    private Integer state;


}
