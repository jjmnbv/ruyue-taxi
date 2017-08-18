var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();

	initForm();
	initSelect2();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OrderManage/GetTaxiOrderByQuery",
        iLeftColumn: 3,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无服务订单信息"
        },
        columns: [
	        {mDataProp: "ordersource", sTitle: "订单来源", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		var orderno = full.orderno.substring(0, 2);
	                if(orderno == "CG") {
	                	return "乘客端 | 个人";
	                } else if(orderno == "CY") {
	                	return "运管端";
	                } else {
	                	return "/";
	                }
	            }
	        },
	        {mDataProp: "orderno", sTitle: "订单号", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		var htmlArr = [];
	                htmlArr.push("<a class='orderhref' href=\"" + $("#baseUrl").val() + "OrderManage/TaxiOrderDetailIndex?orderno=");
	                htmlArr.push(full.orderno);
	                htmlArr.push("\">");
	                htmlArr.push(full.orderno);
	                htmlArr.push("</a>");
	                return htmlArr.join("");
	            }
	        },
	        
	        {mDataProp: "orderstatus", sTitle: "订单状态", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		switch(full.orderstatus) {
                   	case "0": return "待接单"; break;
                   	case "1": return "待接单"; break;
                   	case "2": return "待出发"; break;
                   	case "3": return "已出发"; break;
                   	case "4": return "已抵达"; break;
                   	case "6": return "<span class='font_green'>服务中</span>"; break;
                   	case "7": 
                   		      if (full.paymentstatus == '0') {
                   		    	  return "<span class='font_red'>未支付</span>";
                   		      } else if (full.paymentstatus == '1') {
                   		    	  return "已支付";
                   		      } else if (full.paymentstatus == '3') {
                   		    	return "已结算";
                   		      } else if (full.paymentstatus == '4') {
                   		    	return "<span class='font_red'>未结算</span>";
                   		      } else if (full.paymentstatus == '5' || full.paymentstatus == '6' || full.paymentstatus == '7') {
                   		    	return "<span class='font_red'>未付结</span>";
                   		      } else if (full.paymentstatus == '8') {
                   		    	return "已付结";
                   		      }
                   		      break;
                   	case "8": return "<span class='font_red'>已取消</span>"; break;
                   	case "9": return "<span class='font_red'>待确费</span>"; break;
                   	default: return "";
                   }
	            }
	        },
	        {mDataProp: "paymentmethod", sTitle: "付款方式", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		if (full.paymentmethod == '0') {
	        			return "在线支付";
	        		} else if (full.paymentmethod == '1') {
	        			return "线下付现";
	        		}
	            }
	        },
	        {mDataProp: "paytype", sTitle: "支付渠道", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		if (full.orderstatus == '7') {
	        			if (full.paymentstatus == '1' || full.paymentstatus == '8') {// 1-已支付   8-已付结
	        				if (full.paytype == '1') {
		        				return "余额支付";
		        			} else if (full.paytype == '2') {
		        				return "微信支付";
		        			} else if (full.paytype == '3') {
		        				return "支付宝支付";
		        			} else {
		        				return '/';
		        			}
	        			} else {
	        				return '/';
	        			}
	        		} else {
	        			return '/';
	        		}
	            }
	        },
	        {mDataProp: "orderamount", sTitle: "行程费用（元）", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		if (full.orderstatus == '7') {
	        			return full.shouldpayamount;
	        		} else if (full.orderstatus == '8') {
	        			return '0.0';
	        		} else {
	        			return '/';
	        		}
	            }
	        },
	        {mDataProp: "schedulefee", sTitle: "调度费用（元）", sClass: "center", sortable: true },
	        {mDataProp: "orderperson", sTitle: "下单人信息", sClass: "center", sortable: true },
	        {mDataProp: "passengers", sTitle: "乘车人信息", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		return full.passengers + ' ' + full.passengerphone;
	            }
	        },
	        {mDataProp: "driver", sTitle: "司机信息", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		if (full.orderstatus == '0' || full.orderstatus == '1') {
	        			return '/';
	        		} else {
	        			return full.driver;
	        		}
	            }
	        },
	        {mDataProp: "usetime", sTitle: "用车时间", sClass: "center", sortable: true },
	        {mDataProp: "SXCDZ", sTitle: "上下车地址", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
                	var onaddress = "(" + full.oncity + ")" + full.onaddress;
                	var offaddress = "(" + full.offcity + ")" + full.offaddress;
                	return showToolTips(onaddress, 12, undefined, offaddress);
                }
	        },
	       /* {mDataProp: "cancelparty", sTitle: "取消方", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		switch(full.cancelparty) {
	               	case "1": return "客服"; break;
	               	case "3": return "下单人"; break;
	               	case "4": return "系统"; break;
	               	default: return "/";
	               }
	        	}
	        },*/
	        {mDataProp: "tradeno", sTitle: "交易流水号", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		if (full.orderstatus == '7') {
	        			if (full.paymentstatus == '1' || full.paymentstatus == '8') {// 1-已支付   8-已付结
	        				if (full.paytype == '2' || full.paytype == '3') {
	        					return full.tradeno;
		        			} else {
		        				return '/';
		        			}
	        			} else {
	        				return '/';
	        			}
	        		} else {
	        			return '/';
	        		}
	        	}
	        }/*,
            {mDataProp: "shortname", sTitle: "服务车企", sClass: "center", sortable: true}*/
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

function initForm() {
	$('.searchDate').datetimepicker({
        format: "yyyy/mm/dd hh:ii", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 0,
        forceParse: true,
        clearBtn: true,
        minuteStep: 1
    });
}

/**
 * 显示长度限制
 * @param addr
 */
function limitLength(text) {
	if(null != text && text.length > 18) {
		return text.substr(0, 18) + "...";
	}
	return text;
}

function initSelect2() {
	$("#orderPerson").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrderManage/GetNetAboutCarOrderUserByQuery",
			dataType : 'json',
			data : function(term, page) {
				return {
					orderPerson: term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
	
	$("#driver").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrderManage/GetNetAboutCarOrderDriverByQuery?vehicleType=1",
			dataType : 'json',
			data : function(term, page) {
				return {
					driver: term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});

    $("#leasescompanyid").select2({
        placeholder: "服务车企",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "OrderManage/GetBelongLeaseCompanySelect",
            dataType: 'json',
            data: function (term, page) {
                $(".datetimepicker").hide();
                return {
                    shortname: term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });

}

var isclear = false;
/**
 * 查询
 */
function search() {
	
	if ($("#minUseTime").val() != "" && $("#maxUseTime").val() != "") {
		if ($("#maxUseTime").val() < $("#minUseTime").val()) {
			toastr.error("截止时间应大于开始时间", "提示");
			return;
		}
	}
	
	var conditionArr = [
		{ "name": "orderNo", "value": $("#orderNo").val() },
		{ "name": "orderStatus", "value": $("#orderStatus").val() },
		{ "name": "orderPerson", "value": $("#orderPerson").val() },
		{ "name": "driver", "value": $("#driver").val() },
		{ "name": "payType", "value": $("#payType").val() },
		{ "name": "cancelParty", "value": $("#cancelParty").val() },
		{ "name": "orderSource", "value": $("#orderSource").val() },
		{ "name": "tradeNo", "value": $("#tradeNo").val() },
		{ "name": "minUseTime", "value": $("#minUseTime").val() },
		{ "name": "maxUseTime", "value": $("#maxUseTime").val() },
        { "name":"belongleasecompany", "value":$("#leasescompanyid").val()}
	];
	if (isclear) {
		dataGrid.fnSearch(conditionArr,"暂无服务订单信息");
	} else {
		dataGrid.fnSearch(conditionArr);
	}

	$("#orderNoExport").val($("#orderNo").val());
	$("#orderStatusExport").val($("#orderStatus").val());
	$("#orderPersonExport").val($("#orderPerson").val());
	$("#driverExport").val($("#driver").val());
	$("#payTypeExport").val($("#payType").val());
	$("#cancelPartyExport").val($("#cancelParty").val());
	$("#orderSourceExport").val($("#orderSource").val());
	$("#tradeNoExport").val($("#tradeNo").val());
	$("#minUseTimeExport").val($("#minUseTime").val());
	$("#maxUseTimeExport").val($("#maxUseTime").val());
    $("#leasescompanyidExport").val($("#leasescompanyid").val());
}

/**
 * 清空
 */
function clearParameter() {
	
	$("#orderNo").val("");
	$("#orderStatus").val("");
	$("#orderPerson").select2("val","");
	$("#driver").select2("val","");
	$("#payType").val("");
	$("#cancelParty").val("");
	$("#orderSource").val("");
	$("#tradeNo").val("");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
    $("#leasescompanyid").select2("val", "");
	isclear = true;
	search();
	isclear = false;
}

/**
 * 导出数据
 */
function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrderManage/GetTaxiOrderExport?orderNo="+$("#orderNoExport").val()
	                      +"&orderStatus="+$("#orderStatusExport").val()+"&orderPerson="+$("#orderPersonExport").val()	                      
	                      +"&driver="+$("#driverExport").val()+"&payType="+$("#payTypeExport").val()+"&cancelParty="+$("#cancelPartyExport").val()
	                      +"&orderSource="+$("#orderSourceExport").val()+"&tradeNo="+$("#tradeNoExport").val()+"&minUseTime="+$("#minUseTimeExport").val()	                      
	                      +"&maxUseTime="+$("#maxUseTimeExport").val() + "&belongleasecompany=" + $("#leasescompanyidExport").val();

	$("#orderNo").blur();
	$("#tradeNo").blur();
	$("#minUseTime").blur();
	$("#maxUseTime").blur();
	
}

