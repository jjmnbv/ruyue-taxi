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
        sAjaxSource: $("#baseUrl").val() + "OpAccountrules/GetOpAccountRulesModiLogByQuery?accountrulesid=" + $("#accountrulesid").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        
	        {mDataProp: "moditype", sTitle: "操作类型", sClass: "center", visible: false },
	        {mDataProp: "timetype", sTitle: "时间补贴类型", sClass: "center", visible: false },
	        {mDataProp: "rulesstate", sTitle: "规则状态", sClass: "center", visible: false },
	        {mDataProp: "perhour", sTitle: "时速", sClass: "center", visible: false },

	        {mDataProp: "moditypeName", sTitle: "操作类型", sClass: "center", sortable: true },
	        {mDataProp: "createtime", sTitle: "操作时间", sClass: "center", sortable: true },
	        {mDataProp: "createrName", sTitle: "操作人", sClass: "center", sortable: true },
	        {mDataProp: "startprice", sTitle: "起步价(元)", sClass: "center", sortable: true },
	        {mDataProp: "rangeprice", sTitle: "里程价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "timeprice", sTitle: "时间补贴(元/分钟)", sClass: "center", sortable: true },
	        {mDataProp: "timetypeName", sTitle: "时间补贴类型", sClass: "center", sortable: true },
	        {mDataProp: "perhourVisual", sTitle: "时速(公里/小时)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadmileage", sTitle: "回空里程(公里)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadprice", sTitle: "回空费价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "", sTitle: "夜间征收时段", sClass: "center", sortable: true ,
	        	mRender : function(data, type, full) {
	        		if(full.nightstarttime != null && full.nightstarttime != ''){
	        			return '<span>'+full.nightstarttime+'-'+full.nightendtime+'</span>';
	        		}else{
	        			return '/';
	        		}
	        	}
	        },
	        {mDataProp: "nighteprice", sTitle: "夜间费价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "rulesstateName", sTitle: "规则状态", sClass: "center", sortable: true,visible: false }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

