package com.szyciov.organ.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrganBill;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.param.OrganAccountQueryParam;
import com.szyciov.lease.param.OrganBillQueryParam;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.FinancialManagementQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport2;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
import com.wx.QrcodeUtil;

@Controller
public class FinancialManagementController extends BaseController {
	private static final Logger logger = Logger.getLogger(FinancialManagementController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping("FinancialManagement/Index")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView getIndex(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		OrgUser orgUser = getLoginOrgUser(request);
		String organId = orgUser.getOrganId();
		
		// 账户列表
		List<OrgOrganCompanyRef> organCompanyRef = templateHelper.dealRequestWithToken(
				"/FinancialManagement/GetAccountListByOrganId/{organId}", HttpMethod.GET, userToken,
				null, List.class, organId);
		// 租赁公司列表
		List<LeLeasescompany> leasesCompany = templateHelper.dealRequestWithToken(
				"/FinancialManagement/GetLeasesCompanyListByOrgan?organId={organId}", HttpMethod.GET, userToken,
				null, List.class, organId);
		
		if ("2".equals(request.getParameter("index"))) {
			mav.addObject("tabnum", "1");
		}

		mav.addObject("organCompanyRef", organCompanyRef);
		mav.addObject("leasesCompany", leasesCompany);
		mav.setViewName("resource/financialManagement/index");
		return mav;
	}

	@RequestMapping("FinancialManagement/GetOrganBillByQuery")
	@ResponseBody
	public PageBean getOrganBillByQuery(@RequestBody FinancialManagementQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = getLoginOrgUser(request);
		queryParam.setOrganId(orgUser.getOrganId());
		queryParam.setiDisplayLength(20);
		return templateHelper.dealRequestWithToken("/FinancialManagement/GetOrganBillByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("FinancialManagement/ExpensesDetail")
	@ResponseBody
	public ModelAndView getExpensesDetail(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "organId", required = true) String organId, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("leasesCompanyId", leasesCompanyId);
		mav.addObject("organId", organId);
		mav.setViewName("resource/financialManagement/expensesDetail");
		return mav;
	}

	@RequestMapping("FinancialManagement/GetOrganExpensesByQuery")
	@ResponseBody
	public PageBean getOrganExpensesByQuery(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "organId", required = true) String organId,
			@RequestBody OrganAccountQueryParam queryParam, HttpServletRequest request, HttpServletResponse response)
					throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		//OrgUser orgUser = getLoginOrgUser(request);
		queryParam.setOrganId(organId);
		queryParam.setLeasesCompanyId(leasesCompanyId);
		queryParam.setiDisplayLength(20);
		
		// 初始化时为了查询不到数据设置
		if (queryParam.getNum() == 0) {
			queryParam.setType("9");
		}
		
		return templateHelper.dealRequestWithToken("/FinancialManagement/GetOrganExpensesByQuery", HttpMethod.POST,
				userToken, queryParam, PageBean.class);
	}
	
	@RequestMapping("FinancialManagement/BillDetail/{id}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView getBillDetail(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		// 账单信息
		OrgOrganBill orgOrganBill = templateHelper.dealRequestWithToken(
				"/FinancialManagement/GetOrganBillById/{id}", HttpMethod.GET, userToken,
				null, OrgOrganBill.class, id);
		
		// 账单状态
		List<Map<String, Object>> organBillState = templateHelper.dealRequestWithToken(
				"/FinancialManagement/GetOrganBillStateById/{id}", HttpMethod.GET, userToken,
				null, List.class, id);
		
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  
        String createTime = format1.format(orgOrganBill.getCreateTime());

		mav.addObject("orgOrganBill", orgOrganBill);
		mav.addObject("createTime", createTime);
		mav.addObject("organBillState", organBillState);
		
		mav.setViewName("resource/financialManagement/billDetail");
		return mav;
	}
	
	@RequestMapping("FinancialManagement/GetOrgOrderByQuery/{billsId}")
	@ResponseBody
	public PageBean getOrgOrderByQuery(@PathVariable String billsId, @RequestBody OrganBillQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		//OrgUser orgUser = getLoginOrgUser(request);
		queryParam.setOrganId(request.getParameter("organId"));
		queryParam.setBillsId(billsId);
		queryParam.setiDisplayLength(20);
		
		return templateHelper.dealRequestWithToken("/FinancialManagement/GetOrgOrderByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("FinancialManagement/UpdateOrganBillState")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updateOrganBillState(@RequestParam(value = "billsId", required = true) String billsId,
			@RequestParam(value = "billValue", required = true) String billValue,
			@RequestParam(value = "comment", required = false) String comment, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken(
				"/FinancialManagement/UpdateOrganBillState?billsId={billsId}&billValue={billValue}&comment={comment}",
				HttpMethod.POST, userToken, null, Map.class, billsId, billValue, comment);
	}

	@RequestMapping("FinancialManagement/GetActualBalance")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, Object> getActualBalance(@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "organId", required = true) String organId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		//OrgUser orgUser = getLoginOrgUser(request);
		//String organId = orgUser.getOrganId();
		
		return templateHelper.dealRequestWithToken(
				"/FinancialManagement/GetActualBalance?organId={organId}&leasesCompanyId={leasesCompanyId}", HttpMethod.GET,
				userToken, null, Map.class, organId, leasesCompanyId);
	}
	
	@RequestMapping("FinancialManagement/ConfirmAccount")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> confirmAccount(@RequestBody OrgOrganBill orgOrganBill, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = getLoginOrgUser(request);
		//orgOrganBill.setOrganId(orgUser.getOrganId());
		orgOrganBill.setUpdater(orgUser.getId());
		return templateHelper.dealRequestWithToken("/FinancialManagement/ConfirmAccount",HttpMethod.POST, userToken, orgOrganBill, Map.class);
	}
	
	@RequestMapping("FinancialManagement/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData(@RequestParam(value = "billsId", required = true) String billsId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		//OrgUser orgUser = getLoginOrgUser(request);
		//String organId = orgUser.getOrganId();

		// 账单信息
		OrgOrganBill orgOrganBill = templateHelper.dealRequestWithToken("/FinancialManagement/GetOrganBillById/{billsId}",
				HttpMethod.GET, userToken, null, OrgOrganBill.class, billsId);
		List<String> rowData = new ArrayList<String>();
		rowData.add("账单名称：" + orgOrganBill.getName() + " 账单金额：" + orgOrganBill.getMoney() + "元");
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  
        String createTime = format1.format(orgOrganBill.getCreateTime());
		rowData.add("出账时间：" + createTime + " 租赁公司：" + orgOrganBill.getShortName());
		
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
		
		List<Map> OrgOrder = templateHelper.dealRequestWithToken(
				"/FinancialManagement/GetOrgOrderListExport?billsId={billsId}&organId={organId}", HttpMethod.POST,
				userToken, null, List.class, billsId, orgOrganBill.getOrganId());
		
		for (int i = 0; i < OrgOrder.size(); i++) {
			colData1.add((String) OrgOrder.get(i).get("orderno"));
			colData2.add((String) OrgOrder.get(i).get("nickname"));
			colData3.add((String) OrgOrder.get(i).get("account"));
			if (OrgOrder.get(i).get("deptname") == null) {
				colData4.add("");
			} else {
				colData4.add((String) OrgOrder.get(i).get("deptname"));
			}
			colData5.add((String) OrgOrder.get(i).get("passengers"));
			colData6.add((String) OrgOrder.get(i).get("onaddress"));
			colData7.add((String) OrgOrder.get(i).get("offaddress"));
			colData8.add(OrgOrder.get(i).get("starttime").toString().replace("-", "/").substring(0, 16));
			colData9.add(OrgOrder.get(i).get("endtime").toString().replace("-", "/").substring(0, 16));
			colData10.add("¥" + String.valueOf(OrgOrder.get(i).get("orderamount")));
			if (OrgOrder.get(i).get("tripremark") == null) {
				colData11.add("");
			} else {
				colData11.add((String) OrgOrder.get(i).get("tripremark"));
			}
			if (OrgOrder.get(i).get("vehiclessubjecttype") == null) {
				colData12.add("");
			} else {
				colData12.add((String) OrgOrder.get(i).get("vehiclessubjecttype"));
			}
			if (OrgOrder.get(i).get("vehiclessubject") == null) {
				colData13.add("");
			} else {
				colData13.add((String) OrgOrder.get(i).get("vehiclessubject"));
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("【" + orgOrganBill.getName() + "】账单明细.xls");
		List<String> colName = new ArrayList<String>();
		colName.add("订单号");
		colName.add("下单人姓名");
		colName.add("下单人电话");
		colName.add("下单人部门");
		colName.add("乘车人姓名");
		colName.add("出发地");
		colName.add("目的地");
		colName.add("上车时间");
		colName.add("下车时间");
		colName.add("金额");
		colName.add("用车事由");
		colName.add("事由说明");
		colName.add("行程备注");
		excel.setColName(colName);
		
		colData.put("订单号", colData1);
		colData.put("下单人姓名", colData2);
		colData.put("下单人电话", colData3);
		colData.put("下单人部门", colData4);
		colData.put("乘车人姓名", colData5);
		colData.put("出发地", colData6);
		colData.put("目的地", colData7);
		colData.put("上车时间", colData8);
		colData.put("下车时间", colData9);
		colData.put("金额", colData10);
		colData.put("用车事由", colData12);
		colData.put("事由说明", colData13);
		colData.put("行程备注", colData11);
		excel.setColData(colData);
		
		excel.setRowData(rowData);
		
		ExcelExport2 ee = new ExcelExport2(request, response, excel);
		ee.setTitleRowCount(3);
		//ee.setSheetMaxRow(6);
		ee.setSheetName("账单明细");
		ee.createExcel(tempFile);
	}
	
	@RequestMapping("FinancialManagement/Recharge")
	@ResponseBody
	public ModelAndView recharge(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "organId", required = true) String organId, HttpServletRequest request,
			HttpServletResponse response) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		// 租赁公司信息
		LeLeasescompany leLeasesCompany = templateHelper.dealRequestWithToken("/FinancialManagement/GetLeasesCompanyById/{leasesCompanyId}",
				HttpMethod.GET, userToken, null, LeLeasescompany.class, leasesCompanyId);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("leasesCompanyId", leasesCompanyId);
		mav.addObject("organId", organId);
		mav.addObject("leLeasesCompany", leLeasesCompany);
		mav.addObject("pay", request.getParameter("pay"));//判断是否支付成功
		mav.setViewName("resource/financialManagement/recharge");
		return mav;
	}
	
	@RequestMapping("FinancialManagement/GetLeasesCompanyById/{leasesCompanyId}")
	@ResponseBody
	public Map<String, String> getLeasesCompanyById(@PathVariable String leasesCompanyId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LeLeasescompany leLeasesCompany = templateHelper.dealRequestWithToken(
				"/FinancialManagement/GetLeasesCompanyById/{leasesCompanyId}", HttpMethod.GET, userToken, null,
				LeLeasescompany.class, leasesCompanyId);
		boolean result = false;
		// 后台配置了支付账号并且已开通 0-未开通,1-已开通
		if (leLeasesCompany.getAlipayAccount() != null && !"".equals(leLeasesCompany.getAlipayAccount())
				&& "1".equals(leLeasesCompany.getAlipaystatus())
				|| leLeasesCompany.getWechatAccount() != null && !"".equals(leLeasesCompany.getWechatAccount())
						&& "1".equals(leLeasesCompany.getWechatstatus())) {
			result = true;
		}
		Map<String, String> ret = new HashMap<String, String>();
		if (result) {
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "");
		} else {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "暂未开通充值服务");
		}
		return ret;
	}
	
	@RequestMapping("FinancialManagement/GoPay")
	@ResponseBody
	public ModelAndView goPay(HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		ModelAndView mav = new ModelAndView();
		mav.addObject("leasesCompanyId", request.getParameter("leasesCompanyId"));
		mav.addObject("organId", request.getParameter("organId"));
		mav.addObject("paytype", request.getParameter("paytype"));
		mav.addObject("rechargeAmount", request.getParameter("rechargeAmount"));
		if ("2".equalsIgnoreCase(request.getParameter("paytype"))) {
			// 微信支付			
			mav.setViewName("resource/financialManagement/wxpay");
		} else {
			mav.setViewName("resource/financialManagement/zfbpay");
		}
		return mav;
	}
	
	/**
	 * payorder4zfb
	 * @param loginparam
	 * @return
	 */
	@RequestMapping(value = "FinancialManagement/GetZFBQRCode", method = RequestMethod.GET)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView getZFBQRCode(@RequestParam String leasesCompanyId,@RequestParam String organId,@RequestParam double rechargeAmount,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = getLoginOrgUser(request);
		ModelAndView mav = new ModelAndView();
		Map<String,Object> payparam = new HashMap<String,Object>();
		payparam.put("leasesCompanyId", leasesCompanyId);
		payparam.put("organId", organId);
		payparam.put("rechargeAmount", rechargeAmount);
		payparam.put("paytype", "1");
		payparam.put("operator", orgUser.getId());
		Map<String,Object> resultmap = templateHelper.dealRequestWithToken("/FinancialManagement/UpdatePaytype", HttpMethod.POST, userToken,payparam,Map.class);
		if(resultmap!=null&&"success".equals(resultmap.get("status"))){
			mav.addObject("out_trade_no", resultmap.get("out_trade_no"));
			mav.addObject("partner", resultmap.get("partner"));
			mav.addObject("notify_url", resultmap.get("notify_url"));
			mav.addObject("subject", resultmap.get("subject"));
			mav.addObject("total_fee", resultmap.get("total_fee"));
			mav.addObject("partner_private_key", resultmap.get("partner_private_key"));
			mav.addObject("status","success");
		}else{
			mav.addAllObjects(resultmap);
		}
		mav.addObject("leasesCompanyId", leasesCompanyId);
		mav.addObject("organId", organId);
		mav.setViewName("resource/financialManagement/alipayapi");
		return mav;
	}
	
	@RequestMapping("FinancialManagement/UpdatePaytype")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public  Map<String, Object> updatePaytype(@RequestParam String leasesCompanyId,@RequestParam String organId,@RequestParam String paytype,@RequestParam String rechargeAmount,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		Map<String,Object> res = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("leasesCompanyId", leasesCompanyId);
		param.put("organId", organId);
		param.put("rechargeAmount", rechargeAmount);
		param.put("paytype", paytype);
		param.put("ipadd", request.getRemoteAddr());
		OrgUser orgUser = getLoginOrgUser(request);
		param.put("operator", orgUser.getId());
		res.put("status", "fail");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if("2".equalsIgnoreCase(paytype)){
			//微信支付
			Map<String,Object> preinfo = templateHelper.dealRequestWithToken("/FinancialManagement/UpdatePaytype", HttpMethod.POST, userToken, param,Map.class);
			if(preinfo!=null&&"success".equalsIgnoreCase((String)preinfo.get("status"))){
				res.put("status", "success");
				res.put("code_url", preinfo.get("code_url"));
				
				res.put("out_trade_no", preinfo.get("out_trade_no"));
			}else{
				res.put("message", preinfo.get("message"));
			}
		}else{
			
		}
		return res;
	}
	
	/**
	 * payorder4wx
	 * @param loginparam
	 * @return
	 */
	@RequestMapping(value = "FinancialManagement/GetWXQRCode", method = RequestMethod.GET)
	public void getWXQRCode(@RequestParam String codeurl,HttpServletRequest request,HttpServletResponse response){
		String content = codeurl;
		BufferedImage bim = QrcodeUtil.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 300, 300, QrcodeUtil.getDecodeHintType());
		try {
			ImageIO.write(bim, "jpeg", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * CheckOrderState
	 * @param loginparam
	 * @return
	 */
	@RequestMapping(value = "FinancialManagement/CheckPayState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkPayState(@RequestParam String out_trade_no,HttpServletRequest request,HttpServletResponse response){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("out_trade_no", out_trade_no);
		return templateHelper.dealRequestWithToken("/FinancialManagement/CheckPayState", HttpMethod.POST, userToken,params,Map.class);	
	}
	
	
	@RequestMapping("FinancialManagement/JudgeOrganAccountStatus")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> judgeOrganAccountStatus(@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "organId", required = true) String organId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		Map<String, Object> withdrawInfo = templateHelper.dealRequestWithToken("/FinancialManagement/GetWithdrawInfo?organId={organId}&leasesCompanyId={leasesCompanyId}", HttpMethod.GET,userToken, null, Map.class, organId, leasesCompanyId);
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "");
		if (withdrawInfo.get("creditcardnum") == null || StringUtils.isBlank(withdrawInfo.get("creditcardnum").toString()) || withdrawInfo.get("creditcardname") == null || StringUtils.isBlank(withdrawInfo.get("creditcardname").toString()) || withdrawInfo.get("bankname") == null || StringUtils.isBlank(withdrawInfo.get("bankname").toString())) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "提现账户信息为空，请前往\"账户信息管理\"完成设置，否则无法完成提现业务。");
		}
		return ret;
	}
	
	
	
	@RequestMapping("FinancialManagement/GetWithdrawInfo")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView getWithdrawInfo(@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "organId", required = true) String organId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		Map<String, Object> withdrawInfo = templateHelper.dealRequestWithToken("/FinancialManagement/GetWithdrawInfo?organId={organId}&leasesCompanyId={leasesCompanyId}", HttpMethod.GET,userToken, null, Map.class, organId, leasesCompanyId);
		mav.addObject("withdrawInfo", withdrawInfo);
		mav.addObject("leasesCompanyId", leasesCompanyId);
		mav.addObject("organId", organId);
		mav.setViewName("resource/financialManagement/withdraw");

		return mav;
	}
	
	@RequestMapping("FinancialManagement/WithdrawOrganAccount")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> withdrawOrganAccount(@RequestBody OrgOrganExpenses org_organexpenses, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = getLoginOrgUser(request);

		org_organexpenses.setUpdater(orgUser.getId());
		return templateHelper.dealRequestWithToken("/FinancialManagement/WithdrawOrganAccount",HttpMethod.POST, userToken, org_organexpenses, Map.class);
	}
	
	@RequestMapping("FinancialManagement/GetPubWithdraw/{id}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView getPubWithdraw(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		Map<String, Object> withdrawInfo = templateHelper.dealRequestWithToken("/FinancialManagement/GetPubWithdraw/{id}", HttpMethod.GET,userToken, null, Map.class, id);
		mav.addObject("withdrawInfo", withdrawInfo);
		mav.addObject("leasesCompanyId", request.getParameter("leasesCompanyId"));
		mav.addObject("organId", request.getParameter("organId"));
		mav.setViewName("resource/financialManagement/withdrawdetail");

		return mav;
	}
}
