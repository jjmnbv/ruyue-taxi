package com.szyciov.operate.controller;

import com.szyciov.op.dto.OpPlatformBusinesslicenseDto;
import com.szyciov.op.entity.OpPlatformBusinesslicense;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.op.param.OpPlatformBusinesslicenseQueryParam;
import com.szyciov.op.param.OpPlatformStagnationQueryParam;
import com.szyciov.op.vo.OpPlatformBusinesslicenseVo;
import com.szyciov.operate.service.OpPlatformBusinesslicenseService;
import com.szyciov.operate.service.OpPlatformStagnationService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 派单规则模块控制器
 */
@Controller
public class OpPlatformBusinesslicenseController extends BaseController {
	private static final Logger logger = Logger.getLogger(OpPlatformBusinesslicenseController.class);

	@Autowired
	private OpPlatformBusinesslicenseService opPlatformBusinesslicenseService;

	
	/** 
	 * <p>分页查询</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OpPlatformBusinesslicense/Page", method = RequestMethod.POST)
	@ResponseBody
		public PageBean page(@RequestBody OpPlatformBusinesslicenseQueryParam queryParam) {
		return opPlatformBusinesslicenseService.page(queryParam);
	}


	/** 
	 * <p>新增</p>
	 *
	 * @param opPlatformBusinesslicenseDto
	 * @return
	 */
	@RequestMapping(value = "api/OpPlatformBusinesslicense/Create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(@RequestBody OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto) {
		return opPlatformBusinesslicenseService.create(opPlatformBusinesslicenseDto);
	}
	
	/** 
	 * <p>修改</p>
	 *
	 * @param opPlatformBusinesslicenseDto
	 * @return
	 */
	@RequestMapping(value = "api/OpPlatformBusinesslicense/Update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(@RequestBody OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto) {
		return opPlatformBusinesslicenseService.update(opPlatformBusinesslicenseDto);
	}



	/**
	 * <p>删除</p>
	 * @return 返回一个map的结果
	 */
	@RequestMapping(value = "api/OpPlatformBusinesslicense/Delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> delete(@PathVariable String id){
		return opPlatformBusinesslicenseService.delete(id);
	}

	/**
	 * <p>获取</p>
	 * @return 返回一个map的结果
	 */
	@RequestMapping(value = "api/OpPlatformBusinesslicense/Get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OpPlatformBusinesslicenseVo get(@PathVariable String id){
		return opPlatformBusinesslicenseService.getById(id);
	}

	/**
	 * 获取要导出的数据
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OpPlatformBusinesslicense/ExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public List<OpPlatformBusinesslicenseVo> exportExcel(@RequestBody OpPlatformBusinesslicenseQueryParam queryParam){
		return opPlatformBusinesslicenseService.exportExcel(queryParam);
	}
}
