package com.szyciov.passenger;

import java.util.HashMap;
import java.util.Map;

import com.szyciov.util.UserTokenManager;

public class Const {
	/**
	 * app端广告页的type
	 */
	public static final String APPADTYPE = "广告页";
	
	/**
	 * app端乘客端广告也的value
	 */
	public static final String APPADVALUE = "apppassenger";
	
	/**
	 * 机构用户
	 */
	public static final String ORGUSER = "0";
	
	/**
	 * 个人用户
	 */
	public static final String PEUSER = "1";
	
	/**
	 * 密码登录
	 */
	public static final String PWDLOGIN = "1";
	
	/**
	 * 验证码登录
	 */
	public static final String CODELOGIN = "2";
	
	/**
	 * 登录状态日志标志
	 */
	public static final String LOGINSTATUS_OK = "0";
	public static final String LOGINSTATUS_FAIL = "1";
	public static final String LOGINSTATUS_ERROR = "2";
	
	/**
	 * 用户类型
	 */
	public static final String USERTOKENTYPE_ORGUSER = "0";
	public static final String USERTOKENTYPE_PEUSER = "1";
	public static final String USERTOKENTYPE_DRIVER = "2";
	public static final String USERTOKENTYPE_LEASE = "3";
	public static final String USERTOKENTYPE_ORG = "4";
	
	/**
	 * 验证码类型
	 */
	public static final String SMSTYPE_LOGIN = "0";
	public static final String SMSTYPE_CHANGEPWD = "1";
	public static final String SMSTYPE_REGISTER = "2";
	
	/**
	 * 验证码过期时间
	 */
	public static final int SMSCODEVALITIME = 10;
	
	/**
	 * 对乘客不可见的订单状态
	 */
	public static final String USER_ORDERSTATUS_USERHIDDEN = "0";
	
	/**
	 * 用车类型，因公用车和因私用车
	 */
	public static final String USETYPE_PUBLIC = "0"; 
	public static final String USETYPE_PRIVATE = "1";
	
	//约车、接机、送机
	public static final String ORDERTYPE_YUECHE = "1";
	public static final String ORDERTYPE_JIEJI = "2";
	public static final String ORDERTYPE_SONGJI = "3";
	
	/**
	 * 用车事由的字典类型
	 */
	public static final String USERESON_TYPE = "用车事由";
	
	/**
	 * 用车事由的备注字典类型
	 */
	public static final String USEREMARK_TYPE = "用车备注";

	/**
	 * 接口v3.0.1
	 */
	public static final String INTERFACE_V3_0_1 = "v3.0.1";

	/**
	 * 机构订单类型
	 */
	public static final String ORDERTYPE_ORG = "0";

	/**
	 * 个人订单类型
	 */
	public static final String ORDERTYPE_PERSONAL = "1";
	
	/**
	 * 根据token获取用户信息
	 * @param usertoken
	 * @return
	 */
	public static Map<String,String> getUserInfo(String usertoken){
		String usertype = null;
		String account = null;
		try{
			usertype = UserTokenManager.getUserTypeFromToken(usertoken);
			account = UserTokenManager.getUserNameFromToken(usertoken, usertype);
		}catch(Exception e){}
		Map<String,String> userinfo = new HashMap<String,String>();
		userinfo.put("usertype", usertype);
		userinfo.put("account", account);
		return userinfo;
	}
}
