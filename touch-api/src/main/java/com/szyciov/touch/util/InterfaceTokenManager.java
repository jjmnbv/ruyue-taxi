package com.szyciov.touch.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * 接口令牌token管理
 * @author zhu
 *
 */
public class InterfaceTokenManager {
	
	/**
	 * 创建一个token
	 * @param loginName
	 * @param securityKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String createToken(String channelkey, String securityKey) throws NoSuchAlgorithmException{
		// 1.构造加密前格式：渠道(channelkey)+密钥(securityKey)
		// 2.用MD5Hash
		// 3.构造编码格式：hash值（32位）+ 授权时间（loginTime,yyyyMMddHHmmss,17位）+channelkey
		// 4.用Base64编码
 		String rawText = channelkey + securityKey;
		byte[] inputBytes = rawText.getBytes();
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		mdInst.update(inputBytes);
		byte[] md = mdInst.digest();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");// 可以精确到时分秒
		String loginTime = df.format(new Date().getTime());
		
		String mdStr = md5THexString(md)+ loginTime + channelkey;
		return Base64.encodeBase64String(mdStr.getBytes());
	}
	
	/**
	 * 从token中获取channelkey
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getChannelKeyFromToken(String token) throws ParseException, NoSuchAlgorithmException {
		//解码Base64编码
		String decodeString = DecodeFromBase64(token);
		if(StringUtils.isBlank(decodeString)||decodeString.length()<=49){
			return null;
		}
		String channelkey = decodeString.substring(49);
		return channelkey;
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
}
