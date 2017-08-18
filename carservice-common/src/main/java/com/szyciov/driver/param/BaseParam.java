package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;

public class BaseParam {
	/**
	 * tokenl令牌
	 */
	@SzycValid(rules={"checkNull","checkToken"})
	private String token;
	/**
	 * 用车类型
	 * @see {@linkplain com.szyciov.enums.OrderEnum OrderEnum}  
	 */
	@SzycValid(rules={"checkNull","checkUseType"})
	private String usetype;
	/**
	 * 订单类型
	 * @see {@linkplain com.szyciov.enums.OrderEnum OrderEnum}  
	 */
	@SzycValid(rules={"checkNull","checkOrderType"})
	private String ordertype;
	/**
	 * 请求来源IP
	 */
	private String ipaddr;
	/**
	 * 创建者
	 */
	private String creater;
	
	/**
	 * 更新者
	 */
	private String updater;
	
	/**
	 * 页码
	 */
	private int pageNO;
	/**
	 * 操作次数
	 */
	private int sEcho;
	
	/**
	 * 每页个数
	 */
	private int iDisplayLength = 10;

	/**
	 * 起始下标
	 */
	private int iDisplayStart;

	/**
	 * 关键字
	 */
	public String key;

	/**
	 * 搜索字符串
	 */
	private String sSearch;
	
	/**
	 * 1-运管网约车单 2-机构网约车单 3-运管出租车单
	 */
	private int orderprop;
	
	/**  
	 * 获取操作次数  
	 * @return sEcho 操作次数  
	 */
	public int getsEcho() {
		return sEcho;
	}
	

	/**  
	 * 设置操作次数  
	 * @param sEcho 操作次数  
	 */
	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}
	

	/**
	 * 获取关键字
	 * 
	 * @return key 关键字
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 设置关键字
	 * 
	 * @param key
	 *            关键字
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 获取搜索字符串
	 * 
	 * @return sSearch 搜索字符串
	 */
	public String getsSearch() {
		return sSearch;
	}

	/**
	 * 设置搜索字符串
	 * 
	 * @param sSearch
	 *            搜索字符串
	 */
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	/**
	 * 获取token令牌
	 * 
	 * @return token token令牌
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 设置token令牌
	 * 
	 * @param token
	 *            token令牌
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 获取页码
	 * 
	 * @return pageNO 页码
	 */
	public int getPageNO() {
		return pageNO;
	}

	/**
	 * 设置页码
	 * 
	 * @param pageNO
	 *            页码
	 */
	public void setPageNO(int pageNO) {
		this.pageNO = pageNO;
	}

	/**
	 * 获取每页个数
	 * 
	 * @return iDisplayLength 每页个数
	 */
	public int getiDisplayLength() {
		return iDisplayLength;
	}

	/**
	 * 设置每页个数
	 * 
	 * @param iDisplayLength
	 *            每页个数
	 */
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	/**
	 * 获取起始下标
	 * 
	 * @return iDisplayStart 起始下标
	 */
	public int getiDisplayStart() {
		if (pageNO == 0) {
			return iDisplayStart;
		}
		iDisplayStart = iDisplayStart == 0 
								 ? iDisplayLength * (pageNO-1) > 0
								 ? iDisplayLength * (pageNO-1) : 0
								 :  iDisplayStart;
		return  iDisplayStart;
	}

	/**
	 * 设置起始下标
	 * 
	 * @param iDisplayStart
	 *            起始下标
	 */
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	/**  
	 * 获取创建者  
	 * @return creater 创建者  
	 */
	public String getCreater() {
		return creater;
	}

	/**  
	 * 设置创建者  
	 * @param creater 创建者  
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	/**  
	 * 获取更新者  
	 * @return updater 更新者  
	 */
	public String getUpdater() {
		return updater;
	}

	/**  
	 * 设置更新者  
	 * @param updater 更新者  
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	/**  
	 * 获取请求来源IP  
	 * @return ipaddr 请求来源IP  
	 */
	public String getIpaddr() {
		return ipaddr;
	}
	
	/**  
	 * 设置请求来源IP  
	 * @param ipaddr 请求来源IP  
	 */
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	/**  
	 * 获取用车类型
	 * @see {@linkplain com.szyciov.enums.OrderEnum OrderEnum}  
	 * @return usetype 用车类型
	 */
	public String getUsetype() {
		return usetype;
	}

	/**  
	 * 设置用车类型
	 * @see {@linkplain com.szyciov.enums.OrderEnum OrderEnum}  
	 * @param usetype 用车类型
	 */
	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}
	
	/**  
	 * 获取订单类型
	 * @see {@linkplain com.szyciov.enums.OrderEnum OrderEnum}    
	 * @return ordertype 订单类型
	 */
	public String getOrdertype() {
		return ordertype;
	}
	
	/**  
	 * 设置订单类型
	 * @see {@linkplain com.szyciov.enums.OrderEnum OrderEnum}  
	 * @param ordertype 订单类型
	 */
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	/**  
	 * 获取1-运管网约车单2-机构网约车单3-运管出租车单  
	 * @return orderprop 1-运管网约车单2-机构网约车单3-运管出租车单  
	 */
	public int getOrderprop() {
		return orderprop;
	}
	
	/**  
	 * 设置1-运管网约车单2-机构网约车单3-运管出租车单  
	 * @param orderprop 1-运管网约车单2-机构网约车单3-运管出租车单  
	 */
	public void setOrderprop(int orderprop) {
		this.orderprop = orderprop;
	}
	
}
