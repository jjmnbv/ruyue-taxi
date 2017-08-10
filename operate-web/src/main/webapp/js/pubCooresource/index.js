$(document).ready(function () {
    initLeasecompany();
    initGrid();


});


function initLeasecompany() {
    $.ajax({
        type: 'GET',
        url: "PubCooresource/queryHavingCooLeasecompany",
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
        sAjaxSource: "PubCooresource/queryPubCooresourceData",
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

                    html += '<button type="button" class="SSbtn green" onclick="manage(\'' + data + '\')"><i class="fa fa-paste"></i>资源管理</button>';
                    html += '&nbsp;';
                    html += '<button type="button" class="SSbtn red" onclick="info(\'' + data + '\')"><i class="fa fa-paste"></i>资源信息</button>';

                    return html;
                }
            },
            {mDataProp: "coono", sTitle: "合作编号", sClass: "center", sortable: false},
            {mDataProp: "leasecompanytext", sTitle: "战略伙伴", sClass: "center", sortable: false},
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
            {
                mDataProp: "workresource",
                sClass: "center",
                sTitle: "投运资源数(个)",
                "mRender": function (data, type, full) {
                    if(parseInt(data) < parseInt(full.openresource)){
                        return "<span style='color:red'>" + data + "</span>";
                    }else{
                        return data;
                    }
                }
            }
        ]
    };
    var table = renderGrid(grid);

    $("#search").on("click", function(){
        search(table);
    });

    $("#clearSearchBox").on("click", function(){
        $("#coono").val("");
        $("#leasecompany").val("");
        $("#servicetype").val("");
        search(table);
    });
}


/** 查询 **/
function search(grid) {
    var userQueryParam = [
        {name: "coono", value: $("#coono").val()},
        {name: "leasecompanyid", value: $("#leasecompany").val()},
        {name: "servicetype", value: $("#servicetype").val()}
    ]
    grid.fnSearch(userQueryParam);
}

function manage(id){
    window.location.href = "PubCooresource/manage/" + id;
}

function info(id){
    window.location.href = "PubCooresource/info/" + id;
}