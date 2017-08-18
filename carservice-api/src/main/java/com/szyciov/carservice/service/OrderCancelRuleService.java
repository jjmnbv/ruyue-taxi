package com.szyciov.carservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.szyciov.carservice.dao.OrderApiDao;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.PubOrderCancelRule;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.PubOrderCancelRuleEnum;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by shikang on 2017/7/31.
 */
@Service("OrderCancelRuleService")
public class OrderCancelRuleService {

    private static final Logger LOGGER = Logger.getLogger(OrderCancelRuleService.class);

    @Resource(name = "OrderApiDao")
    private OrderApiDao orderApiDao;

    /**
     *查询订单取消费用
     * @param order
     * @param isDetail
     * @return
     */
    public Map<String, Object> getCancelPrice(Map<String, String> param, boolean isDetail) {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("status", Retcode.OK.code);
        ret.put("message", Retcode.OK.msg);
        ret.put("price", 0);
        ret.put("pricereason", PubOrderCancelRuleEnum.PRICEREASON_EXEMPTION.code);
        if(isDetail) {
            ret.put("ordercancelrule", "");
        }

        //查询订单信息
        PubOrderCancelRule orderCancelRule = new PubOrderCancelRule();
        AbstractOrder order = getOrder(param, orderCancelRule);
        if(null == order) {
            LOGGER.warn("订单(" + order.getOrderno() + ")不存在");
            ret.put("status", Retcode.ORDERNOTEXIT.code);
            ret.put("message", Retcode.ORDERNOTEXIT.msg);
            ret.put("price", null);
            ret.put("pricereason", null);
            return ret;
        }

        //校验订单状态
        if(OrderState.CANCEL.state.equals(order.getOrderstatus())) {
            LOGGER.warn("订单(" + order.getOrderno() + ")已取消");
            ret.put("status", Retcode.ORDERISCANCEL.code);
            ret.put("message", Retcode.ORDERISCANCEL.msg);
            ret.put("price", null);
            ret.put("pricereason", null);
            return ret;
        }
        if(OrderState.INSERVICE.state.equals(order.getOrderstatus()) || OrderState.SERVICEDONE.state.equals(order.getOrderstatus())
            || OrderState.WAITMONEY.state.equals(order.getOrderstatus())) {
            LOGGER.warn("订单(" + order.getOrderno() + ")状态不正确");
            ret.put("status", Retcode.INVALIDORDERSTATUS.code);
            ret.put("message", Retcode.INVALIDORDERSTATUS.msg);
            ret.put("price", null);
            ret.put("pricereason", null);
            return ret;
        }
        if((order instanceof OpTaxiOrder)) {
            OpTaxiOrder taxiOrder = (OpTaxiOrder) order;
            if((OrderState.START.state.equals(order.getOrderstatus()) || OrderState.ARRIVAL.state.equals(taxiOrder.getOrderstatus()))
                && taxiOrder.getMeterrange() > 0) {
                LOGGER.warn("订单(" + order.getOrderno() + ")状态不正确");
                ret.put("status", Retcode.INVALIDORDERSTATUS.code);
                ret.put("message", Retcode.INVALIDORDERSTATUS.msg);
                ret.put("price", null);
                ret.put("pricereason", null);
                return ret;
            }
        }

        //如果接单时间为空，表示没有司机接单，取消不产生费用
        if(null == order.getOrdertime()) {
            LOGGER.info("订单(" + order.getOrderno() + ")没有司机接单，免责取消");
            ret.put("pricereason", PubOrderCancelRuleEnum.PRICEREASON_NOTDRIVER_EXEMPTION.code);
            return ret;
        }
        order.setCanceltime(new Date());

        int canceltimelag = (int) Math.ceil((order.getCanceltime().getTime() - order.getOrdertime().getTime()) / 1000.0 / 60);
        ret.put("canceltimelag", canceltimelag);

        //查询取消规则
        orderCancelRule = getOrderCancelRule(order, orderCancelRule);
        if(null == orderCancelRule) {
            LOGGER.info("订单(" + order.getOrderno() + ")对应的取消规则不存在");
            ret.put("pricereason", PubOrderCancelRuleEnum.PRICEREASON_NOTRULE_EXEMPTION.code);
            return ret;
        }
        if(isDetail) {
            ret.put("ordercancelrule", orderCancelRule);
        }

        //判断司机是否已抵达
        if(OrderState.ARRIVAL.state.equals(order.getOrderstatus()) && null != order.getArrivaltime()) {
            ret.put("driverarraival", true);
            driverArrival(order, orderCancelRule, ret);
        } else {
            ret.put("driverarraival", false);
            driverUnArrival(order, orderCancelRule, ret);
        }
        return ret;
    }

    /**
     * 查询订单详情
     * @param order
     * @param orderParam
     * @return
     */
    private AbstractOrder getOrder(Map<String, String> param, PubOrderCancelRule orderCancelRule) {
        String orderno = param.get("orderno");
        String ordertype = param.get("ordertype");
        String usetype = param.get("usetype");
        if(OrderEnum.ORDERTYPE_TAXI.code.equals(ordertype)) {
            orderCancelRule.setCartype(PubOrderCancelRuleEnum.CARTYPE_TAXI.code);
            orderCancelRule.setPlatformtype(PubOrderCancelRuleEnum.PLATFORMTYPE_OPERATE.code);
            return orderApiDao.getOpTaxiOrder(orderno);
        }

        orderCancelRule.setCartype(PubOrderCancelRuleEnum.CARTYPE_NET.code);
        if(OrderEnum.USETYPE_PERSONAL.code.equals(usetype)) {
            orderCancelRule.setPlatformtype(PubOrderCancelRuleEnum.PLATFORMTYPE_OPERATE.code);
            return orderApiDao.getOpOrder(orderno);
        }
        orderCancelRule.setPlatformtype(PubOrderCancelRuleEnum.PLATFORMTYPE_LEASE.code);
        return orderApiDao.getOrgOrder(orderno);
    }

    /**
     * 从redis中查询取消规则
     * @param order
     * @return
     */
    private PubOrderCancelRule getOrderCancelRule(AbstractOrder order, PubOrderCancelRule orderCancelRule) {
        //组装redis中溢价规则的key
        StringBuilder sb = new StringBuilder();
        sb.append("ORDERCANCELRULE_");
        sb.append(order.getOncity()).append("_");
        sb.append(orderCancelRule.getCartype()).append("_");
        sb.append(orderCancelRule.getPlatformtype()).append("_");
        if(PubOrderCancelRuleEnum.PLATFORMTYPE_LEASE.code == orderCancelRule.getPlatformtype()) {
            sb.append(order.getCompanyid()).append("_");
        }
        sb.append("*");

        //从redis中获取数据
        Set<String> keys = JedisUtil.getKeys(sb.toString());
        if(null != keys && !keys.isEmpty()) {
            for (String key : keys) {
                String value = JedisUtil.getString(key);
                if(StringUtils.isNotBlank(value)) {
                    return StringUtil.parseJSONToBean(value, PubOrderCancelRule.class);
                }
            }
        }
        return null;
    }

    /**
     * 计算乘客取消费用(司机已抵达)
     * @param order
     * @param orderCancelRule
     * @param ret
     * @return
     */
    private void driverArrival(AbstractOrder order, PubOrderCancelRule orderCancelRule, Map<String, Object> ret) {
        //计算司机迟到时间
        long driverLate = order.getArrivaltime().getTime() - order.getUsetime().getTime();
        ret.put("driverlate", (int) Math.ceil(driverLate / 1000.0 / 60));
        if(driverLate < orderCancelRule.getLatecount() * 60 * 1000) { //司机没迟到
            //计算乘客迟到时差
            long passengerLate = order.getCanceltime().getTime() - order.getUsetime().getTime();
            if(passengerLate >= orderCancelRule.getWatingcount() * 60 * 1000) { //乘客迟到
                ret.put("price", orderCancelRule.getPrice());
                ret.put("pricereason", PubOrderCancelRuleEnum.PRICEREASON_PASSENGERLATE_DUTY.code);
            } else { //乘客没有迟到
                ret.put("price", orderCancelRule.getPrice());
                ret.put("pricereason", PubOrderCancelRuleEnum.PRICEREASON_DRIVERARRIVAL_DUTY.code);
            }
        } else {
            ret.put("pricereason", PubOrderCancelRuleEnum.PRICEREASON_DRIVERLATE_EXEMPTION.code);
        }

    }

    /**
     * 计算乘客取消费用(司机未抵达)
     * @param order
     * @param orderCancelRule
     * @param ret
     */
    private void driverUnArrival(AbstractOrder order, PubOrderCancelRule orderCancelRule, Map<String, Object> ret) {
        //计算司机迟到时间
        long driverLate = order.getCanceltime().getTime() - order.getUsetime().getTime();
        ret.put("driverlate", (int) Math.ceil(driverLate / 1000.0 / 60));
        if(driverLate < orderCancelRule.getLatecount() * 60 * 1000) { //司机没迟到
            //计算乘客取消时差
            long cancelTime = order.getCanceltime().getTime() - order.getOrdertime().getTime();
            if(cancelTime >= orderCancelRule.getCancelcount() * 60 * 1000) { //取消时差大于免责取消时限
                ret.put("price", orderCancelRule.getPrice());
                ret.put("pricereason", PubOrderCancelRuleEnum.PRICEREASON_OVERCANCEL_DUTY.code);
            }
        } else {
            ret.put("pricereason", PubOrderCancelRuleEnum.PRICEREASON_DRIVERLATE_EXEMPTION.code);
        }
    }

}
