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
                	vehicletype: "0",
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
            url: $("#baseUrl").val() + "OrderManage/GetBelongCompanySelect",
            dataType: 'json',
            data: function (term, page) {
            	$(".datetimepicker").hide();
                return {
                    belongleasecompany: term,
                    type: "3",
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
                    return "<a href='" + $("#baseUrl").val() + "OrderManage/OrderDetailIndex?orderno="
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
            {mDataProp: "paymentstatus", sTitle: "订单状态", sClass: "center", sortable: true,"mRender": function(data, type, full) {
            	switch(full.paymentstatus) {
	               	case "0": return "<span class='font_red'>未支付</span>"; break;
					case "1": return "已支付"; break;
					default: return "/";
               }
            } },
	        {mDataProp: "reviewperson", sTitle: "复核方", sClass: "center", sortable: true,
            	"mRender": function(data, type, full) {
            		switch(full.reviewperson) {
            			case "1": return "司机"; break;
            			case "2": return "乘客"; break;
            			default: return "/";
            		}
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
                    if(full.paymentstatus == "1") {
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
                    if(full.paymentstatus == "1") {
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
                "sClass": "center",
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
        {"name":"orderType", "value":$("#ordertype").val()},
        {"name":"belongleasecompany", "value":$("#belongleasecompany").val()},
        {"name":"reviewperson", "value":$("#reviewperson").val()},
        {"name":"driverid", "value":$("#drivername").val()},
        {"name":"userId", "value":$("#orderperson").val()},
        {"name":"paymentstatus", "value":$("#paymentstatus").val()},
        {"name":"orderNo", "value":$("#orderno").val()},
        {"name":"minUseTime", "value":$("#minUseTime").val()},
        {"name":"maxUseTime", "value":$("#maxUseTime").val()},
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 复核
 * @param {} orderno
 */
function review(orderno) {
	window.location.href = $("#baseUrl").val() + "OrderManage/OpOrderReviewIndex?orderno=" + orderno;
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
		url: $("#baseUrl").val() + "OrderManage/OpOrderReject",
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
 * 初始化查询
 */
function initSearch() {
    $("#ordersource").val("");
    $("#ordertype").val("");
    $("#belongleasecompany").select2("val", "");
    $("#reviewperson").val("");
    $("#drivername").select2("val", "");
    $("#orderperson").select2("val", "");
    $("#paymentstatus").val("");
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
	var orderType = $("#ordertype").val();
	var paymentstatus = $("#paymentstatus").val();
	var driverid = $("#drivername").val();
	var userId = $("#orderperson").val();
	var reviewperson = $("#reviewperson").val();
	var minUseTime = $("#minUseTime").val();
	var maxUseTime = $("#maxUseTime").val();
	var ordersource = $("#ordersource").val();
	var leasescompanyid = $("#leasescompanyid").val();
	var type = "3";
	window.location.href = $("#baseUrl").val()
			+ "OrderManage/ExportOrder?orderNo=" + orderNo + "&orderType="
			+ orderType + "&paymentstatus=" + paymentstatus + "&driverid="
			+ driverid + "&userId=" + userId + "&reviewperson=" + reviewperson
			+ "&minUseTime=" + minUseTime + "&maxUseTime=" + maxUseTime
			+ "&ordersource=" + ordersource + "&leasescompanyid="
			+ leasescompanyid + "&type=" + type + "&belongleasecompany=" + leasescompanyid;
	$("#orderno").blur();
	$("#minUseTime").blur();
	$("#maxUseTime").blur();
}
