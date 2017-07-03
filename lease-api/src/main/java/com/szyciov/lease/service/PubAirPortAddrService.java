package com.szyciov.lease.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.entity.PubAirPortAddr;
import com.szyciov.lease.dao.PubAirPortAddrDao;

@Service("PubAirPortAddrService")
public class PubAirPortAddrService {
	
	private PubAirPortAddrDao dao;

	@Resource(name = "PubAirPortAddrDao")
	public void setDao(PubAirPortAddrDao dao) {
		this.dao = dao;
	}
	
	public List<PubAirPortAddr> getAirPortAddreList(String city){
		return dao.getPubAirPortAddrList(city);
	}
}
