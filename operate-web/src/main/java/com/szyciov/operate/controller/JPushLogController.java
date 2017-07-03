package com.szyciov.operate.controller;


import java.io.File;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.szyciov.entity.Excel;
import com.szyciov.entity.PubJpushlog;
import com.szyciov.op.param.JPushLogQueryParam;
import com.szyciov.util.Constants;
import com.szyciov.util.DateUtil;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class JPushLogController {

    private static final Logger logger = Logger.getLogger(JPushLogController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping("/JPushLog/Index")
	public String Index(){
		
		return "resource/jpushLog/index";
	}
	
	@RequestMapping(value="/JPushLog/QueryJPushLogByParam")
	@ResponseBody
	public PageBean QueryJPushLogByParam(@RequestBody JPushLogQueryParam queryParam,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken=request.getParameter(Constants.REQUEST_USER_TOKEN);
		
		PageBean pageBean=templateHelper.dealRequestWithToken("/JPushLog/QueryJPushLogByParam", HttpMethod.POST, userToken, queryParam, PageBean.class);
		return pageBean;
	}
	
	@RequestMapping(value="/JPushLog/ExportExcel")
	public void exportExcel(JPushLogQueryParam param,HttpServletRequest request,HttpServletResponse response){
		String userToken=request.getParameter(Constants.REQUEST_USER_TOKEN);
		List<PubJpushlog> list=templateHelper.dealRequestWithToken("/JPushLog/ExportExcel", HttpMethod.POST, userToken, param, List.class);
		Map<String,List<Object>> colData=new HashMap<>();
		//订单号,司机手机号,司机姓名,极光推送状态,手机推送状态,举手状态,接单状态,创建时间,更新时间
		List<List<Object>> colList=new ArrayList<>(9);
		for(int x=0;x<9;x++){
			List<Object> tmpList=new ArrayList<>();
			colList.add(tmpList);
		}
			
		//装载数据
		for(int i=0;i<list.size();i++){
			PubJpushlog pushlog=JSON.parseObject(JSON.toJSONString(list.get(i)), PubJpushlog.class);
			colList.get(0).add(pushlog.getOrderno()==null ? "" : pushlog.getOrderno());
			colList.get(1).add(pushlog.getDriverphone()==null ? "" : pushlog.getDriverphone());
			colList.get(2).add(pushlog.getDrivername()==null ? "" : pushlog.getDrivername());
			colList.get(3).add(pushlog.getPushstate()==null ? "" : pushlog.getPushstate().equals(0) ? "推送失败" : "推送成功");
			colList.get(4).add(pushlog.getPhonepushstate()==null ? "" : pushlog.getPhonepushstate().equals(0) ? "推送失败" : "推送成功");
			colList.get(5).add(pushlog.getHandstate()==null ? "" : pushlog.getHandstate().equals(0) ? "未举手" : "已举手");
			colList.get(6).add(pushlog.getTakeorderstate()==null ? "" : pushlog.getTakeorderstate().equals(0) ? "接单失败" : "接单成功");
			colList.get(7).add(pushlog.getCreatetime()==null ? "" : DateUtil.format(pushlog.getCreatetime(),"yyyy/MM/dd HH:mm:ss"));
			colList.get(8).add(pushlog.getUpdatetime()==null ? "" : DateUtil.format(pushlog.getUpdatetime(),"yyyy/MM/dd HH:mm:ss"));
		}
		
		Excel excel = new Excel();
		File tempFile = new File("司机举手日志.xls");
		List<String> colName=new ArrayList<>();
		colName.add("订单号");
		colName.add("司机手机号");
		colName.add("司机姓名");
		colName.add("极光推送状态");
		colName.add("手机推送状态");
		colName.add("举手状态");
		colName.add("接单状态");
		colName.add("创建时间");
		colName.add("更新时间");
		excel.setColName(colName);
		colData.put("订单号", colList.get(0));
		colData.put("司机手机号", colList.get(1));
		colData.put("司机姓名", colList.get(2));
		colData.put("极光推送状态", colList.get(3));
		colData.put("手机推送状态", colList.get(4));
		colData.put("举手状态", colList.get(5));
		colData.put("接单状态", colList.get(6));
		colData.put("创建时间", colList.get(7));
		colData.put("更新时间", colList.get(8));
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(tempFile);
		
	}
	
	
}
