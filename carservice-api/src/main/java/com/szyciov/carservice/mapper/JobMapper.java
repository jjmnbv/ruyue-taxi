package com.szyciov.carservice.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.op.entity.OpOrder;
import com.szyciov.org.entity.OrgOrder;

public interface JobMapper {
	
	public int cancelOverTimeRules(Map<String, Object> param);
	
	public List<OrgOrder> getBeArtificialOrgOrder(Map<String, Object> param);
	
	public List<OpOrder> getBeArtificialOpOrder(Map<String, Object> param);

}
