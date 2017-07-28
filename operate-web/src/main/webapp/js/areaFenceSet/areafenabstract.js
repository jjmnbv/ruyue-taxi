/**
车牌都加上超链接，连接至车辆具体信息
**/
function getAreaFenceByVId(vId) {

    var $vId = $("[data-vId='" + vId + "']");
    if ($vId.data("data-content")) {
        return;
    }

    $.ajax({
        url: "/AreaFenceSet/GetAreaFenceByVehc",
        cache: false,
        data: {
            vId: vId
        },
        success: function (data) {

            var content = "";
            if (data.aaData) {
                for (i = 0; i < data.aaData.length; i++) {
                    content += "<p>栅栏名称：" + data.aaData[i].AFS_NAME + "&nbsp;&nbsp;状态：" + data.aaData[i].AFS_FENCESTATUS + "</p>";
                    content += "<p>允许运行城市：" + data.aaData[i].AFS_AREA + "</p>";
                    if (i < data.aaData.length - 1) {
                        content += "<hr>";
                    }
                }
            } else {
                content = "<p style='color:red;'>暂未提供数据</p>";
            }
            $vId.data("data-content", content);
            $vId.attr("data-content", content);
        },
        error: function (xhr, status, error) {
            content = "加载出错了";
        },
        async: false
    });
}

function setAreaFenceAbstract() {

    $("[data-toggle='areaFencePopover']").popover({
        trigger: 'manual',
        template: '<div class="popover" style="width:750px;"><div class="arrow"></div><div class="popover-inner"><div class="popover-content"><p></p></div></div></div>',
        html: 'true'
    }).on("mouseenter", function () {
        var _this = this;
        $(this).popover("show");
        $(this).siblings(".popover").on("mouseleave", function () {
            $(_this).popover('hide');
        });
    }).on("mouseleave", function () {
        var _this = this;
        setTimeout(function () {
            if (!$(".popover:hover").length) {
                $(_this).popover("hide");
            }
        }, 100);
    });
}
