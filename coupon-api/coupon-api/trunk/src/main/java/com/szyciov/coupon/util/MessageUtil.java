package com.szyciov.coupon.util;

import java.util.Map;

import com.szyciov.message.BaseMessage;
import com.szyciov.message.CouponMessage;
import com.szyciov.message.DriverVehicleBindMessage;
import org.springframework.http.HttpMethod;

/**
 * 乘客端发送消息的工具类
 * @author admin
 *
 */
public class MessageUtil {
	
	/**
	 * 调用carserviceApi的方法，所以需要在webproperties中配置这个api路径
	 */
	private static TemplateHelper4CarServiceApi tplt4csapi = new TemplateHelper4CarServiceApi();
	
	/**
	 * 发送消息的接口
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> sendMessage(BaseMessage message){
		if(message instanceof CouponMessage){
			return tplt4csapi.dealRequestWithToken("/Message/SendCouponMessage", HttpMethod.POST, null, message ,Map.class);
		}if(message instanceof DriverVehicleBindMessage){
			return tplt4csapi.dealRequestWithToken("/Message/sendBindMessage", HttpMethod.POST, null, message ,Map.class);
		}
		return null;
	}

}
