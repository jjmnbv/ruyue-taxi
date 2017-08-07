/**
 * Created by liubangwei_lc on 2017/7/25.
 */

var map;
var dtGrid;
var id;
$(function () {
    initGrid();
    id = $("#id").val();
    $("#btnCancel").click(function () {
        location.href = basePath + "AlarmFatiguedrie/Index";
    })
});

//绑定地图行驶轨迹
function bindMap() {
    map = new BMap.Map("map_canvas");
    map.centerAndZoom("深圳", 10);
    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.enableScrollWheelZoom();
}
//绑定GPS地图信息
function bindGps(gpsList) {

    if (gpsList == null || gpsList == undefined || gpsList.length == 0) {
        toastr.warning("此行程没有GPS数据", "提示信息");
        $("#hTrack").hide();
        $("#map").hide();
        return;
    }
    if (gpsList.length > 0) {
        var point = new BMap.Point(gpsList[0].longitude, gpsList[0].latitude);
        var icon = new BMap.Icon("Content/img/gpsStart.png", new BMap.Size(29, 35));
        var marker = new BMap.Marker(point, {icon: icon});
        map.addOverlay(marker);
        setTimeout(function () {
            map.panTo(point)
        }, 3000);//三秒后平移到轨迹开始位置
    }

    if (gpsList.length > 1) {
        var icon = new BMap.Icon("Content/img/gpsEnd.png", new BMap.Size(29, 35));
        var marker = new BMap.Marker(new BMap.Point(gpsList[gpsList.length - 1].longitude, gpsList[gpsList.length - 1].latitude), {icon: icon});
        map.addOverlay(marker);
    }

    var points = [];
    for (var i = 0; i < gpsList.length; i++) {
        var point = new BMap.Point(gpsList[i].longitude, gpsList[i].latitude);
        points.push(point);
    }
    var polyline = new BMap.Polyline(points, {strokeColor: "blue", strokeWeight: 6, strokeOpacity: 0.5});
    map.addOverlay(polyline);
}
//初始化违规列表列表
function initGrid() {
    var gridObj = {
        id: "dtGrid",
        bProcessing: true,
        sAjaxSource: "AlarmFatiguedrie/getFatigueDetail",
        userQueryParam: [{name: "id", value: id}],
        columns: [
            {"mDataProp": "startTime", "sTitle": "开始时间"},
            {"mDataProp": "endTime", "sTitle": "结束时间"},
            {"mDataProp": "runTime", "sTitle": "持续时长"},
            {"mDataProp": "stopTime", "sTitle": "停留时间"},
            {
                //自定义操作列
                "mDataProp": "cz",
                "sClass": "center",
                "sTitle": "详情",
                "bSearchable": false,
                "bStorable": false,
                "render": function (data, type, row) {
                    // if (obj.aData.VT_STATUS == -1) {
                    //     return ' ';
                    // }
                    // else {
                    return '<a  class="btn  " href="javascript:void(0)" id="btnSelectTrack" onclick="onSelectTrack(\'' + row.trackId + '\',\'' + row.eqpId + '\') "><img src="img/trafficflux/icon/travelInfo.png" />查看行程轨迹</a>';
                    // }
                }
            }
        ],
    };
    dtGrid = renderGrid(gridObj);
}

//点击查看行程
function onSelectTrack(vtId, vId) {

    $("#hTrack").hide();
    $("#map").hide();
    _loading.show();
    if (vtId == -1) {
        showerror("此行程的数据不存在，请联系管理员");
    }
    if (vId == -1) {
        showerror("此行程对应的车辆信息不存在，请联系管理员");
    }
    $.ajax({
        url: basePath + 'VehicleTrajectory/getTrajectoryByEqp',
        data: {eqpId: vId, trackId: vtId, returnResult: 2, processOption: 2},
        cache: false,
        success: function (data) {
            if (map == null || map == undefined) {
                bindMap();
            }
            map.clearOverlays();
            $("#hTrack").show();
            $("#map").show();

            bindGps(data.trajectory.vehcTrajectory);
        },
        error: function (xhr, status, error) {
            // showerror(xhr.responseText);
            return;
        },
        complete: function (xhr, ts) {
            _loading.hide();
        }
    });
}