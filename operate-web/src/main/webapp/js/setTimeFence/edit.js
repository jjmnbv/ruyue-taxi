var validator;
var id;

$(function () {
    id = $("#id").val();
    var frmmodal = $("#frmmodal");
    var erroralert = $('.alert-danger', frmmodal);
    var successalert = $('.alert-success', frmmodal);
    validator = frmmodal.validate(
        {
            submitHandler: function (form) {
                successalert.show();
                erroralert.hide();
                save();
            }
        });
    $("#btnCancel").click(function () {
        location.href = "SetTimeFence/Index";
    })

    if (id > '0') {

        initForm(id);
    } else {
        //设置栅栏初始值
        bindSlider(480, 1080)
    }
    onSetMonitorPeriod();
});
//绑定控件数据
function bindSlider(from, to) {
    //初始化时间栅栏
    $("#rangeName").ionRangeSlider({
        min: 0,
        max: 1440,
        from: from,
        to: to,
        type: "double",
        step: 30,
        postfix: " H",
        grid: true,
        grid_snap: true,
        onChange: function (obj) {
        },
        onFinish: function (obj) {
            $("#startTime").val(obj.from);
            $("#endTime").val(obj.to);
        },
        prettify: function (value) {
            var hours = Math.floor(value / 60);
            var mins = (value - hours * 60);
            return (hours < 10 ? "0" + hours : hours) + ":" + (mins == 0 ? "00" : mins);
        }
    });
}
//设置重复周期
function onSetMonitorPeriod() {
    _loading.show();
    $.ajax({
        url: "SetTimeFence/getMonitorperiod",
        cache: false,
        data: {id: id > '0' ? id : -1},
        success: function (data) {
            var content = "";
            if (data.success != null && data.success != "undefined") {
                content += "<div class='row checkbox-list'  >";
                content += "<div class='col-12'>";
                for (var i = 0; i < data.success.length; i++) {
                    content += "<div class='col-1'>"
                    content += "<label>";
                    content += "<input name='cbProject'  type='checkbox' id='" + data.success[i].value + "' value='" + data.success[i].value + "'";
                    if (data.success[i].status == 1) {
                        content += " checked='checked' ";
                    }
                    content += "> " + data.success[i].text + " </label>";
                    content += "</div>";

                }
                content += "</div></div>";

                $("#cbMonitorPeriod").html(content);
                _loading.hide();
            }
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText);
        }
    });
}
//初始化栅栏信息界面，如果id>0代表修改，否则代码<img src="~/Content/assets/img/icon/add.png" />新增
function initForm(id) {
    $.ajax({
        url: "SetTimeFence/toUpdate",
        cache: false,
        data: {id: id},
        success: function (json) {
            setForm(json);
        },
        error: function (xhr, status, error) {
            showerror(xhr.responseText, "SetTimeFence/Index");
            return;
        }
    });
}

function setForm(entity) {
    $("#name").val(entity.entityName);
    $("#startTime").val(entity.startTime);
    $("#endTime").val(entity.endTime);
    bindSlider(entity.startTime, entity.endTime);
}

//保存
function save() {
    if (!$("input[name=cbProject]").is(':checked')) {
        toastr.warning("至少选择一个周期");
        return;
    }
    var iList = new Array();
    for (var i = 0; i < $("input:checkbox:checked").length; i++) {
        var iVal = $("input:checkbox:checked")[i].value;
        iList.push(iVal);
    }

    //组合数据
    var data = {
        id: id,
        name: $("#name").val(),
        startTime: $("#startTime").val(),
        endTime: $("#endTime").val(),
        monitorperiod: iList.join('|')
    }

    $.ajax({
        type: 'POST',
        cache: false,
        dataType: 'json',
        url: 'SetTimeFence/setTimeFenceAll',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (data) {
            if (data.status == 0) {
                var message = data.msg == null ? data
                    : data.msg;
                toastr.options.onHidden = function () {
                    window.location.href = basePath + "SetTimeFence/Index";
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