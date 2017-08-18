<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.SystemConfig"%>
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
		<title>订单取消规则</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>

		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
		
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.fileupload.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
	</head>
	<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
				
		.tip_box_b label{float:left}
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
		#dataGrid_wrapper .DTFC_LeftBodyWrapper tbody tr td:first-child{
			text-align:left!important;
		}
		.city_container{display: block;width: 246px;margin-left: 90px;margin-top: 30px;}
		.tip_box_b{max-height:650px}
	</style>
	<body class="leLeasescompany_css_body">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 订单取消规则
			<button class="SSbtn green_q back" onclick="addOn()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-4"><label>城市</label>
						<select id="citycode">
							<option value="">全部</option>
							<c:forEach items="${list}" var="list">
								<option value="${list.id}">${list.text}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4"><label>服务业务</label>
						<select id="cartype">
							<option value="">全部</option>
							<option value="0">网约车</option>
							<option value="1">出租车</option>
						</select>
						<input type="hidden" id="cartype" name="cartype" value="">
					</div>
					<div class="col-4" style="text-align: right;">
						<button class="Mbtn red_q" onclick="search();">查询</button>
						<button class="Mbtn grey_w" onclick="emptys();">清空</button>
			        </div>
					</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="titleForm"><span id="popTitle">新增取消规则</span></h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<div class="row">
	            		 <div class="col-12">
				            <label class="account-items">所属城市<em class="asterisk"></em></label>
							<div class="input_box2"style="width: 100%">
								<input style="width: 60%" type="text" placeholder="请选择城市" id="cityname" name="cityname" class="tally" readonly="readonly" value=""/>
							</div>
			             </div>
			            </div>
	            		<div class="row">
	            		<div class="col-12">
						    <label>服务业务</label>
						    <label style="text-align: left;width:110px"><input id="net"name="cartype" type="radio"  value="0" />网约车 </label> 
							<label style="text-align: left;width:110px"><input id="taxi" name="cartype" type="radio" value="1" />出租车</label> 
			            </div>
	            		</div>
	            		<div class="row">
	            			<div class="col-12">
	            				<label>免责取消时限<em class="asterisk"></em></label>
		               	 		<input style="width:250px" maxlength = "2" id="cancelcount" name="cancelcount" type="text" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                              onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>分钟 
	            			</div>	
	            		</div>
	            		<div class="row">
	            			<div class="col-12">
	            				<label>迟到免责时限<em class="asterisk"></em></label>
		               	 		<input style="width:250px" maxlength = "2" id="latecount" name="latecount" type="text" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                              onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>分钟  
	            			</div>	
	            		</div>
	            		<div class="row">
	            			<div class="col-12">
	            				<label>免责等待时限<em class="asterisk"></em></label>
		               	 		<input style="width:250px" maxlength = "2" id="watingcount" name="watingcount" type="text" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                              onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>分钟  
	            			</div>	
	            		</div>
	            		<div class="row">
	            			<div class="col-12">
	            				<label>取消费用<em class="asterisk"></em></label>
		               	 		<input style="width:250px" maxlength = "2" id="price" name="price" type="text" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                              onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>元  
	            			</div>	
	            		</div>
	            		<div class="row">
	            		<div class="col-12"style="text-align: left ;margin-left: 10px;">
	            		 <span style="text-align: left">注:<br/>免责取消限时:司机接单后，乘客在此时限之前，且司机未抵达，可免责取消订单<br/>
	            		 迟到免责时限:司机迟到时长超过该时限后，乘客可免责取消订单<br/>免责等待时限:乘客迟到时长超过该时限后，客服取消订单，乘客有责</span>
	            		</div>
	            		</div>
	            	</form>
	                <button class="Lbtn red_q" onclick="add()">保存</button>
	                <button class="Lbtn grey_w" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		<script type="text/javascript" src="js/pubOrderCancelRule/index.js"></script>
		<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
	</body>
</html>
