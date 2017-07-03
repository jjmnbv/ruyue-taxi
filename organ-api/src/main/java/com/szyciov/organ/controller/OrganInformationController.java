package com.szyciov.organ.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.org.entity.OrgInformation;
import com.szyciov.organ.service.OrganInformationService;

@Controller
public class OrganInformationController {
	public OrganInformationService organInformationService;

	@Resource(name = "OrganInformationService")
	public void setOrganInformationService(OrganInformationService organInformationService) {
		this.organInformationService = organInformationService;
	}
	@RequestMapping(value = "api/OrganInformation/GetorgInformation/{organId}", method = RequestMethod.GET)
	@ResponseBody
	public OrgInformation getorgInformation(@PathVariable String organId)  {
		return organInformationService.getorgInformation(organId);
	}
	@RequestMapping(value = "api/OrganInformation/InsertOrgInformation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> insertOrgInformation(@RequestBody OrgInformation orgInformation)  {
		return organInformationService.insertOrgInformation(orgInformation);
	}

}
