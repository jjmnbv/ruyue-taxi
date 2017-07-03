package com.szyciov.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.szyciov.driver.entity.OrderInfo;
import com.szyciov.param.PubPushLogParam;
import com.szyciov.passenger.entity.PushMessageLog;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

/**
 * app端的消息对象
 * 通过创建推送内容对象
 * 像指定设备推送消息
 * @author admin
 *
 */
public class AppMessageUtil{
	/**
	 * 乘客端app
	 */
	public static final String APPTYPE_PASSENGER = "0";
	
   private static TemplateHelper templateHelper = new TemplateHelper();
	/**
	 * 司机端app
	 */
	public static final String APPTYPE_DRIVER = "1";
	
	private static final String MASTERSECRET_DRIVER_IOS = SystemConfig.getSystemProperty("mastersecret_driver_ios");//"7e60f0b24b9c4df0d835a984";
	private static final String APPKEY_DRIVER_IOS = SystemConfig.getSystemProperty("appkey_driver_ios");//"8418a9b7759c5e2fd8f4ddbc";
	
	private static final String MASTERSECRET_DRIVER_ANDROID =SystemConfig.getSystemProperty("mastersecret_driver_android");//"7e60f0b24b9c4df0d835a984";
	private static final String APPKEY_DRIVER_ANDROID = SystemConfig.getSystemProperty("appkey_driver_android");//"8418a9b7759c5e2fd8f4ddbc";
	
	private static final String MASTERSECRET_PASSENGER_IOS = SystemConfig.getSystemProperty("mastersecret_passenger_ios");//"7e60f0b24b9c4df0d835a984";
	private static final String APPKEY_PASSENGER_IOS = SystemConfig.getSystemProperty("appkey_passenger_ios");//"8418a9b7759c5e2fd8f4ddbc";
	private static final String MASTERSECRET_PASSENGER_ANDROID = SystemConfig.getSystemProperty("mastersecret_passenger_android");//"7e60f0b24b9c4df0d835a984";
	private static final String APPKEY_PASSENGER_ANDROID = SystemConfig.getSystemProperty("appkey_passenger_android");//"8418a9b7759c5e2fd8f4ddbc";

	private static final Logger logger = Logger.getLogger(AppMessageUtil.class);
	/**
	 * 推送到app
	 * @param payload
	 */
	public static void send(PushPayload payload4ios,PushPayload payload4android,String apptype) {
		if(payload4ios==null&&payload4android==null){
			return ;
		}
		if(APPTYPE_DRIVER.equalsIgnoreCase(apptype)){
			sendPush(payload4ios, payload4android,APPTYPE_DRIVER);
		}else{
			sendPush(payload4ios, payload4android,APPTYPE_PASSENGER);
		}
	}

	private static void sendPush(PushPayload payload4ios, PushPayload payload4android,String apptype) {
		PushMessageLog androidPushLog=null;
		PushMessageLog iosPushLog=null;
		long iosmsgid = 0L;
		long androidmsgid = 0L;
		if(payload4ios!=null){
			try{
				JPushClient jpushClient_ios = null;
				iosPushLog=new PushMessageLog();
				if(APPTYPE_DRIVER.equalsIgnoreCase(apptype)){
					jpushClient_ios = new JPushClient(MASTERSECRET_DRIVER_IOS, APPKEY_DRIVER_IOS, 3);
				}else{
					jpushClient_ios = new JPushClient(MASTERSECRET_PASSENGER_IOS, APPKEY_PASSENGER_IOS, 3);
				}
				iosPushLog.setContent(JSONObject.parseObject(payload4ios.toString()).getString("message"));
				if(StringUtils.isBlank(iosPushLog.getContent()))
					iosPushLog.setContent(JSONObject.parseObject(payload4ios.toString()).getString("notification"));
				iosPushLog.setPlatform("1");
				PushResult result = jpushClient_ios.sendPush(payload4ios);
				iosmsgid = result.msg_id;
				logger.info("Got result -4ios " + result);
				
				
				iosPushLog.setReceiptState(result.statusCode+"");
				iosPushLog.setSendState("0");
				
			} catch (APIConnectionException e) {
		    	logger.error("Connection error. Should retry later. -4ios" + e);
		    //	iosPushLog.setReceiptState(receiptState);
		    	iosPushLog.setSendState("1");
		    } catch (APIRequestException e) {
		    	logger.error("Error response from JPush server. Should review and fix it. -4ios" + e);
		    	logger.error("HTTP Status: -4ios" + e.getStatus());
		    	logger.error("Error Code: -4ios" + e.getErrorCode());
		    	logger.error("Error Message: -4ios" + e.getErrorMessage());
		    	logger.error("Msg ID: -4ios" + e.getMsgId());
		    	iosPushLog.setReceiptState(e.getErrorCode()+"");
		    	iosPushLog.setSendState("1");
		    }catch(Exception e) {
		    	logger.error("4ios" , e);
		    //	iosPushLog.setReceiptMessage(e.getMessage());
		    	iosPushLog.setSendState("1");
			}
		}
		if(payload4android!=null){
			try{
				JPushClient jpushClient_android = null;
				androidPushLog=new PushMessageLog();
				if(APPTYPE_DRIVER.equalsIgnoreCase(apptype)){
					jpushClient_android = new JPushClient(MASTERSECRET_DRIVER_ANDROID, APPKEY_DRIVER_ANDROID, 3);
				}else{
					jpushClient_android = new JPushClient(MASTERSECRET_PASSENGER_ANDROID, APPKEY_PASSENGER_ANDROID, 3);
				}
				androidPushLog.setContent(JSONObject.parseObject(payload4android.toString()).getString("notification"));
				if(StringUtils.isBlank(androidPushLog.getContent()))
					androidPushLog.setContent(JSONObject.parseObject(payload4android.toString()).getString("message"));
				androidPushLog.setPlatform("0");
				PushResult result = jpushClient_android.sendPush(payload4android);
				androidmsgid = result.msg_id;
				logger.info("Got result -4andorid " + result);
				
				androidPushLog.setReceiptState(result.statusCode+"");
				androidPushLog.setSendState("0");
			} catch (APIConnectionException e) {
		    	logger.error("Connection error. Should retry later. -4andorid" + e);
		    	//androidPushLog.setReceiptMessage(e.getMessage());
		    	androidPushLog.setSendState("1");
		    } catch (APIRequestException e) {
		    	logger.error("Error response from JPush server. Should review and fix it. -4andorid" + e);
		    	logger.error("HTTP Status: -4andorid" + e.getStatus());
		    	logger.error("Error Code: -4andorid" + e.getErrorCode());
		    	logger.error("Error Message: -4andorid" + e.getErrorMessage());
		    	logger.error("Msg ID: -4andorid" + e.getMsgId());
		    	androidPushLog.setReceiptState(e.getErrorCode()+"");
		    	androidPushLog.setSendState("1");
		    }catch(Exception e) {
		    	logger.error("4andorid" , e);
		    	//androidPushLog.setReceiptMessage(e.getMessage());
		    	androidPushLog.setSendState("1");
			}
		}
		try {
			//IOS和Android存在同时不为空的情况，必须分开并同时记录.
			iosPushLog.setId(GUIDGenerator.newGUID());
			templateHelper.dealRequestWithToken("/Message/savePushMessage", HttpMethod.POST, null, iosPushLog,null);
			
			androidPushLog.setId(GUIDGenerator.newGUID());
			templateHelper.dealRequestWithToken("/Message/savePushMessage", HttpMethod.POST, null, androidPushLog,null);
			//如果是抢单推送,则保存记录(用来判断司机举手)
			savePubJpushlog(payload4android,iosmsgid,androidmsgid);
		} catch (Exception e) {
			logger.error("记录手机推送日志出错" , e);
		}
	}
	
	/**
	 * 保存记录
	 * @param payload4ios
	 * @param payload4android
	 */
	private static void savePubJpushlog(PushPayload payload4android,long iosmsgid,long androidmsgid){
		JSONObject androidJson = JSONObject.parseObject(payload4android.toString());
		JSONObject ex = androidJson.getJSONObject("message").getJSONObject("extras");
		JSONArray userids = androidJson.getJSONObject("audience").getJSONArray("alias");
		String orderno = ex.getString("orderno");
		List<String> driverphones = new ArrayList<>();
		for(Object phone : userids) driverphones.add((String)phone);
		if(ex != null && PushObjFactory.GRABORDER.equals(ex.getString("type"))){
			PubPushLogParam param = new PubPushLogParam();
			param.setOrderno(orderno);
			param.setDriverphones(driverphones);
			param.setAndroidmsgid(androidmsgid);
			param.setIosmsgid(iosmsgid);
			templateHelper.dealRequestWithToken("/OrderApi/SavePushLog", HttpMethod.POST, null, param,null);
		}
	}
	
/*	@Test
	public void test1(){
		List<String> phones4driver = new ArrayList<String>();
     	phones4driver.add("15821188307");
      
    	PushPayload pushload = PushObjFactory.creatHintObj4Android("测试", "测试", phones4driver, phones4driver);
		AppMessageUtil.sendPush(null, pushload, "1");
		
		
	}*/
	
	public static void main(String[] args) throws Exception{
//		List<String> phones4driver = new ArrayList<String>();
////		phones4driver.add("13476089040");
//		phones4driver.add("18872311190");
//		for(int i=1;i<11;i++){
//			PushPayload pushload = PushObjFactory.creatHintObj("这是模拟推送推送司机端10次的第"+i+"次", PushObjFactory.HINT_BSERVICE, "1231231", phones4driver, null);
//			AppMessageUtil.send(pushload, AppMessageUtil.APPTYPE_DRIVER);
//			Thread.sleep(5000);
//		}
		
		List<String> phones4passenger = new ArrayList<String>();
//		phones4passenger.add("13476089040");
		phones4passenger.add("18872311190");
		for(int i=1;i<11;i++){
//			PushPayload pushload = PushObjFactory.creatHintObj4Android("这是模拟推送推送乘客端10次的第"+i+"次", PushObjFactory.HINT_HAVETAKEORDER, "1231231", phones4passenger, null);
			OrderInfo orderinfo = new OrderInfo();
			orderinfo.setGrabtime(15);
			orderinfo.setOnaddress ("武汉地铁");
			orderinfo.setOffaddress("软件园");
			orderinfo.setOrderno("123123");
			orderinfo.setOnlat(123.23);
			orderinfo.setOnlng(213.213);
			orderinfo.setOfflat(123.221);
			orderinfo.setOfflng(123.122);
			orderinfo.setUsetime(new Date());
			orderinfo.setType("21");
			orderinfo.setIsusenow(true);
			orderinfo.setEstimatedcost(213);
			orderinfo.setEstimatedtime(123);
			orderinfo.setEstimatedmileage(1231);
			orderinfo.setLasttime("12312");
			PushPayload pushload = PushObjFactory.createGrabOrderObj4Android(orderinfo, phones4passenger);
			AppMessageUtil.send(null,pushload, AppMessageUtil.APPTYPE_DRIVER);
			Thread.sleep(4000);
		}
	}

}
