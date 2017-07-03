package com.szyciov.organ.mapper;

import java.util.List;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.org.param.ReportStatisticsParam;

public interface ReportStatisticsMapper {
	List<PubDictionary> getordertype();
	List<ReportStatisticsParam> getCompayByQueryList(ReportStatisticsParam reportStatisticsParam);
	int getCompayByQueryCount(ReportStatisticsParam reportStatisticsParam);
	List<ReportStatisticsParam> getDeptByQueryList(ReportStatisticsParam reportStatisticsParam);
	int getDeptByQueryCount(ReportStatisticsParam reportStatisticsParam);
	List<ReportStatisticsParam> getExportExcal(ReportStatisticsParam reportStatisticsParam);
	List<ReportStatisticsParam> getExportExcal1(ReportStatisticsParam reportStatisticsParam);
}
