package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.OrgOrganCompanyRefDao;
import com.szyciov.lease.dao.OrgOrganDao;
import com.szyciov.lease.entity.OrgOrganCompanyRef;

@Service("OrgOrganCompanyRefService")
public class OrgOrganCompanyRefService {
	private OrgOrganCompanyRefDao dao;
	@Resource(name = "OrgOrganCompanyRefDao")
	public void setDao(OrgOrganCompanyRefDao dao) {
		this.dao = dao;
	}
	public Map<String, String> createOrgOrganCompanyRef(OrgOrganCompanyRef orgOrganCompanyRef) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "创建成功");
		dao.createOrgOrganCompanyRef(orgOrganCompanyRef);
		return ret;
	}
}
