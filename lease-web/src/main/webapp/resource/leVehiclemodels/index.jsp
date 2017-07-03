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
		<title>服务车型</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="css/leVehiclemodels/index.css" />
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
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.fileupload.js"></script>
		<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		.cantainer #content {padding-bottom:40px;border: 1px solid #ccc;}
		.tip_box_b input[type=text]{width: 63%;}
		
		th, td { white-space: nowrap; }
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
		margin-top:-20px;
		}
		.tip_box_b label.error {margin-left: 30%!important;}
		.tip_box_b input[type=text] {
		    width: 65%;
		}
		#fileupload:focus {outline:none;}
		
		.cantainer #content .box .right .right_content .right_list .item_head .minus{
			position:relative;
			line-height:9px;
			cursor:hand;
			cursor:pointer;
		}
		.cantainer #content .box .right .right_content .right_list .item img{
			top:-1px;
		}
		.cantainer #content .box .right .right_content .right_list .item_head img{
			top:0;
		}
		.cantainer #content .box .right .right_content .right_list .right_items div input{
			top:1px;
		}
		.cantainer #content .box .right .right_content .right_list .item .label{
			padding-left:0;
		}
		.cantainer #content {
			z-index:100;
		}
		@media screen and (min-width: 790px) and (max-width: 1100px) {
			.cantainer #content .box{
				padding:0 10px;
			}
			.cantainer #content{
				width:810px;
				position: absolute;
    			left: 50%;
    			transform: translateX(-405px);
			}
		}
		.logo-type{
		    margin-left: -66%;
			margin-left:-48%\9\0 !important;
		}
		</style>
	</head>
	<body>
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 服务车型
			<button class="SSbtn green_q back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				
			</div> 
			<div class="row">
				<div class="col-12">
					<h4>服务车型信息</h4>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b" style="width:500px;">
	            <h3 id="titleForm">新增服务车型</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<div class="row">
	            			<div class="col-12">
			            		<label>车型名称<em class="asterisk"></em></label>
				                <input id="name" name="name" type="text" placeholder="车型名称必须唯一" maxlength="8">
		                	</div>
	            		</div>
	            		<div class="row">
	            			<div class="col-12">
				                <label>车型级别<em class="asterisk"></em></label>
				                <input id="level" name="level" type="text" placeholder="请填写>0的整数，数值越大，级别越高" maxlength="6">
		                	</div>
	            		</div>
	            		<div class="row">
	            			<div class="col-12">
			                	<label>车型图标<em class="asterisk"></em></label>
				                <input id="logo" name="logo" type="hidden" placeholder="建议图片尺寸为120x120px，图片格式为.png">
				                <!-- <input id="fileupload" type="file" name="file" multiple style="display: none;"/>
		               			<a class="SSbtn grey" href="javascript:void(0)" id="tihuan" style="text-decoration: none;color: #fff;">选择上传文件</a> -->
		               			<div style="width: 63%; height: 50px;position: relative;float:right;">
							   		<img src="content/img/ing_tupian.png" style="width: 50px; height: 50px;float: left;" id="imgback">
	                				<input id="fileupload" type="file" style="position: relative;width: 50px; height: 50px;opacity:0;top:0px;left:-50px;" name="file" multiple/>
                					<a id="clear" href="javascript:void(0)" style="left:-103px;position: relative;">删除</a>
                				</div>
		               		</div>
	            		</div>
	            		<!-- <div class="row">
	            			<div class="col-12">
				                <div id="imgShow" style="display: none; width: 100px;height: 100px;margin: auto;">
					                <img style="background-color:gray;" id="imgback"></img>
									<input id="clear" type="button" name="clear" value="删除" style="width:60px;height: 40px;"/>
									<a id="clear" href="javascript:void(0)">删除</a>
								</div>
							</div>
	            		</div> -->
	            	</form>
	                <button class="Lbtn red" onclick="save()">确定</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<!--容器开始-->
		<div class="pop_box" id="allocationLinePop" style="display: none;width:100%;height:100%;background:rgba(0,0,0,0.5)">
			<div class="cantainer">
				<!--主要内容开始-->
				<div id="content" class="tip_box_e">
					<div class="title" style="text-align: center;">
						<p>车系分配【<span style="color: red;">当前车型名称：<span id="currentVel">
							</span></span>】
							<img src="content/img/btn_guanbi.png" class="close" alt="关闭" style="margin-right: 10px;">
						</p>
					</div>
					<div class="box">
						<div class="box_title" style="height: 20px;">
							<!-- <span>当前车型名称：<span id="currentVel"></span></span> -->
						</div>
						<div class="left">
							<p class="left_title">选择品牌</p>
							<div class="left_content">
								<div class="list">
									<!-- <div class="items">
										<span class="item all_brand" id="all_brand">全部</span>
									</div> -->
									<div class="type">ABCDE</div>
									<div class="items" id="items">
										
									</div>
									<div class="type">FGHIJ</div>
									<div class="items"  id="items">
										
									</div>
									<div class="type">KLMNO</div>
									<div class="items"  id="items">
										
									</div>
									<div class="type">PQRST</div>
									<div class="items"  id="items">
										
									</div>
									<div class="type">UVWXYZ</div>
									<div class="items"  id="items">
										
									</div>
								</div>
							</div>
						</div>
						<div class="right">
							<p class="right_title">选择车系</p>
							<div class="right_content" id="right_content">
								<div class="head">
									<label>
										<input onclick="checkAll()" type="checkbox" name="quanxuan" class="fl"/><span class="fl">全选</span>
									</label>
									<label>
										<input onclick="unCheckAll()" type="checkbox" name="fanxuan" class="fl"/><span class="fl">反选</span>
									</label>
								</div>
								<div class="right_list">
									<div class="right_items"> 
										
									</div>
									<div class="item_head">
										<span class="minus">-</span>
										<input type="checkbox" name="checkbox"/>
										<img class="icon" src="img/leVehiclemodels/u274.png" />
										<span>奥迪</span> 
									</div>
									<div class="item right_item">
										<label class="label">
											<input type="checkbox" name="checkbox" disabled="disabled" name="checkbox"/>
											<img class="icon" src="img/leVehiclemodels/u272.png" />
											<span>奥迪 -</span>
											<span class="item_series"> A4L</span>
										</label>
										<i class="item_type"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="footer" style="margin:0 auto; width:350px;"> 
							<div class="cancel">取消</div>
							<div class="sure">确定</div>
						</div>
					</div>
				</div> 
				<!--主要内容结束-->
			</div>
		</div>
		<script type="text/javascript" src="js/leVehiclemodels/index.js"></script>
		<script type="text/javascript" src="js/leVehiclemodels/fenpei.js"></script>
		<script type="text/javascript">
		var serviceAddress = "<%=SystemConfig.getSystemProperty("fileserver")%>";
		$('#fileupload').fileupload({
			url:"LeVehiclemodels/UploadFile",
		    dataType: 'json',
		    done: function(e, data) {
		        if(data.result.status=="success"){
		        	$("#imgShow").show();
		        	$("#imgback").attr("src",data.result.basepath+"/"+data.result.message[0]);
		        	$("#logo").val(data.result.message[0]);
		        }else{
		        	toastr.error(data.result.error, "提示");
		        }
		    }
		});
		$("#imgback").click(function(){
			$("#fileupload").click();
		});
		$("#tihuan").click(function(){
			$("#fileupload").click();
		});
		$("#clear").click(function(){
			$("#fileupload").val("");
			$("#imgback").attr("src","content/img/ing_tupian.png");
			$("#imgShow").hide();
			$("#logo").val("");
		});
		</script>
	</body>
</html>
