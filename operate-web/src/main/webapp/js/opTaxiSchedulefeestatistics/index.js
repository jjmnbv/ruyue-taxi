var dataGrid;
var dataGrid1;
/**
 * 页面初始化
 */
$(function () {
	dateFormat();
	dateFormat1();
	initGrid();
	initGrid1();
	initSelectDriver();
	});
function initGrid() {
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 1,
		scrollX: true,
		sAjaxSource: "OpTaxiSchedulefeestatistics/GetDate?timeType="+$("#timeType").val()+"&startTime1="+$("#startTime1").val()+"&endTime1="+$("#endTime1").val(),
        columns: [
        	{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
 	        {mDataProp: "time", sTitle: "时间", sClass: "center", visible: true },
 	        {mDataProp: "customer", sTitle: "客户名称", sClass: "center", sortable: true },
 	        {mDataProp: "orders", sTitle: "订单数", sClass: "center", sortable: true },
 	        {mDataProp: "money", sTitle: "金额(元)", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
function initGrid1() {
	var gridObj = {
		id: "dataGrid1",
		iLeftColumn: 1,
		scrollX: true,
		sAjaxSource: "OpTaxiSchedulefeestatistics/GetDateDriver?timeType="+$("#timeType1").val()+"&startTime1="+$("#startTime2").val()+"&endTime1="+$("#endTime2").val(),
        columns: [
        	{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
 	        {mDataProp: "time", sTitle: "时间", sClass: "center", visible: true },
 	        {mDataProp: "driver", sTitle: "司机信息", sClass: "center", sortable: true },
 	        {mDataProp: "customer", sTitle: "客户名称", sClass: "center", sortable: true },
 	        {mDataProp: "orders", sTitle: "订单数", sClass: "center", sortable: true },
 	        {mDataProp: "money", sTitle: "金额(元)", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid1 = renderGrid(gridObj);
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
//日期设置
function dateFormat() {
	$('.searchDate').datetimepicker({
        format: "yyyy-mm", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 3, 
        maxViewMode:1,
        minViewMode:1,
        minView: 3,
        forceParse: 0
    });
	Date.prototype.format =function(format)
	{
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
	}
	var date=new Date();
	$("#endTime").val(date.format('yyyy-MM'));
	$("#startTime").val(date.format('yyyy-MM'));
	$("#endTime3").val(date.format('yyyy-MM'));
	$("#startTime3").val(date.format('yyyy-MM'));
	
}
function dateFormat1() {
	$('.searchDate1').datetimepicker({
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });
	Date.prototype.format =function(format)
	{
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
	}
	var date=new Date();
	$("#endTime1").val(date.format('yyyy-MM-dd'));
	$("#startTime1").val(date.format('yyyy-MM-dd'));
	$("#endTime2").val(date.format('yyyy-MM-dd'));
	$("#startTime2").val(date.format('yyyy-MM-dd'));
}
/**
 * 下拉框选择事件
 */
function changeTime(){
	var theTime = $("#timeType").val();
	if(theTime == "0"){
		$("#time").hide();
		$("#time1").show();
	}else{
		$("#time1").hide();
		$("#time").show();
	} 
}
/**
 * 下拉框选择事件
 */
function changeTime1(){
	var theTime = $("#timeType1").val();
	if(theTime == "0"){
		$("#time3").hide();
		$("#time2").show();
	}else{
		$("#time2").hide();
		$("#time3").show();
	} 
}

/**
 * 重设
 */
function reset(){
	var date = new Date();
	$("#timeType").val("0");
	$("#time").hide();
	$("#time1").show();
	$("#endTime1").val(date.format('yyyy-MM-dd'));
	$("#startTime1").val(date.format('yyyy-MM-dd'));
//	$(".select2-search-choice-close").mousedown();
	$("#customer").val("");
	$("#accounttype").val("");
	search();
}
function reset1(){
	var date = new Date();
	$("#timeType1").val("0");
	$("#time3").hide();
	$("#time2").show();
	$("#endTime2").val(date.format('yyyy-MM-dd'));
	$("#startTime2").val(date.format('yyyy-MM-dd'));
	$("#driver").select2("val", "");
	$("#customer1").val("");
	$("#accounttype1").val("");
	search1();
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "startTime1", "value": $("#startTime1").val() },
		{ "name": "endTime1", "value": $("#endTime1").val() },
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "timeType", "value": $("#timeType").val() },
		{ "name": "customer", "value": $("#customer").val()},
		{ "name": "accounttype", "value": $("#accounttype").val()},
		{ "name": "key", "value": "0"},
		
	];
	dataGrid.fnSearch(conditionArr);
}
function search1() {
	var conditionArr = [
		{ "name": "startTime1", "value": $("#startTime2").val() },
		{ "name": "endTime1", "value": $("#endTime2").val() },
		{ "name": "startTime", "value": $("#startTime3").val() },
		{ "name": "endTime", "value": $("#endTime3").val() },
		{ "name": "timeType", "value": $("#timeType1").val() },
		{ "name": "customer", "value": $("#customer1").val()},
		{ "name": "accounttype", "value": $("#accounttype1").val()},
		{ "name": "driver", "value": $("#driver").val()},
		{ "name": "key", "value": "0"},
		
	];
	dataGrid1.fnSearch(conditionArr);
}
/*
 * 导出
 */
function exportExcel() {
	window.location.href=$("#baseUrl").val()+"OpTaxiSchedulefeestatistics/Export?timeType="+$("#timeType").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&startTime1="+$("#startTime1").val()+"&endTime1="+$("#endTime1").val()+"&customer="+$("#customer").val()+"&customertext="+$("#customer option:checked").text()+"&accounttype="+$("#accounttype").val()+"&accounttypetext="+$("#accounttype option:checked").text();
}
/*
 * 导出
 */
function exportExcel1() {
	var dri = $("#driver").val();
	if(dri==null || dri == ""){
		dri = "";
	}else{
		dri = $("#driver").select2('data').text;
	}
	window.location.href=$("#baseUrl").val()+"OpTaxiSchedulefeestatistics/Export1?timeType="+$("#timeType1").val()+"&startTime="+$("#startTime3").val()+"&endTime="+$("#endTime3").val()+"&startTime1="+$("#startTime2").val()+"&endTime1="+$("#endTime2").val()+"&customer="+$("#customer1").val()+"&customertext="+$("#customer1 option:checked").text()+"&accounttype="+$("#accounttype1").val()+"&accounttypetext="+$("#accounttype1 option:checked").text()+"&driver="+$("#driver").val()+"&drivertext="+dri;
}
/**
 * 司机联想输入框
 * @returns
 */
function initSelectDriver() {
	var leasesCompanyId = $("#customer1").val();
	$("#driver").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "OpTaxiSchedulefeestatistics/GetDriver?",
			dataType : 'json',
			data : function(term, page) {
				return {
					driver: term,
			leasesCompanyId :$("#customer1").val()
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
