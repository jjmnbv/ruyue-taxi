var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initOrderno();
	initFullPlateno();
	initJobNum();
	initDriver();
	initDataPicker();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "BalanceManage/QueryBalanceByParam",
        iLeftColumn: 3,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无行程费用结算信息"
        },
        columns: [
	        
            {mDataProp: "orderno", sTitle: "订单号", sClass: "center", sortable: true,
            	"mRender": function (data, type, full) {
            		var html = "";
                    html += '<a class="breadcrumb font_green" href="OrderManage/TaxiOrderDetailIndex?orderno=' + full.orderno + '">' + full.orderno +'</a>';
                    return html;
                }
            },
            {mDataProp: "fullplateno", sTitle: "车牌号", sClass: "center", sortable: true },
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: true },
            {mDataProp: "drivername", sTitle: "司机信息", sClass: "center", sortable: true,
            	"mRender": function (data, type, full) {
                    if (full.drivername == null) {
                    	return full.driverphone;
                    } else {
                    	return full.drivername + ' ' + full.driverphone;
                    }
                }
            },
            {mDataProp: "shortname", sTitle: "服务车企", sClass: "center", sortable: true },
            {mDataProp: "orderamount", sTitle: "行程费用（元）", sClass: "center", sortable: true },
            {mDataProp: "paymentstatus", sTitle: "结算状态", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (data == '未结算') {
                    	return '<span class="font_red">' + data + '</span>';
                    } else {
                    	return data;
                    }
                }
            },
            {mDataProp: "paymenttime", sTitle: "收款时间", sClass: "center", sortable: true },
            {mDataProp: "paymenttype", sTitle: "支付渠道", sClass: "center", sortable: true,
            	"mRender": function (data, type, full) {
                    if (full.paymentstatus == '未结算') {
                    	return '/';
                    } else {
                    	if (full.paymenttype == null || full.paymenttype == "") {
                    		return '余额支付';
                    	} else if (full.paymenttype == '1') {
                    		return '微信支付';
                    	} else if (full.paymenttype == '2') {
                    		return '支付宝支付';
                    	}
                    }
                }
            },
            {mDataProp: "settlementtime", sTitle: "结算时间", sClass: "center", sortable: true },
            {mDataProp: "tradeno", sTitle: "交易流水号", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (full.paymentstatus == '未结算') {
                    	return '/';
                    } else {
                    	if (full.paymenttype == null || full.paymenttype == "") {
                    		return '/';
                    	} else if (full.paymenttype == '1' || full.paymenttype == '2') {
                    		return full.tradeno;
                    	}
                    }
                }
            }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 初始化订单号联想下拉框
 * @returns
 */
function initOrderno() {
	$("#orderno").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "BalanceManage/GetOrderNo",
			dataType : 'json',
			data : function(term, page) {
				return {
					orderno: term
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
 * 初始化车牌号下拉框
 */	
function initFullPlateno(){
	$("#fullplateno").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "BalanceManage/GetFullPlateno",
			dataType : 'json',
			data : function(term, page) {
				return {
					fullplateno: term
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
 * 初始化资格证号下拉框
 */	
function initJobNum(){
	$("#jobnum").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "BalanceManage/GetJobNum",
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

/**
 * 初始化司机联想下拉框
 */	
function initDriver(){
	$("#driverid").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "BalanceManage/GetDriverByNameOrPhone",
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
 * 初始化日期选择空间
 */
function initDataPicker(){
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
	$("#ordernoExport").val($("#orderno").val());
	$("#platenoExport").val($("#fullplateno").val());
	$("#jobnumExport").val($("#jobnum").val());
	$("#driveridExport").val($("#driverid").val());
	$("#paymentstatusExport").val($("#paymentstatus").val());
	$("#companyidExport").val($("#companyid").val());
	$("#tradenoExport").val($("#tradeno").val());
	$("#starttimeExport").val($("#starttime").val());
	$("#endtimeExport").val($("#endtime").val());
	
	
	var conditionArr = [
		{ "name": "orderno", "value": $("#orderno").val() },
		{ "name": "fullplateno", "value": $("#plateno").val() },
		{ "name": "jobnum", "value": $("#jobnum").val() },
		{ "name": "driverid", "value": $("#driverid").val() },
		{ "name": "paymentstatus", "value": $("#paymentstatus").val() },
		{ "name": "companyid", "value": $("#companyid").val() },
		{ "name": "tradeno", "value": $("#tradeno").val() },
		{ "name": "starttime", "value": $("#starttime").val() },
		{ "name": "endtime", "value": $("#endtime").val() }
	];
	if (isclear) {
		dataGrid.fnSearch(conditionArr,"暂无行程费用信息");
	} else {
		dataGrid.fnSearch(conditionArr);
	}
}

/**
 * 清空
 */
function clearSearch() {
	$("#orderno").select2("val","");
	$("#fullplateno").select2("val","");
	$("#jobnum").select2("val","");
	$("#driverid").select2("val","");
	$("#paymentstatus").val("");
	$("#companyid").val("");
	$("#tradeno").val("");
	$("#starttime").val("");
	$("#endtime").val("");

	isclear = true;
	search();
	isclear = false;
}

/**
 * 导出数据
 */
function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "BalanceManage/ExportData?orderno="+$("#ordernoExport").val()+"&plateno="+$("#platenoExport").val()+"&jobnum="+$("#jobnumExport").val()+"&driverid="+$("#driveridExport").val()+"&paymentstatus="+$("#paymentstatusExport").val()+"&companyid="+$("#companyidExport").val()+"&tradeno="+$("#tradenoExport").val()+"&starttime="+$("#starttimeExport").val()+"&endtime="+$("#endtimeExport").val();
	
	//$("#plateno").blur();
	$("#tradeno").blur();
	$("#starttime").blur();
	$("#endtime").blur();
}



