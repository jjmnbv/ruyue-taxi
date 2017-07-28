/*
 *修改警报记录数
*/
function onUpdateMessage(con, act, count) {

    $.ajax({
        url: "/AlarmMessage/UpdateMessage",
        cache: false,
        data: {
            con: con,
            act: act,
            count: count
        },
        success: function () {
            Push();
        },
        error: function (xhr, status, error) {
            return;
        }
    });
}
/*
 *绑定用户报警提示信息
*/
function Push() {
    $.ajax({
        url: "/AlarmMessage/GetAllMessage",
        cache: false,
        success: function (data) {
            $("#layRefreshDateTime").html(data.RefreshDateTime);
            data = data.result;
            if (data != null && data.length > 0) {
                var alarmStr = "";
                var vehcApplyStr = "";
                var annualInspectionStr = "";
                var alarmSum = 0;
                var vehcApplySum = 0;
                var annualInspectionSum = 0;
                var alarmCount = 0;
                var vehcApplyCount = 0;
                var annualInspectionCount = 0;
                var hasAlarm = false;
                var hasVehcApply = false;
                var hasAnnualInspection = false;
                var vehcApplication = "";
                var hasVehcApplication = false; //调度管理提醒
                for (i = 0; i < data.length; i++) {
                    if (data[i].D_TYPE == 2 || data[i].D_TYPE == "" || data[i].D_TYPE == null) {
                        alarmStr += "<li><a href='/" + data[i].D_CON + "/" + data[i].D_ACTION + "?param1=" + data[i].D_VALUE + "'>" +
                                        data[i].D_TEXT + "<span class='badge badge-error badge-roundless  pull-right' >" + data[i].D_VALUE + "</span></a></li>";
                        alarmSum += parseInt(data[i].D_VALUE);
                        alarmCount = alarmCount + 1;
                        hasAlarm = true;
                    }
                    else if (data[i].D_TYPE == 1) {
                        vehcApplyStr += "<li><a href='/" + data[i].D_CON + "/" + data[i].D_ACTION + "?param1=" + data[i].D_VALUE + "'>" +
                                        data[i].D_TEXT + "<span class='badge badge-error badge-roundless  pull-right' >" + data[i].D_VALUE + "</span></a></li>";
                        vehcApplySum += parseInt(data[i].D_VALUE);
                        vehcApplyCount = vehcApplyCount + 1;
                        hasVehcApply = true;
                    }
                    else if (data[i].D_TYPE == 3) {
                        annualInspectionStr += "<li><a href='/" + data[i].D_CON + "/" + data[i].D_ACTION + "?param1=2'>" +
                                        "年检即将到期" + "<span class='badge badge-warning badge-roundless pull-right' >" + data[i].D_VALUE + "</span></a></li>";
                        annualInspectionSum += parseInt(data[i].D_VALUE);
                        annualInspectionCount = annualInspectionCount + 1;
                        hasAnnualInspection = true;
                    }
                    else if (data[i].D_TYPE == 4) {
                        annualInspectionStr += "<li><a href='/" + data[i].D_CON + "/" + data[i].D_ACTION + "?param1=3'>" +
                                        "年检已过期" + "<span class='badge badge-error badge-roundless pull-right' >" + data[i].D_VALUE + "</span></a></li>";
                        annualInspectionSum += parseInt(data[i].D_VALUE);
                        annualInspectionCount = annualInspectionCount + 1;
                        hasAnnualInspection = true;
                    }
                    else if (data[i].D_TYPE == 6) {
                        annualInspectionStr += "<li><a href='/" + data[i].D_CON + "/" + data[i].D_ACTION + "?param1=2'>" +
                                        "保养即将到期" + "<span class='badge badge-warning badge-roundless pull-right' >" + data[i].D_VALUE + "</span></a></li>";
                        annualInspectionSum += parseInt(data[i].D_VALUE);
                        annualInspectionCount = annualInspectionCount + 1;
                        hasAnnualInspection = true;
                    }
                    else if (data[i].D_TYPE == 7) {
                        annualInspectionStr += "<li><a href='/" + data[i].D_CON + "/" + data[i].D_ACTION + "?param1=3'>" +
                                        "保养已过期" + "<span class='badge badge-error badge-roundless pull-right' >" + data[i].D_VALUE + "</span></a></li>";
                        annualInspectionSum += parseInt(data[i].D_VALUE);
                        annualInspectionCount = annualInspectionCount + 1;
                        hasAnnualInspection = true;
                    }
                    else if (data[i].D_TYPE == 9) {
                        annualInspectionStr += "<li><a href='/" + data[i].D_CON + "/" + data[i].D_ACTION + "?param1=3'>" +
                                        "违章10天未处理" + "<span class='badge badge-error badge-roundless pull-right' >" + data[i].D_VALUE + "</span></a></li>";
                        annualInspectionSum += parseInt(data[i].D_VALUE);
                        annualInspectionCount = annualInspectionCount + 1;
                        hasAnnualInspection = true;
                    }
                    else if (data[i].D_TYPE == 10) {
                        annualInspectionStr += "<li><a href='/" + data[i].D_CON + "/" + data[i].D_ACTION + "?param1=10'>" +
                                        "保单即将过期" + "<span class='badge badge-warning badge-roundless  pull-right' >" + data[i].D_VALUE + "</span></a></li>";
                        annualInspectionSum += parseInt(data[i].D_VALUE);
                        annualInspectionCount = annualInspectionCount + 1;
                        hasAnnualInspection = true;
                    }
                    else if (data[i].D_TYPE == 11) {
                        annualInspectionStr += "<li><a href='/" + data[i].D_CON + "/" + data[i].D_ACTION + "?param1=11'>" +
                                        "保单已过期" + "<span class='badge badge-error badge-roundless  pull-right' >" + data[i].D_VALUE + "</span></a></li>";
                        annualInspectionSum += parseInt(data[i].D_VALUE);
                        annualInspectionCount = annualInspectionCount + 1;
                        hasAnnualInspection = true;
                    }
                    else if (data[i].D_TYPE == 12) {
                         vehcApplyStr += "<li><a href='/" + data[i].D_CON + "/" + data[i].D_ACTION + "?param1=" + data[i].D_VALUE + "'>" +
                                        data[i].D_TEXT + "<span class='badge badge-error badge-roundless  pull-right' >" + data[i].D_VALUE + "</span></a></li>";
                        vehcApplySum += parseInt(data[i].D_VALUE);
                        vehcApplyCount = vehcApplyCount + 1;
                        hasVehcApply = true;
                    }
                }
                var istasl = false;
                if (hasAlarm) {
                    $("#partialContainer").html(alarmStr);
                    $("#amcount").html("您有" + alarmCount + "种报警提示");
                    $("#amsum").html(alarmSum);
                    istasl = true;
                }
                if (hasVehcApply) {
                    $("#vehcApplyContainer").html(vehcApplyStr);
                    $("#vehcApplycount").html("您有" + vehcApplyCount + "条待办事项");
                    $("#vehcApplysum").html(vehcApplySum);
                    istasl = true;
                }
                if (hasAnnualInspection) {
                    $("#annualInspectionContainer").html(annualInspectionStr);
                    $("#annualInspectioncount").html("您有" + annualInspectionCount + "种车务提醒");
                    $("#annualInspectionsum").html(annualInspectionSum);
                    istasl = true;
                }
                if (istasl) {
                    $("#taslSum").html(".");
                }
            }
        },
        error: function (xhr, status, error) {
            return;
        }
    });
}

/*
 *刷新车务提醒状态
*/
function onUpdateVehcServiceMessage() {
    $("#annualInspectioncount_layRefresh").show();
    $("#btnLayRefresh").attr("disabled", "true");
    $.ajax({
        url: "/AlarmMessage/UpdateVehcServiceMessage",
        cache: false,
        success: function (data) {
            $("#btnLayRefresh").attr("disabled", false);
            $("#layRefreshDateTime").html(data);
            $("#annualInspectioncount_layRefresh").hide();
            Push();
        },
        error: function (xhr, status, error) {
            return;
        }
    });
}
