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
        sAjaxSource: "StandardAccountRules/GetStandardAccountRulesModiLogByQuery?accountRulesId=" + $("#accountRulesId").val(),
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无计费规则信息"
        },
        columns: [
	        /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        
	        {mDataProp: "modiType", sTitle: "操作类型", sClass: "center", visible: false },
	        {mDataProp: "timeType", sTitle: "时间补贴类型", sClass: "center", visible: false },
	        {mDataProp: "rulesState", sTitle: "规则状态", sClass: "center", visible: false },
	        {mDataProp: "perhour", sTitle: "时速", sClass: "center", visible: false },*/

	        {mDataProp: "modiTypeName", sTitle: "操作类型", sClass: "center", sortable: true },
	        {mDataProp: "createTime", sTitle: "操作时间", sClass: "center", sortable: true },
	        {mDataProp: "createrVisual", sTitle: "操作人", sClass: "center", sortable: true },
	        {mDataProp: "startPrice", sTitle: "起步价(元)", sClass: "center", sortable: true },
	        {mDataProp: "rangePrice", sTitle: "里程价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "timePrice", sTitle: "时间补贴(元/分钟)", sClass: "center", sortable: true },
	        {mDataProp: "timeTypeName", sTitle: "时间补贴类型", sClass: "center", sortable: true },
	        {mDataProp: "perhourVisual", sTitle: "时速(公里/小时)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadmileage", sTitle: "回空里程(公里)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadprice", sTitle: "回空费价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "nighttimes", sTitle: "夜间征收时段", sClass: "center", sortable: true },
	        {mDataProp: "nighteprice", sTitle: "夜间费价(元/公里)", sClass: "center", sortable: true },
	        //{mDataProp: "rulesStateName", sTitle: "规则状态", sClass: "center", sortable: true },
	        {
				mDataProp : "rulesStateName",
				sTitle : "规则状态",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var html = "";
	                    if (full.rulesState == '1') {//禁用
	                    	html += '<span class="font_red">' + full.rulesStateName + '</span>' ;
	                    } else {
	                    	html += '<span>' + full.rulesStateName + '</span>' ;
	                    }
						return html;
					} else {
						return "";
					}
				}
			}
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

