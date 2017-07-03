package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.Dictionary;
import com.szyciov.lease.service.DictionaryService;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;

@Controller
public class DictionaryController extends BaseController {
	private static final Logger logger = Logger.getLogger(DictionaryController.class);

	public DictionaryService dictionaryService;

	@Resource(name = "dictionaryService")
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@RequestMapping(value = "/Dictionary/Index")
	public String getDictionaryManagementIndex() {
		return "resource/dictionary/index";
	}

	@RequestMapping("Dictionary/GetDictionaryByQuery")
	@ResponseBody
	public PageBean getDictionaryByQuery(@RequestBody QueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return dictionaryService.getDictionaryByQuery(queryParam, userToken);
	}

	@RequestMapping("/Dictionary/Delete")
	@ResponseBody
	public Map<String, String> delete(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "删除字典成功！");

		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		dictionaryService.deleteDictionary(id, userToken);
		return ret;
	}

	@RequestMapping("Dictionary/GetById")
	@ResponseBody
	public Dictionary getById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		logger.log(Level.INFO, "web getById id:" + id);
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return dictionaryService.getById(id, userToken);
	}

	@RequestMapping("/Dictionary/Create")
	@ResponseBody
	public Map<String, String> create(@RequestBody Dictionary dictionary, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return dictionaryService.createDictionary(dictionary, userToken);
	}

	@RequestMapping("/Dictionary/Update")
	@ResponseBody
	public Map<String, String> update(@RequestBody Dictionary dictionary, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return dictionaryService.updateDictionary(dictionary, userToken);
	}
}
