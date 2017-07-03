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
            url: $("#baseUrl").val() + "OrderManage/GetPeUser",
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
        sAjaxSource: $("#baseUrl").val() + "TaxiOrderManage/GetOpTaxiOrderByQuery",
        userQueryParam: [{name: "type", value: "1"}],
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
                "mRender": function (data, type, full) {
                	return '<a href="' + $("#baseUrl").val() + 'TaxiOrderManage/OrderDetailIndex?orderno=' + full.orderno + '">' + full.orderno + '</a>';
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
	        {mDataProp: "schedulefee", sTitle: "调度费用(元)", sClass: "center", sortable: true },
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
                "mDataProp": "XCDZ",
                "sClass": "center",
                "sTitle": "下车地址",
                "mRender": function (data, type, full) {
                	var address = "(" + full.offcity + ')' + full.offaddress;
                	return showToolTips(address, 10);
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
		{"name":"userId", "value":$("#orderperson").val()},
		{"name":"minUseTime", "value":$("#minUseTime").val()},
		{"name":"maxUseTime", "value":$("#maxUseTime").val()},
		{"name":"ordersource", "value":$("#ordersource").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 人工派单
 * @param {} id
 */
function manualSendOrder(orderno) {
	// type=1 为人工派单
	window.location.href = $("#baseUrl").val() + "TaxiOrderManage/ManualSendTaxiOrderIndex?orderno=" + orderno + "&type=1";
}

/**
 * 取消订单
 * @param {} id
 */
function cancelOrder(orderno) {
	var data = {orderno: orderno};
	$.get($("#baseUrl").val() + "TaxiOrderManage/GetOpTaxiOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
		if (result && result.driverid) {
			Zconfirm("当前订单已有司机接单，你确认取消订单吗？",function(){cancelOrderPost(orderno);});
		} else {
			Zconfirm("你确认取消订单吗？",function(){cancelOrderPost(orderno);});
		}
	});
}

function cancelOrderPost(orderno){
	var data = {orderno: orderno};
	$.get($("#baseUrl").val() + "TaxiOrderManage/CancelOpTaxiOrder?datetime=" + new Date().getTime(), data, function (result) {
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
	$("#orderperson").select2("val", "");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	$("#ordersource").val("");
	search();
}
