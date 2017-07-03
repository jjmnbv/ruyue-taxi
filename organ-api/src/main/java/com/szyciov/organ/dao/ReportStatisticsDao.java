package com.szyciov.organ.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.org.param.ReportStatisticsParam;
import com.szyciov.organ.mapper.ReportStatisticsMapper;

@Repository("ReportStatisticsDao")
public class ReportStatisticsDao {
	public ReportStatisticsDao() {
	}

	private ReportStatisticsMapper mapper;

	@Resource
	public void setMapper(ReportStatisticsMapper mapper) {
		this.mapper = mapper;
	}
	public List<PubDictionary> getordertype()  {
		return mapper.getordertype();
	}
	public List<ReportStatisticsParam> getCompayByQueryList(ReportStatisticsParam reportStatisticsParam){
		return mapper.getCompayByQueryList(reportStatisticsParam);
	}
	public int getCompayByQueryCount(ReportStatisticsParam reportStatisticsParam){
		return mapper.getCompayByQueryCount(reportStatisticsParam);
	}
	public List<ReportStatisticsParam> getDeptByQueryList(ReportStatisticsParam reportStatisticsParam){
		return mapper.getDeptByQueryList(reportStatisticsParam);
	}
	public int getDeptByQueryCount(ReportStatisticsParam reportStatisticsParam){
		return mapper.getDeptByQueryCount(reportStatisticsParam);
	}
	public List<ReportStatisticsParam> getExportExcal(ReportStatisticsParam reportStatisticsParam)  {
		return mapper.getExportExcal(reportStatisticsParam);
	}
	public List<ReportStatisticsParam> getExportExcal1(ReportStatisticsParam reportStatisticsParam)  {
		return mapper.getExportExcal1(reportStatisticsParam);
	}

}
