package com.szyciov.operate.controller;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.BindingStateEnum;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.op.entity.PubVehicle;
import com.szyciov.op.param.PubVehicleQueryParam;
import com.szyciov.op.param.vehicleManager.VehicleIndexQueryParam;
import com.szyciov.operate.service.PubVehicleService;
import com.szyciov.util.BaseController;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.PageBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 */
@Controller
public class PubVehicleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PubVehicleController.class);

	@Autowired
	public PubVehicleService pubVehicleService;

	/**
	 * <p>
	 * 增加一条记录
	 * </p>
	 *
	 * @param pubVehicle
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/CreatePubVehicle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPubVehicle(@RequestBody PubVehicle pubVehicle) {
		Map<String, String> ret = new HashMap<String, String>();
		try{
			//检查车牌号
			int count = pubVehicleService.checkPubVehicle(pubVehicle);
			if(count>0){
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "车牌号已存在");
			}else{
				String id = pubVehicleService.createPubVehicle(pubVehicle);
				ret.put("ResultSign", "Successful");
				ret.put("MessageKey", "创建成功");
				ret.put("data", id);
			}
		}catch (Exception e){
			logger.error("保存车辆异常：param:{}",GsonUtil.toJson(pubVehicle),e);
		}
		return ret;
	}
	
	/**
	 * <p>
	 * 查询
	 * </p>
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetPubVehicleByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubVehicleByQuery(@RequestBody VehicleIndexQueryParam param){
		return pubVehicleService.getPubVehicleByQuery(param);
	}
	
	/**
	 * <p>
	 * 得到品牌车系
	 * </p>
	 *
	 * @param pubVehicle
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
	 * @param platformType
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetServiceCars/{platformType}", method = RequestMethod.GET)
	@ResponseBody
	public List<PubVehicle> getServiceCars(@PathVariable String platformType) {
		return pubVehicleService.getServiceCars(platformType);
	}
	
	/**
	 * <p>
	 * 得到城市
	 * </p>
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetCity")
	@ResponseBody
	public List<City> getCity(@RequestBody PubVehicleQueryParam param) {
		return pubVehicleService.getCity(param.getQueryCity(),param.getPlatformtype());
	}
	
	/**
	 * <p>
	 * 根据id查询
	 * </p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubVehicle getById(@PathVariable String id) {
		return pubVehicleService.getById(id);
	}

	/**
	 * <p>
	 * 返回车辆数据
	 * </p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/getEditVehicle/{id}", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getEditVehicle(@PathVariable String id) {
		JSONObject jsonObject = null;
		try{
			//获取车辆状态
			PubVehicle pubVehicle = pubVehicleService.getById(id);
			if(pubVehicle!=null) {
				jsonObject = new JSONObject();
				//获取绑定状态
				String boundState = pubVehicle.getBoundState();
				if (BindingStateEnum.UN_BINDING.code.equals(boundState)) {
					jsonObject.put("status", Retcode.OK.code);
					jsonObject.put("data", JSONObject.fromObject(pubVehicle));
				}else{
					jsonObject.put("status", Retcode.FAILED.code);
					jsonObject.put("message", "当前车辆已绑定司机，请解绑后再修改");
				}
			}
		}catch (Exception e){
			logger.error("获取修改车辆消息出错：param:{}",id,e);
		}


		return jsonObject ;
	}


	/**
	 * <p>
	 * 车辆导出
	 * </p>
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/ExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray exportExcel(@RequestBody VehicleIndexQueryParam param) {
		return JSONArray.fromObject(pubVehicleService.exportExcel(param));
	}
	
	/**
	 * <p>
	 * 修改一条记录
	 * </p>
	 * @param pubVehicle
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicle/UpdatePubVehicle", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updatePubVehicle(@RequestBody PubVehicle pubVehicle) {
		JSONObject jsonObject = null;
		try{
			jsonObject = new JSONObject();
			//检查车牌
			int count = pubVehicleService.checkPubVehicle(pubVehicle);

			if(count>0){
				jsonObject.put("ResultSign", "Error");
				jsonObject.put("MessageKey", "车牌号已存在");
			}else {
				if(pubVehicleService.updatePubVehicle(pubVehicle)==1){
					jsonObject.put("ResultSign", "Successful");
					jsonObject.put("MessageKey", "修改成功");
				}else{
					jsonObject.put("ResultSign", "Error");
					jsonObject.put("MessageKey", "修改失败，该车辆不存在！");
				}
			}
		}catch (Exception e){
			logger.error("修改车辆出错：param{}", GsonUtil.toJson(pubVehicle),e);
		}
		return jsonObject;
	}
	
	/**
	 * <p>
	 * 根据id查询 检查删除
	 * </p>
	 *
	 * @param id
	 * @return

	@RequestMapping(value = "api/PubVehicle/CheckDelete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public int checkDelete(@PathVariable String id) {
		return pubVehicleService.checkDelete(id);
	}*/
	
	/** 
	 * <p>删除一条记录</p>
	 *
	 * @param id 待删除记录对应的序列号
	 */
	@RequestMapping(value = "api/PubVehicle/DeletePubVehicle/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject deletePubVehcline(@PathVariable String id) {
		JSONObject jsonObject = null;
		try{
			//获取车辆状态
			PubVehicle pubVehicle = pubVehicleService.getById(id);
			if(pubVehicle!=null) {
				jsonObject = new JSONObject();
				//获取绑定状态
				String boundState = pubVehicle.getBoundState();
				if (BindingStateEnum.UN_BINDING.code.equals(boundState)) {
					pubVehicleService.deletePubVehicle(id);
					jsonObject.put("status", Retcode.OK.code);
					jsonObject.put("message", "删除成功！");
				}else{
					jsonObject.put("status", Retcode.FAILED.code);
					jsonObject.put("message", "当前车辆已绑定司机，请解绑后再删除");
				}
			}
		}catch (Exception e){
			logger.error("删除车辆出错：param{}", id,e);
		}
		return jsonObject;
	}
	
	/**
	 * <p>
	 * 得到pub_cityaddr
	 * </p>
	 *
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
	 * @param id
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
	 * @param pubVehicle
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

    /**
     * 根据用户查询拥有权限的所有车辆信息
     * @param param
     * @return
     */
    @RequestMapping(value = "api/PubVehicle/GetPubVehicleSelectByUser")
    @ResponseBody
	public List<Map<String, Object>> getPubVehicleSelectByUser(@RequestBody Map<String, Object> param) {
        return pubVehicleService.getPubVehicleSelectByUser(param);
    }

}
