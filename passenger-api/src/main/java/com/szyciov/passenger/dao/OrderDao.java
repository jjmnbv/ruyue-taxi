package com.szyciov.passenger.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.passenger.entity.DriverInfo;
import com.szyciov.passenger.entity.Order4List;
import com.szyciov.passenger.entity.PassengerOrder;
import com.szyciov.passenger.mapper.OrderMapper;

@Repository("OrderDao")
public class OrderDao {

	private OrderMapper mapper;

	@Resource
	public void setMapper(OrderMapper mapper) {
		this.mapper = mapper;
	}

	public PassengerOrder getOrder4Org(Map<String, Object> params) {
		return mapper.getOrder4Org(params);
	}

	public PassengerOrder getOrder4Op(Map<String, Object> params) {
		return mapper.getOrder4Op(params);
	}

	public List<Order4List> getOrders4Org(Map<String, Object> params) {
		return mapper.getOrders4Org(params);
	}

	public List<Order4List> getOrders4Op(Map<String, Object> params) {
		return mapper.getOrders4Op(params);
	}

	public void updateOrderState4Org(Map<String, Object> params) {
		mapper.updateOrderState4Org(params);
	}

	public void updateOrderState4Op(Map<String, Object> params) {
		mapper.updateOrderState4Op(params);
	}

	public DriverInfo getDriverInfo(String driverid) {
		return mapper.getDriverInfo(driverid);
	}

	public PassengerOrder getOrderByOrderno4Org(String orderno) {
		return mapper.getOrderByOrderno4Org(orderno);
	}

	public PassengerOrder getOrderByOrderno4Op(String orderno) {
		return mapper.getOrderByOrderno4Op(orderno);
	}

	public void doComment4Org(Map<String, Object> params) {
		mapper.doComment4Org(params);
	}

	public void doComment4Op(Map<String, Object> params) {
		mapper.doComment4Op(params);
	}

	public void addTradeNo4OrgOrder(Map<String, String> orderinfo) {
		mapper.addTradeNo4OrgOrder(orderinfo);
	}

	public void payed4OrgOrder(Map<String, Object> param) {
		mapper.payed4OrgOrder(param);
	}

	public void updateTradeInfo4OrgOrder(Map<String, Object> tradeparam) {
		mapper.updateTradeInfo4OrgOrder(tradeparam);
	}
	
	public void payed4OpOrder(Map<String, Object> param) {
		mapper.payed4OpOrder(param);
	}
	
	public void updateTradeInfo4OpOrder(Map<String, Object> tradeparam) {
		mapper.updateTradeInfo4OpOrder(tradeparam);
	}

	public Map<String, Object> getLeCompanyInfo(String companyid) {
		return mapper.getLeCompanyInfo(companyid);
	}

	public Map<String, Object> getOpInfo() {
		return mapper.getOpInfo();
	}

	public PassengerOrder getUnderTakeOrder4Org(String account) {
		return mapper.getUnderTakeOrder4Org(account);
	}

	public PassengerOrder getUnderTakeOrder4Op(String account) {
		return mapper.getUnderTakeOrder4Op(account);
	}

	public void addTradeNo4OpOrder(Map<String, String> orderinfo) {
		mapper.addTradeNo4OpOrder(orderinfo);
	}

	public PassengerOrder getOrderByOutno4Org(String out_trade_no) {
		return mapper.getOrderByOutno4Org(out_trade_no);
	}

	public PassengerOrder getOrderByOutno4Op(String out_trade_no) {
		return mapper.getOrderByOutno4Op(out_trade_no);
	}

	public List<Map<String, Object>> getOrders4OpSec(Map<String, Object> param) {
		return mapper.getOrders4OpSec(param);
	}

	public Map<String, Object> getOrder4OpNetCar(Map<String, Object> param) {
		return mapper.getOrder4OpNetCar(param);
	}
	
	public Map<String, Object> getOrder4OrgNetCar(Map<String, Object> param) {
		return mapper.getOrder4OrgNetCar(param);
	}

	public Map<String, Object> getOrder4OpTaxi(Map<String, Object> param) {
		return mapper.getOrder4OpTaxi(param);
	}

	public Map<String, Object> getUnpayOrders4OpNetCar(Map<String, Object> param) {
		return mapper.getUnpayOrders4OpNetCar(param);
	}

	public Map<String, Object> getUnpayOrders4OpTaxi(Map<String, Object> param) {
		return mapper.getUnpayOrders4OpTaxi(param);
	}

	public void registerAlarm(Map<String, Object> alarminfo) {
		mapper.registerAlarm(alarminfo);
	}

	public Map<String, Object> getReserveInfo4OpTaxi(Map<String, Object> param) {
		return mapper.getReserveInfo4OpTaxi(param);
	}

	public Map<String, Object> getServiceOder4OpNetCar(Map<String, Object> param) {
		return mapper.getServiceOder4OpNetCar(param);
	}

	public Map<String, Object> getServiceOder4OpTaxi(Map<String, Object> param) {
		return mapper.getServiceOder4OpTaxi(param);
	}

	public void updateOrderState4OpTaxi(Map<String, Object> params) {
		mapper.updateOrderState4OpTaxi(params);
	}

	public void payed4OpTaxiOrder(Map<String, Object> params) {
		mapper.payed4OpTaxiOrder(params);
	}

	public void addTradeNo4OpTaxiOrder(Map<String, String> params) {
		mapper.addTradeNo4OpTaxiOrder(params);
	}

	public Map<String, Object> getPayTradeRecord4Org(String out_trade_no) {
		return mapper.getPayTradeRecord4Org(out_trade_no);
	}

	public Map<String, Object> getPayTradeRecord4OpNetCar(String out_trade_no) {
		return mapper.getPayTradeRecord4OpNetCar(out_trade_no);
	}

	public Map<String, Object> getPayTradeRecord4OpTaxi(String out_trade_no) {
		return mapper.getPayTradeRecord4OpTaxi(out_trade_no);
	}

	public void updateTradeInfo4OpTaxiOrder(Map<String, Object> tradeparam) {
		mapper.updateTradeInfo4OpTaxiOrder(tradeparam);
	}

	public void doComment4OpTaxi(Map<String, Object> params) {
		mapper.doComment4OpTaxi(params);
	}

	public PassengerOrder getServiceOrder4Org(Map<String, Object> params) {
		return mapper.getServiceOrder4Org(params);
	}

	public void setOrderFail4Org(Map<String, Object> params) {
		mapper.setOrderFail4Org(params);
	}

	public void setOrderdao4OpTaxi(Map<String, Object> params) {
		mapper.setOrderdao4OpTaxi(params);
	}

	public void setOrderdao4OpNetCar(Map<String, Object> params) {
		mapper.setOrderdao4OpNetCar(params);
	}

}
