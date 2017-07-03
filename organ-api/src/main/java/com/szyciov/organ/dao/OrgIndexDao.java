package com.szyciov.organ.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.param.PubVehcbrandQueryParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderDetails;
import com.szyciov.org.entity.OrgUsecarrules;
import com.szyciov.org.param.OrgOrderQueryParam;
import com.szyciov.organ.mapper.OrgIndexMapper;
import com.szyciov.util.PageBean;

@Repository("OrgIndexDao")
public class OrgIndexDao {
	public OrgIndexDao() {
	}

	private OrgIndexMapper mapper;

	@Resource
	public void setMapper(OrgIndexMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<OrgOrder> getOrgderList(OrgOrderQueryParam orgOrderQueryParam){
		return mapper.getOrgderList(orgOrderQueryParam);
	};
	
	public int getOrgderListCount(OrgOrderQueryParam orgOrderQueryParam){
		return mapper.getOrgderListCount(orgOrderQueryParam);
	};
	
	public List<LeLeasescompany> getQueryCompany(Map<String, Object> params){
		return mapper.getQueryCompany(params);
	};
	
	public OrgOrderDetails getById(String id){
		return mapper.getById(id);
	};
	
	public List<OrgOrder> exportExcel(OrgOrderQueryParam orgOrderQueryParam){
		return mapper.exportExcel(orgOrderQueryParam);
	};
	
	public Map<String, Object> getOrgOrderByOrderno(String orderno) {
		return mapper.getOrgOrderByOrderno(orderno);
	}
}
