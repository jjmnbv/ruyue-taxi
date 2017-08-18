package com.szyciov.operate.controller;

import com.szyciov.op.entity.OpPlatformServiceorgan;
import com.szyciov.op.vo.OpPlatformServiceorganVo;
import com.szyciov.operate.service.OpPlatformServiceorganService;
import com.szyciov.op.param.OpPlatformServiceorganQueryParam;
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
public class OpPlatformServiceorganController extends BaseController {
	private static final Logger logger = Logger.getLogger(OpPlatformServiceorganController.class);

	public OpPlatformServiceorganService opPlatformServiceorganService;

	@Resource(name = "opPlatformServiceorganService")
	public void setOpPlatformServiceorganService(OpPlatformServiceorganService opPlatformServiceorganService) {
		this.opPlatformServiceorganService = opPlatformServiceorganService;
	}
	
	/** 
	 * <p>分页查询</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OpPlatformServiceorgan/Page", method = RequestMethod.POST)
	@ResponseBody
		public PageBean page(@RequestBody OpPlatformServiceorganQueryParam queryParam) {
		return opPlatformServiceorganService.page(queryParam);
	}


	/** 
	 * <p>新增</p>
	 *
	 * @param opPlatformServiceorgan
	 * @return
	 */
	@RequestMapping(value = "api/OpPlatformServiceorgan/Create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createServiceorgan(@RequestBody OpPlatformServiceorgan opPlatformServiceorgan) {
		return opPlatformServiceorganService.create(opPlatformServiceorgan);
	}
	
	/** 
	 * <p>修改</p>
	 *
	 * @param opPlatformServiceorgan
	 * @return
	 */
	@RequestMapping(value = "api/OpPlatformServiceorgan/Update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateServiceorgan(@RequestBody OpPlatformServiceorgan opPlatformServiceorgan) {
		return opPlatformServiceorganService.update(opPlatformServiceorgan);
	}



	/**
	 * <p>删除</p>
	 * @return 返回一个map的结果
	 */
	@RequestMapping(value = "api/OpPlatformServiceorgan/Delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> deleteServiceorgan(@PathVariable String id){
		return opPlatformServiceorganService.delete(id);
	}

	/**
	 * <p>获取</p>
	 * @return 返回一个map的结果
	 */
	@RequestMapping(value = "api/OpPlatformServiceorgan/Get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OpPlatformServiceorganVo getServiceorgan(@PathVariable String id){
		return opPlatformServiceorganService.getById(id);
	}

	/**
	 * 获取要导出的数据
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OpPlatformServiceorgan/ExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public List<OpPlatformServiceorganVo> getServiceorgan(@RequestBody OpPlatformServiceorganQueryParam queryParam){
		return opPlatformServiceorganService.exportExcel(queryParam);
	}
}
