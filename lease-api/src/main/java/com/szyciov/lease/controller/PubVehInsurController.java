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

import com.szyciov.dto.pubVehInsur.PubVehInsurQueryDto;
import com.szyciov.entity.PubVehInsur;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.param.pubVehInsurance.AddPubVehInsurs;
import com.szyciov.lease.param.pubVehInsurance.PubVehInsurQueryParam;
import com.szyciov.lease.service.PubVehInsurService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

@Controller
public class PubVehInsurController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubVehInsurController.class);
    
	public PubVehInsurService pubVehInsurService;

	@Resource(name = "PubVehInsurService")
	public void setPubVehInsurService(PubVehInsurService pubVehInsurService) {
		this.pubVehInsurService = pubVehInsurService;
	}
	
	/**
	 * 车辆保险分页查询
	 */
	@RequestMapping(value = "api/PubVehInsur/GetPubVehInsurByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubDriverByQuery(@RequestBody PubVehInsurQueryParam pubVehInsurQueryParam){
		return pubVehInsurService.getPubVehInsurByQuery(pubVehInsurQueryParam);
	}
	
	/**
	 * 删除车辆保险
	 */
	@RequestMapping(value = "api/PubVehInsur/DeletePubVehInsur/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deletePubVehInsur(@PathVariable("id") String id) {
		return pubVehInsurService.deletePubVehInsur(id);
	}
	
	/**
	 * 更新车辆保险
	 */
	@RequestMapping(value = "api/PubVehInsur/UpdatePubVehInsur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updatePubVehInsur(@RequestBody PubVehInsur pubDriver){
		return pubVehInsurService.updatePubVehInsur(pubDriver);
	}
	
	/**
	 * 车辆保险编辑页
	 */
	@RequestMapping(value = "api/PubVehInsur/EditIndex/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubVehInsurQueryDto editInsurIndex(@PathVariable("id") String id){
		return pubVehInsurService.getPubVehInsurById(id);
	}
	
	/**
	 * 添加车辆保险
	 */
	@RequestMapping(value = "api/PubVehInsur/AddPubVehInsurs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addPubVehInsurs(@RequestBody AddPubVehInsurs pubInsurs){
		return pubVehInsurService.addPubVehInsurs(pubInsurs);	
	}
	
	/**
	 * 车辆导出
	 */
	@RequestMapping(value = "api/PubVehInsur/ExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public List<PubVehInsurQueryDto> exportExcel(@RequestBody PubVehInsurQueryParam pubVehInsurQueryParam) {
		return pubVehInsurService.exportExcel(pubVehInsurQueryParam);
	}
	
	/**
	 * 检查车辆是否存在
	 */
	@RequestMapping(value = "api/PubVehInsur/checkPubVehicle", method = RequestMethod.GET)
	@ResponseBody
	public PubVehicle checkPubVehicle(String plateNo){
		return pubVehInsurService.checkPubVehicle(plateNo);
	}
}
