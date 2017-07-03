package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.Dictionary;
import com.szyciov.param.QueryParam;

public interface DictionaryMapper {
	@SuppressWarnings("rawtypes")
	void createCompany(Map map);

	List<Dictionary> getDictionaryByType(String queryType);
	
	List<Dictionary> getDictionaryByTypeText(Map<String, String> map);

	List<Dictionary> getDictionaryByQuery(Map<String, String> map);

	List<Dictionary> getDictionaryListByQuery(QueryParam queryParam);

	int getDictionaryListCountByQuery(QueryParam queryParam);

	int getDictionaryListCountByEqualQuery(Dictionary dictionary);

	void deleteDictionary(String id);

	Dictionary getById(String id);

	void createDictionary(Dictionary dictionary);

	void updateDictionary(Dictionary dictionary);
}