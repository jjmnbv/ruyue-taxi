package com.szyciov.operate.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.operate.dao.PubDictionaryDao;

@Service("PubDictionaryService")
public class PubDictionaryService {
	
	private PubDictionaryDao dao;
	@Resource(name = "PubDictionaryDao")
	public void setDao(PubDictionaryDao dao) {
		this.dao = dao;
	}
	
	public List<PubDictionary> getPubDictionaryByList(PubDictionary object) {
		return dao.getPubDictionaryByList(object);
	}

}
