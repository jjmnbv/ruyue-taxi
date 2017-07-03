var dataGrid;
/**
 * 页面初始化
 */
$(function () {
	dateFormat();
	initGrid();
	});
/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 2,
		scrollX: true,
		language: {sEmptyTable: "暂无报警处理记录"},
        sAjaxSource: "PubAlarmprocess/GetPubAlarmprocessData?alarmsource="+$("#alarmsource").val()+"&alarmtype="+$("#alarmtype").val()+"&passenger="+$("#passenger").val()+"&driverid="+$("#driver").val()+"&plateno="+$("#plateno").val()+"&processstatus="+$("#processstatus").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        /*{mDataProp: "code", sTitle: "序列号", sClass: "center", visible: true },*/
	        {
                //自定义操作列
                "mDataProp":"ZDY",
                "sClass":"center",
                "sTitle":"操作",
                "sWidth":80,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '<button type="button" class="SSbtn red_q"  onclick="searchDetail(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>查看详情</button>';
                    return html;
                }
            },

	        {mDataProp: "alarmsource", sTitle: "报警来源", sClass: "center", visible: true },
	        {mDataProp: "alarmtype", sTitle: "报警类型", sClass: "center", visible: true },
	        {
                "mDataProp": "DDH",
                "sClass": "center",
                "sTitle": "订单编号",
                "mRender": function (data, type, full) {
                	 var html = "";
                	 html += '<a hover="text-decoration:underline;" href="javascript:void(0);"onclick="dingdanguolv(' +"'"+ full.orderno +"'"+ ')">'+full.orderno+'</a>';
                	 return html;
                }
            },
            {mDataProp: "plateno", sTitle: "车牌号", sClass: "center", visible: true },
	        {mDataProp: "driverid", sTitle: "司机信息", sClass: "center", visible: true },
	        {mDataProp: "passenger", sTitle: "乘客信息", sClass: "center", visible: true },
	        {mDataProp: "processresult", sTitle: "处理结果", sClass: "center", visible: true },
	        {mDataProp: "processor", sTitle: "操作人", sClass: "center", visible: true },
	        {mDataProp: "alarmtime", sTitle: "报警时间", sClass: "center", visible: true },
	        {mDataProp: "processtime", sTitle: "处理时间", sClass: "center", visible: true },
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "alarmsource", "value": $("#alarmsource").val() },
		{ "name": "alarmtype", "value": $("#alarmtype").val() },
		{ "name": "passenger", "value": $("#passenger").val() },
		{ "name": "driverid", "value": $("#driver").val()},
		{ "name": "plateno", "value": $("#plateno").val()},
		{ "name": "startTime", "value": $("#startTime").val()},
		{ "name": "endTime", "value": $("#endTime").val()},
		{ "name": "processstatus", "value": $("#processstatus").val()},
		{ "name": "processresult", "value": $("#processresult").val()},
		{ "name": "key", "value": "0"},
		
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
		 format: "yyyy/mm/dd hh:ii", //选择日期后，文本框显示的日期格式
	        language: 'zh-CN', //汉化
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 0,
	        forceParse: true,
	        clearBtn: true,
	        minuteStep: 1
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
	/*var date1=new Date();
	$("#endTime").val(date1.format('yyyy-MM-dd hh:mm:ss'));
	 date1.setMonth(date1.getMonth()-1);
	$("#startTime").val(date1.format('yyyy-MM-dd hh:mm:ss'));*/
	
	
}
/**
 * 重设
 */
function reset(){
	$("#endTime").val("");
	$("#startTime").val("");
	$("#alarmsource").val("");
	$("#passenger").val("");
	$("#driver").val("");
	$("#plateno").val("");
	$("#passenger").select2("val", "");
	$("#driver").select2("val", "");
	$("#alarmtype").val("");
	$("#processresult").val("");
	search();
}
/**
 * 查看详情
 */
function searchDetail(id) {
	$("#DetailFormDiv").show();
	var data = {id : id }
	$.ajax({
		type: "POST",
		url:"PubAlarmprocess/GetPubAlarmprocessDetail?id="+id,
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		success: function (response) {
			$("#processresultPop").html(response.processresult);
			$("#processrecord").html(response.processrecord);
		},
		error: function (xhr, status, error) {
			return;
		}
  });

}
/**
 * 订单权限验证(判断订单是否跳转)
 * @param orderno
 * @returns
 */
function dingdanguolv(orderno){
	if(orderno == "/"){
		return;
	}
	var data = {
			orderno : orderno
	}
	$.ajax({
		type: "POST",
		url:"PubAlarmprocess/OrdernoOK",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (response) {
			if(response == 0){
			 toastr.error("您 没有查看该订单的权限", "提示");	
			}else{
				window.location.href = $("#baseUrl").val() + "OrderManage/OrgOrderDetailIndex?orderno="+orderno
			}
		},
		error: function (xhr, status, error) {
			return;
		}
 });
}
/**
 * 取消
 */
function canel() {
	$("#DetailFormDiv").hide();
}


