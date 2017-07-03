/**
 * 页面默认加载处理
 */
$(document).ready(function() {
    initOrder();
	validateForm();
});

function validateForm() {
	$("#orderReivewForm").validate({
		rules : {
			mileageprice : {
				required : true,
				number : true,
				limitNum : [4, 1]
			},
			reviewperson : {
				required : true
			},
			reasonTextarea : {
				required : true,
				maxlength : 100
			}
		},
		messages : {
			mileageprice : {
				required : "请输入行程费用",
				number : "行程费用必须是数字",
				limitNum : "行程费用整数最大4位数，小数最大1位数"
			},
			reviewperson : {
				required : "请选择复核方"
			},
			reasonTextarea : {
				required : "请输入处理意见",
				maxlength : "处理意见最大为100个字符"
			}
		}
	})
}

/**
 * 初始化订单数据
 */
function initOrder() {
	$("#orderno").text(orderObj.orderno);
	var data = {orderno: orderObj.orderno};
	$.get($("#baseUrl").val() + "TaxiOrderManage/GetOpTaxiOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
		if (result) {
			orderObj.orderInfo = result;
			reviewRecordDataGridInit();
		}
	});
}

/**
 * 复核表格数据初始化
 * @param {} order
 */
function initReviewTable(order) {
	//原订单金额
	var orderamount = order.orderamount;
	if(null != order.actualpayamount && order.actualpayamount > 0) {
		orderamount = order.actualpayamount;
	}
	$("#xcfy").text(orderamount); //行程费
	$("#fwlc").text((order.mileage/1000).toFixed(1)); //服务里程
	//服务时长
	var starttime = new Date(order.starttime);
	var endtime = new Date(order.endtime);
	var times = Math.ceil((endtime - starttime)/1000/60);
	$("#fwsc").text(times);
	$("#fwkssj").text(timeStamp2String(order.starttime)); //服务开始时间
	$("#fwjssj").text(timeStamp2String(order.endtime)); //服务结束时间
}

/**
 * 复议记录表格数据初始化
 */
function reviewRecordDataGridInit() {
	var gridObj = {
		id: "reviewRecordDataGrid",
		lengthChange: false,
        sAjaxSource: $("#baseUrl").val() + "TaxiOrderManage/GetOpTaxiOrderReviewByQuery",
        userQueryParam: [{name: "orderno", value: orderObj.orderno}],
        iLeftColumn: 1,
        scrollX: true,
        columns: [
            {mDataProp: "rownum", sTitle: "序号", sClass: "center", sortable: true },
            {mDataProp: "reviewedprice", sTitle: "行程费用(元)", sClass: "center", sortable: true,
            	"mRender": function(data, type, full) {
            		return full.reviewedprice.toFixed(1);
            	}
            },
	        {mDataProp: "price", sTitle: "差异金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "mileage", sTitle: "服务里程(公里)", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return (full.mileage/1000).toFixed(1);
	        	}
	        },
 	        {
				mDataProp : "times",
				sTitle : "服务时长(分钟)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					return Math.ceil(full.times / 60);
				}
			},
	        {
                "mDataProp": "STARTTIME",
                "sClass": "center",
                "sTitle": "服务开始时间",
                "mRender": function (data, type, full) {
                	return timeStamp2String(full.starttime);
                }
            },
            {
                "mDataProp": "ENDTIME",
                "sClass": "center",
                "sTitle": "服务结束时间",
                "mRender": function (data, type, full) {
                	return timeStamp2String(full.endtime);
                }
            },
	        {mDataProp: "reviewperson", sTitle: "提出复核方", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
					if(full.reviewperson == "1") {
						return "司机";
					} else if(full.reviewperson == "2") {
						return "下单人";
					} else {
						return "/";
					}
	        	}
	        },
	        {mDataProp: "reason", sTitle: "申请原因", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return showToolTips(full.reason, 15);
	        	}
	        },
	        {mDataProp: "opinion", sTitle: "处理意见", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return showToolTips(full.opinion, 15);
	        	}
	        },
	        {
                "mDataProp": "REVIEWTIME",
                "sClass": "center",
                "sTitle": "复核时间",
                "mRender": function (data, type, full) {
                	return timeStamp2String(full.reviewtime);
                }
            },
	        {mDataProp: "operatorname", sTitle: "复核人", sClass: "center", sortable: true }
        ],
        userHandle: function(oSettings, result) {
        	if(null != result && null != result.aaData && result.aaData.length > 0) {
        		var orderReview = result.aaData[0];
        		$("#xcfy").text(orderReview.reviewedprice);
        		$("#fwlc").text((orderReview.mileage/1000).toFixed(1));
        		$("#fwsc").text(Math.ceil(orderReview.times/60));
        		$("#fwkssj").text(timeStamp2String(orderReview.starttime));
        		$("#fwjssj").text(timeStamp2String(orderReview.endtime));
        	} else {
        		initReviewTable(orderObj.orderInfo);
        	}
        	$("#beforeprice").val(orderObj.orderInfo.actualpayamount);
        }
    };
    
	reviewRecordDataGrid = renderGrid(gridObj);
}

/**
 * 复核
 */
function review() {
	$("#cyjets").text("");
	$("#orderReivewFormDiv").show();
	
	showObjectOnForm("orderReivewForm", null);
	
	var editForm = $("#orderReivewForm").validate();
	editForm.resetForm();
	editForm.reset();
	
	$("#reviewperson").val(orderObj.orderInfo.reviewperson);
}

/**
 * 提交复核操作
 */
function reviewPost() {
	var form = $("#orderReivewForm");
	var reviewedprice = $("#mileageprice").val();
	var opinion = $("#reasonTextarea").val();
	if(!form.valid()) {
		if(null == reviewedprice || "" == reviewedprice) {
			toastr.error("请输入行程费用", "提示");
			return;
		}
		if(null == opinion || "" == opinion) {
			toastr.error("请输入处理意见", "提示");
			return;
		}
		return;
	}
	var formData = {
		orderno: orderObj.orderInfo.orderno,
		reviewedprice: $("#mileageprice").val(),
		reviewperson: $("#reviewperson").val(),
		opinion: $("#reasonTextarea").val()
	}
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "TaxiOrderManage/OpTaxiOrderReview",
		data: JSON.stringify(formData),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				$("#orderReivewFormDiv").hide();
				toastr.options.onHidden = function() {
            		window.location.href = $("#baseUrl").val() + "TaxiOrderManage/WasabnormalOrderIndex";
            	}
            	toastr.success(message, "提示");
			} else {
            	toastr.error(message, "提示");
			}
		}
	});
}

/**
 * 取消
 */
function canel() {
	$("#orderReivewFormDiv").hide();
}

/**
 * 小数验证
 */
$.validator.addMethod("limitNum", function(value, element, param) {
	var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
	return rep.test(value);
}, "数字格式不正确");

function getPrice() {
	//未支付时，不展示信息
	var paymentstatus = $("#paymentstatus").val();
	if(null == paymentstatus || "" == paymentstatus || paymentstatus == "0") {
		$("#cyjets").text("");
		return;
	}
	
	var beforemileageprice = $("#beforemileageprice").val();
	var mileageprice = $("#mileageprice").val();
	//行程费用为空时不计算价格
	if(null == mileageprice || "" == mileageprice) {
		$("#cyjets").text("");
		return;
	}
	var balance = (beforemileageprice - mileageprice).toFixed(1);
	if(balance > 0) {
		$("#cyjets").text("退给乘客" + balance + "元");
	} else {
		$("#cyjets").text("");
	}
}

