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
		<title>断电处理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
			<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css" />
		
		<link href="content/css/style_chewutong.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>

		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/js/jquery.combotree.js"></script>
				
		
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="js/alarmPowerFailure/index.js"></script>
		<style type="text/css">
		.btn.blue {
		    color: white;
		    text-shadow: none;
		    background-color: #4d90fe;
		   
		}
		
		 .glyphicon-arrow-left::before {
   		 content: "<";
		}
		.glyphicon-arrow-right::before {
   		 content: ">";
		}
			.form-horizontal .control-label{
				padding-top: 10px;
			}

		</style>
	</head>
	<body style="overflow:hidden;">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 断电处理
			
		</div>
		<div class="content">

					
					<!-- 查询  -->
					<div class="form-horizontal">
					    <div class="row">
					    	<div class="col-4" >
						 <div class="form-group">
		                	<label class="control-label col-4">服务车企</label>
							 <div class="col-8">
				            <select id="company">
				        		<option value="">全部</option>
				        		<c:forEach items="${opUserCompany }" var="state">
									<option value="${state.value }">${state.text }</option>
								</c:forEach>
				        	</select>
							 </div>
               			  </div>
               			 </div>
					    	
					    	<div class="col-4">
								<div class="form-group">
									<label class="control-label col-4">报警时间(起)</label>
									<div class="col-8">
										<input type="text" class="form-control  datetimepicker "
											name="alarmTime" id="alarmTime" readonly="readonly" />
									</div>
								</div>
							</div>

							<div class="col-4">
								<div class="form-group">
									<label class="control-label col-4"> 报警时间(止) </label>
									<div class="col-8">
										<input type="text" class="form-control  datetimepicker"
											name="alarmTimeStop" id="alarmTimeStop" readonly="readonly" />
									</div>
								</div>
							</div>
					        
					    </div>
					    <div class="row">
					       
							<div class="col-4">
								<div class="form-group">
									<label class="control-label col-4">车牌</label>
									<div class="col-md-8">
										<input type="text" class="form-control" name="plate"
											id="plate" />
									</div>
								</div>
							</div>
							
							<div class="col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">设备IMEI</label>
									<div class="col-md-8">
										<input type="text" class="form-control" name="imei"
											id="imei" />
									</div>
								</div>
							</div>
							
							<div class="col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4 ">处理状态</label>
									<div class="col-md-8">
										<select class="form-control" id="processingState" name="processingState" style="height: 30px;padding-top: 5px;">
											<option value="">请选择</option>
											<c:forEach items="${stateList }" var="state">
												<option value="${state.Value }">${state.Text }</option>
											</c:forEach>	
											
										</select>
									</div>
								</div>
							</div>
							
							
					    </div>
					    
					    <div  class="row">
							
							<div class="col-md-12">
								<div class="pull-right">
									<button type="button" class="btn btn-default blue "
										id="btnSearch" >
										<img src="img/trafficflux/icon/seacrch.png" alt="" />查询
									</button>
									
									<button type="button" class="btn btn-default blue"
										id="btnClear" onclick="onClear() ">
										<img src="img/trafficflux/icon/refresh.png" alt="" />重置
									</button>
									<button type="button" class="btn btn-default  blue"
										id="btnExport">
										<img src="img/trafficflux/icon/export.png" />导出
									</button>
								</div>
							</div>
							
					    </div>
					    
					    <div class="row">
					        <div class="col-md-12">
					            <table class="table table-bordered table-condensed table-striped table-hover" id="dtGrid"></table>
					        </div>
					    </div>
					
					</div>
				
					<!--处理弹出  -->
					<div class="modal fade" id="mdAdd" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" style="min-height:350px">
					    <div class="modal-dialog">
					        <div class="modal-content">
					            <form id="frmmodal" action="#" class="form-horizontal" role="form">
					                <div class="modal-header">
					                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					                    <h4 class="modal-title">断电报警处理</h4>
					                    <input class="form-control" id="id" type="hidden" />
					                </div>
					                <div class="modal-body">
					                    <div class="form-group">
					                        <label class="control-label col-md-3">断电原因<span class="required">*</span></label>
					                        <div class="col-md-8">
					                            <select class="form-control " id="outageReason" style="width:100%" name="outageReason" required="required">
					                            	<option value="">请选择</option>
					                            	
					                            		<c:forEach items="${outageReasons }" var="outageReason">
					                            			<option value="${ outageReason.Value}">${ outageReason.Text}</option>
					                            		</c:forEach>
					                            	
					                            </select>
					                        </div>
					                    </div>

					                    <div class="form-group">
					                        <label class="control-label col-md-3">
					                            备注信息
					                        </label>
					                        <div class="col-md-8">
					                            <textarea name="remarks" class="form-control" id="remarks" maxlength="100" rows="3" placeholder="最多输入100个字备注说明"></textarea>
					                        </div>
					                    </div>
					                </div>
					                <div class="modal-footer">
					                    <div class="pull-right">
					                        <button type="submit" class="btn blue"><i class="fa fa-pencil"></i> 提交</button>
					                        <button type="button" class="btn default" data-dismiss="modal"><img src="img/trafficflux/icon/shutdown_ga.png" /> 取消</button>
					                    </div>
					                </div>
					            </form>
					        </div>
					    </div>
					</div>
				
				
				
               
    



	</body>
		<script type="text/javascript">
		var basePath="<%=basePath%>"; 
	    var _loading = function () {
			    var loading = $('<div class="loadingdiv">').appendTo($(document.body));;
			    //<img src="img/trafficflux/ajax-modal-loading.gif" alt="图片加载中···" /></div>
			    return {
			        show: function () {
			            //div占满整个页面
			            loading.css("width", "100%");
			            loading.css("display", "block");
			            loading.css("height", $(window).height() + $(window).scrollTop());
			            //设置图片居中
			            $('img', loading).css("display", "block");
			            $('img', loading).css("left", ($(window).width() - 88) / 2);
			            $('img', loading).css("top", ($(window).height() + $(window).scrollTop()) / 2);
			        },
			        hide: function () {
			            loading.css("width", "0");
			            loading.css("display", "none");
			            loading.css("height", "0");
			            //设置图片隐藏
			            $('img', loading).css("display", "none");
			        }
			    };
			}();
			
		var company;
		
		
	</script>
</html>
