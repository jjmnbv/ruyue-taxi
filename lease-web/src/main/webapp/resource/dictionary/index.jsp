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
		<title>字典数据</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	<body>
		<div class="crumbs">首页 > 字典数据</div>
		<div class="content">
			<div class="row form" style="margin-top: 40px;">
				<div class="col-6">
					<label>关键字</label><input type="text" placeholder="请输入字典类型、字典文本、描述进行查询">
				</div>
				<div class="col-6">
					<label>关键字</label><input id="key" type="text" placeholder="请输入字典类型、字典文本、描述进行查询">
				</div>
	
				<div class="col-6" style="text-align: left;">
					<button class="Mbtn blue" onclick="add()">新增</button>
				</div>
				<div class="col-6" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn red" style="margin-left: 20px;" onclick="reset();">重置</button>
				</div>
	
			</div>
			<table id="dataGrid"></table>
		</div>
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3>标题</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<label>字典类型<em class="asterisk"></em></label>
		                <input id="type" name="type" type="text" placeholder="请输入字典类型"><br>
		                <label>字典值<em class="asterisk"></em></label>
		                <input id="value" name="value" type="text" placeholder="请输入字典值"><br>
		                <label>字典文本</label>
		                <input id="text" name="text" type="text" placeholder="请输入字典文本"><br>
		                <label>排序</label>
		                <input id="sort" name="sort" type="text" placeholder="请输入排序"><br>
		                <label>描述</label>
		                <input id="desc" name="desc" type="text" placeholder="请输入描述"><br>
	            	</form>
	                <button class="Lbtn red" onclick="save()">提交</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/dictionary/index.js"></script>
	</body>
</html>
