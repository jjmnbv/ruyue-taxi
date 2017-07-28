var eqpId=$('#eqpId').val();
var trackId=$('#trackId').val();
var imei =$("#imei").val();
$(function () {
    bindVehcInfo();//绑定车辆信息
    bindMap();  //绑定地图行驶轨迹
});
//返回按钮
function back() {
	window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "Track/TrackRecord?imei="+imei;
}
//绑定车辆信息
function bindVehcInfo() {	
    $.ajax({
        url: 'Track/getTrackDetails',
        cache: false,
        data: { trackId: trackId , apikey:apikey},
        success: function (data) {
        	$("#pImei").text(data.trackInfo.imei);
            $("#pV_PLATES").text(data.trackInfo.plate);
           $("#pV_DEPT").text(data.trackInfo.departmentName);
        	bindVehcTrackDetail(data.trackInfo);
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText);
        }
    });
}

//绑定行程明细
var chart;
var legend;
var trackData = [];
var trackPieData = [];
function bindVehcTrackDetail(data) {
    $("#pVT_STARTTIME").text(data.trackStartTime);
    $("#pVT_ENDTIME").text(data.strokeEndTime);
    $("#pVT_TOTALTIME").text(data.totalTimeText);
    $("#pVT_IDLETIME").text(data.idleTimeText);
    $("#pVT_RUNTIME").text(data.runTimeText);
    $("#pVT_TOTALMILEAGE").text(data.trackMileage);
    $("#pVT_FUELCONSUMPTION").text(data.fuelConsumption);
    $("#pVT_CUMULATIVEOIL").text(data.cumulativeOil >0 ? Number(data.cumulativeOil).toFixed(2) : data.cumulativeOil);
    $("#pVT_IDLEFUEL").text(data.idleFuel >0 ? Number(data.idleFuel).toFixed(2) : data.idleFuel);
    trackPieData.push({ country: "0-20", litres: data.mileage0020, color: "#FF9966" });
    trackPieData.push({ country: "20-40", litres: data.mileage2040, color: "#FFCC00" });
    trackPieData.push({ country: "40-60", litres: data.mileage4060, color: "#99CC00" });
    trackPieData.push({ country: "60-90", litres: data.mileage6090, color: "#3DBB00" });
    trackPieData.push({ country: "90-120", litres: data.mileage90120, color: "#17C1D5" });
    trackPieData.push({ country: ">120", litres: data.mileage120, color: "#FF6868" });
    trackData.push({ "name": "0-20", "open": 0, "close": data.mileage0020, "color": "#FF9966", "balloonValue": data.mileage0020 });
    trackData.push({ "name": "20-40", "open": 0, "close": data.mileage2040, "color": "#FFCC00", "balloonValue": data.mileage2040 });
    trackData.push({ "name": "40-60", "open": 0, "close": data.mileage4060, "color": "#99CC00", "balloonValue": data.mileage4060 });
    trackData.push({ "name": "60-90", "open": 0, "close": data.mileage6090, "color": "#3DBB00", "balloonValue": data.mileage6090 });
    trackData.push({ "name": "90-120", "open": 0, "close": data.mileage90120, "color": "#17C1D5", "balloonValue": data.mileage90120 });
    trackData.push({ "name": ">120", "open": 0, "close": data.mileage120, "color": "#FF6868", "balloonValue": data.VT_MILEAGE120 });
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
            bottomText: data.avgTrackSpeed + " km/h",
            bands: [{
                startValue: 0,
                endValue: 90,
                color: "#00CC00"
            },{
                startValue: 90,
                endValue: 130,
                color: "#ffac29"
                },{
                    startValue: 130,
                    endValue: 220,
                    color: "#ea3838",
                    innerRadius: "95%"
                }
            ]
        }],
        arrows: [{
                value: data.avgTrackSpeed
            }]
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
            bottomText: data.maxSpeed + " km/h",
            bands: [{
                startValue: 0,
                endValue: 90,
                color: "#00CC00"
            },{
	            startValue: 90,
	            endValue: 130,
	            color: "#ffac29"
            },{
                startValue: 130,
                endValue: 220,
                color: "#ea3838",
                innerRadius: "95%"
                }
            ]
        }],
        arrows: [{ value: data.maxSpeed }]
    });
}

//绑定地图行驶轨迹
function bindMap() {
    map = new BMap.Map("map_canvas");
    var myCity = new BMap.LocalCity();
    myCity.get(myFun);
    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.enableScrollWheelZoom();

    //获取行驶轨迹
    $.ajax({
        url: 'Track/getVehcLocation',
        cache: false,
        data: { trackId: trackId,apikey:apikey ,eqpId:eqpId},
        success: function (data1) {
        	var data=data1.vehcTrajectory;
        	//todo
            if (data.length > 0) {
                var point = new BMap.Point(data[0].longitude, data[0].latitude);
                setTimeout(function () {
                    map.panTo(point);
                }, 1500);
                var icon = new BMap.Icon("img/trafficflux/gpsStart.png", new BMap.Size(29, 35));
                var markerStart = new BMap.Marker(point, { icon: icon });
                map.addOverlay(markerStart);
            }
            var points = [];
            for (var i = 0; i < data.length; i++) {
                points.push(new BMap.Point(data[i].longitude, data[i].latitude));
            }
            if (points.length > 0) {
                var polyline = new BMap.Polyline(points, { strokeColor: "blue", strokeWeight: 3, strokeOpacity: 0.5 });
                map.addOverlay(polyline);
            }
            if (points.length > 1) {
                var icon = new BMap.Icon("img/trafficflux/gpsEnd.png", new BMap.Size(29, 35));
                var markerEnd = new BMap.Marker(new BMap.Point(data[data.length - 1].longitude, data[data.length - 1].latitude), { icon: icon });
                map.addOverlay(markerEnd);
            }
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText);
        }
    });
}

function myFun(result) {
    var cityName = result.name;
    map.centerAndZoom(cityName, 15);
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
    graph.balloonText = "<span style='color:[[color]]'>[[category]] km/h</span><br><span style='font-size:12px;'><b>[[balloonValue]] km</b></span>";
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