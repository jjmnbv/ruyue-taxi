var validator;
var fenceId;
$(function () {
    var frmmodal = $("#frmmodal");
    fenceId = $("#id").val();
    //绑定查询事件
    $("#btnSearch").click(function () {
        var oData = [
            {"name": "fenceId", "value": fenceId},
            {"name": "plate", "value": $("#plate").val()}
        ];
        dtGrid.fnSearch(oData);
    });
    $("#btnCancel").click(function () {
        location.href = "SetElectronicFence/Index";
    })

    initGrid();
    if (fenceId != '0') {
        initForm(fenceId)
    }
});


//初始化栅栏信息界面，如果id>0代表修改，否则代码<img src="~/Content/assets/img/icon/add.png" />新增
function initForm(fenceId) {
    $.ajax({
        url: "SetElectronicFence/toUpdate",
        cache: false,
        data: {id: fenceId},
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

    var statusStr = "";
    var data = entity.setElectronicFence
    if (data[0].switchState == 1) {
        statusStr = "<div class='label label-success'>栅栏状态：已启用</div>";
    }
    else {
        statusStr = "<div class='label label-danger'>栅栏状态：已停用</div>";
    }
    $("#switchState").html(statusStr);
    $("#name").html(data[0].name);

}

//初始化表格
var dtGrid;
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "SetElectronicFence/electronicFenceInfo",
        userQueryParam: [
            {"name": "fenceId", "value": fenceId}
        ],
        columns: [
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "imei", "sTitle": "设备IMEI"},
            {"mDataProp": "operateTime", "sTitle": "添加时间"},
            {"mDataProp": "operateStaff", "sTitle": "添加人"},
            {
                //自定义操作列
                "mDataProp": "cz",
                "sClass": "center",
                "sTitle": "操作",
                "bSearchable": false,
                "bStorable": false,
                "render": function (data, type, row) {
                    return '&nbsp;<a  class="btn  btn-xs red" href="javascript:void(0)" id="btnDel" onclick=onDel(\'' + row.id + '\',\'' + fenceId + '\',\'' + row.eid + '\') ><i class="fa fa-trash-o"></i> 删除</a>';
                }
            }
        ]

    };
    dtGrid = renderGrid(gridObj);
}


function onDel(id, fenceId, eqpId) {
    bootbox.confirm("确认删除吗?", function (result) {
        if (result) {
            //组合数据
            var data = {
                id: id,   //关系id
                fenceId: fenceId,//栅栏id
                eqpId: eqpId,  //设备id
                fenceType: 3
            };
            $.ajax({
                type: 'POST',
                cache: false,
                dataType: 'json',
                url: 'SetElectronicFence/removeEqp',
                data: JSON.stringify(data),
                contentType: 'application/json; charset=utf-8',
                async: false,
                success: function (data) {
                    toastr.success("删除成功", "提示信息");
                    dtGrid._fnReDraw();
                },
                error: function (xhr, status, error) {
                    showerror(xhr.responseText);
                    return;
                }
            });
        }
    });
}


//栅栏绑定车辆
var dtGrid2;

function onBind() {
//            $("#frmmodal").find(".form-group").removeClass("has-error");

    //初始化表格
    initGridForMd();
    //绑定查询事件
    $("#btnSearchForMd").click(function () {
        var oData = [
            {"name": "plate", "value": $("#plate1").val()},
            {"name": "imei", "value": $("#imei").val()},
            {"name": "entityName", "value": $("#entityName").val()},
            {"name": "workStatus", "value": $("#workStatus").val()}

        ];
        dtGrid2.fnSearch(oData);
        //全选
        $(document).on("click", "#checkAllRows", function () {
            var checked = $(this).is(":checked");
            if (checked) {
                $('#dtGridForMd input[type=checkbox]').attr('checked', $(this).attr('checked'));
                $('#dtGridForMd tr').addClass("active");
            } else {
                $('#dtGridForMd input[type=checkbox]').attr('checked', false);
                $('#dtGridForMd tr').removeClass("active");
            }
        });
        $("#checkAllRows").attr('checked', false);
    });
    $('#mdTimeFenceToVehc').modal('show');
}

//初始化表格
function initGridForMd() {
    var gridObj = {
        id: "dtGrid2",
        sAjaxSource: "SetElectronicFence/getVehcList",
        bProcessing: true,
        bServerSide: true,
        bServerSide: true,
        bRetrieve: true,
        scrollX: true,
        userQueryParam: [
            {"name": "fenceId", "value": fenceId}
        ],
        columns: [
            {"mDataProp": "imei", "sTitle": "设备IMEI"},
            {"mDataProp": "plate", "sTitle": "车牌"},
            {"mDataProp": "entityName", "sTitle": "型号名称"},
            {
                //自定义操作列
                "mDataProp": "cz",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 100,
                "bSearchable": false,
                "bStorable": false,
                "render": function (data, type, row) {
                    return '<a  href="javascript:void(0)" class="btn btn-xs gree" id="btnAdd" onclick="onAddVehc(\'' + row.eqpId + '\') "><img src="img/trafficflux/icon_gallery/addSmal_ga.png" />添加</a>';
                }
            }
        ],
    };
    dtGrid2 = renderGrid(gridObj);
}

function onAddVehc(E_id) {
    var data = {
        fenceId: fenceId,  //栅栏Id
        eqpId: E_id, //设备ID
        fenceType: 3  //栅栏类型
    }
    $.ajax({
        type: 'POST',
        cache: false,
        dataType: 'json',
        url: 'SetElectronicFence/distributionEqp',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (data) {
            if (data.status == 0) {
                toastr.success("添加成功", "提示信息");
                setTimeout(function () {
                    dtGrid2._fnReDraw();
                    dtGrid._fnReDraw();
                }, 500);
                return;

            } else {
                var message = data.msg == null ? data
                    : data.msg;
                toastr.error(message, "提示");
            }
        }
    });
}