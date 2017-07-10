package com.supervision.api.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 3.2.2	网约车平台公司运营规模信息(PTYYGM)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyOperateInfo extends BasicApi {

//平台注册网络预约出租汽车车辆数
    private Integer vehicleNum;
//平台注册网络预约出租汽车驾驶员数量
    private Integer driverNum;

}
