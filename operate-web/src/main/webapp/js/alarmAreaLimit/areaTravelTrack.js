/**
 * Created by liubangwei_lc on 2017/7/25.
 */

var trackId = $("#trackId").val();
var trackStatus = $("#trackStatus").val();
var eqpId = $("#eqpId").val();
var map;
var seconds;
var isfirst = 0;
var setTimeoutId = 0;
$(function () {
    seconds = 0;
    bindMap();
    showInfo();
    $("#btnCancel").click(function () {
        location.href = basePath + "AlarmAreaLimit/Index";
    })
});

//获取界面所有信息
function showInfo() {
    $.ajax({
        url: basePath + 'VehicleTrajectory/getTrajectoryByEqp',
        cache: false,
        data: { eqpId:eqpId, trackId: trackId,returnResult:2,processOption:2},
        success: function (data) {
            var bluegps = data.trajectory.vehcTrajectory;

            if (bluegps == null || bluegps.length == 0) {
                toastr.warning("当前行程没有GPS信息", "提示信息");
                return;
            }
            map.clearOverlays();
            //当前行程
            if (trackStatus == '1') {
                //开始点
                var point = new BMap.Point(bluegps[0].longitude, bluegps[0].latitude);
                if (isfirst == 0) {
                    setTimeout(function () {
                        map.panTo(point);
                    }, 1500);
                }
                var icon = new BMap.Icon("Content/img/gpsStart.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                var markerStart = new BMap.Marker(point, { icon: icon });
                map.addOverlay(markerStart);

                var bluepoints = [];
                for (var j = 0; j < bluegps.length; j++) {
                    bluepoints.push(new BMap.Point(bluegps[j].longitude, bluegps[j].latitude));
                }
                if (bluepoints.length > 0) {
                    var bluepolyline = new BMap.Polyline(bluepoints, { strokeColor: "blue", strokeWeight: 4, strokeOpacity: 0.5 });
                    map.addOverlay(bluepolyline);
                }

                //结束点
                if (bluegps.length > 1) {
                    //现行位置
                    if (data.trajectory.vehcTrajectory == null || ddata.trajectory.vehcTrajectory == undefined ) {
                        toastr.warning("车辆当前状态数据错误，地图无法显示！", "提示信息");
                    }
                    else {
                        //设置图片大小
                        var iconSize1 = new BMap.Size(__seticon.carheight, __seticon.carwidth);
                        //设置地图图标
                        var icon1 = _directiontranslate.setVehcIncoByGpsdrct(data.trajectory.vehcTrajectory.direction, iconSize1);
                        //添加车辆地图标注
                        var marker1 = new BMap.Marker(new BMap.Point(bluegps[bluegps.length - 1].longitude, bluegps[bluegps.length - 1].latitude), { icon: icon1 });
                        map.addOverlay(marker1);
                        marker1.setIcon(icon1);
                    }
                }
                //画区域栅栏的线
                if(data.trajectory.alarmList.arealimitList == null || data.trajectory.alarmList.arealimitList.length ==0){
                    toastr.warning("当前行程没有栅栏信息", "提示信息");
                    return;
                }
                var TimeLimitGpsList =  data.trajectory.alarmList.arealimitList;
                if (TimeLimitGpsList.length > 0) {
                    var gps = TimeLimitGpsList;
                    var timeLimitPoints = [];
                    for (var k = 0; k < gps.length; k++) {
                        timeLimitPoints.push(new BMap.Point(gps[k].longitude, gps[k].latitude));
                    }
                    if (timeLimitPoints.length > 0) {
                        var timeLimitpolyline = new BMap.Polyline(timeLimitPoints, { strokeColor: "red", strokeWeight: 4 });
                        map.addOverlay(timeLimitpolyline);
                    }
                }
                //10秒刷新界面
                if (setTimeoutId != 0)
                    clearTimeout(setTimeoutId);
                seconds = 10;
                isfirst = 1;
                work();
            }else {
                //画整个行程
                //开始点
                var point = new BMap.Point(bluegps[0].longitude, bluegps[0].latitude);
                setTimeout(function () {
                    map.panTo(point);
                }, 1500);
                var icon = new BMap.Icon("Content/img/gpsStart.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                var markerStart = new BMap.Marker(point, { icon: icon });
                map.addOverlay(markerStart);

                var bluepoints = [];
                for (var j = 0; j < bluegps.length; j++) {
                    bluepoints.push(new BMap.Point(bluegps[j].longitude, bluegps[j].latitude));
                }
                if (bluepoints.length > 0) {
                    var bluepolyline = new BMap.Polyline(bluepoints, { strokeColor: "blue", strokeWeight: 4, strokeOpacity: 0.5 });
                    map.addOverlay(bluepolyline);
                }

                //结束点
                if (bluegps.length > 1) {
                    var icon = new BMap.Icon("Content/img/gpsEnd.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                    var markerEnd = new BMap.Marker(new BMap.Point(bluegps[bluegps.length - 1].longitude, bluegps[bluegps.length - 1].latitude), { icon: icon });
                    map.addOverlay(markerEnd);
                }
                //画时间栅栏的线
                if(data.trajectory.alarmList.arealimitList == null || data.trajectory.alarmList.arealimitList.length ==0){
                    toastr.warning("当前行程没有栅栏信息", "提示信息");
                    return;
                }
                var TimeLimitGpsList =  data.trajectory.alarmList.arealimitList;
                if (TimeLimitGpsList.length > 0) {
                    var gps = TimeLimitGpsList;
                    var timeLimitPoints = [];
                    for (var k = 0; k < gps.length; k++) {
                        timeLimitPoints.push(new BMap.Point(gps[k].longitude, gps[k].latitude));
                    }
                    if (timeLimitPoints.length > 0) {
                        var timeLimitpolyline = new BMap.Polyline(timeLimitPoints, { strokeColor: "red", strokeWeight: 4 });
                        map.addOverlay(timeLimitpolyline);
                    }
                }
            }

        },
        error: function (xhr, status, error) {
            // showerror(xhr.responseText);
        },
        complete: function () {
            _loading.hide();
        }
    });
}

//绑定地图行驶轨迹
function bindMap() {
    map = new BMap.Map("map_canvas");
    map.centerAndZoom("深圳", 15);
    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.enableScrollWheelZoom();
    map.clearOverlays();
}
//定时器
function work() {
    if (seconds == 0) {
        seconds = 10;
        showInfo();
        ++isfirst;
    }
    else {
        seconds -= 1;
    }
    setTimeoutId = setTimeout(work, 1000);
}