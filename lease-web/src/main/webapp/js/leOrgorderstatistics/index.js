var dataGrid;
var dataGrid1;

/**
 * 页面初始化
 */
$(function () {
    initSelectOrgan();
    initSelectQueryCity()
	dateFormat();
	organCountAll();
	});
//设置订单信息里面的数据
function organCountAll(){
	var data = {
	            		startTime : $("#startTime").val() ,
	            		 endTime : $("#endTime").val() ,
	            		 cityid: $("#city").val() ,
	            		 organId: $("#organ").val()
	            		 }
	            		
	            	
	$.ajax({
		type: "POST",
		url:"LeOrgorderstatistics/OrganCountAll",
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
			initGrid();
			initGrid1();
		},
		error: function (xhr, status, error) {
			return;
		}
  });
};
function organCountAll1(){
	var data = {
	            		startTime : $("#startTime").val() ,
	            		 endTime : $("#endTime").val() ,
	            		 cityid: $("#city").val() ,
	            		 organId: $("#organ").val()
	            		 }
	            		
	            	
	$.ajax({
		type: "POST",
		url:"LeOrgorderstatistics/OrganCountAll",
		cache: false,
		async:false,
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
			var conditionArr = [
				{ "name": "startTime", "value": $("#startTime").val() },
				{ "name": "endTime", "value": $("#endTime").val() },
				{ "name": "cityid", "value": $("#city").val() },
				{ "name": "organId", "value": $("#organ").val()},
				{ "name": "key", "value": "0"},
				
			];
			dataGrid.fnSearch(conditionArr);
			dataGrid1.fnSearch(conditionArr);
		},
		error: function (xhr, status, error) {
			return;
		}
  });
};
//机构
function initSelectOrgan() {
	$("#organ").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "LeOrgorderstatistics/GetOrganShortName",
			dataType : 'json',
			data : function(term, page) {
				return {
					queryShortName: term
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
//城市
function initSelectQueryCity() {
	$("#queryCity").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "LeOrgorderstatistics/GetOrganCity",
			dataType : 'json',
			data : function(term, page) {
				return {
					queryCity : term
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
		id: "dataGrid",
		iLeftColumn: 1,
		scrollX: true,
		language: {sEmptyTable: "暂无服务订单数据"},
        sAjaxSource: "LeOrgorderstatistics/GetOragnCountByQuery?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "shortname", sTitle: "机构", sClass: "center", visible: true },
	        {mDataProp: "ordermoney", sTitle: "总金额", sClass: "center", sortable: true },
	        {mDataProp: "allOrders", sTitle: "总订单数", sClass: "center", sortable: true },
	        {mDataProp: "carorders", sTitle: "约车", sClass: "center", sortable: true },
	        {mDataProp: "pickuporders", sTitle: "接机", sClass: "center", sortable: true },
	        {mDataProp: "dropofforders", sTitle: "送机", sClass: "center", sortable: true },
	        {mDataProp: "alluporders", sTitle: "异常订单", sClass: "center", sortable: true }
	        
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
        sAjaxSource: "LeOrgorderstatistics/GetCityCountByQuery?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "city", sTitle: "城市", sClass: "center", visible: true },
	        {mDataProp: "ordermoney", sTitle: "总金额", sClass: "center", sortable: true },
	        {mDataProp: "allOrders", sTitle: "总订单数", sClass: "center", sortable: true },
	        {mDataProp: "carorders", sTitle: "约车", sClass: "centefr", sortable: true },
	        {mDataProp: "pickuporders", sTitle: "接机", sClass: "center", sortable: true },
	        {mDataProp: "dropofforders", sTitle: "送机", sClass: "center", sortable: true },
	        {mDataProp: "alluporders", sTitle: "异常订单", sClass: "center", sortable: true }
	        
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
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "cityid", "value": $("#city").val() },
		{ "name": "organId", "value": $("#organ").val()},
		{ "name": "key", "value": "0"},
		
	];
	/*dataGrid.fnSearch(conditionArr);
	dataGrid1.fnSearch(conditionArr);
*/	organCountAll1();
}
/**
 * 重设
 * @returns
 */
function reset(){
	var date = new Date();
	$("#endTime").val(date.format('yyyy-MM-dd'));
	 date.setDate(1);
	$("#startTime").val(date.format('yyyy-MM-dd'));
	$("#city").val("");
	$("#organ").select2("val", "");
	search();
}
function exportOrganExcel() {
	window.location.href=$("#baseUrl").val()+"LeOrgorderstatistics/ExportOrgan?organId="+$("#organ").val()+"&cityid="+$("#city").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
}
function exportCityExcel() {
	window.location.href=$("#baseUrl").val()+"LeOrgorderstatistics/ExportCity?organId="+$("#organ").val()+"&cityid="+$("#city").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
}



