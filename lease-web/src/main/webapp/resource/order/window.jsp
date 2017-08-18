<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.szyciov.util.SystemConfig"%>
<%
String ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<style>
    div.dataTables_wrapper div.dataTables_info{
        text-align: left;
    }
</style>
<!-- 下单人弹窗 start-->
<div class="pop_box orderman">
    <div class="tip_box_c" style="padding:40px;padding-bottom:20px;">
        <div class="row form">
            <div class="col-5">
            	<label style="text-align: left;width: auto;">关键字</label><input type="text" placeholder="手机号码/姓名" name="keyword" id="orderman_keyword"/>
            </div>
            <div class="col-5"><label style="width:auto;">机构名称<em class="asterisk"></em></label>
            	<input type="hidden" value="0" id="isUserFav"/>
				<select name="organ" id="organ" style="width:59%;">
					<option value="">请选择</option>
					<c:forEach var="item" items="${orglist}">
						<option value="${item.id}" data="org_pub">${item.fullName}</option>
					</c:forEach>
					<c:forEach var="item" items="${priOrganList}">
						<option value="${item.id}" data="org_pri">${item.fullName}</option>
					</c:forEach>
				</select>
			</div>
            <div  class="col-2" style="text-align: right;">
                <button class="Mbtn green_a" type="button" id="orderman_search">查询</button>
            </div>
        </div>
        <div class="row">
        	<div  class="col-12" >
        		<table id="orderman" class="table table-bordered" cellspacing="0" width="100%"></table>
        	</div>
        </div>
        <div class="row">
            <div  class="col-12" style="text-align: right;">
            	<button class="Mbtn blue" type="button" id="orderman_confirm">确定</button>
                <button class="Mbtn red" type="button" id="orderman_cancel">关闭</button>
            </div>
        </div>
        
    </div>
</div>
<!-- 下单人弹窗 end-->
<!-- 常用联系人弹窗 start-->
<div class="pop_box favorite">
    <div class="tip_box_a" style="width:650px;margin:120px auto auto auto;">
        <div class="row form">
            <div class="col-6" style="text-align: left;margin:auto;">
	            <label style="margin-left:10px;text-align:left;">关键字</label>
	            <input type="text" placeholder="手机号码/姓名" name="keyword" id="favorite_keyword" style="width:195px;margin-left:-40px;"maxlength="11"/>
            </div>
            <div  class="col-6" style="text-align: right;padding-right:20px;"><button class="Mbtn green_a" type="button" id="favorite_search">查询</button></div>
        </div>
        <div class="row" style="margin:auto;">
        	<div  class="col-12" >
        		<table id="favorite" class="table table-bordered" cellspacing="0" style="width:100%;margin:auto;"></table>
        	</div>
        </div>
        <div class="row" style="margin:auto;">
            <div  class="col-12" style="text-align: right;">
            	<button class="Mbtn blue" type="button" id="favorite_confirm">确定</button>
                <button class="Mbtn red" type="button" id="favorite_cancel">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- 常用联系人弹窗 end-->
<!-- 派单弹窗 start-->
<div class="pop_box" id="sendorder">
    <div class="tip_box_b form">
        <h3>订单提交成功</h3>
        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
        <div class="w400">
                <img src="img/order/bg_zhengzaipaidan.png" alt="正在派单...">
                <br/><br/>
                <div class="hint_content">正在进行系统派单...</div>
                <button class="Lbtn green_a" type="button">确定</button>
                <button class="Lbtn green_a" type="button" style="display:none;">人工派单</button>
                <button class="Lbtn red" type="button">取消订单</button>
        </div>
    </div>
</div>
<!-- 派单弹窗 end-->
<!-- 余额不足弹窗 start-->
<div class="pop_box" id="unpayable">
    <div class="tip_box_b form">
        <h3>提示</h3>
        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
        <div class="w400">
    		机构账户余额不足,不可继续下单。
            <br>
            <button class="Lbtn red" type="button">我知道了</button>
        </div>
    </div>
</div>
<!-- 余额不足弹窗 end-->
<!-- 百度地图弹窗 start-->
<div class="pop_box" id="map">
  <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=<%=ak%>&s=1"></script>
  <div class="tip_box_c">
        <div class="row form">
            <div class="col-12"><label style="min-width:6%; width:auto;">搜索地址</label><input type="text" style="max-width:94%;width:89%;" placeholder="详细地址" name="keyword" id="suggest"/></div>
        </div>
        <div class="row">
        	<div  class="col-12" style="text-align: right;">
		        <!--百度地图容器-->
		  		<div style="width:100%;height:300px;border:#ccc solid 1px;" id="dituContent"></div>
		  		<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
	  		</div>
  		</div>
  		<div class="row">
            <div  class="col-12" style="text-align: right;">
	            <button class="Mbtn green_a" type="button" id="map_confirm">确定</button>
	            <button class="Mbtn green_a" type="button" id="map_cancel">取消</button>
        	</div>
        </div>
  </div>
</div>
<!-- 百度地图弹窗 end-->
<!-- 为填写下单人弹窗 start-->
<div class="pop_box" id="nouserid">
    <div class="tip_box_b form">
        <h3>提示</h3>
        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
        <div class="w400">
    		请先填写下单人信息
            <br>
            <button class="Lbtn red" type="button">关闭</button>
        </div>
    </div>
</div>
<!-- 为填写下单人弹窗 end-->

<div class="pop_box" id="noaddress">
    <div class="tip_box_b form">
        <h3>提示</h3>
        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
        <div class="w400">
    		请选择地址
            <br>
            <button class="Lbtn red" type="button">我知道了</button>
        </div>
    </div>
</div>

<!-- 人工选择司机弹窗 start-->
<div class="pop_box" id="manualSelectDriverPop">
    <div class="tip_box_a" style="width:1200px;margin:60px auto auto auto;">
        <div class="row form">
            <div class="col-3">
                <label class="col-3" style="text-align:left;">资格证号</label>
                <input id="manualDriverJobNum" class="col-9" type="text" placeholder="资格证号" maxlength="18"/>
            </div>
            <div class="col-3">
                <label class="col-3" style="text-align:left;">手机号</label>
                <input id="manualDriverPhone" class="col-9" type="text" placeholder="手机号" maxlength="11"/>
            </div>
            <div class="col-3">
                <label class="col-3" style="text-align:left;">车牌号</label>
                <input id="manualDriverPlateNo" class="col-9" type="text" placeholder="车牌号"/>
            </div>
            <div class="col-3" >
                <button id="manualSelectDriverPopSearch" class="Mbtn green_a" style="float: right;margin-right: 12px;" type="button">查询</button>
            </div>
        </div>
        <div class="row">
            <div  class="col-12" >
                <table id="manualSelectDriverPopTable" class="table table-bordered" cellspacing="0" width="100%"></table>
            </div>
        </div>
        <div class="row" style="margin:auto;">
            <div  class="col-12" style="text-align: right;">
                <button class="Mbtn blue confirmPop" type="button">确定</button>
                <button class="Mbtn red closePop" type="button">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- 人工选择司机弹窗 end-->
<!-- 取消责任方弹窗 start -->
<div class="pop_box" id="cancelpartyFormDiv" style="display: none;">
	<div class="tip_box_b" style="width: 600px;">
		<h3>取消订单</h3>
		<img src="content/img/btn_guanbi.png" class="close" alt="关闭">
		<div style="width: 540px">
			<input type="hidden" id="identifyingHide">
            <input type="hidden" id="orderstatusHide">
			<form id="cancelpartyForm" method="get" class="form">
				<div class="row" style="padding-bottom: 18px">
					<label style="float: left;">责任方<em class="asterisk"></em></label> 
					<select id="dutyparty" name="dutyparty" style="width: 60%" onchange="showCancelreason();">
						<option value="">请选择</option>
						<option value="1">乘客</option>
						<option value="3">客服</option>
						<option value="4">平台</option>
					</select>
				</div>
				<div class="row" style="padding-bottom: 18px">
					<label style="float: left;">取消原因<em class="asterisk"></em></label>
					<select id="cancelreason" name="cancelreason" style="width: 60%" onchange="showDutyparty();">
						<option value="">请选择</option>
						<option value="1">不再需要用车</option>
						<option value="5">业务操作错误</option>
						<option value="6">暂停供车服务</option>
					</select>
				</div>
				<div id="cancelDetail" style="margin-left: 40px;"></div>
			</form>
			<button class="Lbtn red" >提交</button>
			<button class="Lbtn grey" style="margin-left: 10%;">取消</button>
		</div>
	</div>
</div>
<!-- 取消责任方弹窗 end -->