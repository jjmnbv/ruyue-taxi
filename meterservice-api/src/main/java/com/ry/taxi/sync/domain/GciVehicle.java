/**
 * 
 */
package com.ry.taxi.sync.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title:GciVehicle.java
 * @Package com.ry.taxi.sync.domain
 * @Description 对应gci_vehicle表
 * @author zhangdd
 * @date 2017年7月19日 下午4:43:41
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GciVehicle {
	private int id;
	private String company;
	private String maincompany;
	private String plateno;
	private String createtime;

}
