var dataGrid;
/**
 * 页面初始化
 */
$(function() {
	dateFormat();
	dataAll();
});
//销售统计
function dataAll(){
	var data = {
			starttime: $("#startTime").val() ,
			type: "2" ,
    		leasescompanyid: $("#custom").val(),
    		}
	$.ajax({
		type: "POST",
		url:"OpOrderstatistics/AllData",
		cache: false,
		dataType : 'json',
		data:JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		success: function (response) {
			$("#orders").html(response.orders);
			$("#ordersMoney").html("￥"+response.ordermoney);
			initGrid();
		},
		error: function (xhr, status, error) {
			return;
		}
  });
};
function dataAll1(){
	var data = {
			starttime: $("#startTime").val() ,
			type: "2" ,
    		leasescompanyid: $("#custom").val(),
    		}
	$.ajax({
		type: "POST",
		url:"OpOrderstatistics/AllData",
		cache: false,
		dataType : 'json',
		data:JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		success: function (response) {
			$("#orders").html(response.orders);
			$("#ordersMoney").html("￥"+response.ordermoney);
		},
		error: function (xhr, status, error) {
			return;
		}
  });
};
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
        format: "yyyy", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 4, 
        maxViewMode:1,
        minViewMode:1,
        minView: 4,
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
	$("#endTime").val(date.format('yyyy'));
	 date.setDate(1);
	$("#startTime").val(date.format('yyyy'));
}
/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 1,
		scrollX: true,
        sAjaxSource: "OpOrderstatistics/monthData?starttime="+$("#startTime").val()+"&type=2",
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "starttime", sTitle: "年度", sClass: "center", visible: true },
	        {mDataProp: "compayName", sTitle: "客户名称", sClass: "center", sortable: true },
	        {mDataProp: "orders", sTitle: "总订单量", sClass: "center", sortable: true },
	        {mDataProp: "carorders", sTitle: "约车订单", sClass: "center", sortable: true },
	        {mDataProp: "pickuporders", sTitle: "接车订单", sClass: "center", sortable: true },
	        {mDataProp: "dropofforders", sTitle: "送机订单", sClass: "center", sortable: true },
	        {mDataProp: "ordermoney", sTitle: "订单收入(元)", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
	/*var conditionArr = [
	            		{ "name": "starttime", "value": $("#startTime").val() },
	            		{ "name": "type", "value": "2" },
	            	];
	            	dataGrid.fnSearch(conditionArr);*/
}
function search(){
	var conditionArr = [
	            		{ "name": "starttime", "value": $("#startTime").val() },
	            		{ "name": "type", "value": "2" },
	            		{ "name": "key", "value": "0" },
	            		{ "name": "leasescompanyid", "value": $("#custom").val() },
	            	];
	            	dataGrid.fnSearch(conditionArr);
	            	dataAll1();
}
function exportExcel(){
	window.location.href=$("#baseUrl").val()+"OpOrderstatistics/exportExcel?starttime="+$("#startTime").val()+"&leasescompanyid="+$("#custom").val()+"&type=2"+"&endtime=null";
}
