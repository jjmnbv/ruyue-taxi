$(function () {
    getVehcList();//绑定界面数据
//            initTempTrend();
});

//获取车辆列表
function getVehcList() {
    $.ajax({
        url: " AlarmWaterTemp/QueryWaterTempTrend",
        data: {id: $("#id").val()},
        cache: false,
        success: function (data) {
            //初始化车辆信息和报警详情
            initVehcTowInfo(data);
            initTempTrend(data.waterTempTrend, data);
        }
    });
}

//初始化车辆信息和报警详情
function initVehcTowInfo(tow) {
    if (tow != null && tow != "undefined") {
        $("#plate").text(tow.plate);
        $("#imei").text(tow.imei);
        $("#department").text(tow.department);
        $("#alarmTime").text(tow.alarmTime);
        $("#releaseTime").text(tow.releaseTime);
        $("#maxTemp").text(tow.maxTemp + "°C");
    }
}

//初始化水温趋势
function initTempTrend(tempList, waterTempModel) {
    var temp = [];
    var minTemp;
    var maxTemp;
    if (tempList != null && tempList != "" && tempList != "undefined" && tempList.length > 0) {
        for (var i = 0; i < tempList.length; i++) {
            var currentTemp = parseInt(tempList[i].alarmTemp);
            temp.push([revert(tempList[i].alarmTime), tempList[i].alarmTemp]);
            if (minTemp == null || minTemp == undefined || minTemp == "") {
                if (tempList[i].alarmTemp != null || tempList[i].alarmTemp != "" || tempList[i].alarmTemp != "undefined") {
                    minTemp = tempList[i].alarmTemp;
                }
            }
            if (maxTemp == null || maxTemp == undefined || maxTemp == "") {
                if (tempList[i].alarmTemp != null || tempList[i].alarmTemp != "" || tempList[i].alarmTemp != "undefined") {
                    maxTemp = tempList[i].alarmTemp;
                }
            }
            //获得最低最高温度，动态变化水温趋势的Y轴值
            if (currentTemp < minTemp) {
                minTemp = tempList[i].alarmTemp;
            }
            if (currentTemp > maxTemp) {
                maxTemp = tempList[i].alarmTemp;
            }
        }
        var options = {
            hoverable: true,
            series: {
                lines: {show: true},
                points: {show: true}
            },
            grid: {
                hoverable: true,
                clickable: true,
                show: true,
                aboveData: true,
                borderWidth: 1,
                color: "#D9D9D9"
            },
            xaxis: {
                mode: "time",
                timeformat: "%H:%M:%S",
                minTickSize: [10, ""], //不显示X轴
                min: revert(tempList[0].alarmTime),
                max: revert(tempList[tempList.length - 1].alarmTime) + 294000,
                twelveHourClock: false
            },
            yaxis: {
                tickDecimals: 3,
                autoscaleMargin: 0.2
            }

        };
        $.plot("#placeholder", [temp], options);
        bindPoint();
    }
}
//显示数据点的提示信息
function showTooltip(x, y, contents) {
    $('<div id="tooltip">' + contents + '</div>').css({
        position: 'absolute',
        display: 'none',
        top: y + 5,
        left: x + 5,
        border: '1px solid #fdd',
        padding: '2px',
        'background-color': '#fee',
        'font-size': '12px',
        opacity: 0.80
    }).appendTo("body").fadeIn(200);
}
//给温度趋势绑定曲线图，点击曲线点显示时间和温度
function bindPoint() {
    var previousPoint = null;
    $("#placeholder").bind("plothover", function (event, pos, item) {
        if (true) {
            if (item) {
                if (previousPoint != item.datapoint) {
                    previousPoint = item.datapoint;
                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2),
                        y = item.datapoint[1].toFixed(2);
                    var dateTime = new Date();
                    dateTime.setTime(x);
                    var year = dateTime.getFullYear();
                    var month = dateTime.getMonth() + 1 < 10 ? "0" + (dateTime.getMonth() + 1) : dateTime.getMonth() + 1;
                    var date = dateTime.getDate() < 10 ? "0" + dateTime.getDate() : dateTime.getDate();
                    var hour = dateTime.getHours() < 10 ? "0" + dateTime.getHours() : dateTime.getHours();
                    var minute = dateTime.getMinutes() < 10 ? "0" + dateTime.getMinutes() : dateTime.getMinutes();
                    var second = dateTime.getSeconds() < 10 ? "0" + dateTime.getSeconds() : dateTime.getSeconds();
                    var dateTimeStr = year + "/" + month + "/" + date + " " + hour + ":" + minute + ":" + second;
                    var context = "报警时间：" + dateTimeStr + "  , 温度：" + y + "°C";
                    showTooltip(item.pageX, item.pageY, context);
                }
            }
            else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        }
    });
}
//X轴时间的转变
function revert(str) {
    var data = eval('new Date(' + str.replace(/\d+(?=-[^-]+$)/, function (a) {
            return parseInt(a, 10) - 1;
        }).match(/\d+/g) + ')').getTime();
    return data;
}