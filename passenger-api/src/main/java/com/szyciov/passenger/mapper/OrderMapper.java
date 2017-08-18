package com.szyciov.passenger.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.passenger.entity.DriverInfo;
import com.szyciov.passenger.entity.Order4List;
import com.szyciov.passenger.entity.PassengerOrder;

public interface OrderMapper {

	PassengerOrder getOrder4Org(Map<String, Object> params);

	PassengerOrder getOrder4Op(Map<String, Object> params);

	List<Order4List> getOrders4Org(Map<String, Object> params);

	void updateOrderState4Org(Map<String, Object> params);

	DriverInfo getDriverInfo(String driverid);

	PassengerOrder getOrderByOrderno4Org(String orderno);

	void doComment4Org(Map<String, Object> params);

	void addTradeNo4OrgOrder(Map<String, String> orderinfo);

	void payed4OrgOrder(Map<String, Object> param);

	void updateTradeInfo4OrgOrder(Map<String, Object> tradeparam);

	List<Order4List> getOrders4Op(Map<String, Object> params);

	void updateOrderState4Op(Map<String, Object> params);

	PassengerOrder getOrderByOrderno4Op(String orderno);

	void doComment4Op(Map<String, Object> params);

	Map<String, Object> getLeCompanyInfo(String companyid);

	Map<String, Object> getOpInfo();

	PassengerOrder getUnderTakeOrder4Org(String account);

	PassengerOrder getUnderTakeOrder4Op(String account);

	void addTradeNo4OpOrder(Map<String, String> orderinfo);

	void payed4OpOrder(Map<String, Object> param);

	void updateTradeInfo4OpOrder(Map<String, Object> tradeparam);

	PassengerOrder getOrderByOutno4Org(String out_trade_no);

	PassengerOrder getOrderByOutno4Op(String out_trade_no);

	List<Map<String, Object>> getOrders4OpSec(Map<String, Object> param);

	Map<String, Object> getOrder4OpNetCar(Map<String, Object> param);

	Map<String, Object> getOrder4OpTaxi(Map<String, Object> param);

	Map<String, Object> getUnpayOrders4OpNetCar(Map<String, Object> param);

	Map<String, Object> getUnpayOrders4OpTaxi(Map<String, Object> param);

	void registerAlarm(Map<String, Object> alarminfo);

	Map<String, Object> getOrder4OrgNetCar(Map<String, Object> param);

	Map<String, Object> getReserveInfo4OpTaxi(Map<String, Object> param);

	Map<String, Object> getServiceOder4OpNetCar(Map<String, Object> param);

	Map<String, Object> getServiceOder4OpTaxi(Map<String, Object> param);

	void updateOrderState4OpTaxi(Map<String, Object> params);

	void payed4OpTaxiOrder(Map<String, Object> params);

	void addTradeNo4OpTaxiOrder(Map<String, String> params);

	Map<String, Object> getPayTradeRecord4Org(String out_trade_no);

	Map<String, Object> getPayTradeRecord4OpNetCar(String out_trade_no);

	Map<String, Object> getPayTradeRecord4OpTaxi(String out_trade_no);

	void updateTradeInfo4OpTaxiOrder(Map<String, Object> tradeparam);

	void doComment4OpTaxi(Map<String, Object> params);

	PassengerOrder getServiceOrder4Org(Map<String, Object> params);

	void setOrderFail4Org(Map<String, Object> params);

	void setOrderdao4OpTaxi(Map<String, Object> params);

	void setOrderdao4OpNetCar(Map<String, Object> params);

	DriverInfo getDriverInfo4New(Map<String, Object> pp);

	Map<String, Object> getOrderCouponInfo(String orderno);

	Map<String, Object> getCancelInfo4Org(String orderno);

	Map<String, Object> getCancelInfo4Optaxi(String orderno);

	Map<String, Object> getCancelInfo4Opnetcar(String orderno);

	void updateCancelReson4OrgNetCar(Map<String, Object> pp);

	void updateCancelReson4OpTaxi(Map<String, Object> pp);

	void updateCancelReson4OpNetCar(Map<String, Object> pp);

	Map<String, Object> getUnpayOrder4OrgNetCar_cancel(Map<String, Object> params);

	Map<String, Object> getUnpayOrder4OpNetCar_cancel(Map<String, Object> params);

	Map<String, Object> getUnpayOrder4OpTaxi_cancel(Map<String, Object> params);

}