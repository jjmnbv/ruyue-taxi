var dtGrid;
var alarmCount;
var reoCar;

$(function () {

    $("#selAlarmType").multiselect({
        buttonWidth: '100%',
        includeSelectAllOption: true,
        selectAllText: '全选',
        allSelectedText: '全选'
    });
    // $("#selSerStatus").multiselect({
    //     buttonWidth: '100%',
    //     includeSelectAllOption: true,
    //     selectAllText: '全选',
    //     allSelectedText: '全选'
    // });
    initGrid();

    //执行查询
    $("#btnSearch").click(function () {
        dtGrid.fnSearch(getSelectData());
    });

    //绑定导出事件
    $("#btnExport").click(function () {
        var selAlarmTypeList = "";
        var selAlarmType = $("#selAlarmType").val();
        if (selAlarmType != null && selAlarmType.length > 0) {
            selAlarmTypeList = selAlarmType.join(",");
        }

        window.location.href = basePath
            + "AlarmFatiguedrie/Export?" + "plate="
            + $("#plate").val() + "&departmentId=" + $("#selDepartment").val()
            + "&timeRange=" + $("#selLength").val()+ "&imei=" + $("#imei").val()
            + "&alarmTime=" + $("#alarmTime").val() + "&alarmTimeStop=" + $("#alarmTimeStop").val()
  	        + "&alarmType=" + selAlarmTypeList;
      });

})

$(function () {

    //绑定日期
    if (jQuery().datetimepicker) {
        $('.datetimepicker').datetimepicker({
            autoclose: true,
            language: "zh-CN",
            format: "yyyy-mm-dd hh:ii",
            clearBtn: true,
            minView: "day"
        });
    }
});

function getSelectData() {
    var fromTime = $("#alarmTime").val();
    var toTime = $("#alarmTimeStop").val();
    if (fromTime != null && fromTime != "" && fromTime != "undefined" && toTime != null && toTime != "" && toTime != "undefined"
        && fromTime > toTime) {
        toastr.warning("'开始时间起' 应早于或等于 '开始时间止'，请确认!");
        return;
    }

    //服役状态
    // var serStatusList = "";
    // if ($("#divSerStatus").find("button").attr("title") == undefined && reoCar == -1) {
    //     serStatusList = 1;
    // }
    // else {
    //     var serList = $("#selSerStatus").val();
    //     if (serList != null && serList.length > 0) {
    //         serStatusList = serList.join(",");
    //     }
    // }

    //报警类型
    var selAlarmTypeList = "";
    var selAlarmType = $("#selAlarmType").val();
    if (selAlarmType != null && selAlarmType.length > 0) {
        selAlarmTypeList = selAlarmType.join(",");
    }

    var length = $("#selLength").val();
    var oData = [
        {"name": "alarmTime", "value": fromTime},
        {"name": "alarmTimeStop", "value": toTime},
        {"name": "plate", "value": $("#plate").val()},
        {"name": "timeRange", "value": length},
        // {"name": "serStatus", "value": serStatusList},
        {"name": "alarmType", "value": selAlarmTypeList},
        {"name": "departmentId", "value": $("#selDepartment").val()},
        {"name": "imei", "value": $("#imei").val()},
    ];
    return oData;
}

//重置
function onClear() {
    $("#alarmTime").val("");
    $("#alarmTimeStop").val("");
    $("#plate").val("");
    $("#imei").val("");
    $("#selLength").val("");
    $("#selDepartment").val("");
    // $('#selSerStatus').multiselect('deselectAll', false);
    // $('#selSerStatus').multiselect('updateButtonText');
    //报警类型
    $('#selAlarmType').multiselect('deselectAll', false);
    $('#selAlarmType').multiselect('updateButtonText');
}

//初始化疲劳驾驶管理列表
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AlarmFatiguedrie/queryAlarmFatiguedrieList",
        fnServerParams: function (aoData) {
            var oData = getSelectData();
            for (i = 0; i < oData.length; i++) {
                aoData.push(oData[i]);
            }
            aoData.push({"name": "alarmCount", "value": alarmCount});

        },
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "imei", "sTitle": "IMEI"},
            {"mDataProp": "department", "sTitle": "服务车企"},
            {"mDataProp": "alarmType", "sTitle": "报警类型"},
            {"mDataProp": "alarmTime", "sTitle": "报警时间"},
            {"mDataProp": "timeoutTime", "sTitle": "超时时长"},
            {"mDataProp": "alarmLocation", "sTitle": "报警地点"},
            {
                //自定义操作列
                "mDataProp": "cz",
                "sClass": "center",
                "sTitle": "详细信息",
                "bSearchable": false,
                "bStorable": false,
                "sWidth": 200,
                "render": function (data, type, row) {
                    return '<a href='+ basePath + 'AlarmFatiguedrie/toFatigueDetail/' + row.id + '  class="btn default btn-xs blue"><img src="img/trafficflux/icon/travelInfo.png" alt=""/>查看行程</a>'
                        ;
                }
            },
        ],

        fnRowCallback: function (nRow, aData, iDisplayIndex) {
            $('td:eq(0)', nRow).html("<a data-toggle='abstractpopover' data-plates='" + aData.plate + "' rel='popover' data-placement='top' onmouseover='getAbstractByPlates(&#39;" + aData.plate + "&#39;)'>" + aData.plate + "</a>");
            var alarmLocation = aData.alarmLocation;
            if (alarmLocation.length > 10) {
                alarmLocation = alarmLocation.substring(0, 10) + "...";
            }
            $('td:eq(6)', nRow).html("<div data-toggle='popover' rel='popover' data-placement='top' data-content='" + aData.alarmLocation + "'>" + alarmLocation + "</div>");
            $(document).ajaxComplete(function () {
                $("[data-toggle='popover']").popover({
                    template: '<div class="popover" style="position: fixed;width:350px;z-index:999999"><div class="arrow"></div><div class="popover-inner"><div class="popover-content"><p></p></div></div></div>',
                    trigger: 'hover',
                    html: 'true',
                });
            })
        },
        fnServerData: function (sSource, aoData, fnCallback) {
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
                    //dtGrid._fnProcessingDisplay(false);
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
   	