package com.szyciov.organ.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.org.entity.OrgInformation;
import com.szyciov.organ.dao.OrganInformationDao;
import com.szyciov.util.GUIDGenerator;

@Service("OrganInformationService")
public class OrganInformationService {
	private OrganInformationDao dao;
	@Resource(name = "OrganInformationDao")
	public void setDao(OrganInformationDao dao) {
		this.dao = dao;
	}
	public OrgInformation getorgInformation(String organId)  {
		return dao.getorgInformation(organId);
	}
	public Map<String,String> insertOrgInformation(OrgInformation orgInformation){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		if(orgInformation.organId == null){
			String uuid = GUIDGenerator.newGUID();
			orgInformation.setOrganId(uuid);
			dao.insertOrgInformation(orgInformation);
		}else{
			dao.updateOrgInformation(orgInformation);
		}
		return ret;
	}

}
