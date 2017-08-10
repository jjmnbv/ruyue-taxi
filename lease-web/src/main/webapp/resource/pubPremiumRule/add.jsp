<%@ page contentType="text/html; charset=UTF-8"
	import="com.szyciov.util.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String yingyan_ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>新增溢价规则</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		<link rel="stylesheet" type="text/css" href="css/opAccountRules/ztimepicker1.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
</head>
<style>
		.breadcrumb{text-decoration:underline;}
		.form label{float:left;line-height: 30px;height:30px;}
		.form select,.form input[type=text]{width:70%;float:left;}
/* 		.form #inp_box1 label.error{padding-left: 0;margin-left:0;width:175%;} */
		.form-account .col-4 .unit{position:relative;top:4px;}
		.city_container{
			min-width:300px;
			height:210px;
			position:absolute;
			z-index: 9999;
			background: white;
			border:1px solid #e1e1e1;
			font-size: 14px;
			color:#333;
			display: none;
			margin-left: 210px;
			margin-top: 30px;
		}
		#nightstarttime1 label.error {margin-left: -33%!important;}
		#nightstarttime2 label.error {margin-left: 5%!important;}
		.ztimepicker{position: relative;width: 150px;}
    </style>
<body>
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="PubPremiumRule/Index">溢价规则</a> > <span id="sysversionPageTitle">${addOrModify}</span>
		<button class="SSbtn red_q back" onclick="callBack()">返回</button>
	</div>
	<div class="content" style="padding-top:30px;">
	<input id="aaid" name="aaid" value="${pubPremiumParam.id}"type="hidden"/>
	<input id="aaruletype" name="aaid" value="${pubPremiumParam.ruletype}"type="hidden"/>
		  <form id="editPubPremiumRuleForm" class="form-account" method="post">
			<div class="row form" style="padding-top: 30px;">
			<div class="col-6">
				<label class="account-items">所属城市<em class="asterisk"></em></label>
				<div class="input_box2"style="width: 100%">
					<input style="width: 60%" type="text" placeholder="请选择城市" id="cityname" name="cityname" class="tally" readonly="readonly" value=""/>
				</div>
			</div>
			<div class="col-6">
						<label class="account-items">规则名称<em class="asterisk"></em></label>
						<input id="rulename" name="rulename" type="text" style="width: 60%" placeholder="如:上海周末溢价" maxlength="20">
					</div>
			</div>
			<div class="row form">
			<div class="col-6">
			 <label class="account-items">业务类型<em class="asterisk"></em></label>
					   <select id="cartype"name="cartype" style="width: 60%">
							<option value="">请选择业务类型</option>
							<option value="0">网约车</option>
							<option value="1">出租车</option>
						</select>
			</div>
			</div>
			<div class="row form">
			<div class="col-12" style="border-bottom: 1px solid #dbdbdb;">
			    <label style="text-align: left;width:100px;font-weight:bold;font-size:18px">规则类型</label>
			    <label style="text-align: left;width:150px"><input id="week"name="ruletype" type="radio" checked value="week" />按星期 </label> 
				<label style="text-align: left;width:150px"><input id="date" name="ruletype"ruletype" type="radio" value="date" />按日期 </label> 
			</div>
			</div>
            <div>
            <table id="weekTable" style="margin-left:9%">
            <tr><th></th><th> 时间范围</th><th>溢价倍率</th>
            <th>&nbsp&nbsp<a style="display:none"id="th" href="javascript:void(0)"onclick="addSeven(this)"><font color="#8CEA00">新增</font></a>
            <a style="display:none"id="th" href="javascript:void(0)"onclick="deleteSeven(this)"><font color="red">删除</font></a></th></tr>
            <tr><td><input name="theWeek" id="oneWeek" type="checkbox" value="one">星期一</td>
            <td>     <div class="row">
                        <div class="col-5" id="nightstarttime1">
		                    <div class="ztimepicker" >
								<input type="text" readonly value="" class="ztimepicker_input" name="time" id="onetimeS"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-1"style="margin-left: 4%;">
						<span>-</span>
						</div>
						<div class="col-5" id="nightstarttime2">
							<div class="ztimepicker">
								<input type="text" readonly value="" class="ztimepicker_input" name="time" id="onetimeE" placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
					</div>
					</td>
            <td><input id="oneOk" name="ok" style="text-align: center;"type="text" placeholder="溢价倍率"  maxlength = "3" onBlur="overFormat(this)" onkeypress="pageOnlyNumber(event)" onkeyup="clearNoNum2(this)"/></td>
            <td>&nbsp&nbsp<a id="addOne" href="javascript:void(0)"onclick="addOne(this)"><font color="#8CEA00">新增</font></a>
            <a style="display:none"id="deleteOne" href="javascript:void(0)"onclick="deleteOne(this)"><font color="red">删除</font></a></td></tr>
            <tr><td><input name="theWeek" id="towWeek" type="checkbox" value="tow">星期二</td>
            <td>   <div class="row">
                        <div class="col-5" id="nightstarttime1">
		                    <div class="ztimepicker" >
								<input type="text" readonly value="" class="ztimepicker_input" name="time" id="towtimeS"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-1"style="margin-left: 4%;">
						<span>-</span>
						</div>
						<div class="col-5" id="nightstarttime2">
							<div class="ztimepicker">
								<input type="text" readonly value="" class="ztimepicker_input" name="nightendtime" id="towtimeE"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
					</div>
            </td>
            <td><input id="towOk" name="ok" style="text-align: center;"type="text" placeholder="溢价倍率"maxlength = "3" onBlur="overFormat(this)" onkeypress="pageOnlyNumber(event)" onkeyup="clearNoNum2(this)"/></td>
            <td>&nbsp&nbsp<a id="addTow" href="javascript:void(0)"onclick="addTow(this)"><font color="#8CEA00">新增</font></a>
            <a style="display:none"id="deleteTow" href="javascript:void(0)"onclick="deleteTow(this)"><font color="red">删除</font></a></td></tr>
            <tr><td><input name="theWeek" id="threeWeek" type="checkbox" value="three">星期三</td>
            <td>  <div class="row">
                        <div class="col-5" id="nightstarttime1">
		                    <div class="ztimepicker" >
								<input type="text" readonly value="" class="ztimepicker_input" name="nightstarttime" id="threetimeS"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-1"style="margin-left: 4%;">
						<span>-</span>
						</div>
						<div class="col-5" id="nightstarttime2">
							<div class="ztimepicker">
								<input type="text" readonly value="" class="ztimepicker_input" name="nightendtime" id="threetimeE"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
					</div>
            </td>
            <td><input id="threeOk"style="text-align: center;"type="text" placeholder="溢价倍率"maxlength = "3" onBlur="overFormat(this)" onkeypress="pageOnlyNumber(event)" onkeyup="clearNoNum2(this)"/></td>
            <td>&nbsp&nbsp<a id="addThree" href="javascript:void(0)"onclick="addThree(this)"><font color="#8CEA00">新增</font></a>
            <a style="display:none"id="deleteThree" href="javascript:void(0)"onclick="deleteThree(this)"><font color="red">删除</font></a></td></tr>
            <tr><td><input name="theWeek" id="fourWeek" type="checkbox" value="four">星期四</td>
            <td>  <div class="row">
                        <div class="col-5" id="nightstarttime1">
		                    <div class="ztimepicker" >
								<input type="text" readonly value="" class="ztimepicker_input" name="nightstarttime" id="fourtimeS"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-1"style="margin-left: 4%;">
						<span>-</span>
						</div>
						<div class="col-5" id="nightstarttime2">
							<div class="ztimepicker">
								<input type="text" readonly value="${opAccountrules.nightendtime}" class="ztimepicker_input" name="nightendtime" id="fourtimeE"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
					</div>
            </td>
            <td><input id="fourOk"style="text-align: center;"type="text" placeholder="溢价倍率"maxlength = "3" onBlur="overFormat(this)" onkeypress="pageOnlyNumber(event)" onkeyup="clearNoNum2(this)"/></td>
            <td>&nbsp&nbsp<a id="addFour" href="javascript:void(0)"onclick="addFour(this)"><font color="#8CEA00">新增</font></a>
            <a style="display:none"id="deleteFour" href="javascript:void(0)"onclick="deleteFour(this)"><font color="red">删除</font></a></td></tr>
            <tr><td><input name="theWeek" id="fiveWeek" type="checkbox"value="five">星期五</td>
            <td>  <div class="row">
                        <div class="col-5" id="nightstarttime1">
		                    <div class="ztimepicker" >
								<input type="text" readonly value="${opAccountrules.nightstarttime}" class="ztimepicker_input" name="nightstarttime" id="fivetimeS"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-1"style="margin-left: 4%;">
						<span>-</span>
						</div>
						<div class="col-5" id="nightstarttime2">
							<div class="ztimepicker">
								<input type="text" readonly value="${opAccountrules.nightendtime}" class="ztimepicker_input" name="nightendtime" id="fivetimeE"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
					</div>
            </td>
            <td><input id="fiveOk"style="text-align: center;"type="text" placeholder="溢价倍率"maxlength = "3" onBlur="overFormat(this)" onkeypress="pageOnlyNumber(event)" onkeyup="clearNoNum2(this)"/></td>
            <td>&nbsp&nbsp<a id="addFive" href="javascript:void(0)"onclick="addFive(this)"><font color="#8CEA00">新增</font></a>
            <a style="display:none"id="deleteFive" href="javascript:void(0)"onclick="deleteFive(this)"><font color="red">删除</font></a></td></tr>
            <tr><td><input name="theWeek" id="sixWeek" type="checkbox"value="six">星期六</td>
            <td>  <div class="row">
                        <div class="col-5" id="nightstarttime1">
		                    <div class="ztimepicker" >
								<input type="text" readonly value="${opAccountrules.nightstarttime}" class="ztimepicker_input" name="nightstarttime" id="sixtimeS"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-1"style="margin-left: 4%;">
						<span>-</span>
						</div>
						<div class="col-5" id="nightstarttime2">
							<div class="ztimepicker">
								<input type="text" readonly value="${opAccountrules.nightendtime}" class="ztimepicker_input" name="nightendtime" id="sixtimeE"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
					</div>
            </td>
            <td><input id="sixOk"style="text-align: center;"type="text" placeholder="溢价倍率"maxlength = "3" onBlur="overFormat(this)" onkeypress="pageOnlyNumber(event)" onkeyup="clearNoNum2(this)"/></td>
            <td>&nbsp&nbsp<a id="addSix" href="javascript:void(0)"onclick="addSix(this)"><font color="#8CEA00">新增</font></a>
            <a style="display:none"id="deleteSix" href="javascript:void(0)"onclick="deleteSix(this)"><font color="red">删除</font></a></td></tr>
            <tr><td><input name="theWeek" id="sevenWeek" type="checkbox" value="seven">星期日</td>
            <td>  <div class="row">
                        <div class="col-5" id="nightstarttime1">
		                    <div class="ztimepicker" >
								<input type="text" readonly value="" class="ztimepicker_input" name="nightstarttime" id="seventimeS"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-1"style="margin-left: 4%;">
						<span>-</span>
						</div>
						<div class="col-5" id="nightstarttime2">
							<div class="ztimepicker">
								<input type="text" readonly value="" class="ztimepicker_input" name="nightendtime" id="seventimeE"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
					</div>
            </td>
            <td><input id="sevenOk"style="text-align: center;"type="text" placeholder="溢价倍率"/></td>
            <td>&nbsp&nbsp<a id="addSeven" href="javascript:void(0)"onclick="addSeven(this)"><font color="#8CEA00">新增</font></a>
            <a style="display:none"id="deleteSeven" href="javascript:void(0)"onclick="deleteSeven(this)"><font color="red">删除</font></a></td></tr>
            <tr style="display:none">
             <td><input name="save" id="save" type="checkbox" onClick="save_ck(this);"value="ok">星期填充</td>
             <td></td><td></td>
             <td>&nbsp&nbsp<a style="display:none" id="addSeven" href="javascript:void(0)"onclick="addSeven(this)"><font color="#8CEA00">新增</font></a>
            <a id="deleteSeven" href="javascript:void(0)"onclick="deleteSeven(this)"><font color="red">删除</font></a></td>
             </tr>
            </table>
            <table id="dateTable" style="display:none">
             <tr><th>日期范围</th><th>时间范围</th><th>溢价倍率</th>
             <th>
             </th></tr>
             <tr>
             <td id = "dateId" style="text-align:center">
             <input style = "display:none" id="date"value="date"/>
             <label style="display:none">日期</label>
             <input style="width:31.5%;" id="startdt" name="startdt" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">
				-
	         <input style="width:31.5%;" id="enddt" name="enddt" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">
             </td>
             <td>
             <div class="row">
                        <div class="col-5" id="nightstarttime1">
		                    <div class="ztimepicker" >
								<input type="text" readonly value="" class="ztimepicker_input" name="nightstarttime" id="dateS"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-1"style="margin-left: 4%;">
						<span>-</span>
						</div>
						<div class="col-5" id="nightstarttime2">
							<div class="ztimepicker">
								<input type="text" readonly value="" class="ztimepicker_input" name="nightendtime" id="dateE"placeholder="请选择时间"/>
								<div class="ztimebox">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
					</div>
             </td>
            <td><input id="dateOk"style="text-align: center;"type="text" placeholder="溢价倍率"maxlength = "3" onBlur="overFormat(this)" onkeypress="pageOnlyNumber(event)" onkeyup="clearNoNum2(this)"/></td>
             <td>&nbsp&nbsp<a id="addDateOne" href="javascript:void(0)"onclick="addDateOne(this)"><font color="#8CEA00">新增</font></a>
             <a style="display:none"id="deleteDate" href="javascript:void(0)"onclick="deleteDateOne(this)"><font color="red">删除</font></a></td></tr>
             <tr style="display:none">
             <td><input name="save" id="save" type="checkbox" onClick="save_ck(this);"value="hello">日期填充</td>
             <td></td><td></td>
             <td>&nbsp&nbsp<a style="display:none" id="addSeven" href="javascript:void(0)"onclick="addSeven(this)"><font color="#8CEA00">新增</font></a>
            <a id="deleteSeven" href="javascript:void(0)"onclick="deleteSeven(this)"><font color="red">删除</font></a></td>
             </tr>
            </table>
            </div>
			</form>
			<div class="row form">	
				<div class="col-12" style="margin-left:35%">
                     <button class="Mbtn orange" style="margin-right: 20px;" onclick="save()">保存</button>
                     <button class="Mbtn grey" onclick="window.history.go(-1);">取消</button>
                </div>				
		  </div>
	</div>
	<script type="text/javascript" src="js/pubPremiumRule/add.js"></script>
	<script type="text/javascript" src="js/opAccountRules/ztimepicker1.js"></script>
	<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
</body>
</html>
