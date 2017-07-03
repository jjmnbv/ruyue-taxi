package com.szyciov.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

public class CommonUtils {

	/**
	 * 检查candiate是否在whiteList当中，如果是，返回true
	 */
	public static boolean checkWhiteList(String candiate, String whiteList) {
		List<String> list = Arrays.asList(whiteList.split(","));
		return list.contains(candiate);
	}

	public static String getCookieByName(Cookie[] cookies, String key) {
		String value = "";
		if (cookies != null) {
			for (Cookie cs : cookies) {
				if (StringUtils.equalsIgnoreCase(cs.getName(), key)) {
					value = cs.getValue();
					break;
				}
			}
		}
		return value;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getParameterMap(Map properties) {
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				// for (int i = 0; i < values.length; i++) {
				// value = values[i] + ",";
				// }
				// value = value.substring(0, value.length() - 1);
				value = values[0];
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	public static Map<String, String> getOperationByURL(String url) {
		System.out.println(url);
		url = StringUtils.stripStart(url, "/");
		String[] arr = url.split("/");
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("action", arr[2]);
		ret.put("controller", arr[1]);
		return ret;
	}
	
	/** 
	 * <p>api url解析controller和action</p>
	 *
	 * @param url
	 * @return
	 */
	public static Map<String, String> getApiOperationByURL(String url) {
		System.out.println(url);
		url = StringUtils.stripStart(url, "/");
		String[] arr = url.split("/");
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("action", arr[3]);
		ret.put("controller", arr[2]);
		return ret;
	}

}
