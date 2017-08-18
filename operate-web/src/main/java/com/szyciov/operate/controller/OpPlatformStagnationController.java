package com.szyciov.operate.controller;

import com.szyciov.entity.Excel;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.op.entity.OpUser;
import com.szyciov.operate.service.OpPlatformStagnationService;
import com.szyciov.operate.util.excel.OpPlatformStagnationExcel;
import com.szyciov.op.param.OpPlatformStagnationQueryParam;
import com.szyciov.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class OpPlatformStagnationController extends BaseController {
    private static final Logger logger = Logger.getLogger(OpPlatformStagnationController.class);

    @Autowired
    private OpPlatformStagnationService opPlatformStagnationService;


	/**
	 * 首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OpPlatformStagnation/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		OpUser user = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/opPlatformStagnation/index");
		return view;
	}

	/**
	 * ajax分页请求数据列表
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/OpPlatformStagnation/Page")
	@ResponseBody
	public PageBean page(@RequestBody OpPlatformStagnationQueryParam queryParam, HttpServletRequest request,
						 HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return opPlatformStagnationService.page(queryParam,userToken);
	}

	/**
	 * 新增或者编辑页面
	 *
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformStagnation/Edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) String id,
											 HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		 查询服务机构信息
		OpPlatformStagnation opPlatformStagnation = null;
		if (!StringUtils.isBlank(id)) {
			opPlatformStagnation = opPlatformStagnationService.get(id,userToken);
		}
		view.addObject("opPlatformStagnation", opPlatformStagnation);
		view.setViewName("resource/opPlatformStagnation/edit");
		return view;
	}

	/**
	 * 新增机构接口
	 * @param opPlatformStagnation
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformStagnation/Create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(@RequestBody OpPlatformStagnation opPlatformStagnation, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser opuser = getLoginOpUser(request);
		opPlatformStagnation.setCreater(opuser.getId());
		opPlatformStagnation.setUpdater(opuser.getId());
		return opPlatformStagnationService.create(opPlatformStagnation,usertoken);
	}

	/**
	 * 更新机构接口
	 * @param opPlatformStagnation
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformStagnation/Update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(@RequestBody OpPlatformStagnation opPlatformStagnation, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser opuser = getLoginOpUser(request);
		opPlatformStagnation.setUpdater(opuser.getId());
		return opPlatformStagnationService.update(opPlatformStagnation,usertoken);
	}

	/**
	 * 删除
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformStagnation/Delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> delete(@RequestParam String id,  HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		return  opPlatformStagnationService.delete(id,usertoken);
	}

	/**
	 * 下载模板
	 * **/
	@RequestMapping("/OpPlatformStagnation/DownLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// excel文件
		Excel excel=opPlatformStagnationService.download();
		ExcelDataSequenceVehicke ee = new ExcelDataSequenceVehicke(request,response,excel);
		//导出excel序列的值
		String[] s = {};
		String[] s1 = {};
		String[] s2 = {};
		//起始行序号，序列值，起始列序号，终止列序
		ee.createExcel(OpPlatformStagnationExcel.getExportFile(),s,s1,s2);
	}

	@RequestMapping("/OpPlatformStagnation/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData( OpPlatformStagnationQueryParam queryParam,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userToken = getUserToken(request);
//
		Excel excel =opPlatformStagnationService.exportExcel(queryParam,userToken) ;
//
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(OpPlatformStagnationExcel.getExportFile());

	}

	@RequestMapping("/OpPlatformStagnation/ImportExcel")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public JSONObject importExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StringBuffer rets = new StringBuffer();
		String userToken = getUserToken(request);

		MultipartHttpServletRequest mRequest = null;
		mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = mRequest.getFileMap();
		MultipartFile mulfile = files.get("importfile");

		JSONObject ret =opPlatformStagnationService.importExcel(mulfile,userToken);
		return ret;
	}
}
