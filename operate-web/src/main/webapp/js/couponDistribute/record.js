var dataGrid;
/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initSelectAccounts();
	initDataPicker();
});

/**
 * 初始化抵两区用户选择框
 */
function initSelectAccounts(){
	$("#account").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "Couponing/getReceivedCouponUsers",
			dataType : 'json',
			data : function(term, page) {
				return {
					account : term,
					id:$("#id").val()
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
        sAjaxSource: "Couponing/GetPubReceivedCouponByQuery",
        userQueryParam:[{"name": "id", "value": $("#id").val()}],
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无发放记录"
        },
        columns: [
            {mDataProp: "account", sTitle: "领取账号", sClass: "center", sortable: true},
            {mDataProp: "money", sTitle: "领取金额(元)", sClass: "center", sortable: true},
            {mDataProp: "couponstatus", sTitle: "抵用券状态", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		if(full.couponstatus == 0){
	        			return "未使用";
	        		}else if(full.couponstatus == 1){
	        			return "已使用";
	        		}else if(full.couponstatus == 2){
	        			return "已过期";
	        		}
            	}
            },
            {mDataProp: "createtime", sTitle: "发放时间", sClass: "center", sortable: true},
            {mDataProp: "outimestart", sTitle: "有效期", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		if(full.outimestart !="" && full.outimeend!=""){
	        			return full.outimestart + "至" + full.outtimeend;
	        		}
            	}
            },
        ]
    };
	dataGrid = renderGrid(gridObj);
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


/**
 * 查询
 */
function search() {
	var conditionArr = [
        {"name": "id", "value": $("#id").val()},
		{ "name": "account", "value": $("#account").val()},
		{ "name": "couponstatus", "value": $("#couponstatus").val() },
		{ "name": "sendstarttime", "value": $("#sendstarttime").val() },
		{ "name": "sendendtime", "value": $("#sendendtime").val() }
	];
	dataGrid.fnSearch(conditionArr);

}
