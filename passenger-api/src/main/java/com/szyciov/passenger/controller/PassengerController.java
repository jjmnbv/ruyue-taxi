package com.szyciov.passenger.controller;

import com.szyciov.entity.Retcode;
import com.szyciov.passenger.Const;
import com.szyciov.passenger.entity.PassengerOrder;
import com.szyciov.passenger.param.LoginParam;
import com.szyciov.passenger.param.RegisterParam;
import com.szyciov.passenger.service.PassengerService;
import com.szyciov.passenger.service.PassengerService4Third;
import com.szyciov.util.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
public class PassengerController extends BaseController {
	
	public PassengerService passengerService;

	@Resource(name = "PassengerService")
	public void setPassengerService(PassengerService passengerService) {
		this.passengerService = passengerService;
	}

	public PassengerService4Third passengerService4Third;

	@Resource(name = "PassengerService4Third")
	public void setPassengerService4Third(PassengerService4Third passengerService4Third) {
		this.passengerService4Third = passengerService4Third;
	}
	
	/**
	 * 获取广告页接口
	 * @return
	 */
	@RequestMapping(value="Passenger/GetAdvertisementPath",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAdvertisementPath(@RequestParam Map<String,Object> params){
		return passengerService.getAdvertisementPath(params);
	}
	
	/**
	 * 获取引导页接口
	 * @return
	 */
	@RequestMapping(value="Passenger/GetGuidePath",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getGuidePath(@RequestParam Map<String,Object> params){
		return passengerService.getGuidePath(params);
	}
	
	/**
	 * 获取验证码（修改）
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetVerificationCode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getVerificationCode(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getVerificationCode(params);
			}
			String phone = (String) params.get("phone");
			String usertype = (String) params.get("usertype");
			String smstype = (String) params.get("smstype");
			return passengerService.getVerificationCode(phone, usertype, smstype);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 验证验证码（修改）
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "Passenger/ValidateCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> validateCode(@RequestBody Map<String,Object> params){
		return passengerService.validateCode(params);
	}
	
	/**
	 * 登录
	 * @param loginparam
	 * @return
	 */
	@RequestMapping(value = "Passenger/Login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> login(@RequestBody LoginParam loginparam){
		return passengerService.login(loginparam);
	}
	
	/**
	 * 静默登录
	 * @param loginparam
	 * @return
	 */
	@RequestMapping(value = "Passenger/DefLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> defLogin(@RequestBody Map<String,Object> loginparam){
		try{
			String version = (String) loginparam.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.defLogin(loginparam);
			}if(Const.INTERFACE_V3_0_2.equals(version)){
				return passengerService4Third.defLogin2(loginparam);
			}
		}catch (Exception e){
		}
		return passengerService.defLogin(loginparam);
	}
	
	/**
	 * 短信验证码注册
	 * @param phone
	 * @param verificationcode
	 * @return
	 */
	@RequestMapping(value = "Passenger/Register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> register(@RequestBody Map<String,Object> params){
		return passengerService.register(params);
	}
	
	/**
	 * 注册用户信息
	 * @return
	 */
	@RequestMapping(value = "Passenger/DoRegister", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doRegister(@RequestBody RegisterParam registerparam){
		return passengerService.doRegister(registerparam);
	}
	
	/**
	 * 获取最新正在服务中的订单
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetServiceOder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getServiceOder(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口获取未支付的订单
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getServiceOder(params);
			}
			String usertoken = (String) params.get("usertoken");
			return passengerService.getServiceOder(usertoken);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取租赁公司列表
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetCompanys", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCompanys(@RequestParam String usertoken,@RequestParam(required=false) String city){
		return passengerService.getCompanys(usertoken,city);
	}
	
	/**
	 * 获取未支付的订单
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetUnpayOders", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUnpayOders(@RequestParam String usertoken,@RequestParam(required=false) String companyid,@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口获取未支付的订单
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getUnpayOders(usertoken,companyid);
			}
			return passengerService.getUnpayOders(usertoken,companyid);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取租赁公司或者运营端在城市提供的服务（改）
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetBusiness", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getBusiness(@RequestParam Map<String,Object> params){
		return passengerService.getBusiness(params);
	}
	
	/**
	 * 获取服务车型
	 * @param params
	 */
	@RequestMapping(value = "Passenger/GetCarMoudels", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCarMoudels(@RequestParam Map<String,Object> params){
		return passengerService.getCarMoudels(params);
	}
	
	/**
	 * 获取常用联系人
	 * @param params
	 */
	@RequestMapping(value = "Passenger/GetMostContact", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getMostContact(@RequestParam String usertoken, @RequestParam(required = false) String version){
        try{
            //v3.0.1获取常用联系人接口
            if(Const.INTERFACE_V3_0_1.equals(version)){
                return passengerService4Third.getMostContact(usertoken);
            }
        }catch (Exception e){
        }
		return passengerService.getMostContact(usertoken);
	}
	
	/**
	 * 获取预估费用
	 * @param params
	 */
	@RequestMapping(value = "Passenger/GetEstimatedCost", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getEstimatedCost(@RequestParam Map<String,Object> params){
		try {
			if (StringUtils.isNotBlank(String.valueOf(params.get("version"))) && Const.INTERFACE_V3_0_1.equals(String.valueOf(params.get("version")))) {
				return passengerService4Third.getEstimatedCost(params);
			} else {
				return passengerService.getEstimatedCost(params);
			}
		} catch (Exception e) {
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取机场
	 * @param usertoken
	 * @param city
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetAirportAddrt", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAirportAddrt(@RequestParam String usertoken,@RequestParam String city){
		return passengerService.getAirportAddrt(usertoken,city);
	}
	
	/**
	 * 设置常用联系人
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/AddMostContact", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addMostContact(@RequestBody Map<String,Object> params){
        try{
            String version = (String) params.get("version");
            //v3.0.1设置常用联系人接口
            if(Const.INTERFACE_V3_0_1.equals(version)){
                return passengerService4Third.addMostContact(params);
            }
        }catch (Exception e){
        }
		return passengerService.addMostContact(params);
	}
	
	/**
	 * 删除常用联系人
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/DeleteMostContact", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteMostContact(@RequestBody Map<String,Object> params){
        try{
            String version = (String) params.get("version");
            //v3.0.1删除常用联系人接口
            if(Const.INTERFACE_V3_0_1.equals(version)) {
                return passengerService4Third.deleteMostContact(params);
            }
        }catch(Exception e){
        }
		return passengerService.deleteMostContact(params);
	}
	
	/**
	 * 获取用车备注和用车事由
	 * @param usertoken
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetUseResonAndRemark", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUseResonAndRemark(@RequestParam String usertoken){
		return passengerService.getUseResonAndRemark(usertoken);
	}
	
	/**
	 * 获取租赁公司计费规则
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "Passenger/GetAccountRules", method = RequestMethod.GET)
	public void getAccountRules(@RequestParam Map<String,Object> param,HttpServletRequest req,HttpServletResponse res){
		try{
			String version = (String) param.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				 passengerService4Third.getAccountRules(param,res);
				 return ;
			}
			passengerService.getAccountRules(param,res);
		}catch (Exception e){
		}
	}
	
	/**
	 * 获取租赁公司提供服务的城市名称
	 * @param usertoken
	 * @param companyid
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetValidCity", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getValidCity(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getValidCity(params);
			}
			String usertoken = (String) params.get("usertoken");
			String companyid = (String) params.get("companyid");
			return passengerService.getValidCity(usertoken,companyid);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取城市列表
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetCity", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCity(@RequestParam Map<String,Object> params){
		return passengerService.getCity(params);
	}
	
	@RequestMapping(value = "Passenger/GetGetOffCitys", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getGetOffCitys(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getGetOffCitys(params);
			}
			return passengerService.getGetOffCitys(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	@RequestMapping(value = "Passenger/GetGetOnCitys", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getGetOnCitys(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getGetOnCitys(params);
			}
			return passengerService.getGetOnCitys(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 提交订单
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "Passenger/AddOder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOder(@RequestBody PassengerOrder order,HttpServletRequest request){
		return passengerService.addOder(order,request);
	}
	
	/**
	 * 获取用户详细信息
	 * @param usertoken
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetPassengerInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getPassengerInfo(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			// v3.0.1接口我的行程列表调整
			if (Const.INTERFACE_V3_0_1.equals(version)) {
				return passengerService4Third.getPassengerInfo(params);
			}
			String usertoken = (String) params.get("usertoken");
			return passengerService.getPassengerInfo(usertoken);
		}catch (Exception e){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 上传头像
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "Passenger/UploadPassengerImg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadPassengerImg(@RequestBody Map<String,Object> param){
		return passengerService.uploadPassengerImg(param);
	}
	
	/**
	 * 更新用户信息
	 * @param passenger
	 * @return
	 */
	@RequestMapping(value = "Passenger/UpdatePassengerInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePassengerInfo(@RequestBody Map<String,Object> userinfos){
		try{
			String version = (String) userinfos.get("version");
			// v3.0.1接口我的行程列表调整
			if (Const.INTERFACE_V3_0_1.equals(version)) {
				return passengerService4Third.updatePassengerInfo(userinfos);
			}
			return passengerService.updatePassengerInfo(userinfos);
		}catch (Exception e){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取订单列表
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetOders", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOders(@RequestParam Map<String,Object> params){
		try {
			String version = (String) params.get("version");
			// v3.0.1接口我的行程列表调整
			if (Const.INTERFACE_V3_0_1.equals(version)) {
				return passengerService4Third.getOders(params);
			}
			return passengerService.getOders(params);
		} catch (Exception e) {
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 更新订单状态
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/UpdateOderState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateOderState(@RequestBody Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新订单状态
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.updateOderState(params);
			}
			return passengerService.updateOderState(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取司机信息
	 * @param usertoken
	 * @param driverid
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetDriverInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getDriverInfo(@RequestParam String usertoken,@RequestParam String driverid){
		return passengerService.getDriverInfo(usertoken,driverid);
	}
	
	/**
	 * 获取司机位置信息(增加参数)
	 * @param usertoken
	 * @param driverid
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetDriverPosition_old", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getDriverPosition(@RequestParam String usertoken,@RequestParam String driverid,@RequestParam(required=false) String orderno){
		return passengerService.getDriverPosition(usertoken,driverid,orderno);
	}
	
	/**
	 * 获取支付信息
	 * @param usertoken
	 * @param companyid
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetPayAccounts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getPayAccounts(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getPayAccounts(params);
			}
			return passengerService.getPayAccounts(params);
		}catch (Exception e){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 支付接口
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/PayOder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> payOder(@RequestBody Map<String,Object> params,HttpServletRequest req){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.payOder(params,req);
			}
			return passengerService.payOder(params,req);
		}catch (Exception e){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取订单详细信息
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetOder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOder(@RequestParam Map<String,Object> params){
		try {
			if (StringUtils.isNotBlank(String.valueOf(params.get("version"))) && Const.INTERFACE_V3_0_1.equals(String.valueOf(params.get("version")))) {
				return passengerService4Third.getOder(params);
			} else {
				return passengerService.getOder(params);
			}
		} catch (Exception e) {
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取评价列表
	 * @param usertoken
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetComments", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getComments(@RequestParam String usertoken){
		return passengerService.getComments(usertoken);
	}
	
	/**
	 * 评价司机
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/DoComment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doComment(@RequestBody Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.doComment(params);
			}
			return passengerService.doComment(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}

	}
	
	/**
	 * 获取轨迹
	 * @param usertoken
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetOrbit", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrbit(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getOrbit(params);
			}
			return passengerService.getOrbit(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}

	}
	
	/**
	 * 获取用车规则
	 * @param uesrtoken
	 * @param city
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetUseCarRules", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUseCarRules(@RequestParam String usertoken,@RequestParam String city){
		return passengerService.getUseCarRules(usertoken,city);
	}
	
	/**
	 * 获取钱包信息
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetBalance", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getBalance(@RequestParam Map<String,Object> params){
		return passengerService.getBalance(params);
		
	}
	
	/**
	 * 获取详细列表
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetMessages", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getMessages(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getMessages(params);
			}
			return passengerService.getMessages(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 更新消息状态
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/UpdateMessageState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMessageState(@RequestBody Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			if(Const.INTERFACE_V3_0_1.equals(version)) {
				return passengerService4Third.updateMessageState(params);
			}
			return passengerService.updateMessageState(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取常用地址
	 * @param usertoken
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetMostAddress", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getMostAddress(@RequestParam Map<String,Object> params){
        try {
            String version = (String) params.get("version");
            if(Const.INTERFACE_V3_0_1.equals(version)) {
                return passengerService4Third.getMostAddress(params);
            }
        } catch (Exception e) {
        }
        return passengerService.getMostAddress(params);
	}
	
	/**
	 * 删除常用地址
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/DeleteMostAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteMostAddress(@RequestBody Map<String,Object> params){
        try {
            String version = (String) params.get("version");
            if(Const.INTERFACE_V3_0_1.equals(version)) {
                return passengerService4Third.deleteMostAddress(params);
            }
        } catch (Exception e) {
        }
        return passengerService.deleteMostAddress(params);
	}
	
	/**
	 * 新增常用地址
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/AddMostAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addMostAddress(@RequestBody Map<String,Object> params){
        try {
            String version = (String) params.get("version");
            if(Const.INTERFACE_V3_0_1.equals(version)) {
                return passengerService4Third.addMostAddress(params);
            }
        } catch (Exception e) {

        }
        return passengerService.addMostAddress(params);
	}
	
	/**
	 * 验证密码
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/ValidatePwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> validatePwd(@RequestBody Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			if(Const.INTERFACE_V3_0_2.equals(version)){
				return passengerService4Third.validatePwd(params);
			}
			return passengerService.validatePwd(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 修改密码
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/UpdatePwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePwd(@RequestBody Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.updatePwd(params);
			}else if(Const.INTERFACE_V3_0_2.equals(version)){
				return passengerService4Third.updatePwd2(params);
			}
			return passengerService.updatePwd(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取最新版本
	 * @param usertoken
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetNewVersion", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getNewVersion(@RequestParam Map<String,Object> params){
		return passengerService.getNewVersion(params);
	}
	
	@RequestMapping(value = "Passenger/GetAgreement", method = RequestMethod.GET)
	public void getAgreement(HttpServletRequest req,HttpServletResponse res){
		passengerService.getAgreement(req,res);
	}
	
	@RequestMapping(value = "Passenger/PayOrderByBalance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> payOrderByBalance(@RequestBody Map<String,Object> params){
		return passengerService.payOrderByBalance(params);
	}
	
	@RequestMapping(value = "Passenger/DillWXPayed4Org", method = RequestMethod.POST)
	public void dillWXPayed(HttpServletRequest req,HttpServletResponse res){
		passengerService.dillWXPayed4Org(req,res);
	}
	
	@RequestMapping(value = "Passenger/DillWXPayed4Op", method = RequestMethod.POST)
	public void dillWXPayed4Op(HttpServletRequest req,HttpServletResponse res){
		passengerService.dillWXPayed4Op(req,res);
	}
	
	@RequestMapping(value = "Passenger/DillZFBPayed4Org", method = RequestMethod.POST)
	public void dillZFBPayed4Org(HttpServletRequest req,HttpServletResponse res){
		passengerService.dillZFBPayed4Org(req,res);
	}
	
	@RequestMapping(value = "Passenger/DillZFBPayed4Op", method = RequestMethod.POST)
	public void dillZFBPayed4Op(HttpServletRequest req,HttpServletResponse res){
		passengerService.dillZFBPayed4Op(req,res);
	}
	
	@RequestMapping(value = "Passenger/Logout", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> logout(@RequestBody Map<String,Object> params,HttpServletRequest req,HttpServletResponse res){
		return passengerService.logout(params);
	}
	
	@RequestMapping(value = "Passenger/GetServiceInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getServiceInfo(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getServiceInfo(params);
			}
			return passengerService.getServiceInfo(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	@RequestMapping(value = "Passenger/ReadMessageAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> readMessageAll(@RequestBody Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.readMessageAll(params);
			}
			return passengerService.readMessageAll(params);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}

	}
	
	@RequestMapping(value = "Passenger/GetUnderTakeOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUnderTakeOrder(@RequestParam String usertoken,HttpServletRequest req,HttpServletResponse res){
		return passengerService.getUnderTakeOrder(usertoken);
	}
	
	@RequestMapping(value = "Passenger/AddOrderFail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOrderFail(@RequestBody Map<String,Object> params,HttpServletRequest req,HttpServletResponse res){
		return passengerService.addOrderFail(params);
	}
	
	@RequestMapping(value = "Passenger/GetNearDrivers", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getNearDrivers(@RequestParam Map<String,Object> params,HttpServletRequest req,HttpServletResponse res){
        try {
            String version = (String) params.get("version");
            if(Const.INTERFACE_V3_0_1.equals(version)) {
                return passengerService4Third.getNearDrivers(params);
            }
        } catch (Exception e) {
        }
        return passengerService.getNearDrivers(params);
	}
	
	@RequestMapping(value = "Passenger/GetSign4ZFB", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSign4ZFB(@RequestBody Map<String,Object> params,HttpServletRequest req,HttpServletResponse res){
		return passengerService.getSign4ZFB(params);
	}
	
	@RequestMapping(value = "Passenger/GetSign4WX", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSign4WX(@RequestBody Map<String,Object> params,HttpServletRequest req,HttpServletResponse res){
		return passengerService.getSign4WX(params);
	}
	
	/**
	 * 获取常见问答
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "Passenger/GetCommonQA", method = RequestMethod.GET)
	public void getCommonQA(HttpServletRequest req,HttpServletResponse res){
		passengerService.getCommonQA(req,res);
	}
}
