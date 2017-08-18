package com.szyciov.operate.controller;

import com.szyciov.entity.Excel;
import com.szyciov.op.dto.OpPlatformBusinesslicenseDto;

import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.OpPlatformBusinesslicenseQueryParam;

import com.szyciov.op.vo.OpPlatformBusinesslicenseVo;
import com.szyciov.operate.service.OpPlatformBusinesslicenseService;

import com.szyciov.operate.util.excel.OpPlatformBusinesslicenseExcel;
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

/**
 * 经营许可信息
 */
@Controller
public class OpPlatformBusinesslicenseController extends BaseController {
    private static final Logger logger = Logger.getLogger(OpPlatformBusinesslicenseController.class);

    @Autowired
    private OpPlatformBusinesslicenseService opPlatformBusinesslicenseService;


	/**
	 * 首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OpPlatformBusinesslicense/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		OpUser user = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/opPlatformBusinesslicense/index");
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
	@RequestMapping("/OpPlatformBusinesslicense/Page")
	@ResponseBody
	public PageBean page(@RequestBody OpPlatformBusinesslicenseQueryParam queryParam, HttpServletRequest request,
						 HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return opPlatformBusinesslicenseService.page(queryParam,userToken);
	}

	/**
	 * 新增或者编辑页面
	 *
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformBusinesslicense/Edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) String id,
											 HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		 查询服务机构信息
		OpPlatformBusinesslicenseVo opPlatformBusinesslicenseVo = null;
		if (!StringUtils.isBlank(id)) {
			opPlatformBusinesslicenseVo = opPlatformBusinesslicenseService.get(id,userToken);
		}
		view.addObject("opPlatformBusinesslicenseVo", opPlatformBusinesslicenseVo);
		view.setViewName("resource/opPlatformBusinesslicense/edit");
		return view;
	}

	/**
	 * 新增
	 * @param opPlatformBusinesslicenseDto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformBusinesslicense/Create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(@RequestBody OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser opuser = getLoginOpUser(request);
		opPlatformBusinesslicenseDto.setCreater(opuser.getId());
		opPlatformBusinesslicenseDto.setUpdater(opuser.getId());
		return opPlatformBusinesslicenseService.create(opPlatformBusinesslicenseDto,usertoken);
	}

	/**
	 * 更新
	 * @param opPlatformBusinesslicenseDto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformBusinesslicense/Update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(@RequestBody OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser opuser = getLoginOpUser(request);
		opPlatformBusinesslicenseDto.setUpdater(opuser.getId());
		return opPlatformBusinesslicenseService.update(opPlatformBusinesslicenseDto,usertoken);
	}

	/**
	 * 删除
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformBusinesslicense/Delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> delete(@RequestParam String id,  HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		return  opPlatformBusinesslicenseService.delete(id,usertoken);
	}

	/**
	 * 下载模板
	 * **/
	@RequestMapping("/OpPlatformBusinesslicense/DownLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// excel文件
		Excel excel=opPlatformBusinesslicenseService.download();
		ExcelDataSequenceVehicke ee = new ExcelDataSequenceVehicke(request,response,excel);
		//导出excel序列的值
		String[] s = {};
		String[] s1 = {};
		String[] s2 = {};
		//起始行序号，序列值，起始列序号，终止列序
		ee.createExcel(OpPlatformBusinesslicenseExcel.getExportFile(),s,s1,s2);
	}

	@RequestMapping("/OpPlatformBusinesslicense/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData( OpPlatformBusinesslicenseQueryParam queryParam,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userToken = getUserToken(request);
//
		Excel excel =opPlatformBusinesslicenseService.exportExcel(queryParam,userToken) ;
//
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(OpPlatformBusinesslicenseExcel.getExportFile());

	}

	@RequestMapping("/OpPlatformBusinesslicense/ImportExcel")
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

		JSONObject ret =opPlatformBusinesslicenseService.importExcel(mulfile,userToken);
		return ret;
	}
}
