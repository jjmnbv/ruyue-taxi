package com.szyciov.passenger.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.passenger.entity.AccountRules;
import com.szyciov.passenger.entity.VehicleModels;

public interface OpMapper {

	List<Map<String, Object>> getBusiness(Map<String, Object> sparam);

	List<AccountRules> getAccountRules(Map<String, Object> params);

	List<String> getValidCity();

	List<Map<String, Object>> getCity(Map<String, Object> params);

	Map<String, Object> getCostInfo4Op(String orderid);

	List<VehicleModels> getCarMoudels(Map<String, Object> sparam);

	Map<String, Object> getAccountRules4OpTaxi(Map<String, Object> params);

	Map<String, Object> getSendRules4OpTaxi(Map<String, Object> params);

	List<Map<String, Object>> getGetOnCitys4Op(Map<String, Object> params);

	List<Map<String, Object>> getCarMoudels4OpNetCar(Map<String, Object> params);

	Map<String, Object> getOpOrderAccountRules4NetCar(Map<String, Object> params);

	Map<String, Object> getOpOrderAccountRules4Taxi(Map<String, Object> params);

	List<Map<String, Object>> getSuperAndCaiWu();

	void addNews4Op(Map<String, Object> news);

	List<String> getSendRuleCitys();

	List<String> getSendRuleCitys4Taxi();

    List<String> getAccountRuleCitys(Map<String, Object> params);

	List<String> getAccountRuleCitys4Taxi(Map<String, Object> params);

    String getLowestCarType(Map<String, Object> params);

    Map<String,Object> getSendRuleByCity4ReverceNetCar(Map<String, Object> params);

	Map<String,Object> getSendRule4ReverceTaxi(Map<String, Object> params);
}