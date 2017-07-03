var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	dateFormat();
	
	initGrid();
	
	searchCount();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OpUserAccount/GetUserExpensesByQuery?userId=" + $("#userId").val() + "&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val()+"&detaills=0",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无余额交易记录"
        },
        columns: [
	        //{mDataProp: "expenseTime", sTitle: "时间", sClass: "center", sortable: true },
	        {
				mDataProp : "expenseTime",
				sTitle : "时间",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return '<span class="font_green">' + full.expenseTime + '</span>';
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "tradeType", sTitle: "交易类型", sClass: "center", sortable: true },
	        {mDataProp: "expenseType", sTitle: "交易渠道", sClass: "center", sortable: true,
	        	mRender : function(data, type, full) {
					if (full.tradeType == "提现") {
						return '/';
					} else {
						return full.expenseType;
					}
				}	
	        },
	        {mDataProp: "amount", sTitle: "金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "balance", sTitle: "账户余额(元)", sClass: "center", sortable: true },
	        {mDataProp: "remark", sTitle: "备注", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
					return showToolTips(full.remark,50);
				}
	        }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
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
	
	formatDate();
}

/**
 * 毫秒转日期
 * 
 * @param data
 * @returns {String}
 */
function formatDate() {
	var myDate = new Date();
	var month = "";
	var date = "";
	var change = "";
	change += myDate.getFullYear() + "-";

	if (myDate.getMonth() < 9) {
		month = "0" + (myDate.getMonth() + 1);
	} else {
		month = (myDate.getMonth() + 1);
	}
	change += month + "-";
	
	$("#startTime").val(change + "01");

	if (myDate.getDate() < 10) {
		date = "0" + myDate.getDate();
	} else {
		date = myDate.getDate();
	}
	change += date;
	
	$("#endTime").val(change);
	
	$("#startTimeExport").val($("#startTime").val());
	$("#endTimeExport").val($("#endTime").val());
}

/**
 * 控制导出excel按钮可不可点
 */
function searchCount() {
	
	var url = "OpUserAccount/GetUserExpensesCount?userId=" + $("#userId").val()+"&detaills=0";
	var data = {startTime: $("#startTime").val(),
			    endTime: $("#endTime").val(),
			    expenseType: $("#expenseType").val()
	           };
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url:url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (json) {
			var count = json;
			if (count == 0) {// 没有数据
				$("#export").attr("disabled",true);
				$("#export").removeClass("blue_q");
			} else if (count > 0) {
				$("#export").attr("disabled",false);
				$("#export").addClass("blue_q");
			}
			
		},
		error: function (xhr, status, error) {
			return;
		}
    });
}



/**
 * 查询
 */
function search() {
	if (!changeDate()) return;

	$("#expenseTypeExport").val($("#expenseType").val());
	$("#startTimeExport").val($("#startTime").val());
	$("#endTimeExport").val($("#endTime").val());
	
	var conditionArr = [
		{ "name": "expenseType", "value": $("#expenseType").val() },
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "key", "value": "1" }
	];
	dataGrid.fnSearch(conditionArr);
	
	searchCount();
}

function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OpUserAccount/ExportData?userId="+$("#userId").val()+"&expenseType="+$("#expenseTypeExport").val()+"&startTime="+$("#startTimeExport").val()+"&endTime="+$("#endTimeExport").val()+"&account="+$("#account").val()+"&nickName="+$("#nickName").val()+"&detaills=余额明细";
	
	$("#startTime").blur();
	$("#endTime").blur();
}

/**
 * 改变开始时间
 */
function changeDate() {
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if (startTime != "" && endTime != "") {
		if (startTime > endTime) {
			toastr.error("结束日期大于等于开始日期", "提示");
			return false;
		}
	}
	return true;
}
// 返回
function callBack(){
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") +"OpUserAccount/Index";
}
function cancel(){
	$("#expenseType").val("");
//	$("#startTime").val("");
//	$("#endTime").val("");
	formatDate();
	search();
}

