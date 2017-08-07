$(document).ready(function () {
    initLeasecompany();
    initGrid();
    initDate();

    $("#reviewResion").on("blur", function(){
        if($("input[name='reviewRadio']:checked").val() == 0){
            if($("#reviewResion").val() == ""){
                $("#reviewResionTip").show();
            }else{
                $("#reviewResionTip").hide();
            }
        }else{
            $("#reviewResionTip").hide();
        }
    });

    $("input[name='reviewRadio']").on("change", function(){
        $("#reviewResionTip").hide();
    });
});

function initDate(){
    $("#startTime").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
        clearBtn: true,
        todayBtn:  1
    }).on("click",function(){
        $("#startTime").datetimepicker("setEndDate",$("#endTime").val())
    });
    $("#endTime").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
        clearBtn: true,
        todayBtn:  1
    }).on("click",function(){
        $("#endTime").datetimepicker("setStartDate",$("#startTime").val())
    });

    $("#validateTimeStart").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
        clearBtn: true,
        startDate: new Date(),
        todayBtn:  1
    }).on("click",function(){
        $("#validateTimeStart").datetimepicker("setEndDate",$("#validateTimeEnd").val())
    });
    $("#validateTimeEnd").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose: true,
        startDate: new Date(),
        clearBtn: true,
        todayBtn:  1
    }).on("click",function(){
        $("#validateTimeEnd").datetimepicker("setStartDate",$("#validateTimeStart").val())
    });
}

function initLeasecompany() {
    $.ajax({
        type: 'GET',
        url: "coooperate/queryLeasecompany",
        dataType: "json",
        success: function (result) {
            $.each(result, function (i, data) {
                $("#leasecompany").append('<option value="' + data.id + '">' + data.text + '</option>')
            });
        },
        contentType: "application/json"
    });


}

function initGrid() {
    var grid = {
        id: "dataGrid",
        sAjaxSource: "coooperate/queryPubCoooperateData",
        userQueryParam: [
            // {name: "ordertype", value: $("#ordertype").val()}
        ],
        iLeftColumn: 3,
        scrollX: true,
        bAutoWidth: false,
        columns: [
            {
                mDataProp: "id",
                sClass: "left",
                sTitle: "操作",
                sortable: false,
                "mRender": function (data, type, full) {
                    var html = "";
                    // 0-审核中,1-合作中,2-未达成,3-已终止,4-已过期
                    if(full.coostate == 0){
                        html += '<button type="button" class="SSbtn green" onclick="review(\'' + data + '\')"><i class="fa fa-paste"></i>审核</button>';
                        html += '&nbsp;';
                    }

                    if(full.coostate == 1){
                        html += '<button type="button" class="SSbtn" onclick="disable(\'' + data + '\',\'' + full.leasecompanytext + '\')"><i class="fa fa-paste"></i>禁用</button>';
                        html += '&nbsp;';
                    }

                    html += '<button type="button" class="SSbtn red" onclick="agreement(\'' + data + '\')"><i class="fa fa-paste"></i>查看协议</button>';
                    return html;
                }
            },
            {mDataProp: "coono", sTitle: "合作编号", sClass: "center", sortable: false},
            {mDataProp: "leasecompanytext", sTitle: "战略伙伴", sClass: "center", sortable: false},
            {
                mDataProp: "cootype",
                sClass: "center",
                sTitle: "合作类型",
                "mRender": function (data, type, full) {
                    switch (data) {
                        case 0:
                            return "B2B联盟"
                        case 1:
                            return "B2C联营";
                        default:
                            return "";
                    }
                }
            },
            {
                mDataProp: "servicetype",
                sClass: "center",
                sTitle: "加盟业务",
                "mRender": function (data, type, full) {
                    switch (data) {
                        case 0:
                            return "网约车"
                        case 1:
                            return "出租车";
                        default:
                            return "";
                    }
                }
            },
            {mDataProp: "openresource", sTitle: "开放资源数(个)", sClass: "center", sortable: false},
            {mDataProp: "validatetime", sTitle: "有效期限", sClass: "center", sortable: false},
            {
                mDataProp: "coostate",
                sClass: "center",
                sTitle: "合作状态",
                "mRender": function (data, type, full) {
                    switch (data) {
                        case 0:
                            return "审核中";
                        case 1:
                            return "合作中";
                        case 2:
                            return "未达成"
                        case 3:
                            return "已终止"
                        case 4:
                            return "已过期"
                        default:
                            return "";
                    }
                }
            },
            {mDataProp: "applicationtime", sTitle: "申请日期", sClass: "center", sortable: false},
            {mDataProp: "reviewtime", sTitle: "审核日期", sClass: "center", sortable: false},
            {
                mDataProp: "reviewtext",
                sTitle: "审核意见",
                sClass: "center",
                sortable: false,
                "sWidth": 200,
                "mRender": function (data, type, full) {
                    return showToolTips(data, 12, undefined, undefined);
                }
            }
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
        {name: "coono", value: $("#coono").val()},
        {name: "leasecompanyid", value: $("#leasecompany").val()},
        {name: "servicetype", value: $("#servicetype").val()},
        {name: "coostate", value: $("#coostate").val()},
        {name: "applicationtimeStart", value: $("#startTime").val()},
        {name: "applicationtimeEnd", value: $("#endTime").val()}
    ]
    grid.fnSearch(userQueryParam);
}

/** 清空 **/
function clearSearchBox() {
    $("#coono").val("");
    $("#leasecompany").val("");
    $("#servicetype").val("");
    $("#coostate").val("");
    $("#startTime").val("");
    $("#endTime").val("");
}

function review(id){
    $("#reviewPop").show();
    $("#reviewPopConfirmBtn").attr("dataid", id)
}

function disable(id, name){
    var comfirmData={
        tittle:"提示",
        context:"确定要禁用与【" + name + "】的合作关系？",
        button_l:"否",
        button_r:"是",
        click: "disableCoooperate('" + id +"')"
    };
    Zconfirm(comfirmData);
}

function disableCoooperate(id){
    var data = {
        "id" : id
    }

    $.ajax({
        type: 'POST',
        url: "coooperate/disableCoooperate",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (result) {
            toastr.success("禁用成功", "提示");
            $("#search").click();
        },
        contentType: "application/json"
    });
}

function agreement(id){
    window.location.href = "coooperate/cooagreementView/" + id;
}

function reviewPopConfirm(){
    var check = $("input[name='reviewRadio']:checked").val();
    if(!check){
        toastr.error("请选择审核意见", "提示");
        return;
    }

    var coostate = "";
    var reviewResion = "";
    var coostarttime = "";
    var cooendtime = "";

    if(check == 1){
        if($("#validateTimeStart").val() == "" || $("#validateTimeEnd").val() == ""){
            toastr.error("请选择有效期", "提示");
            return;
        }

        coostate = 1;
        coostarttime = $("#validateTimeStart").val();
        cooendtime = $("#validateTimeEnd").val();
    }else if(check == 0){
        if($("#reviewResion").val() == ""){
            $("#reviewResionTip").show();
            return;
        }

        coostate = 2;
        reviewResion = $("#reviewResion").val();
    }

    var data = {
        "coostate" : coostate,
        "id" : $("#reviewPopConfirmBtn").attr("dataid"),
        "coostarttime" : coostarttime,
        "cooendtime" : cooendtime,
        "reviewtext" : reviewResion
    }

    $.ajax({
        type: 'POST',
        url: "coooperate/reviewLeasecompany",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (data) {
            if(data.status == 0){
                toastr.success("审核成功", "提示");
                closereviewPop();
                $("#search").click();
            }else if(data.status != 0){
	    		toastr.error(data.message, "提示");
            }
        },
        contentType: "application/json"
    });
}

function closereviewPop(){
    $("#reviewPop").hide();

    $.each($("input[name='reviewRadio']"), function(i, obj){
        $(obj).attr("checked", false);
    });
    $("#validateTimeStart").val("");
    $("#validateTimeEnd").val("");
    $("#reviewResion").val("");
    $("#reviewPopConfirmBtn").attr("dataid", "");
}

