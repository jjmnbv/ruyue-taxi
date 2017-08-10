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
		<title>新增协议</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/ueditor/themes/default/css/ueditor.css" />
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="content/plugins/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="content/plugins/ueditor/ueditor.all.js"></script>
		<script type="text/javascript" charset="utf-8" src="content/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
	</head>
	<style type="text/css">
		.form select{width:68%;}
		.breadcrumb{text-decoration:underline;}
		body{background: #DADADA!important;}
		.crumbs{font-size: 14px;}
		
		/*样式修改*/
		.companyagreement_css_col_1{
			width:48% !important;
		}
		.companyagreement_css_col_1 input, select{
			width:57% !important;
		}
		.crumbs .back{
	    	position: relative;
	    	top:2px;
	    }
	    .companyagreement_css_col_1 .asterisk{
	    	position: relative;
	    	bottom:1px;
	    }
	</style>

	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="PubCooagreement/Index" class="breadcrumb">合作协议管理</a> > <span id="pubCooagreement">新增协议</span>
			<button class="SSbtn blue back" onclick="window.history.go(-1);">返回</button>
		</div>
		
		<div class="content">
		  <form id="formss" class="form" style="padding-top: 30px;">
		    <input id="id" name="id" value="${id}" type="hidden">
		    <input id="" type="hidden" value="">
		    <span id="dataHtml" style="display: none;"></span>
		    <input id="" type="hidden" value="">
			<div class="row form">
				<div class="col-4">
					<label>车企名称<em class="asterisk"></em></label>
					<%-- <select id="leasecompanyid" name="leasecompanyid">
						<option value="">请选择车企</option>
						<c:forEach items="${list}" var="leasesCompany">
							<option value="${leasesCompany.id}">${leasesCompany.name}</option>
						</c:forEach>
					</select> --%>
					<input id="companyName" name="companyName" type="text" placeholder="请输入车企名称" value="">
				</div>
				<div class="col-4">
					<label>服务类型<em class="asterisk"></em></label>
					<select id="servicetype" name="servicetype">
						<option value="">请选择服务类型</option>
						<option value="0">网约车</option>
						<option value="1">出租车</option>
					</select>
				</div>
				<div class="col-4">
					<label>协议名称<em class="asterisk"></em></label>
					<input type="text" placeholder="请输入" id="cooname" maxlength="30" name="cooname">
				</div>
			</div>
			<div class="row form">
				<div class="row">
					<script id="content" name="content" type="text/javascript"></script>
				</div>
			</div>
		  </form>
		  <div class="row form">	
				<div class="col-12" style="text-align: right;">
                     <button class="Mbtn orange" style="margin-right: 20px;" onclick="save()">确定</button>
                     <button class="Mbtn grey" onclick="cancel()">取消</button>
                </div>
		  </div>
		</div>
		
		<script type="text/javascript" src="js/pubCooagreement/editPubCooagreement.js"></script>
	</body>
</html>
