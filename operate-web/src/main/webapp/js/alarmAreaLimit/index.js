var dtGrid;
var alarmCount;
$(function () {

    //绑定日期
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


    initGrid();
});

//查询
function onSelect() {
    alarmCount = -1;
    dtGrid.fnDraw();
}


//获取查询数据
function getFnData() {
    var fromTime = $("#startTime").val();
    var toTime = $("#endTime").val();
    if (fromTime != null && fromTime != "" && fromTime != "undefined" && toTime != null && toTime != "" && toTime != "undefined"
        && fromTime > toTime) {
        toastr.warning("'开始时间' 应早于或等于 '结束时间'，请确认!");
        return;
    }

    var rcId = -1;
    //查询条件
    var oData = [
        {"name": "plate", "value": $("#plate").val()},
        {"name": "imei", "value": $("#imei").val()},
        {"name": "startTime", "value": $("#startTime").val()},
        {"name": "endTime", "value": $("#endTime").val()},
        {"name": "timeRange", "value": $("#timeRange").val()},
        {"name": "outOfBounds", "value": $("#outOfBounds").val()},
        {"name": "departmentId", "value": $("#selDepartment").val()},
        {"name": "companyId", "value": rcId}
    ];
    return oData;
}

//重置
function onClear() {
    $("#startTime").val("");
    $("#endTime").val("");
    $("#plate").val("");
    $("#timeRange").val("");
    $("#imei").val("")
    $("#outOfBounds").val("");
    $("#selDepartment").val("");
}

//初始化时间栅栏管理列表
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AlarmAreaLimit/getAlarmAreaLimitByPage",
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "imei", "sTitle": "IMEI"},
            {"mDataProp": "department", "sTitle": "车辆所属"},
            {"mDataProp": "startTime", "sTitle": "开始时间"},
            {"mDataProp": "endTime", "sTitle": "结束时间"},
            {"mDataProp": "lengthOfViolation", "sTitle": "违规时长"},
            {"mDataProp": "outOfBoundsMileage", "sTitle": "越界里程 "},
            {"mDataProp": "startOfViolation", "sTitle": "违规开始地点"},
            {"mDataProp": "endOfViolation", "sTitle": "违规结束地点"},
            {
                //自定义操作列
                "mDataProp": "cz",
                "sClass": "center",
                "sTitle": "详细信息",
                "bSearchable": false,
                "bStorable": false,
                "sWidth": 200,
                "render": function (data, type, row) {
                    return '<a target="_blank"   href="/AlarmAreaLimit/AreaTravelTrack/' + row.id + '" class="btn default btn-xs blue"><img src="Content/img/icon/moveTrack.png" alt=""/>行驶轨迹</a>'
                        + '&nbsp;<a target="_blank"   href="/AlarmAreaLimit/SelectTrack/' + row.id + '/' + row.id + '/' + row.VT_STATUS + '" class="btn default btn-xs blue"><img src="Content/img/icon/travelInfo.png" alt=""/>查看行程</a>'
                        ;
                }
            },

        ], "fnRowCallback": function (nRow, aData, iDisplayIndex) {
            // $('td:eq(0)', nRow).html("<a data-toggle='abstractpopover' data-plates='" + aData.AAL_PLATES + "' rel='popover' data-placement='top' onmouseover='getAbstractByPlates(&#39;" + aData.AAL_PLATES + "&#39;)'>" + aData.AAL_PLATES + "</a>");
            //文件名增加悬浮窗
            var startOfViolation = aData.startOfViolation;
            if (startOfViolation.length > 10) {
                startOfViolation = startOfViolation.substring(0, 10) + "...";
            }
            $('td:eq(7)', nRow).html("<div data-toggle='popover' rel='popover' data-placement='top' data-content='" + aData.startOfViolation + "'>" + startOfViolation + "</div>");
            $(document).ajaxComplete(function () {
                $("[data-toggle='popover']").popover({
                    template: '<div class="popover" style="position: fixed;width:350px;z-index:999999"><div class="arrow"></div><div class="popover-inner"><div class="popover-content"><p></p></div></div></div>',
                    trigger: 'hover',
                    html: 'true',
                });
            })
            var endOfViolation = aData.endOfViolation;
            if (endOfViolation.length > 10) {
                endOfViolation = endOfViolation.substring(0, 10) + "...";
            }
            $('td:eq(8)', nRow).html("<div data-toggle='popover' rel='popover' data-placement='top' data-content='" + aData.endOfViolation + "'>" + endOfViolation + "</div>");
            $(document).ajaxComplete(function () {
                $("[data-toggle='popover']").popover({
                    template: '<div class="popover" style="position: fixed;width:350px;z-index:999999"><div class="arrow"></div><div class="popover-inner"><div class="popover-content"><p></p></div></div></div>',
                    trigger: 'hover',
                    html: 'true',
                });
            })
        },

    };
    dtGrid = renderGrid(gridObj);
}

