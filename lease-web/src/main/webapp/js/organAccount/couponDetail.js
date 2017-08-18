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
        sAjaxSource: "OrganAccount/GetPubCouponDetailByQuery?organId=" + $("#organId").val(),
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无抵用券明细"
        },
        columns: [
	        {
				mDataProp : "createtime",
				sTitle : "时间",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var createtime = timeStamp2String(data,'-');
						return '<span class="font_green">' + createtime.substring(0,createtime.length-3) + '</span>';
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "usetype", sTitle: "类型", sClass: "center", sortable: true, 
				mRender : function(data, type, full) {
					if (data != null) {
						if (data == 1) {
							return "充值返券";
						} else if (data == 2) {
							return "账单结算扣款";
						} else if (data == 3) {
							return "注册返券";
						} else if (data == 4) {
							return "活动返券";
						} else if (data == 5) {
							return "违约清零";
						} else {
							return "";
						}
					} else {
						return "";
					}
				}
	        },
	        {mDataProp: "amount", sTitle: "金额(元)", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
					if (data != null) {
						if (full.usetype == 1 || full.usetype == 3 || full.usetype == 4) {
							return "+" + data;
						} else if (full.usetype == 2 || full.usetype == 5) {
							return "-" + data;
						} else {
							return "";
						}
					} else {
						return "";
					}
				}
	        },
	        {mDataProp: "balance", sTitle: "抵用券余额(元)", sClass: "center", sortable: true },
	        {mDataProp: "remark", sTitle: "备注", sClass: "center", sortable: true }
	        
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
		dataGrid.fnSearch(conditionArr,"暂无抵用券明细");
	} else {
		dataGrid.fnSearch(conditionArr);
	}
}

function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganAccount/GetPubCouponDetailExport?organId="+$("#organId").val()+"&type="+$("#typeExport").val()+"&startTime="+$("#startTimeExport").val()+"&endTime="+$("#endTimeExport").val()+"&fullName="+$("#fullName").val();
	
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
