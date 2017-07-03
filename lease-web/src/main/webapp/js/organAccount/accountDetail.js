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
        sAjaxSource: "OrganAccount/GetOrganExpensesByQuery?organId=" + $("#organId").val(),
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无账户明细"
        },
        columns: [
	        /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "type", sTitle: "类型", sClass: "center", visible: false },*/
	        
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
	        {mDataProp: "typeName", sTitle: "类型", sClass: "center", sortable: true },
	        {mDataProp: "amountVisual", sTitle: "金额(元)", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
					if (data != null) {
						return data.substr(0,data.length-1);
					} else {
						return "";
					}
				}
	        },
	        {mDataProp: "balance", sTitle: "账户余额(元)", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
					if (data != null) {
						return data.toFixed(1);
					} else {
						return "";
					}
				}
	        },
	        {mDataProp: "remarkVisual", sTitle: "备注", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}


function formatDate(now) { 
	var year=now.getYear(); 
	var month=now.getMonth()+1; 
	var date=now.getDate(); 
	var hour=now.getHours(); 
	var minute=now.getMinutes(); 
	var second=now.getSeconds(); 
	return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
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

var isclear = false;
/**
 * 查询
 */
function search() {
	$("#typeExport").val($("#type").val());
	$("#startTimeExport").val($("#startTime").val());
	$("#endTimeExport").val($("#endTime").val());
	
	var conditionArr = [
		{ "name": "type", "value": $("#type").val() },
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() }
	];
	if (isclear) {
		dataGrid.fnSearch(conditionArr,"暂无账户明细");
	} else {
		dataGrid.fnSearch(conditionArr);
	}
}

function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganAccount/ExportData?organId="+$("#organId").val()+"&type="+$("#typeExport").val()+"&startTime="+$("#startTimeExport").val()+"&endTime="+$("#endTimeExport").val()+"&fullName="+$("#fullName").val();
	
	$("#startTime").blur();
	$("#endTime").blur();
}

/**
 * 返回
 */
function callBack() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganAccount/Index";
}

/**
 * 清空
 */
function clearParameter() {
	$("#type").val("");
	$("#startTime").val("");
	$("#endTime").val("");
	
	isclear = true;
	search();
	isclear = false;
}
