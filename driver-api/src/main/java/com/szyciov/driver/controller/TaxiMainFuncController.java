package com.szyciov.driver.controller;


import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.driver.enums.PurseEnum;
import com.szyciov.driver.param.DriverMessageParam;
import com.szyciov.driver.param.PurseParam;
import com.szyciov.driver.param.UploadOrdinatesParam;
import com.szyciov.driver.service.TaxiMainFuncService;
import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONObject;



/**
  * @ClassName TaxiMainFuncController
  * @author Efy Shu
  * @Description 出租车侧边栏功能Controller
  * @date 2017年3月23日 09:34:23
  */ 
@Controller("TaxiMainFuncController")
public class TaxiMainFuncController extends ApiExceptionHandle{

	/**
	  *依赖
	  */
	private TaxiMainFuncService taximainfuncservice;

	/**
	  *依赖注入
	  */
	@Resource(name="TaxiMainFuncService")
	public void setTaxiMainFuncService(TaxiMainFuncService taximainfuncservice){
		this.taximainfuncservice=taximainfuncservice;
	}

	/**
	 * 出租车一键报警
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiCallPolice")
	public JSONObject callPolice(@RequestBody UploadOrdinatesParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taximainfuncservice.callPolice(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 我的钱包
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/MyPurse")
	public JSONObject myPurse(@RequestBody PurseParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taximainfuncservice.myPurse(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 余额明细
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/BalanceDetail")
	public JSONObject balanceDetail(@RequestBody PurseParam param){
		starttime.set(System.currentTimeMillis());
		//只查余额
		param.setDetailtype(PurseEnum.DETAILTYPE_BALANCE.code);
		JSONObject result = taximainfuncservice.purseDetail(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 交易明细
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TransDetail")
	public JSONObject transDetail(@RequestBody PurseParam param){
		starttime.set(System.currentTimeMillis());
		//查所有明细
		param.setDetailtype(PurseEnum.DETAILTYPE_ALLTRANS.code);
		JSONObject result = taximainfuncservice.purseDetail(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 校验银行卡信息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/CheckBankCard")
	public JSONObject checkBankCard(@RequestBody PurseParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taximainfuncservice.checkBankCard(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 充值
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/Deposit")
	public JSONObject recharge(@RequestBody PurseParam param,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		//保存ip地址
		param.setIpaddr(request.getRemoteAddr());
		JSONObject result = taximainfuncservice.recharge(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 提现
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/Enchashment")
	public JSONObject enchashment(@RequestBody PurseParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taximainfuncservice.enchashment(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}

    /**
     * 进入提现前的检核
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="Driver/CheckEnchashment")
    public JSONObject checkEnchashment(@RequestBody PurseParam param){
        starttime.set(System.currentTimeMillis());
        JSONObject result = taximainfuncservice.ckeckEnchashment(param);
        releaseResource(taximainfuncservice);
        return checkResult(result);
    }

    /**
	 * 出租车我的贡献
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiOrderStatistics")
	public JSONObject taxiOrderStatistics(@RequestBody PurseParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taximainfuncservice.taxiOrderStatistics(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 获取银行卡信息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetBankInfo")
	public JSONObject getBankInfo(@RequestBody PurseParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taximainfuncservice.getBankInfo(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 添加银行卡(已废弃)
	 * @deprecated
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/AddBankCard")
	public JSONObject addBankCard(@RequestBody PurseParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taximainfuncservice.addBankCard(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 获取支付渠道
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetPayMethods")
	public JSONObject getPayMethods(@RequestBody PurseParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taximainfuncservice.getPayMethods(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 出租车获取消息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiPollMessage")
	public JSONObject taxiPollMessage(@RequestBody DriverMessageParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taximainfuncservice.taxiPollMessage(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 出租车重置密码(目前只能重置提现密码)
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiResetPassword")
	public JSONObject taxiResetPassword(@RequestBody PurseParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taximainfuncservice.taxiResetPassword(param);
		releaseResource(taximainfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 微信支付回调
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/WeChatCallBack")
	public void weChatCallback(HttpServletRequest request,HttpServletResponse response) throws IOException{
		taximainfuncservice.weChatCallback(request, response);
	}
	
	/**
	 * 支付宝支付回调
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/AliPayCallBack")
	public void aliPayCallback(HttpServletRequest request,HttpServletResponse response) throws IOException{
		taximainfuncservice.aliPayCallback(request, response);
	}
}
