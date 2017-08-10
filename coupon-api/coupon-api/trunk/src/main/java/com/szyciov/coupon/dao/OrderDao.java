package com.szyciov.coupon.dao;

import java.util.List;

import javax.annotation.Resource;

import com.szyciov.coupon.dto.OrderInfoDTO;
import com.szyciov.coupon.mapper.OrderMapper;
import com.szyciov.coupon.param.OrderQueryParam;
import org.springframework.stereotype.Repository;

/**
 * 订单DAO
 * @author LC
 * @date 2017/7/27
 */
@Repository
public class OrderDao {

    @Resource
    private OrderMapper orderMapper;


    public Integer getOrderCount(OrderQueryParam param){
        return orderMapper.getOrderCount(param);
    }

    public List<Double> listOrderMoney(OrderQueryParam param){
        return orderMapper.listOrderMoney(param);
    }

    /**
     * 根据订单完成时间获取订单数据
     * @param param
     * @return
     */
    public List<OrderInfoDTO> listOrderByEndTime(OrderQueryParam param){
        return orderMapper.listOrderByEndTime(param);
    }



    /**
     * 返回消费过的订单记录
     * @param param
     * @return
     */
    public List<OrderInfoDTO> listOrderByConsume(OrderQueryParam param){
        return orderMapper.listOrderByConsume(param);
    }




    /**
     * 根据订单支付时间获取订单数据
     * @param param
     * @return
     */
    public List<OrderInfoDTO> listOrderByComplTime(OrderQueryParam param){
        return orderMapper.listOrderByComplTime(param);
    }

}
 