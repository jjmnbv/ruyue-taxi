package com.szyciov.operate.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpUser;
import com.szyciov.operate.service.OpInformationSetService;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;

@Controller
public class OpInformationSetController extends BaseController {
	
	private OpInformationSetService opInformationSetService;
	@Resource(name = "OpInformationSetService")
	public void setOpInformationSetService(OpInformationSetService opInformationSetService) {
		this.opInformationSetService = opInformationSetService;
	}
	
	/**
	 * 收款账户首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("OpInformationSet/Index")
	@ResponseBody
	public ModelAndView getIndex(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		// 运管平台信息
		OpPlatformInfo opPlatformInfo = opInformationSetService.getOpPlatformInfo(userToken);

		view.addObject("opPlatformInfo", opPlatformInfo);
		view.setViewName("resource/opInformationSet/index");
		return view;
	}

	/**
	 * 司机收款账户首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("OpInformationSet/driver/Index")
	@ResponseBody
	public ModelAndView getDriverIndex(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		// 运管平台信息
		OpPlatformInfo opPlatformInfo = opInformationSetService.getOpPlatformInfo(userToken);

		view.addObject("opPlatformInfo", opPlatformInfo);
		view.setViewName("resource/opInformationSet/driver/index");
		return view;
	}
	
	/**
	 * 修改运管端平台信息
	 * @param opPlatformInfo
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("OpInformationSet/UpdateOpPlatformInfo")
	@ResponseBody
	public Map<String, String> updateOpPlatformInfo(@RequestBody OpPlatformInfo opPlatformInfo, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return opInformationSetService.updateOpPlatformInfo(opPlatformInfo, userToken);
	}
	
	/**
	 * 查询运管端平台信息
	 * @param request
	 * @return
	 */
	@RequestMapping("OpInformationSet/GetOpPlatformInfo")
	@ResponseBody
	public OpPlatformInfo getOpPlatformInfo(HttpServletRequest request) {
		String userToken = getUserToken(request);
		return opInformationSetService.getOpPlatformInfo(userToken);
	}
	
	/**
	 * 根据条件修改运管端平台信息
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping("OpInformationSet/EditOpPlatformInfo")
	@ResponseBody
	public Map<String, String> editOpPlatformInfo(@RequestBody OpPlatformInfo object, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return opInformationSetService.editOpPlatformInfo(object, userToken);
	}
	
	/**
	 * 跳转到账户配置页
	 * @param allocationType
	 * @param request
	 * @return
	 */
	@RequestMapping("OpInformationSet/AllocationIndex/{allocationType}")
	public ModelAndView allocationIndex(@PathVariable String allocationType, HttpServletRequest request) {
		String userToken = getUserToken(request);
		//查询运管端平台信息
		OpPlatformInfo opPlatformInfo = opInformationSetService.getOpPlatformInfo(userToken);
		
		ModelAndView view = new ModelAndView();
		view.addObject("opPlatformInfo", opPlatformInfo);
		if("alipay".equals(allocationType)) { //支付宝
			view.setViewName("resource/opInformationSet/alipay");
		} else { //微信
			view.setViewName("resource/opInformationSet/wechat");
		}
		return view;
	}

    /**
     * 跳转到司机账户配置页
     * @param allocationType
     * @param request
     * @return
     */
    @RequestMapping("OpInformationSet/AllocationIndex/driver/{allocationType}")
    public ModelAndView allocationDriverIndex(@PathVariable String allocationType, HttpServletRequest request) {
        String userToken = getUserToken(request);
        //查询运管端平台信息
        OpPlatformInfo opPlatformInfo = opInformationSetService.getOpPlatformInfo(userToken);

        ModelAndView view = new ModelAndView();
        view.addObject("opPlatformInfo", opPlatformInfo);
        if("alipay".equals(allocationType)) { //支付宝
            view.setViewName("resource/opInformationSet/driver/alipay");
        } else { //微信
            view.setViewName("resource/opInformationSet/driver/wechat");
        }
        return view;
    }
	
	/**
     * 配置支付宝账户信息
     * @param object
     * @param request
     * @return
     */
    @RequestMapping("OpInformationSet/UpdateAlipay")
    @ResponseBody
    public Map<String, String> updateAlipay(@RequestBody OpPlatformInfo object, HttpServletRequest request) {
        String userToken = getUserToken(request);
        OpUser user = getLoginOpUser(request);
        object.setUpdater(user.getId());
        return opInformationSetService.updateAlipay(object, userToken);
    }

    /**
     * 配置司机支付宝账户信息
     * @param object
     * @param request
     * @return
     */
    @RequestMapping("OpInformationSet/driver/UpdateAlipay")
    @ResponseBody
    public Map<String, String> updateDriverAlipay(@RequestBody OpPlatformInfo object, HttpServletRequest request) {
        String userToken = getUserToken(request);
        OpUser user = getLoginOpUser(request);
        object.setUpdater(user.getId());
        return opInformationSetService.updateDriverAlipay(object, userToken);
    }
	
	/**
	 * 配置微信账户信息
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping("OpInformationSet/UpdateWechat")
	@ResponseBody
	public Map<String, String> updateWechat(@RequestBody OpPlatformInfo object, HttpServletRequest request) {
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		object.setUpdater(user.getId());
		return opInformationSetService.updateWechat(object, userToken);
	}

    /**
     * 配置微信账户信息
     * @param object
     * @param request
     * @return
     */
    @RequestMapping("OpInformationSet/driver/UpdateWechat")
    @ResponseBody
    public Map<String, String> updateDriverWechat(@RequestBody OpPlatformInfo object, HttpServletRequest request) {
        String userToken = getUserToken(request);
        OpUser user = getLoginOpUser(request);
        object.setUpdater(user.getId());
        return opInformationSetService.updateDriverWechat(object, userToken);
    }
}
