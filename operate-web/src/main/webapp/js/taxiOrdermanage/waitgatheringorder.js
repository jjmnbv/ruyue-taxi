var dataGrid;

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
					type: "5",
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
        userQueryParam: [{name: "type", value: "5"}],
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
					if(full.orderstatus == '7') {
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
            {
                "mDataProp": "DDZT",
                "sClass": "center",
                "sTitle": "订单状态",
                "mRender": function (data, type, full) {
                	switch(full.paymentstatus) {
						case "0": return "未支付"; break;
						case "4": return "未结算"; break;
						case "5": return "未付结"; break;
						case "6": return "未付结"; break;
						case "7": return "未付结"; break;
						default: return "/";
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
                "mDataProp": "paytype",
                "sClass": "center",
                "sTitle": "支付渠道",
                "mRender": function (data, type, full) {
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
						return "行程费用";
					} else if(full.expensetype == 2) {
						return "取消处罚";
					} else {
						return "/";
					}
				}
			},
			{
				mDataProp : "XCFY",
				sTitle : "行程费用(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.expensetype == 2) {
						return "/";
					}
					var orderamount = full.shouldpayamount;
					if(null == full.shouldpayamount) {
						orderamount = full.orderamount;
					}
					var html = orderamount.toFixed(1);
					switch(full.paymentstatus) {
						case "0": html += "<br><span class='font_red'>未支付</span>"; break;
						case "4": html += "<br><span class='font_red'>未结算</span>"; break;
						case "5": html += "<br><span class='font_red'>未结算</span>"; break;
						case "6": html += "<br><span class='font_green'>已结算</span>"; break;
						case "7": html += "<br><span class='font_red'>未结算</span>"; break;
						case "8": html += "<br><span class='font_green'>已结算</span>"; break;
						default: html += "";
					}
					return html;
				}
			},
			{
				mDataProp : "DDFY",
				sTitle : "调度费用(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					var html = full.schedulefee.toFixed(1);
					if(full.schedulefee == 0 || full.expensetype == 2) {
						return html;
					}
					switch(full.paymentstatus) {
						case "0": html += "<br><span class='font_red'>未支付</span>"; break;
						case "5": html += "<br><span class='font_red'>未支付</span>"; break;
						case "6": html += "<br><span class='font_red'>未支付</span>"; break;
						case "7": html += "<br><span class='font_green'>已支付</span>"; break;
						case "8": html += "<br><span class='font_green'>已支付</span>"; break;
						default: html += "";
					}
					return html;
				}
			},
			{
				mDataProp : "cancelamount",
				sTitle : "取消费用(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.expensetype == 1) {
						return "/";
					}
					if(null == full.cancelamount) {
						return "0";
					}
					return full.cancelamount;
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
			{
				mDataProp : "drivername",
				sTitle : "司机信息",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
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
                "sClass": "left",
                "sTitle": "上下车地址",
                "mRender": function (data, type, full) {
					var onaddress = "(" + full.oncityname + ")" + full.onaddress;
					var offaddress = "(" + full.offcityname + ")" + full.offaddress;
					return showToolTips(onaddress, 12, undefined, offaddress);
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
		{"name":"paymentmethod", "value":$("#paymentmethod").val()},
		{"name":"paytype", "value":$("#paytype").val()},
		{"name":"expensetype", "value":$("#expensetype").val()},
		{"name":"orderNo", "value":$("#orderno").val()},
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
            	$("#cancelpartyFormDiv").hide();
			}
			
			dataGrid._fnReDraw();
		}
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
 * 取消
 */
function canel() {
	$("#cancelpartyFormDiv").hide();
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
	$("#paymentmethod").val("");
	$("#paytype").val("");
	$("#expensetype").val("");
	$("#orderno").val("");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	search();
}

/**
 * 导出订单数据
 */
function exportOrder() {
	var orderNo = $("#orderno").val();
	var userId = $("#orderperson").val();
	var paymentstatus = $("#paymentstatus").val();
	var driverid = $("#drivername").val();
	var paymentmethod = $("#paymentmethod").val();
	var paytype = $("#paytype").val();
	var ordersource = $("#ordersource").val();
	var leasescompanyid = $("#leasescompanyid").val();
	var minUseTime = $("#minUseTime").val();
	var maxUseTime = $("#maxUseTime").val();
	var type = "6";
	window.location.href = $("#baseUrl").val()
			+ "TaxiOrderManage/ExportOrder?orderNo=" + orderNo + "&userId="
			+ userId + "&paymentstatus=" + paymentstatus + "&driverid="
			+ driverid + "&paymentmethod=" + paymentmethod + "&paytype="
			+ paytype + "&ordersource=" + ordersource + "&leasescompanyid="
			+ leasescompanyid + "&minUseTime=" + minUseTime + "&maxUseTime="
			+ maxUseTime + "&type=" + type + "&belongleasecompany=" + leasescompanyid;
	$("#orderno").blur();
	$("#minUseTime").blur();
	$("#maxUseTime").blur();
}
