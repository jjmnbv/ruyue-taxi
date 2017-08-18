package com.szyciov.organ.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.dto.coupon.MyCouponDTO;
import com.szyciov.organ.service.MyCouponService;
import com.szyciov.util.BaseController;

/**
 * 控制器
 */
@Controller
public class MyCouponController extends BaseController {
	private static final Logger logger = Logger.getLogger(MyCouponController.class);

	public MyCouponService service;

	@Resource(name = "MyCouponService")
	public void setOrgOrganService(MyCouponService service) {
		this.service = service;
	}
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/MyCoupon/GetMyCouponDTOList/{id}", method = RequestMethod.GET)
	@ResponseBody
	public MyCouponDTO getMyCouponDTOList(@PathVariable String id) {
		return service.getMyCouponDTOList(id);
	}
	
}
