package com.szyciov.operate.controller;


import com.szyciov.op.entity.PubVehcbrand;
import com.szyciov.op.entity.PubVehcline;
import com.szyciov.op.param.PubVehclineQueryParam;
import com.szyciov.operate.service.PubVehclineService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
	 * @param pubVehcline
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
	 * @param queryParam
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
	 * @param pubVehcline
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
	 * @param pubVehcline
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
	 * @param id
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
	@RequestMapping(value = "api/PubVehcline/GetBrand", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getBrand() {
		return pubVehclineService.getBrand();
	}
	
	/**
	 * 查询所有车品牌数据
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcline/GetBrands", method = RequestMethod.GET)
	@ResponseBody
	public List<PubVehcbrand> getBrands() {
		return pubVehclineService.getBrands();
	}
}
