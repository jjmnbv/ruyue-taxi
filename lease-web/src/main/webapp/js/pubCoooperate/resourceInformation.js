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
        sAjaxSource: "PubCoooperate/GetResourceInformationByQuery?id="+$("#id").val()+"&servicetype="+$("#servicetype").val(),
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无任何客户信息"
        },
        columns: [
	        {mDataProp: "plateno", sTitle: "车辆信息", sClass: "center", sortable: true },
	        {mDataProp: "driverInformation", sTitle: "司机信息", sClass: "center", sortable: true },
	        {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "queryPlateno", "value": $("#queryPlateno").val() },
		{ "name": "queryJobnum", "value": $("#queryJobnum").val() },
		{ "name": "queryDriverInformation", "value": $("#queryDriverInformation").val() }
	];
	dataGrid.fnSearch(conditionArr);
}
/**
 * 清空
 */
function clearParameter() {
	$("#queryPlateno").val("");
	$("#queryJobnum").val("");
	$("#queryDriverInformation").val("");
	search();
}
