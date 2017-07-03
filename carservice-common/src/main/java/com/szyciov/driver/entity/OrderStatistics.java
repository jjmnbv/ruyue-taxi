/**
 * 
 */
package com.szyciov.driver.entity;

/**
 * @ClassName OrderStatistics 
 * @author Efy Shu
 * @Description 订单统计数据类
 * @date 2016年9月22日 下午8:53:45 
 */
public class OrderStatistics {
	/**
	 * 订单总数量
	 */
	private int count;
	/**
	 * 订单总金额
	 */
	private double cost;
	/**
	 * 	所属年份
	 */
	private int year;
	/**
	 * 所属月份
	 */
	private int month;
	
	
	/**  
	 * 获取订单总数量  
	 * @return count 订单总数量  
	 */
	public int getCount() {
		return count;
	}
	
	/**  
	 * 设置订单总数量  
	 * @param count 订单总数量  
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**  
	 * 获取订单总金额  
	 * @return cost 订单总金额  
	 */
	public double getCost() {
		return cost;
	}
	
	/**  
	 * 设置订单总金额  
	 * @param cost 订单总金额  
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**  
	 * 获取所属年份  
	 * @return year 所属年份  
	 */
	public int getYear() {
		return year;
	}
	
	/**  
	 * 设置所属年份  
	 * @param year 所属年份  
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	/**  
	 * 获取所属月份  
	 * @return month 所属月份  
	 */
	public int getMonth() {
		return month;
	}
	
	/**  
	 * 设置所属月份  
	 * @param month 所属月份  
	 */
	public void setMonth(int month) {
		this.month = month;
	}
}
