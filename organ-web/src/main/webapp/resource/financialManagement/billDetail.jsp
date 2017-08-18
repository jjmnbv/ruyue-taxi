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
    <title>账单详情</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/zstyle.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
    <script type="text/javascript" src="content/js/jquery.js"></script>
    <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
    <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
    <script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="js/basecommon.js"></script>
    <script type="text/javascript" src="content/js/common.js"></script>
    <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
</head>

<style type="text/css">
	label.error{width:100%;color: #f33333;text-align: left; }
</style>

<body>

<div class="rule_box">
    <div class="crumbs">账单明细
    <button class="btn_green back" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -4px;margin-right: 30px;" onclick="back();">返回</button> </div>
    <div class="account">
        <input id="billsId" name="billsId" value="${orgOrganBill.id}" type="hidden">
        <input id="billState" name="billState" value="${orgOrganBill.billState}" type="hidden">
        <input id="money" name="money" value="${orgOrganBill.money}" type="hidden">
        <input id="leasesCompanyId" name="leasesCompanyId" value="${orgOrganBill.leasesCompanyId}" type="hidden">
        <input id="organId" name="organId" value="${orgOrganBill.organId}" type="hidden">
        <input id="name" name="name" value="${orgOrganBill.name}" type="hidden">
        <table>
            <tr>
                <td class="t">账单名称</td>
                <td>${orgOrganBill.name}</td>
                <td class="t">账单金额</td>
                <td class="font-red">${orgOrganBill.money}元</td>
            </tr>
            <tr>
                <td class="t">出账时间</td>
                <td>${createTime}</td>
                <td class="t">服务车企</td>
                <td>${orgOrganBill.shortName}</td>
            </tr>
        </table>
        <button class="btn_red" id="billButton" style="float: right;margin-top: -52px;margin-right: 100px;">核对</button>
        <span class="font-blue" style="float: right;margin-right: 38px;margin-top: -34px;cursor:hand;cursor:pointer;" onclick="exportExcel()">导出Excel</span>
    </div>

    <ul class="a_path">
       <c:forEach var="organBillState" items="${organBillState}">
           <c:if test="${organBillState.billState != orgOrganBill.billState}">
             <li>${organBillState.billStateName}<br>${organBillState.operationTime}</li>
             <li class="b"></li>
           </c:if>
           <c:if test="${organBillState.billState == orgOrganBill.billState}">
             <li class="end">${organBillState.billStateName}<br>${organBillState.operationTime}</li>
           </c:if>
       </c:forEach>
    </ul>

    <table id="dataGrid" ></table>
    <div class="pop_box bill">
        <div class="pop">
            <div class="head">核对账单<img src="content/img/btn_close.png" alt="关闭" class="close"></div>
            <form id="billForm">
            <div class="con_c">
                <input type="radio" class="billvalue" name="bill" value="0" checked>通过账单<br>
                <input type="radio" class="billvalue" name="bill" value="1">退回账单<br>
                <textarea style="display: none;" id="comment" name="comment" placeholder="备注（必填）" maxlength="100"></textarea>
            </div>
            </form>
            <div class="foot"><button class="btn_red" id="button_r" onclick="save()">确定</button><button class="btn_grey close">取消</button></div>
        </div>
    </div>
    
</div>

<script type="text/javascript" src="js/financialManagement/billDetail.js"></script>
</body>
</html>
