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
		<title>维护计费规则</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/ztimepicker/ztimepicker1.css" rel="stylesheet">
		
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
		<script type="text/javascript" src="content/plugins/ztimepicker/ztimepicker1.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		
	</head>
	
	<style type="text/css">
	    /* 前端对于类似页面的补充 */
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}

		.tip_box_b label{float:left;line-height: 30px;height:30px;}
		.tip_box_b input[type=text]{width:63%;float:left;margin-top: 0px;}
		.tip_box_b label.error {margin-left: 0!important;}
		#perhourDiv label.error {margin-left: 2.5%!important;}
		
		.visual {text-align:left;}
		
		th, td { white-space: nowrap; }
        div.dataTables_wrapper {
            width: $(window).width();
            margin: 0 auto;
        }
        .DTFC_ScrollWrapper{
			margin-top:-20px;
		}
		.ztimepicker{width: 119px;}
		.ztimebox{width:85px;}
    #ztimepicker1 .error{
		margin-left:-3%;
		padding-left:0;
}
         #ztimepicker2 .error:before{
         display:none
}  
         label.error{
           width:150%
         }
         label{
         max-width:150%;
         }
         .tip_box_b{
         max-height:600px;
         }
	</style>
	
	<body>
		<div class="crumbs">
		   <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="IndividualAccountRules/Index">客户计费规则</a> > 修改规则
		   <button class="SSbtn blue back" onclick="back()">返回</button>
		</div>
		<div class="content">
		    <input name="rulesRefId" id="rulesRefId" value="${rulesRefId}" type="hidden"> 
			<div class="row"  style="padding-top: 30px;">
			   <div class="col-10" style="margin-top: 15px;"><h4>客户计费规则(<span class="font_red">${shortName}</span>)</h4></div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3>修改规则</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	                    
	                    <div class="row form visual">
	                        <div class="col-12">
	            		        <label>起步价<em class="asterisk"></em></label>
		                        <input id="startPrice" name="startPrice" type="text" placeholder="起步价" style="width: 45.5%;" maxlength="6">&nbsp;&nbsp;元
		                    </div>
		                    <div class="col-12">
		                        <label>里程价<em class="asterisk"></em></label>
		                        <input id="rangePrice" name="rangePrice" type="text" placeholder="里程价" style="width: 45.5%;" maxlength="5">&nbsp;&nbsp;元/公里
		                    </div>
		                    <div class="col-12">
		                        <label>时间价<em class="asterisk"></em></label>
		                        <input id="timePrice" name="timePrice" type="text" placeholder="时间价" style="width: 45.5%;" maxlength="4">&nbsp;&nbsp;元/分钟
		                    </div>
		                </div>
		                <div id="perhourDiv" class="row form col-12 visual">
		                    <label style="width:32.5%">时速<em class="asterisk"></em></label>
		                    <input id="perhour" name="perhour" type="text" placeholder="时速" style="width: 49%;" maxlength="5">&nbsp;&nbsp;公里/小时
		                </div>
		               <div class="row form visual">
		                <div class="col-12">
						       <label>回空里程<em class="asterisk"></em></label>
						       <input name="deadheadmileage" id="deadheadmileage" type="text"  placeholder="回空里程" style="width: 45.5%;" maxlength="5">&nbsp;&nbsp;公里
					    </div>
					    <div class="col-12">
						       <label>回空费价<em class="asterisk"></em></label>
						       <input name="deadheadprice" id="deadheadprice" type="text"  placeholder="回空费价" style="width: 45.5%;" maxlength="5">&nbsp;&nbsp;公里
					    </div>
					    <div class="col-12">
						<label class="account-items">夜间征收时段<em class="asterisk"></em></label>
						<div class="col-4"  style="margin-left:-1%;">
		                    <div class="ztimepicker" id="ztimepicker1">
								<input type="text" readonly  class="ztimepicker_input" name="nightstarttime" id="nightstarttime"style="width:75%;margin-left:-3%;"/><span style="margin-left: 2%;">-</span>
								<div class="ztimebox">
								<div class="znow">时 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分</div>
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-4"style="margin-left:-10%;">
							<div class="ztimepicker"id="ztimepicker2">
								<input type="text" readonly  class="ztimepicker_input" name="nightendtime" id="nightendtime"style="width:75%;"/>
								<div class="ztimebox">
								<div class="znow">时 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分</div>
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
						 <div class="col-12">
					      <label>夜间费价<em class="asterisk"></em></label>
					       <input name="nighteprice" id="nighteprice" type="text"  placeholder="夜间费价" style="width: 45.5%;" maxlength="5">&nbsp;&nbsp;元/公里
				        </div>
					   </div>
	            	</form>
	                <button class="Lbtn orange" onclick="save()">确定</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/individualAccountRules/editRules.js"></script>
	</body>
</html>
