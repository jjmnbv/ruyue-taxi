<%@page import="com.szyciov.util.SystemConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String yingyan_ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>机构订单</title>
    <base href="<%=basePath%>" >
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="content/css/style.css" />
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
    <link rel="stylesheet" type="text/css" href="css/orgordermanage/orderdetail.css" />
    <link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.css" />
    <link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.skinFlat.css" id="styleSrc"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css" />

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
    <script type="text/javascript" src="content/plugins/ion.rangeslider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
    <script type="text/javascript" src="content/plugins/select2/select2.js"></script>
    <script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
    <script type="text/javascript" src="js/basecommon.js"></script>
    <style type="text/css">
        .form select{width:68%;}
        .breadcrumb{text-decoration:underline;}
        .quantext{position: relative;z-index: 2!important;}
        th, td { white-space: nowrap; }
        div.dataTables_wrapper {
            width: $(window).width();
            margin: 0 auto;
        }
        .DTFC_ScrollWrapper{
            margin-top:-20px;
            height: auto!important;
        }
        #orderCostTable td{text-align: center;}
        label.error[for='remark']{margin-left: 0px!important;padding-left: 0px!important;}
        /*样式增加*/
        #commentDataGrid{
            border:none;
            margin:0!important;
            border-collapse:separate;
            border-spacing:0 10px;
        }
        #commentDataGrid_wrapper .col-sm-12{
            padding-top:0;
            padding-bottom:0;
        }
        #commentDataGrid_wrapper .col-sm-6{
            display: none;
        }
        #commentDataGrid .sorting_disabled{
            display:none;
        }
        #commentDataGrid td{
            padding:0;
            border-top:none;
            border: 1px solid #ddd;

        }
    </style>
</head>
<body class="ordermanage_css_body">
<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
    <c:if test="${empty tmp}">
        <a class="breadcrumb" href="OrderManage/PersonOrderIndex">因私网约车订单</a>
    </c:if>
    <c:if test="${not empty tmp}">
        <a class="breadcrumb" href="TmpOrderManage/PersonCurrentOrderIndex">因私网约车订单</a>
    </c:if>
    > 订单详情
    <button class="SSbtn blue back" onclick="window.history.go(-1);">返回</button>
</div>
<div class="content" style="overflow: auto !important;">
    <h2 id="ddxx" style="padding-top: 30px;"></h2>

    <table class="table_a">
        <tr>
            <td class="grey_c">下单人信息</td>
            <td id="xdrxx"></td>
            <td class="grey_c">订单类型</td>
            <td><span id="ddlx" class="font_grey"></span></td>
            <td class="grey_c">用车时间</td>
            <td id="ycsj"></td>
        </tr>
        <tr>
            <td class="grey_c">乘车人信息</td>
            <td><span id="ccrxx" class="font_grey"></span></td>
            <td class="grey_c">上车地址</td>
            <td id="scdz" title=""></td>
            <td class="grey_c">下车地址</td>
            <td id="xcdz" title=""><span class="font_grey"></span></td>
        </tr>
        <tr>
            <td class="grey_c">下单车型</td>
            <td id="xdcx"></td>
            <td class="grey_c">下单时间</td>
            <td><span id="xdsj" class="font_grey"></span></td>
            <td class="grey_c"><span class="font_grey">支付方式</span></td>
            <td><span id="zffs" class="font_grey"></span></td>
        </tr>
        <tr>
            <td class="grey_c">用车事由</td>
            <td colspan=5 style="border-top-style: none;white-space: normal;"><span id="ycsy" class="font_grey"></span></td>
        </tr>
        <tr>
            <td class="grey_c">具体行程</td>
            <td colspan=5><span id="jtxc" class="font_grey"></span></td>
        </tr>
        <tr>
            <td id="hbhtitle" class="grey_c">航班号</td>
            <td id="hbhtd"><span id="hbh" class="font_grey"></span></td>
            <td id="jlsjtitle" class="grey_c">降落时间</td>
            <td id="jlsj"></td>
            <td class="grey_c">下单来源</td>
            <td id="xdly"></td>
        </tr>
        <tr id="qxtr">
            <td class="grey_c">取消时间</td>
            <td id="qxsj"></td>
            <td class="grey_c">取消方</td>
            <td><span id="qxr" class="font_grey"></span></td>
            <td class="grey_c"></td>
            <td></td>
        </tr>
        <tr>
            <td class="grey_c">订单状态</td>
            <td><span id="ddzt" class="font_green"></span></td>
            <td class="grey_c">司机信息</td>
            <td><span id="sjxx" class="font_grey"></span></td>
            <td class="grey_c">车牌号</td>
            <td id="cph"></td>
        </tr>
        <tr id="cxtr">
            <td class="grey_c">实际车型</td>
            <td id="sjcx"></td>
            <td class="grey_c">计费车型</td>
            <td id="jfcx"></td>
            <td class="grey_c">更新时间</td>
            <td><span id="gxsj" class="font_grey"></span></td>
        </tr>
        <tr id="sjtitle">
            <td class="grey_c" id="ssjetd">实时金额</td>
            <td id="ssje"></td>
            <td class="grey_c" id="cffytd">处罚费用</td>
            <td id="cffy"></td>
            <td class="grey_c"></td>
            <td></td>
        </tr>
        <%--<tr class="sjtitle">
            <td class="grey_c" id="sssjbttd">时长费</td>
            <td id="sssjbt"></td>
            <td class="grey_c" id="ksftd">空驶费</td>
            <td id="ksf"></td>
            <td class="grey_c" id="yjftd">夜间费</td>
            <td id="yjf"></td>--%>
        </tr>
    </table>

    <ul class="tabmenu">
        <li class="on"><a href="javascript:void(0)" style="text-decoration: none;">时间轴</a></li>
        <li><a href="javascript:void(0)" style="text-decoration: none;">人工派单记录</a></li>
        <li><a href="javascript:void(0)" style="text-decoration: none;">更换司机记录</a></li>
        <li><a href="javascript:void(0)" style="text-decoration: none;">复核记录</a></li>
        <li><a href="javascript:void(0)" style="text-decoration: none;">客服备注</a></li>
    </ul>
    <ul class="tabbox">
        <li style="display:block">
            <ul class="timeaxis">
                <li style="width: 13%!important;">
                    <div class="quan">下单<br/>时间</div>
                    <div id="xdsjDiv" class="quantext">

                    </div>
                </li>
                <li style="width: 13%!important;">
                    <div id="jdsjIcon" class="quan ing">接单<br/>时间</div>
                    <div id="jdsjDiv" class="quantext">

                    </div>
                </li>
                <li style="width: 13%!important;">
                    <div id="cfsjIcon" class="quan ing">出发<br/>时间</div>
                    <div id="cfsjDiv" class="quantext">

                    </div>
                </li>
                <li style="width: 13%!important;">
                    <div id="ddsjIcon" class="quan ing">抵达<br/>时间</div>
                    <div id="ddsjDiv" class="quantext">

                    </div>
                </li>
                <li style="width: 13%!important;">
                    <div id="kssjIcon" class="quan ing">开始<br/>时间</div>
                    <div id="kssjDiv" class="quantext">

                    </div>
                </li>
                <li style="width: 16%!important;">
                    <div id="jssjIcon" class="quan ing">结束<br/>时间</div>
                    <div id="jssjDiv" class="quantext">

                    </div>
                </li>
                <li style="width: 16%!important;">
                    <div id="wcsjIcon" class="quan ing">完成<br/>时间</div>
                    <div id="wcsjDiv" class="quantext">

                    </div>
                </li>
            </ul>

            <div class="col-8">
                <div id="map_canvas" style="width: auto; height: 500px; border: #ccc solid 1px;"></div>
            </div>
            <div class="col-4">
                <div class="col-12">
                    <label class="control-label" style="font-size:12px;">播放速度</label>
                    <div class="col-12">
                        <input type="text" id="rangeName" name="rangeName" />
                        <input type="hidden" id="rangeFrom" name="rangeFrom" value="100" />
                    </div>
                </div>
                <div class="col-12">
                    <label>
                        <input id="follow" type="checkbox" checked="checked" /><span style="font-size:12px;">画面跟随</span>
                        <input type="hidden" id="txtAddress" />
                    </label>
                </div>
                <div class="col-12">
                    <div class="col-4">
                        <button type="button" class="SSbtn blue" onclick="play()"><i class="fa fa-paste"></i>播放</button>
                    </div>
                    <div class="col-4">
                        <button type="button" class="SSbtn blue" onclick="pause()"><i class="fa fa-paste"></i>暂停</button>
                    </div>
                    <div class="col-4">
                        <button type="button" class="SSbtn blue" onclick="reset()"><i class="fa fa-paste"></i>重播</button>
                    </div>
                </div>
            </div>
        </li>
        <li>
            <div class="col-12">
                <table class="table_a">
                    <tr>
                        <td class="grey_c">派单司机</td>
                        <td id="pdsj"></td>
                        <td class="grey_c">派单时间</td>
                        <td><span id="pdtime" class="font_grey"></span></td>
                        <td class="grey_c">操作人</td>
                        <td id="czr"></td>
                    </tr>
                    <tr>
                        <td class="grey_c">人工派单原因</td>
                        <td colspan="5" style="border-top-style: none;"><span id="rgpdyy" class="font_grey"></span></td>
                    </tr>
                </table>
            </div>
        </li>
        <li>
            <div class="stabox">
                <table id="changeDriverRecorddataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
            </div>
        </li>
        <li>
            <div class="col-12">
                <h2>原始订单费用明细</h2>
                <table class="table_a" id="orderCostTable">
                    <tr>
                        <td class="grey_c">订单金额(元)</td>
                        <td class="grey_c">服务里程(公里)</td>
                        <td class="grey_c">服务时长(分钟)</td>
                        <td class="grey_c">服务开始时间</td>
                        <td class="grey_c">服务结束时间</td>
                        <td class="grey_c">计费时长(分钟)</td>
                        <td class="grey_c">时间补贴(元)</td>
                        <td class="grey_c">里程费(元)</td>
                    </tr>
                    <tr>
                        <td id="ddje"></td>
                        <td id="fwlc"></td>
                        <td id="fwsc"></td>
                        <td id="fwkssj"></td>
                        <td id="fwjssj"></td>
                        <td id="ljsj"></td>
                        <td id="sjbt"></td>
                        <td id="lcf"></td>
                    </tr>
                </table>
            </div>
            <div class="col-12">
                <h2>订单复核记录明细</h2>
                <table id="reviewRecordDataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
            </div>
        </li>
        <li>
            <div class="col-12">
                <button class="SSbtn green" onclick="remark()" style="float: right;">新增备注</button>
                <table id="commentDataGrid" class="table table-striped table-bordered" width="100%" border="0" cellspacing="10" cellpadding="0" ></table>
            </div>
        </li>
    </ul>
</div>

<div class="pop_box" id="orderCommentFormDiv" style="display: none;">
    <div class="tip_box_b" style="width: 500px;">
        <h3>新增备注</h3>
        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
        <div class="w400" style="width: 371px;">
            <form id="orderCommentForm" method="get">
                <div class="row form" style="text-align: left;padding-left: 10px;">
                    <div class="col-12">
                        <label>备注类型<em class="asterisk"></em></label>
                        <input type="radio" name="remarktype" value="0">复核
                        <input type="radio" name="remarktype" value="1">投诉
                        <input type="radio" name="remarktype" value="2">其他
                    </div>
                </div>
                <div class="row form" style="text-align: left;padding-left: 17px;">
                    <label>备注内容<em class="asterisk"></em></label>
                </div>
                <div class="row form" style="text-align: left;padding-left: 47px;">
                    <textarea id="remark" name="remark" rows="2" style="width: 94%;height: 110px;" cols="3" maxlength="100" placeholder="请输入备注内容"></textarea>
                </div>
            </form>
            <span id="cyjets" class="font_red"></span>
            <button class="Lbtn red" onclick="add()">提交</button>
            <button class="Lbtn grey_b" style="margin-left: 10%;" onclick="canel()">取消</button>
        </div>
    </div>
</div>

<div class="pop_box" id="costDetailDiv" style="display: none;">
    <div class="tip_box_b" style="width: 500px;">
        <h3>订单金额明细</h3>
        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
        <div class="w400" style="width: 371px;">
            <form id="costDetailForm" method="get">
                <span id="sfjeDetailTitle">实付金额</span>：<span id="sfjeDetail"></span><br>
                <span id="xcfyDetailTitle">行程费用</span>：<span id="xcfyDetail"></span><br>
                起步价：<span id="qbjDetail"></span><br>
                时长费(<span id="scDetail"></span>)：<span id="scfDetail"></span><br>
                里程费(<span id="lcDetail"></span>)：<span id="lcfDetail"></span><br>
                券已抵扣：<span id="qydkDetail"></span><br>
                计费规则<br>
                起步价：<span id="qbjRule"></span><br>
                里程费：<span id="lcfRule"></span><br>
                时间补贴：<span id="sjbtRule"></span><br>
                夜间费：<br>
                <span id="yjsdRule"></span><span id="yjfRule"></span>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    var orderObj = {
        orderno: "<%=request.getParameter("orderno")%>"
    };
</script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>&s=1"></script>
<script type="text/javascript" src="js/personordermanage/orderdetail.js"></script>
</body>
</html>
