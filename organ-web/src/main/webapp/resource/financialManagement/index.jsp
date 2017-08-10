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
  <title>财务管理</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zstyle.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
  <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
  <!-- <script type="text/javascript"  src="content/js/bootstrap.min.js"></script> -->
  <style>
  	.zhanghu .zhanghu_item{
  		position:relative;
  		padding-bottom:6px!important;
  		min-height:140px;
  	}
  	.zhanghu_item h3{
  		padding-top:5px;
  	}
  	.zhanghu_item .edu{
  		position:absolute;right:0;top:0;width:112px;height:36px;background-color:#f90;color:#fff;font-size:16px;display:block;line-height:36px;text-align:center;
  	}
  	.zhanghu_item img{
  		margin-right:6px;
  	}
  	.zhanghu_item .font-red, .zhanghu_item e{
  		margin-left:0;
  		position:relative;
  		bottom:4px;
  	}
  	.zhanghu_item .btn_box{
  		position:absolute;
  		top:87px;
  		right:24px;
  	}
  	.zhanghu_item button{
  		margin-left:10px;
  		border:1px solid #f33;
  		background-color:white;
  		color:#f33;
  		border-radius:2px;
  		font-size:13px;
  	}
  	.zhanghu_item .btn_red:hover{
  		color:white;
  		background-color:#f33;
  	}
  	.zhanghu_item .btn_grey:hover{
  		color:white;
  		background-color:#bdbdbd;
  	}
  </style>
</head>
<body>
    <input id="tabnum" name="tabnum" value="${tabnum}" type="hidden">
    <div class="content">
        <div class="con_box">
            <ul class="tabmenu">
                <li class="on">账户管理</li>
                <li>账单管理</li>
            </ul>
            <ul class="tabbox">
                <li  style="display:block">
                    <!--账户管理-->
                    <ul class="zhanghu">
                        <c:forEach var="organCompanyRef" items="${organCompanyRef}">
							<li class="zhanghu_item" >
                                <h3 >${organCompanyRef.name}</h3>
                                <img src="content/img/icon_yue.png"/>
                                <span  class="font-red">${organCompanyRef.actualBalance}</span><e>元</e>
                                <img src="content/img/icon_xinyong.png"  style="margin-left:50px;"/>
                                <span  class="font-red">${organCompanyRef.lineOfCredit}</span><e>元</e><br>
                                <div style="color:#888;font-size:15px;margin-top:20px;">
                                	<span class="black">未结算金额</span>
                                	<span style="color:#f33333;">${organCompanyRef.unBalanced}</span>
                                	<span class="black">元，当前可用额度</span>
                                	<span style="color:#1a1a1a;">${organCompanyRef.balance}</span>
                                	<span class="black">元</span>
                                </div>
                                <div class="btn_box"> 
                                    <button class="btn_red" onclick="recharge('${organCompanyRef.companyId}','${organCompanyRef.organId}')">充值</button>
                                    <c:if test="${organCompanyRef.actualBalance eq 0}">
                                          <button class="btn_grey">提现</button>
                                    </c:if>
                                    <c:if test="${organCompanyRef.actualBalance gt 0}">
                                          <button class="btn_red" onclick="withdraw('${organCompanyRef.companyId}','${organCompanyRef.organId}')">提现</button>
                                    </c:if>
                                    <button class="btn_red" onclick="searchExpensesDetail('${organCompanyRef.companyId}','${organCompanyRef.organId}')">交易明细</button>
                                    <button class="btn_red" onclick="couponDetail('${organCompanyRef.companyId}','${organCompanyRef.organId}')">抵用券</button>                                   
                                </div>
                                <span class="edu" style="display: ${organCompanyRef.balance ge 100?'none':'block'};">额度不足</span>
                            </li>   
						</c:forEach>
                    </ul>
                    <!--账户管理结束-->
                </li>
                <li class="zhandang">
                   <!--账单管理开始-->
                    <div class="select_box" style="height: 34px;line-height: 34px;">
                        <input style="height:initial;line-height:initial" placeholder="按时间段筛选" class="select_val" data-value="" value="" id="timeType" name="timeType">
                        <ul class="select_content">
                            <li data-value="">全部时间</li>
                            <li data-value="0">最近1个月</li>
                            <li data-value="1">最近3个月</li>
                            <li data-value="2">最近半年</li>
                            <li data-value="3">最近1年</li>
                        </ul>
                    </div>
                    <div class="select_box" style="height: 34px;line-height: 34px;">
                        <input style="height:initial;line-height:initial" placeholder="按服务车企" class="select_val" data-value="" value="" id="leasesCompanyId" name="leasesCompanyId">
                        <ul class="select_content">
                               <li data-value="">全部</li>
                            <c:forEach var="leasesCompany" items="${leasesCompany}">
							   <li data-value="${leasesCompany.id}">${leasesCompany.name}</li>
						    </c:forEach>
                        </ul>
                    </div>
                    <div class="select_box" style="height: 34px;line-height: 34px;">
                        <input style="height:initial;line-height:initial" placeholder="全部账单状态" class="select_val" data-value="" value="" id="billState" name="billState">
                        <ul class="select_content">
                            <li data-value="">全部账单状态</li>
                            <li data-value="3">待核对</li>
                            <li data-value="5">已退回</li>
                            <li data-value="4">待支付</li>
                            <li data-value="8">已作废</li>
                            <li data-value="6">已付款</li>
                            <li data-value="A">免单</li>
                        </ul>
                    </div>
                    <button class="btn_grey">清空</button>
                    <table id="dataGrid" style="position:relative;top:1px;"></table>
                   <!--账单管理结束-->
                </li>
            </ul>
        </div>
        
        <div class="pop_box accountjudge">
	        <div class="pop">
	            <div class="head">设置提醒<img src="content/img/btn_close.png" alt="关闭" class="close"></div>
	            <div class="con_c">
	                <span id="zhanghutixing"></span>
	            </div>
	            <div class="foot"><button class="btn_red close">确定</button></div>
	        </div>
        </div>
    </div>

<script type="text/javascript" src="js/financialManagement/index.js"></script>
</body>
</html>
