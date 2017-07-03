package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.op.entity.OrderExportEntity;
import com.szyciov.op.param.OrderExportParam;
import com.szyciov.operate.service.OrderExportService;
import com.szyciov.util.PageBean;

@Controller
public class OrderExportController {
	public OrderExportService orderExportService;

	@Resource(name = "OrderExportService")
	public void setPeUserService(OrderExportService orderExportService) {
		this.orderExportService = orderExportService;
	}
	@RequestMapping(value = "api/OrderExport/GetOrderExportData", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPeUserByQuery(@RequestBody OrderExportEntity queryParam) {
		return orderExportService.getOrderExportData(queryParam);
	}
	@RequestMapping(value = "api/OrderExport/GetAllDriver", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getDriver(@RequestBody OrderExportEntity orderExportEntity)  {
		return orderExportService.getDriver(orderExportEntity);
	}
	@RequestMapping(value = "api/OrderExport/GetOrderperson", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPassage(@RequestBody OrderExportEntity orderExportEntity)  {
		return orderExportService.getPassage(orderExportEntity);
	}
	@RequestMapping(value = "api/OrderExport/GetAllOrganid", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getAllOrganid(@RequestBody OrderExportEntity orderExportEntity)  {
		return orderExportService.getAllOrganid(orderExportEntity);
	}
	@RequestMapping(value = "api/OrderExport/ExportOrders", method = RequestMethod.POST)
	@ResponseBody
	public List<OrderExportParam> exportOrders(@RequestBody OrderExportEntity orderExportEntity)  {
		return orderExportService.exportOrders(orderExportEntity);
	}
	
}
