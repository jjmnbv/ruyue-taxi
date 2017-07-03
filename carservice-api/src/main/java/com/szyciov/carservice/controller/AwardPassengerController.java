package com.szyciov.carservice.controller;

import com.szyciov.carservice.service.AwardPassengerService;
import com.szyciov.entity.Retcode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhu on 2017/6/21.
 */

@Controller
public class AwardPassengerController {

    private AwardPassengerService awardPassengerService;
    @Resource(name="AwardPassengerService")
    public void setAwardPassengerService(AwardPassengerService awardPassengerService) {
        this.awardPassengerService = awardPassengerService;
    }

    /**
     * 返回积分
     * usertype "0"-机构订单返现，"1"-个人订单返现
     * userphone 下单人手机号码
     * passengerphone 乘车人手机号码
     * money 订单的总金额
     * @param params
     * @return
     */
    @RequestMapping("api/AwardPassenger/AwardPoint")
    @ResponseBody
    public Map<String,Object> awardPoint(@RequestBody Map<String,Object> params){
        try{
            return this.awardPassengerService.awardPoint(params);
        }catch (Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
            return res;
        }
    }

}
