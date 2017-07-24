/**
 * 
 */
package com.ry.taxi.util.map;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title:Point.java
 * @Package com.ry.taxi.Util.map
 * @Description
 * @author zhangdd
 * @date 2017年7月19日 下午3:22:56
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
	
	private double lat;// 纬度
	private double lng;// 经度

}
