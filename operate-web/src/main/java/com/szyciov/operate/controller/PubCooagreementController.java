package com.szyciov.operate.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.PubCooagreement;
import com.szyciov.op.entity.LeLeasescompany;
import com.szyciov.op.param.PubCooagreementQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;


@Controller
public class PubCooagreementController extends BaseController {
    private static final Logger logger = Logger.getLogger(PubCooagreementController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/PubCooagreement/Index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		mav.setViewName("resource/pubCooagreement/index");
		return mav;
	}
	
	@RequestMapping("/PubCooagreement/GetPubCooagreementByQuery")
	@ResponseBody
	public PageBean getPubCooagreementByQuery(@RequestBody PubCooagreementQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		queryParam.setKey(this.getLoginOpUser(request).getOperateid());
		return templateHelper.dealRequestWithToken("/PubCooagreement/GetPubCooagreementByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping(value = "/PubCooagreement/Edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) String id,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<LeLeasescompany> list = templateHelper.dealRequestWithToken("/PubCooagreement/GetLeLeasescompanyList", HttpMethod.POST, userToken,
				null,List.class);
		mav.addObject("list",list);
		if(StringUtils.isBlank(id)){
			mav.addObject("id","");
		}else{
			mav.addObject("id",id);
		}
		mav.setViewName("resource/pubCooagreement/editPubCooagreement");
		return mav;
	}
	
	@RequestMapping("/PubCooagreement/CreatePubCooagreement")
	@ResponseBody
	public Map<String,String> createPubCooagreement(@RequestBody PubCooagreement pubCooagreement, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubCooagreement.setId(GUIDGenerator.newGUID());
		pubCooagreement.setCompanyid(this.getLoginOpUser(request).getOperateid());
		pubCooagreement.setPlatformtype(0);
		pubCooagreement.setCreater(this.getLoginOpUser(request).getId());
		pubCooagreement.setUpdater(this.getLoginOpUser(request).getId());
		pubCooagreement.setCoocontent(pubCooagreement.getContent());
		return templateHelper.dealRequestWithToken("/PubCooagreement/CreatePubCooagreement", HttpMethod.POST, userToken,
				pubCooagreement,Map.class);
	}
	
	@RequestMapping("/PubCooagreement/GetById")
	@ResponseBody
	public PubCooagreement getById(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubCooagreement/GetById/{id}", HttpMethod.GET, userToken,
				null,PubCooagreement.class,id);
	}
	
	@RequestMapping("/PubCooagreement/UpdatePubCooagreement")
	@ResponseBody
	public Map<String,String> updatePubCooagreement(@RequestBody PubCooagreement pubCooagreement, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubCooagreement.setCompanyid(this.getLoginOpUser(request).getOperateid());
		pubCooagreement.setUpdater(this.getLoginOpUser(request).getId());
		pubCooagreement.setCoocontent(pubCooagreement.getContent());
		return templateHelper.dealRequestWithToken("/PubCooagreement/UpdatePubCooagreement", HttpMethod.POST, userToken,
				pubCooagreement,Map.class);
	}
	
	@RequestMapping("/PubCooagreement/DeletePubCooagreement")
	@ResponseBody
	public Map<String,String> deletePubCooagreement(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubCooagreement/DeletePubCooagreement/{id}", HttpMethod.GET, userToken,
				null,Map.class,id);
	}
	
	@RequestMapping(value="/PubCooagreement/DetailPubCooagreement/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView detailPubCooagreement(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubCooagreement pubCooagreement= templateHelper.dealRequestWithToken("/PubCooagreement/GetById/{id}", HttpMethod.GET, userToken,
				null,PubCooagreement.class,id);
		mav.addObject("pubCooagreement",pubCooagreement);
		mav.setViewName("resource/pubCooagreement/detailPubCooagreement");
		return mav;
	}
}
