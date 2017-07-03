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
        sAjaxSource: "IndividualAccountRules/GetIndividualAccountRulesByOrgan?id=" + $("#rulesRefId").val(),
        iLeftColumn: 3,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无计费规则信息"
        },
        columns: [
            /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},*/

	        {mDataProp: "cityName", sTitle: "城市", sClass: "center", sortable: true },
	        {mDataProp: "carTypeName", sTitle: "服务车型", sClass: "center", sortable: true },
	        {mDataProp: "rulesTypeName", sTitle: "规则类型", sClass: "center", sortable: true },
	        {mDataProp: "startPrice", sTitle: "起步价(元)", sClass: "center", sortable: true },
	        {mDataProp: "rangePrice", sTitle: "里程价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "timePrice", sTitle: "时间补贴(元/分钟)", sClass: "center", sortable: true },
	        {mDataProp: "timeTypeName", sTitle: "时间补贴类型", sClass: "center", sortable: true },
	        {mDataProp: "perhourVisual", sTitle: "时速(公里/小时)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadmileage", sTitle: "回空里程(公里)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadprice", sTitle: "回空费价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "nighttimes", sTitle: "夜间征收时段", sClass: "center", sortable: true },
	        {mDataProp: "nighteprice", sTitle: "夜间费价(元/公里)", sClass: "center", sortable: true },
	        //{mDataProp: "updateTime", sTitle: "更新时间", sClass: "center", sortable: true }
	        {
				mDataProp : "updateTime",
				sTitle : "更新时间",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return changeToDate(data);
					} else {
						return "";
					}
				}
			}

        ]
    };
    
	dataGrid = renderGrid(gridObj);
}


/**
 * 毫秒转日期
 * 
 * @param data
 * @returns {String}
 */
function changeToDate(data) {
	var myDate = new Date(data);
	var month = "";
	var date = "";
	var hours = "";
	var minutes = "";
	var change = "";
	change += myDate.getFullYear() + "/";

	if (myDate.getMonth() < 9) {
		month = "0" + (myDate.getMonth() + 1);
	} else {
		month = (myDate.getMonth() + 1);
	}
	change += month + "/";
	if (myDate.getDate() < 10) {
		date = "0" + myDate.getDate();
	} else {
		date = myDate.getDate();
	}
	change += date;
	if (myDate.getHours() < 10) {
		hours = "0" + myDate.getHours();
	} else {
		hours = myDate.getHours();
	}
	change += " " + hours;
	if (myDate.getMinutes() < 10) {
		minutes = "0" + myDate.getMinutes();
	} else {
		minutes = myDate.getMinutes();
	}
	change += ":" + minutes;
	return change;
}

/**
 * 返回
 */
function back() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "IndividualAccountRules/Index"
}
