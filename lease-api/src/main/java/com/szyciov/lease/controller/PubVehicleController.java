package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import BP.Tools.Json;
import com.szyciov.entity.Retcode;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.param.PubVehicleQueryParam;
import com.szyciov.lease.service.PubVehicleService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 控制器
 */
@Controller
public class PubVehicleController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubVehicleController.class);

	public PubVehicleService pubVehicleService;

	@Resource(name = "PubVehicleService")
	public void setPubVehicleService(PubVehicleService pubVehicleService) {
		this.pubVehicleService = pubVehicleService;
	}

	/**
	 * <p>
	 * 增加一条记录
	 * </p>
	 *
	 * @param PubVehicle
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/CreatePubVehicle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPubVehicle(@RequestBody PubVehicle pubVehicle) {
		return pubVehicleService.createPubVehicle(pubVehicle);
	}
	
	/**
	 * <p>
	 * 查询
	 * </p>
	 *
	 * @param PubVehicle
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetPubVehicleByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubVehicleByQuery(@RequestBody PubVehicleQueryParam pubVehicleQueryParam){
		return pubVehicleService.getPubVehicleByQuery(pubVehicleQueryParam);
	}
	
	/**
	 * <p>
	 * 得到品牌车系
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetBrandCars", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getBrandCars(@RequestBody PubVehicle pubVehicle) {
		return pubVehicleService.getBrandCars(pubVehicle);
	}
	
	/**
	 * <p>
	 * 得到服务车型
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetServiceCars/{leasesCompanyId}", method = RequestMethod.GET)
	@ResponseBody
	public List<PubVehicle> getServiceCars(@PathVariable String leasesCompanyId) {
		return pubVehicleService.getServiceCars(leasesCompanyId);
	}
	
	/**
	 * <p>
	 * 得到城市
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetCity/{leasesCompanyId}", method = RequestMethod.GET)
	@ResponseBody
	public List<PubVehicle> getCity(@PathVariable String leasesCompanyId) {
		return pubVehicleService.getCity(leasesCompanyId);
	}
	
	/**
	 * <p>
	 * 根据id查询
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubVehicle getById(@PathVariable String id) {
		return pubVehicleService.getById(id);
	}
	
	/**
	 * <p>
	 * 车辆导出
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/ExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public List<PubVehicle> exportExcel(@RequestBody PubVehicleQueryParam pubVehicleQueryParam) {
		return pubVehicleService.exportExcel(pubVehicleQueryParam);
	}
	
	/**
	 * <p>
	 * 修改一条记录
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/UpdatePubVehicle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updatePubVehicle(@RequestBody PubVehicle pubVehicle) {
		return pubVehicleService.updatePubVehicle(pubVehicle);
	}
	
	/**
	 * <p>
	 * 根据id查询 检查删除
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/CheckDelete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public int checkDelete(@PathVariable String id) {
		return pubVehicleService.checkDelete(id);
	}
	
	/** 
	 * <p>删除一条记录</p>
	 *
	 * @param id 待删除记录对应的序列号
	 */
	@RequestMapping(value = "api/PubVehicle/DeletePubVehicle/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deletePubVehcline(@PathVariable String id) {
		return pubVehicleService.deletePubVehicle(id);
	}
	
	/**
	 * <p>
	 * 得到pub_cityaddr
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetPubCityaddr", method = RequestMethod.GET)
	@ResponseBody
	public List<City> getPubCityaddr() {
		return pubVehicleService.getPubCityaddr();
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetPlateNoProvince", method = RequestMethod.GET)
	@ResponseBody
	public List<Dictionary> getPlateNoProvince() {
		return pubVehicleService.getPlateNoProvince();
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetPlateNoCity/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Dictionary> getPlateNoCity(@PathVariable String id) {
		return pubVehicleService.getPlateNoCity(id);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/CheckPubVehicle", method = RequestMethod.POST)
	@ResponseBody
	public int checkPubVehicle(@RequestBody PubVehicle pubVehicle) {
		return pubVehicleService.checkPubVehicle(pubVehicle);
	}
	@RequestMapping(value = "api/PubVehicle/GetPlateCode/{plateOne}", method = RequestMethod.POST)
	@ResponseBody
	public PubDictionary getPlateCode(@PathVariable String plateOne) {
		return pubVehicleService.getPlateCode(plateOne);
	}
	@RequestMapping(value = "api/PubVehicle/GetPlateCity", method = RequestMethod.POST)
	@ResponseBody
	public String getPlateCity(@RequestBody PubDictionary plateTow) {
		return pubVehicleService.getPlateCity(plateTow);
	}
	@RequestMapping(value = "api/PubVehicle/GetCityCode/{city}", method = RequestMethod.POST)
	@ResponseBody
	public PubCityAddr getCityCode(@PathVariable String city) {
		return pubVehicleService.getCityCode(city);
	}
	@RequestMapping(value = "api/PubVehicle/GetVehclineId", method = RequestMethod.POST)
	@ResponseBody
	public String getVehclineId(@RequestBody PubVehicle vehcline) {
		return pubVehicleService.getVehclineId(vehcline);
	}


	/**
	 * 返回车辆信息及绑定信息
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/bind/list")
	@ResponseBody
	public JSONObject listPubVehicleBind(@RequestBody PubVehicleQueryParam pubVehicleQueryParam) {

		JSONObject jsonObject = new JSONObject();
		try {
			PageBean page = pubVehicleService.listVehicleAndBindInfo(pubVehicleQueryParam);
			if(page!=null) {
				jsonObject.put("data", JSONObject.fromObject(page));
				jsonObject.put("status", Retcode.OK.code);
			}else{
				jsonObject.put("status", Retcode.FAILED.code);
			}
		}catch (Exception e){
			jsonObject.put("status", Retcode.EXCEPTION.code);
			logger.error("返回车辆信息及绑定信息："+ JSONObject.fromObject(pubVehicleQueryParam),e);
		}

		return jsonObject;
	}





}
