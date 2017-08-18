package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;

/**
 * 
 * @ClassName SendSMSParam 
 * @author Efy Shu
 * @Description 发送短信参数类 
 * @date 2016年10月9日 上午9:29:17
 */
public class SendSMSParam extends BaseParam {
	/**
	 * 0-登录;1-修改密码;2-忘记密码
	 */
	@SzycValid(rules={"checkNull","checkSMSType"})
	private String type;
	/**
	 * 手机号
	 */
	@SzycValid(rules={"checkNull","isMobile","checkUserCanLogin"})
	private String mobile;
	
	/**
	 * 用户类型(0-机构用户，1-个人用户，2-司机，3-租赁，4-机构端)
	 */
	private String usertype;
	
	/**
	 * 获取验证码次数校验标识
	 */
	@SzycValid(rules={"checkSendCodeTimes"})
	private boolean sendCodeTimesSign;
	
	/**  
	 * 获取用户类型(0-机构用户，1-个人用户，2-司机，3-租赁，4-机构端)  
	 * @return usertype 用户类型(0-机构用户，1-个人用户，2-司机，3-租赁，4-机构端)  
	 */
	public String getUsertype() {
		return usertype;
	}
	

	/**  
	 * 设置用户类型(0-机构用户，1-个人用户，2-司机，3-租赁，4-机构端)  
	 * @param usertype 用户类型(0-机构用户，1-个人用户，2-司机，3-租赁，4-机构端)  
	 */
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	/**  
	 * 获取0-登录;1-修改密码;2-忘记密码  
	 * @return type 0-登录;1-修改密码;2-忘记密码  
	 */
	public String getType() {
		return type;
	}

	/**  
	 * 设置0-登录;1-修改密码;2-忘记密码  
	 * @param type 0-登录;1-修改密码;2-忘记密码  
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**  
	 * 获取手机号  
	 * @return mobile 手机号  
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**  
	 * 设置手机号  
	 * @param mobile 手机号  
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**  
	 * 获取获取验证码次数校验标识  
	 * @return sendCodeTimesSign 获取验证码次数校验标识  
	 */
	public boolean isSendCodeTimesSign() {
		return sendCodeTimesSign;
	}
	
	/**  
	 * 设置获取验证码次数校验标识  
	 * @param sendCodeTimesSign 获取验证码次数校验标识  
	 */
	public void setSendCodeTimesSign(boolean sendCodeTimesSign) {
		this.sendCodeTimesSign = sendCodeTimesSign;
	}
	
}
