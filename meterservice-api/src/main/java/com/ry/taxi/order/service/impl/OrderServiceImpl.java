/**
 * 
 */
package com.ry.taxi.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ry.taxi.base.constant.ErrorEnum;
import com.ry.taxi.base.exception.RyTaxiException;
import com.ry.taxi.order.domain.OpTaxiOrder;
import com.ry.taxi.order.domain.Oporderpaymentrecord;
import com.ry.taxi.order.domain.PubDriver;
import com.ry.taxi.order.mapper.DriverMapper;
import com.ry.taxi.order.mapper.OpTaxiOrderMapper;
import com.ry.taxi.order.request.DistanceUploadParam;
import com.ry.taxi.order.request.DriverArrivalParam;
import com.ry.taxi.order.request.DriverCancelParam;
import com.ry.taxi.order.request.DriverStartParam;
import com.ry.taxi.order.request.DriverTakeParam;
import com.ry.taxi.order.request.EndCalculationParam;
import com.ry.taxi.order.request.PaymentConfirmation;
import com.ry.taxi.order.request.StartCalculationParam;
import com.ry.taxi.order.service.OrderService;
import com.ry.taxi.util.map.AddressUitl;
import com.ry.taxi.util.map.GpsUtil;
import com.ry.taxi.util.map.Point;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.OrdersortColumn;
import com.szyciov.driver.enums.PayState;
import com.szyciov.entity.Retcode;
import com.szyciov.message.TaxiOrderMessage;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.UNID;
import com.xunxintech.ruyue.coach.io.date.DateUtil;

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
	
	
	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private DriverMapper driverMapper;
	
	@Autowired
	private OpTaxiOrderMapper opTaxiOrderMapper;
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int doTakingOrder(DriverTakeParam param) {
		//查询订单信息
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrderNum());
		logger.info("doTakingOrder,param:{},order:{}",param, taxiOrder);
		//1.判断订单状态是否为0-待接单，1-待人工派单,在这些订单状态下才能应邀成功
		if (taxiOrder == null)
			return ErrorEnum.e3012.getValue();//订单不存在
		else if (!(StringUtils.equals(OrderState.WAITTAKE.state, taxiOrder.getOrderstatus()) || StringUtils.equals(OrderState.MANTICSEND.state, taxiOrder.getOrderstatus())))
			return ErrorEnum.e3014.getValue();//订单状态不正确
		//2.根据司机资格证,查询司机信息
		PubDriver driver =  driverMapper.getDriverByJobNum(param.getCertNum());
		logger.info("doTakingOrder,param:{},driver:{}",param, driver);
		//系统若没有司机信
		if(driver == null){
			return ErrorEnum.e3017.getValue();//获取司机信息失败
		}
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
		
		//更改司机电话
		if(!StringUtils.equals(driver.getPhone(), param.getMobile())){
			driverMapper.updateDriverphone(driver.getId(), param.getMobile());
		}
		
		int updateResult = opTaxiOrderMapper.updateTakingOrder(taxiOrder);
		if (updateResult == 0)
			return ErrorEnum.e3015.getValue();//订单状态不正确
//		if (!sendMessage4Order(taxiOrder,Arrays.asList(taxiOrder.getPassengerphone())))
//			throw new RyTaxiException(ErrorEnum.e3016); //订单状态-消息推送失败
		return 0;
	}
	
	
	
	/**
	 * 订单状态变更给司机和乘客发送推送
	 */
	private boolean sendMessage4Order(OpTaxiOrder order,List<String> phones){
		try{
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
		}
		catch(Exception e){
			logger.error("消息推送异常错误:{}",e.getMessage());
			return false;
		}
		return true;
	}
	
	
	/**
	 * 司机执行订单通知（预约单）
	 */
	@Override
	public int doDriverStart(DriverStartParam param) {
		
		//查询订单信息
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrdernum());
		logger.info("doDriverStart,param:{},order:{}",param, taxiOrder);
		if (taxiOrder == null){
			//订单不存在
			return ErrorEnum.e3012.getValue();
		}else if(!(StringUtils.equals(OrderState.WAITSTART.state, taxiOrder.getOrderstatus()))){
			//订单状态不正确
			return ErrorEnum.e3014.getValue();
		}
				
		//解析司机当前地址
		JSONObject result = AddressUitl.getAddress(param.getLatitude(),param.getLongitude());

		//如果地址解析失败,返回失败
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info(result.toString());
			return ErrorEnum.e3018.getValue();
		}	
		
		//判断是否为百度地图坐标,如果不是需要转换
		double lat = param.getLatitude();
		double lng = param.getLongitude();
		if (param.getMapType() != 1 ){
			Point point = GpsUtil.bd_encrypt(lat, lng);
			if (point!=null){
				lat = point.getLat();
				lng = point.getLng();
			}
		}
		
		String departurecity = result.getString("city");
		String departureaddress = result.getString("address");
		taxiOrder.setOrderstatus(OrderState.START.state);
		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrderState.START.state));
		taxiOrder.setDeparturetime(DateUtil.string2Date(param.getDeparturetime()));
		taxiOrder.setDeparturelat(lat);
		taxiOrder.setDeparturelng(lng);
		taxiOrder.setDeparturecity(departurecity);
		taxiOrder.setDepartureaddress(departureaddress);
		
		//更新订单
		int updateResult = opTaxiOrderMapper.updateTaxiOrder(taxiOrder);
		
		if(updateResult == 0 ){
			return ErrorEnum.e3015.getValue();//订单状态不正确
		}
		if (!sendMessage4Order(taxiOrder,Arrays.asList(taxiOrder.getPassengerphone()))){ 
			logger.error("司机执行订单通知{},消息推送失败:{}",param,ErrorEnum.e3016.getValue());
		}
		return 0;
	}
    
	/**
	 * 司机到达乘客起点
	 */
	@Override
	public int doDriverArrival(DriverArrivalParam param) {
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrderNum());
		logger.info("doDriverArrival,param:{},order:{}",param, taxiOrder);
		if (taxiOrder == null)
			return ErrorEnum.e3012.getValue();//订单不存在
		if(OrderState.SERVICEDONE.state.equals(taxiOrder.getOrderstatus()))
			//订单状态不正确
			return ErrorEnum.e3014.getValue();
		
		//判断是否为百度地图坐标,如果不是需要转换
		double lat = param.getLatitude();
		double lng = param.getLongitude();
		if (param.getMapType() != 1 ){
			Point point = GpsUtil.bd_encrypt(lat, lng);
			if (point!=null){
				lat = point.getLat();
				lng = point.getLng();
			}
		}
		
		//解析司机当前地址
		JSONObject result = AddressUitl.getAddress(lat,lng);

		//如果地址解析失败,返回失败
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info(result.toString());
			return ErrorEnum.e3018.getValue();
		}		
		
		String arrivalcity = result.getString("city");
		String arrivaladdress = result.getString("address");

		taxiOrder.setOrderstatus(OrderState.ARRIVAL.state);
		taxiOrder.setArrivaltime(DateUtil.string2Date(param.getArrivalTime()));  
		taxiOrder.setArrivallat(lat);
		taxiOrder.setArrivallng(lng);
		taxiOrder.setArrivalcity(arrivalcity);
		taxiOrder.setArrivaladdress(arrivaladdress);
		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.ARRIVAL.state));
		//更新订单
		int updateResult = opTaxiOrderMapper.updateTaxiOrder(taxiOrder);
		if (updateResult == 0)
			return ErrorEnum.e3012.getValue();//订单不存在
		if (!sendMessage4Order(taxiOrder,Arrays.asList(taxiOrder.getPassengerphone()))){ 
			logger.error("司机到达乘客起点通知{},消息推送失败:{}",param,ErrorEnum.e3016.getValue());
		}
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
		logger.info("doDriverCancel,param:{},order:{}",param, taxiOrder);
		if(taxiOrder == null){
			//订单不存在
			return ErrorEnum.e3012.getValue();
		}else if(!(StringUtils.equals(OrderState.WAITSTART.state, taxiOrder.getOrderstatus()))){
			//订单状态不正确
			return ErrorEnum.e3014.getValue();
		}
		
		taxiOrder.setOrderstatus(OrderState.CANCEL.state);
		taxiOrder.setCanceltime(param.getCanceltime().toDate());
		taxiOrder.setOrderstatus(OrderState.CANCEL.state);
		
			
		//更新订单
		int updateResult = opTaxiOrderMapper.updateTaxiOrder(taxiOrder);
		
		PubDriver driver = driverMapper.getTaxiOrderno(taxiOrder.getDriverid());
		logger.info("doDriverCancel,param:{},driver:{}",param, driver);
		driver.setWorkstatus("0");
		
		//更新司机状态
		driverMapper.setDriverWorkstatus(driver);
				
		if (updateResult == 0){
			return ErrorEnum.e3012.getValue();//订单不存在
		}
		if (!sendMessage4Order(taxiOrder,Arrays.asList(taxiOrder.getPassengerphone()))){ 
			logger.error("司机取消通知{},消息推送失败:{}",param,ErrorEnum.e3016.getValue());
		}
		
		return 0;
	}



	/**
	 * 压表
	 */
	@Override
	public int doStartCalculation(StartCalculationParam param) {	
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrderNum());
		logger.info("doStartCalculation,param:{},order:{}",param, taxiOrder);
		if (taxiOrder == null)
			return ErrorEnum.e3012.getValue();//订单不存在
		if(OrderState.SERVICEDONE.state.equals(taxiOrder.getOrderstatus()))
			//订单状态不正确
			return ErrorEnum.e3014.getValue();
		// 新增 开始服务地址城市  开始服务地址  开始服务地址经度 开始服务地址纬度
		//判断是否为百度地图坐标,如果不是需要转换
		double lat = param.getLatitude();
		double lng = param.getLongitude();
		if (param.getMapType() != 1 ){
			Point point = GpsUtil.bd_encrypt(lat, lng);
			if (point!=null){
				lat = point.getLat();
				lng = point.getLng();
			}
		}
		
		//解析司机当前地址
		JSONObject result = AddressUitl.getAddress(lat,lng);
		//如果地址解析失败,返回失败
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info(result.toString());
			return ErrorEnum.e3018.getValue();
		}		
		
		String startcity = result.getString("city");
		String startaddress = result.getString("address");
		
		taxiOrder.setOrderstatus(OrderState.INSERVICE.state);
		taxiOrder.setStarttime(DateUtil.string2Date(param.getPassengerGetOnTime()));
		taxiOrder.setOrdersortcolumn(Integer.valueOf(OrdersortColumn.INSERVICE.state));
		taxiOrder.setStartcity(startcity);
		taxiOrder.setStartaddress(startaddress);
		taxiOrder.setStartlng(lat);
		taxiOrder.setStartllat(lng);
		//更新订单
		int updateResult = opTaxiOrderMapper.updateTaxiOrder(taxiOrder);
		if (updateResult == 0)
			return ErrorEnum.e3012.getValue();//订单不存在
		if (!sendMessage4Order(taxiOrder,Arrays.asList(taxiOrder.getPassengerphone()))){ 
			logger.error("压表通知{},消息推送失败:{}",param,ErrorEnum.e3016.getValue());
		}
		return 0;
	}


	/**
	 * 起表
	 */
	@Override
	public int doEndCalculation(EndCalculationParam param) {
		
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrdernum());
		logger.info("doEndCalculation,param:{},order:{}",param, taxiOrder);
		if (taxiOrder == null){
			return ErrorEnum.e3012.getValue();//订单不存在
		}
		if(OrderState.SERVICEDONE.state.equals(taxiOrder.getOrderstatus())){
			//订单状态不正确
			return ErrorEnum.e3014.getValue();
		}			
		//解析司机当前地址
		JSONObject result = AddressUitl.getAddress(param.getInputlat(),param.getInputlon());

		//如果地址解析失败,返回失败
		if(result.getInt("status") != Retcode.OK.code) {
			logger.info(result.toString());
			return ErrorEnum.e3018.getValue();
		}	
		
		//判断是否为百度地图坐标,如果不是需要转换
		double lat = param.getInputlat();
		double lng = param.getInputlon();
		if (param.getMaptype() != 1 ){
			Point point = GpsUtil.bd_encrypt(lat, lng);
			if (point!=null){
				lat = point.getLat();
				lng = point.getLng();
			}
		}
			
		taxiOrder.setOrderstatus(OrderState.WAITMONEY.state);
		taxiOrder.setEndcity(result.getString("city"));
		taxiOrder.setEndaddress(result.getString("address"));
		taxiOrder.setEndlng(lng);
		taxiOrder.setEndllat(lat);
		taxiOrder.setEndtime(DateUtil.string2Date(String.valueOf(param.getOrderendtime())));
		
		//更新订单
		int updateResult = opTaxiOrderMapper.updateTaxiOrder(taxiOrder);
		
		PubDriver driver =  driverMapper.getDriverByJobNum(param.getCertnum());
		logger.info("doEndCalculation,param:{},driver:{}",param, driver);
		driver.setWorkstatus("0");
		
		//更新司机状态
		driverMapper.setDriverWorkstatus(driver);
		
		if(updateResult == 0){
			return ErrorEnum.e3012.getValue();
		}
		if (!sendMessage4Order(taxiOrder,Arrays.asList(taxiOrder.getPassengerphone()))){ 
			logger.error("起表通知{},消息推送失败:{}",param,ErrorEnum.e3016.getValue());
		}
		return 0;
	}


    /**
     * 里程回传
     */
	@Override
	public int doDistanceUpload(DistanceUploadParam param) {
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrderNum());
		logger.info("doDistanceUpload,param:{},order:{}",param, taxiOrder);
		if (taxiOrder == null)
			return ErrorEnum.e3012.getValue();//订单不存在
		
		taxiOrder.setMilegae(param.getAccumulatedDistance());
		//更新订单
		int updateResult = opTaxiOrderMapper.updateTaxiOrder(taxiOrder);
		if(updateResult == 0){
			return ErrorEnum.e3012.getValue();
		}
		return 0;
	}


    /**
     * 支付通知
     */
	@Override
	public int doPaymentConfirmation(PaymentConfirmation param) {
		
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrdernum());
		logger.info("doPaymentConfirmation,param:{},order:{}",param, taxiOrder);
		taxiOrder.setOrderstatus(OrderState.SERVICEDONE.state);
		taxiOrder.setShouldpayamount(param.getTotalpayable());
		taxiOrder.setActualpayamount(param.getTotalFee());
		taxiOrder.setOriginalorderamount(param.getTotalpayable());
		taxiOrder.setPaytype(String.valueOf(param.getTranstype()));
		taxiOrder.setPaymentmethod("1");
		taxiOrder.setPaymenttime(new Date());
		
		//更新订单
		int updateResult = opTaxiOrderMapper.updateTaxiOrder(taxiOrder);
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String out_trade_no = format.format(date)+UNID.getUNID();

		//记录订单明细
		Oporderpaymentrecord pde = new Oporderpaymentrecord();
		
		pde.setOuttradeno(out_trade_no);
		pde.setOrderno(param.getOrdernum());
		pde.setPaymenttype(String.valueOf(param.getTranstype()));
		pde.setTradeno(param.getTransId());
		pde.setPrivatekey("");
		pde.setOperateresult(0);
		pde.setCreatetime(new Date());
		pde.setUpdatetime(new Date());
		pde.setStatus(0);
		
		int insetinfo = driverMapper.insertOrder(pde);

		if(updateResult == 0 && insetinfo == 0){
			return ErrorEnum.e3012.getValue();
		}
		if (!sendMessage4Order(taxiOrder,Arrays.asList(taxiOrder.getPassengerphone()))){ 
			logger.error("支付通知{},消息推送失败:{}",param,ErrorEnum.e3016.getValue());
		}
		return 0;
	}



}
