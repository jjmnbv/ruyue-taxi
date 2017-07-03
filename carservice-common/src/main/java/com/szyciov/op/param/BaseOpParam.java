/**
 * 
 */
package com.szyciov.op.param;

import com.szyciov.driver.param.BaseParam;
import com.szyciov.op.entity.OpUser;

/**
 * @ClassName BaseOpParam 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年10月23日 上午10:29:50 
 */
public class BaseOpParam extends BaseParam {
	/**
	 * 个人用户
	 */
	private OpUser opUser;
	/**
	 * 个人用户userid(方便取数据)
	 */
	private String userid;
	/**  
	 * 获取个人用户  
	 * @return opUser 个人用户  
	 */
	public OpUser getOpUser() {
		return opUser;
	}
	
	/**  
	 * 设置个人用户  
	 * @param opUser 个人用户  
	 */
	public void setOpUser(OpUser opUser) {
		this.opUser = opUser;
	}
	
	/**  
	 * 获取个人用户userid(方便取数据)  
	 * @return userid 个人用户userid(方便取数据)  
	 */
	public String getUserid() {
		if(userid == null && opUser != null) userid = opUser.getId();
		return userid;
	}
	
	/**  
	 * 设置个人用户userid(方便取数据)  
	 * @param userid 个人用户userid(方便取数据)  
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
