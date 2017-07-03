package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.dto.driverShiftManagent.ProcessedSaveDto;
import com.szyciov.entity.Retcode;
import com.szyciov.op.entity.OpTaxiBind;
import com.szyciov.op.param.PubDriverVehicleRefQueryParam;
import com.szyciov.op.param.TaxiBindQueryParam;
import com.szyciov.operate.service.TaxiBindService;
import com.szyciov.util.BaseController;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

/**
 * 出租车绑定模块控制器
 * @author chenjunfeng_pc
 *
 */
@Controller
public class TaxiBindController extends BaseController {
	private static final Logger logger = Logger.getLogger(TaxiBindController.class);

	public TaxiBindService taxiBindService;

	@Resource(name = "TaxiBindService")
	public void setTaxiBindService(TaxiBindService taxiBindService) {
		this.taxiBindService = taxiBindService;
	}
	
	/** 
	 * <p>分页查询服务车辆信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/TaxiBind/GetVehicleInfoByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getVehicleInfoByQuery(@RequestBody TaxiBindQueryParam queryParam) {
		return taxiBindService.getVehicleInfoByQuery(queryParam);
	}
	
	/** 
	 * <p>登记城市</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "api/TaxiBind/GetCityaddr", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getCityaddr() {	
		return taxiBindService.getCityaddr();
	}
	
	/** 
	 * <p>当班司机</p>
	 *
	 * @param driver 姓名/手机号
	 * @return
	 */
	@RequestMapping(value = "api/TaxiBind/GetOndutyDriver", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getOndutyDriver(@RequestParam(value = "driver", required = false) String driver) {
		logger.log(Level.INFO, "driver:" + driver);		
		return taxiBindService.getOndutyDriver(driver);
	}
	
	/** 
	 * <p>品牌车系</p>
	 *
	 * @param vehcbrandname 品牌车系
	 * @return
	 */
	@RequestMapping(value = "api/TaxiBind/GetVehcbrandVehcline", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getVehcbrandVehcline(@RequestParam(value = "vehcbrandname", required = false) String vehcbrandname) {
		logger.log(Level.INFO, "vehcbrandname:" + vehcbrandname);		
		return taxiBindService.getVehcbrandVehcline(vehcbrandname);
	}
	
	/** 
	 * <p>绑定司机，资格证号</p>
	 *
	 * @param city 城市
	 * @param jobnum 资格证号
	 * @return
	 */
	@RequestMapping(value = "api/TaxiBind/GetDriverByJobnum", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getDriverByJobnum(@RequestParam(value = "city", required = true) String city,
			@RequestParam(value = "jobnum", required = false) String jobnum) {
		logger.log(Level.INFO, "city:" + city);
		logger.log(Level.INFO, "jobnum:" + jobnum);
		return taxiBindService.getDriverByJobnum(city, jobnum);
	}
	
	/** 
	 * <p>绑定司机，司机</p>
	 *
	 * @param city 城市
	 * @param driver 司机姓名/手机号
	 * @return
	 */
	@RequestMapping(value = "api/TaxiBind/GetDriverByNameOrPhone", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getDriverByNameOrPhone(@RequestParam(value = "city", required = true) String city,
			@RequestParam(value = "driver", required = false) String driver) {
		logger.log(Level.INFO, "city:" + city);
		logger.log(Level.INFO, "driver:" + driver);
		return taxiBindService.getDriverByNameOrPhone(city, driver);
	}
	
	/** 
	 * <p>绑定司机，分页查询未绑定司机信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/TaxiBind/GetUnbindDriverByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getUnbindDriverByQuery(@RequestBody PubDriverVehicleRefQueryParam queryParam) {
		return taxiBindService.getUnbindDriverByQuery(queryParam);
	}
	
	/** 
	 * <p>绑定操作</p>
	 *
	 * @param opTaxiBind
	 * @return
	 */
	@RequestMapping(value = "api/TaxiBind/CreatePubDriverVehicleRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPubDriverVehicleRef(@RequestBody OpTaxiBind opTaxiBind) {
		return taxiBindService.createPubDriverVehicleRef(opTaxiBind);
	}
	
	/** 
	 * <p>解绑司机，可解绑司机</p>
	 *
	 * @param vehicleid 车辆id
	 * @return
	 */
	@RequestMapping(value = "api/TaxiBind/GetBindDriverByVehicleid", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getBindDriverByVehicleid(@RequestParam(value = "vehicleid", required = true) String vehicleid) {
		logger.log(Level.INFO, "vehicleid:" + vehicleid);
		return taxiBindService.getBindDriverByVehicleid(vehicleid);
	}
	
	/** 
	 * <p>解绑操作</p>
	 *
	 * @param opTaxiBind
	 * @return
	 */
	@RequestMapping(value = "api/TaxiBind/UpdatePubDriverVehicleRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updatePubDriverVehicleRef(@RequestBody OpTaxiBind opTaxiBind) {
		return taxiBindService.updatePubDriverVehicleRef(opTaxiBind);
	}
	
	
	/** 
	 * <p>分页查询操作记录</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/TaxiBind/GetOperateRecordByVehicleid", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOperateRecordByVehicleid(@RequestBody TaxiBindQueryParam queryParam) {
		return taxiBindService.getOperateRecordByVehicleid(queryParam);
	}
	
	
	/** 
	 * <p>人工指派时，查找绑定的司机</p>
	 *
	 * @param vehicleid 车辆id
	 * @return
	 */
	@RequestMapping(value = "api/TaxiBind/ListTaxiBindDriver/{vehicleid}", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> listTaxiBindDriver(@PathVariable String vehicleid) {
		logger.log(Level.INFO, "vehicleid:" + vehicleid);
		return taxiBindService.listTaxiBindDriver(vehicleid);
	}
	
	
	/** 
	 * <p>人工指派时，当班司机处理</p>
	 *
	 * @param processedSaveDto
	 * @return
	 */
    @RequestMapping(value = "api/TaxiBind/SaveAssign", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveAssign(@RequestBody ProcessedSaveDto processedSaveDto, HttpServletRequest request,HttpServletResponse response) {

        JSONObject jsonObject = new JSONObject();
        try {
        	taxiBindService.saveAssign(processedSaveDto);
            jsonObject.put("status", Retcode.OK.code);
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("指定当班司机异常："+ GsonUtil.toJson(processedSaveDto),e);
        }
        return jsonObject;
    }
    
    /** 
	 * <p>判断是否需要弹出人工指派的弹窗</p>
	 *
	 * @param vehicleId
	 * @return
	 */
    @RequestMapping("api/TaxiBind/IsAssign/{vehicleId}")
    @ResponseBody
    public JSONObject isAssign(@PathVariable String vehicleId) {

        JSONObject jsonObject = new JSONObject();

        try {
            boolean isshow = taxiBindService.isAssign(vehicleId);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data",isshow);
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("新增交班记录异常："+ vehicleId,e);
        }

        return jsonObject;
    }
    
    /** 
	 * <p>查找所有绑定的司机</p>
	 *
	 * @param vehicleid 车辆id
	 * @return
	 */
	@RequestMapping(value = "api/TaxiBind/GetBindDriversByVehicleid/{vehicleid}", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getBindDriversByVehicleid(@PathVariable String vehicleid) {
		logger.log(Level.INFO, "vehicleid:" + vehicleid);
		return taxiBindService.getBindDriversByVehicleid(vehicleid);
	}
	
}
