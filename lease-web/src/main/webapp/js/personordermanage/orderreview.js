/**
 * 页面默认加载处理
 */
$(document).ready(function() {
	initForm();
    initOrder();
    showByReviewtype();
	validateForm();

    $("input[name='reviewtype']").change(function () {
        $("#reviewtype").val($("input[name='reviewtype']:checked").val());
        showByReviewtype();
        var editForm = $("#orderReivewForm").validate();
        editForm.resetForm();
    });
});

function showByReviewtype() {
    $("#reviewedprice").val("");
    $("#starttime").val("");
    $("#endtime").val("");
    $("#mileage").val("");
    $("#counttimes").val("");
    var reviewtype = $("#reviewtype").val();
    if(reviewtype == 1) { //按里程时长
        $("#reviewedpriceDiv").hide();
        $("#timeDiv").show();
        $("#mileageDiv").show();
        var timetype = $("#timetype").val();
        if(timetype == 0) {
            $("#counttimesDiv").hide();
        } else {
            $("#counttimesDiv").show();
        }
    } else {
        $("#reviewedpriceDiv").show();
        $("#timeDiv").hide();
        $("#mileageDiv").hide();
        $("#counttimesDiv").hide();
    }
    $("#cyjets").text("");
}

function validateForm() {
    $("#orderReivewForm").validate({
        rules : {
            endtime : {
                timecheck : true,
                timeslot: true,
                timesinter: true
            },
            mileage : {
                required : true,
                number : true,
                limitNum : [3, 2]
            },
            counttimes : {
                required : true,
                number : true,
                limitNum : [4, 1],
                checkcounttimes : true
            },
            reviewperson : {
                required : true
            },
            reasonTextarea : {
                required : true,
                maxlength : 100
            },
            reviewedprice : {
                required : true,
                number : true,
                limitNum : [4, 1]
            }
        },
        messages : {
            endtime : {
                timecheck : "请选择服务开始时间和服务结束时间",
                timeslot : "服务开始时间不能大于服务结束时间",
                timesinter: "所输入的时间长度应低于24小时"
            },
            mileage : {
                required : "请输入服务里程",
                number : "服务里程必须是数字",
                limitNum : "服务里程整数最大3位数，小数最大2位数"
            },
            counttimes : {
                required : "请输入计费时长",
                number : "计费时长必须是数字",
                limitNum : "计费时长整数最大4位数，小数最大1位数",
                checkcounttimes : "计费时长不能大于服务时长"
            },
            reviewperson : {
                required : "请选择复核方"
            },
            reasonTextarea : {
                required : "请输入处理意见",
                maxlength : "处理意见最大为100个字符"
            },
            reviewedprice : {
                required : "请输入复核后订单金额",
                number : "复核后金额必须是数字",
                limitNum : "复核后金额整数最大4位数，小数最大1位数"
            }
        }
    })
}

/**
 * 页面初始化
 */
function initForm() {
	$('.searchDate').datetimepicker({
        format: "yyyy/mm/dd hh:ii", //选择日期后，文本框显示的日期格式
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
    }).on('changeDate', function(ev) {
    	getPrice();
    });
}

/**
 * 初始化订单数据
 */
function initOrder() {
	var data = {orderno: orderObj.orderno};
	$.get($("#baseUrl").val() + "OrderManage/GetOrgOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
		if (result) {
			orderObj.orderInfo = result;
			var ordertype = result.ordertype;
			switch(ordertype) {
				case "1": ordertype = "约车"; break;
				case "2": ordertype = "接机"; break;
				case "3": ordertype = "送机"; break;
				default: ordertype = "";
			}
			$("#orderTitle").text("待复核订单，" + ordertype + "订单号：" + result.orderno);
			reviewRecordDataGridInit();
		} else {
		}
	});
}

/**
 * 复核表格数据初始化
 * @param {} order
 */
function initReviewTable(order) {
	var startprice = order.startprice; //起步价
	var rangeprice = order.rangeprice; //里程单价
	var timeprice = order.timeprice; //时间单价
	var slowtimes = order.slowtimes; //低速用时
	var timetype = order.timetype; //时间补贴类型
	var times = order.times; //服务时长
	var mileage = order.mileage; //服务里程
	var rangecost = order.rangecost; //里程费用
	var timecost = order.timecost; //时间补贴费用
	var cost = order.cost;
	
	$("#ddje").text(order.orderamount); //订单金额
	$("#cyje").text(order.price); //差异金额
	$("#fwlc").text(mileage); //服务里程
	$("#fwsc").text(times); //服务时长
	$("#fwkssj").text(timeStamp2String(order.starttime)); //服务开始时间
	$("#fwjssj").text(timeStamp2String(order.endtime)); //服务结束时间
	//计费时长
	if(timetype == 0) {
		$("#ljsj").text(times);
	} else if(timetype == 1) {
		$("#ljsj").text(slowtimes);
	} else {
		$("#ljsj").text("0");
	}
	$("#lcf").text(rangecost); //里程费用
	$("#sjbt").text(timecost); //时间补贴费用
}

/**
 * 复议记录表格数据初始化
 */
function reviewRecordDataGridInit() {
	var gridObj = {
        id: "reviewRecordDataGrid",
        lengthChange: false,
        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOrgOrderReviewByQuery",
        userQueryParam: [{name: "orderNo", value: orderObj.orderno}, {name: "reviewstatus", value: "1"}],
        iLeftColumn: 1,
        scrollX: true,
        columns: [
            {mDataProp: "rownum", sTitle: "序号", sClass: "center", sortable: true },
            {mDataProp: "raworderamount", sTitle: "复核前订单金额(元)", sClass: "center", sortable: true,
                "mRender": function(data, type, full) {
                    return full.raworderamount.toFixed(1);
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
            {mDataProp: "price", sTitle: "差异金额(元)", sClass: "center", sortable: true },
            {mDataProp: "reviewedprice", sTitle: "复核后订单金额(元)", sClass: "center", sortable: true,
                "mRender": function(data, type, full) {
                    return full.reviewedprice.toFixed(1);
                }
            },
            {mDataProp: "mileage", sTitle: "服务里程(公里)", sClass: "center", sortable: true,
                "mRender": function(data, type, full) {
                    if(full.reviewtype == 2) {
                        return "/";
                    }
                    var mileage = (full.mileage/1000).toFixed(1);
                    if(full.mileage != full.rawmileage) {
                        return "<span class='font_red'>" + mileage + "</span>";
                    } else {
                        return mileage;
                    }
                }
            },
            {mDataProp: "times", sTitle: "服务时长(分钟)", sClass: "center", sortable: true,
                "mRender": function(data, type, full) {
                    if(full.reviewtype == 2) {
                        return "/";
                    }
                    var starttime = new Date();
                    if(null != full.starttime && "" != full.starttime) {
                        starttime = new Date(full.starttime);
                    }
                    var endtime = new Date();
                    if(null != full.endtime && "" != full.endtime) {
                        endtime = new Date(full.endtime);
                    }
                    return Math.ceil((endtime - starttime)/1000/60);
                }
            },
            {
                "mDataProp": "STARTTIME",
                "sClass": "center",
                "sTitle": "服务开始时间",
                "mRender": function (data, type, full) {
                    if(full.reviewtype == 2) {
                        return "/";
                    }
                    if(full.starttime != full.rawstarttime) {
                        return "<span class='font_red'>" + timeStamp2String(full.starttime) + "</span>";
                    }
                    return timeStamp2String(full.starttime);
                }
            },
            {
                "mDataProp": "ENDTIME",
                "sClass": "center",
                "sTitle": "服务结束时间",
                "mRender": function (data, type, full) {
                    if(full.reviewtype == 2) {
                        return "/";
                    }
                    if(full.endtime != full.rawendtime) {
                        return "<span class='font_red'>" + timeStamp2String(full.endtime) + "</span>";
                    }
                    return timeStamp2String(full.endtime);
                }
            },
            {mDataProp: "counttimes", sTitle: "计费时长(分钟)", sClass: "center", sortable: true,
                "mRender": function(data, type, full) {
                    if(full.reviewtype == 2) {
                        return "/";
                    }
                    if(null != full.pricecopy) {
                        var pricecopy = JSON.parse(full.pricecopy);
                        var timetype = pricecopy.timetype;
                        if(timetype == 0) { //总用时
                            var times = Math.ceil(full.times/60); //复核后服务时长
                            var rawstarttime = new Date(full.rawstarttime);
                            var rawendtime = new Date(full.rawendtime);
                            var rawtimes = Math.ceil((rawendtime - rawstarttime)/1000/60); //复核前服务时长
                            if(times != rawtimes) {
                                return "<span class='font_red'>" + times + "</span>";
                            } else {
                                return times;
                            }
                        } else if(timetype == 1) { //低速用时
                            if(full.counttimes != full.rawtimes) {
                                return "<span class='font_red'>" + full.counttimes + "</span>";
                            } else {
                                return full.counttimes;
                            }
                        }
                    } else {
                        return "0";
                    }
                }
            },
            {mDataProp: "timesubsidies", sTitle: "时间补贴(元)", sClass: "center", sortable: true,
                "mRender": function(data, type, full) {
                    if(full.reviewtype == 2) {
                        return "/";
                    }
                    if(null != full.pricecopy) {
                        var pricecopy = JSON.parse(full.pricecopy);
                        var timetype = pricecopy.timetype;
                        var timeprice = pricecopy.timeprice;
                        if(timetype == 0) {
                            var starttime = new Date();
                            if(null != full.starttime && "" != full.starttime) {
                                starttime = new Date(full.starttime);
                            }
                            var endtime = new Date();
                            if(null != full.endtime && "" != full.endtime) {
                                endtime = new Date(full.endtime);
                            }
                            var times = Math.ceil((endtime - starttime)/1000/60);
                            return (timeprice * times).toFixed(1);
                        } else if(timetype == 1) {
                            var slowtimes = full.counttimes;
                            return (timeprice * slowtimes).toFixed(1);
                        } else {
                            return "0";
                        }
                    } else {
                        return "0";
                    }
                }
            },
            {mDataProp: "mileageprices", sTitle: "里程费(元)", sClass: "center", sortable: true,
                "mRender": function(data, type, full) {
                    if(full.reviewtype == 2) {
                        return "/";
                    }
                    if(null != full.pricecopy) {
                        var pricecopy = JSON.parse(full.pricecopy);
                        var mileage = (full.mileage/1000).toFixed(1);
                        return (pricecopy.rangeprice * mileage).toFixed(1);
                    } else {
                        return "0";
                    }
                }
            },
            {mDataProp: "reviewperson", sTitle: "提出复核方", sClass: "center", sortable: true,
                "mRender": function(data, type, full) {
                    if(full.reviewperson == "1") {
                        return "司机";
                    } else if(full.reviewperson == "2") {
                        return "乘客";
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
                    return dateFtt(full.reviewtime, "yyyy/MM/dd hh:mm");
                }
            },
            {mDataProp: "operatorname", sTitle: "复核人", sClass: "center", sortable: true }
        ],
        userHandle: function(oSettings, result) {
            if(null != result && null != result.aaData && result.aaData.length > 0) {
                var orderReview = result.aaData[0];
                $("#ddje").text(orderReview.reviewedprice);
                $("#cyje").text(orderReview.price);
                $("#fwlc").text((orderReview.mileage/1000).toFixed(1));
                $("#fwsc").text(Math.ceil(orderReview.times/60));
                $("#fwkssj").text(dateFtt(orderReview.starttime, "yyyy/MM/dd hh:mm:ss"));
                $("#fwjssj").text(dateFtt(orderReview.endtime, "yyyy/MM/dd hh:mm:ss"));
                var timetype = orderObj.orderInfo.timetype;
                //计费时长
                if(timetype == 0) {
                    $("#ljsj").text(Math.ceil(orderReview.times/60));
                } else if(timetype == 1) {
                    $("#ljsj").text(orderReview.counttimes);
                } else {
                    $("#ljsj").text("0");
                }
                $("#sjbt").text(orderReview.timesubsidies);
                $("#lcf").text(orderReview.mileageprices);
            } else {
                initReviewTable(orderObj.orderInfo);
            }
            $("#beforeprice").val(orderObj.orderInfo.actualpayamount);
            $("#pricecopy").val(orderObj.orderInfo.pricecopy); //计价副本
            $("#paymentstatus").val(orderObj.orderInfo.paymentstatus); //支付状态
            //如果是总用时，隐藏计费时长文本框
            var timetype = orderObj.orderInfo.timetype;
            if(timetype == 0) {
                $("#counttimesDiv").hide();
            } else {
                $("#counttimesDiv").show();
            }
            $("#timetype").val(timetype);
        }
    };
    
	reviewRecordDataGrid = renderGrid(gridObj);
}

/**
 * 复核
 */
function review() {
    $("#cyjets").text("");
    $("input[name=reviewtype]").get(0).checked = true;
    $("#reviewtype").val(1);
    $("#reviewedprice").val("");
    $("#starttime").val("");
    $("#endtime").val("");
    $("#mileage").val("");
    $("#counttimes").val("");
    $("#reasonTextarea").val("");
    showByReviewtype();

    $("#orderReivewFormDiv").show();

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
	if(!form.valid()) return;
	
	var startTime = $("#starttime").val();
	var endTime = $("#endtime").val();
	
	var formData = {
        orderno: orderObj.orderInfo.orderno,
        mileage: $("#mileage").val(),
        starttime: startTime,
        endtime: endTime,
        counttimes: $("#counttimes").val(),
        reviewperson: $("#reviewperson").val(),
        opinion: $("#reasonTextarea").val(),
        reviewtype: $("#reviewtype").val(),
        reviewedprice: $("#reviewedprice").val()
	}
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "OrderManage/OrgOrderReview",
		data: JSON.stringify(formData),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				$("#orderReivewFormDiv").hide();
				toastr.options.onHidden = function() {
            		window.location.href = $("#baseUrl").val() + "OrderManage/PersonWasAbnormalOrderIndex";
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
 * 自定义有效时间校验
 */
$.validator.addMethod("timecheck", function(value, element, params) {
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if(null == starttime || "" == starttime || null == endtime || "" == endtime) {
		return false;
	}
	return true;
}, "开始时间和结束时间不能为空")
$.validator.addMethod("timeslot", function(value, element, params) {
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if(null != starttime && "" != starttime && null != endtime && "" != endtime) {
		return starttime <= endtime;
	}
	return true;
}, "开始时间不能大于结束时间")
$.validator.addMethod("timesinter", function(value, element, params) {
	var starttime = new Date($("#starttime").val());
	var endtime = new Date($("#endtime").val());
	var timesinter = endtime - starttime;
	return timesinter <= 24 * 60 * 60 * 1000;
}, "所输入的时间长度应低于24小时")

/**
 * 小数验证
 */
$.validator.addMethod("limitNum", function(value, element, param) {
	var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
	return rep.test(value);
}, "数字格式不正确");

$.validator.addMethod("checkcounttimes", function(value, element, param) {
	if($("#timetype").val() == "0") {
		return true;
	}
	var starttime = new Date($("#starttime").val());
	var endtime = new Date($("#endtime").val());
	var times = (endtime - starttime)/(1000*60);
	var counttimes = $("#counttimes").val();
	if(counttimes > times) {
		return false;
	}
	return true;
}, "计费时长不能大于服务时长");

function getPrice() {
    //未支付时，不展示信息
    var paymentstatus = $("#paymentstatus").val();
    if(null == paymentstatus || "" == paymentstatus || paymentstatus == "0") {
        $("#cyjets").text("");
        return;
    }

    var beforeprice = $("#beforeprice").val();
    var reviewtype = $("#reviewtype").val();
    if(reviewtype == 2) { //按固定金额
        var reviewedprice = $("#reviewedprice").val();
        var balance = (parseFloat(beforeprice) - parseFloat(reviewedprice)).toFixed(1);
        if(balance > 0) {
            $("#cyjets").text("退给乘客" + balance + "元");
        } else {
            $("#cyjets").text("");
        }
        return;
    }

    var mileage = $("#mileage").val();
    var pricecopy = $("#pricecopy").val();
    var starttime = new Date($("#starttime").val());
    var endtime = new Date($("#endtime").val());
    var times = Math.ceil((endtime - starttime)/(1000*60));
    var counttimes = $("#counttimes").val();

    //计价副本为空时不计算价格
    if(null == pricecopy) {
        $("#cyjets").text("");
        return;
    }
    var jsonPricecopy = JSON.parse(pricecopy);
    var timetype = jsonPricecopy.timetype;

    //里程为空时不计算价格
    if(null == mileage || "" == mileage) {
        $("#cyjets").text("");
        return;
    }

    if(isNaN(starttime) || isNaN(endtime) || (timetype == "1" && (null == counttimes || "" == counttimes))) {
        $("#cyjets").text("");
        return;
    }

    jsonPricecopy.starttime = $("#starttime").val();
    jsonPricecopy.mileage = mileage * 1000;
    jsonPricecopy.times = times * 60;
    jsonPricecopy.slowtimes = counttimes;
    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "OrderManage/GetOrdercost",
        data: JSON.stringify(jsonPricecopy),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            var price = result.cost;
            var balance = (parseFloat(beforeprice) - parseFloat(price)).toFixed(1);
            if(balance > 0) {
                $("#cyjets").text("退给乘客" + balance + "元");
            } else {
                $("#cyjets").text("");
            }
        }
    });
}

