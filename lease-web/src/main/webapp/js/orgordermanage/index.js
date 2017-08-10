var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	var menuLi = $(window.top.document).find(".side_bar li");
	if(null != menuLi && menuLi.length > 0) {
		$(menuLi).each(function() {
			if($(this).children().hasClass("dingdanguanli")) {
				$(this).addClass("current");
			} else {
				$(this).removeClass("current");
			}
		});
	}
	
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
                    userName: term,
                    organId: $("#organName").val()
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
 * 表单校验
 */
function validateForm() {
    $("#cancelpartyForm").validate({
        rules : {
            dutyparty : {
                required : true
            },
            cancelreason : {
                required : true
            }
        },
        messages : {
            dutyparty : {
                required : "请选择责任方"
            },
            cancelreason : {
                required : "请选择取消原因"
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
        userQueryParam: [{name: "type", value: "1"}, { "name": "usetype", "value": "0" }],
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
                    html += '<button type="button" class="SSbtn blue" onclick="manualSendOrder(' +"'"+ full.orderno +"'"+ ')"><i class="fa fa-paste"></i>人工派单</button>';
                    html += '&nbsp; <button type="button" class="SSbtn red"  onclick="cancelOrder(' +"'"+ full.orderno +"','" + full.ordertype + "','" + full.usetype + "'"+ ')"><i class="fa fa-times"></i> 取消</button>';
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
                "mRender": function (data, type, full) {
                    return "<a href='" + $("#baseUrl").val() + "OrderManage/OrgOrderPendingDetailIndex?orderno="
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
                "mDataProp": "undertime",
                "sClass": "center",
                "sTitle": "下单时间",
                "mRender": function (data, type, full) {
                    if(null == full.undertime) {
                        return "/";
                    } else {
                        return dateFtt(full.undertime, "yyyy/MM/dd hh:mm");
                    }
                }
            },
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
	        {mDataProp: "pushnumber", sTitle: "订单推送人数", sClass: "center", sortable: true },
	        {
                "mDataProp": "SXCDZ",
                "sClass": "center",
                "sTitle": "上下车地址",
                "mRender": function (data, type, full) {
                    var onaddress = "(" + full.oncity + ")" + full.onaddress;
                    var offaddress = "(" + full.offcity + ")" + full.offaddress;
                    return showToolTips(onaddress, 12, undefined, offaddress);
                }
            }
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
		{"name":"organId", "value":$("#organName").val()},
		{"name":"userId", "value":$("#orderperson").val()},
		{"name":"ordersource", "value":$("#ordersource").val()},
        {"name":"minUseTime", "value":$("#minUseTime").val()},
        {"name":"maxUseTime", "value":$("#maxUseTime").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 人工派单
 */
function manualSendOrder(orderno) {
	// type=1 为人工派单,usetype=0为因公订单
	window.location.href = $("#baseUrl").val() + "OrderManage/ManualSendOrderIndex?orderno=" + orderno + "&type=1&usetype=0";
}

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
	$("#organName").select2("val", "");
	$("#ordertype").val("");
	$("#orderperson").select2("val", "");
	$("#ordersource").val("");
    $("#minUseTime").val("");
    $("#maxUseTime").val("");
	search();
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
    } else if(cancelreason == 5) {
        $("#dutyparty").val(3);
    } else if(cancelreason == 6) {
        $("#dutyparty").val(4);
    }
    showCancelreason();
    $("#cancelreason").val(cancelreason);
}

