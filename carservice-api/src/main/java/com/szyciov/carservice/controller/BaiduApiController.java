package com.szyciov.carservice.controller;

import com.szyciov.carservice.service.BaiduApiService;
import com.szyciov.carservice.service.MileageApiService;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.util.ApiExceptionHandle;
import com.szyciov.util.Constants;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class BaiduApiController extends ApiExceptionHandle{
	
	private BaiduApiService baiduApiService;
	@Resource(name="baiduApiService")
	public void setBaiduApiService(BaiduApiService baiduApiService) {
		this.baiduApiService = baiduApiService;
	}

	private MileageApiService mileageApiService;
    @Resource(name = "MileageApiService")
    public void setMileageApiService(MileageApiService mileageApiService) {
        this.mileageApiService = mileageApiService;
    }

    /**
	 * 获取两点间的里程
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("api/BaiduApi/GetMileageInfo")
	@ResponseBody
	public Map<String, Object> getMileageInfo(@RequestBody BaiduApiQueryParam queryParam) throws IOException {
		return baiduApiService.getMileageInfo(queryParam);
	}
	
	/**
	 * 根据订单号获取行驶轨迹数据
	 * @param orderno
	 * @param startDate
	 * @param endDate
	 * @param lowSpeed
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("api/BaiduApi/GetTraceData")
	@ResponseBody
	public JSONObject getTraceData(@RequestParam String orderno,
			@RequestParam String ordertype,
			@RequestParam String usetype,
			@RequestParam(required = false, defaultValue = "true") boolean fullReturn,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return mileageApiService.getTraceData(orderno, ordertype, usetype, fullReturn);
	}

    /**
     * 查询OBDGPS和APPGPS坐标点
     * @param orderno
     * @param ordertype
     * @param usetype
     * @return
     */
	@RequestMapping(value = "api/BaiduApi/GetGpsTraceData")
    @ResponseBody
	public JSONObject getGpsTraceData(@RequestParam String orderno, @RequestParam String ordertype, @RequestParam String usetype) {
        return mileageApiService.getGpsTraceData(orderno, ordertype, usetype);
    }
	
	/**
	 * 根据订单号获取行驶里程
	 * @param orderno
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("api/BaiduApi/GetOrderMileage")
	@ResponseBody
	public JSONObject getOrderMileage(@RequestParam String orderno,
			@RequestParam String ordertype,
			@RequestParam String usetype,
            @RequestParam(required = false, defaultValue = "false") boolean isLog,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return mileageApiService.getOrderMileage(orderno, ordertype, usetype, isLog);
	}
	
	
	/**
	 * 根据订单号获取行驶轨迹数据
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("api/BaiduApi/CreateTraceData")
	@ResponseBody
	public Map<String, Object> createTraceData(@RequestParam String orderno, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return baiduApiService.createTraceData(orderno, userToken);
	}

	/**
	 * 根据地址解析经纬度
	 * @param param
	 * @return
	 * @see {@linkplain BaiduApiQueryParam}
	 */
	@RequestMapping("api/BaiduApi/GetLatLng")
	@ResponseBody
	public JSONObject getLatLng(@RequestBody BaiduApiQueryParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = baiduApiService.getLatLng(param);
		releaseResource(baiduApiService);
		return checkResult(result);
	}
	
	/**
	 * 根据经纬度解析地址(逆解析)
	 * @param param
	 * @return
	 * @see {@linkplain BaiduApiQueryParam}
	 */
	@RequestMapping("api/BaiduApi/GetAddress")
	@ResponseBody
	public JSONObject getAddress(@RequestBody BaiduApiQueryParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = baiduApiService.getAddress(param);
		releaseResource(baiduApiService);
		return checkResult(result);
	}
}
