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
  <meta name="description" content="">
  <meta name="keywords" content="">
  <title>账户充值</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zstyle.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zsingle.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=AFLc9FVyIHUWExKYFETDeF6T"></script>
  <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
  
</head>
<style type="text/css">
.payway .new_rule_b {height: 180px;}
	.new_rule_box table{
		color:#333;
		font-size:14px!important;
	}
	
	.new_rule_b .t{
		width:30px;
		color:#ccc;
	}
	
	label.error{width:55%;color: #f33333;float: right;text-align: left;padding-top: 10px;}	
</style>
<body>
<div class="rule_box">
    <div class="crumbs">财务管理 > 账户管理 > 充值</div>
    <a class="btn_green" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -41px;margin-right: 30px;" href="javascript:void(0);" onClick="callBack();">返回</a>
    <div class="new_rule_box" payway>
        <div class="new_rule_b" style="border:none;padding-left: 40px;">
                                      账户归属&nbsp;&nbsp;&nbsp;&nbsp;<span><b>${leLeasesCompany.shortName}</b></span><br>
            <!-- <label style="width: 80px;">充值金额</label><input type="text" placeholder="充值金额" id="queryUserMessage" value="" style="margin-right: 0px;"/>元 -->
            <form id="editRechargeForm" method="get" class="form-horizontal  m-t" id="frmmodal" style="margin-top: 20px;">
                                     充值金额&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" placeholder="充值金额" id="rechargeAmount" name="rechargeAmount" value="" style="margin-right: 0px;" maxlength="10" onBlur="overFormat(this)"/>元 <br>
                  <span style="position:relative;left:68px;bottom:16px;">单笔限额20000.0元</span>
            </form>
        </div>
        <div class="rule_box_a" style="padding: 20px 40px 40px  40px;line-height: 300%;">
        	<input id="leasesCompanyId" name="leasesCompanyId" value="${leasesCompanyId}" type="hidden"/>
        	<input id="organId" name="organId" value="${organId}" type="hidden"/>
        	<c:if test="${leLeasesCompany.alipaystatus=='1'}">
        	      <input type="radio" name="payway" value="3" checked="checked" style="position:relative;top:8px;">&nbsp;&nbsp;<img src="content/img/img_zfb.png"><br>
        	</c:if>
            <c:if test="${leLeasesCompany.wechatstatus=='1'}">
                  <c:if test="${leLeasesCompany.alipaystatus=='1'}">
                        <input type="radio" name="payway" value="2" style="position:relative;top:18px;">&nbsp;&nbsp;<img src="content/img/img_wx.png" style="margin-top:10px;">
                  </c:if>
                  <c:if test="${leLeasesCompany.alipaystatus=='0'}">
                        <input type="radio" name="payway" value="2" checked="checked" style="position:relative;top:18px;">&nbsp;&nbsp;<img src="content/img/img_wx.png" style="margin-top:10px;">
                  </c:if>
            </c:if>
            <a href="javascript:void(0)" class="btn_red" onclick="save();"  style="margin-top:30px;">提交</a>
        </div>
    </div>
    <input id="pay" name="pay" value="${pay}" type="hidden"/>
    
    <div class="pop_box zhifu">
        <div class="pop">
            <div class="head">提示<img src="content/img/btn_close.png" alt="关闭" class="close"></div>
            <div class="con_c">
                <span>充值成功</span>
            </div>
            <div class="foot"><button class="btn_red close">确定</button></div>
        </div>
    </div>
</div>
<script type="text/javascript">
var base = document.getElementsByTagName("base")[0].getAttribute("href");

$(document).ready(function() {

	validateForm();
	zhifuchenggong();
});	

/**
 * 表单校验
 */
function validateForm() {
	$("#editRechargeForm").validate({
		rules: {
			rechargeAmount: {required: true, number:true, limitNum:[8, 1], min:0.1, max:20000.0}
		},
		messages: {
			rechargeAmount: {required: "请输入充值金额", number:"金额格式有误", limitNum:"整数位最多8位，小数位最多1位", min:"输入金额须大于0", max:"金额超过单次上限"}
		}
	})
}
	
/**
 * value 验证的值
 * param[0] 最大整数位
 * param[1] 最大小数位
 */
$.validator.addMethod("limitNum", function(value, element, param) {
	var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
	return rep.test(value);
}, "金额格式有误");	
   
    function zhifuchenggong() {
    	var pay = "${pay}";
    	if (pay == "1") {
    		$(".zhifu").show();
    	}
    }

    /**
     * 清除整数位前面的多个0
     */
    function overFormat(obj) {
    	if(/^0+\d+\.?\d*.*$/.test(obj.value)){
    		obj.value = obj.value.replace(/^0+(\d+\.?\d*).*$/, '$1');
    	}
    }

	function save(){
		var leasesCompanyId = $("#leasesCompanyId").val();
		var organId = $("#organId").val();
		var rechargeAmount = $("#rechargeAmount").val();
		var paytype = $("input[name='payway']:checked").val();
		
		var form = $("#editRechargeForm");
		if(!form.valid()) return;
		
		if(!paytype){
			toastr.warning("请选择一种支付方式", "提示");
			return ;
		}

		window.location.href = base+"FinancialManagement/GoPay?leasesCompanyId="+leasesCompanyId+"&organId="+organId+"&paytype="+paytype+"&rechargeAmount="+rechargeAmount;
	}
	function callBack(){
    	location.href = base+"FinancialManagement/Index";
    };
</script>
</body>
</html>