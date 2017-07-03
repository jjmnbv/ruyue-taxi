package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;

/**
 * 司机上下班参数类
 * @ClassName AtworkParam 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年9月26日 下午3:04:57
 */
public class AtworkParam extends BaseParam{
	/**
	 * 工作状态(0-上班;1-下班)
	 */
	@SzycValid(rules={"checkNull","checkAtWorkType","checkCanAtWork"})
	private String type;
	/**
	 * 司机ID
	 */
	private String id;
	
	/**  
	 * 获取司机ID  
	 * @return id 司机ID  
	 */
	public String getId() {
		return id;
	}

	/**  
	 * 设置司机ID  
	 * @param id 司机ID  
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**  
	 * 获取工作状态(0-上班;1-下班)  
	 * @return type 工作状态(0-上班;1-下班)  
	 */
	public String getType() {
		return type;
	}

	/**  
	 * 设置工作状态(0-上班;1-下班)  
	 * @param type 工作状态(0-上班;1-下班)  
	 */
	public void setType(String type) {
		this.type = type;
	}
}
