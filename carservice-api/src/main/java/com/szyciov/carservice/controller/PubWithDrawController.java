package com.szyciov.carservice.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.carservice.service.PubWithDrawService;
import com.szyciov.entity.OrderSource4WithdrawNO;

@Controller
public class PubWithDrawController {
	private PubWithDrawService service;

	@Resource(name="pubWithDrawService")
	public void setService(PubWithDrawService service) {
		this.service = service;
	}
	
	@RequestMapping("api/PubWithDraw/GetPubWithDrawNo")
	@ResponseBody
	public Map<String, Object> getPubWithDrawNo(@RequestBody OrderSource4WithdrawNO orderSource4WithdrawNO) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String pubWithDrawNo = service.getPubWithDrawNo(orderSource4WithdrawNO);
		resultMap.put("pubWithDrawNo", pubWithDrawNo);
		return resultMap;
	}
}
