var dtGrid;

$(function () {

    initGrid();  //初始化表格

    //绑定日期
    $('.datetimepicker').datetimepicker({
        autoclose: true,
        language: "zh-CN",
        format: "yyyy-mm-dd hh:ii",
        // clearBtn: true,
        minView: "day"
    });

    $("#btnSearch").click(function () {
        dtGrid.fnSearch(getFnData());
    });

    //清空查询条件
    $("#btnClear").click(function () {
        $("#plate").val("");
        $("#imei").val("");
        $("#selDepartment").val("");
        $("#alarmTime").val("");
        $("#alarmTimeStop").val("");
        $("#state").val("");
    });

})


//绑定查询数据
function getFnData() {

    var fromTime = $("#alarmTime").val();
    var toTime = $("#alarmTimeStop").val();
    if (fromTime != null && fromTime != "" && fromTime != "undefined" && toTime != null && toTime != "" && toTime != "undefined"
        && fromTime > toTime) {
        toastr.warning("'开始时间' 应早于或等于 '结束时间'，请确认!");
        return;
    }
    var rcId = -1;
    var oData = [
        {"name": "plate", "value": $("#plate").val()},
        {"name": "imei", "value": $("#imei").val()},
        {"name": "departmentId", "value": $("#selDepartment").val()},
        {"name": "alarmTime", "value": $("#alarmTime").val()},
        {"name": "alarmTimeStop", "value": $("#alarmTimeStop").val()},
        {"name": "state", "value": $("#state").val()},
        {"name": "companyId", "value": rcId}
    ];
    return oData;
}

//初始化列表
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AlarmLowVoltage/getAlarmPowerFailureByPage",
        columns: [
            {"mDataProp": "imei", "sTitle": "IMEI"},
            {"mDataProp": "department", "sTitle": "所属部门"},
            {"mDataProp": "alarmTime", "sTitle": "报警时间"},
            {"mDataProp": "releaseTime", "sTitle": "解除时间 "},
            {"mDataProp": "voltage", "sTitle": "电压(V)"},
            {"mDataProp": "alarmAddress", "sTitle": "报警地址", "sWidth": "200px"},
            {"mDataProp": "state", "sTitle": "状态"},
        ], "fnRowCallback": function (nRow, aData, iDisplayIndex) {
            $('td:eq(0)', nRow).html("<a data-toggle='abstractpopover' data-plates='" + aData.plate + "' rel='popover' data-placement='top' onmouseover='getAbstractByPlates(&#39;" + aData.plate + "&#39;)'>" + aData.plate + "</a>");
            var alarmAddress = aData.alarmAddress;
            if (alarmAddress.length > 10) {
                alarmAddress = alarmAddress.substring(0, 10) + "...";
            }
            $('td:eq(5)', nRow).html("<div data-toggle='popover' rel='popover' data-placement='top' data-content='" + aData.alarmAddress + "'>" + alarmAddress + "</div>");
            $(document).ajaxComplete(function () {
                $("[data-toggle='popover']").popover({
                    template: '<div class="popover" style="position: fixed;width:350px;z-index:999999"><div class="arrow"></div><div class="popover-inner"><div class="popover-content"><p></p></div></div></div>',
                    trigger: 'hover',
                    html: 'true',
                });
            })

        }

    };
    dtGrid = renderGrid(gridObj);
}



	 
	