package com.szyciov.lease.service;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.param.OrderStatisticsQueryParam;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

@Service("HomePageService")
public class HomePageService {
	private TemplateHelper templateHelper = new TemplateHelper();

	@SuppressWarnings("unchecked")
	public Map<String, Object> getHomeInfo(String companyid, String userToken) {
		leIndexOrderStatistics(userToken);
		return templateHelper.dealRequestWithToken("/Home/Info/{companyid}", HttpMethod.GET, userToken, null,Map.class, companyid);
	}
	
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> leIndexOrderStatistics(String userToken) {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		return templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl + "/OrderStatistics/LeIndexOrderStatistics", HttpMethod.POST, userToken, 
				queryParam, Map.class);
	}
}
