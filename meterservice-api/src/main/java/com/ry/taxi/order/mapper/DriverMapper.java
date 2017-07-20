/**
 * 
 */
package com.ry.taxi.order.mapper;

import com.ry.taxi.order.domain.PubDriver;
import com.ry.taxi.order.request.DriverTakeParam;

/**
 * @Title:DriverMapper.java
 * @Package com.ry.taxi.order.mapper
 * @Description
 * @author zhangdd
 * @date 2017年7月18日 上午10:10:30
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
public interface DriverMapper {
	
	/*
	 * 根据资格证号查找司机
	 */
	PubDriver getDriverByJobNum(String jboNum);
	

}
