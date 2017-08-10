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
            	$(".datetimepicker").hide();
                return {
                    userName: term,
                    organId: $("#organName").val()
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
                    type: "3",
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
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "manualOrderdataGrid",
        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOrgOrderByQuery",
        userQueryParam: [{name: "type", value: "3"}, { name: "usetype", value: "0" }],
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
                "sWidth": 150,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += "<button type='button' class='SSbtn red' onclick='review(\"" + full.orderno + "\")'><i class='fa fa-paste'></i>复核</button>";
                    html += "&nbsp;";
                    html += "<button type='button' class='SSbtn blue' onclick='reject(\"" + full.orderno + "\")'><i class='fa fa-paste'></i>不受理</button>";
                    return html;
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
                "mDataProp": "LX",
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
                        case "0": return "未支付"; break;
                        case "1": return "已支付"; break;
						case "2": return "结算中"; break;
						case "3": return "已结算"; break;
						case "4": return "未结算"; break;
						default: return "/";
                   }
                }
            },
            {
                "mDataProp": "reviewperson",
                "sClass": "center",
                "sTitle": "复核方",
                "mRender": function (data, type, full) {
                	switch(full.reviewperson) {
		               	case "1": return "司机"; break;
						case "2": return "乘客"; break;
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
				mDataProp : "orderamount",
				sTitle : "订单金额(元)",
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
	        		if(null != full.reviewid && "" != full.reviewid) {
	        			return (full.reviewmileage/1000).toFixed(1);
	        		}
	        		return (full.mileage/1000).toFixed(1);
	        	}
	        },
	        {mDataProp: "JFSC", sTitle: "计费时长(分钟)", sClass: "center",
	        	mRender: function(data, type, full) {
	        		if(null != full.pricecopy) {
	        			var pricecopy = JSON.parse(full.pricecopy);
	        			if(pricecopy.timetype == 1) { //低速用时
	        				if(null != full.reviewid && "" != full.reviewid) {
	        					return full.reviewcounttimes;
	        				}
	        				return pricecopy.slowtimes;
	        			} else if(pricecopy.timetype == 0) { //总用时
	        				if(null != full.reviewid && "" != full.reviewid) {
	        					return Math.ceil(full.reviewtimes/60);
	        				}
	        				var starttime = new Date(full.starttime);
	        				var endtime = new Date(full.endtime);
	        				return Math.ceil((endtime - starttime)/(1000*60));
	        			}
	        		}
	        		return "0";
	        	}
	        },
            /*{mDataProp: "KSF", sTitle: "空驶费（元）", sClass: "center",
                mRender: function(data, type, full) {
                    if(null != full.pricecopy) {
                        var pricecopy = JSON.parse(full.pricecopy);
                        return pricecopy.deadheadcost == null ? "0.0" : pricecopy.deadheadcost;
                    }
                    return "0.0";
                }
            },
            {mDataProp: "YJF", sTitle: "夜间费（元）", sClass: "center",
                mRender: function(data, type, full) {
                    if(null != full.pricecopy) {
                        var pricecopy = JSON.parse(full.pricecopy);
                        return pricecopy.nightcost == null ? "0.0" : pricecopy.nightcost;
                    }
                    return "0.0";
                }
            },*/
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
        {"name":"userId", "value":$("#orderperson").val()},
        {"name":"driverid", "value":$("#drivername").val()},
        {"name":"reviewperson", "value":$("#reviewperson").val()},
        {"name":"orderNo", "value":$("#orderno").val()},
        {"name":"paymentstatus", "value":$("#paymentstatus").val()},
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
	window.location.href = $("#baseUrl").val() + "OrderManage/OrgOrderReviewIndex?orderno=" + orderno + "&usetype=0";
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
		url: $("#baseUrl").val() + "OrderManage/OrgOrderReject",
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
	if(null != text && text.length > 10) {
		return text.substr(0, 10) + "...";
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
    $("#orderperson").select2("val", "");
    $("#drivername").select2("val", "");
    $("#reviewperson").val("");
    $("#orderno").val("");
    $("#paymentstatus").val("");
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
	var driverid = $("#drivername").val();
	var userId = $("#orderperson").val();
	var organId = $("#organName").val();
	var reviewperson = $("#reviewperson").val();
	var minUseTime = $("#minUseTime").val();
	var maxUseTime = $("#maxUseTime").val();
	var ordersource = $("#ordersource").val();
    var leasescompanyid =  $("#leasescompanyid").val();
	var type = "3";
	var usetype = "0";
	window.location.href = $("#baseUrl").val()
			+ "OrderManage/ExportOrder?orderNo=" + orderNo + "&orderType="
			+ orderType + "&driverid=" + driverid + "&userId=" + userId
			+ "&organId=" + organId + "&reviewperson=" + reviewperson
			+ "&minUseTime=" + minUseTime + "&maxUseTime=" + maxUseTime
			+ "&ordersource=" + ordersource + "&type=" + type + "&usetype="
			+ usetype + "&belongleasecompany=" + leasescompanyid;
	$("#orderno").blur();
	$("#minUseTime").blur();
	$("#maxUseTime").blur();
}
