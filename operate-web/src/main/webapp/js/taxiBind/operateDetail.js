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
        sAjaxSource: "TaxiBind/GetOperateRecordByVehicleid",
        userQueryParam: [{ "name": "vehicleid", "value":$("#vehicleid").val()}],
        //iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无人车绑定操作纪录"
        },
        columns: [

			{mDataProp: "createtime", sTitle: "操作时间", sClass: "center", sortable: true },
			{mDataProp: "bindstate", sTitle: "操作类型", sClass: "center", sortable: true, 
				mRender : function(data, type, full) {
					if (data == "0") {
						return "解绑";
					} else if (data == "1") {
						return "绑定";
					} else {
						return "/";
					}
				}
			},
			{mDataProp: "name", sTitle: "被操作司机信息", sClass: "center", sortable: true, 
				mRender : function(data, type, full) {
					if (full.bindstate == "0") {
						if (full.unbinddirverinfo != null) {
							var index = findIndex(full.unbinddirverinfo,"、",3);
							if (index >= 0) {
								return showToolTips(full.unbinddirverinfo,index);
							} else {
								return full.unbinddirverinfo;
							} 
						} else {
							return "/";
						}
					} else if (full.bindstate == "1") {
						if (full.name == null) {
							return full.phone;
						} else {
							return full.name + " " + full.phone;
						}
					} else {
						return "/";
					}
				}
			},
			{mDataProp: "unbindreason", sTitle: "解绑原因", sClass: "center", sortable: true, 
				mRender : function(data, type, full) {
					if (full.bindstate == "0") {
						return showToolTips(full.unbindreason,30);
					} else {
						return "/";
					}
				}
			},
			{mDataProp: "operator", sTitle: "操作人", sClass: "center", sortable: true },
			{mDataProp: "bindpersonnum", sTitle: "已绑定人数", sClass: "center", sortable: true },
			{mDataProp: "binddirverinfo", sTitle: "已绑定司机信息", sClass: "center", sortable: true, 
				mRender : function(data, type, full) {
					if (data != null) {
						var index = findIndex(full.binddirverinfo,"、",3);
						if (index >= 0) {
							return showToolTips(full.binddirverinfo,index);
						} else {
							return full.binddirverinfo;
						}
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
 * 长度显示控制
 */
function findIndex(str,cha,num) {
    var x=str.indexOf(cha);
    for(var i=0;i<num;i++) {
    	if (x == -1) {
    		return x;
    	}
        x=str.indexOf(cha,x+1);
    }
    return x;
}

/**
 * 返回
 */
function callBack() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "TaxiBind/Index";
}


