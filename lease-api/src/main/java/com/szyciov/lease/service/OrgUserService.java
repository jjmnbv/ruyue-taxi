package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.OrgUserDao;
import com.szyciov.org.entity.OrgUser;

@Service("OrgUserService")
public class OrgUserService {
	private OrgUserDao dao;

	@Resource(name = "OrgUserDao")
	public void setDao(OrgUserDao dao) {
		this.dao = dao;
	}
	public Map<String, String> createOrgUser(OrgUser orgUser) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "创建成功");
		dao.createOrgUser(orgUser);
		return ret;
	}
}
