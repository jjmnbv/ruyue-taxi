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
<title>信息设置</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="content/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="content/plugins/toastr/toastr.css" />
<link rel="stylesheet" type="text/css"
	href="content/plugins/select2/select2.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="content/plugins/select2/select2_metro.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>

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
		.tip_box_b label.error {margin-left: 0!important;}
		
		.leleases_css_row_1{
			width:770px;
		}
		.leleases_css_row_1 .col-4{
			width:50%;
		}
		.leleases_css_row_1 .col-4 label:first-child{
			width:40%;
		}
		.leleases_css_row_1 label.error{
			padding-left:40%;
		}
		.leleases_css_row_1 .col-4 input{
			width:60%;
		}
		.leleases_css_row_1 .col-4 textarea{
			width:60% !important;
		}
		.leleases_css_row_2 .col-4 label:first-child{
			/* width:34%; */
		}
		.leleases_css_row_2 label.error{
			padding-left:35%;
		}
		.leleases_css_row_3{
			width:770px;
		}
		.leleases_css_row_3 .col-4{
			width:50%;
		}
	</style>
</head>
<body>
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 信息设置
	</div>
	<div class="content">
		<div class="row form" style="padding-top: 30px;">
		</div> 
		<div>
			<font size="4px" style="margin-left: 10px;">公司信息</font>
			<div style="margin-top: -29px; width: 100%; text-align: right;">
				<input type="hidden" class="SSbtn red_q" onclick="save();" id="save" value="保存" style="margin-right: 10px;"/>
				<input type="button" class="SSbtn green_q" onclick="update();" id="update" value="修改" style="margin-right: 10px;"/>
			</div>
			<hr style="width: 100%;margin-top:3px;">
		</div>
		<form id="editForm" class="form" method="post">
			<div class="row leleases_css_row_2">
				<div class="col-4">
					<label>超管账号<em class="asterisk"></em></label>
					<input id="id" name="id" value="${leLeasescompany.id}" type="hidden"/>
					<label style="text-align: left;">${leLeasescompany.account}</label>
					<!-- <input type="button" class="SSbtn red" onclick="updatePassword();" value="密码" style="margin-left:17%;"/> -->
					<img src="img/xiugaimima.jpg" style="margin-left:17%;" onclick="updatePassword();">
				</div>
				<div class="col-4">
					<label>联系人<em class="asterisk"></em></label>
					<input type="text" id="contacts" name="contacts" value="${leLeasescompany.contacts}" disabled="disabled" maxlength="6" style="width:60%;">
				</div>
				<div class="col-4">
					<label>联系方式<em class="asterisk"></em></label>
					<input type="text" id="phone" name="phone" value="${leLeasescompany.phone}" disabled="disabled" maxlength="11" style="width:60%;">
				</div>
			</div>
			<div class="row leleases_css_row_2">
				<div class="col-4">
					<label>邮箱<em class="asterisk"></em></label>
					<input type="text" id="mail" name="mail" value="${leLeasescompany.mail}" disabled="disabled" style="width:60%;">
				</div>
			</div>
			<div>
				<font size="4px" style="margin-left: 10px;">客服信息</font>
				<hr style="width: 100%;margin-top:3px;">
			</div>
			<div class="row leleases_css_row_2">
				<div class="col-4">
					<label>客服电话<em class="asterisk"></em></label> 
					<input type="text" id="servicesPhone" name="servicesPhone" value="${leLeasescompany.servicesPhone}" disabled="disabled" maxlength="15" style="width:60%;">
				</div>
			</div>
			<div>
				<font size="4px" style="margin-left: 10px;">toC业务</font>
				<hr style="width: 100%;margin-top:3px;">
			</div>
			<div class="row">
				<div class="col-4">
					<label>是否加入toC</label>
					<div style="float: right;">
						<c:if test="${leLeasescompany.tocState == '0'}">
							<font color="red" id="">未加入</font>
							<input type="button" class="SSbtn red_q" value="申请加入" onclick="updateToC();"/>
						</c:if>
						<c:if test="${leLeasescompany.tocState == '1'}">
							<font  id="">申请中</font>
							<!-- <input type="button" class="SSbtn blue" value="申请加入" onclick="updateToC();"/> -->
						</c:if>
						<c:if test="${leLeasescompany.tocState == '2'}">
							<font  id="">已加入</font>
							<input type="button" class="SSbtn red_q" value="退出" onclick="outToC();"/>
						</c:if>
						<c:if test="${leLeasescompany.tocState == '3'}">
							<font  id="">已退出</font>
							<input type="button" class="SSbtn red_q" value="申请加入" onclick="updateToC();"/>
						</c:if>
					</div>
				</div>
			</div>
		</form>
	</div>
	
	<div class="pop_box" id="updatePassword">
	    <div class="tip_box_b form">
	        <h3>修改密码</h3>
	        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	        <div class="w400">
	        	<form id="editPasswordForm" method="post" class="form-horizontal  m-t">
           			<%-- <input type="hidden" id="userId" name="leasescompanyid" value="${leLeasescompany.userId}"/> --%>
            		<div class="row">
            			<div class="col-12">
		            		<label>旧密码<em class="asterisk"></em></label>
			                <input id="oldPassword" name="oldPassword" type="password" placeholder="6~16位数字、字母、符号" style="margin-top: 1px;width: 65%;" maxlength="16">
	                	</div>
            		</div>
            		<div class="row">
            			<div class="col-12">
			                <label>新密码<em class="asterisk"></em></label>
			                <input id="userpassword" name="userpassword" type="password" placeholder="6~16位数字、字母、符号" style="margin-top: 1px;width: 65%;" maxlength="16">
	                	</div>
            		</div>
            		<div class="row">
            			<div class="col-12">
		                	<label>重复密码<em class="asterisk"></em></label>
			                <input id="userpasswordTo" name="userpasswordTo" type="password" placeholder="6~16位数字、字母、符号" style="margin-top: 1px;width: 65%;" maxlength="16">
	               		</div>
            		</div>
            		<button type="button" class="Lbtn red_q" onclick="savePassword();">确定</button>
            		<button type="button" class="Lbtn grey_w" onclick="cancel();">取消</button>
            	</form>
	        </div>
	    </div>
	</div>
	
	<div class="pop_box" id="updateToC">
	    <div class="tip_box_b form">
	        <h3>申请加入toC业务</h3>
	        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	        <div class="w400">
	        	<form id="updateToCForm" method="post" class="form-horizontal  m-t">
           			<input type="hidden" id="leLeasesCompanyId" name="id" value="${leLeasescompany.id}"/>
            		<div class="row">
            			<div class="col-12">
		            		<label>备注<em class="asterisk"></em></label>
			                <textarea rows="4" class="col-10" id="remarks" name="remarks" style="width: 244px;"></textarea>
	                	</div>
            		</div>
            		<div class="row">
            			<div class="col-12" style="margin-left: -7px;">
			                <input type="checkbox" id="checkboxToC" name="checkboxToC"/>同意
	                		<a href="javascript:void(0);" onclick="toCAgreement();" class="breadcrumb"><font color="red">《加入toC协议》</font></a>
	                	</div>
            		</div>
            		<button type="button" class="Lbtn red_q" onclick="saveToC();">确定</button>
            		<button type="button" class="Lbtn grey_W" onclick="cancel();">取消</button>
            	</form>
	        </div>
	    </div>
	</div>
	
	<div class="pop_box" id="toCAgreement">
	    <div class="tip_box_b form" style="height: 400px;">
	        <h3>协议内容</h3>
	        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	        <div class="w400">
           		<div class="row">
           			<div class="col-12" id="tocState" name="tocState">
		                <!-- <textarea rows="6" class="col-10" id="tocState1" name="tocState1">XXXXXXXXXXXXXX</textarea> -->
                	</div>
           		</div>
	       </div>
	    </div>
	</div>
	
	<script type="text/javascript" src="js/leLeasescompany/index.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
</body>
</html>
