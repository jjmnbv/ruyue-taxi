package com.szyciov.operate.controller;

import com.szyciov.entity.Excel;
import com.szyciov.op.entity.OpPlatformServiceorgan;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.vo.OpPlatformServiceorganVo;
import com.szyciov.operate.service.OpPlatformServiceorganService;
import com.szyciov.operate.util.excel.OpPlatformServiceorganExcel;
import com.szyciov.op.param.OpPlatformServiceorganQueryParam;
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
public class OpPlatformServiceorganController extends BaseController {
    private static final Logger logger = Logger.getLogger(OpPlatformServiceorganController.class);

    @Autowired
    private OpPlatformServiceorganService opPlatformServiceorganService;


	/**
	 * 服务机构首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OpPlatformServiceorgan/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		OpUser user = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/opPlatformServiceorgan/index");
		return view;
	}

	/**
	 * ajax分页请求服务机构列表
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/OpPlatformServiceorgan/Page")
	@ResponseBody
	public PageBean page(@RequestBody OpPlatformServiceorganQueryParam queryParam, HttpServletRequest request,
										   HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return opPlatformServiceorganService.page(queryParam,userToken);
	}

	/**
	 * 新增或者编辑页面
	 *
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformServiceorgan/Edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) String id,
											 HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		 查询服务机构信息
		OpPlatformServiceorganVo opPlatformServiceorganVo = null;
		if (!StringUtils.isBlank(id)) {
			opPlatformServiceorganVo = opPlatformServiceorganService.get(id,userToken);
		}
		view.addObject("opPlatformServiceorgan", opPlatformServiceorganVo);
		view.setViewName("resource/opPlatformServiceorgan/edit");
		return view;
	}

	/**
	 * 新增机构接口
	 * @param opPlatformServiceorgan
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformServiceorgan/Create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(@RequestBody OpPlatformServiceorgan opPlatformServiceorgan, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser opuser = getLoginOpUser(request);
		opPlatformServiceorgan.setCreater(opuser.getId());
		opPlatformServiceorgan.setUpdater(opuser.getId());
		return opPlatformServiceorganService.create(opPlatformServiceorgan,usertoken);
	}

	/**
	 * 更新机构接口
	 * @param opPlatformServiceorgan
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpPlatformServiceorgan/Update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(@RequestBody OpPlatformServiceorgan opPlatformServiceorgan, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser opuser = getLoginOpUser(request);
		opPlatformServiceorgan.setUpdater(opuser.getId());
		return opPlatformServiceorganService.update(opPlatformServiceorgan,usertoken);
	}
	@RequestMapping(value = "OpPlatformServiceorgan/Delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> delete(@RequestParam String id,  HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		return  opPlatformServiceorganService.delete(id,usertoken);
	}

	/**
	 * 下载模板
	 * **/
	@RequestMapping("/OpPlatformServiceorgan/DownLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// excel文件
		Excel excel=opPlatformServiceorganService.download();
		ExcelDataSequenceVehicke ee = new ExcelDataSequenceVehicke(request,response,excel);
		//导出excel序列的值
		String[] s = {};
		String[] s1 = {};
		String[] s2 = {};
		//起始行序号，序列值，起始列序号，终止列序
		ee.createExcel(OpPlatformServiceorganExcel.getExportFile(),s,s1,s2);
	}

	@RequestMapping("/OpPlatformServiceorgan/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData( OpPlatformServiceorganQueryParam queryParam,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//
		Excel excel =opPlatformServiceorganService.exportExcel(queryParam,userToken) ;
//
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(OpPlatformServiceorganExcel.getExportFile());

	}

	@RequestMapping("/OpPlatformServiceorgan/ImportExcel")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public JSONObject importExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StringBuffer rets = new StringBuffer();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		MultipartHttpServletRequest mRequest = null;
		mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = mRequest.getFileMap();
		MultipartFile mulfile = files.get("importfile");

		JSONObject ret =opPlatformServiceorganService.importExcel(mulfile,userToken);
		return ret;
	}
}
