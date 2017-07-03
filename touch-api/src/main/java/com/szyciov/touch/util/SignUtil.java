package com.szyciov.touch.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.szyciov.util.MD5;

/**
 * 标准化接口请求验签的接口
 * @author zhu
 *
 */
public class SignUtil {
	
	/**
	 * 构造加密获取sign前的字符串
	 * @param map
	 * @return
	 */
	public static String buildParamStr(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, true));

		return sb.toString();
	}
	
	/**
	 * 通过securitykey加密tagstr获取sign
	 * @param tagstr
	 * @param securitykey
	 * @return
	 */
	public static String getSignStr(String tagstr,String securitykey){
		return MD5.MD5Encode(tagstr+"&"+securitykey);
	}
	
	/**
	 * 拼接键值对
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}

}
