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

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.LeOrgorderstatisticsParam;
import com.szyciov.lease.param.OrgOrganQueryParam;
import com.szyciov.lease.service.LeOrgorderstatisticsService;
import com.szyciov.util.PageBean;
@Controller
public class LeOrgorderstatisticsController {
	public LeOrgorderstatisticsService leOrgorderstatisticsService;
	@Resource(name = "leOrgorderstatisticsService")
	public void setOrganCountService(LeOrgorderstatisticsService leFunctionmanagementService) {
		this.leOrgorderstatisticsService = leFunctionmanagementService;
	}
	@RequestMapping(value = "api/LeOrgorderstatistics/OrganCountParam", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrganCountByQuery(@RequestBody LeOrgorderstatisticsParam leOrgorderstatisticsParam)  {
//		PageBean aa = leOrgorderstatisticsService.getOrganCountByQuery(leOrgorderstatisticsParam);
		return leOrgorderstatisticsService.getOrganCountByQuery(leOrgorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeOrgorderstatistics/OrganCountAll", method = RequestMethod.POST)
	@ResponseBody
	public List<LeOrgorderstatisticsParam> getOrganCountAll(@RequestBody LeOrgorderstatisticsParam leOrgorderstatisticsParam)  {
		return leOrgorderstatisticsService.getOrganCountAll(leOrgorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeOrgorderstatistics/OrganCountAll1", method = RequestMethod.POST)
	@ResponseBody
	public List<LeOrgorderstatisticsParam> getOrganCountAll1(@RequestBody LeOrgorderstatisticsParam leOrgorderstatisticsParam)  {
		return leOrgorderstatisticsService.getOrganCountAll1(leOrgorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeOrgorderstatistics/OrganCityCountAll", method = RequestMethod.POST)
	@ResponseBody
	public List<LeOrgorderstatisticsParam> getOrganCityCountAll(@RequestBody LeOrgorderstatisticsParam leOrgorderstatisticsParam)  {
		return leOrgorderstatisticsService.getOrganCityCountAll(leOrgorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeOrgorderstatistics/OrganCountCityParam", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getCityCountByQuery(@RequestBody LeOrgorderstatisticsParam leOrgorderstatisticsParam)  {
//		PageBean aa = leOrgorderstatisticsService.getCityCountByQuery(leOrgorderstatisticsParam);
		return leOrgorderstatisticsService.getCityCountByQuery(leOrgorderstatisticsParam);
	}
	@RequestMapping(value = "api/LeOrgorderstatistics/GetOrganCity", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getOrganCity(@RequestBody OrgOrganQueryParam orgOrganQueryParam) {
		return leOrgorderstatisticsService.getOrganCity(orgOrganQueryParam);
	}
	@RequestMapping(value = "api/LeOrgorderstatistics/GetOrganShortName", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getOrganShortName(@RequestBody OrgOrganQueryParam orgOrganQueryParam) {
		return leOrgorderstatisticsService.getOrganShortName(orgOrganQueryParam);
	}
	@RequestMapping(value = "api/LeOrgorderstatistics/GetCityListById/{leasesCompanyId}", method = RequestMethod.GET)
	@ResponseBody
	public List<PubCityAddr> getCityListById(@PathVariable String leasesCompanyId) {
		return leOrgorderstatisticsService.getCityListById(leasesCompanyId);
	}
}
