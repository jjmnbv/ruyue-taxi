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
            {"name": "id", "value": id},
            {"name": "plate", "value": $("#plates").val()}
        ];
        dtGrid.fnSearch(oData);
    });
    $("#btnCancel").click(function () {
        location.href = basePath + "SetElectronicFence/Index";
    });
    initGrid();

});


//初始化栅栏信息界面，如果id>0代表修改，否则代码<img src="~/Content/assets/img/icon/add.png" />新增
function initForm(id) {
    $.ajax({
        url: "SetElectronicFence/toUpdate",
        cache: false,
        data: {id: id},
        success: function (json) {
            setForm(json);
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText, "SetElectronicFence/Index");
            return;
        }
    });
}

function setForm(entity) {

    var statusStr;

    if (entity.setElectronicFence[0].switchState == 1) {
        statusStr = "<div class='label label-success'>栅栏状态：已启用</div>";
    }
    else {
        statusStr = "<div class='label label-danger'>栅栏状态：已停用</div>";
    }
    if (entity.setElectronicFence[0].alartType == 1) {
        allowType = "<div class='label label-danger'>区域内</div>";
    }
    else {
        allowType = "<div class='label label-danger'>区域外</div>";
    }
    $("#name").html(entity.setElectronicFence[0].name);
    $("#switchState").html(statusStr);
    $("#alartType").html(allowType);
}

//初始化表格
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "SetElectronicFence/electronicFenceInfo",
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


