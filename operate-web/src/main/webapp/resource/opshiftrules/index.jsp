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
		<title>交接班规则</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript" src="content/plugins/letterselect/letterselect.js"></script>
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
		.city_container{
			top:78px;
		}
		.form label.error {
		    padding-left: 0px;
		    line-height: 100%;
		    height: auto;
		}
		</style>
	</head>
	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 交接班规则<button class="SSbtn green_a back" onclick="add()" id="addBtn" style="margin-top: -1px;">新增</button></div>
		<div class="content">
			<div class="row form">
				<div class="col-3">
					<label class="col-3">城市</label>
					<%-- <select class="col-7" id="cityselect" style="line-height:100%;">
						<option value="">全部</option>
						<c:forEach items="${cityinfo}" var="roleinfo" varStatus="status">
							<option value="${roleinfo.cityid}">${roleinfo.cityname}</option>
						</c:forEach>
					</select> --%>
					<input id="cityselect" class="select2-container" style="width:66%" type="hidden" placeholder="全部">
				</div>
				<div class="col-9" style="text-align:right;">
					<button class="Mbtn blue" id="searchbtn">查询</button>
				</div>
			</div>
			
			<div class="row form">
				
			</div>
			
			<div class="row form" style="margin-top:20px;margin-bottom: 0px;">
				<div class="col-12" style="font-size:20px;">
					<h4>交接班规则</h4>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<!-- <div class="kongjian_list" id="kongjian_list">
			<div class="box">
				<div class="title">
					<span>ABCDE</span>
					<span>FGHIJ</span>
					<span>KLMNO</span>
					<span>PQRST</span>
					<span>UVWXYZ</span>
				</div>
				<div class="con">

				</div>
				<div class="con">

				</div>
				<div class="con">

				</div>
				<div class="con">

				</div>
				<div class="con">
					
				</div>
			</div> 
		</div> -->
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b" style="width:580px;overflow-y:visible;">
	            <h3 id="title">新增交接班规则</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400" style="width:500px;">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<input type="hidden" id="city" name="city" />
	            		<input type="hidden" name="citymarkid" id="citymarkid" />
	            		<div class="row form" style="margin-right:0px;margin-bottom:12px;">
		            		<label style="float:left;">城市名称<em class="asterisk"></em></label>
		            		<div id="input_box2" style="width:60%;float:left;margin-left:13px;">
			                	<input id="citynameshow" style="margin:0px;float:left;width:100%;" name="citynameshow" type="text" readonly="readonly" maxlength="20"><br>
			              		<label for="citynameshow" class="error" style="display:block;margin-left:0px !important;padding-top: 9px;">请选择所属城市</label>
			                </div>
		            		<div id="inp_box" style="width:60%;float:left;margin-left:13px;display:none;">
			                	<input id="cityname" style="margin:0px;float:left;width:100%;" name="cityname" type="text" readonly="readonly" maxlength="20"><br>
			                </div>
		                </div>
		                <div class="row form"  style="margin-right:0px;margin-bottom:12px;">
			                <label>自主交班时限<em class="asterisk"></em></label>
			                <input id="autoshifttime" name="autoshifttime"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}" type="text" style="width:60%;" maxlength="2"><span>分钟</span><br>
		                	<label for="autoshifttime" class="error" style="display:none;">请输入自主交班时限</label>
		                </div>
		               <div class="row form"  style="margin-right:0px;margin-bottom:12px;">
			                <label>人工指派时限<em class="asterisk"></em></label>
			                <input id="manualshifttime" name="manualshifttime" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}" type="text" style="width:60%;" maxlength="2"><span>分钟</span><br>
	            	  		<label for="manualshifttime" class="error" style="display:none;">请输入人工指派时限</label>
	            	   </div>
	            	</form>
	                <button id="save" class="Lbtn red" onclick="save()">保存</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		<script type="text/javascript" src="js/opshiftrules/index.js"></script>
	</body>
</html>
