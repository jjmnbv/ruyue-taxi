package com.szyciov.operate.service;

import com.szyciov.lease.entity.PubCityAddr;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

@Service("PubInfoService")
public class PubInfoService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public JSONObject getCitySelect1(String userToken) {
		return templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl") + "/PubInfoApi/GetCitySelect1", HttpMethod.POST,
				userToken, null, JSONObject.class);
	}

	public JSONObject getCitySelect2(String userToken) {
		return templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl") + "/PubInfoApi/GetCitySelect2", HttpMethod.POST,
				userToken, null, JSONObject.class);
	}

	/**
	 * 获取城市ID
	 * @param userToken
	 * @param name
	 * @return
	 */
	public Map<String,String> getCityIdByName(String userToken,String name) {
		return templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl") + "/PubInfoApi/GetCityIdByName/{name}", HttpMethod.GET,
				userToken, null, Map.class,name);
	}

	/**
	 * 城市查询控件
	 * @param userToken
	 * @param pubCityAddr
	 * @return
	 */
	public List<Map<String,String>> getSearchCitySelect(String userToken, PubCityAddr pubCityAddr){
		return templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl") + "/PubInfoApi/GetSearchCitySelect", HttpMethod.POST,
				userToken, pubCityAddr, List.class);
	}
}
