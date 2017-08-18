var base = document.getElementsByTagName("base")[0].getAttribute("href");

$(function () {
	formValidate();
	validateForm();
	dateFormat();
});

function formValidate(){
	if ($.validator) {
	    $.validator.prototype.elements = function () {
	        var validator = this,
	          rulesCache = {};
	        return $(this.currentForm)
	        .find("input, select, textarea")
	        .not(":submit, :reset, :image, [disabled]")
	        .not(this.settings.ignore)
	        .filter(function () {
	            if (!this.name && validator.settings.debug && window.console) {
	                console.error("%o has no name assigned", this);
	            }
	            rulesCache[this.name] = true;
	            return true;
	        });
	    }
	}
	
}



/**
 * 表单校验
 */
function validateForm() {
	$("#addInsurform").validate({
		rules : {
			plateNo : {
				required : true,
				maxlength : 5
			},
			engineid : {
				required : true
			},
			vin : {
				required : true
			},
			insurcom : {
				required : true
			},
			insurtype :{
				required : true
			},
			insurnum : {
				required : true
			},
			insurcount :{
				required : true
			},
			startTime : {
				required: true
			},
			endTime : {
				required: true
			}
		},
		messages : {
			plateNo : {
				required : "请输入输入车牌号码",
				maxlength : "最大长度不能超过5个字符"
			},
			engineid : {
				required : "请输入发动机号"
			},
			vin : {
				required : "请输入车辆识别码"
			},
			insurcom : {
				required : "请输入保险公司名称"
			},
			insurtype : {
				required : "请选择保险类型"
			},
			insurnum : {
				required : "请输入保险号"
			},
			insurcount :{
				required : "请输入保险金额"
			},
			startTime : {
				required : "请选择开始日期"
			},
			endTime : {
				required : "请选择结束日期"
			}
			
		}
	})
}

$("#plateNoProvince").change(function() {
		var projectId = $("#plateNoProvince").val();
		if (projectId == '') {
			redrawVersionList("");
		}
		var data = {
			id : projectId,
		};
		$.post("PubVehicle/GetPlateNoCity", data, function(ret) {
			if (ret) {
				redrawVersionList(ret);
				return;
			} else {
				var message = "刷新页面失败";
				toastr.error(message, "错误");
			}
		});
});

function redrawVersionList(versionList) {
	//alert(versionList);
	var html = "";
	if (versionList != '') {
		for (var i = 0; i < versionList.length; i++) {
			html += '<option value="'+versionList[i].value+'">'
					+ versionList[i].text + '</option>';
		}
	} else {
		html += '<option value="">' + "" + '</option>';
	}
	$("#plateNoCity").html(html);
}

function dateFormat() {
	$('.searchDate').datetimepicker({
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });
	Date.prototype.format =function(format)
	{
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
	}
	var date=new Date();
	$("#endTime").val(date.format('yyyy-MM-dd'));
	 date.setDate(1);
	$("#startTime").val(date.format('yyyy-MM-dd'));
}

function callBack(){
	window.location.href=base+"PubVehInsur/Index";
}

function del(obj){	
	var comfirmData={
			tittle:"提示",
			context:"您确认要删除这张新增的保险吗？",
			button_l:"否",
			button_r:"是",
			htmltex:"<input type='hidden' placeholder='添加的html'> "
		};
	Zconfirm(comfirmData,function(){
		$(obj).parent().remove();	
	});
}
//添加保险
function add(){
	//保险类型下拉框赋值
	var selectContent = "";
	for(i=0;i<$("#insurtype-0 option").length;i++){
		selectContent += "<option value='"+$("#insurtype-0").get(0).options[i].value+"'>"+$("#insurtype-0").get(0).options[i].text+"</option>"; 
	}
    
	//id命名前缀
	var i = $(".adddiv").length + 1;
	
	//动态增加元素
	var html = "<div class = 'adddiv'>";
	html += "<hr style='height:1px;border:none;border-top:1px dashed #CBCBCB; margin-top: 20px; margin-bottom: 10px;' /><input type ='button' id = 'xbtn' class = 'ico_x_a' onclick = 'del(this)'/>";
	html += "<div class = 'row'>" +
				"<div class = 'col-4'>" +
				"	<label>保险公司名称<em class='asterisk'></em></label>" +
				"		<input id= 'insurcom-"+ i +"' name='insurcom' type='text' placeholder='保险公司名称' style='width: 68%;'/>" +
				"</div>" +
				"<div class = 'col-4'>" + 
				"  <label>保险类型<em class='asterisk'></em></label> " +
				"	<select id='insurtype-"+ i +"' name='insurtype'>" + selectContent + 
				"	</select>" + 
				"</div>" +
				"<div class = 'col-4'>" + 
				"	<label>保险号<em class='asterisk'></em></label>" +
				"	<input id='insurnum-"+ i + "' name='insurnum' type='text' placeholder='请输入保险号' style='width: 68%;'>" + 
				"</div>" + 
			 "</div>";
	html += "<div class = 'row'>" +
				"<div class='col-4'>" +
				"	<label>保险金额<em class='asterisk'></em></label>" + 
				"	<input type='text' placeholder='保险金额' id='insurcount-"+ i + "' name='insurcount' style='width: 64%;'/>&nbsp;元" +
				"</div>" +
				"<div class='col-4'>" +
				"	<label>保险有效期<em class='asterisk'></em></label>	" +
				"	<input style='width:32%;' id='insureff-" + i +"' name='startTime' type='text' placeholder='开始日期' value='' class='searchDate' readonly='readonly'>&nbsp;至" + 
				"	<input style='width:32%;' id='insurexp-' " + i +" name='endTime' type='text' placeholder='结束日期' value='' class='searchDate' readonly='readonly'>" +
				"</div>" +
			  "</div>";
	html +=  "</div>";
	$("#addInsurform").append(html);
	
	//日期选择框格式化
	dateFormat();
	
	validateForm();
}

//保存
function save(){	
	var form = $("#addInsurform");
	if (!form.valid())
		return;
	
	//车牌,发动机,识别码
	var fullplateno = $("#plateNoProvince").find("option:selected").text() + $("#plateNoCity").find("option:selected").text() + $("#plateNo").val();
	var engineid = $("#engineid").val();
	var vin = $("#vin").val();
	
	
	var len = $(".adddiv").length + 1;
	var insurJson;
	var jsonstr = "[]";
	var jsonarray = eval("("+jsonstr+")");  
	for(i = 0;i < len; i++){
		insurJson = {insurcom :  $("#insurcom-"+ i).val(), insurtype : $("#insurtype-" + i).val(), 
					 insurnum : $("#insurnum-" + i).val(), insurcount : $("#insurcount-" + i).val(),
					 insureff :  $("#insureff-" + i).val(), insurexp : $("#insureff-" + i).val()};
		jsonarray.push(insurJson);
	}
	var data = {fullplateno: fullplateno, engineid: engineid, vin:vin,insurList:jsonarray};
	$.ajax({
		type: "post",
		contentType:"application/json",
		url: "PubVehInsur/AddPubVehInsurs",
		data: JSON.stringify(data),
		success: function(status){
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
	            toastr.success(message, "提示");
	        	window.location.href=base+"PubVehInsur/Index";
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	});
	
	
	
}
	
