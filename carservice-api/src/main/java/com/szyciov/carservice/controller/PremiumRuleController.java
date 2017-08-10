package com.szyciov.carservice.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.szyciov.carservice.service.PremiumRuleService;
import com.szyciov.param.PremiumRuleParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shikang on 2017/7/27.
 */
@Controller
public class PremiumRuleController {

    @Resource(name = "PremiumRuleService")
    private PremiumRuleService premiumRuleService;

    /**
     * 查询溢价倍率
     * @param param
     * @return
     */
    @RequestMapping(value = "api/PremiumRule/GetPremiumrate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPremiumrate(@RequestBody PremiumRuleParam param, HttpServletRequest request) {
        return premiumRuleService.getPremiumrate(param);
    }


}
