var dataGrid;
var dataGrid2;
var resourceTemp = new Object();
var resource = new Object();

/**
 * 页面初始化
 */
$(function () {
    initGrid();

    initPop();
    resetResourceTemp();
});

function initPop(){
    initSelectGrid();
    initSelect2();


    $(".confirmPop").on("click", confirmPop);
    $(".closePop").on("click", closePop);
}

function resetResourceTemp(){
    var data = {
        "resource": "1",
        "coooid": $("body").attr("coooid")
    }
    $.ajax({
        type: 'POST',
        data: JSON.stringify(data),
        url: "PubCoooperateUnion/queryResource" ,
        dataType: "json",
        success: function (result) {
            resourceTemp = new Object();
            $.each(result.aaData, function(i, data){
                resourceTemp[data.vehicleid] = data;
            })
        },
        contentType: "application/json"
    });
}

function initSelect2(){
    $("#vehclineid").select2({
        placeholder: "品牌车系",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "PubCoooperateUnion/queryResourceVehclineSelect2",
            dataType: 'json',
            data: function (term, page) {
                var param = {
                    keyword: term,
                    coooId: $("body").attr("coooId")
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
        url: "PubCoooperateUnion/queryResourceCitySelect2?coooId=" + $("body").attr("coooId") ,
        dataType: "json",
        success: function (result) {
            $.each(result, function (i, data) {
                $("#cityaddrid").append('<option value="' + data.id + '">' + data.text + '</option>')
            });
        },
        contentType: "application/json"
    });

    $.ajax({
        type: 'GET',
        url: "PubCoooperateUnion/queryCooVehicleModeSelect2?coooId=" + $("body").attr("coooId") ,
        dataType: "json",
        success: function (result) {
            $.each(result, function (i, data) {
                $("#vehiclemodels").append('<option value="' + data.id + '">' + data.text + '</option>')
            });
        },
        contentType: "application/json"
    });


}

/**
 * 表格初始化
 */
function initGrid() {
    var gridObj = {
        id: "dataGrid",
        sAjaxSource: "PubCoooperateUnion/queryResource",
        userQueryParam: [
            {"name": "resource", "value": "1"},
            {"name": "coooid", "value": $("body").attr("coooid")}
        ],
        scrollX: true,//（加入横向滚动条）
        language: {
            sEmptyTable: "暂无任何客户信息"
        },
        columns: [
            {mDataProp: "fullplateno", sTitle: "车牌号", sClass: "center", sortable: true},
            {mDataProp: "vehclinename", sTitle: "品牌车系", sClass: "center", sortable: true},
            {mDataProp: "vehiclemodelsname", sTitle: "服务车型", sClass: "center", sortable: true},
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: true},
            {mDataProp: "driverinfo", sTitle: "司机信息", sClass: "center", sortable: true},
            {mDataProp: "cityname", sTitle: "登记城市", sClass: "center", sortable: true},
            {
                mDataProp: "workstatus",
                sClass: "center",
                sTitle: "服务状态",
                "mRender": function (data, type, full) {
                    switch (data) {
                        case "0":
                            return "空闲"
                        case "1":
                            return "服务中";
                        case "2":
                            return "下线";
                        case "3":
                            return "未绑定";
                        default:
                            return data;
                    }
                }
            },
        ]
    };

    dataGrid = renderGrid(gridObj);

    if($("body").attr("servicetype") == "1"){
        dataGrid.fnSetColumnVis(2, false);
    }
}
/**
 * 刷新已选资源
 */
function refreshResource() {
    var conditionArr = [
        {"name": "resource", "value": "1"},
        {"name": "coooid", "value": $("body").attr("coooid")}
    ];
    dataGrid.fnSearch(conditionArr);
    resetResourceTemp();
}

/**
 * 表格初始化
 */
function initSelectGrid() {
    var grid = {
        id: "resourceSelect",
        sAjaxSource: "PubCoooperateUnion/queryResource",
        userQueryParam: [
            {"name": "coooid", "value": $("body").attr("coooid")},
            {"name": "resource", "value": "0"}
        ],
        language: {
            sEmptyTable: "暂无任何客户信息"
        },
        iDisplayLength: 6,
        userHandle: function(oSettings, result){
            if($(".checkBtn:enabled").length == $(".checkBtn:checked:enabled").length && $(".checkBtn:enabled").length > 0){
                $(".checkAll").prop("checked", true);
            }else{
                $(".checkAll").prop("checked", false);
            }
        },
        columns: [
            {
                mDataProp: "coooid",
                sClass: "center",
                sTitle: "<input type='checkbox' class='checkAll' onclick='checkAll(this)'>",
                "mRender": function (data, type, full) {
                    resource[full.vehicleid] = full;

                    var html = "<input ";
                    var driverinfo = full.driverinfo==null?"":full.driverinfo;
                    if(full.resource == "1" || resourceTemp[full.vehicleid]){
                        html += " checked='checked' ";
                        resourceTemp[full.vehicleid] = full;
                    }else if(driverinfo == ""){
                        html += " disabled='disabled' "
                    }

                    html += " type='checkbox' class='checkBtn' vid='" + full.vehicleid + "' driverinfo='" + driverinfo + "' >";
                   return html;
                }
            },
            {mDataProp: "fullplateno", sTitle: "车牌号", sClass: "center", sortable: true},
            {mDataProp: "vehclinename", sTitle: "品牌车系", sClass: "center", sortable: true},
            {mDataProp: "vehiclemodelsname", sTitle: "服务车型", sClass: "center", sortable: true},
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: true},
            {mDataProp: "driverinfo", sTitle: "司机信息", sClass: "center", sortable: true},
            {mDataProp: "cityname", sTitle: "登记城市", sClass: "center", sortable: true},
            {
                mDataProp: "workstatus",
                sClass: "center",
                sTitle: "服务状态",
                "mRender": function (data, type, full) {
                    switch (data) {
                        case "0":
                            return "空闲"
                        case "1":
                            return "服务中";
                        case "2":
                            return "下线";
                        case "3":
                            return "未绑定";
                        default:
                            return data;
                    }
                }
            }
        ]
    };

    dataGrid2 = renderGrid(grid);

    if($("body").attr("servicetype") == "1"){
        dataGrid2.fnSetColumnVis(3, false);
    }


    $(".checkAll").on("click", function(){
        if($(this).is(':checked')){
            $.each($(".checkBtn:enabled"), function(i){
                $(this).prop("checked", true);
                resourceTemp[$(this).attr("vid")] = resource[$(this).attr("vid")];
            })
        }else{
            $.each($(".checkBtn:enabled"), function(i){
                $(this).prop("checked", false);
                resourceTemp[$(this).attr("vid")] = null;
            })
        }

        updateSelectVeCount();
    })

    $("body").on("click", ".checkBtn", function(){
        if($(this).attr("driverinfo") == ""){
            $(this).prop("disabled", true);
        }

        if($(".checkBtn:enabled").length == $(".checkBtn:checked:enabled").length && $(".checkBtn:enabled").length > 0){
            $(".checkAll").prop("checked", true);
        }else{
            $(".checkAll").prop("checked", false);
        }

        if($(this).is(':checked')){
            resourceTemp[$(this).attr("vid")] = resource[$(this).attr("vid")];
        }else{
            $(".checkAll").prop("checked", false);
            resourceTemp[$(this).attr("vid")] = null;
        }
        updateSelectVeCount();
    })
}

/**
 * 刷新资源编辑GRID
 */
function searchGridResource() {
    // refreshSelectedVehicleId();
    var conditionArr = [
        {"name": "coooid", "value": $("body").attr("coooid")},
        {"name": "vehclineid", "value": $("#vehclineid").val()},
        {"name": "vehiclemodels", "value": $("#vehiclemodels").val()},
        {"name": "workstatus", "value": $("#workstatus").val()},
        {"name": "cityaddrid", "value": $("#cityaddrid").val()},
        {"name": "fullplateno", "value": $("#fullplateno").val()},
        {"name": "jobnum", "value": $("#jobnum").val()},
        {"name": "resource", "value": "0"}
    ];
    dataGrid2.fnSearch(conditionArr);
}

function pop(){
    resource = new Object();

    $("#vehiclelPop").show();
    searchGridResource();

    updateSelectVeCount();
}

// function refreshSelectedVehicleId(){
//     $.ajax({
//         type: 'GET',
//         url: "PubCoooperateUnion/queryCooVehicleId?coooId=" + $("body").attr("coooId") ,
//         dataType: "json",
//         success: function (result) {
//             $("#vehiclelPop").attr("selectedId", eval(result));
//         },
//         contentType: "application/json"
//     });
// }


function updateSelectVeCount(){
    var count = 0;
    $.each(resourceTemp, function(key, value){
        if(value){
            count++;
        }
    });

    $("#selectVeCount").text(count);
}

function confirmPop(){
    var selectedId = "";
    var isNoDriver = false;

    $.each(resourceTemp, function(key, value){
        if(value){
            selectedId += key + ",";
            if(!value.driverinfo || value.driverinfo == ""){
                isNoDriver = true;
            }
        }
    });

    if(selectedId == ""){
        toastr.error("请选择开放的车辆", "提示");
        return;
    }

    var data = {
        "coooId": $("body").attr("coooId") ,
        "vehicleids": selectedId
    }

    $.ajax({
        type: 'POST',
        url: "PubCoooperateUnion/updateResource",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (data) {
            if (data.status == 0) {
                if(isNoDriver){
                    toastr.success("保存成功，所选车辆中存在未绑定司机车辆", "提示");
                }else{
                    toastr.success("保存成功", "提示");
                }

                closePop();
                refreshResource();
            } else if (data.status != 0) {
                toastr.error(data.message, "提示");
            }
        },
        contentType: "application/json"
    });

}


function closePop(){
    $("#vehiclelPop").hide();
    $("#vehiclelPop").attr("selectedId", "");
    clearParameter();
}

/**
 * 清空
 */
function clearParameter() {
    $("#vehclineid").select2("val", "");
    $("#vehiclemodels").val("");
    $("#workstatus").val("");
    $("#cityaddrid").val("");
    $("#fullplateno").val("");
    $("#jobnum").val("");
    searchGridResource();
    resetResourceTemp();
}