$(function() {
			validateForm();
			getSelectCity();
			$("#addressButton").click(function(){
//			   var selectIndex = document.getElementById("city").selectedIndex;//获得是第几个被选中了
//			   var selectText = document.getElementById("city").options[selectIndex].text //获得被选中的项目的文本
//			   moveMap(selectText,$("#addressHidden").val());
			   mapParam.cityName = $("#cityName").val();
			   if(mapParam.cityName == null || mapParam.cityName == ''){
					toastr.error("请先选择一个机构地址的城市，在点击地图", "提示");
//						$("#map").hide();
					return;
			   }
			   moveMap(mapParam.cityName,$("#addressHidden").val());
			   
			   $("#map_confirm").attr("data-owner","address");
		   	   $("#map").show();
			});
			$("#map_cancel").click(function(){
//			   $("#suggest").val("");
			   $("#map").hide();
			});
			 
			$("#map_confirm").click(function(){
				var cityName = mapParam.cityName;
				var data = {
					cityName : cityName
				} 
				$.ajax({
					type : 'GET',
					dataType : 'json',
					url : "LeLeasescompany/GetCityId",
					data : data,
					contentType : 'application/json; charset=utf-8',
					async : false,
					success : function(data) {
						$("#city").val(data.id);
						$("#cityName").val(data.city);
						changeCity(data.city);
					}
				});
//			   onAddress.hide();
			   $("#"+$(this).attr("data-owner")).val($("#suggest").val());
			   mapParam.cityAddress.setInputValue($("#" + $(this).attr("data-owner")).val());
			   $("#addressHidden").val($("#"+$(this).attr("data-owner")).val());
			   $("#suggest").val("");
			   $("#map").hide();
			});
//			citys();
//			$("#city").change(function(){
//				changeCity($(this).find('option:selected').text(),onAddress);
//			});
			
			$("#cityName").change(function(){
				changeCity($(this).val());
			});
			
			$("#address").blur(function(){
			   $("#"+$(this).attr("data-owner")).val($(this).val());
			});
			
			
			$("#imgback2").click(function(){
				var img = $("#imgback2").attr("src");
				$("#openTitle").html("工商执照");
				var html = "<img src="+img+"></img>";
				$("#openImg1").html(html);
				$("#openImg").show();
			});
			
			$("#imgback3").click(function(){
				var img = $("#imgback3").attr("src");
				$("#openTitle").html("法人身份证正面");
				var html = "<img src="+img+"></img>";
				$("#openImg1").html(html);
				$("#openImg").show();
			});			
			
			
			$("#imgback4").click(function(){
				var img = $("#imgback4").attr("src");
				$("#openTitle").html("法人身份证背面");
				var html = "<img src="+img+"></img>";
				$("#openImg1").html(html);
				$("#openImg").show();
			});
		});
		function citys(){
			changeCity($("#cityName").val());
		}
		/**
		 * 表单校验
		 */
		function validateForm() {
			$("#editLeLeasescompanyForm").validate({
				rules : {
					name : {
						required : true,
						maxlength : 20
					},
					shortName : {
						required : true,
						maxlength : 6
					},
					account : {
						required : true,
						account : true,
						minlength : 3
					},
					contacts : {
						required : true,
						maxlength : 20
					},
					phone : {
						required : true,
						minlength : 11,
						phone : true
					},
					mail : {
						required : true,
						email : true
					},
					address : {
						required : true,
						maxlength : 200
					},
					bizlicNum : {
						required : true,
						minlength : 15,
						digits : true
					},
					idCard : {
						required : true,
						minlength : 18,
						idCardNo : true
					}
					
				},
				messages : {
					name : {
						required : "请输入客户名称",
						maxlength : "最大长度20个字符"
					},
					shortName : {
						required : "请输入客户简称",
						maxlength : "最大长度6个字符"
					},
					account : {
						required : "请输入账号名称",
						minlength : "最小长度3个字符"						
					},
					contacts : {
						required : "请输入联系人姓名",
						maxlength : "最大长度20个字符"
					},
					phone : {
						required : "请输入正确的手机号码",
						minlength : "最小长度11个字符"
					},
					mail : {
						required : "请输入电子邮件",
						email : "请输入一个有效的电子邮件地址"
					},
					address : {
						required : "请选择城市或选择详细地址",
						maxlength : "最大长度200个字符"
					},
					bizlicNum : {
						required : "输入工商执照号码",
						minlength:"最小长度15个字符"
					},
					idCard : {
						required : "输入法人身份证号码",
						maxlength : "最小长度18个字符"
					}
				}
			})
		}
//		var checkAccount1 = false;
//		function checkAccount() {
//			var value = $("#account").val();
//            var Regx = /^[A-Za-z0-9]*$/;
//            if (Regx.test(value)) {
//            	checkAccount1 = true;
//            }else {
//            	toastr.error("请输入正确的账号", "提示");
//            }
//        }
		
		function save(){
			var form = $("#editLeLeasescompanyForm");
			if (!form.valid())
				return;
//			if(!checkAccount1){
//				toastr.error("请输入正确的账号", "提示");
//				return;
//    		}
			var data = form.serializeObject();
			
			var flag = false;
			var data1={
				id : data.id,
				name : data.name
			}
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : 'LeLeasescompany/CheckNameOrShortName',
				data : JSON.stringify(data1),
				contentType : 'application/json; charset=utf-8',
				async : false,
				success : function(count) {
					if(count > 0){
						toastr.error("客户全称已存在", "提示");
						flag = true;
					}
				}
			});
			if(flag){
				return;
			}
			
			var flag1 = false;
			var data2={
				id : data.id,
				shortName : data.shortName
			}
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : 'LeLeasescompany/CheckNameOrShortName',
				data : JSON.stringify(data2),
				contentType : 'application/json; charset=utf-8',
				async : false,
				success : function(count) {
					if(count > 0){
						toastr.error("客户简称已存在", "提示");
						flag1 = true;
					}
				}
			});
			if(flag1){
				return;
			}
			
			var flag2 = false;
			var data3={
				id : data.id,
				account : data.account
			}
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : 'LeLeasescompany/CheckNameOrShortName',
				data : JSON.stringify(data3),
				contentType : 'application/json; charset=utf-8',
				async : false,
				success : function(count) {
					if(count > 0){
						toastr.error("账号名称已存在", "提示");
						flag2 = true;
					}
				}
			});
			if(flag2){
				return;
			}
			
			//身份证验证
//			var code = data.idCard;
//			if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
//				toastr.error("身份证号格式错误", "提示");
//				return;
//			}
			//联系方式
			var phone = $("#phone").val();
//			var message = "请输入有效的手机号码";
//			if(regPhone(phone)){
//				toastr.error(message, "提示");
//				return;
//			}
			
			//城市
			var city = data.city;
			if(city == null || city == ''){
				toastr.error("详细地址不能为空", "提示");
				return;
			}
			
			//城市
			var address = data.address;
			if(address == null || address == ''){
				toastr.error("详细地址不能为空", "提示");
				return;
			}
			
			//工商执照
			var bizlicPic = data.bizlicPic;
			if(bizlicPic == null || bizlicPic == ''){
				toastr.error("请上传工商执照", "提示");
				return;
			}
			
			//法人身份证
			var idCardFront = data.idCardFront;
			if(idCardFront == null || idCardFront == ''){
				toastr.error("请上传法人身份证正面", "提示");
				return;
			}
			
			//法人身份证
			var idCardBack = data.idCardBack;
			if(idCardBack == null || idCardBack == ''){
				toastr.error("请上传法人身份证反面", "提示");
				return;
			}
			
			var url = "LeLeasescompany/Edit?phone="+phone;
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : url,
				data : JSON.stringify(data),
				contentType : 'application/json; charset=utf-8',
				async : false,
				success : function(status) {
					if (status.ResultSign == "Successful") {
						var message = status.MessageKey == null ? status
								: status.MessageKey;
						toastr.options.onHidden = function() {
							window.location.href = base+"LeLeasescompany/Index";
						}
						toastr.success(message, "提示");
					} else {
						var message = status.MessageKey == null ? status
								: status.MessageKey;
						toastr.error(message, "提示");
					}
				}
			});
			
		};
		function callBack(){
			window.location.href=base+"LeLeasescompany/Index";
		}
		// 2
		$("#clear2").click(function() {
			$("#fileupload2").val("");
			$("#imgback2").attr("src", "img/leLeasescompany/zhengmian.jpg");
			// $("#imgShow2").hide();
			$("#bizlicPic").val("");
		});
		// 3
		$("#clear3").click(function() {
			$("#fileupload3").val("");
			$("#imgback3").attr("src", "img/leLeasescompany/zhengmian.jpg");
			// $("#imgShow3").hide();
			$("#idCardFront").val("");
		});
		// 4
		$("#clear4").click(function() {
			$("#fileupload4").val("");
			$("#imgback4").attr("src", "img/leLeasescompany/beimian.jpg");
			// $("#imgShow4").hide();
			$("#idCardBack").val("");
		});
		
		function edit(){
			$("#name").attr("disabled",false);
			$("#shortName").attr("disabled",false);
			
			var data = {
				id : $("#id").val()
			}
			$.post("LeLeasescompany/GetFristTime", data, function (status) {
				if (status == '0') {
					$("#account").attr("disabled",false);
					$("#phone").attr("disabled",false);
				}
			});
			$("#contacts").attr("disabled",false);
			$("#mail").attr("disabled",false);
			$("#city").attr("disabled",false);
			$("#address").attr("disabled",false);
			$("#fileupload2").attr("disabled",false);
			$("#fileupload3").attr("disabled",false);
			$("#fileupload4").attr("disabled",false);
			$("#bizlicNum").attr("disabled",false);
			$("#idCard").attr("disabled",false);
			$("#clear2").show();
			$("#clear3").show();
			$("#clear4").show();
			$("#save").attr("style","");
			$("#callBack").attr("style","margin-left: 20px;");
			$("#rowform").removeAttr("style");
			$("#map_confirm").removeAttr("style");
//			$("#cityName").attr("onfocus","getSelectCity()");
			
			//增加  点击图片放大显示file控件
			$("#fileupload2").attr("type","file");
			$("#fileupload3").attr("type","file");
			$("#fileupload4").attr("type","file");
			
			$("#cityName").attr("disabled",false);
			$('#fileupload2').fileupload(
					{
						url : "LeLeasescompany/UploadFile",
						dataType : 'json',
						done : function(e, data) {
							if (data.result.status == "success") {
								$("#imgShow2").show();
								$("#imgback2")
										.attr(
												"src",
												data.result.basepath + "/"
														+ data.result.message[0]);
								$("#bizlicPic").val(data.result.message[0]);
							}else{
					        	toastr.error(data.result.error, "提示");
					        }
						}
					});
			$("#imgback2").click(function() {
				$("#fileupload2").click();
			});
			$('#fileupload3').fileupload(
					{
						url : "LeLeasescompany/UploadFile",
						dataType : 'json',
						done : function(e, data) {
							if (data.result.status == "success") {
								$("#imgShow3").show();
								$("#imgback3")
										.attr(
												"src",
												data.result.basepath + "/"
														+ data.result.message[0]);
								$("#idCardFront").val(data.result.message[0]);
							}else{
					        	toastr.error(data.result.error, "提示");
					        }
						}
					});
			$("#imgback3").click(function() {
				$("#fileupload3").click();
			});
			$('#fileupload4').fileupload(
					{
						url : "LeLeasescompany/UploadFile",
						dataType : 'json',
						done : function(e, data) {
							if (data.result.status == "success") {
								$("#imgShow4").show();
								$("#imgback4")
										.attr(
												"src",
												data.result.basepath + "/"
														+ data.result.message[0]);
								$("#idCardBack").val(data.result.message[0]);
							}else{
					        	toastr.error(data.result.error, "提示");
					        }
						}
					});
			$("#imgback4").click(function() {
				$("#fileupload4").click();
			});
		}
		/**
		 * 初始化城市下拉框
		 */
		function getSelectCity() {
			/*var parent = document.getElementById("inp_box1");
			var city = document.getElementById("city");
			var cityName = document.getElementById("cityName");
			getData(parent, cityName, city, "OpAccountrules/GetPubCityAddrByList", 30, 0);*/
			showCitySelect1(
				"#inp_box1", 
				"PubInfoApi/GetCitySelect1", 
				$("#citymarkId").val(),
				function(backVal, $obj) {
					$('#cityName').val($obj.text());
					$("#city").val($obj.data('id'));
					changeCity($obj.text());
				}
			);
		}