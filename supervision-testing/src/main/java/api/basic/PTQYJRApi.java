package api.basic;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.19	普通企业接入信息*(PTQYJR)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class PTQYJRApi extends BasicApi {
    public PTQYJRApi(){
        super();
        setCommand("PTQYJR");;
    }
//    行政区划编号
    private String address;
//    企业名称
    private String epName;
//    统一社会信用代码
    private String epCode;
//    epStatus
    private String epStatus;
//    注册时间
    private String accessTime;

    @JsonIgnore
    private Integer state;

}
