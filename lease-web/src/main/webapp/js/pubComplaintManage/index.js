var dataGrid;

/**
 * 页面初始化
 */
$(function() {
	// initGrid();\
    initDropdown();
    initDate();
    initGrid();
});

function initGrid(){
    var grid = {
        id: "dataGrid",
        sAjaxSource: "PubComplaintManage/queryPubComplaints",
        language: {
            sEmptyTable: "暂无待处理投诉信息"
        },
        userQueryParam: [
            {name: "processstatus", value: 0}
        ],
        iLeftColumn: 0,
        scrollX: true,
        bAutoWidth: false,
        columns: [
            {
                mDataProp: "id",
                sClass: "center",
                sTitle: "操作",
                sortable: false,
                "mRender": function (data, type, full) {
                    var html = "";
                        html += '<button type="button" class="SSbtn red" onclick="deal(\'' + data + '\')"><i class="fa fa-paste"></i>处理投诉</button>';
                    return html;
                }
            },
            {mDataProp: "orderno", sTitle: "订单编号", sClass: "center", sortable: false},
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: false},
            {mDataProp: "name", sTitle: "司机", sClass: "center", sortable: false},
            {mDataProp: "phone", sTitle: "司机手机号", sClass: "center", sortable: false},
            {mDataProp: "fullplateno", sTitle: "车牌号", sClass: "center", sortable: false},
            {mDataProp: "nickname", sTitle: "下单人", sClass: "center", sortable: false},
            {mDataProp: "account", sTitle: "下单人手机号", sClass: "center", sortable: false},
            {mDataProp: "leasename", sTitle: "所属机构", sClass: "center", sortable: false},
            {mDataProp: "complainttype", sTitle: "投诉类型", sClass: "center", sortable: false},
            {mDataProp: "complaintcontent", sTitle: "投诉内容", sClass: "center", sortable: false},
            {mDataProp: "createtime", sTitle: "投诉时间", sClass: "center", sortable: false}
        ]
    };
    var table = renderGrid(grid);

    $("#search").on("click", function(){
        search(table);
    });
}

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

function initDropdown(){
    $("#orderno").select2({
        placeholder: "",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "PubComplaintManage/queryPubComplaintsordernos",
            dataType: 'json',
            data: function (term, page) {
                var param = {
                    sSearch: term,
                    key:0
                }
                return param;
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
    $("#userid").select2({
        placeholder: "",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "PubComplaintManage/queryPubComplaintsUsers",
            dataType: 'json',
            data: function (term, page) {
                var param = {
                    sSearch: term,
                    key:0
                }
                return param;
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
    $("#jobnum").select2({
        placeholder: "",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "PubComplaintManage/queryPubComplaintsJobNum",
            dataType: 'json',
            data: function (term, page) {
                var param = {
                    sSearch: term,
                    key:0
                }
                return param;
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
    $("#fullplateno").select2({
        placeholder: "",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "PubComplaintManage/queryPubComplaintsFullplateno",
            dataType: 'json',
            data: function (term, page) {
                var param = {
                    sSearch: term,
                    key:0
                }
                return param;
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
    $("#driverid").select2({
        placeholder: "姓名/手机号",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "PubComplaintManage/queryPubComplaintsDrivers",
            dataType: 'json',
            data: function (term, page) {
                var param = {
                    sSearch: term,
                    key:0
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
        url: "PubComplaintManage/queryPubComplaintsTypes",
        dataType: "json",
        success: function (result) {
            $.each(result, function (i, data) {
                $("#type").append('<option value="' + data.id + '">' + data.text + '</option>')
            });
        },
        contentType: "application/json"
    });
}


function reset(){
    $("#type").val("");
    $("#startTime").val("");
    $("#endTime").val("");
    $("#driverid").select2("val", "");
    $("#userid").select2("val", "");
    $("#fullplateno").select2("val", "");
    $("#jobnum").select2("val", "");
    $("#orderno").select2("val", "");
    $("#search").click();
}

function search(grid){
    var userQueryParam = [
        {name: "orderno", value: $("#orderno").val()},
        {name: "userid", value: $("#userid").val()},
        {name: "jobnum", value: $("#jobnum").val()},
        {name: "driverid", value: $("#driverid").val()},
        {name: "fullplateno", value: $("#fullplateno").val()},
        {name: "type", value: $("#type").val()},
        {name: "complainttimestart", value: $("#startTime").val()},
        {name: "complainttimeend", value: $("#endTime").val()},
        {name: "processstatus", value: 0}
    ]
    grid.fnSearch(userQueryParam);
}

function deal(id){
    $("#resultPop").attr("dataid", id);
    $("#processresultPop").val("0");
    $("#processrecordPop").val("");
    $("#resultPop").show();
}

function confirmPop(){
    if($("#processrecordPop").val() == ""){
        $("#processrecordTips").removeClass("hidden");
        return;
    }else{
        $("#processrecordTips").addClass("hidden");
    }

    var data = {
        "processresult" : $("#processresultPop").val(),
        "id" : $("#resultPop").attr("dataid"),
        "processrecord" : $("#processrecordPop").val()
    }

    $.ajax({
        type: 'POST',
        url: "PubComplaintManage/updateProcessRecord",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (data) {
            if(data.status == 0){
                toastr.success("提交成功", "提示");
                $("#search").click();
                closePop();
            }else if(data.status != 0){
                toastr.error(data.message, "提示");
            }
        },
        contentType: "application/json"
    });
}

function closePop(){
    $("#resultPop").hide();
}
