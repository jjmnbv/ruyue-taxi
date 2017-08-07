function loadCurrenttrack(vehcId, vehcTrackId) {

    var map;
    var dtGrid;
    var setTimeoutId = 0;
    var seconds = 0;
    var isfirst = 0;
    var timeId;
    $(function () {
        bindMap();
        initGrid();
        initTrackChar();
        setTimeout(function () {
            initWork();
        }, 200);
        // initWork();
    });

    function initWork() {

        if (timeId != 0)
            clearTimeout(timeId);
        map.clearOverlays();
        seconds = 0;
        vehcId = vehcId;
        work();
    }

    function work() {

        if (seconds == 0) {

            seconds = 100;
            _loading.show();
            // initModal();
            if (dtGrid) {
                dtGrid.fnClearTable();
            }
            initGrid();
            onShowMap();
            _loading.hide();
        } else {
            seconds -= 1;
        }
        timeId = setTimeout(work, 1000);
    }

    //绑定地图行驶轨迹
    function bindMap() {
        map = new BMap.Map("map_canvas");
        map.centerAndZoom("深圳", 15);
        map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
        map.enableScrollWheelZoom();
    }

    //绑定行程明细
    function onShowMap() {
        map = new BMap.Map("map_canvas");
        map.centerAndZoom("深圳", 15);
        map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
        map.enableScrollWheelZoom();
        map.clearOverlays();
        //获取行驶轨迹
        $.ajax({
            url: basePath + 'VehicleTrajectory/getTrajectoryByEqp',
            cache: false,
            data: {eqpId: vehcId, trackId: vehcTrackId, returnResult: 2, processOption: 2}, //status=2代表历史
            success: function (data) {

                //画整个行程
                if (data.trajectory.vehcTrajectory.length > 0) {
                    var bluegps = data.trajectory.vehcTrajectory;
                    //开始点
                    var point = new BMap.Point(bluegps[0].longitude, bluegps[0].latitude);
                    setTimeout(function () {
                        map.panTo(point);
                    }, 1500);
                    var icon = new BMap.Icon("img/trafficflux/trajectory/gpsStart.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                    var markerStart = new BMap.Marker(point, {icon: icon});
                    map.addOverlay(markerStart);

                    var bluepoints = [];
                    for (var j = 0; j < bluegps.length; j++) {
                        bluepoints.push(new BMap.Point(bluegps[j].longitude, bluegps[j].latitude));
                    }
                    if (bluepoints.length > 0) {
                        var bluepolyline = new BMap.Polyline(bluepoints, {
                            strokeColor: "blue",
                            strokeWeight: 4,
                            strokeOpacity: 0.5
                        });
                        map.addOverlay(bluepolyline);
                    }

                    //结束点
                    if (bluegps > 1) {
                        var icon = new BMap.Icon("img/trafficflux/trajectory/gpsEnd.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                        var markerEnd = new BMap.Marker(new BMap.Point(bluegps[bluegps.length - 1].longitude, bluegps[bluegps.length - 1].latitude), {icon: icon});
                        map.addOverlay(markerEnd);
                    }
                }
                else {
                    toastr.success("当前车辆没有位置信息", "提示信息");
                    return;
                }


                //画超速的线
                // if (data.trajectory.alarmList !=null && data.trajectory.alarmList !=undefined && data.trajectory.alarmList.length > 0) {
                //     for (var w = 0; w < data.trajectory.alarmList.speedList.length; w++) {
                //         var gps = data.trajectory.alarmList.speedList[w];
                //         var speedpoints = [];
                //
                //         for (var k = 0; k < gps.length; k++) {
                //             speedpoints.push(new BMap.Point(gps[k].longitude, gps[k].latitude));
                //             if (k == 0) {
                //                 var pointstart = new BMap.Point(gps[k].longitude, gps[k].latitude);
                //                 var icon = new BMap.Icon("img/trafficflux/trajectory/car-dragracestart.png", new BMap.Size(__seticon.dragracestart, __seticon.dragracestart));
                //                 var speedStart = new BMap.Marker(pointstart, { icon: icon });
                //                 map.addOverlay(speedStart);
                //             }
                //         }
                //         if (speedpoints.length > 0) {
                //             var speedpolyline = new BMap.Polyline(speedpoints, { strokeColor: "#FF6100", strokeWeight: 4 });
                //             map.addOverlay(speedpolyline);
                //         }
                //     }
                // }
                // 画飙车的线
                // if (data.dragraceGps.length > 0) {
                //     for (var f = 0; f < data.dragraceGps.length; f++) {
                //         var dragracegps = data.dragraceGps[f];
                //         var dragracepoints = [];
                //
                //         for (var g = 0; g < dragracegps.length; g++) {
                //             dragracepoints.push(new BMap.Point(dragracegps[g].VL_LONG, dragracegps[g].VL_LAT));
                //             if (g == 0) {
                //                 var pointstart = new BMap.Point(dragracegps[g].VL_LONG, dragracegps[g].VL_LAT);
                //                 var icon = new BMap.Icon("img/trafficflux/trajectory/car-dragracestart.png", new BMap.Size(__seticon.dragracestart, __seticon.dragracestart));
                //                 var speedStart = new BMap.Marker(pointstart, { icon: icon });
                //                 map.addOverlay(speedStart);
                //             }
                //         }
                //         if (dragracepoints.length > 0) {
                //             var dragracepolyline = new BMap.Polyline(dragracepoints, { strokeColor: "#eb1c65", strokeWeight: 4 });
                //             map.addOverlay(dragracepolyline);
                //         }
                //     }
                // }
                if (data.trajectory.alarmList != null && data.trajectory.alarmList != undefined && data.trajectory.alarmList.length > 0) {
                    alarmList = data.trajectory.alarmList;
                    var speedpoints = [];
                    var timepoints = [];
                    for (var k = 0; k < alarmList.length; k++) {
                        if (alarmList[k].alarmType == 1) {//画超速的线
                            speedpoints.push(new BMap.Point(alarmList[k].longitude, alarmList[k].latitude));
                            if (k == 0) {
                                var pointstart = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                                var icon = new BMap.Icon(basePath + "img/trafficflux/trajectory/car-dragracestart.png", new BMap.Size(__seticon.dragracestart, __seticon.dragracestart));
                                var speedStart = new BMap.Marker(pointstart, {icon: icon});
                                map.addOverlay(speedStart);
                            }
                        }
                        if (alarmList[k].alarmType == 2) {
                            var idling = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                            var icon = new BMap.Icon(basePath + "img/trafficflux/trajectory/car-idling.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                            var mark7 = new BMap.Marker(idling, {icon: icon});
                            map.addOverlay(mark7);
                            var content = "<div><p>  怠速报警  </p>";
                            content += "<p>开始时间：" + alarmList[k].startTime + "</p>";
                            content += "<p>结束时间：" + alarmList[k].endTime + "</p>";
                            content += "<p>怠速时长：" + DateMinus(alarmList[k].startTime, alarmList[k].endTime) + "</p>";
                            content += "<p>耗油：" + alarmList[k].fuel + "</p>";
                            //content += "<p>位置：" + alarmList[k].VS_ADDRESS + "</p></div>";
                            mark7.addEventListener("click", openInfo1.bind(null, content));
                        }
                        if (alarmList[k].alarmType == 4) {
                            var acceleratestart = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                            var icon = new BMap.Icon(basePath + "img/trafficflux/trajectory/car-accelerate.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                            var mark4 = new BMap.Marker(acceleratestart, {icon: icon});
                            map.addOverlay(mark4);
                            var content = "<div><p>  急加速报警  </p>";
                            content += "<p>时间：" + alarmList[k].startTime + "</p>";
                            content += "<p>速度：" + alarmList[k].speed + " km/h</p>";
                            //content += "<p>位置：" + alarmList[i].VS_ADDRESS + "</p></div>";
                            mark4.addEventListener("click", openInfo1.bind(null, content));
                        }
                        if (alarmList[k].alarmType == 5) {
                            var deceleratestart = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                            var icon = new BMap.Icon(basePath + "img/trafficflux/trajectory/car-decelerate.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                            var mark5 = new BMap.Marker(deceleratestart, {icon: icon});
                            map.addOverlay(mark5);
                            var content = "<div><p>  急减速报警  </p>";
                            content += "<p>时间：" + alarmList[k].startTime + "</p>";
                            content += "<p>速度：" + alarmList[k].speed + " km/h</p>";
                            //content += "<p>位置：" + alarmList[i].VS_ADDRESS + "</p></div>";
                            mark5.addEventListener("click", openInfo1.bind(null, content));
                        }
                        if (alarmList[k].alarmType == 6) {
                            var sharpTurn = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                            var icon = new BMap.Icon("img/trafficflux/trajectory/car-turn.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                            var mark8 = new BMap.Marker(sharpTurn, {icon: icon});
                            map.addOverlay(mark8);
                            var content = "<div><p>  急转弯报警  </p>";
                            content += "<div><p>报警时间：" + alarmList[k].startTime + "</p>";
                            content += "<p>速度：" + alarmList[k].speed + " km/h</p>";
                            //content += "<p>位置：" + alarmList[i].VS_ADDRESS + "</p></div>";
                            mark8.addEventListener("click", openInfo1.bind(null, content));
                        }
                        if (alarmList[k].alarmType == 7) {
                            var powerFailure = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                            var icon = new BMap.Icon(basePath + "img/trafficflux/trajectory/car-power-failure.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                            var mark6 = new BMap.Marker(powerFailure, {icon: icon});
                            map.addOverlay(mark6);
                            var content = "<div><p>  断电报警  </p>";
                            content += "<p>时间：" + alarmList[k].startTime + "</p>";
                            content += "<p>速度：" + alarmList[k].speed + " km/h</p>";
                            //content += "<p>位置：" + alarmList[i].VS_ADDRESS + "</p></div>";
                            mark6.addEventListener("click", openInfo1.bind(null, content));
                        }
                        if (alarmList[k].alarmType == 12) {
                            timepoints.push(new BMap.Point(alarmList[k].longitude, alarmList[k].latitude));
                            if (k == 0) {
                                var pointstart = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                                var icon = new BMap.Icon(basePath + "img/trafficflux/trajectory/car-dragracestart.png", new BMap.Size(__seticon.dragracestart, __seticon.dragracestart));
                                var timeStart = new BMap.Marker(pointstart, {icon: icon});
                                map.addOverlay(timeStart);
                            }
                        }
                    }
                    if (speedpoints.length > 0) {
                        var speedpolyline = new BMap.Polyline(speedpoints, {strokeColor: "#FF6100", strokeWeight: 4});
                        map.addOverlay(speedpolyline);
                    }
                    if (timepoints.length > 0) {
                        var timepolyline = new BMap.Polyline(timepoints, {strokeColor: "#FF6100", strokeWeight: 4});
                        map.addOverlay(timepolyline);
                    }
                    followChk = document.getElementById("follow");
                    playBtn = document.getElementById("play");
                    pauseBtn = document.getElementById("pause");
                    resetBtn = document.getElementById("reset");
                    speedChoiceBtn = document.getElementById("speedChoice");
                    //点亮操作按钮
                    playBtn.disabled = false;
                    $("#playImg").attr('src', basePath + 'img/trafficflux/vehcTrack/btn_bofang_def.png');
                    $("#speedChoiceImg").attr('src', basePath + 'img/trafficflux/vehcTrack/btn_speed_def.png');
//                         var iconSize = new BMap.Size(32, 32);
//                         car = new BMap.Marker(bluepoints[0], { icon: icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_01.png', iconSize) });
//                         map.addOverlay(car);
//                         car.setTop(true, 99999);

                } else {
                    toastr.success("当前行程没有报警信息", "提示信息");
                    return;
                }
            },
            error: function (xhr, status, error) {
                showerror(xhr.responseText);
            }
        });
    }

    var opts = {
        width: 270,     // 信息窗口宽度
        height: 110,     // 信息窗口高度
        enableMessage: false,
        offset: new BMap.Size(0, -20)
    }

    //弹出不用解析地址的窗口
    function openInfo1(content, e) {
        var p = e.target;
        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
        var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
        map.openInfoWindow(infoWindow, point); //开启信息窗口
    }

    //弹出需要解析地址的窗口
    function openInfo(contentObj, e) {
        var p = e.target;
        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
        var infoWindow = new BMap.InfoWindow(contentObj.content, opts);  // 创建信息窗口对象
        map.openInfoWindow(infoWindow, point); //开启信息窗口
        if (!contentObj.address) {
            gc.getLocation(point, function (rs) {
                contentObj.content = contentObj.content.replace("未解析地址", rs.address);
                infoWindow.setContent(contentObj.content);
                contentObj.address = rs.address;
            });
        }
    }

    //初始化怠速列表
    function initGrid() {
        var gridObj = {
            id: "dtGrid",
            bProcessing: true,
            bRetrieve: true,
            userQueryParam: [{"name": "trackId", "value": vehcTrackId}],
            sAjaxSource: "AlarmIdle/getAlarmIdleByPage",
            columns: [
                {"mDataProp": "startTime", "sTitle": "开始时间"},
                {"mDataProp": "endTime", "sTitle": "结束时间"},
                {"mDataProp": "idleTime", "sTitle": "怠速时长"},

            ],

        };

        dtGrid = renderGrid(gridObj);

    }


    var myChart;
    var option;

    function initTrackChar() {
        myChart = echarts.init(document.getElementById('avgspeedchar'));
        option = {
            tooltip: {
                formatter: "{a} <br/>{c} {b}"
            },
            series: [
                {
                    name: '当前速度',
                    type: 'gauge',
                    min: 0,
                    max: 220,
                    splitNumber: 11,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            width: 10
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        length: 15,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'auto'
                        }
                    },
                    splitLine: {           // 分隔线
                        length: 20,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            color: 'auto'
                        }
                    },
                    title: {
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: 'bolder',
                            fontSize: 20,
                            fontStyle: 'italic'
                        }
                    },
                    detail: {
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: 'bolder'
                        }
                    },
                    data: [{value: 0.00, name: 'km/h'}]
                },
                {
                    name: '当前转速',
                    type: 'gauge',
                    center: ['25%', '55%'],    // 默认全局居中
                    radius: '50%',
                    min: 0,
                    max: 7,
                    endAngle: 45,
                    splitNumber: 7,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            width: 8
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        length: 12,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'auto'
                        }
                    },
                    splitLine: {           // 分隔线
                        length: 20,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            color: 'auto'
                        }
                    },
                    pointer: {
                        width: 5
                    },
                    title: {
                        offsetCenter: [0, '-30%'],       // x, y，单位px
                    },
                    detail: {
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: 'bolder'
                        }
                    },
                    data: [{value: 0.00 / 1000, name: '1000 r/min'}]
                },
                {
                    name: '当前油耗',
                    type: 'gauge',
                    center: ['75%', '50%'],    // 默认全局居中
                    radius: '50%',
                    min: 0,
                    max: 2,
                    startAngle: 135,
                    endAngle: 45,
                    splitNumber: 2,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: [[0.2, '#ff4500'], [0.8, '#48b'], [1, '#228b22']],
                            width: 8
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        splitNumber: 5,
                        length: 10,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'auto'
                        }
                    },
                    axisLabel: {
                        formatter: function (v) {
                            switch (v + '') {
                                case '0':
                                    return 'E';
                                case '1':
                                    return '油耗';
                                case '2':
                                    return 'F';
                            }
                        }
                    },
                    splitLine: {           // 分隔线
                        length: 15,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            color: 'auto'
                        }
                    },
                    pointer: {
                        width: 2
                    },
                    title: {
                        show: false
                    },
                    detail: {
                        show: false
                    },
                    data: [{value: 0.00, name: ' L'}]
                },
                {
                    name: '当前温度',
                    type: 'gauge',
                    center: ['75%', '50%'],    // 默认全局居中
                    radius: '50%',
                    min: 0,
                    max: 2,
                    startAngle: 315,
                    endAngle: 225,
                    splitNumber: 2,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: [[0.2, '#ff4500'], [0.8, '#48b'], [1, '#228b22']],
                            width: 8
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        show: false
                    },
                    axisLabel: {
                        formatter: function (v) {
                            switch (v + '') {
                                case '0':
                                    return 'H';
                                case '1':
                                    return '水温';
                                case '2':
                                    return 'C';
                            }
                        }
                    },
                    splitLine: {           // 分隔线
                        length: 15,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            color: 'auto'
                        }
                    },
                    pointer: {
                        width: 2
                    },
                    title: {
                        show: false
                    },
                    detail: {
                        show: false
                    },
                    data: [{value: 0.00, name: '°C'}]
                },
            ]
        }
        myChart.setOption(option, true)
    }

    function ConvertSecond(value) {
        var theTime = parseInt(value);// 秒  
        var theTime1 = 0;// 分  
        var theTime2 = 0;// 小时  

        if (theTime > 60) {
            theTime1 = parseInt(theTime / 60);
            theTime = parseInt(theTime % 60);

            if (theTime1 > 60) {
                theTime2 = parseInt(theTime1 / 60);
                theTime1 = parseInt(theTime1 % 60);
            }
        }
        var result = "" + parseInt(theTime) + "秒";
        if (theTime1 > 0) {
            result = "" + parseInt(theTime1) + "分" + result;
        }
        if (theTime2 > 0) {
            result = "" + parseInt(theTime2) + "时" + result;
        }
        return result;
    }
}



