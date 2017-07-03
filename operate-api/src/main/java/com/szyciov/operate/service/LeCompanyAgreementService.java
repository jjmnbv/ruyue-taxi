package com.szyciov.operate.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.param.LeCompanyAgreementQueryParam;
import com.szyciov.operate.dao.LeCompanyAgreementDao;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("LeCompanyAgreementService")
public class LeCompanyAgreementService {
	
	private LeCompanyAgreementDao dao;
	@Resource(name = "LeCompanyAgreementDao")
	public void setDao(LeCompanyAgreementDao dao) {
		this.dao = dao;
	}
	
	public PageBean getCompanyAgreementByQuery(LeCompanyAgreementQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		int iTotalRecords = getCompanyAgreementCountByQuery(queryParam);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setiTotalDisplayRecords(iTotalRecords);
		pageBean.setAaData(getCompanyAgreementListByQuery(queryParam));
		return pageBean;
	}
	
	public Map<String, String> editCompanyAgreement(LeCompanyAgreement object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "修改成功");
		Date currentTime = new Date();
		object.setUpdatetime(currentTime);
		updateCompanyAgreement(object);
		return ret;
	}
	
	public Map<String, String> createCompanyAgreement(LeCompanyAgreement object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		//验证该公司是否已存在协议
		LeCompanyAgreement companyAgreement = new LeCompanyAgreement();
		companyAgreement.setLeasescompanyid(object.getLeasescompanyid());
		companyAgreement.setStatus(1);
		List<LeCompanyAgreement> companyAgreementList = getCompanyAgreementList(companyAgreement);
		if(null != companyAgreementList && !companyAgreementList.isEmpty()) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "保存失败");
			return ret;
		}
		//添加租赁公司协议数据
		Date currentTime = new Date();
		object.setId(GUIDGenerator.newGUID());
		object.setUpdatetime(currentTime);
		object.setCreatetime(currentTime);
		object.setStatus(1);
		insertCompanyAgreement(object);
		return ret;
	}
	
	public Map<String, String> deleteCompanyAgreement(LeCompanyAgreement object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "删除成功");
		Date currentTime = new Date();
		object.setUpdatetime(currentTime);
		object.setStatus(2);
		updateCompanyAgreement(object);
		return ret;
	}
	
	public List<LeCompanyAgreement> getCompanyAgreementListByQuery(LeCompanyAgreementQueryParam queryParam) {
		return dao.getCompanyAgreementListByQuery(queryParam);
	}
	
	public int getCompanyAgreementCountByQuery(LeCompanyAgreementQueryParam queryParam) {
		return dao.getCompanyAgreementCountByQuery(queryParam);
	}
	
	public List<LeLeasescompany> getCompanyLisyByEdit() {
		return dao.getCompanyLisyByEdit();
	}
	
	public List<LeLeasescompany> getCompanyLisyByAdd() {
		return dao.getCompanyLisyByAdd();
	}
	
	public LeCompanyAgreement getCompanyAgreementById(String id) {
		return dao.getCompanyAgreementById(id);
	}
	
	public void updateCompanyAgreement(LeCompanyAgreement object) {
		dao.updateCompanyAgreement(object);
	}
	
	public void insertCompanyAgreement(LeCompanyAgreement object) {
		dao.insertCompanyAgreement(object);
	}
	
	public List<LeCompanyAgreement> getCompanyAgreementList(LeCompanyAgreement object) {
		return dao.getCompanyAgreementList(object);
	}

}
