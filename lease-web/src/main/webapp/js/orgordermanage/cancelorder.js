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
    $("#organName").select2({
        placeholder: "机构名称",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "OrderManage/GetOrganByName",
            dataType: 'json',
            data: function (term, page) {
                $(".datetimepicker").hide();
                return {
                    organName: term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });

    $("#organName").on("change", function(e) {
        $("#orderperson").select2("val", "");
    });

    $("#orderperson").select2({
        placeholder: "下单人",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "OrderManage/GetOrgUser",
            dataType: 'json',
            data: function (term, page) {
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
                	vehicletype: "0"
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

    $("#belongleasecompany").select2({
        placeholder: "服务车企",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "OrderManage/GetBelongCompanySelect",
            dataType: 'json',
            data: function (term, page) {
                $(".datetimepicker").hide();
                return {
                    belongleasecompany: term,
                    type: "7",
                    usetype: "0"
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });

}

/**
 * 表单校验
 */
function validateForm() {
	$("#exemptionForm").validate({
		rules : {
            exemption : {
				required : true
			}
		},
		messages : {
            exemption : {
				required : "请输入免责原因"
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
        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOrgOrderByQuery",
        userQueryParam: [{name: "type", value: "7"}, { name: "usetype", value: "0" }],
        iLeftColumn: 3,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无服务订单信息"
        },
        columns: [
            {
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 100,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    if(full.cancelnature == 1 && (full.paymentstatus == "0" || full.paymentstatus == "4")) {
                        return "<button type='button' class='SSbtn red' onclick='exemption(\"" + full.orderno + "\")'><i class='fa fa-paste'></i>免责处理</button>";
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
			       if(orderno == "BC") {
			    	   return "乘客端 | 因公";
			       } else if(orderno == "BZ") {
			    	   return "租赁端 | 因公";
			       } else if(orderno == "BJ") {
			    	   return "机构端";
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
                    return "<a href='" + $("#baseUrl").val() + "OrderManage/OrgOrderDetailIndex?orderno="
                        + full.orderno + "'>" + full.orderno + "</a>";
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
                "mDataProp": "cancelnature",
                "sClass": "center",
                "sTitle": "取消性质",
                "mRender": function (data, type, full) {
                    if(full.cancelnature == 1) {
                        return "有责";
                    } else if(full.cancelnature == 2) {
                        return "免责";
                    } else {
                        return "/";
                    }
                }
            },
            {
                "mDataProp": "cancelparty",
                "sClass": "center",
                "sTitle": "取消方",
                "mRender": function (data, type, full) {
                    if(full.cancelparty == "0") {
                        return "客服";
                    } else if(full.cancelparty == "2" || full.cancelparty == "3") {
                        return "乘客";
                    } else if(full.cancelparty == "4") {
                        return "系统";
                    } else {
                        return "/";
                    }
                }
            },
            {
                "mDataProp": "dutyparty",
                "sClass": "center",
                "sTitle": "责任方",
                "mRender": function (data, type, full) {
                    if(full.cancelparty == "2" || full.cancelparty == "3" || full.cancelparty == "4") {
                        return "/";
                    }
                    if(full.dutyparty == 1) {
                        return "乘客";
                    } else if(full.dutyparty == 2) {
                        return "司机";
                    } else if(full.dutyparty == 3) {
                        return "客服";
                    } else if(full.dutyparty == 4){
                        return "平台";
                    } else {
                        return "/";
                    }
                }
            },
            {
                "mDataProp": "cancelamount",
                "sClass": "center",
                "sTitle": "取消费用(元)",
                "mRender": function (data, type, full) {
                    if(full.cancelnature == 2) {
                        return "/";
                    }
                    return full.cancelamount;
                }
            },
            {
                "mDataProp": "canceltime",
                "sClass": "left",
                "sTitle": "取消时间",
                "mRender": function (data, type, full) {
                    if(null == full.canceltime) {
                        return "/";
                    } else {
                        return dateFtt(full.canceltime, "yyyy-MM-dd hh:mm");
                    }
                }
            },
            {
                "mDataProp": "cancelreason",
                "sClass": "left",
                "sTitle": "取消原因",
                "mRender": function (data, type, full) {
                    switch (full.cancelreason) {
                        case 1: return "不再需要用车";
                        case 2: return "乘客迟到违约";
                        case 3: return "司机迟到违约";
                        case 4: return "司机不愿接乘客";
                        case 5: return "业务操作错误";
                        case 6: return "暂停供车服务";
                        case 7: return "系统派单失败";
                        case 8: return "行程有变，不再需要用车";
                        case 9: return "司机没来接我，联系不上司机";
                        default: return "/";
                    }
                }
            },
            {
                "mDataProp": "cancelname",
                "sClass": "center",
                "sTitle": "操作人",
                "mRender": function (data, type, full) {
                    if(full.cancelparty == "2" || full.cancelparty == "3" || full.cancelparty == "4") {
                        return "/";
                    }
                    return full.cancelname;
                }
            },
            {
                "mDataProp": "exemption",
                "sClass": "center",
                "sTitle": "处理原因",
                "mRender": function (data, type, full) {
                    if(full.cancelnature == 1 && full.paymentstatus == "9") {
                        return full.exemption;
                    }
                    return "/";
                }
            },
            {
                "mDataProp": "exemptionname",
                "sClass": "center",
                "sTitle": "处理人",
                "mRender": function (data, type, full) {
                    if(full.cancelnature == 1 && full.paymentstatus == "9") {
                        return full.exemptionname;
                    }
                    return "/";
                }
            },
            {
                "mDataProp": "DDZT",
                "sClass": "center",
                "sTitle": "订单状态",
                "mRender": function (data, type, full) {
                    if(full.cancelnature == 2) {
                        return "已取消";
                    }
                    switch (full.paymentstatus) {
                        case "0": return "未支付";
                        case "1": return "已支付";
                        case "2": return "结算中";
                        case "3": return "已结算";
                        case "4": return "未结算";
                        case "9": return "已关闭";
                        default: return "/";
                    }
                }
            },
            {
                "mDataProp": "paytype",
                "sClass": "center",
                "sTitle": "支付渠道",
                "mRender": function (data, type, full) {
                    if(full.paymentstatus != "1") {
                        return "/";
                    }
                    if(full.paytype == "1") {
                        return "余额支付";
                    } else if(full.paytype == "2") {
                        return "微信支付";
                    } else if(full.paytype == "3") {
                        return "支付宝支付";
                    } else {
                        return "/";
                    }
                }
            },
            {
                "mDataProp": "paymethod",
                "sClass": "center",
                "sTitle": "支付方式",
                "mRender": function (data, type, full) {
                    if(full.paymentstatus != "1") {
                        return "/";
                    }
                    if(full.paytype == "0") {
                        return "个人支付";
                    } else if(full.paytype == "2") {
                        return "机构支付";
                    }
                    return "/";
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
                mDataProp : "organname",
                sTitle : "所属机构",
                sClass : "center",
                sortable : true,
                "mRender" : function(data, type, full) {
                    if(full.userstatus == 1) {
                        return full.organname;
                    }
                    return "/";
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
                    var onaddress = "(" + full.oncity + ")" + full.onaddress;
                    var offaddress = "(" + full.offcity + ")" + full.offaddress;
                    return showToolTips(onaddress, 12, undefined, offaddress);
                }
            },
            {
                "mDataProp": "ordernature",
                "sClass": "center",
                "sTitle": "订单性质",
                "mRender": function (data, type, full) {
                    if(null == full.belongleasecompany || full.belongleasecompany == "") {
                        return "/";
                    }
                    if(full.companyid == full.belongleasecompany) {
                        return "自营单";
                    } else {
                        return "联盟单";
                    }
                }
            },
            {
                "mDataProp": "belongcompanyname",
                "sClass": "center",
                "sTitle": "服务车企",
                "mRender": function (data, type, full) {
                    if(null == full.belongleasecompany || full.belongleasecompany == "") {
                        return "/";
                    }
                    return full.belongcompanyname;
                }
            }
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
        {"name":"ordernature", "value":$("#ordernature").val()},
        {"name":"orderType", "value":$("#ordertype").val()},
        {"name":"belongleasecompany", "value":$("#belongleasecompany").val()},
        {"name":"organId", "value":$("#organName").val()},
        {"name":"driverid", "value":$("#drivername").val()},
        {"name":"userId", "value":$("#orderperson").val()},
        {"name":"paymentstatus", "value":$("#paymentstatus").val()},
        {"name":"orderNo", "value":$("#orderno").val()},
        {"name":"paymethod", "value":$("#paymethod").val()},
        {"name":"tradeno", "value":$("#tradeno").val()},
        {"name":"cancelparty", "value":$("#cancelparty").val()},
        {"name":"cancelnature", "value":$("#cancelnature").val()},
        {"name":"minUseTime", "value":$("#minUseTime").val()},
        {"name":"maxUseTime", "value":$("#maxUseTime").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 免责处理
 * @param {} orderno
 */
function exemption(orderno) {
	$("#exemptionFormDiv").show();
	showObjectOnForm("exemptionForm", null);
    $("#ordernoHide").val(orderno);
}

/**
 * 免责确认
 */
function save() {
	var form = $("#exemptionForm");
	if(!form.valid()) return;
	
	var formData = {
		orderno: $("#ordernoHide").val(),
        exemption: $("#exemption").val()
	}
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "OrderManage/ExemptionOrder",
		data: JSON.stringify(formData),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				$("#exemptionFormDiv").hide();
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
	$("#exemptionFormDiv").hide();
}

/**
 * 初始化查询
 */
function initSearch() {
    $("#ordersource").val("");
    $("#ordernature").val("");
    $("#ordertype").val("");
    $("#belongleasecompany").select2("val", "");
    $("#organName").select2("val", "");
    $("#drivername").select2("val", "");
    $("#orderperson").select2("val", "");
    $("#paymentstatus").val("");
    $("#orderno").val("");
    $("#paymethod").val("");
    $("#tradeno").val("");
    $("#cancelparty").val("");
    $("#cancelnature").val("");
    $("#minUseTime").val("");
    $("#maxUseTime").val("");
	search();
}

/**
 * 导出订单数据
 */
function exportOrder() {
	var orderNo = $("#orderno").val();
	var orderType = $("#ordertype").val();
	var orderstatus = $("#orderstatus").val();
	var userId = $("#orderperson").val();
	var driverid = $("#drivername").val();
	var cancelparty = $("#cancelparty").val();
	var minUseTime = $("#minUseTime").val();
	var maxUseTime = $("#maxUseTime").val();
	var ordersource = $("#ordersource").val();
	var tradeno = $("#tradeno").val();
    var leasescompanyid =  $("#leasescompanyid").val();
	var type = "5";
	var usetype = "0";
	window.location.href = $("#baseUrl").val()
			+ "OrderManage/ExportOrder?orderNo=" + orderNo + "&orderType="
			+ orderType + "&orderstatus=" + orderstatus + "&userId=" + userId
			+ "&driverid=" + driverid + "&cancelparty=" + cancelparty
			+ "&minUseTime=" + minUseTime + "&maxUseTime=" + maxUseTime
			+ "&ordersource=" + ordersource + "&tradeno=" + tradeno + "&type="
			+ type + "&usetype=" + usetype + "&belongleasecompany=" + leasescompanyid;
	$("#orderno").blur();
	$("#minUseTime").blur();
	$("#maxUseTime").blur();
	$("#tradeno").blur();
}
