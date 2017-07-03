package com.szyciov.operate.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.PubJpushlog;
import com.szyciov.op.param.JPushLogQueryParam;
import com.szyciov.operate.service.JPushLogService;
import com.szyciov.util.PageBean;

@Controller
public class JPushLogController {

	@Resource(name="JPushLogService")
	public JPushLogService service;
	
	@RequestMapping(value="api/JPushLog/QueryJPushLogByParam",method=RequestMethod.POST)
	@ResponseBody
	public PageBean queryJPushLogByParam(@RequestBody JPushLogQueryParam param){
		
		return service.queryJPushLogByParam(param);
	}
	
	@RequestMapping(value="api/JPushLog/ExportExcel")
	@ResponseBody
	public List<PubJpushlog> exportExcel(@RequestBody JPushLogQueryParam param){
		return service.expoerExcel(param);
	}
}
