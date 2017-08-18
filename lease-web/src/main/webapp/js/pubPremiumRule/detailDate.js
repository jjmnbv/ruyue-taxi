var dataGrid;
/**
 * 页面初始化
 */
$(function () {
	dateFormat();
	initGrid();
	});
function initGrid() {
	var aaid = $("#aaid").val();
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 1,
		scrollX: true,
        sAjaxSource: "PubPremiumRule/GetDetailDateData?aaid="+$("#aaid").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        //自定义操作列
	        {mDataProp: "weekday", sTitle: "日期", sClass: "center", sortable: true },
	        {mDataProp: "theTime", sTitle: "时间范围", sClass: "center", sortable: true },
	        {mDataProp: "premiumrate", sTitle: "溢价倍率", sClass: "center", sortable: true },
	        {mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "startTime", "value": $("#startdt1").val() },
		{ "name": "endTime", "value": $("#enddt1").val() },
	];
	dataGrid.fnSearch(conditionArr);
}
/**
* 时间设置
* @param now
* @returns
*/
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
}