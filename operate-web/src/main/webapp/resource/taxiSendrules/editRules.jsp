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
		<title>维护规则</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	
	<style type="text/css">
		.breadcrumb{
			text-decoration:underline;
			padding: 8px 15px;
			margin-bottom: 20px;
			list-style: none;
			background-color: #f5f5f5;
			border-radius:4px;
		}
		.tally{width: 100%!important;}
		li{margin-bottom: 50px;}
		.taxi-12{width: 100%;padding-top: 10px;}
		.taxi-4{width: 33.333333%;float: left;}
		input[type=radio]{margin: 0px 3px 0px 12px;}
		input[type=text]{width: 80px;}
		label.error{
			padding-left: 0%;
			line-height: 100%;
			height: auto;
			float: left;
		}
		.taxi-4 label.error{
			padding-left: 44%;
		}
		#pushnumlimitLabel label.error{
			padding-left: 19%;
		}
		#carsintervalLabel label.error{
			padding-left: 11%;
		}
		a{
			color: inherit;
		}
	</style>

	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="TaxiSendrules/Index">出租车派单规则</a> > <span id="taxiSendrulePageTitle">新增规则</span>
			<button class="SSbtn blue back" onclick="window.history.back()" style="margin-top: -1px;">返回</button>
		</div>
		
		<div class="content">
		  <form id="formss" class="form-edit">
		    <input name="id" id="id" value="${sendrules.id}" type="hidden">
		    <input name="taxiDriverCount" id="taxiDriverCount" value="${taxiDriverCount}" type="hidden">
		    <input type="hidden" name="city" id="city" value="${sendrules.city}">
		    <input type="hidden" name="citymarkid" id="citymarkid" value="${sendrules.citymarkid}">
			<div class="row" style="padding-top: 30px;">
				<ul style="padding-left: 20%;width: 70%;">
					<li>
						<label style="text-align: left;"><b>城市名称</b><em class="asterisk"></em></label><br>
						<div class="input_box2">
							<input type="text" placeholder="请选择城市" id="cityname" name="cityname" class="tally" readonly="readonly" value="${sendrules.cityname}">
						</div>
					</li>
					<li>
						<label style="text-align: left;"><b>用车类型</b></label><br>
						<select class="tally" id="usetype" name="usetype" onchange="initShow();">
							<option value="1" <c:if test="${sendrules.usetype == 1}">selected="selected"</c:if>>即刻用车</option>
							<option value="0" <c:if test="${sendrules.usetype == 0}">selected="selected"</c:if>>预约用车</option>
						</select>
					</li>
					<li>
						<label style="text-align: left;"><b>派单方式</b></label><br>
						<select class="tally" id="sendtype" name="sendtype" onchange="initShow();">
							<option value="0" <c:if test="${sendrules.sendtype == 0}">selected="selected"</c:if>>强派方式</option>
							<option value="1" <c:if test="${sendrules.sendtype == 1}">selected="selected"</c:if>>抢派方式</option>
							<option value="2" <c:if test="${sendrules.sendtype == 2}">selected="selected"</c:if>>抢单方式</option>
						</select>
					</li>
					<li>
						<label style="text-align: left;"><b>派单模式</b></label>
						<div class="taxi-12">
							<input type="radio" name="sendmodel" value="0" onchange="initShow();" <c:if test="${null == sendrules.sendmodel || sendrules.sendmodel == 0}">checked="checked"</c:if>>系统
							<input type="radio" name="sendmodel" value="1" onchange="initShow();" <c:if test="${sendrules.sendmodel == 1}">checked="checked"</c:if>>系统+人工
						</div>
						<br>
						<div class="taxi-12">
							<div class="taxi-4">
								<label style="width: 36%">系统派单时限<em class="asterisk"></em></label>
								<input type="text" id="systemsendinterval" name="systemsendinterval" maxlength="5" oninput="onlyInputNum(this);" value="${sendrules.systemsendinterval}">分钟
							</div>
							<div class="taxi-4" id="driversendintervalLabel">
								<label style="width: 36%">司机抢单时限<em class="asterisk"></em></label>
								<input type="text" id="driversendinterval" name="driversendinterval" maxlength="5" oninput="onlyInputNum(this);" value="${sendrules.driversendinterval}">秒
							</div>
							<div class="taxi-4" id="personsendintervalLabel">
								<label style="width: 36%">人工派单时限<em class="asterisk"></em></label>
								<input type="text" id="personsendinterval" name="personsendinterval" maxlength="5" oninput="onlyInputNum(this);" value="${sendrules.personsendinterval}">分钟
							</div>
						</div>
						<br>
					</li>
					<li>
						<label style="text-align: left;"><b>派单范围</b></label>
						<div class="taxi-12">
							<div class="taxi-4" id="initsendradiusLabel">
								<label style="width: 36%">初始派单半径<em class="asterisk"></em></label>
								<input type="text" id="initsendradius" name="initsendradius" maxlength="7" oninput="onlypositiveNum(this);" value="${sendrules.initsendradius}">公里
							</div>
							<div class="taxi-4">
								<label style="width: 36%">最大派单半径<em class="asterisk"></em></label>
								<input type="text" id="maxsendradius" name="maxsendradius" maxlength="7" oninput="onlypositiveNum(this);" value="${sendrules.maxsendradius}">公里
							</div>
							<div class="taxi-4" id="increratioLabel">
								<label style="width: 36%">&nbsp;&nbsp;&nbsp;半径递增比<em class="asterisk"></em></label>
								<input type="text" id="increratio" name="increratio" maxlength="7" oninput="onlypositiveNum(this);" value="${sendrules.increratio}">%
							</div>
						</div>
						<br>
					</li>
					<li id="pushnumlimitLabel">
						<label style="text-align: left;"><b>推送数量</b></label><br>
						<input type="radio" name="pushnumlimit" value="0" onchange="initShow();" <c:if test="${null == sendrules.pushnumlimit || sendrules.pushnumlimit == 0}">checked="checked"</c:if>>不限制
						<input type="radio" name="pushnumlimit" value="1" onchange="initShow();" <c:if test="${sendrules.pushnumlimit == 1}">checked="checked"</c:if>>限制
						<input type="text" id="pushnum" name="pushnum" oninput="limitPush();" value="${sendrules.pushnum}">人次
					</li>
					<li>
						<label style="text-align: left;"><b>推送限制</b></label><br>
						<input type="radio" name="pushlimit" value="0" <c:if test="${null == sendrules.pushlimit || sendrules.pushlimit == 0}">checked="checked"</c:if>>存在抢单弹窗，不推单
						<input type="radio" name="pushlimit" value="1" <c:if test="${sendrules.pushlimit == 1}">checked="checked"</c:if>>存在抢单弹窗，推单
					</li>
					<li id="carsintervalLabel">
						<label style="text-align: left;"><b>预约订单</b></label><br>
						<label style="width: 13%">约车时限<em class="asterisk"></em></label>
						<input type="text" style="width: 320px;" id="carsinterval" maxlength="5" name="carsinterval" oninput="onlyInputNum(this);" value="${sendrules.carsinterval}" placeholder="超过时限的订单为预约单">分钟
					</li>
					<li>
						<div class="taxi-12" style="text-align: right;">
							<a href="javascript:void(0);" class="Mbtn orange" style="margin-right: 20px;" onclick="save()">保存</a>
                     		<a href="javascript:void(0);" class="Mbtn grey" onclick="window.history.back()">取消</a>
						</div>
					</li>
				</ul>
			</div>
		  </form>
		</div>
		<script type="text/javascript" src="js/taxiSendrules/editRules.js"></script>
	</body>
</html>
