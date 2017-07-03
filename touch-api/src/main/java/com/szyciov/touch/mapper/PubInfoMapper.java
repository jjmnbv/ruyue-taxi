package com.szyciov.touch.mapper;

import com.szyciov.touch.dto.HelloWordUser;

import java.util.Map;

public interface PubInfoMapper {

	HelloWordUser getOrgUserInfo(String account);

	Map<String, Object> getChannelInfo(Map<String, Object> params);

	void addRequestRecord(Map<String, Object> record);

	Map<String, Object> getCompanyByChannel(Map<String, Object> params);

	Map<String, Object> getOrgUserByChannel(Map<String, Object> params);

}
