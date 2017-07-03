package com.szyciov.carservice.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.carservice.service.OrderBillService;
import com.szyciov.lease.entity.OrgOrgan;

@Controller
public class OrderBillController {

	private OrderBillService service;

	@Resource(name="orderBillService")
	public void setService(OrderBillService service) {
		this.service = service;
	}
	
	@RequestMapping("api/OrderBill/GetBillNo")
	@ResponseBody
	public Map<String, Object> getBillNo(@RequestBody OrgOrgan organ) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String billNo = service.getBillNo(organ);
		resultMap.put("billNo", billNo);
		return resultMap;
	}
	
}
