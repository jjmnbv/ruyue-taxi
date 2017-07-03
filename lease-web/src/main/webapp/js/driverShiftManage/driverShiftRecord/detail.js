var dataGrid;
var orderObj = {
	orderInfo: null
};

/**
 * 页面初始化
 */
$(function () {
	manualOrderdataGrid();
	initForm();
});

function initForm() {
	$("#driverid").select2({
		placeholder: "当班司机",
		minimumInputLength: 0,
		allowClear: true,
		ajax: {
			url: $("#baseUrl").val() + "driverShift/listShiftRecordDriver",
			dataType: 'json',
			data: function (term, page) {
				$(".datetimepicker").hide();
				var param = {
					queryText: term,
					vehicleid:vehicleObj.id,
					shiftType:1
				}
				return param;
			},
			results: function (data, page) {
				return { results: data };
			}
		}
	});


	$("#relievedid").select2({
		placeholder: "接班司机",
		minimumInputLength: 0,
		allowClear: true,
		ajax: {
			url: $("#baseUrl").val() + "driverShift/listShiftRecordDriver",
			dataType: 'json',
			data: function (term, page) {
				$(".datetimepicker").hide();

				var param = {
					queryText: term,
					vehicleid:vehicleObj.id,
					shifitType:2
				}
				return param;
			},
			results: function (data, page) {
				return { results: data };
			}
		}
	});

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
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "grid",
        sAjaxSource: $("#baseUrl").val() + "driverShift/listProcessed",
        iLeftColumn: 1,
        userQueryParam: [{name: "vehicleid", value: vehicleObj.id},{name: "queryType", value: "2"}],
        scrollX: true,
		language: {
			sEmptyTable: "暂无车辆交接记录"
		},
        columns: [
        	{
                //自定义操作列
                "mDataProp": "rownum",
                "sClass": "center",
                "sTitle": "序号",
                "sWidth": 150,
                "bSearchable": false,
                "sortable": false
            },
			{mDataProp: "ondutydriverinfo", sTitle: "交班司机", sClass: "center", sortable: true },
			{mDataProp: "onlinetimeStr", sTitle: "在线时长", sClass: "center", sortable: true },
			{mDataProp: "shifttype", sClass: "center", sTitle: "交接类型"},
			{mDataProp: "relieveddriverinfo", sTitle: "接班司机", sClass: "center", sortable: true },
			{mDataProp: "relievedtype", sTitle: "交班类型", sClass: "center", sortable: true},
			{mDataProp: "processpersonname", sTitle: "处理人", sClass: "center", sortable: true },
			{mDataProp: "processtimeStr", sTitle: "交接时间", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}


/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "vehicleid", "value": vehicleObj.id},
		{ "name": "ondutydriverid", "value": !!$("#driverid").select2('data')?$("#driverid").select2('data').id:"" },
		{ "name": "relieveddriverid", "value": !!$("#relievedid").select2('data')?$("#relievedid").select2('data').id:"" },
		{ "name": "processtimeStart", "value": $("#minUseTime").val() },
		{ "name": "processtimeEnd", "value": $("#maxUseTime").val() },
		{ "name": "shifttype", "value": $("#shftType").val() },
		{ "name": "relievedtype", "value": $("#relievedtype").val()},
		{ "name": "queryType", "value": "2"} //查询类型为2 查询交接班、车辆回收、人工指定
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 清空
 */
function clearOptions(){
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	$("#vehicleid").select2("val", "");
	$("#driverid").select2("val", "");
	$("#relievedid").select2("val", "");
	$("#shftType option:first").prop("selected", 'selected');
	$("#relievedtype option:first").prop("selected", 'selected');
	search();
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


//返回
function rebacke(){
	window.location="driverShift/record/Index";
}
