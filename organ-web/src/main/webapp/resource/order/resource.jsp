<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<link rel="stylesheet" type="text/css" href="content/css/shouyelogoutdialog.css" />
<link rel="stylesheet" type="text/css" href="content/css/common.css" />
<link rel="stylesheet" type="text/css" href="content/css/yueche.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/ztimepicker/ztimepicker.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/cityselect/cityselect.css" />

<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="content/plugins/ztimepicker/ztimepicker.js"></script>
<script type="text/javascript" src="content/plugins/cityselect/cityselect.js"></script>
<script type="text/javascript" src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
<!--   <script type="text/javascript" src="content/js/yueche.js"  charset="utf-8"></script> -->
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<style type="text/css">
	.tangram-suggestion-main{
		z-index:99999 !important;
	}
	.myaddclass{
	  	    position: relative;
	    	top: -14px;
	        -ms-top:0px;
	}
	.myaddclass .cityinput{
	  	border: 0px;
	    position: absolute;
	    top: 13px;
	}
	
	.myaddclass+.wei2{
		-ms-top:-27px;
	}
	.select_box{position:relative;}
	.select_box label.error,  label[for="passengerPhone"]{color:#f33333;position:absolute;right:0px;top:-26px;}
	input[type=text] {line-height: 0px;padding:0px;}
	.pop_y{    position: absolute;height: 1035px;width: 653px;bottom: 0px;left: 6px;background: rgba(255,255,255,0.6);z-index: 200;}
	th, td { white-space: nowrap; }
	div.dataTables_wrapper {
		width: $(window).width();
		margin: 0 auto;
	}
	.DTFC_ScrollWrapper{
	margin-top:-20px;
	}

    .cityinput {
        width: 72px;
        height: 42px;
        padding: 10px !important;
    }
</style>