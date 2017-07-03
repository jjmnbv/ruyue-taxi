package com.szyciov.operate.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.szyciov.entity.PubPersonagreement;
import com.szyciov.operate.dao.PubPersonagreementDao;
import com.szyciov.util.GUIDGenerator;

@Service("PubPersonagreementService")
public class PubPersonagreementService {
	
	private PubPersonagreementDao dao;
	@Resource(name = "PubPersonagreementDao")
	public void setDao(PubPersonagreementDao dao) {
		this.dao = dao;
	}
	
	public Map<String, String> editPubPersonagreement(PubPersonagreement object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "修改成功");
		
		String id = object.getId();
		Date currentTime = new Date();
		//新增个人用户协议
		if(StringUtils.isBlank(id)) {
			object.setId(GUIDGenerator.newGUID());
			object.setCreater(object.getUpdater());
			object.setCreatetime(currentTime);
			object.setUpdatetime(currentTime);
			object.setStatus(1);
			insertPubPersonagreement(object);
		} else {
			object.setUpdatetime(currentTime);
			updatePubPersonagreement(object);
		}
		return ret;
	}
	
	public PubPersonagreement getPubPersonagreement() {
		List<PubPersonagreement> personagreementList = getPubPersonagreementList();
		if(null != personagreementList && !personagreementList.isEmpty()) {
			return personagreementList.get(0);
		}
		return null;
	}
	
	public void insertPubPersonagreement(PubPersonagreement object) {
		dao.insertPubPersonagreement(object);
	}
	
	public void updatePubPersonagreement(PubPersonagreement object) {
		dao.updatePubPersonagreement(object);
	}
	
	public List<PubPersonagreement> getPubPersonagreementList() {
		return dao.getPubPersonagreementList();
	}

}
