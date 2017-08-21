package com.szyciov.operate.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.szyciov.entity.Excel;
import com.szyciov.entity.PubOrderCancel;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.op.entity.OpTaxiordercomment;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service("TaxiOrderManageService")
public class TaxiOrderManageService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public PageBean getOpTaxiOrderByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetOpTaxiOrderByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpTaxiOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetOpTaxiOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> cancelOpTaxiOrder(OrderApiParam param, String userToken) {
		Map<String, Object> result = templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl") + "/OrderApi/ChangeOrderState", HttpMethod.POST, userToken,
				param, Map.class);
		if(result.get("status").equals(0)) {
			result.put("status", "success");
			result.put("message", "取消成功");
		} else {
			result.put("status", "fail");
			result.put("message", "取消失败");
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> opTaxiOrderReject(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/OpTaxiOrderReject/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> applyRecheckTaxiOrder(OpTaxiOrder object, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/ApplyRecheckTaxiOrder", HttpMethod.POST, userToken, object, Map.class);
	}
	
	public PageBean getOpTaxiOrderReviewByQuery(OpTaxiOrderReview object, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetOpTaxiOrderReviewByQuery", HttpMethod.POST, userToken, object, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> opTaxiOrderReview(OpTaxiOrderReview object, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/OpTaxiOrderReview", HttpMethod.POST, userToken, object, Map.class);
	}
	
	public PageBean getDriverByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetDriverByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getTaxiPlatonoBySelect(Map<String, String> params, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetTaxiPlatonoBySelect", HttpMethod.POST, userToken, params, List.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> manualSendOrder(OpTaxiOrder object, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/ManualSendOrder", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> sendFail(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/SendFail/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> changeVehicle(OpTaxiOrder object, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/ChangeVehicle", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getFirstTaxiOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetFirstTaxiOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpSendTaxiOrderRecord(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetOpSendTaxiOrderRecord/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	public PageBean getOpChangeDriverList(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetOpChangeDriverList", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOpChangeVehicleList(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetOpChangeVehicleList", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOpTaxiOrderCommentByQuery(OrdercommentQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetOpTaxiOrderCommentByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> addOpTaxiordercomment(OpTaxiordercomment object, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/AddOpTaxiordercomment", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpTaxiOrderTraceData(String orderno, String ordertype, String usetype, String userToken) {
		return templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl")
						+ "/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}",
				HttpMethod.GET, userToken, null, Map.class, orderno, ordertype, usetype);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getBelongCompanySelect(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetBelongCompanySelect", HttpMethod.POST, userToken, queryParam, List.class);
	}

    @SuppressWarnings("unchecked")
    public Map<String, Object> getCancelPriceDetail(Map<String, String> param, String userToken) {
        return templateHelper.dealRequestWithToken("/TaxiOrderManage/GetCancelPriceDetail", HttpMethod.POST, userToken, param, Map.class);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> exemptionOrder(PubOrderCancel object, String userToken) {
        return templateHelper.dealRequestWithToken("/TaxiOrderManage/ExemptionOrder", HttpMethod.POST, userToken, object, Map.class);
    }
	
	/**
	 * 订单导出
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public void exportOrder(OrderManageQueryParam queryParam, String userToken, HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, Object>> orderList = templateHelper.dealRequestWithToken("/TaxiOrderManage/ExportOrder",
				HttpMethod.POST, userToken, queryParam, List.class);
		if (null == orderList || orderList.isEmpty()) {
			return;
		}
		// 表头
		List<String> titleList = new ArrayList<String>();
		Map<String, String> title = getExportTitle(queryParam.getType(), titleList);
		if (null == title || title.isEmpty()) {
			return;
		}
		
		//获取需要导出的数据
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		for (Map.Entry<String, String> entry : title.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			List<Object> dataList = new ArrayList<Object>();
			
			//抽取需要导出的数据
			for (Map<String, Object> dataMap : orderList) {
			    try{
                    Object obj = dataMap.get(key);
                    String data = null;
                    if(null != obj) {
                        data = obj.toString();
                    }
                    if("ordersource".equals(key)) { //订单来源
                        String orderno = dataMap.get("orderno").toString();
                        if(orderno.startsWith("CG")) {
                            dataList.add("乘客端 | 个人");
                        } else if(orderno.startsWith("CY")) {
                            dataList.add("运管端");
                        } else {
                            dataList.add("/");
                        }
                    } else if("orderstatus".equals(key)) { //订单状态
                        if("8".equals(data)) {
                            dataList.add("已取消");
                        } else if("7".equals(data)) {
                            String paymentstatus = dataMap.get("paymentstatus").toString();
                            switch (Integer.valueOf(paymentstatus)) {
                                case 0: dataList.add("未支付"); break;
                                case 1: dataList.add("已支付"); break;
                                case 3: dataList.add("已结算"); break;
                                case 4: dataList.add("未结算"); break;
                                case 5: dataList.add("未付结"); break;
                                case 6: dataList.add("未付结"); break;
                                case 7: dataList.add("未付结"); break;
                                case 8: dataList.add("已付结"); break;
                                default: dataList.add("/"); break;
                            }
                        } else {
                            dataList.add("/");
                        }
                    } else if("paytype".equals(key)) { //支付渠道
                        if(StringUtils.isBlank(data)) {
                            dataList.add("/");
                            continue;
                        }
                        switch (Integer.valueOf(data)) {
                            case 1: dataList.add("余额支付"); break;
                            case 2: dataList.add("微信支付"); break;
                            case 3: dataList.add("支付宝支付"); break;
                            default: dataList.add("/");
                        }
                    } else if("paymentmethod".equals(key)) { //付款方式
                        if(StringUtils.isBlank(data)) {
                            dataList.add("/");
                            continue;
                        }
                        switch (Integer.valueOf(data)) {
                            case 0: dataList.add("在线支付"); break;
                            case 1: dataList.add("线下付现"); break;
                            default: dataList.add("/");
                        }
                    } else if("reviewperson".equals(key)) { //复核方
                        if(StringUtils.isBlank(data)) {
                            dataList.add("/");
                            continue;
                        }
                        switch (Integer.valueOf(data)) {
                            case 1: dataList.add("司机"); break;
                            case 2: dataList.add("下单人"); break;
                            default: dataList.add("/");
                        }
                    } else if("mileage".equals(key)) { //里程
                        if(StringUtils.isBlank(data)) {
                            dataList.add("/");
                            continue;
                        }
                        double mileage = Double.valueOf(data)/1000;
                        dataList.add(StringUtil.formatNum(mileage, 1));
                    } else if("times".equals(key)) { //行驶时长
                        if(null == dataMap.get("endtime")) {
                            dataList.add("/");
                            continue;
                        }
                        Date starttime = StringUtil.parseDate(dataMap.get("starttime").toString(), "yyyy-MM-dd hh:mm");
                        Date endtime = StringUtil.parseDate(dataMap.get("endtime").toString(), "yyyy-MM-dd hh:mm");
                        dataList.add(StringUtil.getTimeMinute(starttime, endtime));
                    } else if("userid".equals(key)) { //下单人信息
                        Object nickname = dataMap.get("nickname");
                        Object account = dataMap.get("account");
                        if(null == nickname || StringUtils.isBlank(nickname.toString())) {
                            dataList.add(account);
                        } else {
                            dataList.add(nickname + " " + account);
                        }
                    } else if("passengers".equals(key)) { //乘车人信息
                        String passengerphone = dataMap.get("passengerphone").toString();
                        if(StringUtils.isBlank(data)) {
                            dataList.add(passengerphone);
                        } else {
                            dataList.add(data + " " + passengerphone);
                        }
                    } else if("onaddress".equals(key)) { //上车地址
                        dataList.add("(" + dataMap.get("oncity") + ")" + data);
                    } else if("offaddress".equals(key)) { //下车地址
                        dataList.add("(" + dataMap.get("offcity") + ")" + data);
                    } else if("differenceprice".equals(key)) { //差异金额
                        String shouldpayamount = dataMap.get("shouldpayamount").toString();
                        String actualpayamount = dataMap.get("actualpayamount").toString();
                        dataList.add(StringUtil.formatNum(Double.valueOf(shouldpayamount) - Double.valueOf(actualpayamount), 1));
                    } else if("paymentstatus".equals(key)) { //支付状态
                        String orderstatus = dataMap.get("orderstatus").toString();
                        if("7".equals(orderstatus)) {
                            if(StringUtils.isBlank(data)) {
                                dataList.add("/");
                                continue;
                            }
                            switch (Integer.valueOf(data)) {
                                case 0: dataList.add("未支付"); break;
                                case 1: dataList.add("已支付"); break;
                                case 3: dataList.add("已结算"); break;
                                case 4: dataList.add("未结算"); break;
                                case 5: dataList.add("未付结"); break;
                                case 6: dataList.add("未付结"); break;
                                case 7: dataList.add("未付结"); break;
                                case 8: dataList.add("已付结"); break;
                                default: dataList.add("/"); break;
                            }
                        } else {
                            dataList.add("/");
                        }
                    } else if("shouldpayamount".equals(key)) { //应付订单金额
                        if(StringUtils.isBlank(data) && (null == dataMap.get("orderamount") || "".equals(dataMap.get("orderamount")))) {
                            dataList.add("/");
                        } else if(StringUtils.isBlank(data) && null != dataMap.get("orderamount") && !"".equals(dataMap.get("orderamount"))) {
                            dataList.add(dataMap.get("orderamount"));
                        } else {
                            dataList.add(data);
                        }
                    } else if("serviceamount".equals(key)) { //应付服务费用
                        Object shouldpayamount = dataMap.get("shouldpayamount");
                        if(null == shouldpayamount) {
                            dataList.add("/");
                            continue;
                        }
                        String schedulefee = dataMap.get("schedulefee").toString();
                        dataList.add(StringUtil.formatNum(Double.valueOf(shouldpayamount.toString()) + Double.valueOf(schedulefee), 1));
                    } else if("serviceorderamount".equals(key)) { //服务费用
                        Object shouldpayamount = dataMap.get("shouldpayamount");
                        if(null == shouldpayamount) {
                            dataList.add("/");
                            continue;
                        }
                        String schedulefee = dataMap.get("schedulefee").toString();
                        dataList.add(StringUtil.formatNum(Double.valueOf(shouldpayamount.toString()) + Double.valueOf(schedulefee), 1));
                    } else if("ordertime".equals(key) || "starttime".equals(key) || "endtime".equals(key) || "tradeno".equals(key)) { //接单时间、开始时间、结束时间、交易流水号
                        if(StringUtils.isBlank(data)) {
                            dataList.add("/");
                        } else {
                            dataList.add(data);
                        }
                    } else {
                        if(StringUtils.isBlank(data)) {
                            dataList.add("/");
                        } else {
                            dataList.add(data);
                        }
                    }
                }catch (Exception e){
                }
			}
			colData.put(value, dataList);
		}
		
		Excel excel = new Excel();
		File tempFile = new File(getFilename(queryParam.getType(), queryParam.getUsetype()));
		excel.setColName(titleList);
		excel.setColData(colData);
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(tempFile);
	}
	
	/**
	 * 获取导出文件的表头
	 * @param type
	 * @param usetype
	 * @return
	 */
	private Map<String, String> getExportTitle(String type, List<String> titleList) {
		switch (Integer.valueOf(type)) {
			case 3:
				return getAbnormalOrderTitle(titleList);
			case 4:
				return getWasabnormalOrderTitle(titleList);
			case 5:
				return getCompleteOrderTitle(titleList);
			case 6:
				return getWaitgatheringOrderTitle(titleList);
			default:
				return null;
		}
	}
	
	/**
	 * 待复核订单表头
	 * @param isOrgOrder
	 * @return
	 */
	private Map<String, String> getAbnormalOrderTitle(List<String> titleList) {
		Map<String, String> title = new HashMap<String, String>();
		title.put("ordersource", "订单来源");
		titleList.add("订单来源");
		
		title.put("orderno", "订单号");
		titleList.add("订单号");
		
		title.put("orderstatus", "订单状态");
		titleList.add("订单状态");
		
		title.put("reviewperson", "复核方");
		titleList.add("复核方");
		
		title.put("shouldpayamount", "行程费用（元）");
		titleList.add("行程费用（元）");
		
		title.put("paymentmethod", "付款方式");
		titleList.add("付款方式");
		
		title.put("userid", "下单人信息");
		titleList.add("下单人信息");
		
		title.put("passengers", "乘车人信息");
		titleList.add("乘车人信息");
		
		title.put("drivername", "司机信息");
		titleList.add("司机信息");
		
		title.put("shortname", "服务车企");
		titleList.add("服务车企");
		
		title.put("usetime", "用车时间");
		titleList.add("用车时间");
		
		title.put("onaddress", "上车地址");
		titleList.add("上车地址");
		
		title.put("offaddress", "下车地址");
		titleList.add("下车地址");

        title.put("shortname", "服务车企");
        titleList.add("服务车企");

		return title;
	}
	
	/**
	 * 已复核订单表头
	 * @param isOrgOrder
	 * @return
	 */
	private Map<String, String> getWasabnormalOrderTitle(List<String> titleList) {
		Map<String, String> title = new HashMap<String, String>();
		title.put("ordersource", "订单来源");
		titleList.add("订单来源");
		
		title.put("orderno", "订单号");
		titleList.add("订单号");
		
		title.put("orderstatus", "订单状态");
		titleList.add("订单状态");
		
		title.put("reviewperson", "复核方");
		titleList.add("复核方");
		
		title.put("paymentmethod", "付款方式");
		titleList.add("付款方式");
		
		title.put("differenceprice", "差异金额（元）");
		titleList.add("差异金额（元）");
		
		title.put("originalorderamount", "原行程费用（元）");
		titleList.add("原行程费用（元）");
		
		title.put("shouldpayamount", "复核后行程费用（元）");
		titleList.add("复核后行程费用（元）");

        title.put("shortname", "服务车企");
        titleList.add("服务车企");

		return title;
	}
	
	/**
	 * 已完成订单表头
	 * @param isOrgOrder
	 * @return
	 */
	private Map<String, String> getCompleteOrderTitle(List<String> titleList) {
		Map<String, String> title = new HashMap<String, String>();
		title.put("ordersource", "订单来源");
		titleList.add("订单来源");
		
		title.put("orderno", "订单号");
		titleList.add("订单号");
		
		title.put("orderstatus", "订单状态");
		titleList.add("订单状态");
		
		title.put("paymentstatus", "支付状态");
		titleList.add("支付状态");
		
		title.put("paymentmethod", "付款方式");
		titleList.add("付款方式");
		
		title.put("paytype", "支付渠道");
		titleList.add("支付渠道");
		
		title.put("shouldpayamount", "行程费用（元）");
		titleList.add("行程费用（元）");
		
		title.put("schedulefee", "调度费用（元）");
		titleList.add("调度费用（元）");
		
		title.put("serviceorderamount", "服务费用（元）");
		titleList.add("服务费用（元）");
		
		title.put("serviceamount", "应付服务费用（元）");
		titleList.add("应付服务费用（元）");
		
		title.put("userid", "下单人信息");
		titleList.add("下单人信息");
		
		title.put("passengers", "乘车人信息");
		titleList.add("乘车人信息");
		
		title.put("drivername", "司机信息");
		titleList.add("司机信息");
		
		title.put("jobnum", "资格证号");
		titleList.add("资格证号");
		
		title.put("plateno", "车牌号");
		titleList.add("车牌号");
		
		title.put("shortname", "服务车企");
		titleList.add("服务车企");
		
		title.put("onaddress", "上车地址");
		titleList.add("上车地址");
		
		title.put("offaddress", "下车地址");
		titleList.add("下车地址");
		
		title.put("estimatedtime", "预估行驶时长（分钟）");
		titleList.add("预估行驶时长（分钟）");
		
		title.put("estimatedmileage", "预估行驶里程（公里）");
		titleList.add("预估行驶里程（公里）");
		
		title.put("undertime", "下单时间");
		titleList.add("下单时间");
		
		title.put("usetime", "用车时间");
		titleList.add("用车时间");
		
		title.put("ordertime", "接单时间");
		titleList.add("接单时间");
		
		title.put("starttime", "开始时间");
		titleList.add("开始时间");
		
		title.put("endtime", "结束时间");
		titleList.add("结束时间");
		
		title.put("times", "行驶时长（分钟）");
		titleList.add("行驶时长（分钟）");
		
		title.put("mileage", "行驶里程（公里）");
		titleList.add("行驶里程（公里）");
		
		title.put("tradeno", "交易流水号");
		titleList.add("交易流水号");

        title.put("shortname", "服务车企");
        titleList.add("服务车企");

		return title;
	}
	
	/**
	 * 待收款订单表头
	 * @param isOrgOrder
	 * @return
	 */
	private Map<String, String> getWaitgatheringOrderTitle(List<String> titleList) {
		Map<String, String> title = new HashMap<String, String>();
		title.put("ordersource", "订单来源");
		titleList.add("订单来源");
		
		title.put("orderno", "订单号");
		titleList.add("订单号");
		
		title.put("orderstatus", "订单状态");
		titleList.add("订单状态");
		
		title.put("paymentmethod", "付款方式");
		titleList.add("付款方式");
		
		title.put("paytype", "支付渠道");
		titleList.add("支付渠道");
		
		title.put("shouldpayamount", "行程费用（元）");
		titleList.add("行程费用（元）");
		
		title.put("schedulefee", "调度费用（元）");
		titleList.add("调度费用（元）");
		
		title.put("userid", "下单人信息");
		titleList.add("下单人信息");
		
		title.put("passengers", "乘车人信息");
		titleList.add("乘车人信息");
		
		title.put("drivername", "司机信息");
		titleList.add("司机信息");
		
		title.put("shortname", "服务车企");
		titleList.add("服务车企");
		
		title.put("usetime", "用车时间");
		titleList.add("用车时间");
		
		title.put("onaddress", "上车地址");
		titleList.add("上车地址");
		
		title.put("offaddress", "下车地址");
		titleList.add("下车地址");

        title.put("shortname", "服务车企");
        titleList.add("服务车企");

		return title;
	}
	
	/**
	 * 获取导出文件名称
	 * @param type
	 * @param usetype
	 * @return
	 */
	private String getFilename(String type, String usetype) {
		StringBuilder sb = new StringBuilder();
		sb.append("出租车订单-");
		
		switch (Integer.valueOf(type)) {
			case 3: sb.append("异常订单-待复核.xls"); break;
			case 4: sb.append("异常订单-已复核.xls"); break;
			case 5: sb.append("已完成订单.xls"); break;
			case 6: sb.append("待收款订单.xls"); break;
			default: return null;
		}
		return sb.toString();
	}

}
