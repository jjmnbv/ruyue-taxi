package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.lease.param.PubAlarmprocessParam;
import com.szyciov.operate.service.LeDriverorderstatisticsService;
import com.szyciov.util.PageBean;

@Controller
public class LeDriverorderstatisticsController {
	public LeDriverorderstatisticsService leDriverorderstatisticsService;
	@Resource(name = "leDriverorderstatisticsService")
	public void setLeDriverorderstatisticsService(LeDriverorderstatisticsService leDriverorderstatisticsService) {
		this.leDriverorderstatisticsService = leDriverorderstatisticsService;
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/LeDriverorderstatisticsParamToC", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getDriverCountByQueryToC(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getDriverCountByQueryToC(leDriverorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/GetcartypeId/{leasesCompanyId}", method = RequestMethod.GET)
	@ResponseBody
	public List<LeDriverorderstatisticsParam> getcartypeId(@PathVariable String leasesCompanyId)  {
		return leDriverorderstatisticsService.getcartypeId(leasesCompanyId);
	}
	@RequestMapping(value = "api/LeOrgorderstatistics/GetVehcBrand", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getVehcBrand(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam) {
		return leDriverorderstatisticsService.getVehcBrand(leDriverorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/DriverAll2", method = RequestMethod.POST)
	@ResponseBody
	public List<LeDriverorderstatisticsParam> getVehcBrandAll2(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getVehcBrandAll2(leDriverorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/DriverAllTo", method = RequestMethod.POST)
	@ResponseBody
	public LeDriverorderstatisticsParam getVehcBrandAllToC(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getVehcBrandAllToC(leDriverorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/GetPlateno", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPlateno(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getPlateno(leDriverorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/GetDriver", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getDriver(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getDriver(leDriverorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/GetJobnum", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getJobnum(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getJobnum(leDriverorderstatisticsParam);
	}

}
