var dataGrid;
var uneffective = true;
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
        sAjaxSource: "OrgOrgan/GetOrganCreditRecord?organId=" + $("#organId").val(),
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无任何信用额度变更记录"
        },
        columns: [
	        {mDataProp: "oldCredit", sTitle: "原信用额度(元)", sClass: "center", sortable: true },
	        {mDataProp: "currentCredit", sTitle: "变更后信用额度(元)", sClass: "center", sortable: true },
	        {mDataProp: "changeTime", sTitle: "变更时间", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
					if (data != null) {
						return timeStamp2String(data).substring(0, 16);
					}
				}
	        },
	        {mDataProp: "effectiveTime", sTitle: "生效时间", sClass: "center", sortable: true,
	        	mRender : function(data, type, full) {
	        		if (uneffective) {
	        			uneffective = false;
	        			if (data) {
							return timeStamp2String(data).substring(0, 16);
						} else {
							return "";
						}
	        		} else {
	        			if (data) {
							return timeStamp2String(data).substring(0, 16);
						} else {
							return "/";
						}
	        		}
				}
	        },
	        {mDataProp: "operator", sTitle: "操作人", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 返回
 */
function callBack() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrgOrgan/Index";
}


