package com.szyciov.operate.service;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.util.TemplateHelper;

@Service("OpInformationSetService")
public class OpInformationSetService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public OpPlatformInfo getOpPlatformInfo(String userToken) {
		return templateHelper.dealRequestWithToken("/OpInformationSet/GetOpPlatformInfo", HttpMethod.GET, userToken, null, OpPlatformInfo.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> updateOpPlatformInfo(OpPlatformInfo object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpInformationSet/UpdateOpPlatformInfo", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> editOpPlatformInfo(OpPlatformInfo object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpInformationSet/EditOpPlatformInfo", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> updateAlipay(OpPlatformInfo object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpInformationSet/UpdateAlipay", HttpMethod.POST, userToken, object, Map.class);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> updateDriverAlipay(OpPlatformInfo object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpInformationSet/driver/UpdateAlipay", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> updateWechat(OpPlatformInfo object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpInformationSet/UpdateWechat", HttpMethod.POST, userToken, object, Map.class);
	}


    @SuppressWarnings("unchecked")
    public Map<String, String> updateDriverWechat(OpPlatformInfo object, String userToken) {
        return templateHelper.dealRequestWithToken("/OpInformationSet/driver/UpdateWechat", HttpMethod.POST, userToken, object, Map.class);
    }
}
