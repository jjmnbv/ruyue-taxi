package com.szyciov.carservice.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.OrderType;
import com.szyciov.dto.orderMessage.LabourMessageDto;
import com.szyciov.dto.orderMessage.SuccessMessageDto;
import com.szyciov.entity.PlatformType;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.message.redis.RedisMessage;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.StringUtil;
import com.szyciov.util.message.RedisListMessage;

/**
 * @ClassName RedisMessageFactory 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年11月25日 上午11:03:09 
 */
public class OrderRedisMessageFactory {
	private Logger logger = LoggerFactory.getLogger(OrderRedisMessageFactory.class);
	/**
	 * 超时时间
	 */
	private int outTime;
	/**
	 * 消息类型
	 */
	private RedisMessageType msgType;
	/**
	 * 推送平台
	 */
	private PlatformType platform;
	/**
	 * 消息实体
	 */
	private Object msgDto;
	/**
	 * 订单信息实体
	 */
	private OrderInfoDetail order;
	/**
	 * 司机对象
	 */
	private DriverInfo driver;
	
	private RedisKeyEnum redisKey;
	
	private OrderApiService service; 
	
	private RedisMessage redisMessage;
	/**
	 * 消息类型枚举类
	 * @ClassName RedisMessageType 
	 * @author Efy Shu
	 * @Description TODO(这里用一句话描述这个类的作用) 
	 * @date 2016年11月25日 下午1:41:54
	 */
	public enum RedisMessageType{
		MANTICORDER(0,"待人工派单"),
		TAKINGORDER(1,"抢单成功");
		public int code;
		public String title;
		private RedisMessageType(int code,String title) {
			this.code = code;
			this.title = title;
		}
	}
	
	/**
	 * 默认构造
	 */
	public OrderRedisMessageFactory() {
	
	}
	
	/**
	 * 有参构造
	 * @param msgType   消息类型
	 * @param platform    推送平台
	 * @see {@linkplain RedisMessageType}
	 * @see {@linkplain PlatformType}
	 */
	public OrderRedisMessageFactory(RedisMessageType msgType,PlatformType platform) {
		this.msgType = msgType;
		this.platform = platform;
	}
	
	/**
	 * 创建消息对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T createMessage(OrderApiService service){
		this.service = service;
		if(RedisMessageType.MANTICORDER.equals(msgType)){
			msgDto = createManticMessage();
			redisKey = RedisKeyEnum.MESSAGE_TYPE_MAKE;
		}else if (RedisMessageType.TAKINGORDER.equals(msgType)) {
			msgDto = createTakingOrderMessage();
			redisKey = RedisKeyEnum.MESSAGE_TYPE_READ;
		}
		return (T) msgDto;
	}
	
	public RedisMessage getRedisMessage(){
		if(redisMessage != null){
			return redisMessage;
		}
		redisMessage = new RedisMessage();
		redisMessage.setBusiness(RedisKeyEnum.MESSAGE_ORDER.code);
		redisMessage.setOperation(redisKey.code);
		redisMessage.setNowTime(System.currentTimeMillis());
		redisMessage.setExTime(((long) outTime)*60*1000);
		redisMessage.setMessage(msgDto);
		redisMessage.setFunction(RedisKeyEnum.MESSAGE_FUNCTION_POPUP.code);
		return redisMessage;
	}
	
	/**
	 * 创建人工派单消息,保证order,outtime有值
	 * @return
	 */
	private LabourMessageDto createManticMessage(){
		String username = "{username}({phone})";
		//只有机构端下单时显示乘车人,其他都显示下单人名称和手机
		if(order.getOrderprop() == 0 && "2".equals(order.getOrdersource())){
			username = username.replace("{username}", order.getPassengers()).replace("{phone}", order.getPassengerphone());
		}else{
			username = username.replace("{username}", order.getUsername()).replace("{phone}", order.getUserphone());
		}
		LabourMessageDto msgDto = new LabourMessageDto();
		//消息标题
		msgDto.setTitle(msgType.title);
		//订单号
		msgDto.setOrderNum(order.getOrderno());
		//车型(中文)
		msgDto.setCarType(order.getCartype());
		//推送平台
		msgDto.setToSystem(platform.code);
		//超时时间
		msgDto.setOutTime(outTime);
		//用车时间(格式化后)
		msgDto.setTimeStr(StringUtil.formatDate(order.getUsetime(), "yyyy-MM-dd HH:mm"));
		//租赁公司companyid
		msgDto.setCompanyId(order.getCompanyid());
		//机构ID
		msgDto.setOrganId(order.getOrderprop()==0 ? order.getOrganid() : order.getCompanyid());
		//支付方式(代码)
		msgDto.setPaymethod(order.getPaymethod());
		//上车城市ID
		msgDto.setCityCode(order.getCityid());
		//订单类型(中文)
		msgDto.setOrderType(OrderType.BOOK.type.equals(order.getType()) ? OrderType.BOOK.msg : 
							OrderType.PICKUP.type.equals(order.getType()) ? OrderType.PICKUP.msg :
							OrderType.PICKDOWN.type.equals(order.getType()) ? OrderType.PICKDOWN.msg :
							"");
		//订单类别 网约车/出租车
		msgDto.setOrderstyle(order.getOrderstyle());
		//下单人(姓名+手机号)
		msgDto.setUserName(username);
		return msgDto;
	}
	
	/**
	 * 创建抢单成功消息,保证order,driver有值
	 * @return {@linkplain SuccessMessageDto}
	 */
	private SuccessMessageDto createTakingOrderMessage(){
		String drivername = "{username}({phone})";
		if(order.getOrderprop() == 0 && "2".equals(order.getOrdersource())){
			drivername = drivername.replace("{username}", driver.getDrivername()).replace("{phone}", driver.getDriverphone());
		}else{
			drivername = drivername.replace("{username}", driver.getDrivername()).replace("{phone}", driver.getDriverphone());
		}
		SuccessMessageDto msgDto = new SuccessMessageDto();
		//订单号
		msgDto.setOrderNum(order.getOrderno());
		//车型(中文)
		msgDto.setCarType(order.getCartype());
		//品牌车系
		msgDto.setCarBrand(driver.getCarbrand()+driver.getCarvehcline());
		//车牌号
		msgDto.setCarNum(driver.getPlateno());
		//司机姓名<姓名(手机号)>
		msgDto.setDriverName(drivername);
		//推送平台
		msgDto.setToSystem(platform.code);
		//上车城市ID
		msgDto.setCityCode(order.getCityid());
		//租赁公司companyid
		msgDto.setCompanyId(order.getCompanyid());
		//机构ID
		msgDto.setOrganId(order.getOrderprop()==0 ? order.getOrganid() : order.getCompanyid());
		//订单类别 网约车/出租车
		msgDto.setOrderstyle(order.getOrderstyle());
		//结算方式
		msgDto.setPaymethod(order.getPaymethod());
		return msgDto;
	}
	
	public void sendMessage(){
		redisMessage = getRedisMessage();
		List<String> list = new ArrayList<>();
		if(RedisMessageType.MANTICORDER.equals(msgType)){
			LabourMessageDto dto = (LabourMessageDto)msgDto;
			list = service.getOrderRedisMessageKeys(dto.getToSystem(),dto.getPaymethod(),dto.getOrganId(),
													dto.getCompanyId(),dto.getCityCode(),dto.getOrderstyle());
			logger.info("----添加待人工派单redis消息，消息内容：{},发送角色：{}", GsonUtil.toJson(dto),GsonUtil.toJson(list));
		}else{
			SuccessMessageDto dto = (SuccessMessageDto)msgDto;
			list = service.getOrderRedisMessageKeys(dto.getToSystem(),dto.getPaymethod(),dto.getOrganId(),
													dto.getCompanyId(),dto.getCityCode(),dto.getOrderstyle());
			logger.info("----添加派单成功redis消息，消息内容：{},发送角色：{}", GsonUtil.toJson(dto),GsonUtil.toJson(list));
		}
		for(String key : list){
			redisMessage.setKey(key);
			try {
				RedisListMessage.getInstance().pushMessage(redisMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**  
	 * 获取超时时间  
	 * @return outTime 超时时间  
	 */
	public int getOutTime() {
		return outTime;
	}
	
	/**  
	 * 设置超时时间  
	 * @param outTime 超时时间  
	 */
	public void setOutTime(int outTime) {
		this.outTime = outTime;
	}
	
	/**  
	 * 获取消息类型  
	 * @return msgType 消息类型  
	 */
	public RedisMessageType getMsgType() {
		return msgType;
	}
	
	/**  
	 * 设置消息类型  
	 * @param msgType 消息类型  
	 */
	public void setMsgType(RedisMessageType msgType) {
		this.msgType = msgType;
	}

	/**  
	 * 获取推送平台  
	 * @return platform 推送平台  
	 */
	public PlatformType getPlatform() {
		return platform;
	}
	
	/**  
	 * 设置推送平台  
	 * @param platform 推送平台  
	 */
	public void setPlatform(PlatformType platform) {
		this.platform = platform;
	}
	
	/**  
	 * 获取订单信息实体  
	 * @return order 订单信息实体  
	 */
	public OrderInfoDetail getOrder() {
		return order;
	}
	
	/**  
	 * 设置订单信息实体  
	 * @param order 订单信息实体  
	 */
	public void setOrder(OrderInfoDetail order) {
		this.order = order;
	}

	/**  
	 * 获取消息实体  
	 * @return msgDto 消息实体  
	 */
	@SuppressWarnings("unchecked")
	public <T> T getMsgDto() {
		return (T)msgDto;
	}

	/**  
	 * 获取司机对象  
	 * @return driver 司机对象  
	 */
	public DriverInfo getDriver() {
		return driver;
	}
	

	/**  
	 * 设置司机对象  
	 * @param driver 司机对象  
	 */
	public void setDriver(DriverInfo driver) {
		this.driver = driver;
	}
}
