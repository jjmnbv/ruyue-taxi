<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.SystemConfig"%>
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
		<title>派发管理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		.tip_box_b input[type=text]{width: 63%;}
		
		/* 添加城市样式 */
		#pubCityaddr{position:absolute;display:inline-block;}
		#pubCityaddr>.addcitybtn{background: white;padding: 2px 10px;color:red;margin-left: 13px;}
		#pubCityaddr .kongjian_list{top:26px!important;}
		#selectuser{position:absolute;display:inline-block;}
		#selectuser>.adduserbtn{background: white;padding: 2px 10px;color:red;margin-left: 13px;}
		/* #selectuser .kongjian_list{top:26px!important;} */
		.added{display:inline-block;padding:2px 4px;}
		.added .ico_x_a{float:right;margin-left: -6px;}
		.addcbox{
		        padding:6px 10px;
		        margin: 0px 30px 10px 120px;
                border: 1px solid #ccc;
                min-height: 60px;
                line-height: 30px;
                width: 636px;
		        }
		.kongjian_list .box .con {max-height: 200px;overflow-y: auto;}
		th, td { white-space: nowrap; }
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
		margin-top:-20px;
		}
		.pop_box{z-index: 1111 !important;}
		
		.tip_box_d #plateNoProvince option{
			font-size:12px;
			padding:0 10px;
		}
		.tip_box_d #plateNoCity option{
			font-size:12px;
			padding:0 10px;
		}
		#pubCityaddr>.city_container{
			margin-left: 12px;
		}
		.ico_x_a{
			display:block;
		}
		#editFormDiv input[type=text]{
		    width:240px;
		}
		#editFormDiv select{width:240px;}
		#editFormDiv input[type=radio]
		{
		    margin-left: 8px;
		}
		.tip_box_d{margin-top: 80px !important;}
		#editFormDiv .Lbtn+.Lbtn {
            margin-top: 10px;
        }
        label.error{
			padding-left: 32%;
			margin-left:0% !important;
			line-height: 100%;
			height: auto;
			float: left;
		}
        #users{
            width:240px;
            display: none;
            position: absolute;
            z-index: 999;
        }
        #userDiv .select2-container {
            width: 240px;
            padding: 0px;
        }
        #fixedstarttime + .error{
            padding-left: 37%;
            padding-right: 0%;
            width: 64%;
        }
         #fixedendtime + .error{
            padding-left: 5%;
            width: 36%;
            padding-right: 0%;
        }
         #sendstarttime + .error{
            padding-left: 21%;
            padding-right: 0%;
            width: 54%;
        }
        #sendendtime + .error{
            padding-left: 0%;
            padding-right: 0%;
            width: 36%;
        } 
        #sendtimeinday + .error{
            padding-left: 23%;
        } 
        .addcbox + .error{
            padding-left: 16% !important;
        }
        .form .col-3{
            width:33%;
        }   
		</style>
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
	</head>
	<body style="overflow-x:hidden;overflow-y:hidden;">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 派发管理
			<button class="SSbtn blue back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-3"><label>派发类别</label>
						<select id="querysendruletype" name="querysendruletype">
							<option value="" selected="selected">全部</option>
							<option value="1">注册返券</option>
							<option value="2">充值返券</option>
							<option value="3">消费返券</option>
							<option value="4">活动返券</option>
						</select>
					</div>
					<div class="col-3" id="brandCarsDiv"><label>发放业务</label>
						<select id="querysendservicetype" name="querysendservicetype" >
							<option value="">全部</option>
							<option value="1">出租车</option>
							<option value="2">网约车</option>
						</select>
					</div>
					<div class="col-3" id="brandCarsDiv"><label>发放对象</label>
						<select id="querysendruletarget" name="querysendruletarget" >
							<option value="">全部</option>
							<option value="2">机构用户</option>
							<option value="1">机构客户</option>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-3"><label>规则状态</label>
						<select id="queryactivystate" name="queryactivystate" >
							<option value="">全部</option>
							<option value="1">待派发</option>
							<option value="2">派发中</option>
							<option value="3">已过期</option>
							<option value="4">已作废</option>
						</select>
					</div>
					<div class="col-3"><label>抵用券名称</label>
					    <input id="queryname" name="queryname" type="text" style="width: 68%;">
					</div>
					<div class="col-3"><label>发放规则</label>
					    <input id="querysendruleidref" name="querysendruleidref" type="text" value="">
					</div>
					
				</div>
				<div class="row">
				   <div class="col-3"><label>使用区域</label>
						<select id="queryusetype" name="queryusetype" >
							<option value="">全部</option>
							<option value="1">限开放区域内可用</option>
							<option value="2">限开通业务城市可用</option>
						</select>
					</div>
				    <div class="col-3"><label>发放时间</label>
						<input style="width:30%;" id="querysendstarttime" name="querysendstarttime" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
						至
			            <input style="width:30%;" id="querysendendtime" name="querysendendtime" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
					</div>
					<div class="col-3" style="text-align: right;">
						<button class="Mbtn green_a" onclick="search();">查询</button>
						<button class="Mbtn gary" onclick="emptys();">清空</button>
					</div>
				</div>
			</div>
		<div class="row">
				<div class="col-12" style="margin-top: 15px;"><h4>抵用券派发信息</h4></div>
		</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		<div class="pop_box" id="editFormDiv" style="display: none;">
		<div class="tip_box_d form" style="max-height: 780px;overflow-y:hidden;overflow-x:hidden;width:800px;">
	            <h3 id="titleForm" style="text-align: center;">新建抵用券</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id" value="" />
	            		<div class="row">
	            			<div class="col-6">
		            			<label>名称<em class="asterisk"></em></label>
	            				<input id="name" name="name" type="text" placeholder="请输入抵用券名称" maxlength="10" />
	            			</div>
	            			<div class="col-6">
	            				<label>发放业务<em class="asterisk"></em></label>
	            				<select id="sendservicetype" name="sendservicetype" >
							        <option value="">请选择</option>
							        <option value="1">出租车</option>
							        <option value="2">网约车</option>
						        </select>
	            			</div>
            			</div>
	            		<div class="row">
	            			<div class="col-6">
	            				<label>发放对象<em class="asterisk"></em></label>
	            				<select id="sendruletarget" name="sendruletarget" >
						        </select>
	            			</div>
	            			<div class="col-6">
	            				<label>发放规则<em class="asterisk"></em></label>
	            				<select id="sendruleidref" name="sendruleidref">
						        </select>
	            			</div>
	            		</div>
	            		<div class="row">
	            			<div class="col-6">
	            				<div style="margin-bottom: 5px;"><label>派发金额<em class="asterisk"></em></label><input type="radio" name="sendmoneytype" value="1" checked="checked" />固定  <input id='sendfixedmoney' name='sendfixedmoney' placeholder="必填" maxlength="4" type="text" style="width:15%;"/> 元</div>
	            				<div><label style="opacity:0;">派发金额<em class="asterisk"></em></label><input type="radio" name="sendmoneytype" value="2"/>随机  
	            				<input id='sendlowmoney' name='sendlowmoney' type="text" maxlength="4" placeholder="必填" style="width:15%;" disabled="disabled"/> - <input id='sendhighmoney' name='sendhighmoney' type="text" maxlength="4" placeholder="必填" style="width:15%;" disabled="disabled" /> 元</div>
	            			</div>
	            		</div>
	            		<div class="row">
	            			<div class="col-6" style="width:72%;">
	            				<label style="width:21%;">发放时间<em class="asterisk"></em></label>
	            				<input style="width:30%;" id="sendstarttime" name="sendstarttime" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
						                    至
			                    <input style="width:30%;" id="sendendtime" name="sendendtime" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
	            			</div>
	            		</div>
	            		<div id ="scopeDiv">
	            		<div class="row">
	            			<div class="col-8" style="padding-left:4.4%;">
								<label style="text-align: left;width: 18%;padding-left: 1.5%;">发放区域<em class="asterisk"></em></label>
		            			<div id="pubCityaddr">
		            				<input type="button" class="addcitybtn" value="+添加城市" style="margin-left: 5px;"/>
								</div>         
	            			</div>
	            		</div>
	            				<input id="businessScope" name="businessScope" type="hidden" value=""/>
	            				<div id ="addcboxId"class="addcbox">           			
	            				</div>
	            				<label for="addcboxId" class="error">请选择发放区域</label>  	
						</div>
						<div class="organuserDiv">
	            		<div class="row">
	            			<div class="col-6">
	            			    <div>
	            				<label>使用区域<em class="asterisk"></em></label>
	            				<input type="radio" name="usetype" value="1" checked="checked" />限发放区域内可用 
	            				</div>
	            				<div>
	            				<label style="opacity:0;">使用区域<em class="asterisk"></em></label>
	            				<input type="radio" name="usetype" value="2"/>限开通业务城市可用
	            				</div>
	            			</div>
	            		</div>
	            		<div class="row">
	            			<div class="col-6" style="width: 72%">
	            				<div><label style="width:20.5%;">有效期<em class="asterisk"></em></label>
	            				     <input type="radio" name="outimetype" value="1" checked="checked" />发放日 至 <input type="text" id="sendtimeinday" name="sendtimeinday" placeholder="必填" maxlength="3" style="width: 15%;margin-bottom: 5px;"/> 天内
	            				</div>
	            				<div><label style="opacity:0;width:21%;">有效期<em class="asterisk"></em></label>
	            				     <input type="radio" name="outimetype" value="2"/>固定期限
	            				     <input style="width:28%;" id="fixedstarttime" name="fixedstarttime" disabled="disabled" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
						                                至
			                         <input style="width:28%" id="fixedendtime" name="fixedendtime" disabled="disabled" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
	            				</div>
	            			</div>
	            		</div>
	            		</div>
	            	</form>
	            	<div class="row" style="margin-left: 40%;">
	            		<div class="col-6" style="line-height: 100%;">
			                <button class="Lbtn red" onclick="save()">提交</button>
			                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
		                </div>
	                </div>
	            </div>
		</div>
		<div class="kongjian_list" id="kongjian_list">
			<div class="box">
				<div class="title">
					<span>ABCDE</span>
					<span>FGHIJ</span>
					<span>KLMNO</span>
					<span>PQRST</span>
					<span>UVWXYZ</span>
				</div>
				<div class="con">

				</div>
				<div class="con">

				</div>
				<div class="con">

				</div>
				<div class="con">

				</div>
				<div class="con">
					
				</div>
			</div> 
		</div>
		<script type="text/javascript" src="js/couponDistribute/index.js"></script>
		<script type="text/javascript" src="js/couponDistribute/letterselect.js"></script>
		<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
	</body>
</html>
