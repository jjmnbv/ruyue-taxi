var dataGrid;
var dataGrid1;
/**
 * 页面初始化
 */
$(function() {
	dateFormat();
	initGrid();
	initGrid1();
});
/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id : "dataGrid",
		sAjaxSource : "ReportStatistics/GetCompayByQuery?starttime="+$("#starttime").val()+"&endtime="+$("#endtime").val()+"&ordertype="+$("#ordertypeOne").attr("data-value"),
		columns : [ 
			{
				mDataProp : "rownum",
				sTitle : "序号",
				sClass : "center",
				sortable : true
			},
		{
			mDataProp : "leasescompanyName",
			sTitle : "服务车企",
			sClass : "center",
			sortable : true
		}, {
			mDataProp : "ordernum",
			sTitle : "总订单数",
			sClass : "center",
			sortable : true
		}, {
			mDataProp : "ordermoney",
			sTitle : "总金额",
			sClass : "center",
			sortable : true,
			}
		]
	};

	dataGrid = renderGrid(gridObj);
	/*var conditionArr = [
	            		{ "name": "starttime", "value": $("#starttime").val() },
	            		{ "name": "endtime", "value": $("#endtime").val() },
	            		{ "name": "ordertypeOne","value": $("#ordertypeOne").attr("data-value")  },
	            	];
	dataGrid.fnSearch(conditionArr);*/
}
function initGrid1() {
	var gridObj = {
		id : "dataGrid1",
		sAjaxSource : "ReportStatistics/GetDeptByQuery?starttime="+$("#starttime1").val()+"&endtime="+$("#endtime1").val()+"&ordertype="+$("#ordertypeTow").attr("data-value"),
		columns : [ {
			mDataProp : "rownum",
			sTitle : "序号",
			sClass : "center",
			sortable : true
		}, {
			mDataProp : "deptName",
			sTitle : "部门",
			sClass : "center",
			sortable : true
		}, {
			mDataProp : "ordernum",
			sTitle : "总订单数",
			sClass : "center",
			sortable : true
		}, {
			mDataProp : "ordermoney",
			sTitle : "总金额",
			sClass : "center",
			sortable : true,
			}]
	};

	dataGrid1 = renderGrid(gridObj);
//	var conditionArr = [
//	            		{ "name": "starttime", "value": $("#starttime1").val() },
//	            		{ "name": "endtime", "value": $("#endtime1").val() },
//	            		{ "name": "ordertypeOne","value": $("#ordertypeTow").attr("data-value")  },
//	            	];
//	dataGrid1.fnSearch(conditionArr);
}
function dateFormat(){
$('.date').datetimepicker({
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
$("#endtime").val(date.format('yyyy-MM-dd'));
$("#endtime1").val(date.format('yyyy-MM-dd'));
//date.setDate(1);
$("#starttime").val(date.format('yyyy-MM-dd'));
$("#starttime1").val(date.format('yyyy-MM-dd'));
}
function reset(){
	var date=new Date();
	$("#endtime").val(date.format('yyyy-MM-dd'));
//	 date.setDate(1);
	$("#starttime").val(date.format('yyyy-MM-dd'));
	$("#ordertypeOne").val("");
	$("#ordertypeOne").attr("data-value")
	var conditionArr = [
	            		{ "name": "starttime", "value": $("#starttime").val() },
	            		{ "name": "endtime", "value": $("#endtime").val() },
	            		{ "name": "ordertype","value": ""  },
	            	];
	dataGrid.fnSearch(conditionArr);
}
function reset1(){
	var date=new Date();
	$("#endtime1").val(date.format('yyyy-MM-dd'));
//	 date.setDate(1);
	$("#starttime1").val(date.format('yyyy-MM-dd'));
	$("#ordertypeTow").val("");
	var conditionArr = [
	            		{ "name": "starttime", "value": $("#starttime1").val() },
	            		{ "name": "endtime", "value": $("#endtime1").val() },
	            		{ "name": "ordertype","value": ""  },
	            	];
	dataGrid1.fnSearch(conditionArr);
}
function search(){
	var start = toDate($("#starttime").val());
	var end = toDate($("#endtime").val());
	if(end<start){
		toastr.error("结束时间不能小于开始时间", "提示");
	}
	var conditionArr = [
	            		{ "name": "starttime", "value": $("#starttime").val() },
	            		{ "name": "endtime", "value": $("#endtime").val() },
	            		{ "name": "ordertype","value": $("#ordertypeOne").attr("data-value")  },
	            		{ "name": "key","value":"0"  },
	            	];
	dataGrid.fnSearch(conditionArr);
}
function toDate(str){
    var sd=str.split("-");
    return new Date(sd[0],sd[1],sd[2]);
}
function search1(){
	var start = toDate($("#starttime1").val());
	var end = toDate($("#endtime1").val());
	if(end<start){
		toastr.error("结束时间不能小于开始时间", "提示");
	}
	var conditionArr = [
	            		{ "name": "starttime", "value": $("#starttime1").val() },
	            		{ "name": "endtime", "value": $("#endtime1").val() },
	            		{ "name": "ordertype","value": $("#ordertypeTow").attr("data-value")  },
	            		{ "name": "key","value":"0"  },
	            	];
	dataGrid1.fnSearch(conditionArr);
}
function exportExcal(){
 	window.location.href=$("#baseUrl").val()+"ReportStatistics/exportExcel?starttime="+$("#starttime").val()+"&endtime="+$("#endtime").val()+"&ordertype="+$("#ordertypeOne").attr("data-value")+"&ordertypeText="+$("#ordertypeOne")[0].defaultValue;
}
function exportExcal1(){
	window.location.href=$("#baseUrl").val()+"ReportStatistics/exportExcel1?starttime="+$("#starttime1").val()+"&endtime="+$("#endtime1").val()+"&ordertype="+$("#ordertypeTow").attr("data-value")+"&ordertypeText="+$("#ordertypeTow")[0].defaultValue;
}