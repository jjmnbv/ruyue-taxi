package com.szyciov.lease.controller;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.OrderType;
import com.szyciov.driver.enums.PayState;
import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.TocOrderManageQueryParam;
import com.szyciov.lease.service.TocOrderManageService;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TocOrderManageController extends BaseController {
    private static final Logger logger = Logger.getLogger(TocOrderManageController.class);
	
    private TocOrderManageService tocOrderManageService;
	@Resource(name = "tocOrderManageService")
	public void setTocOrderManageService(TocOrderManageService tocOrderManageService) {
		this.tocOrderManageService = tocOrderManageService;
	}
    
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/OrderManage/NetAboutCarIndex")
	public String getNetAboutCarIndex(HttpServletRequest request) {
		return "resource/tocOrderManage/index";
	}
	
	@RequestMapping(value = "/OrderManage/TaxiIndex")
	public String getTaxiIndex(HttpServletRequest request) {
		return "resource/tocOrderManage/taxiorder";
	}
	
	@RequestMapping("/OrderManage/GetNetAboutCarOrderByQuery")
	@ResponseBody
	public PageBean getNetAboutCarOrderByQuery(@RequestBody TocOrderManageQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setCompanyId(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/OrderManage/GetNetAboutCarOrderByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/OrderManage/GetTaxiOrderByQuery")
	@ResponseBody
	public PageBean getTaxiOrderByQuery(@RequestBody TocOrderManageQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setCompanyId(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/OrderManage/GetTaxiOrderByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}

	@RequestMapping("/OrderManage/GetNetAboutCarOrderNOByQuery")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getNetAboutCarOrderNOByQuery(
			@RequestParam(value = "orderNo", required = false) String orderNo, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		
		return templateHelper.dealRequestWithToken(
				"/OrderManage/GetNetAboutCarOrderNOByQuery?companyId={leasesCompanyId}&orderNo={orderNo}",
				HttpMethod.GET, userToken, null, List.class, leasesCompanyId, orderNo);
	}
	
	@RequestMapping("/OrderManage/GetNetAboutCarOrderUserByQuery")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getNetAboutCarOrderUserByQuery(
			@RequestParam(value = "orderPerson", required = false) String orderPerson, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken(
				"/OrderManage/GetNetAboutCarOrderUserByQuery?orderPerson={orderPerson}", HttpMethod.GET, userToken,
				null, List.class, orderPerson);
	}
	
	@RequestMapping("/OrderManage/GetNetAboutCarOrderDriverByQuery")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(
			@RequestParam(value = "driver", required = false) String driver,
			@RequestParam(value = "vehicleType", required = true) String vehicleType, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		
		return templateHelper.dealRequestWithToken(
				"/OrderManage/GetNetAboutCarOrderDriverByQuery?companyId={leasesCompanyId}&driver={driver}&vehicleType={vehicleType}",
				HttpMethod.GET, userToken, null, List.class, leasesCompanyId, driver, vehicleType);
	}
	
	@RequestMapping("/OrderManage/GetNetAboutCarOrderExport")
	@SuppressWarnings("unchecked")
	public void getNetAboutCarOrderExport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
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
		List<Object> colData15 = new ArrayList<Object>();
		List<Object> colData16 = new ArrayList<Object>();
		List<Object> colData17 = new ArrayList<Object>();
		List<Object> colData18 = new ArrayList<Object>();
		List<Object> colData19 = new ArrayList<Object>();
		List<Object> colData20 = new ArrayList<Object>();
		List<Object> colData21 = new ArrayList<Object>();
		List<Object> colData22 = new ArrayList<Object>();
		List<Object> colData23 = new ArrayList<Object>();
		List<Object> colData24 = new ArrayList<Object>();
		List<Object> colData25 = new ArrayList<Object>();
		List<Object> colData26 = new ArrayList<Object>();
        List<Object> colData27 = new ArrayList<Object>();
        List<Object> colData28 = new ArrayList<Object>();
        List<Object> colData29 = new ArrayList<Object>();


		TocOrderManageQueryParam queryParam = new TocOrderManageQueryParam();
		queryParam.setCompanyId(getLoginLeUser(request).getLeasescompanyid());
		queryParam.setOrderNo(request.getParameter("orderNo"));
		queryParam.setOrderType(request.getParameter("orderType"));
		queryParam.setOrderStatus(request.getParameter("orderStatus"));
		queryParam.setOrderPerson(request.getParameter("orderPerson"));
		queryParam.setDriver(request.getParameter("driver"));
		queryParam.setPayType(request.getParameter("payType"));
		queryParam.setCancelParty(request.getParameter("cancelParty"));
		queryParam.setOrderSource(request.getParameter("orderSource"));
		queryParam.setTradeNo(request.getParameter("tradeNo"));
		queryParam.setMinUseTime(request.getParameter("minUseTime"));
		queryParam.setMaxUseTime(request.getParameter("maxUseTime"));
		queryParam.setBelongleasecompany(request.getParameter("belongleasecompany"));
		
		List<Map> netAboutCarOrder = templateHelper.dealRequestWithToken("/OrderManage/GetNetAboutCarOrderExport",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < netAboutCarOrder.size(); i++) {
			
			String judgeorderno = netAboutCarOrder.get(i).get("orderno").toString().substring(0, 2);
			if ("CG".equals(judgeorderno)) {
				colData1.add("乘客端 | 个人");
			} else if ("CY".equals(judgeorderno)) {
				colData1.add("运管端");
			} else {
				colData1.add("/");
			}
			
			colData2.add((String) netAboutCarOrder.get(i).get("orderno"));
			
			if (OrderType.BOOK.type.equals(netAboutCarOrder.get(i).get("ordertype"))) {
				colData3.add(OrderType.BOOK.msg);
			} else if (OrderType.PICKUP.type.equals(netAboutCarOrder.get(i).get("ordertype"))) {
				colData3.add(OrderType.PICKUP.msg);
			} else if (OrderType.PICKDOWN.type.equals(netAboutCarOrder.get(i).get("ordertype"))) {
				colData3.add(OrderType.PICKDOWN.msg);
			} else {
				colData3.add("");
			}
			
			colData4.add((String) netAboutCarOrder.get(i).get("selectedmodelname"));
			
			if (OrderState.WAITTAKE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString()) || OrderState.MANTICSEND.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData5.add(OrderState.WAITTAKE.msg);
				colData6.add("/");
				colData7.add("/");
			} else if (OrderState.WAITSTART.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData5.add(OrderState.WAITSTART.msg);
				colData6.add("/");
				colData7.add("/");
			} else if (OrderState.START.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData5.add(OrderState.START.msg);
				colData6.add("/");
				colData7.add("/");
			} else if (OrderState.ARRIVAL.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData5.add(OrderState.ARRIVAL.msg);
				colData6.add("/");
				colData7.add("/");
			} else if (OrderState.INSERVICE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData5.add(OrderState.INSERVICE.msg);
				colData6.add("/");
				colData7.add("/");
			} else if (OrderState.SERVICEDONE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				if (PayState.NOTPAY.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					colData5.add(PayState.NOTPAY.msg);
					colData6.add(PayState.NOTPAY.msg);
					colData7.add("/");
				} else if (PayState.PAYED.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					colData5.add(PayState.PAYED.msg);
					colData6.add(PayState.PAYED.msg);
					if (netAboutCarOrder.get(i).get("paytype") != null) {
						if ("1".equals(netAboutCarOrder.get(i).get("paytype").toString())) {
							colData7.add("余额支付");
						} else if ("2".equals(netAboutCarOrder.get(i).get("paytype").toString())) {
							colData7.add("微信支付");
						} else if ("3".equals(netAboutCarOrder.get(i).get("paytype").toString())) {
							colData7.add("支付宝支付");
						} else {
							colData7.add("/");
						}
					} else {
						colData7.add("");
					}
				}
			} else if (OrderState.CANCEL.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData5.add(OrderState.CANCEL.msg);
				colData6.add("/");
				colData7.add("/");
			} else {
				colData5.add("");
				colData6.add("");
				colData7.add("");
			}
			
			if (OrderState.SERVICEDONE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				if (netAboutCarOrder.get(i).get("shouldpayamount") != null) {
					colData8.add(String.valueOf(netAboutCarOrder.get(i).get("shouldpayamount")));
				} else {
					colData8.add("");
				}
				
				if (netAboutCarOrder.get(i).get("shouldpayamount") != null) {
					colData9.add(String.valueOf(netAboutCarOrder.get(i).get("shouldpayamount")));
				} else {
					colData9.add("");
				}	
				
				
				if (netAboutCarOrder.get(i).get("revieworderno") != null) {
					// 里程(公里)
					Double mileage = (Double) netAboutCarOrder.get(i).get("orderreviewmileage");
					Double mileages = mileage/1000;
					colData10.add(StringUtil.formatNum(mileages, 1));
					
					// 计费时长(分钟)
					String priceCopy = (String) netAboutCarOrder.get(i).get("pricecopy");
					JSONObject priceCopyJson = JSONObject.fromObject(priceCopy);
					if ("1".equals(String.valueOf(priceCopyJson.get("timetype")))) {// 1-低速用时
						colData11.add(String.valueOf(netAboutCarOrder.get(i).get("counttimes")));
					} else if ("0".equals(String.valueOf(priceCopyJson.get("timetype")))) {// 0-总用时
						Double times = (Double) netAboutCarOrder.get(i).get("times");
						colData11.add((int) Math.ceil(times/60));
					}
				} else {
					// 里程(公里)
					if (netAboutCarOrder.get(i).get("mileage") != null) {
						Double mileage = (Double) netAboutCarOrder.get(i).get("mileage");
						Double mileages = mileage/1000;
						colData10.add(StringUtil.formatNum(mileages, 1));
					} else {
						colData10.add("");
					}

					// 计费时长(分钟)
					String priceCopy = (String) netAboutCarOrder.get(i).get("pricecopy");
					JSONObject priceCopyJson = JSONObject.fromObject(priceCopy);
					if ("1".equals(String.valueOf(priceCopyJson.get("timetype")))) {// 1-低速用时
						colData11.add(String.valueOf(priceCopyJson.get("slowtimes")));
					} else if ("0".equals(String.valueOf(priceCopyJson.get("timetype")))) {// 0-总用时
						if (netAboutCarOrder.get(i).get("starttime") == null || netAboutCarOrder.get(i).get("endtime") == null) {
							colData11.add("");
						} else {
							long starttime = (long) netAboutCarOrder.get(i).get("starttime");
							long endtime = (long) netAboutCarOrder.get(i).get("endtime");
							colData11.add((int) Math.ceil((endtime - starttime)/1000.0/60));
						}
						//Date starttime = StringUtil.parseDate(netAboutCarOrder.get(i).get("starttime").toString(), "yyyy/MM/dd hh:mm");
						//Date endtime = StringUtil.parseDate(netAboutCarOrder.get(i).get("endtime").toString(), "yyyy/MM/dd hh:mm");
						//colData11.add(StringUtil.getTimeMinute(starttime, endtime));
					}
				}
				
			} else {
				colData8.add("/");
				colData9.add("/");
				colData10.add("/");
				colData11.add("/");
			}
			
			colData12.add((String) netAboutCarOrder.get(i).get("orderperson"));
			colData13.add((String) netAboutCarOrder.get(i).get("passengers") + " " + (String) netAboutCarOrder.get(i).get("passengerphone"));
			
			if (OrderState.WAITTAKE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString()) || OrderState.MANTICSEND.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData14.add("/");
				colData15.add("/");
				colData16.add("/");
				colData17.add("/");
			} else {
				if (netAboutCarOrder.get(i).get("driver") != null) {
					colData14.add((String) netAboutCarOrder.get(i).get("driver"));
				} else {
					colData14.add("");
				}
				if (netAboutCarOrder.get(i).get("jobnum") != null) {
					colData15.add((String) netAboutCarOrder.get(i).get("jobnum"));
				} else {
					colData15.add("");
				}
				if (netAboutCarOrder.get(i).get("plateno") != null) {
					colData16.add((String) netAboutCarOrder.get(i).get("plateno"));
				} else {
					colData16.add("");
				}
				if (netAboutCarOrder.get(i).get("leasescompanyname") != null) {
					colData17.add((String) netAboutCarOrder.get(i).get("leasescompanyname"));
				} else {
					colData17.add("");
				}
			}
			
			colData18.add("（" + (String) netAboutCarOrder.get(i).get("oncity") + "）" + (String) netAboutCarOrder.get(i).get("onaddress"));
			colData19.add("（" + (String) netAboutCarOrder.get(i).get("offcity") + "）" + (String) netAboutCarOrder.get(i).get("offaddress"));
			
			if (netAboutCarOrder.get(i).get("estimatedtime") != null) {
				colData20.add(netAboutCarOrder.get(i).get("estimatedtime"));
			} else {
				colData20.add("/");
			}
			if (netAboutCarOrder.get(i).get("estimatedmileage") != null) {
				colData21.add(netAboutCarOrder.get(i).get("estimatedmileage"));
			} else {
				colData21.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("undertime") != null) {
				colData22.add(netAboutCarOrder.get(i).get("undertime"));
			} else {
				colData22.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("usetime") != null) {
				colData23.add(netAboutCarOrder.get(i).get("usetime"));
			} else {
				colData23.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("ordertime") != null) {
				colData24.add(netAboutCarOrder.get(i).get("ordertime"));
			} else {
				colData24.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("starttimeexport") != null) {
				colData25.add(netAboutCarOrder.get(i).get("starttimeexport"));
			} else {
				colData25.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("endtimeexport") != null) {
				colData26.add(netAboutCarOrder.get(i).get("endtimeexport"));
			} else {
				colData26.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("tradeno") != null) {
				colData27.add(netAboutCarOrder.get(i).get("tradeno"));
			} else {
				colData27.add("/");
			}

			if(OrderState.SERVICEDONE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())){
                if(netAboutCarOrder.get(i).get("pricecopy") != null){
                    String pricecopy = (String) netAboutCarOrder.get(i).get("pricecopy");
                    if(StringUtils.isBlank(pricecopy)) {
                        colData28.add("0");
                        colData29.add("0");
                    } else {
                        JSONObject json = JSONObject.fromObject(pricecopy);

                        if(json.containsKey("nightcost")){
                            colData28.add(json.get("nightcost"));
                        }else{
                            colData28.add("0");
                        }

                        if(json.containsKey("deadheadcost")){
                            colData29.add(json.get("deadheadcost"));
                        }else{
                            colData29.add("0");
                        }
                    }
                }else{
                    colData28.add("0");
                    colData29.add("0");
                }
            }else{
                colData28.add("/");
                colData29.add("/");
            }

		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("toC订单-网约车.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("订单来源");
		colName.add("订单号");
		colName.add("订单类型");
		colName.add("服务车型");
		colName.add("订单状态");
		colName.add("支付状态");
		colName.add("支付渠道");
		colName.add("订单金额（元）");
		colName.add("应付订单金额（元）");
		colName.add("里程（公里）");
		colName.add("计费时长（分钟）");
        colName.add("空驶费（元）");
        colName.add("夜间费（元）");
        colName.add("下单人信息");
		colName.add("乘车人信息");
		colName.add("司机信息");
		colName.add("资格证号");
		colName.add("车牌号");
		colName.add("服务车企");
		colName.add("上车地址");
		colName.add("下车地址");
		colName.add("预估行驶时长（分钟）");
		colName.add("预估行驶里程（公里）");
		colName.add("下单时间");
		colName.add("用车时间");
		colName.add("接单时间");
		colName.add("开始时间");
		colName.add("结束时间");
		colName.add("交易流水号");
        colName.add("服务车企");
		excel.setColName(colName);
		colData.put("订单来源", colData1);
		colData.put("订单号", colData2);
		colData.put("订单类型", colData3);
		colData.put("服务车型", colData4);
		colData.put("订单状态", colData5);
		colData.put("支付状态", colData6);
		colData.put("支付渠道", colData7);
		colData.put("订单金额（元）", colData8);
		colData.put("应付订单金额（元）", colData9);
		colData.put("里程（公里）", colData10);
		colData.put("计费时长（分钟）", colData11);
        colData.put("空驶费（元）", colData29);
        colData.put("夜间费（元）", colData28);
		colData.put("下单人信息", colData12);
		colData.put("乘车人信息", colData13);
		colData.put("司机信息", colData14);
		colData.put("资格证号", colData15);
		colData.put("车牌号", colData16);
		colData.put("服务车企", colData17);
		colData.put("上车地址", colData18);
		colData.put("下车地址", colData19);
		colData.put("预估行驶时长（分钟）", colData20);
		colData.put("预估行驶里程（公里）", colData21);
		colData.put("下单时间", colData22);
		colData.put("用车时间", colData23);
		colData.put("接单时间", colData24);
		colData.put("开始时间", colData25);
		colData.put("结束时间", colData26);
		colData.put("交易流水号", colData27);

		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(tempFile);
	}
	
	@RequestMapping("/OrderManage/GetTaxiOrderExport")
	@SuppressWarnings("unchecked")
	public void getTaxiOrderExport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
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
		List<Object> colData15 = new ArrayList<Object>();
		List<Object> colData16 = new ArrayList<Object>();
		List<Object> colData17 = new ArrayList<Object>();
		List<Object> colData18 = new ArrayList<Object>();
		List<Object> colData19 = new ArrayList<Object>();
		List<Object> colData20 = new ArrayList<Object>();
		List<Object> colData21 = new ArrayList<Object>();
		List<Object> colData22 = new ArrayList<Object>();
		List<Object> colData23 = new ArrayList<Object>();
		List<Object> colData24 = new ArrayList<Object>();
		List<Object> colData25 = new ArrayList<Object>();
		List<Object> colData26 = new ArrayList<Object>();
		List<Object> colData27 = new ArrayList<Object>();
        List<Object> colData28 = new ArrayList<Object>();
		
		TocOrderManageQueryParam queryParam = new TocOrderManageQueryParam();
		queryParam.setCompanyId(getLoginLeUser(request).getLeasescompanyid());
		queryParam.setOrderNo(request.getParameter("orderNo"));
		queryParam.setOrderStatus(request.getParameter("orderStatus"));
		queryParam.setOrderPerson(request.getParameter("orderPerson"));
		queryParam.setDriver(request.getParameter("driver"));
		queryParam.setPayType(request.getParameter("payType"));
		queryParam.setCancelParty(request.getParameter("cancelParty"));
		queryParam.setOrderSource(request.getParameter("orderSource"));
		queryParam.setTradeNo(request.getParameter("tradeNo"));
		queryParam.setMinUseTime(request.getParameter("minUseTime"));
		queryParam.setMaxUseTime(request.getParameter("maxUseTime"));
		queryParam.setBelongleasecompany(request.getParameter("belongleasecompany"));
		
		List<Map> netAboutCarOrder = templateHelper.dealRequestWithToken("/OrderManage/GetTaxiOrderExport",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < netAboutCarOrder.size(); i++) {
            
			String judgeorderno = netAboutCarOrder.get(i).get("orderno").toString().substring(0, 2);
			if ("CG".equals(judgeorderno)) {
				colData1.add("乘客端 | 个人");
			} else if ("CY".equals(judgeorderno)) {
				colData1.add("运管端");
			} else {
				colData1.add("/");
			}
			
			colData2.add((String) netAboutCarOrder.get(i).get("orderno"));
			
			if (OrderState.WAITTAKE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString()) || OrderState.MANTICSEND.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData3.add(OrderState.WAITTAKE.msg);
			} else if (OrderState.WAITSTART.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData3.add(OrderState.WAITSTART.msg);
			} else if (OrderState.START.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData3.add(OrderState.START.msg);
			} else if (OrderState.ARRIVAL.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData3.add(OrderState.ARRIVAL.msg);
			} else if (OrderState.INSERVICE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData3.add(OrderState.INSERVICE.msg);
			} else if (OrderState.SERVICEDONE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				if (PayState.NOTPAY.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					colData3.add(PayState.NOTPAY.msg);
				} else if (PayState.PAYED.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					colData3.add(PayState.PAYED.msg);
				} else if (PayState.STATEMENTED.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					colData3.add(PayState.STATEMENTED.msg);
				} else if (PayState.MENTED.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					colData3.add(PayState.MENTED.msg);
				} else if (PayState.ALLNOPAY.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					colData3.add(PayState.ALLNOPAY.msg);
				} else if (PayState.PASSENGERNOPAY.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					colData3.add(PayState.PASSENGERNOPAY.msg);
				} else if (PayState.DRIVERNOPAY.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					colData3.add(PayState.DRIVERNOPAY.msg);
				} else if (PayState.PAYOVER.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					colData3.add(PayState.PAYOVER.msg);
				}
			} else if (OrderState.CANCEL.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData3.add(OrderState.CANCEL.msg);
			} else if (OrderState.WAITMONEY.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData3.add(OrderState.WAITMONEY.msg);
			} else {
				colData3.add("");
			}
			
			if ("0".equals(netAboutCarOrder.get(i).get("paymentmethod").toString())) {
				colData4.add("在线支付");
			} else if ("1".equals(netAboutCarOrder.get(i).get("paymentmethod").toString())) {
				colData4.add("线下付现");
			} else {
				colData4.add("");
			}
			
			if (OrderState.SERVICEDONE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				if (PayState.PAYED.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString()) || PayState.PAYOVER.state.equals(netAboutCarOrder.get(i).get("paymentstatus").toString())) {
					if (netAboutCarOrder.get(i).get("paytype") != null) {
						if ("1".equals(netAboutCarOrder.get(i).get("paytype").toString())) {
							colData5.add("余额支付");
						} else if ("2".equals(netAboutCarOrder.get(i).get("paytype").toString())) {
							colData5.add("微信支付");
						} else if ("3".equals(netAboutCarOrder.get(i).get("paytype").toString())) {
							colData5.add("支付宝支付");
						} else {
							colData5.add("/");
						}
					} else {
						colData5.add("");
					}
				} else {
					colData5.add("/");
				}
			} else {
				colData5.add("/");
			}
			
			if (OrderState.SERVICEDONE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				if (netAboutCarOrder.get(i).get("shouldpayamount") != null) {
					String shouldpayamount = String.valueOf(netAboutCarOrder.get(i).get("shouldpayamount"));
					String schedulefee = String.valueOf(netAboutCarOrder.get(i).get("schedulefee"));
					colData6.add(shouldpayamount);
					double shouldpay = StringUtil.formatNum(Double.valueOf(shouldpayamount) + Double.valueOf(schedulefee), 1);
					colData8.add(String.valueOf(shouldpay));
					colData9.add(String.valueOf(shouldpay));
				} else {
					colData6.add("");
					colData8.add("");
					colData9.add("");
				}
			} else if (OrderState.CANCEL.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData6.add("0.0");
				colData8.add("0.0");
				colData9.add("0.0");
			} else {
				colData6.add("/");
				colData8.add("/");
				colData9.add("/");
			}
			
			colData7.add(String.valueOf(netAboutCarOrder.get(i).get("schedulefee")));
			
			/*if (netAboutCarOrder.get(i).get("orderamount") != null) {
				colData8.add(String.valueOf(netAboutCarOrder.get(i).get("orderamount")));
			} else {
				colData8.add("");
			}
			
			if (netAboutCarOrder.get(i).get("shouldpayamount") != null) {
				colData9.add(String.valueOf(netAboutCarOrder.get(i).get("shouldpayamount")));
			} else if (netAboutCarOrder.get(i).get("orderamount") != null) {
				colData9.add(String.valueOf(netAboutCarOrder.get(i).get("orderamount")));
			} else {
				colData9.add("");
			}*/
			
			colData10.add((String) netAboutCarOrder.get(i).get("orderperson"));
			colData11.add((String) netAboutCarOrder.get(i).get("passengers") + " " + (String) netAboutCarOrder.get(i).get("passengerphone"));
			
			if (OrderState.WAITTAKE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString()) || OrderState.MANTICSEND.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				colData12.add("/");
				colData13.add("/");
				colData14.add("/");
				colData15.add("/");
			} else {
				if (netAboutCarOrder.get(i).get("driver") != null) {
					colData12.add((String) netAboutCarOrder.get(i).get("driver"));
				} else {
					colData12.add("");
				}
				if (netAboutCarOrder.get(i).get("jobnum") != null) {
					colData13.add((String) netAboutCarOrder.get(i).get("jobnum"));
				} else {
					colData13.add("");
				}
				if (netAboutCarOrder.get(i).get("plateno") != null) {
					colData14.add((String) netAboutCarOrder.get(i).get("plateno"));
				} else {
					colData14.add("");
				}
				if (netAboutCarOrder.get(i).get("leasescompanyname") != null) {
					colData15.add((String) netAboutCarOrder.get(i).get("leasescompanyname"));
				} else {
					colData15.add("");
				}
			}
			
			colData16.add("（" + (String) netAboutCarOrder.get(i).get("oncity") + "）" + (String) netAboutCarOrder.get(i).get("onaddress"));
			colData17.add("（" + (String) netAboutCarOrder.get(i).get("offcity") + "）" + (String) netAboutCarOrder.get(i).get("offaddress"));
			
			if (netAboutCarOrder.get(i).get("estimatedtime") != null) {
				colData18.add(netAboutCarOrder.get(i).get("estimatedtime"));
			} else {
				colData18.add("/");
			}
			if (netAboutCarOrder.get(i).get("estimatedmileage") != null) {
				colData19.add(netAboutCarOrder.get(i).get("estimatedmileage"));
			} else {
				colData19.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("undertime") != null) {
				colData20.add(netAboutCarOrder.get(i).get("undertime"));
			} else {
				colData20.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("usetime") != null) {
				colData21.add(netAboutCarOrder.get(i).get("usetime"));
			} else {
				colData21.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("ordertime") != null) {
				colData22.add(netAboutCarOrder.get(i).get("ordertime"));
			} else {
				colData22.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("starttimeexport") != null) {
				colData23.add(netAboutCarOrder.get(i).get("starttimeexport"));
			} else {
				colData23.add("/");
			}
			
			if (netAboutCarOrder.get(i).get("endtimeexport") != null) {
				colData24.add(netAboutCarOrder.get(i).get("endtimeexport"));
			} else {
				colData24.add("/");
			}
			
			if (OrderState.SERVICEDONE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString()) || OrderState.WAITMONEY.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())) {
				// 行驶时长（分钟）
				//Date starttime = StringUtil.parseDate(netAboutCarOrder.get(i).get("starttimeexport").toString(), "yyyy/MM/dd hh:mm");
				//Date endtime = StringUtil.parseDate(netAboutCarOrder.get(i).get("endtimeexport").toString(), "yyyy/MM/dd hh:mm");
				//colData25.add(StringUtil.getTimeMinute(starttime, endtime));
				if (netAboutCarOrder.get(i).get("starttime") == null || netAboutCarOrder.get(i).get("endtime") == null) {
					colData25.add("");
				} else {
					long starttime = (long) netAboutCarOrder.get(i).get("starttime");
					long endtime = (long) netAboutCarOrder.get(i).get("endtime");
					colData25.add((int) Math.ceil((endtime - starttime)/1000.0/60));
				}

				// 行驶里程（公里）
				if (netAboutCarOrder.get(i).get("mileage") != null) {
					Double mileage = (Double) netAboutCarOrder.get(i).get("mileage");
					Double mileages = mileage/1000;
					colData26.add(StringUtil.formatNum(mileages, 1));
				} else {
					colData26.add("");
				}
			} else {
				colData25.add("/");
				colData26.add("/");
			}

			if (netAboutCarOrder.get(i).get("tradeno") != null) {
				colData27.add(netAboutCarOrder.get(i).get("tradeno"));
			} else {
				colData27.add("/");
			}

            colData28.add(netAboutCarOrder.get(i).get("shortname"));
			
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("toC订单-出租车.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("订单来源");
		colName.add("订单号");
		colName.add("订单状态");
		colName.add("付款方式");
		colName.add("支付渠道");
		colName.add("行程费用（元）");
		colName.add("调度费用（元）");
		colName.add("服务费用（元）");
		colName.add("应付服务费用（元）");
		colName.add("下单人信息");
		colName.add("乘车人信息");
		colName.add("司机信息");
		colName.add("资格证号");
		colName.add("车牌号");
		colName.add("服务车企");
		colName.add("上车地址");
		colName.add("下车地址");
		colName.add("预估行驶时长（分钟）");
		colName.add("预估行驶里程（公里）");
		colName.add("下单时间");
		colName.add("用车时间");
		colName.add("接单时间");
		colName.add("开始时间");
		colName.add("结束时间");
		colName.add("行驶时长（分钟）");
		colName.add("行驶里程（公里）");
		colName.add("交易流水号");
        colName.add("服务车企");
		excel.setColName(colName);
		colData.put("订单来源", colData1);
		colData.put("订单号", colData2);
		colData.put("订单状态", colData3);
		colData.put("付款方式", colData4);
		colData.put("支付渠道", colData5);
		colData.put("行程费用（元）", colData6);
		colData.put("调度费用（元）", colData7);
		colData.put("服务费用（元）", colData8);
		colData.put("应付服务费用（元）", colData9);
		colData.put("下单人信息", colData10);
		colData.put("乘车人信息", colData11);
		colData.put("司机信息", colData12);
		colData.put("资格证号", colData13);
		colData.put("车牌号", colData14);
		colData.put("服务车企", colData15);
		colData.put("上车地址", colData16);
		colData.put("下车地址", colData17);
		colData.put("预估行驶时长（分钟）", colData18);
		colData.put("预估行驶里程（公里）", colData19);
		colData.put("下单时间", colData20);
		colData.put("用车时间", colData21);
		colData.put("接单时间", colData22);
		colData.put("开始时间", colData23);
		colData.put("结束时间", colData24);
		colData.put("行驶时长（分钟）", colData25);
		colData.put("行驶里程（公里）", colData26);
		colData.put("交易流水号", colData27);
        colData.put("服务车企", colData28);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(tempFile);
	}

	
	/**
	 * 网约车订单详情
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/NetAboutCarOrderDetailIndex")
	public ModelAndView orderDetailIndex(@RequestParam String orderno, @RequestParam(required = false) String tmp) {
        ModelAndView view = new ModelAndView();
        view.addObject("tmp", tmp);
        view.setViewName("resource/tocOrderManage/orderdetail");
        return view;
	}

	// 网约车详情
	/**
	 * 查询订单详情
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getOpOrderByOrderno(@RequestParam String orderno, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return tocOrderManageService.getOpOrderByOrderno(orderno, userToken);
	}
	
	/**
	 * 调用百度地图接口查询订单轨迹
	 * @param orderno
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpOrderTraceData")
	@ResponseBody
	public Map<String, Object> getOpOrderTraceData(@RequestParam String orderno, @RequestParam String ordertype, @RequestParam String usetype, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return tocOrderManageService.getOpOrderTraceData(orderno, ordertype, usetype, userToken);
	}
	
	/**
	 * 查询订单最原始订单记录
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetFirstOpOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getFirstOpOrderByOrderno(@RequestParam String orderno, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return tocOrderManageService.getFirstOpOrderByOrderno(orderno, userToken);
	}
	
	/**
	 * 查询订单的人工派单记录
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpSendOrderRecord")
	@ResponseBody
	public Map<String, Object> getOpSendOrderRecord(@RequestParam String orderno, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return tocOrderManageService.getOpSendOrderRecord(orderno, userToken);
	}
	
	/**
	 * 查询订单的更换司机记录
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpChangeDriverByQuery")
	@ResponseBody
	public PageBean getOpChangeDriverByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return tocOrderManageService.getOpChangeDriverByQuery(queryParam, userToken);
	}
	
	/**
	 * 查询订单复核记录
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpOrderReviewByQuery")
	@ResponseBody
	public PageBean getOpOrderReviewByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return tocOrderManageService.getOpOrderReviewByQuery(queryParam, userToken);
	}
	
	/**
	 * 分页查询订单客服备注
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/GetOpOrderCommentByQuery")
	@ResponseBody
	public PageBean getOpOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return tocOrderManageService.getOpOrderCommentByQuery(queryParam, userToken);
	}
	
	// 出租车详情
	/**
	 * 跳转到出租车订单详情页
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/TaxiOrderDetailIndex")
	public ModelAndView taxiOrderDetailIndex(@RequestParam String orderno, @RequestParam(required = false) String tmp) {
        ModelAndView view = new ModelAndView();
        view.addObject("tmp", tmp);
        view.setViewName("resource/tocOrderManage/taxiorderdetail");
		return view;
	}
	
	/**
	 * 根据订单号查询订单详情
	 * @param orderno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpTaxiOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getOpTaxiOrderByOrderno(@RequestParam String orderno, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return tocOrderManageService.getOpTaxiOrderByOrderno(orderno, userToken);
	}
	
	/**
	 * 调用百度地图接口查询订单轨迹
	 * @param orderno
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpTaxiOrderTraceData")
	@ResponseBody
	public Map<String, Object> getOpTaxiOrderTraceData(@RequestParam String orderno, @RequestParam String ordertype,
			@RequestParam String usetype, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		return tocOrderManageService.getOpTaxiOrderTraceData(orderno, ordertype, usetype, userToken);
	}
	
	/**
	 * 查询原始订单数据(第一条复核记录)
	 * @param orderno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetFirstTaxiOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getFirstTaxiOrderByOrderno(@RequestParam String orderno, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return tocOrderManageService.getFirstTaxiOrderByOrderno(orderno, userToken);
	}
	
	/**
	 * 查询人工派单记录
	 * @param orderno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpSendTaxiOrderRecord")
	@ResponseBody
	public Map<String, Object> getOpSendTaxiOrderRecord(@RequestParam String orderno, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return tocOrderManageService.getOpSendTaxiOrderRecord(orderno, userToken);
	}
	
	/**
	 * 查询更换司机记录
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpChangeDriverList")
	@ResponseBody
	public PageBean getOpChangeDriverList(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return tocOrderManageService.getOpChangeDriverList(queryParam, userToken);
	}
	
	/**
	 * 查询更换车辆记录
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpChangeVehicleList")
	@ResponseBody
	public PageBean getOpChangeVehicleList(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return tocOrderManageService.getOpChangeVehicleList(queryParam, userToken);
	}
	
	/**
	 * 分页查询订单复核记录
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpTaxiOrderReviewByQuery")
	@ResponseBody
	public PageBean getOpTaxiOrderReviewByQuery(@RequestBody OpTaxiOrderReview object, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return tocOrderManageService.getOpTaxiOrderReviewByQuery(object, userToken);
	}
	
	/**
	 * 查询客服备注列表
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpTaxiOrderCommentByQuery")
	@ResponseBody
	public PageBean getOpTaxiOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return tocOrderManageService.getOpTaxiOrderCommentByQuery(queryParam, userToken);
	}
	
}
