package com.szyciov.driver.controller;

import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.driver.param.AtworkParam;
import com.szyciov.driver.param.BaseParam;
import com.szyciov.driver.param.ChangeDriverIconParam;
import com.szyciov.driver.param.ChangePasswordParam;
import com.szyciov.driver.param.GetCommonInfoParam;
import com.szyciov.driver.param.LoginParam;
import com.szyciov.driver.param.LogoutParam;
import com.szyciov.driver.param.SendSMSParam;
import com.szyciov.driver.param.UploadOrdinatesParam;
import com.szyciov.driver.service.AccountService;
import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONObject;

/**
 * 账号信息控制器
 * @ClassName AccountController 
 * @author Efy Shu
 * @Description 处理账号相关的请求 
 * @date 2016年8月24日 上午9:19:47
 */
@Controller
public class AccountController extends ApiExceptionHandle {
	private AccountService as;
	
	@Resource(name="AccountService")
	public void setAs(AccountService as){
		this.as = as;
	}
	
	public AccountController() {
		
	}
	
	//=====================================================================//
	/**
	 * 登入
	 * @param param
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	@ResponseBody
	@RequestMapping(value="Driver/Login")
	public JSONObject login(@RequestBody LoginParam param,HttpServletRequest request) throws NoSuchAlgorithmException{
		starttime.set(System.currentTimeMillis());
		param.setIpaddr(request.getRemoteAddr());
		JSONObject result = as.login(param);
		releaseResource(as);
		return checkResult(result);
	}

	/**
	 * 登出
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/Logout")
	public JSONObject logout(@RequestBody LogoutParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.logout(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 上/下班
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/AtWork")
	public JSONObject atwork(@RequestBody AtworkParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.atwork(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 发送短信验证码
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/SendSMS")
	public JSONObject sendsms(@RequestBody SendSMSParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.sendSMS(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 获取司机个人信息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetDriverInfo")
	public JSONObject getDriverInfo(@RequestBody BaseParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.getDriverInfo(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 更换司机头像
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/ChangeDriverIcon")
	public JSONObject changeDriverIcon(@RequestBody ChangeDriverIconParam param,HttpServletRequest request){
//		JSONObject result = new JSONObject();
//		try {
//			BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
//			BufferedImage bi = ImageIO.read(bis);
//			String realPath = request.getSession().getServletContext().getRealPath("/upload/icon/");
//			File file = new File(realPath);
//			if(!file.exists())	file.mkdirs();
//			String account = UserTokenManager.getUserNameFromToken(token, UserTokenManager.DRIVERUSER);
//			file = new File(realPath+account+".png");
//			ImageIO.write(bi, "png", file);
//			json.put("status", Retcode.OK.code);
//			json.put("message", Retcode.OK.msg);
//		} catch (Exception e) {
//			json.put("status", Retcode.EXCEPTION.code);
//			json.put("message", Retcode.EXCEPTION.msg);
//		}
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.changeDriverIcon(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 更换密码
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/ChangePassword")
	public JSONObject changePassword(@RequestBody ChangePasswordParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.changePassword(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 验证原始密码
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/AuthOldPass")
	public JSONObject authOldPass(@RequestBody ChangePasswordParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.authOldPass(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 上传坐标
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/UploadOrdinates")
	public JSONObject uploadOrdinates(@RequestBody UploadOrdinatesParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.uploadOrdinates(param);
		releaseResource(as);
		return checkResult(result);
	}

	/**
	 * 检查版本信息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/CheckAPPVersion")
	public JSONObject checkAPPVersion(@RequestBody GetCommonInfoParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.checkAPPVersion(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 获取广告页
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetADPage")
	public JSONObject getADPage(@RequestBody GetCommonInfoParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.getADPage(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 获取引导页
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetGuidePage")
	public JSONObject getGuidePage(@RequestBody GetCommonInfoParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.getGuidePage(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 一键报警
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/CallPolice")
	public JSONObject callPolice(@RequestBody UploadOrdinatesParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.callPolice(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 保存极光推送ID
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/SaveRegID")
	public JSONObject saveRegID(@RequestBody LoginParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.saveRegID(param);
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 删除极光推送ID
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/RemoveOBD")
	public JSONObject removeOBD(){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.removeOBD();
		releaseResource(as);
		return checkResult(result);
	}
	
	/**
	 * 校验短信验证码
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/AuthSMSCode")
	public JSONObject authSMSCode(@RequestBody LoginParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = as.authSMSCode(param);
		releaseResource(as);
		return checkResult(result);
	}
}
