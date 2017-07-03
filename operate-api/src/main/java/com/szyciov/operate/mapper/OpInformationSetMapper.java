package com.szyciov.operate.mapper;


import com.szyciov.entity.PubAlipayaccountHistory;
import com.szyciov.entity.PubWechataccountHistory;
import com.szyciov.op.entity.OpPlatformInfo;

public interface OpInformationSetMapper {
	
	OpPlatformInfo getOpPlatformInfo();
	OpPlatformInfo getCityName(OpPlatformInfo opPlatformInfo);
	int getOpPlatformInfoCount();
	
	void createOpPlatformInfo(OpPlatformInfo opPlatformInfo);
	
	void updateOpPlatformInfo(OpPlatformInfo opPlatformInfo);
	void updateServcietel(OpPlatformInfo opPlatformInfo);
	void createServcietel(OpPlatformInfo opPlatformInfo);
	void insertLeLeasescompany(OpPlatformInfo opPlatformInfo);
	void updateLeLeasescompany(OpPlatformInfo opPlatformInfo);
	int getLeLeasescompany(String id);
	
	void editOpPlatformInfo(OpPlatformInfo object);
	
	void insertWechatAccountHistory(PubWechataccountHistory object);
	
	void insertAlipayAccountHistory(PubAlipayaccountHistory object);
	
	String getCityId(String city);

	void insertDriverWechatAccountHistory(PubWechataccountHistory object);

	void insertDriverAlipayAccountHistory(PubAlipayaccountHistory object);

	void updateDriverOpPlatformInfo(OpPlatformInfo opPlatformInfo);
}
