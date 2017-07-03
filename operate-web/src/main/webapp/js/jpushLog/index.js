/**
 * 
 */
var dataGrid;
$(function(){
	initGrid();
})

function initGrid(){
	var gridObj = {
			id: "dataGrid",
	        sAjaxSource: "JPushLog/QueryJPushLogByParam",
	        scrollX: true,
	        language: {
	        	sEmptyTable: "暂无司机举手日志信息"
	        },
	        columns: [
	            {mDataProp: "orderno", sTitle: "订单号", sClass: "center", sortable: true},
	            {mDataProp: "driverphone", sTitle: "司机手机号", sClass: "center", sortable: true},
	            {mDataProp: "drivername", sTitle: "司机姓名", sClass: "center", sortable: true},
	            {mDataProp: "pushstate", sTitle: "极光推送状态", sClass: "center", sortable: true,
	               mRender:	function(data,type,full){
	            	   switch(full.pushstate){
	            	       case 0:return "推送失败";break;
	            	       case 1:return "推送成功";break;
	            	       default:return "/";
	            	   }
	               }
	            },
	            {mDataProp: "phonepushstate", sTitle: "手机推送状态", sClass: "center", sortable: true,
	            	mRender:	function(data,type,full){
		            	   switch(full.phonepushstate){
		            	       case 0:return "推送失败";break;
		            	       case 1:return "推送成功";break;
		            	       default:return "/";
		            	   }
		               }	
	            },
	            {mDataProp: "handstate", sTitle: "举手状态", sClass: "center", sortable: true,
	            	mRender:	function(data,type,full){
		            	   switch(full.handstate){
		            	       case 0:return "未举手";break;
		            	       case 1:return "已举手";break;
		            	       default:return "/";
		            	   }
		               }	
	            },
	            {mDataProp: "takeorderstate", sTitle: "接单状态", sClass: "center", sortable: true,
	            	mRender:	function(data,type,full){
		            	   switch(full.takeorderstate){
		            	       case 0:return "接单失败";break;
		            	       case 1:return "接单成功";break;
		            	       default:return "/";
		            	   }
		               }
	            },
	            {mDataProp: "createtime", sTitle: "创建时间", sClass: "center", sortable: true,
	            	mRender:	function(data,type,full){
		            	  return changeToDate(full.createtime);
	            	}
	            },
	            {mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true,
	            	mRender:	function(data,type,full){
	            		 return changeToDate(full.updatetime);
	            	}	
	            },
	        ]
	    };
		dataGrid = renderGrid(gridObj);
}

/**
 * 查询
 */
function search(){
	var conditionArr = [
	            		{ "name": "orderno", "value": $("#orderno").val() },
	            		{ "name": "driverphone", "value": $("#driverphone").val() }
	            	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 清空
 */
function emptyQueryParam(){
	$("#orderno").val("");
	$("#driverphone").val("");
}

/**
 * 导出数据
 */
function exportExcel(){
	var orderno=$("#orderno").val();
	var driverphone=$("#driverphone").val();
	window.location.href=document.getElementsByTagName("base")[0].getAttribute("href")+"JPushLog/ExportExcel?orderno="+orderno+"&driverphone="+driverphone;
}

/**
 * 毫秒转日期
 * 
 * @param data
 * @returns {String}
 */
function changeToDate(data) {
	var myDate = new Date(data);
	var month = "";
	var date = "";
	var hours = "";
	var minutes = "";
	var second = "";
	var change = "";
	change += myDate.getFullYear() + "/";

	if (myDate.getMonth() < 9) {
		month = "0" + (myDate.getMonth() + 1);
	} else {
		month = (myDate.getMonth() + 1);
	}
	change += month + "/";
	if (myDate.getDate() < 10) {
		date = "0" + myDate.getDate();
	} else {
		date = myDate.getDate();
	}
	change += date;
	if (myDate.getHours() < 10) {
		hours = "0" + myDate.getHours();
	} else {
		hours = myDate.getHours();
	}
	change += " " + hours;
	if (myDate.getMinutes() < 10) {
		minutes = "0" + myDate.getMinutes();
	} else {
		minutes = myDate.getMinutes();
	}
	change += ":" + minutes;
	if (myDate.getSeconds()<10){
		second = "0"+myDate.getSeconds();
	}else{
		second = myDate.getSeconds();
	}
	change += ":" + second;
	return change;
}