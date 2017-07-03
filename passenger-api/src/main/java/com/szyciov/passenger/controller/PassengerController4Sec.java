package com.szyciov.passenger.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.Retcode;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.passenger.Const;
import com.szyciov.passenger.service.PassengerService4Sec;
import com.szyciov.passenger.service.PassengerService4Third;
import com.szyciov.util.BaseController;


@Controller
public class PassengerController4Sec extends BaseController {
	
	public PassengerService4Sec passengerService4Sec;

	@Resource(name = "PassengerService4Sec")
	public void setPassengerService(PassengerService4Sec passengerService4Sec) {
		this.passengerService4Sec = passengerService4Sec;
	}
	
	public PassengerService4Third passengerService4Third;

	@Resource(name = "PassengerService4Third")
	public void setPassengerService4Third(PassengerService4Third passengerService4Third) {
		this.passengerService4Third = passengerService4Third;
	}
	
	/**
	 * 个人用户登录前判断
	 * @param params
	 * @return
	 */
	@RequestMapping(value="Passenger/PreLogin4Op",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> preLogin4Op(@RequestBody Map<String,Object> params){
		return passengerService4Sec.preLogin4Op(params);
	}
	
	/**
	 * 个人用户获取租赁公司列表
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetCompanys4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCompanys4Op(){
		return passengerService4Sec.getCompanys4Op();
	}
	
	/**
	 * 个人用户获取在城市提供的服务
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetBusiness4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getBusiness4Op(@RequestParam Map<String,Object> params){
		return passengerService4Sec.getBusiness4Op(params);
	}
	
	/**
	 * 个人用户获取附件司机
	 * @param params
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetNearDrivers4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getNearDrivers4Op(@RequestParam Map<String,Object> params){
		return passengerService4Sec.getNearDrivers4Op(params);
	}
	
	/**
	 * 判断个人用户是否在某城市提供出租车服务
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/CityHasService4OpTaxi", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> cityHasService4OpTaxi(@RequestParam Map<String,Object> params){
		return passengerService4Sec.cityHasService4OpTaxi(params);
	}
	
	/**
	 * 个人用户获取上车城市列表
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetGetOnCitys4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getGetOnCitys4Op(@RequestParam Map<String,Object> params){
		return passengerService4Sec.getGetOnCitys4Op(params);
	}
	
	/**
	 * 个人用户获取下车城市列表
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetGetOffCitys4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getGetOffCitys4Op(@RequestParam Map<String,Object> params){
		return passengerService4Sec.getGetOffCitys4Op(params);
	}
	
	
	/**
	 * 个人用户网约车服务车型
	 * @param params
	 */
	@RequestMapping(value = "Passenger/GetCarMoudels4OpNetCar", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCarMoudels4OpNetCar(@RequestParam Map<String,Object> params){
		return passengerService4Sec.getCarMoudels4OpNetCar(params);
	}
	
	/**
	 * 获取用车备注和用车事由
	 * 
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetUseResonAndRemark4Sec", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUseResonAndRemark4Sec(){
		return passengerService4Sec.getUseResonAndRemark4Sec();
	}
	
	/**
	 * 获取租赁公司计费规则
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "Passenger/GetAccountRules4OpNetCar", method = RequestMethod.GET)
	public void getAccountRules4OpNetCar(@RequestParam Map<String,Object> param,HttpServletRequest req,HttpServletResponse res){
		passengerService4Sec.getAccountRules4OpNetCar(param,res);
	}
	
	/**
	 * 是否含有登录密码
	 * @return
	 */
	@RequestMapping(value = "Passenger/HasPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> hasPassword(@RequestBody Map<String,Object> param){
		return passengerService4Sec.hasPassword(param);
	}
	
	/**
	 * 是否含有登录密码
	 * @return
	 */
	@RequestMapping(value = "Passenger/EverChangeCashPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> everChangeCashPwd(@RequestBody Map<String,Object> param){
		return passengerService4Sec.everChangeCashPwd(param);
	}
	
	
	/**
	 * 验证提现密码
	 * @return
	 */
	@RequestMapping(value = "Passenger/ValidateCashPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> validateCashPwd(@RequestBody Map<String,Object> param){
		return passengerService4Sec.validateCashPwd(param);
	}
	
	/**
	 * 更改提现密码
	 * @return
	 */
	@RequestMapping(value = "Passenger/UpdateCashPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateCashPwd(@RequestBody Map<String,Object> param){
		return passengerService4Sec.updateCashPwd(param);
	}
	
	/**
	 * 重置提现密码
	 * @return
	 */
	@RequestMapping(value = "Passenger/ResetCashPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resetCashPwd(@RequestBody Map<String,Object> param){
		return passengerService4Sec.resetCashPwd(param);
	}
	
	
	/**
	 * 获取钱包余额
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetBalance4Sec", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getBalance4Sec(@RequestParam Map<String,Object> param){
		try{
			String version = (String) param.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getBalance4Third(param);
			}
			return passengerService4Sec.getBalance4Sec(param);
		}catch (Exception e){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取银行信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetBankInfos", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getBankInfos(@RequestParam Map<String,Object> param){
		return passengerService4Sec.getBankInfos(param);
	}
	
	/**
	 * 获取交易明细
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetDealDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getDealDetail(@RequestParam Map<String,Object> param){
		try{
			String version = (String) param.get("version");
			//v3.0.1接口余额明细
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getDealDetail(param);
			}
			return passengerService4Sec.getDealDetail(param);
		}catch (Exception e){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	
	/**
	 * 获取余额明细
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetBalanceDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getBalanceDetail(@RequestParam Map<String,Object> param){
		try{
			String version = (String) param.get("version");
			//v3.0.1接口余额明细
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getBalanceDetail(param);
			}
			return passengerService4Sec.getBalanceDetail(param);
		}catch (Exception e){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 添加银行卡信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/AddBankCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addBankCard(@RequestBody Map<String,Object> param){
		return passengerService4Sec.addBankCard(param);
	}
	
	/**
	 * 验证银行卡信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/ValidateBankInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> validateBankInfo(@RequestParam Map<String,Object> param){
		return passengerService4Sec.validateBankInfo(param);
	}
	
	/**
	 * 提现申请
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/ApplyCash", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> applyCash(@RequestBody Map<String,Object> param){
		return passengerService4Sec.applyCash(param);
	}
	
	/**
	 * 获取充值的方式有哪些
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetRechargeTypes", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getRechargeTypes(@RequestParam Map<String,Object> param){
		try{
			String version = (String) param.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getRechargeTypes(param);
			}
			return passengerService4Sec.getRechargeTypes(param);
		}catch (Exception e){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}

	}
	
	/**
	 * 充值
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/Recharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> recharge(@RequestBody Map<String,Object> param,HttpServletRequest request){
		try {
			String version = (String) param.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.recharge(param,request);
			}
			return passengerService4Sec.recharge(param,request);
		}catch (Exception e){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取订单列表
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetOders4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOders4Op(@RequestParam Map<String,Object> param){
		return passengerService4Sec.getOders4Op(param);
	}
	
	/**
	 * 获取订单详细信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetOder4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOder4Op(@RequestParam Map<String,Object> param){
		try {
			if (StringUtils.isNotBlank(String.valueOf(param.get("version"))) && Const.INTERFACE_V3_0_1.equals(String.valueOf(param.get("version")))) {
				return passengerService4Third.getOder4Op(param);
			} else {
				return passengerService4Sec.getOder4Op(param);
			}
		} catch (Exception e) {
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 获取正在服务的订单
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetServiceOder4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getServiceOder4Op(@RequestParam Map<String,Object> param){
		return passengerService4Sec.getServiceOder4Op(param);
	}
	
	/**
	 * 是否有未支付的订单
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetUnpayOders4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUnpayOders4Op(@RequestParam Map<String,Object> param){
		return passengerService4Sec.getUnpayOders4Op(param);
	}
	
	/**
	 * 一键报警
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/RegisterAlarm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> registerAlarm(@RequestBody Map<String,Object> param){
		try{
			String version = (String) param.get("version");
			//v3.0.1接口更新密码
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.registerAlarm(param);
			}
			return passengerService4Sec.registerAlarm(param);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}

	}
	
	/**
	 * 个人用户获取网约车的预估费用
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetEstimatedCost4OpNetCar", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getEstimatedCost4OpNetCar(@RequestParam Map<String,Object> param){
		try {
			if (StringUtils.isNotBlank(String.valueOf(param.get("version"))) && Const.INTERFACE_V3_0_1.equals(String.valueOf(param.get("version")))) {
				return passengerService4Third.getEstimatedCost4OpNetCar(param);
			} else {
				return passengerService4Sec.getEstimatedCost4OpNetCar(param);
			}
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 个人用户获取出租车的预估费用
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetEstimatedCost4OpTaxi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getEstimatedCost4OpTaxi(@RequestBody Map<String,Object> param){
		return passengerService4Sec.getEstimatedCost4OpTaxi(param);
	}
	
	/**
	 * 行程分享
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/ItinerarySharing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> itinerarySharing(@RequestBody Map<String,Object> param){
		return passengerService4Sec.itinerarySharing(param);
	}
	
	/**
	 * 行程分享用
	 * 获取订单信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOrder(@RequestParam Map<String,Object> param){
		return passengerService4Sec.getOrder(param);
	}
	
	/**
	 * 行程分享用
	 * 获取订单的轨迹信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetOrderTraceData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOrderTraceData(@RequestParam Map<String,Object> param){
		return passengerService4Sec.getOrderTraceData(param);
	}
	
	/**
	 * 个人微信充值回调
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "Passenger/DillWXCharge4Op", method = RequestMethod.POST)
	public void dillWXCharge4Op(HttpServletRequest req,HttpServletResponse res){
		passengerService4Sec.dillWXCharge4Op(req,res);
	}
	
	/**
	 * 机构用户微信充值回调
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "Passenger/DillWXCharge4Org", method = RequestMethod.POST)
	public void dillWXCharge4Org(HttpServletRequest req,HttpServletResponse res){
		passengerService4Sec.dillWXCharge4Org(req,res);
	}
	
	/**
	 * 个人支付宝充值回调
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "Passenger/DillZFBCharge4Op", method = RequestMethod.POST)
	public void dillZFBCharge4Op(HttpServletRequest req,HttpServletResponse res){
		passengerService4Sec.dillZFBCharge4Op(req,res);
	}
	
	/**
	 * 机构支付宝充值回调
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "Passenger/DillZFBCharge4Org", method = RequestMethod.POST)
	public void dillZFBCharge4Org(HttpServletRequest req,HttpServletResponse res){
		passengerService4Sec.dillZFBCharge4Org(req,res);
	}
	
	/**
	 * 个人用户获取机场
	 * @param usertoken
	 * @param city
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetAirportAddrt4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAirportAddrt(@RequestParam Map<String,Object> param){
		return passengerService4Sec.getAirportAddrt(param);
	}
	
	/**
	 * 个人出租车订单是否是即可单
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/IsUseNowOrder4OpTaxi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> isUseNowOrder4OpTaxi(@RequestBody Map<String,Object> param){
		return passengerService4Sec.isUseNowOrder4OpTaxi(param);
	}
	
	/**
	 * 获取消息列表
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetMessages4Sec", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getMessages4Sec(@RequestParam Map<String,Object> param){
		return passengerService4Sec.getMessages4Sec(param);
	}
	
	/**
	 * 获取当前时间
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetCurrentTime", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCurrentTime(@RequestParam Map<String,Object> params){
		try{
			String version = (String) params.get("version");
			//v3.0.1接口获取未支付的订单
			if(Const.INTERFACE_V3_0_1.equals(version)){
				return passengerService4Third.getCurrentTime(params);
			}
			return passengerService4Sec.getCurrentTime();
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	/**
	 * 出租车下单
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "Passenger/AddOder4OpTaxi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOder4OpTaxi(@RequestBody OpTaxiOrder taxiorder,HttpServletRequest request){
		return passengerService4Sec.addOder4OpTaxi(taxiorder,request);
	}
	
	/**
	 * 个人用户获取计价规则城市列表
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetValidCity4Op", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getValidCity4Op(){
		return passengerService4Sec.getValidCity4Op();
	}
	
	/**
	 * 获取司机位置信息
	 * @param usertoken
	 * @param driverid
	 * @return
	 */
	@RequestMapping(value = "Passenger/GetDriverPosition", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getDriverPosition(@RequestParam Map<String,Object> params){
		String usertoken = (String) params.get("usertoken");
		return passengerService4Sec.getDriverPosition(usertoken,params);
	}
}
