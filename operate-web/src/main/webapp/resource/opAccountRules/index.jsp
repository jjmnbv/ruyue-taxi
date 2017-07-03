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
		<title>网约车计费规则</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
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
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<style type="text/css">
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
		</style>
	</head>
	
	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > 网约车计费规则
			<button class="SSbtn blue back" onclick="add()" id="addBtn" style="margin-top: -1px;">新增</button>
			<input type="hidden" id="usertype" value="${usertype}">
		</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
				<div class="col-4">
					<label>规则类型</label>
					<select id="rulestype" name="rulestype">
						<option value="" selected="selected">全部</option>
						<c:forEach var="ruleTypeList" items="${ruleTypeList}">
							<option value="${ruleTypeList.value}">${ruleTypeList.text}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-4">
					<label class="col-1">城市</label><input class="col-3" id="city" name="city" type="text" placeholder="全部">
				</div>
				<div class="col-4">
					<label>服务车型</label>
					<select id="vehiclemodelsid" name="vehiclemodelsid">
						<option value="" selected="selected">全部</option>
						<c:forEach var="vehiclemodelList" items="${vehiclemodelList}">
							<option value="${vehiclemodelList.id}">${vehiclemodelList.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="row form">
				<div class="col-4">
					<label>规则状态</label>
					<select id="rulesstate" name="rulesstate">
						<option value="" selected="selected">全部</option>
						<c:forEach var="ruleStatusList" items="${ruleStatusList}">
							<option value="${ruleStatusList.value}">${ruleStatusList.text}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-8" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey" onclick="clearSearch();">清空</button>
				</div>
			</div>
			
			<div class="row">
			   <label class="col-10" style="margin-top: 15px;"><h4>计费规则信息</h4></label>
			   <div class="col-2" style="text-align: right;">
			       <button class="Mbtn orange" onclick="editOneKey()" id="editOneKeyBtn">一键更换</button>
			   </div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3>一键更换补贴类型</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<div class="row form col-12">
	            			<label>更换城市<em class="asterisk"></em></label>	            		
	            			<select id="editCity" name="editCity" class="form-control" onchange= "changeTimeType()" style="width:50%;">
								<option value="" selected="selected">请选择</option>
								<c:forEach items="${cityList}" var="cityList" >
									<option value="${cityList.id}">${cityList.text}</option>
								</c:forEach>
							</select>
						</div>						
						<div class="row form" id="allTime">
	            		   <div class="col-12">
	            		      <label style="padding-right: 38px;">当前规则</label>
	            		      <label id="label1" style="width:58%;text-align: left;"></label>
	            		   </div>
	            		   <div class="col-12">
	            		      <label style="padding-right: 38px;">更换成</label>
	            		      <label id="label2" style="width:58%;text-align: left;"></label>
	            		   </div>
	            		</div>
	            		<div class="row form col-12" id="perhourDiv">
	            		   <label>时速<em class="asterisk"></em></label>
		                   <input id="perhour" name="perhour" type="text" placeholder="时速" style="width:50%;float:left;" maxlength="5">公里/小时
		                </div> 
		                <input name="timetype" id="timetype" type="hidden">
	            	</form>
	                <button class="Lbtn red" onclick="save()">更换</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<div class="pop_box" id="disableFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="title">禁用规则</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="disableForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		
	            		<div class="row form col-12">
	            		    <label style="vertical-align:top;">禁用原因<em class="asterisk"></em></label>
	            		    <textarea style="width:68%;" id="reason" name="reason" class="50" rows="5" placeholder="填写禁用原因" maxlength="200"></textarea>
	            		</div>
	            	</form>
	                <button class="Lbtn orange" onclick="saveDisable()">禁用</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/opAccountRules/index.js"></script>
	</body>
</html>
