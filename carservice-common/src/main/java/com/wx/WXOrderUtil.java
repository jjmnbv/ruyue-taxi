package com.wx;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.szyciov.util.MD5;
import com.szyciov.util.NotifyUtil;
import com.szyciov.util.UNID;

public class WXOrderUtil {
	
	static Logger logger = Logger.getLogger(WXOrderUtil.class);

	/**
	 * 构建统一下单参数
	 * @param appid
	 * @param mch_id
	 * @param body
	 * @param out_trade_no
	 * @param total_fee
	 * @param notify_url
	 * @param trade_type
	 * @return
	 */
	public static Map<String,String> createPreOrderParam(String appid,String mch_id,String body,String out_trade_no,String total_fee,String notify_url,String trade_type,String ipadd){
		Map<String,String> preorderinfo = new HashMap<String,String>();
		preorderinfo.put("appid", appid);
		preorderinfo.put("mch_id", mch_id);
		preorderinfo.put("nonce_str", UNID.getUNID(appid, 32));
		preorderinfo.put("body", body);
		preorderinfo.put("out_trade_no", out_trade_no);
		preorderinfo.put("total_fee", total_fee);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date starttime = new Date();
		preorderinfo.put("time_start", format.format(starttime));
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 2);
		preorderinfo.put("time_expire", format.format(calendar.getTime()));
		preorderinfo.put("spbill_create_ip", StringUtils.isNotBlank(ipadd)?ipadd:"0.0.0.0");
		preorderinfo.put("notify_url", notify_url);
		preorderinfo.put("trade_type", trade_type);
		return preorderinfo;
	}
	
	public static String getSign(Map<String,String> params,String key){
		//过滤空值、sign与sign_type参数,微信也需要排序可统一使用支付宝提供的工具类
    	Map<String, String> sParaNew = NotifyUtil.paraFilter(params);
        //获取待签名字符串
        String preSignStr = NotifyUtil.createLinkString(sParaNew);
        String sign = MD5.MD5Encode(preSignStr+"&key="+key).toUpperCase();
        return sign;
	}
	
	/**
	 * 统一下单请求的xml对象
	 * @param preorderparam
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static Document createPreOrderInfo(Map<String,String> preorderparam, String sign)throws Exception {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder=factory.newDocumentBuilder();  
		Document doc = builder.newDocument();
		Element root = doc.createElement("xml");
		//appid
		Element appid = doc.createElement("appid");
		CDATASection appid_data = doc.createCDATASection(preorderparam.get("appid"));
		appid.appendChild(appid_data);
		root.appendChild(appid);
		//mch_id
		Element mch_id = doc.createElement("mch_id");
		CDATASection mch_id_data = doc.createCDATASection(preorderparam.get("mch_id"));
		mch_id.appendChild(mch_id_data);
		root.appendChild(mch_id);
		//nonce_str
		Element nonce_str = doc.createElement("nonce_str");
		CDATASection nonce_str_data = doc.createCDATASection(preorderparam.get("nonce_str"));
		nonce_str.appendChild(nonce_str_data);
		root.appendChild(nonce_str);
		//body
		Element body = doc.createElement("body");
		CDATASection body_data = doc.createCDATASection(preorderparam.get("body"));
		body.appendChild(body_data);
		root.appendChild(body);
		//out_trade_no
		Element out_trade_no = doc.createElement("out_trade_no");
		CDATASection out_trade_no_data = doc.createCDATASection(preorderparam.get("out_trade_no"));
		out_trade_no.appendChild(out_trade_no_data);
		root.appendChild(out_trade_no);
		//total_fee
		Element total_fee = doc.createElement("total_fee");
		CDATASection total_fee_data = doc.createCDATASection(preorderparam.get("total_fee"));
		total_fee.appendChild(total_fee_data);
		root.appendChild(total_fee);
		//spbill_create_ip
		Element spbill_create_ip = doc.createElement("spbill_create_ip");
		CDATASection spbill_create_ip_data = doc.createCDATASection(preorderparam.get("spbill_create_ip"));
		spbill_create_ip.appendChild(spbill_create_ip_data);
		root.appendChild(spbill_create_ip);
		//time_expire
		Element time_expire = doc.createElement("time_expire");
		CDATASection time_expire_data = doc.createCDATASection(preorderparam.get("time_expire"));
		time_expire.appendChild(time_expire_data);
		root.appendChild(time_expire);
		//time_start
		Element time_start = doc.createElement("time_start");
		CDATASection time_start_data = doc.createCDATASection(preorderparam.get("time_start"));
		time_start.appendChild(time_start_data);
		root.appendChild(time_start);
		//notify_url
		Element notify_url = doc.createElement("notify_url");
		CDATASection notify_url_data = doc.createCDATASection(preorderparam.get("notify_url"));
		notify_url.appendChild(notify_url_data);
		root.appendChild(notify_url);
		//trade_type
		Element trade_type = doc.createElement("trade_type");
		CDATASection trade_type_data = doc.createCDATASection(preorderparam.get("trade_type"));
		trade_type.appendChild(trade_type_data);
		root.appendChild(trade_type);
		//sign
		Element sign_n = doc.createElement("sign");
		CDATASection sign_data = doc.createCDATASection(sign);
		sign_n.appendChild(sign_data);
		root.appendChild(sign_n);
		
		doc.appendChild(root);
		return doc;
	}
	
	/**
	 * 构建支付参数
	 * @param appid
	 * @param partnerid
	 * @param prepayid
	 * @return
	 */
	public static Map<String,String> createPayOrderParam(String appid,String partnerid,String prepayid){
		Map<String,String> preorderinfo = new HashMap<String,String>();
		preorderinfo.put("appid", appid);
		preorderinfo.put("partnerid", partnerid);
		preorderinfo.put("prepayid", prepayid);
		preorderinfo.put("package", "Sign=WXPay");
		preorderinfo.put("noncestr", UNID.getUNID(appid, 32));
		preorderinfo.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
		return preorderinfo;
	}
	
	/**
	 * 构建支付xml信息签名后
	 * @param payorderparam
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static Document createPayOrderInfo(Map<String,String> payorderparam,String sign) throws Exception{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder=factory.newDocumentBuilder();  
		Document doc = builder.newDocument();
		Element root = doc.createElement("xml");
		//appid
		Element appid = doc.createElement("appid");
		CDATASection appid_data = doc.createCDATASection(payorderparam.get("appid"));
		appid.appendChild(appid_data);
		root.appendChild(appid);
		//noncestr
		Element noncestr = doc.createElement("noncestr");
		CDATASection noncestr_data = doc.createCDATASection(payorderparam.get("noncestr"));
		noncestr.appendChild(noncestr_data);
		root.appendChild(noncestr);
		//package
		Element package_n = doc.createElement("package");
		CDATASection package_n_data = doc.createCDATASection(payorderparam.get("package"));
		package_n.appendChild(package_n_data);
		root.appendChild(package_n);
		//partnerid
		Element partnerid = doc.createElement("partnerid");
		CDATASection partnerid_data = doc.createCDATASection(payorderparam.get("partnerid"));
		partnerid.appendChild(partnerid_data);
		root.appendChild(partnerid);
		//prepayid
		Element prepayid = doc.createElement("prepayid");
		CDATASection prepayid_data = doc.createCDATASection(payorderparam.get("prepayid"));
		prepayid.appendChild(prepayid_data);
		root.appendChild(prepayid);
		//timestamp
		Element timestamp = doc.createElement("timestamp");
		CDATASection timestamp_data = doc.createCDATASection(payorderparam.get("timestamp"));
		timestamp.appendChild(timestamp_data);
		root.appendChild(timestamp);
		//sign
		Element sign_n = doc.createElement("sign");
		CDATASection sign_data = doc.createCDATASection(sign);
		sign_n.appendChild(sign_data);
		root.appendChild(sign_n);
		doc.appendChild(root);
		return doc;
	}
	
	/**
	 * 获取统一下单结果
	 * @param preorderparam
	 * @param preorderurl
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> getPreOrderId(String preorderparam,String preorderurl) throws Exception{
		Map<String,String> res = new HashMap<String,String>();
		res.put("status", "1");
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(preorderurl);
		StringEntity entity = new StringEntity(preorderparam,"application/xml","UTF-8");
		httppost.setEntity(entity);
		HttpResponse response = httpclient.execute(httppost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {
			HttpEntity resEntity = response.getEntity();
			String resultstr = EntityUtils.toString(resEntity,"utf-8");
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
			DocumentBuilder builder=factory.newDocumentBuilder();
			ByteArrayInputStream resinput = new ByteArrayInputStream(resultstr.getBytes());
            Document doc = builder.parse(resinput);
        	Node return_code = doc.getElementsByTagName("return_code").item(0);
        	if(return_code!=null){
        		if("SUCCESS".equalsIgnoreCase(return_code.getFirstChild().getNodeValue())){
        			Node result_code = doc.getElementsByTagName("result_code").item(0);
        			if(result_code!=null&&"SUCCESS".equalsIgnoreCase(result_code.getFirstChild().getNodeValue())){
        				Node prepay_id_node = doc.getElementsByTagName("prepay_id").item(0);
            			if(prepay_id_node!=null){
            				res.put("prepay_id", prepay_id_node.getFirstChild().getNodeValue());
            				res.put("status", "0");
            				logger.info(prepay_id_node.getFirstChild().getNodeValue());
            			}
        			}
        		}
        	}
		}
		return res;
	}

	/**
	 * 获取统一下单code_url结果
	 * @param preorderparam
	 * @param preorderurl
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> getPreOrderCodeUrl(String preorderparam,String preorderurl) throws Exception{
		Map<String,String> res = new HashMap<String,String>();
		res.put("status", "1");
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(preorderurl);
		StringEntity entity = new StringEntity(preorderparam,"application/xml","UTF-8");
		httppost.setEntity(entity);
		HttpResponse response = httpclient.execute(httppost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {
			HttpEntity resEntity = response.getEntity();
			String resultstr = EntityUtils.toString(resEntity,"utf-8");
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
			DocumentBuilder builder=factory.newDocumentBuilder();
			ByteArrayInputStream resinput = new ByteArrayInputStream(resultstr.getBytes());
            Document doc = builder.parse(resinput);
        	Node return_code = doc.getElementsByTagName("return_code").item(0);
        	if(return_code!=null){
        		if("SUCCESS".equalsIgnoreCase(return_code.getFirstChild().getNodeValue())){
        			Node result_code = doc.getElementsByTagName("result_code").item(0);
        			if(result_code!=null&&"SUCCESS".equalsIgnoreCase(result_code.getFirstChild().getNodeValue())){
        				Node code_url_node = doc.getElementsByTagName("code_url").item(0);
            			if(code_url_node!=null){
            				res.put("code_url", code_url_node.getFirstChild().getNodeValue());
            				res.put("status", "0");
            				System.out.println(code_url_node.getFirstChild().getNodeValue());
            			}
        			}
        		}
        	}
		}
		return res;
	}
}
