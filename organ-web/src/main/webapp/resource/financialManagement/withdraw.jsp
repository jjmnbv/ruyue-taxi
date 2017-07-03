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
  <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
  
</head>
<style>
	.new_rule_box table{
		color:#333;
		font-size:14px!important;
	}
	
	.new_rule_b .t{
		width:30px;
		color:#999;
	}
	
	label.error{width:45.7%;color: #f33333;float: right;text-align: left;padding-top: 20px;}
</style>
<body>
<div class="rule_box">
    <div class="crumbs">财务管理 > 账户管理 > 提现</div>
    <!-- <button class="btn_green" tyle="float: right; width: 60px; height: 27px; line-height: 27px; margin-top: -4px; margin-right: 30px;">返回</button> -->
    <a class="btn_green" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -41px;margin-right: 30px;" href="javascript:void(0);" onClick="callBack();">返回</a>
    <div class="new_rule_box">
        <div class="new_rule_b" style="height:400px;">
            <form id="withdrawForm">
            <table>
                <input id="balance" name="balance" value="${withdrawInfo.balance}" type="hidden"/>
                <input id="actualbalance" name="actualbalance" value="${withdrawInfo.actualbalance}" type="hidden"/>
                <input id="lineofcredit" name="lineofcredit" value="${withdrawInfo.lineofcredit}" type="hidden"/>
                <input id="leasesCompanyId" name="leasesCompanyId" value="${leasesCompanyId}" type="hidden"/>
                <input id="organId" name="organId" value="${organId}" type="hidden"/>
                <tr>
                    <td class="t">账户归属</td>
                    <td>${withdrawInfo.shortname}</td>
                </tr>
                
                <tr>
                    <td class="t" style="padding-top: 8px;">提现金额</td>
                    <td><input id="amount" name="amount" value="" type="text" maxlength="10" style="position:relative;top:13px;" onBlur="overFormat(this)"/><span style="position:relative;right:40px;top:15px;">元</span></td>
                </tr>
                <tr style="height:">
                    <td class="t"></td>
                    <td><span  style="position:relative;bottom:13px;">当前可提现金额<span id="tixian">${withdrawInfo.withdrawalamount lt 0?0.0:withdrawInfo.withdrawalamount}</span>元，<span class="qbtx" style="color:#1b74de;">全部提现</span></span></td>
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
            </form>
            <button class="btn_red" id="withdrawButton" style="float: left;margin-top: 70px;margin-right: 100px;width:175px;height:40px;font-size:15px;margin-left: 30px;" onclick="save();">提交</button>
        </div>
    </div>
</div>
<script type="text/javascript" src="js/financialManagement/withdraw.js"></script>
</body>
</html>