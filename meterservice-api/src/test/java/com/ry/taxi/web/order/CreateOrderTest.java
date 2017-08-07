/**
 * 
 */
package com.ry.taxi.web.order;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ry.taxi.RyMeterServiceApplicationTest;
import com.ry.taxi.order.domain.OpTaxiOrder;
import com.ry.taxi.order.mapper.OrderTestMapper;
import com.szyciov.util.StringUtil;

/**
 * @Title:CreateOrderTest.java
 * @Package com.ry.taxi.order.web
 * @Description
 * @author zhangdd
 * @date 2017年7月21日 上午10:44:51
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
public class CreateOrderTest extends RyMeterServiceApplicationTest  {
	
	@Autowired
	private OrderTestMapper orderTestMapper;
	
	@Test
	public void createOpOrder(){
		OpTaxiOrder order = new OpTaxiOrder();
		order.setOrderno("CGT" + StringUtil.formatDate(new Date(), "yyMMddHHss"));
		order.setUserid("0A0B5EDA-3C68-4101-8FDB-77896BB46756");
		order.setUsetype("1");//因私用车
		order.setOrdertype("4");//出租车
		order.setPaymentmethod("1");//线下支付
		
		order.setPassengers("电召接口-测试");
		order.setPassengerphone("18620291125");
		
		order.setOncity("广州市");
		order.setOnaddress("广州市燕岭路633号");
		order.setOnaddrlat(23.1764890000);
		order.setOnaddrlng(113.3490850000);
		
		order.setOffcity("广州市");
		order.setOffaddress("广东省广州市天河区花城大道53号");
		order.setOffaddrlat(23.1257230000);
		order.setOffaddrlng(113.3334270000);
		
		order.setEstimatedmileage(11);
		order.setEstimatedtime(18);
		order.setEstimatedcost(33);
		order.setPricecopy("");
		
		order.setPaymentstatus("6");
		order.setReviewstatus("0");
		order.setUserhidden("1");
		order.setDriverid("");
		
		order.setOrderstatus("1");
		order.setThirdorderno(order.getOrderno());
        
		orderTestMapper.insertOpTaxiOrder(order);
		
	}
	

}
