var dataGrid;
/**
 * 页面初始化
 */
$(function () {
	initGrid();
	});
function initGrid() {
	var aaid = $("#aaid").val();
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 1,
		scrollX: true,
        sAjaxSource: "PubOrderCancelRule/GetHistoryData?aaid="+$("#aaid").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "operationtype", sTitle: "操作类型", sClass: "center", sortable: true },
	        {mDataProp: "cancelcount", sTitle: "免责取消时限(分钟)", sClass: "center", sortable: true },
	        {mDataProp: "latecount", sTitle: "迟到免责时限(分钟)", sClass: "center", sortable: true },
	        {mDataProp: "watingcount", sTitle: "免责等待时限(分钟)", sClass: "center", sortable: true },
	        {mDataProp: "price", sTitle: "取消费用(元)", sClass: "center", sortable: true },
	        {mDataProp: "updatetime", sTitle: "操作时间", sClass: "center", sortable: true },
	        {mDataProp: "updater", sTitle: "操作人", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}