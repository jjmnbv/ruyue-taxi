package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.Dictionary;
import com.szyciov.lease.service.DictionaryService;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 字典模块控制器
 */
@Controller
public class DictionaryController extends BaseController {
	private static final Logger logger = Logger.getLogger(DictionaryController.class);

	public DictionaryService dictionaryService;

	@Resource(name = "dictionaryService")
	public void setEmployeeService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	/** 
	 * <p>分页查询字典模块信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/Dictionary/GetDictionaryByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getDictionaryByQuery(@RequestBody QueryParam queryParam) {
		return dictionaryService.getDictionaryByQuery(queryParam);
	}

	/** 
	 * <p>增加一条记录</p>
	 *
	 * @param dictionary
	 * @return
	 */
	@RequestMapping(value = "api/Dictionary/Create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(@RequestBody Dictionary dictionary) {
		return dictionaryService.createDictionary(dictionary);
	}

	/** 
	 * <p>删除一条记录</p>
	 *
	 * @param id 待删除记录对应的序列号
	 */
	@RequestMapping(value = "api/Dictionary/Delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteDictionary(@PathVariable String id) {
		logger.log(Level.INFO, "api getById id:" + id);
		dictionaryService.deleteDictionary(id);
	}
	
	/** 
	 * <p>更新一条记录</p>
	 *
	 * @param dictionary
	 * @return
	 */
	@RequestMapping(value = "api/Dictionary/Update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateDictionary(@RequestBody Dictionary dictionary) {
		return dictionaryService.updateDictionary(dictionary);
	}

	/** 
	 * <p>根据序列Id查询记录</p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/Dictionary/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Dictionary getById(@PathVariable String id) {
		logger.log(Level.INFO, "api getById id:" + id);
		return dictionaryService.getById(id);
	}

	/** 
	 * <p>根据字典类型，查询对应的字典项</p>
	 *
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "api/Dictionary/GetDictionaryByType", method = RequestMethod.GET)
	@ResponseBody
	public List<Dictionary> getDictionaryByType(@RequestParam(value = "type", required = false) String type) {
		return dictionaryService.getDictionaryByType(type);
	}
	
	/** 
	 * <p>根据字典类型，查询对应的字典项</p>
	 *
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "api/Dictionary/getDictionaryByTypeText", method = RequestMethod.GET)
	@ResponseBody
	public List<Dictionary> getDictionaryByTypeText(@RequestParam(value = "type", required = false) String type,@RequestParam(value = "text", required = false) String text) {
		return dictionaryService.getDictionaryByTypeText(type,text);
	}

}
