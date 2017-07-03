var vehcId = 0;
var map;
var prevPoint;
var currentPoint;
var marker;
var infoWindow;
var vehcList;
var gc = new BMap.Geocoder();
var markerArr = new Array();//存放所有marker
var polylineArr = [];
var dtGridTimeFenceSet;
var dtGridAreaFenceSet;
var dtGridElectronFenceSet;
var setTimeoutId = 0;
var seconds = 0;
var apikey="EFLc9FVyIHUWE4xKYFETDeF";

$(function () {
    $.fn.modal.Constructor.prototype.enforceFocus = function () { };
    bindControl();//绑定控件
    bindMap();  //绑定地图行驶轨迹
//    if ("@ViewBag.vehcId") {
//        $("#selPlates").val("@ViewBag.vehcId");
//        $("#s2id_selPlates .select2-chosen").text("@ViewBag.plates");
//        $("#spVehcCount").html(1)
//        $.ajax({
//            url: "@Url.Action("GetCurrentVehcList", "VehcLocation")",
//            cache: false,
//            data: { vehcId: "@ViewBag.vehcId" },
//            success: function (data) {
//                vehcList = data;
//                if (vehcList == null || vehcList == undefined || vehcList.length == 0) {
//                    toastr.success("当前查询条件没有车辆信息", "提示信息");
//                    return;
//                }
//                //绑定车辆列表
//                bindVehcList();
//            },
//            error: function (xhr, status, error) {
//                showerror(xhr.responseText);
//            },
//            complete: function (xhr, ts) {
//                _loading.hide();
//            }
//        });
//    }

});

function bindControl() {
    $("#btnSearch").click(function () {
        if ($("#selPlates").val() == null || $("#selPlates").val() == "" || $("#selPlates").val() == undefined) {
            toastr.warning("请选择车辆", "提示信息");
            return;
        }
        //检查车辆是否在实时追踪
        var isbool = true;
        $("[name='vehccheckbox'][checked]").each(function () {
            if ($("#selPlates").val() == $(this).val()) {

                toastr.warning("车辆已在实时追踪列表中", "提示信息");
                isbool = false;
                return false;
            }
        })

        if (isbool == true) {
            $.ajax({
                url: "Location/QueryLocation",
                cache: false,
                data : {
        			apikey : apikey,
        			processOption : 2,//纠偏选项  1_不纠偏;2_百度纠偏;默认不纠偏
        			eqpId : $("#selPlates").val(),
        			relationType:1
        		},
                success: function (data) {
                    vehcList = data.vehcLocation;
                    if (vehcList == null || vehcList == undefined || vehcList.length == 0) {
                        toastr.success("当前车辆未安装设备或设备未激活", "提示信息");
                        return;
                    }
                    //绑定车辆列表
                    
                    bindVehcList();
                },
                error: function (xhr, status, error) {
                    // showerror(xhr.responseText);
                },
                complete: function (xhr, ts) {
                    _loading.hide();
                }
            });
        }
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
						person.id=data.vhecEqpList[i].eqpId;  
						person.text=data.vhecEqpList[i].plate +"|"+data.vhecEqpList[i].imei;
						eqpList.push(person);
					}
				}
                return { results: eqpList };

				  var data = {results: []};
				  $.each(query.vhecEqpList.elementData, function(){
					  data.results.push({id: this.eqpId, text: this.plate });
				  });
                 return data;
              }
		}
	});

}

$('.modal').on('hidden.bs.modal', function (e) {
    setTimeout(work, 1000);
})

$('.modal').on('show.bs.modal', function (e) {
    clearInterval(setTimeoutId);
})

//添加车辆列表
function bindVehcList() {
    
    if (vehcList == null || vehcList == undefined) {
        return;
    }

    var html = "";
    for (var i = 0; i < vehcList.length; i++) {
    	
        if (i == vehcList.length - 1) {
            html += "<li id='li_" + vehcList[i].imei + "' class=\"last-line\"><div class=\"vehc-icon\">";
        }
        else {
            html += "<li id='li_" + vehcList[i].imei + "'><div class=\"vehc-icon\">";
        }
        var status = "";
        if (vehcList[i].workStatus == "在线") {
            html += "<img src=\""+basePath +"img/trafficflux/run.png\" id='vehc-img'/></div><div id='vehc-title' class=\"vehc-title run\">";
            status = "运行";
        }else if (vehcList[i].workStatus == "离线"){
            html += "<img src=\""+basePath +"img/trafficflux/stop.png\" id='vehc-img'/></div><div id='vehc-title' class=\"vehc-title stop\">";
            status = "离线";
        }else {
            html += "<img src=\""+basePath +"img/trafficflux/stop.png\" id='vehc-img'/></div><div id='vehc-title' class=\"vehc-title stop\">";
            status = "停止";
        }
        html += "<input type=\"hidden\" class=\"longitude\" value=\"" + vehcList[i].longitude + "\"/>";
        html += "<input type=\"hidden\" class=\"latitude\" value=\"" + vehcList[i].latitude + "\"/>";
        html += "<p><span class=\"vehc-title-sp\">IMEI："+ vehcList[i].imei + "</span></p>";
		html += "<span class=\"vehc-title-sp\">" + vehcList[i].plate + "</span>";
		html += "<span class=\"vehc-status\">" + status + "</span>";
		
        /*html += "<span class=\"vehc-title-sp\">" + vehcList[i].plate + "</span>";
        html += "<span class=\"vehc-status\">" + status + "</span>";*/
        html += "<input  type='checkbox' name='vehccheckbox'checked='checked' style='display:none;' value=\"" + vehcList[i].eqpId + "\"/>";
        html += "<span class=\"btn btn-xs deleteVehc\" style='color: #333;float:right' data-vehc='" + vehcList[i].imei + "'><i class='fa fa-trash-o'></i>删除</span></div> </li>";
    }

    $(".vehc-list").append(html);

    //删除
    $(".deleteVehc").on("click", function () {
        $(this).closest("li").remove();
        var vehcId = $(this).attr("data-vehc");
        removeMarker(vehcId);
        deletePoint(vehcId);

        vehcList = [];
        $("[name='vehccheckbox'][checked]").each(function () {
        	var person=new Object(); 
        	person.eqpId=$(this).val();
        	person.apikey = apikey;
        	person.processOption = 2;//纠偏选项  1_不纠偏;2_百度纠偏;默认不纠偏
        	person.relationType=1;
            vehcList.push(person);
        })

        //列表为空清除地图标签
        if (vehcList.length <= 0) {
            toastr.warning("请选择车辆", "提示信息");
        }

        $("#spVehcCount").html(vehcList.length);
    });
    //车辆平移
    $(".vehc-list li").on("click", function () {
        $(".vehc-list li").css("background-color", "");
        $(this).css("background-color", "#e9e9e9");
        var long = $(this).find(".longitude").val();
        var lat = $(this).find(".latitude").val();
        prevPoint = new BMap.Point(long, lat);

        //两秒后，地图平移到上车地点
        if (prevPoint != null) {
            setTimeout(function () { map.panTo(prevPoint); }, 1000);
        }
    });

    initWork();//定时更新
    bindVehcStatus();
}

//绑定地图行驶轨迹
function bindMap() {
	
    map = new BMap.Map("map_canvas");
    var myCity = new BMap.LocalCity();
    myCity.get(myFun);
    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.enableScrollWheelZoom();

    //刷新
    // 创建一个DOM元素
    var span = document.createElement("span");
    // 添加文字说明
    span.appendChild(document.createTextNode(""));
    // 设置样式
    span.setAttribute('id', 'spSeconds');
    span.style.cursor = "pointer";

    // 定义一个控件类,即function
    function ZoomControl(x, y) {
        // 默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
        this.defaultOffset = new BMap.Size(x, y);
    }

    // 通过JavaScript的prototype属性继承于BMap.Control
    ZoomControl.prototype = new BMap.Control();

    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
    // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
    ZoomControl.prototype.initialize = function (map) {
        // 绑定事件,点击一次放大两级
        span.onclick = function (e) {
            onShowMap(-1);
        }

        // 添加DOM元素到地图中
        map.getContainer().appendChild(span);

        //document.getElementById('spAllTrack').innerHTML = timeSecond+"秒后刷新";
        // 将DOM元素返回
        return span;
    }

    // 显示地址
    var myZoomCtrl = new ZoomControl(20, 6);

    // 添加到地图当中
    map.addControl(myZoomCtrl);
}

function myFun(result) {
    var cityName = result.name;
    map.centerAndZoom(cityName, 15);
}

//绑定当前行程GPS
function bindCurrentTrackGps(trackId,eqpId) {

    $.ajax({
    	url : 'Location/getTrajectoryByTrack',
        //url: "@Url.Action("GetCurrentTrackGps", "VehcLocation")",
        cache: false,
        data: { trackId: trackId, eqpId: eqpId, apikey : apikey, processOption : 2, returnResult:1 },
        success: function (data) {
            if (data.vehcTrajectory && data.vehcTrajectory.length > 0) {
                //绑定GPS信息
                bindGps(eqpId, data.vehcTrajectory);
            }
        },
        error: function (xhr, status, error) {
            // showerror(xhr.responseText);
        }
    });
}

//绑定GPS地图信息
function bindGps(eqpId, gpsList) {

    if (gpsList == null || gpsList == undefined) {
        return;
    }
    if (gpsList.length > 0) {
        var point = new BMap.Point(gpsList[0].longitudeOffSet, gpsList[0].latitudeOffSet);
        var icon = new BMap.Icon(basePath +"img/trafficflux/gpsStart.png", new BMap.Size(29, 35));
        var marker = new BMap.Marker(point, { icon: icon });
        marker.setTitle(eqpId);//标记唯一值
        markerArr.push(marker);//将marker放进数组
        map.addOverlay(marker);

    }

    if (gpsList.length > 1) {
        prevPoint = new BMap.Point(gpsList[gpsList.length - 1].longitudeOffSet, gpsList[gpsList.length - 1].latitudeOffSet);
    }

    var points = [];
    for (var i = 0; i < gpsList.length; i++) {
        var point = new BMap.Point(gpsList[i].longitudeOffSet, gpsList[i].latitudeOffSet);
        points.push(point);
    }
    var polyline = new BMap.Polyline(points, { strokeColor: "blue", strokeWeight: 6, strokeOpacity: 0.5 });
    map.addOverlay(polyline);

    //轨迹存放到数组
    var items = {
    	eqpId: eqpId,//标记唯一值
        polyline: polyline
    }
    polylineArr.push(items);
}

function initWork() {
    if (setTimeoutId != 0)
        clearTimeout(setTimeoutId);
    marker = null;
    seconds = 0;
    work();
}

function work() {

    if (vehcList.length > 0) {
        if (seconds == 0) {
            seconds = 10;
            bindVehcStatus();
        } else {
            seconds -= 1;
        }
        $("#spSeconds").addClass("btn btn-xs green");
        $("#spSeconds").css("fontSize","14px");
        
        document.getElementById('spSeconds').innerHTML = seconds + "秒后刷新";
        //$("#spSeconds").text(seconds);
        setTimeoutId = setTimeout(work, 1000);

    } else {
        clearTimeout(setTimeoutId);
        seconds = 0
        //$("#spSeconds").addClass("btn btn-xs green");
        document.getElementById('spSeconds').innerHTML = "";
    }

}

var vehcList = []//存放车辆ID值;
function bindVehcStatus() {
    var eqpIdList = "";
    var count=0;
    $("[name='vehccheckbox'][checked]").each(function () {
        //vehcList.push($(this).val());
    	eqpIdList += $(this).val()+",";
    	count++;
    })

    //列表为空清除地图标签
    if (eqpIdList == "") {
        toastr.warning("请选择车辆", "提示信息");
        map.clearOverlays();
        return;
    }
    $("#spVehcCount").html(count);
    var data = {
        eqpIdList: vehcList
    }

    map.clearOverlays();//清除地图上所有标志
    _loading.show();
    
    var param ={};  
    param.apikey = apikey;
    param.processOption = 2;//纠偏选项  1_不纠偏;2_百度纠偏;默认不纠偏
    param.relationType=1;
    param.eqpIdList = eqpIdList;
    $.ajax({
        type: 'post',
        url: basePath + 'Location/QueryEqpLocation',
        //url: "@Url.Action("GetVehcStatusList", "VehcLocation")",
        cache: false,
        data: {eqpIdList : JSON.stringify(param)},
        success: function (data) {
        	var eqpLocation = data.vehcLocation;
            for (var i = 0; i < eqpLocation.length ; i++) {
                var icon;
                var iconSize = new BMap.Size(32, 32);
                //if (eqpLocation[i].VS_GPSDRCT != null && eqpLocation[i].VS_GPSDRCT != undefined) {
                    //if (eqpLocation[i].VS_GPSDRCT >= 337.5 || eqpLocation[i].VS_GPSDRCT < 22.5) {
                	if (eqpLocation[i].direction == "向北"){
                        icon = new BMap.Icon(basePath +"img/trafficflux/car-icon_05.png", iconSize);
                    }
                    //else if (eqpLocation[i].VS_GPSDRCT >= 22.5 && eqpLocation[i].VS_GPSDRCT < 67.5) {
                	else if (eqpLocation[i].direction == "东北") {
                        icon = new BMap.Icon(basePath +"img/trafficflux/car-icon_04.png", iconSize);
                    }
                    //else if (eqpLocation[i].VS_GPSDRCT >= 67.5 && eqpLocation[i].VS_GPSDRCT < 112.5) {
                    else if (eqpLocation[i].direction == "向东") {
                        icon = new BMap.Icon(basePath +"img/trafficflux/car-icon_03.png", iconSize);
                    }
                    //else if (eqpLocation[i].VS_GPSDRCT >= 112.5 && eqpLocation[i].VS_GPSDRCT < 157.5) {
                    else if (eqpLocation[i].direction == "东南") {
                        icon = new BMap.Icon(basePath +"img/trafficflux/car-icon_02.png", iconSize);
                    }
                    //else if (eqpLocation[i].VS_GPSDRCT >= 157.5 && eqpLocation[i].VS_GPSDRCT < 202.5) {
                    else if (eqpLocation[i].direction == "向南") {
                        icon = new BMap.Icon(basePath +"img/trafficflux/car-icon_01.png", iconSize);
                    }
                    //else if (eqpLocation[i].VS_GPSDRCT >= 202.5 && eqpLocation[i].VS_GPSDRCT < 247.5) {
                    else if (eqpLocation[i].direction == "西南") {
                        icon = new BMap.Icon(basePath +"img/trafficflux/car-icon_08.png", iconSize);
                    }
                    //else if (daeqpLocationta[i].VS_GPSDRCT >= 247.5 && eqpLocation[i].VS_GPSDRCT < 292.5) {
                    else if (eqpLocation[i].direction == "向西") {
                        icon = new BMap.Icon(basePath +"img/trafficflux/car-icon_07.png", iconSize);
                    }
                    //else if (eqpLocation[i].VS_GPSDRCT >= 292.5 && eqpLocation[i].VS_GPSDRCT < 337.5) {
                    else if (eqpLocation[i].direction == "西北") {
                        icon = new BMap.Icon(basePath +"img/trafficflux/car-icon_06.png", iconSize);
                    }
                //}

                var point = new BMap.Point(eqpLocation[i].longitude, eqpLocation[i].latitude)
                var marker = new BMap.Marker(point, { icon: icon });
                var iw = createInfoWindow(eqpLocation[i], point);

                var label = new BMap.Label(eqpLocation[i].plate, { offset: new BMap.Size(20, -10) });
                label.setStyle({
                    fontSize: "14px",
                    border: "0",
                    textAlign: "center"
                });
                marker.setLabel(label);
                marker.setZIndex(0);
                marker.setTitle(eqpLocation[i].imei);//标记唯一值
                map.addOverlay(marker);
                markerArr.push(marker);//将marker放进数组
                bindCurrentTrackGps(eqpLocation[i].trackId,eqpLocation[i].eqpId);
                updateVehcStatus(eqpLocation[i]);
                var vehc2Id = $("#selPlates").val();

                if (eqpLocation[i].imei == vehc2Id) {
                    setTimeout(function () { map.panTo(point) }, 1000);//三秒后平移到轨迹开始位置
                }

                (function () {
                    var index = eqpLocation[i];
                    var _iw = createInfoWindow(eqpLocation[i], point);
                    var _marker = marker;
                    _marker.addEventListener("click", function () {
                        this.openInfoWindow(_iw);
                    });
                })()
            }
        },
        error: function (xhr, status, error) {
            // showerror(xhr.responseText);
        },
        complete: function (xhr, ts) {
            _loading.hide();
        }
    });
}
var opts = {
    width: 350,     // 信息窗口宽度
    //height: 155,     // 信息窗口高度
    enableMessage: false,
    offset: new BMap.Size(0, -20)
}

function updateVehcStatus(data) {
    var status = "";
    var statusImg = "";
    var statusClass = "";
    if (data.workStatus == "在线") {
        status = "运行";
        statusImg = basePath +"img/trafficflux/run.png";
        statusClass = "vehc-title run";
    } else if(data.workStatus == "离线"){
        status = "离线";
        statusImg = basePath +"img/trafficflux/stop.png";
        statusClass = "vehc-title stop";
    }else {
        status = "停止";
        statusImg = basePath +"img/trafficflux/stop.png";
        statusClass = "vehc-title stop";
    }
    $("#li_" + data.imei).find(".longitude").val(data.longitude);
    $("#li_" + data.imei).find(".latitude").val(data.latitude);
    $("#li_" + data.imei).find("#vehc-title").css("class", statusClass);
    $("#li_" + data.imei).find("#vehc-img").attr("src", statusImg);
    $("#li_" + data.imei).find(".vehc-status").html(status);
}


//对应不同的Marker创建不同的InfoWindow
function createInfoWindow(data, point) {
    
    var iw = new BMap.InfoWindow("", opts);
    gc.getLocation(point, function (rs) {

        //转换数值方向为方位
//        if (data.VS_GPSDRCT != null && data.VS_GPSDRCT != undefined) {
//            if (data.VS_GPSDRCT >= 337.5 || data.VS_GPSDRCT < 22.5) {
//                gpsdrct = "向北";
//            }
//            else if (data.VS_GPSDRCT >= 22.5 && data.VS_GPSDRCT < 67.5) {
//                gpsdrct = "东北";
//            }
//            else if (data.VS_GPSDRCT >= 67.5 && data.VS_GPSDRCT < 112.5) {
//                gpsdrct = "向东";
//            }
//            else if (data.VS_GPSDRCT >= 112.5 && data.VS_GPSDRCT < 157.5) {
//                gpsdrct = "东南";
//            }
//            else if (data.VS_GPSDRCT >= 157.5 && data.VS_GPSDRCT < 202.5) {
//                gpsdrct = "向南";
//            }
//            else if (data.VS_GPSDRCT >= 202.5 && data.VS_GPSDRCT < 247.5) {
//                gpsdrct = "西南";
//            }
//            else if (data.VS_GPSDRCT >= 247.5 && data.VS_GPSDRCT < 292.5) {
//                gpsdrct = "向西";
//            }
//            else if (data.VS_GPSDRCT >= 292.5 && data.VS_GPSDRCT < 337.5) {
//                gpsdrct = "西北";
//            }
//        }

        //设置地图弹出框内容
        var status = "";
        var content = "<p style='font-weight:bold;text-align:left '>车牌：" + data.plate + "</p>";
        content += "<p style='font-weight:bold;text-align:left '>车企： " + data.department + "</p>";
        if (data.workStatus == "在线") {
            status = "运行";
            content += "<p>状态：" + data.workStatus + "&nbsp;&nbsp;&nbsp;&nbsp;" + data.longTime + " <span style=' float:right; _position:relative;'>车速 ： " + data.speed + " km/h  &nbsp;&nbsp;&nbsp;</span></p>";
            content += "<p>行程开始：" + data.startTime.substring(0,19) + "<span style=' float:right; _position:relative;'>航向：" + data.direction + " &nbsp;&nbsp;&nbsp;</span></p>";
            
            if(data.totalMileage == null){
            	data.totalMileage=0;
            }
            if(data.cumulativeOil == null){
            	data.cumulativeOil=0;
            }
            
            content += "<p>里程：" + data.totalMileage + "km&nbsp;&nbsp;&nbsp;耗油：" + data.cumulativeOil + "L</p>";
            if (rs.address == null || rs.address == undefined) {
                content += "<p>位置：未解析地址</p></div>";
            }
            else {
                content += "<p>位置：" + rs.address + "</p>";
            }
        } else {

            status = "停止";
            content += "<p>状态：" + data.workStatus + "</p>";
            content += "<p>最后更新时间：" + data.locTime + "</p>";
            content += "<p>位置：" + rs.address + "</p>";
        }

        if (data.Flag != "" && data.Flag != null) {
            content += "<p style='float:left;margin-left:90px;margin-top:10px;'>";
//            content += "<a target='_blank' href='" +basePath + "Vehc/showDetail?id=" + data.imei + "' class='btn default btn-xs blue'> <img src='"+basePath +"img/trafficflux/vehcCommon/returnCar.png' /> 车辆档案</a>";
           content += "&nbsp;&nbsp;<a class='btn  btn-xs blue' href='" +basePath + "/VehicleTrajectory/Index/" + data.imei + "' ><img src='"+basePath +"img/trafficflux/vehcCommon/refresh.png' /> 车辆轨迹</a>";
            content += "</p>";

            content += "<div class='btn-group dropup' style='float:right;width:100px;margin-right:-5px;position:relative;margin-top:10px;' id='dDiv'>";
            content += "<button type='button' class='btn default btn-xs blue' onclick='showUlList()' style='height:20px;margin-top:1px;'><img src='"+basePath +"img/trafficflux/vehcCommon/icon_zhanlan.png' />栅栏设置</button>";
            content += "<button type='button' class='btn default btn-xs blue dropdown-toggle' data-toggle='dropdown' onclick='showUlList()' style='height:20px;margin-top:1px;'><i class='fa fa-angle-down'></i></button>";
            content += "<ul class='dropdown-menu pull-right' role='menu' style='list-style:none;margin:0px;padding:0px;width:50px;margin-right:10px;' class='ListStyle'>";
            if (data.Flag.indexOf("1") > -1) {
                content += "<li><a href='javascript:void(0)' class='btn btn-xs blue' onclick=showTimeModel('" + data.plate + "','" + data.imei + "')><img src='"+basePath +"img/trafficflux/vehcCommon/icon_clock_def.png' /> 时间栅栏 </a></li>";
            }
            if (data.Flag.indexOf("2") > -1) {
                content += "<li><a href='javascript:void(0)' class='btn btn-xs blue' onclick=showAreaModel('" + data.plate + "','" + data.imei + "')><img src='"+basePath +"img/trafficflux/vehcCommon/icon_quyu.png' /> 区域栅栏 </a></li>";
            }
            if (data.Flag.indexOf("3") > -1) {
                content += "<li><a href='javascript:void(0)' class='btn btn-xs blue' onclick=showElectronModel('" + data.plate + "','" + data.imei + "')><img src='"+basePath +"img/trafficflux/vehcCommon/icon_dianzi.png' /> 电子栅栏 </a></li>";
            }
            content += "</ul></div>";
        }
        else {
            content += "<p style='float:right;margin-top:10px;'>";
          //content += "<a target='_blank' href='" +basePath + "Vehc/showDetail?id=" + data.imei + "' class='btn default btn-xs blue'> <img src='"+basePath +"img/trafficflux/vehcCommon/returnCar.png' /> 车辆档案</a>";
            content += "&nbsp;&nbsp;<a class='btn  btn-xs blue' href='" +basePath + "/VehicleTrajectory/Index/" + data.imei +"' ><img src='"+basePath +"img/trafficflux/vehcCommon/refresh.png' /> 车辆轨迹</a>";
            content += "</p>";
        }

        iw.setContent(content);
    });
    return iw;
}


function showUlList() {
    if ($("#dDiv").hasClass("btn-group dropup")) {
        $("#dDiv").removeClass("btn-group dropup");
        $("#dDiv").addClass("btn-groupnew dropup open");
    }
    else if ($("#dDiv").hasClass("btn-groupnew dropup open")) {
        $("#dDiv").removeClass("btn-groupnew dropup open");
        $("#dDiv").addClass("btn-group dropup");
    }
    else {
        $("#dDiv").addClass("btn-groupnew dropup open");
    }
}

//删除Marker标志
function removeMarker(name) {

    for (var i = 0; i < markerArr.length; i++) {
        if (markerArr[i].getTitle() == name) {
            map.removeOverlay(markerArr[i]);
        }
    }
}

//删除折线 Polyline
function deletePoint(name) {
    for (var i = 0; i < polylineArr.length; i++) {
        if (polylineArr[i].vehcId == name) {
            map.removeOverlay(polylineArr[i].polyline);
        }
    }
}