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
		<title>品牌车系管理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
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
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.fileupload.js"></script>
		<script type="text/javascript" src="content/plugins/letterselect/letterselect.js"></script>
		<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		
		.tip_box_b input[type=text]{width: 63%;}
		.con{text-align:left;开始}
		
		th, td { white-space: nowrap; }
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
		margin-top:-20px;
		}
		.tip_box_b label.error {margin-left: 31%!important;}
		</style>
	</head>
	<body>
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 品牌车系管理
			<button class="SSbtn green_q back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-4">
						<label style="line-height:30px;float: left;">品牌车系</label>
						 <input id="key" name="key" type="text" placeholder="全部" style="line-height: 20px;float: left;width: 70%">
					</div>
					<div class="col-8" style="text-align: right;">
						<button class="Mbtn red_q" onclick="search();">查询</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<h4>品牌车系信息</h4>
				</div>
				<div class="col-8" style="text-align: right;">
					<button class="Mbtn blue_q" onclick="downLoad()">下载模板</button>
					<button class="Mbtn blue_q" onclick="importExcel()">批量导入</button>
					<%-- <a href="<%=SystemConfig.getSystemProperty("fileserver")%>/group1/M00/00/01/CgAAGVfRE1-Ad7VlAABMAGR4yC4457.xls">下载excel模板</a> --%>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="titleForm">新增车系</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div>
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<div class="row">
	            			<div class="col-12">
			            		<label>车系名称<em class="asterisk"></em></label>
				                <input id="name" name="name" type="text" placeholder="必填" maxlength="8"><br>
		                	</div>
		                </div>
		                <div class="row">
	            			<div class="col-12">
				                <label>品牌归属<em class="asterisk"></em></label>
				                <!-- <input id="vehcBrandID" name="vehcBrandID" type="text" placeholder="必填" style="width: 63%;"><br> -->
				                <input type="hidden" id="vehcBrandID" name="vehcBrandID" value="${orgOrgan.city}">
								<div id="inp_box1" style="width: 62.5%">
									<input type="text" id="vehcBrandIDName" style="width: 100%" value="${orgOrgan.citycaption}" onfocus="getSelectCity();" readonly="readonly">
								</div> 
			                </div>
		                </div>
	            	</form>
	                <button class="Lbtn red_q" onclick="save()">完成</button>
	                <button class="Lbtn grey_w" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<div class="pop_box" id="importExcelDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="importExcel">批量导入车系</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="row">
           			<div class="col-12">
	            		提示：请确保文件的格式与批量导入车系excel模板的格式一致
                	</div>
                </div>
	          	<div class="row">
           			<div class="col-12">
		            	<label style="text-align: left;margin-left: -187px;">上传文件<em class="asterisk"></em></label>
		                <input id="importfile" name="importfile" type="file" multiple style="margin-top: -21px;margin-left: 139px;">
	          		</div>
                </div>
                <div class="row">
           			<div class="col-12">
		            	 <button class="Lbtn red" onclick="canel()">关闭</button>
	          		</div>
                </div>
	        </div>
		</div>
		
		<div class="kongjian_list" id="kongjian_list">
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
		</div>
		<!-- 导入 -->
		<div class="pop_box" id="importExcelDiv1" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="importExcel">提示</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="row">
	            	<div class="col-10" >
	            		<div id="importExcelDiv2" style="margin-left : 100px; text-align: left;overflow-y: auto;height: 200px;">
	            		
	            		</div>
	            	</div>
	            </div>
	            <div class="row">
	            	<div class="col-10" style="text-align: right;">
	            		<button class="Mbtn red"  onclick="canel1()">确定</button>
	            	</div>
	            </div>
	        </div>
		</div>
		<script type="text/javascript" src="js/pubVehcline/index.js"></script>
		<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
			function importExcel(){
				$("#importExcelDiv").show();
			}
			$('#importfile').fileupload({
				url:"PubVehcline/ImportExcel",
			    dataType: 'json',
			    done: function(e, data) {
			    	if (data.result.ResultSign == "Successful") {
						var message = data.result.MessageKey == null ? data : data.result.MessageKey;
		            	$("#importExcelDiv1").show();
		            	$("#importExcelDiv2").html("以下车辆导入失败：<br>"+message);
		            	dataGrid._fnReDraw();
					} else if(data.result.ResultSign == "Error"){
						var message = data.result.MessageKey == null ? data : data.result.MessageKey;
		            	toastr.error(message, "提示");
		            	dataGrid._fnReDraw();
					}else{
						//var message = data.result.MessageKey == null ? data : data.result.MessageKey;
						$("#importExcelDiv1").show();
		            	$("#importExcelDiv2").html("导入成功");
		            	dataGrid._fnReDraw();
					}
			    }
			});
			function canel1(){
				$("#importExcelDiv1").hide();
			}
		</script>
	</body>
</html>
