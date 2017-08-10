<%@page import="com.szyciov.util.SystemConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
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
		<title>机构订单</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.skinFlat.css" id="styleSrc"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css" />
		
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
		<script type="text/javascript" src="content/plugins/ion.rangeslider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<style type="text/css">
			.form select{width:68%;}
			.dataTables_wrapper{margin: 0px 0px 10px 0px;}
			.breadcrumb{text-decoration:underline;}
			.row{padding-right:20px;}
			.w400 label.error{padding-left:0px!important;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.DTFC_ScrollWrapper{
				margin-top:-20px;
			}
			.form label{float:left;line-height: 30px;height:30px;}
			.form input{margin-top: 0px!important;}
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="OrderManage/OrgOrderIndex">因公订单</a> > 订单复核
			<button class="SSbtn blue back" target="iframe" onclick="window.history.go(-1);">返回</button>
		</div>
		<div class="content" style="overflow: auto !important;">
			<div class="col-12" style="padding-top: 30px;">
				<h2 id="orderTitle"></h2>
				<table class="table_a ordermanage_css_table_a">
					<tr>
						<td class="grey_c">订单金额(元)</td>
						<td class="grey_c">差异金额(元)</td>
						<td class="grey_c">服务里程(公里)</td>
						<td class="grey_c">服务时长(分钟)</td>
						<td class="grey_c">服务开始时间</td>
						<td class="grey_c">服务结束时间</td>
						<td class="grey_c">计费时长(分钟)</td>
						<td class="grey_c">时间补贴(元)</td>
						<td class="grey_c">里程费(元)</td>
						<td class="grey_c">操作</td>
					</tr>
					<tr>
						<td id="ddje"></td>
						<td><span id="cyje" class="font_grey"></span></td>
						<td id="fwlc"></td>
						<td id="fwsc"></td>
						<td id="fwkssj"></td>
						<td id="fwjssj"></td>
						<td id="ljsj"></td>
						<td id="sjbt"></td>
						<td id="lcf"></td>
						<td>
							<button type="button" class="SSbtn blue" onclick="review()"><i class="fa fa-paste"></i>复核</button>
						</td>
					</tr>
				</table>
			</div>
			<div class="col-12">
				<h2>订单复核记录</h2>
				<table id="reviewRecordDataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
			</div>
		</div>
		
		<div class="pop_box" id="orderReivewFormDiv" style="display: none;">
			<div class="tip_box_b" style="width: 530px;">
	            <h3>订单复核</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400" style="width: 485px;">
	            	<input type="hidden" id="pricecopy" name="pricecopy">
	            	<input type="hidden" id="beforeprice" name="beforeprice">
	            	<input type="hidden" id="paymentstatus" name="paymentstatus">
                    <input type="hidden" id="timetype" name="timetype">
	            	<form id="orderReivewForm" method="get">
                        <div class="row form">
                            <div class="col-12">
                                <label>复核类型<em class="asterisk"></em></label>
                                <input type="hidden" id="reviewtype" value="1">
                                <input type="radio" name="reviewtype" value="1" checked="checked" style="margin-left: -90px;">按里程时长
                                <input type="radio" name="reviewtype" value="2">按固定金额
                            </div>
                        </div>
                        <div class="row form" style="display: none" id="reviewedpriceDiv">
                            <div class="col-12">
                                <label>复核金额<em class="asterisk"></em></label>
                                <input type="text" id="reviewedprice" name="reviewedprice" style="width: 60%;" oninput="getPrice()" maxlength="6" placeholder="复核后金额">&nbsp;&nbsp;元
                            </div>
                        </div>
                        <div class="row form" id="timeDiv">
		            		<div class="col-12">
		            			<label>服务时间<em class="asterisk"></em></label>
			            		<input style="width:30%;" id="starttime" name="starttime" readonly="readonly" type="text" oninput="getPrice()" placeholder="服务开始时间" value="" class="searchDate">
								至
			            		<input style="width:30%;" id="endtime" name="endtime" readonly="readonly" type="text" oninput="getPrice()" placeholder="服务结束时间" value="" class="searchDate">
		            		</div>
		            	</div>
		            	<div class="row form" id="mileageDiv">
		            		<div class="col-12">
			            		<label>服务里程<em class="asterisk"></em></label>
			            		<input id="mileage" name="mileage" style="width: 58%;" oninput="getPrice()" maxlength="6" type="text" placeholder="服务里程">&nbsp;&nbsp;公里
		            		</div>
		            	</div>
		            	<div class="row form" id="counttimesDiv">
		            		<div class="col-12">
			            		<label>计费时长<em class="asterisk"></em></label>
			            		<input id="counttimes" oninput="getPrice()" name="counttimes" style="width: 58%;" maxlength="6" type="text" placeholder="计费时长">&nbsp;&nbsp;分钟
		            		</div>
		            	</div>
		            	<div class="row form">
		            		<div class="col-12">
			            		<label style="padding-right: 16px;">复核方</label>
			            		<select id="reviewperson" name="reviewperson" style="width: 65%" disabled="disabled">
			            			<option value="">请选择</option>
									<option value="1">司机</option>
									<option value="2">乘客</option>
								</select>
							</div>
		            	</div>
		            	<div class="row form">
			                <div class="col-12">
				                <label style="padding-right: 16px;">处理意见<em class="asterisk"></em></label>
				                <textarea id="reasonTextarea" name="reasonTextarea" rows="2" style="width: 65%" cols="3" maxlength="100" placeholder="请输入处理意见"></textarea>
		            		</div>
		            	</div>
	            	</form>
	            	<span id="cyjets" class="font_red"></span>
	                <button class="Lbtn red" onclick="reviewPost()">确定</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		<script type="text/javascript">
			var orderObj = {
				orderno: "<%=request.getParameter("orderno")%>"
			};
		</script>
		<script type="text/javascript" src="js/orgordermanage/orderreview.js"></script>
	</body>
</html>
