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
<title>车辆保险信息</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="content/css/style.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">

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
<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="content/plugins/select2/app.js"></script>
<script src="content/plugins/fileupload/jquery.ui.widget.js"></script>
<script src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
<style type="text/css">
	.select2-container .select2-choice{height:30px;}
	.select2-container{width:68%;padding:0px;}
	.dataTables_wrapper{margin: 0px 0px 10px 0px;}
	.breadcrumb{text-decoration:underline;}
	th, td { white-space: nowrap; }
	div.dataTables_wrapper {
	  width: $(window).width();
	  margin: 0 auto;
	}
	.form select,.form input[type=text]{
		width:68%;
		
	}
	.form label{
		float:left;
		line-height: 30px;
		height:30px;
	}
</style>
</head>
<body style="overflow-x:hidden;overflow-y:hidden;">
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a href="PubVehInsur/Index" class="breadcrumb">车辆保险信息 </a> > 编辑保险
		<button class="SSbtn red_q back" onclick="callBack()">返回</button>
	</div>
	<div class="content">
		<form id="editDriverForm" class="form" method="post">
		    <input id="insurId" name="insurId" type="hidden" value="${pubInsur.id}">
			<div class="row">
				<div class="col-4">				
					<label>车牌号码<em class="asterisk"></em></label>
					<label  style="text-align:left;">${pubInsur.fullplateno}</label>
				</div>
				<div class="col-4">
					<label>发动机号<em class="asterisk"></em></label>
					<label  style="text-align:left;">${pubInsur.engineid}</label>
					<!--  网约车 0 、 出租车 1  -->
					<input id="vehicleType1" name="vehicleType1" type="hidden" value="${pubDriver.vehicleType}"/>
				</div>
				<div class="col-4">
					<label>车辆识别码<em class="asterisk"></em></label>
					<label  style="text-align:left;">${pubInsur.vin}</label>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>保险公司名称<em class="asterisk"></em></label>
					<input id="insurcom" name="insurcom"
						type="text" placeholder="保险公司名称" value="${pubInsur.insurcom}"/>
				</div>
				<div class="col-4">
					<label>保险类型<em class="asterisk"></em></label>
					<select id="insurtype" name="insurtype">
					    
						<c:forEach var="insurType" items="${insurType}">
							<c:choose>
								<c:when test="${pubInsur.insurtype == insurType.value}">
									<option value="${insurType.value}" selected="selected">${insurType.text}</option>
								</c:when>
								<c:otherwise>
									<option value="${insurType.value}" >${insurType.text}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
				<div class="col-4">
					<label>保险号<em class="asterisk"></em></label>
					<input id="insurnum" name="insurnum"
						type="text" placeholder="保险号" value="${pubInsur.insurnum}"/>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>保险金额<em class="asterisk"></em></label>
					<input type="text" placeholder="保险金额" id="insurcount" name="insurcount" value="${pubInsur.insurcount }" style="width: 63%;"/>&nbsp;&nbsp;&nbsp;元
				</div>
				<div class="col-4">
					<label>保险有效期<em class="asterisk"></em></label>	
				    <input style="width:32%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">
				    	至
	            	<input style="width:32%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">
				</div>
			</div>
		</form>
	    <div class="row">
			<div class="col-10" style="text-align:right;">
				<button class="Mbtn red" onclick="save();">保存</button>
				<button class="Mbtn grey_b" style="margin-left: 20px;" onclick="callBack()">取消</button>
			</div>
		</div>
    </div>
    <script type="text/javascript" src="js/pubVehInsur/editInsur.js"></script>
</body>
</html>
