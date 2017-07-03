package com.szyciov.lease.service;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Service("PubInfoService")
public class PubInfoService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public JSONObject getCitySelect1(String userToken) {
		return templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl") + "/PubInfoApi/GetCitySelect1", HttpMethod.POST,
				userToken, null, JSONObject.class);
	}
	
}
