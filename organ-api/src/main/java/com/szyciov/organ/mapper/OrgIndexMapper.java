package com.szyciov.organ.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderDetails;
import com.szyciov.org.param.OrgOrderQueryParam;

public interface OrgIndexMapper {

	List<OrgOrder> getOrgderList(OrgOrderQueryParam orgOrderQueryParam);
	
	int getOrgderListCount(OrgOrderQueryParam orgOrderQueryParam);
	
	List<LeLeasescompany> getQueryCompany(Map<String, Object> params);
	
	OrgOrderDetails getById(String id);
	
	List<OrgOrder> exportExcel(OrgOrderQueryParam orgOrderQueryParam);
	
	public Map<String, Object> getOrgOrderByOrderno(String orderno);
}