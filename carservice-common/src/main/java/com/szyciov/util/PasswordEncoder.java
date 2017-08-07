package com.szyciov.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class PasswordEncoder {

	public static String encode(CharSequence rawPassword) {
		// TODO Auto-generated method stub
		byte[] inputBytes = ((String) rawPassword).getBytes();
		String result = null;
		MessageDigest mdInst;
		try {
			mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(inputBytes);
			// 获得密文
			byte[] md = mdInst.digest();
			result = Base64.encodeBase64String(md);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(encodedPassword)){
			return false;
		}
		String password = encode(rawPassword);
		return password.contentEquals(encodedPassword);
	}

	public static boolean matches_PWD(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(encodedPassword)){
			return false;
		}
		String password = (String) rawPassword;
		return password.contentEquals(encodedPassword);
	}

}
