package api.basic;

import lombok.Data;

/**
 * 3.2.2	网约车平台公司运营规模信息(PTYYGM)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class PTYYGMApi extends BasicApi {

    public PTYYGMApi(){
        super();
        setCommand("PTYYGM");
    }

//平台注册网络预约出租汽车车辆数
    private Integer vehicleNum;
//平台注册网络预约出租汽车驾驶员数量
    private Integer driverNum;

}
