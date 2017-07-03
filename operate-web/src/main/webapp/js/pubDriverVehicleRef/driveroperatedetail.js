var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initSelect2();
	dateFormat();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "PubDriverVehicleRef/GetDriverOpRecordByQuery",
        iLeftColumn: 3,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无人车绑定操作纪录"
        },
        columns: [
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: true },
            {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: true },
            {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
            {mDataProp: "CLXX", sTitle: "车辆信息", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (full.vehiclemodelsname != null || full.plateno != null) {
                    	return full.vehiclemodelsname + ' ' + full.plateno;
                    } else {
                    	return '/';
                    }
                }
            },
            {mDataProp: "bindstate", sTitle: "操作类型", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (data != null) {
                    	if(full.bindstate == 0) {
                        	return "解绑";
                        } else if (full.bindstate == 1) {
                        	return "绑定";
                        }
                    } else {
                    	return '/';
                    }
                }
            },
            {mDataProp: "createtime", sTitle: "操作时间", sClass: "center", sortable: true },
            {mDataProp: "unbindreason", sTitle: "操作原因", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (data != null) {
                    	return showToolTips(data,30);
                    } else {
                    	return '/';
                    }
                }
            },
            {mDataProp: "operator", sTitle: "操作人", sClass: "center", sortable: true }

        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

function initSelect2() {
	$("#driver").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubDriverVehicleRef/GetDriverByNameOrPhone",
			dataType : 'json',
			data : function(term, page) {
				return {
					driver: term,
					vehicletype: 0
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
	
	$("#jobnum").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubDriverVehicleRef/GetDriverByJobnum",
			dataType : 'json',
			data : function(term, page) {
				return {
					jobnum: term
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

function dateFormat() {
	$('.searchDate').datetimepicker({
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        clearBtn: true
    });
}

/**
 * 查询
 */
function search() {
	var driver = $("#driver").val();
	var jobnum = $("#jobnum").val();
	if (driver == "" && jobnum == "") {
		toastr.error("司机信息和资格证号信息需必填一项", "提示");
		return;
	}
	
	searchmethod();
}

// 为了初始化
var key = "1";
var isclear = false;
/**
 * 查询数据
 */
function searchmethod() {
	var conditionArr = [
		{ "name": "driver", "value": $("#driver").val() },
		{ "name": "jobnum", "value": $("#jobnum").val() },
		{ "name": "bindstate", "value": $("#bindstate").val() },
		{ "name": "starttime", "value": $("#starttime").val() },
		{ "name": "endtime", "value": $("#endtime").val() },
	    { "name": "key", "value": key }
	];
	if (isclear) {
		dataGrid.fnSearch(conditionArr,"暂无人车绑定操作纪录");
	} else {
		dataGrid.fnSearch(conditionArr);
	}
	
	key = "1";
}

/**
 * 清空
 */
function clearSearch() {
	$("#driver").select2("val","");
	$("#jobnum").select2("val","");
	$("#bindstate").val("");
	$("#starttime").val("");
	$("#endtime").val("");
	key = "";
	
	isclear = true;
	searchmethod();
	isclear = false;
}

/**
 * 返回
 */
function callBack() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "PubDriverVehicleRef/Index";
}
