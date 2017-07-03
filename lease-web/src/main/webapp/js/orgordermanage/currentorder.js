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
	$("#organName").select2({
        placeholder: "机构名称",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "OrderManage/GetOrganByName",
            dataType: 'json',
            data: function (term, page) {
            	$(".datetimepicker").hide();
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
            	$(".datetimepicker").hide();
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
        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOrgOrderByQuery",
        userQueryParam: [{name: "type", value: "2"}, { name: "usetype", value: "0" }],
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
                   var htmlArr = [];
                   htmlArr.push("<a href=\"" + $("#baseUrl").val() + "OrderManage/OrgOrderDetailIndex?orderno=");
                   htmlArr.push(full.orderno);
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
                   	default: return "";
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
	        {mDataProp: "organname", sTitle: "所属机构", sClass: "center", sortable: true },
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
				mDataProp : "GXSJ",
				sTitle : "更新时间",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(null != full.updatetime && "" != full.updatetime) {
						return full.updatetime;
					} else {
						return "---";
					}
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
		{"name":"orderType", "value":$("#ordertype").val()},
		{"name":"organId", "value":$("#organName").val()},
		{"name":"userId", "value":$("#orderperson").val()},
		{"name":"orderNo", "value":$("#orderno").val()},
		{"name":"minUseTime", "value":$("#minUseTime").val()},
		{"name":"maxUseTime", "value":$("#maxUseTime").val()},
		{"name":"orderstatus", "value":$("#orderstatus").val()},
		{"name":"driverid", "value":$("#drivername").val()},
		{"name":"ordersource", "value":$("#ordersource").val()},
        {"name":"belongleasecompany", "value":$("#leasescompanyid").val()}
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
	window.location.href = $("#baseUrl").val() + "Order/ManualSendOrderIndex?orderno=" + orderno;
}

/**
 * 取消订单
 * @param {} orderno
 */
function cancelOrder(orderno) {
	var data = {orderno: orderno};
	$.get($("#baseUrl").val() + "OrderManage/GetOrgOrderByOrderno?datetime=" + new Date().getTime(), data, function(result) {
		if (result && result.driverid) {
			Zconfirm("当前订单已有司机接单，您确认取消吗？",function(){cancelOrderPost(orderno, result.ordertype, result.usetype);});
		} else {
			Zconfirm("您确认取消吗？",function(){cancelOrderPost(orderno, result.ordertype, result.usetype);});
		}
	});
	
}

function cancelOrderPost(orderno, ordertype, usetype){
	try {
		var data = {orderno: orderno, ordertype: ordertype, usetype: usetype};
		$.get({
			url: $("#baseUrl").val() + "OrderManage/CancelOrgOrder?datetime=" + new Date().getTime(), 
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
 * 保存
 */
function save() {
	var form = $("#currentOrderForm");
	if(!form.valid()) return;
	
	var formData = {
			orderreason: $("#orderreasonTextarea").val(),
			reviewperson: $("#reviewperson").val(),
			orderno: $("#orderno").val()
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
					$("#currentOrderFormDiv").hide();
	            	toastr.options.onHidden = function() {
	            		window.location.href = $("#baseUrl").val() + "OrderManage/OrgAbnormalOrderIndex";
	            	}
	            	toastr.success(message, "提示");
				} else {
	            	toastr.error(message, "提示");
				}
			}
		});
}


/**
 * 复核
 */
function recheck(orderno) {
	$("#orderno").val(orderno);
	$("#currentOrderFormDiv").show();
	
	showObjectOnForm("currentOrderForm", null);
	
	var editForm = $("#currentOrderForm").validate();
	editForm.resetForm();
	editForm.reset();
}
/**
 * 更换司机
 * @param {} orderno
 */
function changeDriver(orderno) {
	// type=2 为更换司机
	window.location.href = $("#baseUrl").val() + "OrderManage/ManualSendOrderIndex?orderno=" + orderno + "&type=2&usetype=0";
}

/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
}

/**
 * 表单校验
 */
function validateForm() {
	$("#currentOrderForm").validate({
		rules: {
			reviewperson: {required: true, minlength: 1},
			orderreasonTextarea: {required: true, maxlength: 200}
		},
		messages: {
			reviewperson: {required: "复核人不能为空！", minlength: "复核人不能为空！"},
			orderreasonTextarea: {required: "复核原因不能为空！", maxlength: "复核原因最大为200个字符！"}
		}
	})
}

/**
 * 初始化查询
 */
function initSearch() {
	$("#orderno").val("");
	$("#orderstatus").val("");
	$("#ordertype").val("");
	$("#orderperson").select2("val", "");
	$("#organName").select2("val", "");
	$("#drivername").select2("val", "");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	$("#ordersource").val("");
    $("#leasescompanyid").select2("val", "");
	search();
}
