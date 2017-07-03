package com.szyciov.touch.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.touch.enums.ResultStateEnum;
import com.szyciov.touch.service.PubInfoService;
import com.szyciov.touch.util.ResultUtil;


/**
 * 标准化接口helloword
 * @author zhu
 *
 */
@Controller
public class PubInfoController {
	
	private static final Logger logger = Logger.getLogger(PubInfoController.class);
	
	public PubInfoService pubInfoService;

	@Resource(name = "PubInfoService")
	public void setPubInfoService(PubInfoService pubInfoService) {
		this.pubInfoService = pubInfoService;
	}
	
	/**
	 * 根据用户账号获取用户信息
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="partner/getOrgUserInfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOrgUserInfo(HttpServletRequest req,HttpServletResponse res){
		try{
			return pubInfoService.getOrgUserInfo(req,res);
		}catch(Exception e){
			logger.error("获取机构用户信息出错", e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}

}
