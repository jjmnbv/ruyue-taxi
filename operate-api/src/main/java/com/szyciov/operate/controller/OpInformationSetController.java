package com.szyciov.operate.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.operate.service.OpInformationSetService;
import com.szyciov.util.BaseController;

/**
 * 信息设置模块控制器
 */
@Controller
public class OpInformationSetController extends BaseController {

	public OpInformationSetService opInformationSetService;

	@Resource(name = "OpInformationSetService")
	public void setOpInformationSetService(OpInformationSetService opInformationSetService) {
		this.opInformationSetService = opInformationSetService;
	}
	
	/** 
	 * <p>查询运管平台信息表</p>
	 * @return
	 */
	@RequestMapping(value = "api/OpInformationSet/GetOpPlatformInfo", method = RequestMethod.GET)
	@ResponseBody
	public OpPlatformInfo getOpPlatformInfo() {
		return opInformationSetService.getOpPlatformInfo();
	}
	
	/** 
	 * <p>修改运管平台信息</p>
	 *
	 * @param opPlatformInfo 运管平台信息
	 * @return
	 */
	@RequestMapping(value = "api/OpInformationSet/UpdateOpPlatformInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateOpPlatformInfo(@RequestBody OpPlatformInfo opPlatformInfo) {
		return opInformationSetService.updateOpPlatformInfo(opPlatformInfo);
	}
	/** 
	 * <p>修改运管平台公司，电话信息</p>
	 *
	 * @param opPlatformInfo 运管平台信息
	 * @return
	 */
	@RequestMapping(value = "api/OpInformationSet/UpdateServcietel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateServcietel(@RequestBody OpPlatformInfo opPlatformInfo) {
		return opInformationSetService.updateServcietel(opPlatformInfo);
	}
	@RequestMapping(value = "api/OpInformationSet/GetCityName", method = RequestMethod.POST)
	@ResponseBody
	public OpPlatformInfo getCityName(@RequestBody OpPlatformInfo opPlatformInfo) {
		return opInformationSetService.getCityName(opPlatformInfo);
	}
	
	/**
	 * 根据条件修改运管端平台信息
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OpInformationSet/EditOpPlatformInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editOpPlatformInfo(@RequestBody OpPlatformInfo object) {
		return opInformationSetService.editOpPlatformInfo(object);
	}
	
	/**
	 * 配置支付宝账户信息
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OpInformationSet/UpdateAlipay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateAlipay(@RequestBody OpPlatformInfo object) {
		return opInformationSetService.updateAlipay(object);
	}

	/**
	 * 配置司机支付宝账户信息
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OpInformationSet/driver/UpdateAlipay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateDriverAlipay(@RequestBody OpPlatformInfo object) {
		return opInformationSetService.updateDriverAlipay(object);
	}
	
	/**
	 * 配置微信账户信息
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OpInformationSet/UpdateWechat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateWechat(@RequestBody OpPlatformInfo object) {
		return opInformationSetService.updateWechat(object);
	}

    /**
     * 配置微信账户信息
     * @param object
     * @return
     */
    @RequestMapping(value = "api/OpInformationSet/driver/UpdateWechat", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updateDriverWechat(@RequestBody OpPlatformInfo object) {
        return opInformationSetService.updateDriverWechat(object);
    }
}
