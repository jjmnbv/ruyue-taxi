package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.op.param.PubDriverVehicleBindQueryParam;
import com.szyciov.op.param.PubDriverVehicleRefQueryParam;
import com.szyciov.operate.service.PubDriverVehicleRefService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 网约车绑定模块控制器
 */
@Controller
public class PubDriverVehicleRefController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubDriverVehicleRefController.class);
	
	@Autowired
    private PubDriverVehicleRefService refService;
	
	/** 
	 * <p>分页查询服务司机信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetDriverInfoByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getDriverInfoByQuery(@RequestBody PubDriverVehicleRefQueryParam queryParam) {
		return refService.getDriverInfoByQuery(queryParam);
	}
	
	/** 
	 * <p>司机</p>
	 *
	 * @param driver 司机姓名/手机号码
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetDriverByNameOrPhone", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getDriverByNameOrPhone(
			@RequestParam(value = "driver", required = false) String driver,
			@RequestParam(value = "vehicletype", required = true) String vehicletype,
			@RequestParam(value = "jobstatus", required = false) String jobstatus) {
		logger.log(Level.INFO, "driver:" + driver);
		logger.log(Level.INFO, "vehicletype:" + vehicletype);
		logger.log(Level.INFO, "jobstatus:" + jobstatus);
		return refService.getDriverByNameOrPhone(vehicletype, driver, jobstatus);
	}
	
	/** 
	 * <p>资格证号</p>
	 *
	 * @param jobnum 资格证号
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetDriverByJobnum", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getDriverByJobnum(@RequestParam(value = "jobnum", required = false) String jobnum,
			@RequestParam(value = "jobstatus", required = false) String jobstatus) {
		logger.log(Level.INFO, "jobnum:" + jobnum);
		logger.log(Level.INFO, "jobstatus:" + jobstatus);
		return refService.getDriverByJobnum(jobnum, jobstatus);
	}
	
	/** 
	 * <p>服务车型</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetVehiclemodels", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getVehiclemodels() {		
		return refService.getVehiclemodels();
	}
	
	/** 
	 * <p>登记城市</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetCityaddr", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getCityaddr() {		
		return refService.getCityaddr();
	}
	
	/** 
	 * <p>品牌车系</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetVehcbrand", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getVehcbrand(@RequestParam(value = "vehcbrandname", required = false) String vehcbrandname) {		
		return refService.getVehcbrand(vehcbrandname);
	}
	
	
	
	
	/** 
	 * <p>绑定车辆时，查询品牌车系下拉框</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetVehcbrandByCity/{city}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getVehcbrandByCity(@PathVariable String city, @RequestParam(value = "vehcbrandname", required = false) String vehcbrandname) {		
		logger.log(Level.INFO, "city:" + city);
		logger.log(Level.INFO, "vehcbrandname:" + vehcbrandname);
		return refService.getVehcbrandByCity(city, vehcbrandname);
	}
	
	/** 
	 * <p>分页查询未绑定车辆信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetUnbandCarsByCity", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getUnbandCarsByCity(@RequestBody PubDriverVehicleRefQueryParam queryParam) {
		return refService.getUnbandCarsByCity(queryParam);
	}
	
	/** 
	 * <p>绑定操作</p>
	 *
	 * @param pubDriverVehicleBind
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/CreatePubDriverVehicleRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPubDriverVehicleRef(@RequestBody PubDriverVehicleBind pubDriverVehicleBind) {
		return refService.createPubDriverVehicleRef(pubDriverVehicleBind);
	}
	
	/** 
	 * <p>解绑时，需判断当前司机是否存在未完成订单</p>
	 *
	 * @param driverid 司机id
	 * @param vehicleid 车辆id
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/JudgeUncompleteOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> judgeUncompleteOrder(@RequestParam(value = "driverid", required = true) String driverid, @RequestParam(value = "vehicleid", required = true) String vehicleid) {
		return refService.judgeUncompleteOrder(driverid, vehicleid);
	}
	
	/** 
	 * <p>解绑操作</p>
	 *
	 * @param pubDriverVehicleBind
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/UpdatePubDriverVehicleRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updatePubDriverVehicleRef(@RequestBody PubDriverVehicleBind pubDriverVehicleBind) {
		return refService.updatePubDriverVehicleRef(pubDriverVehicleBind);
	}
	
	
	
	
	/** 
	 * <p>分页查询司机操作记录</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetDriverOpRecordByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getDriverOpRecordByQuery(@RequestBody PubDriverVehicleBindQueryParam queryParam) {
		return refService.getDriverOpRecordByQuery(queryParam);
	}
	
	/** 
	 * <p>分页查询车辆操作记录</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetVehicleOpRecordByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getVehicleOpRecordByQuery(@RequestBody PubDriverVehicleBindQueryParam queryParam) {
		return refService.getVehicleOpRecordByQuery(queryParam);
	}
	
	/** 
	 * <p>车辆操作记录，车牌号联想下拉框</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetPlatenoByPlateno", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getPlatenoByPlateno(@RequestParam(value = "plateno", required = false) String plateno) {		
		logger.log(Level.INFO, "plateno:" + plateno);
		return refService.getPlatenoByPlateno(plateno);
	}
	
	/** 
	 * <p>车辆操作记录，车架号联想下拉框</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverVehicleRef/GetVehicleVinByVin", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getVehicleVinByVin(@RequestParam(value = "vin", required = false) String vin) {		
		logger.log(Level.INFO, "vin:" + vin);
		return refService.getVehicleVinByVin(vin);
	}
	
}
