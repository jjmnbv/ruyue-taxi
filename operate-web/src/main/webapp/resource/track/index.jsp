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
		<title>行程数据</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css" />
		<link rel="stylesheet" type="text/css" href="content/css/combotree.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/zTree_v3/css/zTreeStyle/metro.css" />

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
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/js/jquery.combotree.js"></script>
		<script type="text/javascript" src="content/plugins/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="content/plugins/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		
		
		<style type="text/css">
			.form select{width:68%;}
			.select2-container .select2-choice{height:30px;}
			.select2-container{width:68%;padding:0px;}
			.dataTables_wrapper{margin:10px 0px;}
			.breadcrumb{text-decoration:underline;}
			.tabmenu{margin-bottom:10px;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.green_a{
			background:#1E93EE;}
			.orange{
			background:#1E93EE;}
			.grey_q{
			background:#1E93EE;}
			.pull-right {
		    margin-top: 5px;
		    }
			.form-horizontal .control-label{
				padding-top: 10px;
			}
		</style>
	</head>
	<body >
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 行程数据</div>
		<div class="page-content-wrapper">
            <div class="content">
                <div class="page-content-body">
                    <!-- 写具体业务代码 -->
				
					<div class="form-horizontal">
					    <div class="row">
					    <!-- 
					        <div class="col-4">
					            <div class="form-group">
					                <label class="control-label col-4">车辆所属</label>
					                <div class="col-8">
					                    
					                    <select name="selProperty" id="selProperty" class="form-control">
											<option value="">请选择</option>
											<c:forEach items="${propertyList}" var="item">
												<option value="${item.Value}">${item.Text}</option>
											</c:forEach>
										</select>
					                </div>
					            </div>
					        </div>
					        <div class="col-4">
					            <div class="form-group">
					                <label class="control-label col-4">所属部门</label>
					                <div class="col-8">
					                    <input class="form-control" type="text" id="txtDept">
					                </div>
					            </div>
					           
					        </div>
					       --> 
					       
						<div class="col-4">
						 <div class="form-group">
		                	<label class="control-label col-4">服务车企</label>
							 <div class="col-8">
				            <select id="companyId">
				        		<option value="">全部</option>
				        		<c:forEach items="${companyList}" var="item">
									<option value="${item.value}">${item.text}</option>
								</c:forEach>
				        	</select>
							 </div>
               			  </div>
               			 </div>
               			
					        <div class="col-4">
					            <div class="form-group">
					                <label class="control-label col-4">关键字</label>
					                <div class="col-8">
					                    <input class="form-control" type="text" placeholder="车牌、IMEI" id="txtKeyword">
					                </div>
					            </div>
					            <!--/span-->
					        </div>
					        <div class="col-4">
					            <div class="pull-right">
					                <button type="button" class="Mbtn green_a " id="btnSearch">
					                   <img src="img/trafficflux/icon/seacrch.png"/>
					                	查询
					                </button>
					                <!-- <button type="button" class="Mbtn red_q " id="btnAdvancedSearch">高级查询</button> -->
					                <button type="button" class="Mbtn orange" id="exportData" >
					                    <img src="img/trafficflux/icon/export.png"/>                            
					                                                            导出
					                </button>
					            </div>
					        </div>
					
					    </div>
					    <div class="row">
					    <!-- 
					        <div class="col-4">
					            <div class="form-group">
					                <label class="control-label col-4">车系</label>
					                <div class="col-8">
					                    <input type="hidden" style="width:100%" id="selVehcLine" />
					                </div>
					            </div>
					        </div>
					        <div class="col-4">
					            <div class="form-group">
					                <label class="control-label col-4">排量</label>
					                <div class="col-8">
					                    
					                    <select name="selDisplacement" id="selDisplacement" class="form-control">
											<option value="">请选择</option>
											<c:forEach items="${displacementList}" var="item">
												<option value="${item.Value}">${item.Text}</option>
											</c:forEach>
										</select>
					                </div>
					            </div>
					        </div>
					         -->
				
					    </div>
					
					    <div class="row" id="advancedSearchPanel" style="display:none">
					        <div class="col-12">
					            <div class="row">
					                <div class="col-4">
					                    <div class="form-group">
					                        <label class="control-label col-4">总里程(km)</label>
					                        <div class="input-group col-8">
					                            <input class="form-control" id="txtStartMileageSum" name="txtStartMileageSum" type="text" />
					                            <span class="input-group-addon">
					                                到
					                            </span>
					                            <input class="form-control" id="txtEndMileageSum" name="txtEndMileageSum" type="text" />
					                        </div>
					                    </div>
					                </div>
					                <div class="col-4">
					                    <div class="form-group">
					                        <label class="control-label col-4">总行驶时间(h)</label>
					                        <div class="input-group col-8">
					                            <input class="form-control" id="txtStartTimeSum" name="txtStartTimeSum" type="text" />
					                            <span class="input-group-addon">
					                                到
					                            </span>
					                            <input class="form-control" id="txtEndTimeSum" name="txtEndTimeSum" type="text" />
					                        </div>
					                    </div>
					                </div>
					                <div class="col-4">
					                    <div class="form-group">
					                        <label class="control-label col-4">总耗油量(L)</label>
					                        <div class="input-group col-8">
					                            <input class="form-control" id="txtStartOilSum" name="txtStartOilSum" type="text" />
					                            <span class="input-group-addon">
					                                到
					                            </span>
					                            <input class="form-control" id="txtEndOilSum" name="txtEndOilSum" type="text" />
					                        </div>
					                    </div>
					                </div>
					            </div>
					            <div class="row">
					                <div class="col-4">
					                    <div class="form-group">
					                        <label class="control-label col-4">总怠速时间(h)</label>
					                        <div class="input-group col-8">
					                            <input class="form-control" id="txtStartIdleTimeSum" name="txtStartIdleTimeSum" type="text" />
					                            <span class="input-group-addon">
					                                到
					                            </span>
					                            <input class="form-control" id="txtEndIdleTimeSum" name="txtEndIdleTimeSum" type="text" />
					                        </div>
					                    </div>
					                </div>
					                <div class="col-4">
					                    <div class="form-group">
					                        <label class="control-label col-4 ">总行程数(条)</label>
					                        <div class="input-group col-8">
					                            <input class="form-control" id="txtStartTrackSum" name="txtStartTrackSum" type="text" />
					                            <span class="input-group-addon">
					                                到
					                            </span>
					                            <input class="form-control" id="txtEndTrackSum" name="txtEndTrackSum" type="text" />
					                        </div>
					                    </div>
					                </div>
					            </div>
					        </div>
					    </div>
					
					</div>
					
					<div class="row">
					    <div class="col-12">
					        <table class="table table-bordered table-condensed table-striped table-hover" id="dtGrid"></table>
					    </div>
					</div>
                </div>
            </div>
        </div>
		
		
	</body>
	<script type="text/javascript" src="js/track/index.js"></script>
	<script type="text/javascript">
		var basePath = "<%=basePath%>";
	</script>
</html>
