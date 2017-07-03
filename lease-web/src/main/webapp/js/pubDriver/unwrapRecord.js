var unwrapRecordDataGrid;
/**
 * 页面初始化
 */
$(function() {
	unwrapRecordDataGrid();
});

/**
 * 表格初始化
 */
function unwrapRecordDataGrid() {
	var gridObj = {
		id : "unwrapRecordDataGrid",
		sAjaxSource : "PubDriverVehicleRef/GetUnwrapRecordByQuery",
		iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
		columns : [ {
			mDataProp : "jobNum",
			sTitle : "资格证号",
			sClass : "center",
			visible : true
		}, {
			mDataProp : "vehicleInfo",
			sTitle : "车辆信息",
			sClass : "center",
			sortable : true
		}, {
			mDataProp : "createTime",
			sTitle : "绑定时间",
			sClass : "center",
			sortable : true,
			mRender : function(data, type, full) {
				if (data != null) {
					return changeToDate(data);
				} else {
					return "";
				}
			}
		}, {
			mDataProp : "creater",
			sTitle : "绑定人",
			sClass : "center",
			sortable : true
		}, {
			mDataProp : "updateTime",
			sTitle : "解除绑定时间",
			sClass : "center",
			sortable : true,
			mRender : function(data, type, full) {
				if (data != null) {
					return changeToDate(data);
				} else {
					return "";
				}
			}
		}, {
			mDataProp : "unBindReason",
			sTitle : "解除原因",
			sClass : "center",
			sortable : true
		}, {
			mDataProp : "updater",
			sTitle : "解除绑定人",
			sClass : "center",
			sortable : true
		} ]
	};
	unwrapRecordDataGrid = renderGrid(gridObj);
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
function callBack() {
	// history.go(-1);
//	history.back();
	window.location.href = base+"PubDriver/Index";
}