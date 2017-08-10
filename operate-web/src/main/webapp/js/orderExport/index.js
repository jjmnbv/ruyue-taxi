var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	manualOrderdataGrid();
	dateFormat();
	initDriver();
	initSelectPassengers();
	initOrganid();
	initOrganid1();
	initLeasescompany();
});
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
	/*var date=new Date();
	$("#maxUseTime").val(date.format('yyyy-MM-dd'));
	 date.setDate(1);
	$("#minUseTime").val(date.format('yyyy-MM-dd'));*/
}
function initLeasescompany() {
	debugger;
    $("#leasescompany").select2({
        placeholder: "服务车企",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "OrderExport/GetLeasescompany",
            dataType: 'json',
            data: function (term, page) {
                return {
                	leasescompany: term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
}
function initDriver() {
    $("#driver").select2({
        placeholder: "司机",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "OrderExport/GetAllDriver",
            dataType: 'json',
            data: function (term, page) {
                return {
                	driver: term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
}
function initSelectPassengers() {
	$("#passengers").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "OrderExport/GetOrderperson",
			dataType : 'json',
			data : function(term, page) {
				return {
					passengers: term
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
function initSelectDriver() {
	$("#driver").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "OrderExport/GetAllDriver",
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
function initOrganid() {
    $("#organid").select2({
        placeholder: "所属机构",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "OrderExport/GetAllOrganid",
            dataType: 'json',
            data: function (term, page) {
                return {
                	"organid": term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
}
function initOrganid1() {
    $("#organid1").select2({
        placeholder: "所属机构",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "/OrderExport/GetAllOrganid1",
            dataType: 'json',
            data: function (term, page) {
                return {
                	"organid1": term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
}
function orderon(obj){
	var opt = obj.options[obj.selectedIndex]
    var usetype =  opt.value;
	if(usetype == "" || usetype == "2"){
		$("#organidDiv").hide();
		$("#organidDiv1").show();
	}else{
		$("#organidDiv").show();
		$("#organidDiv1").hide();
	}
}
/**
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "manualOrderdataGrid",
        sAjaxSource: $("#baseUrl").val() + "OrderExport/GetOrderExportData",
        scrollX: true,
        columns: [
        	{mDataProp: "orderno", sTitle: "订单号", sClass: "center", sortable: true },
        	{mDataProp: "onaddress", sTitle: "上车地点", sClass: "center", sortable: true },
        	{mDataProp: "offaddress", sTitle: "下车地点", sClass: "center", sortable: true },
	        {mDataProp: "estimatedtime", sTitle: "预估行驶时长(分钟)", sClass: "center", sortable: true },
	        {mDataProp: "estimatedmileage", sTitle: "预估行驶里程(公里)", sClass: "center", sortable: true },
	        {mDataProp: "undertime", sTitle: "下单时间", sClass: "center", sortable: true },
	        {mDataProp: "usetime", sTitle: "用车时间", sClass: "center", sortable: true },
	        {mDataProp: "ordertime", sTitle: "接单时间", sClass: "center", sortable: true },
	        {mDataProp: "starttime", sTitle: "服务开始时间", sClass: "center", sortable: true },
	        {mDataProp: "endtime", sTitle: "服务结束时间", sClass: "center", sortable: true },
	        /*{mDataProp: "ordertype", sTitle: "订单类型", sClass: "center", sortable: true },*/
	        {mDataProp: "runtime", sTitle: "行驶时长(分钟)", sClass: "center", sortable: true },
	        {mDataProp: "mileage", sTitle: "行驶里程(公里)", sClass: "center", sortable: true },
	        {mDataProp: "jobnum", sTitle: "司机资格证号", sClass: "center", sortable: true },
	        {mDataProp: "name", sTitle: "司机姓名", sClass: "center", sortable: true },
	        {mDataProp: "phone", sTitle: "司机电话", sClass: "center", sortable: true },
	        {mDataProp: "plateno", sTitle: "车牌号码", sClass: "center", sortable: true },
	        {mDataProp: "belongleasecompany", sTitle: "服务车企", sClass: "center", sortable: true },
	        {mDataProp: "cartype", sTitle: "服务车型", sClass: "center", sortable: true },
	        {mDataProp: "passengerphone", sTitle: "乘客账号", sClass: "center", sortable: true },
	        {mDataProp: "passengers", sTitle: "乘客名称", sClass: "center", sortable: true },
	        {mDataProp: "orderstatus", sTitle: "订单状态", sClass: "center", sortable: true },
	        {mDataProp: "orderamount", sTitle: "订单金额", sClass: "center", sortable: true },
	        {mDataProp: "paymentstatus", sTitle: "支付状态", sClass: "center", sortable: true },
	        {mDataProp: "ordersource", sTitle: "订单来源", sClass: "center", sortable: true },
	        {mDataProp: "usetype", sTitle: "订单类型", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
/**
 * 清空
 */
function reset(){
	$("#maxUseTime").val("");
	$("#minUseTime").val("");
	$("#driver").select2("val", "");
	$("#passengers").select2("val", "");
	$("#organid").select2("val", "");
	$("#ordertype").val("");
	$("#orderstatus").val("");
	$("#usetype").val("");
	$("#paymentstatus").val("");
	search();
}
/**
 * 查询
 */
function search() {
	/*
	 * 判断订单类型
	 */
	var usetypeok = $("#usetype").val();
	var organid;
	if(usetypeok == "1" || usetypeok == "0"){
		organid = $("#organid").val();
	}else{
		organid = "";
	}
	var conditionArr = [
		{"name":"usetype", "value":$("#usetype").val()},
		{"name":"organid", "value":organid},
		{"name":"ordertype", "value":$("#ordertype").val()},
		{"name":"paymentstatus", "value":$("#paymentstatus").val()},
		{"name":"starttime", "value":$("#minUseTime").val()},
		{"name":"endtime", "value":$("#maxUseTime").val()},
		{"name":"passengers", "value":$("#passengers").val()},
		{"name":"driver", "value":$("#driver").val()},
		{"name":"leasescompany", "value":$("#leasescompany").val()}
	];
	dataGrid.fnSearch(conditionArr);
}
function exportExcel() {
	/*
	 * 判断订单类型
	 */
	var usetypeok = $("#usetype").val();
	var organid;
	if(usetypeok == "1" || usetypeok == "0"){
		organid = $("#organid").val();
	}else{
		organid = "";
	}
	window.location.href=$("#baseUrl").val()+"OrderExport/ExportOrders?usetype="+$("#usetype").val()+"&organid="+organid+"&ordertype="+$("#ordertype").val()+"&paymentstatus="+$("#paymentstatus").val()+"&passengers="+$("#passengers").val()+"&driver="+$("#driver").val()+"&startTime="+$("#minUseTime").val()+"&endTime="+$("#maxUseTime").val()+"&leasescompany="+$("#leasescompany").val();
}
