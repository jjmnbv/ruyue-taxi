package com.szyciov.operate.controller;

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

import com.szyciov.op.entity.PubVehcbrand;
import com.szyciov.op.param.PubVehcbrandQueryParam;
import com.szyciov.operate.service.PubVehcbrandService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 控制器
 */
@Controller
public class PubVehcbrandController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubVehcbrandController.class);

	public PubVehcbrandService pubVehcbrandService;

	@Resource(name = "PubVehcbrandService")
	public void setPubVehcbrandService(PubVehcbrandService pubVehcbrandService) {
		this.pubVehcbrandService = pubVehcbrandService;
	}

	/**
	 * <p>
	 * 增加一条记录
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcbrand/CreatePubVehcbrand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPubVehcbrand(@RequestBody PubVehcbrand pubVehcbrand) {
		return pubVehcbrandService.createPubVehcbrand(pubVehcbrand);
	}
	
	/**
	 * <p>
	 * 修改一条记录
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcbrand/UpdatePubVehcbrand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updatePubVehcbrand(@RequestBody PubVehcbrand pubVehcbrand) {
		return pubVehcbrandService.updatePubVehcbrand(pubVehcbrand);
	}
	
	/**
	 * <p>
	 * 根据id查询
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcbrand/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubVehcbrand getById(@PathVariable String id) {
		return pubVehcbrandService.getById(id);
	}
	
	/** 
	 * <p>删除一条记录</p>
	 *
	 * @param id 待删除记录对应的序列号
	 */
	@RequestMapping(value = "api/PubVehcbrand/DeletePubVehcbrand/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deletePubVehcbrand(@PathVariable String id) {
		return pubVehcbrandService.deletePubVehcbrand(id);
	}
	
	/**
	 * <p>
	 * 初始化、查询
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcbrand/GetPubVehcbrandByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubVehcbrandByQuery(@RequestBody PubVehcbrandQueryParam queryParam) {
		return pubVehcbrandService.getPubVehcbrandByQuery(queryParam);
	}
	
	/**
	 * <p>
	 * 得到品牌
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcbrand/GetBrand", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getBrand(@RequestBody PubVehcbrand pubVehcbrand) {
		return pubVehcbrandService.getBrand(pubVehcbrand);
	}
	
	/**
	 * <p>
	 * 导出
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcbrand/ExportData", method = RequestMethod.POST)
	@ResponseBody
	public List<PubVehcbrand> exportData(@RequestBody Map<String, String> map) {
		return pubVehcbrandService.exportData(map);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcbrand/GetByName", method = RequestMethod.POST)
	@ResponseBody
	public PubVehcbrand getByName(@RequestBody PubVehcbrand pubVehcbrand) {
		return pubVehcbrandService.getByName(pubVehcbrand);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehcbrand/checkBrand", method = RequestMethod.POST)
	@ResponseBody
	public int checkBrand(@RequestBody PubVehcbrand pubVehcbrand) {
		return pubVehcbrandService.checkBrand(pubVehcbrand);
	}
}
