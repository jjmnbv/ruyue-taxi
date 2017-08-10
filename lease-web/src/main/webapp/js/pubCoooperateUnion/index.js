$(document).ready(function () {
    initLeasecompany();
    initGrid();
    initDate();

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

}

function initLeasecompany() {
    $.ajax({
        type: 'GET',
        url: "PubCoooperateUnion/queryLeasecompany",
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
        sAjaxSource: "PubCoooperateUnion/queryPubCoooperateData",
        userQueryParam: [
        ],
        iLeftColumn: 1,
        scrollX: true,
        bAutoWidth: false,
        language: {
            sEmptyTable: "暂无任何合作运营信息"
        },
        columns: [
            {
                mDataProp: "id",
                sClass: "left",
                sTitle: "操作",
                sortable: false,
                "mRender": function (data, type, full) {
                    var html = "";
                    // 0-审核中,1-合作中,2-未达成,3-已终止,4-已过期
                    if(full.coostate != 1){
                        html += '<button type="button" class="SSbtn" onclick="updateResource(\'' + data + '\',\'query\')"><i class="fa fa-paste"></i>查看资源</button>';
                        html += '&nbsp;';
                    }

                    if(full.coostate == 1){
                        html += '<button type="button" class="SSbtn red" onclick="disable(\'' + data + '\',\'' + full.leasecompanytext + '\')"><i class="fa fa-paste"></i>终止合作</button>';
                        html += '&nbsp;';
                        html += '<button type="button" class="SSbtn" onclick="updateResource(\'' + data + '\')"><i class="fa fa-paste"></i>调整资源</button>';
                        html += '&nbsp;';
                    }

                    html += '<button type="button" class="SSbtn" onclick="agreement(\'' + data + '\')"><i class="fa fa-paste"></i>查看协议</button>';
                    return html;
                }
            },
            {mDataProp: "coono", sTitle: "合作编号", sClass: "center", sortable: false},
            {mDataProp: "leasecompanytext", sTitle: "合作方", sClass: "center", sortable: false},
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
                sTitle: "合作业务",
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
            {mDataProp: "validatetime", sTitle: "有效期限", sClass: "center", sortable: false},
            {mDataProp: "applicationtime", sTitle: "申请日期", sClass: "center", sortable: false}
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
        {name: "applicationtimeEnd", value: $("#endTime").val()},
        {name: "cootype", value: $("#cootype").val()}
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
    $("#cootype").val("");
    $("#search").click();
}

function review(id){
    $("#reviewPop").show();
    $("#reviewPopConfirmBtn").attr("dataid", id)
}

function disable(id, name){
    var comfirmData={
        tittle:"提示",
        context:"您确定要终止合作？",
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
        url: "PubCoooperateUnion/disableCoooperate",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (result) {
            toastr.success("终止合作成功", "提示");
            $("#search").click();
        },
        contentType: "application/json"
    });
}

function agreement(id){
    window.location.href = "PubCoooperateUnion/cooagreementView/" + id;
}


function updateResource(id, fun){
    var query = "";
    if(!fun){
        query = "add";
    }

    window.location.href = "PubCoooperateUnion/updateResource/" + id + "?query=" + query;
}



