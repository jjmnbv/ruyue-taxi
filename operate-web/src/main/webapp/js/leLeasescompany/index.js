var dataGrid;

		/**
		 * 页面初始化
		 */
		$(function () {
			initGrid();
			dateFormat();
		});

		/**
		 * 表格初始化
		 */
		function initGrid() {
			var gridObj = {
				id: "dataGrid",
		        sAjaxSource: "LeLeasescompany/getLeLeasescompanyByQuery",
		        iLeftColumn: 1,//（固定表头，1代表固定几列）
		        scrollX: true,//（加入横向滚动条）
		        language: {
		        	sEmptyTable: "暂无任何客户信息"
		        },
		        columns: [
//			        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
			        {
		                //自定义操作列
		                "mDataProp": "ZDY",
		                "sClass": "center",
		                "sTitle": "操作",
		                "sWidth": 160,
		                "bSearchable": false,
		                "sortable": false,
		                "mRender": function (data, type, full) {
		                    var html = "";
		                    if(full.companyStateShow == "禁用"){
		                    	html += '<button type="button" class="SSbtn red_q" onclick="enable(' +"'"+ full.id +"'"+ ",'"+full.account+"'"+')"><i class="fa fa-paste"></i>启用账号</button>';
		                    }else{
		                    	html += '<button type="button" class="SSbtn red_q" onclick="disable(' +"'"+ full.id +"'"+ ",'"+full.account+"'"+')"><i class="fa fa-paste"></i>禁用账号</button>';
		                    }
		                    // 厦门公仆  需求  屏蔽  toc业务   start
//		                    if(full.tocState == '已加入'){
//			                    html += '&nbsp; <button type="button" class="SSbtn red_q"  onclick="disableToc(' +"'"+ full.id +"'"+ ",'"+full.name+"'"+')"><i class="fa fa-times"></i> 禁用toC</button>';
//		                    }else if(full.tocState == '加入待审批'){
//		                    	html += '&nbsp; <button type="button" class="SSbtn red_q"  onclick="examineToc(' +"'"+ full.id +"'"+ ",'"+full.name+"'"+')"><i class="fa fa-times"></i> toC审核</button>';
//		                    }
		                    html += '&nbsp; <button type="button" class="SSbtn grey_q"  onclick="resetPassword(' +"'"+ full.id +"'"+ ",'"+full.account+"'"+')"><i class="fa fa-times"></i> 重置密码</button>';
//		                    if(full.tocState == '已加入'){
//			                    html += '&nbsp; <button type="button" class="SSbtn red_q"  onclick="see(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 加盟资源</button>';
//		                    }
					        //厦门公仆  需求  屏蔽  toc业务   end 加盟资源
		                    return html;
		                }
		            },
			        {mDataProp: "city", sTitle: "所属城市", sClass: "center", sortable: true },
			        {mDataProp: "name", sTitle: "客户名称", sClass: "center", sortable: true,
			        	"mRender": function (data, type, full) {
			        		var html = "";
			        		html+= '<a href="LeLeasescompany/AddIndex?id='+full.id+'" style="color:red;">'+""+full.name+""+'</a>';
			        		return html;
			        	}
			        },
			        {mDataProp: "shortName", sTitle: "客户简称", sClass: "center", sortable: true },
			        {mDataProp: "account", sTitle: "账号名称", sClass: "center", sortable: true },
			        {mDataProp: "companyStateShow", sTitle: "账号状态", sClass: "center", sortable: true },
			        // 厦门公仆  需求  屏蔽  toc业务   start
//			        {mDataProp: "tocState", sTitle: "是否加入toC", sClass: "center", sortable: true },
			        //厦门公仆  需求  屏蔽  toc业务   end
			        {mDataProp: "contacts", sTitle: "联系人", sClass: "center", sortable: true },
			        {mDataProp: "phone", sTitle: "联系方式", sClass: "center", sortable: true },
			        {mDataProp: "createTime", sTitle: "创建日期", sClass: "center", sortable: true ,
			        	mRender : function(data, type, full) {
						if (data != null) {
							return changeToDate(data);
						} else {
							return "";
						}
					}}
		        ]
		    };
		    
			dataGrid = renderGrid(gridObj);
		}
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
					{ "name": "queryName", "value":  $("#queryName").val()},
					{ "name": "queryCity", "value":  $("#queryCity").val()},
					{ "name": "queryCompanystate", "value": $("#queryCompanystate").val()},
					{ "name": "startTime", "value": $("#startTime").val() },
					{ "name": "endTime", "value": addDate($("#endTime").val(),1) }
				];
				dataGrid.fnSearch(conditionArr);
				$("#queryNames").val($("#queryName").val());
				$("#queryCitys").val($("#queryCity").val());
				$("#queryCompanystates").val($("#queryCompanystate").val());
				$("#startTimes").val($("#startTime").val());
				$("#endTimes").val($("#endTime").val());
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
//			$('.searchDate').datetimepicker({
//		        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
//		        language: 'zh-CN', //汉化
//		        weekStart: 1,
//		        todayBtn:  1,
//		        autoclose: 1,
//		        todayHighlight: 1,
//		        startView: 2,
//		        minView: 2,
//		        forceParse: 0
//		    });
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
			$.post("LeLeasescompany/Enable", data, function (status) {
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
		function disable(id,account) {
			var comfirmData={
				tittle:"账号禁用提示",
				context:"您确定是否禁用"+account+"账号?",
				button_l:"否",
				button_r:"是",
				click: "deletePost1('" + id + "')",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
			};
			Zconfirm(comfirmData);
		}
		function deletePost1(id){
			var data = {id: id};
			$.post("LeLeasescompany/Disable", data, function (status) {
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
		 * 禁用 toc
		 * @param {} id
		 */
		function disableToc(id,name) {
			var comfirmData={
				tittle:"提示",
				context:"是否禁用toC业务?",
				button_l:"否",
				button_r:"是",
				click: "deletePost2('" + id + "')",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
			};
			Zconfirm(comfirmData);
		}
		function deletePost2(id){
			var data = {id: id};
			$.post("LeLeasescompany/DisableToc", data, function (status) {
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
		//审核 toc
		function examineToc(id,name){
			$("#id").val(id);
			$("#examineTocDiv").show();
			$("#examineTocTitle").html(name+"申请开通个人用车接单业务");
		}
		function save(){
			var id = $("#id").val();
			var val = $("input[name='examine']:checked").val();
			var data = {
				id : id,
				state : val
			}
			$.post("LeLeasescompany/ExamineToc", data, function (status) {
				if (status.ResultSign == "Successful") {
					var message = status.MessageKey == null ? status : status.MessageKey;
		            toastr.success(message, "提示");
		            $("#examineTocDiv").hide();
					dataGrid._fnReDraw();
				} else {
					var message = status.MessageKey == null ? status : status.MessageKey;
					toastr.error(message, "提示");
					$("#examineTocDiv").hide();
				}
			});
			
		}
		function canel(){
			$("#examineTocDiv").hide();
		}
		//  重置密码
		function resetPassword(id,account) {
			var comfirmData={
				tittle:"密码重置提示",
				context:"是否重置"+account+"账号的登录密码?",
				button_l:"否",
				button_r:"是",
				click: "deletePost3('"+id+"','" + account + "')",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
			};
			Zconfirm(comfirmData);
		}
		function deletePost3(id,account){
			var data = {
				id : id,
				account: account
			};
			$.post("LeLeasescompany/ResetPassword", data, function (status) {
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
		//  新增
		function add(){
			var id = "";
			window.location.href=base+"LeLeasescompany/AddIndex?id="+id;
		}
		//查看
		function see(id){
			window.location.href=base+"LeLeasescompany/DriverInformation?id="+id;
		}
		// 导出
		function exportExcel(){
			var queryName=$("#queryNames").val();
			var queryCity=$("#queryCitys").val();
			var queryCompanystate=$("#queryCompanystates").val();
			var startTime=$("#startTimes").val();
			var endTime=addDate($("#endTimes").val(),1);
			window.location.href=base+"LeLeasescompany/ExportData?queryName="+queryName+"&queryCity="+queryCity+"&queryCompanystate="+queryCompanystate
			+"&startTime="+startTime+"&endTime="+endTime;
			
			$("#startTime").blur();
			$("#endTime").blur();
		}
		function emptys(){
			$("#queryName").val("");
			$("#queryCity").val("");
			$("#queryCompanystate").val("");
			$("#startTime").val("");
			$("#endTime").val("");
			search();
		}