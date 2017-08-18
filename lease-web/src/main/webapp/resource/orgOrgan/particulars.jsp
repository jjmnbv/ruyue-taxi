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
<title>机构管理</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="content/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="content/plugins/toastr/toastr.css" />
<link rel="stylesheet" type="text/css"
	href="content/plugins/select2/select2.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="content/plugins/select2/select2_metro.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="content/css/orgorgan_media.css" />

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
		.tip_box_c{
		    max-height: 726px;
		}
		.breadcrumb{text-decoration:underline;}
	</style>
</head>
<body class="orgorgan_css_body">
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a href="OrgOrgan/Index" class="breadcrumb">机构客户</a> > 客户信息详情
		<button class="SSbtn red_q back" onclick="callBack()">返回</button>
	</div>
	<div class="content">
		<form id="editOrgOrganForm" class="form" method="post">
			<div class="row">
				<div class="col-4">
					<label>机构全称<em class="asterisk"></em></label>
					<input id="id" name="id" value="${orgOrgan.id}" type="hidden"/>
					<input type="text" placeholder="机构全称" name="fullName" id="fullName" value="${orgOrgan.fullName}" disabled="disabled">
				</div>
				<div class="col-4">
					<label>机构简称<em class="asterisk"></em></label>
					<input type="text" placeholder="机构简称" id="shortName" name="shortName" value="${orgOrgan.shortName}" disabled="disabled">
				</div>
				<div class="col-4">
					<label>机构账号<em class="asterisk"></em></label>
					<input id="account" name="account" type="text" placeholder="机构账号" value="${orgOrgan.account}" disabled="disabled">
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>合作状态<em class="asterisk"></em></label>
					<select id="cooperationStatus" name="cooperationStatus" disabled="disabled">
						<c:choose>
							<c:when test="${orgOrgan.cooperationStatus == 1}"> 
								<option value="1" selected="selected">正常</option>
							</c:when>
							<c:otherwise> 
								<option value="0" selected="selected">停止</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
				<div class="col-4">
					<label>联系人<em class="asterisk"></em></label>
					<input type="text" placeholder="联系人" id="contacts" name="contacts" value="${orgOrgan.contacts}" disabled="disabled">
				</div>
				<div class="col-4">
					<label>联系方式<em class="asterisk"></em></label>
					<input type="text" placeholder="联系方式" id="phone" name="phone" value="${orgOrgan.phone}" disabled="disabled">
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>邮箱<em class="asterisk"></em></label>
					<input id="email" name="email" type="text" placeholder="邮箱" value="${orgOrgan.email}" disabled="disabled">
				</div>
				<div class="col-4">
					<label>机构地址<em class="asterisk"></em></label> 
					<select id="city" name="city" disabled="disabled">
						<option value="${orgOrgan.city}" selected="selected">${orgOrgan.citycaption}</option>
					</select>
				</div>
				<div class="col-4">
					<input id="address" name="address" type="text" placeholder="" value="${orgOrgan.address}" disabled="disabled">
					<input id="addressButton" name="addressButton" type="button" value="地图" style="width: 50px;height: 30px;">
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>信用额度<em class="asterisk"></em></label>
					<input  class="orgorgan_css_input_1" id="lineOfCredit" name="lineOfCredit" type="text" placeholder="信用额度" value="${orgOrgan.lineOfCredit}" disabled="disabled" style="width: 295px;">&nbsp;&nbsp;<span class="orgorgan_css_span_1">元</span>
				</div>
				<div class="col-4">
					<label>结算方式<em class="asterisk"></em></label>
					<select id="billType" name="billType" disabled="disabled">
						<c:choose>
							<c:when test="${orgOrgan.billType == '1'}"> 
								<option value="1" selected="selected">季结</option>
							</c:when>
							<c:otherwise> 
								<option value="0" selected="selected">月结</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
				<div class="col-4">
					<label class="orgorgan_css_label_1">账单生成日<em class="asterisk"></em></label>
					<select class="orgorgan_css_select_1" id="billDate" name="billDate" disabled="disabled">
                        <option value="${orgOrgan.billDate}" selected="selected">${orgOrgan.billDate}</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>客户类型<em class="asterisk"></em></label>
					<!-- 客户类型(0-非渠道客户，1-渠道客户) -->
					<select id="customertype" name="customertype" disabled="disabled">
						<c:choose>
							<c:when test="${orgOrgan.customertype == 1}"> 
								<option value="0">非渠道客户</option>
								<option value="1" selected="selected">渠道客户</option>
							</c:when>
							<c:otherwise> 
								<option value="0" selected="selected">非渠道客户</option>
								<option value="1">渠道客户</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
				<div class="col-4">
					<label>供车主体<em class="asterisk"></em></label>
					<input type="hidden" id="forTheCarBodyId" name="forTheCarBodyId" value="">
					<div style="border:1px solid #E0E0E0;width: 260px;height: 100px;overflow-y:scroll">
						<ul id="forTheCarBody" name="forTheCarBody" >
							<li data-value="${leLeasescompany.id}">${leLeasescompany.shortName}</li>
						</ul>
					</div>
					<!-- <div style="height: 90px;margin-top: -95px;margin-left: 424px;">
						<button type="button" onclick="addForTheCarBody();" style="width: 35px; height: 35px;">+</button>
						<br><br>
						<button type="button" onclick="updateForTheCarBody();" style="width: 35px; height: 35px;">-</button>
					</div> -->
				</div>
			</div>
			<!-- <div class="row">
				<div class="col-12">
					<label style="text-align: left;"><font size="5">机构信用代码证</font></label>
					<HR style="border:3 solid #ff0033" width="100%" SIZE=3> 
				</div>
			</div> -->
			<div class="row">
				<div class="col-12" style="padding:8px 0px;font-size:24px;border-bottom:1px solid #ccc;margin-bottom:20px;margin-top:20px;">
					机构信用代码证
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<input id="creditCodePic" name="creditCodePic" type="hidden" value="${orgOrgan.creditCodePic}"> 
					<input id="fileupload" type="file" name="file" multiple style="display:none;"/>
					<div id="imgShow" style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${orgOrgan.creditCodePic != null && orgOrgan.creditCodePic != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.creditCodePic}" style="width: 197px; height: 130px; background-color: gray;" id="imgback"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/shangchuan.png" id="imgback"></img>
						   	</c:otherwise>
						</c:choose> 
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<!-- <a id="clear" href="javascript:void(0)">删除</a> -->
					</div>
				</div>
				<div class="col-4">
					<label></label>
					<input id="creditCode" name="creditCode" type="text" placeholder="机构信用代码编码" value="${orgOrgan.creditCode}" disabled="disabled" style="margin-top: 50px;">
				</div>
			</div>
			<!-- <div class="row">
				<div class="col-12">
					<label style="text-align: left;"><font size="5">工商执照</font></label>
					<HR style="border:3 solid #ff0033" width="100%" SIZE=3>
				</div>
			</div> -->
			<div class="row">
				<div class="col-12" style="padding:8px 0px;font-size:24px;border-bottom:1px solid #ccc;margin-bottom:20px;">工商营业执照/事业单位法人证书
			</div>
			<div class="row">
				<div class="col-4"> 
					<input type="hidden" id="businessLicensePic" name="businessLicensePic" value="${orgOrgan.businessLicensePic}"> 
					<input id="fileupload2" type="file" name="file" multiple  style="display: none;"/>
					<div id="imgShow2" style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${orgOrgan.businessLicensePic != null && orgOrgan.businessLicensePic != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.businessLicensePic}" style="width: 197px; height: 130px; background-color: gray;" id="imgback2"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/shangchuan.png" id="imgback2"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<!-- <a id="clear2" href="javascript:void(0)">删除</a> -->
					</div>
				</div>
				<div class="col-4">
					<label></label>
					<input id="businessLicense" name="businessLicense" type="text" placeholder="请填写执照编码" value="${orgOrgan.businessLicense}" disabled="disabled" style="margin-top: 50px;">
				</div>
			</div>
			<!-- <div class="row">
				<div class="col-12">
					<label style="text-align: left;"><font size="5">法人身份证</font></label>
					<HR style="border:3 solid #ff0033" width="100%" SIZE=3>
				</div>
			</div> -->
			<div class="row">
				<div class="col-12" style="padding:8px 0px;font-size:24px;border-bottom:1px solid #ccc;margin-bottom:20px;">法人身份证
			</div>
			<div class="row">
				<div class="col-4"> 
					<input type="hidden" id="idCardFront" name="idCardFront" value="${orgOrgan.idCardFront}"> 
					<input id="fileupload3" type="file" name="file" multiple  style="display: none;"/>
					<div id="imgShow3" style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${orgOrgan.idCardFront != null && orgOrgan.idCardFront != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.idCardFront}" style="width: 197px; height: 130px; background-color: gray;" id="imgback3"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/shangchuan.png" id="imgback3"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<!-- <a id="clear3" href="javascript:void(0)">删除</a> -->
					</div>
				</div>
				<div class="col-4">
					<input type="hidden" id="idCardBack" name="idCardBack" value="${orgOrgan.idCardBack}"> 
					<input id="fileupload4" type="file" name="file" multiple  style="display: none;"/>
					<div id="imgShow4" style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${orgOrgan.idCardBack != null && orgOrgan.idCardBack != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.idCardBack}" style="width: 197px; height: 130px; background-color: gray;" id="imgback4"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/shangchuan.png" id="imgback4"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<!-- <a id="clear4" href="javascript:void(0)">删除</a> -->
					</div>
				</div>
				<div class="col-4">
					<input type="text" id="idCardNo" name="idCardNo" placeholder="请填写身份证号码" value="${orgOrgan.idCardNo}" disabled="disabled" style="margin-top: 50px;">
				</div>
			</div>
		</form>
		<!-- <div class="row">
			<div class="col-10" style="text-align: right;">
				<button class="Mbtn red" style="margin-left: 20px;" onclick="callBack()">关闭</button>
			</div>
		</div> -->
	</div>	
	<!-- 百度地图弹窗 start-->
	<div class="pop_box" id="map">
	  <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>&s=1"></script>
	  <div class="tip_box_c">
	        <div class="row form" style="display: none;">
	            <div class="col-12"><label style="width:6%;">搜索地址</label><input type="text" style="width:94%;" placeholder="详细地址" name="keyword" id="suggest"/></div>
	        </div>
	        <div class="row">
	        	<div  class="col-12" style="text-align: right;">
			        <!--百度地图容器-->
			  		<div style="width:100%;height:550px;border:#ccc solid 1px;" id="dituContent"></div>
			  		<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
		  		</div>
	  		</div>
	  		<div class="row">
	            <div  class="col-12" style="text-align: center;">
		            <button class="Mbtn red_q" type="button" id="map_confirm" data-owner="" style="display: none;">保存</button>
		            <button class="Mbtn grey_w" type="button" id="map_cancel">取消</button>
	        	</div>
	        </div>
	  </div>
	</div>
	<!-- 百度地图弹窗 end-->
	<!-- 放大图片 -->
	<div class="pop_box" id="openImg" style="display: none;">
		<div class="tip_box_b">
		<img src="content/img/btn_guanbi.png" class="close" alt="关闭" style="margin-top: 14px;">
		<h3 id="openTitle">选择机构</h3>
		<div id="openImg1">
		
		</div>
        </div>
	</div>
	<script type="text/javascript" src="js/orgOrgan/baidu.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
		$(function(){
			$("#addressButton").click(function(){
			   var selectIndex = document.getElementById("city").selectedIndex;//获得是第几个被选中了
			   var selectText = document.getElementById("city").options[selectIndex].text //获得被选中的项目的文本
			   /* moveMap(selectText,$("#address").attr("placeholder"));
			   $("#map_confirm").attr("data-owner","address");
		   	   $("#map").show(); */
				moveMap(selectText, $("#address").val());
				$("#map_confirm").attr("data-owner", "address");
				$("#map").show();
			});
			$("#map_cancel").click(function(){
			   /* $("#suggest").val(""); */
			   $("#map").hide();
			});
			
			$("#imgback").click(function(){
				var img = $("#imgback").attr("src");
				$("#openTitle").html("机构信用代码证");
				var html = "<img src="+img+"></img>";
				$("#openImg1").html(html);
				$("#openImg").show();
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
			if($("#id").val() != null && $("#id").val() != ''){
				$.ajax({
					type : "GET",
					url : "OrgOrgan/GetPubLeaseOrganRelationById",
					cache : false,
					data : {
						id : $("#id").val()
					},
					success : function(json) {
						var html = "";
						for(var i=0;i<json.length;i++){
							html+='<li data-value="'+json[i].leasecompanyid+'">'+json[i].shortName+'</li>';
						}
						$("#forTheCarBody").html(html);
					}
				});
			}
		})
		function callBack(){
			window.location.href=base+"OrgOrgan/Index";
		}
	</script>
</body>
</html>
