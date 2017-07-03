var _directiontranslate = function () {
    return {
        translate: function (vs_gpsdrct) {
            var gpsdrct;
            if (vs_gpsdrct != null && vs_gpsdrct != undefined) {
                if (vs_gpsdrct >= 337.5 || vs_gpsdrct < 22.5) {
                    gpsdrct = "向北";
                }
                else if (vs_gpsdrct >= 22.5 && vs_gpsdrct < 67.5) {
                    gpsdrct = "东北";
                }
                else if (vs_gpsdrct >= 67.5 && vs_gpsdrct < 112.5) {
                    gpsdrct = "向东";
                }
                else if (vs_gpsdrct >= 112.5 && vs_gpsdrct < 157.5) {
                    gpsdrct = "东南";
                }
                else if (vs_gpsdrct >= 157.5 && vs_gpsdrct < 202.5) {
                    gpsdrct = "向南";
                }
                else if (vs_gpsdrct >= 202.5 && vs_gpsdrct < 247.5) {
                    gpsdrct = "西南";
                }
                else if (vs_gpsdrct >= 247.5 && vs_gpsdrct < 292.5) {
                    gpsdrct = "向西";
                }
                else if (vs_gpsdrct >= 292.5 && vs_gpsdrct < 337.5) {
                    gpsdrct = "西北";
                }
            }
            return gpsdrct;
        },
        //根据车辆状态来设置车图标
        setVehcInco: function (status, iconSize) {
            var icon;
            if (status == 1) {
                icon = new BMap.Icon("/Content/assets/img/run_car.png", iconSize);
            }
            else if (status == 2) {
                icon = new BMap.Icon("/Content/assets/img/stop_car.png", iconSize);
            }
            else {
                icon = new BMap.Icon("/Content/assets/img/offline_car.png", iconSize);
            }
            return icon;
        },
        //根据发动机状态来设置车图标
        setIncoByEngineStatus: function (engineStatus, iconSize) {
            var icon;
            if (engineStatus == 1) {
                icon = new BMap.Icon("/Content/assets/img/run_car.png", iconSize);
            }
            else if (engineStatus == 0) {
                icon = new BMap.Icon("/Content/assets/img/stop_car.png", iconSize);
            }
            return icon;
        },
        setVehcIncoByGpsdrct: function (vs_gpsdrct, iconSize) {
            var icon;
            if (vs_gpsdrct >= 337.5 || vs_gpsdrct < 22.5) {
                icon = new BMap.Icon("/Content/assets/img/car-icon_05.png", iconSize);
            }
            else if (vs_gpsdrct >= 22.5 && vs_gpsdrct < 67.5) {
                icon = new BMap.Icon("/Content/assets/img/car-icon_04.png", iconSize);
            }
            else if (vs_gpsdrct >= 67.5 && vs_gpsdrct < 112.5) {
                icon = new BMap.Icon("/Content/assets/img/car-icon_03.png", iconSize);
            }
            else if (vs_gpsdrct >= 112.5 && vs_gpsdrct < 157.5) {
                icon = new BMap.Icon("/Content/assets/img/car-icon_02.png", iconSize);
            }
            else if (vs_gpsdrct >= 157.5 && vs_gpsdrct < 202.5) {
                icon = new BMap.Icon("/Content/assets/img/car-icon_01.png", iconSize);
            }
            else if (vs_gpsdrct >= 202.5 && vs_gpsdrct < 247.5) {
                icon = new BMap.Icon("/Content/assets/img/car-icon_08.png", iconSize);
            }
            else if (vs_gpsdrct >= 247.5 && vs_gpsdrct < 292.5) {
                icon = new BMap.Icon("/Content/assets/img/car-icon_07.png", iconSize);
            }
            else if (vs_gpsdrct >= 292.5 && vs_gpsdrct < 337.5) {
                icon = new BMap.Icon("/Content/assets/img/car-icon_06.png", iconSize);
            }
            return icon;
        }
    };
}();
var __seticon = {
    carstatusheight:24, //车辆状态的图标高度
    carstatuswidth: 29,//车辆状态的图标宽度
    vehctrackheight: 29,//行程中开始、结束、报警的图标高度
    vehctrackwidth: 35,//行程中开始、结束、报警的图标宽度
    carheight: 32,//车子的高度
    carwidth: 32,//车子宽度
    dragracestart: 13,//飙车、超速起点图标高度、宽度
    linestrokeWeight: 4//线宽
}