var dataGrid;
var orderObj = {
	orderInfo: null
};

/**
 * 页面初始化
 */
$(document).ready(function() {

	// 任何需要执行的js特效
	manualOrderdataGrid();
});

/**
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "grid",
        sAjaxSource: $("#baseUrl").val() + "driverVehicleRecord/taxiBindRecord",
        iLeftColumn: 1,
        userQueryParam: [{ "name": "vehicleId", "value": $("#vehicleid").val()},{ "name": "queryType", "value": "1"}],
        scrollX: true,
		iDisplayLength:6,
		columns: [
			{mDataProp: "createtimeStr", sTitle: "操作时间", sClass: "center", sortable: true },
			{mDataProp: "bindstate", sTitle: "操作类型", sClass: "center", sortable: true, },
			{mDataProp: "driverInfo", sTitle: "被操作司机信息", sClass: "center", sortable: true,
				mRender:function (data) {
					if (data != null&& data!="/") {
						var index = findIndex(data,"、",3);
						if (index >= 0) {
							return showToolTips(data,index);
						} else {
							return data;
						}
					} else {
						return "/";
					}
				} },
			{mDataProp: "unbindreason", sTitle: "解绑原因", sClass: "center", sortable: true ,
				mRender:function (data) {
					return showToolTips(data);
				}},
			{mDataProp: "nickname", sTitle: "操作人", sClass: "center", sortable: true },
			{mDataProp: "bindpersonnum", sTitle: "已绑定人数", sClass: "center", sortable: true },
			{mDataProp: "binddirverinfo", sTitle: "已绑定司机信息", sClass: "center", sortable: true,
				mRender:function (data) {
					if (data != null&& data!="/") {
						var index = findIndex(data,"、",3);
						if (index >= 0) {
							return showToolTips(data,index);
						} else {
							return data;
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
 * 显示长度限制
 * @param addr
 */
function limitLength(text) {
	if(null != text && text.length > 18) {
		return text.substr(0, 18) + "...";
	}
	return text;
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

//返回
function rebacke(){
	window.location="driverVehicleBind/taxibind/Index";
}
