var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();

	dateFormat();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OrganUserAccount/GetUserExpensesByQuery?userId=" + $("#userId").val() + "&detailType=0",
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
	        {mDataProp: "expenseType", sTitle: "交易渠道", sClass: "center", sortable: true },
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
}

/**
 * 查询
 */
function search() {
	$("#expenseTypeExport").val($("#expenseType").val());
	$("#startTimeExport").val($("#startTime").val());
	$("#endTimeExport").val($("#endTime").val());
	
	var conditionArr = [
		{ "name": "expenseType", "value": $("#expenseType").val() },
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganUserAccount/ExportData?userId="+$("#userId").val()+"&expenseType="+$("#expenseTypeExport").val()+"&startTime="+$("#startTimeExport").val()+"&endTime="+$("#endTimeExport").val()+"&account="+$("#account").val()+"&detailType=0";
	
	$("#startTime").blur();
	$("#endTime").blur();
}

/**
 * 清空
 */
function clearParameter() {
	$("#expenseType").val("");
	$("#startTime").val("");
	$("#endTime").val("");
	
	search();
}

/**
 * 返回
 */
function callBack() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganUserAccount/Index";
}


