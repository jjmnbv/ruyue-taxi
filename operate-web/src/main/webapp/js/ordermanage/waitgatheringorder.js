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
                	vehicletype: "0",
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
        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOpOrderByQuery",
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
                	var html = [];
                	if(full.paymentstatus == "0" && full.reviewstatus != "1") {
                		html.push("<button type=\"button\" class=\"SSbtn red\" onclick=\"applyReview('" + full.orderno + "')\"><i class=\"fa fa-paste\"></i>申请复核</button>");
                	}
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
                   var htmlArr = [];
                   htmlArr.push("<a href=\"" + $("#baseUrl").val() + "OrderManage/OrderDetailIndex?orderno=");
                   htmlArr.push(full.orderno);
                   htmlArr.push("\">");
                    htmlArr.push(full.orderno);
                   htmlArr.push("</a>");
                   return htmlArr.join("");
                }
            },
	        {
                "mDataProp": "DDLX",
                "sClass": "center",
                "sTitle": "订单类型",
                "mRender": function (data, type, full) {
                   switch(full.ordertype) {
                   	case "1": return "约车"; break;
                   	case "2": return "接机"; break;
                   	case "3": return "送机"; break;
                   	default: return "/";
                   }
                }
            },
            {
                "mDataProp": "DDZT",
                "sClass": "center",
                "sTitle": "订单状态",
                "mRender": function (data, type, full) {
                	switch(full.paymentstatus) {
						case "0": return "<span class='font_red'>未支付</span>"; break;
						default: return "/";
                	}
                }
            },
            {
                "mDataProp": "ZFFS",
                "sClass": "center",
                "sTitle": "支付方式",
                "mRender": function (data, type, full) {
                	return "个人支付";
                }
            },
            {
				mDataProp : "orderamount",
				sTitle : "订单金额(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(null == full.shouldpayamount) {
						return full.orderamount;
					} else {
						return full.shouldpayamount;
					}
				}
			},
	        {mDataProp: "mileage", sTitle: "里程(公里)", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		if(null != full.reviewid && "" != full.reviewid) {
	        			return (full.reviewmileage/1000).toFixed(1);
	        		}
	        		return (full.mileage/1000).toFixed(1);
	        	}
	        },
	        {mDataProp: "JFSC", sTitle: "计费时长(分钟)", sClass: "center",
	        	mRender: function(data, type, full) {
	        		if(full.orderstatus == '8') {
	        			return "0";
	        		}
	        		if(null != full.pricecopy) {
	        			var timetype = JSON.parse(full.pricecopy).timetype;
	        			if(timetype == 0) {
	        				if(null != full.reviewid && "" != full.reviewid) {
	        					return Math.ceil(full.reviewtimes/60);
	        				}
	        				var starttime = new Date(full.starttime);
	        				var endtime = new Date(full.endtime);
	        				return Math.ceil((endtime - starttime)/(1000*60));
	        			} else {
	        				if(null != full.reviewid && "" != full.reviewid) {
	        					return full.reviewcounttimes;
	        				}
	        				return JSON.parse(full.pricecopy).slowtimes;
	        			}
	        		} else {
	        			return 0;
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
			{mDataProp: "usetime", sTitle: "用车时间", sClass: "center", sortable: true },
	        {
                "mDataProp": "SCDZ",
                "sClass": "left",
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
		{"name":"orderType", "value":$("#ordertype").val()},
		{"name":"userId", "value":$("#orderperson").val()},
		{"name":"driverid", "value":$("#drivername").val()},
		{"name":"minUseTime", "value":$("#minUseTime").val()},
		{"name":"maxUseTime", "value":$("#maxUseTime").val()},
		{"name":"ordersource", "value":$("#ordersource").val()},
		// {"name":"leasescompanyid", "value":$("#leasescompanyid").val()}
        {"name":"belongleasecompany", "value":$("#leasescompanyid").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

function review(orderno) {
	window.location.href = $("#baseUrl").val() + "OrderManage/OrderReviewIndex?orderno=" + orderno + "&usetype=0";
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
		url: $("#baseUrl").val() + "OrderManage/ApplyRecheckOrder",
		data: JSON.stringify(formData),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				$("#cancelpartyFormDiv").hide();
				toastr.options.onHidden = function() {
            		window.location.href = $("#baseUrl").val() + "OrderManage/AbnormalOrderIndex";
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
	$("#orderno").val("");
	$("#ordertype").val("");
	$("#orderperson").select2("val", "");
	$("#drivername").select2("val", "");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	$("#ordersource").val("");
	$("#leasescompanyid").select2("val", "");
	search();
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
 * 导出订单数据
 */
function exportOrder() {
	var orderNo = $("#orderno").val();
	var orderType = $("#ordertype").val();
	var userId = $("#orderperson").val();
	var driverid = $("#drivername").val();
	var minUseTime = $("#minUseTime").val();
	var maxUseTime = $("#maxUseTime").val();
	var ordersource = $("#ordersource").val();
	var leasescompanyid = $("#leasescompanyid").val();
	var type = "6";
	window.location.href = $("#baseUrl").val()
			+ "OrderManage/ExportOrder?orderNo=" + orderNo + "&orderType="
			+ orderType + "&userId=" + userId + "&driverid=" + driverid
			+ "&minUseTime=" + minUseTime + "&maxUseTime=" + maxUseTime
			+ "&ordersource=" + ordersource + "&leasescompanyid="
			+ leasescompanyid + "&type=" + type + "&belongleasecompany=" + leasescompanyid;
	$("#orderno").blur();
	$("#minUseTime").blur();
	$("#maxUseTime").blur();
}
