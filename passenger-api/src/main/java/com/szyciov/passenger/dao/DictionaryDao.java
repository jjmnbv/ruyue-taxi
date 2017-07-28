package com.szyciov.passenger.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.passenger.entity.AirportAddr;
import com.szyciov.passenger.entity.SysVersion;
import com.szyciov.passenger.mapper.DictionaryMapper;

/**
 * 字典相关dao
 * @author admin
 *
 */
@Repository("DictionaryDao")
public class DictionaryDao {
	public DictionaryDao() {
	}

	private DictionaryMapper mapper;

	@Resource
	public void setMapper(DictionaryMapper mapper) {
		this.mapper = mapper;
	}
	
	public Dictionary getDictionaryByTypeValue(Map<String, String> map) {
		return mapper.getDictionaryByTypeValue(map);
	}

	public List<AirportAddr> getAirportAddrt(String city) {
		return mapper.getAirportAddrt(city);
	}

	public List<String> getRemark() {
		return mapper.getRemark();
	}

	public List<String> getUseReson() {
		return mapper.getUseReson();
	}

	public List<String> getComments() {
		return mapper.getComments();
	}

	public SysVersion getNewVersion(Map<String, Object> param) {
		return mapper.getNewVersion(param);
	}

	public Map<String, Object> getDriverPosition(String driverid) {
		return mapper.getDriverPosition(driverid);
	}

	public String getCityNo(String citycaption){
		return mapper.getCityNo(citycaption);
	}

	public String getCityCaption(String city) {
		return mapper.getCityCaption(city);
	}

	public Map<String, Object> getDriverRateInfo(String driverid) {
		return mapper.getDriverRateInfo(driverid);
	}

	public void updateDriverRate(Map<String, Object> newrateparam) {
		mapper.updateDriverRate(newrateparam);
	}

	public List<Map<String, Object>> getAllCitys(Map<String, Object> params) {
		return mapper.getAllCitys(params);
	}

	public String getCommonQA(String qaid) {
		return mapper.getCommonQA(qaid);
	}

	public String getAgreement() {
		return mapper.getAgreement();
	}

	public Map<String, Object> getPayInfo4LeByCompanyid(String companyid) {
		return mapper.getPayInfo4LeByCompanyid(companyid);
	}

	public Map<String, Object> getPayInfo4Op() {
		return mapper.getPayInfo4Op();
	}

    public List<Map<String,Object>> getBelongCompanys() {
		return mapper.getBelongCompanys();
    }

	public List<Map<String,Object>> getHotCitys(Map<String, Object> params) {
		return mapper.getHotCitys(params);
	}

	public List<String> getAllCityNames(Map<String, Object> params) {
		return mapper.getAllCityNames(params);
	}
	
	public Map<String, Object> getNearDriverParam() {
		return mapper.getNearDriverParam();
	}

    public Map<String,Object> getAwardOnOff() {
		return mapper.getAwardOnOff();
    }

	public Map<String,Object> getAwardPoint(Map<String, Object> params) {
		return mapper.getAwardPoint(params);
	}

	public Map<String,Object> getAwardStopInfo() {
		return mapper.getAwardStopInfo();
	}

    public Map<String,Object> getZKOnOff() {
		return mapper.getZKOnOff();
    }

	public Map<String,Object> getZKInfo(Map<String, Object> params) {
		return mapper.getZKInfo(params);
	}

	public Map<String,Object> getYYFJF(Map<String, Object> params) {
		return mapper.getYYFJF(params);
	}
}
