package com.szyciov.operate.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubAlipayaccountHistory;
import com.szyciov.entity.PubWechataccountHistory;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.operate.mapper.OpInformationSetMapper;

@Repository("OpInformationSetDao")
public class OpInformationSetDao {
	public OpInformationSetDao() {
	}

	private OpInformationSetMapper mapper;

	@Resource
	public void setMapper(OpInformationSetMapper mapper) {
		this.mapper = mapper;
	}
	
    public OpPlatformInfo getOpPlatformInfo() {
    	return mapper.getOpPlatformInfo();
    }
    public OpPlatformInfo getCityName(OpPlatformInfo opPlatformInfo){
    	return mapper.getCityName(opPlatformInfo);
    }
    public int getOpPlatformInfoCount() {
    	return mapper.getOpPlatformInfoCount();
    }
	
	public void createOpPlatformInfo(OpPlatformInfo opPlatformInfo) {
		mapper.createOpPlatformInfo(opPlatformInfo);
	}
	
	public void updateOpPlatformInfo(OpPlatformInfo opPlatformInfo) {
		mapper.updateOpPlatformInfo(opPlatformInfo);
	}
	public void updateServcietel(OpPlatformInfo opPlatformInfo) {
		 mapper.updateServcietel(opPlatformInfo);
	}
	public void createServcietel(OpPlatformInfo opPlatformInfo) {
		mapper.createServcietel(opPlatformInfo);
	}
	public void insertLeLeasescompany(OpPlatformInfo opPlatformInfo) {
		mapper.insertLeLeasescompany(opPlatformInfo);
	}
	public void updateLeLeasescompany(OpPlatformInfo opPlatformInfo) {
		mapper.updateLeLeasescompany(opPlatformInfo);
	}
	public int getLeLeasescompany(String id) {
		return mapper.getLeLeasescompany(id);
	}
	
	public void editOpPlatformInfo(OpPlatformInfo object) {
		mapper.editOpPlatformInfo(object);
	}
	
	public void insertWechatAccountHistory(PubWechataccountHistory object) {
		mapper.insertWechatAccountHistory(object);
	}
	
	public void insertAlipayAccountHistory(PubAlipayaccountHistory object) {
		mapper.insertAlipayAccountHistory(object);
	}
	public String getCityId(String city) {
    	return mapper.getCityId(city);
    }
		
}
