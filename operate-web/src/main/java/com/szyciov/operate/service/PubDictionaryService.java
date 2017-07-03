package com.szyciov.operate.service;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.util.TemplateHelper;

@Service("PubDictionaryService")
public class PubDictionaryService {

	private TemplateHelper templateHelper = new TemplateHelper();

	@SuppressWarnings("unchecked")
	public List<PubDictionary> getPubDictionary(String type, String userToken) {
		PubDictionary dictionary = new PubDictionary();
		dictionary.setStatus("1");
		dictionary.setType(type);
		return templateHelper.dealRequestWithToken("/PubInfo/GetPubDictionaryByList", HttpMethod.POST, userToken, dictionary, List.class);
	}

}
