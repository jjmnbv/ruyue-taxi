package com.szyciov.lease.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.PubAirPortAddr;
import com.szyciov.lease.service.PubAirPortAddrService;

@Controller
public class PubAirportAddrController {
	public PubAirportAddrController() {
	}
	
	private PubAirPortAddrService ps;
	
	@Resource(name="PubAirPortAddrService")
	public void setPs(PubAirPortAddrService ps) {
		this.ps = ps;
	}

	@ResponseBody
	@RequestMapping(name="api/PubAirPortAddr/GetAirPortAddrList/",method = {RequestMethod.GET })
	public List<PubAirPortAddr> getAirPortList(@RequestParam String city){
		return ps.getAirPortAddreList(city);
	}
}
