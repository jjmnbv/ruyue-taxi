var dtGrid;
$(function () {
    //绑定日期
    $('.datetimepicker').datetimepicker({
        autoclose: true,
        language: "zh-CN",
        format: "yyyy-mm-dd hh:ii",
        // clearBtn: true,
        minView: "day"
    });
    initGrid();
    $("#btnSearch").click(function () {
        dtGrid.fnSearch(getFnData());
    });
});

//查询
function onSelect() {
    alarmCount = -1;
    dtGrid.fnDraw();
}

function getFnData() {

    var fromTime = $("#txtFromTime").val();
    var toTime = $("#txtToTime").val();
    if (fromTime != null && fromTime != "" && fromTime != "undefined" && toTime != null && toTime != "" && toTime != "undefined"
        && fromTime > toTime) {
        toastr.warning("'开始时间' 应早于或等于 '结束时间'，请确认!");
        return;
    }

    var oData = [
        {"name": "alarmTime", "value": fromTime},
        {"name": "alarmTimeStop", "value": toTime},
        {"name": "plate", "value": $("#txtVehcPlate").val()},
        {"name": "imei", "value": ''},
        {"name": "departmentId", "value": $("#selDepartment").val()},
    ];
    return oData;
}

//重置
function onClear() {
    $("#txtFromTime").val("");
    $("#txtToTime").val("");
    $("#txtVehcPlate").val("");
    $("#txtDriverName").val("");
    $("#selDepartment").val("")

}

//初始化产品类型管理列表

function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AlarmWaterTemp/queryAlarmWaterTempList",
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "department", "sTitle": "所属部门"},
            {"mDataProp": "alarmTime", "sTitle": "报警时间"},
            {"mDataProp": "releaseTime", "sTitle": "解除时间"},
            {"mDataProp": "maxWaterTemp", "sTitle": "最高水温°C"},
            {"mDataProp": "alarmAddress", "sTitle": "报警地点"},
            {
                //自定义操作列
                "mDataProp": "cz",
                "sClass": "center",
                "sTitle": "详细信息",
                "bSearchable": false,
                "bStorable": false,
                "sWidth": 200,
                "render": function (data, type, row) {
                    return '<a target="_blank" href="AlarmWaterTemp/TempTrend/' + row.id + '" class="btn default btn-xs blue"><img src="img/trafficflux/icon/waterTemp.png" />水温趋势</a>' ;
                }
            },

        ],
        "fnRowCallback": function (nRow, aData, iDisplayIndex) {
            $('td:eq(0)', nRow).html("<a data-toggle='abstractpopover' data-plates='" + aData.plate + "' rel='popover' data-placement='top' onmouseover='getAbstractByPlates(&#39;" + aData.plate + "&#39;)'>" + aData.plate + "</a>");
        },
        "fnDrawCallback": function (oSettings) {
            if (alarmCount > 0)
                $(".pagination").append("<li ><a onclick=\"onSelect()\" class=\"news-block-btn\" href=\"javascript:void(0)\">查看全部</a></li>");
        }

    };
    dtGrid = renderGrid(gridObj);
}


