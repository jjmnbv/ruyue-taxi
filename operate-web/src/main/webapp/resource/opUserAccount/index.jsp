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
		<title>用户账户</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/opuseraccount_media.css" />
		
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

	</head>
	
	<style type="text/css">
		/* 前端对于类似页面的补充 */
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		/* 固定列样式调整  */
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
	
	<body class="opuseraccount_css_body">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 用户账户</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
				<div class="col-4">
					<label>账号</label><input id="userId" name="userId" type="text" placeholder="">
				</div>

				<div class="col-8" style="text-align: right;">
					<button class="Mbtn red_q" onclick="search();">查询</button>
					<button class="Mbtn grey_w" onclick="cancel();">清空</button>
				</div>
	
			</div>
			<div class="row">
				<div class="col-12" style="text-align: left;margin-top: 15px;">
					<h4>注册用户账户信息</h4>
				</div>
		    </div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="titleForm">送积分</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<div class="row">
	            			<div class="col-10">
	            				<label>用户账号<em class="asterisk"></em></label>
		               	 		<input id="account" name="account" type="text" readOnly="true"/>
	            			</div>
	            		</div>
	            		<div class="row">
	            			<div class="col-10">
	            				<label>当前积分<em class="asterisk"></em></label>
		               	 		<input id="balance" name="balance" type="text" readOnly="true">
	            			</div>
	            		</div>
	            		<div class="row">
	            			<div class="col-10">
	            				<label>赠送积分<em class="asterisk"></em></label>
		               	 		<input maxlength = "4" id="giveBalance" name="giveBalance" type="text"placeholder="请输入正整数,最大为四位" onBlur="overFormat(this)"onkeydown="if(event.keyCode==13)event.keyCode=9" onKeyPress="if ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false"/>  
	            			</div>	
	            		</div>
	            	</form>
	                <button class="Lbtn red_q" onclick="addBalance()">完成</button>
	                <button class="Lbtn grey_w" style="margin-left: 10%;" onclick="canelBalance()">取消</button>
	            </div>
	        </div>
		</div>
		<script type="text/javascript" src="js/opUserAccount/index.js"></script>
	</body>
</html>
