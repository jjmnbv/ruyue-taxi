package com.szyciov.touch.dao;

import com.szyciov.touch.dto.HelloWordUser;
import com.szyciov.touch.mapper.PubInfoMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Repository("PubInfoDao")
public class PubInfoDao {

	private PubInfoMapper pubInfomapper;

	@Resource
	public void setPubInfoMapper(PubInfoMapper pubInfomapper) {
		this.pubInfomapper = pubInfomapper;
	}

	public HelloWordUser getOrgUserInfo(String account) {
		return pubInfomapper.getOrgUserInfo(account);
	}

	public Map<String, Object> getChannelInfo(Map<String, Object> params) {
		return pubInfomapper.getChannelInfo(params);
	}

	public void addRequestRecord(Map<String, Object> record) {
		pubInfomapper.addRequestRecord(record);
	}

	public Map<String, Object> getCompanyByChannel(Map<String, Object> params) {
		return pubInfomapper.getCompanyByChannel(params);
	}

	public Map<String, Object> getOrgUserByChannel(Map<String, Object> params) {
		return pubInfomapper.getOrgUserByChannel(params);
	}
}
