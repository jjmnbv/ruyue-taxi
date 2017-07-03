package com.szyciov.driver.service;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public class TokenService {
	public static <T> T getAjaxData(String jsonKey,HttpServletRequest request) throws IOException{
		int length = request.getContentLength();
		if("POST".equals(request.getMethod())){
			BufferedInputStream bi = new BufferedInputStream(request.getInputStream());
			byte[] b = new byte[length];
			int bLen = bi.read(b);
			String content = new String(b, 0, bLen, "UTF-8");
			content = content.replace("\n", "");
			JSONObject cJson = JSONObject.fromObject(content);
			System.out.println(cJson.get(jsonKey));
			return (T)cJson.get(jsonKey);
		}else{
			String queryStr = request.getQueryString();
			System.out.println(queryStr);
			return (T)queryStr;
		}
	}
}
