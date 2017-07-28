var dtGrid;
var alarmCount;
$(function () {

    initGrid();
    $('.datetimepicker').datetimepicker({
        autoclose: true,
        language: "zh-CN",
        format: "yyyy-mm-dd hh:ii",
        // clearBtn: true,
        minView: "day"
    });
//执行查询操作
    $("#btnSearch").click(function () {
        dtGrid.fnSearch(getFnData());
    });
});
//查询
function onSelect() {
    alarmCount = -1;
    dtGrid.fnDraw();
}

//获取查询条件数据
function getFnData() {
    var fromTime = $("#txtStarTime").val();
    var toTime = $("#txtEndTime").val();
    if (fromTime != null && fromTime != "" && fromTime != "undefined" && toTime != null && toTime != "" && toTime != "undefined"
        && fromTime > toTime) {
        toastr.warning("'开始时间' 应早于或等于 '结束时间'，请确认!");
        return;
    }

    var rcId = -1;

    var length = $("#selLength").val();
    var oData = [
        {"name": "startTime", "value": fromTime},
        {"name": "startTimeStop", "value": toTime},
        {"name": "mileageRange", "value": $("#selMileageRange").val()},
        {"name": "rcId", "value": rcId},
        {"name": "imei", "value": ""},
        {"name": "plate", "value": $("#txtVehcPlate").val()},
        {"name": "timeRange", "value": length},//时长范围
        {"name": "departmentId", "value": $("#selDepartment").val()}
    ];
    return oData;
}

//重置查询条件
function onClear() {
    $("#txtStarTime").val("");
    $("#txtEndTime").val("");
    $("#txtVehcPlate").val("");
    $("#txtDriverName").val("");
    $("#selLength").val("")
    $("#selMileageRange").val("");
    $("#selDepartment").val("");
}

//初始化时间栅栏管理列表

function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AlarmTimeLimit/queryAlarmTimeLimitList",
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "department", "sTitle": "所属部门"},
            {"mDataProp": "startTime", "sTitle": "开始时间"},
            {"mDataProp": "endTime", "sTitle": "结束时间"},
            {"mDataProp": "lengthOfViolation", "sTitle": "违规时长"},
            {"mDataProp": "illegalMileage", "sTitle": "违规里程(km)"},
            {
                //自定义操作列
                "mDataProp": "cz",
                "sClass": "center",
                "sTitle": "详细信息",
                "bSearchable": false,
                "bStorable": false,
                "sWidth": 200,
                "render": function (data, type, row) {
                    return '<a target="_blank"   href="/AlarmTimeLimit/TravelTrack/' + row.id + '" class="btn default btn-xs blue"><img src="img/trafficflux/icon/moveTrack.png" alt=""/>行驶轨迹</a>'
                        + '&nbsp;<a target="_blank"   href="/AlarmTimeLimit/SelectTrack/' + row.id + '/' + row.id + '/' + row.id + '" class="btn default btn-xs blue"><img src="img/trafficflux/icon/travelInfo.png" alt=""/>查看行程</a>';
                }
            }
        ]

    };
    dtGrid = renderGrid(gridObj);
}

