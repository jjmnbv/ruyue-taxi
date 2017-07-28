var v_id;
$(function () {
    var frmmodal = $("#frmmodal");
    v_id = $("#id").val();
    if (v_id != '0') {
        initForm(v_id);
    }
    //绑定查询事件
    $("#btnSearch").click(function () {
        var oData = [
            {"name": "fenceId", "value": v_id},
            {"name": "plate", "value": $("#txtPlates").val()}
        ];
        dtGrid.fnSearch(oData);
    });
    $("#btnCancel").click(function () {
        location.href = basePath + "AreaFenceSet/Index";
    });
    initGrid();
});
//初始化栅栏信息界面，如果id>0代表修改，否则代码<img src="~/Content/assets/img/icon/add.png" />新增
function initForm(id) {
    $.ajax({
        url: "AreaFenceSet/GetById",
        cache: false,
        data: {id: id},
        success: function (json) {
            setForm(json);
        },
        error: function (xhr, status, error) {
            // showerror(xhr.responseText, "AreaFenceSet/Index");
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
    $("#pFENCESTATUS").html(statusStr);

    $("#txtName").html(entity.name);
    $("#txtArea").html(entity.allowRunCity);
}
//初始化表格
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AreaFenceSet/GetAreaFenToVechList",
        userQueryParam: [
            {"name": "fenceId", "value": v_id}
        ],
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "operateTime", "sTitle": "添加时间"},
            {"mDataProp": "operateStaff", "sTitle": "添加人"}

        ]
    };
    dtGrid = renderGrid(gridObj);
}

//初始化允许运行城市树形
function onIniCity() {
    var setting = {
        async: {
            enable: true,
            url: "AreaFenceSet/GetAllowCity",
            autoParam: ["id"],
            otherParam: {"afsId": v_id},
        },
        view: {
            selectedMulti: false //禁止多点选中
        },
        callback: {

            onAsyncSuccess: zTreeOnAsyncSuccess

        }
    };

    var zTree = $.fn.zTree.init($("#cityList"), setting);
    $('#mdSelCity').modal('show');

}
//异步加载成功回调函数
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    _loading.hide();
}