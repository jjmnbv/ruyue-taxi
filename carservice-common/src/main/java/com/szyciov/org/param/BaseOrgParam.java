package com.szyciov.org.param;

import com.szyciov.driver.param.BaseParam;
import com.szyciov.org.entity.OrgUser;

/**
 * @ClassName BaseOrgParam 
 * @author Efy Shu
 * @Description 机构端基本参数类
 * @date 2016年10月17日 下午4:36:58 
 */
public class BaseOrgParam extends BaseParam{
	/**
	 * 机构用户
	 */
	private OrgUser orgUser;

	/**
	 * 机构用户ID(方便取数据)
	 */
	private String userid;

	/**  
	 * 设置机构用户ID(方便取数据)  
	 * @param userid 机构用户ID(方便取数据)  
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	

	/**  
	 * 获取机构用户  
	 * @return orgUser 机构用户  
	 */
	public OrgUser getOrgUser() {
		return orgUser;
	}

	/**  
	 * 设置机构用户  
	 * @param orgUser 机构用户  
	 */
	public void setOrgUser(OrgUser orgUser) {
		this.orgUser = orgUser;
	}

	/**  
	 * 获取机构用户ID(方便取数据)  
	 * @return userid 机构用户ID(方便取数据)  
	 */
	public String getUserid() {
		if(userid == null && orgUser != null){
			userid = orgUser.getId();
		}
		return userid;
	}
}
