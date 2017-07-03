package com.szyciov.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * 短信模板的工具类
 * 短信模板在哪个项目发送，哪个项目需要配置对应的properties文件
 * 文件名为sms.properties
 * 模板的配置方式：
 * 			com.yc.org.user.create=你的机构账户${0}已创建,你的密码是{1}
 * 获取对应的短信模板方式：
 * 			String smstemplate = SMSTempPropertyConfigurer.getSMSTemplate("com.yc.org.user.create","朱周通","123456");
 * 或者                   String smstemplate = SMSTempPropertyConfigurer.getSMSTemplate("com.yc.org.user.create", new String[]{"朱周通","123456"});
 * @author zhu
 *
 */
public class SMSTempPropertyConfigurer extends PropertyPlaceholderConfigurer{

	private static Map<String, String> properties = new HashMap<String, String>();

	private static PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(DEFAULT_PLACEHOLDER_PREFIX, DEFAULT_PLACEHOLDER_SUFFIX,DEFAULT_VALUE_SEPARATOR, true);
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)throws BeansException {
		for (Entry<Object, Object> entry : props.entrySet()){
			String stringKey = String.valueOf(entry.getKey());
			String stringValue = String.valueOf(entry.getValue());
			properties.put(stringKey, stringValue);
		}
		super.processProperties(beanFactoryToProcess, props);
	}
	
	/**
	 * 根据key和参数params获取对应的短信模板
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getSMSTemplate(String key,Object... params){
		String smstemp = properties.get(key);
		if(params==null||params.length<=0){
			return smstemp;
		}
		smstemp = helper.replacePlaceholders(smstemp, convertStringArray2Proobj(params));
		return smstemp;
	}
	
	private static Properties convertStringArray2Proobj(Object... params){
		Properties proerties = new Properties();
		if(params==null||params.length<=0){
			return proerties;
		}
		for(int i=0;i<params.length;i++){
			proerties.put(""+i, objNull2Blank(params[i]));
		}
		return proerties;
	}
	
	private static String objNull2Blank(Object param){
		if(param==null){
			return "";
		}
		return param+"";
	}
	
	public static void main(String[] args) {
		int a = 0;
		System.out.println(getSMSTemplate("1sdfsdf${0}${1}${2}${3}${4}",new Object[]{"1",a,"0","0","2"}));
	}
	
	
}
