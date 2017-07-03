package com.szyciov.util;

import java.util.List;
import java.util.Map;

/**
 * web端消息对象
 * 根据需要自己构造contentinfo信息
 * @author admin
 *
 */
public class WebMessageUtil {

	public static void send(List<String> userids,String title,Map<String,Object> contentinfo) {
		if(contentinfo==null){
			return ;
		}
		try{
			//todo
			System.out.println("web消息发送"+contentinfo.get("title"));
		}catch(Exception e){
			//log
		}
	}
}
