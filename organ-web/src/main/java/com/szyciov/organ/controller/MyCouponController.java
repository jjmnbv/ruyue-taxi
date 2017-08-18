package com.szyciov.organ.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.reflect.TypeToken;
import com.szyciov.dto.coupon.CouponInfoDTO;
import com.szyciov.dto.coupon.MyCouponDTO;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.ResultData;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
/**
 * 我的规则
 * */
@Controller
public class MyCouponController extends BaseController {
	private static final Logger logger = Logger.getLogger(MyCouponController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping("MyCoupon/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("resource/myCoupon/index");
		Map<String,Object> couponparams = new HashMap<String,Object>();
		couponparams.put("userId", this.getLoginOrgUser(request).getId());
		couponparams.put("iDisplayStart",0);
		couponparams.put("iDisplayLength",100);
		String paramstr = GsonUtil.toJson(couponparams);
		try {
			String json = templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("couponApi")+"/coupon/all/list", HttpMethod.POST, null, paramstr, String.class);
			ResultData resultData = GsonUtil.fromJson(json, ResultData.class);
			List<CouponInfoDTO> dtos = new ArrayList<>();
			if(resultData.getStatus() == 0){
				dtos= GsonUtil.fromJson2Array(resultData.getData(), new  TypeToken<List<CouponInfoDTO>>() {}.getType() );
			}
			List<MyCouponDTO> myCouponDTO = new ArrayList<>();
			for(CouponInfoDTO dto:dtos){
				LocalDate today = LocalDate.now();
				LocalDate startTime = dto.getOutimestart();
				LocalDate endTime = dto.getOuttimeend();
				MyCouponDTO mc = getMyCouponDTOList(request,response,dto.getLecompanyid());
				mc.setId(dto.getId());
				mc.setName(dto.getName());
				mc.setMoney(dto.getMoney());
				if(dto.getServicetype().equals("2")){
					mc.setServicetype("网约车");
				}
				if(dto.getUsetype().equals("1")){
					mc.setUsetypename("限"+dto.getCityStr().toString()+"可用");
				}else{
					mc.setUsetypename("限开通业务城市可用");
				}
				if(startTime.isAfter(today)){
					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-dd-MM");
					String startTimes = startTime.format(format);
					mc.setOutimestart(startTimes);
				}else{
					mc.setOutimestart("有效期");
				}
				mc.setOuttimeend(endTime);
				myCouponDTO.add(mc);
			}
			if(myCouponDTO.size() > 0){
				mav.addObject("results","yes");
				mav.addObject("myCouponDTO",myCouponDTO);
			}else{
				mav.addObject("results","no");
			}
		} catch (Exception e) {
			return mav;
		}
		return mav;
	}
	
	public MyCouponDTO getMyCouponDTOList(HttpServletRequest request,HttpServletResponse response,String id){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		MyCouponDTO mc = templateHelper.dealRequestWithToken("/MyCoupon/GetMyCouponDTOList/{id}", HttpMethod.GET, userToken,null,
				MyCouponDTO.class,id);
		return mc;
	}
}
