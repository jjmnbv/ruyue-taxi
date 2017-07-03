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
		<title>车辆品牌管理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
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
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script src="content/plugins/fileupload/jquery.fileupload.js"></script>
		
		<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		
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
		#fileupload:focus {outline:none;}
		</style>
	</head>
	<body>
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 车辆品牌管理
			<button class="SSbtn green_q back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-4" style="text-align: left;">
						<label style="line-height:30px;float: left;">品牌名称</label>
						<input type="text" id="key" name="key" placeholder="全部" value="" style="line-height: 20px;float: left;width: 70%"/>
						<input type="hidden" id="keys" name="keys" placeholder="全部" value=""/>
					</div>
					<div class="col-8" style="text-align: right;">
						<button class="Mbtn red_q" onclick="search();" >查询</button>
						<!-- &nbsp;&nbsp;&nbsp;&nbsp;
						<button class="Mbtn grey_b" onclick="cancel();" >取消</button> -->
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<h4>车辆品牌信息</h4>
				</div>
				<div class="col-8" style="text-align: right;">
				<button class="Mbtn blue_q" onclick="exportData()">导出数据</button>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="titleForm">新增品牌</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<div class="row">
	            			<div class="col-12">
	            				<label>品牌名称<em class="asterisk"></em></label>
		               	 		<input id="name" name="name" type="text" placeholder="必填" maxlength="8"><br>
	            			</div>
	            		</div>
	            		<!-- <div class="row">
	            			<div class="col-12">
				                <label style="margin-left: -164px;">品牌logo<em class="asterisk"></em></label>
				                <input id="logo" name="logo" type="hidden" placeholder="建议图片尺寸为120x120px，图片格式为.png">
				                <input id="fileupload" type="file" name="file" multiple style="display: none;"/>
				                <a class="SSbtn grey" href="javascript:void(0)" id="tihuan" style="text-decoration: none;color: #fff;">选择上传文件</a>
							</div>
	            		</div> -->
	            		<div class="row">
	            			<div class="col-12">
			                	<label>品牌图标<em class="asterisk"></em></label>
				                <input id="logo" name="logo" type="hidden" placeholder="建议图片尺寸为120x120px，图片格式为.png">
				                <!-- <input id="fileupload" type="file" name="file" multiple style="display: none;"/>
		               			<a class="SSbtn grey" href="javascript:void(0)" id="tihuan" style="text-decoration: none;color: #fff;">选择上传文件</a> -->
		               			<div style="width: 63%; height: 50px;position: relative;float:right;">
							   		<img src="content/img/ing_tupian.png" style="width: 50px; height: 50px;float: left;" id="imgback">
	                				<input id="fileupload" type="file" style="position: relative;width: 50px; height: 50px;opacity:0;top:0px;left:-50px;font-size: 15px;" name="file" multiple/>
                					<a id="clear" style="left:-103px;position: relative;" href="javascript:void(0)"><font color="red">删除</font></a>
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
	                <button class="Lbtn red_q" onclick="save()">完成</button>
	                <button class="Lbtn grey_w" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/pubVehcbrand/index.js"></script>
		<script type="text/javascript">
		var serviceAddress = "<%=SystemConfig.getSystemProperty("fileserver")%>";
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		$('#fileupload').fileupload({
			url:"PubVehcbrand/UploadFile",
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
		$("#tihuan").click(function(){
			$("#fileupload").click();
		});
		$("#imgback").click(function(){
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
