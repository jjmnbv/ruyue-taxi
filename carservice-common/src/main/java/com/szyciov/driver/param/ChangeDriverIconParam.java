/**
 * 
 */
package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;

/**
 * @ClassName ChangeDriverIconParam 
 * @author Efy Shu
 * @Description 更换头像参数类
 * @date 2016年9月29日 下午12:40:28 
 */
public class ChangeDriverIconParam extends BaseParam{
	/**
	 * 司机ID
	 */
	private String drverid;
	/**
	 * 头像base64字符串
	 */
	@SzycValid(rules={"checkNull"})
	private String icon;

	/**  
	 * 获取头像base64字符串  
	 * @return icon 头像base64字符串  
	 */
	public String getIcon() {
		return icon;
	}
	
	/**  
	 * 设置头像base64字符串  
	 * @param icon 头像base64字符串  
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**  
	 * 获取司机ID  
	 * @return drverid 司机ID  
	 */
	public String getDrverid() {
		return drverid;
	}
	
	/**  
	 * 设置司机ID  
	 * @param drverid 司机ID  
	 */
	public void setDrverid(String drverid) {
		this.drverid = drverid;
	}
	
}
