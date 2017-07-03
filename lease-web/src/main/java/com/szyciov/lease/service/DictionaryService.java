package com.szyciov.lease.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.entity.Dictionary;
import com.szyciov.param.QueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Service("dictionaryService")
public class DictionaryService {
	private TemplateHelper templateHelper = new TemplateHelper();

	public PageBean getDictionaryByQuery(QueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> deleteDictionary(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/Dictionary/Delete/{id}", HttpMethod.DELETE, userToken, null,
				Map.class, id);
	}

	public Dictionary getById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/Dictionary/GetById/{id}", HttpMethod.GET, userToken, null,
				Dictionary.class, id);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> createDictionary(Dictionary dictionary, String userToken) {
		return templateHelper.dealRequestWithToken("/Dictionary/Create", HttpMethod.POST, userToken, dictionary,
				Map.class);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> updateDictionary(Dictionary dictionary, String userToken) {
		return templateHelper.dealRequestWithToken("/Dictionary/Update", HttpMethod.POST, userToken, dictionary,
				Map.class);
	}

	@SuppressWarnings("unchecked")
	public List<Dictionary> getDictionaryByType(Map<String, String> map) {
		String queryType = map.get("queryType");
		String userToken = map.get("userToken");
		return templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type={queryType}", HttpMethod.GET,
				userToken, null, List.class, queryType);
	}
}
