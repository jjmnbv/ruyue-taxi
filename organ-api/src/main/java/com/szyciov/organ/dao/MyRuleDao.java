package com.szyciov.organ.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.organ.mapper.MyRuleMapper;

@Repository("MyRuleDao")
public class MyRuleDao {
	public MyRuleDao() {
	}

	private MyRuleMapper mapper;

	@Resource
	public void setMapper(MyRuleMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<Map<String, Object>> getOrgUsecarrules(Map<String, String> map){
		return mapper.getOrgUsecarrules(map);
	};
	
}
