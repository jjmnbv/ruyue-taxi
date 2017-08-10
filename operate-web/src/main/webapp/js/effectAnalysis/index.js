var dataGrid;
var rechargeGrid;
/**
 * 页面初始化
 */
$(function () {
	initRechargeGrid();
	initGrid();
	initSelectCitys();
	initDatetimePicker();
});

function initSelectCitys() {
	$("#city").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "EffectAnalysis/GetCouponUsageSendCitys",
			dataType : 'json',
			data : function(term, page) {
				return {
					city: term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
}
/**
 * 充值率表格初始化
 */

function initRechargeGrid() {
	var gridObj = {
		id: "rechargeGrid",
        sAjaxSource: "EffectAnalysis/QueryUserRechargePercent",
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        columns: [
            {mDataProp: "registerCount", sTitle: "新用户数量", sClass: "center", sortable: true},
            {mDataProp: "rechargeCount", sTitle: "充值数量", sClass: "center", sortable: true},
            {mDataProp: "rechargePercent", sTitle: "充值率", sClass: "center", sortable: true}
        ]
    };
	rechargeGrid = renderGrid(gridObj);
}


/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "EffectAnalysis/QueryCouponUsageByParam",
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        columns: [
            {mDataProp: "city", sTitle: "城市", sClass: "center", sortable: true},
            {mDataProp: "totalcount", sTitle: "发放总数量", sClass: "center", sortable: true},
            {mDataProp: "usedcount", sTitle: "已使用数量", sClass: "center", sortable: true},
            {mDataProp: "usedpercent", sTitle: "使用率", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
            		return full.usedpercent+"%";
            	}},
        ]
    };
	dataGrid = renderGrid(gridObj);
}

function initDatetimePicker(){
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
 * 查询充值率
 */
function searchRechargePercent(){
	var conditionArr = [
	            		{ "name": "starttime", "value": $("#querysendstarttime").val()},
	            		{ "name": "endtime", "value": $("#querysendendtime").val()},
	            		{ "name": "rdatetype", "value": $("#rdatetype").val()}
	            	];
	
	rechargeGrid.fnSearch(conditionArr);
}

/**
 * 查询抵扣券使用率
 */
function search() {
	var conditionArr = [
        { "name": "cdatetype", "value": $("#cdatetype").val()},
		{ "name": "usedstarttime", "value": $("#usedstarttime").val()},
		{ "name": "usedendtime", "value": $("#usedendtime").val() },
		{ "name": "city", "value": $("#city").val() }
	];
	dataGrid.fnSearch(conditionArr);
}


/**
 * 新增的清空功能
 * 
 */
function emptys(){
	$("#cdatetype").val("0")
	$("#usedstarttime").val("");
	$("#usedendtime").val("");
	$("#city").select2("val","");
	search();
}

