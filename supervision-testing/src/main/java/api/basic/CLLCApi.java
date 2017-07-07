package api.basic;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.9	网约车车辆里程信息(CLLC)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class CLLCApi extends BasicApi {
    public CLLCApi() {
        super();
        setCommand("CLLC");
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
