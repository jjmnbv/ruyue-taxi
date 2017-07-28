package com.szyciov.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szyciov.entity.PubCooagreement;
import com.szyciov.op.entity.LeLeasescompany;
import com.szyciov.op.param.PubCooagreementQueryParam;
import com.szyciov.operate.dao.PubCooagreementDao;
import com.szyciov.util.PageBean;

@Service("PubCooagreementService")
public class PubCooagreementService {

	@Autowired
	private PubCooagreementDao dao;
	@Resource(name = "PubCooagreementDao")
	public void setDao(PubCooagreementDao dao) {
		this.dao = dao;
	}

	public PageBean getPubCooagreementByQuery(PubCooagreementQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubCooagreement> list = dao.getPubCooagreementList(queryParam);
		int iTotalRecords = dao.getPubCooagreementListCount(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<LeLeasescompany> getLeLeasescompanyList(){
		return dao.getLeLeasescompanyList();
	};
	
	public Map<String, String> createPubCooagreement(PubCooagreement pubCooagreement){
		Map<String, String> map = new HashMap<>();
		dao.createPubCooagreement(pubCooagreement);
//		map.put("ResultSign", "Error");
//		map.put("MessageKey", "租赁端当前仅允许创建一个服务车企作为B2B业务运营方");
 		map.put("ResultSign", "Successful");
		map.put("MessageKey", "保存成功");
 		return map;
	};
	
	public PubCooagreement getById(String id){
		return dao.getById(id);
	};
	
	public Map<String, String> updatePubCooagreement(PubCooagreement pubCooagreement){
		Map<String, String> map = new HashMap<>();
		dao.updatePubCooagreement(pubCooagreement);
		map.put("ResultSign", "Successful");
		map.put("MessageKey", "修改成功");
 		return map;
	};
	
	public Map<String, String> deletePubCooagreement(String id){
		Map<String, String> map = new HashMap<>();
		dao.deletePubCooagreement(id);
		map.put("ResultSign", "Successful");
		map.put("MessageKey", "删除成功");
 		return map;
	};
}
