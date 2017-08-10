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
		<title>溢价规则</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		
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
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.fileupload.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
	</head>
	<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
				
		.tip_box_b label{float:left;line-height: 30px;height:30px;}
		.tip_box_b select,.tip_box_b input[type=text]{width:63%;float:left;margin-top: 0px;}
		.tip_box_b label.error {padding-left: 0%;margin-left: 30%!important;}
		
		th, td { white-space: nowrap; }
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
		margin-top:-20px;
		}
		#dataGrid_wrapper .DTFC_LeftBodyWrapper tbody tr td:first-child{
			text-align:left!important;
		}
	</style>
	<body class="leLeasescompany_css_body">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 溢价规则
			<button class="SSbtn green_q back" onclick="addOn()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-4"><label>城市</label>
						<select id="citycode">
							<option value="">全部</option>
							<c:forEach items="${list}" var="list">
								<option value="${list.id}">${list.text}</option>
							</c:forEach>
						</select>
						<input type="hidden" id="cityName" name="cityName" value="">
					</div>
					<div class="col-4"><label>业务类型</label>
						<select id="cartype">
							<option value="">全部</option>
							<option value="0">网约车</option>
							<option value="1">出租车</option>
						</select>
						<input type="hidden" id="cartype" name="cartype" value="">
					</div>
					<div class="col-4"><label>规则类型</label>
						<select id="ruletype">
							<option value="">全部</option>
							<option value="0">按星期</option>
							<option value="1">按日期</option>
						</select>
						<input type="hidden" id="ruletype" name="ruletype" value="">
					</div>
					</div>
					<div class="row">
					<div class="col-4"><label>规则状态</label>
						<select id="rulestatus">
							<option value="">全部</option>
							<option value="0">禁用</option>
							<option value="1">启用</option>
						</select>
						<input type="hidden" id="rulestatus" name="rulestatus" value="">
					</div>
					<div class="col-4">
					<label>规则名称</label><input id="rulename" type="text" style="width: 69%" placeholder="规则名称">
				    </div>
					<div class="col-4">
						<label>有效期</label>
	            		<input style="width:31.5%;" id="startdt" name="startdt" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">
						至
	            		<input style="width:31.5%;" id="enddt" name="enddt" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">
						<input type="hidden" id="startdt" name="startdt" value="">
						<input type="hidden" id="enddt" name="enddt" value="">
					</div>
				</div>
				<div class= "row">
				<div class="col-12" style="text-align: right;">
						<button class="Mbtn red_q" onclick="search();">查询</button>
						<button class="Mbtn grey_w" onclick="emptys();">清空</button>
					</div>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		<div class="pop_box" id="unwrapVel" style="display: none;">
			<div class="tip_box_b form">
	            <h3 id="unwrapVelTitleForm">提示</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
		           
	            <button class="Lbtn red" onclick="unwrapVelSave()">确定</button>
                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	        </div>
		</div>
		<script type="text/javascript" src="js/pubPremiumRule/index.js"></script>
		<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
	</body>
</html>
