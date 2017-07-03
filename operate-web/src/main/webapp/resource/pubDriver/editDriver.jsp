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
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">

<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/js/bootstrap.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>

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
<script src="content/plugins/fileupload/jquery.ui.widget.js"></script>
<script src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
<script src="content/plugins/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="content/plugins/letterselect/letterselect.js"></script>
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
		th, td { white-space: nowrap; }
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
		margin-top:-20px;
		}
		.pop_box{z-index: 1111 !important;}
		.breadcrumb{text-decoration:underline;}
		.tip_box_d .row {
            margin-right: -5px;
            margin-left: -5px;
        }
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
		#inp_box1>.city_container{
			margin-top: 30px;
		}
	</style>
</head>
<body style="overflow-x:hidden;overflow-y:hidden;">
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a href="PubDriver/Index" class="breadcrumb">司机管理 </a> > ${title}
		<button class="SSbtn blue back" onclick="callBack()">返回</button>
	</div>
	<div class="content">
		<form id="editDriverForm" class="form" method="post">
			<div class="row">
				<div class="col-4">
					<label>资格证号<em class="asterisk"></em></label>
					<input id="id" name="id" value="${pubDriver.id}" type="hidden"/>
					<input type="text" placeholder="司机资格证号" name="jobNum" id="jobNum" value="${pubDriver.jobNum}" maxlength="18">
				</div>
				<div class="col-4"><label>司机类型<em class="asterisk"></em></label>
					<!--  网约车 0 、 出租车 1  -->
					<input id="vehicleType1" name="vehicleType1" type="hidden" value="${pubDriver.vehicleType}"/>
					<select id="vehicleType" name="vehicleType" onclick="getIdEntityTypeDiv()">
						<c:choose>
		               		<c:when test="${pubDriver.vehicleType == '1'}">
		               			<option value="0">网约车</option>
								<option value="1" selected="selected">出租车</option>
		               		</c:when>
		               		<c:otherwise> 
							   	<option value="0" selected="selected">网约车</option>
								<option value="1">出租车</option>
				   			</c:otherwise>
		               </c:choose>
						
					</select>
				</div>
				<div class="col-4">
					<label>姓名<em class="asterisk"></em></label>
					<input type="text" placeholder="司机姓名" id="name" name="name" value="${pubDriver.name }" maxlength="20">
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>驾驶工龄<em class="asterisk"></em></label><input id="driverYears" name="driverYears"
						type="text" placeholder="驾驶工龄" value="${pubDriver.driverYears}" style="width: 58%;" maxlength="4">&nbsp;&nbsp;&nbsp;年
				</div>
				<div class="col-4 editdriver_css_col_1">
					<label style="margin-left: -19px;">性别</label>
					<c:choose>
					   <c:when test="${pubDriver.sex != null && pubDriver.sex != ''}">  
			               <c:choose>
			               		<c:when test="${pubDriver.sex == '1'}">
			               			<input id="sex" name="sex" type="radio" value="0"  />男 
									<input id="sex" name="sex" type="radio" value="1" checked="checked" />女
			               		</c:when>
			               		<c:otherwise> 
								   <input id="sex" name="sex" type="radio" value="0" checked="checked" />男 
								   <input id="sex" name="sex" type="radio" value="1" />女
					   			</c:otherwise>
			               </c:choose>     
					   </c:when>
					   <c:otherwise> 
						   <input id="sex" name="sex" type="radio" value="0" checked="checked" />男 
						   <input id="sex" name="sex" type="radio" value="1" />女
					   </c:otherwise>
					</c:choose>
				</div>
				<div class="col-4">
					<label>驾驶类型<em class="asterisk"></em></label>
					<select id="driversType" name="driversType">
						<option value="">请选择</option>
						<c:forEach var="driversType" items="${driversType}">
							<c:choose>
								<c:when test="${pubDriver.driversTypeName == driversType.text}">
									<option value="${driversType.value}" selected="selected">${driversType.text}</option>
								</c:when>
								<c:otherwise>
									<option value="${driversType.value}">${driversType.text}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
				
			</div>
			<div class="row">
				<div class="col-4">
					<label>手机号码<em class="asterisk"></em></label>
					<input type="text" placeholder="手机号码"  autocomplete='off' id="phone" name="phone" value="${pubDriver.phone }" maxlength="11">
				</div>
				<div class="col-4">
					<label>登记城市<em class="asterisk"></em></label>
					<input type="hidden" id="city" name="city" value="${pubDriver.city}">
					<input type="hidden" name="citymarkId" id="citymarkId" value="${pubDriver.citymarkId}">
						<div id="inp_box1" style="width: 70%">
							<input type="text" id="cityName" style="width: 100%;" value="${pubDriver.cityName}"  readonly="readonly">
						</div>  
					<%-- <select id="city" name="city">
							<option value="${pubDriver.city}">${pubDriver.cityName}</option>
						<c:forEach var="pubCityaddr" items="${pubCityaddr}">
							<option value="${pubCityaddr.id}">${pubCityaddr.city}</option>
						</c:forEach>
					</select> --%>
				</div>
				<div class="col-4 editdriver_css_col_1">
					<label style="margin-left: -19px;">在职状态</label>
					<c:choose>
					   <c:when test="${pubDriver.jobStatus != null && pubDriver.jobStatus != ''}">  
			               <c:choose>
			               		<c:when test="${pubDriver.jobStatus == '1'}">
			               			<input id="jobStatus" name="jobStatus" type="radio" value="0" />在职 
					   	   			<input id="jobStatus" name="jobStatus" type="radio" value="1" checked="checked"/>离职
			               		</c:when>
			               		<c:otherwise> 
			               			<input id="jobStatus" name="jobStatus" type="radio" value="0" checked="checked"/>在职 
					   	   			<input id="jobStatus" name="jobStatus" type="radio" value="1" />离职
					   			</c:otherwise>
			               </c:choose>     
					   </c:when>
					   <c:otherwise> 
						   <input id="jobStatus" name="jobStatus" type="radio" value="0" checked="checked" />在职 
					   	   <input id="jobStatus" name="jobStatus" type="radio" value="1" />离职
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
				<div class="col-4">
					<input id="headPortraitMax" name="headPortraitMax" type="hidden" value="${pubDriver.headPortraitMax}"> 
					<!-- <input id="fileupload" type="file" name="file" multiple style="width: 197px; height: 130px;"/> -->
					<div style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${pubDriver.headPortraitMax != null && pubDriver.headPortraitMax != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.headPortraitMax}" style="width: 197px; height: 130px; background-color: gray;" id="imgback"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img src="img/pubDriver/zhengmian.jpg" style="width: 197px; height: 130px;" id="imgback">
						   	</c:otherwise>
						</c:choose>
                		<input id="fileupload" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;" name="file" multiple/>
                	</div>
                	<a id="clear" href="javascript:void(0)" style="margin-left: 36%;">删除</a>
					<%-- <div id="imgShow" style="margin-left: 80px;">
						<c:choose>
						   	<c:when test="${pubDriver.headPortraitMax != null && pubDriver.headPortraitMax != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.headPortraitMax}" style="width: 197px; height: 130px; background-color: gray;" id="imgback"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px;" src="img/pubDriver/zhengmian.jpg" id="imgback"></img>
						   	</c:otherwise>
						</c:choose> 
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<a id="clear" href="javascript:void(0)">删除</a>
					</div> --%>
				</div>
				<div class="col-4" style="margin-left: 1%;">
					<div>
						<div>照片要求：</div>
						<div>1、司机本人照片</div>
						<div>2、尺寸不低于的2寸证件照</div>
						<div>3、拍照时间为1年以内</div>
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
					<input type="hidden" id="driverPhoto" name="driverPhoto" value="${pubDriver.driverPhoto}"> 
					<!-- <input id="fileupload2" type="file" name="file" multiple  style="display: none;"/> -->
					<div style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${pubDriver.driverPhoto != null && pubDriver.driverPhoto != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.driverPhoto}" style="width: 197px; height: 130px; background-color: gray;" id="imgback2"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img src="img/pubDriver/zhengmian.jpg" style="width: 197px; height: 130px;" id="imgback2">
						   	</c:otherwise>
						</c:choose> 
                		<input id="fileupload2" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;" name="file" multiple/>
                	</div>
                	<a id="clear2" href="javascript:void(0)" style="margin-left: 36%;">删除</a>
					<%-- <div id="imgShow2" style="margin-left: 80px;">
						<c:choose>
						   	<c:when test="${pubDriver.driverPhoto != null && pubDriver.driverPhoto != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.driverPhoto}" style="width: 197px; height: 130px; background-color: gray;" id="imgback2"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px;" src="img/pubDriver/zhengmian.jpg" id="imgback2"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<a id="clear2" href="javascript:void(0)">删除</a>
					</div> --%>
				</div>
				<div class="col-4">
					<label></label>
					<input id="driversNum" name="driversNum" type="text" placeholder="请填写驾驶证号" value="${pubDriver.driversNum }" style="margin-top: 50px;" maxlength="18">
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
					<input type="hidden" id="idCardFront" name="idCardFront" value="${pubDriver.idCardFront}"> 
					<!-- <input id="fileupload3" type="file" name="file" multiple  style="display: none;"/> -->
					<div style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${pubDriver.idCardFront != null && pubDriver.idCardFront != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.idCardFront}" style="width: 197px; height: 130px; background-color: gray;" id="imgback3"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img src="img/pubDriver/zhengmian.jpg" style="width: 197px; height: 130px;" id="imgback3">
						   	</c:otherwise>
						</c:choose> 
                		<input id="fileupload3" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;" name="file" multiple/>
                	</div>
                	<a id="clear3" href="javascript:void(0)" style="margin-left: 36%;">删除</a>

				</div>
				<div class="col-4">
					<input type="hidden" id="idCardBack" name="idCardBack" value="${pubDriver.idCardBack}"> 
					<!-- <input id="fileupload4" type="file" name="file" multiple  style="display: none;"/> -->
					<div style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${pubDriver.idCardBack != null && pubDriver.idCardBack != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.idCardBack}" style="width: 197px; height: 130px; background-color: gray;" id="imgback4"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img src="img/pubDriver/beimian.jpg" style="width: 197px; height: 130px;" id="imgback4">
						   	</c:otherwise>
						</c:choose> 
                		<input id="fileupload4" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;" name="file" multiple/>
                	</div>
                	<a id="clear4" href="javascript:void(0)" style="margin-left: 36%;">删除</a>
					<%-- <div id="imgShow4" style="margin-left: 80px;">
						<c:choose>
						   	<c:when test="${pubDriver.idCardBack != null && pubDriver.idCardBack != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${pubDriver.idCardBack}" style="width: 197px; height: 130px; background-color: gray;" id="imgback4"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px;" src="img/pubDriver/beimian.jpg" id="imgback4"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<a id="clear4" href="javascript:void(0)">删除</a>
					</div> --%>
				</div>
				<div class="col-4">
					<label></label>
					<input type="text" id="idCardNum" name="idCardNum" placeholder="请填写身份证号码" value="${pubDriver.idCardNum }" style="margin-top: 50px;" maxlength="18">
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-10" style="text-align: right;">
				<button class="Mbtn green_a" onclick="save();">保存</button>
				<button class="Mbtn red" style="margin-left: 20px;" onclick="callBack()">取消</button>
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
	
	<script type="text/javascript" src="js/pubDriver/editDriver.js"></script>
	<script type="text/javascript" src="js/pubDriver/reg.js"></script>
	<script type="text/javascript">
		var serviceAddress = "<%=SystemConfig.getSystemProperty("fileserver")%>";
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		$(function(){ 
			var title = "<%=request.getParameter("title")%>";
			var vehicleType1 = $("#vehicleType1").val();
			if(title == '维护司机信息'){
				$("#vehicleType").attr("disabled","disabled");
			}
			if(vehicleType1 == 1){
				$("#idEntityTypeDiv").hide();
			}
		});
	</script>
</body>
</html>
