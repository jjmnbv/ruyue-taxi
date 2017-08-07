function loadHistorytrack(vehcId, vehcTrackId) {

    $(function () {
        initGrid();
        bindVehcTrackDetail();
        onShowMap();
    });

    var map;
    var prevPoint;
    var marker;

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


    //绑定行程明细
    var chart;
    var legend;
    var trackData = [];
    var trackPieData = [];

    function bindVehcTrackDetail() {

        if ($("#pV_DEPT").text().length <= 7) {
            $("#pV_DEPT").css({"line-height": "45px"});
        }
        if ($("#pD_NAME").text().length <= 7) {
            $("#pD_NAME").css({"line-height": "45px"});
        }
        //alert($("#pV_DEPT").text())
        trackPieData.push({country: "0-20", litres: $("#VT_MILEAGE0020").val(), color: "#FF9966"});
        trackPieData.push({country: "20-40", litres: $("#VT_MILEAGE2040").val(), color: "#FFCC00"});
        trackPieData.push({country: "40-60", litres: $("#VT_MILEAGE4060").val(), color: "#99CC00"});
        trackPieData.push({country: "60-90", litres: $("#VT_MILEAGE6090").val(), color: "#3DBB00"});
        trackPieData.push({country: "90-120", litres: $("#VT_MILEAGE90120").val(), color: "#17C1D5"});
        trackPieData.push({country: ">120", litres: $("#VT_MILEAGE120").val(), color: "#FF6868"});
        trackData.push({
            "name": "0-20",
            "open": 0,
            "close": $("#VT_MILEAGE0020").val(),
            "color": "#FF9966",
            "balloonValue": $("#VT_MILEAGE0020").val()
        });
        trackData.push({
            "name": "20-40",
            "open": 0,
            "close": $("#VT_MILEAGE2040").val(),
            "color": "#FFCC00",
            "balloonValue": $("#VT_MILEAGE2040").val()
        });
        trackData.push({
            "name": "40-60",
            "open": 0,
            "close": $("#VT_MILEAGE4060").val(),
            "color": "#99CC00",
            "balloonValue": $("#VT_MILEAGE4060").val()
        });
        trackData.push({
            "name": "60-90",
            "open": 0,
            "close": $("#VT_MILEAGE6090").val(),
            "color": "#3DBB00",
            "balloonValue": $("#VT_MILEAGE6090").val()
        });
        trackData.push({
            "name": "90-120",
            "open": 0,
            "close": $("#VT_MILEAGE90120").val(),
            "color": "#17C1D5",
            "balloonValue": $("#VT_MILEAGE90120").val()
        });
        trackData.push({
            "name": ">120",
            "open": 0,
            "close": $("#VT_MILEAGE120").val(),
            "color": "#FF6868",
            "balloonValue": $("#VT_MILEAGE120").val()
        });
        reportList();

        //平均速度
        var avgspeedchar = AmCharts.makeChart("avgspeedchar", {
            type: "gauge",
            axes: [{
                startValue: 0,
                axisThickness: 1,
                endValue: 220,
                valueInterval: 20,
                bottomTextYOffset: -20,
                bottomText: $("#VT_AVGSPEED").val() + " km/h",
                bands: [{
                    startValue: 0,
                    endValue: 90,
                    color: "#00CC00"
                },

                    {
                        startValue: 90,
                        endValue: 130,
                        color: "#ffac29"
                    },

                    {
                        startValue: 130,
                        endValue: 220,
                        color: "#ea3838",
                        innerRadius: "95%"
                    }
                ]
            }],
            arrows: [{value: $("#VT_AVGSPEED").val()}]
        });

        //最高速度
        var maxspeedchar = AmCharts.makeChart("maxspeedchar", {
            type: "gauge",
            axes: [{
                startValue: 0,
                axisThickness: 1,
                endValue: 220,
                valueInterval: 20,
                bottomTextYOffset: -20,
                bottomText: $("#VT_MAXSPEED").val() + " km/h",

                bands: [{
                    startValue: 0,
                    endValue: 90,
                    color: "#00CC00"
                },
                    {
                        startValue: 90,
                        endValue: 130,
                        color: "#ffac29"
                    },

                    {
                        startValue: 130,
                        endValue: 220,
                        color: "#ea3838",
                        innerRadius: "95%"
                    }
                ]
            }],
            arrows: [{value: $("#VT_MAXSPEED").val()}]
        });
    }

    //图形报表
    function reportList() {

        // Waterfall chart is a simple serial chart with some specific settings
        var chart = new AmCharts.AmSerialChart();
        chart.dataProvider = trackData;
        chart.categoryField = "name";
        chart.columnWidth = 0.6;
        chart.startDuration = 1;

        // AXES
        // Category
        var categoryAxis = chart.categoryAxis;
        categoryAxis.gridAlpha = 0.1;
        categoryAxis.axisAlpha = 0;
        categoryAxis.gridPosition = "start";

        // Value
        var valueAxis = new AmCharts.ValueAxis();
        valueAxis.gridAlpha = 0.1;
        valueAxis.axisAlpha = 0;
        chart.addValueAxis(valueAxis);

        // GRAPH
        var graph = new AmCharts.AmGraph();
        graph.valueField = "close";
        graph.openField = "open";
        graph.type = "column";
        graph.lineAlpha = 0;
        //graph.lineColor = "#BBBBBB";
        graph.colorField = "color";
        graph.fillAlphas = 0.8;
        graph.balloonText = "<span style='color:[[color]]'>[[category]]</span><br><span style='font-size:12px;'><b>[[balloonValue]] km</b></span>";
        graph.labelText = "[[balloonValue]]";
        chart.addGraph(graph);
        // WRITE
        chart.write("mileagechar");

        // PIE CHART
        var chartdiv = new AmCharts.AmPieChart();
        chartdiv.dataProvider = trackPieData;
        chartdiv.titleField = "country";
        chartdiv.valueField = "litres";
        chartdiv.outlineColor = "#FFFFFF";
        chartdiv.colorField = "color";
        chartdiv.outlineAlpha = 0.6;
        chartdiv.outlineThickness = 0.1;
        // WRITE
        chartdiv.write("chartdiv");
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