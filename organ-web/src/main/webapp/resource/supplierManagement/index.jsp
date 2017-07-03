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
<title>供应商管理</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/css/common.css" />
<link rel="stylesheet" type="text/css" href="content/css/gongyingshangguanli.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script src="content/js/common.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<style type="text/css">
	 #parent {
    background: url('http://ourjs.github.io/static/2015/arrow.png') right center no-repeat; 
   /* the width and the height of your image */
    width: 200px;
    height: 30px;
    overflow: hidden;
    border: solid 1px #ccc;
}

#parent select {
    -webkit-appearance:none;
    -moz-appearance:none;
    appearance:none;
    background:transparent;
    border:none;
    padding-left:10px;
    width: 200px;
    height:100%;   
}
input::-ms-clear { display: none; } 
</style>
</head>
<body>
		<!--主要内容开始-->
		<div class="content">
			<div class="con_box">
				<!--供应商管理界面开始  1 是主账号-->
				<div class="supplier">
					<c:choose>
			        	<c:when test="${orgUser.mainaccount == '0'}">
			       			<div class="block">
								<table>
									<tr class="tr_1">
										<td class="td_1">当前帐号</td>
										<td class="td_2">
										<span class="username">${orgUser.account}</span>
											<!--  --> 
											<c:choose>
												<c:when test="${orgUser.bindstate == '1'}">
													<!--  
													<input id="" name="" type="hidden" value="${orgUser.id}"/>
													<span class="link_y">[已关联]</span> 
													-->
													<c:if test="${count > 0}">
														<td colspan="3" class="remove_link" onclick="removeLink('${orgUser.id}','${orgUser.orgOrganCompanyRefId}','${orgUser.mainaccount}');"><span>解除关联</span></td>
													</c:if>
												</c:when>
												<c:otherwise>
												 	<!--
													<input id="" name="" type="hidden" value="${orgUser.id}"/>  
													<span class="link_y" style="color: red;">[未关联]</span>
													<td colspan="3" class="add_link" onclick="addLink('${orgUser.id}','${orgUser.orgOrganCompanyRefId}','${orgUser.mainaccount}');"><span>关联帐号</span></td>
													-->
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr class="tr_2">
										<td class="td_1">帐号归属</td>
										<td class="td_2">${orgUser.name}</td>
									</tr>
									<tr class="tr_2">
										<td class="td_1">类别</td>
										<td class="td_2">主帐号</td>
									</tr>
									<tr class="tr_2">
										<td class="td_1">开通时间</td>
										<td class="td_2">${orgUser.openTime}</td>
									</tr>
								</table>
							</div>
							<c:forEach items="${orgUserList}" var="orgUserList">
								<div class="block">
									<table>
										<tr class="tr_1">
											<td class="td_1">当前帐号</td>
											<td class="td_2">
												<span class="username">${orgUserList.account}</span>
													<c:choose>
														<c:when test="${orgUserList.bindstate == '1'}">
															<input id="" name="" type="hidden" value="${orgUserList.id}"/> 
															<span class="link_y">[已关联]</span>
															<td colspan="3" class="remove_link" onclick="removeLink('${orgUserList.id}','${orgUserList.orgOrganCompanyRefId}','${orgUserList.mainaccount}');"><span>解除关联</span></td>
														</c:when>
														<c:otherwise>
															<input id="" name="" type="hidden" value="${orgUserList.id}"/> 
															<span class="link_y" style="color: red;">[未关联]</span>
															<td colspan="3" class="add_link"  onclick="addLink('${orgUserList.id}','${orgUserList.orgOrganCompanyRefId}','${orgUserList.mainaccount}');"><span>关联帐号</span></td> 
														</c:otherwise>
													</c:choose>
											</td>
										</tr>
										<tr class="tr_2">
											<td class="td_1">帐号归属</td>
											<td class="td_2">${orgUserList.name}</td>
										</tr>
										<tr class="tr_2">
											<td class="td_1">类别</td>
											<c:choose>
												<c:when test="${orgUserList.mainaccount == '0'}">
													<td class="td_2">主帐号</td>
												</c:when>
												<c:otherwise>
													<td class="td_2">子帐号</td>
												</c:otherwise>
											</c:choose>
										</tr>
										<tr class="tr_2">
											<td class="td_1">开通时间</td>
											<td class="td_2">${orgUserList.openTime}</td>
										</tr>
									</table>
								</div>
							</c:forEach>
			      		</c:when>
				       	<c:otherwise>
				       		<div class="block">
								<table>
									<tr class="tr_1">
										<td class="td_1">当前帐号</td>
										<td class="td_2">
										<span class="username">${orgUser.account}</span> 
											<c:choose>
												<c:when test="${orgUser.bindstate == '1'}">
													<span class="link_y">[已关联]</span> 
													<span class="link_active">登录主帐号进行解除关联操作</span>
												</c:when>
												<c:otherwise>
													<span class="link_y" style="color: red;">[未关联]</span>
													<span class="link_active">登录主账号进行关联操作</span>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr class="tr_2">
										<td class="td_1">帐号归属</td>
										<td class="td_2">${orgUser.name}</td>
									</tr>
									<tr class="tr_2">
										<td class="td_1">类别</td>
										<td class="td_2">子帐号</td>
									</tr>
									<tr class="tr_2">
										<td class="td_1">开通时间</td>
										<td class="td_2">${orgUser.openTime}</td>
									</tr>
								</table>
							</div>
							<c:forEach items="${orgUserList}" var="orgUserList">
								<div class="block">
									<table>
										<tr class="tr_1">
											<td class="td_1">当前帐号</td>
											<td class="td_2">
												<span class="username">${orgUserList.account}</span>
													<c:choose>
														<c:when test="${orgUserList.bindstate == '1'}">
															<span class="link_y">[已关联]</span> 
															<span class="link_active">登录主帐号进行解除关联操作</span>
														</c:when>
														<c:otherwise>
															<span class="link_y" style="color: red;">[未关联]</span>
															<span class="link_active">登录主账号进行关联操作</span> 
														</c:otherwise>
													</c:choose>
											</td>
										</tr>
										<tr class="tr_2">
											<td class="td_1">帐号归属</td>
											<td class="td_2">${orgUserList.name}</td>
										</tr>
										<tr class="tr_2">
											<td class="td_1">类别</td>
											<c:choose>
												<c:when test="${orgUserList.mainaccount == '0'}">
													<td class="td_2">主帐号</td>
												</c:when>
												<c:otherwise>
													<td class="td_2">子帐号</td>
												</c:otherwise>
											</c:choose>
										</tr>
										<tr class="tr_2">
											<td class="td_1">开通时间</td>
											<td class="td_2">${orgUserList.openTime}</td>
										</tr>
									</table>
								</div>
							</c:forEach>
			       		</c:otherwise>
					</c:choose>
				</div>
				<!--供应商管理界面结束-->
			</div>
		</div>
		<!--主要内容结束-->
	<!--关联账号弹框开始-->
	<div class="popup_box">
		<!--关联账号-->
		<div class="link_user popup">
			<div class="popup_title">
				<span>关联账号</span> <i class="close"></i>
			</div>
			<div class="popup_content">
				<p class="child_con">
					<span class="child">子帐号</span><span id="addLinkName">asdfdf_001(武汉同城帮)</span>
				</p>
				<div class="child_con">
					<form action="" id="addLinkPassword">
						<div class="form_block">
							<span class="popup_l">原密码</span>
							<div class="inp_box">
								<input type="password" id="passwordOld" placeholder="" value="" maxlength="16">
							</div>
						</div>
						<div class="form_block">
							<span class="popup_l">新密码</span>
							<div class="inp_box">
								<input type="password" id="passwordNew" placeholder="6-16位字母、符号和数字组成" value=""  maxlength="16">
							</div>
						</div>
						<div class="form_block">
							<span class="popup_l">重复新密码</span>
							<div class="inp_box">
								<input type="password" id="passwordNew1" placeholder="6-16位字母、符号和数字组成" value="" maxlength="16">
							</div>
						</div>
					</form>
				</div>
				<p class="con_footer">关联账号需更改子帐号的密码，成功后，子账号与主账号具备相同的权限。</p>
			</div>
			<div class="popup_footer">
				<input id="orgOrganCompanyRefIdAdd" name="" value="" type="hidden"/><!-- 关联账号用 -->
				<input id="userId" name="" value="" type="hidden"/><!-- 关联账号验证密码的时候用 -->
				<span class="cancel">取消</span> 
				<span class="sure" onclick="saveAddLinkName();">确定</span>
			</div>
		</div>
		
		<!--解除关联1-->
		<div class="link_remove1 popup">
			<div class="popup_title">
				<span>解除关联</span> <i class="close"></i>
			</div>
			<div class="popup_content">
				<p class="con_title">由于您解除的是当前的主帐号，请您选择一个子帐号作为主帐号</p>
				<div class="con_con">
					<span>帐号</span>
					<div class="select_box">
						<input placeholder="选择账号" class="select_val" data-value="" value="">
						<ul class="select_content">
							<c:forEach items="${childCccount}" var="childCccount">
								<li data-value="${childCccount.orgOrganCompanyRefId}">${childCccount.account}(${childCccount.name})</li>
							</c:forEach>
						</ul>
						<!-- <div id="parent">
							<select>
							    <option>what</option>
							    <option>the</option>
							    <option>hell</option>
							</select>
						</div> -->
					</div>
					
					
					
				</div>
				<p class="con_f">解除关联后将无法因公使用该公司车辆</p>
			</div>
			<div class="popup_footer">
				<!-- 主账号的解除关联 -->
				<input id="orgOrganCompanyRefIdRemove1" name="" value="" type="hidden"/>
				<span class="cancel">取消</span> 
				<span class="sure" onclick="saveRemoveLink1();">提交</span>
			</div>
		</div>
		<!--解除关联2-->
		<div class="link_remove2 popup">
			<div class="popup_title">
				<span>解除关联</span> <i class="close"></i>
			</div>
			<div class="popup_content">
				<p>解除关联后将无法因公使用该公司车辆</p>
			</div>
			<div class="popup_footer">
				<!-- 子账号解除关联  -->
				<input id="orgOrganCompanyRefIdRemove" name="" value="" type="hidden"/>
				<span class="cancel">取消</span> 
				<span class="sure" onclick="saveRemoveLink();">确定</span>
			</div>
		</div>
	</div>
	<!--关联账号弹框结束-->
	<script type="text/javascript" src="js/supplierManagement/index.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	
	// ie下弱提示
	$('input, textarea').placeholder();
	</script>
</body>
</html>
