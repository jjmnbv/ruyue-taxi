var id;

$(function () {
    var frmmodal = $("#frmmodal");
    id = $("#id").val();
    if (id > '0') {
        initForm(id);
    }
    //绑定查询事件
    $("#btnSearch").click(function () {
        var oData = [
            {"name": "fenceId", "value": id},
            {"name": "plate", "value": $("#plate").val()}
        ];
        dtGrid.fnSearch(oData);
    });
    onSetMonitorPeriod();
    initGrid();
});

//重复周期
function onSetMonitorPeriod() {
    _loading.show();
    $.ajax({
        url: "SetTimeFence/getMonitorperiod",
        cache: false,
        data: {id: id > '0' ? id : -1},
        success: function (data) {
            var content = "";
            if (data.success != null && data.success != "undefined") {
                for (var i = 0; i < data.success.length; i++) {
                    if (data.success[i].status == 1) {
                        content += data.success[i].text;
                        if (i != data.length - 1) {
                            content += "、";
                        }
                    }
                }
                var lastStr = content.substr(content.length - 1, content.length - 1);
                if (lastStr == "、") {
                    content = content.substring(0, content.length - 1);
                }
            }
            $("#cbMonitorPeriod").html(content);
            _loading.hide();
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText);
        }
    });
}

//初始化栅栏信息界面，如果id>0代表修改，否则代码<img src="~/Content/assets/img/icon/add.png" />新增
function initForm(id) {
    $.ajax({
        url: "SetTimeFence/toUpdate",
        cache: false,
        data: {id: id},
        success: function (json) {
            setForm(json);
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText, "SetTimeFence/Index");
            return;
        }
    });
}

function setForm(entity) {
    var statusStr = "";
    if (entity.switchState == 1) {
        statusStr = "<div class='label label-success'>栅栏状态：已启用</div>";
    }
    else {
        statusStr = "<div class='label label-danger'>栅栏状态：已停用</div>";
    }
    $("#switchState").html(statusStr);
    $("#name").html(entity.entityName);
    $("#allowRunTime").html(ConvertedTime(entity.startTime) + "-" + ConvertedTime(entity.endTime));
}

function ConvertedTime(value) {
    var hours = Math.floor(value / 60);
    var mins = (value - hours * 60);
    return (hours < 10 ? "0" + hours : hours) + ":" + (mins == 0 ? "00" : mins);
}

//初始化表格
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "SetTimeFence/timeFenceInfo",
        userQueryParam: [
            {"name": "fenceId", "value": id}
        ],
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "imei", "sTitle": "设备IMEI"},
            {"mDataProp": "operateTime", "sTitle": "添加时间"},
            {"mDataProp": "operateStaff", "sTitle": "添加人"}

        ]

    };
    dtGrid = renderGrid(gridObj);
}


