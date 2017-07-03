package com.szyciov.lease.controller;

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
import com.szyciov.lease.service.LeDriverorderstatisticsService;
import com.szyciov.util.PageBean;

@Controller
public class LeDriverorderstatisticsController {
	public LeDriverorderstatisticsService leDriverorderstatisticsService;
	@Resource(name = "leDriverorderstatisticsService")
	public void setLeDriverorderstatisticsService(LeDriverorderstatisticsService leDriverorderstatisticsService) {
		this.leDriverorderstatisticsService = leDriverorderstatisticsService;
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/LeDriverorderstatisticsParam", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getDriverCountByQuery(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getDriverCountByQuery(leDriverorderstatisticsParam);
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
	@RequestMapping(value = "api/LeDriverorderstatistics/DriverAll", method = RequestMethod.POST)
	@ResponseBody
	public List<LeDriverorderstatisticsParam> getVehcBrandAll(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getVehcBrandAll(leDriverorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/DriverAll1", method = RequestMethod.POST)
	@ResponseBody
	public List<LeDriverorderstatisticsParam> getVehcBrandAll1(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getVehcBrandAll1(leDriverorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/DriverAll2", method = RequestMethod.POST)
	@ResponseBody
	public List<LeDriverorderstatisticsParam> getVehcBrandAll2(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getVehcBrandAll2(leDriverorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeDriverorderstatistics/DriverAllTo", method = RequestMethod.POST)
	@ResponseBody
	public List<LeDriverorderstatisticsParam> getVehcBrandAllToC(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return leDriverorderstatisticsService.getVehcBrandAllToC(leDriverorderstatisticsParam);
	}

}
