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
            url: $("#baseUrl").val() + "PubDriver/listPubDriverBySelect",
            dataType: 'json',
            data: function (term, page) {
            	$(".datetimepicker").hide();

				var param = {
					queryText: term,
					vehicletype:"1",
                    jobstatus:"0"
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
        sAjaxSource: $("#baseUrl").val() + "driverShift/listPending",
        iLeftColumn: 1,
        userQueryParam: [{name: "relievedtype", value: "1"}],
        scrollX: true,
        language: {
            sEmptyTable: "暂无交班申请记录"
        },
        columns: [
        	{
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 150,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                	orderObj.orderInfo = full;
                    var html = [];
                    html.push("<button type=\"button\" class=\"SSbtn red\" onclick=\"shownotcashdiv('" + full.id + "')\"><i class=\"fa fa-paste\"></i>人工指派</button>");
                    return html.join("");
                }
            },
            {mDataProp: "platenoStr", sTitle: "车牌号", sClass: "center", sortable: true },
            {mDataProp: "cityStr", sTitle: "登记城市", sClass: "center", sortable: true },
            {mDataProp: "driverInfo", sTitle: "当班司机", sClass: "center", sortable: true },
            {mDataProp: "onlinetimeStr", sTitle: "在线时长", sClass: "center", sortable: true },
            {mDataProp: "applytimeStr", sTitle: "申请时间", sClass: "center", sortable: true },
            {mDataProp: "succeedDrivers", sTitle: "对班司机", sClass: "center", sortable: true,
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
                }}
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}


/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "plateNo", "value": $("#plateNo").val()},
		{ "name": "driverid", "value": !!$("#driverid").select2('data')?$("#driverid").select2('data').id:"" },
		{ "name": "applytimeStart", "value": $("#minUseTime").val() },
		{ "name": "applytimeEnd", "value": $("#maxUseTime").val() },
		{ "name": "relievedtype", "value": "1"}
	];
	dataGrid.fnSearch(conditionArr);

    //
    // var resultObj = {};
    // resultObj.title="测试弹窗";
    // resultObj.id="50403690-A875-4A59-A12B-D2B337C90CA6";
    // resultObj.platenoStr="test";
    // resultObj.onlinetimeStr="11分10秒";
    // this.top.window.showPendingPop(resultObj);

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
                //默认选中交接班类型
                $("input[name='shftType']").get(0).click();
                selObj.find("option").remove();
                selObj.append( "<option value=''>请选择</option>" );
                for(var i=0;i<result.data.length;i++){
                    var dataObj = result.data[i];
                    selObj.append( "<option value='"+dataObj.driverID+"'>"+dataObj.text+"</option>" );
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
 * 不予提现取消
 */
function cancelNotDiv(){
    $("#notcashdiv").hide();
}


/**
 * 交接班操作
 * @param driverId
 * @param pendingId
 */
function processed(){
    var pendingId = $('#pendingId').val();
    var driverId = $("#plateNoProvince").val();
    var driverStr = $("#plateNoProvince").find("option:selected").text();
    var type = $("input[name='shftType']:checked").val();
    //初始化参数
    var data = {pendingId: pendingId,shifttype:type};

    var str = '';
    if(!type){
        toastr.warning("请选择交班类型", "提示");
        return;
    }
    if(type==0 && !driverId){
        toastr.warning("请选择接班司机", "提示");
        return ;
    }
    //交接班
    if(type==0){
        str = "确定进行交接班？"
        data.relieveddriverInfo = driverStr;
        data.relieveddriverid=driverId;
    }else{
        //车辆回收
        str = "确定进行车辆回收？"
    }


    Zconfirm(str,function(){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "driverShift/processd",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (result) {
                var message = result.message == null ? result : result.message;
                if (result.status == "0") {
                    toastr.success(message, "提示");
                } else {
                    toastr.error(message, "提示");
                }
                $("#notcashdiv").hide();
                search();
            }
        });
    });

}

/**
 * 清空
 */
function clearOptions(){
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	$("#plateNo").val("");
	$("#driverid").select2("val", "");
	search();
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
    if($(this).val()=='0'){
        /**
         * 选择交班司机处理
         */
        $("#plateNoProvince").removeAttr("disabled");
    }else if($(this).val()=='1'){
        /**
         * 选择车辆回收处理
         */
        $("#plateNoProvince").val("");
        $("#plateNoProvince").attr("disabled","disabled")
    }

});
