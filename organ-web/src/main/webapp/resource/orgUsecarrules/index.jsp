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
<title>用车规则页</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/css/common.css" />
<link rel="stylesheet" type="text/css" href="content/css/zstyle.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<style type="text/css">
	.edit{cursor:pointer;}
</style>	
</head>
<body>
<div class="content">
	<div class="con_box rule">
		<label>关键词</label> 
		<input type="text" placeholder="规则名称" style="width: 392px;" id="queryCompany" value="${queryCompany}" maxlength="10">
		<button type="submit" class="btn_red" onclick="query();">查询</button>
		<button class="btn_grey" id="emptyCompany" onclick="empty();">清空</button>
		<a href="OrgUsecarrules/AddIndex" target="iframe" title="新建规则" class="btn_green">新建规则</a>
		<ul class="rule_list">
		<c:choose>
			<c:when test="${size > 0}">
				<c:forEach items="${map}" var="map">
					<li>
						<h4 id="ruleName">${map.key}</h4> 
						<em class="del" style="cursor:pointer;" onclick="del('${map.key}');">删除</em>
						<em class="edit"  style="display: none;cursor:pointer;" data-value="${map.key}" onclick="edit('${map.key}');">编辑</em>
						<c:forEach items="${map.value}" var="map1">
							<div class="left">用车方式</div>
							<div class="right">
								<c:if test="${map1.key == '1'}">
									约车<br />
									<c:forEach items="${map1.value}" var="map2">
									${map2.leasesCompanyName}
							                        （
						            	<c:forEach items="${map2.vehiclemodels}" var="map3">             
							        		${map3.vehicleModelsName}
							            </c:forEach>             
							                        ）<br />
									</c:forEach>
								</c:if>
								<c:if test="${map1.key == '2'}">
									接机<br />
									<c:forEach items="${map1.value}" var="map2">
									${map2.leasesCompanyName}
							                        （
						            	<c:forEach items="${map2.vehiclemodels}" var="map3">             
							        		${map3.vehicleModelsName}
							            </c:forEach>             
							                        ）<br />
									</c:forEach>
								</c:if>
								<c:if test="${map1.key == '3'}">
									送机<br />
									<c:forEach items="${map1.value}" var="map2">
									${map2.leasesCompanyName}
							                        （
						            	<c:forEach items="${map2.vehiclemodels}" var="map3">             
							        		${map3.vehicleModelsName}
							            </c:forEach>             
							                        ）<br />
									</c:forEach>
								</c:if>
							</div>
							<br />
						</c:forEach>
					</li>								
				</c:forEach>
			</c:when>
			<c:otherwise>
				<li>
					<c:choose>
						<c:when test="${not empty search}">
							<h4>${search}</h4>
						</c:when>
						<c:otherwise>
							<h4>暂没有制定用车规则</h4>
						</c:otherwise>
					</c:choose>
	            </li>
			</c:otherwise>
		</c:choose>
		</ul>
	</div>
</div>
<script type="text/javascript" src="js/orgUsecarrules/index.js"></script>
<script type="text/javascript">
var base = document.getElementsByTagName("base")[0].getAttribute("href");
	$(function(){
		var name = $("#ruleName").text();
		$(".rule_list .edit").each(function(){
			var d = $(this).attr("data-value");
			(function(id,$edit){
				$.post("OrgUsecarrules/CheckRulesUpdate", {name:id}, function (data) {
					if(data == 0){
						$edit.removeAttr("style");
					}
			    });
			})(d,$(this));
		});
	}); 
</script>
</body>
</html>
