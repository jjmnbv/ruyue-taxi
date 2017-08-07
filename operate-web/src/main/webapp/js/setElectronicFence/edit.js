var validator;
var map;
var gc = new BMap.Geocoder();
var radius; //半径
var efsaPoints = "";//多边形的点
var strEfsa = "";
var shape; //形状
var calculate;  //面积
var efsaList = []; //存储 区域所有数据
var drawingManager, overlays = [], points;
var labelLays = [];
var id;
$(function () {
    id = $("#id").val();
    bindMap();  //绑定地图
//            bindControl(); //绑定控件
    var frmmodal = $("#frmmodal");
    var erroralert = $('.alert-danger', frmmodal);
    var successalert = $('.alert-success', frmmodal);
    validator = frmmodal.validate({
        submitHandler: function (form) {
            successalert.show();
            erroralert.hide();
            Save();
        }
    });
    if (id > '0') {
        initForm(id);
    }

    $("#btnCancel").click(function () {
        location.href = basePath + "SetElectronicFence/Index";
    })

});
function bindControl() {
    //绑定电子围栏图形类型
    $.ajax({
//                url: "@Url.Action("GetDict", "Common")",
        cache: false,
        data: {dictType: "电子围栏图形类型"},
        success: function (data) {
            var optStr = "<option value='' selected='selected'>请选择</option>";
            for (var i in data) {
                optStr += "<option value='" + data[i].D_VALUE + "'>" + data[i].D_TEXT + "</option>";
            }
            $("#selEFSAShape").html(optStr);
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText);
        }
    });
    //绑定电子围栏区域报警类型
    $.ajax({
//                url: "@Url.Action("GetDict", "Common")",
        cache: false,
        data: {dictType: "电子围栏报警"},
        success: function (data) {
            var optStr = "<option value='' selected='selected'>请选择</option>";
            for (var i in data) {
                optStr += "<option value='" + data[i].D_VALUE + "'>" + data[i].D_TEXT + "</option>";
            }
            $("#selEFSAType").html(optStr);
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText);
        }
    });
    $("#btnCancle").click(function () {
//                location.href = "@Url.Action("ElectronFenceSetManage", "ElectronFenceSet")";
    });
}
//绑定地图
function bindMap() {
    map = new BMap.Map("map_canvas");
    map.centerAndZoom("深圳", 12);
    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.enableScrollWheelZoom();
    //实例化鼠标绘制工具
    var drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: false, //是否开启绘制模式
        enableDrawingTool: true, //是否显示工具栏
        drawingToolOptions: {
            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
            offset: new BMap.Size(5, 5), //偏离值
            scale: 0.6,                 //工具栏的缩放比例,默认为1
            drawingModes: [
                BMAP_DRAWING_CIRCLE,               //BMAP_DRAWING_CIRCLE 画圆       BMAP_DRAWING_MARKER 画点
                BMAP_DRAWING_POLYGON,              //BMAP_DRAWING_RECTANGLE 画矩形  BMAP_DRAWING_POLYLINE 画线
                BMAP_DRAWING_RECTANGLE             //BMAP_DRAWING_POLYGON 画多边形
            ]
        },
        circleOptions: styleOptions,   //圆的样式
        polylineOptions: styleOptions, //线的样式
        polygonOptions: styleOptions, //多边形的样式
        rectangleOptions: styleOptions //矩形的样式
    });
    drawingManager.enableCalculate();   //打开距离或面积计算
    var myDis = new BMapLib.DistanceTool(map, {
        enableDrawingTool: true, //是否显示工具栏
    })
    //添加鼠标绘制工具监听事件，用于获取绘制结果
    drawingManager.addEventListener('overlaycomplete', getOverlayPoints);
}
var styleOptions = {
    strokeColor: "blue",
    strokeWeight: 2,
    strokeOpacity: 0.5
    //strokeColor: "blue",    //边线颜色。
    //fillColor: "blue",      //填充颜色。当参数为空时，圆形将没有填充效果。
    //strokeWeight: 2,       //边线的宽度，以像素为单位。
    //strokeOpacity: 0.5,	   //边线透明度，取值范围0 - 1。
    //fillOpacity: 0,      //填充的透明度，取值范围0 - 1。
    //strokeStyle: 'solid' //边线的样式，solid或dashed。
}
//获取 所画图形的信息
function getOverlayPoints(e) {
    clearAll();
    overlays.push(e.overlay);  //多边形的个数
    labelLays.push(e.label);
    if (overlays.length > 1) {
        clearTheLast();
    }
    if (e.drawingMode == BMAP_DRAWING_RECTANGLE) { //矩形
        efsaPoints = "";
        radius = null;
        shape = "1";
        calculate = e.calculate;
        for (var i = 0; i < e.overlay.getPath().length; i++) {
            efsaPoints += '(' + e.overlay.getPath()[i].lng + ',' + e.overlay.getPath()[i].lat + ');';
        }
        var a = {"EFSA_SHAPE": shape, "EFSA_CALCULATE": calculate, "EFSA_RADIUS": radius, "EFSA_POINTS": efsaPoints};
    }
    if (e.drawingMode == BMAP_DRAWING_POLYGON) { //多边形
        radius = null;
        efsaPoints = "";
        shape = "3";
        calculate = e.calculate;
        for (var i = 0; i < e.overlay.getPath().length; i++) {
            efsaPoints += '(' + e.overlay.getPath()[i].lng + ',' + e.overlay.getPath()[i].lat + ');';
        }
        var a = {"EFSA_SHAPE": shape, "EFSA_CALCULATE": calculate, "EFSA_RADIUS": radius, "EFSA_POINTS": efsaPoints};
    }
    if (e.drawingMode == BMAP_DRAWING_CIRCLE) { //圆形
        efsaPoints = "";
        shape = "2";
        calculate = e.calculate;
        radius = e.overlay.getRadius();
        efsaPoints += '(' + e.overlay.getCenter().lng + ',' + e.overlay.getCenter().lat + ')';//(lng 经度，lat 纬度)
        var a = {"EFSA_SHAPE": shape, "EFSA_CALCULATE": calculate, "EFSA_RADIUS": radius, "EFSA_POINTS": efsaPoints};
    }
    if (calculate > 0) {
        efsaList.push(a);
    }
    if (calculate <= 0) {
        toastr.warning("图形无效,请重画当前多边形!", "提示信息");
        clearAll();
        bindMap();
        // map.removeOverlay(labelLays[labelLays.length]);
        // map.removeOverlay(overlays[overlays.length]);
    }
}
function initForm(id) {
    $.ajax({
        url: "SetElectronicFence/toUpdate",
        cache: false,
        data: {id: id},
        success: function (json) {
            setForm(json);
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText, "SetElectronicFence/Index");
            return;
        }
    });
}
// 初始化
function setForm(entity) {
    if (entity.setElectronicFence.length > 0) {
        var data = entity.setElectronicFence;
        for (var count = 0; count < data.length; count++) {
            $("#name").val(data[0].name);
            $("#alartType").val(data[0].alartType);
            var radius = data[count].radius;
            var shape = data[count].shape;
            var points = data[count].points;
            var graphicsarea = data[count].calculatitudee;

            // 画圆形
            if (shape == '2') {
                var p0 = points.replace("(", "");
                var p1 = p0.replace(")", "");
                var p = p1.split(',');
                var point = new BMap.Point(p[0], p[1]);
                var circle = new BMap.Circle(point, radius, {
                    strokeColor: "black",
                    strokeWeight: 2,
                    strokeOpacity: 0.5
                }); //创建圆
                map.addOverlay(circle);
                overlays.push(circle);
                labelLays.push(calculate);
                var a = {
                    "EFSA_SHAPE": shape,
                    "EFSA_CALCULATE": graphicsarea,
                    "EFSA_RADIUS": radius,
                    "EFSA_POINTS": points
                };
                efsaList.push(a);
            }
            //画矩形
            if (shape == '1') {
                var rectPoints = [];
                var p0 = points.split(';');
                for (var i = 0; i < p0.length; i++) {
                    var p1 = p0[i].replace("(", "");
                    var p2 = p1.replace(")", "");
                    var p3 = p2.split(',');
                    var point = new BMap.Point(p3[0], p3[1]);
                    rectPoints.push(point);
                }
                var rectangle = new BMap.Polygon(rectPoints, {
                    strokeColor: "black",
                    strokeWeight: 2,
                    strokeOpacity: 0.5
                });  //创建矩形
                map.addOverlay(rectangle);
                overlays.push(rectangle);
                labelLays.push(calculate);
                var a = {
                    "EFSA_SHAPE": shape,
                    "EFSA_CALCULATE": graphicsarea,
                    "EFSA_RADIUS": radius,
                    "EFSA_POINTS": points
                };
                efsaList.push(a);
            }
            //画多边形
            if (shape == '3') {
                var polyPoints = [];
                var p0 = points.split(';');
                for (var i = 0; i < p0.length; i++) {
                    var p1 = p0[i].replace("(", "");
                    var p2 = p1.replace(")", "");
                    var p3 = p2.split(',');
                    var point = new BMap.Point(p3[0], p3[1]);
                    polyPoints.push(point);
                }
                var polygon = new BMap.Polygon(polyPoints, {strokeColor: "black", strokeWeight: 2, strokeOpacity: 0.5});  //创建多边形
                map.addOverlay(polygon);
                overlays.push(polygon);
                labelLays.push(calculate);
                var a = {
                    "EFSA_SHAPE": shape,
                    "EFSA_CALCULATE": graphicsarea,
                    "EFSA_RADIUS": radius,
                    "EFSA_POINTS": points
                };
                efsaList.push(a);
            }
        }
    }
}
//提交
function Save() {
    if (overlays.length == 0 || efsaList.length < overlays.length) {
        toastr.warning("图形无效,请至少绘制一个图形", "提示信息");
        clearAll();
        bindMap();
        return;
    }
    var data = {
        electronicFenceId: id,
        name: $("#name").val(),
        // 电子围栏
        shape: efsaList[0].EFSA_SHAPE,  //区域类型
        calculatitudee: efsaList[0].EFSA_CALCULATE,  //电子围栏区域面积
        radius: efsaList[0].EFSA_RADIUS,  //电子围栏区域半径
        points: efsaList[0].EFSA_POINTS,  //电子围栏区域点经纬度
        alartType: $("#alartType").val() //报警类型
    };
    $.ajax({
        type: 'POST',
        cache: false,
        dataType: 'json',
        url: 'SetElectronicFence/setSetElectronicFenceAll',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (data) {
            if (data.status == 0) {
                var message = data.msg == null ? data
                    : data.msg;
                toastr.options.onHidden = function () {
                    window.location.href = basePath + "SetElectronicFence/Index";
                }
                toastr.success(message, "提示");

            } else {
                var message = data.msg == null ? data
                    : data.msg;
                toastr.error(message, "提示");
            }
        }
    });
}

function clearTheLast() {
    for (var i = 0; i < overlays.length - 1; i++) {
        map.removeOverlay(overlays[i]);
        map.removeOverlay(labelLays[i]);
    }
}
//清除多边形
function clearAll() {
    for (var i = 0; i < overlays.length; i++) {
        map.removeOverlay(overlays[i]);
        map.removeOverlay(labelLays[i]);
        map.removeOverlay(efsaList[i]);
    }
    overlays.length = 0;
    labelLays.length = 0;
    efsaList.length = 0;
}