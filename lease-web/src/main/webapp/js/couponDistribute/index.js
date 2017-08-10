var dataGrid;
var validator;
/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
	initSelectCoupon();
	initSelectRule();
	initDataPicker();
	//getSelectCitys();
	//initSelectGetCity();
	$(".close").click(function(){
		$("#editFormDiv").hide();
		refreshForm("editForm")
	})
	
	//添加城市，右上角的删除
	$(".addcbox").on('click','.ico_x_a', function () {
		$(this).parent(".added").remove();
	});
	
	//点击添加城市
	$(".addcitybtn").on('click',function(){
		getSelectCitys(); 
	})
	//选择发放业务时联动发放对象
	$("#sendservicetype").on('change',function(){
		$("#pubCityaddr .kongjian_list").remove();
		$("#sendruletarget").empty();
		var sendservicetype=$(this).val();
		if(sendservicetype=='1'){
			$("#sendruletarget").append('<option value="">请选择</option><option value="2">机构用户</option>');
		}else if(sendservicetype=='2'){
			$("#sendruletarget").append('<option value="">请选择</option><option value="1">机构客户</option><option value="2">机构用户</option>');
		}
	})
	
	//发放对象选择时分别加载各自的发放规则
	$("#sendruletarget").on('change',function(){
		var sendruletarget=$("#sendruletarget").val();
		if(sendruletarget!=""){
		if(sendruletarget=='1'){
			$("#sendfixedmoney,#sendhighmoney,#sendlowmoney").val("");
			$("#sendfixedmoney,#sendhighmoney,#sendlowmoney").attr("maxlength","4");
			$(".organuserDiv").hide();//机构客户
		}else if(sendruletarget=='2'){
			$("#sendfixedmoney,#sendhighmoney,#sendlowmoney").val("");
			$("#sendfixedmoney,#sendhighmoney,#sendlowmoney").attr("maxlength","3");
			$(".organuserDiv").show();//机构用户
		}
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: 'Couponing/getCouponRuleNames',
			data:{"sendruletarget":sendruletarget},
			success: function (result) {
				if (result.ResultSign == "Successful") {
	                  var data=result.data;
	                  $("#sendruleidref").empty();
	                  $("#sendruleidref").append("<option value=''>请选择</option>");
	                  for(var i in data){
	                	  $("#sendruleidref").append("<option value='"+data[i].id+"'>"+data[i].text+"</option>");
	                  }
				} else {
				}	
			}
		});
		}else{
			$(".organuserDiv").hide();//机构客户
			$("#sendruleidref").empty();
		}
	})
	
	//派发金额单选框切换事件
	$("input[name='sendmoneytype']").change(function(){
		if($(this).val()=='1'){
			$("#sendlowmoney").attr("disabled","disabled");
			$("#sendhighmoney").attr("disabled","disabled");
			$("#sendlowmoney").val("")
			$("#sendhighmoney").val("");
			$("#sendfixedmoney").removeAttr("disabled");
		}else{
			$("#sendlowmoney").removeAttr("disabled");
			$("#sendhighmoney").removeAttr("disabled");
			$("#sendfixedmoney").attr("disabled","disabled");
			$("#sendfixedmoney").val("");
		}
	});
	
	//有效期单选框切换事件
	$("input[name='outimetype']").change(function(){
		if($(this).val()=='1'){
			$("#sendtimeinday").removeAttr("disabled");
			$("#fixedstarttime").attr("disabled","disabled");
			$("#fixedendtime").attr("disabled","disabled");
			$("#fixedstarttime").val("");
			$("#fixedendtime").val("");
		}else{
			$("#fixedstarttime").removeAttr("disabled");
			$("#fixedendtime").removeAttr("disabled");
			$("#sendtimeinday").attr("disabled","disabled");
			$("#sendtimeinday").val("");
		}
	});
	
	//派发金额、发放张数、有效期天数只可输入数字
	$("#sendfixedmoney,#sendhighmoney,#sendlowmoney,#sendtimeinday").keyup(function () {
		var c=$(this);  
        if(/[^\d]/.test(c.val())){//替换非数字字符  
          var temp_amount=c.val().replace(/[^\d]/g,'');  
          $(this).val(temp_amount);  
        }  
   }).bind("paste", function () {  //CTR+V事件处理   
	   $(this).val("");
   }).css("ime-mode", "disabled"); //CSS设置输入法不可用  
	//抵扣券名称只能为数字字母汉字
	$("#name").keyup(function () {
		var c=$(this);  
        if(/[^\u4E00-\u9FFF\da-zA-Z]/.test(c.val())){//替换非数字字符  
          var temp_amount=c.val().replace(/[^\u4E00-\u9FFF\da-zA-Z]/g,'');  
          $(this).val(temp_amount);  
        }  
   }).bind("paste", function () {  //CTR+V事件处理   
	   $(this).val("");
   });
});

//点击添加城市后，获取所有业务城市
function getSelectCitys() {
	var parent = document.getElementById("pubCityaddr");
	var city = document.getElementById("city");
	var cityName = document.getElementById("addcboxId");
	var sendservicetype=$("#sendservicetype").val();
	if(sendservicetype==null || sendservicetype=="")
		sendservicetype=3;
	getData1(parent, cityName,null,"Couponing/getBusinessCitys/"+sendservicetype, 30, 0);
}

/**
 * 初始化抵扣券活动名称选择框
 */
function initSelectCoupon(){
	$("#queryname").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "Couponing/getCouponActivityNames",
			dataType : 'json',
			data : function(term, page) {
				return {
					name : term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
	$(".form .select2-search-choice-close").css("display","block");
}

/**
 * 初始化发放规则选择框
 */
function initSelectRule(){
	$("#querysendruleidref").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "Couponing/getAlreadyCouponRuleNames",
			dataType : 'json',
			data : function(term, page) {
				return {
					rulename : term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
	$(".form .select2-search-choice-close").css("display","block");
}
/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "Couponing/QueryCouponActivityByParam",
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无抵用券派发信息"
        },
        columns: [
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 100,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    if(full.activystate==1){
                    html += '<button type="button" class="SSbtn green" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                    html += '&nbsp; <button type="button" class="SSbtn red"  onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 删 除</button>';
                    }else if(full.activystate==2){
                    html += '&nbsp; <button type="button" class="SSbtn grey"  onclick="invalid(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>作废</button>';
                    html += '&nbsp; <button type="button" class="SSbtn blue"  onclick="record(' +"'"+ full.id +"',"+ "'"+full.sendruletarget+"'"+')"><i class="fa fa-times"></i>发放记录</button>';                  
                    }else if(full.activystate==3 || full.activystate==4){
                        html = '&nbsp; <button type="button" class="SSbtn blue"  onclick="record(' +"'"+ full.id +"',"+ "'"+full.sendruletarget+"'"+')"><i class="fa fa-times"></i>发放记录</button>';                  
                    }
                    return html;
                }
            },
            {mDataProp: "name", sTitle: "抵用券名称", sClass: "center", sortable: true},
            {mDataProp: "sendfixedmoney", sTitle: "抵用券金额(元)", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		if(full.sendmoneytype == 1){
	        			return full.sendfixedmoney;
	        		}else{
	        			return full.sendlowmoney + "-" + full.sendhighmoney;
	        		}
	        	}
            },
            {mDataProp: "sendservicetype", sTitle: "发放业务", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		if(full.sendservicetype == 1){
	        			return "出租车";
	        		}else{
	        			return "网约车";
	        		}
	        	}
            },
            {mDataProp: "sendruletarget", sTitle: "发放对象", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		if(full.sendruletarget == 1){
	        			return "机构客户";
	        		}else{
	        			return "机构用户";
	        		}
	        	}
            },
            {mDataProp: "sendruletype", sTitle: "派发类别", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		if(full.sendruletype == 1){
	        			return "注册返券";
	        		}else if(full.sendruletype == 2){
	        			return "充值返券";
	        		}else if(full.sendruletype == 3){
	        			return "消费返券";
	        		}else if(full.sendruletype == 4){
	        			return "活动返券";
	        		}
            	}
            },
            {mDataProp: "activystate", sTitle: "规则状态", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		if(full.activystate == 1){
	        			return "待派发";
	        		}else if(full.activystate == 2){
	        			return "派发中";
	        		}else if(full.activystate == 3){
	        			return "已过期";
	        		}else if(full.activystate == 4){
	        			return "已作废";
	        		}
	        	}
            },
            {mDataProp: "rulename", sTitle: "发放规则", sClass: "center", sortable: true},
            {mDataProp: "citys", sTitle: "发放区域", sClass: "center", sortable: true},
            {mDataProp: "usetype", sTitle: "使用区域", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
            		if(full.sendruletarget == 2){
	        		if(full.usetype == 1){
	        			return "限发放区域内可用";
	        		}else{
	        			return "限开通业务城市可用";
	        		}
            		}else{
            			return "/";       //机构客户没有限制
            		}
	        	}
            },
            {mDataProp: "fixedstarttime", sTitle: "有效期", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		//if(full.outimetype==2)
            		if(full.sendruletarget == 2){
	        			return full.fixedstarttime + "至" + full.fixedendtime;
            		}else{
            			return "/";       //机构客户没有限制
            		}
	        		//else
	        		//	return "/"
	        	}
            },
            {mDataProp: "sendstarttime", sTitle: "发放时间", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		return full.sendstarttime + "至" + full.sendendtime;
	        	}
            },
            {mDataProp: "createtime", sTitle: "创建时间", sClass: "center", sortable: true},
        ]
    };
	dataGrid = renderGrid(gridObj);
}

/**
 * 表单校验
 */
function validateForm() {
	validator=$("#editForm").validate({
		rules: {       
			name: {required: true,maxlength : 10},
			sendservicetype: {required: true},
			sendruletarget: {required: true},
			sendruleidref: {required: true},
			sendfixedmoney: {required: true,digits: true,min:1},
			sendlowmoney: {required: true,digits: true,min:1},
			sendhighmoney: {required: true,digits: true,min:1},
			sendstarttime: {required: true},
			sendendtime: {required: true},
			sendtimeinday: {required: true,maxlength: 3,digits: true,min:1},
			fixedstarttime: {required: true},
			fixedendtime: {required: true}
		},
		messages: {
			name: {required: "请输入抵用券名称",maxlength : 10},//汉字、字母、数字输入校验
			sendservicetype: {required: "请选择发放业务"},
			sendruletarget: {required: "请选择发放对象"},
			sendruleidref: {required: "请选择发放规则"},
			sendfixedmoney: {required: "请输入派发金额",digits: "请输入正整数",min:"请输入大于0的数字"}, //限制输入数字
			sendlowmoney: {required: "请输入派发金额",digits: "请输入正整数",min:"请输入大于0的数字"}, //限制输入数字
			sendhighmoney: {required: "请输入派发金额",digits: "请输入正整数",min:"请输入大于0的数字"}, //限制输入数字
			sendstarttime: {required: "请选择发放时间"},
			sendendtime: {required: "请选择发放时间"},
			sendtimeinday: {required: "请输入有效期",maxlength: "最多只能输入三位整数",digits: "请输入正整数",min:"请输入大于0的数字"}, //限制输入数字
			fixedstarttime: {required: "请输入有效期"},
			fixedendtime: {required: "请输入有效期"}
		}
	})
}

/**
 * 弹出新增窗口
 */
function add() {
	$("#titleForm").html("新建抵用券");
	$("#sendruleidref,#sendruletarget").append("<option value=''>请选择</option>");
	$("#addcboxId + .error").css("display","none");
	$("#scopeDiv").show();
	$("#editFormDiv").show();
	$(".organuserDiv").hide();
	//initDataPicker();       //初始化日期选择空间
}

/**
 * 初始化日期选择空间
 */
function initDataPicker(){
	var date = new Date();
	date.setDate(date.getDate()+1);
	$('#querysendstarttime,#querysendendtime').datetimepicker({
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
	$('#sendstarttime,#sendendtime').datetimepicker({
		format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
	    startDate: date,
	    language: 'zh-CN', //汉化
	    weekStart: 1,
	    autoclose: 1,
	    todayHighlight: 1,
	    startView: 2,
	    minView: 2,
	    forceParse: 0,
	    clearBtn: true
	});
	$('#fixedstarttime,#fixedendtime').datetimepicker({
	    format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
	    startDate: date,
	    language: 'zh-CN', //汉化
	    weekStart: 1,
	    autoclose: 1,
	    todayHighlight: 1,
	    startView: 2,
	    minView: 2,
	    forceParse: 0,
	    clearBtn: true,
	    pickerPosition:'top-right'
	});
}

/**
 * 处理保存时的数据，对于隐藏或者不可用的控件数据清空
 * 并添加城市和用户
 */
function handleData(data){
	if(typeof data != 'undefined' && data != null){
		for(var e in data){
			if(($("input[name="+e+"]").is(":hidden") && $("input[name="+e+"]").attr("type")!="hidden")||$("input[name="+e+"]").attr("disabled"))
				data[e]="";
		}
	}
	var citys="";
	$("#scopeDiv .added").each(function(i){
		var cid=$(this).attr("id");
		if(i==0)
			citys=cid;
		else
		    citys=citys+","+cid;
	})
	//处理有效期,若有效期选择为发放日至N天内
	if(data.outimetype=='1'){
		data.fixedstarttime=data.sendstarttime;
		data.fixedendtime=getFutureDate(data.sendendtime, data.sendtimeinday);
	}
	
	
	data.citys=citys;
}

/**
 * 清空form表单,恢复原状
 */
function refreshForm(formId) {
	//文本框、下拉框、文本域
	var inputs = $("#" + formId + " input");
	var selects= $("#" + formId + " select");
	//清空所有输入框
	inputs.each(function(){
		if($(this).attr("type")=="text" || $(this).attr("type")=="hidden"){
			$(this).val("");
			$(this).removeAttr("disabled");
		}
		if($(this).attr("type")=="radio"){
			$(this).removeAttr("disabled");
		}
	});
	
	selects.each(function(){
		$(this).val("");
		$(this).removeAttr("disabled");
	});
	//默认第一个选中
	$("input[name='sendmoneytype']").eq(0).prop("checked",true);
	$("input[name='usetype']").eq(0).prop("checked",true);
	$("input[name='outimetype']").eq(0).prop("checked",true);
	$("#sendlowmoney,#sendhighmoney,#fixedstarttime,#fixedendtime").attr("disabled","disabled");
	$("#sendruleidref").empty();   //清空发放规则
	$("#sendruletarget").empty();   //清空发放规则
	$(".addcbox").empty();    //清空选择的城市
	$(".organuserDiv").hide();
	// 清除表单校验
	$("#editForm label[class='error']").remove();
	$("#editForm input").removeClass('error');
	$("#addcboxId").after('<label for="addcboxId" class="error">请选择发放区域</label>');
	$("#addcboxId + .error").css("display","none");
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "queryruletype", "value": $("#querysendruletype").val()},
		{ "name": "querysendservicetype", "value": $("#querysendservicetype").val() },
		{ "name": "querysendruletarget", "value": $("#querysendruletarget").val() },
		{ "name": "queryactivystate", "value": $("#queryactivystate").val() },
		{ "name": "queryusetype", "value": $("#queryusetype").val() },
		{ "name": "queryname", "value": $("#queryname").val() },
		{ "name": "querysendruleidref", "value": $("#querysendruleidref").val() },
		{ "name": "querysendstarttime", "value": $("#querysendstarttime").val() },
		{ "name": "querysendendtime", "value": $("#querysendendtime").val() }
	];
	dataGrid.fnSearch(conditionArr);

}
/**
 * 修改
 * @param {} id
 */
function edit(id) {
	$("#titleForm").html("修改抵用券");
	$("#addcboxId + .error").css("display","none");
	$("#editFormDiv").show();
	$.ajax({
		type: "GET",
		url:"Couponing/edit/"+id,
		cache: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var act=status.activity;
				var citys=status.citys;
				$("#id").val(act.id);
				$("#name").val(act.name);
				$("#sendservicetype").val(act.sendservicetype);
				if(act.sendruletarget==1)
					$("#sendruletarget").append("<option value='1'>机构客户</option>");
				else if(act.sendruletarget==2){
					$("#sendruletarget").append("<option value='2'>机构用户</option>");
				}
				//$("#sendruletarget").val(act.sendruletarget);
				$("#sendruleidref").empty();
                $("#sendruleidref").append("<option value='"+act.sendruleidref+"' selected='selected'>"+act.rulename+"</option>");
                $("#name,#sendservicetype,#sendruletarget,#sendruleidref,input[name='usetype']").attr("disabled","disabled");
				if(act.sendruletarget==1){
					$("#sendfixedmoney,#sendhighmoney,#sendlowmoney").attr("maxlength","4");
					$(".organuserDiv").hide();
				}else if(act.sendruletarget==2){
					$("#sendfixedmoney,#sendhighmoney,#sendlowmoney").attr("maxlength","3");
					$(".organuserDiv").show();
				}
                
                //处理城市
                $("#scopeDiv").show();
            	$("#addcboxId").empty();
            	for(var i=0;i<citys.length;i++){
            		var cityhtml='<div class="added" id="'+citys[i].id+'">'+citys[i].city+'<em class="ico_x_a"></em></div>';
        				$("#addcboxId").append(cityhtml);
            	}
            	//派发金额
            	if(act.sendmoneytype==1){
            		$("input[name='sendmoneytype']").eq(0).prop("checked",true);
            		$("#sendfixedmoney").val(act.sendfixedmoney);
            		$("#sendfixedmoney").removeAttr("disabled");
            		$("#sendhighmoney,#sendlowmoney").attr("disabled","disabled");
            	}else{
            		$("input[name='sendmoneytype']").eq(1).prop("checked",true);
            		$("#sendlowmoney").val(act.sendlowmoney);
            		$("#sendhighmoney").val(act.sendhighmoney);
            		$("#sendlowmoney,#sendhighmoney").removeAttr("disabled");
            		$("#sendfixedmoney").attr("disabled","disabled");
            	}
            	//发放时间
            	//initDataPicker();
            	$("#sendstarttime").val(act.sendstarttime);
				$("#sendendtime").val(act.sendendtime);
				//使用区域
				if(act.usetype==1){
					$("input[name='usetype']").eq(0).prop("checked",true);
				}else if(act.usetype==2){
					$("input[name='usetype']").eq(1).prop("checked",true);
				}
				//有效期
				if(act.outimetype==1){
					$("input[name='outimetype']").eq(0).prop("checked",true);
					$("#sendtimeinday").val(act.sendtimeinday);
					$("#fixedstarttime,#fixedendtime").attr("disabled","disabled");
					$("#sendtimeinday").removeAttr("disabled");
				}else if(act.outimetype==2){
					$("input[name='outimetype']").eq(1).prop("checked",true);
					$("#fixedstarttime").val(act.fixedstarttime);
					$("#fixedendtime").val(act.fixedendtime);
					$("#sendtimeinday").attr("disabled","disabled");
					$("#fixedstarttime,#fixedendtime").removeAttr("disabled");
				}			
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		},
		error: function (xhr, status, error) {
			toastr.error("请刷新后重试", "提示");
		}
    });
}

/**
 * 删除一条记录
 * @param id
 */
function del(id){
	var comfirmData={
			tittle:"提示",
			context:"抵用券规则删除后不可恢复，您确定要删除？",
			button_l:"取消",
			button_r:"确认",
			click: "deletePost('" + id + "')",
			htmltex:"<input type='hidden' placeholder='添加的html'> "
		};
		Zconfirm(comfirmData);
	
}
function deletePost(id){
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: 'Couponing/del/'+id,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");
				search();      //重新搜索，刷新结果
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}
/**
 * 作废一条记录
 */
function invalid(id){
	var comfirmData={
			tittle:"提示",
			context:"抵用券规则作废后将会失效，您确定要作废？",
			button_l:"取消",
			button_r:"确认",
			click: "invalidPost('" + id + "')",
			htmltex:"<input type='hidden' placeholder='添加的html'> "
		};
		Zconfirm(comfirmData);
}
function invalidPost(id){
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: 'Couponing/invalid/'+id,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");
				search();      //重新搜索，刷新结果
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}
/**
 * 查看优惠券的发放记录
 */
function record(id,sendruletarget){
	if(sendruletarget=='1')
	window.location.href=base+"Couponing/organRecord?id="+id;
	if(sendruletarget=='2')
	window.location.href=base+"Couponing/organUserRecord?id="+id;
}

/**
 * 保存
 */
function save() {
	var form = $("#editForm");
	if (!form.valid()){
		//如果派发金额选择了随机,出现两个请选择派发金额，删除一个
		if($("input[name='sendmoneytype']:checked").val()=='2'){
			if($("#sendlowmoney").val()=="" && $("#sendhighmoney").val()==""){
				$("#sendhighmoney + .error").remove();
			}
		}
		if($("#addcboxId .added").size()<1){
			$("#addcboxId + .error").css("display","block");
		}else{
			$("#addcboxId + .error").css("display","none");
		}
		$("input[type='text']").each(function() {
			if($(this).is(":disabled") || $(this).is(":hidden")) {
				return true;
			}
			if(null == $(this).val() || "" == $(this).val()) {
				toastr.error("请输入完整信息", "提示");
				return false;
			}
			})		
		return;
	}
	if($("#addcboxId .added").size()<1){
		$("#addcboxId + .error").css("display","block");
		toastr.error("请输入完整信息", "提示");
		return;
	}else{$("#addcboxId + .error").css("display","none");}
    //检测必须项目
	if(!checkInputs()){
		return;
	}
	
	var url = "Couponing/add";
	if($("#id").val()) {
		url = "Couponing/update";
	}
	var data = form.serializeObject();
    //处理数据
	handleData(data);
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");

				$("#editFormDiv").hide();
				refreshForm("editForm");  //还原表单
				search();      //重新搜索，刷新结果
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}

/**
 * 取消(保存与取消)
 */
function canel() {
	$("#editFormDiv").hide();
	refreshForm("editForm");
}

/**
 * 新增的清空功能
 * 
 */
function emptys(){
	$("#querysendruletype").val("");
	$("#querysendservicetype").val("");
	$("#querysendruletarget").val("");
	$("#queryactivystate").val("");
	$("#queryusetype").val("");
	$("#queryname").select2("val","");
	$("#querysendruleidref").select2("val","");
	$("#querysendstarttime").val("");
	$("#querysendendtime").val("");
	search();
}

function checkInputs(){
	    //检测用户信息
		if($("#sendruleidref").val()=='0' && $("#sendruleidref").find("option:selected").text()=="人工发券"){
			if($("#addcboxId2 .added").size()==0){
				toastr.error("请输入完整信息", "提示");
				return false;
			}
		}
		//检测城市信息
		if($("#addcboxId .added").size()==0){
			toastr.error("请输入完整信息", "提示");
			return false;
		}
		//检测随机派发金额
		if($("input[name='sendmoneytype']:checked").val()=='2'){
			if($("#sendlowmoney").val()>=$("#sendhighmoney").val()){
				toastr.error("随机派发金额参数错误，随机上限须大于下限", "提示");
				return false;
			}
		}
		//检测发放开始时间
		if($("#sendstarttime").val()>$("#sendendtime").val()){
			  toastr.error("发放时间的结束时间须大于等于开始时间", "提示");
			  return false;
		}
		//检测有效期
		if($("input[name='outimetype']:checked").val()=='2'){
			if($("#fixedstarttime").val()<$("#sendstarttime").val() || $("#fixedendtime").val()<$("#sendendtime").val()){
				toastr.error("有效期起止时间须大于或等于发放起止时间", "提示");
				  return false;
			}
			if($("#fixedstarttime").val()>$("#fixedendtime").val()){
				toastr.error("有效期截止时间须大于等于有效期开始时间", "提示");
				  return false;
			}
		}
		return true;
}
//获取明天的时间 yyyy-MM-dd
function getCurrentDate() {
    var date = new Date();
    date.setDate(date.getDate()+1);
    var seperator1 = "-";
    //var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

/*$.validator.addMethod("checkLength", function(value, element, param) {
	if($("#sendruletarget").val()==2){
		if(value.length>3){
			return false;
		}
		    return true;
	}
	return true;
}, "最多只能输入三位正整数");*/

//获取明天时间yyyy-MM-dd
function getFutureDate(dateStr,addCount) {
    var date = new Date(dateStr);
    //date.setDate(date.getDate()+addCount);
    date.setTime(date.getTime()+24*60*60*1000*addCount)
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
}