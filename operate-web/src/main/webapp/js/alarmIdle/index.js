var dtGrid;

$(function () {
    var frmmodal = $("#frmmodal");
    var erroralert = $('.alert-danger', frmmodal);
    var successalert = $('.alert-success', frmmodal);

    initGrid();
    $('.datetimepicker').datetimepicker({
        autoclose: true,
        language: "zh-CN",
        format: "yyyy-mm-dd hh:ii",
        clearBtn: true,
        minView: "day"
    });
    validator = frmmodal.validate(
        {
            submitHandler: function (form) {
                successalert.show();
                erroralert.hide();
                save();
            }
        });

    //执行查询
    $("#btnSearch").click(function () {
        dtGrid.fnSearch(getFnData());
    });

    //绑定导出事件
    $("#btnExport").click(function () {
        _loading.show();
        window.location.href = basePath
            + "AlarmIdle/Export?" + "plate="
            + $("#plate").val() + "&imei=" + $("#imei").val() + "&departmentId=" + $("#company").val()
            + "&startTime=" + $("#startTime").val() + "&startTimeStop=" + $("#startTimeStop").val()
            + "&postage=" + $("#postage").val() + "&timeRange=" + $("#timeRange").val();
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
});

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
        {"name": "postage", "value": $("#postage").val()},
        {"name": "departmentId", "value": companydeptCode},
        {"name": "companyId", "value": rcId}
    ];
    return oData;
}


//初始化表格
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AlarmIdle/getAlarmIdleByPage",
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "imei", "sTitle": "IMEI"},
            {"mDataProp": "department", "sTitle": "服务车企"},
            {"mDataProp": "idleTime", "sTitle": "怠速时长"},
            {"mDataProp": "startTime", "sTitle": "开始时间"},
            {"mDataProp": "endTime", "sTitle": "结束时间"},
            {"mDataProp": "location", "sTitle": "地址", "sWidth": "250px",},
            {"mDataProp": "timeRange", "sTitle": "违规级别"},
            {"mDataProp": "oilCharge", "sTitle": "核查结果"},
            {
                //自定义操作列
                "mDataProp": "id",
                "sClass": "center",
                "sTitle": "操作",
                "bSearchable": false,
                "bStorable": false,
                "mRender": function (data, type, row) {
                    var html = '';
                    if (row.oilCharge == null || row.oilCharge == "") {
                        html += '<button  class="btn  btn-xs blue"   onclick=onCheck("' + row.id + '") ><img src="img/trafficflux/icon/verification.png"/>核查</button>'
                        + '&nbsp;<a target="_blank" href='+ basePath + 'AlarmSpeed/selectTrack/' + row.eqpId + '/' + row.trackId + '/' + row.trackStatus + '  class="btn default btn-xs blue"><img src="img/trafficflux/icon/travelInfo.png" alt=""/>查看行程</a>';
                    }
                    else {
                        html += '<a  href= ' + basePath + 'AlarmIdle/toAlarmIdleDetail/' + row.id  +' class="btn default btn-xs blue"><img src="img/trafficflux/icon/operaRecords.png"/>记录</a>'
                            +  '&nbsp;<a target="_blank" href='+ basePath + 'AlarmSpeed/selectTrack/' + row.eqpId + '/' + row.trackId + '/' + row.trackStatus + '  class="btn default btn-xs blue"><img src="img/trafficflux/icon/travelInfo.png" alt=""/>查看行程</a>';
                    }

                    return html;
                }
            }
        ],
        fnRowCallback: function (nRow, aData, iDisplayIndex) {

            $('td:eq(6)', nRow).html("<div  title='" + aData.location + "'>" + aData.location + "</div>");

            //文件名增加悬浮窗
            var location = aData.location;
            if (location.length > 20) {
                location = location.substring(0, 20) + "...";
            }
            $('td:eq(6)', nRow).html("<div data-toggle='popover' rel='popover' data-placement='top' data-content='" + aData.location + "'>" + location + "</div>");
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


function onCheck(id) {

    $("#frmmodal").find(".form-group").removeClass("has-error");
    $("span").remove(".help-block");
    $("#id").val(id);
    $("#oilCharge").val("");
    $("#verifyPerson").val("");
    $("#verifyPersonTel").val("");
    // $("#selVDepartment").combotree("setValue", { id: "-1", name: "请选择", pId: "" });
    $("#selVDepartment").val("");
    $("#idleReason").val("");
    $('#mdAdd').modal('show');
}


// 保存
function save() {
    var companydeptCode = $("#selVDepartment").val();

    var data = {
        id: $("#id").val(),
        oilCharge: $("#oilCharge").val(),
        verifyPerson: $("#verifyPerson").val(),
        verifyPersonTel: $("#verifyPersonTel").val(),
        departmentId: companydeptCode,
        operateStaff: $("#operateStaff").val(),
        idleReason: $("#idleReason").val()
    };

    $.ajax({
        type: 'POST',
        cache: false,
        dataType: 'json',
        url: 'AlarmIdle/getVerifyIdle',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (data) {
            toastr.success("核实成功", "提示信息");
            setTimeout(function () {
                dtGrid._fnReDraw();
                $('#mdAdd').modal('hide');
            }, 500);
            return;
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText);
            return;
        },
        complete: function (xhr, ts) {
            $("#mdAdd").modal("hide");
        }
    });
}



