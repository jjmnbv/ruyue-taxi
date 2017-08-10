package com.szyciov.carservice.controller;

import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.carservice.service.OrderCancelRuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shikang on 2017/7/27.
 */
@Controller
public class OrderCancelRuleController {

    @Resource(name = "OrderCancelRuleService")
    private OrderCancelRuleService orderCancelRuleService;

    /**
     * 查询订单取消费用(不包含取消规则明细)
     * @param order
     * @return
     */
    @RequestMapping(value = "api/OrderCancelRule/GetCancelPrice", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCancelPrice(@RequestBody Map<String, String> param) {
        return orderCancelRuleService.getCancelPrice(param, false);
    }

    /**
     * 查询订单取消费用(包含取消规则明细)
     * @param order
     * @return
     */
    @RequestMapping(value = "api/OrderCancelRule/GetCancelPriceDetail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCancelPriceDetail(@RequestBody Map<String, String> param) {
        return orderCancelRuleService.getCancelPrice(param, true);
    }

}
