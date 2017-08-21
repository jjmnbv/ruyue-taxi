var dataGrid;
var validator;
var isOut=true;
/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
	initSelectCoupon();
	initSelectRule();
	initDataPicker();
	 
	//关闭新增抵扣券弹框
	$(".close").click(function(){
		$("#editFormDiv").hide();
		refreshForm("editForm");
		 $("body").off('click');
		 initSelectCoupon();
		 initSelectRule();
	})
	
	//滚动条滚动式，由于用户选择和城市选择控件是绝对定位，需要隐藏
	$("#editForm").on('scroll',function(){
		//$("#editForm .select2-container-multi").css("display","none");
		   $(".content ~ .select2-drop").remove();
 	       $(".content ~ #select2-drop-mask").remove();
	       $("#selectuser #s2id_users").remove(); 
		$("#pubCityaddr .kongjian_list").css("display","none");
	})
	
	//添加城市，添加用户，右上角的删除
	$(".addcbox").on('click','.ico_x_a', function () {
		$(this).parent(".added").remove();
	});
	
	//点击添加用户
	$(".adduserbtn").on('click',function(event){
		var top = $('.adduserbtn').offset().top;
		var left = $('.adduserbtn').offset().left;
		//$(".adduserbtn").css("top",top+27+"px");
		//$(".adduserbtn").css("left",left+10+"px");
		$("#users").css("display","block");
		$("#users").css("position","absolute");
		$("#users").css("top",top+25+"px");
		$("#users").css("left",left-5+"px");
		if($("#editForm .select2-container-multi").size()==0){
		    getSelectUsers();
		}else{
			//$("#editForm #s2id_users").css("display","block");
			//$("#users").css("display","block");
			//$("#users").select2('destroy');
			if($("#selectuser #s2id_users").size()>0){
				   $(".content ~ .select2-drop").remove();
		    	   $(".content ~ #select2-drop-mask").remove();
		  	       $("#selectuser #s2id_users").remove(); 
			}
			getSelectUsers();
		}
	})
	
	//点击添加城市
	$(".addcitybtn").on('click',function(){
		var top = $('.addcitybtn').offset().top;
		var left = $('.addcitybtn').offset().left;
		$("#pubCityaddr .kongjian_list").css("top",top+25+"px");
		$("#pubCityaddr .kongjian_list").css("left",left+"px");
		getSelectCitys(); 
	})
	//发放业务改变时，需要重新加载选择城市控件
	$("#sendservicetype").on('change',function(){
		$("#pubCityaddr .con").off('click');
		$("#pubCityaddr .kongjian_list").remove();
	})
	
	
	//发放规则 选中人工派发时
	$("#sendruleidref").on('change',function(){
		if($(this).val()=='0' && $(this).find("option:selected").text()=="人工发券"){
			$("#userDiv").show();
		}else{
			$("#userDiv").hide();
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
	$("#sendfixedmoney,#sendhighmoney,#sendlowmoney,#sendcount,#sendtimeinday").keyup(function () {
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
   });//.css("ime-mode", "disabled"); //CSS设置输入法不可用  
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



//点击添加用户后，联想搜索用户
function getSelectUsers(){
    $("#users").select2({
    	placeholder : "",
		minimumInputLength : 1,
		maximumInputLength : 11,
		multiple : false, //控制是否多选
		closeOnSelect: false,
		allowClear : true,
	ajax : {
		url : "Couponing/getPeUsers",
		dataType : 'json',
		data : function(term, page) {
			return {
				account : term
			};
		},
		results : function(data, page) {
			return {
				results: data
			};
		}
	},
	formatResult:formatState,
	formatNoMatches:noResult,
	formatInputTooShort:noInput,
}).select2('open');
    
    
    
    $("#users").on("select2-selected", function (e) {
    	//e.stopPropagation();
    	$("#users").select2("val","");
    	var uid=e.choice.id;
        var phone=e.choice.text;
        if(!$(".content ~ #select2-drop").find("#"+phone).prop("checked")){
        var divs = document.getElementById("addcboxId2").childNodes;
		for(var i=0;i<divs.length;i++){
			if(divs[i].id == uid){
				//toastr.error("用户已经存在，请重新选择", "提示");
				return;
			}
		}
		
		var userhtml='<div class="added" id="'+uid+'">'+phone+'<em class="ico_x_a"></em></div>';
		var tempStr = $("#addcboxId2").text();
		var bool = tempStr.indexOf(phone);
		if(bool<1){
			$("#addcboxId2").append(userhtml);
			$("#select2-drop").find("#"+phone).prop("checked",true);
		}
        }
    	$(".adduserbtn").trigger('click'); 
    });
}

function formatState (state) {
	var $state="";
    if (!state.id) { return state.text; }//未找到结果时直接跳出函数
    var divs = document.getElementById("addcboxId2").childNodes;
	for(var i=0;i<divs.length;i++){
		if(divs[i].id == state.id){
		    $state = $('<span style="cursor:not-allowed">' + state.text + '<input type="checkbox" style="margin-left:80px;" checked="checked" id="'+state.text+'" value="'+state.id+'"/></span>'); 
		}
	}
	if($state==""){
			$state = $('<span>' + state.text + '<input type="checkbox" style="margin-left:80px;" id="'+state.text+'" value="'+state.id+'"/></span>');//将API返回的结果转换为模板
	}
    return $state;
}

function noResult(e){
	if(e!=""){
	if(/^(((13[0-9]{1})|(15[0-3,5-9])|(18[0-9]{1})|(14[5,7,9])|(17[0,1,3,5-8]))+\d{0,8})$/.test(e)){
	    return "<span style='color:red'>暂无搜索结果</span>";
	}else{
		return "<span style='color:red'>请输入正确的手机号码</span>";
	}
	}else{
		return "请输入手机号码";
	}
}

function noInput(term, minLength){
	$(".content ~ #select2-drop .select2-input").attr('placeholder','请输入电话号码');
	return "";
}
/**
 * 初始化抵扣券活动名称选择框
 */
function initSelectCoupon(){
	 $(".content ~ .select2-drop").remove();
     $(".content ~ #select2-drop-mask").remove();
     $("#s2id_queryname").remove(); 
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
	$(".select2-results").css('height','auto');
	$(".form .select2-search-choice-close").css("display","block");
}

/**
 * 初始化发放规则选择框
 */
function initSelectRule(){
	 $(".content ~ .select2-drop").remove();
     $(".content ~ #select2-drop-mask").remove();
     $("#s2id_querysendruleidref").remove(); 
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
	$(".select2-results").css('height','auto');
	$(".form .select2-search-choice-close").css("display","block");
}
/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "Couponing/GetPubCouponActivityByQuery",
        iLeftColumn: 1,//（固定表头，1代表固定几列）
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
                    html += '&nbsp; <button type="button" class="SSbtn blue"  onclick="record(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>发放记录</button>';                  
                    }else if(full.activystate==3 || full.activystate==4){
                        html = '&nbsp; <button type="button" class="SSbtn blue"  onclick="record(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>发放记录</button>';                  
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
	        		}else {
	        			return "/";
	        		}
	        		/*else if(full.sendruletype == 5){
	        			return "邀请返券";
	        		}else if(full.sendruletype == 6){
	        			return "人工发券";
	        	    }*/
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
            {mDataProp: "rulename", sTitle: "发放规则", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
            		if(full.sendruletype == 6){
	        			return "人工发券";
	        	    }else{
	        	    	return full.rulename;
	        	    }
            	}	
            },
            {mDataProp: "citys", sTitle: "发放区域", sClass: "center", sortable: true},
            {mDataProp: "usetype", sTitle: "使用区域", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		if(full.usetype == 1){
	        			return "限发放区域内可用";
	        		}else{
	        			return "限开通业务城市可用";
	        		}
	        	}
            },
            {mDataProp: "fixedstarttime", sTitle: "有效期", sClass: "center", sortable: true,
            	mRender: function (data, type, full) {
	        		//if(full.outimetype==2)
	        			return full.fixedstarttime + "至" + full.fixedendtime;
	        		//else if(full.outimetype==1)
	        			//return "【发放开始时间】至【发放结束时间+"+full.sendtimeinday+"】"
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
			sendcount: {required: true,maxlength: 2,digits: true,min:1},
			sendfixedmoney: {required: true,maxlength: 3,digits: true,min:1},
			sendlowmoney: {required: true,maxlength: 3,digits: true,min:1},
			sendhighmoney: {required: true,maxlength: 3,digits: true,min:1},
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
			sendcount: {required: "请输入发放张数",maxlength: "最多只能输入两位整数",digits: "请输入正整数",min:"请输入大于0的数字"}, //限制输入数字
			sendfixedmoney: {required: "请输入派发金额",maxlength: "最多只能输入三位整数",digits: "请输入正整数",min:"请输入大于0的数字"}, //限制输入数字
			sendlowmoney: {required: "请输入派发金额",maxlength: "最多只能输入三位整数",digits: "请输入正整数",min:"请输入大于0的数字"}, //限制输入数字
			sendhighmoney: {required: "请输入派发金额",maxlength: "最多只能输入三位整数",digits: "请输入正整数",min:"请输入大于0的数字"}, //限制输入数字
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
	$("#sendruleidref").append("<option value=''>请选择</option>");
	$("#addcboxId + .error").css("display","none");
	$("#addcboxId2 + .error").css("display","none");
	$("#scopeDiv").show();
	$("#editFormDiv").show();
	initSelectCouponRules();//显示发放规则下拉框
	
	 $("body").on('click',function(e){ 
		 if($("#editFormDiv").is(':visible')){
	       if(e.target!=null && e.target.className!="adduserbtn"){
	    	   $("#s2id_users").remove(); 
	    	   $(".content ~ .select2-drop").remove();
	    	   $(".content ~ #select2-drop-mask").remove();
	       }
		 }   
	}); 
}

/**
 * 初始化新增窗体 发放规则下拉框
 */
function initSelectCouponRules() {
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: 'Couponing/getCouponRuleNames',
		contentType: 'application/json; charset=utf-8',
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
    todayHighlight: false,
    startView: 2,
    minView: 2,
    forceParse: 0,
    clearBtn: true,
    initialDate :date
});
$('#fixedstarttime,#fixedendtime').datetimepicker({
    format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
    startDate: date,
    language: 'zh-CN', //汉化
    weekStart: 1,
    autoclose: 1,
    todayHighlight: false,
    startView: 2,
    minView: 2,
    forceParse: 0,
    clearBtn: true,
    initialDate :date,
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
	var users="";
	//处理城市，拼接城市id
	$("#scopeDiv .added").each(function(i){
		var cid=$(this).attr("id");
		if(i==0)
			citys=cid;
		else
		    citys=citys+","+cid;
	})
	//处理用户，拼接用户账户
	$("#userDiv .added").each(function(j){
		var uid=$(this).attr("id");
		var phone=$(this).text();
		if(j==0)
			users=uid+","+phone;
		else
		    users=users+";"+uid+","+phone;
	})
	//处理有效期,若有效期选择为发放日至N天内
	if(data.outimetype=='1'){
		data.fixedstarttime=data.sendstarttime;
		data.fixedendtime=getFutureDate(data.sendendtime, data.sendtimeinday);
	}
	
	data.citys=citys;
	data.users=users;
	if($("#sendruleidref").val()=='0' && $("#sendruleidref").find("option:selected").text()=="人工发券"){
		data.sendruletype=6;
	}
	
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
	$(".addcbox").empty();    //清空选择的城市、用户
	$("#userDiv").hide();       //隐藏添加用户
	 $(".content ~ .select2-drop").remove();
	 $(".content ~ #select2-drop-mask").remove();
     $("#selectuser #s2id_users").remove(); //将添加用户select2控件恢复原状
	//$("#users").removeAttr("class");
	// 清除表单校验
	$("#editForm label[class='error']").remove();
	$("#editForm input").removeClass('error');
	$("#addcboxId").after('<label for="addcboxId" class="error">请选择发放区域</label>');
	$("#addcboxId2").after('<label for="addcboxId" class="error">请选择指定用户</label>');
	$("#addcboxId + .error").css("display","none");
	$("#addcboxId2 + .error").css("display","none");
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "queryruletype", "value": $("#querysendruletype").val()},
		{ "name": "querysendservicetype", "value": $("#querysendservicetype").val() },
		{ "name": "queryactivystate", "value": $("#queryactivystate").val() },
		{ "name": "queryusetype", "value": $("#queryusetype").val() },
		{ "name": "queryname", "value": $("#queryname").val() },
		{ "name": "querysendruleidref", "value": $("#querysendruleidref").val() },
		{ "name": "querysendstarttime", "value": $("#querysendstarttime").val() },
		{ "name": "querysendendtime", "value": $("#querysendendtime").val() }
	];
	if($("#querysendruleidref").val()!=""){
		var data=$("#querysendruleidref").select2("data");
		if(data.id=="0" && data.text=="人工发券"){
			var querysendruletype={ "name": "querysendruletype", "value": 6};
			conditionArr.push(querysendruletype);
			conditionArr[5].value="";
		}
			
	}
	//此处处理人工发券，分用queryruletype和querysendruletype字段
	dataGrid.fnSearch(conditionArr);

}
/**
 * 修改
 * @param {} id
 */
function edit(id) {
	$("#titleForm").html("修改抵用券");
	$("#addcboxId + .error").css("display","none");
	$("#addcboxId2 + .error").css("display","none");
	$("#editFormDiv").show();
	$.ajax({
		type: "GET",
		url:"Couponing/edit/"+id,
		cache: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var act=status.activity;
				var citys=status.citys;
				var users=status.users;
				$("#id").val(act.id);
				$("#name").val(act.name);
				$("#sendservicetype").val(act.sendservicetype);
				$("#sendruletarget").val(act.sendruletarget);
				$("#sendruleidref").empty();
                $("#sendruleidref").append("<option value='"+act.sendruleidref+"' selected='selected'>"+act.rulename+"</option>");
                $("#name,#sendservicetype,#sendruletarget,#sendruleidref,input[name='usetype']").attr("disabled","disabled");
                //处理人工，
                if(act.sendruletype==6){
                	$("#sendruleidref").empty();
                    $("#sendruleidref").append("<option value='0' selected='selected'>人工发券</option>");
                	$("#sendcount").val(act.sendcount);
                	$("#sendcount").attr("disabled","disabled");
                	$("#userDiv").show();
                	$("#addcboxId2").empty();
                	$(".adduserbtn").attr("disabled","disabled");
                	for(var i=0;i<users.length;i++){
                		var cityhtml='<div class="added" id="'+users[i].id+'">'+users[i].account+'</div>';
            				$("#addcboxId2").append(cityhtml);
                	}
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
				}else{
					$("input[name='usetype']").eq(1).prop("checked",true);
				}
				//有效期
				if(act.outimetype==1){
					$("input[name='outimetype']").eq(0).prop("checked",true);
					$("#sendtimeinday").val(act.sendtimeinday);
					$("#fixedstarttime,#fixedendtime").attr("disabled","disabled");
					$("#sendtimeinday").removeAttr("disabled");
				}else{
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
function record(id){
	window.location.href=base+"Couponing/record?id="+id;
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
		
		if($("#addcboxId2 .added").size()<1){
			$("#addcboxId2 + .error").css("display","block");
		}else{
			$("#addcboxId2 + .error").css("display","none");
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
	
	if($("#userDiv").is(":hidden")){
		$("#addcboxId2 + .error").css("display","none");
		if($("#addcboxId .added").size()<1){
			$("#addcboxId + .error").css("display","block");
			toastr.error("请输入完整信息", "提示");
			return;
		}
	}else{
		if($("#addcboxId2 .added").size()<1 && $("#addcboxId .added").size()<1){
			$("#addcboxId2 + .error").css("display","block");
			$("#addcboxId + .error").css("display","block");
			toastr.error("请输入完整信息", "提示");
			return;
		}
		if($("#addcboxId2 .added").size()<1){
			$("#addcboxId2 + .error").css("display","block");
			toastr.error("请输入完整信息", "提示");
			return;
		}else{$("#addcboxId2 + .error").css("display","none");}
		if($("#addcboxId .added").size()<1){
			$("#addcboxId + .error").css("display","block");
			toastr.error("请输入完整信息", "提示");
			return;
		}else{$("#addcboxId + .error").css("display","none");}
	}
	
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
	$("body").off('click');
	initSelectCoupon();
	initSelectRule();
}

/**
 * 新增的清空功能
 * 
 */
function emptys(){
	$("#querysendruletype").val("");
	$("#querysendservicetype").val("");
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
	/*	if($("#sendruleidref").val()=='0' && $("#sendruleidref").find("option:selected").text()=="人工发券"){
			if($("#addcboxId2 .added").size()==0){
				toastr.error("请输入完整信息", "提示");
				return false;
			}
		}*/
/*		//检测城市信息
		if($("#addcboxId .added").size()==0){
			toastr.error("请输入完整信息", "提示");
			return false;
		}*/
		//检测随机派发金额
		if($("input[name='sendmoneytype']:checked").val()=='2'){
			if(parseInt($("#sendlowmoney").val())>=parseInt($("#sendhighmoney").val())){
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
				toastr.error("有效期结束时间须大于等于有效期开始时间", "提示");
				  return false;
			}
		}
		return true;
}
//获取明天时间yyyy-MM-dd
function getCurrentDate() {
    var date = new Date();
    date.setDate(date.getDate());
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
