package com.szyciov.operate.service;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.entity.PubPersonagreement;
import com.szyciov.util.TemplateHelper;

@Service("PubPersonagreementService")
public class PubPersonagreementService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@SuppressWarnings("unchecked")
	public Map<String, String> editPubPersonagreement(PubPersonagreement object, String userToken) {
		return templateHelper.dealRequestWithToken("/PubPersonagreement/EditPubPersonagreement", HttpMethod.POST, userToken, object, Map.class);
	}
	
	public PubPersonagreement getPubPersonagreement(String userToken) {
		return templateHelper.dealRequestWithToken("/PubPersonagreement/GetPubPersonagreement", HttpMethod.POST, userToken, null, PubPersonagreement.class);
	}

}
