<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.szyciov.util.SystemConfig"%>
<%
String ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<!-- 弹窗 start-->
<style>
.tip_box_b label.minute{margin-left:-125px;}
.tip_box_b label.error{margin-left:20px!important;}
.city_container{
	min-width:300px;
	height:210px;
	position:absolute;
	z-index: 9999;
	background: white;
	border:1px solid #e1e1e1;
	font-size: 14px;
	color:#333;
	display: none;
	margin-left: 140px;
	margin-top: 0px;
}
</style>
<div class="pop_box" id="window">
	<form id="form" method="post">
    <div class="tip_box_b form">
        <h3 id="title">新增交接班规则</h3>
        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
        <div class="w400">
        		<div>
	        		<input type="hidden" id="id" name="id"/>
	        		<label for="cityselect" >城市名称<em class="asterisk"></em></label>
	        		<input type="hidden" id="cityid" name="id" style="width:245px;"/>
	        		<input type="text" id="cityname" name="cityname" style="width:245px;" readonly="readonly"/>
        		</div>
        		<div>
	        		<label for="autoshifttime" >自主交班时限<em class="asterisk"></em></label>
					<input type="text" id="autoshifttime" name="autoshifttime" style="width:245px;" maxlength="2"/>
					<label for="autoshifttime"  class="minute">分钟</label>
        		</div>
        		<div>
	                <label for="manualshifttime" >人工指派时限<em class="asterisk"></em></label>
	                <input type="text" id="manualshifttime" name="manualshifttime" style="width:245px;" maxlength="2"/>
					<label for="manualshifttime"  class="minute">分钟</label>
        		</div>
                <button class="Lbtn red" type="button" id="save">保存</button>
                <button class="Lbtn green_a" type="button" id="cancel">取消</button>
        </div>
    </div>
    </form>
</div>
<!-- 弹窗 end-->