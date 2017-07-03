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
<meta name="description" content="">
<meta name="keywords" content="">
<title>账户信息管理</title>
<base href="<%=basePath%>">
<!-- <link rel="stylesheet" type="text/css" href="content/css/style.css"/> -->
<link rel="stylesheet" type="text/css" href="content/css/common.css" />
<link rel="stylesheet" type="text/css" href="content/css/zhanghuxinxiguanli.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
<link rel="stylesheet" type="text/css" href="content/css/yuangongguanli.css"/>
<link rel="stylesheet" type="text/css" href="content/css/zstyle.css" />
<script type="text/javascript" src="content/js/jquery.js"></script>
<script src="content/plugins/toastr/toastr.min.js"></script>
 <script type="text/javascript" src="js/basecommon.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
 <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
 <style type="text/css">
 .con_inp .error{
  	position: absolute;
    left: 0px;
    top: 30px;
    font-size: 10px;
    color: #f33333;
  	}
  	 input .error{
  		position: absolute;
        left: 0px;
        top:  0px;
  	}
  label{display:inline-block;width: 150px;text-align: right;margin-right: 20px;}
 </style>
</head>
<body>
	<div class="con_box">
		<div class="con_header">
		账户信息管理
		<div style="margin-top:20px;float:right"><button class="btn_green" id="edit" onclick="edit()">编辑</button></div>
		</div>
		<input name="organId" id="organId" value="${orgInformation.organId}" type="hidden"/>
		<div class="con_con">
			<div class="con_left">
				<div class="con_pass">开户账号</div>
				<div class="con_pass">开户名称</div>
				<div class="con_pass">开户银行</div>
			</div>
			<div class="con_middle">
				<form action="" method="post">
					<div class="con_inp">
						<input name="creditcardnum" id="creditcardnum" value="${orgInformation.creditcardnum}"
						readonly="readonly"	 autocomplete="off" />
					</div>
					<div class="con_inp">
						<input name="creditcardname" id="creditcardname" value="${orgInformation.creditcardname}"
						readonly="readonly"	autocomplete="off" />
					</div>
					<div class="con_inp">
						<input name="bankname" id="bankname" value="${orgInformation.bankname}" 
						readonly="readonly"	autocomplete="off" />
					</div>
				</form>
			</div>
		</div>
		<!--  <div class="pop_box" id="organInformation" style="display: none;">
			<div class="tip_box_b">
				<h3 id="title">编辑账户信息</h3>
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭">
			<div class="con_con">
				<div class="con_left">
					<div class="con_pass">开户账号</div>
					<div class="con_pass">开户名称</div>
					<div class="con_pass">开户银行</div>
				</div>
					<div class="con_middle">
			<form id="editForm">
				<div class="con_inp">
					<input name="creditcardnum1" id="creditcardnum1" value=""  maxlength="19" autocomplete="off"/>
				</div>
 			<div class="con_inp">
 				<input name="creditcardname1" id="creditcardname1" value=""  maxlength="16" autocomplete="off"/>
 			</div>
 			<div class="con_inp">
 				<input name="bankname1" id="bankname1" value=""  maxlength="16" autocomplete="off"/>
 			</div>
 			<div style="text-align:right">
	                <button class="Mbtn orange"  onclick="save()">提交</button>
	                <button class="Mbtn grey"    onclick="canel()">关闭</button>
	        </div>
			</form>
		</div>
			</div>
		</div>
		</div> -->
	<div class="popup_box">
		<div class="add_department popup" id="organInformation">
				<div class="popup_title">
					<span id="addorupdatedept_title">编辑账户信息</span>
					<i class="close"></i>
				</div>
				<div class="popup_content">
					<div class="con_con">
				<div class="con_left">
					<div class="con_pass" style="margin-bottom:25px">开户账号</div>
					<div class="con_pass" style="margin-bottom:25px">开户名称</div>
					<div class="con_pass" style="margin-bottom:25px">开户银行</div>
				</div>
					<div class="con_middle">
					<form id="editForm">
						<div class="con_inp" style="margin-bottom:25px">
							<input name="creditcardnum1" id="creditcardnum1" value="${orgInformation.creditcardnum}"  maxlength="23" autocomplete="off"/>
						</div>
		 			<div class="con_inp"style="margin-bottom:25px">
		 				<input name="creditcardname1" id="creditcardname1" value="${orgInformation.creditcardname}"  maxlength="30" autocomplete="off"/>
		 			</div>
		 			<div class="con_inp"style="margin-bottom:25px">
		 				<input name="bankname1" id="bankname1" value="${orgInformation.bankname}"  maxlength="30" autocomplete="off"/>
		 			</div>
					</form>
		         </div>
			</div>
				</div>
				<div class="popup_footer">
					<span class="cancel" onclick="canel()">取消</span>
					<span class="sure" onclick="save()">保存</span>
				</div>
			</div>
	</div>
	</div>
	<script type="text/javascript" src="js/organInformation/index.js"></script>
</body>
</html>
</body>
</html>