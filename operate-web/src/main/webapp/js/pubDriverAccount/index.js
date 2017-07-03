var dataGrid;

		/**
		 * 页面初始化
		 */
		$(function () {
			initGrid();
			initSelectQueryDriver();
		});

		/**
		 * 表格初始化
		 */
		function initGrid() {
			var gridObj = {
				id: "dataGrid",
		        sAjaxSource: "PubDriverAccount/GetOrgDriverAccountByQuery",
		        iLeftColumn: 1,
		        scrollX: true,
		        language: {
		        	sEmptyTable: "暂无司机账户信息"
		        },
		        columns: [
			        
			        {
		                //自定义操作列
		                "mDataProp": "ZDY",
		                "sClass": "center",
		                "sTitle": "操作",
		                "sWidth": 150,
		                "bSearchable": false,
		                "sortable": false,
		                "mRender": function (data, type, full) {
		                	var html = "";
		                	if (full.dealCount > 0) {
		                		html += '<button type="button" class="SSbtn pink"  onclick="transactionDetailed(' +"'"+ full.driverid + "','" + full.driverName + "','" + full.driverAccount +"'"+ ')"><i class="fa fa-times"></i>交易明细</button>';
		                	}
		                	if (html != "") {
		                    	if (full.balanceCount > 0) {
		                    		html += '&nbsp; ';
		                    	}
		                    }
		                    if (full.balanceCount > 0) {
		                    	html += '<button type="button" class="SSbtn pink"  onclick="balanceDetailed(' +"'"+ full.driverid + "','" + full.driverName + "','" + full.driverAccount +"'"+ ')"><i class="fa fa-times"></i>余额明细</button>';
		                    }
		                    return html;
		                }
		            },
			        {mDataProp: "driverAccount", sTitle: "账号", sClass: "center", sortable: true },
			        {mDataProp: "driverName", sTitle: "司机姓名", sClass: "center", sortable: true },
			        {mDataProp: "driverType", sTitle: "司机类型", sClass: "center", sortable: true,
			        	"mRender": function (data, type, full) {
			        		var html = "";
			        		if(full.driverType == '0'){
			        			html+='<font>网约车</font>';
			        		}else{
			        			html+='<font>出租车</font>';
			        		}
			        		return html;
			        	}	
			        },
			        {mDataProp: "balance", sTitle: "账户余额（元）", sClass: "center", sortable: true }
			        
		        ]
		    };
			dataGrid = renderGrid(gridObj);
		}

		function initSelectQueryDriver() {
			$("#queryDriver").select2({
				placeholder : "",
				minimumInputLength : 0,
				multiple : false, //控制是否多选
				allowClear : true,
				ajax : {
					url : "PubDriverAccount/GetQueryDriver",
					dataType : 'json',
					data : function(term, page) {
						return {
							queryDriver: term
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

		var isclear = false;
		/**
		 * 查询
		 */
		function search() {
			var conditionArr = [
				{ "name": "queryDriver", "value": $("#queryDriver").val() },
				{ "name": "queryType", "value": $("#queryType").val() }
			];
			if (isclear) {
				dataGrid.fnSearch(conditionArr,"暂无司机账户信息");
			} else {
				dataGrid.fnSearch(conditionArr);
			}
		}
		
		//清空
		function clearSearch() {
			$("#queryDriver").select2("val","");
			$("#queryType").val("");
			
			isclear = true;
			search();
			isclear = false;
		}

		/**
		 * 交易明细
		 * @param {} id
		 */
		function transactionDetailed(driverid,driverName,driverAccount) {
			window.location.href=document.getElementsByTagName("base")[0].getAttribute("href")+"PubDriverAccount/TransactionDetailedIndex?driverid="+driverid+"&driverName="+driverName+"&driverAccount="+driverAccount;
		}
		
		/**
		 * 余额明细
		 * @param {} id
		 */
		function balanceDetailed(driverid,driverName,driverAccount) {
			window.location.href=document.getElementsByTagName("base")[0].getAttribute("href")+"PubDriverAccount/BalanceDetailedIndex?driverid="+driverid+"&driverName="+driverName+"&driverAccount="+driverAccount;
		}
