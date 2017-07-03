package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.entity.PubAdImage;
import com.szyciov.param.PubAdimageQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Service("PubAdimageService")
public class PubAdimageService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public PageBean getPubAdimageByQuery(PubAdimageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/PubAdimage/GetPubAdimageByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> createPubAdimage(PubAdImage object, String userToken) {
		return templateHelper.dealRequestWithToken("/PubAdimage/CreatePubAdimage", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> editPubAdimage(PubAdImage object, String userToken) {
		return templateHelper.dealRequestWithToken("/PubAdimage/EditPubAdimage", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<PubAdImage> getPubAdimageList(PubAdImage object, String userToken) {
		return templateHelper.dealRequestWithToken("/PubAdimage/GetPubAdimageList", HttpMethod.POST, userToken, object, List.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> editPubAdviseToStart(PubAdImage object, String userToken) {
		return templateHelper.dealRequestWithToken("/PubAdimage/EditPubAdviseToStart", HttpMethod.POST, userToken, object, Map.class);
	}
	
}
