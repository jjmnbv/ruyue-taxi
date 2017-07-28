/**
 * 
 */
package com.szyciov.carservice.util.sendservice.sendmethod.op.car;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jpush.api.push.model.PushPayload;
import com.szyciov.carservice.service.MessagePubInfoService;
import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.carservice.service.SendInfoService;
import com.szyciov.carservice.util.OrderRedisMessageFactory;
import com.szyciov.carservice.util.sendservice.sendmethod.AbstractSendMethod;
import com.szyciov.carservice.util.sendservice.sendmethod.op.taxi.ForceSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.carservice.util.sendservice.sendrules.impl.op.car.OpCarGrabSendRuleImp;
import com.szyciov.driver.entity.DriverMessage;
import com.szyciov.driver.entity.OrderInfo;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.DriverMessageEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.PlatformType;
import com.szyciov.entity.PubDriver;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.SendRulesEnum;
import com.szyciov.enums.VehicleEnum;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.SendOrderDriverQueryParam;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.BaiduUtil;
import com.szyciov.util.PushObjFactory;
import com.szyciov.util.SMMessageUtil;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * @ClassName GrabSendMethodImp 
 * @author Efy Shu
 * @Description 抢派模式派单逻辑 （暂无此需求，逻辑无改动2017.06.26）
 * @date 2017年4月8日 上午10:48:55 
 */
public class OpCarGrabSendMethodImp extends AbstractSendMethod{

	private static final Logger logger = Logger.getLogger(ForceSendMethodImp.class);

	private SendInfoService sendInfoService;

	private MessagePubInfoService messagePubInfoService;

	private OrderApiService orderApiService;

	public OpCarGrabSendMethodImp(){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		this.sendInfoService = (SendInfoService) wac.getBean("sendInfoService");
		this.messagePubInfoService = (MessagePubInfoService) wac.getBean("MessagePubInfoService");
		this.orderApiService = (OrderApiService)wac.getBean("OrderApiService");
	}

	/**
	 *
	 * @see com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper#send(com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper, com.szyciov.entity.AbstractOrder)
	 */
	@Override
	public void send(SendRuleHelper rules, AbstractOrder order) {
		if(rules==null||order==null){
			logger.warn("运管端网约车抢派——信息不足，无法派单");
			return ;
		}
		OpCarGrabSendRuleImp temprules = (OpCarGrabSendRuleImp)rules;
		String usetype = temprules.getUsetype();
		if(SendRuleHelper.USE_TYPE_USENOW.equalsIgnoreCase(usetype)){
			//即可用车派单
			send_UseNow(temprules,order);
		}else if(SendRuleHelper.USE_TYPE_RESERVE.equalsIgnoreCase(usetype)){
			//预约用车派单
			send_Reserve(temprules,order);
		}
	}


	@Override
	protected void send_Reserve(SendRuleHelper rule, AbstractOrder orderinfo) {
		OpCarGrabSendRuleImp temprules = (OpCarGrabSendRuleImp)rule;
		Date usetime = orderinfo.getUsetime();
		//约车时限
		int carsinterval = temprules.getCarsinterval();
		Date current = new Date();
		//约车时限的2倍
		Date temptimeobj = new Date(current.getTime()+2*carsinterval*60*1000);
		if(usetime.after(temptimeobj)){
			//用车时间比较远，弱调度
			send_Reserve_SOW(rule, orderinfo, false);
		}else{
			//用车时间比较近，强调度
			send_Reserve_SOW(rule, orderinfo, true);
		}
	}
	/**
	 * 即刻用车
	 */
	@Override
	protected void send_UseNow(SendRuleHelper rule, AbstractOrder orderinfo){
		OpCarGrabSendRuleImp temprules = (OpCarGrabSendRuleImp)rule;
		List<PubDriver> drivers = getVariableDrivers(temprules,orderinfo);
		if(drivers==null||drivers.size()<=0){
			if(isOrderTakedOrCancel(orderinfo.getOrderno())){
				return ;
			}
			//没有可用的司机
			logger.warn("运管端网约车抢派，没有找到合法的司机！");
			String sendmodel = temprules.getSendmodel();
			if(SendRuleHelper.SEND_MODEL_SYSTEM.equalsIgnoreCase(sendmodel)){
				//派单失败，结束，推送消息
				logger.info("抢派模式，派单失败结束！");
			}else if(SendRuleHelper.SEND_MODEL_MANTICANDSYSTEM.equalsIgnoreCase(sendmodel)){
				//进入人工派单
				logger.info("抢派模式，系统派单结束，进入人工派单！");
				orderinfo.setOrderstatus(OrderState.MANTICSEND.state);
				orderinfo.setPushnumber(0);
				go2Mantic(orderinfo);
			}
			return ;
		}


	}

	@Override
	protected boolean canPush2Driver(PubDriver driver, AbstractOrder order) {
		return false;
	}

	/**
	 * 发送订单到司机
	 * @param temprules
	 * @param drivers
	 * @param orderinfo
	 */
	private void sendOrder2Driver(OpCarGrabSendRuleImp temprules,List<PubDriver> drivers,AbstractOrder orderinfo){
		//优化司机排序
		drivers = optimalSortDriver(drivers,orderinfo);
		String pushnumlimit = temprules.getPushnumlimit();
		int pushmax = temprules.getPushnum();
		List<String> driverphones = new ArrayList<String>();
		for(int i=0;i<drivers.size()&&(!"1".equals(pushnumlimit)||i<pushmax);i++){
			PubDriver driver = drivers.get(i);
			String phone = driver.getPhone();
			if(StringUtils.isNotBlank(phone)){
				driverphones.add(phone);
			}
		}
		// 过滤已经发送过即刻抢单的司机
		//CommonService.filterHadSendDriver(driverphones, temprules.getDriversendinterval());

		if(driverphones.size()>0){
			if(isOrderTakedOrCancel(orderinfo.getOrderno())){
				return ;
			}
			//推送订单
			OrderInfo order = abstractOrderToOrderInfo(orderinfo);
			order.setGrabtime(temprules.getDriversendinterval());

			PushPayload pushload4android = PushObjFactory.createGrabOrderObj4Android(order, driverphones);
			PushPayload pushload4ios = PushObjFactory.createGrabOrderObj4IOS(order, driverphones);
			AppMessageUtil.send(pushload4ios, pushload4android, AppMessageUtil.APPTYPE_DRIVER);
		}
	}

	/**
	 * 预约派单强调度或者弱调度
	 * @param rule
	 * @param orderinfo
	 * @param sow false->弱调度,true->强调度 
	 */
	@Override
	protected void send_Reserve_SOW(SendRuleHelper rule, AbstractOrder orderinfo,boolean sow){
		OpCarGrabSendRuleImp temprules = (OpCarGrabSendRuleImp)rule;
		String pushnumlimit = temprules.getPushnumlimit();
		int pushnum = temprules.getPushnum();
		int syssendinterval_send = temprules.getSystemsendinterval();
		Date time_send = new Date();
		int sleeptime = 3;
		Date temptime_send = new Date(time_send.getTime()+syssendinterval_send*60*1000-(sleeptime)*1000);
		Date changetime_send = new Date();
		int tempcount = 0;
		List<String> driverids = new ArrayList<String>();
		List<PubDriver> alreadydrivers = new ArrayList<>();
		//初始化预约单查询司机条件
		SendOrderDriverQueryParam param = getReserveQueryParam(temprules,orderinfo);
		//自主选单循环找司机
		while(temptime_send.after(changetime_send)&&(!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(pushnumlimit)||pushnum>=tempcount)){
			try {
				//获取需要获取的车型ID
				List<String> models = sendInfoService.listOpvehiclemodelId(param.getSelectedModelId(),param.getNextCount());
				param.setQueryModelIds(models);
				//获取司机
				List<PubDriver> drivers = getVariableDrivers4Reverve(param,orderinfo);
				List<PubDriver> cansenddrivers = new ArrayList<PubDriver>();
				if(drivers!=null){
					for(int i=0;i<drivers.size();i++){
						PubDriver pubdriver = drivers.get(i);
						if(!driverids.contains(pubdriver.getId())){
							cansenddrivers.add(pubdriver);
							driverids.add(pubdriver.getId());
						}
					}
				}
				if(isOrderTakedOrCancel(orderinfo.getOrderno())){
					return ;
				}
				if(cansenddrivers!=null&&cansenddrivers.size()>0){
					onceSend4Reserve_SOW(temprules, orderinfo, cansenddrivers,tempcount,sow);
					alreadydrivers.addAll(cansenddrivers);
					tempcount += cansenddrivers.size();
				}
				Thread.sleep(sleeptime*1000);
				changetime_send = new Date();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return ;
		}
		//等待最后一秒找到的司机接单
		String sendmodel = temprules.getSendmodel();
		//是否查询到了合法的司机，如果有主动推送抢派一次
		if(alreadydrivers.size()>0){
			try{
				//抢派推送一次
				grabSend2Drivers(temprules,orderinfo,alreadydrivers,sow);
				return ;
			}catch(Exception e){
				logger.error("抢派一次订单失败", e);
			}
		}
		//判断是否有人工派单
		if(SendRuleHelper.SEND_MODEL_SYSTEM.equalsIgnoreCase(sendmodel)){
			//派单失败，结束，推送消息
			logger.info("抢派模式，派单失败结束！");
		}else if(SendRuleHelper.SEND_MODEL_MANTICANDSYSTEM.equalsIgnoreCase(sendmodel)){
			//进入人工派单
			logger.info("抢派模式，系统派单结束，进入人工派单！");
			orderinfo.setOrderstatus(OrderState.MANTICSEND.state);
			orderinfo.setPushnumber(0);
			go2Mantic(orderinfo);
		}
	}

	/**
	 * 返回预约单查询条件
	 * @param rule
	 * @param orderinfo
	 * @return
	 */
	private SendOrderDriverQueryParam getReserveQueryParam(OpCarGrabSendRuleImp rule, AbstractOrder orderinfo){

		SendOrderDriverQueryParam param = new SendOrderDriverQueryParam();
		param.setCity(orderinfo.getOncity());
		param.setOffcity(orderinfo.getOffcity());
		param.setCompanyid(orderinfo.getCompanyid());
		//添加下单人id，过滤司机与下单人为同一人
		param.setUserid(orderinfo.getUserid());
		//下单车型
		param.setSelectedModelId(orderinfo.getSelectedmodel());
		//运管端订单
		param.setPlatformType(PlatformTypeByDb.OPERATING.code);
		//司机类型 网约车
		param.setVehicleType(DriverEnum.DRIVER_TYPE_CAR.code);
		//升级次数默认为1
		param.setNextCount(1);

		//中心点
		double lat = orderinfo.getOnaddrlat();
		double lng = orderinfo.getOnaddrlng();
		double[] rangeinfo = BaiduUtil.getRange(lng, lat, (int)(rule.getMaxsendradius()*1000));
		param.setMinLng(rangeinfo[0]);
		param.setMaxLng(rangeinfo[1]);
		param.setMinLat(rangeinfo[2]);
		param.setMaxLat(rangeinfo[3]);

		return param;
	}

	/**
	 * 剔除给派单推送的时候的不合法司机
	 * （目前只有服务中的订单司机才需要剔除）
	 * @param alreadydrivers
	 * @return
	 */
	private void removeUnvaliableDrivers(List<PubDriver> alreadydrivers, AbstractOrder orderinfo){
		if(alreadydrivers==null||alreadydrivers.size()<=0){
			return ;
		}
		List<PubDriver> valiabledrivers = new ArrayList<PubDriver>();
		for(int i=0;i<alreadydrivers.size();i++){
			PubDriver driver = alreadydrivers.get(i);
			if(canPush2Driver(driver, orderinfo)){
				valiabledrivers.add(driver);
			}
		}
		alreadydrivers.clear();
		alreadydrivers.addAll(valiabledrivers);
	}
	
	/**
	 * 强派一次订单
	 * @param rule
	 * @param orderinfo
	 * @param alreadydrivers
	 * @param sow
	 */
	private void grabSend2Drivers(OpCarGrabSendRuleImp rule, AbstractOrder orderinfo, List<PubDriver> alreadydrivers,boolean sow) {
		removeUnvaliableDrivers(alreadydrivers, orderinfo);
		OrderInfo order = abstractOrderToOrderInfo(orderinfo);
		order.setGrabtime(rule.getDriversendinterval());
		List<String> driverphones = new ArrayList<String>();
		//查询出可用的司机司机超限后选择的方式
		dillWithDrivers(driverphones,alreadydrivers,rule,0,sow);
		//推送司机端
		if(driverphones!=null&&driverphones.size()>0){
            // 过滤已经发送过即刻抢单的司机
            //CommonService.filterHadSendDriver(driverphones, rule.getDriversendinterval());
            if(driverphones!=null&&driverphones.size()>0){
            	PushPayload pushpayload4android = PushObjFactory.createGrabOrderObj4Android(order, driverphones);
    			PushPayload pushpayload4ios = PushObjFactory.createGrabOrderObj4IOS(order, driverphones);
    			AppMessageUtil.send(pushpayload4ios, pushpayload4android, AppMessageUtil.APPTYPE_DRIVER);
            }
		}
		int grabtime = rule.getDriversendinterval();
		Date current = new Date();
		int sleeptime = 3;
		Date temptime = new Date(current.getTime()+grabtime*1000-sleeptime*1000);
		Date changetime = new Date();
		//自主选单循环找司机
		while(temptime.after(changetime)){
			try{
				if(isOrderTakedOrCancel(orderinfo.getOrderno())){
					return ;
				}
				Thread.sleep(sleeptime*1000);
				changetime = new Date();
			}catch(Exception e){
				logger.error("抢派一次失败了",e);
			}
		}
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return ;
		}
		//都没抢单，执行指定
		PubDriver driver = getForceDriver(alreadydrivers,orderinfo);
		if(driver!=null){
			orderinfo.setDriverid(driver.getId());
			orderinfo.setVehicleid(driver.getVehicleid());
			orderinfo.setVehcbrandname(driver.getVehcbrandname());
			orderinfo.setVehclinename(driver.getVehclinename());
			orderinfo.setPlateno(driver.getPlateno());
			orderinfo.setOrderstatus(OrderState.WAITSTART.state);
			orderinfo.setPaymentstatus(PayState.NOTPAY.state);
			orderinfo.setCompanyid(driver.getLeasescompanyid());
			orderinfo.setPushnumber(driverphones.size());
			orderinfo.setBelongleasecompany(driver.getBelongleasecompany());
			forceOrder2Driver(driver, orderinfo);
		}else{
			String sendmodel = rule.getSendmodel();
			//判断是否有人工派单
			if(SendRuleHelper.SEND_MODEL_SYSTEM.equalsIgnoreCase(sendmodel)){
				//派单失败，结束，推送消息
				logger.info("抢派模式，派单失败结束！");
			}else if(SendRuleHelper.SEND_MODEL_MANTICANDSYSTEM.equalsIgnoreCase(sendmodel)){
				//进入人工派单
				logger.info("抢派模式，系统派单结束，进入人工派单！");
				orderinfo.setOrderstatus(OrderState.MANTICSEND.state);
				orderinfo.setPushnumber(0);
				go2Mantic(orderinfo);
			}
		}
	}

	/**
	 * 抢派约单一次循环
	 * @param rule
	 * @param orderinfo
	 * @param drivers
	 * @param alreadysend
	 * @param sow
	 */
	private void onceSend4Reserve_SOW(OpCarGrabSendRuleImp rule, AbstractOrder orderinfo, List<PubDriver> drivers,int alreadysend,boolean sow) {
		//计算抢单结束时限
		Date grabEndTime = StringUtil.addDate(orderinfo.getUndertime(), rule.getSystemsendinterval() * 60);
		saveDriverMessage(orderinfo, drivers,grabEndTime);
		
		//推送,然后等待接单处理
		OrderInfo order = abstractOrderToOrderInfo(orderinfo);
		List<String> driverphones = new ArrayList<String>();
		//查询出可用的司机司机超限后选择的方式
		dillWithDrivers(driverphones,drivers,rule,alreadysend,sow);
		//推送司机端
		if(driverphones!=null&&driverphones.size()>0){
			PushPayload pushpayload4android = PushObjFactory.createSilenceOrderObj4Android(order, driverphones);
			PushPayload pushpayload4ios = PushObjFactory.createSilenceOrderObj4IOS(order, driverphones);
			AppMessageUtil.send(pushpayload4ios, pushpayload4android, AppMessageUtil.APPTYPE_DRIVER);
		}
	}
	
	/**
	 * 处理选择合理的司机
	 * @param driverphones
	 * @param drivers
	 */
	private void dillWithDrivers(List<String> driverphones, List<PubDriver> drivers,OpCarGrabSendRuleImp rule,int alreadysend, boolean sow) {
		//查询出可用的司机司机超限后选择的方式
		if(sow){
			//强调度
			for(int i=0;i<drivers.size();i++){
				PubDriver driver = drivers.get(i);
				if(!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(rule.getPushnumlimit())||(driverphones.size()<rule.getPushnum()-alreadysend)){
					driverphones.add(driver.getPhone());
				}else{
					//超限了不用推送了
					return ;
				}
			}
		}else{
			//弱调度
			Map<String,PubDriver> fuwusx = new HashMap<>();
			Map<String,Double> fuwu = new HashMap<String,Double>();
			for(int i=0;i<drivers.size();i++){
				PubDriver driver = drivers.get(i);
				fuwusx.put(driver.getId(), driver);
				fuwu.put(driver.getId(), driver.getAvgrate());
			}
			Map<String,Double> newfuwu = sortFWDriver(fuwu);
			Iterator<String> keyit = newfuwu.keySet().iterator();
			while(keyit.hasNext()&&(!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(rule.getPushnumlimit())||(driverphones.size()<rule.getPushnum()-alreadysend))){
				String driverid = keyit.next();
				PubDriver driver = fuwusx.get(driverid);
				driverphones.add(driver.getPhone());
			}
		}
	}
	
	@Override
	protected void go2Mantic(AbstractOrder orderinfo) {
		sendInfoService.opOrder2Mantic(orderinfo);
		this.sendRedisMessage(orderinfo,OrderState.MANTICSEND.state);
	}


	/**
	 * 获取可以自主选的的接口
	 * @param param
	 * @param orderinfo
	 * @return
	 */
	private List<PubDriver> getVariableDrivers4Reverve(SendOrderDriverQueryParam param, AbstractOrder orderinfo) {

		List<PubDriver> canpushdrivers = new ArrayList<>();
		//强调度
		List<PubDriver> drivers = sendInfoService.listOpOrderDriver(param);
		//筛选
		if(drivers!=null&&drivers.size()>0){
			//订单的用车时间
			Date orderusetime = orderinfo.getUsetime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for(int i=0;i<drivers.size();i++){
				try {
					PubDriver driver = drivers.get(i);
					boolean canpush = true;
					//服务中订单或者即可单
					List<OpOrder> orders = sendInfoService.listInServiceOrUseNow4CarDriver(driver.getId());
					if(orders!=null&&orders.size()>0){
						for(int k=0;k<orders.size();k++){
							AbstractOrder order = orders.get(k);
							//预估时间
							int minute = order.getEstimatedtime();
							Date starttime = order.getStarttime();
							//这个订单的结束时间,加上1小时之后可以赶到目的地
							Date temptime = null;
							if(starttime!=null){
								//已经开始服务了
								//正在服务中订单
								//这个订单的结束时间,加上预估时间的2倍约等于调度时间
								temptime = new Date(starttime.getTime()+minute*60*1000*2);
							}else{
								//未是开始的一定是即可单
								temptime = new Date(order.getUsetime().getTime()+(minute+60)*60*1000);
							}
							if(temptime.after(orderusetime)){
								//不能推送了，不能再规定时间内到达
								canpush = false;
								break;
							}
						}
					}
					if(!canpush){
						continue;
					}
					//存在预约单判断
					List<OpOrder> reverceorderinfos = sendInfoService.listReverceOrders4CarDriver(driver.getId());
					if(reverceorderinfos!=null&&reverceorderinfos.size()>0){
						for(int m=0;m<reverceorderinfos.size();m++){
							AbstractOrder reverceorderinfo = reverceorderinfos.get(m);
							Date usetime = reverceorderinfo.getUsetime();
							String usetimestr = format.format(usetime);
							String currenttimestr = format.format(orderusetime);
							if(currenttimestr.equals(usetimestr)){
								//不能推送了，同日内有预约单
								canpush = false;
								break;
							}
						}
					}
					if(!canpush){
						continue;
					}
					if(canpush){
						canpushdrivers.add(driver);
					}
				}catch (Exception e){
					logger.error(e);
				}
			}
		}

		return canpushdrivers;
	}
	
	/**
	 * 强派订单到司机
	 * @param driver
	 * @param orderinfo
	 */
	private void forceOrder2Driver(PubDriver driver,AbstractOrder orderinfo){
		//更新订单
		sendInfoService.forceOrder4Driver(orderinfo);
		//发送消息到对应司机和乘客
		List<String> dirphone = new ArrayList<String>();
		dirphone.add(driver.getPhone());
		OrderInfo order = abstractOrderToOrderInfo(orderinfo);
		String lasttime = StringUtil.formatOrderStatus(orderinfo.getUsetime(), OrderState.WAITSTART.state);
		order.setLasttime(lasttime);
		//推送此司机
		PushPayload pushobj4ios = PushObjFactory.createTaskOrderObj4IOS(order, dirphone);
		PushPayload pushobj4android = PushObjFactory.createTaskOrderObj4Android(order, dirphone);
		//直接发送
		AppMessageUtil.send(pushobj4ios,pushobj4android,AppMessageUtil.APPTYPE_DRIVER);
		
		if(orderinfo instanceof OpTaxiOrder){
			//个人端出租车订单
			String passengerphone = orderinfo.getPassengerphone();
			String onaddress = orderinfo.getOnaddress();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String usertimestr = format.format(order.getUsetime());
			Map<String,Object> userinfo = messagePubInfoService.getOpUserInfo(orderinfo.getUserid());
			Map<String,Object> carinfo = messagePubInfoService.getCarInfo(orderinfo.getVehicleid());
			Map<String,Object> opinfo = messagePubInfoService.getOpInfo();
			if(userinfo!=null&&carinfo!=null&&opinfo!=null){
				logger.info("获取到相关信息");
				String driverstr = driver.getName()+"师傅"+driver.getPhone();
				String carstr = ""+carinfo.get("color")+carinfo.get("vehcbrandname")+carinfo.get("vehclinename")+carinfo.get("plateno");
				String servicephone = (String) opinfo.get("servcietel");
				String companyname = "运管端";
				String userphone = (String) userinfo.get("account");
				List<String> userphones = new ArrayList<String>();
				userphones.add(userphone);
				if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
					logger.info("乘车人和坐车人不同的消息发送");
					//乘车人不同
					String username = (String) userinfo.get("nickname");
					//模板：您好，{0}为您安排专车于{1}去{2}接您，司机为{3}，{4}。如需帮助可联系{5}(租赁公司简称)
					String passenggercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.passenger", username,usertimestr,onaddress,driverstr,carstr,servicephone,companyname);
					List<String> passenggerphones = new ArrayList<String>();
					passenggerphones.add(passengerphone);
					SMMessageUtil.send(passenggerphones, passenggercontent);
					//发送给下单人
					//模板：预订成功，您为{0}预订的{1}的专车，司机为{2}，{3}，如需帮助可联系{4}(租赁公司简称)
					String usercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.user", orderinfo.getPassengers(),usertimestr,driverstr,carstr,servicephone,companyname);
					SMMessageUtil.send(userphones, usercontent);
				}else{
					logger.info("乘车人和坐车人相同同的消息发送");
					//认为相同，只发送userphone
					//模板：{0}的用车预订成功，将由{1}为您服务，{2}，如需帮助可联系{3}(租赁公司简称)
					String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.userpassentger", usertimestr,driverstr,carstr,servicephone,companyname);
					SMMessageUtil.send(userphones, content);
				}
				//推送给乘客
				String hintcontent = "用车订单司机已接单，会准时来接您";
				if(orderinfo.isIsusenow()){
					hintcontent = "您的即刻"+hintcontent;
				}else{
					Date usetime = orderinfo.getUsetime();
					SimpleDateFormat formatday = new SimpleDateFormat("yyyy-MM-dd");
					String timestr = formatday.format(usetime);
					Date servertime = new Date();
					Calendar calender = Calendar.getInstance();
					calender.setTime(servertime);
					SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
					if(timestr.equals(formatday.format(calender.getTime()))){
						//今天
						hintcontent = "今天"+format2.format(usetime)+"的预约"+hintcontent;
					}else{
						calender.add(Calendar.DATE, 1);
						if(timestr.equals(formatday.format(calender.getTime()))){
							//明天
							hintcontent = "明天"+format2.format(usetime)+"的预约"+hintcontent;
						}else{
							calender.add(Calendar.DATE, 1);
							if(timestr.equals(formatday.format(calender.getTime()))){
								//后天
								hintcontent = "后天"+format2.format(usetime)+"的预约"+hintcontent;
							}else{
								//其他时间
								hintcontent = format.format(usetime)+"的预约"+hintcontent;
							}
						}
					}
				}
				List<String> tag_ands = new ArrayList<String>();
				tag_ands.add("1");
				PushPayload pushload4ios = PushObjFactory.creatHintOrderStatus4IOS(hintcontent,PushObjFactory.HINT_HAVETAKEORDER,orderinfo.getOrderno(),"0",userphones,tag_ands,orderinfo.getUsetype(),orderinfo.getOrdertype());
				PushPayload pushload4android = PushObjFactory.creatHintOrderStatus4Android(hintcontent,PushObjFactory.HINT_HAVETAKEORDER,orderinfo.getOrderno(),"0",userphones,tag_ands,orderinfo.getUsetype(),orderinfo.getOrdertype());
				AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_PASSENGER);
				//发消息给司机端
				String drivername = driver.getName();
				//String content = drivername+"师傅，您好！客服为您指派了一个"+usertimestr+"的新订单，请登录司机端查看已接订单或咨询"+servicephone+"("+companyname+")";
				String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.changedriver.new", drivername,usertimestr,servicephone,companyname);
				SMMessageUtil.send(dirphone, content);
			}
		}
	}
	

	/**
	 * 获取规则和下单信息内的合法司机
	 * @param orderinfo
	 * @return
	 */
	private List<PubDriver> getVariableDrivers(OpCarGrabSendRuleImp rule, AbstractOrder orderinfo){
		List<PubDriver> res = new ArrayList<PubDriver>();
		//派单时限（分）
		int sendinterval = rule.getSystemsendinterval();

		SendOrderDriverQueryParam param = this.getReserveQueryParam(rule,orderinfo);
		//中心点
		double lat = orderinfo.getOnaddrlat();
		double lng = orderinfo.getOnaddrlng();
		
		double initraduis = rule.getInitsendradius();
		double maxraduis = rule.getMaxsendradius();
		double increrate = rule.getIncreratio();
		//初始半径
		double changeraduis = initraduis;

		//如果初始半径小于1
		if(initraduis<=0){
			if(maxraduis<=0||increrate<=0){
				return res;
			}
			changeraduis = maxraduis;
		}
		
		//派单时限分钟之后的时间
		int sleeptime = 3;
		Date temptime = new Date(System.currentTimeMillis() + sendinterval * 60 * 1000-sleeptime*1000);
		List<PubDriver> sendDriver = null;
		//派单时限结束之后
		while(temptime.after(new Date())){
			try{
				sendDriver = new ArrayList<PubDriver>();
				double[] rangeinfo = BaiduUtil.getRange(lng, lat, (int)(changeraduis*1000));
				param.setMinLng(rangeinfo[0]);
				param.setMaxLng(rangeinfo[1]);
				param.setMinLat(rangeinfo[2]);
				param.setMaxLat(rangeinfo[3]);

				//获取需要获取的车型ID
				List<String> models = sendInfoService.listOpvehiclemodelId(param.getSelectedModelId(),param.getNextCount());
				param.setQueryModelIds(models);
				List<PubDriver> drivers = sendInfoService.listOpOrderDriver(param);
				if(drivers!=null){
					for(int i=0;i<drivers.size();i++){
						PubDriver driver = drivers.get(i);
						if(canPush2Driver(driver,orderinfo)){
							if(!sendDriver.contains(driver)){
								sendDriver.add(driver);
							}
						}
					}
					if(sendDriver.size()>0){
						sendOrder2Driver(rule,sendDriver,orderinfo);
					}
				}
				//如果当前轮训半径小于最大半径，则进行递增
				if(changeraduis<=maxraduis){
					changeraduis = changeraduis + (increrate/100)*initraduis;
				}else{
					//如果设置升级车型
					if(rule.isUpgrade()) {
						param.setNextCount(param.getNextCount() + 1);
						//初始化轮训半径
						changeraduis = initraduis;
					}
				}
				if(isOrderTakedOrCancel(orderinfo.getOrderno())){
					return res;
				}
				Thread.sleep(sleeptime*1000);
			}catch(Exception e){
				logger.error(e);
			}
		}
		res.addAll(sendDriver);
		return res;
	}
	

	
	/**
	 * 将抽象订单类转为简化订单类
	 * @return
	 */
	private OrderInfo abstractOrderToOrderInfo(AbstractOrder orderinfo){
		OrderInfo order = new OrderInfo();
		order.setOrderno(orderinfo.getOrderno());
		order.setOnaddress(orderinfo.getOnaddress());
		order.setOffaddress(orderinfo.getOffaddress());
		order.setOnlat(orderinfo.getOnaddrlat());
		order.setOnlng(orderinfo.getOnaddrlng());
		order.setOfflat(orderinfo.getOffaddrlat());
		order.setOfflng(orderinfo.getOffaddrlng());
		order.setEstimatedtime(orderinfo.getEstimatedtime());
		order.setEstimatedmileage(orderinfo.getEstimatedmileage());
		order.setEstimatedcost(orderinfo.getEstimatedcost());
		if(StringUtils.isNotBlank(orderinfo.getTripremark())){
			order.setRemark(orderinfo.getTripremark());
		}
		order.setUsetime(orderinfo.getUsetime());
		order.setType(orderinfo.getOrdertype());
		order.setUsetype(orderinfo.getUsetype());
		order.setIsusenow(orderinfo.isIsusenow());
		order.setLasttime("0");
		order.setOrderstyle("1");
		return order;
	}
	
	/**
	 * 获取可以强派的一个最优的司机
	 * @return
	 */
	private PubDriver getForceDriver(List<PubDriver> drivers,AbstractOrder order){
		if(drivers==null||drivers.size()<=0){
			return null;
		}
		for(int i=0;i<drivers.size();i++){
			PubDriver driver = drivers.get(i);
			if(canPush2Driver(driver,order)){
				return driver;
			}
		}
		return null;
	}
	
	/**
	 * 派单失败处理
	 * @param orderinfo

	private void grabSendOrderCancel(AbstractOrder orderinfo){
		//更新订单状态
		sendInfoService.forceOrderCancel(orderinfo);
		//推送信息
		List<String> userids = new ArrayList<String>();
		Map<String,Object> userinfo = messagePubInfoService.getOpUserInfo(orderinfo.getUserid());
		String phone = (String) userinfo.get("account");
		userids.add(phone);
		List<String> tag_ands = new ArrayList<String>();
		tag_ands.add("1");
		OrderInfo order = abstractOrderToOrderInfo(orderinfo);
		PushPayload pushload4android = PushObjFactory.createSendOrderFailObj4Android(order, userids, tag_ands);
		PushPayload pushload4ios = PushObjFactory.createSendOrderFailObj4IOS(order, userids, tag_ands);
		AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_PASSENGER);
	}
	 */


	/**
	 * 添加弹窗消息，仅限运管端出租车
	 * @param orderinfo
	 * @param state
	 */
	private void sendRedisMessage(AbstractOrder orderinfo,String state){


		OrderInfoDetail order = new OrderInfoDetail();
		//订单号
		order.setOrderno(orderinfo.getOrderno());
		//车型(中文)
		order.setCartype(orderinfo.getSelectedmodelname());
		//数据状态
		order.setStatus(state);
		//城市ID
		order.setCityid(orderinfo.getOncity());
		//租赁公司ID
		order.setCompanyid(orderinfo.getCompanyid());
		//订单属性 默认个人订单
		order.setOrderprop(1);
		//发送系统 默认运管端
		order.setPaymethod("0");
		//用车时间
		order.setUsetime(orderinfo.getUsetime());
		//乘车人姓名
		order.setPassengers(orderinfo.getPassengers());
		//乘车人电话
		order.setPassengerphone(orderinfo.getPassengerphone());

		PeUser peUser = sendInfoService.getPeUser(orderinfo.getUserid());
		if(peUser!=null) {
			order.setUsername(peUser.getNickname());
			order.setUserphone(peUser.getAccount());
		}

		//车辆类型 出租车
		order.setOrderstyle(VehicleEnum.VEHICLE_TYPE_TAXI.code);
		OrderRedisMessageFactory rmf = new OrderRedisMessageFactory(OrderRedisMessageFactory.RedisMessageType.MANTICORDER,PlatformType.OPERATING);
		rmf.setOrder(order);
		rmf.createMessage(orderApiService);
		rmf.sendMessage();
	}
	
	/**
	 * 保存司机抢单消息
	 * @param orderinfo
	 */
	@Override
	protected void saveDriverMessage(AbstractOrder orderinfo,List<PubDriver> drivers,Date grabEndTime){
		String remark = "",headimage = "";
		String fileserver = SystemConfig.getSystemProperty("fileserver");
		if(orderinfo instanceof OpTaxiOrder){
//		}else if (orderinfo instanceof OrgTaxiOrder) {
			int meterrange = ((OpTaxiOrder)orderinfo).getMeterrange();
			headimage = ((OpTaxiOrder)orderinfo).getPassengericonmax();
			headimage = (headimage == null || headimage.trim().isEmpty()) ? "" : fileserver + headimage;
			remark = (meterrange == 0 ? "" : "(" + meterrange + "公里打表来接)") + orderinfo.getTripremark();
		}else if (orderinfo instanceof OrgOrder) {
			headimage = "";
			remark = orderinfo.getTripremark();
		}else if (orderinfo instanceof OpOrder) {
			headimage = "";
			remark = orderinfo.getTripremark();
		}
		DriverMessage message = new DriverMessage();
		message.setCreatetime(new Date());
		message.setNewstype(DriverMessageEnum.NEWS_TYPE_TAKEORDER.code);
		JSONObject orderJson = new JSONObject();
		orderJson.put("orderno", orderinfo.getOrderno());
		orderJson.put("usetype", orderinfo.getUsetype());
		orderJson.put("ordertype", orderinfo.getOrdertype());
		orderJson.put("type",OrderEnum.getOrderType(orderinfo.getOrdertype()).msg);
		orderJson.put("onaddr", orderinfo.getOnaddress());
		orderJson.put("offaddr", orderinfo.getOffaddress());
		orderJson.put("usetime", orderinfo.getUsetime());
		orderJson.put("usenow", orderinfo.isIsusenow() ? "即刻用车" : "预约用车");
		orderJson.put("lasttime", StringUtil.formatOrderStatus(orderinfo.getUsetime(), orderinfo.getOrderstatus()));//剩余时间
        orderJson.put("usetimeLong",orderinfo.getUsetime().getTime());
        orderJson.put("userid", orderinfo.getUserid());
		//下面的字段都是在任务消息里才会有
		orderJson.put("grabendtime", grabEndTime.getTime());
		orderJson.put("headimage", headimage);
		orderJson.put("remark", remark);
		JSONObject takecashinfo = new JSONObject();
		takecashinfo.put("remark", "");
		takecashinfo.put("bank", "");
		takecashinfo.put("amount", 0);
		takecashinfo.put("applytime", 0);
		message.setOrderinfo(orderJson);
		message.setTakecashinfo(takecashinfo);

		setDriverMessage(message,orderinfo,drivers,grabEndTime);
	}


	@Override
	protected String getOrderStatus(String orderNo) {
		return sendInfoService.getOpCarOrderStatus(orderNo);
	}

}
