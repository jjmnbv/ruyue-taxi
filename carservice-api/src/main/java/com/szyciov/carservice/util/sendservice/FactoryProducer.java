package com.szyciov.carservice.util.sendservice;

import com.szyciov.carservice.util.sendservice.factory.SendServiceFactory;
import com.szyciov.carservice.util.sendservice.factory.le.LeCarSendServiceFactory;
import com.szyciov.carservice.util.sendservice.factory.op.car.OpCarSendServiceFactory;
import com.szyciov.carservice.util.sendservice.factory.op.taxi.TaxiSendServiceFactory;
import com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.entity.AbstractOrder;

/**
 * 派单服务的使用工具类
 * @author zhu
 *
 */
public class FactoryProducer {
	/**
	 * 网约车类型
	 */
	public  static final String TYPE_CAR_OP = "0";
	
	public  static final String TYPE_CAR_LE = "1";
	/**
	 * 出租车类型
	 */
	public static final String TYPE_TAXI_OP = "2";
	
	public  static final String TYPE_TAXI_LE = "3";

	public static SendServiceFactory getFactory(String type) {
		if (TYPE_CAR_OP.equalsIgnoreCase(type)) {
			//运管端网约车工厂类
			return new OpCarSendServiceFactory();
		} else if (TYPE_CAR_LE.equalsIgnoreCase(type)) {
			//租赁端网约车工厂类
			return new LeCarSendServiceFactory();
		} else if (TYPE_TAXI_OP.equalsIgnoreCase(type)) {
			//运管端出租车工厂类
			return new TaxiSendServiceFactory();
		} else if (TYPE_TAXI_LE.equalsIgnoreCase(type)) {
			//租赁端出租车工厂类
			return null;
		}
		return null;
	}
	
	public static void main(String[] args) {
		/**
		 * 派单的使用方式
		 */
		AbstractOrder orderinfo = null;
		SendServiceFactory sendservice = FactoryProducer.getFactory(TYPE_TAXI_OP);
		SendRuleHelper senrule = sendservice.createSendRuleFactory().createSendRule(orderinfo);
		SendMethodHelper sendmethod = sendservice.createSendMethodFactory().createSendMethod(senrule);
		sendmethod.send(senrule, orderinfo);
	}
}
