package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.op.entity.OpAccountrulesModilog;
import com.szyciov.op.param.OpAccountrulesModilogQueryParam;
import com.szyciov.operate.mapper.OpAccountrulesModilogMapper;

@Repository("OpAccountrulesModilogDao")
public class OpAccountrulesModilogDao {
	
	private OpAccountrulesModilogMapper mapper;
	@Resource
	public void setMapper(OpAccountrulesModilogMapper mapper) {
		this.mapper = mapper;
	}
	
	public void insertOpAccountrulesModilog(OpAccountrulesModilog object) {
		mapper.insertOpAccountrulesModilog(object);
	}
	
	public List<OpAccountrulesModilog> getOpAccountRulesModiLogListByQuery(OpAccountrulesModilogQueryParam queryParam) {
		return mapper.getOpAccountRulesModiLogListByQuery(queryParam);
	}
	
	public int getOpAccountRulesModiLogCountByQuery(OpAccountrulesModilogQueryParam queryParam) {
		return mapper.getOpAccountRulesModiLogCountByQuery(queryParam);
	}

}
