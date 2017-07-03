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
<title>用车规则—修改规则</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/css/common.css" />
<link rel="stylesheet" type="text/css" href="content/css/zstyle.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
</head>
<body>
	<div class="rule_box">
		<div class="crumbs">
			用车规则 > 修改规则2
			<button class="btn_green"
				style="float: right; width: 60px; height: 27px; line-height: 27px; margin-top: -4px; margin-right: 30px;">返回</button>
		</div>
		<div class="new_rule_box">
			<div class="new_rule_head">
				<em class="asterisk"></em>规则名称 <input type="text" placeholder="低于10个文字" style="width: 390px; margin-left: 20px;" value="${ruleName}" id="yc">
				<input type="hidden" id="ycyuan" name="ycyuan" value="${ruleName}"/ maxlength="10">
			</div>
			<div class="rule_box_a">
				<div class="left">
					<em class="asterisk"></em>用车方式
				</div>
				<div class="right">
					<c:forEach items="${list}" var="list">
						<c:if test="${list.rulesType == '1'}">
							<input type="checkbox" class="company" value="1" />约车<br>
							<div class="right_box" id="ruletype1"></div>
						</c:if>
						<c:if test="${list.rulesType == '2'}">
							<input type="checkbox" class="company" value="2" />接机<br>
							<div class="right_box" id="ruletype2"></div>
						</c:if>
						<c:if test="${list.rulesType == '3'}">
							<input type="checkbox" class="company" value="3" />送机<br>
							<div class="right_box" id="ruletype3"></div>
						</c:if>
					</c:forEach>
				</div>
				<div
					style="text-align: right; position: absolute; width: 100%; bottom: 40px; right: 50px;">
					<button class="btn_red">保存</button>
					<!-- <a href="" class="btn_grey">取消</a> -->
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/orgUsecarrules/edit.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
</body>
</html>