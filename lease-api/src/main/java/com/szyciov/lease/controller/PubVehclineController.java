package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.PubVehcline;
import com.szyciov.lease.param.PubVehclineQueryParam;
import com.szyciov.lease.service.PubVehclineService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

/**
 * 控制器
 */
@Controller
public class PubVehclineController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubVehclineController.class);

	public PubVehclineService pubVehclineService;

	@Resource(name = "PubVehclineService")
	public void setPubVehclineService(PubVehclineService pubVehclineService) {
		this.pubVehclineService = pubVehclineService;
	}

	/**
	 * <p>
	 * 增加一条记录
	 * </p>
	 *
	 * @param PubVehcline
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcline/CreatePubVehcline", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPubVehcline(@RequestBody PubVehcline pubVehcline) {
		return pubVehclineService.createPubVehcline(pubVehcline);
	}
	/**
	 * <p>
	 * 初始化、查询
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcline/GetPubVehclineByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubVehclineByQuery(@RequestBody PubVehclineQueryParam queryParam) {
		return pubVehclineService.getPubVehclineByQuery(queryParam);
	}
	
	/**
	 * <p>
	 * 得到品牌车系
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcline/GetBrandCars", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getBrandCars(@RequestBody PubVehcline pubVehcline) {
		return pubVehclineService.getBrandCars(pubVehcline);
	}
	
	/**
	 * <p>
	 * 修改一条记录
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcline/UpdatePubVehcline", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updatePubVehcline(@RequestBody PubVehcline pubVehcline) {
		return pubVehclineService.updatePubVehcline(pubVehcline);
	}
	
	/**
	 * <p>
	 * 根据id查询
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcline/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubVehcline getById(@PathVariable String id) {
		return pubVehclineService.getById(id);
	}
	
	/** 
	 * <p>删除一条记录</p>
	 *
	 * @param id 待删除记录对应的序列号
	 */
	@RequestMapping(value = "api/PubVehcline/DeletePubVehcline/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deletePubVehcline(@PathVariable String id) {
		return pubVehclineService.deletePubVehcline(id);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcline/CheckImprot", method = RequestMethod.POST)
	@ResponseBody
	public int checkImprot(@RequestBody PubVehcline pubVehcline) {
		return pubVehclineService.checkImprot(pubVehcline);
	}
	
	/**
	 * 查询所有车品牌数据
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcline/GetBrand/{id}", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getBrand(@PathVariable String id) {
		return pubVehclineService.getBrand(id);
	}
	
	/**
	 * 查询所有车品牌数据
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcline/GetBrands/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<PubVehcbrand> getBrands(@PathVariable String id) {
		return pubVehclineService.getBrands(id);
	}
}
