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
	validateForm();
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

	$("#belongleasecompany").select2({
		placeholder: "服务车企",
		minimumInputLength: 0,
		allowClear: true,
		ajax: {
			url: $("#baseUrl").val() + "TaxiOrderManage/GetBelongCompanySelect",
			dataType: 'json',
			data: function (term, page) {
				$(".datetimepicker").hide();
				return {
					belongleasecompany: term,
					type: "6",
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
 * 表单校验
 */
function validateForm() {
	$("#cancelpartyForm").validate({
		rules : {
			reviewpersonAgain : {
				required : true
			},
			reasonTextarea : {
				required : true,
				maxlength : 200
			}
		},
		messages : {
			reviewpersonAgain : {
				required : "复核人不能为空"
			},
			reasonTextarea : {
				required : "请输入申请复核原因",
				maxlength : "最大长度不能超过200个字符"
			}
		}
	})
}

/**
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "manualOrderdataGrid",
        sAjaxSource: $("#baseUrl").val() + "TaxiOrderManage/GetOpTaxiOrderByQuery",
        userQueryParam: [{name: "type", value: "6"}],
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
                "sWidth": 100,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
					if(full.orderstatus == '7' && full.paymentstatus != "9" && full.shouldpayamount > 0 && full.actualpayamount > 0) {
						return "<button type='button' class='SSbtn red' onclick='applyReview(\"" + full.orderno + "\")'><i class='fa fa-paste'></i>申请复核</button>";
					} else {
						return "";
					}
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
					return "<a href='" + $("#baseUrl").val() + "TaxiOrderManage/OrderDetailIndex?orderno="
						+ full.orderno + "'>" + full.orderno + "</a>";
                }
            },
            {mDataProp: "paymentstatus", sTitle: "订单状态", sClass: "center", sortable: true,
            	"mRender": function(data, type, full) {
					if(full.paymentstatus == "1") {
						return "已支付";
					} else if(full.paymentstatus == "3") {
						return "已结算";
					} else if(full.paymentstatus == "8") {
						return "已付结";
					} else if(full.paymentstatus == "9") {
						return "已关闭";
					} else {
						return "/";
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
                "mDataProp": "ZFQD",
                "sClass": "center",
                "sTitle": "支付渠道",
                "mRender": function (data, type, full) {
					if(full.paymentstatus == "3") {
						return "/";
					}
                	switch(full.paytype) {
	                	case "1": return "余额支付"; break;
	                	case "2": return "微信支付"; break;
	                	case "3": return "支付宝支付"; break;
	                	default: return "/";
                	}
                }
            },
			{
				"mDataProp": "expensetype",
				"sClass": "center",
				"sTitle": "费用类型",
				"mRender": function (data, type, full) {
					if(full.expensetype == 1) {
						return "行程服务";
					} else if(full.expensetype == 2) {
						return "取消处罚";
					} else {
						return "/";
					}
				}
			},
            {
				mDataProp : "orderamount",
				sTitle : "行程费用(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.expensetype == 2) {
						return "/";
					}
					if (null == full.shouldpayamount) {
						return full.orderamount;
					} else {
						return full.shouldpayamount;
					}
				}
			},
			{
				mDataProp : "SFJE",
				sTitle : "实付金额(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.expensetype == 2) {
						return "/";
					}
					var actualamount = full.actualamount;
					if(null == actualamount) {
						actualamount = 0;
					}
					return full.shouldpayamount - actualamount;
				}
			},
			{
				mDataProp : "actualamount",
				sTitle : "优惠金额(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.paymentstatus == "1" && full.paymentmethod == "0") {
						if(null == full.actualamount) {
							return "0";
						} else {
							return full.actualamount;
						}
					} else {
						return "/";
					}
				}
			},
			{
				mDataProp : "DDFY",
				sTitle : "调度费用(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					return full.schedulefee.toFixed(1);
				}
			},
			{
				mDataProp : "cancelamount",
				sTitle : "取消费用(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.expensetype == 2) {
						return full.cancelamount;
					} else {
						return "/";
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
            {mDataProp: "drivername", sTitle: "司机信息", sClass: "center", sortable: true,
            	"mRender": function(data, type, full) {
            		if(null != full.driverid && "" != full.driverid) {
						return full.drivername;
					} else {
						return "/";
					}
            	}
            },
			{
				"mDataProp": "usetime",
				"sClass": "left",
				"sTitle": "用车时间",
				"mRender": function (data, type, full) {
					if(null == full.usetime) {
						return "/";
					} else {
						return dateFtt(full.usetime, "yyyy-MM-dd hh:mm");
					}
				}
			},
            {
                "mDataProp": "SCDZ",
                "sClass": "center",
                "sTitle": "上下车地址",
                "mRender": function (data, type, full) {
					var onaddress = "(" + full.oncityname + ")" + full.onaddress;
					var offaddress = "(" + full.offcityname + ")" + full.offaddress;
					return showToolTips(onaddress, 12, undefined, offaddress);
                }
            },
			{
				"mDataProp": "tradeno",
				"sClass": "center",
				"sTitle": "交易流水号",
				"mRender": function (data, type, full) {
					if(null != full.tradeno) {
						return full.tradeno;
					} else {
						return "/";
					}
				}
			},
			{mDataProp: "belongcompanyname", sTitle: "服务车企", sClass: "center", sortable: true}
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
		{"name":"ordersource", "value":$("#ordersource").val()},
		{"name":"belongleasecompany", "value":$("#belongleasecompany").val()},
		{"name":"driverid", "value":$("#drivername").val()},
		{"name":"userId", "value":$("#orderperson").val()},
		{"name":"paymentstatus", "value":$("#paymentstatus").val()},
		{"name":"paytype", "value":$("#paytype").val()},
		{"name":"expensetype", "value":$("#expensetype").val()},
		{"name":"paymentmethod", "value":$("#paymentmethod").val()},
		{"name":"orderNo", "value":$("#orderno").val()},
		{"name":"tradeno", "value":$("#tradeno").val()},
		{"name":"minUseTime", "value":$("#minUseTime").val()},
		{"name":"maxUseTime", "value":$("#maxUseTime").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 申请复核
 * @param {} orderno
 */
function applyReview(orderno) {
	$("#orderno").val(orderno);
	
	$("#cancelpartyFormDiv").show();
	
	showObjectOnForm("cancelpartyForm", null);
	
	var editForm = $("#cancelpartyForm").validate();
	editForm.resetForm();
	editForm.reset();
}

/**
 * 复核
 */
function save() {
	var form = $("#cancelpartyForm");
	if(!form.valid()) return;
	
	var formData = {
		orderno: $("#orderno").val(),
		orderreason: $("#reasonTextarea").val(),
		reviewperson: $("#reviewpersonAgain").val()
	}
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "TaxiOrderManage/ApplyRecheckTaxiOrder",
		data: JSON.stringify(formData),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				$("#cancelpartyFormDiv").hide();
				toastr.options.onHidden = function() {
            		window.location.href = $("#baseUrl").val() + "TaxiOrderManage/AbnormalOrderIndex";
            	}
            	toastr.success(message, "提示");
			} else {
            	toastr.error(message, "提示");
			}
			
			dataGrid._fnReDraw();
		}
	});
}

/**
 * 取消
 */
function canel() {
	$("#cancelpartyFormDiv").hide();
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
	$("#ordersource").val("");
	$("#belongleasecompany").select2("val", "");
	$("#drivername").select2("val", "");
	$("#orderperson").select2("val", "");
	$("#paymentstatus").val("");
	$("#paytype").val("");
	$("#expensetype").val("");
	$("#paymentmethod").val("");
	$("#orderno").val("");
	$("#tradeno").val("");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	search();
}

/**
 * 导出订单数据
 */
function exportOrder() {
	var orderNo = $("#orderno").val();
	var orderstatus = $("#orderstatus").val();
	var userId = $("#orderperson").val();
	var driverid = $("#drivername").val();
	var paymentmethod = $("#paymentmethod").val();
	var paytype = $("#paytype").val();
	var cancelparty = $("#cancelparty").val();
	var ordersource = $("#ordersource").val();
	var tradeno = $("#tradeno").val();
	var leasescompanyid = $("#leasescompanyid").val();
	var minUseTime = $("#minUseTime").val();
	var maxUseTime = $("#maxUseTime").val();
	var type = "5";
	window.location.href = $("#baseUrl").val()
			+ "TaxiOrderManage/ExportOrder?orderNo=" + orderNo + "&orderstatus="
			+ orderstatus + "&userId=" + userId + "&driverid=" + driverid
			+ "&paymentmethod=" + paymentmethod + "&paytype=" + paytype
			+ "&ordersource=" + ordersource + "&tradeno=" + tradeno
			+ "&cancelparty=" + cancelparty + "&leasescompanyid="
			+ leasescompanyid + "&minUseTime=" + minUseTime + "&maxUseTime="
			+ maxUseTime + "&type=" + type + "&belongleasecompany=" + leasescompanyid;
	$("#orderno").blur();
	$("#minUseTime").blur();
	$("#maxUseTime").blur();
	$("#tradeno").blur();
}

