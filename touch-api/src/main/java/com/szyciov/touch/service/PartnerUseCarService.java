package com.szyciov.touch.service;

import com.szyciov.driver.enums.AlarmProcessEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.OrderType;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.dto.pubAlarmprocess.AlarmprocessPopDto;
import com.szyciov.dto.pubAlarmprocess.PubAlarmprocessSaveDto;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.PayMethod;
import com.szyciov.entity.PlatformType;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserType;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.message.UserMessage;
import com.szyciov.message.redis.RedisMessage;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.entity.TreeNode;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.passenger.param.LoginParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.touch.dao.PartnerUseCarDao;
import com.szyciov.touch.dto.AirPortDTO;
import com.szyciov.touch.dto.AroundVehicleDTO;
import com.szyciov.touch.dto.ChargeRuleDTO;
import com.szyciov.touch.dto.CityDTO;
import com.szyciov.touch.dto.CostEstimateDTO;
import com.szyciov.touch.dto.NetworkCarChargeRuleDTO;
import com.szyciov.touch.dto.OrderResultDTO;
import com.szyciov.touch.dto.OrderSimplesResultDTO;
import com.szyciov.touch.dto.OrderTrackDTO;
import com.szyciov.touch.dto.PointLocationDTO;
import com.szyciov.touch.dto.ServiceBusinessDTO;
import com.szyciov.touch.dto.ServiceModelsDTO;
import com.szyciov.touch.dto.TaxiChargeRuleDTO;
import com.szyciov.touch.dto.UserDetailDTO;
import com.szyciov.touch.enums.CustomerType;
import com.szyciov.touch.enums.OrderPayStatusEnums;
import com.szyciov.touch.enums.OrderStatusEnums;
import com.szyciov.touch.enums.OrderTypeEnum;
import com.szyciov.touch.enums.PayTypeEnum;
import com.szyciov.touch.enums.ResultStateEnum;
import com.szyciov.touch.util.Const;
import com.szyciov.touch.util.DateUtil;
import com.szyciov.touch.util.InterfaceTokenManager;
import com.szyciov.touch.util.ResultUtil;
import com.szyciov.util.BaiduUtil;
import com.szyciov.util.FileUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PYTools;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.SMSCodeUtil;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper4CarServiceApi;
import com.szyciov.util.UNID;
import com.szyciov.util.UserTokenManager;
import com.szyciov.util.message.RedisListMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shikang on 2017/5/10.
 */

@Service("PartnerUseCarService")
public class PartnerUseCarService {

    private static final Logger logger = Logger.getLogger(PartnerUseCarService.class);

    //资源id的分割符
  	private static final String SEPARATOR = "$";
    
    private TemplateHelper4CarServiceApi carserviceapi = new TemplateHelper4CarServiceApi();

	private PartnerUseCarDao partnerUseCarDao;
	@Resource(name = "PartnerUseCarDao")
	public void setPartnerUseCarDao(PartnerUseCarDao partnerUseCarDao) {
		this.partnerUseCarDao = partnerUseCarDao;
	}

	private PubInfoService pubInfoService;
	@Resource(name = "PubInfoService")
	public void setPubInfoService(PubInfoService pubInfoService) {
		this.pubInfoService = pubInfoService;
	}
	
	/**
	 * 获取全量城市列表
	 * @param req
	 * @param res
	 * @return
	 */
	public Map<String, Object> getFullCityList(HttpServletRequest req) {
		Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);

		//业务方法
		List<CityDTO> fullcitylist = partnerUseCarDao.getFullCityList();
		result.put("data", fullcitylist);
		return result;
	}
	
	/**
	 * 获取服务城市列表
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getServiceCityList(HttpServletRequest req) throws Exception {
		Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
		
		//参数校验
		
		//租赁公司id
		Map<String, Object> company = pubInfoService.getCompanyByChannel(req);
		if (null == company || company.isEmpty() || StringUtil.isBlank(company, "companyid")) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		String companyid = company.get("companyid").toString();
		//String companyid = req.getParameter("companyid");
		
		//业务方法
		List<CityDTO> servicecitylist = partnerUseCarDao.getServiceCityList(companyid);
		result.put("data", servicecitylist);
		return result;
	}
	
	/**
	 * 获取城市机场列表
	 * @param req
	 * @param res
	 * @return
	 */
	public Map<String, Object> getAirPortList(HttpServletRequest req) {
		Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
		
		//参数校验
		String cityId = req.getParameter("cityId");
		if (StringUtils.isBlank(cityId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		//业务方法
		List<AirPortDTO> airportlist = partnerUseCarDao.getAirPortList(cityId);
		result.put("data", airportlist);
		return result;
	}
	
	/**
	 * 获取服务业务列表
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getServiceBusiness(HttpServletRequest req) throws Exception {
		Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
		
		//参数校验
		String cityId = req.getParameter("cityId");
		if (StringUtils.isBlank(cityId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		//租赁公司id
		Map<String, Object> company = pubInfoService.getCompanyByChannel(req);
		if (null == company || company.isEmpty() || StringUtil.isBlank(company, "companyid")) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		Map<String, Object> parammap = new HashMap<String, Object>();
		//parammap.put("companyid", req.getParameter("companyid"));
		parammap.put("companyid", company.get("companyid").toString());
		parammap.put("cityId", cityId);
		parammap.put("type", "0");
		// 业务方法
		List<ServiceBusinessDTO> servicebusinesslist = partnerUseCarDao.getServiceBusiness(parammap);
		if (servicebusinesslist == null) {
			result = ResultUtil.getResultMapInfo(ResultStateEnum.NOSERVICESINCITY);
		} else {
			for (ServiceBusinessDTO serviceBusinessDTO : servicebusinesslist) {
				if (OrderTypeEnum.ORDERTYPE_TAXI.realOrdertype.equals(serviceBusinessDTO.getBusinessId())) {
					serviceBusinessDTO.setBusinessId(OrderTypeEnum.ORDERTYPE_TAXI.ordertype);
				} else if (OrderTypeEnum.ORDERTYPE_RESERVE.realOrdertype.equals(serviceBusinessDTO.getBusinessId())) {
					serviceBusinessDTO.setBusinessId(OrderTypeEnum.ORDERTYPE_RESERVE.ordertype);
				} else if (OrderTypeEnum.ORDERTYPE_PICKUP.realOrdertype.equals(serviceBusinessDTO.getBusinessId())) {
					serviceBusinessDTO.setBusinessId(OrderTypeEnum.ORDERTYPE_PICKUP.ordertype);
				} else if (OrderTypeEnum.ORDERTYPE_DROPOFF.realOrdertype.equals(serviceBusinessDTO.getBusinessId())) {
					serviceBusinessDTO.setBusinessId(OrderTypeEnum.ORDERTYPE_DROPOFF.ordertype);
				}
			}
			
			result.put("data", servicebusinesslist);
		}
		return result;
	}
	
	/**
	 * 获取服务车型列表
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getServiceModels(HttpServletRequest req) throws Exception {
		Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
		
		//参数校验
		String cityId = req.getParameter("cityId");
		if (StringUtils.isBlank(cityId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		String businessId = req.getParameter("businessId");
		if (StringUtils.isBlank(businessId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}

		//租赁公司id
		Map<String, Object> company = pubInfoService.getCompanyByChannel(req);
		if (null == company || company.isEmpty() || StringUtil.isBlank(company, "companyid")) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		Map<String, Object> parammap = new HashMap<String, Object>();
		//parammap.put("companyid", req.getParameter("companyid"));
		parammap.put("companyid", company.get("companyid").toString());
		parammap.put("cityId", cityId);
		parammap.put("type", "0");
		parammap.put("businessId", OrderTypeEnum.getOrdertype(businessId).realOrdertype);

		// 业务方法
		List<ServiceModelsDTO> servicemodelslist = partnerUseCarDao.getServiceModels(parammap);
		result.put("data", servicemodelslist);
		return result;
	}
	
	/**
	 * 获取周边车辆列表
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getAroundVehicle(HttpServletRequest req) throws Exception {
		Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
		
		//参数校验
		String longitude = req.getParameter("longitude");
		if (StringUtils.isBlank(longitude)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		String latitude = req.getParameter("latitude");
		if (StringUtils.isBlank(latitude)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		/*String cityId = req.getParameter("cityId");
		if (StringUtils.isBlank(cityId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}*/
		
		//租赁公司id
		Map<String, Object> company = pubInfoService.getCompanyByChannel(req);
		if (null == company || company.isEmpty() || StringUtil.isBlank(company, "companyid")) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		
		String usertoken = req.getParameter("token");
		int radius = 5000;
		double[] rangeinfo = BaiduUtil.getRange(parseDouble(longitude), parseDouble(latitude), radius);
		PubDriverInBoundParam pubparam = new PubDriverInBoundParam(rangeinfo);
		// pubparam.setCity(cityId);
		pubparam.setCompanyid(company.get("companyid").toString());
		pubparam.setSimple(true);
		// 0-网约车，1-出租车
		pubparam.setVehicletype(0);
		if (isOrgUser(usertoken)) {
			pubparam.setOrguser(true);
		} else {
			pubparam.setOrguser(false);
		}
		JSONObject driverinfos = carserviceapi.dealRequestWithToken("/PubDriver/GetLeDriverInBound", HttpMethod.POST,
				usertoken, pubparam, JSONObject.class);
		List<AroundVehicleDTO> aroundVehicleList = new ArrayList<AroundVehicleDTO>();
		if (driverinfos != null && driverinfos.getInt("status") == 0) {
			int count = driverinfos.getInt("count");
			if (count > 0) {
				AroundVehicleDTO aroundVehicleDTO = new AroundVehicleDTO();
				JSONArray drivers = driverinfos.getJSONArray("list");
				for (int i = 0; i < drivers.size(); i++) {
					JSONObject drinfo = drivers.getJSONObject(i);
					aroundVehicleDTO.setLongitude((double) drinfo.get("lng"));
					aroundVehicleDTO.setLatitude((double) drinfo.get("lat"));
					aroundVehicleList.add(aroundVehicleDTO);
				}
			}
		}
		
		result.put("data", aroundVehicleList);
		return result;
	}
	
	/**
	 * 转化obj成value，空值就为0
	 * @param value
	 * @return
	 */
	private double parseDouble(Object value) {
		if (value == null || "".equalsIgnoreCase(String.valueOf(value))) {
			return 0;
		}
		return Double.parseDouble(String.valueOf(value));
	}
	
	/**
	 * 获取行程费用预估
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getCostEstimate(HttpServletRequest req) throws Exception {
		Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
		
		// 参数校验
		// 出发地经度
		String departureLon = req.getParameter("departureLon");
		if (StringUtils.isBlank(departureLon)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		// 出发地纬度
		String departureLat = req.getParameter("departureLat");
		if (StringUtils.isBlank(departureLat)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		// 目的地经度
		String destinationLon = req.getParameter("destinationLon");
		if (StringUtils.isBlank(destinationLon)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		// 目的地纬度
		String destinationLat = req.getParameter("destinationLat");
		if (StringUtils.isBlank(destinationLat)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		// 用车时间（时间戳、秒数）
		String useTime = req.getParameter("useTime");
		if (StringUtils.isBlank(useTime)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		// 城市ID
		String cityId = req.getParameter("cityId");
		if (StringUtils.isBlank(cityId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		// 业务ID
		String businessId = req.getParameter("businessId");
		if (StringUtils.isBlank(businessId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		// 车型ID
		String modelsId = req.getParameter("modelsId");
		if (StringUtils.isBlank(modelsId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}		
		
		// 租赁公司id
		Map<String, Object> company = pubInfoService.getCompanyByChannel(req);
		if (null == company || company.isEmpty() || StringUtil.isBlank(company, "companyid")) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		Map<String, Object> parammap = new HashMap<String, Object>();
		parammap.put("companyid", company.get("companyid").toString());
		parammap.put("city", cityId);
		parammap.put("ordertype", OrderTypeEnum.getOrdertype(businessId).realOrdertype);
		parammap.put("rulestype", "0");
		parammap.put("cartype", modelsId);
		
		Map<String, Object> orgOrderCost = null;
		if (OrderTypeEnum.ORDERTYPE_TAXI.ordertype.equals(businessId)) {
			
		} else {
			orgOrderCost = partnerUseCarDao.getOrgOrderCost(parammap);
		}

		if (orgOrderCost == null) {
			result = ResultUtil.getResultMapInfo(ResultStateEnum.NOSERVICESINCITY);
		} else {
			if (OrderTypeEnum.ORDERTYPE_TAXI.realOrdertype.equals(orgOrderCost.get("rulestype").toString())) {
				
			} else {
				BaiduApiQueryParam baqp = new BaiduApiQueryParam();
				baqp.setOrderStartLng(parseDouble(departureLon));
				baqp.setOrderStartLat(parseDouble(departureLat));
				baqp.setOrderEndLng(parseDouble(destinationLon));
				baqp.setOrderEndLat(parseDouble(destinationLat));
				Map<String, Object> direc = carserviceapi.dealRequestWithToken("/BaiduApi/GetMileageInfo",
						HttpMethod.POST, null, baqp, Map.class);
				if (direc == null || ((int) direc.get("status")) != Retcode.OK.code) {
					result = ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
					logger.error("获取预估里程失败！");
					return result;
				}
				
				int mileage = (int) direc.get("distance");
				int times = (int) direc.get("duration");
				// accountrules.put("mileage", mileage);
				// accountrules.put("times", times);
				CostEstimateDTO costEstimateDTO = new CostEstimateDTO();
				costEstimateDTO.setDistance(Double.valueOf(String.valueOf(mileage)));
				costEstimateDTO.setDuration(times);
				// 计算价格
				double startprice = parseDouble(orgOrderCost.get("startprice"));
				double rangeprice = parseDouble(orgOrderCost.get("rangeprice"));
				double timeprice = parseDouble(orgOrderCost.get("timeprice"));
				String timetype = (String) orgOrderCost.get("timetype");
				double perhour = parseDouble(orgOrderCost.get("perhour"));
				double rangecost = StringUtil.formatNum(mileage / 1000, 1) * rangeprice;
				// accountrules.put("rangecost", StringUtil.formatNum(rangecost,1));
				// 低速模式计算累计时长(预估时仍按总时长计费)
				double timecost = (times % 60 > 0 ? (times / 60 + 1) : times / 60) * timeprice;
				// accountrules.put("timecost",StringUtil.formatNum(timecost,1));
				costEstimateDTO.setAmount(StringUtil.formatNum(startprice + rangecost + timecost, 1));
				result.put("data", costEstimateDTO);
			}
		}

		return result;
	}
	
	
	/**
	 * 获取计价规则
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getChargeRule(HttpServletRequest req) throws Exception {
		Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
		
		// 参数校验
		// 城市ID
		String cityId = req.getParameter("cityId");
		if (StringUtils.isBlank(cityId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		// 业务ID
		String businessId = req.getParameter("businessId");
		if (StringUtils.isBlank(businessId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		// 车型ID
		String modelsId = req.getParameter("modelsId");
		if (StringUtils.isBlank(modelsId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		
		//租赁公司id
		Map<String, Object> company = pubInfoService.getCompanyByChannel(req);
		if (null == company || company.isEmpty() || StringUtil.isBlank(company, "companyid")) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}

		Map<String, Object> parammap = new HashMap<String, Object>();
		parammap.put("companyid", company.get("companyid").toString());
		parammap.put("city", cityId);
		parammap.put("ordertype", OrderTypeEnum.getOrdertype(businessId).realOrdertype);
		parammap.put("rulestype", "0");
		parammap.put("cartype", modelsId);
		
		Map<String, Object> orgOrderCost = null;
		if (OrderTypeEnum.ORDERTYPE_TAXI.ordertype.equals(businessId)) {
			
		} else {
			orgOrderCost = partnerUseCarDao.getOrgOrderCost(parammap);
		}

		if (orgOrderCost == null) {
			result = ResultUtil.getResultMapInfo(ResultStateEnum.NOSERVICESINCITY);
		} else {
			ChargeRuleDTO chargeRuleDTO = new ChargeRuleDTO();
			if (OrderTypeEnum.ORDERTYPE_TAXI.realOrdertype.equals(orgOrderCost.get("rulestype").toString())) {
				// 1- 出租车模式；2- 网约车模式；
				chargeRuleDTO.setRuleType(OrderTypeEnum.ORDERTYPE_TAXI.ordertype);
				TaxiChargeRuleDTO taxiChargeRuleDTO = new TaxiChargeRuleDTO();
				// 起租价
				//taxiChargeRuleDTO.setOnhirePrice(parseDouble(orgOrderCost.get("startprice")));
				// 起租里程
				//taxiChargeRuleDTO.setOnhireMileage(parseDouble(orgOrderCost.get("startrange")));
				// 续租价
				//taxiChargeRuleDTO.setReletePrice(parseDouble(orgOrderCost.get("renewalprice")));
				// 空驶费（标准里程）
				//taxiChargeRuleDTO.setEmptyFee(parseDouble(orgOrderCost.get("standardrange")));
				// 空驶费率
				//taxiChargeRuleDTO.setEmptyRate(parseDouble(orgOrderCost.get("emptytravelrate")));
				// 附加费
				//taxiChargeRuleDTO.setAttachedFee(parseDouble(orgOrderCost.get("surcharge")));
				chargeRuleDTO.setTaxiRule(taxiChargeRuleDTO);
				chargeRuleDTO.setModelsName(orgOrderCost.get("name").toString());
				chargeRuleDTO.setModelsLogo(orgOrderCost.get("logo").toString());
			} else {
				chargeRuleDTO.setRuleType(OrderTypeEnum.ORDERTYPE_RESERVE.ordertype);
				NetworkCarChargeRuleDTO networkCarChargeRuleDTO = new NetworkCarChargeRuleDTO();
				// 起步价
				networkCarChargeRuleDTO.setStartPrice(parseDouble(orgOrderCost.get("startprice")));
				// 起始里程
				// 里程费价
				networkCarChargeRuleDTO.setMileagePrice(parseDouble(orgOrderCost.get("rangeprice")));
				// 时长费价
				networkCarChargeRuleDTO.setTimePrice(parseDouble(orgOrderCost.get("timeprice")));
				// 标准里程
				// 远途费价
				// 夜间费起征时间
				// 夜间费止征时间
				// 夜间费价
				// 低速标准车速
				networkCarChargeRuleDTO.setLowSpeed(parseDouble(orgOrderCost.get("perhour")));
				// 低速费价
				// 最低消费
				
				chargeRuleDTO.setNetWorkRule(networkCarChargeRuleDTO);
				chargeRuleDTO.setModelsName(orgOrderCost.get("name").toString());
				chargeRuleDTO.setModelsLogo(orgOrderCost.get("logo").toString());
			}
			
			result.put("data", chargeRuleDTO);
		}
		return result;
	}

	/**
	 * 用车下单
	 *
	 * @param req
	 * @return
	 */
	public Map<String, Object> postInstantOrder(HttpServletRequest req) throws Exception {
		OrgOrder order = new OrgOrder();
		//参数校验返回结果
		Map<String, Object> result = checkParam4PostInstantOrder(req, order);
		if (!result.get("result").equals(ResultStateEnum.OK.state)) {
			return result;
		}
		//创建订单
		order.setUsetype(OrderEnum.USETYPE_PRIVATE.code);
		order.setPaymethod(PayMethod.PERSONAL.code);
		order.setOrdersource(OrderEnum.ORDERSOURCE_PARTNER.code);
		JSONObject resJson = carserviceapi.dealRequestWithToken("/OrderApi/CreateOrgOrder", HttpMethod.POST, null, order, JSONObject.class);
		int codeStatus = Retcode.OK.code;
		if (resJson.containsKey("status")) {
			codeStatus = resJson.getInt("status");
		}
		if (codeStatus == Retcode.OK.code) {
			String orderno = resJson.getString("orderno");
			result.put("data", getOrderResultDTO(orderno));
			return result;
		} else if (codeStatus == Retcode.NOSERVICESINCITY.code) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.NOSERVICESINCITY);
		} else if (codeStatus == Retcode.ORDERNOTPAY.code) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.ORDERNOTPAY);
		} else if (codeStatus == Retcode.ORDEROUTOFLIMIT.code) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.ORDEROUTOFLIMIT);
		} else if (codeStatus == Retcode.BEFORENOWTIME.code) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.BEFORENOWTIME);
		} else {
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}

	/**
	 * 用户下单参数校验
	 *
	 * @param req
	 * @param order
	 * @return
	 */
	private Map<String, Object> checkParam4PostInstantOrder(HttpServletRequest req, OrgOrder order) throws Exception {
		//Email（渠道为原生App时必须）
		String email = req.getParameter("email");

		//用户名（渠道为原生App时必须）
		String username = req.getParameter("username");

		//乘车人手机号
		String passengerPhone = req.getParameter("passengerPhone");
		if (StringUtils.isBlank(passengerPhone)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setPassengerphone(passengerPhone);

		//用车时间
		String usetime = req.getParameter("usetime");
		if (StringUtils.isBlank(usetime)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setUsetime(usetime);

		//出发地城市ID
		String departureId = req.getParameter("departureId");
		if(StringUtils.isBlank(departureId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setOncity(departureId);

		//出发地地址
		String departureAddress = req.getParameter("departureAddress");
		if (StringUtils.isBlank(departureAddress)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setOnaddress(departureAddress);

		//出发地经度
		String departureLon = req.getParameter("departureLon");
		if (StringUtils.isBlank(departureLon)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setOnaddrlng(Double.valueOf(departureLon));

		//出发地纬度
		String departureLat = req.getParameter("departureLat");
		if (StringUtils.isBlank(departureLat)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setOnaddrlat(Double.valueOf(departureLat));

		//目的地城市ID
		String destinationId = req.getParameter("destinationId");
		if(StringUtils.isBlank(destinationId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setOffcity(destinationId);

		//目的地地址
		String destinationAddress = req.getParameter("destinationAddress");
		if (StringUtils.isBlank(destinationAddress)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setOffaddress(destinationAddress);

		//目的地经度
		String destinationLon = req.getParameter("destinationLon");
		if (StringUtils.isBlank(destinationLon)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setOffaddrlng(Double.valueOf(destinationLon));

		//目的地纬度
		String destinationLat = req.getParameter("destinationLat");
		if (StringUtils.isBlank(destinationLat)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setOffaddrlat(Double.valueOf(destinationLat));

		//用车备注
		String useRemark = req.getParameter("useRemark");
		if (StringUtils.isBlank(useRemark)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setTripremark(useRemark);

		//业务ID
		String businessId = req.getParameter("businessId");
		if (StringUtils.isBlank(businessId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setOrdertype(OrderTypeEnum.getOrdertype(businessId).realOrdertype);

		//车型ID
		String modelsId = req.getParameter("modelsId");
		if (StringUtils.isBlank(modelsId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setSelectedmodel(modelsId);

		if (businessId.equals(OrderTypeEnum.ORDERTYPE_PICKUP.ordertype)) {
			//航班号，接机时必填
			String fltno = req.getParameter("fltno");
			if (StringUtils.isBlank(fltno)) {
				return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
			}
			order.setFltno(fltno);

			//降落时间，接机时必填
			String falltime = req.getParameter("falltime");
			if (StringUtils.isBlank(falltime)) {
				return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
			}
			order.setFalltime(DateUtil.formatStrToDate(falltime));
		}

		//下单人
		Map<String, Object> orgUser = pubInfoService.getOrgUserByChannel(req);
		if(null == orgUser || orgUser.isEmpty() || StringUtil.isBlank(orgUser, "id")) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setUserid(orgUser.get("id").toString());

		//租赁公司id
		Map<String, Object> company = pubInfoService.getCompanyByChannel(req);
		if(null == company || company.isEmpty() || StringUtil.isBlank(company, "companyid")) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setCompanyid(company.get("companyid").toString());

		return ResultUtil.getResultMapInfo(ResultStateEnum.OK);
	}

	/**
	 * 订单详情结果返回
	 * @param orderno
	 * @return
	 */
	private OrderResultDTO getOrderResultDTO(String orderno) throws Exception {
		Map<String, Object> order = partnerUseCarDao.getOrgOrderByOrderno(orderno);
		if(null == order) {
			return null;
		}
		OrderResultDTO orderResultDTO = new OrderResultDTO();
		//订单信息
		orderResultDTO.setOrderId(order.get("orderno").toString()); //订单ID
        if(OrderStatusEnums.SERVICEDONE.realstate.equals(order.get("orderstatus").toString())) { //订单状态
            orderResultDTO.setOrderState(OrderPayStatusEnums.getRealOrderPayStatus(order.get("paymentstatus").toString()).state);
        } else {
            orderResultDTO.setOrderState(OrderStatusEnums.getRealOrderstatus(order.get("orderstatus").toString()).state);
        }
		orderResultDTO.setOrderPersonPhone(order.get("account").toString()); //下单人标识ID
		orderResultDTO.setPassengerPhone(order.get("passengerphone").toString()); //乘车人手机号
		orderResultDTO.setDepartureLon(Double.valueOf(order.get("onaddrlng").toString())); //出发地经度
		orderResultDTO.setDepartureLat(Double.valueOf(order.get("onaddrlat").toString())); //出发地纬度
		orderResultDTO.setDepartureName(order.get("oncityname").toString()); //出发地名称
		orderResultDTO.setDepartureAddress(order.get("onaddress").toString()); //出发地地址
		orderResultDTO.setDestinationLon(Double.valueOf(order.get("offaddrlng").toString())); //目的地经度
		orderResultDTO.setDestinationLat(Double.valueOf(order.get("offaddrlat").toString())); //目的地纬度
		orderResultDTO.setDestinationName(order.get("offcityname").toString()); //目的地名称
		orderResultDTO.setDestinationAddress(order.get("offaddress").toString()); //目的地地址
		if(StringUtil.isNotBlank(order, "tripremark")) { //用车备注
			orderResultDTO.setUseRmark(order.get("tripremark").toString());
		}
		orderResultDTO.setOrderTime(DateUtil.formatStrToStr(order.get("undertime").toString())); //下单时间
		orderResultDTO.setUseTime(DateUtil.formatStrToStr(order.get("usetime").toString())); //用车时间
		if(StringUtil.isNotBlank(order, "canceltime")) { //取消时间
			orderResultDTO.setCancelTime(DateUtil.formatStrToStr(order.get("canceltime").toString()));
		}
		orderResultDTO.setEstimateAmount(Double.valueOf(order.get("estimatedcost").toString())); //预估费用
		orderResultDTO.setEstimateDistance(Double.valueOf(order.get("estimatedmileage").toString()) * 1000); //预估里程
		orderResultDTO.setEstimateDuration(Integer.valueOf(order.get("estimatedtime").toString()) * 60); //预估时长
		if (orderResultDTO.getOrderState().equals(OrderStatusEnums.INSERVICE.state) || orderResultDTO.getOrderState().equals(OrderStatusEnums.SERVICEDONE.state)) {
			orderResultDTO.setRealDepartureName(order.get("startcityname").toString()); //实际上车点名称
			orderResultDTO.setRealDepartureAddress(order.get("startaddress").toString()); //实际上车点地址
			orderResultDTO.setRealDepartureLon(Double.valueOf(order.get("startlng").toString())); //实际上车点经度
			orderResultDTO.setRealDepartureLat(Double.valueOf(order.get("startllat").toString())); //实际上车点纬度

			//查询订单费用相关数据
			OrderCostParam param = new OrderCostParam();
			param.setHasunit(false);
			param.setOrderno(order.get("orderno").toString());
			param.setUsetype(order.get("usetype").toString());
			param.setOrdertype(order.get("ordertype").toString());
			JSONObject costJson = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, null, param, JSONObject.class);
			orderResultDTO.setRealDistance(Double.valueOf(
					costJson.containsKey("mileage") ? StringUtil.formatNum(costJson.getDouble("mileage"), 1) : 0.0D)); //行驶里程
			orderResultDTO.setRealDuration(Integer.valueOf(
					costJson.containsKey("times") ? costJson.getInt("times") : 0)); //行驶时长
			orderResultDTO.setRealAmount(costJson.containsKey("cost") ? costJson.getDouble("cost") : 0); //订单金额
		}
		if(orderResultDTO.getOrderState().equals(OrderStatusEnums.SERVICEDONE.state)) {
			orderResultDTO.setRealDestinationName(order.get("endcityname").toString()); //实际下车点名称
			orderResultDTO.setRealDestinationAddress(order.get("endaddress").toString()); //实际下车点地址
			orderResultDTO.setRealDestinationLon(Double.valueOf(order.get("endlng").toString())); //实际下车点经度
			orderResultDTO.setRealDestinationLat(Double.valueOf(order.get("endllat").toString())); //实际下车点纬度
		}
		if(StringUtil.isNotBlank(order, "departuretime")) { //司机出发时间
			orderResultDTO.setDriverDepartureTime(DateUtil.formatStrToStr(order.get("departuretime").toString()));
		}
		if(StringUtil.isNotBlank(order, "arrivaltime")) { //司机抵达时间
			orderResultDTO.setDriverArrivalTime(DateUtil.formatStrToStr(order.get("arrivaltime").toString()));
		}
		if(StringUtil.isNotBlank(order, "starttime")) { //开始服务时间
			orderResultDTO.setDriverStartTime(DateUtil.formatStrToStr(order.get("starttime").toString()));
		}
		if(StringUtil.isNotBlank(order, "endtime")) { //服务结束时间
			orderResultDTO.setDriverEndTime(DateUtil.formatStrToStr(order.get("endtime").toString()));
		}
		orderResultDTO.setModelsName(order.get("selectedmodelname").toString()); //下单车型名称
		orderResultDTO.setServiceModelsName(order.get("factmodelname").toString()); //服务车型名称
		orderResultDTO.setPriceModelsName(order.get("pricemodelname").toString()); //计价车型名称
		orderResultDTO.setCouponAmount(0d);
		if(StringUtil.isNotBlank(order, "actualpayamount")) { //实付金额
			orderResultDTO.setActuallyAmount(Double.valueOf(order.get("actualpayamount").toString()));
		} else {
			orderResultDTO.setActuallyAmount(0d);
		}
		orderResultDTO.setPayTime("");

		//司机信息
		if(StringUtil.isNotBlank(order, "driverid")) {
			Map<String, Object> driver = partnerUseCarDao.getPubDriverById(order.get("driverid").toString());
			if(null != driver && !driver.isEmpty()) {
				orderResultDTO.setDriverNickName(driver.get("name").toString()); //司机称呼
				orderResultDTO.setDriverLogo(SystemConfig.getSystemProperty("fileserver") + "/" + driver.get("headportraitmin")); //司机头像
				orderResultDTO.setDriverPhone(driver.get("phone").toString()); //司机手机号
				orderResultDTO.setDriverName(driver.get("name").toString()); //司机姓名
				if(StringUtil.isNotBlank(driver, "driverModelsName")) { //司机车型
					orderResultDTO.setDriverModelsName(driver.get("driverModelsName").toString());
				}
				if(StringUtil.isNotBlank(driver, "plateno")) { //司机车牌
					orderResultDTO.setDriverPlateNo(driver.get("plateno").toString());
				}
				if(StringUtil.isNotBlank(driver, "ordercount")) { //司机服务单数
					orderResultDTO.setDriverServiceOrderNum(Integer.valueOf(driver.get("ordercount").toString()));
				} else {
					orderResultDTO.setDriverServiceOrderNum(0);
				}
				if(StringUtil.isNotBlank(driver, "avgrate")) {
					orderResultDTO.setDriverGrade(driver.get("avgrate").toString()); //司机星级
				}
				orderResultDTO.setDriverLon((Double.valueOf(driver.get("lng").toString()))); //司机实时经度
				orderResultDTO.setDriverLat(Double.valueOf(driver.get("lat").toString())); //司机实时纬度
			}
		}

		return orderResultDTO;
	}

	/**
	 * 服务行程列表
	 * @param req
	 * @return
	 */
	public Map<String, Object> getServiceTravel(HttpServletRequest req) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//参数校验返回结果
		Map<String, Object> result = checkParam4GetServiceTravel(req, param);
		if (!result.get("result").equals(ResultStateEnum.OK.state)) {
			return result;
		}

		//查询订单数据
		List<Map<String, Object>> orderList = partnerUseCarDao.getSimpleOrder(param);
		result.put("data", getResult4GetServiceTravel(orderList));

		return result;
	}

	/**
	 * 服务行程列表参数校验
	 * @param req
	 * @return
	 */
	public Map<String, Object> checkParam4GetServiceTravel(HttpServletRequest req, Map<String, Object> param) throws Exception {
		//开始时间
		String startTime = req.getParameter("startTime");
		if(StringUtils.isBlank(startTime)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		param.put("startTime", startTime);

		//结束时间
		String endTime = req.getParameter("endTime");
		if(StringUtils.isBlank(endTime)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		param.put("endTime", endTime);

		//订单来源
		String orderSource = req.getParameter("orderSource");
		if(StringUtils.isBlank(orderSource)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		param.put("orderSource", orderSource);

		//下单人手机号码
		String orderPersonPhone = req.getParameter("orderPersonPhone");
		if(StringUtils.isBlank(orderPersonPhone)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		param.put("orderPersonPhone", orderPersonPhone);

		//Email
		String email = req.getParameter("email");
		param.put("email", email);

		//用户名
		String username = req.getParameter("username");
		param.put("username", username);

		Map<String, Object> orgUser = pubInfoService.getOrgUserByChannel(orderPersonPhone, req);
		if(null == orgUser || orgUser.isEmpty()) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		param.put("userid", orgUser.get("id"));

		return ResultUtil.getResultMapInfo(ResultStateEnum.OK);
	}

	/**
	 * 获取服务行程列表数据
	 * @param dataList
	 * @return
	 */
	public List<OrderSimplesResultDTO> getResult4GetServiceTravel(List<Map<String, Object>> dataList) throws Exception {
		if(null == dataList || dataList.isEmpty()) {
			return null;
		}

		//获取订单数据
		List<OrderSimplesResultDTO> resData = new ArrayList<OrderSimplesResultDTO>();
		for (Map<String, Object> data : dataList) {
			OrderSimplesResultDTO orderSimplesResultDTO = new OrderSimplesResultDTO();
			orderSimplesResultDTO.setOrderId(data.get("orderno").toString()); //订单ID
            if(OrderStatusEnums.SERVICEDONE.realstate.equals(data.get("orderstatus").toString())){
                //订单完成使用支付状态
                orderSimplesResultDTO.setOrderState(OrderPayStatusEnums.getRealOrderPayStatus(data.get("paymentstatus").toString()).state); //订单状态
            }else{
                //订单未完成
                orderSimplesResultDTO.setOrderState(OrderStatusEnums.getRealOrderstatus(data.get("orderstatus").toString()).state); //订单状态
            }
			orderSimplesResultDTO.setModelsName(data.get("factmodelname").toString()); //车型名称
			orderSimplesResultDTO.setOrderPersonPhone(data.get("account").toString()); //下单人标识ID
			orderSimplesResultDTO.setPassengerPhone(data.get("passengerphone").toString()); //乘车人手机号
			orderSimplesResultDTO.setDepartureName(data.get("oncityname").toString()); //出发地名称
			orderSimplesResultDTO.setDepartureAddress(data.get("onaddress").toString()); //出发地地址
			orderSimplesResultDTO.setDestinationName(data.get("offcityname").toString()); //目的地名称
			orderSimplesResultDTO.setDestinationAddress(data.get("offaddress").toString()); //目的地地址
			if(StringUtil.isNotBlank(data, "tripremark")) { //用车备注
				orderSimplesResultDTO.setUseRmark(data.get("tripremark").toString());
			}
			orderSimplesResultDTO.setOrderTime(DateUtil.formatStrToStr(data.get("undertime").toString())); //下单时间
			orderSimplesResultDTO.setUseTime(DateUtil.formatStrToStr(data.get("usetime").toString())); //用车时间
			if(StringUtil.isNotBlank(data, "canceltime")) { //取消时间
				orderSimplesResultDTO.setCancelTime(DateUtil.formatStrToStr(data.get("canceltime").toString()));
			}
			if(StringUtil.isNotBlank(data, "orderamount")) { //订单金额
				orderSimplesResultDTO.setRealAmount(Double.valueOf(data.get("orderamount").toString()));
			}
			orderSimplesResultDTO.setCouponAmount(0d); //优惠金额
			if(StringUtil.isNotBlank(data, "actualpayamount")) { //实付金额
				orderSimplesResultDTO.setActuallyAmount(Double.valueOf(data.get("actualpayamount").toString()));
			}
			resData.add(orderSimplesResultDTO);
		}
		return resData;
	}

	/**
	 * 行程详情
	 * @param req
	 * @return
	 */
	public Map<String, Object> getServiceTravelDetail(HttpServletRequest req) throws Exception {
		//订单ID
		String orderId = req.getParameter("orderId");
		if(StringUtils.isBlank(orderId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}

		Map<String, Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
		result.put("data", getOrderResultDTO(orderId));
		return result;
	}

	/**
	 * 行程轨迹
	 * @param req
	 * @return
	 */
	public Map<String, Object> getServiceTravelTrack(HttpServletRequest req) {
		//订单ID
		String orderId = req.getParameter("orderId");
		if(StringUtils.isBlank(orderId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}

		Map<String, Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
		result.put("data", getResult4GetServiceTravelTrack(orderId));
		return result;
	}

	/**
	 * 行程轨迹结果返回
	 * @param orderno
	 * @return
	 */
	public OrderTrackDTO getResult4GetServiceTravelTrack(String orderno) {
		Map<String, Object> order = partnerUseCarDao.getOrgOrderByOrderno(orderno);
		if(null != order && !order.isEmpty()) {
			Object orderstatus = order.get("orderstatus");
			if(!orderstatus.equals(OrderStatusEnums.INSERVICE.realstate) && !orderstatus.equals(OrderStatusEnums.SERVICEDONE.realstate)) {
				return null;
			}
			String url = "/BaiduApi/GetTraceData?orderno=" + orderno + "&ordertype=" + order.get("ordertype") + "&usetype=" + order.get("usetype");
			JSONObject resJson = carserviceapi.dealRequestWithToken(url, HttpMethod.GET, null, null, JSONObject.class);
			if(null != resJson && !resJson.isEmpty() && resJson.getInt("status") == Retcode.OK.code) {
				OrderTrackDTO orderTrackDTO = new OrderTrackDTO();
				//起点
				if(resJson.containsKey("start_point")) {
					JSONObject startPoint = resJson.getJSONObject("start_point");
					if(null != startPoint && !startPoint.isEmpty()) {
						orderTrackDTO.setDepartureLon(startPoint.getDouble("longitude")); //上车地址经度
						orderTrackDTO.setDepartureLat(startPoint.getDouble("latitude")); //上车地址纬度
					}
				}
				//终点
				if(resJson.containsKey("end_point")) {
					JSONObject endPoint = resJson.getJSONObject("end_point");
					if(null != endPoint && !endPoint.isEmpty()) {
						orderTrackDTO.setDestinationLon(endPoint.getDouble("longitude")); //下车地址经度
						orderTrackDTO.setDestinationLat(endPoint.getDouble("latitude")); //下车地址纬度
					}
				} else {
					JSONObject startPoint = resJson.getJSONObject("start_point");
					if(null != startPoint && !startPoint.isEmpty()) {
						orderTrackDTO.setDestinationLon(startPoint.getDouble("longitude")); //下车地址经度
						orderTrackDTO.setDestinationLat(startPoint.getDouble("latitude")); //下车地址纬度
					}
				}

				//坐标点集合
				if(resJson.containsKey("points")) {
					JSONArray points = resJson.getJSONArray("points");
					if(null != points && !points.isEmpty()) {
						List<PointLocationDTO> pointLocationDTOList = new ArrayList<PointLocationDTO>();
						for (int m = 0;m < points.size(); m++) {
							PointLocationDTO pointLocationDTO = new PointLocationDTO();
							JSONObject point = points.getJSONObject(m);
							pointLocationDTO.setLocation(point.getString("location")); //百度加密坐标
							pointLocationDTO.setLoc_time(point.getLong("loc_time")); //坐标上传时间(Unix时间戳)
							pointLocationDTO.setCreate_time(point.getString("create_time")); //服务端时间
							pointLocationDTO.setDirection(point.getInt("direction")); //方向
							pointLocationDTO.setHeight(0d); //高度
							pointLocationDTO.setSpeed(point.getDouble("speed")); //速度,单位:km/h
							pointLocationDTO.setRadius(point.getDouble("radius")); //定位经度,单位:m
							pointLocationDTOList.add(pointLocationDTO);
						}
						orderTrackDTO.setPoints(pointLocationDTOList);
					}
				}

				return orderTrackDTO;
			}
		}
		return null;
	}

	/**
	 * 行程服务取消
	 * @param req
	 * @return
	 */
	public Map<String, Object> cancelOrder(HttpServletRequest req) {
		//订单ID
		String orderId = req.getParameter("orderId");
		if(StringUtils.isBlank(orderId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}

		//取消订单
		OrderApiParam orderApiParam = new OrderApiParam();
		orderApiParam.setOrderno(orderId);
		orderApiParam.setUsetype(OrderEnum.USETYPE_PRIVATE.code);
		orderApiParam.setOrderstate(OrderState.CANCEL.state);
		orderApiParam.setReqsrc(CancelParty.PARTNER.code);
		Map<String, Object> resMap = carserviceapi.dealRequestWithToken("/OrderApi/ChangeOrderState", HttpMethod.POST, null, orderApiParam, Map.class);
		int codeStatus = Retcode.OK.code;
		if(resMap.containsKey("status")) {
			codeStatus = Integer.valueOf(resMap.get("status").toString());
		}

		if(codeStatus == Retcode.OK.code) {
			Map<String, Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
			result.remove("data");
			result.put("penaltyAmount", 0);
			return result;
		} else if(codeStatus == Retcode.INVALIDORDERSTATUS.code) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.INVALIDORDERSTATUS);
		} else if(codeStatus == Retcode.ORDERISCANCEL.code) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.ORDERISCANCEL);
		} else {
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}

	/**
	 * 行程服务报警
	 * @param req
	 * @return
	 */
	public Map<String, Object> travelAlarm(HttpServletRequest req) throws Exception {
		PubAlarmprocessSaveDto pubAlarmprocessSaveDto = new PubAlarmprocessSaveDto();
		Map<String, Object> result = checkParam4TravelAlarm(req, pubAlarmprocessSaveDto);
		if(result.get("result").equals(ResultStateEnum.OK.state)) {
			createTravelAlarm(pubAlarmprocessSaveDto);
		} else {
			return result;
		}
		return result;
	}

	/**
	 * 行程服务报警参数校验
	 * @param req
	 * @return
	 */
	private Map<String, Object> checkParam4TravelAlarm(HttpServletRequest req, PubAlarmprocessSaveDto pubAlarmprocessSaveDto) throws Exception {
		//订单ID
		String orderId = req.getParameter("orderId");
		if(StringUtils.isBlank(orderId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		pubAlarmprocessSaveDto.setOrderno(orderId);
		Map<String, Object> order = partnerUseCarDao.getOrgOrderByOrderno(orderId);
		if(null != order && !order.isEmpty() && StringUtil.isNotBlank(order, "driverid")) {
			pubAlarmprocessSaveDto.setDriverid(order.get("driverid").toString());
		} else {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		String userid = order.get("userid").toString();
		pubAlarmprocessSaveDto.setUserid(userid);

		//报警位置经度
		String alarmLon = req.getParameter("alarmLon");
		if(StringUtils.isBlank(alarmLon)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		pubAlarmprocessSaveDto.setLng(alarmLon);

		//报警位置纬度
		String alarmLat = req.getParameter("alarmLat");
		if(StringUtils.isBlank(alarmLat)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		pubAlarmprocessSaveDto.setLat(alarmLat);

		//报警时间
		String alarmTime = req.getParameter("alarmTime");
		if(StringUtils.isBlank(alarmTime)) {
			ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		pubAlarmprocessSaveDto.setAlarmtime(alarmTime);

		//租赁公司id
		Map<String, Object> company = pubInfoService.getCompanyByChannel(req);
		if(null == company || company.isEmpty() || StringUtil.isBlank(company, "companyid")) {
			ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		pubAlarmprocessSaveDto.setLeasecompanyid(company.get("companyid").toString());

		pubAlarmprocessSaveDto.setCreater(userid);
		pubAlarmprocessSaveDto.setUpdater(userid);

		return ResultUtil.getResultMapInfo(ResultStateEnum.OK);
	}

	/**
	 * 创建行程服务报警信息
	 * @param object
	 */
	private void createTravelAlarm(PubAlarmprocessSaveDto object) throws Exception {
		object.setId(GUIDGenerator.newGUID());
		object.setPlatformtype(PlatformTypeByDb.LEASE.code);
		object.setUsertype(UserType.ORGUSER.code);
		object.setOrdertype(OrderType.ONLINECAR.type);
		object.setAlarmsource(AlarmProcessEnum.ALARMSOURCE_PASSENGER.code);
		object.setAlarmtype(AlarmProcessEnum.ALARMTYPE_INSERVICE.code);
		object.setProcessstatus(AlarmProcessEnum.PROCESSSTATUS_WAITING.code);
		partnerUseCarDao.insertPubAlarmprocessSaveDto(object);

		//发送redis消息
		List<String> userList = partnerUseCarDao.getLeUserByCompanyid(object.getLeasecompanyid());
		RedisMessage redisMessage = createRedisMessage(object, 30000);
		if(null != userList && !userList.isEmpty()) {
			redisMessage.setKey(RedisKeyEnum.MESSAGE_LEASE_ROLE_ADMIN.code + object.getLeasecompanyid());
			RedisListMessage.getInstance().pushMessage(redisMessage);
		} else {
			for (String userid : userList) {
				redisMessage.setKey(userid);
				RedisListMessage.getInstance().pushMessage(redisMessage);
			}
		}

	}

	/**
	 * 创建行程报警redis消息
	 * @param alarmprocessSaveDto
	 * @param extime
	 * @return
	 */
	private RedisMessage createRedisMessage(PubAlarmprocessSaveDto alarmprocessSaveDto, long extime) {
		AlarmprocessPopDto alarmprocessPopDto = new AlarmprocessPopDto();
		alarmprocessPopDto.setId(alarmprocessSaveDto.getId());
		alarmprocessPopDto.setAlarmsource(AlarmProcessEnum.ALARMSOURCE_PASSENGER.msg);
		alarmprocessPopDto.setAlarmtype("行程报警");
		alarmprocessPopDto.setTitle("待处理报警");

		RedisMessage redisMessage = new RedisMessage();
		redisMessage.setBusiness(RedisKeyEnum.MESSAGE_ALARMPROCESS.code);
		redisMessage.setOperation(RedisKeyEnum.MESSAGE_TYPE_MAKE.code);
		redisMessage.setNowTime(System.currentTimeMillis());
		redisMessage.setExTime(extime);
		redisMessage.setMessage(alarmprocessPopDto);
		redisMessage.setFunction(RedisKeyEnum.MESSAGE_FUNCTION_POPUP.code);
		return redisMessage;
	}

	/**
	 * 行程支付确认
	 * @param req
	 * @return
	 */
	public Map<String, Object> paymentConfirm(HttpServletRequest req) {
		Map<String, String> param = new HashMap<String, String>();

		Map<String, Object> result = checkParam4PaymentConfirm(req, param);
		if(result.get("result").equals(ResultStateEnum.OK.state)) {
			Map<String, Object> order = partnerUseCarDao.getOrgOrderByOrderno(param.get("orderno"));
			if(null != order && !order.isEmpty()) {
				if(order.get("orderstatus").equals(OrderStatusEnums.SERVICEDONE.realstate)
						&& order.get("paymentstatus").equals(OrderPayStatusEnums.NOTPAY.realstate)) {
					//修改订单支付状态
					OrgOrder orgOrder = new OrgOrder();
					orgOrder.setOrderno(param.get("orderno"));
					orgOrder.setPaymentstatus(OrderPayStatusEnums.PAYED.realstate);
					orgOrder.setPaytype(param.get("paytype"));
					partnerUseCarDao.updateOrgOrderPaymentStatus(orgOrder);

					//创建订单支付记录
					Map<String, String> orderPayRecord = new HashMap<String, String>();
					orderPayRecord.put("outtradeno", DateUtil.getNowDateNosymbol() + UNID.getUNID());
					orderPayRecord.put("orderno", param.get("orderno"));
					orderPayRecord.put("paymenttype", PayTypeEnum.getRealPayType(param.get("paytype")).recordstate);
					orderPayRecord.put("tradeno", param.get("tradeno"));
					partnerUseCarDao.insertOrgOrderpaymentrecord(orderPayRecord);
				} else {
					return ResultUtil.getResultMapInfo(ResultStateEnum.INVALIDORDERSTATUS);
				}
			} else {
				return ResultUtil.getResultMapInfo(ResultStateEnum.ORDERNOTEXIT);
			}
		}
		return result;
	}

	/**
	 * 行程支付确认参数校验
	 * @param req
	 * @return
	 */
	private Map<String, Object> checkParam4PaymentConfirm(HttpServletRequest req, Map<String, String> param) {
		//订单ID
		String orderId = req.getParameter("orderId");
		if(StringUtils.isBlank(orderId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		param.put("orderno", orderId);

		//支付平台
		String payPlatform = req.getParameter("payPlatform");
		if(StringUtils.isBlank(payPlatform)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		PayTypeEnum paytype = PayTypeEnum.getPayType(payPlatform);
		if(null == paytype) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		param.put("paytype", paytype.realstate);

		//流水号
		String serialNumber = req.getParameter("serialNumber");
		if(StringUtils.isBlank(serialNumber)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		param.put("tradeno", serialNumber);

		return ResultUtil.getResultMapInfo(ResultStateEnum.OK);
	}

	/**
	 * 司机服务评价
	 * @param req
	 * @return
	 */
	public Map<String, Object> orderEvaluate(HttpServletRequest req) {
		OrgOrder orgOrder = new OrgOrder();
		Map<String, Object> result = checkParam4OrderEvaluate(req, orgOrder);
		if(result.get("result").equals(ResultStateEnum.OK.state)) {
			Map<String, Object> order = partnerUseCarDao.getOrgOrderByOrderno(orgOrder.getOrderno());
			if(null != order && !order.isEmpty()) {
				String driverid = null;
				if(StringUtil.isNotBlank(order, "driverid")) {
					driverid = order.get("driverid").toString();
					int userrate = Integer.valueOf(orgOrder.getUserrate());
					double avgrate = 4.5;
					int ordercount = 1;
					Map<String, Object> driver = partnerUseCarDao.getPubDriverById(driverid);
					if(null != driver && !driver.isEmpty()) {
						//计算司机评分
						if(StringUtil.isNotBlank(driver, "avgrate")) {
							avgrate = Double.valueOf(driver.get("avgrate").toString());
						}
						if(StringUtil.isNotBlank(driver, "ordercount")) {
							ordercount = Integer.valueOf(driver.get("ordercount").toString());
						}
						if(ordercount <= 0) {
							ordercount = 1;
						}
						double newavgrate = (avgrate*(ordercount-1)+userrate)/ordercount;
						//修改司机评分
						Map<String, String> driverParam = new HashMap<String, String>();
						driverParam.put("avgrate", StringUtil.formatNum(newavgrate, 6) + "");
						driverParam.put("driverid", driverid);
						partnerUseCarDao.updateDriverRate(driverParam);
					}
				}
				//修改订单评分
				partnerUseCarDao.updateOrgOrderRate(orgOrder);
			} else {
				return ResultUtil.getResultMapInfo(ResultStateEnum.ORDERNOTEXIT);
			}
		}
		return result;
	}

	/**
	 * 司机服务评价参数校验
	 * @param req
	 * @return
	 */
	private Map<String, Object> checkParam4OrderEvaluate(HttpServletRequest req, OrgOrder order) {
		//订单ID
		String orderId = req.getParameter("orderId");
		if(StringUtils.isBlank(orderId)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setOrderno(orderId);

		//星级评分
		String score = req.getParameter("score");
		if(StringUtils.isBlank(score)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setUserrate(score);

		//评价内容
		String evaluationContent = req.getParameter("evaluationContent");
		if(StringUtils.isBlank(evaluationContent)) {
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		order.setUsercomment(evaluationContent);

		return ResultUtil.getResultMapInfo(ResultStateEnum.OK);
	}

	/**
	 * 上传用户头像
	 *
	 * @param request
	 * @return
	 * @author xuxxtr
	 * @throws IOException
	 */
	public Map<String, Object> uploadImg(HttpServletRequest request) throws IOException {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> result = null;
		String img = request.getParameter("userImg");
		String userToken = request.getParameter("usertoken");
		if (StringUtils.isBlank(img) || StringUtils.isBlank(userToken)) {
			res.put("result", ResultStateEnum.PARAMUNVALIABLE.state);
			res.put("errmsg", ResultStateEnum.PARAMUNVALIABLE.message);
			return res;
		}
		byte[] imgbyte = Base64.decodeBase64(img);
		ByteArrayInputStream inputstream = new ByteArrayInputStream(imgbyte);
		try {
			result = FileUtil.upload2FileServer(inputstream, "passengerimg.jpg");
		} finally {
			inputstream.close();
		}
		if (result != null && result.get("message") != null) {
			String path = (String) ((List) result.get("message")).get(0);
			// 更新用户头像
			res.put("imgPath", SystemConfig.getSystemProperty("fileserver") + File.separator + path);
			Map<String, Object> userinfo = new HashMap<String, Object>();
			userinfo.put("path", path);
			String account = Const.getUserInfo(userToken).get("account");
			if (isOrgUser(userToken)) {
				// 机构用户
				OrgUser user = partnerUseCarDao.getUser4Org(account);
				if (user != null && StringUtils.isNotBlank(user.getId())) {
					userinfo.put("userid", user.getId());
					partnerUseCarDao.updateUser4Org(userinfo);
					res.put("result", Retcode.OK.code);
				}else{
					res.put("result", ResultStateEnum.USERNOTEXIST.state);
					res.put("errmsg", ResultStateEnum.USERNOTEXIST.message);
				}
			} else {
				res.put("result", ResultStateEnum.ILLEGALUSERTYPE.state);
				res.put("errmsg", ResultStateEnum.ILLEGALUSERTYPE.message);
			}
		} else {
			res.put("result", ResultStateEnum.EXCEPTION.state);
			res.put("errmsg", "文件上传出错");
		}
		return res;
	}

	/**
	 * 修改用户信息
	 *
	 * @param request
	 * @return
	 * @author xuxxtr
	 */
	public Map<String, Object> modifyUser(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> userinfo = new HashMap<>();
		String usertoken = request.getParameter("usertoken");
		String nickname = request.getParameter("nickname");
		if (StringUtils.isBlank(usertoken) || StringUtils.isBlank(nickname)) {
			result.put("result", ResultStateEnum.PARAMUNVALIABLE.state);
			result.put("errmsg", ResultStateEnum.PARAMUNVALIABLE.state);
			return result;
		}
		String account = Const.getUserInfo(usertoken).get("account");
		userinfo.put("usertoken", usertoken);
		userinfo.put("nickname", nickname);
		if (isOrgUser(usertoken)) {
			// 机构用户
			OrgUser orguser = partnerUseCarDao.getUser4Org(account);
			if (orguser != null) {
				userinfo.put("userid", orguser.getId());
				partnerUseCarDao.updateUser4Org(userinfo);
				result.put("result", ResultStateEnum.OK.state);
			} else {
				result.put("result", ResultStateEnum.USERNOTEXIST.state);
				result.put("errmsg", ResultStateEnum.USERNOTEXIST.message);
			}
		} else {
			result.put("result", ResultStateEnum.ILLEGALUSERTYPE.state);
			result.put("errmsg", ResultStateEnum.ILLEGALUSERTYPE.message);
		}
		return result;
	}

	/**
	 * 应用授权认证
	 *
	 * @param request
	 * @return
	 * @author xuxxtr
	 */
	public Map<String, Object> auth(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<>();
		Map<String,String> data=new HashMap<>(1);
		String channelKey = request.getParameter("channelKey");
		String requestType = request.getParameter("requestType");
		String personType = request.getParameter("personType");
		if (StringUtils.isBlank(requestType) || StringUtils.isBlank(personType) || StringUtils.isBlank(channelKey)) {
			result.put("result", ResultStateEnum.PARAMUNVALIABLE.state);
			result.put("errmsg", ResultStateEnum.PARAMUNVALIABLE.message);
			data.put("token", null);
			result.put("data", data);
			return result;
		}
		// 根据channelKey获取token
		Map<String, Object> authInfo = partnerUseCarDao.getAuthInfoByKey(channelKey);
		if (authInfo == null || authInfo.isEmpty()) {
			result.put("result", ResultStateEnum.NOAUTHINFO.state);
			result.put("errmsg", ResultStateEnum.NOAUTHINFO.message);
			data.put("token", null);
			result.put("data", data);
		} else {
			result.put("token", InterfaceTokenManager.createToken(channelKey, SystemConfig.getSystemProperty("securityKey")));
			result.put("channelkey", channelKey);
			result.put("overtime", SystemConfig.getSystemProperty("tokenovertime"));
			// 设置过期时间
			partnerUseCarDao.addAuthTimeliness(result);
			result.remove("overtime");
			result.remove("channelkey");
			result.put("result", ResultStateEnum.OK.state);
			data.put("token", result.get("token").toString());
			result.put("data", data);
			result.remove("token");
		}
		return result;
	}


	/**
	 * 授权时效延长
	 *
	 * @param request
	 * @return
	 * @author xuxxtr
	 */
	public Map<String, Object> addAuthTimeLiness(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<>();
		//String channelId = request.getParameter("channelId");
		String token = request.getParameter("token");
		Map<String, Object> params = new HashMap<>();

		if (StringUtils.isBlank(token)) {
			result.put("result", ResultStateEnum.PARAMUNVALIABLE.state);
			result.put("errmsg", ResultStateEnum.PARAMUNVALIABLE.message);
		} else {
			params.put("channelkey", InterfaceTokenManager.getChannelKeyFromToken(token));
			params.put("overtime", SystemConfig.getSystemProperty("peradd_tokenovertime"));
			//params.put("token", token);
			partnerUseCarDao.addAuthTimeliness(params);
			result.put("result", "0");
		}
		return result;
	}

	/**
	 * 获取用户详细信息
	 *
	 * @param request
	 * @return
	 */
	public Map<String, Object> getUserDetail(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String usertoken = request.getParameter("usertoken");
		if (StringUtils.isBlank(usertoken)) {
			result.put("result", ResultStateEnum.PARAMUNVALIABLE.state);
			result.put("errmsg", ResultStateEnum.PARAMUNVALIABLE.message);
			result.put("data", null);
			return result;
		}
		String account = Const.getUserInfo(usertoken).get("account");
		if (isOrgUser(usertoken)) {
			// 机构用户
			OrgUser userinfo = partnerUseCarDao.getUser4Org(account);
			if (userinfo != null) {
				UserDetailDTO userDto = new UserDetailDTO();
				String img = userinfo.getHeadPortraitMin();
				if (StringUtils.isNotBlank(img)) {
					userDto.setImgPath(SystemConfig.getSystemProperty("fileserver") + File.separator + img);
				} else {
					userDto.setImgPath("");
				}
				userDto.setNickname(userinfo.getNickName());
				userDto.setPhone(userinfo.getAccount());
				result.put("result", ResultStateEnum.OK.state);
				result.put("data", userDto);
			}else{
				result.put("result", ResultStateEnum.USERNOTEXIST.state);
				result.put("errmsg", ResultStateEnum.USERNOTEXIST.message);
				result.put("data", null);
			}
		} else {
			result.put("result", ResultStateEnum.ILLEGALUSERTYPE.state);
			result.put("errmsg", ResultStateEnum.ILLEGALUSERTYPE.message);
			result.put("data", null);
		}
		return result;
	}


	/**
	 * 获取登录验证码
	 *
	 * @param request
	 * @return
	 * @author xuxxtr
	 */
	public Map<String, Object> getCaptcha(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String phone = request.getParameter("phone");
		String type = request.getParameter("type");
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(type)) {
			result.put("result", ResultStateEnum.PARAMUNVALIABLE.state);
			result.put("errmsg", ResultStateEnum.PARAMUNVALIABLE.message);
			return result;
		}
		// 获取一串随机的短信验证码
		String smscode = SMSCodeUtil.getRandCode();
		Map<String, String> smscodeobj = new HashMap<String, String>();
		smscodeobj.put("smstype", type);
		smscodeobj.put("usertype", "0");
		smscodeobj.put("username", phone);
		smscodeobj.put("smscode", smscode);
		if (partnerUseCarDao.hasSMSCode(smscodeobj)) {
			partnerUseCarDao.updateSMSCode(smscodeobj);
		} else {
			smscodeobj.put("id", GUIDGenerator.newGUID());
			partnerUseCarDao.saveSMSCode(smscodeobj);
		}
		// 发送短信到手机
		String content = SMSTempPropertyConfigurer.getSMSTemplate(
				"com.szyciov.passenger.service.passengerservice.smscode", smscode, PlatformType.PASSENGER.msg);
		List<String> userids = new ArrayList<String>();
		userids.add(phone);
		UserMessage usermessage = new UserMessage(userids, content, UserMessage.GETSMSCODE);
		MessageUtil.sendMessage(usermessage);
		result.put("result", ResultStateEnum.OK.state);
		return result;
	}

	/**
	 * 用户登录
	 * 需先判断用户是否已经注册，若未注册，则先注册后在进行登录;对新注册用户同意分配渠道部门,若不存在，则创建该部门。
	 * @param request
	 * @return
	 * @author xuxxtr
	 * @throws NoSuchAlgorithmException
	 */
	public Map<String, Object> login(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String,String> data=new HashMap<>(1);
		String phone = request.getParameter("phone");
		String type = request.getParameter("type");
		String validateCode = request.getParameter("validateCode");
		String token=request.getParameter("token");
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(type) || StringUtils.isBlank(validateCode) || StringUtils.isBlank(token)) {
			result.put("result", ResultStateEnum.PARAMUNVALIABLE.state);
			result.put("errmsg", ResultStateEnum.PARAMUNVALIABLE.message);
			data.put("usertoken", null);
			result.put("data",data);
			return result;
		}
		String channelkey=InterfaceTokenManager.getChannelKeyFromToken(token);
		//检查是否在该机构下已经注册
		if(!partnerUseCarDao.checkRegister4Org(phone,channelkey)){
			register4Org(phone,channelkey);
		}
		LoginParam loginParam = new LoginParam();
		loginParam.setPhone(phone);
		loginParam.setLogintype(type);
		loginParam.setValidatecode(validateCode);
		// 登录操作
		result = login4Org(loginParam);
		return result;
	}

	private void register4Org(String phone, String channelkey) throws Exception {
		OrgUser user=new OrgUser();
		String password = UNID.getUNID(null, 6);
		String userID=GUIDGenerator.newGUID();
		user.setId(userID);
		Map<String,Object> chInfo=partnerUseCarDao.getChInfoByKey(channelkey);
		if(chInfo!=null && chInfo.get("organid")!=null)
			user.setOrganId(chInfo.get("organid").toString());
		user.setAccount(phone);
		user.setCustomerType(CustomerType.CHANNELCUSTOMER.type);
		user.setUserType("0");
		user.setSpecialState("0");
		user.setUserPassword(PasswordEncoder.encode(password));
		List<TreeNode> deptNodes=partnerUseCarDao.getDeptTreeNodes(chInfo.get("organid").toString());
		//是否存在渠道客户部门，不存在则添加
		for(TreeNode t:deptNodes){
			if(t.getName().indexOf("渠道")>=0){
				user.setDpetId(t.getId());
				break;
			}
		}
		//姓曾渠道客户部门
		if(user.getDpetId()==null){
			String parentresid = PYTools.getFirstPinYin(chInfo.get("companyname").toString());
			String resid = getUniqueResId(parentresid);
			Map<String,Object> deptInfo=new HashMap<>();
			deptInfo.put("resid", resid);
			deptInfo.put("id", GUIDGenerator.newGUID());
			deptInfo.put("parentid", null);
			deptInfo.put("level", "1");
			deptInfo.put("organid", chInfo.get("organid").toString());
			deptInfo.put("deptname", "渠道");
			deptInfo.put("creater", userID);
			partnerUseCarDao.addDept(deptInfo);
			user.setDpetId(deptInfo.get("id").toString());
		}
		//注册渠道机构用户
		partnerUseCarDao.registerOrgUser(user);
		List<String> list=new ArrayList<>(1);
		list.add(user.getAccount());
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.orgorganservice.addorguser", user.getAccount(),password);
		UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
		MessageUtil.sendMessage(um);
	}

	/**
	 * 获取部门唯一编码
	 * @param parentresid
	 * @return
	 */
	private String getUniqueResId(String parentresid){
		if(parentresid==null||"".equalsIgnoreCase(parentresid.trim())){
			throw new RuntimeException("上级资源id为空,请先为上级添加一个唯一资源Id!");
		}
		String resid = parentresid+SEPARATOR+UNID.getUNID();
		int count = 0;
		while(hasResId(resid)){
			if(count>10){
				//10次还未创建成功就报错
				throw new RuntimeException("创建更新部门出错,部门资源id创建出问题!");
			}
			resid = parentresid+SEPARATOR+UNID.getUNID();
			count++;
		}
		return resid;
	}
	
	
	/**
	 * 判断公司的resid在数据库中是否已经存在
	 * @param resid
	 * @return
	 */
	private boolean hasResId(String resid){
		return partnerUseCarDao.hasResId(resid);
	}
	
	/**
	 * 是否是机构用户
	 *
	 * @param usertoken
	 * @return
	 * @author xuxxtr
	 */
	private boolean isOrgUser(String usertoken) {
		return UserTokenManager.ORGUSERTYPE.equals(Const.getUserInfo(usertoken).get("usertype"));
	}

	/**
	 * 机构用户登录
	 *
	 * @param loginParam
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private Map<String, Object> login4Org(LoginParam loginParam) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String,String> data=new HashMap<>(1);
		// 登录日志信息
		Map<String, Object> loginfo = new HashMap<>();
		String account = loginParam.getPhone();
		OrgUser orguser = partnerUseCarDao.getUser4Org(account);
		
		loginfo.put("device", "3");// 添加行的设备方式
		loginfo.put("id", GUIDGenerator.newGUID());
		if (orguser == null) {
			loginfo.put("loginstatus", Const.LOGINSTATUS_ERROR);
			// 添加日志
			partnerUseCarDao.addLog4Org(loginfo);
			// 机构用户不存在
			res.put("result", ResultStateEnum.USERNOTEXIST.state);
			res.put("errmsg", ResultStateEnum.USERNOTEXIST.message);
			data.put("usertoken", null);
			res.put("data", data);
			return res;
		}
		loginfo.put("userid", orguser.getId());
		if (Const.PWDLOGIN.equalsIgnoreCase(loginParam.getLogintype())) {
			// 密码登录判断
			String dbpwd = orguser.getUserPassword();
			if (!StringUtils.isNotBlank(dbpwd) || !PasswordEncoder.matches(loginParam.getValidatecode(), dbpwd)) {
				// 密码错误
				res.put("result", ResultStateEnum.PASSWORDWRONG.state);
				res.put("errmsg", ResultStateEnum.PASSWORDWRONG.message);
				data.put("usertoken", null);
				res.put("data", data);
				loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
			} else {
				res.put("result", ResultStateEnum.OK.state);
				addUserInfo(res, "", orguser,data);
				loginfo.put("loginstatus", Const.LOGINSTATUS_OK);
			}
		} else {
			// 验证码登录
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("phone", loginParam.getPhone());
			params.put("usertype", Const.USERTOKENTYPE_ORGUSER);
			params.put("smstype", Const.SMSTYPE_LOGIN);
			// 验证码登录判断
			Map<String, Object> smsobj = partnerUseCarDao.getSMSInfo(params);
			if (smsobj == null) {
				res.put("result", ResultStateEnum.SMSCODEINVALID.state);
				res.put("errmsg", ResultStateEnum.SMSCODEINVALID.message);
				data.put("usertoken", null);
				res.put("data", data);
				loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
			} else {
				String smscode = (String) smsobj.get("smscode");
				Date savetime = (Date) smsobj.get("updatetime");
				Date temptime = new Date(savetime.getTime() + (long) Const.SMSCODEVALITIME * 60 * 1000);
				Date currentime = new Date();
				if (smscode == null || !smscode.equals(loginParam.getValidatecode())) {
					res.put("result", ResultStateEnum.SMSCODEINVALID.state);
					res.put("errmsg", ResultStateEnum.SMSCODEINVALID.message);
					data.put("usertoken", null);
					res.put("data", data);
					loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
				} else if (currentime.after(temptime)) {
					res.put("result", ResultStateEnum.SMSCODEOUTTIME.state);
					res.put("errmsg", ResultStateEnum.SMSCODEOUTTIME.message);
					data.put("usertoken", null);
					res.put("data", data);
					loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
				} else {
					res.put("result", Retcode.OK.code);
					addUserInfo(res, "", orguser,data);
					deleteSMSCode(loginParam.getPhone(), Const.USERTOKENTYPE_ORGUSER, Const.SMSTYPE_LOGIN);
					loginfo.put("loginstatus", Const.LOGINSTATUS_OK);
				}
			}
		}
		// 记录日志
		partnerUseCarDao.addLog4Org(loginfo);
		return res;
	}

	private void addUserInfo(Map<String, Object> res, String uuid, OrgUser orguser,Map<String,String> data) throws Exception {
		String usertoken = UserTokenManager.createUserToken(UserTokenManager.ORGUSERTYPE, orguser.getAccount(),
				SystemConfig.getSystemProperty("securityKey"));
		Map<String, Object> pp = new HashMap<String, Object>();
		pp.put("userid", orguser.getId());
		pp.put("usertype", Const.USERTOKENTYPE_ORGUSER);
		Map<String, Object> dbusertokeninfo = partnerUseCarDao.getUserTokenByUserId(pp);
		Map<String, Object> tokeninfo = new HashMap<String, Object>();
		if (dbusertokeninfo == null) {
			tokeninfo.put("id", GUIDGenerator.newGUID());
		}
		tokeninfo.put("usertoken", usertoken);
		tokeninfo.put("userid", orguser.getId());
		tokeninfo.put("usertype", Const.USERTOKENTYPE_ORGUSER);
		tokeninfo.put("uuid", uuid);
		partnerUseCarDao.createOrUpdateUsertoken(tokeninfo);
		data.put("usertoken", usertoken);
		res.put("data", data);
		// 挤下线,不存在，token会失效
	}

	/**
	 * 验证码使用完成后删除
	 *
	 * @param phone
	 * @param usertype
	 * @param smstype
	 */
	private void deleteSMSCode(String phone, String usertype, String smstype) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		params.put("usertype", usertype);
		params.put("smstype", smstype);
		partnerUseCarDao.deleteSMSCode(params);
	}

}
