	var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	validateForm();
	dateFormat();
	initSelectOrgan();
});

/**
 * 表单校验
 */
function validateForm() {
	$("#selectForm").validate({
		rules: {
			organId: {required: true},
			city:{required: true},
			startTime:{required: true,date: true,dateISO: true},
			endTime:{required: true,date: true,dateISO: true}
		},
		messages: {
			organId: {required: "请输入机构名称"},
			city: {required: "请输入城市名称"},
			startTime: {required: "请输入起始时间",date: "请输入合法的日期",dateISO: "请输入合法的日期 (ISO)"},
			endTime: {required: "请输入结束时间",date: "请输入合法的日期",dateISO: "请输入合法的日期 (ISO)"}
		}
	})
}

function dateFormat() {
	$('.searchDate').datetimepicker({
        format: "yyyy/mm/dd", //选择日期后，文本框显示的日期格式
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

function initSelectOrgan() {
	$("#city").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "IndividualAccountRules/GetInsertCityList",
			dataType : 'json',
			data : function(term, page) {
				$(".datetimepicker").hide();
				return {
					cityName: term
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
 * 选择机构名称
 */
function changeOrgan() {
	var organId = $("#organId").val();
	if (organId == "") {
		$("#dateVisual").html("");
		$("#dateDiv").hide();
	} else {
		    var url = "IndividualAccountRules/GetRulesDateByOrgan/" + organId + "?datetime=" + new Date().getTime();
			$.ajax({
				type: 'GET',
				dataType: 'json',
				url: url,
				contentType: 'application/json; charset=utf-8',
				async: false,
				success: function (json) {
					
					if (json.length == 0) {
						$("#dateVisual").html("");
						$("#dateDiv").hide();
					} else {
						var date = ""
						for (var i=0;i<json.length;i++) {
							date += "<span class='font_green'>注：当前已存在启用的有效期" + changeToDate(json[i].starttime) + "至" + changeToDate(json[i].endtime) +"的规则</span><br>" ;
						}
						$("#dateVisual").html(date);
						$("#dateDiv").show();
					}

				}
			});
	}
}

/**
 * 毫秒转日期
 * 
 * @param data
 * @returns {String}
 */
function changeToDate(data) {
	var myDate = new Date(data);
	var month = "";
	var date = "";
	var change = "";
	change += myDate.getFullYear() + "/";

	if (myDate.getMonth() < 9) {
		month = "0" + (myDate.getMonth() + 1);
	} else {
		month = (myDate.getMonth() + 1);
	}
	change += month + "/";
	if (myDate.getDate() < 10) {
		date = "0" + myDate.getDate();
	} else {
		date = myDate.getDate();
	}
	change += date;
	return change;
}


var citymap = {};
/**
 * 增加城市
 */
function addCity() {
	var city = $("#city").val();
	var name = $("#s2id_city .select2-chosen").text();
	
	if (city != "") {
		
		if (!citymap.hasOwnProperty(city)) {
			citymap[city] = city;

			var $ul = $("#cityName");
			$ul.append("<li data-value='"+city+"' id='li"+city+"'>"+name+"</li>");

			$.ajax({
				type: "GET",
				url:"IndividualAccountRules/GetStandardRulesByCity/" + city + "?datetime=" + new Date().getTime(),
				cache: false,
				data: null,
				success: function (json) {

					var html='';
					html += '<div class="col-12 grey_c"><input type="checkbox" id="checkCityAll" name="checkCityAll" value="' + city + '" onclick="checkCityAllHander(this)" /><label style="font-size:20px;font-weight:bold;line-height:170%;">' + name + '</label><br></div>';

					var html1 = '';
					for (var i=0;i<json.length;i++) {
						if (json[i].rulesTypeName == "约车") {
							if (html1 == "") {
								html1 += '<div class="col-4 radius" style="position:relative;min-height:225px;margin-top:0px;border-top:none;padding-top:10px;">';
								html1 += '<div style="text-align:left;"><div style="height:1px;position:absolute;top:39px;left:0;border-bottom:1px solid #ccc;width:100%;"></div><label id="title" style="font-size:18px;;margin-left:26%">约车</label><input name="carqx" type="checkbox" id="qx1" style="margin-left:15px; margin-right:6px;" value="' + city + '" onclick="qx1ClickHander(this)" />全选<input style="margin-left:15px; margin-right:6px;" name="carfx" type="checkbox" id="fx1" value="' + city + '" style="margin-bottom:0px;" onclick="fx1ClickHander(this)" />反选<br>';
								html1 += '<div style="min-height:200px;max-height:200px;overflow-y:auto;">';
							}
							html1 += '<input type="checkbox" style="margin-left:26%;margin-right:6px;" id="check1" name="check1' + city + '" value="' + json[i].id +'" cityvalue="' + city + '" qxvalue="ruleqx" onclick="onClickHander(this)">' + json[i].carTypeName +  '</input><br>';
						}
					}
					if (html1 != "") {
						html1 += '</div> </div> </div>';
					}
					html += html1;
					
					var html2 = '';
					for (var i=0;i<json.length;i++) {
						if (json[i].rulesTypeName == "接机") {
							if (html2 == "") {
								html2 += '<div class="col-4 radius" style="position:relative;min-height:225px;margin-top:0px;border-top:none;padding-top:10px;">';
								html2 += '<div style="text-align:left;"><div style="height:1px;position:absolute;top:39px;left:0;border-bottom:1px solid #ccc;width:100%;"></div><label id="title" style="font-size:18px;;margin-left:26%">接机</label><input name="carqx" type="checkbox" id="qx2" style="margin-left:15px; margin-right:6px;" value="' + city + '" onclick="qx2ClickHander(this)" />全选<input name="carfx" type="checkbox" style="margin-left:15px; margin-right:6px;" id="fx2" value="' + city + '" style="margin-bottom:0px;" onclick="fx2ClickHander(this)" />反选<br>';
								html2 += '<div style="min-height:200px;max-height:200px;overflow-y:auto;">';
							}
							html2 += '<input type="checkbox" style="margin-left:26%;margin-right:6px;" id="check2" name="check2' + city + '" value="' + json[i].id +'" cityvalue="' + city + '" qxvalue="ruleqx" onclick="onClickHander(this)">' + json[i].carTypeName +  '</input><br>';
						}
					}
					if (html2 != "") {
						html2 += '</div> </div> </div>';
					}
					html += html2;
					
					var html3 = '';
					for (var i=0;i<json.length;i++) {
						if (json[i].rulesTypeName == "送机") {
							if (html3 == "") {
								html3 += '<div class="col-4 radius" style="position:relative;min-height:225px;margin-top:0px;border-top:none;padding-top:10px;">';
								html3 += '<div style="text-align:left;"><div style="height:1px;position:absolute;top:39px;left:0;border-bottom:1px solid #ccc;width:100%;"></div><label id="title" style="font-size:18px;;margin-left:26%">送机</label><input name="carqx" type="checkbox" id="qx3" style="margin-left:15px; margin-right:6px;" value="' + city + '" onclick="qx3ClickHander(this)" />全选<input name="carfx" style="margin-left:15px; margin-right:6px;" type="checkbox" id="fx3" value="' + city + '" style="margin-bottom:0px;" onclick="fx3ClickHander(this)" />反选<br>';
								html3 += '<div style="min-height:200px;max-height:200px;overflow-y:auto;">';
							}
							html3 += '<input type="checkbox" style="margin-left:26%;margin-right:6px;" id="check3" name="check3' + city + '" value="' + json[i].id +'" cityvalue="' + city + '" qxvalue="ruleqx" onclick="onClickHander(this)">' + json[i].carTypeName +  '</input><br>';
						}
					}
					if (html3 != "") {
						html3 += '</div> </div> </div>';
					}
					html += html3;

                    if ($("#"+city+"Div").length == 0) {
						html = '<div class="row" style="margin:0 auto;" id="'+city+'Div">' + html + '</div>';
						$("#rulesDiv").append(html);
					} else {
						$("#"+city+"Div").append(html);
					}
                    
                    $("#productqxfxDiv").show();
                    $("#buttonDiv").show();
					
				},
				error: function (xhr, status, error) {
					return;
				}
		    });
			
		}

	}

}

var rulemap={};
/**
 * 勾选服务车型
 */
function onClickHander(obj) {
	var tempId = "";
	if (obj.checked) {
		tempId = obj.value;
		rulemap[tempId] = tempId;
	} else {
		tempId = obj.value;
		delete rulemap[tempId];
		
		
		deleteCarCheck(obj);
		
	}
}

/**
 * 任一服务车型去掉后
 */
function deleteCarCheck(obj) {
	var a = document.getElementsByTagName("input");
	for(var i = 0;i < a.length; i++) {
		if (a[i].type == "checkbox" && a[i].name == "carqx" && obj.getAttribute("cityvalue") == a[i].value && a[i].id == "qx" + obj.id.charAt(5)) {
			a[i].checked = false;
		}
	}
	$("#qx").attr("checked",false);
}


/**
 * （约车、接机、送机）全选
 */
function qxNumClickHander(obj,num) {
	var a = document.getElementsByTagName("input");
	var tempId = "";
	var cityCheck = true;
	if (obj.checked) {
		for(var i = 0;i < a.length; i++) {
			if(a[i].name == "check"+num+obj.value && a[i].type == "checkbox" && a[i].checked == false) {
				a[i].checked = true;
				tempId = a[i].value;
				rulemap[tempId] = tempId;
			}
			
			if(a[i].name == "carfx" && a[i].type == "checkbox" && a[i].value == obj.value && a[i].id == "fx" + num ) {
				a[i].checked = false;
			}
			
		}
	} else {
		for(var i = 0;i < a.length; i++) {
			if(a[i].name == "check"+num+obj.value && a[i].type == "checkbox" && a[i].checked == true) {
				a[i].checked = false;
				tempId = a[i].value;
				delete rulemap[tempId];
				
				cityCheck = false;
			}
			
			if(a[i].name == "carfx" && a[i].type == "checkbox" && a[i].value == obj.value && a[i].id == "fx" + num ) {
				a[i].checked = false;
			}
		}
	}
	
	if (!cityCheck) {
		$("#qx").attr("checked",false);
	}

}

/**
 * （约车、接机、送机）反选
 */
function fxNumClickHander(obj,num) {
	var a = document.getElementsByTagName("input");
	var tempId = "";
	
	var cityCheck = true;
	for(var i = 0;i < a.length; i++) {
		if (a[i].name == "check"+num+obj.value && a[i].type == "checkbox") {
			if (a[i].checked == false) {
				a[i].checked = true;
				tempId = a[i].value;
				rulemap[tempId] = tempId;
			} else {
				a[i].checked = false;
				tempId = a[i].value;
				delete rulemap[tempId];
				
				cityCheck = false;
			}
		}
	}
	
	if (!cityCheck) {
		for(var j = 0;j < a.length; j++) {
			if (a[j].type == "checkbox" && a[j].name == "carqx" && obj.value == a[j].value && a[j].id == "qx" + num) {
				a[j].checked = false;
			}
		}
		$("#qx").attr("checked",false);
	}
	
}

function qx1ClickHander(obj) {
	qxNumClickHander(obj,1);
}

function fx1ClickHander(obj) {
	fxNumClickHander(obj,1);
}

function qx2ClickHander(obj) {
	qxNumClickHander(obj,2);
}

function fx2ClickHander(obj) {
	fxNumClickHander(obj,2);
}

function qx3ClickHander(obj) {
	qxNumClickHander(obj,3);
}

function fx3ClickHander(obj) {
	fxNumClickHander(obj,3);
}

/**
 * 城市全选
 */
function checkCityAllHander(obj) {
	var a = document.getElementsByTagName("input");
	var tempId = "";
	if (obj.checked) {
		for(var i = 0;i < a.length; i++) {
			if (a[i].type == "checkbox" && a[i].attributes.length > 5 && a[i].getAttribute("cityvalue") == obj.value && a[i].checked == false) {
				a[i].checked = true;
				tempId = a[i].value;
				rulemap[tempId] = tempId;
			}
			
			if (a[i].type == "checkbox" && a[i].name == "carqx" && a[i].value == obj.value) {
				a[i].checked = true;
			}
			
			if (a[i].type == "checkbox" && a[i].name == "carfx" && a[i].value == obj.value) {
				a[i].checked = false;
			}
		}
	} else {
		for(var i = 0;i < a.length; i++) {
			if (a[i].type == "checkbox" && a[i].attributes.length > 5  && a[i].getAttribute("cityvalue") == obj.value && a[i].checked == true) {
				a[i].checked = false;
				tempId = a[i].value;
				delete rulemap[tempId];
			}
			
			if (a[i].type == "checkbox" && a[i].name == "carqx" && a[i].value == obj.value) {
				a[i].checked = false;
			}
			
			if (a[i].type == "checkbox" && a[i].name == "carfx" && a[i].value == obj.value) {
				a[i].checked = false;
			}
		}
		
		$("#qx").attr("checked",false);
	}
}


/**
 * 产品服务 全选
 */
function qxProductHander(obj) {
	var a = document.getElementsByTagName("input");
	var tempId = "";
	if (obj.checked) {
		for(var i = 0;i < a.length; i++) {
			if (a[i].type == "checkbox" && a[i].attributes.length > 6 && a[i].getAttribute("qxvalue") == "ruleqx" && a[i].checked == false) {
				a[i].checked = true;
				tempId = a[i].value;
				rulemap[tempId] = tempId;
			}
			
			if (a[i].type == "checkbox" && a[i].name == "carqx") {
				a[i].checked = true;
			}
			
			if (a[i].type == "checkbox" && a[i].name == "carfx") {
				a[i].checked = false;
			}
			
			if (a[i].type == "checkbox" && a[i].name == "checkCityAll") {
				a[i].checked = true;
			}

		}
	} else {
		for(var i = 0;i < a.length; i++) {
			if (a[i].type == "checkbox" && a[i].attributes.length > 6  && a[i].getAttribute("qxvalue") == "ruleqx" && a[i].checked == true) {
				a[i].checked = false;
				tempId = a[i].value;
				delete rulemap[tempId];
			}
			
			if (a[i].type == "checkbox" && a[i].name == "carqx") {
				a[i].checked = false;
			}
			
			if (a[i].type == "checkbox" && a[i].name == "checkCityAll") {
				a[i].checked = false;
			}
		}
	}
	
}

/**
 * 产品服务 反选
 */
/*function fxProductHander(obj) {
	var a = document.getElementsByTagName("input");
	var tempId = "";
	for(var i = 0;i < a.length; i++) {
		if (a[i].type == "checkbox" && a[i].attributes.length > 5 && a[i].attributes[5].value == "ruleqx") {
			if (a[i].checked == false) {
				a[i].checked = true;
				tempId = a[i].value;
				rulemap[tempId] = tempId;
			} else {
				a[i].checked = false;
				tempId = a[i].value;
				delete rulemap[tempId];
			}
		}
	}
}*/

/**
 * 保存
 */
function save() {
	var form = $("#selectForm");
	if(!form.valid()) return;
	
	if(!dateCheck()) return;
	
	if(!cityCheck()) return;

	if (haspro(rulemap)) {
		var rulelist = [];
		for ( var pro in rulemap) {
			if (pro) {
				rulelist.push(pro);
			}
		}

		var data = {organId:$("#organId").val(),
				    startTime:$("#startTime").val(),
				    endTime:$("#endTime").val(),
				    ruleList:rulelist
                   };
		
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: 'IndividualAccountRules/CreateIndividualAccountRules',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=utf-8',
			async: false,
			success: function (status) {
				if (status.ResultSign == "Successful") {
					var message = status.MessageKey == null ? status : status.MessageKey;
					toastr.options.onHidden = function() {
						window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "IndividualAccountRules/Index"
	            	}
					toastr.success(message, "提示"); 	
				} else {
					
				}	
			}
		});

	} else {
		toastr.error("请至少勾选一个服务车型", "提示");
		return;
	}

}

/**
 * 判断是否有勾选的服务车型
 */
function haspro(map) {
	if (!map) {
		return false;
	}
	for ( var pro in map) {
		if (!pro) {
			return false;
		}
		return true;
	}
}

/**
 * 勾选了城市复选框的check
 */
function cityCheck() {
	var a = document.getElementsByTagName("input");
	var message = "";
	for(var i = 0;i < a.length; i++) {
		
		if (a[i].type == "checkbox" && a[i].name == "checkCityAll" && a[i].checked == true) {
			var existCheck = false;
			for(var j = 0;j < a.length; j++) {
				if (a[j].type == "checkbox" && a[j].attributes.length > 5 && a[j].getAttribute("cityvalue") == a[i].value && a[j].checked == true) {
					existCheck = true;
				}
			}
			
			if (!existCheck) {
				message += a[i].nextSibling.innerText +"至少需要勾选一个服务车型<br>"
			}
		}
	}
	
	if (message != "") {
		toastr.error(message, "提示");
		return false;
	} else {
		return true;
	}
}

/**
 * 有效期限的check
 */
function dateCheck() {
	var currentDate = changeToDate(new Date);
	var message = "";
	if (currentDate > $("#startTime").val()) {
		message += "起始时间要大于等于当前服务器时间";
	}
	if ($("#startTime").val() >= $("#endTime").val()) {
		message += "结束时间要大于起始时间";
	}
	if (message != "") {
		toastr.error(message, "提示");
		return false;
	} else {
		return true;
	}
}


/**
 * 取消或返回
 */
function back() {
	$("#reminderDiv").show();
}

/**
 * 返回保存
 */
function canel() {
	$("#reminderDiv").hide();
}

/**
 * 放弃保存
 */
function saveBack() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "IndividualAccountRules/Index"
}


document.getElementById("t_button").onclick = function(e){
    e = e || window.event;
    var target = e.srcElement || e.target,
    tagName = target.tagName.toLowerCase(),
    allLi = document.getElementById("t_button").getElementsByTagName("li");
            
    for(var i=0,len=allLi.length;i<len;i++){
       if(target == allLi[i]){
          allLi[i].style.background = "#66bb6a";
          allLi[i].style.color="#FFF";
          //allLi[i].style.border="1px solid #66bb6a";
       }else{
          allLi[i].style.background = "#f8f8f8";
          allLi[i].style.color="#999999";
          //allLi[i].style.border=" 1px solid #cccccc";
       }
    }
    
    if (allLi.length > 0 && target.getAttribute("data-value")) {
    	$("#selectCityId").val(target.getAttribute("data-value"));
    } else if (!target.getAttribute("data-value")) {
    	$("#selectCityId").val("");
    }
    
};

/**
 * 移除城市
 */
function removeCity() {
	
	var cityId = $("#selectCityId").val();
	if (cityId == "") {
		toastr.error("请选择要移除的城市", "提示");
	} else {
		
		var a = document.getElementsByTagName("input");
		var exist = false;
		for(var i = 0;i < a.length; i++) {
			if (a[i].type == "checkbox" && a[i].attributes.length > 5 && a[i].getAttribute("cityvalue") == cityId && a[i].checked == true) {
				exist = true;
				break;
			}
		}
		
		if (exist) {
			toastr.error("请先将该城市的服务车型前的勾都去掉，再移除该城市", "提示");
			return;
		} else {
			var liobj = document.getElementById("li"+cityId);
			var ulobj = document.getElementById('cityName');
			ulobj.removeChild(liobj);
			delete citymap[cityId];

			var cityIdDiv = document.getElementById(cityId+"Div");
			cityIdDiv.innerHTML = "";
			
			var rulesDiv = document.getElementById("rulesDiv");
			rulesDiv.removeChild(cityIdDiv);

			$("#selectCityId").val("");
			
			
			if ($("#rulesDiv").html().length == 0) {
				$("#productqxfxDiv").hide();
                $("#buttonDiv").hide();
			}
		}

	}
}


