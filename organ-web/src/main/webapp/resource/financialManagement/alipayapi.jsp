<%
/**
 * 即时到账交易接口接入页
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.szyciov.util.SystemConfig"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>支付宝即时到账交易接口</title>
		<script type="text/javascript">
		var out_trade_no = "<%=(String)request.getAttribute("out_trade_no")%>";
		window.top.outtradeno=out_trade_no;
		</script>
	</head>
	<%
	 	String status = (String)request.getAttribute("status");
		if("success".equalsIgnoreCase(status)){
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			String orderno = (String)request.getAttribute("orderno");
			////////////////////////////////////请求参数//////////////////////////////////////
	        //商户订单号，商户网站订单系统中唯一订单号，必填
	        String out_trade_no = (String)request.getAttribute("out_trade_no");
	        //订单名称，必填
	        String subject = (String)request.getAttribute("subject");
	        //付款金额，必填
	        String total_fee = request.getAttribute("total_fee").toString();
	        //商品描述，可空
	        String body = (String)request.getAttribute("body");
	        
	        String partner = (String)request.getAttribute("partner");
	        String notify_url = (String)request.getAttribute("notify_url");
			//////////////////////////////////////////////////////////////////////////////////
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "create_direct_pay_by_user");
	        sParaTemp.put("partner", partner);
	        sParaTemp.put("seller_id", partner);
	        sParaTemp.put("_input_charset", "utf-8");
			sParaTemp.put("payment_type", "1");
			sParaTemp.put("notify_url", notify_url);
			/* sParaTemp.put("return_url", basePath+"resource/zfbreturn?id="+orderno); */
			sParaTemp.put("anti_phishing_key", "");
			sParaTemp.put("exter_invoke_ip", "");
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("subject", subject);
			sParaTemp.put("total_fee", total_fee);
			sParaTemp.put("body", body);
	        //如sParaTemp.put("参数名","参数值");
	        sParaTemp.put("qr_pay_mode", "4");
	        sParaTemp.put("qrcode_width", "300");
			//建立请求
			String sHtmlText = OrderInfoUtil.buildRequest(sParaTemp,"get","确认",(String)request.getAttribute("partner_private_key"));
			out.println(sHtmlText);
		}else{
			String message = (String)request.getAttribute("message");
			out.println((message==null||"".equalsIgnoreCase(message)?"支付出错":message));
		}
	%>
	<body>
	</body>
</html>
