package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.dto.pubAlarmprocess.SavePubAlarmprocessDto;
import com.szyciov.entity.Retcode;
import com.szyciov.lease.entity.PubRoleId;
import com.szyciov.lease.param.PubAlarmprocessParam;
import com.szyciov.operate.service.PubAlarmprocessService;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

@Controller
public class PubAlarmprocessController {
	Logger logger = LoggerFactory.getLogger(PubAlarmprocessController.class);
	public PubAlarmprocessService pubAlarmprocessService;
	@Resource(name = "pubAlarmprocessService")
	public void setLeDriverorderstatisticsService(PubAlarmprocessService pubAlarmprocessService) {
		this.pubAlarmprocessService = pubAlarmprocessService;
	}
	@RequestMapping(value = "api/PubAlarmprocess/PubAlarmprocessParam", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubAlarmprocessByQuery(@RequestBody PubAlarmprocessParam pubAlarmprocessParam)  {
		return pubAlarmprocessService.getPubAlarmprocessByQuery(pubAlarmprocessParam);
	}
	@RequestMapping(value = "api/PubAlarmprocess/GetPubAlarmprocessDetail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubAlarmprocessParam getPubAlarmprocessDetail(@PathVariable String id){
		return pubAlarmprocessService.getPubAlarmprocessDetail(id);
	}
	@RequestMapping(value = "api/PubAlarmprocess/UpdataDetail", method = RequestMethod.POST)
	@ResponseBody
	public int updataDetail(@RequestBody PubAlarmprocessParam pubAlarmprocessParam)  {
		return pubAlarmprocessService.updataDetail(pubAlarmprocessParam);
	}
	@RequestMapping(value = "api/PubAlarmprocess/GetPubAlarmprocessDriver", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPubAlarmprocessDriver(@RequestBody PubAlarmprocessParam pubAlarmprocessParam)  {
		return pubAlarmprocessService.getPubAlarmprocessDriver(pubAlarmprocessParam);
	}
	@RequestMapping(value = "api/PubAlarmprocess/GetPubAlarmprocessPassenger", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPubAlarmprocessPassenger(@RequestBody PubAlarmprocessParam pubAlarmprocessParam)  {
		return pubAlarmprocessService.getPubAlarmprocessPassenger(pubAlarmprocessParam);
	}
	@RequestMapping(value = "api/PubAlarmprocess/GetPubAlarmprocessPlateno", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPubAlarmprocessPlateno(@RequestBody PubAlarmprocessParam pubAlarmprocessParam)  {
		return pubAlarmprocessService.getPubAlarmprocessPlateno(pubAlarmprocessParam);
	}
	@RequestMapping(value = "api/PubAlarmprocess/OrdernoOK", method = RequestMethod.POST)
	@ResponseBody
	public int ordernoOK(@RequestBody PubRoleId pubRoleId)  {
		return pubAlarmprocessService.ordernoOK(pubRoleId);
	}
	@RequestMapping(value = "api/PubAlarmprocess/HandleOK", method = RequestMethod.POST)
	@ResponseBody
	public int handleOK(@RequestBody PubAlarmprocessParam pubAlarmprocessParam)  {
		return pubAlarmprocessService.handleOK(pubAlarmprocessParam);
	}
	@RequestMapping(value = "api/PubAlarmprocess/carOrder", method = RequestMethod.POST)
	@ResponseBody
	public int carOrder(@RequestBody PubRoleId pubRoleId)  {
		return pubAlarmprocessService.carOrder(pubRoleId);
	}
	/**
	 * 报警管理
	 */
	  @RequestMapping("api/pubAlarmprocess/apply")
	  @ResponseBody
	  public JSONObject pubAlarmprocess(@RequestBody SavePubAlarmprocessDto saveDto){
		  JSONObject jsonObject = new JSONObject();
	        try {
	            	pubAlarmprocessService.save(saveDto);
	                jsonObject.put("status", Retcode.OK.code);
//	                jsonObject.put("data", infoDto);
	        }catch (Exception e){
	            jsonObject.put("status", Retcode.EXCEPTION.code);
	            jsonObject.put("message", "系统繁忙请稍后再试！");
	            logger.error("新增交班记录异常："+ GsonUtil.toJson(saveDto),e);
	        }

	        return jsonObject;
		  
	  }
}
