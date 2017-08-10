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
        sAjaxSource: "PubCouponRule/GetPubCouponRuleHistoryByQuery?id=" + $("#id").val(),
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无历史规则记录"
        },
        columns: [
			{mDataProp: "czlx", sTitle: "操作类型", sClass: "center", sortable: true, 
				mRender : function(data, type, full) {
					return "修改";
				}
			},
			{mDataProp: "rulecontent", sTitle: "规则内容", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
            		if (data != null && data != "") {
            			return showToolTips(data, 50);
            		} else {
            			return "/";
            		}
				  }
            },
            {mDataProp: "updatetime", sTitle: "操作时间", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
            		if (data != null) {
            			var updatetime = timeStamp2String(data,'-');
            			return updatetime.substring(0,updatetime.length-3);
            		} else {
            			return "";
            		}
				}
	        },
	        {mDataProp: "updater", sTitle: "操作人", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 返回
 */
function callBack() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "PubCouponRule/Index";
}
