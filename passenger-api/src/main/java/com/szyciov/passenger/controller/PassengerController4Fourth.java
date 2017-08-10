package com.szyciov.passenger.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.Retcode;
import com.szyciov.passenger.service.PassengerService4Fourth;

/**
 * 网约车4期修改新增接口
 * @author zhu
 *
 */
@Controller
public class PassengerController4Fourth {
	
	public PassengerService4Fourth passengerService4Fourth;

    @Resource(name = "PassengerService4Fourth")
    public void setPassengerService(PassengerService4Fourth passengerService4Fourth) {
        this.passengerService4Fourth = passengerService4Fourth;
    }

    /**
     * 用户获取钱包和优惠券数目
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetAmountInfo",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAmountInfo(@RequestParam Map<String,Object> params){
        try{
            return passengerService4Fourth.getAmountInfo(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }
    
    /**
     * 用户优惠券明细
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetCouponDetail",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getCouponDetail(@RequestParam Map<String,Object> params){
        try{
            return passengerService4Fourth.getCouponDetail(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }
    
    /**
     * 获取投诉列表
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetComplaints",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getComplaints(@RequestParam Map<String,Object> params){
        try{
            return passengerService4Fourth.getComplaints(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }
    
    /**
     * 投诉
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/DoComplaint",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doComplaint(@RequestBody Map<String,Object> params){
        try{
            return passengerService4Fourth.doComplaint(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }
    
    /**
     * 邀请注册
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/InviteUser",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> inviteUser(@RequestBody Map<String,Object> params){
        try{
            return passengerService4Fourth.inviteUser(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }
    
    /**
     * 获取当前溢价倍数
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetPremium",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPremium(@RequestParam Map<String,Object> params){
        try{
            return passengerService4Fourth.getPremium(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }
    
    /**
     * 取消前判断
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/PreCancel",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> preCancel(@RequestParam Map<String,Object> params){
        try{
            return passengerService4Fourth.preCancel(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }
    
    /**
     * 获取取消规则
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetCancelRules",method= RequestMethod.GET)
    public void getCancelRules(@RequestParam Map<String,Object> params,HttpServletRequest req,HttpServletResponse res){
        passengerService4Fourth.getCancelRules(params,res);
    }
    
    /**
     * 获取可用优惠券列表
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetAbleCoupons",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAbleCoupons(@RequestParam Map<String,Object> params){
        try{
            return passengerService4Fourth.getAbleCoupons(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }
    
    /**
     * 获取可用优惠券列表
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/UseCoupon",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> useCoupon(@RequestBody Map<String,Object> params){
        try{
            return passengerService4Fourth.useCoupon(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }
    
    /**
     * 提交取消原因
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/SubmitCancelReson",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> submitCancelReson(@RequestBody Map<String,Object> params){
        try{
            return passengerService4Fourth.submitCancelReson(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }
}
