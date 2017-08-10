var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	manualOrderdataGrid();
	validateForm();
});
/**
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "manualOrderdataGrid",
        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOrgOrderByQuery",
        userQueryParam: [{name: "type", value: "4"}, { name: "usetype", value: "1" }],
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
                    if(full.reviewedprice > 0 && full.actualpayamount > 0 && full.paymentstatus != "9") {
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
                   if(orderno == "CJ") {
                	   return "乘客端 | 因私";
                   } else if(orderno == "CZ") {
                	   return "租赁端 | 因私";
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
                   switch(full.paymentstatus) {
                   	case "0": return "<span class='font_red'>未支付</span>"; break;
					case "1": return "已支付"; break;
					default: return "/";
                   }
                }
            },
            {mDataProp: "reviewperson", sTitle: "复核方", sClass: "center", sortable: true,
            	"mRender": function(data, type, full) {
            		switch(full.reviewperson) {
            			case "1": return "司机"; break;
            			case "2": return "乘客"; break;
            			default: return "/";
            		}
            	}
            },
            {mDataProp: "price", sTitle: "差异金额(元)", sClass: "center", sortable: true,
            	"mRender": function(data, type, full) {
            		var shouldpayamount = full.shouldpayamount;
            		var actualpayamount = full.actualpayamount;
            		var orderamount = full.orderamount;
            		if(null == shouldpayamount) {
            			shouldpayamount = orderamount
            		}
            		if(null == actualpayamount) {
            			actualpayamount = orderamount;
            		}
            		return (shouldpayamount - actualpayamount).toFixed(1);
            	}
            },
            {mDataProp: "reviewtype", sTitle: "复核类型", sClass: "center", sortable: true,
                "mRender": function(data, type, full) {
                    if(full.reviewtype == 1) {
                        return "按里程时长";
                    } else if(full.reviewtype == 2) {
                        return "按固定金额";
                    } else {
                        return "/";
                    }
                }
            },
	        /*{mDataProp: "cost", sTitle: "原订单金额(元)", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return full.originalorderamount.toFixed(1);
	        	}
	        },
	        {mDataProp: "rawmileage", sTitle: "原里程(公里)", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return (full.rawmileage/1000).toFixed(1);
		        }
	        },
	        {mDataProp: "rawtimes", sTitle: "原计费时长(分钟)", sClass: "center",
	        	mRender: function(data, type, full) {
	        		if(null != full.pricecopy) {
	        			var pricecopy = JSON.parse(full.pricecopy);
	        			var timetype = pricecopy.timetype;
	        			if(timetype == 0) {
	        				var starttime = new Date(full.rawstarttime);
	        				var endtime = new Date(full.rawendtime);
	        				return Math.ceil((endtime - starttime)/(1000*60));
	        			} else {
	        				return full.rawtimes;
	        			}
	        		} else {
	        			return 0;
	        		}
	        	}
	        },
            {mDataProp: "YKSF", sTitle: "原空驶费（元）", sClass: "center",
                mRender: function(data, type, full) {
                    if(null != full.rawpricecopy) {
                        var pricecopy = JSON.parse(full.rawpricecopy);
                        return pricecopy.deadheadcost == null ? "0.0" : pricecopy.deadheadcost;
                    }
                    return "0.0";
                }
            },
            {mDataProp: "YYJF", sTitle: "原夜间费（元）", sClass: "center",
                mRender: function(data, type, full) {
                    if(null != full.rawpricecopy) {
                        var pricecopy = JSON.parse(full.rawpricecopy);
                        return pricecopy.nightcost == null ? "0.0" : pricecopy.nightcost;
                    }
                    return "0.0";
                }
            },
	        {mDataProp: "cost", "sClass": "center", "sTitle": "复核后订单金额(元)", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return full.reviewedprice.toFixed(1);
	        	}
	        },*/
            {mDataProp: "shouldpayamount", sTitle: "订单金额(元)", sClass: "center", sortable: true },
            {mDataProp: "SFJE", "sClass": "center", "sTitle": "实付金额(元)", sortable: true,
                "mRender": function(data, type, full) {
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
            {mDataProp: "actualamount", "sClass": "center", "sTitle": "优惠金额(元)", sortable: true,
                "mRender": function(data, type, full) {
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
	        {mDataProp: "mileage", "sClass": "center", "sTitle": "里程(公里)", sortable: true,
	        	"mRender": function(data, type, full) {
                    if(full.reviewtype == 2) {
                        return "/";
                    }
	        		return (full.reviewmileage/1000).toFixed(1);
		        }
	        },
	        {mDataProp: "reviewtime", "sClass": "center", "sTitle": "计费时长(分钟)", sortable: true,
	        	"mRender": function(data, type, full) {
                    if(full.reviewtype == 2) {
                        return "/";
                    }
	        		if(null != full.pricecopy) {
	        			var timetype = JSON.parse(full.pricecopy).timetype;
	        			if(timetype == 0) {
	        				return Math.ceil(full.reviewtimes/60);
	        			} else {
	        				return full.reviewcounttimes;
	        			}
	        		} else {
	        			return 0;
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
            /*{mDataProp: "FHHKSF", sTitle: "复核后空驶费（元）", sClass: "center",
                mRender: function(data, type, full) {
                    if(null != full.pricecopy) {
                        var pricecopy = JSON.parse(full.pricecopy);
                        return pricecopy.deadheadcost == null ? "0.0" : pricecopy.deadheadcost;
                    }
                    return "0.0";
                }
            },
            {mDataProp: "FHHYJF", sTitle: "复核后夜间费（元）", sClass: "center",
                mRender: function(data, type, full) {
                    if(null != full.pricecopy) {
                        var pricecopy = JSON.parse(full.pricecopy);
                        return pricecopy.nightcost == null ? "0.0" : pricecopy.nightcost;
                    }
                    return "0.0";
                }
            },*/
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
        {"name":"reviewperson", "value":$("#reviewperson").val()},
        {"name":"orderType", "value":$("#ordertype").val()},
        {"name":"paymentstatus", "value":$("#paymentstatus").val()},
        {"name":"orderNo", "value":$("#orderno").val()}
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
                    window.location.href = $("#baseUrl").val() + "OrderManage/PersonAbnormalOrderIndex";
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
    $("#reviewperson").val("");
    $("#ordertype").val("");
    $("#paymentstatus").val("");
    $("#orderno").val("");
	search();
}

/**
 * 导出订单数据
 */
function exportOrder() {
	var orderNo = $("#orderno").val();
	var orderType = $("#ordertype").val();
	var paymentstatus = $("#paymentstatus").val();
	var reviewperson = $("#reviewperson").val();
	var ordersource = $("#ordersource").val();
    var leasescompanyid =  $("#leasescompanyid").val();
	var type = "4";
	var usetype = "1";
	window.location.href = $("#baseUrl").val()
			+ "OrderManage/ExportOrder?orderNo=" + orderNo + "&orderType="
			+ orderType + "&paymentstatus=" + paymentstatus + "&reviewperson=" + reviewperson
			+ "&ordersource=" + ordersource + "&type=" + type + "&usetype="
			+ usetype + "&belongleasecompany=" + leasescompanyid;
	$("#orderno").blur();
}
