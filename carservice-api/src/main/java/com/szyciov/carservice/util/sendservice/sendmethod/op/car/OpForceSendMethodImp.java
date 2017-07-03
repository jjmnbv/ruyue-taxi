package com.szyciov.carservice.util.sendservice.sendmethod.op.car;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.jpush.api.push.model.PushPayload;
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
 * 强派方式派单类
 * @author zhu
 *
 */
@Service("opForceSendMethodImp")
public class OpForceSendMethodImp extends AbstractSendMethod  {

	private static final Logger logger = LoggerFactory.getLogger(OpForceSendMethodImp.class);

	@Autowired
	private SendInfoService sendInfoService;
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
	 * @param rule		强派派单规则
	 * @param orderinfo
	 */
	@Override
	protected void send_Reserve(SendRuleHelper rule, AbstractOrder orderinfo) {

		SendLogMessage.loginSend_Reserve(logger,this.getClass(),orderinfo.getOrderno());

		Date usetime = orderinfo.getUsetime();
		ForceSendRuleImp ruleImp = (ForceSendRuleImp)rule;
		//约车时限
		int carsinterval = ruleImp.getCarsinterval();
		//约车时限的2倍
		Date temptimeobj = new Date(System.currentTimeMillis()+2L*carsinterval*60*1000);
		if(usetime.after(temptimeobj)){
			//用车时间比较远，弱调度
			send_Reserve_SOW(ruleImp, orderinfo, false);
		}else{
			//用车时间比较近，强调度
			send_Reserve_SOW(ruleImp, orderinfo, true);
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

		//系统派单时限
		long syssendinterval_send = ruleImp.getSystemsendinterval();
		Date time_send = new Date();
		int sleeptime = 3;
		//系统派单结束时间=系统当前时间+系统派单时限+睡眠等待时间
		Date temptime_send = new Date(time_send.getTime()+syssendinterval_send*60*1000-sleeptime*1000);
		int tempcount = 0;
		List<PubDriver> alreadydrivers = new ArrayList<>();
		//初始化预约单查询司机条件
		SendOrderDriverQueryParam param = getReserveQueryParam(ruleImp,orderinfo);
		//如果升级车型
		if(ruleImp.isUpgrade()) {
			//升级车型,此处写死最大值
			param.setNextCount(99);
		}

		if(!sow){
			//弱调度需查询下线司机
			List<String> status = param.getWorkstatus();
			status.add(DriverEnum.WORK_STATUS_OFFLINE.code);
			param.setWorkstatus(status);
		}
		Map<String,PubDriver> sendPubDriver = new HashMap<String,PubDriver>();
		//自主选单循环找司机
		while(temptime_send.after(new Date())&&!isOrderTakedOrCancel(orderinfo.getOrderno())){
			try {
				//获取司机
				List<PubDriver> drivers = getVariableDrivers4Reverve(param,orderinfo);
				List<PubDriver> cansenddrivers = new ArrayList<>();

				if(drivers!=null && drivers.size()>0){
					for(int i=0;i<drivers.size();i++){
						PubDriver pubdriver = drivers.get(i);
						if(sendPubDriver.get(pubdriver.getId())==null){
							cansenddrivers.add(pubdriver);
							sendPubDriver.put(pubdriver.getId(),pubdriver);
						}
					}
				}
				//订单是否继续有效
				if(isOrderTakedOrCancel(orderinfo.getOrderno())){
					return ;
				}

				if(cansenddrivers!=null&&cansenddrivers.size()>0){
					onceSend4Reserve_SOW(ruleImp, orderinfo, cansenddrivers,tempcount,sow);
					alreadydrivers.addAll(cansenddrivers);
					tempcount += cansenddrivers.size();
				}
				Thread.sleep(sleeptime*1000);
			} catch (InterruptedException e) {
				logger.error("{} 调度派单异常！订单编号-【{}】",SendLogMessage.getOrderType(this.getClass()),orderinfo.getOrderno(),e);
			}
		}
		//订单取消或者被接走
		if(isOrderTakedOrCancel(orderinfo.getOrderno())){
			return ;
		}

		//将司机根据服务车型 进行分组
		Map<Integer,List<PubDriver>> vehicleLevelDrivers = groupByVehicleLevel(alreadydrivers);
		//获取强派司机
		PubDriver driver = getForceDriver(vehicleLevelDrivers,orderinfo,sow);

		if(driver!=null){
			//有找到司机就强派一个
			orderinfo.setDriverid(driver.getId());
			orderinfo.setVehicleid(driver.getVehicleid());
			orderinfo.setVehcbrandname(driver.getVehcbrandname());
			orderinfo.setVehclinename(driver.getVehclinename());
			orderinfo.setPlateno(driver.getPlateno());
			orderinfo.setOrderstatus(OrderState.WAITSTART.state);
			orderinfo.setPaymentstatus(PayState.NOTPAY.state);
			orderinfo.setPushnumber(1);
			//设置所属车企ID
			orderinfo.setBelongleasecompany(driver.getBelongleasecompany());
			orderinfo.setCompanyid(driver.getLeasescompanyid());
			forceOrder2Driver(driver,orderinfo);
			return ;
		}
		String sendmodel = ruleImp.getSendmodel();
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
		//下单车型
		param.setSelectedModelId(orderinfo.getSelectedmodel());
		//运管端订单
		param.setPlatformType(PlatformTypeByDb.OPERATING.code);
		//司机类型 网约车
		param.setVehicleType(DriverEnum.DRIVER_TYPE_CAR.code);
		//升级次数默认为1
		param.setNextCount(1);

		//工作状态
		List<String> statusList = new ArrayList<String>();
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
	/**
	 * 获取预约单司机
	 * @param orderinfo 订单信息
	 * @param param 	查询参数
	 * @return
	 */
	protected List<PubDriver> getVariableDrivers4Reverve(SendOrderDriverQueryParam param,AbstractOrder orderinfo) {


		List<PubDriver> canpushdrivers = new ArrayList<>();

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
		return  canpushdrivers;

	}

	/**
	 * 订单进入人工派单
	 * @param orderinfo
	 */
	@Override
	protected void go2Mantic(AbstractOrder orderinfo){
		sendInfoService.opOrder2Mantic(orderinfo);
		this.sendRedisMessage(orderinfo,OrderState.MANTICSEND.state);
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
		order.setOrderstyle(OrderEnum.ORDERSTYPE_CAR.code);

		if(StringUtils.isNotBlank(orderinfo.getTripremark())){
			if(StringUtils.isNotBlank(tempstr)){
				tempstr = tempstr+","+orderinfo.getTripremark();
			}else{
				tempstr = orderinfo.getTripremark();
			}
		}
		order.setRemark(tempstr);
		order.setLasttime("无");

		if(sow){
			SendLogMessage.optimalSortDriver(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
		}else{
			SendLogMessage.serverLevelSortDriver(logger,this.getClass(),new String[]{orderinfo.getOrderno()});
		}
		//将司机根据服务车型 进行分组
		Map<Integer,List<PubDriver>> vehicleLevelDrivers = groupByVehicleLevel(drivers);

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

			SendLogMessage.pushAppMessage(logger,this.getClass(),new String[]{orderinfo.getOrderno(),GsonUtil.toJson(driverphones)});
		}
	}

	/**
	 * 即可派单
	 * @param
	 * @param orderinfo
	 */
	@Override
	public void send_UseNow(SendRuleHelper rule, AbstractOrder orderinfo) {

		SendLogMessage.loginSend_UseNow(logger,this.getClass(),orderinfo.getOrderno());

		ForceSendRuleImp temprules = (ForceSendRuleImp)rule;
		List<PubDriver> drivers = getVariableDrivers(temprules,orderinfo);

		//将司机根据服务车型 进行分组
		Map<Integer,List<PubDriver>> vehicleLevelDrivers = groupByVehicleLevel(drivers);
		//逻辑与预约单强调度一致
		PubDriver driver = getForceDriver(vehicleLevelDrivers,orderinfo,true);

		if(driver==null){
			//订单取消或者被接走
			if(isOrderTakedOrCancel(orderinfo.getOrderno())){
				return ;
			}
			//没有可用的司机
			String sendmodel = temprules.getSendmodel();
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
		orderinfo.setPaymentstatus(PayState.NOTPAY.state);
		orderinfo.setPushnumber(1);
		//设置所属车企
		orderinfo.setBelongleasecompany(driver.getBelongleasecompany());
		orderinfo.setCompanyid(driver.getLeasescompanyid());
		forceOrder2Driver(driver,orderinfo);
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
	 * 强派订单到司机
	 * @param driver
	 * @param orderinfo
	 */
	private void forceOrder2Driver(PubDriver driver,AbstractOrder orderinfo){
		//更新订单
		sendInfoService.forceOpOrder4Driver(orderinfo);
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
		order.setOrderstyle(OrderEnum.ORDERSTYPE_CAR.code);
		String tempstr = "";

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
		
	}
	
	/**
	 * 获取规则和下单信息内的合法司机
	 * @param
	 * @param orderinfo
	 * @return
	 */
	private List<PubDriver> getVariableDrivers(ForceSendRuleImp rule, AbstractOrder orderinfo){
		List<PubDriver> res = new ArrayList<PubDriver>();
		//派单时限（分）
		long sendinterval = rule.getSystemsendinterval();

		SendOrderDriverQueryParam param =  this.getReserveQueryParam(rule,orderinfo);

		//中心点
		double lat = orderinfo.getOnaddrlat();
		double lng = orderinfo.getOnaddrlng();

		//初始派单半径
		double initraduis = rule.getInitsendradius();
		//最大派单半径
		double maxraduis = rule.getMaxsendradius();
		//半径递增比
		double increrate = rule.getIncreratio();

		//循环轮训半径
		double changeraduis = initraduis;

		//如果初始半径小于1
		if(initraduis<=0){
			if(maxraduis<=0||increrate<=0){
				return res;
			}
			changeraduis = maxraduis;
		}
		
		Date current = new Date();
		//派单时限分钟之后的时间
		int sleeptime = 3;

		//派单结束时间
		Date temptime = new Date(current.getTime() + sendinterval * 60 * 1000-sleeptime*1000);

		Map<String,PubDriver> tempdrivers = new HashMap<String,PubDriver>();
		//派单时限结束之后
		while(temptime.after(new Date())){
			try{
				double[] rangeinfo = BaiduUtil.getRange(lng, lat, (int)(changeraduis*1000));
				param.setMinLng(rangeinfo[0]);
				param.setMaxLng(rangeinfo[1]);
				param.setMinLat(rangeinfo[2]);
				param.setMaxLat(rangeinfo[3]);
				//获取需要获取的车型ID
				List<String> models = sendInfoService.listOpvehiclemodelId(param.getSelectedModelId(),param.getNextCount());
				param.setQueryModelIds(models);
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

                //进行半径递增
                changeraduis = changeraduis + (increrate/100)*initraduis;
				//如果递增后半径小于等于最大半径，则继续轮训
				if(changeraduis<=maxraduis){
					continue;
				}else{
					//如果设置升级车型
					if(rule.isUpgrade()) {
						param.setNextCount(param.getNextCount() + 1);
						//初始化轮训半径
						changeraduis = initraduis;
					}
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
		String remark = orderinfo.getTripremark(),
				headimage = "";

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
		if(grabEndTime!=null){
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

	/**
	 * 获取存在服务的预约订单的时间
	 * @param driverId
	 * @return
	 */
	@Override
	protected  List<String> listDriverUnServiceTimes(String driverId) {
		return sendInfoService.listCarDriverUnServiceTimes(driverId);
	}

	@Override
	protected String getOrderStatus(String orderNo) {
		return sendInfoService.getOpCarOrderStatus(orderNo);
	}

	@Override
	protected AbstractOrder getLastReverceOrder(String driverId) {
		List<OpOrder> list =  sendInfoService.listReverceOrders4CarDriver(driverId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
