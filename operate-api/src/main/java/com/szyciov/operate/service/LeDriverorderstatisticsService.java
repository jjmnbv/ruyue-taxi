package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.operate.dao.LeDriverorderstatisticsDao;
import com.szyciov.util.PageBean;

@Service("leDriverorderstatisticsService")
public class LeDriverorderstatisticsService {
	private LeDriverorderstatisticsDao dao;

	@Resource(name = "LeDriverorderstatisticsDao")
	public void setDao(LeDriverorderstatisticsDao dao) {
		this.dao = dao;
	}
	public PageBean getDriverCountByQueryToC(LeDriverorderstatisticsParam leDriverorderstatisticsParam){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(leDriverorderstatisticsParam.getsEcho());
		List<LeDriverorderstatisticsParam> list = getDriverCountListByQueryToc(leDriverorderstatisticsParam);
		int iTotalRecords = getOrganCountListCountByQueryToC(leDriverorderstatisticsParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<LeDriverorderstatisticsParam> getDriverCountListByQueryToc(LeDriverorderstatisticsParam leDriverorderstatisticsParam) {
		List<LeDriverorderstatisticsParam> list = dao.getDriverCountListByQueryToC(leDriverorderstatisticsParam);
		return list;
	}

	public int getOrganCountListCountByQueryToC(LeDriverorderstatisticsParam leDriverorderstatisticsParam) {
		return dao.getDriverCountListCountByQueryToC(leDriverorderstatisticsParam);
	}
	public List<LeDriverorderstatisticsParam> getcartypeId(String leasesCompanyId)  {
		return dao.getcartypeId(leasesCompanyId);
	}
	public List<Map<String, Object>> getVehcBrand(LeDriverorderstatisticsParam leDriverorderstatisticsParam) {
		return dao.getVehcBrand(leDriverorderstatisticsParam);
	}
	public List<LeDriverorderstatisticsParam> getVehcBrandAll2(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return dao.getVehcBrandAll2(leDriverorderstatisticsParam);
	}
	public LeDriverorderstatisticsParam getVehcBrandAllToC(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return dao.getVehcBrandAllToC(leDriverorderstatisticsParam);
	}
	public List<Map<String, Object>> getPlateno(LeDriverorderstatisticsParam leDriverorderstatisticsParam){
		return dao.getPlateno(leDriverorderstatisticsParam);
	}
	public List<Map<String, Object>> getDriver(LeDriverorderstatisticsParam leDriverorderstatisticsParam){
		return dao.getDriver(leDriverorderstatisticsParam);
	}
	public List<Map<String, Object>> getJobnum(LeDriverorderstatisticsParam leDriverorderstatisticsParam){
		return dao.getJobnum(leDriverorderstatisticsParam);
	}
}
