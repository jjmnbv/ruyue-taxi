var dtGrid;
var insertButton;   //新增
var editButton;    //修改
var deleteButton;  //删除
var distributionButton; //分配

$(function () {
    insertButton = "True";
    editButton = "True";
    deleteButton = "True";
    distributionButton = "True";
    insertButton == "True" ? $("#btnInsert").show() : $("#btnInsert").hide();
    //初始化表格
    initGrid();
    //绑定查询事件
    $("#btnSearch").click(function () {
        var oData = [
            {"name": "name", "value": $("#txtAFS_NAME").val()}
        ];
        dtGrid.fnSearch(oData);
    });
});

function onDel(id) {

    bootbox.confirm("确认删除吗?", function (result) {
        if (result) {
            //组合数据
            var data = {id: id};
            $.ajax({
                type: 'POST',
                cache: false,
                dataType: 'json',
                url: 'AreaFenceSet/Delete',
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
//初始化表格
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AreaFenceSet/queryAreaFenceSetList",
        columns: [
            {"mDataProp": "name", "sTitle": "栅栏名称"},
            {"mDataProp": "count", "sTitle": "受控车数"},
            {"mDataProp": "allowRunCity", "sTitle": "允许运行城市"},
            {"mDataProp": "switchState", "sTitle": "状态"},
            {"mDataProp": "operateStaff", "sTitle": "最后操作人"},
            {"mDataProp": "operateTime", "sTitle": "最后操作时间",},
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
                        html += '<a href=" ' + basePath + 'AreaFenceSet/AreaFenceToVehc/' + row.id + '" class="btn default btn-xs blue"><img src="img/trafficflux/icon/distributionCar.png" />分配车辆</a>';
                    }
                    if (true) {
                        html += '&nbsp;<a  href=" ' + basePath + 'AreaFenceSet/AreaFenceSetEdit/' + row.id + '" class="btn default btn-xs blue"><img src="img/trafficflux/icon/edit.png" />修改</a>';
                    }
                    if (true) {
                        html += '&nbsp;<a  class="SSbtn red" href="javascript:void(0)" id="btnDel" onclick=onDel(' + '"' + row.id + '"' + ') ><i class="fa fa-trash-o"></i> 删除</a>';
                    }

                    return html += '&nbsp;<a  href=" ' + basePath + 'AreaFenceSet/AreaFenceSetInfo/' + row.id + '" class="btn default btn-xs blue"><img src="img/trafficflux/icon/checkDetail.png" />详情</a>';
                }
            },


        ],
        fnRowCallback: function (nRow, aData, iDisplayIndex) {

            var del = "2";
            var normal = "1";
            if (aData.switchState == 2) {
                $('td:eq(3)', nRow).html('<div onClick=UpdateStatus(\'' + aData.id + '\',\'' + aData.name + '\',\'' + aData.operateStaff + '\',\'' + normal + '\') class="bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-mini bootstrap-switch-id-switch-size bootstrap-switch-animate bootstrap-switch-off" style="width: 80px;height:20px"><div class="bootstrap-switch-container" style="width: 120px; margin-left: -40px;"><span class="bootstrap-switch-handle-on bootstrap-switch-primary" style="width:40px;height:20px">启用</span><span class="bootstrap-switch-label" style="width: 37px;">&nbsp;</span><span class="bootstrap-switch-handle-off bootstrap-switch-default" style="width: 40px;height:20px">停用</span><input type="checkbox" data-size="mini" checked="" id="switch-size"></div></div>');
            }
            else {
                $('td:eq(3)', nRow).html('<div onClick=UpdateStatus(\'' + aData.id + '\',\'' + aData.name + '\',\'' + aData.operateStaff + '\',\'' + del + '\') class="bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-mini bootstrap-switch-id-switch-size bootstrap-switch-animate bootstrap-switch-on" style="width: 80px;height:20px"><div class="bootstrap-switch-container" style="width: 120px; margin-left: 0px;"><span class="bootstrap-switch-handle-on bootstrap-switch-primary" style="width: 40px;height:20px">启用</span><span class="bootstrap-switch-label" style="width: 37px;">&nbsp;</span><span class="bootstrap-switch-handle-off bootstrap-switch-default" style="width: 40px;height:20px">停用</span><input type="checkbox" data-size="mini" checked="" id="switch-size"></div></div>');
            }

            $('td:eq(2)', nRow).html("<div  title='" + aData.allowRunCity + "'>" + aData.allowRunCity + "</div>");

            //文件名增加悬浮窗
            var allowRunCity = aData.allowRunCity;
            if (allowRunCity.length > 20) {
                allowRunCity = allowRunCity.substring(0, 20) + "...";
            }
            $('td:eq(2)', nRow).html("<div data-toggle='popover' rel='popover' data-placement='top' data-content='" + aData.allowRunCity + "'>" + allowRunCity + "</div>");
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

//修改栅栏状态
function UpdateStatus(id, name, operateStaff, switchState) {

    var data = {id: id, name: name, operateStaff: operateStaff, switchState: switchState};

    $.ajax({
        type: 'POST',
        cache: false,
        dataType: 'json',
        url: 'AreaFenceSet/UpdateFenceStatus',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (data) {
            dtGrid._fnReDraw();
        }
    });
}