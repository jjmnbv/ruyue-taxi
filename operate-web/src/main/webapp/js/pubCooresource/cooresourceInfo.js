$(document).ready(function () {
    initDriverDropdown();
    initGrid();


});

function initDriverDropdown(){
    $("#driverinfo").select2({
        placeholder: "姓名/手机号",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "PubCooresource/info/queryPubCooresourceInfoDriverSelect/" + $("body").attr("coooid"),
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
}

function initGrid() {
    var grid = {
        id: "dataGrid",
        sAjaxSource: "PubCooresource/info/queryPubCooresourceInfoData/" + $("body").attr("coooid"),
        userQueryParam: [],
        language: {
            sEmptyTable: "没有查询到相关信息"
        },
        bAutoWidth: false,
        columns: [
            {mDataProp: "fullplateno", sTitle: "车辆信息", sClass: "center", sortable: false},
            {mDataProp: "driverinfo", sTitle: "司机信息", sClass: "center", sortable: false},
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: false}
        ]
    };
    var table = renderGrid(grid);

    $("#search").on("click", function(){
        search(table);
    });

    $("#clearSearchBox").on("click", function(){
        $("#driverinfo").select2("val", "");
        $("#jobnum").val("");
        $("#fullplateno").val("");
        search(table);
    });
}


/** 查询 **/
function search(grid) {
    var userQueryParam = [
        {name: "driverinfo", value: $("#driverinfo").val()},
        {name: "jobnum", value: $("#jobnum").val()},
        {name: "fullplateno", value: $("#fullplateno").val()}
    ]
    grid.fnSearch(userQueryParam);
}
