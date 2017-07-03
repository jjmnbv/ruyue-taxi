package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.op.entity.OpTaxiSchedulefeestatistics;
import com.szyciov.operate.service.OpTaxiSchedulefeestatisticsService;
import com.szyciov.util.PageBean;

@Controller
public class OpTaxiSchedulefeestatisticsController {
	public OpTaxiSchedulefeestatisticsService opTaxiSchedulefeestatisticsService;
	@Resource(name = "opTaxiSchedulefeestatisticsService")
	public void setopTaxiSchedulefeestatisticsService(OpTaxiSchedulefeestatisticsService opTaxiSchedulefeestatisticsService) {
		this.opTaxiSchedulefeestatisticsService = opTaxiSchedulefeestatisticsService;
	}
	@RequestMapping(value = "api/OpTaxiSchedulefeestatistics/GetCustomer", method = RequestMethod.POST)
	@ResponseBody
	public  List<PubDictionary> getCustomer(){
		return opTaxiSchedulefeestatisticsService.getCustomer();
	}
	@RequestMapping(value = "api/OpTaxiSchedulefeestatistics/GetDate", method = RequestMethod.POST)
	@ResponseBody
	public  PageBean getDate(@RequestBody OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return opTaxiSchedulefeestatisticsService.getDate(opTaxiSchedulefeestatistics);
	}
	@RequestMapping(value = "api/OpTaxiSchedulefeestatistics/GetDriver", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getDriver(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return opTaxiSchedulefeestatisticsService.getDriver(leDriverorderstatisticsParam);
	}
	@RequestMapping(value = "api/OpTaxiSchedulefeestatistics/GetDateDriver", method = RequestMethod.POST)
	@ResponseBody
	public  PageBean getDateDriver(@RequestBody OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return opTaxiSchedulefeestatisticsService.getDateDriver(opTaxiSchedulefeestatistics);
	}
	@RequestMapping(value = "api/OpTaxiSchedulefeestatistics/Export", method = RequestMethod.POST)
	@ResponseBody
	public  List<OpTaxiSchedulefeestatistics> opTaxiSchedulefeesExport(@RequestBody OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return opTaxiSchedulefeestatisticsService.opTaxiSchedulefeesExport(opTaxiSchedulefeestatistics);
	}
	@RequestMapping(value = "api/OpTaxiSchedulefeestatistics/Export1", method = RequestMethod.POST)
	@ResponseBody
	public  List<OpTaxiSchedulefeestatistics> opTaxiSchedulefeesExport1(@RequestBody OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return opTaxiSchedulefeestatisticsService.opTaxiSchedulefeesExport1(opTaxiSchedulefeestatistics);
	}
}
