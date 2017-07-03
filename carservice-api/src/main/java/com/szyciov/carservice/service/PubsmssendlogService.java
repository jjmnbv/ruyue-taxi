package com.szyciov.carservice.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.PubsmssendlogDao;
import com.szyciov.entity.Pubsmssendlog;
@Service("pubsmssendlogService")
public class PubsmssendlogService {

	private PubsmssendlogDao pubsmssendlogDao;

	@Resource(name="pubsmssendlogDao")
	public void setPubsmssendlogDao(PubsmssendlogDao pubsmssendlogDao) {
		this.pubsmssendlogDao = pubsmssendlogDao;
	}
	public void insertPubsmssendlog(Pubsmssendlog pubsmssendlog){
		String[] phones = pubsmssendlog.getPhone().split(";");
		for(String phone : phones){
			pubsmssendlog.setPhone(phone);
			pubsmssendlogDao.insertPubsmssendlog(pubsmssendlog);
		}
	}



}
