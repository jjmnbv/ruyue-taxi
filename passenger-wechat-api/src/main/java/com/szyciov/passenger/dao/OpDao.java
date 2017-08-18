package com.szyciov.passenger.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.passenger.entity.AccountRules;
import com.szyciov.passenger.entity.VehicleModels;
import com.szyciov.passenger.mapper.OpMapper;

@Repository("OpDao")
public class OpDao {

	private OpMapper mapper;

	@Resource
	public void setMapper(OpMapper mapper) {
		this.mapper = mapper;
	}

	public List<Map<String, Object>> getBusiness(Map<String, Object> sparam) {
		return mapper.getBusiness(sparam);
	}

	public List<VehicleModels> getCarMoudels(Map<String, Object> sparam) {
		return mapper.getCarMoudels(sparam);
	}

	public List<AccountRules> getAccountRules(Map<String, Object> params) {
		return mapper.getAccountRules(params);
	}

	public List<String> getValidCity() {
		return mapper.getValidCity();
	}

	public List<Map<String, Object>> getCity(Map<String, Object> params) {
		return mapper.getCity(params);
	}

	public Map<String, Object> getCostInfo4Op(String orderid) {
		return mapper.getCostInfo4Op(orderid);
	}

	public Map<String, Object> getAccountRules4OpTaxi(Map<String, Object> params) {
		return mapper.getAccountRules4OpTaxi(params);
	}

	public Map<String, Object> getSendRules4OpTaxi(Map<String, Object> params) {
		return mapper.getSendRules4OpTaxi(params);
	}

	public List<Map<String, Object>> getGetOnCitys4Op(Map<String, Object> params) {
		return mapper.getGetOnCitys4Op(params);
	}

	public List<Map<String, Object>> getCarMoudels4OpNetCar(Map<String, Object> params) {
		return mapper.getCarMoudels4OpNetCar(params);
	}

	public Map<String, Object> getOpOrderAccountRules4NetCar(Map<String, Object> params) {
		return mapper.getOpOrderAccountRules4NetCar(params);
	}

	public Map<String, Object> getOpOrderAccountRules4Taxi(Map<String, Object> params) {
		return mapper.getOpOrderAccountRules4Taxi(params);
	}

	public List<Map<String, Object>> getSuperAndCaiWu() {
		return mapper.getSuperAndCaiWu();
	}

	public void addNews4Op(Map<String, Object> news) {
		mapper.addNews4Op(news);
	}

	public List<String> getSendRuleCitys() {
		return mapper.getSendRuleCitys();
	}

	public List<String> getSendRuleCitys4Taxi() {
		return mapper.getSendRuleCitys4Taxi();
	}

    public List<String> getAccountRuleCitys(Map<String, Object> params) {
		return mapper.getAccountRuleCitys(params);
    }

	public List<String> getAccountRuleCitys4Taxi(Map<String, Object> params) {
		return mapper.getAccountRuleCitys4Taxi(params);
	}

    public String getLowestCarType(Map<String, Object> params) {
		return mapper.getLowestCarType(params);
    }

    public Map<String,Object> getSendRuleByCity4ReverceNetCar(Map<String, Object> params) {
		return mapper.getSendRuleByCity4ReverceNetCar(params);
    }

	public Map<String,Object> getSendRule4ReverceTaxi(Map<String, Object> params) {
		return mapper.getSendRule4ReverceTaxi(params);
	}
}
