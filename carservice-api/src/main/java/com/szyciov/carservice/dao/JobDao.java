package com.szyciov.carservice.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.JobMapper;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.org.entity.OrgOrder;

@Repository("jobDao")
public class JobDao {
	
	private JobMapper mapper;

	@Resource
	public void setMapper(JobMapper mapper) {
		this.mapper = mapper;
	}
	
	public int cancelOverTimeRules(Map<String, Object> param) {
		return mapper.cancelOverTimeRules(param);
	}
	
	public List<OrgOrder> getBeArtificialOrgOrder(Map<String, Object> param) {
		return mapper.getBeArtificialOrgOrder(param);
	}
	
	public List<OpOrder> getBeArtificialOpOrder(Map<String, Object> param) {
		return mapper.getBeArtificialOpOrder(param);
	}
	
}
