package com.szyciov.operate.service;

import com.szyciov.entity.Excel;
import com.szyciov.entity.Retcode;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpOrdercomment;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("OrderManageService")
public class OrderManageService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getSendRulesByName(String cityName, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetSendRulesByName?cityName={cityName}", HttpMethod.GET, userToken, null, List.class, cityName);
	}
	
	public PageBean getOpOrderByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpOrderByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPeUser(String userName, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetPeUser?userName={userName}", HttpMethod.GET, userToken, null, List.class, userName);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> cancelOpOrder(OrderApiParam param, String userToken) {
		Map<String, Object> result = templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl") + "/OrderApi/ChangeOrderState", HttpMethod.POST, userToken,
				param, Map.class);
		if (result.get("status").equals(Retcode.OK.code)) {
			result.put("status", "success");
			result.put("message", "取消成功");
		} else {
			result.put("status", "fail");
			result.put("message", result.get("message"));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCompanyVehicleModel(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetCompanyVehicleModel/{orderno}", HttpMethod.GET, userToken, null, List.class, orderno);
	}
	
	public PageBean getDriverByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetDriverByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> manualSendOrder(OpOrder object, String userToken) {
		return templateHelper.dealRequestWithTokenCarserviceApiUrl("/OrderManage/ManualSendOpOrder", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> changeOpDriver(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/ChangeOpDriver", HttpMethod.POST, userToken, params, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> applyRecheckOrder(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/ApplyRecheckOrder", HttpMethod.POST, userToken, params, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpOrderTraceData(String orderno, String ordertype, String usetype, String userToken) {
		return templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl")
						+ "/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}",
				HttpMethod.GET, userToken, null, Map.class, orderno, ordertype, usetype);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpSendOrderRecord(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpSendOrderRecord/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	public PageBean getOpChangeDriverByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpChangeDriverByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOpOrderReviewByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpOrderReviewByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> opOrderReject(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/OpOrderReject", HttpMethod.POST, userToken, params, Map.class);
	}
	
	public OpAccountrules getOrderAccountRulesByOrderno(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOrderAccountRulesByOrderno", HttpMethod.POST, userToken, params, OpAccountrules.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> opOrderReview(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/OpOrderReview", HttpMethod.POST, userToken, params, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getFirstOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetFirstOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getOrdernoBySelect(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOrdernoBySelect", HttpMethod.POST, userToken, queryParam, List.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> addOpOrderRemark(OpOrdercomment object, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/AddOpOrderRemark", HttpMethod.POST, userToken, 
				object, Map.class);
	}
	
	public PageBean getOpOrderCommentByQuery(OrdercommentQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpOrderCommentByQuery", HttpMethod.POST, userToken, queryParam,PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getToCCompanySelect(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetToCCompanySelect", HttpMethod.POST, userToken, params, List.class);
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
		List<Map<String, Object>> orderList = templateHelper.dealRequestWithToken("/OrderManage/ExportOrder",
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
                    } else if("ordertype".equals(key)) { //订单类型
                        if(StringUtils.isBlank(data)) {
                            dataList.add("/");
                            continue;
                        }
                        switch (Integer.valueOf(data)) {
                            case 1: dataList.add("约车"); break;
                            case 2: dataList.add("接机"); break;
                            case 3: dataList.add("送机"); break;
                            default: dataList.add("/");
                        }
                    } else if("orderstatus".equals(key)) { //订单状态
                        if("8".equals(data)) {
                            dataList.add("已取消");
                        } else if("7".equals(data)) {
                            String paymentstatus = dataMap.get("paymentstatus").toString();
                            switch (Integer.valueOf(paymentstatus)) {
                                case 0: dataList.add("未支付"); break;
                                case 1: dataList.add("已支付"); break;
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
                    } else if("reviewmileage".equals(key)) { //里程
                        double mileage = 0;
                        if(null != dataMap.get("reviewid") && StringUtils.isNotBlank(dataMap.get("reviewid").toString())) {
                            mileage = Double.valueOf(data)/1000;
                        } else {
                            if(StringUtils.isBlank(data)) {
                                dataList.add("/");
                                continue;
                            }
                            mileage = Double.valueOf(dataMap.get("mileage").toString())/1000;
                        }
                        dataList.add(StringUtil.formatNum(mileage, 1));
                    } else if("pricecopy".equals(key)) { //计费时长
                        String orderstatus = dataMap.get("orderstatus").toString();
                        if(StringUtils.isBlank(data) || !"7".equals(orderstatus)) {
                            dataList.add("/");
                        } else {
                            JSONObject json = JSONObject.fromObject(data);
                            int timetype = json.getInt("timetype");
                            Object reviewid = dataMap.get("reviewid");
                            if(timetype == 0) { //总用时
                                if(null != reviewid && StringUtils.isNotBlank(reviewid.toString())) {
                                    dataList.add(Math.ceil(Double.valueOf(dataMap.get("reviewtimes").toString())/60));
                                } else {
                                    if(null == dataMap.get("endtime")) {
                                        dataList.add("/");
                                        continue;
                                    }
                                    Date starttime = StringUtil.parseDate(dataMap.get("starttime").toString(), "yyyy/MM/dd hh:mm:ss");
                                    Date endtime = StringUtil.parseDate(dataMap.get("endtime").toString(), "yyyy/MM/dd hh:mm:ss");
                                    dataList.add(StringUtil.getTimeMinute(starttime, endtime));
                                }
                            } else if(timetype == 1) { //低速用时
                                if(null != reviewid && StringUtils.isNotBlank(reviewid.toString())) {
                                    dataList.add(dataMap.get("reviewcounttimes"));
                                } else {
                                    dataList.add(json.get("slowtimes"));
                                }
                            } else {
                                dataList.add("/");
                            }
                        }
                    } else if("rawpricecopy".equals(key)) { //原计费时长
                        JSONObject json = JSONObject.fromObject(dataMap.get("pricecopy"));
                        int timetype = json.getInt("timetype");
                        if(timetype == 0) { //总用时
                            Date starttime = new Date((Long) dataMap.get("rawstarttime"));
                            Date endtime = new Date((Long) dataMap.get("rawendtime"));
                            dataList.add(StringUtil.getTimeMinute(starttime, endtime));
                        } else if(timetype == 1) { //低速用时
                            dataList.add(dataMap.get("rawtimes"));
                        } else {
                            dataList.add("/");
                        }
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
                    } else if("rawedmileage".equals(key)) { //复核后里程
                        if(StringUtils.isBlank(data)) {
                            dataList.add("/");
                            continue;
                        }
                        double rawedmileage = Double.valueOf(data)/1000;
                        dataList.add(StringUtil.formatNum(rawedmileage, 1));
                    } else if("pricetimed".equals(key)) { //复核后计费时长
                        String pricecopy = dataMap.get("pricecopy").toString();
                        if(StringUtils.isBlank(pricecopy)) {
                            dataList.add("/");
                        } else {
                            JSONObject json = JSONObject.fromObject(pricecopy);
                            int timetype = json.getInt("timetype");
                            if(timetype == 0) { //总用时
                                String rawedtimes = dataMap.get("rawedtimes").toString();
                                dataList.add(Math.ceil(Double.valueOf(rawedtimes)/60));
                            } else if(timetype == 1) { //低速用时
                                dataList.add(dataMap.get("counttimes"));
                            } else {
                                dataList.add("/");
                            }
                        }
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
                                default: dataList.add("/");
                            }
                        } else {
                            dataList.add("/");
                        }
                    } else if("paymethod".equals(key)) { //支付方式
                        dataList.add("个人支付");
                    } else if("shouldpayamount".equals(key)) { //应付订单金额
                        if(StringUtils.isBlank(data)) {
                            dataList.add(dataMap.get("orderamount"));
                        } else {
                            dataList.add(data);
                        }
                    } else if("shouldorderamount".equals(key)) { //订单金额
                        Object shouldpayamount = dataMap.get("shouldpayamount");
                        if(null == shouldpayamount) {
                            dataList.add(dataMap.get("orderamount"));
                        } else {
                            dataList.add(shouldpayamount);
                        }
                    } else if("rawmileage".equals(key)) { //原里程
                        if(StringUtils.isBlank(data)) {
                            dataList.add("/");
                            continue;
                        }
                        Double rawmileage = Double.valueOf(data)/1000;
                        dataList.add(StringUtil.formatNum(rawmileage, 1));
                    } else if("ordertime".equals(key) || "starttime".equals(key) || "endtime".equals(key) || "tradeno".equals(key)) { //接单时间、开始时间、结束时间、交易流水号
                        if(StringUtils.isBlank(data)) {
                            dataList.add("/");
                        } else {
                            dataList.add(data);
                        }
                    } else if("nightcost".equals(key)) {
                        String orderstatus = dataMap.get("orderstatus").toString();
                        if(!"7".equals(orderstatus)) {
                            dataList.add("/");
                        }else{
                            String pricecopy = dataMap.get("pricecopy").toString();
                            if(StringUtils.isBlank(pricecopy)) {
                                dataList.add("0");
                            } else {
                                JSONObject json = JSONObject.fromObject(pricecopy);
                                if(json.containsKey("nightcost")){
                                    dataList.add(json.get("nightcost"));
                                }else{
                                    dataList.add("0");
                                }
                            }
                        }
                    } else if("deadheadcost".equals(key)) {
                        String orderstatus = dataMap.get("orderstatus").toString();
                        if(!"7".equals(orderstatus)) {
                            dataList.add("/");
                        }else{
                            String pricecopy = dataMap.get("pricecopy").toString();
                            if(StringUtils.isBlank(pricecopy)) {
                                dataList.add("0");
                            } else {
                                JSONObject json = JSONObject.fromObject(pricecopy);
                                if(json.containsKey("deadheadcost")){
                                    dataList.add(json.get("deadheadcost"));
                                }else{
                                    dataList.add("0");
                                }
                            }
                        }
                    } else if("rawnightcost".equals(key)) {
                        if(dataMap.get("rawpricecopy") == null){
                            dataList.add("0");
                        }else{
                            String pricecopy = dataMap.get("rawpricecopy").toString();
                            if(StringUtils.isBlank(pricecopy)) {
                                dataList.add("0");
                            } else {
                                JSONObject json = JSONObject.fromObject(pricecopy);
                                if(json.containsKey("nightcost")){
                                    dataList.add(json.get("nightcost"));
                                }else{
                                    dataList.add("0");
                                }
                            }
                        }
                    } else if("rawdeadheadcost".equals(key)) {
                        if(dataMap.get("rawpricecopy") == null){
                            dataList.add("0");
                        }else{
                            String pricecopy = dataMap.get("rawpricecopy").toString();
                            if(StringUtils.isBlank(pricecopy)) {
                                dataList.add("0");
                            } else {
                                JSONObject json = JSONObject.fromObject(pricecopy);
                                if(json.containsKey("deadheadcost")){
                                    dataList.add(json.get("deadheadcost"));
                                }else{
                                    dataList.add("0");
                                }
                            }
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
		
		title.put("ordertype", "订单类型");
		titleList.add("订单类型");
		
		title.put("orderstatus", "订单状态");
		titleList.add("订单状态");
		
		title.put("reviewperson", "复核方");
		titleList.add("复核方");
		
		title.put("shouldorderamount", "订单金额（元）");
		titleList.add("订单金额（元）");
		
		title.put("reviewmileage", "里程（公里）");
		titleList.add("里程（公里）");
		
		title.put("pricecopy", "计费时长（分钟）");
		titleList.add("计费时长（分钟）");

        title.put("deadheadcost", "空驶费（元）");
        titleList.add("空驶费（元）");

        title.put("nightcost", "夜间费（元）");
        titleList.add("夜间费（元）");
		
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
		
		title.put("ordertype", "订单类型");
		titleList.add("订单类型");
		
		title.put("orderstatus", "订单状态");
		titleList.add("订单状态");
		
		title.put("reviewperson", "复核方");
		titleList.add("复核方");
		
		title.put("differenceprice", "差异金额（元）");
		titleList.add("差异金额（元）");
		
		title.put("originalorderamount", "原订单金额（元）");
		titleList.add("原订单金额（元）");
		
		title.put("rawmileage", "原里程（公里）");
		titleList.add("原里程（公里）");
		
		title.put("rawpricecopy", "原计费时长（分钟）");
		titleList.add("原计费时长（分钟）");

        title.put("rawdeadheadcost", "原空驶费（元）");
        titleList.add("原空驶费（元）");

        title.put("rawnightcost", "原夜间费（元）");
        titleList.add("原夜间费（元）");

        title.put("reviewedprice", "复核后订单金额（元）");
		titleList.add("复核后订单金额（元）");
		
		title.put("reviewmileage", "复核后里程（公里）");
		titleList.add("复核后里程（公里）");
		
		title.put("pricecopy", "复核后计费时长（分钟）");
		titleList.add("复核后计费时长（分钟）");

        title.put("deadheadcost", "复核后空驶费（元）");
        titleList.add("复核后空驶费（元）");

        title.put("nightcost", "复核后夜间费（元）");
        titleList.add("复核后夜间费（元）");

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
		
		title.put("ordertype", "订单类型");
		titleList.add("订单类型");
		
		title.put("factmodelname", "服务车型");
		titleList.add("服务车型");
		
		title.put("orderstatus", "订单状态");
		titleList.add("订单状态");
		
		title.put("paymentstatus", "支付状态");
		titleList.add("支付状态");
		
		title.put("paymethod", "支付方式");
		titleList.add("支付方式");
		
		title.put("paytype", "支付渠道");
		titleList.add("支付渠道");
		
		title.put("shouldorderamount", "订单金额（元）");
		titleList.add("订单金额（元）");
		
		title.put("shouldpayamount", "应付订单金额（元）");
		titleList.add("应付订单金额（元）");
		
		title.put("reviewmileage", "里程（公里）");
		titleList.add("里程（公里）");
		
		title.put("pricecopy", "计费时长（分钟）");
		titleList.add("计费时长（分钟）");

        title.put("deadheadcost", "空驶费（元）");
        titleList.add("空驶费（元）");

        title.put("nightcost", "夜间费（元）");
        titleList.add("夜间费（元）");
		
		title.put("userid", "下单人信息");
		titleList.add("下单人信息");
		
		title.put("passengers", "乘车人信息");
		titleList.add("乘车人信息");
		
		title.put("drivername", "司机信息");
		titleList.add("司机信息");
		
		title.put("jobnum", "资格证号");
		titleList.add("资格证号");
		
		title.put("shortname", "服务车企");
		titleList.add("服务车企");
		
		title.put("plateno", "车牌号");
		titleList.add("车牌号");
		
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
		
		title.put("tradeno", "交易流水号");
		titleList.add("交易流水号");

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
		
		title.put("ordertype", "订单类型");
		titleList.add("订单类型");
		
		title.put("orderstatus", "订单状态");
		titleList.add("订单状态");
		
		title.put("paymethod", "支付方式");
		titleList.add("支付方式");
		
		title.put("shouldorderamount", "订单金额（元）");
		titleList.add("订单金额（元）");
		
		title.put("reviewmileage", "里程（公里）");
		titleList.add("里程（公里）");
		
		title.put("pricecopy", "计费时长（分钟）");
		titleList.add("计费时长（分钟）");

        title.put("deadheadcost", "空驶费（元）");
        titleList.add("空驶费（元）");

        title.put("nightcost", "夜间费（元）");
        titleList.add("夜间费（元）");

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
		sb.append("网约车订单-");
		
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
