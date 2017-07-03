var dataGrid;
var usertype;

/**
 * 页面初始化
 */
$(function () {
	initForm();
	initBtn();
	initGrid();
});

function initForm() {
	$("#city").select2({
        placeholder: "城市",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "TaxiSendrules/GetTaxiSendrulesCityBySelect",
            dataType: 'json',
            data: function (term, page) {
                return {
                    cityname: term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
}

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "TaxiSendrules/GetTaxiSendrulesByQuery",
        iLeftColumn: 2,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无派单规则信息"
        },
        columns: [
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 80,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    if(null != usertype && usertype != 1) {
                    	html += '<button type="button" class="SSbtn grey" disabled="disabled"><i class="fa fa-paste"></i>修改</button>';
                    	if(full.rulesstate == "0") {
                    		html += '<button type="button" class="SSbtn grey" disabled="disabled"><i class="fa fa-paste"></i>禁用</button>';
                    	} else if(full.rulesstate == "1") {
                    		html += '<button type="button" class="SSbtn grey" disabled="disabled"><i class="fa fa-paste"></i>启用</button>';
                    	}
                    } else {
                    	html += '<button type="button" class="SSbtn green_a" onclick="edit(\'' + full.id + '\')"><i class="fa fa-paste"></i>修改</button>';
                    	if(full.rulesstate == "0") {
                    		html += '<button type="button" class="SSbtn red" onclick="updateState(\'' + full.id + '\', 1)"><i class="fa fa-paste"></i>禁用</button>';
                    	} else if(full.rulesstate == "1") {
                    		html += '<button type="button" class="SSbtn green_a" onclick="updateState(\'' + full.id + '\', 0)"><i class="fa fa-paste"></i>启用</button>';
                    	}
                    }
                    html += '<button type="button" class="SSbtn grey_b" onclick="history(\'' + full.id + '\')"><i class="fa fa-paste"></i>历史记录</button>';
                    return html;
                }
            },
            {
				mDataProp : "cityname",
				sTitle : "城市名称",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					return full.cityname + "(" + full.shortname + ")";
				}
			},
 			{
				mDataProp : "usetype",
				sTitle : "用车类型",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					switch(full.usetype) {
						case "0": return "预约用车"; break;
						case "1": return "即刻用车"; break;
						default: return "/";
					}
				}
			},
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
				sTitle : "预约时限(分钟)",
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
				sTitle : "人工指派时限(分钟)",
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
					if(full.usetype == "1" && full.sendtype == "0") {
						return "/";
					}
					if(full.pushnumlimit == 0) {
						return "不限制";
					} else {
						return full.pushnum;
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

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{"name":"city", "value":$("#city").val()},
		{"name":"usetype", "value":$("#usecartype").val()},
		{"name":"sendtype", "value":$("#sendtype").val()},
		{"name":"sendmodel", "value":$("#sendmodel").val()}
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 新增
 */
function add() {
	window.location.href= $("#baseUrl").val() + "TaxiSendrules/EditRulesPage";
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {
	window.location.href= $("#baseUrl").val() + "TaxiSendrules/EditRulesPage?id=" + id;
}

/**
 * 启用、禁用(0-启用,1-禁用)
 * @param id
 * @param state
 */
function updateState(id, state) {
	var data = {
		id : id,
		rulesstate : state
	};
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: "TaxiSendrules/UpdateTaxiSendrulesState",
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.options.onHidden = function() {
					window.location.href = $("#baseUrl").val()+"TaxiSendrules/Index";
            	}
				toastr.success(message, "提示");
			} else {
				$("#usetype").attr("disabled", true);
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}

/**
 * 跳转到出租车派单规则历史记录页面
 * @param id
 */
function history(id) {
	window.location.href= $("#baseUrl").val() + "TaxiSendrules/TaxiSendrulesHistoryIndex/" + id;
}

/**
 * 初始化按钮
 */
function initBtn() {
	usertype = $("#usertype").val();
	if(null != usertype && usertype != 1) {
		$("#addBtn").attr("disabled", "disabled");
		$("#addBtn").removeClass("blue").addClass("grey");
	}
}

/**
 * 初始化查询
 */
function initSearch() {
	$("#city").select2("val", "");
	$("#usecartype").val("");
	$("#sendtype").val("");
	$("#sendmodel").val("");
	search();
}

