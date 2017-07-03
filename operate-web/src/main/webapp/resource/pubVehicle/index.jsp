<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.SystemConfig"%>
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
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>车辆管理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		.tip_box_b input[type=text]{width: 63%;}
		
		/* 添加城市样式 */
		#pubCityaddr{position:absolute;display:inline-block;}
		#pubCityaddr>.addcitybtn{background: #997C26;padding: 2px 10px;color:#fff;margin-left: 13px;}
		#pubCityaddr .kongjian_list{top:26px!important;}
		.added{display:inline-block;padding:2px 4px;}
		.added .ico_x_a{float:right;margin-left: -6px;}
		.addcbox{padding:6px 10px;margin:0px 37px 10px 37px;border:1px solid #ccc;min-height:100px;line-height:30px;}
		.kongjian_list .box .con {max-height: 200px;overflow-y: auto;}
		th, td { white-space: nowrap; }
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
		margin-top:-20px;
		}
		.pop_box{z-index: 1111 !important;}
		
		.tip_box_d #plateNoProvince option{
			font-size:12px;
			padding:0 10px;
		}
		.tip_box_d #plateNoCity option{
			font-size:12px;
			padding:0 10px;
		}
		#pubCityaddr>.city_container{
			margin-left: 12px;
		}
		.ico_x_a{
			display:block;
		}
		</style>
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.fileupload.js"></script>
		<script type="text/javascript" src="content/plugins/letterselect/letterselect.js"></script>
	</head>
	<body style="overflow-x:hidden;overflow-y:hidden;">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 车辆管理
			<button class="SSbtn blue back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-3"><label>车辆类型</label>
						<!--  网约车 0 、 出租车 1  -->
						<select id="queryVehicleType" name="queryVehicleType">
							<option value="" selected="selected">全部</option>
							<option value="0">网约车</option>
							<option value="1">出租车</option>
						</select>
						<input id="queryVehicleTypes" name="queryVehicleTypes" type="hidden" value=""/>	
					</div>
					<div class="col-3" id="brandCarsDiv"><label>品牌车系</label>
						<%--<input id="queryBrandCars" name="queryBrandCars" type="text" placeholder="请选择品牌车系" value="">--%>
						<%--<input id="queryBrandCarss" name="queryBrandCarss" type="hidden" value="">--%>
						<select id="queryBrandCars" name="queryBrandCars" >
							<option value="">全部</option>
							<c:forEach var="listBrandCars" items="${listBrandCars}">
								<option value="${listBrandCars.text}">${listBrandCars.text}</option>
							</c:forEach>
						</select>
					</div>
					
					<div class="col-3"><label>服务车型</label>
						<select id="queryServiceCars" name="queryServiceCars">
							<option value="" selected="selected">全部</option>
							<c:forEach var="serviceCars" items="${serviceCars}">
								<option value="${serviceCars.id}">${serviceCars.serviceCars}</option>
							</c:forEach>
						</select>
						<input id="queryServiceCarss" name="queryServiceCarss" type="hidden" value="">
					</div>
					<div class="col-3"><label>登记城市</label>
						<input id="queryCity" name="queryCity" type="text" placeholder="请选择城市" value=""/>
						<input id="queryCitys" name="queryCitys" type="hidden" value="">
					</div>
				</div>
				<div class="row">
					<div class="col-3"><label>车牌号</label>
					<input id="queryPlateNo" name="queryPlateNo" type="text" placeholder="车牌号" style="width: 68%;">
					<input id="queryPlateNos" name="queryPlateNos" type="hidden" value="">
					</div>
					<div class="col-3"><label>服务状态</label>
						<select id="queryWorkStatus" name="queryWorkStatus">
						<option value="" selected="selected">全部</option>
						<c:forEach var="workStatus" items="${workStatus}">
							<option value="${workStatus.value}">${workStatus.text}</option>
						</c:forEach>
					</select>
					<input id="queryWorkStatuss" name="queryWorkStatuss" type="hidden" value="">
					</div>
					<!-- 新增绑定状态 -->
					<div class="col-3"><label>绑定状态</label>
							<!-- 未绑定 0 、已绑定 1  -->
							<select id="queryBoundState" name="queryBoundState">
								<option value="" selected="selected">全部</option>
								<option value="0">未绑定</option>
								<option value="1">已绑定</option>
							</select>
							<input id="queryBoundStates" name="queryBoundStates" type="hidden" value=""/>	
					</div>
					<div class="col-3"><label>营运状态</label>
						<!--  营运中 0 、维修中 1  -->
						<select id="queryVehicleStatus" name="queryVehicleStatus">
							<option value="" selected="selected">全部</option>
							<option value="0">营运中</option>
							<option value="1">维修中</option>
						</select>
						<input id="queryVehicleStatuss" name="queryVehicleStatuss" type="hidden" value=""/>	
					</div>
				</div>
				<div class="row">
					<div class="col-12" style="text-align: right;">
						<button class="Mbtn green_a" onclick="search();">查询</button>
						<button class="Mbtn gary" onclick="emptys();">清空</button>
					</div>
				</div>
				
			</div>
			<div class="row">
				<div class="col-4">
					<h4>车辆管理列表</h4>
				</div>
				<div class="col-8" style="text-align: right;">
					<button class="Mbtn gary" onclick="download()">下载模板</button>
					<button class="Mbtn red" onclick="importExcel();">导入</button>
					<button class="Mbtn orange" onclick="exportExcel();">导出数据</button>
					<%-- <a href="<%=SystemConfig.getSystemProperty("fileserver")%>/group1/M00/00/01/CgAAGVfRRguAe8ljAABOABCCtZ8568.xls">下载模板</a> --%>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
		<div class="tip_box_d form" style="max-height: 780px;overflow-y:hidden;overflow-x:hidden;width:736px;">
	            <h3 id="titleForm">新增车辆信息</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<div class="row">
	            			<div class="col-6">
		            			<label>车牌<em class="asterisk"></em></label>
	            				<select id="plateNoProvince" name="plateNoProvince" style="width: 22%;">
	            					<option value="">请选择</option>
	            					<c:forEach var="getPlateNoProvince" items="${getPlateNoProvince}">
										<option value="${getPlateNoProvince.value}">${getPlateNoProvince.text}</option>
									</c:forEach>
			                	</select>
		            			<select id="plateNoCity" name="plateNoCity"  style="width: 22%;">
		            				<option value="">请选择</option>
			                	</select>
			                	<input id="plateNo" name="plateNo" type="text" placeholder="号码"  style="width: 22%;" maxlength="5">
	            			</div>
	            			<div class="col-6">
	            				<label>车架号<em class="asterisk"></em></label>
	            				<input id="vin" name="vin" type="text" placeholder="由大写字母和数字共17位组成" style="width: 59%;" maxlength="17">
	            			</div>
            			</div>
	            		<div class="row">
	            			<div class="col-6">
	            				<label>品牌车系<em class="asterisk"></em></label>
	            				<input id="vehclineId" name="vehclineId" type="text" placeholder="请选择" value="">
	            			</div>
	            			<div class="col-6">
	            				<label>颜色<em class="asterisk"></em></label>
	            				<input id="color" name="color" type="text" placeholder="例如：红色" value="" style="width: 59%;" maxlength="8">
	            			</div>
	            		</div>
	            		<div class="row">
	            			<div class="col-6">
	            				<label>登记城市<em class="asterisk"></em></label>
	            				<input type="hidden" id="city" name="city" value="">
	            				<input type="hidden" name="citymarkid" id="citymarkid" value="">
								<div id="inp_box1" style="width: 65%">
									<input type="text" id="cityName" style="width: 104%"   readonly="readonly">
								</div> 
	            			</div>
	            			<div class="col-6">
	            				<label>荷载人数<em class="asterisk"></em></label>
	            				<input id="loads" name="loads" type="text" placeholder="人数" value="" style="width: 59%;" maxlength="2">
	            			</div>
	            		</div>
	            		<div class="row">
	            			<div class="col-6" id="vehicleTypeDiv">
	            				<label>车辆类型<em class="asterisk"></em></label>
								<!--  网约车 0 、 出租车 1  -->
								<select id="vehicleType" name="vehicleType">
									<option value="0" selected="selected">网约车</option>
									<option value="1">出租车</option>
								</select>	
	            			</div>
	            			<div class="col-6" id = "vehicleStatusDiv" style="display: none;">
	            				<label>营运状态<em class="asterisk"></em></label>
	            				<!--  营运中 0 、维修中 1  -->
								<input id="vehicleStatus" name="vehicleStatus" type="radio" value="0" checked="checked"/>营运中
				   	   			<input id="vehicleStatus" name="vehicleStatus" type="radio" value="1"/>维修中
	            			</div>
	            		</div>
	            		<div id ="scopeDiv">
	            		<div class="row">
	            			<div class="col-8" style="padding-left:4.4%;">
								<label style="text-align: left;width: 18%">经营区域<em class="asterisk"></em></label>
		            			<div id="pubCityaddr">
		            				<input type="button" class="addcitybtn" value="+添加城市" style="margin-left: 5px;">
		            				<!-- <div class="addcitybtn">+添加城市</div> -->
								</div>         
	            			</div>

	            		</div>
	            		
	            				<input id="businessScope" name="businessScope" type="hidden" value=""/>
	            				<!-- <textarea style="margin-left:30%;width:70%;" id="businessScopeTemp" name="businessScopeTemp" cols="50" rows="3" placeholder=""></textarea> -->
	            				<div id ="addcboxId"class="addcbox" style="margin:0px 30px 10px 108px;border:1px solid #ccc;min-height:100px;line-height:30px;">           			
	            				</div>
						</div>
	            			
	            	</form>
	            	<div class="row" style="margin-left: 40%;">
	            		<div class="col-6">
			                <button class="Lbtn red" onclick="save()">确定</button>
			                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
		                </div>
	                </div>
	            </div>
		</div>
		
		<div class="pop_box" id="importExcelDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="importExcel">导入车辆信息</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="row">
	            	<div class="col-12">
	            		提示：请确保文件的格式与导入车辆excel模板的格式一致
	            	</div>
	            </div>
	            <div class="row">
	            	<div class="col-12">
	            		<label style="margin-right: 334px;">上传文件<em class="asterisk"></em></label>
	                	<input id="importfile" name="importfile" type="file" multiple style="margin-left: 177px;margin-top: -21px;"><br>
	            	</div>
	            </div>
	            <div class="row">
	            	<div class="col-12">
	            		<button class="Lbtn red" onclick="canel()">关闭</button>
	            	</div>
	            </div>
	        </div>
		</div>
		<!-- 导入 -->
		<div class="pop_box" id="importExcelDiv1" style="display: none;">
			<div class="tip_box_b">
				<h3 id="importExcel">提示</h3>
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭">
				<div class="row">
					<div class="col-10" id="importExcelDiv2" style="margin-left: 70px;width: 350px;text-align: left;overflow-y: auto;height: 200px;">
					</div>
				</div>
				<div class="row">
					<div class="col-10" style="text-align: right;">
						<button class="Mbtn red"  onclick="canel1()">确定</button>
					</div>
				</div>
			</div>
		</div>
		<div class="kongjian_list" id="kongjian_list">
			<div class="box">
				<div class="title">
					<span>ABCDE</span>
					<span>FGHIJ</span>
					<span>KLMNO</span>
					<span>PQRST</span>
					<span>UVWXYZ</span>
				</div>
				<div class="con">

				</div>
				<div class="con">

				</div>
				<div class="con">

				</div>
				<div class="con">

				</div>
				<div class="con">
					
				</div>
			</div> 
		</div>
		
		<script type="text/javascript" src="js/pubVehicle/index.js"></script>
		<script type="text/javascript" src="js/pubVehicle/reg.js"></script>
		<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		 	function gradeChange(){
		 		var optionValTo = $("#businessScopeTemp").val();//textarea value 值
		 		var optionTo = $("#businessScope").val();//input value 值
		 		var optionVal = $("#pubCityaddr").val(); //select value 的值
		 		var option = $('#pubCityaddr option:selected').text();//select option 属性值
		 		var tempVal = optionTo.split(',');
		 		var val = $.inArray(optionVal, tempVal);  //返回 3,
		 		if(!optionTo){
		 			$("#businessScopeTemp").val(option);
			 		$("#businessScope").val(optionVal);
		 		}else{
			 		if(val == -1){
			 			$("#businessScopeTemp").val(optionValTo+","+option);
				 		$("#businessScope").val(optionTo+","+optionVal);
			 		}else{
			 			toastr.error("城市已经存在，请重新选择", "提示");
			 		}
		 		}
		    }
		 	$("#loadAsDefault").click(function(){
		 		if(this.checked){  
		 	        //do someting 选中
		 			$.post("PubVehicleScope/CheckLoadAsDefault", function (status) {
		 				if (status > 0) {
		 					$.post("PubVehicleScope/LoadAsDefault", function (data) {
		 						var businessScope="";
		 						var businessScopeTemp ="";
		 						var html = "";
		 						for(var i=0;i<data.length;i++){

		 							html+='<div class="added" id="'+data[i].id+'">'+data[i].city+'<em class="ico_x_a"></em></div>';

		 						}

		 						$(".addcbox").html(html);
		 					});
		 				} else {
		 					toastr.error("没用常用经营区域", "提示");
		 				}
		 			});
		 	    }else{  
		 	        //do someting else 未选中
		 	    	$("#businessScopeTemp").val("");
			 		$("#businessScope").val("");
		 	    }  
		 	});
			function canel1(){
				$("#importExcelDiv1").hide();
			}
		 	
		 	$("#plateNoProvince").change(function() {
				var projectId = $("#plateNoProvince").val();

				//alert(projectId);
				if (projectId == '') {
					//alert("000");
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
					html += '<option value="">' + "全部" + '</option>';
					for (var i = 0; i < versionList.length; i++) {
						html += '<option value="'+versionList[i].value+'">'
								+ versionList[i].text + '</option>';
					}
				} else {
					html += '<option value="">' + "全部" + '</option>';
				}
				$("#plateNoCity").html(html);
			}
			function importExcel(){
				$("#importExcelDiv").show();
			}
			$('#importfile').fileupload({
				url:"PubVehicle/ImportExcel",
				dataType: 'json',
				done: function(e, data) {
					if (data.result.ResultSign == "Successful") {
						var message = data.result.MessageKey == null ? data : data.result.MessageKey;
						//toastr.warning(message, "提示");
						//Zalert("提示","以下车辆导入失败：<br>"+message);
						$("#importExcelDiv1").show();
						$("#importExcelDiv2").html("以下车辆导入失败：<br>"+message);
						dataGrid._fnReDraw();
					}else if(data.result.ResultSign == "Error"){
						var message = data.result.MessageKey == null ? data : data.result.MessageKey;
						toastr.error(message, "提示");
						dataGrid._fnReDraw();
					}else{
						var message = data.result.MessageKey == null ? data : data.result.MessageKey;
						$("#importExcelDiv1").show();
						$("#importExcelDiv2").html("导入成功");
						dataGrid._fnReDraw();
					}
				}
			});
			function exportExcel(){
				var queryBrandCars = $("#queryBrandCars").val();
				var queryPlateNo = $("#queryPlateNo").val();
				var queryWorkStatus = $("#queryWorkStatus").val();
				var queryCity = $("#queryCity").val();
				var queryServiceCars = $("#queryServiceCars").val();
				var queryVehicleType = $("#queryVehicleType").val();
				var queryVehicleStatus = $("#queryVehicleStatus").val();
				var queryBoundState = $("#queryBoundState").val();
				window.location.href = base+"PubVehicle/ExportData?queryBrandCars="+queryBrandCars+"&queryPlateNo="+queryPlateNo+"&queryWorkStatus="+queryWorkStatus+"&queryCity="+queryCity+"&queryServiceCars="+queryServiceCars
										+"&queryVehicleType="+queryVehicleType+"&queryVehicleStatus="+queryVehicleStatus+"&queryBoundState="+queryBoundState;
			}
			/**
			 * 初始化城市下拉框
			 */
			function getSelectCity() {
				/* var parent = document.getElementById("inp_box1");
				var city = document.getElementById("city");
				var cityName = document.getElementById("cityName");
				getData(parent, cityName, city, "StandardAccountRules/GetPubCityAddrList", 30, 0);
				var parent = document.getElementById("pubCityaddr"); */
				changeCity1(
					"#inp_box1", 
					$("#citymarkid").val(),
					function(backVal, $obj) {
						$('#cityName').val($obj.text());
						$("#city").val($obj.data('id'));
					}
				);
			}
			
			function getSelectCitys() {

				showCitySelect1(
					"#pubCityaddr", 
					"PubInfoApi/GetCitySelect1", 
					null,
					function(backVal, $obj) {
						var cityId=$obj.attr("data-id");
						var divs = document.getElementById("addcboxId").childNodes;
						for(var i=0;i<divs.length;i++){
							if(divs[i].id == cityId){
								toastr.error("城市已经存在，请重新选择", "提示");
								return;
							}
						}
						var cityName=$obj.text();
						var cityhtml='<div class="added" id="'+cityId+'">'+cityName+'<em class="ico_x_a"></em></div>';
						var tempStr = $(".addcbox").text();
						var bool = tempStr.indexOf(cityName);
						if(bool<1){
							$(".addcbox").append(cityhtml);
						}
					}
				);
			}

			$(".addcbox").on('click','.ico_x_a', function () {
				$(this).parent(".added").remove();
			});
		</script>
	</body>
</html>
