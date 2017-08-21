/**
 * 
 */
package com.szyciov.op.param;

import com.szyciov.org.param.OrgUserParam;

/**
 * @ClassName PeUserParam 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年10月26日 下午5:25:41 
 */
public class PeUserParam extends OrgUserParam {
	/**
	 * 用户手机号
	 */
	String userphone;

	/**  
	 * 获取用户手机号  
	 * @return userphone 用户手机号  
	 */
	public String getUserphone() {
		return userphone;
	}
	

	/**  
	 * 设置用户手机号  
	 * @param userphone 用户手机号  
	 */
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
}
