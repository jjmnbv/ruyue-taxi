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
                    type: "6",
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
        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOrgOrderByQuery",
        userQueryParam: [{name: "type", value: "6"}, { name: "usetype", value: "0" }],
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
                    if(full.orderstatus == '7' && full.reviewstatus != "1" && full.paymentstatus != "9") {
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
                "mDataProp": "DDZT",
                "sClass": "center",
                "sTitle": "订单状态",
                "mRender": function (data, type, full) {
                	if(full.paymentstatus == "1") {
                        return "已支付";
                    } else if(full.paymentstatus == "3") {
                        return "已结算";
                    } else if(full.paymentstatus == "9") {
                        return "已关闭";
                    } else {
                        return "/";
                    }
                }
            },
            {
                "mDataProp": "ZFQD",
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
                mDataProp : "paymethod",
                sTitle : "支付方式",
                sClass : "center",
                sortable : true,
                "mRender" : function(data, type, full) {
                    if(full.paymethod == "0") {
                        return "个人支付";
                    } else if(full.paymethod == "1") {
                        return "个人垫付";
                    } else if(full.paymethod == "2") {
                        return "机构支付";
                    } else {
                        return "/";
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
				sTitle : "订单金额(元)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.expensetype == 1) {
                        return full.shouldpayamount;
                    } else {
                        return "/";
                    }
				}
			},
            {
                mDataProp : "SFJE",
                sTitle : "实付金额(元)",
                sClass : "center",
                sortable : true,
                "mRender" : function(data, type, full) {
                    if(full.paymethod == "1" && full.paymentstatus == "1") {
                        var actualamount = full.actualamount;
                        if(null == actualamount) {
                            actualamount = 0;
                        }
                        return full.shouldpayamount - actualamount;
                    } else {
                        return "/";
                    }
                }
            },
            {
                mDataProp : "actualamount",
                sTitle : "优惠金额(元)",
                sClass : "center",
                sortable : true,
                "mRender" : function(data, type, full) {
                    if(full.paymethod == "1" && full.paymentstatus == "1") {
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
	        {mDataProp: "mileage", sTitle: "里程(公里)", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
                    if(full.expensetype == 2) {
                        return "/";
                    }
	        		if(null != full.reviewid && "" != full.reviewid) {
	        			return (full.reviewmileage/1000).toFixed(1);
	        		}
	        		return (full.mileage/1000).toFixed(1);
	        	}
	        },
	        {mDataProp: "JFSC", sTitle: "计费时长(分钟)", sClass: "center",
	        	mRender: function(data, type, full) {
	        		if(full.expensetype == 2) {
                        return "/";
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
                "mDataProp": "ordernature",
                "sClass": "center",
                "sTitle": "订单性质",
                "mRender": function (data, type, full) {
                    if(full.companyid == full.belongleasecompany) {
                        return "自营单";
                    } else {
                        return "联盟单";
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
        {"name":"ordernature", "value":$("#ordernature").val()},
        {"name":"orderType", "value":$("#ordertype").val()},
        {"name":"belongleasecompany", "value":$("#belongleasecompany").val()},
        {"name":"organId", "value":$("#organName").val()},
        {"name":"driverid", "value":$("#drivername").val()},
        {"name":"userId", "value":$("#orderperson").val()},
        {"name":"paymentstatus", "value":$("#paymentstatus").val()},
        {"name":"orderNo", "value":$("#orderno").val()},
        {"name":"expensetype", "value":$("#expensetype").val()},
        {"name":"paymethod", "value":$("#paymethod").val()},
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
	$("#cancelpartyFormDiv").show();
	
	showObjectOnForm("cancelpartyForm", null);
    $("#ordernoHide").val(orderno);
	
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
		orderno: $("#ordernoHide").val(),
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
            		window.location.href = $("#baseUrl").val() + "OrderManage/OrgAbnormalOrderIndex";
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
    $("#ordernature").val("");
    $("#ordertype").val("");
    $("#belongleasecompany").select2("val", "");
    $("#organName").select2("val", "");
    $("#drivername").select2("val", "");
    $("#orderperson").select2("val", "");
    $("#paymentstatus").val("");
    $("#orderno").val("");
    $("#expensetype").val("");
    $("#paymethod").val("");
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
