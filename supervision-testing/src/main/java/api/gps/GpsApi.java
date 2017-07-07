package api.gps;

import api.BaseApi;
import lombok.Data;

/**
 * GPS数据
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class GpsApi extends BaseApi {
    public GpsApi() {
        super();
        setApiType("GPS");
    }

    /**
     * 当前位置行政区划编号
     */
    private String address;

}
