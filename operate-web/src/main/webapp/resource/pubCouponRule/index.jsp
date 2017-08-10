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
		<title>规则管理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>				
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		
	</head>
	
	<style type="text/css">
	    /* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		/* 固定列样式调整  */
		th, td { white-space: nowrap; }
        div.dataTables_wrapper {
            width: $(window).width();
            margin: 0 auto;
        }
        .DTFC_ScrollWrapper{
			margin-top:-20px;
		}
		tbody tr td:first-child{
			text-align:left;
		}
		
		#rechargeDiv label.error{padding-left: 0!important;}
		#frenquencyDiv label.error{padding-left: 0!important;}
		#monetaryDiv label.error{padding-left: 0!important;}
		.tip_box_d {overflow-x:hidden;;overflow-y:hidden;padding-bottom: 4px;}
		.marginvisual1{margin-right: 8px;display: inline-block;margin-top:4px;}
		.marginvisual2{margin-right: 8px;margin-left: 8px;display: inline-block;margin-top:4px;}
		#frenquencyDiv label.error,#monetaryDiv label.error{width: 250%;max-width: 250%;}
	</style>
	
	<body>
		<div class="crumbs">
		   <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 规则管理
		   <button class="SSbtn green_q back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
				<div class="col-4">
					<label>派发类型</label>
					<select id="ruletypeQuery" name="ruletypeQuery">
						<option value="" selected="selected">全部</option>
						<option value="15">注册返券</option>
						<option value="2">充值返券</option>
						<option value="3">消费返券</option>
						<option value="4">活动返券</option>
					</select>
				</div>

				<div class="col-8" style="text-align: right;">
					<button class="Mbtn red_q" onclick="search();">查询</button>
				</div>
				
				<div class="col-12" style="margin-top: 15px;"><h4>发放规则信息</h4></div>
			</div>
			
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="createFormDiv" style="display: none;">
			<div class="tip_box_d form">
	            <h3 id="titleRule" style="text-align: center;">新建发放规则</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div style="max-height:448px;overflow-y:scroll;overflow-x:hidden;padding-bottom: 40px;">
	            	<form id="createForm">
	            		<input type="hidden" id="ruletypevalue" name="ruletypevalue"/>
	            		<input type="hidden" id="id" name="id"/>
	                    
	                    <div class="row">
	                        <div class="col-12">
	                        <div class="col-6">
	            		        <label>规则名称<em class="asterisk"></em></label>
		                        <input id="name" name="name" type="text" placeholder="仅限数字、字母、汉字" style="width: 60%;" maxlength="20">
		                    </div>
		                    <div class="col-6">
		                        <label>派发类别<em class="asterisk"></em></label>
		                        <select id="ruletype" name="ruletype" style="width: 60%;" onchange= "changeRuleType()">
						            <option value="" selected="selected">全部</option>
						            <option value="15">注册返券</option>
						            <option value="2">充值返券</option>
						            <option value="3">消费返券</option>
						            <option value="4">活动返券</option>
					            </select>
		                    </div>
		                    </div>
		                    <div class="col-12">
		                    <div class="col-6">
		                        <label>规则对象<em class="asterisk"></em></label>
		                        <span>个人用户</span>
		                    </div>
		                    <div class="col-6" id="registerTypeDiv" style="display: none;">
		                        <label>规则类型<em class="asterisk"></em></label>
		                        <input id="ruletype11" name="ruletype1" type="radio" style="margin-left:0px;" value="1" checked/>注册&nbsp;&nbsp;<input id="ruletype12" name="ruletype1" type="radio" value="5"/>邀请
		                    </div>
		                    </div>
		                </div>
		                <div class="row" id="expenseDiv" style="display: none;">
		                    <div class="col-12">
		                    <div class="col-6">
		                        <label>规则类型<em class="asterisk"></em></label>
		                        <select id="consumetype" name="consumetype" style="width: 60%" onchange= "changeConsumeType()">
						            <option value="" selected="selected">请选择规则类型</option>
						            <option value="1">按消费频次</option>
						            <option value="2">按消费金额</option>
					            </select>
		                    </div>
		                    </div>
		                    
		                    <div class="row" id="frenquencyDiv" style="display: none;">
			                    <div class="col-12">
		            		        <!-- <label style="width: 16%"></label><span>周期<input id="cycleday1" name="cycleday1" type="text" placeholder="必填" style="width: 12%;" maxlength="3"/>天（连续时间）</span> -->
		            		        <div style="float:left;width: 17.2%">
		                                 <label></label>
		                            </div>
		            		        <div style="float:left;">
		                                 <span class="marginvisual1">周期</span>
		                            </div>
		            		        <div style="float:left;width: 15%">
		                                 <input id="cycleday1" name="cycleday1" type="text" placeholder="必填" style="width: 100%;" maxlength="3" onBlur="overFormat(this)"/>
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">天<span class="font_grey">（连续时间）</span></span> 
		                            </div>
			                    </div>
			                    <div class="col-12">
		            		        <!-- <label style="width: 13%"></label><input id="consumefrequencytype1" name="consumefrequencytype" type="radio" value="1" onchange="changeConsumeFrequencyType()" checked/>累计消费次数满<input id="consumelowtimes1" name="consumelowtimes1" type="text" placeholder="必填" style="width: 12%;" maxlength="3">次 -->		            		        
		            		        <div style="float:left;width: 13.5%">
		                                 <label></label>
		                            </div>
		                            <div style="float:left;margin-top:3px;">
		                                 <input id="consumefrequencytype1" name="consumefrequencytype" type="radio" value="1" onchange="changeConsumeFrequencyType()" checked/>
		                            </div>
		            		        <div style="float:left;">
		                                 <span class="marginvisual1">累计消费次数满</span>
		                            </div>
		            		        <div style="float:left;width: 15%">
		                                 <input id="consumelowtimes1" name="consumelowtimes1" type="text" placeholder="必填" style="width: 100%;" maxlength="3" onBlur="overFormat(this)"/>
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">次</span>
		                            </div>
			                    </div>
			                    <div class="col-12">
		            		        <!-- <label style="width: 13%"></label><input id="consumefrequencytype2" name="consumefrequencytype" type="radio" value="2" onchange="changeConsumeFrequencyType()"/>累计消费次数满<input id="consumelowtimes2" name="consumelowtimes2" type="text" placeholder="必填" style="width: 12%;" maxlength="3">次低于<input id="consumehightimes1" name="consumehightimes1" type="text" placeholder="必填" style="width: 12%;" maxlength="3">次 -->		            		        
		            		        <div style="float:left;width: 13.5%">
		                                 <label></label>
		                            </div>
		                            <div style="float:left;margin-top:3px;">
		                                 <input id="consumefrequencytype2" name="consumefrequencytype" type="radio" value="2" onchange="changeConsumeFrequencyType()"/>
		                            </div>
		            		        <div style="float:left;">
		                                 <span class="marginvisual1">累计消费次数满</span>
		                            </div>
		            		        <div style="float:left;width: 15%">
		                                 <input id="consumelowtimes2" name="consumelowtimes2" type="text" placeholder="必填" style="width: 100%;" maxlength="3" onBlur="overFormat(this)">
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">次低于</span>
		                            </div>
		                            <div style="float:left;width: 15%">
		                                 <input id="consumehightimes1" name="consumehightimes1" type="text" placeholder="必填" style="width: 100%;" maxlength="3" onBlur="overFormat(this)">
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">次</span>
		                            </div>
			                    </div>
			                    <div class="col-12">
		            		        <!-- <label style="width: 13%"></label><input id="consumefrequencytype3" name="consumefrequencytype" type="radio" value="3" onchange="changeConsumeFrequencyType()"/>累计消费次数低于<input id="consumehightimes2" name="consumehightimes2" type="text" placeholder="必填" style="width: 12%;" maxlength="3">次 -->		            		        
		            		        <div style="float:left;width: 13.5%">
		                                 <label></label>
		                            </div>
		                            <div style="float:left;margin-top:3px;">
		                                 <input id="consumefrequencytype3" name="consumefrequencytype" type="radio" value="3" onchange="changeConsumeFrequencyType()"/>
		                            </div>
		            		        <div style="float:left;">
		                                 <span class="marginvisual1">累计消费次数低于</span>
		                            </div>
		            		        <div style="float:left;width: 15%">
		                                 <input id="consumehightimes2" name="consumehightimes2" type="text" placeholder="必填" style="width: 100%;" maxlength="3" onBlur="overFormat(this)">
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">次</span>
		                            </div>
			                    </div>
		                    </div>
		                    
		                    <div class="row" id="monetaryDiv" style="display: none;">
		                        <div class="col-12">
		            		        <label style="width: 17%"></label><input id="consumemoneysingleable" name="consumemoneysingleable" type="checkbox" onchange="changeCheckbox1()"/>单次消费金额返券
			                    </div>
			                    <div class="col-12">
		            		        <!-- <label style="width: 18.5%"></label><span>单次消费金额满<input id="consumemoneysingelfull" name="consumemoneysingelfull" type="text" placeholder="必填" style="width: 12%;" maxlength="6"/>元</span> -->		            		        
		            		        <div style="float:left;width: 19.6%">
		                                 <label></label>
		                            </div>
		            		        <div style="float:left;">
		                                 <span class="marginvisual1">单次消费金额满</span>
		                            </div>
		            		        <div style="float:left;width: 15%">
		                                 <input id="consumemoneysingelfull" name="consumemoneysingelfull" type="text" placeholder="必填" style="width: 100%;" maxlength="6" onBlur="overFormat(this)"/>
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">元</span>
		                            </div>
			                    </div>
			                    
			                    <div class="col-12">
		            		        <label style="width: 17%"></label><input id="consumemoneycycleable" name="consumemoneycycleable" type="checkbox" onchange="changeCheckbox2()"/>周期消费总额返券
			                    </div>
			                    <div class="row">
			                    <div class="col-12">
		            		        <!-- <label style="width: 19.5%"></label><span>周期<input id="cycleday2" name="cycleday2" type="text" placeholder="必填" style="width: 12%;" maxlength="3"/>天（连续时间）</span> -->		            		        
		            		        <div style="float:left;width: 20.6%">
		                                 <label></label>
		                            </div>
		            		        <div style="float:left;">
		                                 <span class="marginvisual1">周期</span>
		                            </div>
		            		        <div style="float:left;width: 15%">
		                                 <input id="cycleday2" name="cycleday2" type="text" placeholder="必填" style="width: 100%;" maxlength="3" onBlur="overFormat(this)"/>
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">天<span class="font_grey">（连续时间）</span></span>
		                            </div>
			                    </div>
			                    <div class="col-12">
		            		        <!-- <label style="width: 16.5%"></label><input id="consumemoneycycletype1" name="consumemoneycycletype" type="radio" value="1" onchange="changeConsumeMoneyCycleType()" checked/>累计消费金额满<input id="consumemoneycyclelow1" name="consumemoneycyclelow1" type="text" placeholder="必填" style="width: 12%;" maxlength="6">元 -->		            		        
		            		        <div style="float:left;width: 17%">
		                                 <label></label>
		                            </div>
		                            <div style="float:left;margin-top:3px;">
		                                 <input id="consumemoneycycletype1" name="consumemoneycycletype" type="radio" value="1" onchange="changeConsumeMoneyCycleType()" checked/>
		                            </div>
		            		        <div style="float:left;">
		                                 <span class="marginvisual1">累计消费金额满</span>
		                            </div>
		            		        <div style="float:left;width: 15%">
		                                 <input id="consumemoneycyclelow1" name="consumemoneycyclelow1" type="text" placeholder="必填" style="width: 100%;" maxlength="6" onBlur="overFormat(this)">
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">元</span>
		                            </div>
			                    </div>
			                    <div class="col-12">
		            		        <!-- <label style="width: 16.5%"></label><input id="consumemoneycycletype2" name="consumemoneycycletype" type="radio" value="2" onchange="changeConsumeMoneyCycleType()"/>累计消费金额满<input id="consumemoneycyclelow2" name="consumemoneycyclelow2" type="text" placeholder="必填" style="width: 12%;" maxlength="6">元低于<input id="consumemoneycyclefull1" name="consumemoneycyclefull1" type="text" placeholder="必填" style="width: 12%;" maxlength="6">元 -->		            		        
		            		        <div style="float:left;width: 17%">
		                                 <label></label>
		                            </div>
		                            <div style="float:left;margin-top:3px;">
		                                 <input id="consumemoneycycletype2" name="consumemoneycycletype" type="radio" value="2" onchange="changeConsumeMoneyCycleType()"/>
		                            </div>
		            		        <div style="float:left;">
		                                 <span class="marginvisual1">累计消费金额满</span>
		                            </div>
		            		        <div style="float:left;width: 15%">
		                                 <input id="consumemoneycyclelow2" name="consumemoneycyclelow2" type="text" placeholder="必填" style="width: 100%;" maxlength="6" onBlur="overFormat(this)">
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">元低于</span>
		                            </div>
		                            <div style="float:left;width: 15%">
		                                 <input id="consumemoneycyclefull1" name="consumemoneycyclefull1" type="text" placeholder="必填" style="width: 100%;" maxlength="6" onBlur="overFormat(this)">
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">元</span>
		                            </div>
			                    </div>
			                    <div class="col-12">
		            		        <!-- <label style="width: 16.5%"></label><input id="consumemoneycycletype3" name="consumemoneycycletype" type="radio" value="3" onchange="changeConsumeMoneyCycleType()"/>累计消费金额低于<input id="consumemoneycyclefull2" name="consumemoneycyclefull2" type="text" placeholder="必填" style="width: 12%;" maxlength="6">元 -->		            		        
		            		        <div style="float:left;width: 17%">
		                                 <label></label>
		                            </div>
		                            <div style="float:left;margin-top:3px;">
		                                 <input id="consumemoneycycletype3" name="consumemoneycycletype" type="radio" value="3" onchange="changeConsumeMoneyCycleType()"/>
		                            </div>
		            		        <div style="float:left;">
		                                 <span class="marginvisual1">累计消费金额低于</span>
		                            </div>
		            		        <div style="float:left;width: 15%">
		                                 <input id="consumemoneycyclefull2" name="consumemoneycyclefull2" type="text" placeholder="必填" style="width: 100%;" maxlength="6" onBlur="overFormat(this)">
		                            </div>
		                            <div style="float:left;">
		                                 <span class="marginvisual2">元</span>
		                            </div>
			                    </div>
		                        </div>
		                    </div>
		                </div>
		                <div id="rechargeDiv" style="display: none;">
		                    <div class="col-12">
		                        <!-- <label style="width: 14%"></label><span>充值金额满<input id="rechargemoney" name="rechargemoney" type="text" placeholder="必填" style="width: 12%;" maxlength="6"/>元</span> -->
		                        <div style="float:left;width: 15%">
		                             <label></label>
		                        </div>
		                        <div style="float:left;">
		                             <span class="marginvisual1">充值金额满</span>
		                        </div>
		                        <div style="float:left;width: 15%">
		                             <input id="rechargemoney" name="rechargemoney" type="text" placeholder="必填" style="width: 100%;" maxlength="6" onBlur="overFormat(this)"/>
		                        </div>
		                        <div style="float:left;">
		                             <span class="marginvisual2">元</span>
		                        </div>
			                </div>
		                </div>
	            	</form>
	            	<div class="row" style="text-align: center;">
	                <button class="Lbtn red" onclick="save()">提交</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	                </div>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/pubCouponRule/index.js"></script>
	</body>
</html>
