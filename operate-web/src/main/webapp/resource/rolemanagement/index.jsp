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
		<title>管理员角色</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<!-- <link href="content/plugins/zTree/zTreeStyle.css" rel="stylesheet"> -->
		<link href="content/plugins/zTree_v3/css/metroStyle/metroStyle.css" rel="stylesheet"> 
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<style type="text/css">
		#datatreeFormDiv .ztree{
			display:block;
			bottom: 50px;
			overflow: auto;
			margin: 5px 0;
			border:0;
    		border-bottom: 1px solid #c1bfbf;
    		top: 39px;
    		border-radius:0;
    		width: 100%;
   			position: absolute;
   			margin-top: 28px;
		}
		#datatreeFormDiv .tip_box_b{
			height:50%;
			padding-bottom: 56px;
		}
		
		#datatreeFormDiv .w400{
			position: absolute;
    		bottom: 5px;
		}
		
		#roletype{   width: 67%;
		    margin-top: 18px;
		    height: 30px;
		    line-height: 30px;
		    border: 1px solid #dbdbdb;
		    border-radius: 3px;
		    color: #949494;
		    resize: none;
		    padding: 0px 10px;
	    }
	    .tip_box_b {
	        overflow-y: auto;
	    }
	      /* .dataTables_wrapper{margin-top:10px;} */
	      .ztree{padding-left:30px;}
	      th, td { white-space: nowrap;}
	      tr>td:first-child{white-space:normal}
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
			margin-top:-20px;
		}
		.breadcrumb{text-decoration:underline;}
		/*样式修改*/
		.table-bordered thead tr th{ 
	        border-bottom-width: 1px; 
	    }
	    #datatreeFormDiv .w400{
	    	width:100%;
	    }
		</style>
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
		<script type="text/javascript" src="content/plugins/zTree/jquery.ztree.core-3.5.min.js"></script>
		<script type="text/javascript" src="content/plugins/zTree/jquery.ztree.excheck-3.5.min.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	<body>
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 角色管理</div>
		<div class="content">
			<div class="row form" style="padding-top: 20px;">
				<div class="col-6" style="text-align: left;font-size:20px;">
					<h4>管理员角色信息</h4>
				</div>
	
				<div class="col-6" style="text-align: right;">
					<button class="Mbtn blue" onclick="add()">新增</button>
				</div>
	
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="title">新增角色</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400" style="width:420px;">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal" style="margin-bottom:10px;">
	            		<input type="hidden" id="id" name="id"/>
	            		<label style="float:left;width:31%;margin-right:5px;"><span id="editFormDiv_type">选择角色类型</span><em class="asterisk"></em></label>
						<select name="roletype" id="roletype" class="form-control">
							 <option selected="selected" value="">请选择</option>
							 <c:forEach items="${roletypelist}" var="item"> 
								<option value="${item.value}">${item.text}</option>
							 </c:forEach> 
						</select>
						<label id="roletypecaption" style="display:none;width:67%;text-align:left;"></label>
		                <label style="CLEAR:BOTH;FLOAT:LEFT;MARGIN-TOP:20PX;width:31%;">角色名称<em class="asterisk"></em></label>
		                <input style="width:67%;" id="rolename" name="rolename" type="text"maxlength="20" placeholder="请输入角色名称"><br><br>
		                <label style="vertical-align:top;width:31%;padding-right:26px;">角色描述</label>
		                <textarea style="width:67%;" id="roledesc" name="roledesc" cols="50" rows="5" maxlength="50"></textarea>
	            	</form>
	                <button id="save" class="Lbtn red" onclick="save()">提交</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<div class="pop_box" id="datatreeFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="title"><lable id="roletitle"></lable></h3>
	            <input type="hidden" id="roledataroleid" />
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div style="float:left;" id="qxfx">
	            	<input name="ze" type="radio" id="qx" value="" />全选
					<input name="ze" type="radio" id="fx" value="" style="margin-bottom:0px;" />反选
	            </div>
	            <ul class="ztree" id="ztree"></ul>
	            <div class="w400">
	                <button id="saveauthority" class="Lbtn red">提交</button>
	                <button class="Lbtn grey" style="margin-left: 10%;margin-top:0px;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/rolemanagement/index.js"></script>
	</body>
</html>
