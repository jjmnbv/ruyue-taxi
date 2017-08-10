package com.szyciov.carservice.util.sendservice.sendmethod.le;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.szyciov.carservice.service.DriverService;
import com.szyciov.carservice.service.MessagePubInfoService;
import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.carservice.service.SendInfoService;
import com.szyciov.carservice.util.OrderRedisMessageFactory;
import com.szyciov.carservice.util.OrderRedisMessageFactory.RedisMessageType;
import com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper;
import com.szyciov.carservice.util.sendservice.sendmethod.log.SendLogMessage;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.carservice.util.sendservice.sendrules.impl.le.LeCarForceSendRuleImp;
import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.driver.entity.DriverMessage;
import com.szyciov.driver.entity.OrderInfo;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.DriverMessageEnum;
import com.szyciov.driver.enums.DriverState;
import com.szyciov.driver.enums.OrderListEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.PlatformType;
import com.szyciov.entity.PubDriver;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.enums.SendRulesEnum;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.BaiduUtil;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PushObjFactory;
import com.szyciov.util.SMMessageUtil;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;

import cn.jpush.api.push.model.PushPayload;
import net.sf.json.JSONObject;

/**
 * 强派派单实现类
 * @ClassName LeCarForceSendMethodImp 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2017年6月1日 下午6:22:54
 */
public class LeCarForceSendMethodImp implements SendMethodHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(LeCarForceSendMethodImp.class);
	
	private SendInfoService sendInfoService;

	private MessagePubInfoService messagePubInfoService;
	private DriverService driverService;
	private OrderApiService orderApiService;

	public LeCarForceSendMethodImp(){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
		this.sendInfoService = (SendInfoService) wac.getBean("sendInfoService");
		this.messagePubInfoService = (MessagePubInfoService) wac.getBean("MessagePubInfoService");
		this.orderApiService = (OrderApiService)wac.getBean("OrderApiService");
		this.driverService = (DriverService)wac.getBean("driverService");
	}
	
	/**
	 * 派单方法
	 */
	@Override
	public void send(SendRuleHelper rules, AbstractOrder orderinfo) {
		if(rules==null || orderinfo==null){ return;}
		SendLogMessage.loginSendLog(logger,this.getClass(),orderinfo.getOrderno());
		LeCarForceSendRuleImp temprules = (LeCarForceSendRuleImp)rules;
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
	 * 抢单模式即可单派单
	 * @param temprules
	 * @param orderinfo
	 */
	private void send_UseNow(LeCarForceSendRuleImp rule, AbstractOrder orderinfo) {
		SendLogMessage.loginSend_UseNow(logger,this.getClass(),orderinfo.getOrderno());
		int sendinterval = rule.getSystemsendinterval();  //派单时限（分）
		int sleeptime = 3;  //每次轮询间隔
		int times = 0;         //轮询次数
		int upgradeTimes = 0;  //已升级次数
		Date sendEndTime = StringUtil.addDate(new Date(), sendinterval*60);      //派单结束时间点
		List<PubDriver> alreadySendDrivers = new ArrayList<>();                            //所有已查询过的司机(含未推送成功的司机)
		PubDriverInBoundParam param = new PubDriverInBoundParam();
		param.setCompanyid(orderinfo.getCompanyid());
		param.setCartype(orderinfo.getSelectedmodel());
		List<LeVehiclemodels> vehiclemodels = driverService.getLeDriverLevels(param);  //当前租赁公司的车型级别列表
		PubDriver driver = doUsenowPush(orderinfo, rule, alreadySendDrivers, vehiclemodels, sendEndTime, upgradeTimes, sleeptime, times);
		//订单取消或者被接走	
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return;
		//如果超时也没有找到司机
		}else if(driver==null){
			doNoMoreDriver(rule,orderinfo);
			return;
		}
		//如果找到了司机
		orderinfo.setPushnumber(1);
		forceOrder2Driver(driver,orderinfo);
	}
	
	/**
	 * 抢单模式预约单派单
	 * @param temprules
	 * @param orderinfo
	 */
	private void send_Reserve(LeCarForceSendRuleImp rule, AbstractOrder orderinfo) {
		SendLogMessage.loginSend_Reserve(logger,this.getClass(),orderinfo.getOrderno());
		Date usetime = orderinfo.getUsetime();
		//约车时限
		int carsinterval = rule.getCarsinterval();
		//约车时限的2倍
		Date temptimeobj = StringUtil.addDate(new Date(), 2*carsinterval*60);
		boolean sow = false;
		if(usetime.after(temptimeobj)){ //用车时间比较远，弱调度
			sow = false;
		}else{ //用车时间比较近，强调度
			sow = true;
		}
		SendLogMessage.send_Reserve_SOW(logger,this.getClass(),orderinfo.getOrderno(),sow);
		send_Reserve_SOW(rule, orderinfo, sow);
	}
	
	/**
	 * 预约派单强调度或者弱调度
	 * @param rule
	 * @param orderinfo
	 * @param sow false->弱调度,true->强调度 
	 */
	private void send_Reserve_SOW(LeCarForceSendRuleImp rule, AbstractOrder orderinfo,boolean sow){
		boolean pushNumLimit = SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(rule.getPushnumlimit()); //限制推送人数开关
		int pushnum = rule.getPushnum();  //限制推送总人数
		int sendinterval = rule.getSystemsendinterval();  //派单时限（分）
		int sleeptime = 3;                //每次轮询间隔
		int times = 0;                       //已轮询次数
		Date sendEndTime = StringUtil.addDate(new Date(), sendinterval*60-sleeptime);   //派单结束时间
		List<PubDriver> alreadySendDrivers = new ArrayList<>();           //已查询过的司机(包含未推送成功的司机)
		List<PubDriver> allCanPushDrivers = new ArrayList<>();             //所有推送成功的司机
		PubDriverInBoundParam param = new PubDriverInBoundParam();
		param.setCompanyid(orderinfo.getCompanyid());
		param.setCartype(orderinfo.getSelectedmodel());
		List<LeVehiclemodels> vehiclemodels = driverService.getLeDriverLevels(param);  //当前租赁公司的车型级别列表
		StringBuffer sb = new StringBuffer();
		for(LeVehiclemodels model : vehiclemodels) sb.append(",'").append(model.getLevel()).append("'");  //预约类型,全车型同时推送
		String levelList = vehiclemodels.isEmpty() ? null : sb.toString().substring(1);
		//自主选单循环找司机
		doSilencePush(orderinfo,rule,allCanPushDrivers,alreadySendDrivers,sendEndTime,
				sow,pushNumLimit,pushnum,levelList,sleeptime,times);
		PubDriver driver = null;
		//订单取消或者被接走
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return;
		}else if((driver = getForceDriver(allCanPushDrivers,orderinfo,sow)) == null){
			doNoMoreDriver(rule, orderinfo);
			return;
		}
		//有找到司机就强派一个
		orderinfo.setPushnumber(allCanPushDrivers.size());
		forceOrder2Driver(driver,orderinfo);
	}
	
	/**
	 * 即刻订单推送逻辑
	 * @param orderinfo    订单信息
	 * @param rule             派单规则
	 * @param alreadySendDrivers  已查找司机列表
	 * @param vehiclemodels   车型列表
	 * @param sendEndTime    派单结束时间
	 * @param upgradeTimes   已升级次数
	 * @param sleeptime           每次轮询间隔
	 * @param times                  已轮询次数
	 */
	public PubDriver doUsenowPush(AbstractOrder orderinfo,LeCarForceSendRuleImp rule,
			List<PubDriver> alreadySendDrivers, List<LeVehiclemodels> vehiclemodels,
			Date sendEndTime,int upgradeTimes,int sleeptime,int times){
		boolean allowupgrade = SendRulesEnum.VEHICLEUPGRADE_UPGRADE.code.equals(rule.getVehicleupgrade());
		/**
		 * 结束条件
		 * 1.派单时间结束
		 * 2.订单被接走或取消
		 * 3.已经找到能强派的司机
		 */
		PubDriver driver = null;
		while(new Date().before(sendEndTime) && 
				driver == null && 
				!isOrderTakedOrCancel(orderinfo.getOrderno())){
			try {
				double[] rangeInfo = getRangeInfo(rule,orderinfo,times);
				StringBuffer sb = new StringBuffer();
				for(int i =0;i <= upgradeTimes;i++) {
					sb.append(",'").append(vehiclemodels.get(i).getLevel()).append("'");
				}//即刻类型,逐级轮询
				String levelList = sb.toString().substring(1);
				List<PubDriver> drivers = getDriversInBound(rule, orderinfo, alreadySendDrivers, true, rangeInfo , levelList);
				for(PubDriver d : drivers){
					alreadySendDrivers.add(d);
				}
				driver = getForceDriver(drivers,orderinfo,true);
				//记录日志
				String orderno = orderinfo.getOrderno();
				String[] message = new String[]{orderno,drivers.size()+""};
				SendLogMessage.findDriver(logger,this.getClass(),message);
				//达到派单半径上限,并且允许升级车型时,从初始半径,累加升级车型,重新轮询
				if(rangeInfo.length == 5 && allowupgrade){
					times = 0;
					upgradeTimes = upgradeTimes >= vehiclemodels.size() - 1 ? vehiclemodels.size() - 1 :  upgradeTimes+1;
				}else{
					times++;
				}
				Thread.sleep(sleeptime*1000);
			} catch (InterruptedException e) {
				logger.error("强派模式-即刻派单出错",e);
			}
		}
		return driver;
	}
	
	/**
	 * 静默推送逻辑
	 * @param orderinfo
	 * @param rule
	 * @param allCanPushDrivers 所有推送成功的司机
	 * @param alreadySendDrivers 已查询过的司机(包含未推送成功的司机)
	 * @param sendEndTime 派单结束时间
	 * @param sow false->弱调度,true->强调度 
	 * @param pushNumLimit 限制推送人数开关
	 * @param pushnum 限制推送总人数
	 * @param levelList 当前租赁公司的车型级别列表
	 * @param sleeptime 每次轮询间隔
	 * @param times 已轮询次数
	 */
	private void doSilencePush(AbstractOrder orderinfo,LeCarForceSendRuleImp rule,
			List<PubDriver> allCanPushDrivers,List<PubDriver> alreadySendDrivers,
			Date sendEndTime,boolean sow,boolean pushNumLimit,
			int pushnum,String levelList,int sleeptime,int times
			){
		/**
		 * 结束条件
		 * 1.派单时间结束
		 * 2.订单被接走或取消
		 */
		while(new Date().before(sendEndTime)
				&& !isOrderTakedOrCancel(orderinfo.getOrderno())
				){
			try {
				double[] rangeInfo = getRangeInfo(rule,orderinfo,times);
				//当前范围内司机
				List<PubDriver> drivers = new ArrayList<>();
				drivers = getDriversInBound(rule, orderinfo, alreadySendDrivers, sow, rangeInfo, levelList);
				//对需要静默推送的司机进行排序
				optimalSortDriver(drivers, orderinfo,sow);
				//过滤本轮能推送的司机
				List<PubDriver> currentCanPushDrivers = new ArrayList<>();
				for(PubDriver d : drivers) {
					alreadySendDrivers.add(d);
					if((!pushNumLimit || allCanPushDrivers.size() < pushnum) 
						&& canPush2Driver(d, orderinfo,sow,false)){
						allCanPushDrivers.add(d);
						currentCanPushDrivers.add(d);
					}
				}
				//记录日志
				String orderno = orderinfo.getOrderno();
				String[] message = new String[]{orderno,drivers.size()+""};
				SendLogMessage.findDriver(logger,this.getClass(),message);
				String pushlimit = pushNumLimit ? pushnum+"" : "不限制";
				String asDrivers = alreadySendDrivers.size()+"";
				String pushDrivers = allCanPushDrivers.size()+"";
				message = new String[]{orderno,pushlimit,asDrivers,pushDrivers};
				SendLogMessage.onceSend4Reserve_SOW(logger,this.getClass(),message);
				silenceSendOrder(rule, orderinfo, currentCanPushDrivers);
				times++;
				Thread.sleep(sleeptime*1000);
			} catch (Exception e) {
				logger.error("强派模式-静默推送出错",e);
			}
		}
	}
	
	/**
	 * 强派约单一次循环
	 * @param rule
	 * @param orderinfo
	 * @param drivers
	 */
	private void silenceSendOrder(LeCarForceSendRuleImp rule, AbstractOrder orderinfo, List<PubDriver> drivers) {
		if(drivers == null || drivers.isEmpty()) return;
		//计算抢单结束时限
		Date grabEndTime = StringUtil.addDate(orderinfo.getUndertime(), rule.getSystemsendinterval() * 60);
		saveDriverMessage(orderinfo,drivers,grabEndTime);
		OrderInfo order = convert2OrderInfo(orderinfo);
		List<String> driverphones = new ArrayList<String>();
		for(PubDriver pd : drivers) driverphones.add(pd.getPhone());
		//推送司机端
		PushPayload pushpayload4android = PushObjFactory.createSilenceOrderObj4Android(order, driverphones);
		PushPayload pushpayload4ios = PushObjFactory.createSilenceOrderObj4IOS(order, driverphones);
		AppMessageUtil.send(pushpayload4ios, pushpayload4android, AppMessageUtil.APPTYPE_DRIVER);
	}
	
	
	/**
	 * 没有可用司机的处理逻辑
	 * @param rule
	 * @param orderinfo
	 */
	private void doNoMoreDriver(LeCarForceSendRuleImp rule, AbstractOrder orderinfo){
		String sendmodel = rule.getSendmodel();
		String[] m = new String[]{orderinfo.getOrderno()};
		if(SendRuleHelper.SEND_MODEL_SYSTEM.equalsIgnoreCase(sendmodel)){
			//派单失败，结束，推送消息
			SendLogMessage.sendEnd(logger,this.getClass(),m);
//			orderinfo.setOrderstatus(OrderState.CANCEL.state);
//			orderinfo.setCancelparty("0");
//			orderinfo.setPushnumber(0);
//			forceOrderCancel(orderinfo);
		}else if(SendRuleHelper.SEND_MODEL_MANTICANDSYSTEM.equalsIgnoreCase(sendmodel)){
			//进入人工派单
			SendLogMessage.toMantic(logger,this.getClass(),m);
			orderinfo.setOrderstatus(OrderState.MANTICSEND.state);
			orderinfo.setPushnumber(0);
			go2Mantic(orderinfo);
		}
	}
	
	/**
	 * 订单是否被接走
	 * @param orderno
	 * @return
	 */
	private boolean isOrderTakedOrCancel(String orderno){
		//查看订单有没有司机接单或者取消掉
		AbstractOrder orderinfo = sendInfoService.getOrgOrderByNo(orderno);
		String orderstatus = orderinfo.getOrderstatus();
		if(OrderState.CANCEL.state.equals(orderstatus)){
			logger.info("订单已取消");
			return true;
		//不属于系统派单或者人工派单状态的订单就表示被接走了
		}else if(!(OrderState.WAITTAKE.state.equals(orderstatus) || 
				OrderState.MANTICSEND.state.equals(orderstatus))){
			logger.info("订单已被接走");
			return true;
		}
		return false;
	}
	
	/**
	 * 订单进入人工派单
	 * @param orderinfo
	 */
	private void go2Mantic(AbstractOrder orderinfo){
		sendInfoService.go2Mantic(orderinfo);
		sendRedisMessage(orderinfo,OrderState.MANTICSEND.state);
	}


	/**
	 * 添加弹窗消息，仅限租赁端网约车
	 * @param orderinfo
	 * @param state
	 */
	private void sendRedisMessage(AbstractOrder orderinfo,String state){
		String[] m = new String[]{orderinfo.getOrderno()};
		SendLogMessage.toRedisMessage(logger,this.getClass(),m);
		OrderInfoDetail order = convert2OrderInfoDetail(orderinfo);
		order.setStatus(state);
		OrderRedisMessageFactory rmf = new OrderRedisMessageFactory(RedisMessageType.MANTICORDER, PlatformType.LEASE);
		rmf.setOrder(order);
		rmf.createMessage(orderApiService);
		rmf.sendMessage();
	}
	
	/**
	 * 转换AbstractOrder成OrderInfoDetail
	 * @param orderinfo
	 * @return
	 */
	private OrderInfoDetail convert2OrderInfoDetail(AbstractOrder orderinfo){
		OrgUser orgUser = sendInfoService.getOrgUser(orderinfo.getUserid());
		OrderInfoDetail order = new OrderInfoDetail();
		order.setOrderno(orderinfo.getOrderno());
		order.setType(orderinfo.getOrdertype());
		order.setCartype(orderinfo.getSelectedmodelname());
		order.setStatus(orderinfo.getOrderstatus());
		order.setCityid(orderinfo.getOncity());
		order.setCompanyid(orderinfo.getCompanyid());
		order.setOrderprop(0);
		order.setPaymethod(((OrgOrder)orderinfo).getPaymethod());
		order.setUsetime(orderinfo.getUsetime());
		order.setPassengers(orderinfo.getPassengers());
		order.setPassengerphone(orderinfo.getPassengerphone());
		order.setOrganid(((OrgOrder)orderinfo).getOrganid());
		//车辆类型 网约车
		order.setOrderstyle(OrderEnum.ORDERSTYPE_CAR.code);
		if(orgUser!=null) {
			order.setUsername(orgUser.getNickName());
			order.setUserphone(orgUser.getAccount());
		}
		return order;
	}
	
	/**
	 * 转换成OrderInfo
	 * @param orderinfo
	 * @return
	 */
	private OrderInfo convert2OrderInfo(AbstractOrder orderinfo){
		OrderInfo order = new OrderInfo();
		order.setOrderno(orderinfo.getOrderno());
		order.setOnaddress(orderinfo.getOnaddress());
		order.setOffaddress(orderinfo.getOffaddress());
		order.setOnlat(orderinfo.getOnaddrlat());
		order.setOnlng(orderinfo.getOnaddrlng());
		order.setOfflat(orderinfo.getOffaddrlat());
		order.setOfflng(orderinfo.getOffaddrlng());
		order.setGrabtime(0);   //强派模式没有抢单时间
		order.setEstimatedtime(orderinfo.getEstimatedtime());
		order.setEstimatedmileage(orderinfo.getEstimatedmileage());
		order.setEstimatedcost(orderinfo.getEstimatedcost());
		order.setUsetime(orderinfo.getUsetime());
		order.setType(orderinfo.getOrdertype());
		order.setUsetype(orderinfo.getUsetype());
		order.setIsusenow(orderinfo.isIsusenow());
		order.setOrderstyle(OrderEnum.ORDERSTYPE_CAR.code);
		order.setRemark(orderinfo.getTripremark());
		return order;
	}
	
	/**
	 * 强派订单到司机
	 * @param driver
	 * @param orderinfo
	 */
	private void forceOrder2Driver(PubDriver driver,AbstractOrder orderinfo){
		DriverInfo driverInfo = driverService.getDriverInfoById(driver.getId());
		orderinfo.setDriverid(driver.getId());
		orderinfo.setVehicleid(driver.getVehicleid());
		orderinfo.setVehcbrandname(driverInfo.getCarbrand());
		orderinfo.setVehclinename(driverInfo.getCarvehcline());
		orderinfo.setPlateno(driverInfo.getPlateno());
		orderinfo.setOrderstatus(OrderState.WAITSTART.state);
		orderinfo.setPaymentstatus(PayState.MENTED.state);
		orderinfo.setCompanyid(driver.getLeasescompanyid());
		orderinfo.setBelongleasecompany(driver.getBelongleasecompany());
		//更新订单
		sendInfoService.forceOrder4Driver(orderinfo);
		//发送消息到对应司机和乘客
		List<String> dirphone = new ArrayList<String>();
		dirphone.add(driver.getPhone());
		OrderInfo order = convert2OrderInfo(orderinfo);
		String lasttime = StringUtil.formatOrderStatus(orderinfo.getUsetime(), OrderState.WAITSTART.state);
		order.setLasttime(lasttime);
		pushNotify(order,dirphone,orderinfo,driver);
		
		//设置最后一次发送时间
		orderinfo.setLastsendtime(StringUtil.addDate(null, 20));
		JedisUtil.setString(RedisKeyEnum.DRIVER_TRAVEL_REMINDER.code+orderinfo.getOrderno() + "_" + orderinfo.getUsetype(),StringUtil.parseBeanToJSON(orderinfo));
	}
	
	/**
	 * 推送通知消息
	 * @param order
	 * @param dirphone
	 * @param orderinfo
	 * @param driver
	 */
	private void pushNotify(OrderInfo order,List<String> dirphone,AbstractOrder orderinfo,PubDriver driver){
		//推送此司机
		PushPayload pushobj4ios = PushObjFactory.createTaskOrderObj4IOS(order, dirphone);
		PushPayload pushobj4android = PushObjFactory.createTaskOrderObj4Android(order, dirphone);
		//直接发送
		AppMessageUtil.send(pushobj4ios,pushobj4android,AppMessageUtil.APPTYPE_DRIVER);
		//个人端网约车订单
		String passengerphone = orderinfo.getPassengerphone();
		String onaddress = orderinfo.getOnaddress();
		String usertimestr = StringUtil.formatDate(order.getUsetime(), StringUtil.TIME_WITH_MINUTE);
		OrgUser userinfo = messagePubInfoService.getOrgUserById(orderinfo.getUserid());
		DriverInfo driverinfo = driverService.getDriverInfoById(driver.getId());
		LeasesCompany opinfo = messagePubInfoService.getLeaseCompanyById(orderinfo.getCompanyid());
		if(userinfo!=null&&driverinfo!=null&&opinfo!=null){
			logger.info("获取到相关信息");
			String driverstr = "{drivername}师傅{driverphone}"
									.replace("{drivername}", driverinfo.getDrivername()).replace("{driverphone}", driverinfo.getDriverphone());
			String carstr = "{color}{carbrand}{carvehcline}{plateno}"
									.replace("{color}", driverinfo.getColor()).replace("{carbrand}", driverinfo.getCarbrand())
									.replace("{carvehcline}", driverinfo.getCarvehcline()).replace("{plateno}", driverinfo.getPlateno());
			String servicephone = opinfo.getServicesphone();
//			String companyname = opinfo.getShortname();
			String userphone = userinfo.getAccount();
			List<String> userphones = new ArrayList<String>();
			userphones.add(userphone);
			if(StringUtils.isNotBlank(passengerphone)&&!passengerphone.equalsIgnoreCase(userphone)){
				logger.info("乘车人和坐车人不同的消息发送");
				//乘车人不同
				String username = userinfo.getNickName();
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
			String hintcontent;
			if(orderinfo.isIsusenow()){
				hintcontent = "您的即刻用车订单司机已接单，会准时来接您";
			}else{
				hintcontent = "{datetime}的预约用车订单司机已接单，会准时来接您";
				hintcontent = hintcontent.replace("{datetime}", StringUtil.formatUsetimeInChinese(orderinfo.getUsetime()));
			}
			List<String> tag_ands = new ArrayList<String>();
			tag_ands.add("1");
			PushPayload pushload4ios = PushObjFactory.creatHintOrderStatus4IOS(hintcontent,PushObjFactory.HINT_HAVETAKEORDER,orderinfo.getOrderno(),"0",userphones,tag_ands,orderinfo.getUsetype(),orderinfo.getOrdertype());
			PushPayload pushload4android = PushObjFactory.creatHintOrderStatus4Android(hintcontent,PushObjFactory.HINT_HAVETAKEORDER,orderinfo.getOrderno(),"0",userphones,tag_ands,orderinfo.getUsetype(),orderinfo.getOrdertype());
			AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_PASSENGER);
			//发消息给司机端
			String drivername = driver.getName();
			//模板： {0}师傅，您好！客服为您指派了一个{1}的新订单，请登录司机端查看已接订单或咨询{2}(租赁公司简称)
			String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.message.ordermessage.changedriver.new", drivername,usertimestr,servicephone);
			String[] message = new String[]{orderinfo.getOrderno(),driver.getPhone()};
			SendLogMessage.canPush2Driver(logger,this.getClass(),message);
			SMMessageUtil.send(dirphone, content);
		}
	}
	
	/**
	 * 轮询获取范围内司机
	 * @param rule
	 * @param orderinfo
	 * @param alreadySendDrivers 已推送司机
	 * @param sow (强弱调度关联 true - 强 false - 弱)
	 * @param rangeInfo 查询半径
	 * @param levellist 车型级别列表
	 * @return
	 */
	private List<PubDriver> getDriversInBound(LeCarForceSendRuleImp rule ,AbstractOrder orderinfo,List<PubDriver> alreadySendDrivers,
			boolean sow,double[] rangeInfo,String levellist){
		List<PubDriver> list = new ArrayList<>();
		PubDriverInBoundParam pdbp = new PubDriverInBoundParam(rangeInfo);
		pdbp.setOrderno(orderinfo.getOrderno());
		//加上城市和租赁公司验证
//		pdbp.setOrganid(orderinfo.getOrganid());
		pdbp.setCompanyid(orderinfo.getCompanyid());
		pdbp.setCity(orderinfo.getOncity());
		pdbp.setAllowgrade(SendRulesEnum.VEHICLEUPGRADE_UPGRADE.code.equals(rule.getVehicleupgrade()));
		pdbp.setCartype(orderinfo.getSelectedmodel());
		pdbp.setUsenow(orderinfo.isIsusenow());
		pdbp.setVehicletype(Integer.valueOf(DriverEnum.DRIVER_TYPE_CAR.code));
		pdbp.setLevellist(levellist);
		//加上下单人，用于筛除司机本人
		pdbp.setUserid(orderinfo.getUserid());
		//强弱调度关联
		pdbp.setSow(sow);
		//过滤已推送司机
		StringBuffer sb = new StringBuffer();
		for(PubDriver d : alreadySendDrivers){
			sb.append(",'").append(d.getId()).append("'");
		}
		if(alreadySendDrivers != null && !alreadySendDrivers.isEmpty()) pdbp.setAlreadySendDrivers(sb.toString().substring(1));
		list = driverService.getLeDriversInBound(pdbp);
		return list;
	}
	
	/**
	 * 获取半径信息
	 * @param rule
	 * @param orderinfo
	 * @param times 目前递增次数
	 * @return
	 */
	private double[] getRangeInfo(LeCarForceSendRuleImp rule, AbstractOrder orderinfo,int times){
		//中心点
		double lat = orderinfo.getOnaddrlat();
		double lng = orderinfo.getOnaddrlng();
		//预约规则直接取最大半径
		if(SendRulesEnum.USETYPE_APPOINTMENT.code.equals(rule.getUsetype())){
			return BaiduUtil.getRange(lng, lat, rule.getMaxsendradius() * 1000);
		}
		//初始派单半径(公里)
		double initraduis = rule.getInitsendradius();
		//最大派单半径(公里)
		double maxraduis = rule.getMaxsendradius();
		//半径递增比(百分比,使用时要除以100)
		double increrate = rule.getIncreratio();
		//当前检索半径(初始半径+(初始半径*半径递增比*递增次数))
		int r = (int) (initraduis * 1000 +  initraduis * 1000 * increrate / 100 * times);
		//不能超过最大半径
		r = (int) (r > maxraduis * 1000 ? maxraduis * 1000 : r);
		double[] rangeinfo = BaiduUtil.getRange(lng, lat, r);
		if(r >= maxraduis * 1000) rangeinfo = new double[]{rangeinfo[0],rangeinfo[1],rangeinfo[2],rangeinfo[3],1.0};
		return rangeinfo;
	}
	
	/**
	 * 保存司机抢单消息
	 * @param orderinfo
	 */
	private void saveDriverMessage(AbstractOrder orderinfo,List<PubDriver> drivers,Date grabEndTime){
		String remark = "",headimage = "";
		String fileserver = SystemConfig.getSystemProperty("fileserver");
		OrgUser user = sendInfoService.getOrgUser(orderinfo.getUserid());
		headimage = fileserver + user.getHeadPortraitMax();
		remark = orderinfo.getTripremark();
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
		Date useday = StringUtil.getToday(orderinfo.getUsetime());
		String usedaystr = StringUtil.formatDate(useday, StringUtil.TIME_WITH_DAY);
		int pushDriver = 0;
		for(PubDriver pd : drivers){
			String key = "DriverGrabMessage_" + pd.getId() + "_" + pd.getPhone()+"_" + orderinfo.getOrderno() + "_" + usedaystr;
			//抢单结束时间比现在时间晚,才保存
			if(grabEndTime != null && grabEndTime.after(new Date())){
				JedisUtil.setString(key, (int)((grabEndTime.getTime() - System.currentTimeMillis())/1000), value);
				pushDriver++;
			}
		}
		String[] m = new String[]{orderinfo.getOrderno(),pushDriver+""};
		SendLogMessage.saveDriverMessage(logger,this.getClass(),m);
	}
	
	/************************************************************筛选司机方法*******************************************************************/

	/**
	 * 判断某个司机是否可以推送
	 * @param driver
	 * @param order
	 * @param sow (强弱调度关联 true - 强 false - 弱)
	 * @param isObvious 是否显性推送
	 * @return
	 */
	private boolean canPush2Driver(PubDriver driver,AbstractOrder order,boolean sow,boolean isObvious){
		//记录日志
		String orderno = order.getOrderno();
		String driverphone = driver.getPhone();
		String[] message = new String[]{orderno,driverphone};
		if(!checkDriverState(driver,order,sow,isObvious)) {
			SendLogMessage.canPush2Driver(logger,this.getClass(),message);
			return false;
		}
		if(!checkDriverOrderValid(driver,order)) {
			SendLogMessage.canPush2Driver(logger,this.getClass(),message);
			return false;
		}
		return true;
	}
	
	/**
	 * 检查司机状态是否可以推送
	 * @param driver
	 * @param order
	 * @param sow (强弱调度关联 true - 强 false - 弱)
	 * @param isObvious 是否显性推送
	 * @return
	 */
	private boolean checkDriverState(PubDriver driver,AbstractOrder order, boolean sow, boolean isObvious){
		String workstatus = driver.getWorkstatus();
		String shiftstatus = driver.getPassworkstatus();
		shiftstatus = (null == shiftstatus || shiftstatus.trim().isEmpty()) ? "0" : shiftstatus;
		if(order.isIsusenow() || isObvious){
			if(
				!(      //司机状态不是空闲
						DriverState.IDLE.code.equals(workstatus) 
				) 	||  //或者
				!(      //司机对班状态不是当班,无对班
						DriverState.ONSHIFT.code.equals(shiftstatus)
					||	DriverState.NOSHIFTDRIVER.code.equals(shiftstatus)
				)
				){
				return false;
			}
		}else{
			if(sow){//强调度关联
				if(
					!(      //司机状态不是空闲,服务中
							DriverState.IDLE.code.equals(workstatus) 
						|| DriverState.INSERVICE.code.equals(workstatus) 
					) 	||  //或者
					!(      //司机对班状态不是当班,无对班
							DriverState.ONSHIFT.code.equals(shiftstatus)
						||	DriverState.NOSHIFTDRIVER.code.equals(shiftstatus)
					)
					){
					return false;
				}
			}else{  //弱调度关联
				if(
					!(      //司机状态不是空闲,服务中,离线状态
							DriverState.IDLE.code.equals(workstatus) 
						|| DriverState.INSERVICE.code.equals(workstatus) 
						|| DriverState.OFFLINE.code.equals(workstatus)
					) 	||  //或者
					!(      //司机对班状态不是当班,无对班
							DriverState.ONSHIFT.code.equals(shiftstatus)
						||	DriverState.NOSHIFTDRIVER.code.equals(shiftstatus)
					)
					){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 检查司机所拥有订单是否符合接单条件
	 * @param driver
	 * @param order
	 * @return
	 */
	private boolean checkDriverOrderValid(PubDriver driver,AbstractOrder order){
		//检查司机的未出行订单和是否有服务中订单
		OrderListParam olp = new OrderListParam();
		olp.setType(OrderListEnum.CURRENT.state);
		olp.setDriverid(driver.getId());
		List<OrderInfoDetail> currentOrder = orderApiService.getOrderInfoList(olp);
		if(order.isIsusenow()){   //当前是即刻单
			for(OrderInfoDetail o : currentOrder){
				//当前订单预估时长(秒)+1小时
				int estimatedSecond = order.getEstimatedtime()*60+3600;
				//预估结束时间
				Date noTakeStart = StringUtil.addDate(order.getUsetime(), estimatedSecond);
				//存在未开始的即刻单或正在服务的订单
				if(o.isIsusenow() || o.getStarttime() != null){
					return false;
				//当前订单的预估结束时间(用车时间+预估时长+1小时)不在已存在的预约单用车时间之前是不可以接的
				}else if(!noTakeStart.before(o.getUsetime())){
					return false;
				}
			}
		}else{   //当前是预约单
			for(OrderInfoDetail o : currentOrder){
				//已存在订单预估时长(秒)+1小时
				int estimatedSecond = (int)o.getEstimatedtime()*60+3600;
				//预估结束时间
				Date noTakeStart = StringUtil.addDate(o.getUsetime(), estimatedSecond);
				if(o.getStarttime() != null){  //存在正在服务的订单
					int minute = (int)o.getEstimatedtime();  //预估时间
					//调度时间(当前订单的用车时间,必须晚于服务中订单的预估时间的2倍)
	                Date temptime = StringUtil.addDate(o.getStarttime(), minute*60*2);
	                if(temptime.after(order.getUsetime())){
	                	return false;
	                }
	            //当前订单用车时间不在已存在的即刻单一个小时之后是不可以接的
				}else if(o.isIsusenow() && order.getUsetime().before(noTakeStart)){
					return false;
				//当前订单的用车时间与已存在的预约单是同一天,不可以接
				}else if (!o.isIsusenow() && StringUtil.getToday(order.getUsetime()).equals(StringUtil.getToday(o.getUsetime()))) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 获取可以强派的一个最优的司机
	 * @param drivers
	 * @param order
	 * @param sow
	 * @return
	 */
	private PubDriver getForceDriver(List<PubDriver> drivers,AbstractOrder order,boolean sow){
		optimalSortDriver(drivers, order,sow);
		for(PubDriver d : drivers){
			if(canPush2Driver(d, order,true,true)){
				return d;
			}
		}
		return null;
	}
	
	/**
	 * 将司机按车型级别分组,取组名
	 * @param drivers
	 * @return
	 */
	private List<Integer> getDriverLevels(List<PubDriver> drivers){
		if(drivers==null || drivers.isEmpty()) return new ArrayList<>();
		//司机车型级别
		Map<Integer,PubDriver> levelmap = new HashMap<>();
		for(PubDriver driver : drivers) levelmap.put(driver.getVehiclelevel(), driver);
		//按key升序排序
	    List<Integer> levellist = new ArrayList<Integer>(levelmap.keySet());  
		Collections.sort(levellist);
		return levellist;
	}
	
	/**
	 * 获取同级别司机
	 * @param level
	 * @param drivers
	 * @return
	 */
	private List<PubDriver> getSameLevelDriver(int level,List<PubDriver> drivers){
		if(drivers==null || drivers.isEmpty()) return new ArrayList<>();
		List<PubDriver> sameleveldriver = new ArrayList<>();
		for(PubDriver driver : drivers) {
			if(driver.getVehiclelevel() == level){
				sameleveldriver.add(driver);
			}
		}
		return sameleveldriver;
	}
	
	/**
	 * 按服务评级排序
	 * @param drivers
	 * @return
	 */
	private void sortByAvg(List<PubDriver> drivers,AbstractOrder orderinfo){
		if(drivers==null || drivers.isEmpty()) return;
		Collections.sort(drivers, new Comparator<PubDriver>() {
			public int compare(PubDriver o1, PubDriver o2) {
				//找最高评级司机(评级高的在前,为负数)
				int result = o1.getAvgrate() == o2.getAvgrate() ? 0 :
									o1.getAvgrate() < o2.getAvgrate() ? 1 :
									o1.getAvgrate() > o2.getAvgrate() ? -1 : 0;
				return result;
			};
		});
	}
	
	/**
	 * 按距离远近排序
	 * @param drivers
	 */
	private void sortByRange(List<PubDriver> drivers,AbstractOrder orderinfo){
		if(drivers==null || drivers.isEmpty()) return;
		double endlng = orderinfo.getOnaddrlng();
		double endlat = orderinfo.getOnaddrlat();
		Collections.sort(drivers, new Comparator<PubDriver>() {
			public int compare(PubDriver o1, PubDriver o2) {
				//找最近距离司机(距离近的在前)
				double distance1 = BaiduUtil.getLongDistance(o1.getLng(), o1.getLat(),endlng, endlat);
				double distance2 = BaiduUtil.getLongDistance(o2.getLng(), o2.getLat(),endlng, endlat);
				int result = distance1 == distance2 ? 0 :
									distance1 < distance2 ? -1 :
									distance1 > distance2 ? 1 : 0;
				return result;
			};
		});
	}
	
	/**
	 * 路径最短优化司机排序
	 * @param drivers
	 * @param orderinfo
	 * @param sow 强调度按距离远近,弱调度按服务评级
	 */
	private void optimalSortDriver(List<PubDriver> drivers,AbstractOrder orderinfo,boolean sow) {
		if(drivers==null||drivers.size()<=0||orderinfo==null) return;
		List<PubDriver> temp = new ArrayList<>(drivers);
		drivers.clear();
		//记录日志
		String[] message = new String[]{orderinfo.getOrderno()};
		if(sow) SendLogMessage.optimalSortDriver(logger,this.getClass(),message);
		else SendLogMessage.serverLevelSortDriver(logger,this.getClass(),message);
		//司机车型级别
		List<Integer> levellist = getDriverLevels(temp);
		for(int level : levellist){
			List<PubDriver> sameleveldriver = getSameLevelDriver(level, temp);  //相同级别司机
			if(sow){  //强调度
				sortByRange(sameleveldriver,orderinfo);
				drivers.addAll(sameleveldriver);
			}else{     //弱调度
				sortByAvg(sameleveldriver,orderinfo);
				drivers.addAll(sameleveldriver);
			}
		}
	}
}
