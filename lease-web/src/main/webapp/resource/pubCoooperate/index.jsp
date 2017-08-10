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
		<title>联盟资源</title>
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
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 联盟资源
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-3">
						<label>合作编号</label>
						<input id="queryCoono" name="queryCoono" type="text" value="" placeholder="请输入合作编号"/>
					</div>
					<div class="col-3">
						<label>战略伙伴</label>
						<select id="queryCompanyname" name="queryCompanyname">
							<option value="">全部</option>
							<c:forEach items="${list}" var="list">
								<option value="${list.leasecompanyid}">${list.companyName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-3">
						<label>加盟业务</label>
						<select id="queryServicetype" name="queryServicetype">
							<option value="">全部</option>
							<option value="0">网约车</option>
							<option value="1">出租车</option>
						</select>
					</div>
					<div class="col-3" style="text-align: right;">
						<button class="Mbtn red_q" onclick="search();">查询</button>
						<button class="Mbtn grey_w" onclick="clearParameter();">清空</button>
					</div>
				</div>
			</div>
			<div class="row">
			   <div class="col-10" style="margin-top: 15px;"><h4>联盟资源信息</h3></div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<script type="text/javascript" src="js/pubCoooperate/index.js"></script>
		<script type="text/javascript">
			var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
	</body>
</html>
