<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.szyciov.util.SystemConfig"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/ztimepicker/ztimepicker.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/cityselect/cityselect.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/css/style.css" />
<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
<link rel="stylesheet" type="text/css" href="css/order/che_xing.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/order/address.css" rel="stylesheet">

<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="content/plugins/ztimepicker/ztimepicker.js"></script>
<script type="text/javascript" src="content/plugins/cityselect/cityselect.js"></script>
<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="content/plugins/select2/app.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>

<style type="text/css">
	.myaddclass{
	  	    position: relative;
	    	top: -14px;
	}
	.myaddclass .cityinput{
	  	border: 0px;
	    position: absolute;
	    top: 13px;
	}
	.select_box{position:relative;}
	.select_box label.error,  label[for="passengerPhone"]{color:#f33333;position:absolute;right:0px;top:-26px;}
	/* input[type=text] {line-height: 0px;padding:0px;} */
	.textarea_box label.error{color:#f33333;position:absolute;right:0px;top:-76px;}
	.pop_y{    position: absolute;height: 1035px;width: 653px;bottom: 0px;left: 6px;background: rgba(255,255,255,0.6);z-index: 200;}
	th, td { white-space: nowrap; }
	div.dataTables_wrapper {
		width: $(window).width();
		margin: 0 auto;
	}
	.DTFC_ScrollWrapper{
	margin-top:-20px;
	}
    .form_yueche .col-4-label{
        width: 14.5% !important;
    }
    #cancelDetail table{
        width: 100%;
    }
    #cancelDetail td{
        padding-right: 18px;
        padding-bottom: 5px;
        color:#9e9e9e;
    }

    @media screen and (max-width: 1240px) {
        .form_yueche .col-4-label {
            width: 23% !important;
        }
    }
    @media screen and (max-width: 800px) {
        .form_yueche .col-4-label {
            width: 21% !important;
        }
    }

    @media screen and (min-width: 1241px) {
        .form_yueche .col-12-label {
            width: 13.8% !important;
        }
    }

    @media screen and (max-width: 1240px) and (min-width: 800px) {
        .form_yueche .col-12-label {
            width: 22% !important;
        }
    }

</style>