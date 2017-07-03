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
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>新增规则</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css">
		<link rel="stylesheet" type="text/css" href="css/individualAccountRules/createRules.css">
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		
	</head>
	
	<style type="text/css">
       /* 前端对于类似页面的补充 */
		.form select{width:67%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.breadcrumb{text-decoration:underline;}
       
        .radius{border:1px solid #ddd;border-radius:5px;text-align:center;}
        .col-4 .radius{padding:30px 0;margin-top:10px;}
        #dateDiv label{width:100%;padding-left:20px;}
        #dateVisual{text-align:left;margin-left:20px;}

        #validdatediv input[type=text]{width: 100%;}    
        #validdatediv label{width: 100%;}
        #validdatediv label.error{padding-left:0%;}
        
        #selectForm .rules_css_col_1 .col-5 label:first-child{
        	width:31%;
        }
        .Sbtn{
        	padding:7px 11px;
        }
         #validdatediv .rules_css_div_2 label.error,
         #validdatediv .rules_css_div_3 label.error{
        	width:138%;
        }
        @media screen and (min-width: 110px) and (max-width: 1250px) {
		  	#selectForm .rules_css_col_1 .col-5{
		  		width:39%;
		  	}
		  	#selectForm .rules_css_col_1 .col-2{
		  		width:20%;
		  	}
        }
        @media screen and (min-width: 790px) and (max-width: 1110px) {
		  	#selectForm .rules_css_col_1 .col-5{
		  		width:39%;
		  	}
		  	#selectForm .rules_css_col_1 .col-2{
		  		width:20%;
		  	}
		  	#selectForm .rules_css_col_1 .col-5 label:first-child{
		  		width:47%;
		  	}
		  	#selectForm .rules_css_col_1 .col-5 label.error{
		  		padding-left:0;
		  		margin-left:48%;
		  	}
		  	#selectForm .rules_css_col_1 .col-5 select{
		  		width:50%;
		  	}
		  	#selectForm .rules_css_col_1 .col-5 .select2-container{
		  		width:53%;
		  	}
		  	#selectForm .rules_css_col_1 .col-2 button{
		  		padding:7px 0;
		  	}
		  	#selectForm .rules_css_col_2 .col-5{
		  		width:80%;
		  	}
		  	#selectForm .rules_css_div_1{
		  		width:22% !important;
		  	}
		  	#selectForm .rules_css_div_2{
			    margin-left: 4px;
		  	}
		  	#selectForm .rules_css_div_3{
		  		
		  	}
        }
   	  	#organId option{
	  		padding:2px 10px;
	  	}
	  	
	</style>
	
	<body>
		<div class="crumbs">
		   <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="IndividualAccountRules/Index">客户计费规则</a> > 新增规则
		   <button class="SSbtn blue back" onclick="back()">返回</button>		   
		</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
			  <form id="selectForm">
			  <div class="col-9">
			  <div class="col-12 rules_css_col_1">	
				<div class="col-5">
					<label>机构名称<em class="asterisk"></em></label>
					<select id="organId" name="organId" onchange= "changeOrgan()">
						<option value="" selected="selected">请选择</option>
						<c:forEach var="orgOrgan" items="${orgOrgan}">
							<option value="${orgOrgan.id}">${orgOrgan.shortName}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="col-5">
					<label>城市名称<em class="asterisk"></em></label><input id="city" name="city" type="text" placeholder="请选择">
				</div>
				
				<div class="col-2" style="text-align: left;">
				    <button type="button" class="Sbtn orange" onclick="addCity()">添加</button>
					<button type="button" class="Sbtn orange" onclick="removeCity()">删除</button>
			    </div>
			  </div>
			  <div class="col-12 rules_css_col_2">  
			    <div class="col-5" id="validdatediv">
					<div class="rules_css_div_1" style="float:left;width: 30%;">
					    <label>有效期限<em class="asterisk"></em></label>
					</div>
					<div class="rules_css_div_2" style="float:left;width: 32.5%;">
					    <input id="startTime" name="startTime" type="text" placeholder="起始时间" value="" class="searchDate" readonly="readonly">
					</div>
					<div style="float:left;text-align:center;">
					    至
					</div>
					<div class="rules_css_div_3" style="float:left;width: 32.5%;">
					    <input id="endTime" name="endTime" type="text" placeholder="结束时间" value="" class="searchDate" readonly="readonly">
					</div>
				</div>
				
				<div id="dateDiv" class="col-7">
					<label id="dateVisual"></label>
				</div>
			  </div>
			  </div>

				<div class="col-3" id="t_button" style="text-align: left;">
					<input type="hidden" id="selectCityId" name="selectCityId" value="">
					<!-- <textarea class="col-3" cols="50" rows="3"  id="cityName" name="cityName" placeholder="" value="">${pubDriver.serviceOrgName}</textarea> -->
					<ul id="cityName" class="select_content" style="display:block;height:100px;width:90%;overflow:auto;position:inherit;"></ul>
				</div>
			  </form>
			</div>
			
			<div class="row">
			   <div class="col-12" style="float:left;border-bottom: 1px solid #ccc; display: none;" id="productqxfxDiv">
			   <label style="font-size:25px;">产品服务</label><input name="ze" type="checkbox" id="qx" value="" onclick="qxProductHander(this)" style="margin-left:20px;"/>全选  
			   </div>

			   <div class="row" id="rulesDiv"></div>

			   <div class="col-12" style="text-align: right; display: none;width:90%;" id="buttonDiv">
                     <button class="Mbtn orange" style="margin-right: 20px;" onclick="save()">保存</button>
                     <button class="Mbtn grey" onclick="back()">取消</button>
               </div>	
			   
			</div>

		</div>

		<div class="pop_box" id="reminderDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3>温馨提示</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="reminderForm" method="get" class="form-horizontal  m-t" id="frmmodal">

	            		<label></label> 
	            		<label style="width:80%;">您当前未点击【保存】，确认放弃保存吗？</label>
		                <label></label>
	            	</form>
	                <button class="Mbtn orange" onclick="canel()">返回保存</button>
	                <button class="Mbtn grey" style="margin-left: 10%;" onclick="saveBack()">放弃保存</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/individualAccountRules/createRules.js"></script>
	</body>
</html>
