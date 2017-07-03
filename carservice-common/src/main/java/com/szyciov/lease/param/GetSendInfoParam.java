/**
 * 
 */
package com.szyciov.lease.param;

import com.szyciov.driver.param.BaseParam;

/**
 * @ClassName GetSendInfoParam 
 * @author Efy Shu
 * @Description 获取派单规则参数类
 * @date 2016年9月30日 下午4:03:13 
 */
public class GetSendInfoParam extends BaseParam{
	/**
	 * 所属租赁公司
	 */
	private String companyid;
	/**
	 * 所属城市
	 */
	private String city;
	
	/**
	 * 订单类型 (0-机构 1-个人)
	 */
	private int orderprop;
	
	/**
	 * 0-预约用车,1-即刻用车
	 */
	private String usevechiletype;
	
	
	/**  
	 * 获取订单类型(0-机构1-个人)  
	 * @return orderprop 订单类型(0-机构1-个人)  
	 */
	public int getOrderprop() {
		return orderprop;
	}
	

	/**  
	 * 设置订单类型(0-机构1-个人)  
	 * @param orderprop 订单类型(0-机构1-个人)  
	 */
	public void setOrderprop(int orderprop) {
		this.orderprop = orderprop;
	}
	

	/**  
	 * 获取所属租赁公司  
	 * @return leasecompanyid 所属租赁公司  
	 */
	public String getCompanyid() {
		return companyid;
	}
	
	/**  
	 * 设置所属租赁公司  
	 * @param leasecompanyid 所属租赁公司  
	 */
	public void setCompanyid(String leasecompanyid) {
		this.companyid = leasecompanyid;
	}
	
	/**  
	 * 获取所属城市  
	 * @return city 所属城市  
	 */
	public String getCity() {
		return city;
	}
	
	/**  
	 * 设置所属城市  
	 * @param city 所属城市  
	 */
	public void setCity(String city) {
		this.city = city;
	}

	public String getUsevechiletype() {
		return usevechiletype;
	}


	public void setUsevechiletype(String usevechiletype) {
		this.usevechiletype = usevechiletype;
	}
}
