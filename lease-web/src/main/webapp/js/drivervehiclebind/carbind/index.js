var dataGrid;
var orderObj = {
	orderInfo: null
};
var bindingVelDataGrid;

var citySet;
var modelSet;
var leasecompanySet;

/**
 * 页面初始化
 */
$(function () {
	manualOrderdataGrid();
	initForm();
    initselect();
    // initBelongLeasecompany();
});

function initForm() {
    $("#queryDriverId").select2({
        placeholder: "姓名/手机号2",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "PubDriver/listPubDriverBySelect",
            dataType: 'json',
            data: function (term, page) {
                $(".datetimepicker").hide();

                var param = {
                    queryText: term,
                    vehicletype:"0",
                    jobstatus:"0"
                }
                return param;
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });


    //初始化品牌车系
    $("#lineName").select2({
        placeholder : "请选择品牌车系",
        minimumInputLength : 0,
        multiple : false, //控制是否多选
        allowClear : true,
        ajax : {
            url : "PubVehicle/GetBrandCars",
            dataType : 'json',
            data : function(term, page) {
                return {
                    id : term
                };
            },
            results : function(data, page) {
                return {
                    results: data
                };
            }
        }
    });


    $("#queryDriverNum").select2({
        placeholder: "司机资格证号1",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "PubDriver/listPubDriverBySelectJobNum",
            dataType: 'json',
            data: function (term, page) {
                $(".datetimepicker").hide();

                var param = {
                    queryText: term,
                    vehicletype:"0",
                    jobstatus:"0"
                }
                return param;
            },
            results: function (data, page) {
                for(var i=0;i<data.length;i++){
                    data[i].text = data[i].jobnum;
                }
                return { results: data};
            }
        }
    });



    $('.searchDate').datetimepicker({
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        clearBtn: true
    });
}

function initselect(){
    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "driverVehicleBind/listCarBindModelAndCity",
        data: "{}",
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            var message = result.message == null ? result : result.message;
            if (result.status == "0") {
                citySet = new Set();
                modelSet = new Set();
                for (var i = 0; i < result.data.length; i++) {
                    var dataObj = result.data[i];
                    if(dataObj.city!=null &&dataObj.city!='') {
                        citySet.add("<option value='" + dataObj.city + "'>" + dataObj.cityStr + "</option>")
                    }
                    if(dataObj.modelId!=null &&dataObj.modelId!=''){
                        modelSet.add("<option value='" + dataObj.modelId + "'>" + dataObj.vehiclemodelName + "</option>")
                    }
                }
                loadCity(citySet);
                loadModel(modelSet)
            } else {
                $("#notcashdiv").hide()
                closePop(id);
                toastr.error(message, "提示");
            }
        }
    });

}

/**
 * 加载城市下拉框
 */
function loadCity(setObj){
    var cityObj = $("#city");
    cityObj.find("option").remove();
    cityObj.append( "<option value=''>全部</option>" );
    setObj.forEach(function (element, sameElement, set) {
        cityObj.append( element );
    });
}

/**
 * 加载服务车型
 */
function loadModel(setObj){
    var modelObj = $("#modelId");
    modelObj.find("option").remove();
    modelObj.append( "<option value=''>全部</option>" );


    setObj.forEach(function (element, sameElement, set) {
        modelObj.append( element );
    });

}

function initBelongLeasecompany(){
    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "driverVehicleBind/getBelongLeasecompany",
        data: "{}",
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            var message = result.message == null ? result : result.message;
            if (result.status == "0") {
            	leasecompanySet = new Set();
                for (var i = 0; i < result.data.length; i++) {
                    var dataObj = result.data[i];
                    if(dataObj.id!=null &&dataObj.id!='') {
                    	leasecompanySet.add("<option value='" + dataObj.id + "'>" + dataObj.text + "</option>")
                    }
                }
                loadLeasecompany(leasecompanySet);
            }
        }
    });

}

/**
 * 加载归属车企下拉框
 */
function loadLeasecompany(setObj){
    var belongLeasecompanyObj = $("#belongLeasecompany");
    belongLeasecompanyObj.find("option").remove();
    belongLeasecompanyObj.append( "<option value=''>全部</option>" );
    setObj.forEach(function (element, sameElement, set) {
    	belongLeasecompanyObj.append( element );
    });
}

/**
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "grid",
        sAjaxSource: $("#baseUrl").val() + "driverVehicleBind/listCarBindinfo",
        iLeftColumn: 3,
        userQueryParam: [],
        scrollX: true,
        iDisplayLength:6,
        language: {
            sEmptyTable: "暂无服务司机信息"
        },
        columns: [
        	{
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 150,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    if(full.boundstate=='已绑定'){
                        var obj = "{driverId:'"+full.id+"',driverNamePhone:'"+full.driverName+" "+full.phone+"',plateNo:'"+full.platenoStr+"',brandCars:'"+full.vehiclemodelName+"',vehicleId:'"+full.vehicleId+"'}";
                        html += '&nbsp; <button type="button" class="SSbtn green_a"  onclick="unwrapVel('+obj+')"><i class="fa fa-times"></i> 解绑</button>';
                    }else{
                        html += '&nbsp; <button type="button" class="SSbtn red"  onclick="bindingVel(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 绑定</button>';
                    }
                    return html;
                }
            },
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: true },
            {mDataProp: "driverName", sTitle: "姓名", sClass: "center", sortable: true,mRender:function (data) {
                    return "<span style='color: red'>"+data+"</span>"
            } },
            {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
            {mDataProp: "cityStr", sTitle: "登记城市", sClass: "center", sortable: true },
            {mDataProp: "boundstate", sTitle: "绑定状态", sClass: "center", sortable: true },
            {mDataProp: "workstatus", sTitle: "服务状态", sClass: "center", sortable: true,mRender:function (data) {
                if(data == "服务中"){
                    return "<span style='color: red'>"+data+"</span>"
                }
                if(data == "空闲"){
                    return "<span style='color: green'>"+data+"</span>"
                }
                if(data == "未绑定"){
                	return "/";
                }
                return data;

            } },
            {mDataProp: "vehiclemodelName", sTitle: "服务车型", sClass: "center", sortable: true },
            {mDataProp: "vehclineName", sTitle: "品牌车系", sClass: "center", sortable: true },
            {mDataProp: "platenoStr", sTitle: "车牌号", sClass: "center", sortable: true }
            // {mDataProp: "belongLeasecompanyName", sTitle: "归属车企", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}




/**
 * 显示长度限制
 * @param addr
 */
function limitLength(text) {
    if(null != text && text.length > 18) {
        return text.substr(0, 18) + "...";
    }
    return text;
}



$("input:radio[name='shftType']").click(function(){
    if($(this).val()=='1'){
        /**
         * 选择交班司机处理
         */
        $("#plateNoProvince").removeAttr("disabled");
    }else if($(this).val()=='2'){
        /**
         * 选择车辆回收处理
         */
        $("#plateNoProvince").val("");
        $("#plateNoProvince").attr("disabled","disabled")
    }

});


/**
 * 查询
 */
function search() {
    var conditionArr = [
        { "name": "driverId", "value":!!$("#queryDriverId").select2('data')?$("#queryDriverId").select2('data').id:"" },
        { "name": "jobNum", "value": $("#queryDriverNum").select2('data')?$("#queryDriverNum").select2('data').text:"" },
        { "name": "boundState", "value": $("#bindStatus").val() },
        { "name": "city", "value": $("#city").val() },
        { "name": "workStatus", "value": $("#workStatus").val() },
        { "name": "modelId", "value": $("#modelId").val()},
        { "name": "lineName", "value": $("#lineName").select2('data')?$("#lineName").select2('data').text:""},
        { "name": "platenoStr", "value": $("#platenoStr").val()}
        // { "name": "belongLeasecompany", "value": $("#belongLeasecompany").val()}
    ];
    dataGrid.fnSearch(conditionArr);
}


/**
 * 清空
 */
function clearOptions(){
    $("#queryDriverId").select2("val", "");
    $("#queryDriverNum").select2("val", "");
    $("#city option:first").prop("selected", 'selected');
    $("#workStatus option:first").prop("selected", 'selected');
    $("#bindStatus option:first").prop("selected", 'selected');
    $("#modelId option:first").prop("selected", 'selected');
    $("#lineName").select2("val", "");
    $("#platenoStr").val("");
    // $("#belongLeasecompany option:first").prop("selected", 'selected');
    search();
}

//跳转到操作记录页面
function toRecord(){
    window.location="driverVehicleRecord/car/driverRecord";
}
/**解绑相关 start**/

//解绑
function unwrapVel(bindObj){
    $.ajax({
        type: "GET",
        url:"PubDriverVehicleRef/isExistsValidCarOrder",
        cache: false,
        data: { driverId: bindObj.driverId},
        success: function (json) {
            if(json > 0){
                toastr.error("该司机存在未完成订单，请在其完成订单后或将订单更换其他司机后再进行解绑", "提示");
            }else{
                $("#unBindReason").val("");
                $("#unwrapVelId").val(bindObj.driverId);
                $("#driverNamePhone").html(bindObj.driverNamePhone);
                $("#unBindPlateNo").html(bindObj.plateNo);
                $("#brandCars").html(bindObj.brandCars);
                $('#vehicleId').val(bindObj.vehicleId);
                $("#unwrapVel").show();
                unwrapVelForm();
                // 清除验证提示
                var editForm = $("#unwrapVelForm").validate();
                editForm.resetForm();
                editForm.reset();
            }
        }
    });
}

/**
 * 取消
 */
function canel() {
    $("#editFormDiv").hide();
    $("#unwrapVel").hide();
}

/**
 * 表单校验
 */
function unwrapVelForm() {
    $("#unwrapVelForm").validate({
        rules: {
            unBindReason: {required: true, maxlength: 200}
        },
        messages: {
            unBindReason: {required: "请输入解绑原因",maxlength: "最大长度不能超过200个字符"}
        }
    })
}


function unwrapVelSave(){
    var form = $("#unwrapVelForm");
    if(!form.valid()) return;
    var url = "PubDriverVehicleRef/carUnBin";
    var id =$("#unwrapVelId").val();
    var vehicleId = $('#vehicleId').val();
    var unBindReason = $("#unBindReason").val();
    var data = {
        driverId : id,
        vehicleId:vehicleId,
        unBindStr : unBindReason
    }

    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: url,
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (result) {
            if (result.status == "0") {
                var message = result.message == null ? result : result.message;
                toastr.success(message, "提示");
                $("#unwrapVel").hide();
                dataGrid._fnReDraw();
                initselect();
            } else {
                var message = result.message == null ? result : result.message;
                toastr.error(message, "提示");
            }
        }
    });
}

/**解绑相关 end**/





/***绑定功能相关 start****/

//绑定
function bindingVel(id){
    $.ajax({
        type: "GET",
        url:"PubDriver/GetById",
        cache: false,
        data: { id: id },
        success: function (json) {
            $("#driid").val(json.id);
            $("#cityId").val(json.city);
            $("#leasecompanyId").val(json.belongleasecompany);
            $("#bindingVelTitleForm").html("绑定车辆 【"+json.name+"】");
            $("#queryBrandCars").select2("data", {
                id : "",
                text : ""
            });
            $("#queryPlateNo").val("");
            $("#bindingVel").show();

            initSelectQueryBrandCars();

            if(!bindingVelDataGrid){
                bindingVelInitGrid();
            }else{
                query();
            }
        }
    });

}

/**
 * 初始化查询司机table
 */
function initSelectQueryBrandCars() {
    var cityId = $("#cityId").val();

    $("#queryBrandCars").select2({
        placeholder : "",
        minimumInputLength : 0,
        multiple : false, //控制是否多选
        allowClear : true,
        ajax : {
            url : "PubVehicle/GetBrandCars",
            dataType : 'json',
            data : function(term, page) {
                return {
                    id : term
                };
            },
            results : function(data, page) {
                return {
                    results: data
                };
            }
        }
    });
}
/**
 * 查询
 */
function query() {
    var conditionArr = [
        { "name": "platenoStr", "value": $("#queryPlateNo").val()},
        { "name": "lineName", "value": $("#queryBrandCars").select2('data')?$("#queryBrandCars").select2('data').text:''},
        { "name": "qCity", "value": $("#cityId").val()}
        // { "name": "qBelongLeasecompany", "value": $("#leasecompanyId").val()}

     ];
    bindingVelDataGrid.fnSearch(conditionArr);
}

/**
 * 清空
 */
function clearbinding(){
    $("#queryPlateNo").val("");
    $("#queryBrandCars").select2("val", "");

    query();
}

/**
 * 添加绑定
 * @param velid
 */
function bindingVelAdd(velid){
    var id = $("#driid").val();
    var url = "PubDriverVehicleRef/carBin";
    var data = {
        vehicleid : velid,
        driverID : id
    }
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: url,
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (result) {
            if (result.status == "0") {
                var message = result.message == null ? result : result.message;
                toastr.success(message, "提示");
                $("#bindingVel").hide();
                dataGrid._fnReDraw();
                initselect();
            } else {
                var message = result.message == null ? result : result.message;
                toastr.error(message, "提示");
                
                $.ajax({
                    type: "GET",
                    url:"PubDriver/GetById",
                    cache: false,
                    data: { id: id },
                    success: function (json) {
                        $("#cityId").val(json.city);
                        $("#leasecompanyId").val(json.belongleasecompany);
                        query();
                    }
                });
            }
        }
    });
}

/**
 * 表格初始化
 */
var gridObj;
function bindingVelInitGrid() {
    var cityId = $("#cityId").val();
    var belongleasecompany = $("#leasecompanyId").val();
    gridObj = {
        id: "bindingVelDataGrid",
        sAjaxSource: "driverVehicleBind/listUnBindVehicle",
        userQueryParam: [{name: "city", value: cityId}
            // ,{name: "belongLeasecompany", value: belongleasecompany}
            ],
        //iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        iDisplayLength:6,
        columns: [
            {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
            {mDataProp: "rownum", sTitle: "序号", sClass: "center", sortable: true },
            {mDataProp: "platenoStr", sTitle: "车牌号", sClass: "center", sortable: true },
            {mDataProp: "vehclineName", sTitle: "品牌车系", sClass: "center", sortable: true },
            {mDataProp: "vehiclemodelName", sTitle: "服务车型", sClass: "center", sortable: true },
            {mDataProp: "color", sTitle: "颜色", sClass: "center", sortable: true },
            {mDataProp: "cityStr", sTitle: "登记城市", sClass: "center", sortable: true },
            {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 60,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '&nbsp; <button type="button" class="SSbtn orange"  onclick="bindingVelAdd(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 绑定</button>';
                    return html;
                }
            }
        ]
    };

    bindingVelDataGrid = renderGrid(gridObj);
    //清空初始化参数
    //gridObj.userQueryParam="";
}

/**
 * 取消
 */
function canelBind() {
    $(".select2-drop.select2-display-none.select2-with-searchbox.select2-drop-active").css("display","none");
}

/*******绑定功能相关 end*********/


