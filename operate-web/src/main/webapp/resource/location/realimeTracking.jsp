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
		<title>实时追踪</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
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
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=AFLc9FVyIHUWExKYFETDeF6T"></script>
		
		<script type="text/javascript" src="js/location/realtimeTracking.js?v=1.6"></script>
														
		<style type="text/css">
			 #map_canvas {
        width: 100%;
        height: 600px;
        overflow: hidden;
        margin: 0;
    }

    .vehc-list {
        list-style: none;
        padding: 0;
        margin: 0;
    }

        .vehc-list > li {
            position: relative;
            padding: 10px 5px;
            border-bottom: 1px solid #eaeaea;
        }

        .vehc-list li.last-line {
            border-bottom: none;
        }

        .vehc-list li > .vehc-bell {
            margin-left: 10px;
        }

        .vehc-list li > .vehc-icon {
            float: left;
            width: 30px;
            padding-left: 5px;
        }

        .vehc-list li > .vehc-title {
            margin-right: 0px;
        }

            .vehc-list li > .vehc-title .vehc-status {
                margin-left: 15px;
            }

        .vehc-list li > .run {
            color: #28b779;
        }

        .vehc-list li > .stop {
            color: #333333;
        }

        .vehc-list li > .offline {
            color: #ffb848;
        }

        .vehc-list li .vehc-title .vehc-title-sp {
            margin-right: 5px;
        }

        .vehc-list li.vehc-done .vehc-title-sp {
            text-decoration: line-through;
        }

        .vehc-list li.vehc-done {
            background: #f6f6f6;
        }

            .vehc-list li.vehc-done:hover {
                background: #f4f4f4;
            }

        .vehc-list li:hover {
            background: #f9f9f9;
        }

    .ListStyle li {
        margin-bottom: 15px;
        margin-top: 10px;
        margin-left: 9px;
    }

    .util-btn-group-margin-bottom-5 .btn-groupnew {
        margin-bottom: 5px !important;
    }

    .btn-groupnew, .btn-group-vertical {
        position: relative;
        display: inline-block;
        vertical-align: middle;
    }

    #dDiv > .dropdown-menu > li > a {
        padding: 0px 3px 3px 3px;
        color: #ffffff;
        text-decoration: none;
        display: block;
        clear: both;
        font-weight: normal;
        line-height: 18px;
        white-space: nowrap;
        width: 77px;
        margin: 5px auto;
        height: 20px;
    }

    #dDiv > .dropdown-menu {
        min-width: 90px;
    }

    #dDiv > .dropdown-menu .pull-right {
        right: 10px;
        left: auto;
    }
    img{max-width:none;}
    p {
    margin: 0 0 10px;
}

   .BMapLabel{
        	color:green !important;
        	max-width:none;
        }
.blue{
	background: #4d90fe;
	}
	.btn.btn-primary:hover{
		background-color:#0362FD;
	}
	.btn.btn-xs.blue{color:#fff;}
		</style>
		<script type="text/javascript">
		var basePath="<%=basePath%>";
		</script>
	</head>
	<body style="height:auto; overflow-y:scroll">
		
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 实时追踪</div>
		<div class="content">
			
					<div class="row margin-top-10">
					    <div class="col-md-10" style="width:80%;">
					        <div id="map_canvas">
					        </div>
					    </div>
					    <div class="col-md-2" style="width:20%;">
					        <div class="row margin-top-10">
					            <div class="col-md-12">
					                <input type="hidden" style="width:100%" id="selPlates" />
					            </div>
					        </div>
					        <div class="row margin-top-10">
					            <div class="col-md-12">
					                <button type="button" class="btn btn-primary  blue pull-right" id="btnSearch"><img src="img/trafficflux/icon/add.png" alt="" />添加</button>
					            </div>
					        </div>
					        <div class="row margin-top-10">
					            <div class="col-md-12">
					                <div class="portlet">
					                    <div class="portlet-title">
					                        <div class="caption">
					                            <i class="fa fa-check"></i>车辆:<span id="spVehcCount">0</span>
					                        </div>
					                    </div>
					                    <div class="portlet-body">
					                        <div class="vehc-content">
					                            <div class="scroller" style="height: 440px;" data-always-visible="1" data-rail-visible1="1">
					                                <!-- START vehc LIST -->
					                                <ul class="vehc-list"></ul>
					                                <!-- END START vehc LIST -->
					                            </div>
					                        </div>
					                    </div>
					                </div>
					            </div>
					        </div>
					    </div>
					</div>
					
					<!-- 时间栅栏  -->
					<div class="modal fade" id="mdTimeFenceSet" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" style="min-height:350px">
					    <div class="modal-dialog" style="min-width:700px;">
					        <div class="modal-content">
					            <form id="frmmodal" action="#" class="form-horizontal" role="form">
					                <div class="modal-header">
					                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					                    <h4 class="modal-title">设置时间栅栏<span style="background-color:#3fbcfb;" id="TimeFenceSetInfo"></span></h4>
					                    <input type="hidden" id="timeV_ID" />
					                </div>
					                <div class="modal-body" style="min-width:400px;max-height: 400px; overflow-x: hidden; overflow-y: auto;">
					                    <div class="row">
					                        <div class="col-md-12">
					                            <table class="table table-bordered table-condensed table-striped table-hover" id="dtGridTimeFenceSet"></table>
					                        </div>
					                    </div>
					                </div>
					                <div class="modal-footer">
					                    <div class="pull-right" id="footer">
					                        <button type="button" class="btn blue" onclick="AddTimeFaceToVehc()"><i class="fa fa-pencil"></i> 保存</button>
					                        <button type="button" class="btn default" data-dismiss="modal"><img src="Content/img/icon_gallery/shutdown_ga.png" /> 关闭</button>
					                    </div>
					                </div>
					            </form>
					        </div>
					    </div>
					</div>
					
					<!-- 区域栅栏  -->
					<div class="modal fade" id="mdAreaFenceSet" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" style="min-height:350px">
					    <div class="modal-dialog" style="min-width:700px;">
					        <div class="modal-content">
					            <form id="frmmodal" action="#" class="form-horizontal" role="form">
					                <div class="modal-header">
					                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					                    <h4 class="modal-title">设置区域栅栏<span style="background-color:#3fbcfb;" id="AreaFenceSetInfo"></span></h4>
					                    <input type="hidden" id="areaV_ID" />
					                </div>
					                <div class="modal-body" style="min-width: 400px; max-height: 400px; overflow-x: hidden; overflow-y: auto;">
					                    <div class="row">
					                        <div class="col-md-12">
					                            <table class="table table-bordered table-condensed table-striped table-hover" id="dtGridAreaFenceSet"></table>
					                        </div>
					                    </div>
					                </div>
					                <div class="modal-footer">
					                    <div class="pull-right" id="footer">
					                        <button type="button" class="btn blue" onclick="AddAreaFaceToVehc()"><i class="fa fa-pencil"></i> 保存</button>
					                        <button type="button" class="btn default" data-dismiss="modal"><img src="Content/img/icon_gallery/shutdown_ga.png" /> 关闭</button>
					                    </div>
					                </div>
					            </form>
					        </div>
					    </div>
					</div>
					
					<!-- 电子围栏  -->
					<div class="modal fade" id="mdElectronFenceSet" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" style="min-height:350px">
					    <div class="modal-dialog" style="min-width:200px;">
					        <div class="modal-content">
					            <form id="frmmodal" action="#" class="form-horizontal" role="form">
					                <div class="modal-header">
					                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					                    <h4 class="modal-title">设置电子围栏<span style="background-color:#3fbcfb;" id="ElectronFenceSetInfo"></span></h4>
					                    <input type="hidden" id="EfV_ID" />
					                </div>
					                <div class="modal-body" style="min-width: 150px; max-height: 400px; overflow-x: hidden; overflow-y: auto;">
					                    <div class="row">
					                        <div class="col-md-12">
					                            <table class="table table-bordered table-condensed table-striped table-hover" id="dtGridElectronFenceSet"></table>
					                        </div>
					                    </div>
					                </div>
					                <div class="modal-footer">
					                    <div class="pull-right" id="footer">
					                        <button type="button" class="btn blue" onclick="AddElectronFaceToVehc()"><i class="fa fa-pencil"></i> 保存</button>
					                        <button type="button" class="btn default" data-dismiss="modal"><img src="Content/img/icon_gallery/shutdown_ga.png" /> 关闭</button>
					                    </div>
					                </div>
					            </form>
					        </div>
					    </div>
					</div>
		
	</body>
		<script type="text/javascript">
	var _loading = function () {
	    var loading = $('<div class="loadingdiv">').appendTo($(document.body));;
	   // <img src="img/trafficflux/ajax-modal-loading.gif" alt="图片加载中···" /></div>
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
	</script>
</html>
