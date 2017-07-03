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
		<title>管理员账户</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<style type="text/css">
		.tip_box_b {
	        overflow-y: auto;
	    }
	    .content{padding-top:20px;}
	  /*   .dataTables_wrapper{margin-top:10px;} */
	    th, td { white-space: nowrap; }
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
			margin-top:-20px;
		}
		.breadcrumb{text-decoration:underline;}
		
		tbody tr td:first-child {
		    text-align: left !important;
		}
		</style>
	</head>
	<body>
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 账号管理</div>
		<div class="content">
			<div class="row form">
				<div class="col-3" style="text-align: right;">
					<label class="col-5">姓名</label>
					<input class="col-7" id="nickname_s" name="nickname" type="text" placeholder="姓名">
				</div>
				<div class="col-3" style="text-align: right;">
					<label class="col-5">电话</label>
					<input class="col-7" id="telphone_s" name="telphone" type="text" placeholder="电话">
				</div>
				<div class="col-3" style="text-align: right;">
					<label class="col-5">角色</label>
					<select class="col-7" id="roletype" style="line-height:100%;">
						<option value="">全部</option>
						<c:forEach items="${rolesinfo}" var="roleinfo" varStatus="status">
							<option value="${roleinfo.type}">${roleinfo.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-3" style="text-align:right;">
					<button class="Mbtn blue" id="searchbtn">查询</button>
				</div>
			</div>
			
			<div class="row form">
				
			</div>
			
			<div class="row form" style="margin-top:20px;margin-bottom: 0px;">
				<div class="col-6" style="font-size:20px;">
					<h4>管理员账号信息</h4>
				</div>
				<div class="col-6" style="text-align:right;">
					<button class="Mbtn blue" onclick="add()">新增</button>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="title">新增账号</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<label>账号<em class="asterisk"></em></label>
		                <input id="account" name="account" type="text" placeholder="请填写正确的账号" maxlength="20"><br>
		                <label>密码<em class="asterisk"></em></label>
		                <input id="userpassword" name="userpassword" type="password" placeholder="请输入正确长度的密码" maxlength="16"><br>
		                <label>姓名<em class="asterisk"></em></label>
		                <input id="nickname" name="nickname" type="text" placeholder="请输入姓名" maxlength="20"><br>
		                <label>电话<em class="asterisk"></em></label>
		                <input id="telphone" name="telphone" type="text" placeholder="请输入正确的号码"maxlength="11"><br>
		                <label style="padding-right:30px;">邮箱</label>
		                <input id="email" name="email" type="text" placeholder="请输入正确的邮箱格式"maxlength="20"><br>
	            	</form>
	                <button id="save" class="Lbtn red" onclick="save()">提交</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<div class="pop_box" id="assignrole" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="title" style="padding-left:5px;">角色授权<span id="titlerolename"></span></h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="jseditForm" method="get" class="form-horizontal  m-t" id="frmmodal" style="margin:13px;">
	            			<input type="hidden" id="id4assignrole" name="id"/>
	            			<label style="float:left;text-align:inherit;">管理员账号:</label>
	            			<span style="float:left"id="accountNow"></span><br/>
	            			<label style="float:left;text-align:inherit;">请选择角色:</label>
	            			<ul id="rolelist" class="select_content" 
	            			style="display:block;height:200px;width:60%;overflow:auto;position:inherit;margin:14px">
							</ul>
	            	</form>
	                <button id="save" class="Lbtn red" onclick="doassignrole()">提交</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/user/index.js"></script>
	</body>
</html>
