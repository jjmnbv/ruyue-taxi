package com.szyciov.operate.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.operate.service.PubCityAddrService;
import com.szyciov.operate.service.PubDictionaryService;
import com.szyciov.util.BaseController;

import net.sf.json.JSONObject;

/**
 * 公共数据管理
 */
@Controller
public class PubInfoController extends BaseController {
	
	private PubCityAddrService cityAddrService;
	@Resource(name = "PubCityAddrService")
	public void setCityAddrService(PubCityAddrService cityAddrService) {
		this.cityAddrService = cityAddrService;
	}
	
	private PubDictionaryService dictionaryService;
	@Resource(name = "PubDictionaryService")
	public void setDictionaryService(PubDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
	/**
	 * 查询所有城市数据
	 * @return
	 */
	@RequestMapping(value = "api/PubInfo/GetPubCityAddrByList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getPubCityAddrByList() {
		return cityAddrService.getPubCityAddrByList();
	}
	
	/**
	 * 查询字典数据
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/PubInfo/GetPubDictionaryByList", method = RequestMethod.POST)
	@ResponseBody
	public List<PubDictionary> getPubDictionaryByList(@RequestBody PubDictionary object) {
		return dictionaryService.getPubDictionaryByList(object);
	}
	

}
