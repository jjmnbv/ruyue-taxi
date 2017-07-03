var dataGrid;
/**
 * 页面初始化
 */
$(function () {
	initSelectVehcBrand();
	dateFormat();
	driverAll();
	initSelectPlateno();
	initSelectDriver();
	initSelectJobnum();
	});
//设置订单系统里面的数据
function driverAll(){
	var data = {
	            		startTime : $("#startTime").val() ,
	            		 endTime : $("#endTime").val() ,
	            		 name:$("#driver").val(),
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
			$("#sumAllOrders").html(response.allOrders);
			$("#sumOrdermoney").html("￥"+parseFloat(response.ordermoney).toFixed(1));
			$("#sumAlluporders").html(parseInt(response.processedorders)+parseInt(response.confirmedorders));
			$("#sumConfirmedorders").html(response.confirmedorders);
			$("#sumProcessedorders").html(response.processedorders);
			$("#sumPickporders").html(response.pickuporders);
			$("#sumDropofforders").html(response.dropofforders);
			$("#sumCarorders").html(response.carorders);
			$("#sumIncomePrice").html("￥"+parseFloat((response.ordermoney-response.orderreviewPrice)).toFixed(1));
			$("#sumOrderreviewPrice").html("￥"+parseFloat(response.orderreviewPrice).toFixed(1));
			$("#sumOrderreview").html(response.orderreview);
			$("#sumTaxiOrders").html(response.taxiOrders);
			initGrid();
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
	            		 name:$("#driver").val(),
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
			$("#sumAllOrders").html(response.allOrders);
			$("#sumOrdermoney").html("￥"+parseFloat(response.ordermoney).toFixed(1));
			$("#sumAlluporders").html(parseInt(response.processedorders)+parseInt(response.confirmedorders));
			$("#sumConfirmedorders").html(response.confirmedorders);
			$("#sumProcessedorders").html(response.processedorders);
			$("#sumPickporders").html(response.pickuporders);
			$("#sumDropofforders").html(response.dropofforders);
			$("#sumCarorders").html(response.carorders);
			$("#sumIncomePrice").html("￥"+parseFloat((response.ordermoney-response.orderreviewPrice)).toFixed(1));
			$("#sumOrderreviewPrice").html("￥"+parseFloat(response.orderreviewPrice).toFixed(1));
			$("#sumOrderreview").html(response.orderreview);
			$("#sumTaxiOrders").html(response.taxiOrders);
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
 * 车牌联想输入框
 * @returns
 */
function initSelectPlateno() {
	$("#plateno").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "LeDriverorderstatistics/GetPlateno",
			dataType : 'json',
			data : function(term, page) {
				return {
					plateno: term
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
 * 司机联想输入框
 * @returns
 */
function initSelectDriver() {
	$("#driver").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "LeDriverorderstatistics/GetDriver",
			dataType : 'json',
			data : function(term, page) {
				return {
					driver: term
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
 * 司机资格证号联想输入框
 * @returns
 */
function initSelectJobnum() {
	$("#jobnum").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "LeDriverorderstatistics/GetJobnum",
			dataType : 'json',
			data : function(term, page) {
				return {
					jobnum: term
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
function initGrid() {
	var gridObj = {
		id: "dataGridtoC",
		iLeftColumn: 1,
		scrollX: true,
        sAjaxSource: "LeDriverorderstatistics/GetOragnCountByQueryToC?name="+$("#driver").val()+"&plateno="+$("#plateno").val()+"&vehcBrand="+$("#vehcBrand").val()+"&jobnum="+$("#jobnum").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", visible: true },
	        {mDataProp: "name", sTitle: "司机信息", sClass: "center", sortable: true },
	        {mDataProp: "plateno", sTitle: "车牌号", sClass: "center", sortable: true },
	        {mDataProp: "vehcBrand", sTitle: "品牌车系", sClass: "center", sortable: true },
	     /*   {mDataProp: "cartype", sTitle: "服务车型", sClass: "center", sortable: true },*/
	        {mDataProp: "cityName", sTitle: "城市名称", sClass: "center", sortable: true },
	        {mDataProp: "ordermoney", sTitle: "总金额(元)", sClass: "center", sortable: true },
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
	$("#driver").select2("val", "");
	$("#plateno").select2("val", "");
	$("#vehcBrand").select2("val", "");
	$("#jobnum").select2("val", "");
	search();
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "name", "value": $("#driver").val() },
		{ "name": "jobnum", "value": $("#jobnum").val()},
		{ "name": "plateno", "value": $("#plateno").val()},
		{ "name": "vehcBrand", "value": $("#vehcBrand").val()},
		{ "name": "key", "value": "0"},
		
	];
	driverAll1();
	dataGridtoC.fnSearch(conditionArr);
}
/*
 * 导出toC
 */
function exporttoCExcel() {
	var dri = $("#driver").val();
	var pla = $("#plateno").val();
	var veh = $("#vehcBrand").val();
	if(dri==null || dri == ""){
		dri = "";
	}else{
		dri = $("#driver").select2('data').text;
	}
	if(pla==null || pla == ""){
		pla = "";
	}else{
		pla = $("#plateno").select2('data').text;
	}
	if(veh==null || veh == ""){
		veh = "";
	}else{
		veh = $("#vehcBrand").select2('data').text;
	}
	window.location.href=$("#baseUrl").val()+"LeDriverorderstatistics/ExportDrivetoC?name="+$("#driver").val()+"&nametext="+dri+"&jobnum="+$("#jobnum").val()+"&plateno="+$("#plateno").val()+"&platenotext="+pla+"&vehcBrand="+$("#vehcBrand").val()+"&vehcBrandtext="+veh+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
}



