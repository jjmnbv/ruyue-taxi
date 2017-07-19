/**
 * 
 */
package com.ry.taxi.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ry.taxi.Util.AddressUitl;
import com.ry.taxi.base.constant.ErrorEnum;
import com.ry.taxi.order.domain.OpTaxiOrder;
import com.ry.taxi.order.domain.PubDriver;
import com.ry.taxi.order.mapper.DriverMapper;
import com.ry.taxi.order.mapper.OpTaxiOrderMapper;
import com.ry.taxi.order.request.DriverArrivalParam;
import com.ry.taxi.order.request.DriverCancelParam;
import com.ry.taxi.order.request.DriverStartParam;
import com.ry.taxi.order.request.DriverTakeParam;
import com.ry.taxi.order.service.OrderService;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.OrdersortColumn;
import com.szyciov.driver.enums.PayState;
import com.szyciov.entity.Retcode;
import com.szyciov.message.TaxiOrderMessage;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.passenger.util.MessageUtil;

import net.sf.json.JSONObject;

/**
 * @Title:OrderServiceImpl.java
 * @Package com.ry.taxi.order.service.impl
 * @Description
 * @author zhangdd
 * @date 2017年7月17日 下午2:28:35
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	
	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private DriverMapper driverMapper;
	
	@Autowired
	private OpTaxiOrderMapper opTaxiOrderMapper;
	
	
	
	@Transactional
	@Override
	public int doTakingOrder(DriverTakeParam param) {
		//查询订单信息
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrderNum());
		//1.判断订单状态是否为0-待接单，1-待人工派单,在这些订单状态下才能应邀成功
		if (taxiOrder == null)
			return ErrorEnum.e3012.getValue();//订单不存在
		else if (!(StringUtils.equals(OrderState.WAITTAKE.state, taxiOrder.getOrderstatus()) || StringUtils.equals(OrderState.MANTICSEND.state, taxiOrder.getOrderstatus())))
			return ErrorEnum.e3012.getValue();//订单状态不正确
		//2.根据司机资格证,查询司机信息
		PubDriver driver =  driverMapper.getDriverByJobNum(param.getCertNum());
		
		taxiOrder.setOrderstatus(OrderState.WAITSTART.state);
		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.WAITSTART.state));
		taxiOrder.setOrdertime(new Date());
		taxiOrder.setPaymentstatus(PayState.ALLNOPAY.state);
		taxiOrder.setCompanyid(driver.getLeasescompanyid());
		taxiOrder.setDriverid(driver.getId());
		taxiOrder.setVehicleid(driver.getVehicleid());
		taxiOrder.setPlateno(param.getPlateNum());
		taxiOrder.setVehcbrandname(driver.getCarbrand());
		taxiOrder.setVehclinename(driver.getCarvehcline());
		taxiOrder.setBelongleasecompany(driver.getBelongleasecompany());
		
		int updateResult = opTaxiOrderMapper.updateTakingOrder(taxiOrder);
		if (updateResult == 0)
			return ErrorEnum.e3015.getValue();//订单状态不正确
		if (!sendMessage4Order(taxiOrder,null))
			return ErrorEnum.e3016.getValue();//订单状态-消息推送失败
		return 0;
	}
	
	
	
	/**
	 * 订单状态变更给司机和乘客发送推送
	 */
	private boolean sendMessage4Order(OpTaxiOrder order,List<String> phones){
		String messagetype = null;
		if(OrderState.WAITSTART.state.equals(order.getOrderstatus()) && phones == null){
			messagetype = TaxiOrderMessage.TAKEORDER;
		}else if(!OrderState.WAITSTART.state.equals(order.getOrderstatus()) && phones == null){
			messagetype = TaxiOrderMessage.TAXI_ORDERHINT;
		}else{
			messagetype = TaxiOrderMessage.TAXI_DRIVERMESSAGE;
		}
		TaxiOrderMessage om = null;
		//如果推送手机号为空,表示是抢单推送,推送给相关人
		if(phones == null){
			om = new TaxiOrderMessage(order,messagetype);
			//推送给其他之前推送过抢单消息的司机
		}else{
			om = new TaxiOrderMessage(messagetype,phones);
		}
		MessageUtil.sendMessage(om);
		return true;
	}
	
	
	/**
	 * 司机执行订单通知（预约单）
	 */
	@Override
	@Transactional
	public int doDriverStart(DriverStartParam param,BaiduApiQueryParam baiduapiquery) {
		
		//查询订单信息
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrdernum());
		
		if (taxiOrder == null){
			//订单不存在
			return ErrorEnum.e3012.getValue();
		}else if(!(StringUtils.equals(OrderState.WAITSTART.state, taxiOrder.getOrderstatus()))){
			//订单状态不正确
			return ErrorEnum.e3012.getValue();
		}
				
		//解析司机当前地址
		JSONObject result = AddressUitl.getAddress(baiduapiquery);
		
		//如果地址解析失败,返回失败
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info(result.toString());
			return ErrorEnum.e1005.getValue();
		}		
		
		String departurecity = result.getString("city");
		String departureaddress = result.getString("address");
		taxiOrder.setOrderstatus(OrderState.START.state);
		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrderState.START.state));
		taxiOrder.setOrdertime(new Date());
		taxiOrder.setDeparturelat(baiduapiquery.getDriverLat());
		taxiOrder.setDeparturelng(baiduapiquery.getDriverLng());
		taxiOrder.setDeparturecity(departurecity);
		taxiOrder.setDepartureaddress(departureaddress);
		
		//更新订单
		int updateResult = opTaxiOrderMapper.updateTakingOrder(taxiOrder);
		
		if(updateResult == 0 ){
			return ErrorEnum.e3015.getValue();//订单状态不正确
		}
		if (!sendMessage4Order(taxiOrder,null)){
			return ErrorEnum.e3016.getValue();//订单状态-消息推送失败
		}
		return 0;
	}

	@Transactional
	@Override
	public int doDriverArrival(DriverArrivalParam param) {
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrderNum());
		taxiOrder.setOrderstatus(OrderState.ARRIVAL.state);
		taxiOrder.setArrivaltime(new Date()); // param.getArrivalTime()
		taxiOrder.setArrivallat(taxiOrder.getOnaddrlat());
		taxiOrder.setArrivallng(taxiOrder.getOnaddrlng());
		taxiOrder.setArrivalcity(taxiOrder.getOncity());
		taxiOrder.setArrivaladdress(taxiOrder.getOnaddress());
		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.ARRIVAL.state));
		//更新订单
		int updateResult = opTaxiOrderMapper.updateTaxiOrder(taxiOrder);
		if (updateResult == 0)
			return ErrorEnum.e3012.getValue();//订单不存在
		return 0;
	}


	/**
	 * 司机取消通知
	 */
	@Override
	@Transactional
	public int doDriverCancel(DriverCancelParam param) {
		//查询订单信息
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrdernum());
		
		if(taxiOrder == null){
			//订单不存在
			return ErrorEnum.e3012.getValue();
		}else if(!(StringUtils.equals(OrderState.WAITSTART.state, taxiOrder.getOrderstatus()))){
			//订单状态不正确
			return ErrorEnum.e3012.getValue();
		}
		
		
		
		return 0;
	}


}
