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
    $("#queryDriverId").select2({
        placeholder: "姓名/手机号2",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "PubDriver/listPubDriverBySelect",
            dataType: 'json',
            data: function (term, page) {
                $(".datetimepicker").hide();

                var param = {
                    queryText: term,
                    vehicletype:"0",
                    jobstatus:"0"
                }
                return param;
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });

    $("#queryDriverNum").select2({
        placeholder: "司机资格证号1",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "PubDriver/listPubDriverBySelectJobNum",
            dataType: 'json',
            data: function (term, page) {
                $(".datetimepicker").hide();

                var param = {
                    queryText: term,
                    vehicletype:"0",
                    jobstatus:"0"
                }
                return param;
            },
            results: function (data, page) {
                for(var i=0;i<data.length;i++){
                    data[i].text = data[i].jobnum;
                }
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
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: true },
            {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: true },
            {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
            {mDataProp: "vechicleInfo", sTitle: "车辆信息", sClass: "center", sortable: true },
            {mDataProp: "belongLeasecompanyName", sTitle: "归属车企", sClass: "center", sortable: true },
            {mDataProp: "bindstate", sTitle: "操作类型", sClass: "center", sortable: true },
            {mDataProp: "createtimeStr", sTitle: "操作时间", sClass: "center", sortable: true },
            {mDataProp: "unbindreason", sTitle: "操作原因", sClass: "center", sortable: true,
                mRender:function (data) {
                    return showToolTips(data,20);
                } },
            {mDataProp: "nickname", sTitle: "操作人", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}


/**
 * 查询
 */
function search() {
    var driverid = !!$("#queryDriverId").select2('data')?$("#queryDriverId").select2('data').id:"";
    var jobNum = !!$("#queryDriverNum").select2('data')?$("#queryDriverNum").select2('data').text:"";
    var conditionArr = [
		{ "name": "driverId", "value":driverid},
		{ "name": "jobNum", "value": jobNum},
		{ "name": "timeStart", "value": $("#minUseTime").val() },
		{ "name": "timeEnd", "value": $("#maxUseTime").val() },
        { "name": "bindStatus", "value": $("#bindStatus").val()},
        { "name": "queryType", "value": "1" },
        { "name": "belongLeasecompany", "value": $("#belongLeasecompany").val() }
	];

    if(driverid==""&&jobNum==""){
        toastr.warning("司机信息和资格证号信息需必填一项", "提示");
        return;
    }
    dataGrid.fnSearch(conditionArr);

}

/**
 * 弹出选择接班司机框
 * @param id
 */
function shownotcashdiv(id){
	$("#pendingId").val(id);
	$("#notcashdiv").show();
    var data = {'id':id};
    var selObj = $("#plateNoProvince");
    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "driverShift/listBindDrivers",
        data: data,
        // contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            var message = result.message == null ? result : result.message;
            if (result.status == "0") {
                selObj.find("option").remove();
                selObj.append( "<option value=''>请选择</option>" );
                for(var i=0;i<result.data.length;i++){
                    var dataObj = result.data[i];
                    selObj.append( "<option value='"+dataObj.id+"'>"+dataObj.text+"</option>" );
                }
            } else {
                $("#notcashdiv").hide()
                toastr.error(message, "提示");
            }
            search();
        }
    });
}

/**
 * 清空
 */
function clearOptions(){
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	$("#bindStatus").val("");
	$("#queryDriverId").select2("val", "");
    $("#queryDriverNum").select2("val", "");
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

$("input:radio[name='shftType']").click(function(){
    if($(this).val()=='1'){
        /**
         * 选择交班司机处理
         */
        $("#plateNoProvince").removeAttr("disabled");
    }else if($(this).val()=='2'){
        /**
         * 选择车辆回收处理
         */
        $("#plateNoProvince").val("");
        $("#plateNoProvince").attr("disabled","disabled")
    }

});

//返回
function rebacke(){
    window.location="driverVehicleBind/carbind/Index";
}