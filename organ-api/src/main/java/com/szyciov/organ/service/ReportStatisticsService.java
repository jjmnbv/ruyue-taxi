package com.szyciov.organ.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.org.param.ReportStatisticsParam;
import com.szyciov.organ.dao.ReportStatisticsDao;
import com.szyciov.util.PageBean;

@Service("ReportStatisticsService")
public class ReportStatisticsService {
	private ReportStatisticsDao dao;

	@Resource(name = "ReportStatisticsDao")
	public void setDao(ReportStatisticsDao dao) {
		this.dao = dao;
	}
	public List<PubDictionary> getordertype()  {
		return dao.getordertype();
	}
	public PageBean getCompayByQuery(ReportStatisticsParam reportStatisticsParam)  {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(reportStatisticsParam.getsEcho());
		List<ReportStatisticsParam> list = getCompayByQueryList(reportStatisticsParam);
		int iTotalRecords = getCompayByQueryCount(reportStatisticsParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<ReportStatisticsParam> getCompayByQueryList(ReportStatisticsParam reportStatisticsParam){
		return dao.getCompayByQueryList(reportStatisticsParam);
	}
	public int getCompayByQueryCount(ReportStatisticsParam reportStatisticsParam){
		return dao.getCompayByQueryCount(reportStatisticsParam);
	}
	public PageBean getDeptByQuery(ReportStatisticsParam reportStatisticsParam)  {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(reportStatisticsParam.getsEcho());
		List<ReportStatisticsParam> list = getDeptByQueryList(reportStatisticsParam);
		int iTotalRecords = getDeptByQueryCount(reportStatisticsParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<ReportStatisticsParam> getDeptByQueryList(ReportStatisticsParam reportStatisticsParam){
		return dao.getDeptByQueryList(reportStatisticsParam);
	}
	public int getDeptByQueryCount(ReportStatisticsParam reportStatisticsParam){
		return dao.getDeptByQueryCount(reportStatisticsParam);
	}
	public List<ReportStatisticsParam> getExportExcal(ReportStatisticsParam reportStatisticsParam)  {
		return dao.getExportExcal(reportStatisticsParam);
	}
	public List<ReportStatisticsParam> getExportExcal1(ReportStatisticsParam reportStatisticsParam)  {
		return dao.getExportExcal1(reportStatisticsParam);
	}

}
