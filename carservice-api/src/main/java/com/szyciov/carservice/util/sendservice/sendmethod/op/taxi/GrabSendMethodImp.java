/**
 * 
 */
package com.szyciov.carservice.util.sendservice.sendmethod.op.taxi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.jpush.api.push.model.PushPayload;
import com.szyciov.carservice.service.MessagePubInfoService;
import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.carservice.service.SendInfoService;
import com.szyciov.carservice.util.OrderRedisMessageFactory;
import com.szyciov.carservice.util.sendservice.sendmethod.AbstractSendMethod;
import com.szyciov.carservice.util.sendservice.sendmethod.log.SendLogMessage;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.carservice.util.sendservice.sendrules.impl.AbstractSendRule;
import com.szyciov.carservice.util.sendservice.sendrules.impl.op.taxi.GrabSendRuleImp;
import com.szyciov.driver.entity.DriverMessage;
import com.szyciov.driver.entity.OrderInfo;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.DriverMessageEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.param.OrderListParam;
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
import com.szyciov.util.GsonUtil;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PushObjFactory;
import com.szyciov.util.SMMessageUtil;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName GrabSendMethodImp 
 * @author Efy Shu
 * @Description 抢派模式派单逻辑
 * @date 2017年4月8日 上午10:48:55 
 */
@Service
public class GrabSendMethodImp extends AbstractSendMethod{
	
	private static final Logger logger = LoggerFactory.getLogger(GrabSendMethodImp.class);
	@Autowired
	private SendInfoService sendInfoService;
	@Autowired
	private MessagePubInfoService messagePubInfoService;
	@Autowired
	private OrderApiService orderApiService;

	/**
	 * 
	 * @see com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper#send(com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper, com.szyciov.entity.AbstractOrder)
	 */
	@Override
	public void send(SendRuleHelper rules, AbstractOrder order) {
		if(rules==null||order==null){
			logger.warn("{}信息不足，无法派单", SendLogMessage.getOrderType(this.getClass()));
			return ;
		}

		SendLogMessage.loginSendLog(logger,this.getClass(),order.getOrderno());

		GrabSendRuleImp temprules = (GrabSendRuleImp)rules;
		String usetype = temprules.getUsetype();
		if(SendRuleHelper.USE_TYPE_USENOW.equalsIgnoreCase(usetype)){
			//即可用车派单
			send_UseNow(temprules,order);
		}else if(SendRuleHelper.USE_TYPE_RESERVE.equalsIgnoreCase(usetype)){
			//预约用车派单
			send_Reserve(temprules,order);
		}
	}

	/**
	 * 即刻用车
	 */
	@Override
	public void send_UseNow(SendRuleHelper rule,AbstractOrder orderinfo){
		SendLogMessage.loginSend_UseNow(logger,this.getClass(),orderinfo.getOrderno());
		GrabSendRuleImp ruleImp = (GrabSendRuleImp)rule;

		List<PubDriver> drivers = getVariableDrivers(ruleImp,orderinfo);

		if(drivers==null||drivers.size()<=0){
			lastOper4SendOrder(ruleImp, orderinfo);
			return ;
		}

		//优化司机排序
		drivers = optimalSortDriver(drivers,orderinfo);

		SendLogMessage.optimalSortDriver(logger,this.getClass(),new String[]{orderinfo.getOrderno()});

		String pushnumlimit = ruleImp.getPushnumlimit();
		int pushmax = ruleImp.getPushnum();
		List<String> driverphones = new ArrayList<String>();
		for(int i = 0; i<drivers.size()&&(!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(pushnumlimit)||i<pushmax); i++){
			PubDriver driver = drivers.get(i);
			String phone = driver.getPhone();
			if(StringUtils.isNotBlank(phone)){
				driverphones.add(phone);
			}
		}
		// 过滤已经发送过即刻抢单的司机
        //CommonService.filterHadSendDriver(driverphones, ruleImp.getDriversendinterval());

		if(driverphones.size()>0){
			if(isOrderTakedOrCancel(orderinfo.getOrderno())){
				return ;
			}
			//推送订单
			OrderInfo order = abstractOrderToOrderInfo(orderinfo,ruleImp);
			order.setGrabtime(ruleImp.getDriversendinterval());
			PushPayload pushload4android = PushObjFactory.createGrabOrderObj4Android(order, driverphones);
			PushPayload pushload4ios = PushObjFactory.createGrabOrderObj4IOS(order, driverphones);
			AppMessageUtil.send(pushload4ios, pushload4android, AppMessageUtil.APPTYPE_DRIVER);
			Date currenttime = new Date();
			int grabtime = ruleImp.getDriversendinterval();
			int sleeptime = 3;
			Date temptime = new Date(currenttime.getTime()+grabtime*1000-sleeptime*1000);
			while(temptime.after(new Date())){
				try{
					if(isOrderTakedOrCancel(orderinfo.getOrderno())){
						return ;
					}
					Thread.sleep(sleeptime*1000);
				}catch(Exception e){
					logger.error("{}循环等待接单结束异常！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderinfo.getOrderno(),e);
				}
			}
		}
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return ;
		}


        //将司机根据服务车型 进行分组
        Map<Integer,List<PubDriver>> vehicleLevelDrivers = new HashMap<>();
        //出租车默认车型为1
        vehicleLevelDrivers.put(1,drivers);
		//没人接单，直接指派 默认按距离排序
		PubDriver driver = getForceDriver(vehicleLevelDrivers,orderinfo,true);
		if(driver!=null){
			orderinfo.setDriverid(driver.getId());
			orderinfo.setVehicleid(driver.getVehicleid());
			orderinfo.setVehcbrandname(driver.getVehcbrandname());
			orderinfo.setVehclinename(driver.getVehclinename());
			orderinfo.setPlateno(driver.getPlateno());
			orderinfo.setOrderstatus(OrderState.WAITSTART.state);
			orderinfo.setPaymentstatus(PayState.ALLNOPAY.state);
			orderinfo.setCompanyid(driver.getLeasescompanyid());
			orderinfo.setPushnumber(driverphones.size());
			orderinfo.setBelongleasecompany(driver.getBelongleasecompany());
			forceOrder2Driver(driver,orderinfo,ruleImp);
		}else{
			lastOper4SendOrder(ruleImp, orderinfo);
			return ;
		}
		
	}


	/**
	 * 派单结束，进入人工或者进入失败
	 */
	private void lastOper4SendOrder(GrabSendRuleImp rule, AbstractOrder orderinfo){
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return ;
		}
		String sendmodel = rule.getSendmodel();
		//判断是否有人工派单
		if(SendRuleHelper.SEND_MODEL_SYSTEM.equalsIgnoreCase(sendmodel)){
			//派单失败，结束，推送消息
			SendLogMessage.sendEnd(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
		}else if(SendRuleHelper.SEND_MODEL_MANTICANDSYSTEM.equalsIgnoreCase(sendmodel)){
			//进入人工派单
			SendLogMessage.toMantic(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
			orderinfo.setOrderstatus(OrderState.MANTICSEND.state);
			orderinfo.setPushnumber(0);
			go2Mantic(orderinfo);
		}
	}


	/**
	 * 预约用车
	 */
	@Override
	public void send_Reserve(SendRuleHelper rule,AbstractOrder orderinfo){

		SendLogMessage.loginSend_Reserve(logger,this.getClass(),orderinfo.getOrderno());

		Date usetime = orderinfo.getUsetime();
		//约车时限
		int carsinterval = ((GrabSendRuleImp)rule).getCarsinterval();
		//约车时限的2倍
		Date temptimeobj = new Date(System.currentTimeMillis()+2L*carsinterval*60*1000);
		if(usetime.after(temptimeobj)){
			//用车时间比较远，弱调度
			send_Reserve_SOW(rule, orderinfo, false);
		}else{
			//用车时间比较近，强调度
			send_Reserve_SOW(rule, orderinfo, true);
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

		SendLogMessage.send_Reserve_SOW(logger,this.getClass(),orderinfo.getOrderno(),sow);

		GrabSendRuleImp ruleImp = (GrabSendRuleImp)rule;
		String pushnumlimit = ruleImp.getPushnumlimit();
		int pushnum = ruleImp.getPushnum();
		long syssendinterval_send = ruleImp.getSystemsendinterval();

		int sleeptime = 3;
		Date temptime_send = new Date(System.currentTimeMillis()+syssendinterval_send*60*1000-(sleeptime)*1000);
		int tempcount = 0;

		//初始化预约单查询司机条件
		SendOrderDriverQueryParam param = getReserveQueryParam(ruleImp,orderinfo);
		if(!sow){
			//弱调度需查询下线司机
			List<String> status = param.getWorkstatus();
			status.add(DriverEnum.WORK_STATUS_OFFLINE.code);
			param.setWorkstatus(status);
		}
		List<String> driverids = new ArrayList<String>();
		List<PubDriver> alreadydrivers = new ArrayList<>(); 
		//自主选单循环找司机
		while(temptime_send.after(new Date())){
			try {
				if(!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(pushnumlimit)||pushnum>=tempcount){
					//继续找司机
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
						onceSend4Reserve_SOW(ruleImp, orderinfo, cansenddrivers,tempcount,sow);
						alreadydrivers.addAll(cansenddrivers);
						tempcount += cansenddrivers.size();
					}
				}
				if(isOrderTakedOrCancel(orderinfo.getOrderno())){
					return ;
				}
				Thread.sleep(sleeptime*1000);
			} catch (InterruptedException e) {
				logger.error("{} 调度派单异常！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderinfo.getOrderno(),e);
			}
		}
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return ;
		}
		//是否查询到了合法的司机，如果有主动推送抢派一次
		if(alreadydrivers.size()>0){
			try{
				//抢派推送一次
				grabSend2Drivers(ruleImp,orderinfo,alreadydrivers,sow);
				return ;
			}catch(Exception e){
				logger.error("{} 主动推一次异常！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderinfo.getOrderno(),e);
			}
		}

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
	private void grabSend2Drivers(GrabSendRuleImp rule, AbstractOrder orderinfo, List<PubDriver> alreadydrivers,boolean sow) {
		removeUnvaliableDrivers(alreadydrivers, orderinfo);
		OrderInfo order = abstractOrderToOrderInfo(orderinfo,rule);
		order.setGrabtime(rule.getDriversendinterval());
		//将司机根据服务车型 进行分组
		Map<Integer,List<PubDriver>> vehicleLevelDrivers = new HashMap<>();
		//出租车默认车型为1
		vehicleLevelDrivers.put(1,alreadydrivers);
		List<PubDriver> driverList = dillWithDrivers(vehicleLevelDrivers,rule.getPushnumlimit(),rule.getPushnum(),orderinfo,0,sow);
		List<String> driverphones = this.listDriverPhone(driverList);
		//推送司机端
		if(driverphones!=null&&driverphones.size()>0){
            // 过滤已经发送过即刻抢单的司机
            //CommonService.filterHadSendDriver(driverphones, rule.getDriversendinterval());
			PushPayload pushpayload4android = PushObjFactory.createGrabOrderObj4Android(order, driverphones);
			PushPayload pushpayload4ios = PushObjFactory.createGrabOrderObj4IOS(order, driverphones);
			AppMessageUtil.send(pushpayload4ios, pushpayload4android, AppMessageUtil.APPTYPE_DRIVER);
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
				logger.error("{} 等待司机抢单失败！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderinfo.getOrderno(),e);
			}
		}
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return ;
		}

		//将司机根据服务车型 进行分组
		PubDriver driver = getForceDriver(vehicleLevelDrivers,orderinfo,sow);
		//都没抢单，执行指定
		if(driver!=null){
			orderinfo.setDriverid(driver.getId());
			orderinfo.setVehicleid(driver.getVehicleid());
			orderinfo.setVehcbrandname(driver.getVehcbrandname());
			orderinfo.setVehclinename(driver.getVehclinename());
			orderinfo.setPlateno(driver.getPlateno());
			orderinfo.setOrderstatus(OrderState.WAITSTART.state);
			orderinfo.setPaymentstatus(PayState.ALLNOPAY.state);
			orderinfo.setCompanyid(driver.getLeasescompanyid());
			orderinfo.setPushnumber(driverphones.size());
			orderinfo.setBelongleasecompany(driver.getBelongleasecompany());
			forceOrder2Driver(driver, orderinfo,rule);
		}else{
			String sendmodel = rule.getSendmodel();
			//判断是否有人工派单
			if(SendRuleHelper.SEND_MODEL_SYSTEM.equalsIgnoreCase(sendmodel)){
				//派单失败，结束，推送消息
				SendLogMessage.sendEnd(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
			}else if(SendRuleHelper.SEND_MODEL_MANTICANDSYSTEM.equalsIgnoreCase(sendmodel)){
				//进入人工派单
				SendLogMessage.toMantic(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
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
	private void onceSend4Reserve_SOW(GrabSendRuleImp rule, AbstractOrder orderinfo, List<PubDriver> drivers,int alreadysend,boolean sow) {
		//计算抢单结束时限
		Date grabEndTime = StringUtil.addDate(orderinfo.getUndertime(), rule.getSystemsendinterval() * 60);

		//推送,然后等待接单处理
		OrderInfo order = abstractOrderToOrderInfo(orderinfo,rule);
		//将司机根据服务车型 进行分组
		Map<Integer,List<PubDriver>> vehicleLevelDrivers = new HashMap<>();
		//出租车默认车型为1
		vehicleLevelDrivers.put(1,drivers);

		if(sow){
			SendLogMessage.optimalSortDriver(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
		}else{
			SendLogMessage.serverLevelSortDriver(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
		}

		List<PubDriver> driverList = dillWithDrivers(vehicleLevelDrivers,rule.getPushnumlimit(),rule.getPushnum(),orderinfo,alreadysend,sow);

		SendLogMessage.onceSend4Reserve_SOW(logger,this.getClass(),
			new String[]{
				orderinfo.getOrderno(),
				(!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(rule.getPushnumlimit()))?-1+"":rule.getPushnum()-alreadysend+"",
				drivers.size()+"",
				driverList.size()+""});
		//推送司机端
		if(driverList!=null&&driverList.size()>0){

			saveDriverMessage(orderinfo,driverList,grabEndTime);

			List<String> driverphones = this.listDriverPhone(driverList);

			PushPayload pushpayload4android = PushObjFactory.createSilenceOrderObj4Android(order, driverphones);
			PushPayload pushpayload4ios = PushObjFactory.createSilenceOrderObj4IOS(order, driverphones);
			AppMessageUtil.send(pushpayload4ios, pushpayload4android, AppMessageUtil.APPTYPE_DRIVER);

			SendLogMessage.pushAppMessage(logger,this.getClass(),new String[]{orderinfo.getOrderno(),
				GsonUtil.toJson(driverphones)});

		}
	}


	/**
	 * 订单是否被接走
	 * @param orderno
	 * @return
	 */
	@Override
	protected boolean isOrderTakedOrCancel(String orderno){
		//查看订单有没有司机接单
		AbstractOrder orderinfo = sendInfoService.getOpTaxiOrderByNo(orderno);
		String orderstatus = orderinfo.getOrderstatus();
		if(!(OrderState.WAITTAKE.state.equals(orderstatus)||OrderState.MANTICSEND.state.equals(orderstatus))){
				return true;
		}
		return false;
	}
	
	/**
	 * 获取可以自主选的的接口
	 * @param orderinfo
	 * @return
	 */
	private List<PubDriver> getVariableDrivers4Reverve(SendOrderDriverQueryParam param,AbstractOrder orderinfo) {
		List<PubDriver> canpushdrivers = new ArrayList<>();
		//
		List<PubDriver> drivers = sendInfoService.listOpOrderDriver4Reverve(param);

		SendLogMessage.findDriver(logger,this.getClass(),new String[]{orderinfo.getOrderno(),drivers.size()+""});

		//筛选
		if(drivers!=null&&drivers.size()>0){
			//订单的用车时间
			for(int i=0;i<drivers.size();i++){
				PubDriver driver = drivers.get(i);
				if(this.canPush2Driver(driver,orderinfo)){
					canpushdrivers.add(driver);
				}else{
					SendLogMessage.canPush2Driver(logger,this.getClass(),new String[]{orderinfo.getOrderno(),driver.getPhone()});
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
	private void forceOrder2Driver(PubDriver driver,AbstractOrder orderinfo,AbstractSendRule rule){
		//更新订单
		sendInfoService.forceOrder4Driver(orderinfo);
		//发送消息到对应司机和乘客
		List<String> dirphone = new ArrayList<String>();
		dirphone.add(driver.getPhone());
		OrderInfo order = abstractOrderToOrderInfo(orderinfo,rule);
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
					String passenggercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.passenger", username,usertimestr,onaddress,driverstr,carstr,servicephone);
					List<String> passenggerphones = new ArrayList<String>();
					passenggerphones.add(passengerphone);
					SMMessageUtil.send(passenggerphones, passenggercontent);
					//发送给下单人
					//模板：预订成功，您为{0}预订的{1}的专车，司机为{2}，{3}，如需帮助可联系{4}(租赁公司简称)
					String usercontent = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.user", orderinfo.getPassengers(),usertimestr,driverstr,carstr,servicephone);
					SMMessageUtil.send(userphones, usercontent);
				}else{
					logger.info("乘车人和坐车人相同同的消息发送");
					//认为相同，只发送userphone
					//模板：{0}的用车预订成功，将由{1}为您服务，{2}，如需帮助可联系{3}(租赁公司简称)
					String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.takeorder.userpassentger", usertimestr,driverstr,carstr,servicephone);
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
				String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.changedriver.new", drivername,usertimestr,servicephone);
				SMMessageUtil.send(dirphone, content);
			}
		}
	}
	
	/**
	 * 获取规则和下单信息内的合法司机
	 * @param rule
	 * @param orderinfo
	 * @return
	 */
	private List<PubDriver> getVariableDrivers(GrabSendRuleImp rule, AbstractOrder orderinfo){
		List<PubDriver> res = new ArrayList<PubDriver>();
		//派单时限（分）
		long sendinterval = rule.getSystemsendinterval();

		SendOrderDriverQueryParam param = getReserveQueryParam(rule,orderinfo);
		//中心点
		double lat = orderinfo.getOnaddrlat();
		double lng = orderinfo.getOnaddrlng();
		
		double initraduis = rule.getInitsendradius();
		double maxraduis = rule.getMaxsendradius();
		double increrate = rule.getIncreratio();
		double increraduis = initraduis;
		//初始半径
		double changeraduis = initraduis;
		if(initraduis<=0){
			if(maxraduis<=0||increrate<=0){
				return res;
			}
			increraduis = maxraduis;
			changeraduis = changeraduis + (increrate/100)*increraduis;
		}
		
		//派单时限分钟之后的时间
		int sleeptime = 3;
		Date temptime = new Date(System.currentTimeMillis() + sendinterval * 60 * 1000-sleeptime*1000);
		Map<String,PubDriver> tempdrivers = new HashMap<String,PubDriver>();
		//派单时限结束之后
		while(temptime.after(new Date())){
			try{
				double[] rangeinfo = BaiduUtil.getRange(lng, lat, (int)(changeraduis*1000));
				param.setMinLng(rangeinfo[0]);
				param.setMaxLng(rangeinfo[1]);
				param.setMinLat(rangeinfo[2]);
				param.setMaxLat(rangeinfo[3]);

				List<PubDriver> drivers = sendInfoService.listOpOrderDriver(param);
				if(drivers!=null){
					for(int i=0;i<drivers.size();i++){
						PubDriver driver = drivers.get(i);
						if(!tempdrivers.containsKey(driver.getId())){
							if(canPush2Driver(driver,orderinfo)){
								tempdrivers.put(driver.getId(), driver);
							}
						}
					}
					if(tempdrivers.size()>0){
						break;
					}
				}
				double tempraduis = changeraduis + (increrate/100)*increraduis;
				if(tempraduis<=maxraduis){
					changeraduis = tempraduis;
				}
				if(isOrderTakedOrCancel(orderinfo.getOrderno())){
					return res;
				}
				Thread.sleep(sleeptime*1000);
			}catch(Exception e){
				logger.error("{}派单异常！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderinfo.getOrderno(),e);
			}
		}
		res.addAll(tempdrivers.values());
		return res;
	}

	/**
	 * 将抽象订单类转为简化订单类
	 * @param orderinfo
	 * @return
	 */
	public OrderInfo abstractOrderToOrderInfo(AbstractOrder orderinfo, AbstractSendRule rule){
		String tempstr = "";
		if(orderinfo instanceof OpTaxiOrder){
			OpTaxiOrder taxiorder = (OpTaxiOrder) orderinfo;
			int meterrange = taxiorder.getMeterrange();
			if(meterrange>0){
				tempstr = meterrange+"公里打表来接";
			}
		}
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
		order.setAlwayshowdialog(SendRuleHelper.ALWAYSPUSH.equals(rule.getPushlimit()));
		if(StringUtils.isNotBlank(orderinfo.getTripremark())){
			if(StringUtils.isNotBlank(tempstr)){
				tempstr = tempstr+","+orderinfo.getTripremark();
			}else{
				tempstr = orderinfo.getTripremark();
			}
		}
		order.setRemark(tempstr);
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
	private PubDriver getForceDriver(Map<Integer,List<PubDriver>> drivers,
									 AbstractOrder orderInfo,
									 boolean sow){
		if(drivers==null||drivers.size()<=0){
			return null;
		}
		if(sow){
			SendLogMessage.optimalSortDriver(logger,this.getClass(),new String[]{orderInfo.getOrderno()});
		}else{
			SendLogMessage.serverLevelSortDriver(logger,this.getClass(),new String[]{orderInfo.getOrderno()});
		}
		//查询出可用的司机司机超限后选择的方式
		if(sow){
			//强调度
			for(Entry<Integer,List<PubDriver>> entry:drivers.entrySet()){
				//根据上车距离 升序排序
				List<PubDriver> sortDrivers = optimalSortDriver(entry.getValue(),orderInfo);
				for(PubDriver pubDriver:sortDrivers) {
					if(canPush2Driver(pubDriver,orderInfo)){
						return pubDriver;
					}else{
						SendLogMessage.canPush2Driver(logger,this.getClass(),new String[]{orderInfo.getOrderno(),pubDriver.getPhone()});
					}
				}
			}
		}else{
			for(Entry<Integer,List<PubDriver>> entry:drivers.entrySet()) {
				//根据司机服务星级进行升序排序
				List<PubDriver> driverList = serverLevelSortDriver(entry.getValue());
				//根据服务星级， 从后往前取，优先取服务星级高的
				for(int i = driverList.size()-1;i>=0;i--){
					PubDriver pubDriver = driverList.get(i);
					if(canPush2Driver(pubDriver,orderInfo)){
						return pubDriver;
					}else{
						SendLogMessage.canPush2Driver(logger,this.getClass(),new String[]{orderInfo.getOrderno(),pubDriver.getPhone()});
					}
				}
			}
		}
		return null;
	}

	
	/**
	 * 订单进入人工派单
	 * @param orderinfo
	 */
	@Override
	protected void go2Mantic(AbstractOrder orderinfo){
		sendInfoService.go2Mantic(orderinfo);

		this.sendRedisMessage(orderinfo,OrderState.MANTICSEND.state);
	}

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
		if(grabEndTime!=null) {
			orderJson.put("grabendtime", grabEndTime.getTime());
		}
		orderJson.put("headimage", headimage);
		orderJson.put("remark", remark);
		JSONObject takecashinfo = new JSONObject();
		takecashinfo.put("remark", "");
		takecashinfo.put("bank", "");
		takecashinfo.put("amount", 0);
		takecashinfo.put("applytime", 0);
		message.setOrderinfo(orderJson);
		message.setTakecashinfo(takecashinfo);

		//订单信息转为字符串
		String value = JSONObject.fromObject(message).toString();
		for(PubDriver pd : drivers){
			String key = "DriverGrabMessage_" + pd.getId() + "_" + pd.getPhone()+"_" + orderinfo.getOrderno();
			//抢单结束时间比现在时间晚,才保存
			if(grabEndTime != null && grabEndTime.after(new Date())){
				JedisUtil.setString(key, (int)((grabEndTime.getTime() - System.currentTimeMillis())/1000), value);
			}
		}
	}



	@Override
	protected String getOrderStatus(String orderNo) {
		return sendInfoService.getOpTaxiOrderStatus(orderNo);
	}

	@Override
	protected List<OrderInfoDetail> listOrderInfo(OrderListParam olp) {
		return orderApiService.listTaxiOrderInfo(olp);
	}

	/**
	 * 返回预约单查询条件
	 * @param rule
	 * @param orderinfo
	 * @return
	 */
	private SendOrderDriverQueryParam getReserveQueryParam(GrabSendRuleImp rule, AbstractOrder orderinfo){

		SendOrderDriverQueryParam param = new SendOrderDriverQueryParam();
		param.setCity(orderinfo.getOncity());
		param.setOffcity(orderinfo.getOffcity());
		param.setCompanyid(orderinfo.getCompanyid());
		param.setBelongleasecompany(orderinfo.getBelongleasecompany());
		//添加下单人id，过滤司机与下单人为同一人
		param.setUserid(orderinfo.getUserid());
		//运管端订单
		param.setPlatformType(PlatformTypeByDb.OPERATING.code);
		//司机类型 出租车
		param.setVehicleType(DriverEnum.DRIVER_TYPE_TAXI.code);

		//工作状态
		List<String> statusList = new ArrayList<>();
		statusList.add(DriverEnum.WORK_STATUS_LEISURE.code);
		statusList.add(DriverEnum.WORK_STATUS_SERVICE.code);
		param.setWorkstatus(statusList);

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
}
