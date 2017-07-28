<%@ page contentType="text/html; charset=UTF-8"
	import="com.szyciov.util.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String yingyan_ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>新增客户</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="content/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="content/plugins/toastr/toastr.css" />
<link rel="stylesheet" type="text/css"
	href="content/plugins/select2/select2.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="content/plugins/select2/select2_metro.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
<link rel="stylesheet" type="text/css" href="content/css/leLeasescompany_css_media.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		
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
<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
<script type="text/javascript"
	src="content/plugins/select2/select2_locale_zh-CN.js"></script>
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
		.tip_box_c{
		    max-height: 726px;
		}
		
		.content{padding-top:20px;}
		.tangram-suggestion-main{z-index:10000;}
		.breadcrumb{text-decoration:underline;}
		#inp_box1>.city_container{
			margin-top: 29.6px;
		}
	</style>
</head>
<body class="leLeasescompany_css_body">
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="LeLeasescompany/Index">客户管理</a> > 新增客户
		<button class="SSbtn red_q back" onclick="callBack()">返回</button>
	</div>
	<div class="content" style="padding-top:30px;">
		<form id="editLeLeasescompanyForm" class="form" method="post">
			<div class="row leLeasescompany_css_row_1">
				<div class="col-4">
					<label class="leLeasescompany_css_label_1">客户全称<em class="asterisk"></em></label>
					<input id="id" name="id" value="" type="hidden"/>
					<input class="leLeasescompany_css_input_1" type="text" placeholder="服务车企全称" name="name" id="name" value="" maxlength="20">
				</div>
				<div class="col-4">
					<label class="leLeasescompany_css_label_1">客户简称<em class="asterisk"></em></label>
					<input class="leLeasescompany_css_input_1" type="text" placeholder="服务车企简称" id="shortName" name="shortName" value="" maxlength="6">
				</div>
				<div class="col-4">
					<label class="leLeasescompany_css_label_1">账号名称<em class="asterisk"></em></label>
					<input class="leLeasescompany_css_input_1" type="text" placeholder="服务车企超级管理员账号" id="account" name="account" value="" maxlength="15">
				</div>
			</div>
			<div class="row leLeasescompany_css_row_1">
				<div class="col-4">
					<label class="leLeasescompany_css_label_1">联系人<em class="asterisk"></em></label>
					<input class="leLeasescompany_css_input_1" type="text" placeholder="联系人" id="contacts" name="contacts" value="" maxlength="5">
				</div>
				<div class="col-4">
					<label class="leLeasescompany_css_label_1">联系方式<em class="asterisk"></em></label>
					<input class="leLeasescompany_css_input_1" type="text" placeholder="手机号码" id="phone" name="phone" value="" maxlength="11">
				</div>
				<div class="col-4">
					<label class="leLeasescompany_css_label_1">邮箱<em class="asterisk"></em></label>
					<input class="leLeasescompany_css_input_1" id="mail" name="mail" type="text" placeholder="邮箱" value="" maxlength="50">
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label class="leLeasescompany_css_label_2">详细地址<em class="asterisk"></em></label> 
					<%-- <select id="city" name="city">
						<c:forEach var="pubCityaddr" items="${pubCityaddr}">
							<option value="${pubCityaddr.id}" data-owner="city">${pubCityaddr.city}</option>
						</c:forEach>
					</select> --%>
					<!-- <input type="hidden" id="city" name="city" value="">
					<div class="leLeasescompany_css_inp_2" id="inp_box1" style="width: 70%">
					<input type="text" id="cityName" readonly="readonly" name="cityName" value="" style="width: 100%" onfocus="getSelectCity();">
					</div> -->
					<input type="hidden" id="city" name="city" value="">
       				<input type="hidden" name="citymarkid" id="citymarkid" value="">
					<div id="inp_box1" style="width: 70%">
						<input type="text" id="cityName" style="width: 104%" value="" readonly="readonly">
					</div>
				</div>
				<div class="col-4">
					<input id="addressHidden" type="hidden" name="address" value=""/>
					<input id="address" type="text" placeholder="可输入搜索" value="" data-owner="addressHidden" maxlength="100"/>
					<input id="addressButton" name="" type="button" value="地图" style="width: 50px;height: 30px;"/>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<label style="text-align: left;"><font size="5">工商执照</font></label>
					<HR style="border:3 solid #ff0033" width="100%" SIZE=3>
				</div>
			</div>
			<div class="row">
				<div class="col-4">  
					<input type="hidden" id="bizlicPic" name="bizlicPic" value=""> 
					<!-- <input id="fileupload2" type="file" name="file" multiple  style="display: none;"/> -->
					<div style="width: 197px; height: 130px;margin-left:19%;">
				   		<img src="img/leLeasescompany/zhengmian.jpg" style="width: 197px; height: 130px;" id="imgback2">
                		<input id="fileupload2" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;font-size: 66px;" name="file" multiple/>
                	</div>
                	<a id="clear2" href="javascript:void(0)" style="margin-left: 36%;"><font color="red">删除</font></a>
					<%-- <div id="imgShow2">
						<c:choose>
						   	<c:when test="${orgOrgan.businessLicensePic != null && orgOrgan.businessLicensePic != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.businessLicensePic}" style="width: 197px; height: 130px; background-color: gray;" id="imgback2"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;"  src="img/leLeasescompany/zhengmian.jpg" id="imgback2"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<a id="clear2" href="javascript:void(0)">删除</a>
					</div> --%>
				</div>
				<div class="col-4">
					<label></label>
					<input id="bizlicNum" name="bizlicNum" type="text" placeholder="请填写执照编码" value="" style="margin-top: 50px;" maxlength="15">
				</div>
			</div>
			<div class="row">
				<div class="col-12">
				<label style="text-align: left;"><font size="5">法人身份证</font></label>
					<HR style="border:3 solid #ff0033" width="100%" SIZE=3>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<input type="hidden" id="idCardFront" name="idCardFront" value=""> 
					<!-- <input id="fileupload3" type="file" name="file" multiple  style="display: none;"/> -->
					<div style="width: 197px; height: 130px;margin-left:19%;">
				   		<img src="img/leLeasescompany/zhengmian.jpg" style="width: 197px; height: 130px;" id="imgback3">
                		<input id="fileupload3" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;font-size: 66px;" name="file" multiple/>
                	</div>
					<a id="clear3" href="javascript:void(0)" style="margin-left: 36%;"><font color="red">删除</font></a>
					<%-- <div id="imgShow3">
						<c:choose>
						   	<c:when test="${orgOrgan.idCardFront != null && orgOrgan.idCardFront != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.idCardFront}" style="width: 197px; height: 130px; background-color: gray;" id="imgback3"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/leLeasescompany/zhengmian.jpg" id="imgback3"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<a id="clear3" href="javascript:void(0)">删除</a>
					</div> --%>
				</div>
				<div class="col-4">
					<input type="hidden" id="idCardBack" name="idCardBack" value=""> 
					<!-- <input id="fileupload4" type="file" name="file" multiple  style="display: none;"/> -->
					<div style="width: 197px; height: 130px;margin-left:19%;">
				   		<img src="img/leLeasescompany/beimian.jpg" style="width: 197px; height: 130px;" id="imgback4">
                		<input id="fileupload4" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;font-size: 66px;" name="file" multiple/>
                	</div>
					<a id="clear4" href="javascript:void(0)" style="margin-left: 36%;"><font color="red">删除</font></a>
					<%-- <div id="imgShow4">
						<c:choose>
						   	<c:when test="${orgOrgan.idCardBack != null && orgOrgan.idCardBack != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.idCardBack}" style="width: 197px; height: 130px; background-color: gray;" id="imgback4"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width:197px; height: 130px; background-color: gray;" src="img/leLeasescompany/beimian.jpg" id="imgback4"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<a id="clear4" href="javascript:void(0)">删除</a>
					</div> --%>
				</div>
				<div class="col-4">
					<label></label>
					<input type="text" id="idCard" name="idCard" placeholder="请填写身份证号码" value="" style="margin-top: 50px;" maxlength="18">
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-10" style="text-align:center;">
				<button class="Mbtn green_a" onclick="save();">保存</button>
				<button class="Mbtn red" style="margin-left: 20px;" onclick="callBack()">取消</button>
			</div>
		</div>
	</div>
	
	<!-- 百度地图弹窗 start-->
	<div class="pop_box" id="map">
	  <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>&s=1"></script>
	  <div class="tip_box_c">
	        <div class="row form">
	            <div class="col-12"><label style="min-width:6%;width:auto;">搜索地址</label><input type="text" style="max-width:94%;width:89%;" placeholder="详细地址" name="keyword" id="suggest"/></div>
	        </div>
	        <div class="row">
	        	<div  class="col-12" style="text-align: right;">
			        <!--百度地图容器-->
			  		<div style="width:100%;height:400px;border:#ccc solid 1px;" id="dituContent"></div>
			  		<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
		  		</div>
	  		</div>
	  		<div class="row">
	            <div  class="col-12" style="text-align: right;">
		            <button class="Mbtn green_a" type="button" id="map_confirm">确定</button>
		            <button class="Mbtn green_a" type="button" id="map_cancel">取消</button>
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
	
	<!-- 百度地图弹窗 end-->
	<script type="text/javascript" src="js/leLeasescompany/baidu.js"></script>
	<script type="text/javascript" src="js/leLeasescompany/reg.js"></script>
	<script type="text/javascript" src="js/leLeasescompany/add.js"></script>
	<script type="text/javascript">
		var serviceAddress = "<%=SystemConfig.getSystemProperty("fileserver")%>";
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
</body>
</html>
