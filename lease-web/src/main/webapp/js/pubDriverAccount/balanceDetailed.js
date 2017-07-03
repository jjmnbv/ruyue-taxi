var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();

	dateFormat();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "PubDriverAccount/GetDetailedByQuery?driverid=" + $("#driverid").val()+"&detailed=balance",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无余额交易记录"
        },
        columns: [
	        //{mDataProp: "expenseTime", sTitle: "时间", sClass: "center", sortable: true },
	        {
				mDataProp : "expensetime",
				sTitle : "时间",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return '<span class="font_green">' + changeToDate(full.expensetime) + '</span>';
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "tradetype", sTitle: "交易类型", sClass: "center", sortable: true,
	        	mRender : function(data, type, full) {
					if (full.tradetype == 0) {
						return "<font>充值</font>";
					} else if(full.tradetype == 1){
						return "<font>订单结算</font>";
					}else if(full.tradetype == 2){
						return "<font>提现</font>";
					}else if(full.tradetype == 3){
						return "<font>提现</font>";
					}else if(full.tradetype == 4){
						return "<font>订单收入</font>";
					}	
				}
			},
			{mDataProp: "expensetype", sTitle: "交易渠道", sClass: "center", sortable: true,
				mRender : function(data, type, full) {
					if (full.tradetype == 2 || full.tradetype == 3) {
						return "<font>/</font>";
					}else{
						if(full.expensetype == 1){
							return "<font>微信支付</font>";
						}else if(full.expensetype == 2){
							return "<font>支付宝支付</font>";
						}else if(full.expensetype == 3){
							return "<font>余额支付</font>";
						}else if(full.expensetype == 4){
							return "<font>/</font>";
						}else if(full.expensetype == 5){
							return "<font>平台转入</font>";
						}
					}
				}
			},
	        {mDataProp: "amount", sTitle: "金额(元)", sClass: "center", sortable: true,
	        	mRender : function(data, type, full) {
					if(full.tradetype == 3) {
						return "<font>-"+full.amount+"</font>";
					}else if(full.tradetype == 2){
						return "<font>+"+full.amount+"</font>";
					}else if(full.tradetype == 0){
						return "<font>+"+full.amount+"</font>";
					}else if(full.tradetype == 1){
						return "<font>-"+full.amount+"</font>";
					}else if(full.tradetype == 4){
						return "<font>+"+full.amount+"</font>";
					}
				}
			},
	        {mDataProp: "balance", sTitle: "账户余额(元)", sClass: "center", sortable: true },
	        {mDataProp: "remark", sTitle: "备注", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
	        		if (full.tradetype == 0 || full.tradetype == 1) {
						return "<font>/</font>";
					} else{
						return showToolTips(full.remark,50);
					}
				}
	        }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
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
        forceParse: 0,
        clearBtn: true
    });
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
//	var second = "";
	var change = "";
	change += myDate.getFullYear() + "-";

	if (myDate.getMonth() < 9) {
		month = "0" + (myDate.getMonth() + 1);
	} else {
		month = (myDate.getMonth() + 1);
	}
	change += month + "-";
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
//	if (myDate.getSeconds()<10){
//		second = "0"+myDate.getSeconds();
//	}else{
//		second = myDate.getSeconds();
//	}
//	change += ":" + second;
	return change;
}
//endtime 时间上+1天
function addDate(date, days) {
    if (days == undefined || days == '') {
        days = 1;
    }
	if(date != '' && date != undefined && date != null){
        var date = new Date(date);
        date.setDate(date.getDate() + days);
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return date.getFullYear() + '-' + month + '-' + day;
	}else{
		return "";
	}	
}
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "queryTradetype", "value": $("#queryTradetype").val() },
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": addDate($("#endTime").val(),1) }
	];
	dataGrid.fnSearch(conditionArr);
	$("#queryTradetypes").val($("#queryTradetype").val());
	$("#startTimes").val($("#startTime").val());
	$("#endTimes").val($("#endTime").val());
}
//取消
function cancel(){
	$("#queryTradetype").val("");
	$("#startTime").val("");
	$("#endTime").val("");
	search();
}
function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "PubDriverAccount/ExportData?driverid="
	+$("#driverid").val()+"&queryTradetype="+$("#queryTradetypes").val()+"&startTime="+$("#startTimes").val()+"&endTime="+$("#endTimes").val()+"&detailed=balance"
	+"&driverName="+$("#driverName").val()+"&driverAccount="+$("#driverAccount").val();
}
//返回
function callback(){
	window.location.href=document.getElementsByTagName("base")[0].getAttribute("href")+"PubDriverAccount/Index";
}


