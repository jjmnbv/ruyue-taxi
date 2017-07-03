package com.szyciov.carservice.controller;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.carservice.service.PubsmssendlogService;
import com.szyciov.entity.Pubsmssendlog;
import com.szyciov.util.SMMessageUtil;
@Controller
public class PubsmssendlogController {
	@Resource(name="pubsmssendlogService")
	private PubsmssendlogService pubsmssendlogService;

	public void setPubsmssendlogService(PubsmssendlogService pubsmssendlogService) {
		this.pubsmssendlogService = pubsmssendlogService;
	}
	/**
	 * 发送短信日志插入
	 */
	@RequestMapping("/api/Pubsmssendlog/InsertPubsmssendlog")
	@ResponseBody
	public void insertPubsmssendlog(
			@RequestParam String phones,
			@RequestParam String content,
			@RequestParam String sendstate,
			@RequestParam String receiptstate,
			HttpServletRequest request,
			HttpServletResponse response){
		    response.setContentType("text/html;charset=utf-8");
		    Pubsmssendlog pubsmssendlog = new Pubsmssendlog();
		    pubsmssendlog.setPhone(phones);
		    pubsmssendlog.setContent(content);
		    pubsmssendlog.setSendstate(sendstate);
		    pubsmssendlog.setReceiptstate(receiptstate);
		    pubsmssendlogService.insertPubsmssendlog(pubsmssendlog);
	}
	/**
	 * 发短信接口调用测试
	 */
	@RequestMapping("/api/Pubsmssendlog/testPubsmssendlog")
	@ResponseBody
	public void testPubsmssendlog(
			HttpServletRequest request,
			HttpServletResponse response){
		    response.setContentType("text/html;charset=utf-8");
		    SMMessageUtil aaaa = new SMMessageUtil();
		    List<String> list = new ArrayList<String>();
		    list.add("15717134245");
		    aaaa.send(list, "sfsfsfsf");
	}

}
