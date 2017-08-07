var dtGrid;
var alarmCount;
$(function () {
    //初始化控件

    //初始化表格，界面打开直接查出数据
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
//绑定控件

//查询
function onSelect() {
    alarmCount = -1;
    dtGrid.fnDraw();
}

function getFnData() {

    var fromTime = $("#txtStarTime").val();
    var toTime = $("#txtEndTime").val();
    if (fromTime != null && fromTime != "" && fromTime != "undefined" && toTime != null && toTime != "" && toTime != "undefined"
        && fromTime > toTime) {
        toastr.warning("'开始时间起' 应早于或等于 '开始时间止'，请确认!");
        return;
    }

    var length = $("#selLength").val();
    var oData = [
        {"name": "startTime", "value": fromTime},
        {"name": "startTimeStop", "value": toTime},
        {"name": "timeRange", "value": length},
        {"name": "mileageRange", "value": $("#selMileageRange").val()},
        {"name": "plate", "value": $("#txtVehcPlate").val()},
        {"name": "departmentId", "value": $("#selDepartment").val()},
    ];
    return oData;
}

//重置
function onClear() {
    $("#txtStarTime").val("");
    $("#txtEndTime").val("");
    $("#txtVehcPlate").val("");
    $("#txtDriverName").val("");
    $("#selLength").val("")
    $("#selMileageRange").val("");
    $("#selDepartment").val("");
}
//导出
function onExport() {

    _loading.show();
    $.ajax({
        url: basePath + "AlarmElectronicLimit/Export",
        data: getFnData(),
        cache: false,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            debugger
            window.location.href = basePath + "Excel/download?path=" + data.path;
        },
        error: function (xhr, status, error) {
            _loading.hide();
            showerror(xhr.responseText);
        },
        complete: function (xhr, ts) {
            _loading.hide();
        }
    });


}

//初始化 电子围栏管理列表
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AlarmElectronicLimit/queryAlarmElectronicLimitList",
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "department", "sTitle": "所属部门"},
            {"mDataProp": "fenceName", "sTitle": "电子围栏名称"},
            {"mDataProp": "startTime", "sTitle": "开始时间"},
            {"mDataProp": "endTime", "sTitle": "结束时间"},
            {"mDataProp": "startOfViolation", "sTitle": "开始地址"},
            {"mDataProp": "endOfViolation", "sTitle": "结束地址"},
            {"mDataProp": "lengthOfViolation", "sTitle": "违规时长"},
            {"mDataProp": "illegalMileage", "sTitle": "违规里程(km)"},
            // {
            //     //自定义操作列
            //     "mDataProp": "cz",
            //     "sClass": "center",
            //     "sTitle": "详细信息",
            //     "bSearchable": false,
            //     "bStorable": false,
            //     "sWidth": 200,
            //     "render": function (data, type, row) {
            //         return '<a target="_blank"   href="/AlarmAreaLimit/AreaTravelTrack/' + row.id + '" class="btn default btn-xs blue"><img src="Content/img/icon/moveTrack.png" alt=""/>行驶轨迹</a>'
            //             + '&nbsp;<a target="_blank"   href="/AlarmAreaLimit/SelectTrack/' + row.id + '/' + row.id + '/' + row.VT_STATUS + '" class="btn default btn-xs blue"><img src="Content/img/icon/travelInfo.png" alt=""/>查看行程</a>'
            //             ;
            //     }
            // },

        ], "fnRowCallback": function (nRow, aData, iDisplayIndex) {
            $('td:eq(0)', nRow).html("<a data-toggle='abstractpopover' data-plates='" + aData.plate + "' rel='popover' data-placement='top' onmouseover='getAbstractByPlates(&#39;" + aData.plate + "&#39;)'>" + aData.plate + "</a>");
            var startOfViolation = aData.startOfViolation;
            if (startOfViolation.length > 15) {
                startOfViolation = startOfViolation.substring(0, 15) + "...";
            }
            $('td:eq(5)', nRow).html("<div data-toggle='popover' rel='popover' data-placement='top' data-content='" + aData.startOfViolation + "'>" + startOfViolation + "</div>");
            $(document).ajaxComplete(function () {
                $("[data-toggle='popover']").popover({
                    template: '<div class="popover" style="position: fixed;width:350px;z-index:999999"><div class="arrow"></div><div class="popover-inner"><div class="popover-content"><p></p></div></div></div>',
                    trigger: 'hover',
                    html: 'true',
                });
            })
            var endOfViolation = aData.endOfViolation;
            if (endOfViolation.length > 15) {
                endOfViolation = endOfViolation.substring(0, 15) + "...";
            }
            $('td:eq(6)', nRow).html("<div data-toggle='popover' rel='popover' data-placement='top' data-content='" + aData.endOfViolation + "'>" + endOfViolation + "</div>");
            $(document).ajaxComplete(function () {
                $("[data-toggle='popover']").popover({
                    template: '<div class="popover" style="position: fixed;width:350px;z-index:999999"><div class="arrow"></div><div class="popover-inner"><div class="popover-content"><p></p></div></div></div>',
                    trigger: 'hover',
                    html: 'true',
                });
            })
        },
        "fnServerData": function (sSource, aoData, fnCallback) {
            $.ajax({
                "dataType": 'json',
                "type": "GET",
                "url": sSource,
                "data": aoData,
                "cache": false,
                "success": fnCallback,
                "complete": function (obj) {
                    setVehcAbstract();
                },
                "timeout": __Constant.ajaxTimeout,
                "error": function (xhr, status, error) {
                    showerror(xhr.responseText);
                    dtGrid._fnProcessingDisplay(false);
                    return;
                }
            });
        },
        "fnDrawCallback": function (oSettings) {
            if (alarmCount > 0)
                $(".pagination").append("<li ><a onclick=\"onSelect()\" class=\"news-block-btn\" href=\"javascript:void(0)\">查看全部</a></li>");
        }

    };
    dtGrid = renderGrid(gridObj);
}


