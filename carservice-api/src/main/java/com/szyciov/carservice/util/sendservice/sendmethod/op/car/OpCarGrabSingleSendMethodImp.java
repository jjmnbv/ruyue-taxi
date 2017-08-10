package com.szyciov.carservice.util.sendservice.sendmethod.op.car;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.api.push.model.PushPayload;
import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.carservice.service.SendInfoService;
import com.szyciov.carservice.util.OrderRedisMessageFactory;
import com.szyciov.carservice.util.sendservice.sendmethod.AbstractSendMethod;
import com.szyciov.carservice.util.sendservice.sendmethod.log.SendLogMessage;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.carservice.util.sendservice.sendrules.impl.AbstractSendRule;
import com.szyciov.carservice.util.sendservice.sendrules.impl.op.car.OpCarGrabSingleSendRuleImp;
import com.szyciov.driver.entity.DriverMessage;
import com.szyciov.driver.entity.OrderInfo;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.DriverMessageEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.PlatformType;
import com.szyciov.entity.PubDriver;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.SendRulesEnum;
import com.szyciov.enums.VehicleEnum;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.PeUser;
import com.szyciov.param.SendOrderDriverQueryParam;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.BaiduUtil;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PushObjFactory;
import com.szyciov.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 抢单派单规则
 * @author zhu
 *
 */
@Service("opCarGrabSingleSendMethodImp")
public class OpCarGrabSingleSendMethodImp extends AbstractSendMethod{

	private static final Logger logger = LoggerFactory.getLogger(OpCarGrabSingleSendMethodImp.class);
    @Autowired
	private SendInfoService sendInfoService;

    @Autowired
	private OrderApiService orderApiService;


	
	/**
	 * 抢单派单规则
	 */
	@Override
	public void send(SendRuleHelper rules, AbstractOrder order) {
		if(rules==null||order==null){
			logger.warn("{}信息不足，无法派单", SendLogMessage.getOrderType(this.getClass()));
			return ;
		}

		SendLogMessage.loginSendLog(logger,this.getClass(),order.getOrderno());

		OpCarGrabSingleSendRuleImp temprules = (OpCarGrabSingleSendRuleImp)rules;
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

		SendLogMessage.loginSend_Reserve(logger,this.getClass(),orderinfo.getOrderno());

		OpCarGrabSingleSendRuleImp temprules = (OpCarGrabSingleSendRuleImp)rule;
		Date usetime = orderinfo.getUsetime();
		//约车时限
		long carsinterval = temprules.getCarsinterval();
		//约车时限的2倍
		Date temptimeobj = new Date(System.currentTimeMillis()+2*carsinterval*60*1000);
		if(usetime.after(temptimeobj)){
			//用车时间比较远，弱调度
			send_Reserve_SOW(rule, orderinfo, false);
		}else{
			//用车时间比较近，强调度
			send_Reserve_SOW(rule, orderinfo, true);
		}
	}


	/**
	 * 抢单模式即可单派单
	 * @param rule
	 * @param orderinfo
	 */
	@Override
	protected void send_UseNow(SendRuleHelper rule, AbstractOrder orderinfo) {

		SendLogMessage.loginSend_UseNow(logger,this.getClass(),orderinfo.getOrderno());

		OpCarGrabSingleSendRuleImp temprules = (OpCarGrabSingleSendRuleImp)rule;
		//派单时限（分）
		long sendinterval = temprules.getSystemsendinterval();

		SendOrderDriverQueryParam param = getReserveQueryParam(temprules,orderinfo);

		//中心点
		double lat = orderinfo.getOnaddrlat();
		double lng = orderinfo.getOnaddrlng();
		double initraduis = temprules.getInitsendradius();
		double maxraduis = temprules.getMaxsendradius();
		double increrate = temprules.getIncreratio();
		//初始半径
		double changeraduis = initraduis;

		if(initraduis<=0){
			if(maxraduis<=0||increrate<=0){
				//派单失败，走人工或者派单失败
				lastOper4SendOrder(temprules, orderinfo);
			}
			changeraduis = maxraduis;
		}
		//派单时限分钟之后的时间
		int sleeptime = 3;
		int grabtime = temprules.getDriversendinterval();

		Date temptime = new Date(System.currentTimeMillis() + sendinterval * 60 * 1000-(grabtime+sleeptime)*1000);

		//派单时限结束或者订单被取消或者订单被接走
		String orderno = orderinfo.getOrderno();
		Map<String,PubDriver> tempdrivers = new HashMap<String,PubDriver>();
		int sendCount = 0;
		//派单未超时，且该订单未被抢走
		while(temptime.after(new Date())&&!isOrderTakedOrCancel(orderno)){
			try{
				double[] rangeinfo = BaiduUtil.getRange(lng, lat, (int)(changeraduis*1000));
				param.setMinLng(rangeinfo[0]);
				param.setMaxLng(rangeinfo[1]);
				param.setMinLat(rangeinfo[2]);
				param.setMaxLat(rangeinfo[3]);
				param.setCompanyid(orderinfo.getCompanyid());

				//获取需要获取的车型ID
				List<String> models = sendInfoService.listOpvehiclemodelId(param.getSelectedModelId(),param.getNextCount());
				param.setQueryModelIds(models);

				List<PubDriver> drivers = sendInfoService.listOpOrderDriver(param);

				SendLogMessage.findDriver(logger,this.getClass(),new String[]{orderno,drivers.size()+""});

				if(drivers!=null){
					List<PubDriver> currentdriver = new ArrayList<PubDriver>();
					for(int i=0;i<drivers.size();i++){
						PubDriver driver = drivers.get(i);
						if(!tempdrivers.containsKey(driver.getId())){
							if(canPush2Driver(driver,orderinfo)){
								tempdrivers.put(driver.getId(), driver);
								currentdriver.add(driver);
							}else{
								SendLogMessage.canPush2Driver(logger,this.getClass(),new String[]{orderno,driver.getPhone()});
							}
						}
					}
					//订单可以继续派单
					if(isOrderTakedOrCancel(orderno)){
						return ;
					}
					//如果找到司机
					if(currentdriver.size()>0){
						//发送抢单
						this.sendOrder2Driver(currentdriver,orderinfo,temprules,sendCount);
						sendCount = sendCount+currentdriver.size();
						//则等待抢单时限
						Thread.sleep(grabtime*1000);
					}else{
						Thread.sleep(sleeptime*1000);
					}
					//订单可以继续派单
					if(isOrderTakedOrCancel(orderno)){
						return ;
					}
				}
                //进行半径递增
                changeraduis = changeraduis + (increrate/100)*initraduis;
                //如果递增后半径小于等于最大半径，则继续轮训
				if(changeraduis<=maxraduis){
                    continue;
				}else{
					//如果设置升级车型
					if(temprules.isUpgrade()) {
						//升级一个车型
						param.setNextCount(param.getNextCount() + 1);
						//初始化轮训半径
						changeraduis = initraduis;
					}
				}
			}catch(Exception e){
				logger.error("{}派单异常！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderno,e);
			}
		}

		try{
			Thread.sleep(grabtime*1000);
		}catch(Exception e){
			logger.error("{}循环等待接单结束异常！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderno,e);
		}
		if(isOrderTakedOrCancel(orderno)){
			return ;
		}
		//判断是否进入人工操作处理
		lastOper4SendOrder(temprules, orderinfo);
	}

	/**
	 * 推送订单给司机
	 * @param currentdriver
	 * @param orderinfo
	 * @param temprules
	 */
	private void sendOrder2Driver(List<PubDriver> currentdriver, AbstractOrder orderinfo,
								  OpCarGrabSingleSendRuleImp temprules, int sendCount){
		//优化司机排序
		List<PubDriver> drivers = optimalSortDriver(currentdriver,orderinfo);

		SendLogMessage.optimalSortDriver(logger,this.getClass(),new String[]{orderinfo.getOrderno()});

		String pushnumlimit = temprules.getPushnumlimit();
		int pushmax = temprules.getPushnum();

		List<String> driverphones = new ArrayList<String>();

		for(int k = 0; k<drivers.size()&&(!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(pushnumlimit)||k<pushmax-sendCount); k++){
			PubDriver tempdriver = drivers.get(k);
			String phone = tempdriver.getPhone();
			if(StringUtils.isNotBlank(phone)){
				driverphones.add(phone);
			}
		}

		SendLogMessage.sendOrder2Driver(logger,this.getClass(),
			new String[]{
				orderinfo.getOrderno(),
				(!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(pushnumlimit))?-1+"":pushmax-sendCount+"",
				drivers.size()+"",
				driverphones.size()+""});

		// 过滤已经发送过即刻抢单的司机
		//CommonService.filterHadSendDriver(driverphones, temprules.getDriversendinterval());

		if(driverphones.size()>0){
			//推送订单
			OrderInfo order = abstractOrderToOrderInfo(orderinfo,temprules);
			order.setGrabtime(temprules.getDriversendinterval());
			PushPayload pushload4android = PushObjFactory.createGrabOrderObj4Android(order, driverphones);
			PushPayload pushload4ios = PushObjFactory.createGrabOrderObj4IOS(order, driverphones);
			AppMessageUtil.send(pushload4ios, pushload4android, AppMessageUtil.APPTYPE_DRIVER);

			SendLogMessage.pushAppMessage(logger,this.getClass(),new String[]{orderinfo.getOrderno(), GsonUtil.toJson(driverphones)});
		}

	}



	/**
	 * 派单结束，进入人工或者进入失败
	 */
	private void lastOper4SendOrder(OpCarGrabSingleSendRuleImp rule,AbstractOrder orderinfo){
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
	 * 将抽象订单类转为简化订单类
	 * @param
	 * @return
	 */
	public OrderInfo abstractOrderToOrderInfo(AbstractOrder orderinfo, AbstractSendRule rule){
		String tempstr = "";
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
		order.setAlwayshowdialog(SendRuleHelper.ALWAYSPUSH.equals(rule.getPushlimit()));
		order.setLasttime("0");
		order.setOrderstyle(OrderEnum.ORDERSTYPE_CAR.code);
		return order;
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

    @Override
    protected void saveDriverMessage(AbstractOrder orderinfo, List<PubDriver> drivers, Date grabEndTime) {
		String remark = "",headimage = "";
		headimage = "";
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
		if(grabEndTime!=null) {
			orderJson.put("grabendtime", grabEndTime.getTime());
		}else{
			orderJson.put("grabendtime", "");
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

		setDriverMessage(message,orderinfo,drivers,grabEndTime);
		String[] m = new String[]{orderinfo.getOrderno(),drivers.size()+""};
		SendLogMessage.saveDriverMessage(logger,this.getClass(),m);
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
		//订单类型
		order.setType(orderinfo.getOrdertype());
		//城市ID
		order.setCityid(orderinfo.getOncity());
		//租赁公司ID
		order.setCompanyid(orderinfo.getCompanyid());
		//订单属性 默认个人订单
		order.setOrderprop(1);
		//发送系统 默认运管端
		order.setPaymethod(PlatformTypeByDb.OPERATING.code);
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

		//车辆类型 网约车
		order.setOrderstyle(VehicleEnum.VEHICLE_TYPE_CAR.code);
		OrderRedisMessageFactory rmf = new OrderRedisMessageFactory(OrderRedisMessageFactory.RedisMessageType.MANTICORDER, PlatformType.OPERATING);
		rmf.setOrder(order);
		rmf.createMessage(orderApiService);
		rmf.sendMessage();
	}

	/**
	 * 预约派单强调度或者弱调度
	 * @param rule
	 * @param orderinfo
	 * @param sow false->弱调度,true->强调度 
	 */
	@Override
	protected void send_Reserve_SOW(SendRuleHelper rule, AbstractOrder orderinfo, boolean sow) {

		SendLogMessage.send_Reserve_SOW(logger,this.getClass(),orderinfo.getOrderno(),sow);

		OpCarGrabSingleSendRuleImp temprules = (OpCarGrabSingleSendRuleImp)rule;

		long syssendinterval_send = temprules.getSystemsendinterval();

		int sleeptime = 3;

		Date temptime_send = new Date(System.currentTimeMillis()+syssendinterval_send*60*1000-(sleeptime)*1000);

		int tempcount = 0;

		List<PubDriver> alreadydrivers = new ArrayList<>();

		//初始化预约单查询司机条件
		SendOrderDriverQueryParam param = getReserveQueryParam(temprules,orderinfo);
		//如果升级车型
		if(temprules.isUpgrade()) {
			//升级车型,此处写死最大值
			param.setNextCount(99);
		}

		if(!sow){
			//弱调度需查询下线司机
			List<String> status = param.getWorkstatus();
			status.add(DriverEnum.WORK_STATUS_OFFLINE.code);
			param.setWorkstatus(status);
		}
		Map<String,PubDriver> tempdrivers = new HashMap<String,PubDriver>();
		//自主选单循环找司机
		while(temptime_send.after(new Date())&&!isOrderTakedOrCancel(orderinfo.getOrderno())){
			try {
				List<PubDriver> drivers = getVariableDrivers4Reverve(param,orderinfo);

				List<PubDriver> cansenddrivers = new ArrayList<PubDriver>();

				if(drivers!=null){
					for(int i=0;i<drivers.size();i++){
						PubDriver pubdriver = drivers.get(i);
						if(!tempdrivers.containsKey(pubdriver.getId())){
							tempdrivers.put(pubdriver.getId(), pubdriver);
							cansenddrivers.add(pubdriver);
						}
					}
				}

				if(isOrderTakedOrCancel(orderinfo.getOrderno())){
					return ;
				}

				if(cansenddrivers!=null&&cansenddrivers.size()>0){
					//静默推送，显示在待抢单列表中
					onceSend4Reserve_SOW(temprules, orderinfo, cansenddrivers,tempcount,sow);
					alreadydrivers.addAll(cansenddrivers);
					tempcount += cansenddrivers.size();
				}
				Thread.sleep(sleeptime*1000);
			} catch (InterruptedException e) {
				logger.error("{} 调度派单异常！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderinfo.getOrderno(),e);
			}
		}
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return ;
		}
		//抢单模式  主动推送
		if(alreadydrivers.size()>0){
			try{
				//抢单推送一次
				grabSend2Drivers(temprules,orderinfo,alreadydrivers,sow);
			}catch(Exception e){
				logger.error("{} 主动推一次异常！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderinfo.getOrderno(),e);
			}
		}
		//判断是否有人工派单
		lastOper4SendOrder(temprules, orderinfo);
	}
	
	/**
	 * 抢单一次订单 （主动推送）
	 * @param rule
	 * @param orderinfo
	 * @param alreadydrivers
	 * @param sow
	 */
	private void grabSend2Drivers(OpCarGrabSingleSendRuleImp rule, AbstractOrder orderinfo, List<PubDriver> alreadydrivers,boolean sow) {
		removeUnvaliableDrivers(alreadydrivers, orderinfo);
		OrderInfo order = abstractOrderToOrderInfo(orderinfo,rule);
		order.setGrabtime(rule.getDriversendinterval());

		//将司机根据服务车型 进行分组
		Map<Integer,List<PubDriver>> vehicleLevelDrivers = groupByVehicleLevel(alreadydrivers);

		//查询出可用的司机司机超限后选择的方式
		//List<String> driverphones = dillWithDrivers(vehicleLevelDrivers,rule.getPushlimit(),rule.getPushnum(),orderinfo,0,sow);
		List<PubDriver> driverList = dillWithDrivers(vehicleLevelDrivers,rule.getPushnumlimit(),rule.getPushnum(),orderinfo,0,sow);


		//推送司机端
		if(driverList!=null&&driverList.size()>0){

			List<String> driverphones = this.listDriverPhone(driverList);
            // 过滤已经发送过即刻抢单的司机
            //CommonService.filterHadSendDriver(driverphones, rule.getDriversendinterval());
			PushPayload pushpayload4android = PushObjFactory.createGrabOrderObj4Android(order, driverphones);
			PushPayload pushpayload4ios = PushObjFactory.createGrabOrderObj4IOS(order, driverphones);
			AppMessageUtil.send(pushpayload4ios, pushpayload4android, AppMessageUtil.APPTYPE_DRIVER);
		}
		int grabtime = rule.getDriversendinterval();
		int sleeptime = 3;

		Date temptime = new Date(System.currentTimeMillis()+grabtime*1000-sleeptime*1000);

		//自主选单循环找司机
		while(temptime.after(new Date())){
			try{
				if(isOrderTakedOrCancel(orderinfo.getOrderno())){
					return ;
				}
				Thread.sleep(sleeptime*1000);
			}catch(Exception e){
				logger.error("{} 等待司机抢单失败！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderinfo.getOrderno(),e);
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
			if(!driver.getWorkstatus().equals(DriverEnum.WORK_STATUS_SERVICE.code)) {
				if (canPush2Driver(driver, orderinfo)) {
					valiabledrivers.add(driver);
				}
			}
		}
		alreadydrivers.clear();
		alreadydrivers.addAll(valiabledrivers);
	}
	
	/**
	 * 抢单约单一次循环 (静默推送)
	 * @param rule
	 * @param orderinfo
	 * @param drivers
	 * @param alreadysend
	 * @param sow
	 */
	private void onceSend4Reserve_SOW(OpCarGrabSingleSendRuleImp rule, AbstractOrder orderinfo, List<PubDriver> drivers,int alreadysend,boolean sow) {
		//计算抢单结束时限
		Date grabEndTime = StringUtil.addDate(orderinfo.getUndertime(), rule.getSystemsendinterval() * 60);

		//推送,然后等待接单处理
		OrderInfo order = abstractOrderToOrderInfo(orderinfo,rule);

		//将司机根据服务车型 进行分组
		Map<Integer,List<PubDriver>> vehicleLevelDrivers = groupByVehicleLevel(drivers);

		if(sow){
			SendLogMessage.optimalSortDriver(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
		}else{
			SendLogMessage.serverLevelSortDriver(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
		}

		List<PubDriver> driverList = dillWithDrivers(vehicleLevelDrivers,rule.getPushnumlimit(),rule.getPushnum(),orderinfo,0,sow);

		SendLogMessage.onceSend4Reserve_SOW(logger,this.getClass(),
			new String[]{
				orderinfo.getOrderno(),
				(!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(rule.getPushnumlimit()))?-1+"":rule.getPushnum()-alreadysend+"",
				drivers.size()+"",
				driverList.size()+""});

		//推送司机端
		if(driverList!=null&&driverList.size()>0){

			saveDriverMessage(orderinfo, driverList,grabEndTime);
			List<String> driverphones = this.listDriverPhone(driverList);

			PushPayload pushpayload4android = PushObjFactory.createSilenceOrderObj4Android(order, driverphones);
			PushPayload pushpayload4ios = PushObjFactory.createSilenceOrderObj4IOS(order, driverphones);
			AppMessageUtil.send(pushpayload4ios, pushpayload4android, AppMessageUtil.APPTYPE_DRIVER);

			SendLogMessage.pushAppMessage(logger,this.getClass(),new String[]{orderinfo.getOrderno(),GsonUtil.toJson(driverphones)});
		}
	}

	@Override
	protected String getOrderStatus(String orderNo) {
		return sendInfoService.getOpCarOrderStatus(orderNo);
	}

	/**
	 * 获取可以自主选的的接口
	 * @param param
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
	 * 返回预约单查询条件
	 * @param rule
	 * @param orderinfo
	 * @return
	 */
	private SendOrderDriverQueryParam getReserveQueryParam(OpCarGrabSingleSendRuleImp rule, AbstractOrder orderinfo){

		SendOrderDriverQueryParam param = new SendOrderDriverQueryParam();
		param.setCity(orderinfo.getOncity());
		param.setOffcity(orderinfo.getOffcity());
		param.setCompanyid(orderinfo.getCompanyid());
        param.setBelongleasecompany(orderinfo.getBelongleasecompany());
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
