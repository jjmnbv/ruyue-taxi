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
        sAjaxSource: $("#baseUrl").val() + "TmpOrderManage/GetOpOrderByQuery",
        userQueryParam: [{name: "type", value: "2"}],
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
                		html += '<button type="button" class="SSbtn green" onclick="changeDriver(\'' + full.orderno + '\')"><i class="fa fa-paste"></i>更换司机</button>';
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
                   var htmlArr = [];
                   htmlArr.push("<a href=\"" + $("#baseUrl").val() + "OrderManage/OrderDetailIndex?orderno=");
                   htmlArr.push(full.orderno + "&tmp=tmp");
                   htmlArr.push("\">");
                   htmlArr.push(full.orderno);
                   htmlArr.push("</a>");
                   return htmlArr.join("");
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
                   	case "0": return "待接单"; break;
					case "1": return "待人工派单"; break;
					case "2": return "<span class='font_red'>待出发</span>"; break;
					case "3": return "已出发"; break;
					case "4": return "已抵达"; break;
					case "5": return "接到乘客"; break;
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
	        {mDataProp: "usetime", sTitle: "用车时间", sClass: "center", sortable: true },
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
		{"name":"orderType", "value":$("#ordertype").val()},
		{"name":"userId", "value":$("#orderperson").val()},
		{"name":"orderNo", "value":$("#orderno").val()},
		{"name":"minUseTime", "value":$("#minUseTime").val()},
		{"name":"maxUseTime", "value":$("#maxUseTime").val()},
		{"name":"orderstatus", "value":$("#orderstatus").val()},
		{"name":"driverid", "value":$("#drivername").val()},
		{"name":"ordersource", "value":$("#ordersource").val()},
		// {"name":"leasescompanyid", "value":$("#leasescompanyid").val()},
        {"name":"belongleasecompany", "value":$("#leasescompanyid").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 修改
 * @param {} id
 */
function manualSendOrder(orderno) {
	window.location.href = $("#baseUrl").val() + "Order/ManualSendOrderIndex?orderno=" + orderno;
}

/**
 * 取消订单
 * @param {} orderno
 */
function cancelOrder(orderno) {
	var data = {orderno: orderno};
	$.get($("#baseUrl").val() + "OrderManage/GetOpOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
		if (result && result.driverid) {
			Zconfirm("当前订单已有司机接单，你确认取消订单吗？",function(){cancelOrderPost(orderno, result.ordertype);});
		} else {
			Zconfirm("你确认取消订单吗？",function(){cancelOrderPost(orderno, result.ordertype);});
		}
	});
	
}

function cancelOrderPost(orderno, ordertype){
	try {
		var data = {orderno: orderno, ordertype: ordertype};
		$.get({
			url: $("#baseUrl").val() + "OrderManage/CancelOpOrder?datetime=" + new Date().getTime(), 
			data: data, 
			success: function (result) {
				var message = result.message == null ? result : result.message;
				if (result.status == "success") {
		            toastr.success(message, "提示");
					dataGrid._fnReDraw();
				} else {
					toastr.error(message, "提示");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				console.info("网络异常");
			}
		});
	} catch(e) {
		
	}
	
}

/**
 * 更换司机
 * @param {} orderno
 */
function changeDriver(orderno) {
	// type=2 为更换司机
	window.location.href = $("#baseUrl").val() + "OrderManage/ManualSendOrderIndex?orderno=" + orderno + "&type=2&tmp=tmp";
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
	$("#orderstatus").val("");
	$("#ordertype").val("");
	$("#orderperson").select2("val", "");
	$("#drivername").select2("val", "");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	$("#ordersource").val("");
	$("#leasescompanyid").select2("val", "");
	search();
}
