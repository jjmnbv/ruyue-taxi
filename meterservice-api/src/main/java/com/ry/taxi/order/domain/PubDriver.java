/**
 * 
 */
package com.ry.taxi.order.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title:PubDriver.java
 * @Package com.ry.taxi.order.domain
 * @Description
 * @author zhangdd
 * @date 2017年7月18日 上午10:11:59
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PubDriver {
	/**
	 * id
	 */
    private String id;
	/**
	 * 司机姓名
	 */
	private String drivername;
	
	/**
	 * 司机电话
	 */
	private String driverphone;	
	/**
	 * 车牌号码
	 */
	private String plateno;
	/**
	 * 汽车品牌
	 */
	private String carbrand;
	/**
	 * 汽车车系
	 */
	private String carvehcline;
	/**
	 * 司机编号
	 */
	private String jobnum;

	/**
	 * 工作状态(0-空闲 1-服务中 2-下线)
	 */
	private String workstatus;
	
	/**
	 * 交接班状态
	 */
	private String passworkstatus;
	/**
	 * 司机类型(0-网约车 1-出租车)
	 */
	private String drivertype;
	/**
	  *所属租赁公司
	  */
	private String leasescompanyid;
	/**
	 * 车辆ID
	 */
	private String vehicleid;
	/**
	 * 归属车企,从字典表中读取
	 */
	private String belongleasecompany;	
	/**
	 * 经度
	 */
	private Double lng;
	/**
	 * 纬度
	 */
	private Double lat;

}
