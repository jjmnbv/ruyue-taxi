package com.szyciov.passenger.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.passenger.entity.AirportAddr;
import com.szyciov.passenger.entity.SysVersion;

public interface DictionaryMapper {
	
	Dictionary getDictionaryByTypeValue(Map<String, String> map);

	List<AirportAddr> getAirportAddrt(String city);

	List<String> getUseReson();

	List<String> getRemark();

	List<String> getComments();

	SysVersion getNewVersion(Map<String, Object> param);

	Map<String, Object> getDriverPosition(String driverid);

	String getCityNo(String citycaption);

	String getCityCaption(String city);

	Map<String, Object> getDriverRateInfo(String driverid);

	void updateDriverRate(Map<String, Object> newrateparam);

	List<Map<String, Object>> getAllCitys(Map<String, Object> params);

	String getCommonQA(String qaid);

	String getAgreement();

	Map<String, Object> getPayInfo4LeByCompanyid(String companyid);

	Map<String, Object> getPayInfo4Op();

    List<Map<String,Object>> getBelongCompanys();

    List<Map<String,Object>> getHotCitys(Map<String, Object> params);

	List<String> getAllCityNames(Map<String, Object> params);
	
	Map<String, Object> getNearDriverParam();

    Map<String,Object> getAwardOnOff();

	Map<String,Object> getAwardPoint(Map<String, Object> params);

	Map<String,Object> getAwardStopInfo();

    Map<String,Object> getZKOnOff();

	Map<String,Object> getZKInfo(Map<String, Object> params);

    Map<String,Object> getYYFJF(Map<String, Object> params);

	List<String> getComplaints();

	List<String> getCancelResons();
}