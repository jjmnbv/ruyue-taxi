var dataGrid;
var dataGrid1;
var dataGrid2;
var dataGrid3;
/**
 * 页面初始化
 */
$(function () {
	dateFormat();
	initGrid();
//	initGrid1();
	initGrid2();
//	initGrid3();
	initCity();
	});
/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 1,
		scrollX: true,
		language: {sEmptyTable: "暂无服务订单数据"},
        sAjaxSource: "LePersonalorderstatistics/GetPersonalByQuery?ordertype="+$("#ordertype").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&type=1",
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "time", sTitle: "时间", sClass: "center", visible: true },
	        {mDataProp: "ordernum", sTitle: "订单", sClass: "center", sortable: true },
	        {mDataProp: "ordermoney", sTitle: "订单额(元)", sClass: "center", sortable: true },
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
/**
 * 表格初始化
 */
function initGrid1() {
	var gridObj = {
		id: "dataGrid1",
		iLeftColumn: 1,
		scrollX: true,
		language: {sEmptyTable: "暂无服务订单数据"},
        sAjaxSource: "LePersonalorderstatistics/GetPersonalByQuery1?cityid="+$("#city").val()+"&startTime="+$("#startTime1").val()+"&type=1",
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "city", sTitle: "城市", sClass: "center", visible: true },
	        {mDataProp: "ordernum", sTitle: "订单数", sClass: "center", sortable: true },
	        {mDataProp: "ordermoney", sTitle: "订单额(元)", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid1 = renderGrid(gridObj);
}
/**
 * 表格初始化
 */
function initGrid2() {
	var gridObj = {
		id: "dataGrid2",
		iLeftColumn: 1,
		scrollX: true,
		language: {sEmptyTable: "暂无服务订单数据"},
        sAjaxSource: "LePersonalorderstatistics/GetPersonalByQuery?startTime="+$("#startTime2").val()+"&endTime="+$("#endTime2").val()+"&type=2",
        columns: [
		{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
		{mDataProp: "time", sTitle: "时间", sClass: "center", visible: true },
		{mDataProp: "ordernum", sTitle: "订单数", sClass: "center", sortable: true },
		{mDataProp: "runfee", sTitle: "行程费用(元)", sClass: "center", sortable: true },
		{mDataProp: "schedulefee", sTitle: "调度费用(元)", sClass: "center", sortable: true }
	   ]
    };
	dataGrid2 = renderGrid(gridObj);
}
/**
 * 表格初始化
 */
function initGrid3() {
	var gridObj = {
		id: "dataGrid3",
		iLeftColumn: 1,
		scrollX: true,
		language: {sEmptyTable: "暂无服务订单数据"},
        sAjaxSource: "LePersonalorderstatistics/GetPersonalByQuery1?startTime="+$("#startTime3").val()+"&type=2",
        columns: [
		{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
		{mDataProp: "city", sTitle: "城市", sClass: "center", visible: true },
		{mDataProp: "ordernum", sTitle: "订单数", sClass: "center", sortable: true },
		{mDataProp: "runfee", sTitle: "行程费用(元)", sClass: "center", sortable: true },
		{mDataProp: "schedulefee", sTitle: "调度费用(元)", sClass: "center", sortable: true }
		]
    };
    
	dataGrid3 = renderGrid(gridObj);
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
	var date1=new Date();
	$("#endTime").val(date1.format('yyyy-MM'));
	$("#endTime2").val(date1.format('yyyy-MM'));
	 date1.setMonth(date1.getMonth()-1);
	$("#startTime").val(date1.format('yyyy-MM'));
	$("#startTime1").val(date1.format('yyyy-MM'));
	$("#startTime2").val(date1.format('yyyy-MM'));
	$("#startTime3").val(date1.format('yyyy-MM'));
	
	
}
/**
 * 重设
 */
function reset(){
	var date = new Date();
	$("#endTime").val(date.format('yyyy-MM'));
	date.setMonth(date.getMonth()-1);
	$("#startTime").val(date.format('yyyy-MM'));
	$("#ordertype").val("");
	search();
}
function reset1(){
	var date = new Date();
	date.setMonth(date.getMonth()-1);
	$("#startTime1").val(date.format('yyyy-MM'));
	$("#city").val("");
	search1();
}
function reset2(){
	var date = new Date();
	$("#endTime2").val(date.format('yyyy-MM'));
	date.setMonth(date.getMonth()-1);
	$("#startTime2").val(date.format('yyyy-MM'));
	search2();
}
function reset3(){
	var date = new Date();
	date.setMonth(date.getMonth()-1);
	$("#startTime3").val(date.format('yyyy-MM'));
	$("#city").val("");
	search3();
}
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "ordertype", "value": $("#ordertype").val() },
		{"name": "type","value": "1"},
		{"name": "key","value": "1"},
		
	];
	dataGrid.fnSearch(conditionArr);
}
function search1() {
	var userQueryParam = [
		{  "name": "startTime", "value": $("#startTime1").val()},
		{ "name": "cityid", "value": $("#city").val()},
		{"name": "type","value": "1"},
		{"name": "key","value": "1"},
		
	];
	dataGrid1.fnSearch(userQueryParam);
}
function search2() {
	var conditionArr = [
        { "name": "startTime", "value": $("#startTime2").val() },
		{ "name": "endTime", "value": $("#endTime2").val() },
		{"name": "type","value": "2"},
		{"name": "key","value": "1"},
		
	];
	dataGrid2.fnSearch(conditionArr);
}
function search3() {
	var conditionArr = [
		{ "name": "startTime", "value": $("#startTime3").val() },
		{ "name": "cityid", "value": $("#city1").val()},
		{"name": "type","value": "2"},
		{"name": "key","value": "1"},
		
	];
	dataGrid3.fnSearch(conditionArr);
}

function exportOrganExcel() {
	window.location.href=$("#baseUrl").val()+"LePersonalorderstatistics/ExportPersonal4?ordertype="+$("#ordertype").val()+"&ordertypetext="+$("#ordertype option:checked").text()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&type=1";
}
function exportOrganExcel1() {
	window.location.href=$("#baseUrl").val()+"LePersonalorderstatistics/ExportPersonal5?cityid="+$("#city").val()+"&city="+$("#city option:checked").text()+"&startTime="+$("#startTime1").val()+"&type=1";
}
function exportOrganExcel2() {
	window.location.href=$("#baseUrl").val()+"LePersonalorderstatistics/ExportPersonal4?startTime="+$("#startTime2").val()+"&endTime="+$("#endTime2").val()+"&type=2";
}
function exportOrganExcel3() {
	window.location.href=$("#baseUrl").val()+"LePersonalorderstatistics/ExportPersonal5?cityid="+$("#city1").val()+"&city="+$("#city option:checked").text()+"&startTime="+$("#startTime3").val()+"&type=2";
}
/*
 * 切换点击事件
 */
function carTime(){
	$("#carCity").hide();
	$("#carTime").show();
	$("#carTimeA").addClass("shen_on");
	$("#carCityA").removeClass("shen_on");
	$("#carCityE").hide();
	$("#carTimeE").show();
}
function carCity(e){
	$("#carTime").hide();
	$("#carCity").show();
	$("#carTimeE").hide();
	$("#carCityE").show();
	$("#carTimeA").removeClass("shen_on");
	$("#carCityA").addClass("shen_on");
	arguments.callee.num = arguments.callee.num ? arguments.callee.num : 0;
	if(++arguments.callee.num == 1){
		initGrid1();
	}else{
		return;
	}
}
function taxiTime(){
	$("#taxiCity").hide();
	$("#taxiTime").show();
	$("#taxiTimeA").addClass("shen_on");
	$("#taxiCityA").removeClass("shen_on");
	$("#taxiCityE").hide();
	$("#taxiTimeE").show();
}
function taxiCity(e){
	$("#taxiTime").hide();
	$("#taxiCity").show();
	$("#taxiTimeE").hide();
	$("#taxiCityE").show();
	$("#taxiTimeA").removeClass("shen_on");
	$("#taxiCityA").addClass("shen_on");
	arguments.callee.num = arguments.callee.num ? arguments.callee.num : 0;
	if(++arguments.callee.num == 1){
		initGrid3();
	}else{
		return;
	}
}
function initCity(){
	$("#carCity").hide();
	$("#taxiCity").hide();
	$("#taxiCity").hide();
	$("#carTimeA").addClass("shen_on");
	$("#taxiTimeA").addClass("shen_on");
}
