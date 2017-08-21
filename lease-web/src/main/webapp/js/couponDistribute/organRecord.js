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
        sAjaxSource: "Couponing/GetOrganReceivedCoupon",
        userQueryParam:[{"name": "id", "value": $("#id").val()}],
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无发放记录"
        },
        columns: [
            {mDataProp: "organname", sTitle: "领取机构", sClass: "center", sortable: true},
            {mDataProp: "money", sTitle: "领取金额(元)", sClass: "center", sortable: true},
            {mDataProp: "createtime", sTitle: "发放时间", sClass: "center", sortable: true},
        ]
    };
	dataGrid = renderGrid(gridObj);
}

