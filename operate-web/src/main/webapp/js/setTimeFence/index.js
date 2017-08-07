var dtGrid;

$(function () {

    //执行查询操作
    $("#btnSearch").click(function () {
        dtGrid.fnSearch(getFnData());
    });

    initGrid();
});


//获取查询数据
function getFnData() {
    //查询条件
    var oData = [
        {"name": "name", "value": $("#name").val()},
    ];
    return oData;
}

//删除操作
function onDel(id) {
    bootbox.confirm("确认删除吗?", function (result) {
        if (result) {
            //组合数据
            var data = {id: id};
            $.ajax({
                type: 'POST',
                cache: false,
                dataType: 'json',
                url: 'SetTimeFence/deleteTimeFence',
                data: JSON.stringify(data),
                contentType: 'application/json; charset=utf-8',
                async: false,
                success: function (data) {
                    if (data.status == 0) {
                        var message = data.msg == null ? data
                            : data.msg;
                        toastr.options.onHidden = function () {
                            window.location.href = basePath + "SetTimeFence/Index";
                        }
                        toastr.success(message, "提示");

                    } else {
                        var message = data.msg == null ? status
                            : status.msg;
                        toastr.error(message, "提示");
                    }
                }
            });
        }
    });
}

//修改栅栏状态
function UpdateStatus(id, state) {
    var data = {id: id, switchState: state};
    $.ajax({
        type: 'POST',
        cache: false,
        dataType: 'json',
        url: 'SetTimeFence/setTimeFenceAll',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (data) {
            dtGrid._fnReDraw();
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText);
            return;
        }
    });
}


//初始化时间栅栏管理列表
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "SetTimeFence/getSetTimeFenceByPage",
        columns: [
            {"mDataProp": "name", "sTitle": "栅栏名称"},
            {"mDataProp": "count", "sTitle": "受控设备数"},
            {"mDataProp": "allowRunTime", "sTitle": "允许运行时间"},
            {"mDataProp": "monitorperiod", "sTitle": "监控周期"},
            {"mDataProp": "switchState", "sTitle": "启用状态"},
            {"mDataProp": "operateStaff", "sTitle": "最后操作人"},
            {"mDataProp": "operateTime", "sTitle": "最后操作时间 "},
            {
                //自定义操作列
                "mDataProp": "cz",
                "sClass": "center",
                "sTitle": "详细信息",
                "bSearchable": false,
                "bStorable": false,
                "sWidth": 400,
                "render": function (data, type, row) {
                    var html = '';
                    if (true) {
                        html += '<a href=" ' + basePath + ' /SetTimeFence/timeFenceToVehc/' + row.id + '" class="btn default btn-xs blue"><img src="img/trafficflux/icon/distributionCar.png" />分配车辆</a>';
                    }
                    if (true) {
                        html += '&nbsp;<a  href=" ' + basePath + ' /SetTimeFence/toAddOrUpdate/' + row.id + '" class="btn default btn-xs blue"><img src="img/trafficflux/icon/edit.png" />修改</a>';
                    }
                    if (true) {
                        html += '&nbsp;<a  class="btn  btn-xs red" href="javascript:void(0)" id="btnDel" onclick=onDel(' + '"' + row.id + '"' + ') ><i class="fa fa-trash-o"></i> 删除</a>';
                    }

                    return html += '&nbsp;<a target="_blank" href=" ' + basePath + ' /SetTimeFence/timeFenceSetInfo/' + row.id + '" class="btn default btn-xs blue"><img src="img/trafficflux/icon/checkDetail.png" />详情</a>';
                }
            },

        ],
        fnRowCallback: function (nRow, aData, iDisplayIndex) {
            if (aData.switchState == 2) {
                $('td:eq(4)', nRow).html('<div onClick=UpdateStatus(' + '"' + aData.id + '",' + 1 + ') class="bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-mini bootstrap-switch-id-switch-size bootstrap-switch-animate bootstrap-switch-off" style="width: 80px;height:20px"><div class="bootstrap-switch-container" style="width: 120px; margin-left: -40px;"><span class="bootstrap-switch-handle-on bootstrap-switch-primary" style="width:40px;height:20px">启用</span><span class="bootstrap-switch-label" style="width: 37px;">&nbsp;</span><span class="bootstrap-switch-handle-off bootstrap-switch-default" style="width: 40px;height:20px">停用</span><input type="checkbox" data-size="mini" checked="" id="switch-size"></div></div>');
            }
            else {
                $('td:eq(4)', nRow).html('<div onClick=UpdateStatus(' + '"' + aData.id + '",' + 2 + ') class="bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-mini bootstrap-switch-id-switch-size bootstrap-switch-animate bootstrap-switch-on" style="width: 80px;height:20px"><div class="bootstrap-switch-container" style="width: 120px; margin-left: 0px;"><span class="bootstrap-switch-handle-on bootstrap-switch-primary" style="width: 40px;height:20px">启用</span><span class="bootstrap-switch-label" style="width: 37px;">&nbsp;</span><span class="bootstrap-switch-handle-off bootstrap-switch-default" style="width: 40px;height:20px">停用</span><input type="checkbox" data-size="mini" checked="" id="switch-size"></div></div>');
            }
        }


    };
    dtGrid = renderGrid(gridObj);
}


       