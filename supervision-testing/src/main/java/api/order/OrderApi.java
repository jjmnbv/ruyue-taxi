package api.order;

import api.BaseApi;
import lombok.Data;

/**
 * 订单数据
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OrderApi extends BaseApi {
    public OrderApi(){
        setApiType("ORDER");
    }

    //    行政区划编号
    private String address;


}
