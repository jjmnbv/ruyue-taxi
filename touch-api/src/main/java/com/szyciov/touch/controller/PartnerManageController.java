package com.szyciov.touch.controller;

import com.szyciov.touch.enums.ResultStateEnum;
import com.szyciov.touch.service.PartnerManageService;
import com.szyciov.touch.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 合作方管理接口
 * Created by shikang on 2017/5/10.
 */

@Controller
@RequestMapping("/partner")
public class PartnerManageController {

    private static final Logger logger = Logger.getLogger(PartnerUseCarController.class);

    private PartnerManageService partnerManageService;
    
    @Resource(name = "PartnerManageService")
    public void setPartnerManageService(PartnerManageService partnerManageService) {
        this.partnerManageService = partnerManageService;
    }

    /**
     * 检测未支付订单信息
     * @return
     */
    @RequestMapping(value="getUnPaidOrder",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getUnPaidOrder(HttpServletRequest req, HttpServletResponse res){
        try {
            return partnerManageService.getUnPaidOrder(req,res);
        } catch (Exception e) {
            logger.error("获取未支付订单信息",e);
            return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
        }
    }
}
