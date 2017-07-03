package com.szyciov.lease.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.LeOrgorderstatisticsDao;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.LeOrgorderstatisticsParam;
import com.szyciov.lease.param.OrgOrganQueryParam;
import com.szyciov.util.PageBean;
@Service("leOrgorderstatisticsService")
public class LeOrgorderstatisticsService {
	private LeOrgorderstatisticsDao dao;

	@Resource(name = "LeOrgorderstatisticsDao")
	public void setDao(LeOrgorderstatisticsDao dao) {
		this.dao = dao;
	}
	public PageBean getOrganCountByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(leOrgorderstatisticsParam.getsEcho());
		List<LeOrgorderstatisticsParam> list = getOrganCountListByQuery(leOrgorderstatisticsParam);
		int iTotalRecords = getOrganCountListCountByQuery(leOrgorderstatisticsParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<LeOrgorderstatisticsParam> getOrganCountListByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
		List<LeOrgorderstatisticsParam> list = dao.getOrganCountListByQuery(leOrgorderstatisticsParam);
		return list;
	}

	public int getOrganCountListCountByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
		return dao.getOrganCountListCountByQuery(leOrgorderstatisticsParam);
	}
	public List<LeOrgorderstatisticsParam> getOrganCityCountAll(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
		List<LeOrgorderstatisticsParam> list = dao.getOrganCityCountAll(leOrgorderstatisticsParam);
		return list;
	}
	public List<LeOrgorderstatisticsParam> getOrganCountAll(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
		List<LeOrgorderstatisticsParam> list = dao.getOrganCountAll(leOrgorderstatisticsParam);
		return list;
	}
	public List<LeOrgorderstatisticsParam> getOrganCountAll1(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
		List<LeOrgorderstatisticsParam> list = dao.getOrganCountAll1(leOrgorderstatisticsParam);
		return list;
	}
	public PageBean getCityCountByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(leOrgorderstatisticsParam.getsEcho());
		List<LeOrgorderstatisticsParam> list = getCityCountListByQuery(leOrgorderstatisticsParam);
		int iTotalRecords = getCityCountListCountByQuery(leOrgorderstatisticsParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<LeOrgorderstatisticsParam> getCityCountListByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
		List<LeOrgorderstatisticsParam> list = dao.getCityCountListByQuery(leOrgorderstatisticsParam);
		return list;
	}

	public int getCityCountListCountByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
		return dao.getCityCountListCountByQuery(leOrgorderstatisticsParam);
	}
	public List<Map<String, Object>> getOrganCity(OrgOrganQueryParam orgOrganQueryParam){
		return dao.getOrganCity(orgOrganQueryParam);
	};
	public List<Map<String, Object>> getOrganShortName(OrgOrganQueryParam orgOrganQueryParam){
		return dao.getOrganShortName(orgOrganQueryParam);
	};
	public List<PubCityAddr> getCityListById(String leasesCompanyId) {
		return dao.getCityListById(leasesCompanyId);
	}

}
