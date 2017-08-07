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
		<link rel="stylesheet" type="text/css" href="css/orgordermanage/orderdetail.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.skinFlat.css" id="styleSrc"/>
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
		<script type="text/javascript" src="content/plugins/ion.rangeslider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<style type="text/css">
			.form select{width:68%;}
			.breadcrumb{text-decoration:underline;}
			.quantext{position: relative;z-index: 2!important;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.DTFC_ScrollWrapper{
				margin-top:-20px;
				height: auto!important;
			}
			#orderCostTable td{text-align: center;}
			label.error[for='remark']{margin-left: 0px!important;padding-left: 0px!important;}
			/*样式增加*/
			#commentDataGrid{
				border:none;
     			margin:0!important;
				border-collapse:separate;
  				border-spacing:0 10px;
			}
			#commentDataGrid_wrapper .col-sm-12{
				padding-top:0;
				padding-bottom:0;
			}
			#commentDataGrid_wrapper .col-sm-6{
				display: none;
			}
			#commentDataGrid .sorting_disabled{
				display:none;
			}
			#commentDataGrid td{
				padding:0;
				border-top:none;
				border: 1px solid #ddd;
				
			}
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="content" style="overflow: auto !important;">
			<ul class="tabbox">
				<li style="display:block">
					<div class="col-8">
						<div id="map_canvas" style="width: 100%; height: 500px; border: #ccc solid 1px;"></div>
					    <div><span style="color: red;">绿线表示APPGPS轨迹，蓝线表示OBDGPS轨迹</span></div>
                    </div>
				</li>
			</ul>
		</div>
		<script type="text/javascript">
			var orderObj = {
				orderno: "<%=request.getParameter("orderno")%>"
			};
		</script>
		<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>&s=1"></script>
		<script type="text/javascript" src="js/orgordermanage/gps.js"></script>
	</body>
</html>
