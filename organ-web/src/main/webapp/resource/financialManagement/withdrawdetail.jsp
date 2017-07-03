<%@ page contentType="text/html; charset=UTF-8"
		import="com.szyciov.util.SystemConfig"%>
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
  <meta name="description" content="">
  <meta name="keywords" content="">
  <title>账户提现</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zstyle.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zsingle.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
</head>
<style>
	.new_rule_box table{
		color:#333;
		font-size:14px!important;
	}
	
	.new_rule_b .t{
		width:70px;
		color:#999;
	}
</style>
<body>
<div class="rule_box">
    <div class="crumbs">财务管理 > 账户管理 > 提现</div>
    <!-- <button class="btn_green" tyle="float: right; width: 60px; height: 27px; line-height: 27px; margin-top: -4px; margin-right: 30px;">返回</button> -->
    <a class="btn_green" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -41px;margin-right: 30px;" href="javascript:void(0);" onClick="callBack();">返回</a>
    <div class="new_rule_box">
        <div class="new_rule_b" style="height:500px;">
            <table style="width:initial;">
                <tr>
                    <td colspan="2" class="t" style="font-size:24px;color:#333;text-align:left;text-indent: 12px;">提现申请成功</td>
                </tr>
                
                <tr>
                    <td colspan="2" style="text-indent:12px;color:#999">已提交银行处理，预计3-5个工作日可以到账</td>
                </tr>
                <tr style="height:10px;">
                    <td class="t"></td>
                    <td "></td>
                </tr>
                <tr>
                    <td class="t">提现编号</td>
                    <td style="width:500px;">${withdrawInfo.uuid}</td>
                </tr>
                
                <tr>
                    <td class="t">账户归属</td>
                    <td>${withdrawInfo.shortname}</td>
                </tr>
                
                <tr>
                    <td class="t">提现金额</td>
                    <td><span class="font_red" style="color:#f33;">${withdrawInfo.amount}</span>元</td>
                </tr>
      			<tr style="height:30px;">
                    <td class="t"></td>
                    <td "></td>
                </tr>
                <tr>
                    <td class="t">收款账户</td>
                    <td>${withdrawInfo.creditcardnum}</td>
                </tr>
                
                <tr>
                    <td class="t">开户名称</td>
                    <td>${withdrawInfo.creditcardname}</td>
                </tr>
                
                <tr>
                    <td class="t">开户银行</td>
                    <td>${withdrawInfo.bankname}</td>
                </tr>
            </table>
            
            <button class="btn_red" id="withdrawButton" style="float: left;margin-top: 70px;margin-right: 100px;width:175px;height:40px;font-size:15px;margin-left: 17px;" onclick="save();">完成</button>
        </div>
    </div>
</div>
<script type="text/javascript">
var base = document.getElementsByTagName("base")[0].getAttribute("href");
var leasesCompanyId = "${leasesCompanyId}";
var organId = "${organId}";

	function save(){
		window.location.href = base+"FinancialManagement/Index";
	}
	function callBack(){
    	location.href = base+"FinancialManagement/GetWithdrawInfo?leasesCompanyId=" + leasesCompanyId + "&organId=" + organId;
    };
</script>
</body>
</html>