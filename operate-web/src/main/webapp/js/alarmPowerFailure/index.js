var dtGrid;
var validator;
var params = {             //好约车参数
    isNullName: false,
    checkSwitch: true,
    isNewWord: false,
    passengers: null,
    passengerPhone: null,
    text: null,
    userid: null,
    organid: null,
    airports: null,
    cities: null,
    companies: null,
    onLat: 0,
    onLng: 0,
    onAddress: null,
    offLat: 0,
    offLng: 0,
    offAddress: null,
    cartypes: null,
    cartypeindex: 0,
    cartypemin: 4      //车型最小显示个数
};
$(function () {
    var frmmodal = $("#frmmodal");
    var erroralert = $('.alert-danger', frmmodal);
    var successalert = $('.alert-success', frmmodal);
    validator = frmmodal.validate(
        {
            submitHandler: function (form) {
                successalert.show();
                erroralert.hide();
                submit();
            }
        });
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
//绑定导出事件  
    $("#btnExport").click(function () {
        window.location.href = basePath
            + "AlarmPowerFailure/Export?" + "plate="
            + $("#plate").val() + "&imei=" + $("#imei").val() + "&departmentId=" + $("#company").val()
            + "&alarmTime=" + $("#alarmTime").val() + "&alarmTimeStop=" + $("#alarmTimeStop").val()
            + "&processingState=" + $("#processingState").val() + "&mileageRange=" + $("#mileageRange").val();
    });
});
//查询
function onSelect() {
    alarmCount = -1;
    dtGrid.fnDraw();
}
//获取查询数据
function getFnData() {
    var fromTime = $("#alarmTime").val();
    var toTime = $("#alarmTimeStop").val();
    if (fromTime != null && fromTime != "" && fromTime != "undefined" && toTime != null && toTime != "" && toTime != "undefined"
        && fromTime > toTime) {
        toastr.warning("'开始时间' 应早于或等于 '结束时间'，请确认!");
        return;
    }
    var rcId = -1;
    var companydeptCode = $("#company").val();
    var length = $("#selLength").val();
    var oData = [
        {"name": "plate", "value": $("#plate").val()},
        {"name": "imei", "value": $("#imei").val()},
        {"name": "departmentId", "value": companydeptCode},
        {"name": "alarmTime", "value": $("#alarmTime").val()},
        {"name": "alarmTimeStop", "value": $("#alarmTimeStop").val()},
        {"name": "processingState", "value": $("#processingState").val()},
        {"name": "mileageRange", "value": $("#mileageRange").val()},
        {"name": "companyId", "value": rcId}
    ];
    return oData;
}

//重置
function onClear() {
    $("#plate").val("");
    $("#imei").val("")
    $("#company").val("");
    $("#alarmTime").val("");
    $("#alarmTimeStop").val("");
    $("#processingState").val("");
    $("#mileageRange").val("");
}
//初始化表格
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AlarmPowerFailure/getAlarmPowerFailureByPage",
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "imei", "sTitle": "IMEI"},
            {"mDataProp": "department", "sTitle": "服务车企"},
            {"mDataProp": "alarmTime", "sTitle": "报警时间"},
            {"mDataProp": "processingTime", "sTitle": "处理时间"},
            {"mDataProp": "alarmAddress", "sTitle": "报警地址"},
            {"mDataProp": "processingState", "sTitle": "处理状态 "},
            {"mDataProp": "processingPeople", "sTitle": "处理人 "},
            {"mDataProp": "updateTime", "sTitle": "更新时间 "},
            {
                //自定义操作列
                "mDataProp": "id",
                "sClass": "center",
                "sWidth": 100,
                "sTitle": "操作",
                "bSearchable": false,
                "bStorable": false,
                "render": function (data, type, row) {
                    var html = '';
                    if (row.processingState == '已处理' || row.processingState == '已恢复') {
                        html += '<button  class="btn default btn-xs" disabled=disabled><img src="img/trafficflux/icon/alarmProcessing_ga.png" />处理</button> ';
                    } else {
                        html += '<button  class="btn default btn-xs blue " onclick=onHandle("' + row.id + '") ><img src="img/trafficflux/icon/alarmProcessing.png" />处理</button>';
                    }
                    return html;
                }
            }
        ],
    };
    dtGrid = renderGrid(gridObj);
}

//处理弹窗
function onHandle(id) {
    $("#frmmodal").find(".form-group").removeClass("has-error");
    $("span").remove(".help-block");
    $("#id").val(id);
    $("#outageReason").val("");
    $("#verifyName").val("");
    $("#remarks").val("");
    $('#mdAdd').modal('show');
}

//执行处理
function submit() {
    var data = {
        id: $("#id").val(),
        outageReason: $("#outageReason").val(),
        verifyName: $("#txtVerifyPerson").val(),
        remarks: $("#remarks").val()
    }
    $.ajax({
        type: 'POST',
        cache: false,
        dataType: 'json',
        url: 'AlarmPowerFailure/getOutage',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (data) {
            toastr.success("处理成功", "提示信息");
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
            $('#mdAdd').modal('hide');
        }
    });
}
