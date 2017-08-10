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
import com.szyciov.carservice.util.sendservice.sendrules.impl.op.taxi.ForceSendRuleImp;
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
import com.szyciov.enums.RedisKeyEnum;
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
 * 强派方式派单类
 * @author zhu
 *
 */
@Service
public class ForceSendMethodImp extends AbstractSendMethod{
	
	private static final Logger logger = LoggerFactory.getLogger(ForceSendMethodImp.class);
	@Autowired
	private SendInfoService sendInfoService;
	@Autowired
	private MessagePubInfoService messagePubInfoService;
	@Autowired
	private OrderApiService orderApiService;


	
	/**
	 * 派单方法
	 */
	@Override
	public void send(SendRuleHelper rules, AbstractOrder orderinfo) {
		if(rules==null||orderinfo==null){
			logger.warn("{}信息不足，无法派单", SendLogMessage.getOrderType(this.getClass()));
			return ;
		}

		SendLogMessage.loginSendLog(logger,this.getClass(),orderinfo.getOrderno());

		ForceSendRuleImp temprules = (ForceSendRuleImp)rules;
		String usetype = temprules.getUsetype();
		if(SendRuleHelper.USE_TYPE_USENOW.equalsIgnoreCase(usetype)){
			//即可用车派单
			send_UseNow(temprules,orderinfo);
		}else if(SendRuleHelper.USE_TYPE_RESERVE.equalsIgnoreCase(usetype)){
			//预约用车派单
			send_Reserve(temprules,orderinfo);
		}
	}

	/**
	 * 预约派单
	 * @param rule
	 * @param orderinfo
	 */
	@Override
	protected void send_Reserve(SendRuleHelper rule, AbstractOrder orderinfo) {

		SendLogMessage.loginSend_Reserve(logger,this.getClass(),orderinfo.getOrderno());

		Date usetime = orderinfo.getUsetime();
		//约车时限
		int carsinterval = ((ForceSendRuleImp)rule).getCarsinterval();
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

		ForceSendRuleImp ruleImp = (ForceSendRuleImp)rule;

		String pushnumlimit = ruleImp.getPushnumlimit();
		int pushnum = ruleImp.getPushnum();
		long syssendinterval_send = ruleImp.getSystemsendinterval();
		int sleeptime = 3;
		Date temptime_send = new Date(System.currentTimeMillis()+syssendinterval_send*60*1000-sleeptime*1000);
		int tempcount = 0;

		List<String> driverids = new ArrayList<String>();
		List<PubDriver> alreadydrivers = new ArrayList<>();

		//初始化预约单查询司机条件
		SendOrderDriverQueryParam param = getReserveQueryParam(ruleImp,orderinfo);
		if(!sow){
			//弱调度需查询下线司机
			List<String> status = param.getWorkstatus();
			status.add(DriverEnum.WORK_STATUS_OFFLINE.code);
			param.setWorkstatus(status);
		}
		//自主选单循环找司机
		while(temptime_send.after(new Date())){
			try {
				if(!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(pushnumlimit)||pushnum>=tempcount){
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
						onceSend4Reserve_SOW(((ForceSendRuleImp)rule), orderinfo, cansenddrivers,tempcount,sow);
						alreadydrivers.addAll(cansenddrivers);
						tempcount += cansenddrivers.size();
					}
				}
				if(isOrderTakedOrCancel(orderinfo.getOrderno())){
					return ;
				}
				Thread.sleep(sleeptime*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//订单取消或者被接走
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return ;
		}

        //将司机根据服务车型 进行分组
        Map<Integer,List<PubDriver>> vehicleLevelDrivers = new HashMap<>();
        //出租车默认车型为1
        vehicleLevelDrivers.put(1,alreadydrivers);

		PubDriver driver = getForceDriver(vehicleLevelDrivers,orderinfo,sow);

		if(driver!=null){
			//有找到司机就强派一个
			orderinfo.setDriverid(driver.getId());
			orderinfo.setVehicleid(driver.getVehicleid());
			orderinfo.setVehcbrandname(driver.getVehcbrandname());
			orderinfo.setVehclinename(driver.getVehclinename());
			orderinfo.setPlateno(driver.getPlateno());
			orderinfo.setOrderstatus(OrderState.WAITSTART.state);
			orderinfo.setPaymentstatus(PayState.ALLNOPAY.state);
			orderinfo.setPushnumber(1);
			orderinfo.setCompanyid(driver.getLeasescompanyid());
			orderinfo.setBelongleasecompany(driver.getBelongleasecompany());
			forceOrder2Driver(driver,orderinfo);
			return ;
		}
		String sendmodel = ((ForceSendRuleImp)rule).getSendmodel();
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
	 * 强派约单一次循环
	 * @param rule
	 * @param orderinfo
	 * @param drivers
	 * @param alreadysend
	 * @param sow
	 */
	private void onceSend4Reserve_SOW(ForceSendRuleImp rule, AbstractOrder orderinfo, List<PubDriver> drivers,int alreadysend,boolean sow) {
		//计算抢单结束时限
		Date grabEndTime = StringUtil.addDate(orderinfo.getUndertime(), rule.getSystemsendinterval() * 60);

		//推送,然后等待接单处理
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
		order.setGrabtime(rule.getSystemsendinterval());
		order.setOnaddress(orderinfo.getOnaddress());
		order.setOffaddress(orderinfo.getOffaddress());
		order.setOnlat(orderinfo.getOnaddrlat());
		order.setOnlng(orderinfo.getOnaddrlng());
		order.setOfflat(orderinfo.getOffaddrlat());
		order.setOfflng(orderinfo.getOffaddrlng());
		order.setEstimatedtime(orderinfo.getEstimatedtime());
		order.setEstimatedmileage(orderinfo.getEstimatedmileage());
		order.setEstimatedcost(orderinfo.getEstimatedcost());
		order.setUsetime(orderinfo.getUsetime());
		order.setType(orderinfo.getOrdertype());
		order.setUsetype(orderinfo.getUsetype());
		order.setIsusenow(orderinfo.isIsusenow());
		order.setOrderstyle("1");
		if(StringUtils.isNotBlank(orderinfo.getTripremark())){
			if(StringUtils.isNotBlank(tempstr)){
				tempstr = tempstr+","+orderinfo.getTripremark();
			}else{
				tempstr = orderinfo.getTripremark();
			}
		}
		order.setRemark(tempstr);
		order.setLasttime("无");

 		//将司机根据服务车型 进行分组
		Map<Integer,List<PubDriver>> vehicleLevelDrivers = new HashMap<>();
		//出租车默认车型为1
		vehicleLevelDrivers.put(1,drivers);

		List<PubDriver> driverList = dillWithDrivers(vehicleLevelDrivers,rule.getPushnumlimit(),rule.getPushnum(),orderinfo,alreadysend,sow);
		//推送司机端
		if(driverList!=null&&driverList.size()>0){

			saveDriverMessage(orderinfo,driverList,grabEndTime);

			List<String> driverphones = listDriverPhone(driverList);

			PushPayload pushpayload4android = PushObjFactory.createSilenceOrderObj4Android(order, driverphones);
			PushPayload pushpayload4ios = PushObjFactory.createSilenceOrderObj4IOS(order, driverphones);
			AppMessageUtil.send(pushpayload4ios, pushpayload4android, AppMessageUtil.APPTYPE_DRIVER);
		}
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
		//筛选
		if(drivers!=null&&drivers.size()>0){
			//订单的用车时间
			for(int i=0;i<drivers.size();i++){
				PubDriver driver = drivers.get(i);
				if(this.canPush2Driver(driver,orderinfo)){
					canpushdrivers.add(driver);
				}
			}
		}
		return canpushdrivers;
	}

	/**
	 * 即可派单
	 * @param rule
	 * @param orderinfo
	 */
	@Override
	protected void send_UseNow(SendRuleHelper rule, AbstractOrder orderinfo) {

		SendLogMessage.loginSend_UseNow(logger,this.getClass(),orderinfo.getOrderno());

		List<PubDriver> drivers = getVariableDrivers((ForceSendRuleImp)rule,orderinfo);

        //将司机根据服务车型 进行分组
        Map<Integer,List<PubDriver>> vehicleLevelDrivers = new HashMap<>();
        //出租车默认车型为1
        vehicleLevelDrivers.put(1,drivers);
		//获取强派司机 默认按距离排序
		PubDriver driver = getForceDriver(vehicleLevelDrivers,orderinfo,true);
		if(driver==null){
			//订单取消或者被接走
			if(isOrderTakedOrCancel(orderinfo.getOrderno())){
				return ;
			}
			//没有可用的司机
			String sendmodel = ((ForceSendRuleImp)rule).getSendmodel();
			if(SendRuleHelper.SEND_MODEL_SYSTEM.equalsIgnoreCase(sendmodel)){
				SendLogMessage.sendEnd(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
				//派单失败，结束，推送消息
			}else if(SendRuleHelper.SEND_MODEL_MANTICANDSYSTEM.equalsIgnoreCase(sendmodel)){
				SendLogMessage.toMantic(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
				orderinfo.setOrderstatus(OrderState.MANTICSEND.state);
				orderinfo.setPushnumber(0);
				go2Mantic(orderinfo);
			}
			return ;
		}
		//订单取消或者被接走
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return ;
		}
		orderinfo.setDriverid(driver.getId());
		orderinfo.setVehicleid(driver.getVehicleid());
		orderinfo.setVehcbrandname(driver.getVehcbrandname());
		orderinfo.setVehclinename(driver.getVehclinename());
		orderinfo.setPlateno(driver.getPlateno());
		orderinfo.setOrderstatus(OrderState.WAITSTART.state);
		orderinfo.setPaymentstatus(PayState.ALLNOPAY.state);
		orderinfo.setPushnumber(1);
		orderinfo.setCompanyid(driver.getLeasescompanyid());
		orderinfo.setBelongleasecompany(driver.getBelongleasecompany());
		forceOrder2Driver(driver,orderinfo);
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
		OrderRedisMessageFactory rmf = new OrderRedisMessageFactory(OrderRedisMessageFactory.RedisMessageType.MANTICORDER, PlatformType.OPERATING);
		rmf.setOrder(order);
		rmf.createMessage(orderApiService);
		rmf.sendMessage();
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
		order.setUsetime(orderinfo.getUsetime());
		order.setType(orderinfo.getOrdertype());
		order.setUsetype(orderinfo.getUsetype());
		order.setIsusenow(orderinfo.isIsusenow());
		order.setOrderstyle("1");
		String tempstr = "";
		if(orderinfo instanceof OpTaxiOrder){
			OpTaxiOrder taxiorder = (OpTaxiOrder) orderinfo;
			int meterrange = taxiorder.getMeterrange();
			if(meterrange>0){
				tempstr = meterrange+"公里打表来接";
			}
		}
		if(StringUtils.isNotBlank(orderinfo.getTripremark())){
			if(StringUtils.isNotBlank(tempstr)){
				tempstr = tempstr+","+orderinfo.getTripremark();
			}else{
				tempstr = orderinfo.getTripremark();
			}
		}
		order.setRemark(tempstr);
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


				//设置最后一次发送时间
				orderinfo.setLastsendtime(new Date(System.currentTimeMillis()+(10*1000)));
				JedisUtil.setString(RedisKeyEnum.DRIVER_TRAVEL_REMINDER.code+orderinfo.getOrderno()+"_"+orderinfo.getUsetype(),StringUtil.parseBeanToJSON(orderinfo));
			}
		}
	}
	
	/**
	 * 获取规则和下单信息内的合法司机
	 * @param rule
	 * @param orderinfo
	 * @return
	 */
	private List<PubDriver> getVariableDrivers(ForceSendRuleImp rule, AbstractOrder orderinfo){
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

				SendLogMessage.findDriver(logger,this.getClass(),new String[]{orderinfo.getOrderno(),drivers.size()+""});

				if(drivers!=null){
					for(int i=0;i<drivers.size();i++){
						PubDriver driver = drivers.get(i);
						if(canPush2Driver(driver,orderinfo)){
							if(!tempdrivers.containsKey(driver.getId())){
								tempdrivers.put(driver.getId(), driver);	
							}
						}else{
							SendLogMessage.canPush2Driver(logger,this.getClass(),new String[]{orderinfo.getOrderno(),driver.getPhone()});
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
				//订单取消或者被接走
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
	private SendOrderDriverQueryParam getReserveQueryParam(ForceSendRuleImp rule, AbstractOrder orderinfo){

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
