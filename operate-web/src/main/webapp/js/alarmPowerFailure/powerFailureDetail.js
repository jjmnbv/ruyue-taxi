/**
 * Created by liubangwei_lc on 2017/7/25.
 */
var map;
var eqpId;
var departmentId;
var gc = new BMap.Geocoder();
$(function () {
    bindMap();
    getVehcList();//绑定界面数据
    eqpId = $("#eqpId").val();
    departmentId = $("#departmentId").val();
    $("#btnCancel").click(function () {
        location.href = basePath + "AlarmPowerFailure/Index";
    })
});

//获取车辆列表
function getVehcList() {
    $.ajax({
        url: "Location/QueryLocation",
        data: {
            processOption : 2,//纠偏选项  1_不纠偏;2_百度纠偏;默认不纠偏
            eqpId : eqpId,
            relationType:1,
            departmentId:departmentId
        },
        cache: false,
        success: function (data) {
            //初始化车辆信息和报警详情
            var vehcList = data.vehcLocation;
            //绑定地图标记
            if (vehcList == null || vehcList == undefined || vehcList.length == 0) {
                toastr.success("当前没有车辆断电报警明细信息", "提示信息");
                return;
            }
            else {
                bindVehcMarker( vehcList);
            }
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText);
            return;
        },
        complete: function (xhr, ts) {
            _loading.hide();
        }
    });
}


//绑定地图行驶轨迹
function bindMap() {
    map = new BMap.Map("map_canvas");
    map.centerAndZoom("深圳", 10);
    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.enableScrollWheelZoom();
}

//绑定车辆地图标记
function bindVehcMarker(vehcList) {

    if (vehcList == null || vehcList == undefined) {
        return;
    }
    var point = null;

    for (var i = 0; i < vehcList.length; i++) {
        point = new BMap.Point(vehcList[i].longitude, vehcList[i].latitude);
        var iconSize = new BMap.Size(__seticon.carstatusheight, __seticon.carstatuswidth);
        //设置地图图标
        var icon = new BMap.Icon("img/trafficflux/trajectory/car-power-failure.png", new BMap.Size(__seticon.vehctrackheight, __seticon.vehctrackwidth));
        //转换数值方向为方位
        // var gpsdrct = _directiontranslate.translate(vehcList[i].direction);
        //添加车辆地图标注
        var marker = new BMap.Marker(point);
        map.addOverlay(marker);
        marker.setIcon(icon);

        //设置地图弹出框内容
        var content = "<div><p>车牌：" + vehcList[i].plate + "</p>";
        content += "<p>速度：" + vehcList[i].speed + " km" + "</p>";
        content += "<p>时间：" + vehcList[i].locTime + "&nbsp;&nbsp;&nbsp;" + "航向：" + vehcList[i].direction + "</p>";
        if (vehcList[i].address == null || vehcList[i].address == undefined) {
            content += "<p>位置：未解析地址</p></div>";
        }
        else {
            content += "<p>位置：" + vehcList[i].address + "</p></div>";
        }
        var contentObj = { content: content };
        marker.addEventListener("click", openInfo.bind(null, contentObj));
    }
    //两秒后，地图平移到最后一个车辆位置
    if (point != null) {
        setTimeout(function () {
            map.panTo(point);
        }, 2000);
    }
}

var opts = {
    width: 270,     // 信息窗口宽度
    height: 130,     // 信息窗口高度
    enableMessage: false,
    offset: new BMap.Size(0, -20)
}
function openInfo(contentObj, e) {
    var p = e.target;
    var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
    var infoWindow = new BMap.InfoWindow(contentObj.content, opts); // 创建信息窗口对象
    map.openInfoWindow(infoWindow, point); // 开启信息窗口
    if (!contentObj.address) {
        gc.getLocation(point, function(rs) {
            contentObj.content = contentObj.content
                .replace("未解析地址", rs.address);
            infoWindow.setContent(contentObj.content);
            contentObj.address = rs.address;
        });
    }
}