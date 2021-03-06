package com.szyciov.carservice.util.sendservice.sendmethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.szyciov.carservice.service.MessagePubInfoService;
import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.DriverState;
import com.szyciov.driver.enums.OrderListEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.PubDriver;
import com.szyciov.enums.SendRulesEnum;
import com.szyciov.util.StringUtil;
import com.szyciov.util.latlon.LatLonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 派单流程抽象类
 * @author zhu
 *
 */
@Service
public abstract class AbstractSendMethod implements SendMethodHelper {

	private static final Logger logger = Logger.getLogger(AbstractSendMethod.class);

    @Autowired
	protected MessagePubInfoService messagePubInfoService;
    @Autowired
	private OrderApiService orderApiService;

//	public AbstractSendMethod(){
//		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//		this.messagePubInfoService = (MessagePubInfoService)wac.getBean("MessagePubInfoService");
//		this.orderApiService = (OrderApiService)wac.getBean("OrderApiService");
//	}



	/**
	 * 派单方法
	 * @param rules
	 * @param orderinfo
	 */
	@Override
	public abstract void send(SendRuleHelper rules, AbstractOrder orderinfo);

	/**
	 * 预约派单
	 * @param
	 * @param orderinfo
	 */
	protected abstract void send_Reserve(SendRuleHelper rule, AbstractOrder orderinfo);
	
	/**
	 * 预约派单强调度或者弱调度
	 * @param rule
	 * @param orderinfo
	 * @param sow false->弱调度,true->强调度 
	 */
	protected abstract void send_Reserve_SOW(SendRuleHelper rule, AbstractOrder orderinfo,boolean sow);


	/**
	 * 排序服务评级的司机
	 * @param oldhMap
	 * @return
	 */
    protected LinkedHashMap<String,Double> sortFWDriver(Map<String,Double> oldhMap) {
        //把map转成Set集合  
        Set<Entry<String,Double>> set = oldhMap.entrySet();
        ArrayList<Entry<String,Double>> arrayList = new ArrayList<>(set);
        Collections.sort(arrayList, new Comparator<Entry<String,Double>>() {
            @Override
            public int compare(Entry<String,Double> arg0,Entry<String,Double> arg1){
                //逆序 就用后面的参数 - 前面的参数  
                return (int)(arg1.getValue() - arg0.getValue());
            }
        });
        //创建一个map
        LinkedHashMap<String,Double> map = new LinkedHashMap<String,Double>();
        for (int i = 0; i < arrayList.size(); i++) {
            Entry<String,Double> entry = arrayList.get(i);
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

	/**
	 * 根据用车时间、预估时间判断是否可以下单
	 * 预约单：（已存在订单的用车时间、预估时间）、新订单的用车时间
	 * 即刻单：(当前即刻单的用车时间、预估时间)、存在预约单的用车时间
	 * @param oldUsetime		已存在订单的用车时间
	 * @param estimatedtime		已存在订单的预估时间
	 * @param newUsetime		新订单的用车时间
	 * @return
	 */
	protected boolean isPlaceOrderByDate (Date oldUsetime,int estimatedtime ,Date newUsetime){
		Date usenowusetime = oldUsetime;
		int estime = estimatedtime;
		//订单结束时间=用车时间+预估时间+1小时
		Date usenowendtime = new Date(usenowusetime.getTime()+estime*60*1000+60*60*1000);
		//当前下单的预约单的用车时间
		Date cuusetime =newUsetime;
		//如果存在的订单结束时间在新订单用车时间之后，则不可派单
		if(usenowendtime.after(cuusetime)){
			//不可以下单的
			return false;
		}else{
			return true;
		}
	}


	/**
	 * 即可派单
	 * @param
	 * @param orderinfo
	 */
	protected abstract void send_UseNow(SendRuleHelper rule, AbstractOrder orderinfo);

	/**
	 * 判断司机是否可以派单
	 * @param driver
	 * @param order
	 * @return
	 */
	protected  boolean canPush2Driver(PubDriver driver,AbstractOrder order){
		//司机必须是当班空闲
		Map<String, Object> pdStatus = messagePubInfoService.getDriverInfo(driver.getId());
		if(pdStatus==null){
			return false;
		}
		String workstatus = (String) pdStatus.get("workstatus");
		if(!(DriverState.IDLE.code.equals(workstatus))){
			return false;
		}
		//检查司机的未出行订单和是否有服务中订单
		OrderListParam olp = new OrderListParam();
		olp.setType(OrderListEnum.CURRENT.state);
		olp.setDriverid(driver.getId());
		List<OrderInfoDetail> currentOrder = orderApiService.getOrderInfoList(olp);

		return isPush(currentOrder,order);
	}

	/**
	 * 处理选择合理的司机
	 * @param drivers		根据服务车型级别排序后的司机
	 * @param pushNumLimit	推送人数限制
	 * @param pushNum		推送人数
	 * @param orderInfo		订单信息
	 * @param alreadysend	已经推送的信息条数
	 * @param sow			强弱调度 true:强 false:弱
	 */
	protected List<PubDriver> dillWithDrivers(Map<Integer,List<PubDriver>> drivers,
										 String pushNumLimit,
										   int pushNum,
										 AbstractOrder orderInfo,
										 int alreadysend, boolean sow) {
		List<PubDriver> driverList = new ArrayList<PubDriver>();
		//查询出可用的司机司机超限后选择的方式
		if(sow){
			//强调度
			for(Entry<Integer,List<PubDriver>> entry:drivers.entrySet()){
				//根据上车距离 升序排序
				List<PubDriver> sortDrivers = optimalSortDriver(entry.getValue(),orderInfo);
				for(PubDriver pubDriver:sortDrivers) {
					if (!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(pushNumLimit)
						|| (driverList.size()+alreadysend < pushNum )) {
						driverList.add(pubDriver);
					} else {
						//超限了不用推送了
						break;
					}
				}
			}
		}else{

			for(Entry<Integer,List<PubDriver>> entry:drivers.entrySet()) {
				//根据司机服务星级进行升序排序
				List<PubDriver> sortDrivers = serverLevelSortDriver(entry.getValue());
				//根据服务星级， 从后往前取，优先取服务星级高的
				for(int i = sortDrivers.size()-1;i>=0;i--){
					PubDriver pubDriver = sortDrivers.get(i);

					if (!SendRulesEnum.PUSHNUMLIMIT_ON.code.equals(pushNumLimit)
						|| (driverList.size()+alreadysend< pushNum)) {

						driverList.add(pubDriver);
					} else {
						//超限了不用推送了
						break;
					}
				}
			}
		}
		return driverList;
	}
	/**
	 * 根据时间判断是否发送
	 * @param currentOrder
	 * @param order
	 * @return
	 */
	protected boolean isPush(List<OrderInfoDetail> currentOrder,AbstractOrder order){
		if(order.isIsusenow()){   //当前是即刻单
			for(OrderInfoDetail o : currentOrder){
				//存在未开始的即刻单或正在服务的订单
				if(o.isIsusenow() || o.getStarttime() != null){
					return false;
					//当前订单用车时间不在已存在的预约单一个小时之后是不可以接的
				}else if(!o.isIsusenow() && order.getUsetime().before(StringUtil.addDate(o.getUsetime(), 3600))){
					return false;
				}
			}
		}else{   //当前是预约单
			for(OrderInfoDetail o : currentOrder){
				if(o.getStarttime() != null){  //存在正在服务的订单
					int minute = (int)o.getEstimatedtime();  //预估时间
					//调度时间(当前订单的用车时间,必须晚于服务中订单的预估时间的2倍)
					Date temptime = StringUtil.addDate(o.getStarttime(), minute*60*2);
					if(temptime.after(order.getUsetime())){
						return false;
					}
					//当前订单用车时间不在已存在的即刻单一个小时之后是不可以接的
				}else if(o.isIsusenow() && order.getUsetime().before(StringUtil.addDate(o.getUsetime(), 3600))){
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
         * 订单是否被接走
         * @param orderno
         * @return
         */
	protected boolean isOrderTakedOrCancel(String orderno){
		//查看订单有没有司机接单或者取消掉
		String orderstatus = getOrderStatus(orderno);
		if(!(OrderState.WAITTAKE.state.equals(orderstatus)||OrderState.MANTICSEND.state.equals(orderstatus))){
				return true;
		}
		return false;
	}



	/**
	 * 订单进入人工派单
	 * @param orderinfo
	 */
	protected abstract void go2Mantic(AbstractOrder orderinfo);

	/**
	 * 路径最短优化司机排序
	 * @param drivers
	 * @param orderinfo
	 */
	protected List<PubDriver> optimalSortDriver(List<PubDriver> drivers,AbstractOrder orderinfo) {
		if(drivers==null||drivers.size()<=0||orderinfo==null){
			return null;
		}

		double endlng = parseDouble(orderinfo.getOnaddrlng());
		double endlat = parseDouble(orderinfo.getOnaddrlat());

		Map<PubDriver, Double> driverNoSort = new HashMap<>();
		for(PubDriver driver : drivers){
            double startlat = driver.getLat();
            double startlng = driver.getLng();
            double distance = LatLonUtil.getDistance(endlng, endlat, startlng, startlat);
            driverNoSort.put(driver,distance);
        }

        List<PubDriver> tmpDrivers = this.sortDriver(driverNoSort);
		return tmpDrivers;
	}

	/**
	 * 根据车型级别对司机进行分组
	 * @param drivers
	 * @return
	 */
	protected Map<Integer,List<PubDriver>> groupByVehicleLevel(List<PubDriver> drivers){

		if(drivers==null||drivers.size()<=0){
			return null;
		}

		Map<Integer, List<PubDriver>> map = new LinkedHashMap<>(drivers.size());

		for(int i=0;i<drivers.size();i++){

			PubDriver driver = drivers.get(i);

			List<PubDriver> driverList = map.get(driver.getVehiclelevel());
			if(driverList==null){
				driverList = new ArrayList<>(drivers.size());
			}
			driverList.add(driver);
			map.put(driver.getVehiclelevel(),driverList);
		}
		return map;
	}


	/**
	 * 根据服务星级排序 顺序排序
	 * @param drivers
	 */
	protected List<PubDriver> serverLevelSortDriver(List<PubDriver> drivers) {
		if(drivers==null||drivers.size()<=0){
			return null;
		}

		Map<PubDriver, Double> map = new HashMap<>(drivers.size());
		for(int i=0;i<drivers.size();i++){
			PubDriver driver = drivers.get(i);
			map.put(driver,driver.getAvgrate());
		}

		return sortDriver(map);
	}

	/**
	 * 获取司机电话号码
	 * @param driverList
	 * @return
	 */
	protected List<String> listDriverPhone(List<PubDriver> driverList){
		List<String> driverPhone = new ArrayList<>();
		for(PubDriver driver:driverList){
			driverPhone.add(driver.getPhone());
		}
		return driverPhone;
	}


	/**
	 * 排序算法 顺序排序
	 * @param map m
	 */
	private List<PubDriver> sortDriver(Map<PubDriver, Double> map){
        List<PubDriver> drivers = new ArrayList<>();
        Set<Entry<PubDriver, Double>> set = map.entrySet();

        ArrayList<Entry<PubDriver, Double>> arrayList = new ArrayList<>(set);
        Collections.sort(arrayList, new Comparator<Entry<PubDriver, Double>>() {
            @Override
            public int compare(Entry<PubDriver, Double> arg0,Entry<PubDriver, Double> arg1){
                //逆序 就用后面的参数 - 前面的参数
                return arg0.getValue().compareTo(arg1.getValue());
            }
        });

        for(Entry<PubDriver, Double> entity : arrayList){
            drivers.add(entity.getKey());
        }

        return drivers;
        //
        //for(int i=1;i<distance.size()-1;i++){
        //
			//for(int j=0;j<distance.size()-i;j++){
			//	Double distance1 = distance.get(j);
			//	Double distance2 = distance.get(j+1);
        //
			//	PubDriver driver1 = drivers.get(j);
			//	PubDriver driver2 = drivers.get(j+1);
        //
			//	if(distance1>distance2){
        //
			//		distance.set(j,distance2);
			//		distance.set(j+1,distance1);
        //
			//		drivers.set(j,driver1);
			//		drivers.set(j+1,driver2);
			//	}
			//}
        //}
	}

	/**
	 * 转化obj成value，空值就为0
	 * @param value
	 * @return
	 */
	private double parseDouble(Object value){
		if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
			return 0;
		}
		return Double.parseDouble(String.valueOf(value));
	}
	
	/**
	 * 保存司机抢单消息
	 * @param orderinfo
	 */
	protected abstract void saveDriverMessage(AbstractOrder orderinfo,List<PubDriver> drivers,Date grabEndTime) ;


	/**
	 * 获取存在服务的预约订单的时间
	 * @param driverId
	 * @return
	 */
	protected abstract List<String> listDriverUnServiceTimes(String driverId);

	/**
	 * 获取当前订单状态
	 * @param orderNo
	 * @return
	 */
	protected  abstract String getOrderStatus(String orderNo);

	/**
	 * 获取最近一次要出行的订单
	 * @param driverId
	 * @return
	 */
	protected  abstract AbstractOrder getLastReverceOrder(String driverId);

}
