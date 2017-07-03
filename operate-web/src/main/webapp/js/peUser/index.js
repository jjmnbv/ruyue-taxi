		var dataGrid;

		/**
		 * 页面初始化
		 */
		$(function () {
			initGrid();
			dateFormat();
			validateForm();
		});

		/**
		 * 表格初始化
		 */
		function initGrid() {
			var gridObj = {
				id: "dataGrid",
		        sAjaxSource: "PeUser/GetPeUserByQuery",
		        iLeftColumn: 1,//（固定表头，1代表固定几列）
		        scrollX: true,//（加入横向滚动条）
		        language: {
		        	sEmptyTable: "暂无个人用户信息"
		        },
		        columns: [
//			        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
			        {
		                //自定义操作列
		                "mDataProp": "ZDY",
		                "sClass": "center",
		                "sTitle": "操作",
		                "sWidth": 210,
		                "bSearchable": false,
		                "sortable": false,
		                "mRender": function (data, type, full) {
		                    var html = "";
		                    html += '&nbsp; <button type="button" class="SSbtn red_q"  onclick="resetPassword(' +"'"+ full.id +"'"+ ",'"+full.account+"'"+",'"+full.orgUserAccount+"'"+')"><i class="fa fa-times"></i> 重置密码</button>';
	                    	if(full.disablestate == '0'){
	                    		html += '&nbsp; <button type="button" class="SSbtn grey_q" onclick="disable(' +"'"+ full.id +"'"+ ",'"+full.account+"'"+')"><i class="fa fa-paste"></i>禁用</button>';	
	                    	}else{
		                    	html += '&nbsp; <button type="button" class="SSbtn green_q" onclick="enable(' +"'"+ full.id +"'"+ ",'"+full.account+"'"+')"><i class="fa fa-paste"></i>启用</button>';
	                    	}
	                    	if(full.disableRecord > 0){
	                    		html += '&nbsp; <button type="button" class="SSbtn blue_w" onclick="disableRecord(' +"'"+ full.id +"'"+ ",'"+full.account+"'"+')"><i class="fa fa-paste"></i>禁用记录</button>';
	                    	}
		                    return html;
		                }
		            },
		            {mDataProp: "", sTitle: "用户类型", sClass: "center", sortable: true,
		            	"mRender": function (data, type, full) {
		            		if(full.orgUserAccount != null && full.orgUserAccount != ''){
		            			return "机构用户"
		            		}else{
		            			return "非机构用户"
		            		}
		                }
		            },
			        {mDataProp: "nickname", sTitle: "称呼", sClass: "center", sortable: true },
			        {mDataProp: "account", sTitle: "账号", sClass: "center", sortable: true},
			        {mDataProp: "sex", sTitle: "性别", sClass: "center", sortable: true },
			        //{mDataProp: "email", sTitle: "邮箱", sClass: "center", sortable: true },
			        {mDataProp: "headportraitmin", sTitle: "头像", sClass: "center", sortable: true ,
			        	"mRender": function (data, type, full) {
		                    var html = "";
		                    html += '<img alt="" src="'+serviceAddress+'/'+full.headportraitmin+'"  style="width: 50px;height: 50px;">';
		                    if(full.headportraitmin == null || full.headportraitmin == ''){
		                    	if(full.sex == '女'){
		                    		return '<img src="img/peUser/nv.png" style="width: 50px;height: 50px;">';
		                    	}else if(full.sex == '男'){
		                    		return '<img src="img/peUser/nan.png" style="width: 50px;height: 50px;">';
		                    	}else{
		                    		return '<img src="img/peUser/zhongxing.png" style="width: 50px;height: 50px;">';
		                    	}
		                    }
		                    return html;
		                }
			        },
			        {mDataProp: "registertime", sTitle: "注册时间", sClass: "center", sortable: true ,
			        	"mRender" : function(data, type, full) {
						if (data != null) {
							return changeToDate(data);
						} else {
							return "";
						}
					}},
					{mDataProp: "disablestateShow", sTitle: "当前状态", sClass: "center", sortable: true ,
						"mRender" : function(data, type, full) {
							var html = "";
							if (full.disablestateShow == '禁用') {
								html+="<font color='red'>禁用</font>";
							} else {
								html+="<font>启用</font>";
							}
							return html;
						}
					}
		        ]
		    };
		    
			dataGrid = renderGrid(gridObj);
		}
		var isclear = false;
		/**
		 * 查询
		 */
		function search() {
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var d1 = new Date(startTime.replace(/\-/g, "\/"));  
			var d2 = new Date(endTime.replace(/\-/g, "\/"));  
			if(startTime!="" && endTime!="" && d1 > d2){  
				toastr.error("开始时间不能大于结束时间！", "提示");
				return;  
			}else{
				var conditionArr = [
					{ "name": "queryAccount", "value":  $("#queryAccount").val()},
					{ "name": "queryCompanystate", "value": $("#queryCompanystate").val()},
					{ "name": "startTime", "value": $("#startTime").val() },
					{ "name": "endTime", "value": addDate($("#endTime").val(),1) }
				];
				if (isclear) {
					dataGrid.fnSearch(conditionArr,"暂无个人用户信息");
				} else {
					dataGrid.fnSearch(conditionArr);
				}				
				$("#queryAccounts").val($("#queryAccount").val());
				$("#queryCompanystates").val($("#queryCompanystate").val());
				$("#startTimes").val($("#startTime").val());
				$("#endTimes").val($("#endTime").val());
				searchCount();
			}
		}
		
		
		/**
		 * 控制导出excel按钮可不可点
		 */
		function searchCount() {
			
			var url = "PeUser/GetPeUserListCountByQuery";
			var data = { queryAccount:  $("#queryAccount").val(),
						 queryCompanystate: $("#queryCompanystate").val(),
						 startTime: $("#startTime").val() ,
						 endTime: addDate($("#endTime").val(),1) 
					   };
			$.ajax({
				type: 'POST',
				dataType: 'json',
				url:url,
				data: JSON.stringify(data),
				contentType: 'application/json; charset=utf-8',
				async: false,
				success: function (json) {
					var count = json;
					if (count == 0) {// 没有数据
						$("#export").attr("disabled",true);
						$("#export").removeClass("blue_q");
					} else if (count > 0) {
						$("#export").attr("disabled",false);
						$("#export").addClass("blue_q");
					}
					
				},
				error: function (xhr, status, error) {
					return;
				}
		    });
		}
		
		/**
		 * 清空
		 */
		function clearParameter() {
			$("#queryAccount").val("");
			$("#queryCompanystate").val("");
			$("#startTime").val("");
			$("#endTime").val("");
			
			isclear = true;
			search();
			isclear = false;
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
			var hours = "";
			var minutes = "";
			var second = "";
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
			if (myDate.getHours() < 10) {
				hours = "0" + myDate.getHours();
			} else {
				hours = myDate.getHours();
			}
			change += " " + hours;
			if (myDate.getMinutes() < 10) {
				minutes = "0" + myDate.getMinutes();
			} else {
				minutes = myDate.getMinutes();
			}
			change += ":" + minutes;
			if (myDate.getSeconds()<10){
				second = "0"+myDate.getSeconds();
			}else{
				second = myDate.getSeconds();
			}
			change += ":" + second;
			return change;
		}
		//endtime 时间上+1天
		function addDate(date, days) {
		    if (days == undefined || days == '') {
		        days = 1;
		    }
			if(date != '' && date != undefined && date != null){
		        var date = new Date(date);
		        date.setDate(date.getDate() + days);
		        var month = date.getMonth() + 1;
		        var day = date.getDate();
		        return date.getFullYear() + '-' + month + '-' + day;
			}else{
				return "";
			}	
		}
		//日期 控件 加载
		function dateFormat() {
			$('.searchDate1').datetimepicker({
		        format: "yyyy-mm-dd hh:ii", //选择日期后，文本框显示的日期格式
		        language: 'zh-CN', //汉化
		        weekStart: 1,
		        todayBtn:  1,
		        autoclose: 1,
		        todayHighlight: 1,
		        startView: 0,
		        minView: 0,
		        forceParse: true,
		        clearBtn: true
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
		 * 启用
		 * @param {} id
		 */
		function enable(id,account) {
			var comfirmData={
				tittle:"账号启用提示",
				context:"您确定是否启用"+account+"账号?",
				button_l:"否",
				button_r:"是",
				click: "deletePost('" + id + "')",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
			};
			Zconfirm(comfirmData);
		}
		function deletePost(id){
			var data = {id: id};
			$.post("PeUser/Enable", data, function (status) {
				if (status.ResultSign == "Successful") {
					var message = status.MessageKey == null ? status : status.MessageKey;
		            toastr.success(message, "提示");
					dataGrid._fnReDraw();
				} else {
					var message = status.MessageKey == null ? status : status.MessageKey;
					toastr.error(message, "提示");
				}
			});
		}
		
		/**
		 * 禁用
		 * @param {} id
		 */
		function disable(id) {
			$("#disabledDiv").show();
			$("#starttime").val("");
			$("#endtime").val("");
			$("#reason").val("");
			$("#userid").val(id);
			var data = {id: id};
			$.post("PeUser/GetById", data, function (status) {
				if(status.nickname!=null){
					$("#disabledaccount").html(status.nickname+" "+status.account);
				}else{
					$("#disabledaccount").html(" "+status.account);
				}
				var editForm = $("#disabledForm").validate();
				editForm.resetForm();
				editForm.reset();
			});
		}
		function save(){
			var form = $("#disabledForm");
			if (!form.valid())
				return;
			var startTime = getNowFormatDate();
//			var startTime = $("#starttime").val();
			var endTime = $("#endtime").val();
//			var d1 = new Date(startTime.replace(/\-/g, "\/"));
			var d1 = new Date(startTime.replace(/\-/g, "\/"));
			var d2 = new Date(endTime.replace(/\-/g, "\/")); 
			if(endTime==""){
				toastr.error("请选择结束时间", "提示");
				return;
			}
//			if(startTime==""){
//				toastr.error("请选择开始时间", "提示");
//				return;
//			}  startTime!="" && 
			if(endTime!="" && d1 > d2){  
				toastr.error("当前时间不能大于结束时间！", "提示");
				return;  
			}else{
				$("#starttime").val(startTime);
				var data = form.serializeObject();
				var url = 'PeUser/CreatePeUserdisablelog';
//				$.ajax({
//					type : 'POST',
//					dataType : 'json',
//					url : url,
//					data : JSON.stringify(data),
//					contentType : 'application/json; charset=utf-8',
//					async : false,
//					success : function(status) {
//						if (status.ResultSign == "Successful") {
//							var message = status.MessageKey == null ? status : status.MessageKey;
//				            toastr.success(message, "提示");
//				            $("#disabledDiv").hide();
//							dataGrid._fnReDraw();
//						} else {
//							var message = status.MessageKey == null ? status : status.MessageKey;
//							toastr.error(message, "提示");
//						}
//					}
//				});
				
//				$.ajax({
//					type : 'POST',
//					dataType : 'json',
//					url : url,
//					data : JSON.stringify(data),
//					contentType : 'application/json; charset=utf-8',
//					async : false,
//					success : function(status) {
//						if (status.ResultSign == "Successful") {
//							var message = status.MessageKey == null ? status : status.MessageKey;
//				            toastr.success(message, "提示");
//				            $("#disabledDiv").hide();
//							dataGrid._fnReDraw();
//						} else {
//							var message = status.MessageKey == null ? status : status.MessageKey;
//							toastr.error(message, "提示");
//						}
//					}
//				});
				var userid = data.userid;               
				var  starttime = data.starttime;           
				var endtime = data.endtime;           
				var reason = data.reason; 
				$.post(url, {userid : userid,starttime : starttime,endtime : endtime,reason : reason}, function (status) {
					if (status.ResultSign == "Successful") {
						var message = status.MessageKey == null ? status : status.MessageKey;
			            toastr.success(message, "提示");
			            $("#disabledDiv").hide();
						dataGrid._fnReDraw();
					} else {
						var message = status.MessageKey == null ? status : status.MessageKey;
						toastr.error(message, "提示");
					}
				});
			}
		}
		//获取当前时间
		function getNowFormatDate() {
			var myDate = new Date();
			var month = "";
			var date = "";
			var hours = "";
			var minutes = "";
//			var second = "";
			var change = "";
			change += myDate.getFullYear() + "-";
		
			if (myDate.getMonth() < 9) {
				month = "0" + (myDate.getMonth() + 1);
			} else {
				month = (myDate.getMonth() + 1);
			}
			change += month + "-";
			if (myDate.getDate() < 10) {
				date = "0" + myDate.getDate();
			} else {
				date = myDate.getDate();
			}
			change += date;
			if (myDate.getHours() < 10) {
				hours = "0" + myDate.getHours();
			} else {
				hours = myDate.getHours();
			}
			change += " " + hours;
			if (myDate.getMinutes() < 10) {
				minutes = "0" + myDate.getMinutes();
			} else {
				minutes = myDate.getMinutes();
			}
			change += ":" + minutes;
//			if (myDate.getSeconds()<10){
//				second = "0"+myDate.getSeconds();
//			}else{
//				second = myDate.getSeconds();
//			}
//			change += ":" + second;
			return change;
		}
		
		/**
		 * 表单校验
		 */
		function validateForm() {
			$("#disabledForm").validate({
				rules : {
					reason : {
						required : true
					}
					
				},
				messages : {
					reason : {
						required : "填写禁用原因"
					}
				}
			})
		}
		function canel(){
			$("#disabledDiv").hide();
		}
		//  重置密码
		function resetPassword(id,account,orgUserAccount) {
			var comfirmData={
				tittle:"密码重置提示",
				context:"是否重置"+account+"账号的登录密码?",
				button_l:"否",
				button_r:"是",
				click: "deletePost3('" + id + "','"+orgUserAccount+"')",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
			};
			Zconfirm(comfirmData);
		}
		function deletePost3(id,orgUserAccount){
			var data = {id: id,orgUserAccount:orgUserAccount};
			$.post("PeUser/ResetPassword", data, function (status) {
				if (status.ResultSign == "Successful") {
					var message = status.MessageKey == null ? status : status.MessageKey;
		            toastr.success(message, "提示");
					dataGrid._fnReDraw();
				} else {
					var message = status.MessageKey == null ? status : status.MessageKey;
					toastr.error(message, "提示");
				}
			});
		}
		function disableRecord(id){
			window.location.href=base+"PeUser/DisableRecord?id="+id;
		}
		// 导出
		function exportExcel(){
			var queryAccount=$("#queryAccounts").val();
			var queryCompanystate=$("#queryCompanystates").val();
			var startTime=$("#startTimes").val();
			var endTime=addDate($("#endTimes").val(),1);
			window.location.href=base+"PeUser/ExportData?queryAccount="+queryAccount+"&queryCompanystate="+queryCompanystate
			+"&startTime="+startTime+"&endTime="+endTime;
			
			$("#queryAccount").blur();
			$("#startTime").blur();
			$("#endTime").blur();
		}
