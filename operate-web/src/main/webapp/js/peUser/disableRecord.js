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
		        sAjaxSource: "PeUser/GetPeUserdisablelogByQuery?id="+ $("#id").val(),
		        iLeftColumn: 1,//（固定表头，1代表固定几列）
		        scrollX: true,//（加入横向滚动条）
		        columns: [
//			        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
			        {mDataProp: "starttime", sTitle: "禁用期间", sClass: "center", sortable: true,
			        	"mRender": function (data, type, full) {
			        		$("#account").html("账号："+full.account+"的禁用记录");
		                    var html = "";
		                    if (full.starttime != null && full.endtime != null) {
		                    	html += '<div>'+changeToDate(full.starttime)+' 至 '+changeToDate(full.endtime)+'</div>';
							} else {
								return "";
							}
		                    return html;
		                }	
			        },
			        {mDataProp: "reason", sTitle: "禁用原因", sClass: "center", sortable: true},
			        {mDataProp: "updatetime", sTitle: "操作时间", sClass: "center", sortable: true ,
			        	"mRender" : function(data, type, full) {
							if (data != null) {
								return changeToDate(data);
							} else {
								return "";
							}
						}
			        },
			        {mDataProp: "updater", sTitle: "操作人", sClass: "center", sortable: true }
		        ]
		    };
		    
			dataGrid = renderGrid(gridObj);
		}
		/**
		 * 毫秒转日期
		 * 
		 * @param data
		 * @returns {String}
		 */
		function changeToDate1(data) {
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
			return change;
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
		}
		function callBack(){
			window.location.href=base+"PeUser/Index";
		}