var dataGrid;
var rechargeGrid;
/**
 * 页面初始化
 */
$(function () {
	initRDatetimePicker("0");
	initCDatetimePicker("0");
	
	initRechargeGrid();
	initGrid();
	initSelectCitys();
	
	
	$("#rdatetype").on('change',function(){
		if($(this).val()=='0'){
			$('#querysendstarttime,#querysendendtime').datetimepicker('remove');
			initRDatetimePicker("0");
		}else{
			$('#querysendstarttime,#querysendendtime').datetimepicker('remove');
			initRDatetimePicker("1");
		}
	})
	
	$("#cdatetype").on('change',function(){
		if($(this).val()=='0'){
			$('#usedstarttime,#usedendtime').datetimepicker('remove');
			initCDatetimePicker("0");
		}else{
			$('#usedstarttime,#usedendtime').datetimepicker('remove');
			initCDatetimePicker("1");
		}
	})
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
       /* userQueryParam: [{ "name": "starttime", "value": $("#querysendstarttime").val()},
	            		 { "name": "endtime", "value": $("#querysendendtime").val()},
	            		 { "name": "rdatetype", "value": $("#rdatetype").val()}],*/
        scrollX: true,//（加入横向滚动条）
        columns: [
            {mDataProp: "registerCount", sTitle: "新用户数量", sClass: "center", sortable: true},
            {mDataProp: "rechargeCount", sTitle: "充值数量", sClass: "center", sortable: true},
            {mDataProp: "rechargePercent", sTitle: "充值率", sClass: "center", sortable: true}
        ]
    };
	rechargeGrid = renderGrid(gridObj);
	$("#rechargeGrid_info,#rechargeGrid_paginate").hide();
}
/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "EffectAnalysis/QueryCouponUsageByParam",
        iLeftColumn: 1,//（固定表头，1代表固定几列）
       /* userQueryParam: [{ "name": "cdatetype", "value": $("#cdatetype").val()},
                  		 { "name": "usedstarttime", "value": $("#usedstarttime").val()},
                		 { "name": "usedendtime", "value": $("#usedendtime").val()}],*/
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

function initRDatetimePicker(dateType){
	if(dateType=="0"){
		$('#querysendstarttime,#querysendendtime').datetimepicker({
			format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
			language: 'zh-CN', //汉化
			weekStart: 1,
			todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0,
			clearBtn: false
		});
		
		var today=getCurrentDate();
		var firstDay=today.substr(0,today.length-2)+"01";
		$('#querysendstarttime').datetimepicker('update',firstDay);
		$('#querysendendtime').datetimepicker('update',today);
		
	}else{
		
		$('#querysendstarttime,#querysendendtime').datetimepicker({
			format: "yyyy-mm", //选择日期后，文本框显示的日期格式
			language: 'zh-CN', //汉化
			weekStart: 1,
			todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 3,
			minView: 3,
			forceParse: 0,
			clearBtn: false
		});
		
		var thisMonth=getCurrentMonth();
		$("#querysendstarttime").datetimepicker('update',thisMonth);
		$("#querysendendtime").datetimepicker('update',thisMonth);
	}
}

function initCDatetimePicker(dateType){
	if(dateType=="0"){
		$('#usedstarttime,#usedendtime').datetimepicker({
			format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
			language: 'zh-CN', //汉化
			weekStart: 1,
			todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0,
			clearBtn: false
		});
		
		var today=getCurrentDate();
		var firstDay=today.substr(0,today.length-2)+"01";
		$("#usedstarttime").datetimepicker('update',firstDay);
		$("#usedendtime").datetimepicker('update',today);
	}else{
		$('#usedstarttime,#usedendtime').datetimepicker({
			format: "yyyy-mm", //选择日期后，文本框显示的日期格式
			language: 'zh-CN', //汉化
			weekStart: 1,
			todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 3,
			minView: 3,
			forceParse: 0,
			clearBtn: false
		});
		
		var thisMonth=getCurrentMonth();
		$("#usedstarttime").datetimepicker('update',thisMonth);
		$("#usedendtime").datetimepicker('update',thisMonth);
	}
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
	$("#rechargeGrid_info,#rechargeGrid_paginate").hide();
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
	$("#cdatetype").val("0");
	//$("#usedstarttime").val("");
	//$("#usedendtime").val("");
	$("#city").select2("val","");
	$("#cdatetype").trigger("change");
	search();
}

/**
 * 获取当前时间  yyyy-MM-dd
 * @returns {String}
 */
function getCurrentDate() {
    var date = new Date();
    date.setDate(date.getDate());
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

/**
 * 获取当前月份  yyyy-MM
 * @returns {String}
 */
function getCurrentMonth() {
    var date = new Date();
    date.setDate(date.getDate());
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    var currentdate = date.getFullYear() + seperator1 + month;
    return currentdate;
}
