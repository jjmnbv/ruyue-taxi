var dataGrid2;
var dataGridLocal;
var selectedResource = new Object();
var resourceTemp = new Object();
var resource = new Object();
var companyIdGloble= "";
var step1cootype="";
var step1leaseCompanyName="";
var step1servicetype="";

$(document).ready(function () {
    initStep1();

    initSelectGrid();
    $(".confirmPop").on("click", confirmPop);
    $(".closePop").on("click", closePop);
    $("#cooagreementCheck").on("click", function(){
        if($(this).is(':checked')){
            $("#step3ConfirmBtn").addClass("red");
        }else{
            $("#step3ConfirmBtn").removeClass("red");
        }
    })

});

function initStep1() {
    $("#cootype").on("change", function(){
        if ($("#cootype").val() == "") {
            $("#cootypeError").removeClass("hidden");
        }else{
            $("#cootypeError").addClass("hidden");
        }
    })

    $("#leaseCompanyName").on("change", function(){
        $("#leaseCompanyNameError").addClass("hidden");
        if ($("#leaseCompanyName").val() == "") {
            $("#leaseCompanyNameError2").removeClass("hidden");
        }else{
            $("#leaseCompanyNameError2").addClass("hidden");
        }
    })

    $("#servicetype").on("change", function(){
        if ($("#servicetype").val() == "") {
            $("#servicetypeError").removeClass("hidden");
        }else{
            $("#servicetypeError").addClass("hidden");
        }
    })
}

function validateLeaseCompanyName(){
    var result = false;
    var data = {
        "name": $("#leaseCompanyName").val() ,
        "type": $("#cootype").val(),
        "servicetype":$("#servicetype").val()
    }

    $.ajax({
        type: 'POST',
        url: "PubCoooperateUnion/queryApplyLeaseCompany",
        data: JSON.stringify(data),
        dataType: "json",
        async: false,
        success: function (data) {
            var res = data.result;
           if(res == "0"){
               $("#leaseCompanyNameError").addClass("hidden");
               companyIdGloble = data.cooagreement.companyid;
               $("#cooagreementIframe").attr("src", "PubCoooperateUnion/cooagreementApply?leasecompanyid=" + data.cooagreement.leasecompanyid + "&companyid=" + data.cooagreement.companyid + "&servicetype=" + data.cooagreement.servicetype);
               result = true;
           }else if(res == "1"){
               $("#leaseCompanyNameError").removeClass("hidden");
               result = false;
           }else if(res == "2"){
               $("#leaseCompanyNameError").addClass("hidden");
               Zalert("提示", "您所选择的合作方未建立合作协议，暂不可申请加盟");
               result = false;
           }else if(res == "3"){
               $("#leaseCompanyNameError").addClass("hidden");
               Zalert("提示", "当前与" + $("#leaseCompanyName").val() + "存在" + $("#cootype").find("option:selected").text() + $("#servicetype").find("option:selected").text() + "合作，请终止或到期后再申请合作");
               result = false;
           }
        },
        contentType: "application/json"
    });

    return result;
}


function validateStep1() {
    if ($("#cootype").val() == "") {
        $("#cootypeError").removeClass("hidden");
        return false;
    }

    if($("#leaseCompanyName").val() == ""){
        $("#leaseCompanyNameError2").removeClass("hidden");
        return false;
    }


    if(!validateLeaseCompanyName()){
        return false;
    }

    if($("#servicetype").val() == ""){
        $("#servicetypeError").removeClass("hidden");
        return false;
    }

    return true;
}


function goStep2() {
    if(!validateStep1()){
        return;
    }

    if(step1cootype != $("#cootype").val() || step1leaseCompanyName != $("#leaseCompanyName").val() || step1servicetype != $("#servicetype").val()){
        resource = new Object();
        resourceTemp = new Object();
        selectedResource = new Object();
        var dataset = [];
        initDetailTableData(dataset);
        $("#cooagreementCheck").prop('checked', false);
        $("#step3ConfirmBtn").removeClass("red");
        changeBtnName();
    }

    step1cootype = $("#cootype").val();
    step1leaseCompanyName = $("#leaseCompanyName").val();
    step1servicetype = $("#servicetype").val();


    $(".step.s1").removeClass("active");
    $(".step.s2").addClass("active");
    $("#step1").addClass("hidden");
    $("#step2").removeClass("hidden");

    if($("#servicetype").val() == "1") {
        $("#vehiclemodelsdiv").addClass("hidden");
    }else{
        $("#vehiclemodelsdiv").removeClass("hidden");
    }

    if(dataGrid2 && $("#servicetype").val() == "1") {
        dataGrid2.fnSetColumnVis(3 , false);
    }else{
        dataGrid2.fnSetColumnVis(3 , true);
    }

    if(dataGridLocal && $("#servicetype").val() == "1") {
        dataGridLocal.fnSetColumnVis(2 , false);
    }else{
        dataGridLocal.fnSetColumnVis(2 , true);
    }

    $("#vehclineid").select2({
        placeholder: "",
        minimumInputLength: 0,
        multiple : false, //控制是否多选
        allowClear : true,
        ajax: {
            url: "PubCoooperateUnion/queryApplyResourceVehclineSelect2",
            dataType: 'json',
            data: function (term, page) {
                var param = {
                    keyword: term,
                    vehicletype: $("#servicetype").val()
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
        url: "PubCoooperateUnion/queryApplyResourceCitySelect2?vehicletype=" + $("#servicetype").val(),
        dataType: "json",
        success: function (result) {
            $("#cityaddrid").html("");
            $("#cityaddrid").append('<option value="">全部</option>')
            $.each(result, function (i, data) {
                $("#cityaddrid").append('<option value="' + data.id + '">' + data.text + '</option>')
            });
        },
        contentType: "application/json"
    });

    $.ajax({
        type: 'GET',
        url: "PubCoooperateUnion/queryApplyCooVehicleModeSelect2?vehicletype=" + $("#servicetype").val(),
        dataType: "json",
        success: function (result) {
            $("#vehiclemodels").html("");
            $("#vehiclemodels").append('<option value="">全部</option>');
            $.each(result, function (i, data) {
                $("#vehiclemodels").append('<option value="' + data.id + '">' + data.text + '</option>')
            });
        },
        contentType: "application/json"
    });
}

function vehiclelPopup(){
    searchGridResource();
    $("#vehiclelPop").show();


    $.each(selectedResource, function(key, value){
        if(value){
            resourceTemp[key] = value;
        }
    });


    updateSelectVeCount();
}

/**
 * 刷新资源编辑GRID
 */
function searchGridResource() {
    var conditionArr = [
        {"name": "vehclineid", "value": $("#vehclineid").val()},
        {"name": "vehiclemodels", "value": $("#vehiclemodels").val()},
        {"name": "workstatus", "value": $("#workstatus").val()},
        {"name": "cityaddrid", "value": $("#cityaddrid").val()},
        {"name": "fullplateno", "value": $("#fullplateno").val()},
        {"name": "jobnum", "value": $("#jobnum").val()},
        {"name": "vehicletype", "value": $("#servicetype").val()}
    ];
    dataGrid2.fnSearch(conditionArr);
}

/**
 * 表格初始化
 */
function initSelectGrid() {
    var grid = {
        id: "resourceSelect",
        sAjaxSource: "PubCoooperateUnion/queryApplyResource",
        userQueryParam: [
            {"name": "vehicletype", "value": $("#servicetype").val()}
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
                mDataProp: "vehicleid",
                sClass: "center",
                sTitle: "<input type='checkbox' class='checkAll' onclick='checkAll(this)'>全选",
                "mRender": function (data, type, full) {
                    var html = "<input ";
                    var driverinfo = full.driverinfo==null?"":full.driverinfo;
                    if(resourceTemp[full.vehicleid]){
                        html += " checked='checked' ";
                    }
                    // else if(driverinfo == ""){
                    //     html += " disabled='disabled' "
                    // }

                    html += " type='checkbox' class='checkBtn' vid='" + full.vehicleid + "' driverinfo='" + driverinfo + "' >";

                    resource[full.vehicleid] = full;

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

    if($("#servicetype").val() == "1") {
        dataGrid2.fnSetColumnVis(3 , false);
    }

    $(".checkAll").on("click", function(){
        if($(this).is(':checked')){
            $.each($(".checkBtn"), function(i){
                $(this).prop("checked", true);
                resourceTemp[$(this).attr("vid")] = resource[$(this).attr("vid")];
            })
        }else{
            $.each($(".checkBtn"), function(i){
                $(this).prop("checked", false);
                resourceTemp[$(this).attr("vid")] = null;
            })
        }

        updateSelectVeCount();
    })

    $("body").on("click", ".checkBtn", function(){
        if($(".checkBtn").length == $(".checkBtn:checked").length){
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


    var gridLocal = {
        id: "selectedTable",
        language: {
            sEmptyTable: "您还没有选择资源"
        },
        bServerSide: false,
        iDisplayLength: 6,
        dataset: [],
        columns: [
            { title: "车牌号" },
            { title: "品牌车系" },
            { title: "服务车型" },
            { title: "资格证号" },
            { title: "司机信息" },
            { title: "登记城市" }
        ]
    };

    dataGridLocal = renderGridLocal(gridLocal);

    if($("#servicetype").val() == "1") {
        dataGridLocal.fnSetColumnVis(2 , false);
    }
}

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
    selectedResource = resourceTemp;

    var dataset = [];
    $.each(selectedResource, function(key, value){
        if(value){
            var v = [];
            v.push(value.fullplateno);
            v.push(value.vehclinename);
            v.push(value.vehiclemodelsname);
            v.push(value.jobnum);
            v.push(value.driverinfo);
            v.push(value.cityname);

            dataset.push(v);
        }
    })

    initDetailTableData(dataset);
    changeBtnName();
    closePop();
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
}

function closePop(){
    $("#vehiclelPop").hide();
    clearParameter();
    resourceTemp = new Object();
}

function initDetailTableData(dataArr) { //dataArr是表格数据数组，和初始化配置需一样的结构
    var oSettings = dataGridLocal.fnSettings(); //这里获取表格的配置
    dataGridLocal.fnClearTable(this); //动态刷新关键部分语句，先清空数据
    for (var i = 0, l = dataArr.length; i < l; i++) {
        dataGridLocal.oApi._fnAddData(oSettings, dataArr[i]); //这里添加一行数据
    }
    oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
    dataGridLocal.fnDraw();//绘制表格
}

function goStep3() {
    if(!validateStep2()){
        return;
    }

    $(".step.s2").removeClass("active");
    $(".step.s3").addClass("active");
    $("#step2").addClass("hidden");
    $("#step3").removeClass("hidden");
}

function validateStep2(){
    var dataset = [];
    $.each(selectedResource, function(key, value){
        if(value){
            var v = [];
            v.push(value.fullplateno);
            v.push(value.vehclinename);
            v.push(value.vehiclemodelsname);
            v.push(value.jobnum);
            v.push(value.driverinfo);
            v.push(value.cityname);

            dataset.push(v);
        }
    });

    if(dataset.length == 0){
        toastr.error("请选择开放资源", "提示");
        return false;
    }

    return true;
}

function changeBtnName(){
    var count = 0;
    $.each(selectedResource, function(key, value){
        if(value){
            count++;
        }
    });

    if(count==0){
        $("#step2EditBtn").text("添加");
    }else{
        $("#step2EditBtn").text("编辑");
    }

}


function goStep4() {
    if(!$("#cooagreementCheck").is(':checked')){
        toastr.error("您未勾选《我已阅读并同意该协议》", "提示");
        return;
    }

    var vehicleid = "";
    $.each(selectedResource, function(key, value){
        if(value){
            vehicleid += value.vehicleid + ",";
        }
    });

    var data = {
        "leasescompanyid": companyIdGloble ,
        "vehicletype": $("#servicetype").val(),
        "cootype":$("#cootype").val(),
        "vehicleid": vehicleid
    }

    $.ajax({
        type: 'POST',
        url: "PubCoooperateUnion/addApplyCoooperate",
        data: JSON.stringify(data),
        dataType: "json",
        async: false,
        success: function (data) {
            if (data.status == 0) {
                $("#coono4").text(data.cooono);
                $("#leaseCompanyName4").text($("#leaseCompanyName").val());
                $("#servicetype4").text($("#servicetype").find("option:selected").text());
                $("#cootype4").text($("#cootype").find("option:selected").text());

                $(".step.s3").removeClass("active");
                $(".step.s4").addClass("active");
                $("#step3").addClass("hidden");
                $("#step4").removeClass("hidden");
            } else if (data.status != 0) {
                toastr.error(data.message, "提示");
            }
        },
        contentType: "application/json"
    });
}

function backStep(){
    if($(".step.s1").hasClass("active")){
        back();
    }else if($(".step.s2").hasClass("active")){
        $(".step.s2").removeClass("active");
        $("#step2").addClass("hidden");
        $(".step.s1").addClass("active");
        $("#step1").removeClass("hidden");
    }else if($(".step.s3").hasClass("active")){
        $(".step.s3").removeClass("active");
        $("#step3").addClass("hidden");
        $(".step.s2").addClass("active");
        $("#step2").removeClass("hidden");
    }else if($(".step.s4").hasClass("active")){
        back();
    }
}



function goBack(){
    var comfirmData={
        tittle:"提示",
        context:"取消后所填写数据不会保存，确定放弃申请操作？",
        button_l:"取消",
        button_r:"确定",
        click: "back()"
    };
    Zconfirm(comfirmData);
}

function back(){
    window.location.href="PubCoooperateUnion/Index";
}




function renderGridLocal(gridObj) {
    var params = {
        data:gridObj.dataset ? gridObj.dataset : null,
        rowCallback:gridObj.rowCallback ? gridObj.rowCallback : null,
        bProcessing: gridObj.bProcessing ? gridObj.bProcessing : false,
        bServerSide: gridObj.bServerSide ? gridObj.bServerSide : false,
        lengthChange: gridObj.hasOwnProperty("lengthChange") ? gridObj.lengthChange : false,
        userQueryParam: gridObj.userQueryParam ? gridObj.userQueryParam : null,
        ordering: gridObj.ordering ? gridObj.ordering : false,
        searching: gridObj.searching ? gridObj.searching : false,
        bSort: gridObj.bSort ? gridObj.bSort : false,
        bInfo: undefined != gridObj.bInfo && null != gridObj.bInfo ? gridObj.bInfo : true,
        bFilter: gridObj.bFilter ? gridObj.bFilter : false,
        bAutoWidth: gridObj.bAutoWidth ? gridObj.bAutoWidth : true,
        iDisplayLength: gridObj.iDisplayLength ? gridObj.iDisplayLength : 10,
        sAjaxSource: gridObj.sAjaxSource,
        columns: gridObj.columns,
        fnInitComplete: gridObj.fnInitComplete ? gridObj.fnInitComplete : null,
        language: {
            sProcessing: "处理中...",
            sLengthMenu: "显示 _MENU_ 项结果",
            sZeroRecords: "没有匹配结果",
            sInfo: "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            sInfoEmpty: "显示第 0 至 0 项结果，共 0 项",
            sInfoFiltered: "(由 _MAX_ 项结果过滤)",
            sInfoPostFix: "",
            sSearch: "搜索:",
            sUrl: "",
            sEmptyTable: gridObj.language ? (gridObj.language.sEmptyTable ? gridObj.language.sEmptyTable : "表中数据为空") : "表中数据为空",
            sLoadingRecords: "载入中...",
            sInfoThousands: ",",
            oPaginate: {
                sFirst: "首页",
                sPrevious: "上页",
                sNext: "下页",
                sLast: "末页"
            },
            oAria: {
                sSortAscending: ": 以升序排列此列",
                sSortDescending: ": 以降序排列此列"
            }
        },
        fnServerData: function(sSource, aoData, fnCallback, oSettings) {
            if(gridObj.userQueryParam != null) {
                aoData = aoData.concat(gridObj.userQueryParam);
            }

            $.ajax({
                url: gridObj.sAjaxSource,
                data: JSON.stringify(convertFormToJson(aoData)),
                type: "post",
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                cache: false,
                success: function(result) {
                    fnCallback(result);

                    if(gridObj.userHandle) {
                        gridObj.userHandle(oSettings, result);
                    }
//	            	$('[data-toggle="tooltip"]').tooltip();
                },
                error: function(msg) {

                }
            })
        }
    }

    if(gridObj.scrollX) {
        params["scrollX"] = true;
        params["scrollCollapse"] = true;
    }
    var dataTable = $("#" + gridObj.id).dataTable(params);

    // 固定列属性设置
    if(gridObj.iLeftColumn) {
        new $.fn.dataTable.FixedColumns(dataTable,{"iLeftColumns": gridObj.iLeftColumn});
    }

    return dataTable;
}