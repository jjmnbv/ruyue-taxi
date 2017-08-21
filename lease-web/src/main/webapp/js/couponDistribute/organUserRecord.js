var dataGrid;
/**
 * 页面初始化
 */
$(function () {
	initGrid();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "Couponing/GetOrganUserReceivedCoupon",
        userQueryParam:[{"name": "id", "value": $("#id").val()}],
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无发放记录"
        },
        columns: [
            {mDataProp: "account", sTitle: "领取用户", sClass: "center", sortable: true},
            {mDataProp: "belongorgan", sTitle: "所属机构", sClass: "center", sortable: true},
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
            {mDataProp: "outimestart", sTitle: "有效期", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
            		if(full.outimestart !="" && full.outimeend!=""){
            			return full.outimestart + "至" + full.outtimeend;
            		}
            	}
            },
            {mDataProp: "createtime", sTitle: "发放时间", sClass: "center", sortable: true},
        ]
    };
	dataGrid = renderGrid(gridObj);
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
        {"name": "id", "value": $("#id").val()},
		{"name": "couponstatus", "value": $("#couponstatus").val()}
	];
	dataGrid.fnSearch(conditionArr);
}
