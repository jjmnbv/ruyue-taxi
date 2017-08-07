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
                return {
                    userName: term
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
        userQueryParam: [{name: "type", value: "1"}, { name: "usetype", value: "1" }],
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
                    if(full.orderstatus == "1") {
                        html += '<button type="button" class="SSbtn blue" onclick="manualSendOrder(' +"'"+ full.orderno +"'"+ ')"><i class="fa fa-paste"></i>人工派单</button>';
                    }
                    html += '&nbsp; <button type="button" class="SSbtn red"  onclick="cancelOrder(' +"'"+ full.orderno +"'"+ ')"><i class="fa fa-times"></i> 取消</button>';
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
                "mRender": function (data, type, full) {
                	var html = "";
                	html += '<a href="' + $("#baseUrl").val() + 'OrderManage/PersonOrderPendingDetailIndex?orderno=' + full.orderno + '">' + full.orderno + '</a>';
                	return html;
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
	        {mDataProp: "undertime", sTitle: "下单时间", sClass: "center", sortable: true },
	        {mDataProp: "usetime", sTitle: "用车时间", sClass: "center", sortable: true },
	        {mDataProp: "pushnumber", sTitle: "订单推送人数", sClass: "center", sortable: true },
	        {
                "mDataProp": "SCDZ",
                "sClass": "center",
                "sTitle": "上车地址",
                "mRender": function (data, type, full) {
                	var address = "(" + full.oncity + ")" + full.onaddress;
                	return showToolTips(address, 10);
                }
            },
            {
                "mDataProp": "SCDZ",
                "sClass": "center",
                "sTitle": "下车地址",
                "mRender": function (data, type, full) {
                	var address = "(" + full.offcity + ")" + full.offaddress;
                	return showToolTips(address, 10);
                }
            }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 新增
 */
function add() {
	$("#editFormDiv").show();
	showObjectOnForm("editForm", null);
	
	var editForm = $("#editForm").validate();
	editForm.resetForm();
	editForm.reset();
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{"name":"orderNo", "value":$("#orderNo").val()},
		{"name":"orderType", "value":$("#ordertype").val()},
		{"name":"userId", "value":$("#orderperson").val() },
		{"name":"ordersource", "value":$("#ordersource").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 重置
 */
function reset() {
	$("#key").val("");
	dataGrid._fnReDraw();
}

/**
 * 修改
 * @param {} id
 */
function manualSendOrder(orderno) {
	// type=1 为人工派单,usetype=1为个人订单
	window.location.href = $("#baseUrl").val() + "OrderManage/ManualSendOrderIndex?orderno=" + orderno + "&type=1&usetype=1";
}

/**
 * 删除
 * @param {} id
 */
function cancelOrder(orderno) {
	var data = {orderno: orderno};
	$.get($("#baseUrl").val() + "OrderManage/GetOrgOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
		if (result && result.driverid) {
			Zconfirm("当前订单已有司机接单，您确认取消吗？",function(){cancelOrderPost(orderno, result.ordertype, result.usetype);});
		} else {
			Zconfirm("您确认取消吗？",function(){cancelOrderPost(orderno, result.ordertype, result.usetype);});
		}
	});
}

function cancelOrderPost(orderno, ordertype, usetype){
	var data = {orderno: orderno, ordertype: ordertype, usetype: usetype};
	$.get($("#baseUrl").val() + "OrderManage/CancelOrgOrder?datetime=" + new Date().getTime(), data, function (result) {
		var message = result.message == null ? result : result.message;
		if (result.status == "success") {
            toastr.success(message, "提示");
			dataGrid._fnReDraw();
		} else {
			toastr.error(message, "提示");
		}
	});
}

/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
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
	$("#orderno").val("");
	$("#ordertype").val("");
	$("#orderperson").select2("val", "");
	$("#ordersource").val("");
	search();
}
