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
        sAjaxSource: "PubDriverAccount/GetDetailedByQuery?driverid=" + $("#driverid").val()+"&detailtype=0",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无余额交易记录"
        },
        columns: [
	        
	        {mDataProp: "expensetimevisual", sTitle: "时间", sClass: "center", sortable: true },
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
						}else if(full.expensetype == 5){
							return "<font>平台转入</font>";
						}else{
							return "<font>/</font>";
						}
					}
				}
			},
	        {mDataProp: "amount", sTitle: "金额（元）", sClass: "center", sortable: true,
	        	mRender : function(data, type, full) {
					if(full.tradetype == 0) {
						return "<font>+"+full.amount+"</font>";
					}else if(full.tradetype == 1){
						return "<font>-"+full.amount+"</font>";
					}else if(full.tradetype == 2){
						return "<font>+"+full.amount+"</font>";
					}else if(full.tradetype == 3){
						return "<font>-"+full.amount+"</font>";
					}else if(full.tradetype == 4){
						return "<font>+"+full.amount+"</font>";
					}
				}
			},
	        {mDataProp: "balance", sTitle: "账户余额（元）", sClass: "center", sortable: true },
	        {mDataProp: "remark", sTitle: "备注", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
					return showToolTips(full.remark,50);
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

var isclear = false;
/**
 * 查询
 */
function search() {
	if ($("#startTime").val() != "" && $("#endTime").val() != "") {
		if ($("#endTime").val() < $("#startTime").val()) {
			toastr.error("结束日期应大于等于开始日期", "提示");
			return;
		}
	}
	
	var conditionArr = [
		{ "name": "queryTradetype", "value": $("#queryTradetype").val() },
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() }
	];
	if (isclear) {
		dataGrid.fnSearch(conditionArr,"暂无余额交易记录");
	} else {
		dataGrid.fnSearch(conditionArr);
	}
	$("#queryTradetypes").val($("#queryTradetype").val());
	$("#startTimes").val($("#startTime").val());
	$("#endTimes").val($("#endTime").val());
}
//取消
function cancel(){
	$("#queryTradetype").val("");
	$("#startTime").val("");
	$("#endTime").val("");
	
	isclear = true;
	search();
	isclear = false;
}
function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "PubDriverAccount/ExportData?driverid="
	+$("#driverid").val()+"&queryTradetype="+$("#queryTradetypes").val()+"&startTime="+$("#startTimes").val()+"&endTime="+$("#endTimes").val()+"&detailtype=0"
	+"&driverName="+$("#driverName").val()+"&driverAccount="+$("#driverAccount").val();
	
	$("#startTime").blur();
	$("#endTime").blur();
}
//返回
function callback(){
	window.location.href=document.getElementsByTagName("base")[0].getAttribute("href")+"PubDriverAccount/Index";
}


