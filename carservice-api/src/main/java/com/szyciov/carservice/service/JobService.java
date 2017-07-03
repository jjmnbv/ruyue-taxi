package com.szyciov.carservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.JobDao;
import com.szyciov.entity.Retcode;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.org.entity.OrgOrder;

@Service("jobService")
public class JobService {
	
	private JobDao dao;

	@Resource(name="jobDao")
	public void setDao(JobDao dao) {
		this.dao = dao;
	}
	
	public Map<String, Object> cancelOverTimeRules(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int result = dao.cancelOverTimeRules(param);
		
		resultMap.put("status", Retcode.OK.code);
		resultMap.put("message", result);
		
		return resultMap;
	}
	
	public List<OrgOrder> getBeArtificialOrgOrder() {
		
		Map<String, Object> param = new HashMap<String, Object>();
		return dao.getBeArtificialOrgOrder(param);
	}
	
	public List<OpOrder> getBeArtificialOpOrder() {
		Map<String, Object> param = new HashMap<String, Object>();
		return dao.getBeArtificialOpOrder(param);
	}

}
