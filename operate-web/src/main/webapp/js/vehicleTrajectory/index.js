var map,
	gc = new BMap.Geocoder(),
	 marker1,
	 timer,//定时器
	 index = 0, //记录播放到第几个point
	 bluepoints = [],
	 followChk, playBtn, pauseBtn, resetBtn, speedChoiceBtn,//控制按钮
	 bluegps;
var timerclick = null;
var exportButton;//导出
var trajectoryAll = null;//行程轨迹
$(function () {
    $.fn.modal.Constructor.prototype.enforceFocus = function () { };
    bindControl();
    bindMap();  //绑定地图行驶轨迹
    /**
     * 数据回显
     */
    if (null != echoeqpId && echoeqpId !="" && echoeqpId!=undefined) {
        $("#selPlates").val( echoVehcId +","+echoeqpId);
        $("#s2id_selPlates .select2-chosen").text(echoplate+'|'+echoImei);
        $("#txtStartDate").val(echostartTime + ' 00:00');
        $("#txtEndDate").val(echoendTime + ' 23:59');
        $("#btnSearch").click();
    }
    $("#hList").hide()
    $("#map_canvas").css("height", "655px");
    //点击速度按钮 出现 回放速度div
    $("#speedChoice").click(function () {
        $("#speed").show();
    });
    //播放按钮的鼠标移入移出事件
    $("#play").mouseover(function () {
        $("#playImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_bofang_set.png');
    }).mouseout(function () {
        $("#playImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_bofang_def.png');
    });
    //暂停按钮的鼠标移入移出事件
    $("#pause").mouseover(function () {
        $("#pauseImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_stop_set.png');
    }).mouseout(function () {
        $("#pauseImg").attr('src',basePath +'img/trafficflux/vehcTrack/btn_stop_def.png');
    });
    //重置按钮的鼠标移入移出事件
    $("#reset").mouseover(function () {
        $("#resetImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_chong_set.png');
    }).mouseout(function () {
        $("#resetImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_chong_def.png');
    });
    //重置按钮的鼠标移入移出事件
    $("#speedChoice").mouseover(function () {
        $("#speedChoiceImg").attr('src',basePath +'img/trafficflux/vehcTrack/btn_speed_set.png');
    }).mouseout(function () {
        $("#speedChoiceImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_speed_def.png');
    });
    var frmmodal = $("#frmmodal");
    var erroralert = $('.alert-danger', frmmodal);
    var successalert = $('.alert-success', frmmodal);
    validator = frmmodal.validate({
        submitHandler: function (form) {
            successalert.show();
            erroralert.hide();
            SaveExport();
        }
    });

});

/**
 * 绑定控件
 */
function bindControl() {
    //初始化回放速度
    $("#rangeName").ionRangeSlider({
        min: 0,
        max: 1000,
        from: 100,
        to: 100,
        step: 100,
        grid: true,
        grid_snap: true,
        onChange: function (obj) {
        }, onFinish: function (obj) {
            $("#rangeFrom").val(obj.from);
        }
    });
    //绑定日期控件
	$('.searchDate').datetimepicker({
        format: "yyyy-mm-dd hh:ii", //选择日期后，文本框显示的日期格式
        language: 'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 0,
        forceParse: true,
        clearBtn: true,
        minuteStep: 10
    });

	$("#selPlates").select2({
		placeholder : "车牌",
		minimumInputLength : 1,
		allowClear : true,
		ajax : {
			url : 'Location/getVehclineByQuery',
			dataType : 'json',
			data : function(term, page) {
				return {
					plate : term,
					apikey:apikey
				};
			},
			results: function (data, page) {
				var eqpList=[];
				if(data!=null){
					for(var i=0;i<data.vhecEqpList.length;i++){
						var person=new Object(); 
						person.id=data.vhecEqpList[i].vehcId + "," + data.vhecEqpList[i].eqpId;
						person.text=data.vhecEqpList[i].plate +"|"+data.vhecEqpList[i].imei;
						eqpList.push(person);
					}
				}
                return { results: eqpList };
              }
		}
	});
    
    $("#mdselPlates").select2({
        placeholder: "车牌",
        minimumInputLength: 3,
        aysnc: false,
        allowClear: true,
        ajax: {
        	url : 'VehicleTrajectory/getVehclineByQuery',
			dataType : 'json',
			data : function(term, page) {
				return {
					plate : term,
					apikey:apikey
				};
			},
			results: function (data, page) {
				var eqpList=[];
				if(data!=null){
					for(var i=0;i<data.vhecEqpList.length;i++){
						var person=new Object(); 
						person.id=data.vhecEqpList[i].vehcId + "," + data.vhecEqpList[i].eqpId;
						person.text=data.vhecEqpList[i].plate +"|"+data.vhecEqpList[i].imei;
						eqpList.push(person);
					}
				}
                return { results: eqpList };
            }
        }
    });
    $("#btnSearch").click(function () {
        getTrackList();
        $("#hList").hide();
        $("#map_canvas").css("height", "655px");
        if (timer) {
            window.clearTimeout(timer);
        }
        index = 0;
    });
    $("#Grid").scroll(function () {
        GridHeaderSet();
    });
    $(window).resize(function () {
        GridHeaderSet();
    });
}

//弹出 导出界面
function ExportData() {
    var plates = $("#s2id_selPlates .select2-chosen").text();
    var strs= new Array();
    var id = $("#selPlates").select2("val");
    var strs = id.split(",");
    var vehcId = strs[0];
    var eqpId = strs[1];
    var startTime = $("#txtStartDate").val();
    var endTime = $("#txtEndDate").val();
    
    if ( null != vehcId && vehcId !="" && vehcId !="undefined") {
        $("#mdselPlates").val(vehcId + ","+eqpId);
        $("#s2id_mdselPlates .select2-chosen").text(plates);
    }
    if (startTime != null) {
        $("#txtStartTime").val(startTime)
    }
    if (endTime != null) {
        $("#txtEndTime").val(endTime)
    }
    $('#mdExport').modal('show');
}

//导出
function SaveExport() {
    var value = $("#radioList input:radio[name='optionsRadios']:checked").val();
    if (value == 1) {
        exportHistoryGps(); //导出轨迹
    }
    else if (value == 2) {
        exportVehcTrack(); //导出行程
    }
}
//绑定地图行驶轨迹
function bindMap() {
    map = new BMap.Map("map_canvas");
    var myCity = new BMap.LocalCity();
    myCity.get(myFun);
    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.enableScrollWheelZoom();
}
function myFun(result) {
    var cityName = result.name;
    map.centerAndZoom(cityName, 10);
}
//获取车辆列表
function getTrackList() {
    var plates = $("#s2id_selPlates .select2-chosen").text();
    var id = $("#selPlates").select2("val");
    var strs = id.split(",");
    var vehcId = strs[0];
    var eqpId = strs[1];

    var startTime = $("#txtStartDate").val();
    var endTime = $("#txtEndDate").val();
    $("#PointList").hide();
    $("#historyTrack").hide();
    if (vehcId == "" || vehcId == null || vehcId == undefined) {
        toastr.warning("请选择车牌", "提示信息");
        return;
    }
    var flag=getValidateTime(startTime,endTime);
    if (flag == false) {
        return;
    }
    map.clearOverlays();
    getTrajectoryByEqp(eqpId,startTime,endTime)
}

//查询轨迹
function getTrajectoryByEqp(eqpId,startTime,endTime){
	$.ajax({
    	url: basePath + 'VehicleTrajectory/getTrajectoryByEqp',
        cache: false,
        data: { apikey: apikey,eqpId: eqpId, startTime: startTime, endTime: endTime,returnResult:2,processOption:2 },
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            if (data == null || data == undefined || data.length == 0) {
                toastr.success("当前查询条件没有轨迹信息", "提示信息");
                return;
            }
            else {
                    bluepoints = [];
                    map.clearOverlays();
                    if (data.trajectory !=null && data.trajectory.vehcTrajectory !=null && data.trajectory.vehcTrajectory !=undefined && data.trajectory.vehcTrajectory.length > 0) {
                        bluegps = data.trajectory.vehcTrajectory;
                        //开始点
                        var point = new BMap.Point(bluegps[0].longitude, bluegps[0].latitude);
                        setTimeout(function () {
                            map.panTo(point);
                        }, 1500);
                        var icon = new BMap.Icon(basePath +"img/trafficflux/trajectory/gpsStart.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                        var markerStart = new BMap.Marker(point, { icon: icon });
                        map.addOverlay(markerStart);
                        markerStart.setTop(true, 99999);
                        gc.getLocation(point, function (rs) { $("#startPoint").text(rs.address); });
                        for (var j = 0; j < bluegps.length; j++) {
                            var point = new BMap.Point(bluegps[j].longitude, bluegps[j].latitude);
                            bluepoints.push(point);
                        }
                        if (bluepoints.length > 0) {
                            var bluepolyline = new BMap.Polyline(bluepoints, { strokeColor: "blue", strokeWeight: 4, strokeOpacity: 0.5 });
                            map.addOverlay(bluepolyline);
                        }
                        //结束点
                        if (bluegps.length > 1) {
                            var icon = new BMap.Icon(basePath +"img/trafficflux/trajectory/gpsEnd.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                            var endPoint = new BMap.Point(bluegps[bluegps.length - 1].longitude, bluegps[bluegps.length - 1].latitude);
                            var markerEnd = new BMap.Marker(endPoint, { icon: icon });
                            map.addOverlay(markerEnd);
                            markerEnd.setTop(true, 99999);
                            gc.getLocation(endPoint, function (rs) { $("#endPoint").text(rs.address); });
                           // $("#PointList").show();
                        }
                        
                        followChk = document.getElementById("follow");
                        playBtn = document.getElementById("play");
                        pauseBtn = document.getElementById("pause");
                        resetBtn = document.getElementById("reset");
                        speedChoiceBtn = document.getElementById("speedChoice");
                        //点亮操作按钮
                        playBtn.disabled = false;
                        $("#playImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_bofang_def.png');
                        $("#speedChoiceImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_speed_def.png');
                        var iconSize = new BMap.Size(32, 32);
                        car = new BMap.Marker(bluepoints[0], { icon: icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_01.png', iconSize) });
                        map.addOverlay(car);
                        car.setTop(true, 99999);
                    }else {
                        toastr.success("当前查询条件没有轨迹信息", "提示信息");
                        return;
                    }
                    //提醒类型(1_超速;2_怠速;3_疲劳驾驶;4_急加速;5_急减速;6_急转弯;7_断电;8_水温;9_拖吊;10_低电压;11_区域栅栏;12_时间栅栏;13_电子围栏)
                     if (data.trajectory.alarmList !=null && data.trajectory.alarmList !=undefined && data.trajectory.alarmList.length > 0) {
                     	alarmList = data.trajectory.alarmList;
                         var speedpoints = [];
                         var timepoints = [];
                         for (var k = 0; k < alarmList.length; k++) {
                         	if (alarmList[k].alarmType==1) {//画超速的线
                         		speedpoints.push(new BMap.Point(alarmList[k].longitude, alarmList[k].latitude));
                                 if (k == 0) {
                                     var pointstart = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                                     var icon = new BMap.Icon(basePath +"img/trafficflux/trajectory/car-dragracestart.png", new BMap.Size(__seticon.dragracestart, __seticon.dragracestart));
                                     var speedStart = new BMap.Marker(pointstart, { icon: icon });
                                     map.addOverlay(speedStart);
                                 }
                         	}
                         	if (alarmList[k].alarmType == 2) {
                                 var idling = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                                 var icon = new BMap.Icon(basePath +"img/trafficflux/trajectory/car-idling.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                                 var mark7 = new BMap.Marker(idling, { icon: icon });
                                 map.addOverlay(mark7);
                                 var content = "<div><p>  怠速报警  </p>";
                                 content += "<p>开始时间：" + alarmList[k].startTime + "</p>";
                                 content += "<p>结束时间：" + alarmList[k].endTime + "</p>";
                                 content += "<p>怠速时长：" + DateMinus(alarmList[k].startTime,alarmList[k].endTime) + "</p>";
                                 content += "<p>耗油：" + alarmList[k].fuel + "</p>";
                                 //content += "<p>位置：" + alarmList[k].VS_ADDRESS + "</p></div>";
                                 mark7.addEventListener("click", openInfo1.bind(null, content));
                             }
                         	if (alarmList[k].alarmType == 4) {
                                 var acceleratestart = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                                 var icon = new BMap.Icon(basePath +"img/trafficflux/trajectory/car-accelerate.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                                 var mark4 = new BMap.Marker(acceleratestart, { icon: icon });
                                 map.addOverlay(mark4);
                                 var content = "<div><p>  急加速报警  </p>";
                                 content += "<p>时间：" + alarmList[k].startTime + "</p>";
                                 content += "<p>速度：" + alarmList[k].speed + " km/h</p>";
                                 //content += "<p>位置：" + alarmList[i].VS_ADDRESS + "</p></div>";
                                 mark4.addEventListener("click", openInfo1.bind(null, content));
                             }
                             if (alarmList[k].alarmType == 5) {
                                 var deceleratestart = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                                 var icon = new BMap.Icon(basePath +"img/trafficflux/trajectory/car-decelerate.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                                 var mark5 = new BMap.Marker(deceleratestart, { icon: icon });
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
                                 var mark8 = new BMap.Marker(sharpTurn, { icon: icon });
                                 map.addOverlay(mark8);
                                 var content = "<div><p>  急转弯报警  </p>";
                                 content += "<div><p>报警时间：" + alarmList[k].startTime + "</p>";
                                 content += "<p>速度：" + alarmList[k].speed + " km/h</p>";
                                 //content += "<p>位置：" + alarmList[i].VS_ADDRESS + "</p></div>";
                                 mark8.addEventListener("click", openInfo1.bind(null, content));
                             }
                             if (alarmList[k].alarmType == 7) {
                                 var powerFailure = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                                 var icon = new BMap.Icon(basePath +"img/trafficflux/trajectory/car-power-failure.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
                                 var mark6 = new BMap.Marker(powerFailure, { icon: icon });
                                 map.addOverlay(mark6);
                                 var content = "<div><p>  断电报警  </p>";
                                 content += "<p>时间：" + alarmList[k].startTime + "</p>";
                                 content += "<p>速度：" + alarmList[k].speed + " km/h</p>";
                                 //content += "<p>位置：" + alarmList[i].VS_ADDRESS + "</p></div>";
                                 mark6.addEventListener("click", openInfo1.bind(null, content));
                             }
                         	if (alarmList[k].alarmType==12) {
                         		timepoints.push(new BMap.Point(alarmList[k].longitude, alarmList[k].latitude));
                                 if (k == 0) {
                                     var pointstart = new BMap.Point(alarmList[k].longitude, alarmList[k].latitude);
                                     var icon = new BMap.Icon(basePath +"img/trafficflux/trajectory/car-dragracestart.png", new BMap.Size(__seticon.dragracestart, __seticon.dragracestart));
                                     var timeStart = new BMap.Marker(pointstart, { icon: icon });
                                     map.addOverlay(timeStart);
                                 }
                         	}
                         }
                         if (speedpoints.length > 0) {
                             var speedpolyline = new BMap.Polyline(speedpoints, { strokeColor: "#FF6100", strokeWeight: 4 });
                             map.addOverlay(speedpolyline);
                         }
                         if (timepoints.length > 0) {
                             var timepolyline = new BMap.Polyline(timepoints, { strokeColor: "#FF6100", strokeWeight: 4 });
                             map.addOverlay(timepolyline);
                         }
                         followChk = document.getElementById("follow");
                         playBtn = document.getElementById("play");
                         pauseBtn = document.getElementById("pause");
                         resetBtn = document.getElementById("reset");
                         speedChoiceBtn = document.getElementById("speedChoice");
                         //点亮操作按钮
                         playBtn.disabled = false;
                         $("#playImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_bofang_def.png');
                         $("#speedChoiceImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_speed_def.png');
//                         var iconSize = new BMap.Size(32, 32);
//                         car = new BMap.Marker(bluepoints[0], { icon: icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_01.png', iconSize) });
//                         map.addOverlay(car);
//                         car.setTop(true, 99999);
                        
                     }else {
                         //toastr.success("当前行程没有报警信息", "提示信息");
                         return;
                     }
            	}
        },
        error: function (xhr, status, error) {
            return;
        },
        complete: function (xhr, ts) {
        }
    });
}


//导出行程信息
function exportVehcTrack() {
    var strs= new Array();
    var id = $("#selPlates").select2("val");
    var strs = id.split(",");
    var vehcId = strs[0];
    var eqpId = strs[1];

    var startTime = $("#txtStartTime").val();
    var endTime = $("#txtEndTime").val();

    if (vehcId == "" || vehcId == null || vehcId == undefined) {
        toastr.warning("请选择车牌", "提示信息");
        return;
    }
    if (startTime == "" || startTime == null || startTime == undefined) {
        toastr.warning("请选择开始时间", "提示信息");
        return;
    }
    if (endTime == "" || endTime == null || endTime == undefined) {
        toastr.warning("请选择结束时间", "提示信息");
        return;
    }
    if (startTime > endTime) {
        toastr.warning("开始时间必须小于结束时间", "提示信息");
        return;
    }
    
    window.location.href =basePath
	+ "VehicleTrajectory/ExportTrackRecord?apikey=" + apikey + "&eqpId="
	+ eqpId + "&startTime=" + startTime + "&endTime=" + endTime;
}

//导出历史轨迹
function exportHistoryGps() {
    // var eqpId = $("#mdselPlates").select2("val");
    var startTime = $("#txtStartTime").val();
    var endTime = $("#txtEndTime").val();
    var strs= new Array();
    var id = $("#mdselPlates").select2("val");
    var strs = id.split(",");
    var vehcId = strs[0];
    var eqpId = strs[1];
    if (vehcId == "" || vehcId == null || vehcId == undefined) {
        toastr.warning("请选择车牌", "提示信息");
        return;
    }
    if (startTime == "" || startTime == null || startTime == undefined) {
        toastr.warning("请选择开始时间", "提示信息");
        return;
    }
    if (endTime == "" || endTime == null || endTime == undefined) {
    	toastr.warning("请选择结束时间", "提示信息");       
    	return;
    }
    if (startTime > endTime) {
       toastr.warning("开始时间必须小于结束时间", "提示信息");
        return;
    }
    
    window.location.href =basePath
	+ "VehicleTrajectory/ExportTrajectory?apikey=" + apikey + "&eqpId="
	+ eqpId + "&vehcId=" +vehcId + "&startTime=" + startTime + "&endTime=" + endTime ;
}

/*********************************************************************轨迹回放*************************************************************/
function hideSpeed() {
    $("#speed").hide();
}

var opts = {
    width: 270,     // 信息窗口宽度
    height: 110,     // 信息窗口高度
    enableMessage: false,
    offset: new BMap.Size(0, -20)
}


function DateMinus(startTime,endTime){ 
	var start = new Date(startTime.replace(/-/g, "/")); 
	var end = new Date(endTime.replace(/-/g, "/"));
	var days = end.getTime() - start.getTime(); 
	return  ConvertSecond(days/1000);
}

function ConvertSecond(value) {
	if(null == value || value == undefined || value==""){
		return "";
	}
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
    if (theTime1 > 0) { result = "" + parseInt(theTime1) + "分" + result; }
    if (theTime2 > 0) { result = "" + parseInt(theTime2) + "时" + result; }
    return result;
}

//验证时间范围
function getValidateTime(fromTime, toTime) {
    var flag = true;
    if (fromTime == null || fromTime == "" || fromTime == "undefined"
       || toTime == null || toTime == "" || toTime == "undefined") {
        flag = false;
        toastr.warning("'开始时间、结束时间不能为空'，请确认!");
        return flag;
    }
    else {
        if (fromTime > toTime) {
            flag = false;
            toastr.warning("'开始时间' 应早于或等于 '结束时间'，请确认!");
            return flag;
        }

        var days = _Date.getDaysq(fromTime.substring(0, 10), toTime.substring(0, 10));
        if (days > 1) {
            flag = false;
            toastr.warning("此功能只能查一天以内的数据，请确认!");
            return flag;
        }
        else {
            return flag = true;
        }
    }
}

//验证三个月以内时间范围
function getValidateTimeTree(startTime,endTime)
{            
    var flag = true;
    if (startTime == null || startTime == "" || startTime == "undefined"
       || endTime == null || endTime == "" || endTime == "undefined") {
        flag = false;
        toastr.warning("'开始时间、结束时间不能为空'，请确认!");
        return flag;
    }
    else {
        if (startTime > endTime) {
            flag = false;
            toastr.warning("'开始时间' 应早于或等于 '结束时间'，请确认!");
            return flag;
        }

        var startTime = new Date(startTime.replace("-", "/").replace("-", "/"));
        var endTime = new Date(endTime.replace("-", "/").replace("-", "/"));
        var year = endTime.getYear() - startTime.getYear();
        var month = endTime.getMonth() - startTime.getMonth();
        if (year >0 || month > 2) {
            flag = false;
            toastr.warning("此功能只能导出3个月以内的数据，请确认!");
            return flag;
        }
        else {
            return flag = true;
        }
    }
}
//播放
function play() {
    playBtn.disabled = true;
    $("#playImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_bofang_no.png');

    pauseBtn.disabled = false;
    $("#pauseImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_stop_def.png');

    resetBtn.disabled = false;
    $("#resetImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_chong_def.png');

    $("#hList").show()
    $("#map_canvas").css("height", "400px");
    var data = bluegps[index];
    if (data.longitude && data.latitude) {
        var point = new BMap.Point(data.longitude, data.latitude);
        GridHeaderSet();
        var icon;
        var gpsdrct;
        var iconSize = new BMap.Size(32, 32);
        if (data.direction != null && data.direction != undefined) {
            //if (data.direction >= 337.5 || data.direction < 22.5) {
        	gpsdrct = data.direction;
            if (data.direction =="向北") {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_05.png', iconSize);
            }
            //else if (data.direction >= 22.5 && data.direction < 67.5) {
            else if (data.direction == "东北") {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_04.png', iconSize);
            }
            //else if (data.direction >= 67.5 && data.direction < 112.5) {
            else if (data.direction == "向东") {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_03.png', iconSize);
            }
            //else if (data.direction >= 112.5 && data.direction < 157.5) {
            else if (data.direction == "东南") {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_02.png', iconSize);
            }
            //else if (data.direction >= 157.5 && data.direction < 202.5) {
            else if (data.direction == "向南") {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_01.png', iconSize);
            }
            //else if (data.direction >= 202.5 && data.direction < 247.5) {
            else if (data.direction == "西南") {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_08.png', iconSize);
            }
            //else if (data.direction >= 247.5 && data.direction < 292.5) {
            else if (data.direction == "向西") {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_07.png', iconSize);
            }
            //else if (data.direction >= 292.5 && data.direction < 337.5) {
            else if (data.direction == "西北") {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_06.png', iconSize);
            }
            car.setIcon(icon);
        }
        car.setZIndex(0);
        car.setPosition(point);
        index++;
        
        var html = "<tr id='row_" + index + "' ondblclick='tableSelectRow(" + index + ")' onclick='tableClickRow(" + index + ")'>"; // 2016/9/28 增加点击事件
        //var html = "<tr id='row_" + index + "' ondblclick='tableSelectRow(" + index + ")'>";
        html += "<td style='width:80px'>" + index + "</td>";
        html += "<td id='column_" + index + "_2'>" + gpsdrct + "</td>";
        html += "<td id='column_" + index + "_3'>" + data.locTime + "</td>";
        html += "<td style='width:160px' id='column_" + index + "_4'>" + data.speed + "</td>";
        html += "<td id='column_" + index + "_5'>" + data.longitude + "</td>";
        html += "<td id='column_" + index + "_6'>" + data.latitude + "</td>";
        gc.getLocation(point, function (rs) { $("#txtAddress").val(rs.address); });
        html += "<td id='column_" + index + "_7'>" + $("#txtAddress").val() + "</td>";
        html += "</tr>";
        $("#trackList").after(html);

        $('#dtGrid tbody').off("click", "tr").on('click', 'tr', function () {
            $(this).addClass("hover").siblings().removeClass("hover");
        });

        if (followChk.checked) {
            map.panTo(point);
        }
        if (index < bluepoints.length) {
            timer = window.setTimeout("play(" + index + ")", $("#rangeFrom").val());
        } else {
            index = 0;
            toastr.success("轨迹回放完成", "提示信息");
            playBtn.disabled = false;
            $("#playImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_bofang_def.png');
            resetBtn.disabled = false;
            $("#resetImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_chong_def.png');
            //pauseBtn.disabled = true;
            //$("#pauseImg").attr('src', basePath +'Content/img/trafficflux/vehcTrack/btn_stop_no.png');
            map.panTo(point);
        }
    }
}
//暂停
function pause() {
    playBtn.disabled = false;
    $("#playImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_bofang_def.png');

    $("#speedChoiceImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_speed_def.png');

    pauseBtn.disabled = true;
    $("#pauseImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_stop_no.png');

    resetBtn.disabled = false;
    $("#resetImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_chong_def.png');

    if (timer) {
        window.clearTimeout(timer);
    }
}
//重置
function reset() {

    resetBtn.disabled = true;
    $("#resetImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_chong_no.png');

    playBtn.disabled = false;
    $("#playImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_bofang_def.png');

    pauseBtn.disabled = true;
    $("#pauseImg").attr('src', basePath +'img/trafficflux/vehcTrack/btn_stop_no.png');

    if (timer) {
        window.clearTimeout(timer);
    }
    index = 0;
    car.setPosition(bluepoints[0]);
    map.panTo(bluepoints[0]);
    $("#dtGrid tr:gt(0)").remove();
    $("#hList").hide();
    $("#map_canvas").css("height", "655px");
}

function GridHeaderSet() {
    $(".gridHeader").each(function () {

        var headerId = $(this).attr("id") + "Header";
        if ($(".gridHeader[id='" + headerId + "']").length > 0) {
            $(".gridHeader[id='" + headerId + "']").css("width", $(this).css("width"));
            var colNum = $(".gridHeader[id='" + headerId + "'] tr:eq(0) th").length;
            for (var i = 0; i < colNum; i++) {
                var width = $(this).find("tr").eq(1).find("td").eq(i).innerWidth();
                $(".gridHeader[id='" + headerId + "']  tr").eq(0).find("th").eq(i).outerWidth(width);
            }
        }
    });
}

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

//表格双击事件
function tableSelectRow(rowno) {
    clearTimeout(timerclick);
    for (var i = (rowno + 1) ; i <= index ; i++) {
        var id = "row_" + i;
        $("#" + id).remove();
    }
    index = rowno;

    var data = bluegps[index];
    if (data.longitude && data.latitude) {
        var point = new BMap.Point(data.longitude, data.latitude);
        var icon;
        var gpsdrct;
        var iconSize = new BMap.Size(32, 32);
        if (data.direction != null && data.direction != undefined) {
            if (data.direction >= 337.5 || data.direction < 22.5) {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_05.png', iconSize);
                gpsdrct = "向北";
            }
            else if (data.direction >= 22.5 && data.direction < 67.5) {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_04.png', iconSize);
                gpsdrct = "东北";
            }
            else if (data.direction >= 67.5 && data.direction < 112.5) {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_03.png', iconSize);
                gpsdrct = "向东";
            }
            else if (data.direction >= 112.5 && data.direction < 157.5) {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_02.png', iconSize);
                gpsdrct = "东南";
            }
            else if (data.direction >= 157.5 && data.direction < 202.5) {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_01.png', iconSize);
                gpsdrct = "向南";
            }
            else if (data.direction >= 202.5 && data.direction < 247.5) {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_08.png', iconSize);
                gpsdrct = "西南";
            }
            else if (data.direction >= 247.5 && data.direction < 292.5) {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_07.png', iconSize);
                gpsdrct = "向西";
            }
            else if (data.direction >= 292.5 && data.direction < 337.5) {
                icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/car-icon_06.png', iconSize);
                gpsdrct = "西北";
            }
            car.setIcon(icon);
        }
        car.setZIndex(0);
        car.setPosition(point);
    }
}

//表格单击事件
function tableClickRow(rowno) {
    clearTimeout(timerclick);
    //在单击事件中添加一个setTimeout()函数，设置单击事件触发的时间间隔
    timerclick = setTimeout(function () {
        index = rowno;
        var long = $("#column_" + index + "_5").text();//经度
        var lat = $("#column_" + index + "_6").text();//纬度
        var icon;
        if (long > 0 || lat > 0) {
            var gpsdrct = $("#column_" + index + "_2").text();//方向
            var gpsSpeed = $("#column_" + index + "_4").text();//速度
            var address = $("#column_" + index + "_7").text();//地址
            var time = $("#column_" + index + "_3").text();//时间

            var pointC = new BMap.Point(long, lat);
            var iconSize = new BMap.Size(24, 29);
            //图标
            marker1 = new BMap.Marker(pointC,{ icon: icon = new BMap.Icon(basePath +'img/trafficflux/trajectory/offline_car.png', iconSize) });  // 创建标注
        	map.addOverlay(marker1); 
           
            var content = "<div style='line-height:14px;'><p style='text-align:left '><span style='width:100px;'>车牌：</span>" + $("#s2id_selPlates .select2-chosen").text() + "</p>";
            content += "<p  style='text-align:left '><span style='width:100px;'>方向：</span>" + gpsdrct + "</p>";
            content += "<p  style='text-align:left '><span style='width:100px;'>速度(km/h)：</span>" + gpsSpeed + "</p>";
            content += "<p  style='text-align:left '><span style='width:100px;'>GPS时间：</span>" + time + "</p>";
            content += "<p  style='text-align:left '><span style='width:100px;'>位置：</span>" + address + "</p></div>";
            var contentObj = { content: content };
            var contentStr = contentObj.content;
            map.panTo(pointC);
            var infoWindow = new BMap.InfoWindow(contentStr, opts);  // 创建信息窗口对象
            map.openInfoWindow(infoWindow, pointC); //开启信息窗口
            gc.getLocation(pointC, function (rs) {
                infoWindow.setContent(contentStr);
            });
        }
    }, 300);
}
//时间校验
Date.prototype.format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
var _Date=function(){
    return {
        //两个日期相减的天数
        getDays: function (day1, day2)
        {
            var arrDate, objDate1, objDate2, intDays;
            objDate1 = new Date();
            objDate2 = new Date();

            arrDate = day1.split("-");
            objDate1.setFullYear(arrDate[0], arrDate[1], arrDate[2]);
            
            arrDate = day2.split("-");
            objDate2.setFullYear(arrDate[0], arrDate[1], arrDate[2]);

            intDays = parseInt(Math.abs(objDate2 - objDate1) / 1000 / 60 / 60 / 24);
            return intDays+1;
        },
        getDaysq: function (startDate, endDate) {

            var mmSec = ((new Date(endDate).getTime() -((new Date( startDate)).getTime()))); //得到时间戳相减 得到以毫秒为单位的差    
            return (mmSec / 3600000 / 24)+1; //单位转换为天并返回    
        }
    }
}();

