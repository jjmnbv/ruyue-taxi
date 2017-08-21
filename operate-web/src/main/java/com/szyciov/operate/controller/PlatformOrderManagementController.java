package com.szyciov.operate.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.OrderType;
import com.szyciov.driver.enums.PayState;
import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.TobOrderManageQueryParam;
import com.szyciov.op.param.PlatformOrderManagementParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Controller
public class PlatformOrderManagementController extends BaseController{
	 private static final Logger logger = Logger.getLogger(PlatformOrderManagementController.class);
		private TemplateHelper templateHelper = new TemplateHelper();
		@RequestMapping(value = "/PlatformOrderManagement/NetAboutCarIndex")
		public String getNetAboutCarIndex(HttpServletRequest request) {
			return "resource/platformOrderManagement/index";
		}
		/**
		 * 查询网约车数据
		 */
		@RequestMapping(value = "PlatformOrderManagement/GetNetAboutCarOrderByQuery")
		@ResponseBody
		public PageBean getOrderExportData(
				@RequestBody PlatformOrderManagementParam platformOrderManagementParam,
				HttpServletRequest request, HttpServletResponse response) throws IOException{ 
			response.setContentType("application/json;charset=utf-8");
			String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
			return templateHelper.dealRequestWithToken("/PlatformOrderManagement/GetNetAboutCarOrderByQuery",
					HttpMethod.POST, userToken, platformOrderManagementParam, PageBean.class);
		}
		//下单人信息
		@RequestMapping("/PlatformOrderManagement/GetNetAboutCarOrderUserByQuery")
		@ResponseBody
		@SuppressWarnings("unchecked")
		public List<Map<String, Object>> getNetAboutCarOrderUserByQuery(
				@RequestParam(value = "orderPerson", required = false) String orderPerson, HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			response.setContentType("text/html;charset=utf-8");
			String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
			
			return templateHelper.dealRequestWithToken(
					"/PlatformOrderManagement/GetNetAboutCarOrderUserByQuery?orderPerson={orderPerson}", HttpMethod.GET, userToken,
					null, List.class, orderPerson);
		}
		//司机信息
		@RequestMapping("/PlatformOrderManagement/GetNetAboutCarOrderDriverByQuery")
		@ResponseBody
		@SuppressWarnings("unchecked")
		public List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(
				@RequestParam(value = "driver", required = false) String driver,
				@RequestParam(value = "vehicleType", required = true) String vehicleType, HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			response.setContentType("text/html;charset=utf-8");
			String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
			return templateHelper.dealRequestWithToken(
					"/PlatformOrderManagement/GetNetAboutCarOrderDriverByQuery?driver={driver}&vehicleType={vehicleType}",
					HttpMethod.GET, userToken, null, List.class, driver, vehicleType);
		}
		//订单归属
		@RequestMapping("/PlatformOrderManagement/GetPartnerCompanySelect")
		@ResponseBody
		@SuppressWarnings("unchecked")
		public List<Map<String, Object>> getPartnerCompanySelect(@RequestParam Map<String,Object> params, HttpServletRequest request,HttpServletResponse response) throws IOException {
			response.setContentType("text/html;charset=utf-8");
			String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
			return templateHelper.dealRequestWithToken("/PlatformOrderManagement/GetPartnerCompanySelect",HttpMethod.POST, userToken, params, List.class);
		}
		//网约车导出
		@RequestMapping("/PlatformOrderManagement/GetNetAboutCarOrderExport")
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
//	        List<Object> colData28 = new ArrayList<Object>();
//	        List<Object> colData29 = new ArrayList<Object>();
	        
	        List<Object> colData30 = new ArrayList<Object>();
	        List<Object> colData31 = new ArrayList<Object>();
	        List<Object> colData32 = new ArrayList<Object>();
	        List<Object> colData33 = new ArrayList<Object>();
	        List<Object> colData34 = new ArrayList<Object>();
	        List<Object> colData35 = new ArrayList<Object>();
	        List<Object> colData36 = new ArrayList<Object>();


			TobOrderManageQueryParam queryParam = new TobOrderManageQueryParam();
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
			queryParam.setCompanyId(request.getParameter("leasescompanyid"));
			
			List<Map> netAboutCarOrder = templateHelper.dealRequestWithToken("/PlatformOrderManagement/GetNetAboutCarOrderExport",
					HttpMethod.POST, userToken, queryParam, List.class);
			for (int i = 0; i < netAboutCarOrder.size(); i++) {
				
				String ordersource = (String) netAboutCarOrder.get(i).get("ordersource");
				String usetype = (String) netAboutCarOrder.get(i).get("usetype");
	            if("00".equals(ordersource)||"01".equals(ordersource)) {
	            	if("0".equals(usetype)){
	            		colData1.add("乘客端 | 因公");
	            	}else{
	            		colData1.add("乘客端 | 因私");
	            	}
	            } else if("10".equals(ordersource)) {
	            	if("0".equals(usetype)){
	            		colData1.add("租赁端 | 因公");
	            	}else{
	            		colData1.add("租赁端 | 因私");
	            	}
	            }else if("20".equals(ordersource)){
	            	colData1.add("机构端");
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
					if (netAboutCarOrder.get(i).get("actualpayamount") != null) {
						colData36.add(String.valueOf(netAboutCarOrder.get(i).get("actualpayamount")));
					} else {
						colData36.add("");
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

				/*if(OrderState.SERVICEDONE.state.equals(netAboutCarOrder.get(i).get("orderstatus").toString())){
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
	            }*/
			colData30.add((String) netAboutCarOrder.get(i).get("cancelamount"));
			colData31.add((String) netAboutCarOrder.get(i).get("actualamount"));
			//费用类型
			if( netAboutCarOrder.get(i).get("expensetype") !=null){
				Object expensetype = netAboutCarOrder.get(i).get("expensetype");
				/*if(expensetype == 0){
					colData32.add("行程费用");
				}else{
					colData32.add("取消处罚");
				}*/
			}else{
				colData32.add("/");
			}
			colData33.add((String) netAboutCarOrder.get(i).get("ordernature"));
			if(netAboutCarOrder.get(i).get("cancelparty") != null){
			/*	int cancelparty = (int) netAboutCarOrder.get(i).get("cancelparty");
				if(cancelparty == 0){
					colData34.add("租赁端");
				}else if(cancelparty == 1){
					colData34.add("乘客端");
				}else{
					colData34.add("司机端");
				}*/
			}else{
				colData33.add("/");
			}
			colData35.add((String) netAboutCarOrder.get(i).get("leasescompanyname"));
			}
			
			Excel excel = new Excel();
			// excel文件
			File tempFile = new File("平台监管订单管理-网约车.xls");
			
			List<String> colName = new ArrayList<String>();
			colName.add("订单来源");
			colName.add("订单号");
			colName.add("订单类型");
			colName.add("服务车型");
			colName.add("订单状态");
			colName.add("支付状态");
			colName.add("支付渠道");
			colName.add("费用类型");
			
			colName.add("订单金额（元）");
			colName.add("应付金额（元）");
			colName.add("实付金额（元）");
			colName.add("优惠金额（元）");
			
			colName.add("里程（公里）");
			colName.add("计费时长（分钟）");
			colName.add("取消费用（分钟）");
			
//	        colName.add("空驶费（元）");
//	        colName.add("夜间费（元）");
	        colName.add("下单人信息");
			colName.add("乘车人信息");
			colName.add("司机信息");
			colName.add("资格证号");
			colName.add("车牌号");
//			colName.add("服务车企");
			colName.add("上车地址");
			colName.add("下车地址");
			colName.add("预估行驶时长（分钟）");
			colName.add("预估行驶里程（公里）");
			colName.add("下单时间");
			colName.add("用车时间");
			colName.add("接单时间");
			colName.add("开始时间");
			colName.add("结束时间");
			colName.add("服务车企");
			colName.add("订单归属方");
			colName.add("订单性质");
			
			colName.add("交易流水号");
			colName.add("取消方");
			
//	        colName.add("合作方");
			excel.setColName(colName);
			colData.put("订单来源", colData1);
			colData.put("订单号", colData2);
			colData.put("订单类型", colData3);
			colData.put("服务车型", colData4);
			colData.put("订单状态", colData5);
			colData.put("支付状态", colData6);
			colData.put("支付渠道", colData7);
			colData.put("费用类型", colData32);
			
			colData.put("订单金额（元）", colData8);
			colData.put("应付金额（元）", colData9);
			colData.put("实付金额（元）",colData36 );
			colData.put("优惠金额（元）", colData31);
			
			colData.put("里程（公里）", colData10);
			colData.put("计费时长（分钟）", colData11);
			colData.put("取消费用（元）", colData30);
			
//	        colData.put("空驶费（元）", colData29);
//	        colData.put("夜间费（元）", colData28);
			colData.put("下单人信息", colData12);
			colData.put("乘车人信息", colData13);
			colData.put("司机信息", colData14);
			colData.put("资格证号", colData15);
			colData.put("车牌号", colData16);
			colData.put("合作方", colData17);
			colData.put("上车地址", colData18);
			colData.put("下车地址", colData19);
			colData.put("预估行驶时长（分钟）", colData20);
			colData.put("预估行驶里程（公里）", colData21);
			colData.put("下单时间", colData22);
			colData.put("用车时间", colData23);
			colData.put("接单时间", colData24);
			colData.put("开始时间", colData25);
			colData.put("结束时间", colData26);
			
			colData.put("订单归属方", colData35);
			colData.put("订单性质",colData33);
			
			colData.put("交易流水号", colData27);
			
			colData.put("取消方", colData34);

			excel.setColData(colData);
			
			ExcelExport ee = new ExcelExport(request,response,excel);
			ee.createExcel(tempFile);
		}
		

}
