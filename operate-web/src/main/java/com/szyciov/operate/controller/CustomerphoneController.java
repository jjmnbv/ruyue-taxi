package com.szyciov.operate.controller;


import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.TemplateHelper;
@Controller
public class CustomerphoneController extends BaseController{
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping(value = "/Customerphone/Index")
	public ModelAndView getCustomerphoneIndex(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		// 运管平台信息
		OpPlatformInfo opPlatformInfo = templateHelper.dealRequestWithToken(
						"/OpInformationSet/GetOpPlatformInfo", HttpMethod.GET, userToken,
						null, OpPlatformInfo.class);
		if(opPlatformInfo != null){
		OpPlatformInfo cityName = templateHelper.dealRequestWithToken(
				"/OpInformationSet/GetCityName", HttpMethod.POST, userToken,
				opPlatformInfo, OpPlatformInfo.class);
		 if(cityName != null){
		     opPlatformInfo.setCity(cityName.getCity());
		 }
		}
		    mav.addObject("opPlatformInfo", opPlatformInfo);
		    mav.setViewName("resource/customerphone/index");
		return mav;
	}
	@RequestMapping("OpInformationSet/UpdateServcietel")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updateOpPlatformInfo(@RequestBody OpPlatformInfo opPlatformInfo, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OpInformationSet/UpdateServcietel", HttpMethod.POST, userToken, opPlatformInfo,
				Map.class);
	}

}
