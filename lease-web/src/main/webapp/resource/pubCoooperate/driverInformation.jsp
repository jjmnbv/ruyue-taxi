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
		<title>加盟资源</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/leLeasescompany_css_media.css" />
		
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
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
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
		</style>
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
	</head>
	<body class="leLeasescompany_css_body">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="PubCoooperate/Index">联盟资源</a> > 资源管理</div>
			<button class="SSbtn red_q back leLeasescompany_css_back" onclick="callBack();" style="margin-top: -29px;">返回</button>
		<div class="content">
			<div class="form" style="padding-top: 30px;margin-bottom:20px;">
				<input type="hidden" id="leasescompanyid" value="${leasescompanyid}"/>
				<input type="hidden" id="servicetype" value="${servicetype}"/> 
				<div class="row">
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
					<div class="col-3"><label>服务车型</label>
						<select id="queryModels" name="queryModels">
							<option value="" selected="selected">未分配</option>
							<c:forEach var="list" items="${list}">
								<option value="${list.name}">${list.name}</option>
							</c:forEach>
							
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-12" style="text-align: right;">
						<button class="Mbtn red_q" onclick="search();">查询</button>
						<button class="Mbtn grey_q" onclick="emptys();">清空</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<h4>加盟投运资源信息</h4>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="titleForm">修改车型</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="vehicleid" name="vehicleid" value=""/>
	            		<input type="hidden" id="leasecompanyid" name="leasecompanyid" value=""/>
	            		<input type="hidden" id="id" name="id" value=""/>
	            		<div class="row">
	            			<div class="col-12">
		            			<label>原服务车型</label>
	            				<input id="oldService" name="" type="text" disabled="disabled" value=""/>
	            			</div>
            			</div>
            			<div class="row">
	            			<div class="col-12">
		            			<label>分配车型</label>
	            				<select style="width: 63%;" id="vehiclemodelsid" name="vehiclemodelsid">
	            					<%-- <c:forEach var="opVehiclemodels" items="${opVehiclemodels}">
	            						<option value="${opVehiclemodels.id}">${opVehiclemodels.name}</option>
	            					</c:forEach> --%>
	            				</select>
	            			</div>
            			</div>
            			<div class="row">
	            			<div class="col-12">
		            			<label style="width: 68%"><font color="red">*</font>车型修改不影响车辆归属方服务车型数据</label>
	            			</div>
            			</div>
	            	</form>
	                <button class="Sbtn red" onclick="save()">完成</button>
	                <button class="Sbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
	<script type="text/javascript" src="js/pubCoooperate/driverInformation.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>	
	</body>
</html>
