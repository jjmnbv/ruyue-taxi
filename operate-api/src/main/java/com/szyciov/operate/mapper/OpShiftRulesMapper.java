package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.op.entity.OpShiftRules;
import com.szyciov.op.param.OpShiftRulesQueryParam;
import org.apache.ibatis.annotations.Param;

public interface OpShiftRulesMapper {

	List<Map<String, String>> getVailableCitys(Map<String, Object> params);

	int getShiftRulesListCountByQuery(OpShiftRulesQueryParam queryParam);

	List<OpShiftRules> getShiftRulesListByQuery(OpShiftRulesQueryParam queryParam);

	OpShiftRules getShiftRules(Map<String, Object> params);

	boolean hasCity(Map<String, Object> params);

	void createShiftRules(Map<String, Object> params);

	void updateShiftRules(Map<String, Object> params);

	List<Map<String, String>> getCitys(Map<String, Object> params);


	/**
	 * 根据条件返回单个规则
	 * @param city			城市
	 * @return
	 */
	OpShiftRules getRules( @Param(value = "city") String city);


}
