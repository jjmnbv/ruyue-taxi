package com.szyciov.operate.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Excel;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserdisablelog;
import com.szyciov.op.param.PeUserQueryParam;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class PeUserController extends BaseController {
    private static final Logger logger = Logger.getLogger(PeUserController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/PeUser/Index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		List<LeLeasescompany> list = templateHelper.dealRequestWithToken("/LeLeasescompany/GetNameOrCity", HttpMethod.POST, userToken,
//				null,List.class);
//		List<LeLeasescompany> list1 = templateHelper.dealRequestWithToken("/LeLeasescompany/GetCityOrName", HttpMethod.POST, userToken,
//				null,List.class);
//		mav.addObject("list",list);
//		mav.addObject("list1",list1);
		mav.setViewName("resource/peUser/index");
		return mav;
	}
	
	@RequestMapping("/PeUser/GetPeUserByQuery")
	@ResponseBody
	public PageBean getPeUserByQuery(@RequestBody PeUserQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PeUser/GetPeUserByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/PeUser/GetPeUserListCountByQuery")
	@ResponseBody
	public int getPeUserListCountByQuery(@RequestBody PeUserQueryParam queryParam,HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PeUser/GetPeUserListCountByQuery", HttpMethod.POST, userToken,
				queryParam,int.class);
	}
	
	
	@RequestMapping("/PeUser/Enable")
	@ResponseBody
	public Map<String, String> enable(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PeUser/Enable/{id}", HttpMethod.GET, userToken,
				null,Map.class,id);
	}
	
	@RequestMapping("/PeUser/ResetPassword")
	@ResponseBody
	public Map<String, String> resetPassword(@RequestParam String id,@RequestParam String orgUserAccount, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PeUser/ResetPassword/{id}/{orgUserAccount}", HttpMethod.GET, userToken,
				null,Map.class,id,orgUserAccount);
	}
	
	@RequestMapping("/PeUser/DisableRecord")
	@ResponseBody
	public ModelAndView disableRecord(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		mav.addObject("id",request.getParameter("id"));
		mav.setViewName("resource/peUser/disableRecord");
		return mav;
	}
	@RequestMapping("/PeUser/GetPeUserdisablelogByQuery")
	@ResponseBody
	public PageBean getPeUserdisablelogByQuery(@RequestBody QueryParam queryParam,HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		queryParam.setKey(request.getParameter("id"));
		return templateHelper.dealRequestWithToken("/PeUser/GetPeUserdisablelogByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping(value = "/PeUser/GetById")
	@ResponseBody
	public PeUser getById(@RequestParam String id,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PeUser/GetById/{id}", HttpMethod.GET, userToken, null,
				PeUser.class,id);
	}
	
	@RequestMapping(value = "/PeUser/CreatePeUserdisablelog")
	@ResponseBody                   
	public Map<String, String> CreatePeUserdisablelog(@RequestParam String userid,@RequestParam String starttime,@RequestParam String endtime,@RequestParam String reason,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser op = this.getLoginOpUser(request);
		PeUserdisablelog peUserdisablelog = new PeUserdisablelog();
		peUserdisablelog.setUserid(userid);
		peUserdisablelog.setReason(reason);
		try {
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date starttime1 = sdf.parse(starttime);
			Date endtime1 = sdf.parse(endtime);
			peUserdisablelog.setStarttime(starttime1);
			peUserdisablelog.setEndtime(endtime1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		peUserdisablelog.setId(GUIDGenerator.newGUID());
		peUserdisablelog.setCreater(op.getNickname());
		peUserdisablelog.setUpdater(op.getNickname());
		peUserdisablelog.setAction("0");
		return templateHelper.dealRequestWithToken("/PeUser/CreatePeUserdisablelog", HttpMethod.POST, userToken, peUserdisablelog,
				Map.class);
	}
	
	@RequestMapping("/PeUser/ExportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		//List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		PeUserQueryParam pu = new PeUserQueryParam();
		String queryAccount =request.getParameter("queryAccount");
		String queryCompanystate =request.getParameter("queryCompanystate");
		String startTime =request.getParameter("startTime");
		String endTime =request.getParameter("endTime");
		pu.setQueryAccount(queryAccount);
		pu.setQueryCompanystate(queryCompanystate);
		pu.setStartTime(startTime);
		pu.setEndTime(endTime);
		List<Map> peUser = templateHelper.dealRequestWithToken("/PeUser/ExportData", HttpMethod.POST,
				userToken, pu, List.class);
		for(int i=0;i<peUser.size();i++){
			if(peUser.get(i).get("nickname") != null){
				colData1.add(peUser.get(i).get("nickname"));
			}else{
				colData1.add("");
			}
			if(peUser.get(i).get("account") != null){
				colData2.add(peUser.get(i).get("account"));
			}else{
				colData2.add("");
			}
			if(peUser.get(i).get("sex") != null){
				colData3.add(peUser.get(i).get("sex"));
			}else{
				colData3.add("");
			}
			/*if(peUser.get(i).get("email") != null){
				colData4.add(peUser.get(i).get("email"));
			}else{
				colData4.add("");
			}*/
			if(peUser.get(i).get("registertime") != null){
				Long times = (Long)peUser.get(i).get("registertime");
				Date date = new Date(times);
		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				colData5.add(format.format(date));
			}else{
				colData5.add("");
			}
			if(peUser.get(i).get("disablestate") != null){
				colData6.add(peUser.get(i).get("disablestate"));
			}else{
				colData6.add("");
			}
			if(peUser.get(i).get("orgUserAccount") != null && !peUser.get(i).get("orgUserAccount").equals("")){
				colData7.add("机构用户");
			}else{
				colData7.add("非机构用户");
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("用户管理.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("用户类型");
		colName.add("称呼");
		colName.add("账号");
		colName.add("性别");
		//colName.add("邮箱");
		colName.add("注册时间");
		colName.add("账号状态");
		excel.setColName(colName);
		colData.put("用户类型", colData7);
		colData.put("称呼", colData1);
		colData.put("账号", colData2);
		colData.put("性别", colData3);
		//colData.put("邮箱", colData4);
		colData.put("注册时间", colData5);
		colData.put("账号状态", colData6);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
//		ee.setSheetMaxRow(colData.size());
		ee.setSheetName("个人用户管理");
		ee.createExcel(tempFile);
		
//		ExcelExportController excelExportController = new ExcelExportController();
//		excelExportController.exportExcel(request, response, excel);
	}
}
