var dtGrid;

$(function () {
    bind();// 绑定时间控件

    initGrid();  //初始化表格

    //绑定导出事件
    $("#btnExport").click(function () {
        window.location.href = basePath
            + "AlarmSpeed/Export?" + "plate="
            + $("#plate").val() + "&imei=" + $("#imei").val() + "&departmentId=" + $("#company").val()
            + "&startTime=" + $("#startTime").val() + "&startTimeStop=" + $("#startTimeStop").val()
            + "&timeRange=" + $("#timeRange").val();
    });

    $("#btnSearch").click(function () {

        dtGrid.fnSearch(getFnData());
    });

    //清空查询条件
    $("#btnClear").click(function () {
        $("#startTime").val("");
        $("#startTimeStop").val("");
        $("#timeRange").val("");
        $("#plate").val("");
        $("#imei").val("");
        $("#postage").val("");
        $("#company").val("");
    });

})

function getFnData() {

    var fromTime = $("#startTime").val();
    var toTime = $("#startTimeStop").val();
    if (fromTime != null && fromTime != "" && fromTime != "undefined" && toTime != null && toTime != "" && toTime != "undefined"
        && fromTime > toTime) {
        toastr.warning("'开始时间起' 应早于或等于 '开始时间止'，请确认!");
        return;
    }

    var rcId = -1;
    var companydeptCode = $("#company").val();


    var oData = [
        {"name": "startTime", "value": $("#startTime").val()},
        {"name": "startTimeStop", "value": $("#startTimeStop").val()},
        {"name": "timeRange", "value": $("#timeRange").val()},
        {"name": "plate", "value": $("#plate").val()},
        {"name": "imei", "value": $("#imei").val()},
        {"name": "serviceStatus", "value": $("#serviceStatus").val()},
        {"name": "departmentId", "value": companydeptCode},
        {"name": "companyId", "value": rcId}
    ];
    return oData;
}


//时间控件
function bind() {
    if (jQuery().datetimepicker) {
        $('.datetimepicker').datetimepicker({
            autoclose: true,
            language: "zh-CN",
            format: "yyyy-mm-dd hh:ii",
            clearBtn: true,
            minView: "day"
        });
    }
}


function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AlarmSpeed/getAlarmSpeedByPage",
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "imei", "sTitle": "IMEI"},
            {"mDataProp": "department", "sTitle": "服务车企"},
            {"mDataProp": "startTime", "sTitle": "开始时间"},
            {"mDataProp": "endTime", "sTitle": "结束时间"},
            {"mDataProp": "overspeedTime", "sTitle": "超速时长"},
            {"mDataProp": "overspeed", "sTitle": "超速里程(km)"},
            {"mDataProp": "address", "sTitle": "地址"},
        ],
        fnRowCallback: function (nRow, aData, iDisplayIndex) {
            // $('td:eq(0)', nRow).html("<a data-toggle='abstractpopover' data-plates='" + aData.plate + "' rel='popover' data-placement='top' onmouseover='getAbstractByPlates(&#39;" + aData.plate + "&#39;)'>" + aData.plate + "</a>");

            $('td:eq(7)', nRow).html("<div  title='" + aData.address + "'>" + aData.address + "</div>");
            var address = aData.address;
            if (address.length > 10) {
                address = address.substring(0, 10) + "...";
            }
            $('td:eq(7)', nRow).html("<div data-toggle='popover' rel='popover' data-placement='top' data-content='" + aData.address + "'>" + address + "</div>");
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






	 