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
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>维护规则</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/ztimepicker/ztimepicker1.css" rel="stylesheet">
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="content/plugins/letterselect/letterselect.js"></script>
		<script type="text/javascript" src="content/plugins/ztimepicker/ztimepicker1.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	
	<style>
        /* 前端对于类似页面的补充 */
        .breadcrumb{text-decoration:underline;}
        
        .form label{float:left;line-height: 30px;height:30px;}
		.form select,.form input[type=text]{width:68%;float:left;}
		.form #inp_box1 label.error{padding-left: 0;}		
		
		@media screen and (min-width: 790px) and (max-width: 1310px) {
			.form .edit_label{
				width:37% !important;
				padding-right:0;
			}
			.form select, .form input{
				width:32% !important;
			}
			#inp_box1{
				width:32% !important;
			} 
			#inp_box1 input{
				width:100% !important;
			}
			.form label.error{
				padding-left:37%;
			}
			#inp_box1 label[for=cityName]{
				width:158%;
			}
		}
		#ztimepicker1 .error{
		margin-left:-3%;
		padding-left:0;
}
        #ztimepicker2 .error{
		margin-left:6%
}
    </style>

	<body>
		<div class="crumbs">
		     <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="StandardAccountRules/Index">标准计费规则</a> > <c:if test="${leAccountRules.id == null or leAccountRules.id == ''}">新增规则</c:if><c:if test="${leAccountRules.id != null and leAccountRules.id != ''}">修改规则</c:if>
		     <button class="SSbtn blue back" onclick="window.history.go(-1);">返回</button>
		</div>
		
		<div class="content">
		  <form id="formss">
		    <input name="id" id="id" value="${leAccountRules.id}" type="hidden">
		    <input name="cityValue" id="cityValue" type="hidden" value="${leAccountRules.city}">
		    <input name="cityNameHidden" id="cityNameHidden" type="hidden" value="${leAccountRules.cityName}">
		    <input name="rulestype" id="rulestype" type="hidden" value="${leAccountRules.rulesType}">
		    <input name="cartype" id="cartype" type="hidden" value="${leAccountRules.carType}">
		    <input name="timetype" id="timetype" type="hidden" value="${leAccountRules.timeType}">
			<div class="row" style="padding-top: 30px;">
				<div class="row form">
					<div class="col-4">
						<label class="edit_label">城市名称<em class="asterisk"></em></label>
						<input type="hidden" id="city" name="city">
						<div id="inp_box1" style="width: 68%">
							<input type="text" id="cityName" name="cityName" style="width: 100%" onfocus="getSelectCity();" readonly>
						</div>
					</div>
					
					<div class="col-4">
						<label class="edit_label">规则类型<em class="asterisk"></em></label>
						<select id="rulesType" name="rulesType">
							<option value="" selected="selected">请选择</option>
							<c:forEach var="rulesTypeDictionary" items="${rulesTypeDictionary}">
								<option value="${rulesTypeDictionary.value}">${rulesTypeDictionary.text}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-4">
						<label class="edit_label">服务车型<em class="asterisk"></em></label>
						<select id="carType" name="carType" value="${leAccountRules.carType}">
							<option value="" selected="selected">请选择</option>
							<c:forEach var="leVehiclemodels" items="${leVehiclemodels}">
								<option value="${leVehiclemodels.id}">${leVehiclemodels.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row form">
					<div class="col-4">
						<label class="edit_label">起步价<em class="asterisk"></em></label>
						<input name="startPrice" id="startPrice" type="text" value="${leAccountRules.startPrice}" placeholder="起步价" style="width: 63%;" maxlength="6">&nbsp;&nbsp;元
					</div>
					<div class="col-4">
						<label class="edit_label">里程价<em class="asterisk"></em></label>
						<input name="rangePrice" id="rangePrice" type="text" value="${leAccountRules.rangePrice}" placeholder="里程价格" style="width: 55%;" maxlength="5">&nbsp;&nbsp;元/公里
					</div>
					<div class="col-4">
						<label class="edit_label">时间价<em class="asterisk"></em></label>
						<input name="timePrice" id="timePrice" type="text" value="${leAccountRules.timePrice}" placeholder="时间价格" style="width: 55%;" maxlength="4">&nbsp;&nbsp;元/分钟
					</div>
				</div>
				<div class="row form">
					<div class="col-4">
						<label class="edit_label">时间类型<em class="asterisk"></em></label>                   
						<select id="timeType" name="timeType" onchange= "changeTimeType()">
							<option value="" selected="selected">请选择</option>
							<option value="0">总用时</option>
							<option value="1">低速用时</option>
						</select>				    
				    </div>
			        <div id="perhourDiv" style="display: ${leAccountRules.timeType == '0'?'none':'block'};" class="col-4">
						<label class="edit_label">时速<em class="asterisk"></em></label>
						<input name="perhour" id="perhour" type="text" value="${leAccountRules.perhour}" placeholder="时速" style="width: 52%" maxlength="5">&nbsp;&nbsp;公里/小时
					</div>
					<div class="col-4">
						<label class="edit_label">回空里程<em class="asterisk"></em></label>
						<input name="deadheadmileage" id="deadheadmileage" type="text" value="${leAccountRules.deadheadmileage}" placeholder="回空里程" style="width: 55%;" maxlength="5">&nbsp;&nbsp;公里
					</div>
				</div>
				<div class="row form">
					<div class="col-4">
						<label class="edit_label">回空费价<em class="asterisk"></em></label>
						<input name="deadheadprice" id="deadheadprice" type="text" value="${leAccountRules.deadheadprice}" placeholder="回空费价" style="width: 55%;" maxlength="5">&nbsp;&nbsp;元/公里
					</div>
					<div class="col-4">
						<label class="account-items">夜间征收时段<em class="asterisk"></em></label>
						<div class="col-1">
		                    <div class="ztimepicker" id="ztimepicker1">
								<input type="text" readonly  class="ztimepicker_input" name="nightstarttime" id="nightstarttime" value="${leAccountRules.nightstarttime}"style="width:45%;margin-left:-3%;"/><span style="margin-left: 2%;">-</span>
								<div class="ztimebox" style="margin-left:-3%;">
								<div class="znow">时 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分</div>
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-1">
							<div class="ztimepicker"id="ztimepicker2">
								<input type="text" readonly  class="ztimepicker_input" name="nightendtime" id="nightendtime" value="${leAccountRules.nightendtime}"style="width:45%;margin-left:36%;"/>
								<div class="ztimebox" style="margin-left:36%;">
								<div class="znow">时 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分</div>
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
					</div>
				<div class="col-4">
					<label class="edit_label">夜间费价<em class="asterisk"></em></label>
					<input name="nighteprice" id="nighteprice" type="text" value="${leAccountRules.nighteprice}" placeholder="夜间费价" style="width: 55%;" maxlength="5">&nbsp;&nbsp;元/公里
				</div>
			</div>
			</div>
		  </form>
		  <div class="row">	
				<div class="col-12" style="text-align: right;">
                     <button class="Mbtn orange" style="margin-right: 20px;" onclick="save()">保存</button>
                     <button class="Mbtn grey" onclick="window.history.go(-1);">取消</button>
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
		
		<script type="text/javascript" src="js/standardAccountRules/editRules.js"></script>
		<script type="text/javascript">
		$(function(){
			/* 火狐浏览器兼容问题 */
			 if (navigator.userAgent.indexOf('Firefox') >= 0){
				 $('select').css('padding-top','5px');
			 }
		})
			
		</script>
	</body>
</html>
