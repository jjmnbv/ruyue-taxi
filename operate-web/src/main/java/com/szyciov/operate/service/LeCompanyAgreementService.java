package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.param.LeCompanyAgreementQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Service("LeCompanyAgreementService")
public class LeCompanyAgreementService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public PageBean getCompanyAgreementByQuery(LeCompanyAgreementQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/CompanyAgreement/GetCompanyAgreementByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<LeLeasescompany> getCompanyLisyByEdit(String userToken) {
		return templateHelper.dealRequestWithToken("/CompanyAgreement/GetCompanyLisyByEdit", HttpMethod.POST, userToken, null, List.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<LeLeasescompany> getCompanyLisyByAdd(String userToken) {
		return templateHelper.dealRequestWithToken("/CompanyAgreement/GetCompanyLisyByAdd", HttpMethod.POST, userToken, null, List.class);
	}
	
	public LeCompanyAgreement getCompanyAgreementById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/CompanyAgreement/GetCompanyAgreementById/{id}", HttpMethod.GET, userToken, null, LeCompanyAgreement.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> createCompanyAgreement(LeCompanyAgreement object, String userToken) {
		return templateHelper.dealRequestWithToken("/CompanyAgreement/CreateCompanyAgreement", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> editCompanyAgreement(LeCompanyAgreement object, String userToken) {
		return templateHelper.dealRequestWithToken("/CompanyAgreement/EditCompanyAgreement", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> deleteCompanyAgreement(LeCompanyAgreement object, String userToken) {
		return templateHelper.dealRequestWithToken("/CompanyAgreement/DeleteCompanyAgreement", HttpMethod.POST, userToken, object, Map.class);
	}

}
