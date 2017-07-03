package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.PubSysVersion;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.operate.service.PubSysversionService;
import com.szyciov.param.PubSysversionQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 版本说明管理
 */
@Controller
public class PubSysversionController extends BaseController {
	
	private PubSysversionService sysversionService;
	@Resource(name = "PubSysversionService")
	public void setSysversionService(PubSysversionService sysversionService) {
		this.sysversionService = sysversionService;
	}
	
	/**
	 * 查询版本说明中存在的适用系统
	 * @return
	 */
	@RequestMapping(value = "api/PubSysversion/GetSystemtypeByList", method = RequestMethod.POST)
	@ResponseBody
	public List<PubDictionary> getSystemtypeByList() {
		return sysversionService.getSystemtypeByList();
	}
	
	/**
	 * 条件查询版本说明数据
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/PubSysversion/GetPubSysversionList", method = RequestMethod.POST)
	@ResponseBody
	public List<PubSysVersion> getPubSysversionList(@RequestBody PubSysVersion object) {
		return sysversionService.getPubSysversionList(object);
	}
	
	/**
	 * 查询版本说明中已存在的当前版本号
	 * @param curversion
	 * @return
	 */
	@RequestMapping(value = "api/PubSysversion/GetCurversionByList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getCurversionByList(@RequestBody PubSysVersion object) {
		return sysversionService.getCurversionByList(object);
	}
	
	/**
	 * 分页查询版本说明数据
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubSysversion/GetPubSysversionByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubSysversionByQuery(@RequestBody PubSysversionQueryParam queryParam) {
		return sysversionService.getPubSysversionByQuery(queryParam);
	}
	
	/**
	 * 根据id查询版本说明详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/PubSysversion/GetPubSysversionById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<PubSysVersion> getPubSysversionById(@PathVariable String id) {
		return sysversionService.getPubSysversionById(id);
	}
	
	/**
	 * 添加版本说明
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/PubSysversion/CreatePubSysversion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPubSysversion(@RequestBody PubSysVersion object) {
		return sysversionService.createPubSysversion(object);
	}
	
	/**
	 * 修改版本说明
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/PubSysversion/EditPubSysversion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editPubSysversion(@RequestBody PubSysVersion object) {
		return sysversionService.editPubSysversion(object);
	}
	
	/**
	 * 删除版本说明
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/PubSysversion/DeletePubSysversion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deletePubSysversion(@RequestBody PubSysVersion object) {
		return sysversionService.deletePubSysversion(object);
	}

}
