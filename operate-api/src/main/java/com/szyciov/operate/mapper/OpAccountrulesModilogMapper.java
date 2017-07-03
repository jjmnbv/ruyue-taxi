package com.szyciov.operate.mapper;

import java.util.List;

import com.szyciov.op.entity.OpAccountrulesModilog;
import com.szyciov.op.param.OpAccountrulesModilogQueryParam;

public interface OpAccountrulesModilogMapper {
	
	void insertOpAccountrulesModilog(OpAccountrulesModilog object);
	
	List<OpAccountrulesModilog> getOpAccountRulesModiLogListByQuery(OpAccountrulesModilogQueryParam queryParam);
	
	int getOpAccountRulesModiLogCountByQuery(OpAccountrulesModilogQueryParam queryParam);
	
}
