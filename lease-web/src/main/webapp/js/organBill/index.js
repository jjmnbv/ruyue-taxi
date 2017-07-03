var dataGrid;
var dataGridHis;
var dataGridWaitCheck;
var dataGridManual;
//选择订单
var map = {};
var checknum = 0;
var checkamount = parseFloat("0").toFixed(1);
var pagemap = {};
/**
 * 页面初始化
 */
$(function () {
	//initTab();
	initGrid();
	//initGrid2();
	initSelectOrgan();
	validateForm();
	dateFormat();
	initGridManual();
});

/**
 * tab初始化
 */
function initTab() {
	var tabnum = $("#tabnum").val();
	if (tabnum == "") {
		tabnum = 0;
	}
	$(".tabmenu>li:eq("+tabnum+")").addClass("on").siblings().removeClass("on");
	$(".tabbox>li:eq("+tabnum+")").show().siblings().hide();

	$(".tabmenu>li").click(function() {
        $(this).addClass("on").siblings().removeClass("on");
        var n=$(this).index();
        $("#tabnum").val(n);
        $(".tabbox>li:eq("+n+")").show().siblings().hide();
    });
}

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OrganBill/GetCurOrganBillByQuery?billClass=0",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无未处理账单信息"
        },
        columns: [
	        /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "billState", sTitle: "账单状态", sClass: "center", visible: false},
	        {mDataProp: "source", sTitle: "账单来源", sClass: "center", visible: false},
	        {mDataProp: "organId", sTitle: "机构Id", sClass: "center", visible: false},*/

	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 230,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    if (full.billState == '2') {//待核对
                    	if (full.orderCount > 0) {
                    		html += '&nbsp; <button type="button" class="SSbtn orange"  onclick="searchWaitCheck(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>核对账单</button>';
                    	} else {
                    		html += '&nbsp; <button type="button" class="SSbtn orange"  onclick="searchBillDetail(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>查看详情</button>';
                    	}
                    } else if (full.billState == '3' || full.billState == '4') {//待机构核对  待机构付款
                    	html += '&nbsp; <button type="button" class="SSbtn orange"  onclick="searchBillDetail(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>查看详情</button>';
                    } else if (full.billState == '5') {//机构退回账单
                    	html += '&nbsp; <button type="button" class="SSbtn orange"  onclick="reCreateBill(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>重新生成账单</button>';
                    	html += '&nbsp; <button type="button" class="SSbtn orange" onclick="searchBillDetail(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>查看详情</button>';
                    } else if (full.billState == '6') {//机构已付款
                    	//html += '<button type="button" class="SSbtn orange"  onclick="confirmAccount(' +"'"+ full.id + "','" + full.organId + "','" + full.money +"'"+ ')"><i class="fa fa-times"></i>确认收款</button>';
                    	html += '&nbsp; <button type="button" class="SSbtn orange" onclick="searchBillDetail(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>查看详情</button>';
                    }
                    
                    html += '&nbsp; <button type="button" class="SSbtn orange" onclick="searchLog(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>日志</button>';
                    if (full.billState == '2') {//待核对
                    	html += '&nbsp; <button type="button" class="SSbtn orange" onclick="cancellation(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>作废</button>';
                    } 
                    return html;
                }
            },

	        {mDataProp: "id", sTitle: "账单编号", sClass: "center", sortable: true },
	        {mDataProp: "sourceName", sTitle: "账单来源", sClass: "center", sortable: true },
	        {mDataProp: "name", sTitle: "账单名称", sClass: "center", sortable: true },
	        {mDataProp: "shortName", sTitle: "机构", sClass: "center", sortable: true },
	        //{mDataProp: "billStateName", sTitle: "账单状态", sClass: "center", sortable: true },
	        {
				mDataProp : "billStateName",
				sTitle : "账单状态",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var html = "";
	                    if (full.billState == '2') {//待核对
	                    	html += '<span class="font_green">' + full.billStateName + '</span>' ;
	                    } else if (full.billState == '5') {//机构退回账单
	                    	html += '<span class="font_red">' + full.billStateName + '</span>' ;
	                    } else {
	                    	html += '<span>' + full.billStateName + '</span>' ;
	                    }
						return html;
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "money", sTitle: "账单金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "operationTime", sTitle: "最后更新时间", sClass: "center", sortable: true },
	        {mDataProp: "createTime", sTitle: "账单生成时间", sClass: "center", sortable: true } 
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 表格初始化
 */
function initGrid2() {
	var gridObj = {
		id: "dataGridHis",
        sAjaxSource: "OrganBill/GetCurOrganBillByQuery?billClass=1",
        iLeftColumn: 1,
        scrollX: true,
        columns: [
	        /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "billState", sTitle: "账单状态", sClass: "center", visible: false},
	        {mDataProp: "source", sTitle: "账单来源", sClass: "center", visible: false},
	        {mDataProp: "organId", sTitle: "机构Id", sClass: "center", visible: false},*/

	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 300,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '<button type="button" class="SSbtn orange"  onclick="searchBillDetail(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>查看详情</button>';
                    html += '&nbsp; <button type="button" class="SSbtn orange" onclick="searchLog(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>日志</button>';
                    
                    return html;
                }
            },

	        {mDataProp: "id", sTitle: "账单编号", sClass: "center", sortable: true },
	        {mDataProp: "sourceName", sTitle: "账单来源", sClass: "center", sortable: true },
	        {mDataProp: "name", sTitle: "账单名称", sClass: "center", sortable: true },
	        {mDataProp: "shortName", sTitle: "机构", sClass: "center", sortable: true },
	        //{mDataProp: "billStateName", sTitle: "账单状态", sClass: "center", sortable: true },
	        {
				mDataProp : "billStateName",
				sTitle : "账单状态",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var html = "";
	                    if (full.billState == '8') {//已作废
	                    	html += '<span class="font_red">' + full.billStateName + '</span>' ;
	                    } else {
	                    	html += '<span>' + full.billStateName + '</span>' ;
	                    }
						return html;
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "money", sTitle: "账单金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "operationTime", sTitle: "最后更新时间", sClass: "center", sortable: true },
	        {mDataProp: "createTime", sTitle: "账单生成时间", sClass: "center", sortable: true } 
        ]
    };
    
	dataGridHis = renderGrid(gridObj);
}

/**
 * 搜索下拉框
 */
function initSelectOrgan() {
	$("#organId").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrganBill/SelectOrganList",
			dataType : 'json',
			data : function(term, page) {
				$(".datetimepicker").hide();
				return {
					fullName: term
				};
			},
			results : function(data, page) {
				return {
					results: getDataVar(data)
				};
			}
		}
	});
	
	/*$("#organIdHis").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrganBill/SelectOrganList",
			dataType : 'json',
			data : function(term, page) {
				return {
					fullName: term
				};
			},
			results : function(data, page) {
				return {
					results: getDataVar(data)
				};
			}
		}
	});*/
	
	$("#organIdManual").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : false,
		ajax : {
			url : "OrganBill/SelectOrganList",
			dataType : 'json',
			data : function(term, page) {
				$(".datetimepicker").hide();
				return {
					fullName: term
				};
			},
			results : function(data, page) {
				return {
					results: getDataVar(data)
				};
			}
		}
		
	});

}

/**
 * 对结果分组
 */
function getDataVar(data) {
	var datavar = [];
	var initials = "";
	var children = [];
	var dataend = [];
	for (var i=0;i<data.length;i++) {
		if (initials != data[i].initials) {
			
			if (dataend.children) {
				datavar.push(dataend);
			}

			initials = data[i].initials;
			children = [];
			children.push(data[i]);
			
			dataend = [];
			dataend.text = initials;
			dataend.disabled = "disabled";
			dataend.children = children;
		} else {
			children.push(data[i]);
			dataend.children = children;
		}

	}
	
	if (dataend.children) {
		datavar.push(dataend);
	}
	
	return datavar;
}

/**
 * 手动生成账单初始值
 */
function initSelect() {
	var url = "OrganBill/SelectOrganList" + "?datetime=" + new Date().getTime();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: url,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (json) {
			if (json.length > 0) {
				$("#organIdManual").select2("data", {
					id:json[0].id,
					text:json[0].text
				}); 
			}
		}
	});
}

/**
 * 表单校验
 */
function validateForm() {
	$("#cancellationForm").validate({
		rules: {
			remark: {required: true, maxlength: 200}
		},
		messages: {
			remark: {required: "请填写作废缘由", maxlength: "最大长度不能超过200个字符"}
		}
	})
	
	$("#manualForm").validate({
		rules: {
			name: {required: true, maxlength: 20},
			remarkManual: {maxlength: 30}
		},
		messages: {
			name: {required: "请填写账单名称", maxlength: "最大长度不能超过20个字符"},
			remarkManual: {maxlength: "最大长度不能超过30个字符"}
		}
	})
}

var isclear = false;
/**
 * 当前账单查询
 */
function search() {
	
	$("#organIdExport").val($("#organId").val());
	$("#startTimeExport").val($("#startTime").val());
	$("#endTimeExport").val($("#endTime").val());
	$("#billStateExport").val($("#billState").val());
	
	var conditionArr = [
		{ "name": "organId", "value": $("#organId").val() },
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "billState", "value": $("#billState").val() }
	];
	if (isclear) {
		dataGrid.fnSearch(conditionArr,"暂无未处理账单信息");
	} else {
		dataGrid.fnSearch(conditionArr);
	}
}

/**
 * 历史账单查询
 */
function searchHis() {
	
	$("#organIdHisExport").val($("#organIdHis").val());
	$("#startTimeHisExport").val($("#startTimeHis").val());
	$("#endTimeHisExport").val($("#endTimeHis").val());
	$("#billStateHisExport").val($("#billStateHis").val());
	
	var conditionArr = [
		{ "name": "organId", "value": $("#organIdHis").val() },
		{ "name": "startTime", "value": $("#startTimeHis").val() },
		{ "name": "endTime", "value": $("#endTimeHis").val() },
		{ "name": "billState", "value": $("#billStateHis").val() }
	];
	dataGridHis.fnSearch(conditionArr);
}

function dateFormat() {
	$('.searchDate').datetimepicker({
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        clearBtn: true
    });
}

/**
 * 毫秒转日期
 * 
 * @param data
 * @returns {String}
 */
function changeToDate(data) {
	var myDate = new Date(data.replace(/-/g,"/"));
	var month = "";
	var date = "";
	var hours = "";
	var minutes = "";
	var change = "";
	change += myDate.getFullYear() + "/";

	if (myDate.getMonth() < 9) {
		month = "0" + (myDate.getMonth() + 1);
	} else {
		month = (myDate.getMonth() + 1);
	}
	change += month + "/";
	if (myDate.getDate() < 10) {
		date = "0" + myDate.getDate();
	} else {
		date = myDate.getDate();
	}
	change += date;
	if (myDate.getHours() < 10) {
		hours = "0" + myDate.getHours();
	} else {
		hours = myDate.getHours();
	}
	change += " " + hours;
	if (myDate.getMinutes() < 10) {
		minutes = "0" + myDate.getMinutes();
	} else {
		minutes = myDate.getMinutes();
	}
	change += ":" + minutes;
	return change;
}

/**
 * 查看详情
 */
function searchBillDetail(id) {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganBill/BillDetail/" + id;
}

/**
 * 重新生成账单
 */
function reCreateBill(id) {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganBill/ReCreateBill/" + id;
}

/**
 * 日志
 */
function searchLog(id) {
	var url = "OrganBill/GetOrganBillStateById/" + id + "?datetime=" + new Date().getTime();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: url,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (json) {
			var html="";
			for(var i=0;i<json.length;i++) {
				html += '<li><div class="j_time">' + json[i].operationTime + '</div><div class="j_text"><b>' + json[i].billState + '</b></div></li>';
				if (json[i].comment) {
					html += '<li><div class="j_timeno"></div><div class="j_text">说明：' + json[i].comment + '</div></li>';
				} else {
					html += '<li><div class="j_timeno"></div><div class="j_text">' + json[i].comment + '</div></li>';
				}				
			}
			$("#jindu").html(html);
			$("#logDiv").show();
		}
	});
}

/**
 * 核对账单
 */
function searchWaitCheck(id) {
	$("#billsId").val(id);
	var url = "OrganBill/BillCheckDetail/" + id + "?datetime=" + new Date().getTime();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: url,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (json) {
			$("#billName").text(json.name);
			$("#billShortName").text(json.shortName);
			$("#billId").text(json.id);
			if(json.remark == null) {
				$("#billRemark").text("");
			} else {
				$("#billRemark").text(json.remark);
			}
			$("#billMoney").text(json.money);
			
			$("#waitCheckFormDiv").show();
			if (!dataGridWaitCheck) {
				initGridWaitCheck(id);
			}else{
				var conditionArr = [
    				{ "name": "billsId", "value": id }
    			];
				dataGridWaitCheck.fnSearch(conditionArr);
			}
			
			$("#dataGridWaitCheck_wrapper .col-sm-6").remove();
		}
	});
}

/**
 * 核对账单表格初始化
 */
function initGridWaitCheck(id) {
	var gridObj = {
		id: "dataGridWaitCheck",
        sAjaxSource: "OrganBill/GetOrgOrderByQuery/" + id,
        /*iLeftColumn: 1,*/
        scrollX: true,
        language: {
        	sEmptyTable: "没有查询到未结算订单数据"
        },
        columns: [     
            {mDataProp: "endtime", sTitle: "订单结束时间", sClass: "center", visible: false },
            {
				mDataProp : "endtime",
				sTitle : "订单结束时间",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return changeToDate(data);
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "ordertype", sTitle: "类型", sClass: "center", sortable: true },
	        //{mDataProp: "orderno", sTitle: "订单号", sClass: "center", sortable: true },
	        {
                //自定义操作列
                "mDataProp": "ddh",
                "sClass": "center",
                "sTitle": "订单号",
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '<a class="breadcrumb font_green" href="OrderManage/OrgOrderDetailIndex?orderno=' + full.orderno + '">' + full.orderno +'</a>';
                    return html;
                }
            },
	        {mDataProp: "orderstatus", sTitle: "订单状态", sClass: "center", sortable: true },
	        {mDataProp: "orderamount", sTitle: "订单金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "mileage", sTitle: "里程(公里)", sClass: "center", sortable: true, 
	        	"mRender": function(data, type, full) {
	        		return (full.mileage/1000).toFixed(1);
	        	}
	        },
	        {mDataProp: "userid", sTitle: "下单人", sClass: "center", sortable: true },
	        {mDataProp: "passengers", sTitle: "乘车人", sClass: "center", sortable: true }
        ]
    };
    
	dataGridWaitCheck = renderGrid(gridObj);
}

/**
 * 核对完成
 */
function saveCheck() {
	var url = "OrganBill/CheckComplete/" + $("#billsId").val();
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");
            
				$("#waitCheckFormDiv").hide();
				dataGrid._fnReDraw();
			} else {
				
			}	
		}
	});
}

/**
 * 作废
 */
function cancellation(id) {
	$("#cancellationFormDiv").show();
	showObjectOnForm("cancellationForm", null);
	
	var editForm = $("#cancellationForm").validate();
	editForm.resetForm();
	editForm.reset();
	
	$("#billsId").val(id);
}

/**
 * 确认作废
 */
function saveCancellation() {
	var form = $("#cancellationForm");
	if(!form.valid()) return;
	
	var url = "OrganBill/DeleteOrganBill/" + $("#billsId").val() + "?remark=" + $("#remark").val();
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");
            
				$("#cancellationFormDiv").hide();
				dataGrid._fnReDraw();
				dataGridHis._fnReDraw();
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}

/**
 * 确认收款
 */
/*function confirmAccount(id,organId,money) {
	
	var comfirmData={
			tittle:"提示",
			context:"确认已收款？",
			button_l:"取消",
			button_r:"确认",
			click: "confirmPost(" + "'" + id +"','" + organId + "','" + money +"'" +")",
			htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
}*/

/*function confirmPost(id,organId,money){
	var data = {id: id,organId: organId,money: money};
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: "OrganBill/ConfirmAccount",
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");
            
				dataGrid._fnReDraw();
				dataGridHis._fnReDraw();
			} else {
				
			}	
		}
	});
}*/

/**
 * 手动生成账单
 */
function manualGenerate(){
	$("#manualFormDiv").show();
	$("#searchDiv").hide();
	
	showObjectOnForm("manualForm", null);
	
	var editForm = $("#manualForm").validate();
	editForm.resetForm();
	editForm.reset();

	$("#startTimeManual").val("");
	$("#endTimeManual").val("");
	$("#billStateManual").val("");
	
	map = {};
	checknum = 0;
	checkamount = parseFloat("0").toFixed(1);
	pagemap = {};
	
	initSelect();
}

/**
 * 获取当前页的页数
 */
function getPage() {
	var page;
	var lis = $("#dataGridManual_paginate>ul>li");
	for (var i=0;i<lis.length;i++) {
		if (lis[i].className == "paginate_button active") {
			page = lis[i].children[0].innerHTML;
			break;
		}
	}
	return page;
}

/**
 * 手动生成账单表格初始化
 */
function initGridManual() {
	var gridObj = {
		id: "dataGridManual",
        sAjaxSource: "OrganBill/GetManualOrgOrderByQuery",
        userHandle: function(oSettings, result) {
        	for(var index in result.aaData) {
        		var orderResult = result.aaData[index];
	        	if(map.hasOwnProperty(orderResult.orderno)) {
	        		$("#checkOrderManual" + orderResult.orderno).attr("checked","true");
	        	}
        	}

        	//var page = $("li.paginate_button.active")[1].children[0].innerHTML;
        	var page = getPage();
        	if (page) {
        		$("#checkAllManual").prop("disabled",false);
        	} else {
        		$("#checkAllManual").prop("disabled",true);
        	}
        	if (pagemap.hasOwnProperty(page)) {
        		$("#checkAllManual").prop("checked",true);
        	} else {
        		$("#checkAllManual").prop("checked",false);
        	}
        },
        /*iLeftColumn: 1,*/
        scrollX: true,
        language: {
        	sEmptyTable: "没有查询到未结算订单数据"
        },
        columns: [ 
            {mDataProp: "orderno", sTitle: "订单号", sClass: "center", visible: false },
            {
				// 自定义操作列
				"mDataProp" : "ZDY",
				"sClass" : "center",
				"sTitle" : "<input type='checkbox' id='checkAllManual' name='checkAllManual' onclick='checkAllManualHander(this)'></input>全选",
				"sWidth" : 70,
				"bSearchable" : false,
				"sortable" : false,
				"mRender" : function(data, type, full) {
					var html = "";
					html += '<input type="checkbox" id="checkOrderManual' + full.orderno + '" name="checkOrderManual" value="'
							+ full.orderno + '" amountvalue="' + full.orderamount
							+ '" onclick="onClickManualHander(this)"></input>';
					return html;
				}
			},
			//{mDataProp: "endtime", sTitle: "订单结束时间", sClass: "center", sortable: true },
			{
				mDataProp : "endtime",
				sTitle : "订单结束时间",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return changeToDate(data);
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "ordertype", sTitle: "类型", sClass: "center", sortable: true },
	        //{mDataProp: "orderno", sTitle: "订单号", sClass: "center", sortable: true },
	        {
                //自定义操作列
                "mDataProp": "ddh",
                "sClass": "center",
                "sTitle": "订单号",
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '<a class="breadcrumb font_green" href="OrderManage/OrgOrderDetailIndex?orderno=' + full.orderno + '">' + full.orderno +'</a>';
                    return html;
                }
            },
	        //{mDataProp: "orderstatus", sTitle: "订单状态", sClass: "center", sortable: true },
	        {
				mDataProp : "orderstatus",
				sTitle : "订单状态",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var html = "";
	                    if (full.reviewstatus == '2') {//已复核
	                    	html += '<span class="font_red">' + full.orderstatus + '</span>' ;
	                    } else {
	                    	html += '<span>' + full.orderstatus + '</span>' ;
	                    }
						return html;
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "orderamount", sTitle: "订单金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "mileage", sTitle: "里程(公里)", sClass: "center", sortable: true, 
	        	"mRender": function(data, type, full) {
	        		return (full.mileage/1000).toFixed(1);
	        	}
	        },
	        {mDataProp: "userid", sTitle: "下单人", sClass: "center", sortable: true },
	        {mDataProp: "passengers", sTitle: "乘车人", sClass: "center", sortable: true }
        ]
    };
    
	dataGridManual = renderGrid(gridObj);
}

/**
 * 手动生成账单查询
 */
function searchManual() {
	if ($("#organIdManualSearch").val() != "" && $("#organIdManual").val() != $("#organIdManualSearch").val()) {
		$("#name").val("");
		$("#remarkManual").val("");
	}
	$("#organIdManualSearch").val($("#organIdManual").val());
	map = {};
	checknum = 0;
	checkamount = parseFloat("0").toFixed(1);
	pagemap = {};
	
	var conditionArr = [
		{ "name": "organId", "value": $("#organIdManual").val() },
		{ "name": "startTime", "value": $("#startTimeManual").val() },
		{ "name": "endTime", "value": $("#endTimeManual").val() },
		{ "name": "billState", "value": $("#billStateManual").val() }
	];
	dataGridManual.fnSearch(conditionArr,"没有查询到未结算订单数据");
	$("#searchDiv").show();
	$("#checkNum").text(checknum);
	$("#checkAmount").text(checkamount);
}

function checkAllManualHander(obj) {
	var a = document.getElementsByTagName("input");
	var tempId = "";
	//var page = $("li.paginate_button.active")[1].children[0].innerHTML;
	var page = getPage();
	if (obj.checked) {
		for(var i = 0;i < a.length; i++) {
			if(a[i].name == "checkOrderManual" && a[i].type == "checkbox" && a[i].checked == false) {
				a[i].checked = true;
				tempId = a[i].value;
				map[tempId] = tempId;
				checknum++;
				checkamount = (checkamount*10 + parseFloat(a[i].attributes[4].value)*10)/10;
			}
		}
		
		pagemap[page] = page;
	} else {
		for(var i = 0;i < a.length; i++) {
			if(a[i].name == "checkOrderManual" && a[i].type == "checkbox" && a[i].checked == true) {
				a[i].checked = false;
				tempId = a[i].value;
				delete map[tempId];
				checknum--;
				checkamount = (checkamount*10 - parseFloat(a[i].attributes[4].value)*10)/10;
			}
		}
		
		delete pagemap[page];
	}
	$("#checkNum").text(checknum);
	$("#checkAmount").text(checkamount.toFixed(1));
}

function onClickManualHander(obj) {
	var tempId = "";
	if (obj.checked) {
		tempId = obj.value;
		map[tempId] = tempId;
		checknum++;
		checkamount = (checkamount*10 + parseFloat($(obj).attr("amountvalue"))*10)/10;
	} else {
		tempId = obj.value;
		delete map[tempId];
		checknum--;
		checkamount = (checkamount*10 - parseFloat($(obj).attr("amountvalue"))*10)/10;
		
		if ($("#checkAllManual").prop("checked")) {
			$("#checkAllManual").prop("checked",false);
			//var page = $("li.paginate_button.active")[1].children[0].innerHTML;
			var page = getPage();
			delete pagemap[page];
		}
	}
	$("#checkNum").text(checknum);
	$("#checkAmount").text(checkamount.toFixed(1));
}

/**
 * 生成账单
 */
function saveGenerate() {
	var form = $("#manualForm");
	if(!form.valid()) return;
	
	if (haspro(map)) {
		var orderId="";
		for ( var pro in map) {
			if (pro) {
				orderId += pro + ",";
			}
		}
		orderId = orderId.substring(0,orderId.lastIndexOf(","));

		var data = {
				name: $("#name").val(),
				remark: $("#remarkManual").val(),
				money: $("#checkAmount").text(),
				organId: $("#organIdManualSearch").val(),
				orderId: orderId
				};
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: "OrganBill/CreateOrganbill",
			data: JSON.stringify(data),
			contentType: 'application/json; charset=utf-8',
			async: false,
			success: function (status) {
				if (status.ResultSign == "Successful") {
					var message = status.MessageKey == null ? status : status.MessageKey;
	            	toastr.success(message, "提示");
	            
	            	$("#manualFormDiv").hide();
					dataGrid._fnReDraw();
					dataGridHis._fnReDraw();
				} else {
					
				}	
			}
		});
		
		
	} else {
		toastr.error("请至少勾选一条订单预览", "提示");
	}
}

function haspro(map) {
	if (!map) {
		return false;
	}
	for ( var pro in map) {
		if (!pro) {
			return false;
		}
		return true;
	}
}

/**
 * 取消
 */
function canel() {
	$("#manualFormDiv").hide();
	$("#cancellationFormDiv").hide();
}


/**
 * 导出
 */
function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganBill/ExportData?organId="+$("#organIdExport").val()+"&billState="+$("#billStateExport").val()+"&startTime="+$("#startTimeExport").val()+"&endTime="+$("#endTimeExport").val()+"&billClass=0";
	
	$("#startTime").blur();
	$("#endTime").blur();
}

function exportExcelHis() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganBill/ExportData?organId="+$("#organIdHisExport").val()+"&billState="+$("#billStateHisExport").val()+"&startTime="+$("#startTimeHisExport").val()+"&endTime="+$("#endTimeHisExport").val()+"&billClass=1";
}

/**
 * 清空
 */
function clearParameter() {
	$("#organId").select2("val","");
	$("#startTime").val("");
	$("#endTime").val("");
	$("#billState").val("");

	isclear = true;
	search();
	isclear = false;
}

/**
 * 导出核对账单订单明细
 */
function exportOrderExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganBill/GetOrgOrderListExport?billsId="+$("#billId").text()+"&billName="+$("#billName").text()+"&shortName="+$("#billShortName").text()+"&remark="+$("#billRemark").text()+"&money="+$("#billMoney").text()+"&check=1";
}
