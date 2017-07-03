package com.szyciov.carservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.szyciov.driver.entity.OrderInfoDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.carservice.service.DriverService;
import com.szyciov.dto.driver.PubDriverSelectDto;
import com.szyciov.entity.Retcode;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.lease.param.PubDriverSelectParam;
import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class DriverController extends ApiExceptionHandle{
	
	private DriverService driverService;

	@Resource(name="driverService")
	public void setDriverService(DriverService driverService) {
		this.driverService = driverService;
	}
	
	/**
	 * 根据订单获取附近司机
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("api/OrderManage/GetAllDriverByQuery")
	@ResponseBody
	public List<Map<String, Object>> getAllDriverByQuery(@RequestBody OrderManageQueryParam queryParam) throws IOException {
		return driverService.getAllDriverByQuery(queryParam);
	}
	
	/**
	 * 查询司机列表(select2)
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/PubDriver/listPubDriverBySelect", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject listPubDriverBySelect(@RequestBody PubDriverSelectParam param, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();

		try {
			List<PubDriverSelectDto> list = this.driverService.listPubDriverBySelect(param);
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
	 * 联想查询资格证号
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "api/PubDriver/listPubDriverBySelectJobNum", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject listPubDriverBySelectJobNum(@RequestBody PubDriverSelectParam param, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();

		try {
			List<PubDriverSelectDto> list = this.driverService.listPubDriverBySelectJobNum(param);
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
	 * 查询运管平台附近司机
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/PubDriver/GetOpDriverInBound", method = RequestMethod.POST)
	public JSONObject getOpDriverInBound(@RequestBody PubDriverInBoundParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = driverService.getOpDriverInBound(param);
		releaseResource(driverService);
		return checkResult(result);
	}
	
	/**
	 * 查询租赁平台附近司机
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/PubDriver/GetLeDriverInBound", method = RequestMethod.POST)
	public JSONObject getLeDriverInBound(@RequestBody PubDriverInBoundParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = driverService.getLeDriverInBound(param);
		releaseResource(driverService);
		return checkResult(result);
	}
	
	/**
	 * 查询特殊司机
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/PubDriver/GetSpecialDriver", method = RequestMethod.POST)
	public JSONObject getSpecialDriver(@RequestBody PubDriverInBoundParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = driverService.getSpecialDriver(param);
		releaseResource(driverService);
		return checkResult(result);
	}

    /**
     * 获取不推送网约车司机
     * 即刻单：
     * 1、服务中不推；
     * 2、已有即刻单不推；
     * 3、预约单用车时间在一小时内不推；
     * <p>
     * 预约单
     * 1、已有预约单不推 预约单；
     * 2、已有即刻单 且 时间间隔在3小时内 不推；
     * 3、服务中不推；
     *
     * @param order 订单信息
     * @return 司机
     */
    @ResponseBody
    @RequestMapping(value = "api/PubDriver/GetUnExceptDriver", method = RequestMethod.POST)
    public JSONObject getUnExceptDriver(@RequestBody OrderInfoDetail order){
        starttime.set(System.currentTimeMillis());
        JSONObject result = driverService.getUnExceptDriver(order);
        releaseResource(driverService);
        return checkResult(result);
    }
}
