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
	});
/**
 * 表格初始化
 */
function initGrid() {
	var returnId = $("#returnId").val();
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 2,
		bgColor:"red",
		scrollX: true,
		language: {sEmptyTable: "暂无报警记录"},
        sAjaxSource: "PubAlarmprocess/GetPubAlarmprocessData?alarmsource="+$("#alarmsource").val()+"&alarmtype="+$("#alarmtype").val()+"&passenger="+$("#passenger").val()+"&driverid="+$("#driver").val()+"&plateno="+$("#plateno").val()+"&processstatus="+$("#processstatus").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	       /* {mDataProp: "code", sTitle: "序列号", sClass: "center", visible: true },*/
	        {"mDataProp":"ZDY","sClass":"center","sTitle":"操作","sWidth":80,"bSearchable": false,"sortable": false,"mRender": function (data, type, full) {
                  var html = "";
                    html += '<button type="button" class="SSbtn red_q"  onclick="chulibaojing(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>处理报警</button>';
                    return html;
                	/*var html = "";
                	html += '<font color="red">'+"aaaaa"+'<font>';
                	return html;*/
                }
            },
            {"mDataProp":"ZDY","sClass":"center","sTitle":"报警来源","bSearchable": false,"sortable": false,"mRender": function (data, type, full) {
              	var html = "";
              	if(returnId==null || returnId==""){
              	 html += '<font>'+full.alarmsource+'<font>';
              	}else{
              		if(returnId == full.id){
              	       html += '<font color="red">'+full.alarmsource+'<font>';
              		}else{
              		   html += '<font>'+full.alarmsource+'<font>';
              		}
              	}
              	return html;
              }
          },
          {"mDataProp":"ZDY","sClass":"center","sTitle":"报警类型","bSearchable": false,"sortable": false,"mRender": function (data, type, full) {
        	var html = "";
        	if(returnId==null || returnId==""){
        	 html += '<font>'+full.alarmtype+'<font>';
        	}else{
        		if(returnId == full.id){
        	       html += '<font color="red">'+full.alarmtype+'<font>';
        		}else{
        		   html += '<font>'+full.alarmtype+'<font>';
        		}
        	}
        	return html;
          }
        
        },
	        {
                "mDataProp": "DDH",
                "sClass": "center",
                "sTitle": "订单编号",
                "mRender": function (data, type, full) {
			        var html = "";
			    	if(returnId==null || returnId==""){
			    		html += '<a hover="text-decoration:underline;" href="javascript:void(0);"onclick="dingdanguolv(' +"'"+ full.orderno +"'"+ ')">'+full.orderno+'</a>';
			    	}else{
			    		if(returnId == full.id){
			    			html += '<a hover="text-decoration:underline;"  href="javascript:void(0);"onclick="dingdanguolv(' +"'"+ full.orderno +"'"+ ')"><font color="red">'+full.orderno+'</font></a>';
			    		}else{
			    			html += '<a hover="text-decoration:underline;"  href="javascript:void(0);"onclick="dingdanguolv(' +"'"+ full.orderno +"'"+ ')">'+full.orderno+'</a>';
			    		}
			    	}
			    	return html;
			      }
            },
            {"mDataProp":"ZDY","sClass":"center","sTitle":"车牌号","bSearchable": false,"sortable": false,"mRender": function (data, type, full) {
            var html = "";
        	if(returnId==null || returnId==""){
        	 html += '<font>'+full.plateno+'<font>';
        	}else{
        		if(returnId == full.id){
        	       html += '<font color="red">'+full.plateno+'<font>';
        		}else{
        		   html += '<font>'+full.plateno+'<font>';
        		}
        	}
        	return html;
          }
        },
	        {"mDataProp":"ZDY","sClass":"center","sTitle":"司机信息","bSearchable": false,"sortable": false,"mRender": function (data, type, full) {
		        var html = "";
		    	if(returnId==null || returnId==""){
		    	 html += '<font>'+full.driverid+'<font>';
		    	}else{
		    		if(returnId == full.id){
		    	       html += '<font color="red">'+full.driverid+'<font>';
		    		}else{
		    		   html += '<font>'+full.driverid+'<font>';
		    		}
		    	}
		    	return html;
		      }
	    },
		    {"mDataProp":"ZDY","sClass":"center","sTitle":"乘客信息","bSearchable": false,"sortable": false,"mRender": function (data, type, full) {
			    var html = "";
		    	if(returnId==null || returnId==""){
		    	 html += '<font>'+full.passenger+'<font>';
		    	}else{
		    		if(returnId == full.id){
		    	       html += '<font color="red">'+full.passenger+'<font>';
		    		}else{
		    		   html += '<font>'+full.passenger+'<font>';
		    		}
		    	}
		    	return html;
		      }
		},
		{"mDataProp":"ZDY","sClass":"center","sTitle":"报警时间","bSearchable": false,"sortable": false,"mRender": function (data, type, full) {
				var html = "";
		    	if(returnId==null || returnId==""){
		    	 html += '<font>'+full.alarmtime+'<font>';
		    	}else{
		    		if(returnId == full.id){
		    	       html += '<font color="red">'+full.alarmtime+'<font>';
		    		}else{
		    		   html += '<font>'+full.alarmtime+'<font>';
		    		}
		    	}
		    	return html;
		      }
	},
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
	$("#passenger").select2("val", "");
	$("#driver").select2("val", "");
	$("#driver").val("");
	$("#plateno").val("");
	$("#alarmtype").val("");
	search();
}
/**
 * 处理报警
 */
function chulibaojing(id) {
	var data = {id:id}
	$.ajax({
		type: "POST",
		url:"PubAlarmprocess/HandleOK",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (result) {
			if(result){
				$("#chuliFormDiv").show();
				$("#id").val(id);
			}else{
				toastr.success("该报警已经被其他客服处理！", "提示");

			}
		},
		fail:function(){
//			window.location.href = $("#baseUrl").val() + "PubAlarmprocessDaichuli/Index"
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
				 toastr.error("无权利查看该订单信息", "提示");	
				}else if(response == 2){
					window.location.href = $("#baseUrl").val() + "OrderManage/OrderDetailIndex?orderno="+orderno
				}else if(response == 1){
					window.location.href = $("#baseUrl").val() + "TaxiOrderManage/OrderDetailIndex?orderno="+orderno
				}
				
			},
		error: function (xhr, status, error) {
			return;
		}
 });
}
/**
 * 提交
 */
function save1(){
	var processresult = $('input:radio[name="processresult"]:checked').val();
	var processrecord = $("#processrecord").val();
	if(processresult==null){
		toastr.error("请选择处理结果", "提示");
		return;
	}
	if(processrecord == ""){
		toastr.error("请输入处理记录", "提示");
		return
	}
	var data = {
			 id : $("#id").val(),
			 processresult : processresult,
			 processrecord : $("#processrecord").val()
	}
	$.ajax({
		type: "POST",
		url:"PubAlarmprocess/UpdataDetail",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (response) {
			/*//隐藏并且刷新页面*/
			$("#chuliFormDiv").hide();
			location.reload(true);
		},
		error: function (xhr, status, error) {
			return;
		}
 });
	
}
//提交验证
function save(){
	var data = {id: $("#id").val()}
	$.ajax({
		type: "POST",
		url:"PubAlarmprocess/HandleOK",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (result) {
			if(result){
				save1();
			}else{
				toastr.success("该报警已经被其他客服处理！", "提示");

			}
		},
		fail:function(){
//			window.location.href = $("#baseUrl").val() + "PubAlarmprocessDaichuli/Index"
		}
 });

}
/**
 * 取消
 */
function canel() {
	$("#chuliFormDiv").hide();
}



