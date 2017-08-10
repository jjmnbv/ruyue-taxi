package com.szyciov.coupon.mapper;

import java.util.List;

import com.szyciov.coupon.dto.OrderInfoDTO;
import com.szyciov.coupon.param.OrderQueryParam;

/**
 * 订单mapper
 * @author LC
 * @date 2017/7/27
 */
public interface OrderMapper {

    Integer getOrderCount(OrderQueryParam param);

    List<Double> listOrderMoney(OrderQueryParam param);

    List<OrderInfoDTO> listOrderByEndTime(OrderQueryParam param);

    List<OrderInfoDTO> listOrderByComplTime(OrderQueryParam param);


    List<OrderInfoDTO> listOrderByConsume(OrderQueryParam param);

}
 