var dataGrid;
var dataGrid1;
/**
 * 页面初始化
 */
$(function () {
	initOrdertype();
	dateFormat();
	driverAll();
	});
//设置订单系统里面的数据
function driverAll(){
	var data = {
	            		startTime : $("#startTime").val() ,
	            		endTime : $("#endTime").val() ,
	            		type : "0" ,
	            		 }
	            		
	            	
	$.ajax({
		type: "POST",
		url:"LePersonalorderstatistics/PersonalAll",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (response) {
			$("#sumAllOrders").html(response.sumAllOrders);
			$("#sumCarorders").html(response.sumCarorders);
			$("#sumPickporders").html(response.sumPickporders);
			$("#sumDropofforders").html(response.sumDropofforders);
			$("#sumOrderreview").html(response.sumOrderreview);
			
			$("#sumOrdermoney").html("￥"+response.sumOrdermoney);
			$("#sumIncomePrice").html("￥"+response.sumIncomePrice);
			$("#sumDiffmoney").html("￥"+response.sumDiffmoney);
			
			$("#sumAlluporders").html(response.sumAlluporders);
			$("#sumConfirmedorders").html(response.sumConfirmedorders);
			$("#sumProcessedorders").html(response.sumProcessedorders);
			initGrid();
			initGrid1();
			/*initGrid2();
			initGrid3();*/
		},
		error: function (xhr, status, error) {
			return;
		}
  });
};
function driverAll1(){
	var data = {
	            		startTime : $("#startTime").val() ,
	            		endTime : $("#endTime").val() ,
	            		type : "0" ,
	            		 }
	            		
	            	
	$.ajax({
		type: "POST",
		url:"LePersonalorderstatistics/PersonalAll",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (response) {
			$("#sumAllOrders").html(response.sumAllOrders);
			$("#sumCarorders").html(response.sumCarorders);
			$("#sumPickporders").html(response.sumPickporders);
			$("#sumDropofforders").html(response.sumDropofforders);
			$("#sumOrderreview").html(response.sumOrderreview);
			
			$("#sumOrdermoney").html("￥"+response.sumOrdermoney);
			$("#sumIncomePrice").html("￥"+response.sumIncomePrice);
			$("#sumDiffmoney").html("￥"+response.sumDiffmoney);
			
			$("#sumAlluporders").html(response.sumAlluporders);
			$("#sumConfirmedorders").html(response.sumConfirmedorders);
			$("#sumProcessedorders").html(response.sumProcessedorders);
		},
		error: function (xhr, status, error) {
			return;
		}
  });
};
//支付类型
function initOrdertype(){
	$.ajax({
		type: "GET",
		url:"LePersonalorderstatistics/GetPaymethod",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		success: function (paymethod) {
		$.each(paymethod, function(i,item) {
			$("#paymethod").append('<option value="' + item.value + '">' + item.text + '</option>');
			});
		$.each(paymethod, function(i,item) {
			$("#paymethod1").append('<option value="' + item.value + '">' + item.text + '</option>');
			});
		},
		error: function (xhr, status, error) {
			return;
		}
  })
};

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 1,
		scrollX: true,
		language: {sEmptyTable: "暂无服务订单数据"},
        sAjaxSource: "LePersonalorderstatistics/GetPersonalByQuery?ordertype="+$("#ordertype").val()+"&paymethod="+$("#paymethod").val()+"&startTime="+$("#startTime1").val()+"&endTime="+$("#endTime1").val()+"&type=0",
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "time", sTitle: "时间", sClass: "center", visible: true },
	        {mDataProp: "ordernum", sTitle: "订单", sClass: "center", sortable: true },
	        {mDataProp: "ordermoney", sTitle: "订单额(元)", sClass: "center", sortable: true },
	        {mDataProp: "alluporders", sTitle: "异常单数", sClass: "center", visible: true },
	        {mDataProp: "diffmoney", sTitle: "差异金额", sClass: "center", sortable: true }
	        
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
        sAjaxSource: "LePersonalorderstatistics/GetPersonalByQuery1?ordertype="+$("#ordertype1").val()+"&paymethod="+$("#paymethod1").val()+"&cityid="+$("#city").val()+"&startTime="+$("#startTime2").val()+"&type=0",
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "city", sTitle: "城市", sClass: "center", visible: true },
	        {mDataProp: "ordernum", sTitle: "订单数", sClass: "center", sortable: true },
	        {mDataProp: "ordermoney", sTitle: "订单额(元)", sClass: "center", sortable: true }
	        
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
	var date1=new Date();
	$("#endTime").val(date1.format('yyyy-MM'));
	$("#endTime1").val(date1.format('yyyy-MM'));
	$("#endTime3").val(date1.format('yyyy-MM'));
	$("#startTime2").val(date1.format('yyyy-MM'));
	$("#startTime4").val(date1.format('yyyy-MM'));
	 date1.setMonth(date1.getMonth()-1);
	$("#startTime").val(date1.format('yyyy-MM'));
	$("#startTime1").val(date1.format('yyyy-MM'));
	$("#startTime3").val(date1.format('yyyy-MM'));
	
	
}
/**
 * 重设
 */
function reset1(){
	var date = new Date();
	$("#endTime").val(date.format('yyyy-MM'));
	date.setMonth(date.getMonth()-1);
	$("#startTime").val(date.format('yyyy-MM'));
	search1();
}
function reset2(){
	var date = new Date();
	$("#endTime1").val(date.format('yyyy-MM'));
	date.setMonth(date.getMonth()-1);
	$("#startTime1").val(date.format('yyyy-MM'));
	$("#ordertype").val("");
	$("#paymethod").val("");
	search2();
}
function reset3(){
	var date = new Date();
	$("#startTime2").val(date.format('yyyy-MM'));
	$("#ordertype1").val("");
	$("#city").val("");
	search3();
}
/**
 * 查询
 */
function search1(){
	driverAll1();
}
function search2() {
	var conditionArr = [
		{ "name": "startTime", "value": $("#startTime1").val() },
		{ "name": "endTime", "value": $("#endTime1").val() },
		{ "name": "ordertype", "value": $("#ordertype").val() },
		{ "name": "paymethod", "value": $("#paymethod").val()},
		{"name": "type","value": "0"},
		{"name": "key","value": "1"},
		
	];
	dataGrid.fnSearch(conditionArr);
}
function search3() {
	var userQueryParam = [
		{  "name": "startTime", "value": $("#startTime2").val()},
		{ "name": "ordertype", "value": $("#ordertype1").val() },
		/*{ "name": "paymethod", "value": $("#paymethod1").val()},*/
		{ "name": "cityid", "value": $("#city").val()},
		{"name": "type","value": "0"},
		{"name": "key","value": "1"},
		
	];
	dataGrid1.fnSearch(userQueryParam);
}
function exportOrganExcel2() {
	window.location.href=$("#baseUrl").val()+"LePersonalorderstatistics/ExportPersonal2?ordertype="+$("#ordertype").val()+"&ordertypetext="+$("#ordertype option:checked").text()+"&paymethodtext="+$("#paymethod option:checked").text()+"&paymethod="+$("#paymethod").val()+"&startTime="+$("#startTime1").val()+"&endTime="+$("#endTime1").val()+"&type=0";
}
function exportOrganExcel3() {
	window.location.href=$("#baseUrl").val()+"LePersonalorderstatistics/ExportPersonal3?ordertype="+$("#ordertype1").val()+"&ordertypetext="+$("#ordertype1 option:checked").text()+"&city="+$("#city option:checked").text()+"&cityid="+$("#city").val()+"&startTime="+$("#startTime2").val()+"&type=0";
}



