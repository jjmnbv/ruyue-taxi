package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.entity.PubSysVersion;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.param.PubSysversionQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Service("PubSysversionService")
public class PubSysversionService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@SuppressWarnings("unchecked")
	public List<PubDictionary> getSystemtypeByList(String userToken) {
		return templateHelper.dealRequestWithToken("/PubSysversion/GetSystemtypeByList", HttpMethod.POST, userToken, null, List.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<PubSysVersion> getPubSysversionList(PubSysVersion object, String userToken) {
		return templateHelper.dealRequestWithToken("/PubSysversion/GetPubSysversionList", HttpMethod.POST, userToken, object, List.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getCurversionByList(PubSysVersion object, String userToken) {
		return templateHelper.dealRequestWithToken("/PubSysversion/GetCurversionByList", HttpMethod.POST, userToken, object, List.class);
	}
	
	public PageBean getPubSysversionByQuery(PubSysversionQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/PubSysversion/GetPubSysversionByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<PubSysVersion> getPubSysversionById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/PubSysversion/GetPubSysversionById/{id}", HttpMethod.GET, userToken, null, List.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> createPubSysversion(PubSysVersion object, String userToken) {
		return templateHelper.dealRequestWithToken("/PubSysversion/CreatePubSysversion", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> editPubSysversion(PubSysVersion object, String userToken) {
		return templateHelper.dealRequestWithToken("/PubSysversion/EditPubSysversion", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> deletePubSysversion(PubSysVersion object, String userToken) {
		return templateHelper.dealRequestWithToken("/PubSysversion/DeletePubSysversion", HttpMethod.POST, userToken, object, Map.class);
	}
	
}
