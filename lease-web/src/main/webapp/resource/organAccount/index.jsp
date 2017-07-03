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
		<title>机构账户</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css"/>
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

	</head>
	
	<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		
		.tip_box_b label{float:left;line-height: 30px;height:30px;}
		.tip_box_b input[type=text]{width:61%;float:left;margin-top: 0px;}
		.tip_box_b label.error {margin-left: 0!important;}
		/* 固定列样式调整  */
		th, td { white-space: nowrap; }
        div.dataTables_wrapper {
            width: $(window).width();
            margin: 0 auto;
        }
        .DTFC_ScrollWrapper{
			margin-top:-20px;
		}
	</style>
	
	<body>
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 机构账户</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
				<div class="col-4">
					<label>所属城市</label>
					<select id="city" name="city">
						<option value="" selected="selected">全部</option>
						<c:forEach var="pubCityAddr" items="${pubCityAddr}">
							<option value="${pubCityAddr.id}">${pubCityAddr.city}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-4">
					<label>机构简称</label><input id="organ" name="organ" type="text" placeholder="输入机构简称">
				</div>
				
				<div class="col-4">
					<label>未结算金额</label><input id="unbalance" name="unbalance" type="text" placeholder="检索出大于输入金额" onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d$/.test(value)) event.returnValue=false" onkeyup="clearNoNum2(this)">
				</div>
			</div>
			<div class="row">
			    <div class="col-12" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey" onclick="clearParameter();">清空</button>
				</div>
			</div>
			<div class="row">
				<div class="col-12" style="margin-top: 15px;"><h4>机构账户信息</h4></div>
		    </div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="editRechargeFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="titleAmount">充值</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editRechargeForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<input type="hidden" id="organId" name="organId"/>
	            		<input type="hidden" id="editType" name="editType"/>
	            		<input type="hidden" id="advanceAmount" name="advanceAmount"/>
	            		<div class="row form">
	            			<div class="col-12">
	            				<label>机构名称:</label>
	            				<label id="organName" name="organName" style="width:70%;text-align: left;"></label>
		                	</div>
		                	<div class="col-12">
		                		<label id="lableAmount">充值金额</label>
		                		<input id="amount" name="amount" type="text" placeholder="请输入正确的金额" onkeypress="pageOnlyNumber(event)" onkeyup="clearNoNum(this)" maxlength="9">&nbsp;&nbsp;元
		                	</div>
		                </div>
		                <div id="advanceDiv">
		                    <label id="advanceVisual" style="margin-left: 30%;width:70%;text-align: left;"></label><br>
		                    <label style="margin-left: 30%;width:70%;text-align: left;" class="font_grey">注：可提现金额=账户余额-未结算金额</label>
		                </div>

	            	</form>
	                <button class="Lbtn orange" onclick="saveRecharge()">确定</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>

		<script type="text/javascript" src="js/organAccount/index.js"></script>
	</body>
</html>
