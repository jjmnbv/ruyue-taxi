var dataGrid;
var usertype;

/**
 * 页面初始化
 */
$(function () {
	initBtn();
	initGrid();
	initSelectCity();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "SendRules/GetSendRulesByQuery",
        iLeftColumn: 4,
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
                    	if(full.rulesState == "0") {
                    		html += '<button type="button" class="SSbtn red" onclick="updateState(\'' + full.id + '\', 1)"><i class="fa fa-paste"></i>禁用</button>';
                    	} else if(full.rulesState == "1") {
                    		html += '<button type="button" class="SSbtn green_a" onclick="updateState(\'' + full.id + '\', 0)"><i class="fa fa-paste"></i>启用</button>';
                    	}
                    }
                    html += '<button type="button" class="SSbtn grey_b" onclick="history(\'' + full.id + '\')"><i class="fa fa-paste"></i>历史记录</button>';
                    return html;
                }
            },
            {
            	mDataProp: "cityName", 
                sTitle: "所属城市", 
                sClass: "center", 
                sortable: true, 
                mRender:function(data,type,full){
            	           return full.cityName+"("+full.shortName+")";
             }
            },
            {
            	mDataProp: "useType", 
            	sTitle: "用车类型", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
            		switch(full.useType) {
						case 0: return "预约用车"; break;
						case 1: return "即刻用车"; break;
						default: return "/";
            	}
            	}
            },
            {
            	mDataProp: "rulesState", 
            	sTitle: "规则状态", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
            		switch(full.rulesState) {
					case "0": return "启用"; break;
					case "1": return "禁用"; break;
					default: return "/";
            	    }
            	}
            },
            {
            	mDataProp: "sendType", 
            	sTitle: "派单方式", 
                Class: "center", 
                sortable: true,
                mRender:function(data,type,full){
            		switch(full.sendType) {
					case 0: return "强派"; break;
					case 1: return "抢派"; break;
					case 2: return "抢单"; break;
					case 3: return "纯人工"; break;
					default: return "/";
            	    }
            	}
            },
            {
            	mDataProp: "sendModel", 
            	sTitle: "派单模式", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
            		switch(full.sendModel) {
					case 0: return "系统"; break;
					case 1: return "系统+人工"; break;
					default: return "/";
            	    }
            	}
            },
            {
            	mDataProp: "carsInterval", 
            	sTitle: "约车时限(分钟)", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
      	          if(full.useType==1)
      	        	  return "/";
      	          else 
      	        	  return full.carsInterval;
               }	
            },
            {
            	mDataProp: "systemSendInterval", 
            	sTitle: "系统派单时限(分钟)", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
        	          if(full.sendType==3)
        	        	  return "/";
        	          else
        	        	  return full.systemSendInterval;
                }	
            },
            {
            	mDataProp: "specialInterval", 
            	sTitle: "特殊派单时限(秒)", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
            		if(full.useType==1 && full.sendType==2)
            			return full.specialInterval;
            		else
        	        	  return "/";
        	        	  
                 }	
            },
            {
            	mDataProp: "driverSendInterval", 
            	sTitle: "抢单时限(秒)", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
        	          if(full.sendType !=2)
        	        	  return "/";
        	          else
        	        	  return full.driverSendInterval;
                 }	
            },
            {
            	mDataProp: "personSendInterval", 
            	sTitle: "人工指派时限(分钟)", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
        	          if(full.sendModel==0 && full.sendType !=3)//系统且非人工
        	        	  return "/";
        	          else
        	        	  return full.personSendInterval;
                 }	
            },
            {
            	mDataProp: "initSendRadius", 
            	sTitle: "初始派单半径(公里)", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
        	          if(full.useType==0 || full.sendType== 3)//预约或者人工
        	        	  return "/";
        	          else
        	        	  return full.initSendRadius;
                }	
            },
            {
            	mDataProp: "maxSendRadius", 
            	sTitle: "最大派单半径(公里)", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
        	          if(full.sendType==3)
        	        	  return "/";
        	          else
        	        	  return full.maxSendRadius;
                }	
            },
            {
            	mDataProp: "increRatio", 
            	sTitle: "半径递增比", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){//人工
      	          if(full.useType==0 || full.sendType==3)
      	        	  return "/";
      	          else
      	        	  return full.increRatio+"%";
              }	
            },
            {
            	mDataProp: "vehicleUpgrade", 
            	sTitle: "服务车型", 
            	sClass: "center", 
            	sortable: true, 
            	mRender:function(data,type,full){
            		switch(full.vehicleUpgrade) {
					case 0: return "限同级车型"; break;
					case 1: return "可升级车型"; break;
					default: return "/";
            	    }
            	}
            },
            {
            	mDataProp: "pushNum", 
            	sTitle: "推送数量(人次)", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
             		if(full.pushNumLimit == null || full.pushNumLimit =="" || full.pushNumLimit == "0")
             			return "/";
             		else
             			return full.pushNum;
            	}
            },
            {
            	mDataProp: "pushLimit", 
            	sTitle: "推送限制", 
            	sClass: "center", 
            	sortable: true,
            	mRender:function(data,type,full){
            		switch(full.pushLimit) {
					case "0": return "存在抢单窗口，不推单"; break;
					case "1": return "存在抢单窗口，推单"; break;
					default: return "/";
            	    }
            	}
            },
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

function initSelectCity() {
	$("#city").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "SendRules/GetCityListById",
			dataType : 'json',
			data : function(term, page) {
				return {
					cityName: term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "city", "value": $("#city").val() },
		{ "name": "useType", "value": $("#usecartype").val() },
		{ "name": "sendType", "value": $("#sendtype").val() },
		{ "name": "sendModel", "value": $("#sendmodel").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 初始化查询(清空)
 */
function initSearch() {
	$("#city").select2("val", "");
	$("#usecartype").val("");
	$("#sendtype").val("");
	$("#sendmodel").val("");
	search();
}


/**
 * 新增
 */
function add() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "SendRules/EditRules";
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "SendRules/EditRules?id=" + id;
}

/**
 * 启用、禁用(0-启用,1-禁用)
 * @param id
 * @param state
 */
function updateState(id, state) {
	var data = {
		id : id,
		rulesState : state
	};
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: "SendRules/UpdateSendRules",
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.options.onHidden = function() {
					window.location.href = document.getElementsByTagName("base")[0].getAttribute("href")+"SendRules/Index";
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
 * 跳转网约车派单规则历史记录页面
 * @param id
 */
function history(id) {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "SendRules/SendRulesHistoryIndex/" + id;
}

/**
 * 初始化按钮
 */
function initBtn() {
	usertype = $("#usertype").val();
	if(null != usertype && usertype != 1) {//非管理员无权操作
		$("#addBtn").attr("disabled", "disabled");
		$("#addBtn").removeClass("blue").addClass("grey");
	}
}

