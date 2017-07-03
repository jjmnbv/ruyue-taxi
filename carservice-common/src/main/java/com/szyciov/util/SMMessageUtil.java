package com.szyciov.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 短信消息
 * 需要发送短信消息的地方创建此消息并按需发送
 * 通知、验证码都可通过此类发送
 * @author admin
 *
 */
public class SMMessageUtil{
	
	private static Logger logger = LoggerFactory.getLogger(SMMessageUtil.class);
	
	public static String send(List<String> phones,String content) {
		//根据smsonoff判断是否是开发环境
		String smsonoff = SystemConfig.getSystemProperty("smsonoff");
		if(StringUtils.isBlank(smsonoff)||"false".equalsIgnoreCase(smsonoff)){
			return null;
		}
		if(phones==null||phones.size()<=0||content==null||"".equalsIgnoreCase(content)){
			return "14";
		}
		String messageuseruyue = SystemConfig.getSystemProperty("messageuseruyue");
		String sendstate = "0";
		String receiptstate = "";
		try{
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<phones.size();i++){
				buf.append(phones.get(i));
				if(i!=phones.size()-1){
					if("true".equalsIgnoreCase(messageuseruyue)){
						buf.append(";");
					}else{
						buf.append(",");
					}
				}
			}
			if(buf.length()>0){
				if("true".equalsIgnoreCase(messageuseruyue)){
				    receiptstate = sendMessageByRUYUE(buf.toString(),content);
				}else{
					receiptstate = sendMessage(buf.toString(),content);
				}
			}
		}catch(Exception e){
		    sendstate = "1";
			logger.info("发送短信异常",e);
		}
		try{
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<phones.size();i++){
				buf.append(phones.get(i));
				if(i!=phones.size()-1){
						buf.append(";");
				}
			}
			if(buf.length()>0){
				insertPubsmssendlog(buf.toString(),content,sendstate,receiptstate);
			}
		}catch(Exception e){
			logger.info("插入短信数据异常",e);
		}
		return "0";
	}
	
	/**
		0	提交成功
		1	含有敏感词汇
		2	余额不足
		3	没有号码
		4	包含sql语句
		10	账号不存在
		11	账号注销
		12	账号停用
		13	IP鉴权失败
		14	格式错误
		-1	系统异常
	 **/
	private static String sendMessage(String phones,String content){
		try{
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000); 
			
			HttpPost httppost = new HttpPost(SystemConfig.getSystemProperty("messageurl"));//接口地址
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("name", SystemConfig.getSystemProperty("messageusername")));//用户账号
			formparams.add(new BasicNameValuePair("pwd", SystemConfig.getSystemProperty("messagepwd")));//基本资料中的接口密码
			formparams.add(new BasicNameValuePair("type", SystemConfig.getSystemProperty("messagetype")));//固定值pt
			
			formparams.add(new BasicNameValuePair("content", "【"+SystemConfig.getSystemProperty("sign")+"】"+content));//发送内容
			formparams.add(new BasicNameValuePair("mobile",phones));//手机号码,多个可以用逗号分隔
			formparams.add(new BasicNameValuePair("sign", ""));//用户签名
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity); 
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String s = EntityUtils.toString(entity, "UTF-8");
			//截取状态码
			String[] strs = s.split(",");
			logger.info("发送给"+phones+"短信的返回信息:{}",s);
			return strs[0];
		//最后得到结果后，是一个字符长串，截取有用的信息，可判断短信是否发送成功
		}catch (Exception e) {
			logger.error("发送短信异常",e);
		}
		return null;
	}
	
	
	/**
	 * 如约需要发送短信调用他们自己的短信接口
	 * @param phones
	 * @param content
	 */
	private static String sendMessageByRUYUE(String phones,String content){
		try{
			String url = SystemConfig.getSystemProperty("messageurl_ruyue");
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000); 
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("user", SystemConfig.getSystemProperty("user_ruyue")));
	        params.add(new BasicNameValuePair("pwd", SystemConfig.getSystemProperty("pwd_ruyue")));
	        String channel = SystemConfig.getSystemProperty("channel_ruyue");
	        params.add(new BasicNameValuePair("channel", StringUtils.isBlank(channel)?"":channel));
	        params.add(new BasicNameValuePair("extend", SystemConfig.getSystemProperty("extend_ruyue")));
	        params.add(new BasicNameValuePair("phonelist", phones));
	        params.add(new BasicNameValuePair("cont", content));
	        
            //转换为键值对  
	        String str = EntityUtils.toString(new UrlEncodedFormEntity(params, "gbk"));  
            System.out.println(str); 
            HttpGet httpget = new HttpGet(url+"?"+str);
            HttpResponse response = httpclient.execute(httpget);  
            //得到响应体  
            HttpEntity entity = response.getEntity();  
            if(entity != null){
            	StringBuffer buf = new StringBuffer();
                InputStream is = entity.getContent();
                try{
                	//转换为字节输入流  
                	InputStreamReader inread = new InputStreamReader(is, "gbk");
                	try{
                		BufferedReader br = new BufferedReader(inread);
                		try{
                			String body = null;  
                            while((body=br.readLine()) != null){  
                            	buf.append(body);  
                            } 
                		}finally{
                			br.close();
                		}
                	}finally{
                		inread.close();
                	}
                }finally{
                	is.close();
                }
                if(buf.length()>0){
                	logger.info("发送给"+phones+"短信的返回信息",buf.toString());
                	 return spitReceiptstate(buf.toString());
                }
            }  
            //最后得到结果后，是一个字符长串，截取有用的信息，可判断短信是否发送成功
		}catch (Exception e) {
			logger.error("发送短信异常",e);
		}
		return null;
	}
	
	public static void main(String[] args){
		String phones = "15623695619;18627767179";
		String content = "你的登录账户：1213232";
//		insertPubsmssendlog("12323213123;12323323232;12321212212","啊哈哈哈","0");
//		insertPubsmssendlog("12323213123","啊哈哈哈","0");
		sendMessageByRUYUE(phones,content);
	}
	/**
	 * 发送短信日志插入
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 *  
	 * 
	 */
	public static  void insertPubsmssendlog(String phones,String content,String sendstate,String receiptstate){
		HttpClient httpclient = new DefaultHttpClient();
//		HttpPost httppost = new HttpPost(SystemConfig.getSystemProperty("carserviceApi")+"/Pubsmssendlog/InsertPubsmssendlog");
//		String url = "http://localhost:8002/carservice-api/api/Pubsmssendlog/InsertPubsmssendlog";
		String url = SystemConfig.getSystemProperty("carserviceApi")+"/Pubsmssendlog/InsertPubsmssendlog";
		List<NameValuePair> pubsmssendlog = new ArrayList<NameValuePair>();
		pubsmssendlog.add(new BasicNameValuePair("phones",phones));
		pubsmssendlog.add(new BasicNameValuePair("content",content));
		pubsmssendlog.add(new BasicNameValuePair("sendstate",sendstate));
		pubsmssendlog.add(new BasicNameValuePair("receiptstate",receiptstate));
		try {
			 String str = EntityUtils.toString(new UrlEncodedFormEntity(pubsmssendlog, "utf-8"));
			 HttpGet httpget = new HttpGet(url+"?"+str);
			httpclient.execute(httpget);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{  
			 httpclient.getConnectionManager().shutdown(); 
		}
		
	}
	/**
	 * 截取短信接口返回的状态码
	 */
	public static String spitReceiptstate(String receiptstate){
		if(receiptstate !=null){
			int a = receiptstate.indexOf("<status>");
			int b = receiptstate.indexOf("</status>");
			String c = receiptstate.substring(a+8, b);
		       return c;
		}
		return receiptstate;
		
	}
}
