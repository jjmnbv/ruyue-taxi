package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.PubCooagreement;
import com.szyciov.op.entity.LeLeasescompany;
import com.szyciov.op.param.PubCooagreementQueryParam;
import com.szyciov.operate.service.PubCooagreementService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 字典模块控制器
 */
@Controller
public class PubCooagreementController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubCooagreementController.class);

	public PubCooagreementService service;
	
	@Resource(name = "PubCooagreementService")
	public void setPubCooagreementService(PubCooagreementService service) {
		this.service = service;
	}
	/** 
	 * <p>分页查询字典模块信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的条件和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/PubCooagreement/GetPubCooagreementByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubCooagreementByQuery(@RequestBody PubCooagreementQueryParam queryParam) {
		return service.getPubCooagreementByQuery(queryParam);
	}
	/** 
	 * <p>新增协议，加载的租赁公司</p>
	 * @return 返回一个list
	 */
	@RequestMapping(value = "api/PubCooagreement/GetLeLeasescompanyList", method = RequestMethod.POST)
	@ResponseBody
	public List<LeLeasescompany> getLeLeasescompanyList(){
		return service.getLeLeasescompanyList();
	};
	/** 
	 * <p>新增协议</p>
	 * @return 返回一个map的结果
	 */
	@RequestMapping(value = "api/PubCooagreement/CreatePubCooagreement", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> createPubCooagreement(@RequestBody PubCooagreement pubCooagreement){
		return service.createPubCooagreement(pubCooagreement);
	};
	/** 
	 * <p>根据id查</p>
	 * @return 返回一个PubCooagreement的对象
	 */
	@RequestMapping(value = "api/PubCooagreement/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubCooagreement getById(@PathVariable String id){
		return service.getById(id);
	};
	/** 
	 * <p>修改协议</p>
	 * @return 返回一个map的结果
	 */
	@RequestMapping(value = "api/PubCooagreement/UpdatePubCooagreement", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updatePubCooagreement(@RequestBody PubCooagreement pubCooagreement){
		return service.updatePubCooagreement(pubCooagreement);
	};
	/** 
	 * <p>删除协议</p>
	 * @return 返回一个map的结果
	 */
	@RequestMapping(value = "api/PubCooagreement/DeletePubCooagreement/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> deletePubCooagreement(@PathVariable String id){
		return service.deletePubCooagreement(id);
	};
}
