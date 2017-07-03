package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.Dictionary;
import com.szyciov.lease.mapper.DictionaryMapper;
import com.szyciov.param.QueryParam;

@Repository("DictionaryDao")
public class DictionaryDao {
	public DictionaryDao() {
	}

	private DictionaryMapper mapper;

	@Resource
	public void setMapper(DictionaryMapper mapper) {
		this.mapper = mapper;
	}

	public List<Dictionary> getDictionaryByType(String loginName) {
		return mapper.getDictionaryByType(loginName);
	}
	
	public List<Dictionary> getDictionaryByTypeText(Map<String, String> map) {
		return mapper.getDictionaryByTypeText(map);
	}

	// public List<Dictionary> getDictionaryByQuery(Map<String, String> map) {
	// return mapper.getDictionaryByQuery(map);
	// }

	public List<Dictionary> getDictionaryListByQuery(QueryParam queryParam) {
		return mapper.getDictionaryListByQuery(queryParam);
	}

	public int getDictionaryListCountByQuery(QueryParam queryParam) {
		return mapper.getDictionaryListCountByQuery(queryParam);
	}

	public void deleteDictionary(String id) {
		mapper.deleteDictionary(id);
	}

	public Dictionary getById(String id) {
		return mapper.getById(id);
	}

	@SuppressWarnings("rawtypes")
	public void createDictionary(Dictionary dictionary) {
		mapper.createDictionary(dictionary);
	}

	public int getDictionaryListCountByEqualQuery(Dictionary dictionary) {
		return mapper.getDictionaryListCountByEqualQuery(dictionary);
	}
	
	@SuppressWarnings("rawtypes")
	public void updateDictionary(Dictionary dictionary) {
		mapper.updateDictionary(dictionary);
	}
}
