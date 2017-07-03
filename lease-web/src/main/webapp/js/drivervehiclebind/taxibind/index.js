var dataGrid;
var orderObj = {
	orderInfo: null
};
var bindingVelDataGrid;
var unBindingVelDataGrid;
/**
 * 页面初始化
 */
$(function () {
	manualOrderdataGrid();
	initForm();
});


function number()
{
    var char = String.fromCharCode(event.keyCode)
    var re = /[0-9]/g
    event.returnValue = char.match(re) != null ? true : false
}

function filterInput()
{
    if (event.type.indexOf("key") != -1)
    {
        var re = /37|38|39|40/g
        if (event.keyCode.toString().match(re)) return false
    }
    event.srcElement.value = event.srcElement.value.replace(/[^0-9]/g, "")
}

function filterPaste()
{
    var oTR = this.document.selection.createRange()
    var text = window.clipboardData.getData("text")
    oTR.text = text.replace(/[^0-9]/g, "")
}


function initForm() {

    //初始化司机
    $("#driverId").select2({
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
                    vehicletype:'1',
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


    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "driverVehicleBind/listTaxiVehicleRefCity",
        data: "{}",
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            var message = result.message == null ? result : result.message;
            if (result.status == "0") {
                loadCity(result.data);
            } else {
                $("#notcashdiv").hide()
                closePop(id);
                toastr.error(message, "提示");
            }
        }
    });
    
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
                loadLeasecompany(result.data);
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

/**
 * 加载城市下拉框
 */
function loadCity(data){
    var cityObj = $("#city");
    cityObj.find("option").remove();
    cityObj.append( "<option value=''>全部</option>" );
    data.forEach(function (element, sameElement, set) {
        cityObj.append( "<option value='" + element.id + "'>" + element.city + "</option>" );
    });
}

/**
 * 加载归属车企下拉框
 */
function loadLeasecompany(data){
    var belongLeasecompanyObj = $("#belongLeasecompany");
    belongLeasecompanyObj.find("option").remove();
    belongLeasecompanyObj.append( "<option value=''>全部</option>" );
    data.forEach(function (element, sameElement, set) {
    	belongLeasecompanyObj.append(  "<option value='" + element.id + "'>" + element.text + "</option>"  );
    });
}

/**
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "grid",
        sAjaxSource: $("#baseUrl").val() + "driverVehicleBind/listTaxiVehicleRef",
        iLeftColumn: 3,
        //userQueryParam: [],
        scrollX: true,
        iDisplayLength:6,
        language: {
            sEmptyTable: "暂无服务车辆信息"
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
                    var html = "<div style='text-align: left'>";

                    var obj = "{name:'"+full.platenoStr+" "+full.cityStr+"',id:'"+full.id+"',city:'"+full.city+"',platenoStr:'"+full.platenoStr+"',belongleasecompany:'"+full.belongleasecompany+"'}";
                    if(full.vehiclestatus=="营运中"){

                        html += '&nbsp; <button type="button" class="SSbtn red"  onclick="bindingVel('+obj+')"><i class="fa fa-times"></i> 绑定</button>';
                    }
                    if(full.boundstate=="已绑定"){
                        html += '&nbsp; <button type="button" class="SSbtn green_a"  onclick="unwrapVel('+obj+')"><i class="fa fa-times"></i> 解绑</button>';
                    }
                    html += '&nbsp; <button type="button" class="SSbtn blue"  onclick="toRecord('+obj+')"><i class="fa fa-times"></i> 操作记录</button>';
                    if(full.vehiclestatus=="营运中" && full.boundstate=="已绑定" && full.driverid==""){
                        html += '&nbsp; <button type="button" class="SSbtn purple"  onclick="shownotcashdiv(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 指派当班</button>';
                    }
                    html+="</div>";
                    return html;
                }
            },
            {mDataProp: "platenoStr", sTitle: "车牌号", sClass: "center", sortable: true },
            {mDataProp: "vehclineName", sTitle: "品牌车系", sClass: "center", sortable: true },
            {mDataProp: "color", sTitle: "颜色", sClass: "center", sortable: true },
            {mDataProp: "cityStr", sTitle: "登记城市", sClass: "center", sortable: true },
            {mDataProp: "belongLeasecompanyName", sTitle: "归属车企", sClass: "center", sortable: true },
            {mDataProp: "boundstate", sTitle: "绑定状态", sClass: "center", sortable: true },
            {mDataProp: "vehiclestatus", sTitle: "营运状态", sClass: "center", sortable: true},
            {mDataProp: "driverid", sTitle: "排班状态", sClass: "center", sortable: true ,
                mRender:function (data) {
                    if(data=="/"){
                        return "/";
                    }
                if(data!=null && data!=''){
                    return "已排班";
                }
                return "未排班";
            } },
            {mDataProp: "workstatus", sTitle: "服务状态", sClass: "center", sortable: true},
            {mDataProp: "driverInfo", sTitle: "当班司机", sClass: "center", sortable: true },
            {mDataProp: "bindpersonnum", sTitle: "绑定人数", sClass: "center", sortable: true ,mRender:function (data) {
                if(data<1){
                    return "/";
                }
                return data;
            } },
            {mDataProp: "bindDriverInfos", sTitle: "绑定司机信息", sClass: "center", sortable: true,
                mRender:function (data) {
                    if (data != null&& data!="/") {
                        var index = findIndex(data,"、",3);
                        if (index >= 0) {
                            return showToolTips(data,index);
                        } else {
                            return data;
                        }
                    } else {
                        return "/";
                    }
                }}
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}



/**
 * 长度显示控制
 */
function findIndex(str,cha,num) {
    var x=str.indexOf(cha);
    for(var i=0;i<num;i++) {
        if (x == -1) {
            return x;
        }
        x=str.indexOf(cha,x+1);
    }
    return x;
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

    //绑定人数
    if($("#bindCount").val()!=''){
        var re =  /^[1-9][0-9]*$/;;
        if (!re.test($("#bindCount").val())){
            toastr.error("请输入正确的绑定人数", "提示");
            return false;
        }
    }

    var conditionArr = [
        { "name": "lineName", "value":!!$("#lineName").select2('data')?$("#lineName").select2('data').text:"" },
        { "name": "platenoStr", "value": $("#platenoStr").val() },
        { "name": "boundState", "value": $("#boundState").val() },
        { "name": "workStatus", "value": $("#workStatus").val() },
        { "name": "city", "value": $("#city").val() },
        { "name": "bindCount", "value": $("#bindCount").val() },
        { "name": "driverId", "value": $("#driverId").select2('data')?$("#driverId").select2('data').id:"" },
        { "name": "online", "value": $("#online").val() },
        { "name": "vehiclestatus", "value": $("#vehiclestatus").val() },
        { "name": "belongLeasecompany", "value": $("#belongLeasecompany").val() }
    ];
    dataGrid.fnSearch(conditionArr);
}


/**
 * 清空
 */
function clearOptions(){
    $("#lineName").select2("val", "");
    $("#platenoStr").val("");
    $("#boundState option:first").prop("selected", 'selected');
    $("#workStatus option:first").prop("selected", 'selected');
    $("#city option:first").prop("selected", 'selected');
    $("#bindCount").val("");
    $("#driverId").select2("val", "");
    $("#online").val("");
    $("#vehiclestatus option:first").prop("selected", 'selected');
    $("#belongLeasecompany option:first").prop("selected", 'selected');
    dataGrid.fnSearch([]);
}
//跳转到操作记录页面
function toRecord(bindObj){
    window.location="driverVehicleRecord/taxi/vehicleRecord/"+bindObj.id+"/"+bindObj.platenoStr;
}


/**解绑相关 start**/

//解绑
function unwrapVel(bindObj){
    $("#unwrapVelTitleForm").html("解绑司机 <font color='orange'>【"+bindObj.name+"】</font>");

    $("#unBindReason").val("");
    $('#vehicleId').val(bindObj.id);
    $("#checkAllManual").prop("checked",false);
    $("#unwrapVel").show();

    unwrapVelForm();

    if(!unBindingVelDataGrid){
        //初始化查询司机
        unBindingVelInitGrid(bindObj);
    } else{
        queryBindDriver(bindObj);
    }
    // queryBindDriver(bindObj);
    // 清除验证提示
    var editForm = $("#unwrapVelForm").validate();
    editForm.resetForm();
    editForm.reset();

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
            unBindReason: {required: true, maxlength: 100}
        },
        messages: {
            unBindReason: {required: "请输入解绑原因",maxlength: "最大长度不能超过100个字符"}
        }
    })
}
/**
 * 确定解绑
 */
function unwrapVelSave(){
    var form = $("#unwrapVelForm");
    if(!form.valid()) return;
    var url = "PubDriverVehicleRef/taxiUnBin";
    var vehicleId = $('#vehicleId').val();
    var unBindReason = $("#unBindReason").val();
    var driverArray = new Array();
    var a = document.getElementsByName("checkOrderManual");
    for(var i = 0;i < a.length; i++) {
        if(a[i].checked == true) {
            driverArray.push(a[i].value);
        }
    }

    if(driverArray.length<1){
        toastr.error("请选择要解绑的司机", "提示");
        return;
    }


    var data = {
        taxiDrivers : driverArray,
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
            } else {
                var message = result.message == null ? result : result.message;
                toastr.error(message, "提示");
                unBindingVelDataGrid._fnReDraw();
            }
        }
    });
}

var gridObj;
/**
 * 表格初始化
 */
function unBindingVelInitGrid(parmObj) {
    gridObj = {
        id: "unBindingVelDataGrid",
        sAjaxSource: $("#baseUrl").val() +"driverVehicleBind/listAllVehicleBindInfo",
        userQueryParam: [ { "name": "vehicleId", "value": parmObj.id}],
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollY: true,//（加入纵向滚动条）
        columns: [
            {
                // 自定义操作列
                "mDataProp" : "ZDY",
                "sClass" : "center",
                "sTitle" : "<input type='checkbox' id='checkAllManual' name='checkAllManual' onclick='checkAllManualHander(this)'></input>全选",
                "sWidth" : 70,
                "bSearchable" : false,
                "sortable" : false,
                "mRender" : function(data, type, full) {
                    var html = "";
                    html += '<input type="checkbox" id="checkOrderManual" name="checkOrderManual" value="'
                        + full.driverID + '"></input>';
                    return html;
                }
            },
            {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: true },
            {mDataProp: "sex", sTitle: "性别", sClass: "center", sortable: true ,mRender:function (data, type, full) {
                if(data=='0'){
                    return "男";
                }
                    return "女";

            }},
            {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
            {mDataProp: "passworkstatus", sTitle: "班次状态", sClass: "center", sortable: true,mRender:function (data, type, full) {
                //只显示当班跟歇班
                if(data=='当班'||data=='歇班'){
                    return data;
                }
                return "/";

            } },
            {mDataProp: "cityStr", sTitle: "登记城市", sClass: "center", sortable: true }
        ]
    };

    unBindingVelDataGrid = renderGrid(gridObj);
    //清空初始化参数
   // ;
}


function checkAllManualHander(obj) {
    var a = document.getElementsByTagName("input");
    if (obj.checked) {
        for(var i = 0;i < a.length; i++) {
            if(a[i].name == "checkOrderManual" && a[i].type == "checkbox" && a[i].checked == false) {
                a[i].checked = true;
            }
        }
    } else {
        for(var i = 0;i < a.length; i++) {
            if(a[i].name == "checkOrderManual" && a[i].type == "checkbox" && a[i].checked == true) {
                a[i].checked = false;
            }
        }
    }
}



function queryBindDriver(parmObj){
    gridObj.userQueryParam="";
    var conditionArr = [

        { "name": "vehicleId", "value": parmObj.id}

    ];
    unBindingVelDataGrid.fnSearch(conditionArr);
}

/**解绑相关 end**/






/***绑定功能相关 start****/

//绑定
function bindingVel(json){

    $("#vehcile").val(json.id);
    //$("#cityId").val(json.city);
    //$("#leasecompanyId").val(json.belongleasecompany);
    
    $("#queryDriver").select2("val","");
    $("#queryJobnum").select2("val","");
    
    $.ajax({
        type: "GET",
        url:"PubVehicle/GetById",
        cache: false,
        data: { id: json.id },
        success: function (vehcilejson) {
            $("#cityId").val(vehcilejson.city);
            $("#leasecompanyId").val(vehcilejson.belongleasecompany);
            
            //渲染显示标题
            $("#bindingVelTitleForm").html("绑定司机 <font color='orange'>【"+json.name+"】</font>");

            $("#queryDriver").select2({
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
                            jobstatus:"0",
                            city:vehcilejson.city,
                            boundstate:'0',
                            vehicletype:'1',
                            belongleasecompany:vehcilejson.belongleasecompany
                        }
                        return param;
                    },
                    results: function (data, page) {
                        return { results: data };
                    }
                }
            });

            $("#queryJobnum").select2({
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
                            jobstatus:"0",
                            city:vehcilejson.city,
                            boundstate:'0',
                            vehicletype:'1',
                            belongleasecompany:vehcilejson.belongleasecompany
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

            $("#bindingVel").show();

            if(!bindingVelDataGrid){
                bindingVelInitGrid();
            }else{
                query();
            }

            //初始化查询
            initBindedDriver(json.id);
        }
    });

    
}

//初始化绑定司机信息
function initBindedDriver(vehicleId){

    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: $("#baseUrl").val() +"/driverVehicleBind/listTaxiBindDriver/"+vehicleId,
        async: false,
        success: function (result) {
            var data = result.data;
            var htmltext="";
            for(var i=0;i<data.length;i++){
                htmltext+=data[i].name+"&nbsp;"+data[i].phone;
                if(i<data.length-1){
                    htmltext+="、";
                }
            }
            $("#bindedArea").html(htmltext);
        }
    });

}


/**
 * 查询
 */
function query() {
    var drivreId = $("#queryDriver").select2('data') ? $("#queryDriver").select2('data').id : '';
    var jobNum = $("#queryJobnum").select2('data') ? $("#queryJobnum").select2('data').text : '';
    //如果都为空，则返回空结果集
    if (drivreId == '' && jobNum == '') {
        var conditionArr = [
            { "name": "queryStatus", "value": "1"}
        ];
        bindingVelDataGrid.fnSearch(conditionArr);
    } else {
        var conditionArr = [
            {"name": "driverId", "value": drivreId},
            {"name": "jobNum", "value": jobNum},
            {"name": "queryCity", "value": $("#cityId").val()},
            {"name": "belongleasecompanyQuery", "value": $("#leasecompanyId").val()}
        ];
        bindingVelDataGrid.fnSearch(conditionArr);
    }
}


/**
 * 添加绑定
 * @param velid
 */
function bindingVelAdd(driverId){
    var vehicleId = $("#vehcile").val();
    var url = "PubDriverVehicleRef/taxiBin";

    var data = {
        vehicleid : vehicleId,
        driverID : driverId
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
                dataGrid._fnReDraw();
                isAssign(vehicleId);
                query();
            } else {
                var message = result.message == null ? result : result.message;
                toastr.error(message, "提示");
                $.ajax({
                    type: "GET",
                    url:"PubVehicle/GetById",
                    cache: false,
                    data: { id: vehicleId },
                    success: function (vehcilejson) {
                        $("#cityId").val(vehcilejson.city);
                        $("#leasecompanyId").val(vehcilejson.belongleasecompany);
                        $("#queryDriver").select2("val","");
                        $("#queryJobnum").select2("val","");
                        reselect2(vehcilejson);
                        query();
                    }
                });
            }
            initBindedDriver(vehicleId);
        }
    });
}

function reselect2(vehcilejson) {
	$("#queryDriver").select2({
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
                    jobstatus:"0",
                    city:vehcilejson.city,
                    boundstate:'0',
                    vehicletype:'1',
                    belongleasecompany:vehcilejson.belongleasecompany
                }
                return param;
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });

    $("#queryJobnum").select2({
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
                    jobstatus:"0",
                    city:vehcilejson.city,
                    boundstate:'0',
                    vehicletype:'1',
                    belongleasecompany:vehcilejson.belongleasecompany
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
}

function isAssign(vehicleId){
    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "driverShift/isAssign/"+vehicleId,
        // data:  { "name": "vehicleId", "value": id},
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            if (result.status == "0") {
              if(result.data){
                  shownotcashdiv(vehicleId);
              }
            }
        }
    });
}

/**
 * 表格初始化
 */
function bindingVelInitGrid() {

    var cityId = $("#cityId").val();
    var gridObj = {
        id: "bindingVelDataGrid",
        sAjaxSource: "PubDriver/GetPubDriverByQuery",
        userQueryParam: [{"name":"queryStatus","value":"1"}],
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        iDisplayLength:6,
        language: {
        sEmptyTable: "暂无可绑定服务司机信息"
        },
        columns: [
            {mDataProp: "jobNum", sTitle: "资格证号", sClass: "center", sortable: true },
            {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: true },
            // {mDataProp: "sex", sTitle: "性别", sClass: "center", sortable: true },
            {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
            {mDataProp: "boundState", sTitle: "绑定状态", sClass: "center", sortable: true,mRender:function (data) {
                if(data=='0'){
                    return "未绑定";
                }else if(data=='1'){
                    return "已绑定";
                }
                return "/"
            } },
            {mDataProp: "cityName", sTitle: "登记城市", sClass: "center", sortable: true },
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
                    if(full.boundState=="0"){
                        html += '&nbsp; <button type="button" class="SSbtn orange"  onclick="bindingVelAdd(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 绑定</button>';
                    }
                    return html;
                }
            }
        ]
    };

    bindingVelDataGrid = renderGrid(gridObj);
    //清空初始化参数
    gridObj.userQueryParam="";
}


/**
 * 取消
 */
function canelBind() {
    $(".select2-drop.select2-display-none.select2-with-searchbox.select2-drop-active").css("display","none");
}
/*******绑定功能相关 end*********/




/**********指定交接班 start***************/


/**
 * 弹出选择接班司机框
 * @param id
 */
function shownotcashdiv(vehicleid){
    $('#vehcile').val(vehicleid);
    $("#notcashdiv").show();
    var selObj = $("#plateNoProvince");

    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "driverVehicleBind/listAssignDriver/"+vehicleid,
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            var message = result.message == null ? result : result.message;
            if (result.status == "0") {
                selObj.find("option").remove();
                selObj.append( "<option value=''>请选择</option>" );
                for(var i=0;i<result.data.length;i++){
                    var dataObj = result.data[i];
                    selObj.append( "<option value='"+dataObj.driverID+"'>"+dataObj.text+"</option>" );
                }

                if(result.data.length==1){
                    selObj.attr("disabled","disabled");
                    selObj.val(result.data[0].driverID);
                }else{
                    selObj.removeAttr("disabled");
                }

            } else {
                $("#notcashdiv").hide()
                toastr.error(message, "提示");
            }
        }
    });
}


/**
 * 交接班操作
 * @param driverId
 * @param pendingId
 */
function processed(){
    var vehicleId = $("#vehcile").val();
    var driverId = $("#plateNoProvince").val();
    var driverStr = $("#plateNoProvince").find("option:selected").text();
    //初始化参数
    var data = {vehicleId: vehicleId,relieveddriverid:driverId,relieveddriverInfo:driverStr};

    if(!driverId){
        toastr.warning("请选择当班司机", "提示");
        return ;
    }

    Zconfirm("确定指定当班司机？",function(){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "driverShift/saveAssign",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (result) {
                var message = result.message == null ? result : result.message;
                if (result.status == "0") {

                    toastr.success(message, "提示");

                } else {
                    toastr.error(message, "提示");
                }
                $("#notcashdiv").hide();
                search();
            }
        });
    });

}

/**
 * 不予提现取消
 */
function cancelNotDiv(){
    $("#notcashdiv").hide();
}


/**********指定交接班 end***************/
