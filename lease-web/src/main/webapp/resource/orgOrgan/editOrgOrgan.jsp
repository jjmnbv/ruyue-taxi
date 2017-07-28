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
<title>维护客户信息</title>
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
<link rel="stylesheet" type="text/css" href="content/css/orgorgan_media.css" />
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
		.content{padding-top:20px;}
		.tangram-suggestion-main{z-index:10000;}
		
		
		select option{
			padding:1px 10px;
		}
	</style>
</head>
<body class="orgorgan_css_body">
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a href="OrgOrgan/Index" class="breadcrumb">机构客户</a> > <c:if test="${orgOrgan.id == null or orgOrgan.id == ''}">新增客户信息</c:if><c:if test="${orgOrgan.id != null and orgOrgan.id != ''}">修改客户信息</c:if>
		<button class="SSbtn red_q back" onclick="callBack()">返回</button>
	</div>
	<div class="content">
		<form id="editOrgOrganForm" class="form" method="post">
			<div class="row">
				<div class="col-4">
					<label>机构全称<em class="asterisk"></em></label>
					<input id="id" name="id" value="${orgOrgan.id}" type="hidden"/>
					<input type="text" placeholder="机构全称" name="fullName" id="fullName" value="${orgOrgan.fullName}" maxlength="20" onkeyup="value=value.replace(/[ -~]/g,'')" onkeydown="if(event.keyCode==13)event.keyCode=9" >
				</div>
				<div class="col-4">
					<label>机构简称<em class="asterisk"></em></label>
					<input type="text" placeholder="机构简称" id="shortName" name="shortName" value="${orgOrgan.shortName}" maxlength="6">
				</div>
				<div class="col-4">
					<label>客户类型<em class="asterisk"></em></label>
					<!-- 客户类型(0-非渠道客户，1-渠道客户) -->
					<select id="customertype" name="customertype">
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
			</div>	
			<div class="row">
				<div class="col-4">
					<label>合作状态<em class="asterisk"></em></label>
					<select id="cooperationStatus" name="cooperationStatus">
						<c:choose>
							<c:when test="${orgOrgan.cooperationStatus == 0}"> 
								<option value="1">正常</option>
								<option value="0" selected="selected">停止</option>
							</c:when>
							<c:otherwise> 
								<option value="1" selected="selected">正常</option>
								<option value="0">停止</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
				<div class="col-4">
					<label>联系人<em class="asterisk"></em></label>
					<input type="text" placeholder="联系人" id="contacts" name="contacts" value="${orgOrgan.contacts}" maxlength="20">
				</div>
				<div class="col-4">
					<label>联系方式<em class="asterisk"></em></label>
					<input type="text" placeholder="手机号码" id="phone" name="phone" value="${orgOrgan.phone}" maxlength="11">
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>邮箱<em class="asterisk"></em></label>
					<input id="email" name="email" type="text" placeholder="邮箱" value="${orgOrgan.email}" maxlength="30">
				</div>
				<div class="col-4">
					<label>机构地址<em class="asterisk"></em></label>
						<%-- <input type="hidden" id="city" name="city" value="${orgOrgan.city}">
						<div id="inp_box1" style="width: 20%">
							<input type="text" id="cityName" style="width: 100%;float:none;" value="${orgOrgan.citycaption}" onfocus="getSelectCity();" readonly="readonly">
						</div> --%>
						<input type="hidden" id="city" name="city" value="${orgOrgan.city}">
          				<input type="hidden" name="citymarkid" id="citymarkid" value="">
						<div id="inp_box1" style="width: 20%">
							<input type="text" id="cityName" style="width: 104%" value="${orgOrgan.citycaption}" readonly="readonly">
						</div>  
					<%-- <select id="city" name="city">
						<option value="${orgOrgan.city}" selected="selected">${orgOrgan.citycaption}</option>
						<c:forEach var="pubCityaddr" items="${pubCityaddr}">
							<option value="${pubCityaddr.id}" data-owner="city">${pubCityaddr.city}</option>
						</c:forEach>
					</select> --%>
					<input id="addressHidden" type="hidden" name="address" value="${orgOrgan.address}"/>
					<input class="orgorgan_css_input_2" id="address" type="text" placeholder="可输入搜索" value="${orgOrgan.address}" data-owner="addressHidden" style="width:35.5%;" maxlength="36"/>
					<input class="orgorgan_css_input_3" id="addressButton" name="" type="button" value="地图" style="width: 50px;height: 30px;"/>
				</div>
				<div class="col-4">
					
				</div>
			</div>
			<div class="row">
				<div class="col-4" style="display: none;" id="lineOfCreditDiv">
					<label>信用额度<em class="asterisk"></em></label>
					<input class="orgorgan_css_input_1" id="lineOfCredit" name="lineOfCredit" type="text" placeholder="下单限额" value="${orgOrgan.lineOfCredit}" style="width: 290px;" maxlength="7">&nbsp;&nbsp;<span class="orgorgan_css_span_1">元</span>
				</div>
				<div class="col-4">
					<label>结算方式<em class="asterisk"></em></label>
					<select id="billType" name="billType">
						<c:choose>
							<c:when test="${orgOrgan.billType == '0'}"> 
								<option value="1">季结</option>
								<option value="0" selected="selected">月结</option>
							</c:when>
							<c:otherwise>
								<option value="1" selected="selected">季结</option> 
								<option value="0">月结</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
				<div class="col-4" style="position: relative;">
					<label class="orgorgan_css_label_1">账单生成日<em class="asterisk"></em></label>
					<select class="orgorgan_css_select_1" id="billDate" name="billDate" style="position: absolute;background-color:#fff;">
						<c:choose>
                           	<c:when test="${orgOrgan.billDate != null && orgOrgan.billDate != ''}">
                           		<option value="${orgOrgan.billDate}" selected="selected">${orgOrgan.billDate}</option>
                           		<c:forEach  begin="1" end="20" varStatus="i">
                            		<option value="${i.count}">${i.count}</option>
		                  		</c:forEach>
                           	</c:when>
                           	<c:otherwise>
                           		<c:forEach  begin="1" end="20" varStatus="i">
									<c:choose>
		                            	<c:when test="${10==i.count}">
		                            		<option value="${i.count}" selected="selected">${i.count}</option>
		                            	</c:when>
		                            	<c:otherwise>
		                            		<option value="${i.count}">${i.count}</option>
		                            	</c:otherwise>
		                            </c:choose>
		                  		</c:forEach>
                           	</c:otherwise>
                        </c:choose>	
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>机构账号<em class="asterisk"></em></label>
					<input id="account" name="account" type="text" placeholder="机构账号" value="${orgOrgan.account}" maxlength="15">
				</div>
			</div>
			<div class="row">
				<div class="col-12" style="padding:8px 0px;font-size:24px;border-bottom:1px solid #ccc;margin-bottom:20px;margin-top:20px;">
					机构信用代码证
				</div>
			</div>
			<div class="row">
				<div class="col-4">	
					<input id="creditCodePic" name="creditCodePic" type="hidden" value="${orgOrgan.creditCodePic}"> 
					<!-- <input id="fileupload" type="file" name="file" multiple style="display:none;"/> -->
					<div style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${orgOrgan.creditCodePic != null && orgOrgan.creditCodePic != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.creditCodePic}" style="width: 197px; height: 130px; background-color: gray;" id="imgback"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img src="img/pubDriver/zhengmian.jpg" style="width: 197px; height: 130px;" id="imgback">
						   	</c:otherwise>
						</c:choose> 
                		<input id="fileupload" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;font-size: 66px;" name="file" multiple/>
                	</div>
                	<a id="clear" href="javascript:void(0)" style="margin-left: 36%;"><font color="red">删除</font></a>
					<%-- <div id="imgShow">
						<c:choose>
						   	<c:when test="${orgOrgan.creditCodePic != null && orgOrgan.creditCodePic != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.creditCodePic}" style="width: 197px; height: 130px; background-color: gray;" id="imgback"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/zhengmian.jpg" id="imgback"></img>
						   	</c:otherwise>
						</c:choose> 
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<a id="clear" href="javascript:void(0)">删除</a>
					</div> --%>
				</div>
				<div class="col-4">
					<label></label>
					<input id="creditCode" name="creditCode" type="text" placeholder="机构信用代码编码" value="${orgOrgan.creditCode}" style="margin-top: 50px;" maxlength="18">
				</div>
			</div>
			<div class="row">
				<div class="col-12" style="padding:8px 0px;font-size:24px;border-bottom:1px solid #ccc;margin-bottom:20px;">工商执照
			</div>
			</div>
			<div class="row">
				<div class="col-4"> 
					<input type="hidden" id="businessLicensePic" name="businessLicensePic" value="${orgOrgan.businessLicensePic}"> 
					<!-- <input id="fileupload2" type="file" name="file" multiple  style="display: none;"/> -->
					<div style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${orgOrgan.businessLicensePic != null && orgOrgan.businessLicensePic != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.businessLicensePic}" style="width: 197px; height: 130px; background-color: gray;" id="imgback2"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img src="img/pubDriver/zhengmian.jpg" style="width: 197px; height: 130px;" id="imgback2">
						   	</c:otherwise>
						</c:choose> 
                		<input id="fileupload2" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;font-size: 66px;" name="file" multiple/>
                	</div>
                	<a id="clear2" href="javascript:void(0)" style="margin-left: 36%;"><font color="red">删除</font></a>
					<%-- <div id="imgShow2">
						<c:choose>
						   	<c:when test="${orgOrgan.businessLicensePic != null && orgOrgan.businessLicensePic != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.businessLicensePic}" style="width: 197px; height: 130px; background-color: gray;" id="imgback2"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/zhengmian.jpg" id="imgback2"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<a id="clear2" href="javascript:void(0)">删除</a>
					</div> --%>
				</div>
				<div class="col-4">
					<label></label>
					<input id="businessLicense" name="businessLicense" type="text" placeholder="请填写执照编码" value="${orgOrgan.businessLicense}" style="margin-top: 50px;" maxlength="15">
				</div>
			</div>
			<div class="row">
				<div class="col-12" style="padding:8px 0px;font-size:24px;border-bottom:1px solid #ccc;margin-bottom:20px;">法人身份证
			</div>
			</div>
			<div class="row">
				<div class="col-4"> 
					<input type="hidden" id="idCardFront" name="idCardFront" value="${orgOrgan.idCardFront}"> 
					<!-- <input id="fileupload3" type="file" name="file" multiple  style="display: none;"/> -->
					<div style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${orgOrgan.idCardFront != null && orgOrgan.idCardFront != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.idCardFront}" style="width: 197px; height: 130px; background-color: gray;" id="imgback3"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img src="img/pubDriver/zhengmian.jpg" style="width: 197px; height: 130px;" id="imgback3">
						   	</c:otherwise>
						</c:choose> 
                		<input id="fileupload3" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;font-size: 66px;" name="file" multiple/>
                	</div>
                	<a id="clear3" href="javascript:void(0)" style="margin-left: 36%;"><font color="red">删除</font></a>
					<%-- <div id="imgShow3">
						<c:choose>
						   	<c:when test="${orgOrgan.idCardFront != null && orgOrgan.idCardFront != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.idCardFront}" style="width: 197px; height:130px; background-color: gray;" id="imgback3"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width: 197px; height: 130px; background-color: gray;" src="img/pubDriver/zhengmian.jpg" id="imgback3"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<a id="clear3" href="javascript:void(0)">删除</a>
					</div> --%>
				</div>
				<div class="col-4">
					<input type="hidden" id="idCardBack" name="idCardBack" value="${orgOrgan.idCardBack}"> 
					<!-- <input id="fileupload4" type="file" name="file" multiple  style="display: none;"/> -->
					<div style="width: 197px; height: 130px;margin-left: 19%;">
						<c:choose>
						   	<c:when test="${orgOrgan.idCardBack != null && orgOrgan.idCardBack != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.idCardBack}" style="width: 197px; height: 130px; background-color: gray;" id="imgback4"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img src="img/pubDriver/beimian.jpg" style="width: 197px; height: 130px;" id="imgback4">
						   	</c:otherwise>
						</c:choose> 
                		<input id="fileupload4" type="file" style="position: relative;width: 197px; height: 130px;opacity:0;top: -99%;left: 0px;font-size: 66px;" name="file" multiple/>
                	</div>
                	<a id="clear4" href="javascript:void(0)" style="margin-left: 36%;"><font color="red">删除</font></a>
					<%-- <div id="imgShow4">
						<c:choose>
						   	<c:when test="${orgOrgan.idCardBack != null && orgOrgan.idCardBack != ''}">  
			                	<img src="<%=SystemConfig.getSystemProperty("fileserver")%>/${orgOrgan.idCardBack}" style="width: 197px; height: 130px; background-color: gray;" id="imgback4"></img>  
						   	</c:when>
						   	<c:otherwise> 
						   		<img style="width:197px; height: 130px; background-color: gray;" src="img/pubDriver/beimian.jpg" id="imgback4"></img>
						   	</c:otherwise>
						</c:choose>
						<!-- <input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/> -->
						<a id="clear4" href="javascript:void(0)">删除</a>
					</div> --%>
				</div>
				<div class="col-4">
					<label></label>
					<input type="text" id="idCardNo" name="idCardNo" placeholder="请填写身份证号码" value="${orgOrgan.idCardNo}" style="margin-top: 50px;" maxlength="18">
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-10" style="text-align: center;">
				<button class="Mbtn red_q" onclick="save();">保存</button>
				<button class="Mbtn grey_w" style="margin-left: 20px;" onclick="callBack()">取消</button>
			</div>
		</div>
	</div>
	
	<!-- 百度地图弹窗 start-->
	<div class="pop_box" id="map">
	  <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>&s=1"></script>
	  <div class="tip_box_c">
	        <div class="row form">
	            <div class="col-12"><label style="min-width:6%; width:auto;padding-right:10px;">搜索地址</label><input type="text" style="max-width:94%;width:89%;" placeholder="详细地址" name="keyword" id="suggest"/></div>
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
	<!-- 百度地图弹窗 end-->
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
	<script type="text/javascript" src="js/orgOrgan/editOrgOrgan.js"></script>
	<script type="text/javascript" src="js/orgOrgan/reg.js"></script>
	<script type="text/javascript" src="js/orgOrgan/baidu.js"></script>
	<script type="text/javascript">
		var serviceAddress = "<%=SystemConfig.getSystemProperty("fileserver")%>";
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
	<script type="text/javascript">
		$(function(){
			/* 火狐浏览器兼容问题 */
			 if (navigator.userAgent.indexOf('Firefox') >= 0){
				 $('select').css('padding-top','3px');
			 }
			 /* 处理账单生成日下拉选择框太长 */
			  window.onload = function(){  
			        $("#billDate option").click(function(){  
			            $("#billDate").removeAttr("size").blur();;  
			        });  
			        $("#billDate").focus(function(){  
			            $(this).attr("size","10");  
			        })  
			    }   
		})
			
	</script>
</body>
</html>
