var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initTitle();
	initGrid();
});

/**
 * 初始化标题
 */
function initTitle() {
	var usetype = $("#usetype").val();
	var msg = $("#cityname").val() + "，";
	if(usetype == 0) {
		msg += "预约用车";
	} else if(usetype == 1) {
		msg += "即刻用车";
	}
	$("#taxisendrules").text(msg);
}

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "TaxiSendrules/GetTaxiSendrulesHistoryByQuery",
        scrollX: true,
        language: {
        	sEmptyTable: "暂无派单规则历史信息"
        },
        userQueryParam:[{"name": "taxisendrulesid", "value": $("#taxisendrulesid").val()}],
        columns: [
            {
				mDataProp : "operatetype",
				sTitle : "操作类型",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					switch(full.operatetype) {
						case "0": return "新增操作";break;
						case "1": return "修改操作";break;
						case "2": return "启用操作";break;
						case "3": return "禁用操作";break;
						default: return "/";
					}
				}
			},
			{mDataProp : "operatetime", sTitle : "操作时间", sClass : "center", sortable : true},
			{mDataProp : "operatorname", sTitle : "操作人", sClass : "center", sortable : true},
			{
				mDataProp : "sendtype",
				sTitle : "派单方式",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					switch(full.sendtype) {
						case "0": return "强派"; break;
						case "1": return "抢派"; break;
						case "2": return "抢单"; break;
						default: return "/";
					}
				}
			},
			{
				mDataProp : "sendmodel",
				sTitle : "派单模式",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					switch(full.sendmodel) {
						case "0": return "系统"; break;
						case "1": return "系统+人工"; break;
						default: return "/";
					}
				}
			},
			{
				mDataProp : "carsinterval",
				sTitle : "约车时限(分钟)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.usetype == "0") {
						return full.carsinterval;
					} else {
						return "/";
					}
				}
			},
			{mDataProp: "systemsendinterval", sTitle: "系统派单时限(分钟)", sClass: "center", sortable: true },
			{
				mDataProp : "driversendinterval",
				sTitle : "抢单时限(秒)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.sendtype == 1 || full.sendtype == 2) {
						return full.driversendinterval;
					} else {
						return "/";
					}
				}
			},
			{
				mDataProp : "personsendinterval",
				sTitle : "人工派单时限(分钟)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.sendmodel == 1) {
						return full.personsendinterval;
					} else {
						return "/";
					}
				}
			},
			{
				mDataProp : "initsendradius",
				sTitle : "初始派单半径(公里)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.usetype == 1) {
						return full.initsendradius;
					} else {
						return "/";
					}
				}
			},
			{
				mDataProp : "maxsendradius",
				sTitle : "最大派单半径(公里)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					return full.maxsendradius;
				}
			},
			{
				mDataProp : "increratio",
				sTitle : "半径递增比",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.usetype == 1) {
						return full.increratio + "%";
					} else {
						return "/";
					}
				}
			},
			{
				mDataProp : "pushnum",
				sTitle : "推送数量(人次)",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.sendtype == 1 || full.sendtype == 2) {
						if(full.pushnumlimit == 0) {
							return "不限制";
						} else {
							return full.pushnum;
						}
					} else {
						return "/";
					}
				}
			},
			{
				mDataProp : "pushlimit",
				sTitle : "推送限制",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					if(full.pushlimit == 0) {
						return "存在抢单弹窗，不推单";
					} else if(full.pushlimit == 1) {
						return "存在抢单弹窗，推单";
					} else {
						return "/";
					}
				}
			}
        ]
    };
	dataGrid = renderGrid(gridObj);
}

