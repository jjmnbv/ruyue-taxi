package com.szyciov.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;

public class UserTokenManager {
	
	/**
	 * 机构app用户
	 */
	public static final String ORGUSERTYPE = "0";
	
	/**
	 * 个人用户
	 */
	public static final String PERSONNALUSER = "1";
	
	/**
	 * 司机
	 */
	public static final String DRIVERUSER = "2";
	
	/**
	 * 租赁用户
	 */
	public static final String LEASEUSER = "3";
	
	/**
	 * 机构web用户
	 */
	public static final String ORGWEB = "4";
	
	/**
	 * 运管web用户
	 */
	public static final String OPWEB = "5";

	/**
	 * 请求carserviceapi
	 */
	private static TemplateHelper4CarServiceApi carserviceapi = new TemplateHelper4CarServiceApi();
	
	/**
	 * 从usertoken中获取用户名
	 * 有验证用户token的有效性
	 * @param userToken
	 * @return
	 * @throws ParseException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getUserNameFromToken(String userToken,String usertype) throws ParseException, NoSuchAlgorithmException {
		if (!validateUserTicket(userToken,usertype)) {
			return null;
		}
		// 解码Base64编码
		String decodeString = DecodeFromBase64(userToken);
		String logonName = decodeString.substring(50);
		return logonName;
	}
	
	/**
	 * 根据usertoken获取用户类型
	 * 没有验证用户类型有效性
	 * @param userToken
	 * @return
	 * @throws ParseException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static String getUserTypeFromToken(String userToken) throws NoSuchAlgorithmException, ParseException{
		// 解码Base64编码
		String decodeString = DecodeFromBase64(userToken);
		String usertype = decodeString.substring(49,50);
		return usertype;
	}
	
	/**
	 * 创建一个usertoken，主要用于用户权限有限验证
	 * web端存放在session中，app端的每次请求都传递
	 * @param usertype
	 * @param loginName
	 * @param securityKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String createUserToken(String usertype,String loginName, String securityKey) throws NoSuchAlgorithmException{
		// 1.构造加密前格式：登录名(logonName)+密钥(securityKey)
		// 2.用MD5Hash
		// 3.构造编码格式：hash值（32位）+ 登录时间（loginTime,yyyyMMddHHmmss,17位）+usertype(用户类型,1位)+登录名
		// 4.用Base64编码
 		String rawText = loginName + securityKey;
		byte[] inputBytes = rawText.getBytes();
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		mdInst.update(inputBytes);
		byte[] md = mdInst.digest();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");// 可以精确到时分秒
		String loginTime = df.format(new Date().getTime());
		
		String mdStr = md5THexString(md)+ loginTime + usertype + loginName;
		return Base64.encodeBase64String(mdStr.getBytes());
	}
	
	/**
	 * usertoken的有效性
	 * @param userToken
	 * @return
	 * @throws ParseException
	 */
	public static boolean validateUserTicket(String userToken,String usertype) throws ParseException, NoSuchAlgorithmException {
		if (StringUtils.isBlank(userToken)) {
			return false;
		}
		// 解码Base64编码
		String decodeString = DecodeFromBase64(userToken);

		// 验证登录名是否有效
		if (!IsLogonNameValid(decodeString, SystemConfig.getSystemProperty("securityKey"))) {
			return false;
		}
		
		//验证用户类型是否一样
		String cusertype = decodeString.substring(49,50);
		if(!cusertype.equalsIgnoreCase(usertype)){
			return false;
		}
		
		//验证用户token,是否是被其他人登录更新了单独验证
		return true;
	}
	
	
	/**
	 * 根据用户类型和用户名判断用户usertoken是否有效
	 * @param usertype
	 * @param loginname
	 * @param usertoken
	 * @return
	 */
	public static boolean validUserToken4Db(String usertype,String usertoken){
		//（根据usertype和loginName判断usertoken是否已失效）
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("usertype", usertype);
		params.put("usertoken", usertoken);
		Map<String,Object> info = carserviceapi.dealRequestWithToken("/PubInfoApi/CheckTokenValid", HttpMethod.POST, null,params,Map.class);
		if(info==null){
			return false;
		}
		boolean flag = (boolean) info.get("flag");
		return flag;
	}
	
	/**
	 * 解码Base64编码
	 * @param value
	 * @return
	 */
	private static String DecodeFromBase64(String value) {
		byte[] bytes = Base64.decodeBase64(value);
		return new String(bytes);
	}

	/**
	 * 校验用户名是否合法
	 */
	private static Boolean IsLogonNameValid(String decodeString, String securityKey) throws NoSuchAlgorithmException {
		String md5Result = decodeString.substring(0, 32);
		String logonName = decodeString.substring(50);
		String rawText = logonName + securityKey;
		byte[] inputBytes = rawText.getBytes();
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		mdInst.update(inputBytes);
		byte[] md = mdInst.digest();
		return md5Result.equals(md5THexString(md));
	}

	private static String md5THexString(byte[] md) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < md.length; i++) {
			int v = md[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 获取登录时间
	 * @param userToken
	 * @return
	 */
	public static Date getLoginTimeFromToken(String userToken) throws Exception {
		String decodeString = DecodeFromBase64(userToken);
		String logintime = decodeString.substring(32,49);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");// 可以精确到时分秒
		return df.parse(logintime);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, Exception{
		System.out.println(createUserToken(UserTokenManager.DRIVERUSER,"zhu", "").length());
		System.out.println(createUserToken(UserTokenManager.DRIVERUSER,"zhu", ""));
		System.out.println(createUserToken(UserTokenManager.DRIVERUSER,"zhu", ""));
		System.out.println(createUserToken(UserTokenManager.DRIVERUSER,"zhu", ""));
		System.out.println(createUserToken(UserTokenManager.DRIVERUSER,"zhu", ""));
		System.out.println(getLoginTimeFromToken(createUserToken(UserTokenManager.DRIVERUSER,"zhu", "")));
		System.out.println(getUserTypeFromToken(createUserToken(UserTokenManager.DRIVERUSER,"zhu", "")));
	}
}
