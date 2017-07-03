var dataGrid;
var orderObj = {
	orderInfo: null
};

/**
 * 页面初始化
 */
$(function () {
	manualOrderdataGrid();
	initForm();
});

function initForm() {
	$("#orderperson").select2({
        placeholder: "下单人",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "OrderManage/GetPeUser",
            dataType: 'json',
            data: function (term, page) {
            	$(".datetimepicker").hide();
                return {
                    userName: term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
	
	$("#drivername").select2({
        placeholder: "司机",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "PubDriver/listPubDriverBySelect",
            dataType: 'json',
            data: function (term, page) {
            	$(".datetimepicker").hide();
                return {
                	queryText: term,
                	vehicletype: "1",
                	toC: "0"
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
	
	$("#leasescompanyid").select2({
        placeholder: "服务车企",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "OrderManage/GetToCCompanySelect",
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
	
	$('.searchDate').datetimepicker({
        format: "yyyy-mm-dd hh:ii", //选择日期后，文本框显示的日期格式
        language: 'zh-CN',
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
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "manualOrderdataGrid",
        sAjaxSource: $("#baseUrl").val() + "TaxiOrderManage/GetOpTaxiOrderByQuery",
        userQueryParam: [{name: "type", value: "3"}],
        iLeftColumn: 3,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无服务订单信息"
        },
        columns: [
        	{
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 150,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                	orderObj.orderInfo = full;
                    var html = [];
                    html.push("<button type=\"button\" class=\"SSbtn red\" onclick=\"review('" + full.orderno + "')\"><i class=\"fa fa-paste\"></i>复核</button>");
                    html.push("&nbsp;");
                    html.push("<button type=\"button\" class=\"SSbtn blue\" onclick=\"reject('" + full.orderno + "')\"><i class=\"fa fa-paste\"></i>不受理</button>");
                    return html.join("");
                }
            },
            {
                "mDataProp": "DDLY",
                "sClass": "center",
                "sTitle": "订单来源",
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
	        {
                "mDataProp": "DDH",
                "sClass": "center",
                "sTitle": "订单号",
                "sWidth": 200,
                "mRender": function (data, type, full) {
                	return '<a href="' + $("#baseUrl").val() + 'TaxiOrderManage/OrderDetailIndex?orderno=' + full.orderno + '">' + full.orderno + '</a>';
                }
            },
			{
				mDataProp : "paymentstatus",
				sTitle : "订单状态",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					switch (full.paymentstatus) {
						case "0": return "未支付"; break;
						case "1": return "已支付"; break;
						case "3": return "已结算"; break;
						case "4": return "未结算"; break;
						case "5": return "未付结"; break;
						case "6": return "未付结"; break;
						case "7": return "未付结"; break;
						case "8": return "已付结"; break;
						default: return "/";
					}
				}
			},
	        {mDataProp: "reviewperson", sTitle: "复核方", sClass: "center", sortable: true,
            	"mRender": function(data, type, full) {
            		switch(full.reviewperson) {
            			case "1": return "司机"; break;
            			case "2": return "下单人"; break;
            			default: return "/";
            		}
            	}
            },
            {
				mDataProp : "orderamount",
				sTitle : "行程费用(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if (null == full.shouldpayamount) {
						return full.orderamount;
					} else {
						return full.shouldpayamount;
					}
				}
			},
	        {mDataProp: "paymentmethod", sTitle: "付款方式", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		switch(full.paymentmethod) {
	        			case "0": return "在线支付"; break;
	        			case "1": return "线下付现"; break;
	        			default: return "/";
	        		}
	        	}
	        },
	        {
                "mDataProp": "XDRXX",
                "sClass": "center",
                "sTitle": "下单人信息",
                "mRender": function (data, type, full) {
                	if(null == full.nickname || "" == full.nickname) {
                		return full.account;
                	} else {
                		return full.nickname + " " + full.account;
                	}
                }
            },
            {
                "mDataProp": "CCRXX",
                "sClass": "center",
                "sTitle": "乘车人信息",
                "mRender": function (data, type, full) {
                	if(null == full.passengers || "" == full.passengers) {
                		return full.passengerphone;
                	} else {
                		return full.passengers + " " + full.passengerphone;
                	}
                }
            },
	        {mDataProp: "drivername", sTitle: "司机信息", sClass: "center", sortable: true },
	        {mDataProp: "usetime", sTitle: "用车时间", sClass: "center", sortable: true },
	        {
                "mDataProp": "SCDZ",
                "sClass": "center",
                "sTitle": "上下车地址",
                "mRender": function (data, type, full) {
                	var onaddress = "(" + full.oncity + ")" + full.onaddress;
                	var offaddress = "(" + full.offcity + ")" + full.offaddress;
                	return showToolTips(onaddress, 12, undefined, offaddress);
                }
            },
            {mDataProp: "shortname", sTitle: "服务车企", sClass: "center", sortable: true}
        ],
        userHandle: function(oSettings, result) {
        	if(null == result.aaData || result.aaData.length == 0) {
        		$("#exportBtn").attr("disabled", true);
        		$("#exportBtn").removeClass("blue_q").addClass("grey");
        	} else {
        		$("#exportBtn").attr("disabled", false);
        		$("#exportBtn").removeClass("grey").addClass("blue_q");
        	}
        }
    };
	dataGrid = renderGrid(gridObj);
}


/**
 * 查询
 */
function search() {
	var conditionArr = [
		{"name":"orderNo", "value":$("#orderno").val()},
		{"name":"paymentstatus", "value":$("#paymentstatus").val()},
		{"name":"driverid", "value":$("#drivername").val()},
		{"name":"userId", "value":$("#orderperson").val()},
		{"name":"reviewperson", "value":$("#reviewperson").val()},
		{"name":"paymentmethod", "value":$("#paymentmethod").val()},
		{"name":"ordersource", "value":$("#ordersource").val()},
		// {"name":"leasescompanyid", "value":$("#leasescompanyid").val()},
        {"name":"belongleasecompany", "value":$("#leasescompanyid").val()},
		{"name":"minUseTime", "value":$("#minUseTime").val()},
		{"name":"maxUseTime", "value":$("#maxUseTime").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 复核
 * @param {} orderno
 */
function review(orderno) {
	window.location.href = $("#baseUrl").val() + "TaxiOrderManage/OpTaxiOrderReviewIndex?orderno=" + orderno;
}

/**
 * 不受理
 * @param {} orderno
 */
function reject(orderno) {
	var data = {orderno: orderno};
	Zconfirm("您确认不受理吗？",function(){
		$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "TaxiOrderManage/OpTaxiOrderReject",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				toastr.success(message, "提示");
			} else {
            	toastr.error(message, "提示");
			}
			dataGrid._fnReDraw();
		}
	});
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

/**
 * 初始化查询
 */
function initSearch() {
	$("#orderno").val("");
	$("#paymentstatus").val("");
	$("#drivername").select2("val", "");
	$("#orderperson").select2("val", "");
	$("#reviewperson").val("");
	$("#paymentmethod").val("");
	$("#ordersource").val("");
	$("#leasescompanyid").select2("val", "");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	search();
}

/**
 * 导出订单数据
 */
function exportOrder() {
	var orderNo = $("#orderno").val();
	var paymentstatus = $("#paymentstatus").val();
	var driverid = $("#drivername").val();
	var userId = $("#orderperson").val();
	var reviewperson = $("#reviewperson").val();
	var paymentmethod = $("#paymentmethod").val();
	var ordersource = $("#ordersource").val();
	var leasescompanyid = $("#leasescompanyid").val();
	var minUseTime = $("#minUseTime").val();
	var maxUseTime = $("#maxUseTime").val();
	var type = "3";
	window.location.href = $("#baseUrl").val()
			+ "TaxiOrderManage/ExportOrder?orderNo=" + orderNo + "&paymentstatus="
			+ paymentstatus + "&driverid=" + driverid + "&userId=" + userId
			+ "&reviewperson=" + reviewperson + "&paymentmethod="
			+ paymentmethod + "&ordersource=" + ordersource
			+ "&leasescompanyid=" + leasescompanyid + "&minUseTime="
			+ minUseTime + "&maxUseTime=" + maxUseTime + "&type=" + type + "&belongleasecompany=" + leasescompanyid;
	$("#orderno").blur();
	$("#minUseTime").blur();
	$("#maxUseTime").blur();
}
