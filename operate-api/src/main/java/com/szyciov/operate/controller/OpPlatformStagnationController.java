package com.szyciov.operate.controller;

import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.operate.service.OpPlatformStagnationService;
import com.szyciov.op.param.OpPlatformStagnationQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 派单规则模块控制器
 */
@Controller
public class OpPlatformStagnationController extends BaseController {
	private static final Logger logger = Logger.getLogger(OpPlatformStagnationController.class);

	private OpPlatformStagnationService opPlatformStagnationService;

	@Resource(name = "OpPlatformStagnationService")
	public void setOpPlatformStagnationService(OpPlatformStagnationService opPlatformStagnationService) {
		this.opPlatformStagnationService = opPlatformStagnationService;
	}
	
	/** 
	 * <p>分页查询</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OpPlatformStagnation/Page", method = RequestMethod.POST)
	@ResponseBody
		public PageBean page(@RequestBody OpPlatformStagnationQueryParam queryParam) {
		return opPlatformStagnationService.page(queryParam);
	}


	/** 
	 * <p>新增</p>
	 *
	 * @param opPlatformStagnation
	 * @return
	 */
	@RequestMapping(value = "api/OpPlatformStagnation/Create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(@RequestBody OpPlatformStagnation opPlatformStagnation) {
		return opPlatformStagnationService.create(opPlatformStagnation);
	}
	
	/** 
	 * <p>修改</p>
	 *
	 * @param opPlatformStagnation
	 * @return
	 */
	@RequestMapping(value = "api/OpPlatformStagnation/Update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(@RequestBody OpPlatformStagnation opPlatformStagnation) {
		return opPlatformStagnationService.update(opPlatformStagnation);
	}



	/**
	 * <p>删除</p>
	 * @return 返回一个map的结果
	 */
	@RequestMapping(value = "api/OpPlatformStagnation/Delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> delete(@PathVariable String id){
		return opPlatformStagnationService.delete(id);
	}

	/**
	 * <p>获取</p>
	 * @return 返回一个map的结果
	 */
	@RequestMapping(value = "api/OpPlatformStagnation/Get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OpPlatformStagnation get(@PathVariable String id){
		return opPlatformStagnationService.getById(id);
	}

	/**
	 * 获取要导出的数据
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OpPlatformStagnation/ExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public List<OpPlatformStagnation> exportExcel(@RequestBody OpPlatformStagnationQueryParam queryParam){
		return opPlatformStagnationService.exportExcel(queryParam);
	}
}
