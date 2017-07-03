package com.szyciov.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class SystemConfig {
	/**
	 * 读取配置文件
	 * 
	 * @param cfgFile
	 * @throws Exception
	 */
	public static void ReadConfigFile(InputStream fis) throws Exception
	{
		
		if (SystemConfig.getAppSettings() != null)
		{
			SystemConfig.getAppSettings().clear();
		}
		
		Properties properties = new Properties();
		try
		{
			properties.load(fis);
			for (Object s : properties.keySet())
			{
				getAppSettings().put(s.toString(), properties.get(s));
				// new String(String.valueOf(properties.get(s)).getBytes(
				// "ISO8859-1"), "UTF-8"));
			}
			fis.close();
		} catch (IOException e)
		{
			throw new Exception("读取配置文件失败", e);
		}
	}
	
	/**
	 * 封装了AppSettings, 负责存放 配置的基本信息
	 */
	private static Hashtable<String, Object> _AppSettings = null;
	
	public static Hashtable<String, Object> getAppSettings(){
		if (_AppSettings == null){
			// try
			// {
			_AppSettings = new java.util.Hashtable<String, Object>();
				Map<String, String> properties = CustomPropertyConfigurer
						.getProperties();
				for (String s : properties.keySet())
				{
				// _AppSettings.put(s,
					// new
					// String(String.valueOf(properties.get(s)).getBytes("ISO8859-1"),
					// "UTF-8"));
				_AppSettings.put(s, properties.get(s));
				}
			// } catch (IOException e)
			// {
			// throw new RuntimeException("读取配置文件失败", e);
			// }
		}
		return _AppSettings;
	}

	public static String getSystemProperty(String key) {
		String value = (String) getAppSettings().get(key);
		return StringUtils.isNotEmpty(value) ? value : "";
	}

	public static String getSystemProperty(String key, String defaulValue) {
		String value = (String) getAppSettings().get(key);
		return StringUtils.isNotEmpty(value) ? value : defaulValue;
	}
}