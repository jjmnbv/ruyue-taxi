package com.szyciov.carservice.controller;

import com.szyciov.carservice.service.VehicleService;
import com.szyciov.dto.pubvehicle.PubVehicleSelectDto;
import com.szyciov.entity.Retcode;
import com.szyciov.param.pubvehicle.PubVehicleSelectParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;

	/**
	 * 根据车架号联想下拉框  返回车辆ID
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "api/Vehicle/listVehicleBySelect", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject listVehicleBySelect(@RequestBody PubVehicleSelectParam param, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();

		try {
			List<PubVehicleSelectDto> list = this.vehicleService.listVehicleBySelect(param);
			if(list!=null&&list.size()>0) {
				jsonObject.put("data", JSONArray.fromObject(list));
				jsonObject.put("status", Retcode.OK.code);
			}else{
				jsonObject.put("status", Retcode.FAILED.code);
			}
		}catch (Exception e){
			jsonObject.put("status", Retcode.EXCEPTION.code);
		}
		return jsonObject;
	}


	/**
	 * 根据车牌号联想下拉框  返回车辆ID
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "api/Vehicle/listVehicleByPlateno", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject listVehicleByPlateno(@RequestBody PubVehicleSelectParam param, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();

		try {
			List<PubVehicleSelectDto> list = this.vehicleService.listVehicleByPlateno(param);
			if(list!=null&&list.size()>0) {
				jsonObject.put("data", JSONArray.fromObject(list));
				jsonObject.put("status", Retcode.OK.code);
			}else{
				jsonObject.put("status", Retcode.FAILED.code);
			}
		}catch (Exception e){
			jsonObject.put("status", Retcode.EXCEPTION.code);
		}
		return jsonObject;
	}

}
