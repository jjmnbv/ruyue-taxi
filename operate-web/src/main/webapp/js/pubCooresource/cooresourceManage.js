$(document).ready(function () {
    initDropdown();
    initGrid();
});





function initDropdown(){
    $("#driverinfo").select2({
        placeholder: "姓名/手机号",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "PubCooresource/manage/queryPubCooresourceManageDriverSelect/" + $("body").attr("coooid"),
            dataType: 'json',
            data: function (term, page) {
                var param = {
                    keyword: term,

                }
                return param;
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });

    $.ajax({
        type: 'GET',
        url: "PubCooresource/manage/queryPubCooresourceManageVehicleModelSelect",
        dataType: "json",
        success: function (result) {
            $.each(result, function (i, data) {
                $("#vehicleModelId").append('<option value="' + data.id + '">' + data.text + '</option>')
            });
        },
        contentType: "application/json"
    });

    $.ajax({
        type: 'GET',
        url: "PubCooresource/manage/queryPubCooresourceManageVehicleModelSelect",
        dataType: "json",
        success: function (result) {
            $.each(result, function (i, data) {
                $("#vehicleModelIdPop").append('<option value="' + data.id + '">' + data.text + '</option>')
            });
        },
        contentType: "application/json"
    });
}

function initGrid() {
    var grid = {
        id: "dataGrid",
        sAjaxSource: "PubCooresource/manage/queryPubCooresourceManageData/" + $("body").attr("coooid"),
        userQueryParam: [],
        bAutoWidth: false,
        language: {
            sEmptyTable: "没有查询到相关信息"
        },
        columns: [
            {
                mDataProp: "id",
                sClass: "left",
                sTitle: "操作",
                sortable: false,
                "mRender": function (data, type, full) {
                    var vehicleno = "";
                    if(full.vehicleinfo.split(" ").length > 1){
                        vehicleno = full.vehicleinfo.split(" ")[1];
                    }

                    var html = "";
                    html += '<button type="button" class="SSbtn green" onclick="openVehicleModelPop(\'' + data + '\',\'' + vehicleno + '\')"><i class="fa fa-paste"></i>分配车型</button>';
                    return html;
                }
            },
            {mDataProp: "vehicleinfo", sTitle: "车辆信息", sClass: "center", sortable: false},
            {mDataProp: "driverinfo", sTitle: "司机信息", sClass: "center", sortable: false},
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: false},
            {mDataProp: "vehicleModel", sTitle: "服务车型", sClass: "center", sortable: false},
            {mDataProp: "updatetime", sTitle: "修改时间", sClass: "center", sortable: false}
        ]
    };
    var table = renderGrid(grid);

    $("#search").on("click", function(){
        search(table);
    });
}


/** 查询 **/
function search(grid) {
    var userQueryParam = [
        {name: "driverinfo", value: $("#driverinfo").val()},
        {name: "fullplateno", value: $("#fullplateno").val()},
        {name: "jobnum", value: $("#jobnum").val()},
        {name: "vehicleModelId", value: $("#vehicleModelId").val()}
    ]
    grid.fnSearch(userQueryParam);
}

/** 清空 **/
function clearSearchBox() {
    $("#driverinfo").select2("val", "");
    $("#vehicleModelId").val("");
    $("#fullplateno").val("");
    $("#jobnum").val("");
    $("#search").click();
}

function openVehicleModelPop(coorId, vehicleno){
    $("#vehicleModelPop").show();
    $("#vehicleModelPop").attr("dataid", coorId);
    $("#vehicleModelOrg").html(vehicleno);
}

function updateVehicleModel(coorId) {
    if ($("#vehicleModelIdPop").val() == "") {
        toastr.error("请选择分配车型", "提示");
        return;
    }

    var data = {
        "coorId": $("#vehicleModelPop").attr("dataid"),
        "vehiclemodelsid": $("#vehicleModelIdPop").val()
    }

    $.ajax({
        type: 'POST',
        url: "PubCooresource/manage/updateVehicleModel",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (data) {
            if (data.status == 0) {
                toastr.success("分配成功", "提示");
                closeVehicleModelPop();
                $("#search").click();
            } else if (data.status != 0) {
                toastr.error(data.message, "提示");
            }
        },
        contentType: "application/json"
    });
}


function closeVehicleModelPop(){
    $("#vehicleModelPop").hide();
    $("#vehicleModelIdPop").val("");
    $("#vehicleModelPop").attr("dataid", "");
}

