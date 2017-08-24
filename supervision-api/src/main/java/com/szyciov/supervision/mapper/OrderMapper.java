package com.szyciov.supervision.mapper;

import com.szyciov.supervision.api.dto.order.DriverOffWork;
import com.szyciov.supervision.api.dto.order.DriverOnWork;
import com.szyciov.supervision.api.dto.order.OrderBreach;
import com.szyciov.supervision.api.dto.order.OrderCancel;
import com.szyciov.supervision.api.dto.order.OrderInitiation;
import com.szyciov.supervision.api.dto.order.OrderSuccess;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by lzw on 2017/8/18.
 */
@Mapper
public interface OrderMapper {
     /**
      * 驾驶员上班
      * @param driverId
      * @return
      */
     DriverOnWork driverOnWork(String driverId);

     /**
      * 驾驶员下班
      * @param driverId
      * @return
      */
     DriverOffWork driverOffWork(String driverId);

     /**
      * 机构订单发起
      * @param map
      * @return
      */
     OrderInitiation orgOrderInitiation(Map<String,String> map);

     /**
      * 运营订单发起
      * @param map
      * @return
      */
     OrderInitiation opOrderInitiation(Map<String,String> map);

     /**
      * 出租车订单发起
      * @param map
      * @return
      */
     OrderInitiation taxiOrderInitiation(Map<String,String> map);

     /**
      * 订单成功
      * @param map
      * @return
      */
     OrderSuccess orderSuccess(Map<String,String> map);

     /**
      * 订单取消
      * @param map
      * @return
      */
     OrderCancel orderCancel(Map<String,String> map);

     /**
      * 订单违约
      * @param map
      * @return
      */
     OrderBreach orderBreak(Map<String,String> map);

}
