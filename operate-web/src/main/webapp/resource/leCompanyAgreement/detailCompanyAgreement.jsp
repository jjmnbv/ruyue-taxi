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
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
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
	    	top:1px;
	    }
	</style>

	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="CompanyAgreement/Index" class="breadcrumb">加盟服务协议</a> > 协议详情
			<button class="SSbtn blue back" onclick="window.history.go(-1);">返回</button>
		</div>
		<div class="content">
			<form id="formss" class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-4 companyagreement_css_col_1">
						<label>服务车企</label>
						<select id="leasecompanyid" name="leasecompanyid" disabled="disabled">
							<option value="${companyAgreement.leasescompanyid}">${companyAgreement.leasecompanyName}</option>
						</select>
					</div>
					<div class="col-4 companyagreement_css_col_1">
						<label>协议简称</label>
						<input type="text" placeholder="输入简称" id="shortname" style="width: 68%" disabled="disabled" name="shortname" value="${companyAgreement.shortname}">
					</div>
				</div>
				<div class="row form">
					<div class="col-12">${companyAgreement.content}</div>
				</div>
			</form>
		</div>
	</body>
</html>
