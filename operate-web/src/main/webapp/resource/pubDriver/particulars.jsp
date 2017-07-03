<%@ page contentType="text/html; charset=UTF-8"
	import="com.szyciov.util.SystemConfig"%>
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
<title>司机管理</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="content/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="content/plugins/toastr/toastr.css" />
<link rel="stylesheet" type="text/css"
	href="content/plugins/select2/select2.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="content/plugins/select2/select2_metro.css" rel="stylesheet">

<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/js/bootstrap.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript"
	src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
<script type="text/javascript"
	src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript"
	src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
<script type="text/javascript"
	src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
<script type="text/javascript"
	src="content/plugins/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="content/plugins/select2/app.js"></script>
<script src="content/plugins/fileupload/jquery.ui.widget.js"></script>
<script src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
<script src="content/plugins/fileupload/jquery.fileupload.js"></script>
	<style type="text/css">
		/* 目前样式select在form中无法自适应 */
		.form label{
			float:left;
		}
		.form select,.form input[type=text]{
			width:70%;
			float:left;
		}
		.form label{
			line-height: 30px;
			height:30px;
		}
		.form .select2-container{
			width:70%;
			float:left;
			margin-top: -5px;
		}
		.breadcrumb{text-decoration:underline;}
	 	@media screen and (min-width: 790px) and (max-width: 1100px) {
			.form label{
				padding-right:0;
			}
			.editdriver_css_col_1 input{
				margin-left:13px;
			}
		}
		.editdriver_css_col_1{
			line-height:200%;
		}
	</style>
</head>
<body>
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a href="PubDriver/Index" class="breadcrumb">司机管理 </a> > 司机信息
		<button class="SSbtn blue back" onclick="callBack()">返回</button>
	</div>
	<div class="content">
		<form id="editDriverForm" class="form" method="post">
			<div class="row">
				<div class="col-4">
					<label>资格证号<em class="asterisk"></em></label>
					<input id="id" name="id" value="${pubDriver.id}" type="hidden"/>
					<input type="text" placeholder="司机工号" name="jobNum" id="jobNum" value="${pubDriver.jobNum}" disabled="disabled">
				</div>
				<div class="col-4"><label>司机类型<em class="asterisk"></em></label>
					<input id="vehicleType1" name="vehicleType1" type="hidden" value="${pubDriver.vehicleType}"/>
					<!--  网约车 0 、 出租车 1  -->
					<select id="vehicleType" name="vehicleType" disabled="disabled">
						<c:choose>
		               		<c:when test="${pubDriver.vehicleType == '1'}">
								<option value="1" selected="selected" disabled="disabled">出租车</option>
		               		</c:when>
		               		<c:otherwise> 
							   	<option value="0" selected="selected" disabled="disabled">网约车</option>
				   			</c:otherwise>
		               </c:choose>
					</select>
				</div>
				<div class="col-4">
					<label>司机姓名<em class="asterisk"></em></label>
					<input type="text" placeholder="司机姓名" id="name" name="name" value="${pubDriver.name }" disabled="disabled">
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>驾驶工龄<em class="asterisk"></em></label><input id="driverYears" name="driverYears"
						type="text" placeholder="驾驶工龄" value="${pubDriver.driverYears}"  disabled="disabled" style="width:58%;">&nbsp;&nbsp;&nbsp;年
				</div>
				<div class="col-4 editdriver_css_col_1">
					<label style="margin-left: -19px;">性别</label>
					<c:choose>
					   <c:when test="${pubDriver.sex != null && pubDriver.sex != ''}">  
			               <c:choose>
			               		<c:when test="${pubDriver.sex == '1'}">
			               			<input id="sex" name="sex" type="radio" value="0" disabled="disabled" />男 
									<input id="sex" name="sex" type="radio" value="1" checked="checked" disabled="disabled"/>女
			               		</c:when>
			               		<c:otherwise> 
								   <input id="sex" name="sex" type="radio" value="0" checked="checked" disabled="disabled"/>男 
								   <input id="sex" name="sex" type="radio" value="1" disabled="disabled"/>女
					   			</c:otherwise>
			               </c:choose>     
					   </c:when>
					   <c:otherwise> 
						   <input id="sex" name="sex" type="radio" value="0" checked="checked" disabled="disabled"/>男 
						   <input id="sex" name="sex" type="radio" value="1" disabled="disabled"/>女
					   </c:otherwise>
					</c:choose>
				</div>
				<div class="col-4">
					<label>驾驶类型<em class="asterisk"></em></label> <select id="driversType" name="driversType" disabled="disabled">
							<option value="${pubDriver.driversType}">${pubDriver.driversTypeName}</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>手机号码<em class="asterisk"></em></label>
					<input type="text" placeholder="手机号码" id="phone" name="phone" value="${pubDriver.phone }" disabled="disabled">
				</div>
				<div class="col-4">
					<label>登记城市<em class="asterisk"></em></label> <select id="city" name="city"  disabled="disabled">
							<option value="${pubDriver.city}">${pubDriver.cityName}</option>
					</select>
				</div>
				<div class="col-4 editdriver_css_col_1">
					<label style="margin-left: -19px;">在职状态</label>
					<c:choose>
					   <c:when test="${pubDriver.jobStatus != null && pubDriver.jobStatus != ''}">  
			               <c:choose>
			               		<c:when test="${pubDriver.jobStatus == '1'}">
			               			<input id="jobStatus" name="jobStatus" type="radio" value="0" disabled="disabled"/>在职 
					   	   			<input id="jobStatus" name="jobStatus" type="radio" value="1" checked="checked"disabled="disabled"/>离职
			               		</c:when>
			               		<c:otherwise> 
			               			<input id="jobStatus" name="jobStatus" type="radio" value="0" checked="checked" disabled="disabled"/>在职 
					   	   			<input id="jobStatus" name="jobStatus" type="radio" value="1" disabled="disabled"/>离职
					   			</c:otherwise>
			               </c:choose>     
					   </c:when>
					   <c:otherwise> 
						   <input id="jobStatus" name="jobStatus" type="radio" value="0" checked="checked" disabled="disabled"/>在职 
					   	   <input id="jobStatus" name="jobStatus" type="radio" value="1" disabled="disabled"/>离职
					   </c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<!--  logo -->
					<label style="text-align: left;"><font size="5">司机照片</font></label>
					<HR style="border:3 solid #ff0033" width="100%" SIZE=3>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<input id="headPortraitMax" name="headPortraitMax" type="hidden"> 
					<input id="fileupload" type="file" name="file" multiple style="display:none;"/>
					<div id="imgShow"  style="width: 197px; height: 130px;margin-left: 6%;">
						<c:choose>
						   	<c:when test="${pubDriver.headPortraitMax != null && pubDriver.headPortraitMax != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.headPortraitMax}" style="width: 197px; height: 130px; background-color: gray;" id="imgback"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/shangchuan.png" id="imgback"></img>
						   	</c:otherwise>
						</c:choose> 
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<label style="text-align: left;"><font size="5">司机驾驶证</font></label>
					<HR style="border:3 solid #ff0033" width="100%" SIZE=3> 
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<input type="hidden" id="driverPhoto" name="driverPhoto"> 
					<input id="fileupload2" type="file" name="file" multiple  style="display: none;"/>
					<div id="imgShow2" style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${pubDriver.driverPhoto != null && pubDriver.driverPhoto != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.driverPhoto}" style="width: 197px; height: 130px; background-color: gray;" id="imgback2"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/shangchuan.png" id="imgback2"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
					</div>
				</div>
				<div class="col-4">
					<label></label>
					<input id="driversNum" name="driversNum" type="text" placeholder="请填写驾驶证号" value="${pubDriver.driversNum }"  disabled="disabled" style="margin-top: 50px;">
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<label style="text-align: left;"><font size="5">司机身份证</font></label>
					<HR style="border:3 solid #ff0033" width="100%" SIZE=3> 
				</div>
			</div>
			<div class="row">
				<div class="col-4"> 
					<input type="hidden" id="idCardFront" name="idCardFront"> 
					<input id="fileupload3" type="file" name="file" multiple  style="display: none;"/>
					<div id="imgShow3" style="width: 197px; height: 130px;margin-left:19%;">
						<c:choose>
						   	<c:when test="${pubDriver.idCardFront != null && pubDriver.idCardFront != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.idCardFront}" style="width: 197px; height: 130px; background-color: gray;" id="imgback3"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/shangchuan.png" id="imgback3"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
					</div>
				</div>
				<div class="col-4">
					<input type="hidden" id="idCardBack" name="idCardBack"> 
					<input id="fileupload4" type="file" name="file" multiple  style="display: none;"/>
					<div id="imgShow4">
						<c:choose>
						   	<c:when test="${pubDriver.idCardBack != null && pubDriver.idCardBack != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.idCardBack}" style="width: 197px; height: 130px; background-color: gray;" id="imgback4"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/shangchuan.png" id="imgback4"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
					</div>
				</div>
				<div class="col-4">
					<input type="text" id="idCardNum" name="idCardNum" placeholder="请填写身份证号码" value="${pubDriver.idCardNum }"  disabled="disabled" style="margin-top: 50px;">
				</div>
			</div>
		</form>
	</div>
	
	<div class="pop_box" id="addServiceOrg" style="display: none;">
		<div class="tip_box_c">
            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
            <h3 id="bindingVelTitleForm">选择机构</h3>
            <div class="row form">
            	<input type="hidden" id="driid" name="driid">
	            <div class="col-4"><label>机构名称</label>
					<select id="queryServiceOrgFullName" name="queryServiceOrgFullName">
						<option value="">全部</option>
						<c:forEach var="orgOrganShortName" items="${orgOrganShortName}">
							<option value="${orgOrganShortName.fullName}">${orgOrganShortName.fullName}</option>
						</c:forEach>
					</select>
				</div>
	            <div class="col-4"><label>所属城市</label>
	            	<select id="queryServiceOrgCity" name="queryServiceOrgCity">
						<option value="">全部</option>
						<c:forEach var="orgOrganCity" items="${orgOrganCity}">
							<option value="${orgOrganCity.city}">${orgOrganCity.city}</option>
						</c:forEach>
					</select>
	            </div>
	            <div  class="col-4" style="text-align: right;">
	                <button class="Mbtn green_a" onclick="query();">查询</button>
	                <!-- <button class="Mbtn red" style="margin-left: 20px;">重置</button> -->
	            </div>
		        <table id=addServiceOrgGrid></table>
		        <div class="row">
		        	<div  class="col-4" style="text-align: right;">
		                <button type="button" class="Mbtn green_a" onclick="saveOrg();">确定</button>
	           		</div>
	           		<div  class="col-4" style="text-align: right;">
		                <button class="Mbtn red" style="margin-left: 20px;" onclick="cancle();">取消</button>
	           		</div>
		        </div>
        	</div>
		</div>
	</div>
	<!-- 放大图片 -->
	<div class="pop_box" id="openImg" style="display: none;">
		<div class="tip_box_b">
		<img src="content/img/btn_guanbi.png" class="close" alt="关闭" style="margin-top: 14px;">
		<h3 id="openTitle">选择机构</h3>
		<div id="openImg1">
		
		</div>
        </div>
	</div>
	
	<script type="text/javascript" src="js/pubDriver/particulars.js"></script>
	<script type="text/javascript">
		var serviceAddress = "<%=SystemConfig.getSystemProperty("fileserver")%>";
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		$(function(){
			$("#imgback").click(function(){
				var img = $("#imgback").attr("src");
				$("#openTitle").html("司机照片");
				var html = "<img src="+img+"></img>";
				$("#openImg1").html(html);
				$("#openImg").show();
			});
			
			$("#imgback2").click(function(){
				var img = $("#imgback2").attr("src");
				$("#openTitle").html("司机驾驶证");
				var html = "<img src="+img+"></img>";
				$("#openImg1").html(html);
				$("#openImg").show();
			});
			
			$("#imgback3").click(function(){
				var img = $("#imgback3").attr("src");
				$("#openTitle").html("司机身份证正面");
				var html = "<img src="+img+"></img>";
				$("#openImg1").html(html);
				$("#openImg").show();
			});
			
			$("#imgback4").click(function(){
				var img = $("#imgback4").attr("src");
				$("#openTitle").html("司机身份证背面");
				var html = "<img src="+img+"></img>";
				$("#openImg1").html(html);
				$("#openImg").show();
			});
			var vehicleType1 = $("#vehicleType1").val();
			if(vehicleType1 == 1){
				$("#idEntityTypeDiv").hide();
			}
		});
	</script>
</body>
</html>
