package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.PubAdImage;
import com.szyciov.operate.service.PubAdimageService;
import com.szyciov.param.PubAdimageQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 广告图片信息管理
 */
@Controller
public class PubAdimageController extends BaseController {
	
	private PubAdimageService adimageService;
	@Resource(name = "PubAdimageService")
	public void setAdimageService(PubAdimageService adimageService) {
		this.adimageService = adimageService;
	}
	
	/**
	 * 分页查询广告图片信息
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubAdimage/GetPubAdimageByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubAdimageByQuery(@RequestBody PubAdimageQueryParam queryParam) {
		return adimageService.getPubAdimageByQuery(queryParam);
	}
	
	/**
	 * 新增广告图片信息
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/PubAdimage/CreatePubAdimage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPubAdimage(@RequestBody PubAdImage object) {
		return adimageService.createPubAdimage(object);
	}
	
	/**
	 * 修改广告图片信息
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/PubAdimage/EditPubAdimage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editPubAdimage(@RequestBody PubAdImage object) {
		return adimageService.editPubAdimage(object);
	}
	
	/**
	 * 根据id获取广告图片详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/PubAdimage/GetPubAdimageList", method = RequestMethod.POST)
	@ResponseBody
	public List<PubAdImage> getPubAdimageList(@RequestBody PubAdImage object) {
		return adimageService.getPubAdimageList(object);
	}
	
	/**
	 * 启用广告页配置
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/PubAdimage/EditPubAdviseToStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editPubAdviseToStart(@RequestBody PubAdImage object) {
		return adimageService.editPubAdviseToStart(object);
	}

}
