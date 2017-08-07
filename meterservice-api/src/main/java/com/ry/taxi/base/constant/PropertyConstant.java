/**
 * 
 */
package com.ry.taxi.base.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Title:PropertyConstant.java
 * @Package com.ry.taxi.base.constant
 * @Description
 * @author zhangdd
 * @date 2017年7月19日 上午11:32:04
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Component
public class PropertyConstant {
	
	/**
	 * 百度地图ak
	 */
	public static String BAIDU_AK;
	/**
	 * 百度api地址
	 */
    public static String BAIDU_URL;
   
	@Value("${baiduMap.ak}")
	public  void setBAIDU_AK(String baidu_ak) {
		BAIDU_AK = baidu_ak;
	}

	@Value("${baiduMap.url}")
	public void setBAIDU_URL(String baidu_url) {
		BAIDU_URL = baidu_url;
	}


	
	

}
