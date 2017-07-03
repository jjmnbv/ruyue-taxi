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

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LePersonalorderstatisticsParam;
import com.szyciov.operate.service.LePersonalorderstatisticsService;
import com.szyciov.util.PageBean;

@Controller
public class LePersonalorderstatisticsController {
	public LePersonalorderstatisticsService lePersonalorderstatisticsService;
	@Resource(name = "lePersonalorderstatisticsService")
	public void setOrganCountService(LePersonalorderstatisticsService lePersonalorderstatisticsService) {
		this.lePersonalorderstatisticsService = lePersonalorderstatisticsService;
	}
	@RequestMapping(value = "api/LePersonalorderstatistics/GetPersonalByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPersonalByQuery(@RequestBody LePersonalorderstatisticsParam lePersonalorderstatisticsParam)  {
		return lePersonalorderstatisticsService.getPersonalByQuery(lePersonalorderstatisticsParam);
	}
	@RequestMapping(value = "api/LePersonalorderstatistics/GetPersonalByQuery1", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPersonalByQuery1(@RequestBody LePersonalorderstatisticsParam lePersonalorderstatisticsParam)  {
		return lePersonalorderstatisticsService.getPersonalByQuery1(lePersonalorderstatisticsParam);
	}
	@RequestMapping(value = "api/LePersonalorderstatistics/PersonalAll", method = RequestMethod.POST)
	@ResponseBody
	public List<LePersonalorderstatisticsParam> getPersonalAll(@RequestBody LePersonalorderstatisticsParam lePersonalorderstatisticsParam)  {
		return lePersonalorderstatisticsService.getPersonalAll(lePersonalorderstatisticsParam);
	}
	//导出
	@RequestMapping(value = "api/LePersonalorderstatistics/PersonalAll1", method = RequestMethod.POST)
	@ResponseBody
	public List<LePersonalorderstatisticsParam> getPersonalAll1(@RequestBody LePersonalorderstatisticsParam lePersonalorderstatisticsParam)  {
		return lePersonalorderstatisticsService.getPersonalAll1(lePersonalorderstatisticsParam);
	}
	@RequestMapping(value = "api/LePersonalorderstatistics/PersonalAll2", method = RequestMethod.POST)
	@ResponseBody
	public List<LePersonalorderstatisticsParam> getPersonalAll2(@RequestBody LePersonalorderstatisticsParam lePersonalorderstatisticsParam)  {
		return lePersonalorderstatisticsService.getPersonalAll2(lePersonalorderstatisticsParam);
	}
	@RequestMapping(value = "api/LePersonalorderstatistics/Getordertype", method = RequestMethod.GET)
	@ResponseBody
	public List<PubDictionary> getordertype()  {
		return lePersonalorderstatisticsService.getordertype();
	}
	@RequestMapping(value = "api/LePersonalorderstatistics/GetPaymentstatus", method = RequestMethod.GET)
	@ResponseBody
	public List<PubDictionary> getPaymentstatus()  {
		return lePersonalorderstatisticsService.getPaymentstatus();
	}
	@RequestMapping(value = "api/LePersonalorderstatistics/GetCityListById", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getCityListById(@RequestBody LePersonalorderstatisticsParam lePersonalorderstatisticsParam)  {
		return lePersonalorderstatisticsService.getCityListById(lePersonalorderstatisticsParam);
	}
	@RequestMapping(value = "api/LePersonalorderstatistics/GetCustomer", method = RequestMethod.GET)
	@ResponseBody
	public List<PubDictionary> getCustomer()  {
		return lePersonalorderstatisticsService.getCustomer();
	}

}
