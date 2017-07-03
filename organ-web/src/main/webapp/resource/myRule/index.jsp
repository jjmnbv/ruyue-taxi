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
</head>
<body>
		<div class="content">
			<div class="con_box rule">
				<ul class="con_box rule_list zhuguan">
				<c:choose>
					<c:when test="${size > 0}">
						<c:forEach items="${map}" var="map">
							<li>
							<h4 id="ruleName">${map.key}</h4>
        					<div class="zhuguan_box">
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
								</div>
							</li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<li>
                        	<font style="height: 50px; font-size: 16px; font-weight: normal; line-height: 50px; padding-left: 20px;">您还没有被授予用车规则</font>
			            </li>
					</c:otherwise>
				</c:choose>
				</ul>
			</div>
		</div>
<!-- 		<script type="text/javascript" src="js/orgUsecarrules/index.js"></script> -->
<script>
	$(document).ready(function() {
		$(".zhuguan h4").click(function(){
			if($(this).next(".zhuguan_box").css("display")=='none'){
			    $(this).css("background-image","url('content/img/btn_upone.png')");
			}else {
			    $(this).css("background-image","url('content/img/btn_downone.png')");
			}
			$(this).next(".zhuguan_box").toggle(200);
	    });
    });
</script>
</body>
</html>
