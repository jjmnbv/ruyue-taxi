package com.ry.taxi.Util;

import org.json.JSONObject;
import org.springframework.http.HttpMethod;

import com.szyciov.entity.PubDriver;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

public class AddressUitl {
	
	
	public static final TemplateHelper templateHelper = new TemplateHelper();
	
	public static ThreadLocal<PubDriver> driver = new ThreadLocal<PubDriver>();
	
	public static final String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
	
	
	public static JSONObject getAddress(String token){
		//解析司机当前地址
		BaiduApiQueryParam baqp = new BaiduApiQueryParam();
		baqp.setOrderStartLat(driver.get().getLat());
		baqp.setOrderStartLng(driver.get().getLng());
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
			carserviceApiUrl + "/BaiduApi/GetAddress", 
			HttpMethod.POST, 
			token, 
			baqp, 
			JSONObject.class
		);
		return result;
	}

}
