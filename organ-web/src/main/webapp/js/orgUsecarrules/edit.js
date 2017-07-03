var rulesObj = {
	allRules: "",
	editRules: ""
}
var yc = window.location.search;
var rulename = decodeURI(yc.split("=")[1]);

$(document).ready(function() {
	loadAllCarRules();
});

/**
 * 获取所有用车规则数据
 */
function loadAllCarRules() {
	$.ajax({
		type : 'get',
		url : 'OrgUsecarrules/GetALLJson',
		dataType: "json",
		async : false,
		contentType: "application/json; charset=utf-8",
		success : function(data) {
			rulesObj.allRules = data;
			loadEditCarRules();
		}
	});
}
	
/**
 * 获取已经设置的用车规则数据
 */
function loadEditCarRules() {
	//解决ie中文乱码
	var url = encodeURI('OrgUsecarrules/GetEditJson?ruleName=' + rulename);
	$.ajax({
		type : 'get',
		url : url,
		dataType: "json",
		async : false,
		contentType: "application/json; charset=utf-8",
		success : function(data) {
			rulesObj.editRules = data;
			initFormData();
		}
	});
}

/**
 * 页面元素初始化
 */
function initFormData() {
	for(var i = 1; i <= 3; i++) {
		
		if(rulesObj.allRules[i]) {
			var rulesArr = rulesObj.allRules[i];
			var modiDataTmp = {1: [], 2: [], 3: []};
			for(var j = 0; j < rulesArr.length; j++) {
				var leasescompanyid = rulesArr[j].leasescompanyid;
				var leasesCompanyName = rulesArr[j].leasesCompanyName;
//				
//				var j = {s:{'是':2}};
//				alert(j.s[是]);
//				
//				var temp = rulesObj.editRules;
//				alert( temp.rulename );
				if(validateHasRepeatValue("leasescompanyid", leasescompanyid, rulesObj.editRules[rulename][i])) {
					modiDataTmp[i].push(leasescompanyid);
					
					var htmlArr = [];
					htmlArr.push('<div class="right_box_m"><div class="right_box_s" id="right_box_' + i + leasescompanyid + '"></div>');
					
					var currentCompany = $("#ruletype" + i).children(".right_box").children(".right_box_m").children(".right_box_s").children(".select_box");

					if(modiDataTmp[i].length >= rulesObj.editRules[rulename][i].length) {
						htmlArr.push('<span class="xinzeng">添加</span>');
					}
					
					if(modiDataTmp[i].length <= rulesObj.editRules[rulename][i].length && rulesObj.editRules[rulename][i].length > 1) {
						htmlArr.push('<span class="font-red">删除</span>');
					}
					
					htmlArr.push('</div>');
					$(".company:eq(" + (i - 1) + ")").attr("checked", "checked");
					$("#ruletype" + i).append(htmlArr.join(""));
					$("#ruletype" + i).show();
					
					
					var ruleHtmlArr = [];
					var cartypeHtmlArr = [];
					ruleHtmlArr.push('<div class="select_box"><input class="select_val" readonly data-value="');
					ruleHtmlArr.push(leasescompanyid);
					ruleHtmlArr.push('" value="');
					ruleHtmlArr.push(leasesCompanyName);
					ruleHtmlArr.push('">');
					ruleHtmlArr.push('<ul class="select_content">');
					
					for(var k = 0; k < rulesArr.length; k ++) {
						var rules = rulesArr[k];
						
						ruleHtmlArr.push('<li data-value="');
						ruleHtmlArr.push(rules.leasescompanyid);
						ruleHtmlArr.push('">');
						ruleHtmlArr.push(rules.leasesCompanyName);
						ruleHtmlArr.push('</li>');
						
						if(rules.leasescompanyid == leasescompanyid) {
							for(var m = 0; m < rules.cartype.length; m ++) {
								var cartype = rules.cartype[m];
								if(m% 3 == 0){
									var margin=0;
								}else{
									var margin=10;
								}
								cartypeHtmlArr.push('<div class="lei ');
								if(validateHasRepeatModels("vehiclemodelid", cartype.vehiclemodelid, rulesObj.editRules[rulename][i])) {
									cartypeHtmlArr.push(' on');
								}
								cartypeHtmlArr.push('"');
								cartypeHtmlArr.push(' style="margin-left: '+margin+'px;" ');
								cartypeHtmlArr.push(' id="');
								cartypeHtmlArr.push(cartype.vehiclemodelid);
								cartypeHtmlArr.push('">');
								cartypeHtmlArr.push(cartype.vehicleModelsName);
								cartypeHtmlArr.push('</div>');
							}
						}
					}
					
					ruleHtmlArr.push('</ul></div>');
					
					$("#right_box_" + i + leasescompanyid).append(ruleHtmlArr.concat(cartypeHtmlArr).join(""));
				}
			}
			
		}
	}
	
}


// 用户重新选择按钮
$(".select_content>li").live("click", function() {
	var tindex = $(this).parents(".right_box_m").index();
	var n = $(this).parents(".right_box").children(".right_box_m").length;
	var sli = $(this).index() + n;
	var id = $(this).parents(".right_box").attr("id");
	var rulesType = id.substring(8, id.length);
	var thisid = $(this).attr("data-value");
	$(this).parents(".right_box_m").find(".lei").remove();
	var nthis = $(this);
	
	var rulesArr = rulesObj.allRules[rulesType];
	
	for(var j = 0; j < rulesArr.length; j++) {
		var leasescompanyid = rulesArr[j].leasescompanyid;
		var leasesCompanyName = rulesArr[j].leasesCompanyName;
		
		if(leasescompanyid == thisid) {
			var cartypeArr = rulesArr[j].cartype;
			
			var cartypeHtmlArr = [];
			for(var k = 0; k < cartypeArr.length; k ++) {
				if(k% 3 == 0){
					var margin=0;
				}else{
					var margin=10;
				}
				cartypeHtmlArr.push('<div class="lei ');
				cartypeHtmlArr.push('"');
				cartypeHtmlArr.push(' style="margin-left: '+margin+'px;" ');
				cartypeHtmlArr.push(' id="');
				cartypeHtmlArr.push(cartypeArr[k].vehiclemodelid);
				cartypeHtmlArr.push('">');
				cartypeHtmlArr.push(cartypeArr[k].vehicleModelsName);
				cartypeHtmlArr.push('</div>');
			}
			
			nthis.parents(".right_box_s").append(cartypeHtmlArr.join(""));
		}
	}

});


// 新增按钮操作
$(".right_box .xinzeng").live('click', function() {
	var id = $(this).parents(".right_box").attr("id");
	var n = $(this).parents(".right_box").children(".right_box_m").length;
	var rulesType = id.substring(8, id.length);
	xthis = $(this).parents(".right_box");
	
	var currentCompany = xthis.children(".right_box_m").children(".right_box_s").children(".select_box");
	
	var rulesArr = rulesObj.allRules[rulesType];
	
	if(currentCompany.length == rulesArr.length) return;
	
	for(var j = 0; j < rulesArr.length; j++) {
		var leasescompanyid = rulesArr[j].leasescompanyid;
		var leasesCompanyName = rulesArr[j].leasesCompanyName;
		
		var cartypeArr = rulesArr[j].cartype;
		
		var cartypeHtmlArr = [];
		cartypeHtmlArr.push('<div class="right_box_m"><div class="right_box_s"><div class="select_box">');
		
		cartypeHtmlArr.push('<input class="select_val" readonly data-value="');
		cartypeHtmlArr.push(leasescompanyid);
		cartypeHtmlArr.push('" value="');
		cartypeHtmlArr.push(leasesCompanyName);
		cartypeHtmlArr.push('">');
		cartypeHtmlArr.push('<ul class="select_content">');
		
		for(var k = 0; k < rulesArr.length; k++) {
			cartypeHtmlArr.push('<li data-value="');
			cartypeHtmlArr.push(rulesArr[k].leasescompanyid);
			cartypeHtmlArr.push('">');
			cartypeHtmlArr.push(rulesArr[k].leasesCompanyName);
			cartypeHtmlArr.push('</li>');
		}
		
		cartypeHtmlArr.push('</ul>');
		cartypeHtmlArr.push('</div>');
		
		
		for(var k = 0; k < cartypeArr.length; k ++) {
			if(k% 3 == 0){
				var margin=0;
			}else{
				var margin=10;
			}
			cartypeHtmlArr.push('<div class="lei ');
			cartypeHtmlArr.push('"');
			cartypeHtmlArr.push(' style="margin-left: 0px;" ');
			cartypeHtmlArr.push(' id="');
			cartypeHtmlArr.push(cartypeArr[k].vehiclemodelid);
			cartypeHtmlArr.push('">');
			cartypeHtmlArr.push(cartypeArr[k].vehicleModelsName);
			cartypeHtmlArr.push('</div>');
		}
		
		cartypeHtmlArr.push('</div>');
		/**
		if(currentCompany.length < (rulesArr.length - 1)) {
			cartypeHtmlArr.push('<span class="xinzeng">添加</span>');
		}
		*/
		if($(this).next().attr("class")!=="font-red"){
            $(this).before('<span class="font-red">删除</span>');
        }
		$(this).remove();
		cartypeHtmlArr.push('<span class="xinzeng">添加</span>');
		cartypeHtmlArr.push('<span class="font-red">删除</span></div>');
		console.info(cartypeHtmlArr.join(""));
		xthis.append(cartypeHtmlArr.join(""));
		
		break;
	}
	
});


// 删除按钮操作
$(".font-red").live("click", function() {
	var n = $(this).parents(".right_box").children(".right_box_m").length;
	var tindex = $(this).parent(".right_box_m").index();
	if(n==2&&tindex==0){
        $(this).parents(".right_box").find(".font-red:eq(1)").remove();
    }
	if(tindex===n-1){
        var m=n-2;
        $(this).parents(".right_box").find(".font-red:eq("+m+")").before('<span class="xinzeng">添加</span>');
        if(n==2){
            $(this).parents(".right_box").find(".font-red:eq("+m+")").remove();
        }
    }
	$(this).parent(".right_box_m").remove();
});


// 是否checked,是选中的话显示选择框
$(".right>input").click(function() {
	var n = $(this).val();
	
	var id = $("#ruletype" + n).attr("id");
	var rulesType = id.substring(8, id.length);
	
	var currentCompany = $("#ruletype" + n).children(".right_box_m").children(".right_box_s").children(".select_box");
	
	var rulesArr = rulesObj.allRules[rulesType];
	
	if(currentCompany.length < 1 && currentCompany.length < rulesArr.length) {
		for(var j = 0; j < rulesArr.length; j++) {
			var leasescompanyid = rulesArr[j].leasescompanyid;
			var leasesCompanyName = rulesArr[j].leasesCompanyName;
			
			var cartypeArr = rulesArr[j].cartype;
			
			var cartypeHtmlArr = [];
			cartypeHtmlArr.push('<div class="right_box_m"><div class="right_box_s"><div class="select_box">');
			
			cartypeHtmlArr.push('<input class="select_val" readonly data-value="');
			cartypeHtmlArr.push(leasescompanyid);
			cartypeHtmlArr.push('" value="');
			cartypeHtmlArr.push(leasesCompanyName);
			cartypeHtmlArr.push('">');
			cartypeHtmlArr.push('<ul class="select_content">');
			
			for(var k = 0; k < rulesArr.length; k++) {
				cartypeHtmlArr.push('<li data-value="');
				cartypeHtmlArr.push(rulesArr[k].leasescompanyid);
				cartypeHtmlArr.push('">');
				cartypeHtmlArr.push(rulesArr[k].leasesCompanyName);
				cartypeHtmlArr.push('</li>');
			}
			
			cartypeHtmlArr.push('</ul>');
			cartypeHtmlArr.push('</div>');
			
			
			for(var k = 0; k < cartypeArr.length; k ++) {
				if(k% 3 == 0){
					var margin=0;
				}else{
					var margin=10;
				}
				cartypeHtmlArr.push('<div class="lei ');
				cartypeHtmlArr.push('"');
				cartypeHtmlArr.push(' style="margin-left: '+margin+'px;" ');
				cartypeHtmlArr.push(' id="');
				cartypeHtmlArr.push(cartypeArr[k].vehiclemodelid);
				cartypeHtmlArr.push('">');
				cartypeHtmlArr.push(cartypeArr[k].vehicleModelsName);
				cartypeHtmlArr.push('</div>');
			}
			
			cartypeHtmlArr.push('</div>');
			if(currentCompany.length < (rulesArr.length - 1)) {
				cartypeHtmlArr.push('<span class="xinzeng">添加</span>');
			}
			
			 $("#ruletype" + n).append(cartypeHtmlArr.join(""));
			
			break;
		}
		
	}
	
	
	if ($(this).is(':checked')) {
		$("#ruletype" + n).show();
	} else {
		$("#ruletype" + n).hide();
	}
	
});

// 点击返回、取消两个按钮之后的操作
$(".btn_grey,.btn_green").click(function() {
	Zconfirm("确定放弃修改规则？", function() {
		$(window.parent.document).find(".pop_index").hide();
		location.href = base+"OrgUsecarrules/Index";
	});
});

// 车类型选择
$(".right_box_s .lei").live('click', function() {
	$(this).toggleClass("on");
});

// 跪着得出的最后数组组合
$(".btn_red").click(function() {
	var arrbox = {};
	for (i = 0; i < 3; i++) {
		if ($(".company:eq(" + i + ")").is(':checked')) {
			id = $(".company:eq(" + i + ")").val();
			var n = $(".right_box:eq(" + i + ")").children(".right_box_m").length;
			var lbox = [];
			for (j = 0; j < n; j++) {
				var obj = $(".right_box:eq(" + i + ")")
						.children(".right_box_m:eq(" + j + ")");
				var m = obj.find(".on").length;
				var value = obj.find(".select_val").attr("data-value");
				var modelbox = obj.find(".on:eq(0)").attr("id");
				for (h = 1; h < m; h++) {
					var modelId = obj.find(".on:eq(" + h + ")").attr("id");
					modelbox += "," + modelId;
				}

				lbox.push({
							leasesCompanyId : value,
							modelId : modelbox
						});
			}
			arrbox[id] = lbox;
		}
	}
	if (arrbox == '' || arrbox == null) {

	}
	if(JSON.stringify(arrbox) == "{}"){ 
		toastr.error("用车方式不能为空","提示");
		return;
	}
	if(arrbox[1] != undefined){
		for(var i=0;i<arrbox[1].length;i++){
			if(arrbox[1][i].modelId == undefined){
				toastr.error("用车方式不能为空","提示");
				return;
			}
		}
	}
	if(arrbox[2] != undefined){
		for(var i=0;i<arrbox[2].length;i++){
			if(arrbox[2][i].modelId == undefined){
				toastr.error("用车方式不能为空","提示");
				return;
			}
		}
	}
	if(arrbox[3] != undefined){
		for(var i=0;i<arrbox[3].length;i++){
			if(arrbox[3][i].modelId == undefined){
				toastr.error("用车方式不能为空","提示");
				return;
			}
		}
	}
	if(!validateForm(arrbox)) {
		toastr.error("存在重复的服务车企", "提示");
		return;
	}

	if ($("#yc").val() != '') {
		arrbox["ruleName"] = $("#yc").val();
		arrbox["ruleYName"] = $("#ycyuan").val();
		$.ajax({
					type : 'post',
					url : 'OrgUsecarrules/Update',
					data : JSON.stringify(arrbox),
					dataType : 'json',
					contentType : "application/json; charset=utf-8",
					success : function(status) {
						if (status.ResultSign == "Successful") {
							var message = status.MessageKey == null
									? status
									: status.MessageKey;
							toastr.options.onHidden = function() {
								window.location.href = base+"OrgUsecarrules/Index";
							}
							toastr.error(message, "提示");
						} else {
							var message = status.MessageKey == null
									? status
									: status.MessageKey;
							toastr.error(message, "提示");
						}
					}
				});
	} else {
		toastr.error("规则名称不能空", "提示");
	}
});

/**
 * 表单验重
 * @param {} arrbox
 * @return {Boolean}
 */
function validateForm(arrbox) {
	for(var i in arrbox) {
		var rules = arrbox[i];
		var repeatData = [];
		for(var j in rules) {
			var companyid = rules[j].leasesCompanyId;
			repeatData.push(companyid);
			
			if(isRepeat(repeatData)) {
				return false;
			}
			
		}
		
	}
	return true;
}

/**
 * 检查数据是否存在于修改数据集中
 * @param {} key
 * @param {} value
 * @param {} modiData
 * @return {}
 */
function validateHasRepeatValue(key, value, modiData) {
	var result = false;
	for(var i in modiData) {
		var data = modiData[i];
		if(data[key] == value) {
			result = true; 
			break;
		}
	}
	
	return result;
}

/**
 * 获取重复的数据集
 * @param {} key
 * @param {} value
 * @param {} modiData
 * @return {}
 */
function validateHasRepeatModels(key, value, modiData) {
	var result = false;
	for(var i in modiData) {
		var data = modiData[i].vehiclemodels;
		
		for(var j in data) {
			if(data[j][key] == value) {
				result = true;
				break;
			}
		}
	}
	
	return result;
}

function isRepeat(arr) {
    var hash = {};
    for(var i in arr) {
        if(hash[arr[i]])
        {
            return true;
        }
        hash[arr[i]] = true;
    }
    return false;
}