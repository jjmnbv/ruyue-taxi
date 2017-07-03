package com.szyciov.passenger.util;

import java.util.Map;

import org.springframework.http.HttpMethod;

import com.szyciov.message.BaseMessage;
import com.szyciov.message.CashMessage;
import com.szyciov.message.DriverVehicleBindMessage;
import com.szyciov.message.OrderMessage;
import com.szyciov.message.ProcessedMessage;
import com.szyciov.message.TaxiOrderMessage;
import com.szyciov.message.UserMessage;
import com.szyciov.util.TemplateHelper4CarServiceApi;

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
		if(message instanceof UserMessage){
			return tplt4csapi.dealRequestWithToken("/Message/SendUserMessage", HttpMethod.POST, null, message ,Map.class);
		}else if(message instanceof TaxiOrderMessage){
			return tplt4csapi.dealRequestWithToken("/Message/SendTaxiOrderMessage", HttpMethod.POST, null, message ,Map.class);
		} else if(message instanceof OrderMessage) {
			return tplt4csapi.dealRequestWithToken("/Message/SendOrderMessage", HttpMethod.POST, null, message ,Map.class);
		} else if(message instanceof ProcessedMessage) {
			return tplt4csapi.dealRequestWithToken("/Message/sendProcessedMessage", HttpMethod.POST, null, message ,Map.class);
		}else if(message instanceof DriverVehicleBindMessage) {
			return tplt4csapi.dealRequestWithToken("/Message/sendBindMessage", HttpMethod.POST, null, message ,Map.class);
		}else if(message instanceof CashMessage){
			return tplt4csapi.dealRequestWithToken("/Message/sendCashMessage", HttpMethod.POST, null, message ,Map.class);
		}
		return null;
	}

}
