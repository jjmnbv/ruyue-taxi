var dataGrid;
var orderObj = {
	orderInfo: null
};

var leasecompanySet;
/**
 * 页面初始化
 */
$(function () {
	manualOrderdataGrid();
	initForm();
	initBelongLeasecompany();
});

function initForm() {

	$("#vehicleId").select2({
		placeholder: "车架号",
		minimumInputLength: 0,
		allowClear: true,
		ajax: {
			url: $("#baseUrl").val() + "Vehicle/listVehicleBySelect",
			dataType: 'json',
			data: function (term, page) {
				$(".datetimepicker").hide();

				var param = {
					queryText: term,
					vehicletype:'0'

				}
				return param;
			},
			results: function (data, page) {
				return { results: data};
			}
		}
	});


	$("#plateNo").select2({
		placeholder: "车牌号",
		minimumInputLength: 0,
		allowClear: true,
		ajax: {
			url: $("#baseUrl").val() + "Vehicle/listVehicleByPlateno",
			dataType: 'json',
			data: function (term, page) {
				$(".datetimepicker").hide();

				var param = {
					queryText: term,
					vehicletype:'0'

				}
				return param;
			},
			results: function (data, page) {
				return { results: data};
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

function initBelongLeasecompany(){
    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "driverVehicleBind/getBelongLeasecompany",
        data: "{}",
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            var message = result.message == null ? result : result.message;
            if (result.status == "0") {
            	leasecompanySet = new Set();
                for (var i = 0; i < result.data.length; i++) {
                    var dataObj = result.data[i];
                    if(dataObj.id!=null &&dataObj.id!='') {
                    	leasecompanySet.add("<option value='" + dataObj.id + "'>" + dataObj.text + "</option>")
                    }
                }
                loadLeasecompany(leasecompanySet);
            }
        }
    });

}

/**
 * 加载归属车企下拉框
 */
function loadLeasecompany(setObj){
    var belongLeasecompanyObj = $("#belongLeasecompany");
    belongLeasecompanyObj.find("option").remove();
    belongLeasecompanyObj.append( "<option value=''>全部</option>" );
    setObj.forEach(function (element, sameElement, set) {
    	belongLeasecompanyObj.append( element );
    });
}

/**
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "grid",
        sAjaxSource: $("#baseUrl").val() + "driverVehicleRecord/carBindRecord",
        iLeftColumn: 1,
        userQueryParam: [],
        scrollX: true,
		iDisplayLength:6,
		language: {
			sEmptyTable: "暂无人车绑定操作记录"
		},
		columns: [
			{mDataProp: "platenoStr", sTitle: "车牌号", sClass: "center", sortable: true },
			{mDataProp: "modelName", sTitle: "服务车型", sClass: "center", sortable: true },
			{mDataProp: "vinStr", sTitle: "车架号", sClass: "center", sortable: true },
			{mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: true },
			{mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
			{mDataProp: "belongLeasecompanyName", sTitle: "归属车企", sClass: "center", sortable: true },
			{mDataProp: "bindstate", sTitle: "操作类型", sClass: "center", sortable: true },
			{mDataProp: "createtimeStr", sTitle: "操作时间", sClass: "center", sortable: true },
			{mDataProp: "unbindreason", sTitle: "操作原因", sClass: "center", sortable: true,
				mRender:function (data) {
					return showToolTips(data,20);
				}},
			{mDataProp: "nickname", sTitle: "操作人", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}


/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "platenoStr", "value": !!$("#plateNo").select2('data')?$("#plateNo").select2('data').id:""},
		{ "name": "vehicleId", "value": !!$("#vehicleId").select2('data')?$("#vehicleId").select2('data').id:"" },
		{ "name": "timeStart", "value": $("#minUseTime").val() },
		{ "name": "timeEnd", "value": $("#maxUseTime").val() },
		{ "name": "bindStatus", "value": $("#bindStatus").val()},
		{ "name": "queryType", "value": "1" },
		{ "name": "belongLeasecompany", "value": $("#belongLeasecompany").val() }
	];

	if($("#plateNo").val()==""&&$("#vehicleId").select2('data')==null){
		toastr.warning("车牌号和车架号需必填一项", "提示");
		return;
	}
	dataGrid.fnSearch(conditionArr);
}

/**
 * 清空
 */
function clearOptions(){
	$("#plateNo").select2("val", "");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	$("#bindStatus").val("");
	$("#vehicleId").select2("val", "");
	$("#belongLeasecompany").val("");
	dataGrid.fnSearch([]);

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
	window.location="driverVehicleBind/carbind/Index";
}
