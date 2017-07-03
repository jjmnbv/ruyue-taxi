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
  <meta name="description" content="网站内容描述，后台记得改！">
  <meta name="keywords" content="网页关键字，后台记得换掉">
  <title>账户管理—支付宝支付</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zstyle.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zsingle.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=AFLc9FVyIHUWExKYFETDeF6T"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
  
</head>
<body>
<div class="rule_box">
    <div class="crumbs">财务管理 > 账户管理 > 充值支付</div>
    <a class="btn_green" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -41px;margin-right: 30px;" href="javascript:void(0);" onClick="callBack();">返回</a>
    <div class="new_rule_box">
        <div class="new_rule_b">
          	 支付金额 <span class="bigger_red"> ¥${rechargeAmount}</span>
        </div>
        <div class="rule_box_a" style="padding: 60px 40px 40px  40px;line-height: 300%;">
        <div style="margin-left: -8px;">
        	<img alt="" src="img/myOrder/zhifubaotop.png">
        	</div>
        	<iframe id="qrframe" style="width:300px;height:310px;border:0px;background-color:aliceblue;">
        	</iframe>
        	<div style="margin-left: -7px;">
        	<img alt="" src="img/myOrder/zhifubao.png">
        	</div>
        </div>
    </div>
</div>
<script type="text/javascript">
var base = document.getElementsByTagName("base")[0].getAttribute("href");
	var leasesCompanyId = "${leasesCompanyId}";
	var organId = "${organId}";
	var paytype = "${paytype}";
	var rechargeAmount = "${rechargeAmount}";
	window.top.outtradeno="";
	$("#qrframe").attr("src","FinancialManagement/GetZFBQRCode?leasesCompanyId="+leasesCompanyId+"&organId="+organId+"&rechargeAmount="+rechargeAmount);
	var t;
	function checkOrderState(){
		$.post("FinancialManagement/CheckPayState", {"out_trade_no":window.top.outtradeno},function(data){
			if(data.status == "success"){
				if(data.payed){
					//支付成功界面
					window.top.outtradeno = ""; 
					toastr.success("支付成功！", "提示");
					setTimeout(function(){
						//跳转到订单详情界面
						window.location.href = base+"FinancialManagement/Recharge?leasesCompanyId="+leasesCompanyId+"&organId="+organId+"&pay=1";
					},500);
					clearTimeout(t);
				}else{
					t = setTimeout(function(){
						checkOrderState();
					},5000);
				}
			}
	    });
	}
	checkOrderState(); 
	function callBack(){
    	location.href = base+"FinancialManagement/Recharge?leasesCompanyId=" + leasesCompanyId + "&organId=" + organId;
    };
</script>
</body>
</html>