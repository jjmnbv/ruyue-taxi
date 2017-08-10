var dataGrid;

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
            url: $("#baseUrl").val() + "OrderManage/GetOrgUser",
            dataType: 'json',
            data: function (term, page) {
            	$(".datetimepicker").hide();
                return {
                	queryText: term
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
                    type: "2",
                    usetype: "1"
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
        userQueryParam: [{name: "type", value: "2"}, { name: "usetype", value: "1" }],
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
                "sWidth": 180,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = '';
                    //服务中之前的状态才显示取消和更换司机按钮
                    if(full.orderstatus < 6) {
                        html += '<button type="button" class="SSbtn red" onclick="cancelOrder(\'' + full.orderno + '\')"><i class="fa fa-paste"></i>取消</button>';
                        html += '&nbsp;';
                        html += '<button type="button" class="SSbtn green" onclick="changeDriver(\'' + full.orderno + '\')"><i class="fa fa-paste"></i>更换司机</button>';
                    } else {
                        html += '<button type="button" class="SSbtn red" onclick="endOrder(\'' + full.orderno + '\')"><i class="fa fa-paste"></i>结束订单</button>';
                    }
                    return html;
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
                "mRender": function (data, type, full) {
                    return "<a href='" + $("#baseUrl").val() + "OrderManage/PersonOrderDetailIndex?orderno="
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
                   switch(full.orderstatus) {
					case "2": return "<span class='font_red'>待出发</span>"; break;
					case "3": return "已出发"; break;
					case "4": return "已抵达"; break;
					case "6": return "<span class='font_green'>服务中</span>"; break;
					default: return "/";
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
	        {mDataProp: "drivername", sTitle: "司机信息", sClass: "center", sortable: true },
            {
                "mDataProp": "usetime",
                "sClass": "center",
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
                "mDataProp": "SXCDZ",
                "sClass": "center",
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
        ]
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
        {"name":"orderNo", "value":$("#orderno").val()},
        {"name":"driverid", "value":$("#drivername").val()},
        {"name":"userId", "value":$("#orderperson").val()},
        {"name":"orderstatus", "value":$("#orderstatus").val()},
        {"name":"minUseTime", "value":$("#minUseTime").val()},
        {"name":"maxUseTime", "value":$("#maxUseTime").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 更换司机
 * @param {} orderno
 */
function changeDriver(orderno) {
	// type=2 为更换司机
	window.location.href = $("#baseUrl").val() + "OrderManage/ManualSendOrderIndex?orderno=" + orderno + "&type=2&usetype=1";
}

/**
 * 初始化查询
 */
function initSearch() {
    $("#ordersource").val("");
    $("#ordernature").val("");
    $("#ordertype").val("");
    $("#belongleasecompany").select2("val", "");
    $("#orderno").val("");
    $("#drivername").select2("val", "");
    $("#orderperson").select2("val", "");
    $("#orderstatus").val("");
    $("#minUseTime").val("");
    $("#maxUseTime").val("");
	search();
}

/**
 * 取消订单
 * @param {} orderno
 */
/**
 * 取消订单
 */
function cancelOrder(orderno, ordertype, usetype) {
    $("#ordernoHide").val(orderno);
    $("#ordertypeHide").val(ordertype);
    $("#usetypeHide").val(usetype);

    showObjectOnForm("cancelpartyForm", null);

    //查询取消费用
    var data = {
        orderno: orderno,
        ordertype: ordertype,
        usetype: usetype
    };
    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "OrderManage/GetCancelPriceDetail",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            if (result.status == 0) {
                $("#identifyingHide").val(result.identifying);
                var pricereason = result.pricereason;
                if(pricereason == 5 || pricereason == 6 || pricereason == 7) {
                    $("#cancelDetail").html(getCancelShowTable(result));
                }
                $("#cancelpartyFormDiv").show();
            } else {
                toastr.error(result.message, "提示");
            }
        }
    });
}

/**
 * 取消费用说明
 * @param result
 * @returns {string}
 */
function getCancelShowTable(result) {
    var ordercancelrule = result.ordercancelrule;
    var html = '<table>';
    if(result.pricereason == 5) {
        html += '<tr><td colspan="4" style="text-align: left;">说明</td></tr>';
        html += '<tr><td colspan="4" style="text-align: left;">乘客需支付取消费用<span class="font_red">' + result.price + '元</span></td></tr>';
        html += '<tr><td>取消时差(分钟)</td><td>免责取消时限(分钟)</td><td>司机迟到时长(分钟)</td><td>迟到免责时限(分钟)</td></tr>';
        html += '<tr><td>' + result.canceltimelag + '</td><td>' + ordercancelrule.cancelcount + '</td><td>' + result.driverlate + '</td><td>' + ordercancelrule.latecount + '</td></tr>';
    } else if(result.pricereason == 6 || result.pricereason == 7) {
        html += '<tr><td colspan="3" style="text-align: left;">说明</td></tr>';
        html += '<tr><td colspan="3" style="text-align: left;">乘客需支付取消费用<span class="font_red">' + result.price + '</span></td></tr>';
        html += '<tr><td>取消时差(分钟)</td><td>免责取消时限(分钟)</td><td>司机是否抵达</td></tr>';
        html += '<tr><td>' + result.canceltimelag + '</td><td>' + ordercancelrule.cancelcount + '</td>';
        if(result.driverarraival == true) {
            html += '<td>正常抵达</td></tr>';
        } else {
            html += '<td>未抵达</td></tr>';
        }
    }
    html += '</table>';
    return html;
}

function save(){
    var form = $("#cancelpartyForm");
    if(!form.valid()) {
        return;
    }

    var orderno = $("#ordernoHide").val();
    var ordertype = $("#ordertypeHide").val();
    var usetype = $("#usetypeHide").val();
    var identifying = $("#identifyingHide").val();

    var data = {
        orderno: orderno,
        ordertype: ordertype,
        usetype: usetype,
        identifying: identifying,
        dutyparty: $("#dutyparty").val(),
        cancelreason: $("#cancelreason").val()
    };

    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "OrderManage/CancelOrgOrder",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            if (result.status == "success") {
                toastr.success(message, "提示");
                dataGrid._fnReDraw();
            } else {
                toastr.error(message, "提示");
            }
        }
    });
}

/**
 * 根据责任方对应显示取消原因
 */
function showCancelreason() {
    var dutyparty = $("#dutyparty").val();

    var html = '';
    if(dutyparty == 1) {
        html += '<option value="">请选择</option>';
        html += '<option value="1">不再需要用车</option>';
        html += '<option value="2">乘客迟到违约</option>';
    } else if(dutyparty == 2) {
        html += '<option value="">请选择</option>';
        html += '<option value="3">司机迟到违约</option>';
        html += '<option value="4">司机不愿接乘客</option>';
    } else if(dutyparty == 3) {
        html += '<option value="5">业务操作错误</option>';
    } else if(dutyparty == 4) {
        html += '<option value="6">暂停供车服务</option>';
    } else {
        html += '<option value="">请选择</option>';
        html += '<option value="1">不再需要用车</option>';
        html += '<option value="5">业务操作错误</option>';
        html += '<option value="6">暂停供车服务</option>';
    }
    $("#cancelreason").html(html);
}

/**
 * 根据取消原因对应显示责任方
 */
function showDutyparty() {
    var cancelreason = $("#cancelreason").val();
    if(cancelreason == 1 || cancelreason == 2) {
        $("#dutyparty").val(1);
    } else if(cancelreason == 3 || cancelreason == 4) {
        $("#dutyparty").val(2);
    } else if(cancelreason == 5) {
        $("#dutyparty").val(3);
    } else if(cancelreason == 6) {
        $("#dutyparty").val(4);
    }
    showCancelreason();
    $("#cancelreason").val(cancelreason);
}

/**
 * 取消
 */
function canel() {
    $("#cancelpartyFormDiv").hide();
}

/**
 * 结束订单
 */
function endOrder(orderno) {
    $.ajax({
        type: "GET",
        url: $("#baseUrl").val() + "OrderManage/EndOrder/" + orderno + "?date=" + new Date(),
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            if (result.status == 0) {
                toastr.success("操作成功", "提示");
                dataGrid._fnReDraw();
            } else {
                toastr.error(message, "提示");
            }
        }
    });
}
