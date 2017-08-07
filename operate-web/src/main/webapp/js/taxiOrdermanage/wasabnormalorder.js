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
}

/**
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "manualOrderdataGrid",
        sAjaxSource: $("#baseUrl").val() + "TaxiOrderManage/GetOpTaxiOrderByQuery",
        userQueryParam: [{name: "type", value: "4"}],
        iLeftColumn: 3,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无服务订单信息"
        },
        columns: [
        	/*{
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 100,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                	orderObj.orderInfo = full;
                	var html = [];
                    html.push("<button type=\"button\" class=\"SSbtn red\" onclick=\"applyReview('" + full.orderno + "')\"><i class=\"fa fa-paste\"></i>申请复核</button>");
                    return html.join("");
                }
            },*/
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
                "mDataProp": "DDZT",
                "sClass": "center",
                "sTitle": "订单状态",
                "mRender": function (data, type, full) {
                	switch(full.paymentstatus) {
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
            {mDataProp: "paymentmethod", sTitle: "付款方式", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		switch(full.paymentmethod) {
	        			case "0": return "在线支付"; break;
	        			case "1": return "线下付现"; break;
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
	        {mDataProp: "YXCFY", sTitle: "原行程费用(元)", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return full.originalorderamount.toFixed(1);
	        	}
	        },
	        {mDataProp: "FHHXCFY", "sClass": "center", "sTitle": "复核后行程费用(元)", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return full.reviewedprice.toFixed(1);
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
		{"name":"reviewperson", "value":$("#reviewperson").val()},
		{"name":"paymentmethod", "value":$("#paymentmethod").val()},
		{"name":"ordersource", "value":$("#ordersource").val()},
		// {"name":"leasescompanyid", "value":$("#leasescompanyid").val()}
        {"name":"belongleasecompany", "value":$("#leasescompanyid").val()}
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
	$("#paymentstatus").val("");
	$("#reviewperson").val("");
	$("#paymentmethod").val("");
	$("#ordersource").val("");
	$("#leasescompanyid").select2("val", "");
	search();
}

/**
 * 导出订单数据
 */
function exportOrder() {
	var orderNo = $("#orderno").val();
	var paymentstatus = $("#paymentstatus").val();
	var reviewperson = $("#reviewperson").val();
	var paymentmethod = $("#paymentmethod").val();
	var ordersource = $("#ordersource").val();
	var leasescompanyid = $("#leasescompanyid").val();
	var type = "4";
	window.location.href = $("#baseUrl").val()
			+ "TaxiOrderManage/ExportOrder?orderNo=" + orderNo + "&paymentstatus="
			+ paymentstatus + "&reviewperson=" + reviewperson
			+ "&paymentmethod=" + paymentmethod + "&ordersource=" + ordersource
			+ "&leasescompanyid=" + leasescompanyid + "&type=" + type + "&belongleasecompany=" + leasescompanyid;
	$("#orderno").blur();
}
