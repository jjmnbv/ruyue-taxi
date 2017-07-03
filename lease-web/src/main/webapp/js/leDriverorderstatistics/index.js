var dataGrid;
/**
 * 页面初始化
 */
$(function () {
	initSelectVehcBrand();
	dateFormat();
	driverAll();
	});
//设置订单系统里面的数据
function driverAll(){
	var data = {
	            		startTime : $("#startTime").val() ,
	            		 endTime : $("#endTime").val() ,
	            		 name:$("#keyword").val(),
	            		 cartype:$("#cartype").val(),
	            		 plateno:$("#plateno").val(),
	            		 vehcBrand:$("#vehcBrand").val(),
	            		 jobnum:$("#jobnum").val(),
	            		 }
	            		
	            	
	$.ajax({
		type: "POST",
		url:"LeDriverorderstatistics/DriverAll",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (response) {
			$("#sumAllOrders").html(response.sumAllOrders);
			$("#sumOrdermoney").html("￥"+response.sumOrdermoney);
			$("#sumAlluporders").html(response.sumAlluporders);
			$("#sumConfirmedorders").html(response.sumConfirmedorders);
			$("#sumPickuporders").html(response.sumPickuporders);
			$("#sumPickporders").html(response.sumPickporders);
			$("#sumDropofforders").html(response.sumDropofforders);
			$("#sumCarorders").html(response.sumCarorders);
			$("#sumOragnAgency").html(response.sumOragnAgency);
			$("#sumPersonOrders").html(response.sumPersonOrders);
			$("#sumIncomePrice").html("￥"+response.sumIncomePrice);
			$("#sumOrderreviewPrice").html("￥"+response.sumOrderreviewPrice);
			$("#sumOrderreview").html(response.sumOrderreview);
			$("#sumTaxiOrders").html(response.sumTaxiOrders);
			initGrid();
			initGrid1();
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
	            		 name:$("#keyword").val(),
	            		 cartype:$("#cartype").val(),
	            		 plateno:$("#plateno").val(),
	            		 vehcBrand:$("#vehcBrand").val(),
	            		 jobnum:$("#jobnum").val(),
	            		 }
	            		
	            	
	$.ajax({
		type: "POST",
		url:"LeDriverorderstatistics/DriverAll",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (response) {
			$("#sumAllOrders").html(response.sumAllOrders);
			$("#sumOrdermoney").html("￥"+response.sumOrdermoney);
			$("#sumAlluporders").html(response.sumAlluporders);
			$("#sumConfirmedorders").html(response.sumConfirmedorders);
			$("#sumPickuporders").html(response.sumPickuporders);
			$("#sumPickporders").html(response.sumPickporders);
			$("#sumDropofforders").html(response.sumDropofforders);
			$("#sumCarorders").html(response.sumCarorders);
			$("#sumOragnAgency").html(response.sumOragnAgency);
			$("#sumPersonOrders").html(response.sumPersonOrders);
			$("#sumIncomePrice").html("￥"+response.sumIncomePrice);
			$("#sumOrderreviewPrice").html("￥"+response.sumOrderreviewPrice);
			$("#sumOrderreview").html(response.sumOrderreview);
			$("#sumTaxiOrders").html(response.sumTaxiOrders);
		},
		error: function (xhr, status, error) {
			return;
		}
  });
};

function initSelectVehcBrand() {
	$("#vehcBrand").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "LeDriverorderstatistics/GetVehcBrand",
			dataType : 'json',
			data : function(term, page) {
				return {
					vehcBrand: term
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
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGridtoB",
		iLeftColumn: 1,
		scrollX: true,
	    language: {sEmptyTable: "暂无服务订单数据"},
        sAjaxSource: "LeDriverorderstatistics/GetOragnCountByQuery?name="+$("#keyword").val()+"&plateno="+$("#plateno").val()+"&vehcBrand="+$("#vehcBrand").val()+"&jobnum="+$("#jobnum").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", visible: true },
	        {mDataProp: "name", sTitle: "姓名/手机号", sClass: "center", sortable: true },
	        {mDataProp: "plateno", sTitle: "车牌号", sClass: "center", sortable: true },
	        {mDataProp: "vehcBrand", sTitle: "品牌车系", sClass: "center", sortable: true },
	     /*   {mDataProp: "cartype", sTitle: "服务车型", sClass: "center", sortable: true },*/
	        {mDataProp: "cityName", sTitle: "城市名称", sClass: "center", sortable: true },
	        {mDataProp: "ordermoney", sTitle: "总金额", sClass: "center", sortable: true },
	        {mDataProp: "allOrders", sTitle: "总订单数", sClass: "center", sortable: true },
	        {mDataProp: "carorders", sTitle: "约车", sClass: "center", sortable: true },
	        {mDataProp: "pickuporders", sTitle: "接机", sClass: "center", sortable: true },
	        {mDataProp: "dropofforders", sTitle: "送机", sClass: "center", sortable: true },
	        {mDataProp: "reviewstatus", sTitle: "异常订单", sClass: "center", sortable: true },
	        {mDataProp: "userrate", sTitle: "星级评价", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGridtoB = renderGrid(gridObj);
	/*var conditionArr = [
	            		{ "name": "startTime", "value": $("#startTime").val() },
	            		{ "name": "endTime", "value": $("#endTime").val() },
	            		{ "name": "name", "value": $("#keyword").val() },
	            		{ "name": "cartype", "value": $("#cartype").val()},
	            		{ "name": "plateno", "value": $("#plateno").val()},
	            		{ "name": "vehcBrand", "value": $("#vehcBrand").val()},
	            		
	            	];
	            	dataGrid.fnSearch(conditionArr);*/
}
function initGrid1() {
	var gridObj = {
		id: "dataGridtoC",
		iLeftColumn: 1,
		scrollX: true,
		language: {sEmptyTable: "暂无服务订单数据"},
        sAjaxSource: "LeDriverorderstatistics/GetOragnCountByQueryToC?name="+$("#keyword").val()+"&plateno="+$("#plateno").val()+"&vehcBrand="+$("#vehcBrand").val()+"&jobnum="+$("#jobnum").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", visible: true },
	        {mDataProp: "name", sTitle: "姓名/手机号", sClass: "center", sortable: true },
	        {mDataProp: "plateno", sTitle: "车牌号", sClass: "center", sortable: true },
	        {mDataProp: "vehcBrand", sTitle: "品牌车系", sClass: "center", sortable: true },
	     /*   {mDataProp: "cartype", sTitle: "服务车型", sClass: "center", sortable: true },*/
	        {mDataProp: "cityName", sTitle: "城市名称", sClass: "center", sortable: true },
	        {mDataProp: "ordermoney", sTitle: "总金额", sClass: "center", sortable: true },
	        {mDataProp: "allOrders", sTitle: "总订单数", sClass: "center", sortable: true },
	        {mDataProp: "carorders", sTitle: "约车", sClass: "center", sortable: true },
	        {mDataProp: "pickuporders", sTitle: "接机", sClass: "center", sortable: true },
	        {mDataProp: "dropofforders", sTitle: "送机", sClass: "center", sortable: true },
	        {mDataProp: "taxiOrders", sTitle: "出租车", sClass: "center", sortable: true },
	        {mDataProp: "userrate", sTitle: "星级评价", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGridtoC = renderGrid(gridObj);
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
	$("#endTime").val(date.format('yyyy-MM-dd'));
	 date.setDate(1);
	$("#startTime").val(date.format('yyyy-MM-dd'));
}
/**
 * 重设
 */
function reset(){
	var date = new Date();
	$("#endTime").val(date.format('yyyy-MM-dd'));
	 date.setDate(1);
	$("#startTime").val(date.format('yyyy-MM-dd'));
	$("#jobnum").val("");
	$("#keyword").val("");
	$("#plateno").val("");
	$("#vehcBrand").select2("val", "");
	search();
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "name", "value": $("#keyword").val() },
		{ "name": "jobnum", "value": $("#jobnum").val()},
		{ "name": "plateno", "value": $("#plateno").val()},
		{ "name": "vehcBrand", "value": $("#vehcBrand").val()},
		{ "name": "key", "value": "0"},
		
	];
	driverAll1();
	dataGridtoB.fnSearch(conditionArr);
	dataGridtoC.fnSearch(conditionArr);
}
/*
 * 导出toB
 */
function exporttoBExcel() {
	var veh = $("#vehcBrand").val();
	if(veh==null || veh == ""){
		veh = "";
	}else{
		veh = $("#vehcBrand").select2('data').text;
	}
	window.location.href=$("#baseUrl").val()+"LeDriverorderstatistics/ExportDrive?name="+$("#keyword").val()+"&cartype="+$("#cartype").val()+"&jobnum="+$("#jobnum").val()+"&plateno="+$("#plateno").val()+"&vehcBrand="+$("#vehcBrand").val()+"&vehcBrandtext="+veh+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
}
/*
 * 导出toC
 */
function exporttoCExcel() {
	var veh = $("#vehcBrand").val();
	if(veh==null || veh == ""){
		veh = "";
	}else{
		veh = $("#vehcBrand").select2('data').text;
	}
	window.location.href=$("#baseUrl").val()+"LeDriverorderstatistics/ExportDrivetoC?name="+$("#keyword").val()+"&cartype="+$("#cartype").val()+"&jobnum="+$("#jobnum").val()+"&plateno="+$("#plateno").val()+"&vehcBrand="+$("#vehcBrand").val()+"&vehcBrandtext="+veh+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
}



