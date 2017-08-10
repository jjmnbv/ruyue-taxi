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
		<title>资源信息</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		
		<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		
		.tip_box_b input[type=text]{width: 63%;}
		
		th, td { white-space: nowrap; }
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
		margin-top:-20px;
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
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.fileupload.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	<body>
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a href="PubCoooperate/Index" class="breadcrumb">联盟资源</a> > 资源信息
			<button class="SSbtn blue back" onclick="window.history.go(-1);">返回</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<input id="id" value="${p.id}" type="hidden"/>
					<input id="companyid" value="${p.companyid}" type="hidden"/>
					<input id="leasecompanyid" value="${p.leasecompanyid}" type="hidden"/>
					<input id="servicetype" value="${p.servicetype}" type="hidden"/>
					<div class="col-3">
						<label>车牌号</label>
						<input id="queryPlateno" name="queryPlateno" type="text" value="" placeholder="车牌号"/>
					</div>
					<div class="col-3">
						<label>资格证号</label>
						<input id="queryJobnum" name="queryJobnum" type="text" value="" placeholder="司机资格证号"/>
					</div>
					<div class="col-3">
						<label>司机</label>
						<input id="queryDriverInformation" name="queryDriverInformation" type="text" value="" placeholder="姓名/手机号"/>
					</div>
					<div class="col-3" style="text-align: right;">
						<button class="Mbtn red_q" onclick="search();">查询</button>
						<button class="Mbtn grey_w" onclick="clearParameter();">清空</button>
					</div>
				</div>
			</div>
			<div class="row">
			   <div class="col-10" style="margin-top: 15px;"><h4>加盟投运资源信息</h3></div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<script type="text/javascript" src="js/pubCoooperate/resourceInformation.js"></script>
		<script type="text/javascript">
			var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
	</body>
</html>
