package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szyciov.entity.PubCooagreement;
import com.szyciov.lease.dao.PubCooagreementDao;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.op.param.PubCooagreementQueryParam;
import com.szyciov.util.GUIDGenerator;
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
	
	public List<Map<String, Object>> getLeLeasescompanyList(Map<String, String> map){
		return dao.getLeLeasescompanyList(map);
	};
	
	public Map<String, String> createPubCooagreement(PubCooagreement pubCooagreement){
		Map<String, String> map = new HashMap<>();
		Map<String, String> map1 = new HashMap<>();
		map1.put("name", pubCooagreement.getCompanyName());
		//根据车企名称查 是否有这个车企  （租赁公司）
		LeLeasescompany ll = dao.getLeLeasescompany(map1);
		if(ll != null){
			pubCooagreement.setLeasecompanyid(ll.getId());
		}else{
			map.put("ResultSign", "Error");
			map.put("MessageKey", "所输入的车企不存在，请重新输入");
	 		return map;
		}
		//根据车企名称  服务类型  判断重复
		if(dao.checkCreatePubCooagreement(pubCooagreement)>0){
			map.put("ResultSign", "Error");
			map.put("MessageKey", "合作协议已存在");
	 		return map;
		}
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
		dao.deletePubCooagreement(pubCooagreement.getId());
		Map<String, String> map = new HashMap<>();
		Map<String, String> map1 = new HashMap<>();
		map1.put("name", pubCooagreement.getCompanyName());
//		dao.updatePubCooagreement(pubCooagreement);
		pubCooagreement.setId(GUIDGenerator.newGUID());
		pubCooagreement.setLeasecompanyid(dao.getLeLeasescompany(map1).getId());
		dao.createPubCooagreement(pubCooagreement);
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
