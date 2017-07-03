package com.szyciov.organ.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.org.param.ReportStatisticsParam;
import com.szyciov.organ.service.ReportStatisticsService;
import com.szyciov.util.PageBean;

@Controller
public class ReportStatisticsController {
	private static final Logger logger = Logger.getLogger(OrgUsecarrulesController.class);

	public ReportStatisticsService reportStatisticsService;

	@Resource(name = "ReportStatisticsService")
	public void setReportStatisticsService(ReportStatisticsService reportStatisticsService) {
		this.reportStatisticsService = reportStatisticsService;
	}
	@RequestMapping(value = "api/ReportStatistics/Getordertype", method = RequestMethod.GET)
	@ResponseBody
	public List<PubDictionary> getordertype()  {
		return reportStatisticsService.getordertype();
	}
	@RequestMapping(value = "api/ReportStatistics/GetCompayByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getCompayByQuery(@RequestBody ReportStatisticsParam reportStatisticsParam)  {
		return reportStatisticsService.getCompayByQuery(reportStatisticsParam);
	}
	@RequestMapping(value = "api/ReportStatistics/GetDeptByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getDeptByQuery(@RequestBody ReportStatisticsParam reportStatisticsParam)  {
		return reportStatisticsService.getDeptByQuery(reportStatisticsParam);
	}
	@RequestMapping(value = "api/ReportStatistics/GetExportExcal", method = RequestMethod.POST)
	@ResponseBody
	public List<ReportStatisticsParam> exportExcal(@RequestBody ReportStatisticsParam reportStatisticsParam)  {
		return reportStatisticsService.getExportExcal(reportStatisticsParam);
	}
	@RequestMapping(value = "api/ReportStatistics/GetExportExcal1", method = RequestMethod.POST)
	@ResponseBody
	public List<ReportStatisticsParam> exportExcal1(@RequestBody ReportStatisticsParam reportStatisticsParam)  {
		return reportStatisticsService.getExportExcal1(reportStatisticsParam);
	}

}
