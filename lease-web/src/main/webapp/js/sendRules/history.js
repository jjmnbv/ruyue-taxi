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
	var msg = $("#cityname").val();
	if(usetype == 0) {
		msg += "预约用车";
	} else if(usetype == 1) {
		msg += "即刻用车";
	}
	$("#sendrules").text(msg);
}

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "SendRules/GetSendRulesHistoryByQuery",
        scrollX: true,
        userQueryParam:[{"name": "sendRulesId", "value": $("#sendrulesid").val()},{"name": "leasesCompanyId", "value": $("#leasescompanyid").val()}],
        language: {sEmptyTable: "暂无更过历史记录信息"},
        columns: [
            {
				mDataProp : "operateType",
				sTitle : "操作类型",
				sClass : "center",
				sortable : true,
				"mRender" : function(data, type, full) {
					switch(full.operateType) {
						case "0": return "新增操作";break;
						case "1": return "修改操作";break;
						case "2": return "启用操作";break;
						case "3": return "禁用操作";break;
						default: return "/";
					}
				}
			},
			{mDataProp : "operateTime", sTitle : "操作时间", sClass : "center", sortable : true},
			{mDataProp : "operateName", sTitle : "操作人", sClass : "center", sortable : true},
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
        	          if(full.sendModel==0 && full.sendType != 3)//系统且非人工
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
        	          if(full.useType==0 || full.sendType==3)//预约或者人工
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
             		if(full.pushNumLimit == null || full.pushNumLimit =="" ||full.pushNumLimit == "0")
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
            }
        ]
    };
	dataGrid = renderGrid(gridObj);
}

