/**
 * 页面初始化
 */
$(function () {
	initData();
	dateFormat();
	getModify();
	//初始化radio绑定事件
	 $(":radio").click(function(){
		    if($(this).val() == "week"){
		    	$("#weekTable").show();
		    	$("#dateTable").hide();
		    }else{
		    	$("#weekTable").hide();
		    	$("#dateTable").show();
		    }
		  });
});
/**
 * 初始化表格数据
 */
function initData() {
		showCitySelect1(
			".input_box2", 
			"PubInfoApi/GetCitySelect1", 
			$("#citymarkid").val(), 
			function(backVal, $obj) {
				$('#cityname').val($obj.text());
				$("#city").val($obj.data('id'));
				$('#cityname').valid();
			}
		);
}
function callBack(){
	window.location.href=base+"PubPremiumRule/Index";
}
/**
 * 时间设置
 * @param now
 * @returns
 */
function formatDate(now) { 
	var year=now.getYear(); 
	var month=now.getMonth()+1; 
	var date=now.getDate(); 
	var hour=now.getHours(); 
	var minute=now.getMinutes(); 
	var second=now.getSeconds(); 
	return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
} 

//日期设置
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
} 
//添加规则 b当前行号,a当前星期,d当前星期点击事件,f上一行的按钮id 
function addRules(b,a,d,f){
	var number = f.replace(/[^0-9]/ig,""); 
	//添加一行
	var newTr = weekTable.insertRow(b+1);
	//添加列
	var cell0 = newTr.insertCell();
	var cell1 = newTr.insertCell();
	var cell2 = newTr.insertCell();
	var cell3 = newTr.insertCell();
	var c;
	if(number == ""){
	  c = a+b;
	}else{
	  c = a+(parseInt(number)+1).toString();
	}
	var e = "a" + c;
	var m = "d" + c;
//	var e = "#"+a+(parseInt(b)-1).toString();
	 $("#"+f).hide();	
	//判断隐藏哪一个
	if(a=="one"){
		$("#addOne").hide();
		$("#deleteOne").show();
	}else if(a=="tow"){
		$("#addTow").hide();
		$("#deleteTow").show();
	}else if(a=="three"){
		$("#addThree").hide();
		$("#deleteThree").show();
	}else if(a=="four"){
		$("#addFour").hide();
		$("#deleteFour").show();
	}else if(a=="five"){
		$("#addFive").hide();
		$("#deleteFive").show();
	}else if(a=="six"){
		$("#addSix").hide();
		$("#deleteSix").show();
	}else{
		$("#addSeven").hide();
		$("#deleteSeven").show();
	}
	//设置列内容和属性
	cell0.innerHTML= '';
	cell1.innerHTML= '<div class="row"><div class="col-5" id="nightstarttime1"><div class="ztimepicker" >'
		+'<input type="text" readonly value="" class="ztimepicker_input" name="nightstarttime" id="nightstarttime"placeholder="请选择时间"/>'
		+'<div class="ztimebox"><div class="ztimebox_s"><ul class="zhour"></ul><ul class="zmin"></ul></div></div></div></div>'
+'<div class="col-1"style="margin-left: 4%;"><span>-</span></div>'
+'<div class="col-5" id="nightstarttime2"><div class="ztimepicker">'
+'<input type="text" readonly value="" class="ztimepicker_input" name="nightendtime" id="nightendtime"placeholder="请选择时间"/>'
+'<div class="ztimebox"><div class="ztimebox_s"><ul class="zhour"></ul><ul class="zmin"></ul></div></div></div></div></div>';
	
	cell2.innerHTML= '<input style="text-align: center;"type="text" placeholder="溢价倍率"maxlength = "3" onBlur="overFormat(this)" onkeypress="pageOnlyNumber(event)" onkeyup="clearNoNum2(this)"/>';
	cell3.innerHTML = '&nbsp&nbsp<a id='+"'"+e+"'"+'" href="javascript:void(0)"onclick='+"'"+d+"'"+'><font color="#8CEA00">新增</font></a>'
		+'&nbsp&nbsp<a  id='+"'"+m+"'"+'" href="javascript:void(0)"onclick="deleteWeek(this)"><font color="red">删除</font></a>';
	//初始化ztimepicker1
	abc();
	}
//删除规则 
function deleteWeek(obj){
//    var $td= $(obj).parents('tr').children('td');
    //获取上一行的第一个里面的有没有数据
    var firstCell = $(obj).parent().parent().prev()[0].firstElementChild.textContent;
    var nextCell = $(obj).parent().parent().next()[0].firstElementChild.textContent;
    //获取当前项的display属性
    var isDisplay = $(obj).prev()[0].style.display;
    //获取删除当前项的id
    var nowId = $(obj)[0].id;
    var preId = $(obj).prev()[0].id
    //获取删除前一项项的id
    var id = $(obj).parent().parent().prev()[0].lastChild.children[0].id;
    //获取下一行数据display
    var atheNext = $(obj).parent().parent().prev()[0].lastChild.children[0].style.display;
    var btheNext = $(obj).parent().parent().prev()[0].lastChild.children[1].style.display;
    //提取里面的数字和字母
  //  var number = id.replace(/[^0-9]/ig,""); 
  //  var english = id.replace(/[^a-z]+/ig,"");
 //   var onId = english+(parseInt(number)-1).toString();
    if(isDisplay == "none"){
    }else{ 
    	$("#"+id).show();
    }
    if(firstCell == "星期一"){
    	if(atheNext == ""  && btheNext == "" || (atheNext == "none") && nextCell !=""){
    	$("#addOne").show();
    	$("#deleteOne").hide();
    	}
    }else if(firstCell == "星期二"){
    	if(atheNext == ""  && btheNext == ""|| (atheNext == "none") && nextCell !=""){
    	$("#addTow").show();
    	$("#deleteTow").hide();
    	}
    }else if(firstCell == "星期三"){
    	if(atheNext == ""  && btheNext == ""|| (atheNext == "none") && nextCell !=""){
    	$("#addThree").show();
    	$("#deleteThree").hide();
    	}
    }else if(firstCell == "星期四"){
    	if(atheNext == ""  && btheNext == ""|| (atheNext == "none") && nextCell !=""){
    	$("#addFour").show();
    	$("#deleteFour").hide();
    	}
    }else if(firstCell == "星期五"){
    	if(atheNext == ""  && btheNext == ""|| (atheNext == "none") && nextCell !=""){
    	$("#addFive").show();
    	$("#deleteFive").hide();
    	}
    }else if(firstCell == "星期六"){
    	if(atheNext == ""  && btheNext == ""|| (atheNext == "none") && nextCell !=""){
    	$("#addSix").show();
    	$("#deleteSix").hide();
    	}
    }else if(firstCell == "星期日"){
    	if(atheNext == ""  && btheNext == ""|| (atheNext == "none") && nextCell !=""){
    	$("#addSeven").show();
    	$("#deleteSeven").hide();
    	}
    }else{
    }
    //在js端删除一整行数据  
    $(obj).parent().parent().remove();  
}
function deleteOne(obj){
//    var $td= $(obj).parents('tr').children('td');
	 $(obj).parent().parent().next()[0].cells[0].innerHTML ='<input name="theWeek" id="theWeek" type="checkbox" checked value="one">星期一';
	    $(obj).parent().parent().next()[0].lastChild.children[0].onclick= function(){addOne(this)};
	    $(obj).parent().parent().next()[0].lastChild.children[1].onclick =function(){deleteOne(this)};
	    $(obj).parent().parent().next()[0].lastChild.children[0].id="addOne";
	    $(obj).parent().parent().next()[0].lastChild.children[1].id ="deleteOne";
	    var atheNext = $(obj).parent().parent().next()[0].lastChild.children[0].style.display;
	    var btheNext = $(obj).parent().parent().next()[0].lastChild.children[1].style.display;
	    if(atheNext == "" && btheNext==""){
	    	$(obj).parent().parent().next()[0].lastChild.children[1].style.display = "none";
	    }
    //在js端删除一整行数据  
    $(obj).parent().parent().remove();  
}
function deleteTow(obj){
//    var $td= $(obj).parents('tr').children('td');
	 $(obj).parent().parent().next()[0].cells[0].innerHTML ='<input name="theWeek" id="theWeek" type="checkbox"  checked value="tow">星期二';
	    $(obj).parent().parent().next()[0].lastChild.children[0].onclick= function(){addTow(this)};
	    $(obj).parent().parent().next()[0].lastChild.children[1].onclick =function(){deleteTow(this)};
	    $(obj).parent().parent().next()[0].lastChild.children[0].id="addTow";
	    $(obj).parent().parent().next()[0].lastChild.children[1].id ="deleteTow";
	    var atheNext = $(obj).parent().parent().next()[0].lastChild.children[0].style.display;
	    var btheNext = $(obj).parent().parent().next()[0].lastChild.children[1].style.display;
	    if(atheNext == "" && btheNext==""){
	    	$(obj).parent().parent().next()[0].lastChild.children[1].style.display = "none";
	    }
    //在js端删除一整行数据  
    $(obj).parent().parent().remove();  
}
function deleteThree(obj){
//    var $td= $(obj).parents('tr').children('td');
	 $(obj).parent().parent().next()[0].cells[0].innerHTML ='<input name="theWeek" id="theWeek" type="checkbox" checked value="three">星期三';
	    $(obj).parent().parent().next()[0].lastChild.children[0].onclick= function(){addThree(this)};
	    $(obj).parent().parent().next()[0].lastChild.children[1].onclick =function(){deleteThree(this)};
	    $(obj).parent().parent().next()[0].lastChild.children[0].id="addThree";
	    $(obj).parent().parent().next()[0].lastChild.children[1].id ="deleteThree";
	    var atheNext = $(obj).parent().parent().next()[0].lastChild.children[0].style.display;
	    var btheNext = $(obj).parent().parent().next()[0].lastChild.children[1].style.display;
	    if(atheNext == "" && btheNext==""){
	    	$(obj).parent().parent().next()[0].lastChild.children[1].style.display = "none";
	    }
    //在js端删除一整行数据  
    $(obj).parent().parent().remove();  
}
function deleteFour(obj){
//    var $td= $(obj).parents('tr').children('td');
	 $(obj).parent().parent().next()[0].cells[0].innerHTML ='<input name="theWeek" id="theWeek" type="checkbox" checked value="four">星期四';
	    $(obj).parent().parent().next()[0].lastChild.children[0].onclick= function(){addFour(this)};
	    $(obj).parent().parent().next()[0].lastChild.children[1].onclick =function(){deleteFour(this)};
	    $(obj).parent().parent().next()[0].lastChild.children[0].id="addFour";
	    $(obj).parent().parent().next()[0].lastChild.children[1].id ="deleteFour";
	    var atheNext = $(obj).parent().parent().next()[0].lastChild.children[0].style.display;
	    var btheNext = $(obj).parent().parent().next()[0].lastChild.children[1].style.display;
	    if(atheNext == "" && btheNext==""){
	    	$(obj).parent().parent().next()[0].lastChild.children[1].style.display = "none";
	    }
    //在js端删除一整行数据  
    $(obj).parent().parent().remove();  
}
function deleteFive(obj){
    var $td= $(obj).parents('tr').children('td');
    $(obj).parent().parent().next()[0].cells[0].innerHTML ='<input name="theWeek" id="theWeek" type="checkbox" checked value="five">星期五';
    $(obj).parent().parent().next()[0].lastChild.children[0].onclick= function(){addFive(this)};
    $(obj).parent().parent().next()[0].lastChild.children[1].onclick =function(){deleteFive(this)};
    $(obj).parent().parent().next()[0].lastChild.children[0].id="addFive";
    $(obj).parent().parent().next()[0].lastChild.children[1].id ="deleteFive";
    var atheNext = $(obj).parent().parent().next()[0].lastChild.children[0].style.display;
    var btheNext = $(obj).parent().parent().next()[0].lastChild.children[1].style.display;
    if(atheNext == "" && btheNext==""){
    	$(obj).parent().parent().next()[0].lastChild.children[1].style.display = "none";
    }
    //在js端删除一整行数据  
    $(obj).parent().parent().remove();  
}
function deleteSix(obj){
    var $td= $(obj).parents('tr').children('td');
    $(obj).parent().parent().next()[0].cells[0].innerHTML ='<input name="theWeek" id="theWeek" type="checkbox" checked value="six">星期六';
    $(obj).parent().parent().next()[0].lastChild.children[0].onclick= function(){addSix(this)};
    $(obj).parent().parent().next()[0].lastChild.children[1].onclick =function(){deleteSix(this)};
    $(obj).parent().parent().next()[0].lastChild.children[0].id="addSix";
    $(obj).parent().parent().next()[0].lastChild.children[1].id ="deleteSix";
    var atheNext = $(obj).parent().parent().next()[0].lastChild.children[0].style.display;
    var btheNext = $(obj).parent().parent().next()[0].lastChild.children[1].style.display;
    if(atheNext == "" && btheNext==""){
    	$(obj).parent().parent().next()[0].lastChild.children[1].style.display = "none";
    }
    //在js端删除一整行数据  
    $(obj).parent().parent().remove();  
}
function deleteSeven(obj){
    var $td= $(obj).parents('tr').children('td');
    $(obj).parent().parent().next()[0].cells[0].innerHTML ='<input name="theWeek" id="theWeek" type="checkbox" checked value="seven">星期日';
    $(obj).parent().parent().next()[0].lastChild.children[0].onclick= function(){addSeven(this)};
    $(obj).parent().parent().next()[0].lastChild.children[1].onclick =function(){deleteSeven(this)};
    $(obj).parent().parent().next()[0].lastChild.children[0].id="addSeven";
    $(obj).parent().parent().next()[0].lastChild.children[1].id ="deleteSeven";
    var atheNext = $(obj).parent().parent().next()[0].lastChild.children[0].style.display;
    var btheNext = $(obj).parent().parent().next()[0].lastChild.children[1].style.display;
    if(atheNext == "" && btheNext==""){
    	$(obj).parent().parent().next()[0].lastChild.children[1].style.display = "none";
    }
    //在js端删除一整行数据  
    $(obj).parent().parent().remove();  
}
function addOne(obj){
//	 var f = $(obj).parent().parent().prev()[0].lastChild.children[0].id
	 var f = $(obj).parent().parent()[0].lastChild.children[0].id;
//	 var v = $(obj).parent().parent()[0].lastChild.children[1].id;
	 var b = $(obj).parent().parent().prevAll().length;
	 var a = "one";
	 var d = "addOne(this)"; 
	 addRules(b,a,d,f);
	}
function addTow(obj){
	 var f = $(obj).parent().parent()[0].lastChild.children[0].id;
	 var b = $(obj).parent().parent().prevAll().length;
	 var a = "tow";
	 var d = "addTow(this)";
	 addRules(b,a,d,f);
	}
function addThree(obj){
	 var b = $(obj).parent().parent().prevAll().length;
	 var f = $(obj).parent().parent()[0].lastChild.children[0].id;
	 var a = "three";
	 var d = "addThree(this)";
	 addRules(b,a,d,f);
	}
function addFour(obj){
	//获取表的总行数 tr
	//var aaa = $("#weekTable").find("tr").length ;
	 var b = $(obj).parent().parent().prevAll().length;
	 var f = $(obj).parent().parent()[0].lastChild.children[0].id;
	 var a = "four";
	 var d = "addFour(this)";
	 addRules(b,a,d,f);
	}
function addFive(obj){
	//获取表的总行数 tr
	//var aaa = $("#weekTable").find("tr").length ;
	 var b = $(obj).parent().parent().prevAll().length;
	 var f = $(obj).parent().parent()[0].lastChild.children[0].id;
	 var a = "five";
	 var d = "addFive(this)";
	 addRules(b,a,d,f);
	}
function addSix(obj){
	//获取表的总行数 tr
	//var aaa = $("#weekTable").find("tr").length ;
	 var b = $(obj).parent().parent().prevAll().length;
	 var f = $(obj).parent().parent()[0].lastChild.children[0].id;
	 var a = "six";
	 var d = "addSix(this)";
	 addRules(b,a,d,f);
	}
function addSeven(obj){
	 var b = $(obj).parent().parent().prevAll().length;
	 var f = $(obj).parent().parent()[0].lastChild.children[0].id;
	 var a = "seven";
	 var d = "addSeven(this)";
	 addRules(b,a,d,f);
	}


//添加按日期的数据
function addDateOne(obj){
	var f = $(obj).parent().parent()[0].lastChild.children[0].id;
	var number = f.replace(/[^0-9]/ig,""); 
	var b = $(obj).parent().parent().prevAll().length;
	var c;
	if(number == ""){
	  c = "a"+b;
	}else{
	  c = "a"+(parseInt(number)+1).toString();
	}
	var e = "a" + c;
	var m = "d" + c;
	var oneCell = $(obj).parent().parent().prev()[0].firstElementChild.id;
	if(oneCell == ""){
		$("#deleteDateOne").hide();
		$("#addDateOne").show();
	}
	var onid = $(obj).parent().parent()[0].lastChild.children[0].id;
	/*$("#"+"a"+(parseInt(b)-1).toString()).hide();
	$("#"+"b"+(parseInt(b)-1).toString()).show();*/
	 $("#"+onid).hide();
	$("#deleteDate").show();
	$("#addDateOne").hide();
	var newTr = dateTable.insertRow(b+1);
	var cell0 = newTr.insertCell();
	var cell1 = newTr.insertCell();
	var cell2 = newTr.insertCell();
	var cell3 = newTr.insertCell();
	cell0.innerHTML= '';
	cell1.innerHTML= '<div class="row"><div class="col-5" id="nightstarttime1"><div class="ztimepicker" >'
		+'<input type="text" readonly value="" class="ztimepicker_input" name="nightendtime" id="nightendtime"placeholder="请选择时间"/>'
		+'<div class="ztimebox"><div class="ztimebox_s"><ul class="zhour"></ul><ul class="zmin"></ul></div></div></div></div>'
+'<div class="col-1"style="margin-left: 4%;"><span>-</span></div>'
+'<div class="col-5" id="nightstarttime2"><div class="ztimepicker">'
+'<input type="text" readonly value="" class="ztimepicker_input" name="nightendtime" id="nightendtime"placeholder="请选择时间"/>'
+'<div class="ztimebox"><div class="ztimebox_s"><ul class="zhour"></ul><ul class="zmin"></ul></div></div></div></div></div>';
	
	cell2.innerHTML= '<input style="text-align: center;"type="text" placeholder="溢价倍率"maxlength = "3" onBlur="overFormat(this)" onkeypress="pageOnlyNumber(event)" onkeyup="clearNoNum2(this)"/>';
	cell3.innerHTML = '&nbsp&nbsp<a id='+"'"+e+"'"+'href="javascript:void(0)"onclick="addDateOne(this)"><font color="#8CEA00">新增</font></a>'
		+'&nbsp&nbsp<a id='+"'"+m+"'"+'href="javascript:void(0)"onclick="deleteDate(this)"><font color="red">删除</font></a>';
	abc();
}
//主删除
function deleteDateOne(obj){
	 var $td= $(obj).parents('tr').children('td');
	    $(obj).parent().parent().next()[0].cells[0].innerHTML ='<input style = "display:none" id="date"value="date"/><label style="display:none">日期</label>'
            +'<input style="width:31.5%;" id="startdt" name="startdt" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">-'
     +'<input style="width:31.5%;" id="enddt" name="enddt" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">';
	    $(obj).parent().parent().next()[0].cells[0].style.textAlign = "center";
	    $(obj).parent().parent().next()[0].cells[0].id = "dateId";
	    $(obj).parent().parent().next()[0].lastChild.children[0].onclick= function(){addDateOne(this)};
	    $(obj).parent().parent().next()[0].lastChild.children[1].onclick =function(){deleteDateOne(this)};
	    $(obj).parent().parent().next()[0].lastChild.children[0].id="addDateOne";
	    $(obj).parent().parent().next()[0].lastChild.children[1].id ="deleteDate";
	    var atheNext = $(obj).parent().parent().next()[0].lastChild.children[0].style.display;
	    var btheNext = $(obj).parent().parent().next()[0].lastChild.children[1].style.display;
	    if(atheNext == "" && btheNext==""){
	    	$(obj).parent().parent().next()[0].lastChild.children[1].style.display = "none";
	    }
	    //在js端删除一整行数据  
	    $(obj).parent().parent().remove();
	    dateFormat();
}
//子删除
function deleteDate(obj){
  //获取上一行的第一个里面的有没有数据
  var firstCell = $(obj).parent().parent().prev()[0].firstElementChild.id;
  var nextCell = $(obj).parent().parent().next()[0].firstElementChild.textContent;
  //获取当前项的display属性
  var isDisplay = $(obj).prev()[0].style.display;
  //获取删除当前项的id
  var nowId = $(obj)[0].id;
  var preId = $(obj).prev()[0].id
  //获取删除前一项项的id
  var id = $(obj).parent().parent().prev()[0].lastChild.children[0].id;
  //获取下一行数据display
  var atheNext = $(obj).parent().parent().prev()[0].lastChild.children[0].style.display;
  var btheNext = $(obj).parent().parent().prev()[0].lastChild.children[1].style.display;
  //提取里面的数字和字母
  if(isDisplay == "none"){
  }else{ 
  	$("#"+id).show();
  }
  if(firstCell == "dateId"){
  	if(atheNext == ""  && btheNext == "" || (atheNext == "none") && nextCell !=""){
  	$("#addDateOne").show();
  	$("#deleteDate").hide();
  	}
  }
  //在js端删除一整行数据  
  $(obj).parent().parent().remove();  

}
//子添加
function addDate(obj){
	
}
function validateForm() {
	$("#editPubPremiumRuleForm").validate({
		ignore:'',
		rules: {
			cartype: {required: true},
			cityname: {required: true},
			rulename: {required: true,maxlength:20}
		},
		messages: {
			cartype: {required:  "请选择业务类型"},
			cityname: {required: "请选择城市"},
			rulename: {required: "请输入规则名称",maxlength: "最大长度不能超过20个字符"}
		}
	})
}
//保存
function save(){
	//验证页面数据
	validateForm();
	var form = $("#editPubPremiumRuleForm");
	var a = !form.valid();
	if(!form.valid()) return;
	var editForm = $("#editPubPremiumRuleForm").validate();
	//验证勾选框是否勾选
	var arrayObj = new Array()
	var checkVal = "";
	//判断所选的星期
	//查看选择的checkbox
	obj = document.getElementsByName("theWeek");
	for(k in obj){
        if(obj[k].checked)
        	 var aa = obj[k].value
        	checkVal += aa+",";
            arrayObj.push(aa);
    }
	var ruletype = $('input:radio[name="ruletype"]:checked').val();
	//判断勾选框
	if(ruletype == "week"){
	  var aaa = $(':checkbox:checked').size()
		if(aaa == 0){
			toastr.error("请选择星期", "提示");
			return;
		}
	}else{
		if($("#startdt").val() == "" || $("#enddt").val() == ""){
			toastr.error("请选择时间", "提示");
			return;
		}else{
			var startime = $("#startdt").val();
			var endtime =  $("#enddt").val();
			var d1 = new Date(startime.replace(/\-/g, "\/"));  
			 var d2 = new Date(endtime.replace(/\-/g, "\/")); 
			if(d1>d2){
				toastr.error("输入日期范围错误", "提示");
				return;
			}
		}
	}
	//遍历table内容
	var tableInfo = "";
	jQuery('tr td input').each(function(){
		tableInfo += jQuery(this).val()+",";
		})
	var cityname = $("#cityname").val();
	var rulename = $("#rulename").val();
	var cartype = $("#cartype").val();
	var id = $("#aaid").val();
	var rulestatus = $("#aarulestatus").val();
	var number;
	var data = {
			 id : id,
			 rulestatus : rulestatus,
			 ruletype : ruletype,
			 cityname : cityname,
			 rulename : rulename,
			 tableInfo : tableInfo,
			 checkVal : checkVal,
			 cartype : cartype
	}
	$.ajax({
		type: "POST",
		url:"PubPremiumRule/PubPremiumAdd",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (response) {
			if(response.ifHaveNull == "noOk"){
			  toastr.error("请输入完整信息", "提示");
			  return;
			}
		    if(response.ifHavePremiumRule == "noOk" || response.ifDateOk == "noOk"){
				 toastr.error("同类溢价规则已存在", "提示"); 
			  return;
			}
		    if(response.ifTimeOk == "noOk"){
			  toastr.error("时间范围输入错误", "提示");
			  return;
		    }
		    if(response.ifHaveOverlying == "noOk"){
			  toastr.error("时间范围存在冲突", "提示");
			  return;
		    }
		   if(response.ifHaveOverlyingWeek == "noOk1"){
			  toastr.error("【星期一】的时间范围存在冲突", "提示");
			  return;
		    }
		   if(response.ifHaveOverlyingWeek == "noOk2"){
			  toastr.error("【星期二】的时间范围存在冲突", "提示");
			  return;
		    }
		   if(response.ifHaveOverlyingWeek == "noOk3"){
			  toastr.error("【星期三】的时间范围存在冲突", "提示");
			  return;
		    }
		   if(response.ifHaveOverlyingWeek == "noOk4"){
			  toastr.error("【星期四】的时间范围存在冲突", "提示");
			  return;
		    }
		   if(response.ifHaveOverlyingWeek == "noOk5"){
			  toastr.error("【星期五】的时间范围存在冲突", "提示"); 
			  return;
		    }
		   if(response.ifHaveOverlyingWeek == "noOk6"){
			  toastr.error("【星期六】的时间范围存在冲突", "提示");
			  return;
		    }
		   if(response.ifHaveOverlyingWeek == "noOk7"){
			  toastr.error("【星期天】的时间范围存在冲突", "提示");
			  return;
		    }
			  toastr.options.onHidden = function() {
				  window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") +"PubPremiumRule/Index";
            	}
			  if(id == null || id == ""){
				  toastr.success("规则新增成功", "提示");
			  }else{
				  toastr.success("规则修改成功", "提示");  
			  }
		},
		error: function (xhr, status, error) {
			return;
		}
 });
}
//获取修改的数据
function getModify(){
	var aaid = $("#aaid").val();
	var aaruletype = $("#aaruletype").val();
	var data = {
			id : aaid,
			ruletype :aaruletype
		};
		$.ajax({
			type : 'POST',
			dataType : 'json',
			async: false,
			url : "PubPremiumRule/modifyDate;",
			data : JSON.stringify(data),
			contentType : 'application/json; charset=utf-8',
			async : false,
			success : function(result) {
			  //填充数据
				$("#cityname").val(result.cityCode);
				$("#rulename").val(result.rulename);
				$("#cartype").val(result.cartype);
				$("#rulename").attr("readonly","readonly");
				/*var osel=document.getElementById("cartype");
				  osel.onfocus=function(){
				    this.defaultIndex=this.selectedIndex;
				  }
				  osel.onchange=function(){
				    this.selectedIndex=this.defaultIndex;
				  }*/
				  $("#cityname").attr("disabled","disabled");
				  $("#cartype").attr("disabled","disabled");
			 //判断是什么规则类型 0 星期,1日期
				if(result.ruletype == "0"){
					$("#week").attr("checked","checked");
					$("#date").removeAttr("checked");
					$("input:radio:not([checked])").attr("disabled","disabled");
					$("#weekTable").show();
			    	$("#dateTable").hide();
			    	//填充数据
			    	if(result.week.length == 0){
			    		return;
			    	}else{
			    		//增加table列
			    		modifyWeek(result.week);
			    	}
				}else{
					$("#date").attr("checked","checked");
					$("#week").removeAttr("checked");
					$("input:radio:not([checked])").attr("disabled","disabled");
					$("#weekTable").hide();
			    	$("#dateTable").show();
			    	if(result.date.length== 0){
			    		return;
			    	}else{
			    		$("#startdt").val(result.startdt);
			    		$("#enddt").val(result.enddt);
			    		//增加table列
			    		modifyDate(result.date);
			    	}
				}
			}
		});
}
function modifyDate(date){
//	$("#startdt").val(startdt.)
	var count = 0;
	for(var i=0;i<date.length;i++){
		  var next;
		  if(count == 0){
			$("#dateS").val(date[i].startdt);
			$("#dateE").val(date[i].enddt);
			$("#dateOk").val(date[i].premiumrate);
			}
			if(count == 1){
			  document.getElementById("addDateOne").click();
			  //获取他下一行的按钮id
			  next = $("#addDateOne").parent().parent().next()[0].lastChild.children[0].id;
			  var allInput = $("#addDateOne").parent().parent().next().find("input");
			  allInput[0].value = date[i].startdt;
			  allInput[1].value = date[i].enddt;
			  allInput[2].value = date[i].premiumrate;
			}
			if(count > 1){
//				 document.getElementById("#"+next).click();
				$("#"+next).click();
				 next = $("#"+next).parent().parent().next()[0].lastChild.children[0].id;
				 var allInput = $("#"+next).parent().parent().find("input");
				  allInput[0].value = date[i].startdt;
				  allInput[1].value = date[i].enddt;
				  allInput[2].value = date[i].premiumrate;
			}
			count = count +1;
    }
}
//增加weektable列
function modifyWeek(week){
	//每个星期有多少条数据
	var oneCount = 0;
	var towCount = 0;
	var threeCount = 0;
	var fourCount = 0;
	var fiveCount = 0;
	var sixCount = 0;
	var sevenCount = 0;
	//循环遍历星期的数据
	for(var i=0;i<week.length;i++){
		if(week[i].weekday == "1"){
			var next;
		  if(oneCount == 0){
			$("#oneWeek").attr("checked","checked");
			$("#onetimeS").val(week[i].startdt);
			$("#onetimeE").val(week[i].enddt);
			$("#oneOk").val(week[i].premiumrate);
			}
			if(oneCount == 1){
			  document.getElementById("addOne").click();
			  //获取他下一行的按钮id
			  next = $("#addOne").parent().parent().next()[0].lastChild.children[0].id;
			  var allInput = $("#addOne").parent().parent().next().find("input");
			  allInput[0].value = week[i].startdt;
			  allInput[1].value = week[i].enddt;
			  allInput[2].value = week[i].premiumrate;
			}
			if(oneCount > 1){
//				 document.getElementById("#"+next).click();
				$("#"+next).click();
				 next = $("#"+next).parent().parent().next()[0].lastChild.children[0].id;
				 var allInput = $("#"+next).parent().parent().find("input");
				  allInput[0].value = week[i].startdt;
				  allInput[1].value = week[i].enddt;
				  allInput[2].value = week[i].premiumrate;
			}
			 oneCount = oneCount +1;
		}
		if(week[i].weekday == "2"){
			var next;
			if(towCount == 0){
			$("#towWeek").attr("checked","checked");
			$("#towtimeS").val(week[i].startdt);
			$("#towtimeE").val(week[i].enddt);
			$("#towOk").val(week[i].premiumrate);
			}
			if(towCount == 1){
			  document.getElementById("addTow").click();
			  //获取他下一行的按钮id
			  next = $("#addTow").parent().parent().next()[0].lastChild.children[0].id;
			  var allInput = $("#addTow").parent().parent().next().find("input");
			  allInput[0].value = week[i].startdt;
			  allInput[1].value = week[i].enddt;
			  allInput[2].value = week[i].premiumrate;
			}
			if(towCount > 1){
//				 document.getElementById("#"+next).click();
				$("#"+next).click();
				 next = $("#"+next).parent().parent().next()[0].lastChild.children[0].id;
				 var allInput = $("#"+next).parent().parent().find("input");
				  allInput[0].value = week[i].startdt;
				  allInput[1].value = week[i].enddt;
				  allInput[2].value = week[i].premiumrate;
			}
			towCount = towCount +1;
		}
		if(week[i].weekday == "3"){
			var next;
			if(threeCount == 0){
			$("#threeWeek").attr("checked","checked");
			$("#threetimeS").val(week[i].startdt);
			$("#threetimeE").val(week[i].enddt);
			$("#threeOk").val(week[i].premiumrate);
			}
			if(threeCount == 1){
			  document.getElementById("addThree").click();
			  //获取他下一行的按钮id
			  next = $("#addThree").parent().parent().next()[0].lastChild.children[0].id;
			  var allInput = $("#addThree").parent().parent().next().find("input");
			  allInput[0].value = week[i].startdt;
			  allInput[1].value = week[i].enddt;
			  allInput[2].value = week[i].premiumrate;
			}
			if(threeCount > 1){
//				 document.getElementById("#"+next).click();
				$("#"+next).click();
				 next = $("#"+next).parent().parent().next()[0].lastChild.children[0].id;
				 var allInput = $("#"+next).parent().parent().find("input");
				  allInput[0].value = week[i].startdt;
				  allInput[1].value = week[i].enddt;
				  allInput[2].value = week[i].premiumrate;
			}
			threeCount = threeCount +1;
		}
		if(week[i].weekday == "4"){
			var next;
			if(fourCount == 0){
			$("#fourWeek").attr("checked","checked");
			$("#fourtimeS").val(week[i].startdt);
			$("#fourtimeE").val(week[i].enddt);
			$("#fourOk").val(week[i].premiumrate);
			}
			if(fourCount == 1){
			  document.getElementById("addFour").click();
			  //获取他下一行的按钮id
			  next = $("#addFour").parent().parent().next()[0].lastChild.children[0].id;
			  var allInput = $("#addFour").parent().parent().next().find("input");
			  allInput[0].value = week[i].startdt;
			  allInput[1].value = week[i].enddt;
			  allInput[2].value = week[i].premiumrate;
			}
			if(fourCount > 1){
//				 document.getElementById("#"+next).click();
				$("#"+next).click();
				 next = $("#"+next).parent().parent().next()[0].lastChild.children[0].id;
				 var allInput = $("#"+next).parent().parent().find("input");
				  allInput[0].value = week[i].startdt;
				  allInput[1].value = week[i].enddt;
				  allInput[2].value = week[i].premiumrate;
			}
			fourCount = fourCount +1;
		}
		if(week[i].weekday == "5"){
			var next;
			if(fiveCount == 0){
			$("#fiveWeek").attr("checked","checked");
			$("#fivetimeS").val(week[i].startdt);
			$("#fivetimeE").val(week[i].enddt);
			$("#fiveOk").val(week[i].premiumrate);
			}
			if(fiveCount == 1){
			  document.getElementById("addFive").click();
			  //获取他下一行的按钮id
			  next = $("#addFive").parent().parent().next()[0].lastChild.children[0].id;
			  var allInput = $("#addFive").parent().parent().next().find("input");
			  allInput[0].value = week[i].startdt;
			  allInput[1].value = week[i].enddt;
			  allInput[2].value = week[i].premiumrate;
			}
			if(fiveCount > 1){
//				 document.getElementById("#"+next).click();
				$("#"+next).click();
				 next = $("#"+next).parent().parent().next()[0].lastChild.children[0].id;
				 var allInput = $("#"+next).parent().parent().find("input");
				  allInput[0].value = week[i].startdt;
				  allInput[1].value = week[i].enddt;
				  allInput[2].value = week[i].premiumrate;
			}
			 fiveCount = fiveCount +1;
		}
		if(week[i].weekday == "6"){
			var next;
			if(sixCount == 0){
			$("#sixWeek").attr("checked","checked");
			$("#sixtimeS").val(week[i].startdt);
			$("#sixtimeE").val(week[i].enddt);
			$("#sixOk").val(week[i].premiumrate);
			}
			if(sixCount == 1){
			  document.getElementById("addSix").click();
			  //获取他下一行的按钮id
			  next = $("#addSix").parent().parent().next()[0].lastChild.children[0].id;
			  var allInput = $("#addSix").parent().parent().next().find("input");
			  allInput[0].value = week[i].startdt;
			  allInput[1].value = week[i].enddt;
			  allInput[2].value = week[i].premiumrate;
			}
			if(sixCount > 1){
//				 document.getElementById("#"+next).click();
				$("#"+next).click();
				 next = $("#"+next).parent().parent().next()[0].lastChild.children[0].id;
				 var allInput = $("#"+next).parent().parent().find("input");
				  allInput[0].value = week[i].startdt;
				  allInput[1].value = week[i].enddt;
				  allInput[2].value = week[i].premiumrate;
			}
			sixCount = sixCount +1;
		}
		if(week[i].weekday == "7"){
			var next;
			if(sevenCount == 0){
			$("#sevenWeek").attr("checked","checked");
			$("#seventimeS").val(week[i].startdt);
			$("#seventimeE").val(week[i].enddt);
			$("#sevenOk").val(week[i].premiumrate);
			}
			if(sevenCount == 1){
			  document.getElementById("addSeven").click();
			  //获取他下一行的按钮id
			  next = $("#addSeven").parent().parent().next()[0].lastChild.children[0].id;
			  var allInput = $("#addSeven").parent().parent().next().find("input");
			  allInput[0].value = week[i].startdt;
			  allInput[1].value = week[i].enddt;
			  allInput[2].value = week[i].premiumrate;
			}
			if(sevenCount > 1){
//				 document.getElementById("#"+next).click();
				$("#"+next).click();
				 next = $("#"+next).parent().parent().next()[0].lastChild.children[0].id;
				 var allInput = $("#"+next).parent().parent().find("input");
				  allInput[0].value = week[i].startdt;
				  allInput[1].value = week[i].enddt;
				  allInput[2].value = week[i].premiumrate;
			}
			sevenCount = sevenCount +1;
		}
	}
}
//去除前面多余的0
/*function overFormat(obj) {
	if(/^0+\d+\.?\d*.*$/.test(obj.value)){
		obj.value = obj.value.replace(/^0+(\d+\.?\d*).*$/, '$1');
	}
}*/
function overFormat(obj) {
if(obj.value>=10){
	obj.value = obj.value.replace(obj.value, '');
}
}
function pageOnlyNumber(evt){ 
	 evt = (evt) ? evt : window.event; 
	 keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
	     if((keyCode <= 48 || keyCode > 57) && keyCode != 8 && keyCode != 46)
	 {
	        if(window.event){
	         
	          window.event.returnValue = false; 
	        }
	         else{
	         evt.preventDefault(); 
	        }
	 } 
}
function clearNoNum(obj) {
	 obj.value = obj.value.replace(/[^\d.]/g, "");//清除“数字”和“.”以外的字符
	 obj.value = obj.value.replace(/^\./g, "");//验证第一个字符是数字而不是.
	 obj.value = obj.value.replace(/\.{2,}/g, ".");//只保留第一个. 清除多余的.
	 obj.value = obj.value.replace(".", "$#$").replace(/\./g,"").replace("$#$", ".");
}
function clearNoNum2(obj) {
	clearNoNum(obj);
	//只能输入1个小数
	obj.value = obj.value.replace(/^(\-)*(\d)\.(\d).*$/, '$1$2.$3');
}



