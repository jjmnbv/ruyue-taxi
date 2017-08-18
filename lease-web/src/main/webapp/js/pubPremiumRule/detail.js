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
        sAjaxSource: "PubPremiumRule/GetDetailData?aaid="+$("#aaid").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        //自定义操作列
	        {mDataProp: "weekday", sTitle: "日期", sClass: "center", sortable: true },
	        {mDataProp: "theTime", sTitle: "时间范围", sClass: "center", sortable: true },
	        {mDataProp: "premiumrate", sTitle: "溢价倍率", sClass: "center", sortable: true },
	        {mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "weekday", "value": $("#weeks").val() },
	];
	dataGrid.fnSearch(conditionArr);
}