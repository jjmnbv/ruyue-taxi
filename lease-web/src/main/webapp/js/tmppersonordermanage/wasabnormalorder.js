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
/**
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "manualOrderdataGrid",
        sAjaxSource: $("#baseUrl").val() + "TmpOrderManage/GetOrgOrderByQuery",
        userQueryParam: [{name: "type", value: "4"}, { name: "usetype", value: "1" }],
        iLeftColumn: 3,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无服务订单信息"
        },
        columns: [
            {
                "mDataProp": "DDLY",
                "sClass": "center",
                "sTitle": "订单来源",
                "mRender": function (data, type, full) {
                   var orderno = full.orderno.substring(0, 2);
                   if(orderno == "BC") {
                	   return "乘客端 | 因公";
                   } else if(orderno == "CJ") {
                	   return "乘客端 | 因私";
                   } else if(orderno == "BZ") {
                	   return "租赁端 | 因公";
                   } else if(orderno == "CZ") {
                	   return "租赁端 | 因私";
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
                   var htmlArr = [];
                   htmlArr.push("<a href=\"" + $("#baseUrl").val() + "OrderManage/PersonOrderDetailIndex?orderno=");
                   htmlArr.push(full.orderno + "&tmp=tmp");
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
                   	default: return "";
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
            			case "2": return "下单人"; break;
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
	        {mDataProp: "cost", sTitle: "原订单金额(元)", sClass: "center", sortable: true,
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
	        },
	        {mDataProp: "mileage", "sClass": "center", "sTitle": "复核后里程(公里)", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return (full.reviewmileage/1000).toFixed(1);
		        }
	        },
	        {mDataProp: "reviewtime", "sClass": "center", "sTitle": "复核后计费时长(分钟)", sortable: true,
	        	"mRender": function(data, type, full) {
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
            {mDataProp: "FHHKSF", sTitle: "复核后空驶费（元）", sClass: "center",
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
            },
            {mDataProp: "shortname", sTitle: "服务车企", sClass: "center", sortable: true}
        ]
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
		{"name":"paymentstatus", "value":$("#paymentstatus").val()},
		{"name":"reviewperson", "value":$("#reviewperson").val()},
		{"name":"ordersource", "value":$("#ordersource").val()},
        {"name":"belongleasecompany", "value":$("#leasescompanyid").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

function review(orderno) {
	window.location.href = $("#baseUrl").val() + "OrderManage/OrgOrderReviewIndex?orderno=" + orderno + "&usetype=1";
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
            		window.location.href = $("#baseUrl").val() + "OrderManage/PersonAbnormalOrderIndex";
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
	$("#paymentstatus").val("");
	$("#reviewperson").val("");
	$("#ordersource").val("");
    $("#leasescompanyid").select2("val", "");
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
