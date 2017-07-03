package com.szyciov.operate.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.szyciov.op.param.OpOrderstatisticsParam;
import com.szyciov.operate.service.OpOrderstatisticsService;
import com.szyciov.util.PageBean;
/**
 * 销售管理
 * @author Administrator
 *
 */
@Controller
public class OpOrderstatisticsController {
	public OpOrderstatisticsService opOrderstatisticsService;

	@Resource(name = "OpOrderstatisticsService")
	public void setOpOrderstatisticsService(OpOrderstatisticsService opOrderstatisticsService) {
		this.opOrderstatisticsService = opOrderstatisticsService;
	}
	@RequestMapping(value = "api/OpOrderstatistics/month", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getMonthData(@RequestBody OpOrderstatisticsParam opOrderstatisticsParam) {
		return opOrderstatisticsService.getMonthDataQuery(opOrderstatisticsParam);
	}
	@RequestMapping(value = "api/OpOrderstatistics/AllData", method = RequestMethod.POST)
	@ResponseBody
	public OpOrderstatisticsParam getAllData(@RequestBody OpOrderstatisticsParam opOrderstatisticsParam) {
		return opOrderstatisticsService.getAllData(opOrderstatisticsParam);
	}
	@RequestMapping(value = "api/OpOrderstatistics/exportExcel", method = RequestMethod.POST)
	@ResponseBody
	public List<OpOrderstatisticsParam> exportData(@RequestBody OpOrderstatisticsParam opOrderstatisticsParam) {
		return opOrderstatisticsService.exportData(opOrderstatisticsParam);
	}
	@RequestMapping(value = "api/OpOrderstatistics/GetCustom/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public List<OpOrderstatisticsParam> getCustom(@PathVariable String userid)  {
		return opOrderstatisticsService.getCustom(userid);
	}

}
