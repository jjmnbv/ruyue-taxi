package com.szyciov.passenger.controller;

import com.szyciov.entity.Retcode;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.passenger.entity.PassengerOrder;
import com.szyciov.passenger.service.PassengerService4Third;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhu on 2017/5/26.
 */
@Controller
public class PassengerController4Third {

    public PassengerService4Third passengerService4Third;

    @Resource(name = "PassengerService4Third")
    public void setPassengerService(PassengerService4Third passengerService4Third) {
        this.passengerService4Third = passengerService4Third;
    }


    /**
     * 用户登录前判断
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/PreLogin",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> preLogin4Op(@RequestBody Map<String,Object> params){
        try{
            return passengerService4Third.preLogin(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("hasorgidentity",false);
            res.put("hasorguser",false);
            res.put("info","");
            return res;
        }
    }

    /**
     * 判断用户是否有
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/HasOrgIdentity",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> hasOrgIdentity(@RequestBody Map<String,Object> params){
        try{
            return passengerService4Third.hasOrgIdentity(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("hasorgidentity",false);
            return res;
        }
    }

    /**
     * 跳转到其他界面
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/Go2OtherPage",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> go2OtherPage(@RequestBody Map<String,Object> params){
        try{
            return passengerService4Third.go2OtherPage(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("hasorgidentity",false);
            res.put("usertoken",false);
            res.put("tags",new ArrayList<>());
            res.put("nickname","");
            res.put("telphone","");
            res.put("imgpath","");
            res.put("hasorguser",false);
            return res;
        }
    }


    /**
     * 回执设置极光的注册id
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/PostRegisterId",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> postRegisterId(@RequestBody Map<String,Object> params){
        try{
            return passengerService4Third.postRegisterId(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }

    /**
     * 获取服务车企列表
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetServiceCompanys",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getServiceCompanys(@RequestParam Map<String,Object> params){
        try{
            return passengerService4Third.getServiceCompanys(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("companys",new ArrayList());
            return res;
        }
    }

    /**
     * 获取网约车预估费用
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetEstimatedCost4NetCar",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEstimatedCost4NetCar(@RequestParam Map<String,Object> params){
        try{
            return passengerService4Third.getEstimatedCost4NetCar(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }


    /**
     * 获取网约车预估费用
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetEstimatedCost4Taxi",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getEstimatedCost4Taxi(@RequestBody Map<String,Object> params){
        try{
            return passengerService4Third.getEstimatedCost4Taxi(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }

    /**
     * 初步预估费用
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetEstimatedCostFirst",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEstimatedCostFirst(@RequestParam Map<String,Object> params){
        try{
            return passengerService4Third.getEstimatedCostFirst(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }

    /**
     * 获取网约车服务车型
     * @param params
     * @return
     */
    @RequestMapping(value="Passenger/GetCarMoudels4NetCar",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getCarMoudels4NetCar(@RequestParam Map<String,Object> params){
        try{
            return passengerService4Third.getCarMoudels4NetCar(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }

    /**
     * 网约车下单
     * @param order
     * @param request
     * @return
     */
    @RequestMapping(value = "Passenger/AddOder4NetCar", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addOder4NetCar(@RequestBody PassengerOrder order, HttpServletRequest request) {
        try{
            return passengerService4Third.addOder4NetCar(order, request);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }

    /**
     * 出租车下单
     * @param order
     * @param request
     * @return
     */
    @RequestMapping(value = "Passenger/AddOder4Taxi", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addOder4Taxi(@RequestBody OpTaxiOrder order, HttpServletRequest request) {
        try{
            return passengerService4Third.addOder4Taxi(order, request);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }

    /**
     * 当前城市是否有服务
     * @param order
     * @param request
     * @return
     */
    @RequestMapping(value = "Passenger/HasServiceInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> hasServiceInfo(@RequestBody Map<String,Object> params) {
        try{
            return passengerService4Third.hasServiceInfo(params);
        }catch(Exception e){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            return res;
        }
    }

    /**
     * 获取网约车计费规则
     * @param param
     * @param req
     * @param res
     */
    @RequestMapping(value = "Passenger/GetAccountRules4NetCar", method = RequestMethod.GET)
    public void getAccountRules4NetCar(@RequestParam Map<String,Object> param,HttpServletRequest req,HttpServletResponse res){
        passengerService4Third.getAccountRules4NetCar(param,res);
    }

    /**
     * 出租车获取计费规则
     * @param param
     * @param req
     * @param res
     */
    @RequestMapping(value = "Passenger/GetAccountRules4Taxi", method = RequestMethod.GET)
    public void getAccountRules4Taxi(@RequestParam Map<String,Object> param,HttpServletRequest req,HttpServletResponse res){
        passengerService4Third.getAccountRules4Taxi(param,res);
    }
}
