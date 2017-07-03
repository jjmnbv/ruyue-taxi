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
                	vehicletype: "1",
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
        sAjaxSource: $("#baseUrl").val() + "TaxiOrderManage/GetOpTaxiOrderByQuery",
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
                	if(full.orderstatus == "2") {
                		html += '<button type="button" class="SSbtn red" onclick="cancelOrder(\'' + full.orderno + '\')"><i class="fa fa-paste"></i>取消</button>';
                		html += '&nbsp;';
                		html += '<button type="button" class="SSbtn green" onclick="changeDriver(\'' + full.orderno + '\')"><i class="fa fa-paste"></i>更换车辆</button>';
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
                	return '<a href="' + $("#baseUrl").val() + 'TaxiOrderManage/OrderDetailIndex?orderno=' + full.orderno + '">' + full.orderno + '</a>';
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
					case "5": return "接到乘客"; break;
					case "6": return "<span class='font_green'>服务中</span>"; break;
					case "9": return "待确费"; break;
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
		{"name":"orderNo", "value":$("#orderno").val()},
		{"name":"orderstatus", "value":$("#orderstatus").val()},
		{"name":"userId", "value":$("#orderperson").val()},
		{"name":"driverid", "value":$("#drivername").val()},
		{"name":"ordersource", "value":$("#ordersource").val()},
		// {"name":"leasescompanyid", "value":$("#leasescompanyid").val()},
        {"name":"belongleasecompany", "value":$("#leasescompanyid").val()},
		{"name":"minUseTime", "value":$("#minUseTime").val()},
		{"name":"maxUseTime", "value":$("#maxUseTime").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 取消订单
 * @param {} orderno
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
	try {
		var data = {orderno: orderno};
		$.get({
			url: $("#baseUrl").val() + "TaxiOrderManage/CancelOpTaxiOrder?datetime=" + new Date().getTime(), 
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
	window.location.href = $("#baseUrl").val() + "TaxiOrderManage/ManualSendTaxiOrderIndex?orderno=" + orderno + "&type=2";
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
	$("#orderperson").select2("val", "");
	$("#drivername").select2("val", "");
	$("#ordersource").val("");
	$("#leasescompanyid").select2("val", "");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	search();
}
