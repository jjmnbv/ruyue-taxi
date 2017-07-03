<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%   
String path = request.getContextPath();   
String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <base href="<%=basePath%>">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <title>用车规则—超级管理员</title>
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/zTree/metroStyle/metroStyle.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/yuangongguanli.css"/>
  <link href="content/css/combotree/combotree.css" rel="stylesheet">
  <style type="text/css">
  	.popup_box .popup .combotree-chosen {
  		color:black !important
  	}
  	
  	select{
  		border: 1px solid #e8e0e0;
	    color: rgb(88, 88, 88);
	    font-size: 14px;
	    padding-left: 8px;
	    height: 29px;
	    font-family: 微软雅黑;
	    width: 125px;
  	}
  	
  	#specialstate_driver td {
  		vertical-align:bottom;
  	}
  	
  	#specialstate_driver .p_active{
  		color: #F33333;
   		border: 1px solid #F33333;
  	}
  	.noborder p{
  		border:0 !important;
  	}
  	
  	.progress_c{
  		border:1pxsolid #BCBCBC;
  		background-color:#E6E6E6;
  		font-size:12px;
  		margin-right:25px;
  		border-radius: 20px;
  	}
  	
  	.progress_v{
  		width:0%;
  		height:15px;
  		background-color:rgb(38, 160, 218);
  		border-radius: 20px;
  		display:block;
  	}
  	
  	#select_rulecon span{
  		text-overflow: ellipsis;
    	white-space: nowrap;
    	overflow: hidden;
  	}
  	.inp_1 label.error{
  	position: absolute;
    left: 0px;
    top: 30px;
    font-size: 10px;
    color: #f33333;
  	
  	}
  </style>
  <script type="text/javascript">
  var issuper = ${issuper};
  </script>
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script src="content/js/common.js" type="text/javascript" charset="utf-8"></script>
  <script src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
  <script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
  <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
  <script src="content/plugins/zTree/jquery.ztree.core-3.5.js" type="text/javascript" charset="utf-8"></script>
  <script src="content/plugins/zTree/jquery.ztree.excheck-3.5.min.js" type="text/javascript" charset="utf-8"></script>
  <script src="content/plugins/combotree/jquery.combotree.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
  <script src="content/plugins/fileupload/jquery.ui.widget.js"></script>
  <script src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
  <script src="content/plugins/fileupload/jquery.fileupload.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script src="js/user/index.js" type="text/javascript" charset="utf-8"></script>
</head>
	<body>
		 <div class="content">	
		   	<div class="con_box">
		   		<div class="con_header">
		    		<div class="head_left">员工管理</div>
		    		<div class="head_middle" id="import">批量导入员工</div>
		    		<div class="head_right"><a href="resource/user/批量导入员工模版.xls">批量导入模板</a></div>
		    	</div>
		    	<div class="con_body">
		    		<!--树形菜单-->
		    		<div class="con_sidebar">
		    			<div class="con_side_head">
		    				<div class="con_side_input" style="background:url() no-repeat 135px center">
		    					<input type="text" placeholder="员工姓名" id="searchword" style="float:left;"/>
		    					<img id="search_close" alt="清空" src="content/img/btn_searchclose.png" style="display:none;">
		    				</div>
		    				<div class="con_side_search" id="searchbtn">
		    					查询
		    				</div>
		    			</div>
		    			<!--树形结构菜单-->
		    			<div class="con_side_content">
		    				<div class="tree_box">
		    					<ul id="treeDemo" class="ztree">
			    					
			    				</ul>
		    				</div>
		    			</div>
		    		</div>
		    		<!--右侧信息-->
		    		<div class="con_content">
		    			<!--公司界面-->
		    			<div class="con_explain" id="companypage">
		    				<div class="con_con_top">
			    				<img src="content/img/img_szt.png" alt="" />
			    			</div>
			    			<div class="con_con_info">
			    				在左侧树形图上选择公司名称、部门名称或者员工名称，可以进行新增下级部门、新增员工、编辑删除部门、编辑删除员工等操作。
			    			</div>
		    			</div> 
		    			<!--部门界面-->
		    			<div class="con_list_product" id="deptpage">
		    				<p class="con_list_title" id="deptpagetitle"></p>
		    				<table id="deptspagetable"> 
		    					
		    				</table>
		    			</div>
		    			<!--人员界面-->
		    			<div class="con_list_boss" id="userspage">
		    				<p class="con_list_title" id="userspagetitle"></p>
		    				<table id="userspagetable"></table>
		    			</div>
		    			<!--第四页，基本信息-->
		    			<div class="con_list_info" id="infopage">
		    				<p class="con_list_title">基本信息</p>
		    				<table> 
		    					<tr>
		    						<td>姓名</td>
		    						<td class="td_1" id="nickname"></td>
		    					</tr>
		    					<tr>
		    						<td>性别</td>
		    						<td class="td_1"  id="sex"></td>
		    					</tr>
		    					<tr>
		    						<td>分类</td>
		    						<td class="td_1" id="type"></td>
		    					</tr>
		    					<tr>
		    						<td>头像</td>
		    						<td class="td_1 td_active"><img  id="img" src="content/img/ing_tupian.png" alt="" /></td>
		    					</tr>
		    				</table>
		    				<table> 
		    					<tr>
		    						<td class="td_2">手机号码</td>
		    						<td class="td_1" id="account"></td>
		    					</tr>
		    					<tr>
		    						<td class="td_2">所属部门</td>
		    						<td class="td_1"  id="deptname" ></td>
		    					</tr>
		    					<tr>
		    						<td class="td_2">角色</td>
		    						<td class="td_1"  id="rolename"></td>
		    					</tr>
		    				</table>
		    				<p class="con_list_title active">用车信息</p>
		    				<div class="yongche">
		    					<span>规则</span>
		    					<span id="rules"></span>
		    				</div>
		    			</div>
		    		</div>
		    	</div>
		   	</div>
		   </div>
		   <!--员工管理界面结束-->
			<!--弹窗开始-->
			<div class="popup_box">
				<!--新增部门弹窗-->
		    <div class="add_department popup" id="addorupdatedept">
				<div class="popup_title">
					<span id="addorupdatedept_title">新增部门</span>
					<i class="close"></i>
				</div>
				<div class="popup_content">
					<form id="addorupdatedept_form">
					<input type="hidden" name="deptid" id="deptid">
					<table>
						<tr>
							<td>上级部门</td>
							<td class="td_1">
								<div class="inp">
									<input id="parentid" name="parentid" type="text" title="上级不能为空！" class="form-control" />
								</div>
							</td>
						</tr>
						<tr>
							<td>部门名称<span> *</span></td>
							<td>
								<div class="inp inp_1 inp_2">
									<input id="saveorupdatedept_deptname" name="saveorupdatedept_deptname" type="text"  maxlength="15"/>
								</div>
							</td>
						</tr>
					</table>
					</form>
				</div>
				<div class="popup_footer">
					<span class="cancel" id="deptaddorupdate_cancel">取消</span>
					<span class="sure" id="deptaddorupdate_save" onclick="savedept()">保存</span>
				</div>
			</div>
		
			<!--重置密码弹框-->
		    <div class="change_department popup" id="resetpwd">
				<div class="popup_title">
					<span>提示</span>
					<i class="close"></i>
				</div>
				<div class="popup_content">
					<label id="resetpwdcontent">确定重置密码</label>
				</div>
				<div class="popup_footer">
					<span class="cancel" id="resetpwd_cancel">取消</span>
					<span class="sure" id="resetpwd_save">确定</span>
				</div>
			</div>
			<!--新增员工-->
			<div class="add_staff popup" id="addorupdateuser" style="display:block">
				<div class="popup_title">
					<span id="addorupdateuser_title">新增员工</span>
					<i class="close"></i>
				</div>
				<div class="popup_content">
					<form id="addorupdateuser_form">
					<input type="hidden" id="userid" />
					<table class="add_staff_table1">
						<tr>
							<td class="td_right">所属部门</td>
							<td colspan="5"> 
								<div class="inp">
									<input id="addorupdateuser_parentid" name="parentid" type="text" title="部门名称不能为空！" class="form-control" />
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_right"><span>* </span>姓名</td>
							<td> 
								<div class="inp inp_1">
									<input type="text" id="addorupdateuser_nickname" name="nickname" maxlength="20" value=""/>
								</div>
							</td>
							<td class="td_right"><span>* </span>手机号码</td>
							<td>
								<div class="inp inp_1">
									<input type="text" id="addorupdateuser_phone" name="phone"  maxlength="11" value=""/>
								</div>
							</td>
							<td class="td_right td_left">性别</td>
							<td>
								<select name="sex" id="addorupdateuser_sex" class="form-control">
									 <option value="0">男</option>
									 <option value="1">女</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="td_right"><span>* </span>分类</td>
							<td>
						        <select name="specialstate" id="addorupdateuser_specialstate" class="form-control">
									 <option value="0">普通</option>
									 <option value="1">特殊</option>
								</select>
							</td>
							<td class="td_right"><span>* </span>角色</td>
							<td>
						        <select name="role" id="addorupdateuser_role" class="form-control" style="width:150px;">
									
								</select>
							</td>
							<td class="td_right">头像</td>
							<td class="td_img">
								<input id="imgpath" type="hidden" name="imgpath"/>
								<img src="content/img/ing_tupian.png" style="position:relative;width:50px;height:50px;vertical-align: bottom;background-color:gray;" id="imgback" />
								<input id="fileupload" type="file" name="file" multiple style="position:absolute;top:0px;left:0px;width:50px;height:50px;filter:alpha(opacity=0);-moz-opacity:0;opacity:0" accept="image/jpg,image/png,image/gif"/>
								<a id="clear" type="button" name="clear">删除</a>
							</td>
						</tr>
						<tr>
							<td class="td_right" style="width:56px;"><span></span>用车规则</td>
							<td colspan="5" class="td_yongche">
								<input name="Fruit" id="addorupdateuser_norule" type="radio" checked>
								无规则&nbsp;&nbsp;&nbsp;
								<input name="Fruit" id="addorupdateuser_rules" type="radio">
								选择用车规则
							</td>
						</tr>
						<tr class="tr_active" id="addorupdateuser_selectrules">
							<td></td>
							<td colspan="5" id="select_rulecon" width="601">
								
							</td>
						</tr>
						<tr>
							<td colspan="6" class="zhuyi">注：每位员工最多可以选择5条用车规则</td>
						</tr>
						<tr>
							<td class="td_empty"></td>
						</tr>
					</table>
					<span style="float:left;line-height:47px;display:none;" id="driverlable">配驾司机</span>
					<table class="add_staff_table2" id="specialstate_driver">
						
					</table>
					</form>
				</div>
				<div class="popup_footer">
					<span class="cancel" id="addorupdateuser_cancel">取消</span>
					<span class="sure" onclick="saveUser()">保存</span>
				</div>
			</div>
			<!--提示弹窗-->
		    <div class="hint_win popup" id="delete_dlg" >
				<div class="popup_title">
					<span>提示</span>
					<i class="close"></i>
				</div>
				<div class="popup_content">
					确定要删除员工？
				</div>
				<div class="popup_footer">
					<span class="cancel">取消</span>
					<span class="sure">确定</span>
				</div>
			</div>
			<!--批量更改部门弹窗-->
		    <div class="batch_change popup" id="batch_change_dept">
				<div class="popup_title">
					<span>批量更改部门</span>
					<i class="close"></i>
				</div>
				<div class="popup_content">
					<table>
						<tr>
							<td><span>* </span>变更后的部门</td>
							<td>
								<div class="inp">
									<input id="batch_change_dept4input" type="text" placeholder="低于10个文字" maxlength="10"/>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="popup_footer">
					<span class="cancel">取消</span>
					<span class="sure">保存</span>
				</div>
			</div>
			<!--批量导入弹窗-->
		    <div class="batch_import popup" id="import_dialog">
				<div class="popup_title">
					<span>批量导入</span>
					<i class="close"></i>
				</div>
				<div class="popup_content">
					<form id="import_form" method="post" enctype="multipart/form-data">
						<table>
							<tr>
								<td>1、</td>
								<td><span><a href="resource/user/批量导入员工模版.xls">点此下载批量上传员工信息模块，</a></span>按说明填写员工信息，然后上传。</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<i id="xuanze_value"></i>
									<i class="xuanze" id="xuanze">选择文件</i>
									<input id="file" name="file" type="file" style="display:none" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
									<i class="shanghcuan">上传文件不能大于5M</i>
								</td>
							</tr>
							<tr>
								<td>2、</td>
								<td class="td_3">
									<div style="width:150px;float:left;line-height:34px;">请选择批量导入的部门</div>
									<div style="width:264px;float:left;">
							            <input placeholder="选择选择导入员工的部门" id="import_dept" data-value="" value="">
							        </div>
								</td>
							</tr>
							<tr>
								<td>3、</td>
								<td class="td_hei">批量设置导入员工的用车规则</td>
							</tr>
							<tr>
								<td class="td_right"></td>
								<td colspan="5" class="td_yongche">
									<input name="Fruit" id="import_norule" type="radio" checked>
									无规则&nbsp;&nbsp;&nbsp;
									<input name="Fruit" id="import_rules" type="radio">
									选择用车规则
								</td>
							</tr>
							<tr class="tr_active" id="import_selectrules">
								<td></td>
								<td id="import_rulecon">
								
								</td>
							</tr>
							<tr>
								<td></td>
								<td class="zhuyi">注：每位员工最多可以选择5条用车规则</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="popup_footer">
					<span class="cancel" id="import_cancel">取消</span>
					<span class="sure" id="import_do">导入</span>
				</div>
			</div>
			
			<!-- 进度框 -->
			<div class="batch_import popup" id="import_progress_dialog">
				<div class="popup_title">
					<span id="import_progress_title">提示</span>
					<i class="import_progress_close"></i>
				</div>
				<div class="popup_content" id="progress">
					<div class="progress_c" id="progress_c">
						<div class="progress_v"></div>
					</div>
					<div id="import_message" style="display:none;">
						
					</div>
				</div>
				<div class="popup_footer">
					<span class="sure import_progress_close" id="import_progress_btn">确定</span>
				</div>
			</div>
		</div>
		
		<!--弹窗结束-->
		<!--右键菜单结构-->
		<div id="rMenu" style="position:fixed;">
			<ul>
				<li id="m_addPatent">新增下级部门</li>
				<li id="m_addChild">新增员工</li>
				<li id="m_editNode">编辑</li>
				<li id="m_editNode_user">编辑</li>
				<li id="m_del">删除</li>
				<li id="m_pass">重置密码</li>
				<li id="m_bumen">批量更改部门</li>
				<li id="m_allDel">批量删除</li>
			</ul>
		</div>
	</body>
</html>