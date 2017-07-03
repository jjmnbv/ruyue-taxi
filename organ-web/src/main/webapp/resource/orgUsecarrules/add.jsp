<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<title>用车规则—新建规则</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/css/common.css" />
<link rel="stylesheet" type="text/css" href="content/css/zstyle.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
</head>
<body>
	<div class="rule_box">
		<div class="crumbs">
			用车规则 > 新建规则
			<button class="btn_green" style="float: right; width: 60px; height: 27px; line-height: 27px; margin-top: -4px; margin-right: 30px;">返回</button>
		</div>
		<div class="new_rule_box">
			<div class="new_rule_head">
				<em class="asterisk"></em>规则名称 <input type="text"
					placeholder="低于10个文字" style="width: 390px; margin-left: 20px;" id="ruleName" maxlength="10">
			</div>
			<div class="rule_box_a">
				<div class="left">
					<em class="asterisk"></em>用车方式
				</div>
				<div class="right">
					<c:forEach items="${list}" var="list">
						<c:if test="${list.rulesType == '1'}">
							<input type="checkbox" class="company" value="1" />约车<br>
							<div class="right_box" id="ruletype1"></div>
						</c:if>
						<c:if test="${list.rulesType == '2'}">
							<input type="checkbox" class="company" value="2" />接机<br>
							<div class="right_box" id="ruletype2"></div>
						</c:if>
						<c:if test="${list.rulesType == '3'}">
							<input type="checkbox" class="company" value="3" />送机<br>
							<div class="right_box" id="ruletype3"></div>
						</c:if>
					</c:forEach>
				</div>
				<div
					style="text-align: right; position: absolute; width: 100%; bottom: 40px; right: 50px;">
					<button class="btn_red">保存</button>
					<!-- <a href="" class="btn_grey">取消</a> -->
				</div>
			</div>
		</div>
	</div>
	<!-- <script type="text/javascript" src="js/orgUsecarrules/add.js"></script> -->
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	var rulesObj = {
			allRules: "",
			/* editRules: "" */
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
		/* function loadEditCarRules() {
			$.ajax({
				type : 'get',
				url : 'OrgUsecarrules/GetEditJson?ruleName=' + rulename,
				dataType: "json",
				async : false,
				contentType: "application/json; charset=utf-8",
				success : function(data) {
					rulesObj.editRules = data;
					initFormData();
				}
			});
		} */

		/**
		 * 页面元素初始化
		 */
		function initFormData() {
			for(var i = 1; i <= 3; i++) {
				if(rulesObj.allRules[i]) {
					var rulesArr = rulesObj.allRules[i];
					for(var j = 0; j < rulesArr.length; j++) {
						var leasescompanyid = rulesArr[j].leasescompanyid;
						var leasesCompanyName = rulesArr[j].leasesCompanyName;
						
						/* if(validateHasRepeatValue("leasescompanyid", leasescompanyid, rulesObj.editRules[rulename][i])) {
							
							var htmlArr = [];
							htmlArr.push('<div class="right_box_m"><div class="right_box_s"></div>');
							
							var currentCompany = $("#ruletype" + i).children(".right_box").children(".right_box_m").children(".right_box_s").children(".select_box");
							
							if(currentCompany.length < (rulesArr.length - 1)) {
								htmlArr.push('<span class="xinzeng">添加</span>');
							}
							
							if(currentCompany.length > 0) {
								htmlArr.push('<span class="font-red">删除</span>');
							}
							
							htmlArr.push('</div>');
							$(".company:eq(" + (i - 1) + ")").attr("checked", "checked")
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
										
										cartypeHtmlArr.push('<div class="lei ');
										if(validateHasRepeatModels("vehiclemodelid", cartype.vehiclemodelid, rulesObj.editRules[rulename][i])) {
											cartypeHtmlArr.push(' on');
										}
										cartypeHtmlArr.push('"');
										cartypeHtmlArr.push(' style="margin-left: 0px;" ');
										cartypeHtmlArr.push(' id="');
										cartypeHtmlArr.push(cartype.vehiclemodelid);
										cartypeHtmlArr.push('">');
										cartypeHtmlArr.push(cartype.vehicleModelsName);
										cartypeHtmlArr.push('</div>');
									}
								}
							}
							
							ruleHtmlArr.push('</ul></div>');
							
							$("#ruletype" + i).find(".right_box_s:eq(0)").html(ruleHtmlArr.concat(cartypeHtmlArr).join(""));
						} */
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
					cartypeHtmlArr.push(' style="margin-left: '+margin+'px;" ');
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
			
			if(currentCompany.length < rulesArr.length) {
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
					//if(currentCompany.length < (rulesArr.length - 1)) {
						cartypeHtmlArr.push('<span class="xinzeng">添加</span>');
					//}
					/* if(rulesArr.length > 1) {
						cartypeHtmlArr.push('<span class="font-red">删除</span></div>');
					}  */
					//console.info(cartypeHtmlArr.join(""));
					$("#ruletype" + n).html(cartypeHtmlArr.join(""));
					
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
				alert(arrbox);
			}
			
			if(!validateForm(arrbox)) {
				toastr.error("存在重复的服务车企", "提示");
				return;
			}
			
			if ($("#ruleName").val() != '') {
				var ruleName = $("#ruleName").val();
				arrbox["ruleName"] = ruleName;
				$.ajax({
							type : 'post',
							url : 'OrgUsecarrules/Add',
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
					if(data[i][key] == value) {
						result = true;
					}
				}
			}
			
			return result;
		}
		 
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
	</script>
</body>
</html>