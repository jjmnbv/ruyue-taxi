package com.szyciov.lease.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.OrgOrganBill;
import com.szyciov.lease.entity.OrgOrganBillState;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.OrganBillQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.ExcelExport2;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Controller
public class OrganBillController extends BaseController {
	private static final Logger logger = Logger.getLogger(OrganBillController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/OrganBill/Index")
	public String getOrganBillIndex(HttpServletRequest request) {
		return "resource/organBill/index";
	}
	
	@RequestMapping(value = "/OrganBill/HistoryIndex")
	public String getOrganBillHistoryIndex(HttpServletRequest request) {
		return "resource/organBill/history";
	}
	
	@RequestMapping("/OrganBill/GetCurOrganBillByQuery")
	@ResponseBody
	public PageBean getCurOrganBillByQuery(@RequestParam(value = "billClass", required = true) String billClass, @RequestBody OrganBillQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setSpecialState(user.getSpecialstate());
		queryParam.setAccount(user.getAccount());
		
		queryParam.setBillClass(billClass);
		return templateHelper.dealRequestWithToken("/OrganBill/GetCurOrganBillByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/OrganBill/GetOrganBillStateById/{id}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<OrgOrganBillState> getOrganBillStateById(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		return templateHelper.dealRequestWithToken("/OrganBill/GetOrganBillStateById/{id}", HttpMethod.GET, userToken, null,
				List.class, id);
	}
	
	@RequestMapping("/OrganBill/GetOrgOrderByQuery/{id}")
	@ResponseBody
	public PageBean getOrgOrderByQuery(@PathVariable String id, @RequestBody OrganBillQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		
		if (queryParam.getBillsId() == null) {
			queryParam.setBillsId(id);
		}
		return templateHelper.dealRequestWithToken("/OrganBill/GetOrgOrderByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/OrganBill/GetManualOrgOrderByQuery")
	@ResponseBody
	public PageBean getManualOrgOrderByQuery(@RequestBody OrganBillQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/OrganBill/GetManualOrgOrderByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/OrganBill/CreateOrganbill")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createOrganbill(@RequestBody OrgOrganBill orgOrganBill, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		orgOrganBill.setLeasesCompanyId(user.getLeasescompanyid());
		
		return templateHelper.dealRequestWithToken("/OrganBill/CreateOrganbill", HttpMethod.POST, userToken, orgOrganBill,
				Map.class);
	}
	
	@RequestMapping("/OrganBill/DeleteOrganBill/{id}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> deleteOrganbill(@PathVariable String id,
			@RequestParam(value = "remark", required = true) String remark, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/OrganBill/DeleteOrganBill/{id}?remark={remark}", HttpMethod.POST,
				userToken, null, Map.class, id, remark);
	}
	
	/*@RequestMapping("/OrganBill/ConfirmAccount")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> confirmAccount(@RequestBody OrgOrganBill orgOrganBill, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);

		orgOrganBill.setLeasesCompanyId(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/OrganBill/ConfirmAccount",HttpMethod.POST, userToken, orgOrganBill, Map.class);
	}*/
	
	@RequestMapping("/OrganBill/GetActualBalance")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, Object> getActualBalance(@RequestParam(value = "organId", required = true) String organId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		
		return templateHelper.dealRequestWithToken(
				"/OrganBill/GetActualBalance?organId={organId}&leasesCompanyId={leasesCompanyId}", HttpMethod.GET,
				userToken, null, Map.class, organId, leasesCompanyId);
	}
	
	@RequestMapping(value = "/OrganBill/BillDetail/{id}")
	@SuppressWarnings("unchecked")
	public ModelAndView getOrganBillDetail(@PathVariable String id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		OrgOrganBill orgOrganBill = templateHelper.dealRequestWithToken("/OrganBill/GetOrganBillById/{id}", HttpMethod.GET,
				userToken, null, OrgOrganBill.class, id);
		
		mav.addObject("orgOrganBill", orgOrganBill);
		mav.setViewName("resource/organBill/billDetail");
		return mav;
	}
	
	@RequestMapping(value = "/OrganBill/BillCheckDetail/{id}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public OrgOrganBill getOrganBillCheckDetail(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrganBill/GetOrganBillById/{id}", HttpMethod.GET, userToken, null,
				OrgOrganBill.class, id);
	}
	
	@RequestMapping("/OrganBill/CheckComplete/{billsId}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> CheckComplete(@PathVariable String billsId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/OrganBill/CheckComplete/{billsId}", HttpMethod.POST, userToken,
				null, Map.class, billsId);
	}
	
	@RequestMapping(value = "/OrganBill/ReCreateBill/{id}")
	@SuppressWarnings("unchecked")
	public ModelAndView getReCreateBill(@PathVariable String id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		OrgOrganBill orgOrganBill = templateHelper.dealRequestWithToken("/OrganBill/GetOrganBillById/{id}", HttpMethod.GET,
				userToken, null, OrgOrganBill.class, id);
		
		mav.addObject("orgOrganBill", orgOrganBill);
		mav.setViewName("resource/organBill/reCreateBill");
		return mav;
	}
	
	@RequestMapping("/OrganBill/GetOrgOrderById/{id}")
	@ResponseBody
	public PageBean getOrgOrderById(@PathVariable String id, @RequestBody OrganBillQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		
		if (queryParam.getOrderNo() == null) {
			queryParam.setOrderNo(id);
		}
		
		String[] orderNo = queryParam.getOrderNo().split(",");
		String orderId = "";
		for (int i = 0; i < orderNo.length; i++) {
			if (i == 0) {
				orderId += "'" + orderNo[i] + "'";
			} else {
				orderId += ",'" + orderNo[i] + "'";
			}
		}
		
		queryParam.setOrderNo(orderId);
		return templateHelper.dealRequestWithToken("/OrganBill/GetOrgOrderById", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/OrganBill/ReCreateOrganbill")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> reCreateOrganbill(@RequestBody OrgOrganBill orgOrganBill, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		orgOrganBill.setLeasesCompanyId(user.getLeasescompanyid());
		
		return templateHelper.dealRequestWithToken("/OrganBill/ReCreateOrganbill", HttpMethod.POST, userToken, orgOrganBill,
				Map.class);
	}
	
	@RequestMapping("/OrganBill/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData(@RequestParam(value = "organId", required = false) String organId,
			@RequestParam(value = "billState", required = false) String billState,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "billClass", required = true) String billClass, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8 = new ArrayList<Object>();
		OrganBillQueryParam queryParam = new OrganBillQueryParam();
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setOrganId(organId);
		queryParam.setBillState(billState);
		queryParam.setStartTime(startTime);
		queryParam.setEndTime(endTime);
		queryParam.setBillClass(billClass);
		queryParam.setSpecialState(user.getSpecialstate());
		queryParam.setAccount(user.getAccount());
		List<Map> orgOrganBill = templateHelper.dealRequestWithToken("/OrganBill/GetOrganBillListExport",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganBill.size(); i++) {
			colData1.add((String) orgOrganBill.get(i).get("sourceName"));
			colData2.add((String) orgOrganBill.get(i).get("name"));
			colData3.add((String) orgOrganBill.get(i).get("shortName"));
			colData4.add((String) orgOrganBill.get(i).get("billStateName"));
			colData5.add(String.valueOf(orgOrganBill.get(i).get("money")));
			colData6.add((String) orgOrganBill.get(i).get("operationTime"));
			colData7.add((String) orgOrganBill.get(i).get("createTime"));
			colData8.add((String) orgOrganBill.get(i).get("id"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("机构账单.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("账单编号");
		colName.add("账单来源");
		colName.add("账单名称");
		colName.add("机构");
		colName.add("账单状态");
		colName.add("账单金额(元)");
		colName.add("最后更新时间");
		colName.add("账单生成时间");
		excel.setColName(colName);
		colData.put("账单编号", colData8);
		colData.put("账单来源", colData1);
		colData.put("账单名称", colData2);
		colData.put("机构", colData3);
		colData.put("账单状态", colData4);
		colData.put("账单金额(元)", colData5);
		colData.put("最后更新时间", colData6);
		colData.put("账单生成时间", colData7);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		//ee.setSheetMaxRow(6);
		ee.setSheetName("机构账单");
		ee.createExcel(tempFile);

	}
	
	@RequestMapping("/OrganBill/SelectOrganList")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectOrganList(@RequestParam(value = "fullName", required = false) String fullName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		String specialState = user.getSpecialstate();
		String account = user.getAccount();
		
		return templateHelper.dealRequestWithToken("/OrganBill/SelectOrganList?leasesCompanyId={leasesCompanyId}&fullName={fullName}&specialState={specialState}&account={account}", HttpMethod.GET, userToken, null,
				List.class, leasesCompanyId, fullName, specialState, account);
	}
	
	@RequestMapping("/OrganBill/CreateOrganBillAuto")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> reCreateOrganbill(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		OrgOrganBill orgOrganBill = new OrgOrganBill();
		if (request.getParameter("leasesCompanyId") != null && !"".equals(request.getParameter("leasesCompanyId"))) {
			orgOrganBill.setLeasesCompanyId(request.getParameter("leasesCompanyId"));
		} else {
			if (user != null) {
				orgOrganBill.setLeasesCompanyId(user.getLeasescompanyid());
			}
		}
		if (request.getParameter("userId") != null && !"".equals(request.getParameter("userId"))) {
			orgOrganBill.setCreater(request.getParameter("userId"));
			orgOrganBill.setUpdater(request.getParameter("userId"));
		} else {
			if (user != null) {
				orgOrganBill.setCreater(user.getId());
				orgOrganBill.setUpdater(user.getId());
			}
		}
		// 必须的
		orgOrganBill.setOrganId(request.getParameter("organId"));
		orgOrganBill.setSource(request.getParameter("source"));
		orgOrganBill.setStartTime(request.getParameter("startTime"));
		orgOrganBill.setEndTime(request.getParameter("endTime"));
		// 非必须的
		if (request.getParameter("name") != null && !"".equals(request.getParameter("name"))) {
			orgOrganBill.setName(request.getParameter("name"));
		} else {
			Date date=new Date();
			int month = date.getMonth();
			int year = date.getYear() + 1900;
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	        //String year = sdf.format(date);
			
			String name = "";
			// 0-月结
			if ("0".equals(orgOrganBill.getSource())) {
				if (month == 0) {
					year = year - 1;
					month = 12;
				}
				name = year + "年" + month + "月账单";
			} else if ("1".equals(orgOrganBill.getSource())) {// 1-季结
				String quarter = "";
				if (month >= 0 && month < 3) {
					year = year - 1;
					quarter = "4";
				} else if (month >= 3 && month < 6) {
					quarter = "1";
				} else if (month >= 6 && month < 9) {
					quarter = "2";
				} else if (month >= 9 && month < 12) {
					quarter = "3";
				}
				name = year + "年" + quarter + "季度账单";
			}
			orgOrganBill.setName(name);
		}
		
		if (request.getParameter("remark") != null) {
			orgOrganBill.setRemark(request.getParameter("remark"));
		} else {
			orgOrganBill.setRemark("");
		}
		orgOrganBill.setBillState(request.getParameter("billState"));

		return templateHelper.dealRequestWithToken("/OrganBill/CreateOrganBillAuto", HttpMethod.POST, userToken, orgOrganBill,
				Map.class);
	}
	
	@RequestMapping("/OrganBill/GetOrgOrderListExport")
	@SuppressWarnings("unchecked")
	public void getOrgOrderListExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		
		List<String> rowData = new ArrayList<String>();
		String billname = request.getParameter("billName") + "(" + request.getParameter("shortName") + ")";
		rowData.add("账单名称：" + billname + " 账单编号：" + request.getParameter("billsId"));
		rowData.add("账单金额：￥" + request.getParameter("money") + " 账单备注：" + request.getParameter("remark"));

		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8 = new ArrayList<Object>();
		List<Object> colData9 = new ArrayList<Object>();
		List<Object> colData10 = new ArrayList<Object>();
		List<Object> colData11 = new ArrayList<Object>();
		List<Object> colData12 = new ArrayList<Object>();
		List<Object> colData13 = new ArrayList<Object>();
		List<Object> colData14 = new ArrayList<Object>();
		OrganBillQueryParam queryParam = new OrganBillQueryParam();
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setBillsId(request.getParameter("billsId"));
		List<Map> orgOrganBill = templateHelper.dealRequestWithToken("/OrganBill/GetOrgOrderListExport",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganBill.size(); i++) {
			colData1.add((String) orgOrganBill.get(i).get("ordertype"));
			colData2.add((String) orgOrganBill.get(i).get("orderno"));
			colData3.add((String) orgOrganBill.get(i).get("paymentstatus"));
			
			if ("7".equals(orgOrganBill.get(i).get("orderstatus").toString())) {
				colData4.add(String.valueOf(orgOrganBill.get(i).get("orderamount")));
				// 里程(公里)
				Double mileage = (Double) orgOrganBill.get(i).get("mileage");
				BigDecimal mileages = new BigDecimal(mileage/1000);
				colData5.add(String.valueOf(mileages.setScale(1, BigDecimal.ROUND_FLOOR)));
				// 计费时长(分钟)
				String priceCopy = (String) orgOrganBill.get(i).get("pricecopy");
				JSONObject priceCopyJson = JSONObject.fromObject(priceCopy);
				if ("1".equals(String.valueOf(priceCopyJson.get("timetype")))) {// 1-低速用时
					colData6.add(String.valueOf(priceCopyJson.get("slowtimes")));
				} else if ("0".equals(String.valueOf(priceCopyJson.get("timetype")))) {// 0-总用时
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					BigDecimal startTime = new BigDecimal(format.parse((String) orgOrganBill.get(i).get("starttime")).getTime());
					BigDecimal endTime = new BigDecimal(format.parse((String) orgOrganBill.get(i).get("endtime")).getTime());
					//long interval = (endTime - startTime)/(1000*60);
					BigDecimal interval = endTime.subtract(startTime).divide(new BigDecimal("1000").multiply(new BigDecimal("60")), 0, BigDecimal.ROUND_UP);
					colData6.add(String.valueOf(interval));
				}
				// 处罚金额
				colData14.add("/");
			} else {
				colData4.add("/");
				colData5.add("/");
				colData6.add("/");
				if (orgOrganBill.get(i).get("cancelamount") != null) {
					colData14.add(String.valueOf(orgOrganBill.get(i).get("cancelamount")));
				} else {
					colData14.add("/");
				}
			}

			colData7.add((String) orgOrganBill.get(i).get("userid"));
			colData8.add((String) orgOrganBill.get(i).get("passengers"));
			colData9.add((String) orgOrganBill.get(i).get("driverid"));
			colData10.add((String) orgOrganBill.get(i).get("endtime"));
			
			if (orgOrganBill.get(i).get("vehiclessubjecttype") != null) {
				colData11.add((String) orgOrganBill.get(i).get("vehiclessubjecttype"));
			} else {
				colData11.add("/");
			}
			if (orgOrganBill.get(i).get("vehiclessubject") != null) {
				colData12.add((String) orgOrganBill.get(i).get("vehiclessubject"));
			} else {
				colData12.add("/");
			}

			if ("1".equals(orgOrganBill.get(i).get("expensetype").toString())) {
				colData13.add("行程服务");
			} else {
				colData13.add("取消处罚");
			}
		}
		Excel excel = new Excel();
		// excel文件
		String fileName = "";
		if (request.getParameter("check") != null && "1".equals(request.getParameter("check"))) {
			fileName = "机构账单-【" + billname + "】核对账单.xls";
		} else {
			fileName = "机构账单-【" + billname + "】账单详情.xls";
		}
		File tempFile = new File(fileName);
		
		List<String> colName = new ArrayList<String>();
		colName.add("类型");
		colName.add("订单号");
		colName.add("订单状态");
		colName.add("费用类型");
		colName.add("订单金额(元)");
		colName.add("里程(公里)");
		colName.add("计费时长(分钟)");
		colName.add("处罚金额(元)");
		colName.add("下单人");
		colName.add("乘车人");
		colName.add("司机信息");
		colName.add("订单结束时间");
		colName.add("用车事由");
		colName.add("事由说明");
		excel.setColName(colName);
		colData.put("类型", colData1);
		colData.put("订单号", colData2);
		colData.put("订单状态", colData3);
		colData.put("费用类型", colData13);
		colData.put("订单金额(元)", colData4);
		colData.put("里程(公里)", colData5);
		colData.put("计费时长(分钟)", colData6);
		colData.put("处罚金额(元)", colData14);
		colData.put("下单人", colData7);
		colData.put("乘车人", colData8);
		colData.put("司机信息", colData9);
		colData.put("订单结束时间", colData10);
		colData.put("用车事由", colData11);
		colData.put("事由说明", colData12);
		excel.setColData(colData);
		
		excel.setRowData(rowData);
		
		ExcelExport2 ee = new ExcelExport2(request,response,excel);
		ee.setTitleRowCount(3);
		//ee.setSheetMaxRow(6);
		ee.setSheetName("账单详情");
		ee.createExcel(tempFile);

	}
}
