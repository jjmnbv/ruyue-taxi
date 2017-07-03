package com.szyciov.lease.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.LePersonalorderstatisticsDao;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LePersonalorderstatisticsParam;
import com.szyciov.util.PageBean;

@Service("lePersonalorderstatisticsService")
public class LePersonalorderstatisticsService {
	private LePersonalorderstatisticsDao dao;

	@Resource(name = "LePersonalorderstatisticsDao")
	public void setDao(LePersonalorderstatisticsDao dao) {
		this.dao = dao;
	}
	public PageBean getPersonalByQuery(LePersonalorderstatisticsParam lePersonalorderstatisticsParam){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(lePersonalorderstatisticsParam.getsEcho());
		List<LePersonalorderstatisticsParam> list = getPersonalCountListByQuery(lePersonalorderstatisticsParam);
		int iTotalRecords = getPersonalCountListCountByQuery(lePersonalorderstatisticsParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public PageBean getPersonalByQuery1(LePersonalorderstatisticsParam lePersonalorderstatisticsParam){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(lePersonalorderstatisticsParam.getsEcho());
		List<LePersonalorderstatisticsParam> list = getPersonalCountListByQuery1(lePersonalorderstatisticsParam);
		int iTotalRecords = getPersonalCountListCountByQuery1(lePersonalorderstatisticsParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<LePersonalorderstatisticsParam> getPersonalCountListByQuery(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
		return dao.getPersonalCountListByQuery(lePersonalorderstatisticsParam);
	}

	public int getPersonalCountListCountByQuery(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
		return dao.getPersonalCountListCountByQuery(lePersonalorderstatisticsParam);
	}
	public List<LePersonalorderstatisticsParam> getPersonalCountListByQuery1(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
		return dao.getPersonalCountListByQuery1(lePersonalorderstatisticsParam);
	}

	public int getPersonalCountListCountByQuery1(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
		return dao.getPersonalCountListCountByQuery1(lePersonalorderstatisticsParam);
	}
	public List<LePersonalorderstatisticsParam> getPersonalAll(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
		return dao.getPersonalAll(lePersonalorderstatisticsParam);
	}
	public List<LePersonalorderstatisticsParam> getPersonalAll1(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
		return dao.getPersonalAll1(lePersonalorderstatisticsParam);
	}
	public List<LePersonalorderstatisticsParam> getPersonalAll2(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
		return dao.getPersonalAll2(lePersonalorderstatisticsParam);
	}
	public List<PubDictionary> getordertype(String leasesCompanyId)  {
		return dao.getordertype(leasesCompanyId);
	}
	public List<PubDictionary> getPaymethod(String leasesCompanyId)  {
		return dao.getPaymethod(leasesCompanyId);
	}
	public LePersonalorderstatisticsParam getTheUser(String leasesCompanyId)  {
		return dao.getTheUser(leasesCompanyId);
	}


}
