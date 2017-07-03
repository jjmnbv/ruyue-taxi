package com.szyciov.operate.service;

import com.szyciov.driver.entity.OrderInfoMessage;
import com.szyciov.driver.enums.ReviewState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.OrderMessageFactory;
import com.szyciov.entity.OrderMessageFactory.OrderMessageType;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserNews;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.message.OrderMessage;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpDriverchanges;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpOrderReview;
import com.szyciov.op.entity.OpOrdercomment;
import com.szyciov.op.entity.OpSendrecord;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.operate.dao.OrderManageDao;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.latlon.LatLonUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("OrderManageService")
public class OrderManageService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	private OrderManageDao orderManageDao;
	@Resource(name = "OrderManageDao")
	public void setOrderManageDao(OrderManageDao orderManageDao) {
		this.orderManageDao = orderManageDao;
	}
	
	public List<Map<String, String>> getSendRulesByName(String cityName) {
		return orderManageDao.getSendRulesByName(cityName);
	}
	
	public PageBean getOpOrderByQuery(OrderManageQueryParam queryParam) {
		String minUseTime = queryParam.getMinUseTime();
		if(StringUtils.isNotBlank(minUseTime)) {
			queryParam.setMinUseTime(minUseTime + ":00");
		}
		String maxUseTime = queryParam.getMaxUseTime();
		if(StringUtils.isNotBlank(maxUseTime)) {
			queryParam.setMaxUseTime(maxUseTime + ":59");
		}
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = null;
		int iTotalRecords = 0;
		switch (Integer.valueOf(queryParam.getType()).intValue()) {
			case 1:
				list = getOpLabourOrderListByQuery(queryParam);
				iTotalRecords = getOpLabourOrderCountByQuery(queryParam);
				break;
			case 2:
				list = getOpCurrentOrderListByQuery(queryParam);
				iTotalRecords = getOpCurrentOrderCountByQuery(queryParam);
				break;
			case 3:
				list = getOpAbnormalOrderListByQuery(queryParam);
				iTotalRecords = getOpAbnormalOrderCountByQuery(queryParam);
				break;
			case 4:
				list = getOpWasabnormalOrderListByQuery(queryParam);
				iTotalRecords = getOpWasabnormalOrderCountByQuery(queryParam);
				break;
			case 5:
				list = getOpCompleteOrderListByQuery(queryParam);
				iTotalRecords = getOpCompleteOrderCountByQuery(queryParam);
				break;
			case 6:
				list = getOpWaitgatheringOrderListByQuery(queryParam);
				iTotalRecords = getOpWaitgatheringOrderCountByQuery(queryParam);
				break;
		}
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public Map<String, Object> cancelOpOrder(String orderno) {
		Map<String, Object> resultMap = new HashMap<>();
		
		OpOrder order = getOpOrder(orderno);
		//当订单状态为已出发、已抵达、接到乘客时，将司机修改为空闲状态
		String orderstatus = order.getOrderstatus();
		if("3".equals(orderstatus) || "4".equals(orderstatus) || "5".equals(orderstatus)) {
			PubDriver driver = new PubDriver();
			driver.setId(order.getDriverid());
			driver.setWorkStatus("0");
			updatePubDriverWorkstatus(driver);
		}
		
		int result = orderManageDao.cancelOpOrder(orderno);
		order = getOpOrder(orderno);
		
		OrderMessage message = new OrderMessage(order, OrderMessage.CANCELORDER);
		MessageUtil.sendMessage(message);
		
		if(result == 1) {
			resultMap.put("status", "success");
			resultMap.put("message", "取消成功");
		} else {
			resultMap.put("status", "fail");
			resultMap.put("message", "取消失败");
		}
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	public PageBean getDriverByQuery(OrderManageQueryParam queryParam) {
		OpOrder order = getOpOrder(queryParam.getOrderNo());
		queryParam.setUserId(order.getUserid());
		if(queryParam.getDistance() > 0) {
			double[] around = LatLonUtil.getAround(queryParam.getOrderLat(), queryParam.getOrderLon(), queryParam.getDistance());
			queryParam.setMinLat(around[0]);
			queryParam.setMinLon(around[1]);
			queryParam.setMaxLat(around[2]);
			queryParam.setMaxLon(around[3]);
		}
		
		//判断是否可以升级车型
		String models = "0";
        PubSendRules sendRules = new PubSendRules();
		sendRules.setCity(queryParam.getOncity());
		List<PubSendRules> sendRulesList = orderManageDao.getSendRulesList(sendRules);
		if(null != sendRulesList && !sendRulesList.isEmpty()) {
			models = sendRulesList.get(0).getVehicleUpgrade() + "";
		} else {
			models = "0";
		}
		queryParam.setModels(models);
		
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getDriverListByQuery(queryParam);
		
		if(null != list && !list.isEmpty()) {
			Iterator<Map<String, Object>> ite = list.iterator();
			while(ite.hasNext()) {
				Map<String, Object> map = ite.next();
				
				BaiduApiQueryParam baiduparam = new BaiduApiQueryParam();
				baiduparam.setOrderStartLat(queryParam.getOrderLat());
				baiduparam.setOrderStartLng(queryParam.getOrderLon());
				if(null != map.get("lat")) {
					baiduparam.setOrderEndLat((double) map.get("lat"));
				} else {
					baiduparam.setOrderEndLat(0);
				}
				if(null != map.get("lng")) {
					baiduparam.setOrderEndLng((double) map.get("lng"));
				} else {
					baiduparam.setOrderEndLng(0);
				}
				
				Map<String,Object> hintMap = templateHelper.dealRequestWithFullUrlToken(
						SystemConfig.getSystemProperty("carserviceApi") +"/BaiduApi/GetMileageInfo", 
						HttpMethod.POST, null, baiduparam, Map.class);
				
				if(hintMap != null && (Integer)hintMap.get("status") == Retcode.OK.code){
					map.put("distance", hintMap.get("distance"));
					map.put("duration", hintMap.get("duration"));
				} else {
					map.put("distance", 0);
					map.put("duration", 0);
				}
				
			}
		}
		
		int iTotalRecords = getDriverCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

    /**
     * 人工派单
     * 已于2017/05/26移动到carservice-api中
     * 访问路径api/OrderManage/ManualSendOpOrder
     * @param object o
     * @return r
     */
	@Deprecated
	public Map<String, Object> manualSendOrder(OpOrder object) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			OpOrder order = getOpOrder(object.getOrderno());
			if ("8".equals(order.getOrderstatus())) {
				resultMap.put("status", "fail");
				resultMap.put("message", "人工派单超时，订单已取消");
				return resultMap;
			}
			if(!"1".equals(order.getOrderstatus())) {
				resultMap.put("status", "fail");
				resultMap.put("message", "该订单已被其他客服处理");
				return resultMap;
			}
			
			//查询计费规则
			Map<String, Object> pricecopyMap = new HashMap<String, Object>();
			pricecopyMap.put("city", order.getOncity());
			pricecopyMap.put("rulestype", order.getOrdertype());
			//按下单车型计费
			String chargemodel = object.getPricecopy();
			if(chargemodel.equals("0")) {
				object.setPricemodel(order.getSelectedmodel());
				pricecopyMap.put("cartype", order.getSelectedmodel());
				object.setPricecopy(order.getPricecopy());
			} else {
				object.setPricemodel(object.getFactmodel());
				pricecopyMap.put("cartype", object.getFactmodel());
				object.setPricecopy(findModelPriceByModels(pricecopyMap, order.getPricecopy()));
			}
			
			if(StringUtils.isBlank(object.getPricecopy())) {
				resultMap.put("status", "fail");
				resultMap.put("message", "所指派服务车型计费规则不存在，建议选择“按下单车型计费”或指派其他司机");
				return resultMap;
			}
			
			//判断该订单是即可订单还是预约订单
			Boolean isusenow = getOrderIsUseNow(order);
			if(null == isusenow) {
				object.setIsusenow(order.isIsusenow());
			} else {
				object.setIsusenow(isusenow);
			}
			
			//修改订单状态
			int orderResult = orderManageDao.manualSendOrder(object);
			PubDriver driver = orderManageDao.getPubDriver(order.getDriverid());
			//更新车辆基本信息
			updateOpOrderVehicleInfo(object.getOrderno(),driver.getBelongleasecompany());
			
			//添加人工派单记录信息
			OpSendrecord sendrecord = new OpSendrecord();
			sendrecord.setId(GUIDGenerator.newGUID());
			sendrecord.setOrderno(object.getOrderno());
			sendrecord.setOperator(object.getOperator());
			sendrecord.setChargemodel(chargemodel);
			sendrecord.setReason(object.getOrderreason());
			sendrecord.setDriverid(object.getDriverid());
			int recordRsult = insertOpSendrecord(sendrecord);
			
			//添加司机消息
			order = getOpOrder(object.getOrderno());
			createDriverNews(order, 0, OrderMessageType.MANTICORDER);
			
			if(orderResult == 1 && recordRsult == 1) {
				OrderMessage message = new OrderMessage(order, OrderMessage.MANTICORDER);
				MessageUtil.sendMessage(message);
				
				resultMap.put("status", "success");
				resultMap.put("message", "派单成功");
			} else {
				resultMap.put("status", "fail");
				resultMap.put("message", "派单失败");
			}
		} catch (Exception e) {
			resultMap.put("status", "fail");
			resultMap.put("message", "派单失败");
			resultMap.put("exmessage", e.getMessage());
		}
		return resultMap;
	}
	
	public Map<String, Object> changeOpDriver(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String orderno = (String) params.get("orderno");
			//查询订单详情
			OpOrder order = getOpOrder(orderno);
			
			if(order.getOrderstatus().equals("6") || order.getOrderstatus().equals("7") || order.getOrderstatus().equals("8")) {
				resultMap.put("status", "fail");
				resultMap.put("message", "当前订单状态无法更换司机");
				return resultMap;
			}
			
			Date currentTime = new Date();
			String chargemodel = params.get("pricecopy").toString();
			String newdriverid = params.get("newdriverid").toString();
			String modelsId = params.get("modelsid").toString();
			//根据车型查询计费规则
			Map<String, Object> pricecopyMap = new HashMap<String, Object>();
			pricecopyMap.put("city", order.getOncity());
			pricecopyMap.put("rulestype", order.getOrdertype());
			//按下单车型计费
			if(chargemodel.equals("0")) {
				params.put("pricemodel", order.getSelectedmodel());
				params.put("factmodel", modelsId);
				pricecopyMap.put("cartype", order.getSelectedmodel());
				params.put("pricecopy", order.getPricecopy());
			} else {
				params.put("pricemodel", modelsId);
				params.put("factmodel", modelsId);
				pricecopyMap.put("cartype", modelsId);
				params.put("pricecopy", findModelPriceByModels(pricecopyMap, order.getPricecopy()));
			}
			
			if(null == params.get("pricecopy")) {
				resultMap.put("status", "fail");
				resultMap.put("message", "所指派服务车型计费规则不存在，建议选择“按下单车型计费”或指派其他司机");
				return resultMap;
			}
			
			//判断该订单是即可订单还是预约订单
			Boolean isusenow = getOrderIsUseNow(order);
			if(null == isusenow) {
				params.put("isusenow", order.isIsusenow());
			} else {
				params.put("isusenow", isusenow);
			}
			
			//添加更换司机记录
			OpDriverchanges driverchanges = new OpDriverchanges();
			driverchanges.setId(GUIDGenerator.newGUID());
			driverchanges.setOrderno(orderno);
			driverchanges.setBeforedriverid(order.getDriverid());
			driverchanges.setAfterdriverid(newdriverid);
			driverchanges.setReason(params.get("orderreason").toString());
			driverchanges.setSendtime(currentTime);
			driverchanges.setUpdatetime(currentTime);
			driverchanges.setCreatetime(currentTime);
			driverchanges.setOperator(params.get("operator").toString());
			driverchanges.setStatus(1);
			driverchanges.setChargemodel(chargemodel);
			insertOpDriverchanges(driverchanges);
			//修改订单状态
			int result = orderManageDao.changeOpDriver(params);
			PubDriver driver = orderManageDao.getPubDriver(order.getDriverid());
			//更新车辆基本信息
			updateOpOrderVehicleInfo(orderno,driver.getBelongleasecompany());
			
			//如果订单状态为已出发、已抵达、接到乘客，将司机修改为空闲状态
			String orderstatus = order.getOrderstatus();
			if("3".equals(orderstatus) || "4".equals(orderstatus) || "5".equals(orderstatus)) {
				driver.setWorkStatus("0");
				updatePubDriverWorkstatus(driver);
			}
			
			//发送司机消息
			order = getOpOrder(orderno);
			createDriverNews(order, 0, OrderMessageType.MANTICORDER);
			
			if(result == 1) {
				OrderMessage message = new OrderMessage(order, OrderMessage.CHAGEDRIVER, params);
				MessageUtil.sendMessage(message);
				
				resultMap.put("status", "success");
				resultMap.put("message", "操作成功");
			} else {
				resultMap.put("status", "fail");
				resultMap.put("message", "操作失败");
			}
		} catch (Exception e) {
			resultMap.put("status", "fail");
			resultMap.put("message", "操作失败");
		}
		
		return resultMap;
	}
	
	public Map<String, Object> applyRecheckOrder(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OpOrder order = getOpOrder((String) params.get("orderno"));
		if (!"7".equals(order.getOrderstatus()) || "1".equals(order.getReviewstatus()) || "1".equals(order.getPaymentstatus())) {
			resultMap.put("status", "fail");
			resultMap.put("message", "当前订单状态不允许申请复核");
			return resultMap;
		}
		try {
			int result = orderManageDao.applyRecheckOrder(params);
			
			if(result == 1) {
				resultMap.put("status", "success");
				resultMap.put("message", "操作成功");
			} else {
				resultMap.put("status", "fail");
				resultMap.put("message", "操作失败");
			}
		} catch (Exception e) {
			resultMap.put("status", "fail");
			resultMap.put("message", "操作失败");
		}
		
		return resultMap;
	}
	
	public PageBean getOpChangeDriverByQuery(OrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getOpChangeDriverListByQuery(queryParam);
		int iTotalRecords = getOpChangeDriverCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public PageBean getOpOrderReviewByQuery(OrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getOpOrderReviewListByQuery(queryParam);
		int iTotalRecords = getOpOrderReviewCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public Map<String, Object> opOrderReject(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<>();

		OpOrder opOrder = new OpOrder();
		String orderno = (String) params.get("orderno");
		opOrder.setOrderno(orderno);
		opOrder.setReviewstatus(ReviewState.NOTREVIEW.state);
		
		orderManageDao.orderReject(opOrder);
		
		OpOrder order = getOpOrder(orderno);
		
		//添加司机消息
		if(order.getReviewperson().equals("1")) {
			createDriverNews(order, 0, OrderMessageType.REVIEWORDER);
		}
		
		OrderMessage message = new OrderMessage(order, OrderMessage.NOTREVIEW);
		MessageUtil.sendMessage(message);
		
		resultMap.put("status", "success");
		resultMap.put("message", "操作成功");
		
		return resultMap;
	}
	
	public OpAccountrules getOrderAccountRulesByOrderno(Map<String, Object> params) {
		List<OpAccountrules> accountruleList = orderManageDao.findModelPriceByModels(params);
		if(null != accountruleList && !accountruleList.isEmpty()) {
			return accountruleList.get(0);
		}
		return null;
	}
	
	public Map<String, Object> opOrderReview(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<>();
		
		String orderno = (String) params.get("orderno");
		Double mileage = Double.valueOf(params.get("mileage").toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		
		OpOrder cOrder = getOpOrder(orderno);
		//判断当前订单状态
		if(!"1".equals(cOrder.getReviewstatus()) || "1".equals(cOrder.getPaymentstatus())) {
			resultMap.put("status", "fail");
			resultMap.put("message", "当前订单状态无法复核");
			return resultMap;
		}
		
		try {
			Date starttime = sdf.parse(params.get("starttime").toString());
			Date endtime = sdf.parse(params.get("endtime").toString());
			double counttimes = 0;
			if(StringUtils.isNotBlank(params.get("counttimes").toString())) {
				counttimes = Double.valueOf(params.get("counttimes").toString());
			}
			String reviewperson = (String) params.get("reviewperson");
			String reason = (String) params.get("opinion");
			String operator = (String) params.get("operator");
            int times = (int) Math.ceil((endtime.getTime() - starttime.getTime()) / 1000.0D / 60.0D);

			OpOrder order = getOpOrder(orderno);
            String pricecopy = order.getPricecopy();
            if(StringUtils.isBlank(pricecopy)) {
                resultMap.put("status", "fail");
                resultMap.put("message", "操作失败");
                return resultMap;
            }
            OrderCost orderCost = StringUtil.parseJSONToBean(pricecopy, OrderCost.class);
            int slowtimes = orderCost.getSlowtimes();
            orderCost.setMileage(mileage * 1000);
            orderCost.setTimes(times * 60);
            orderCost.setSlowtimes((int) counttimes);
            orderCost = StringUtil.countCost(orderCost, starttime, false);
            StringUtil.formatOrderCost2Int(orderCost);

			//添加复核记录
			OpOrderReview orderReview = new OpOrderReview();
			orderReview.setId(GUIDGenerator.newGUID());
			orderReview.setOrderno(orderno);
			orderReview.setTimesubsidies(orderCost.getTimecost());
			orderReview.setMileage(formatDouble(mileage * 1000, 1));
			orderReview.setTimes(formatDouble(times * 60, 0));
			orderReview.setStarttime(starttime);
			orderReview.setEndtime(endtime);
			orderReview.setCounttimes(formatDouble(counttimes, 0));
			orderReview.setMileageprices(formatDouble(orderCost.getRangecost(), 1));
			orderReview.setReviewperson(reviewperson);
			orderReview.setReason(cOrder.getOrderreason());
			orderReview.setOpinion(reason);
			orderReview.setOperator(operator);
			orderReview.setStatus(1);
			orderReview.setReviewedprice(orderCost.getCost());
            orderReview.setPricecopy(JSONObject.fromObject(orderCost).toString());
			//原服务数据
			orderReview.setRawstarttime(order.getStarttime());
			orderReview.setRawendtime(order.getEndtime());
			orderReview.setRawmileage(order.getMileage());
			orderReview.setRawtimes(counttimes);
			
			order.setReviewstatus(ReviewState.REVIEWED.state);
			
			//上次订单实际支付金额
			Double actualpayamount = order.getActualpayamount();
			if(null == actualpayamount) {
				actualpayamount = order.getOrderamount();
			}
			double price = orderCost.getCost();
			//实际金额小于上次实际支付金额时修改订单实付金额
			if(price < actualpayamount) {
				order.setActualpayamount(price);
			}
			order.setShouldpayamount(price);
			
			//本次复核的差异金额,大于0时需退款给乘客
			double differPrice = actualpayamount - price;
			//只有未支付才修改订单数据
			if(StringUtils.isBlank(order.getPaymentstatus()) || order.getPaymentstatus().equals("0")) {
				order.setOrderamount(orderReview.getReviewedprice());
				order.setActualpayamount(orderReview.getReviewedprice());
				order.setMileage(formatDouble(mileage * 1000, 1));
				order.setStarttime(starttime);
				order.setEndtime(endtime);
				order.setPricecopy(JSONObject.fromObject(orderCost).toString());
			}
			//查询最近一次复核记录
			OpOrderReview review = orderManageDao.getOpOrderreviewLastByOrderno(orderno);
			if(null != review) {
				orderReview.setRawstarttime(review.getStarttime());
				orderReview.setRawendtime(review.getEndtime());
				orderReview.setRawtimes(review.getCounttimes());
				orderReview.setRawmileage(review.getMileage());
				orderReview.setRaworderamount(review.getReviewedprice());
                orderReview.setRawpricecopy(review.getPricecopy());
			} else {
				orderReview.setRaworderamount(order.getOriginalorderamount());
                orderReview.setRawpricecopy(pricecopy);
			}
			//修改订单数据
			orderReview.setPrice(StringUtil.formatNum(order.getShouldpayamount() - order.getActualpayamount(), 1));
			int result = applyOpOrderReview(orderReview);
			result += orderManageDao.updateOrderState(order);
			
			//发送短信数据
			order.setOrderamount(orderReview.getReviewedprice());
			order.setMileage(formatDouble(mileage * 1000, 1));
			order.setStarttime(starttime);
			order.setEndtime(endtime);
			order.setPricecopy(JSONObject.fromObject(orderCost).toString());
			
			//添加司机消息类
			if("1".equals(orderReview.getReviewperson())) {
				createDriverNews(order, differPrice, OrderMessageType.REVIEWORDER);
			}
			
			//已支付且乘客多给钱才需要添加退款信息
			if(order.getPaymentstatus().equals("1") && differPrice > 0) {
				//添加退款信息
				PeUserRefund userRefund = new PeUserRefund();
				userRefund.setId(GUIDGenerator.newGUID());
				userRefund.setUserId(order.getUserid());
				userRefund.setOrderNo(orderno);
				userRefund.setAmount(BigDecimal.valueOf(differPrice));
				userRefund.setRefundStatus("0");
				userRefund.setRemark(orderReview.getOpinion());
				userRefund.setCreater(operator);
				userRefund.setUpdater(operator);
				userRefund.setStatus(1);
				insertPeUserRefund(userRefund);
			}
			//复核短信额外参数
			Map<String, Object> smsParams = new HashMap<String, Object>();
			smsParams.put("reviewperson", orderReview.getReviewperson());
			smsParams.put("price", differPrice);
			OrderMessage message = new OrderMessage(order, OrderMessage.EXCEPTIONORDER, smsParams);
			MessageUtil.sendMessage(message);
			
			if(result == 2) {
				resultMap.put("status", "success");
				resultMap.put("message", "操作成功");
			} else {
				resultMap.put("status", "fail");
				resultMap.put("message", "操作失败");
			}
		} catch (Exception e) {
			resultMap.put("status", "fail");
			resultMap.put("message", "操作失败");
		}
		return resultMap;
	}
	
	/**
	 * 订单导出
	 * @param queryParam
	 * @return
	 */
	public List<Map<String, Object>> exportOrder(OrderManageQueryParam queryParam) {
		//初始化参数
		queryParam.setiDisplayStart(0);
		queryParam.setiDisplayLength(60000);
		String minUseTime = queryParam.getMinUseTime();
		if (StringUtils.isNotBlank(minUseTime)) {
			queryParam.setMinUseTime(minUseTime + ":00");
		}
		String maxUseTime = queryParam.getMaxUseTime();
		if (StringUtils.isNotBlank(maxUseTime)) {
			queryParam.setMaxUseTime(maxUseTime + ":59");
		}
		//查询需要导出的订单数据
		List<Map<String, Object>> list = null;
		switch (Integer.valueOf(queryParam.getType()).intValue()) {
			case 3:
				list = getOpAbnormalOrderListByQuery(queryParam);
				break;
			case 4:
				list = getOpWasabnormalOrderListByQuery(queryParam);
				break;
			case 5:
				list = getOpCompleteOrderListByQuery(queryParam);
				break;
			case 6:
				list = getOpWaitgatheringOrderListByQuery(queryParam);
				break;
		}
		return list;
	}
	
	public List<Map<String, Object>> getOpLabourOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpLabourOrderListByQuery(queryParam);
	}
	
	public int getOpLabourOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpLabourOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpCurrentOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpCurrentOrderListByQuery(queryParam);
	}
	
	public int getOpCurrentOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpCurrentOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpAbnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpAbnormalOrderListByQuery(queryParam);
	}
	
	public int getOpAbnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpAbnormalOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpWasabnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpWasabnormalOrderListByQuery(queryParam);
	}
	
	public int getOpWasabnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpWasabnormalOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpCompleteOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpCompleteOrderListByQuery(queryParam);
	}
	
	public int getOpCompleteOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpCompleteOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpWaitgatheringOrderListByQuery(queryParam);
	}
	
	public int getOpWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpWaitgatheringOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getPeUser(String userName) {
		return orderManageDao.getPeUser(userName);
	}
	
	public Map<String, Object> getOpOrderByOrderno(String orderno) {
		Map<String, Object> ret = orderManageDao.getOpOrderByOrderno(orderno);
		OrderCostParam param = new OrderCostParam();
		param.setOrderno(orderno);
		param.setUsetype(ret.get("usetype").toString());
		param.setOrdertype(ret.get("ordertype").toString());
		param.setHasunit(false);
		JSONObject json = templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApi") + "/OrderApi/GetOrderCost", HttpMethod.POST, null, param, JSONObject.class);
		ret.put("cost", (json.containsKey("cost")) ? json.get("cost") : 0);
		ret.put("rangecost", (json.containsKey("rangecost")) ? json.get("rangecost") : 0);
		ret.put("timecost", (json.containsKey("timecost")) ? json.get("timecost") : 0);
		ret.put("times", (json.containsKey("times")) ? (int)Math.ceil((int)json.get("times")/60.0) : 0);
		ret.put("slowtimes", (json.containsKey("slowtimes")) ? json.get("slowtimes") : 0);
		ret.put("mileage", (json.containsKey("mileage")) ? StringUtil.formatNum((double)json.get("mileage")/1000, 1) : 0);
		ret.put("startprice", (json.containsKey("startprice")) ? json.get("startprice") : 0);
		ret.put("timeprice", (json.containsKey("timeprice")) ? json.get("timeprice") : 0);
		ret.put("rangeprice", (json.containsKey("rangeprice")) ? json.get("rangeprice") : 0);
		ret.put("timetype", (json.containsKey("timetype")) ? json.get("timetype") : 0);

        ret.put("deadheadcost", json.containsKey("deadheadcost") ? json.get("deadheadcost") : 0); // 空驶费
        ret.put("nightcost", json.containsKey("nightcost") ? json.get("nightcost") : 0); // 夜间费
        ret.put("startprice", json.containsKey("startprice") ? json.get("startprice") : 0); // 起步价

		return ret;
	}
	
	/**
	 * 添加客服备注
	 * @param object
	 * @return
	 */
	public Map<String, String> addOpOrdercomment(OpOrdercomment object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("status", "success");
		ret.put("message", "操作成功");
		
		object.setId(GUIDGenerator.newGUID());
		insertOpOrdercomment(object);
		
		return ret;
	}
	
	/**
	 * 分页查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	public PageBean getOpOrderCommentByQuery(OrdercommentQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getOpOrderCommentListByQuery(queryParam);
		int iTotalRecords = getOpOrderCommentCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public OpOrder getOpOrder(String orderno) {
		return orderManageDao.getOpOrder(orderno);
	}
	
	public List<Map<String, Object>> getCompanyVehicleModel(String orderno) {
		return orderManageDao.getCompanyVehicleModel(orderno);
	}
	
	public List<PubSendRules> getSendRulesList(PubSendRules object) {
		return orderManageDao.getSendRulesList(object);
	}
	
	public List<Map<String, Object>> getDriverListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getDriverListByQuery(queryParam);
	}
	
	public int getDriverCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getDriverCountByQuery(queryParam);
	}
	
	public int insertOpSendrecord(OpSendrecord object) {
		return orderManageDao.insertOpSendrecord(object);
	}
	
	public int insertOpDriverchanges(OpDriverchanges object) {
		return orderManageDao.insertOpDriverchanges(object);
	}
	
	public Map<String, Object> getOpSendOrderRecord(String orderno) {
		return orderManageDao.getOpSendOrderRecord(orderno);
	}
	
	public List<Map<String, Object>> getOpChangeDriverListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpChangeDriverListByQuery(queryParam);
	}
	
	public int getOpChangeDriverCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpChangeDriverCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpOrderReviewListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpOrderReviewListByQuery(queryParam);
	}
	
	public int getOpOrderReviewCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpOrderReviewCountByQuery(queryParam);
	}
	
	public int updateOrderState(OpOrder object) {
		return orderManageDao.updateOrderState(object);
	}
	
	public int applyOpOrderReview(OpOrderReview object) {
		return orderManageDao.applyOpOrderReview(object);
	}
	
	public int insertPeUserRefund(PeUserRefund object) {
		return orderManageDao.insertPeUserRefund(object);
	}
	
	public int updatePubDriverWorkstatus(PubDriver object) {
		return orderManageDao.updatePubDriverWorkstatus(object);
	}
	
	public Map<String, Object> getFirstOrderByOrderno(String orderno) {
		return orderManageDao.getFirstOrderByOrderno(orderno);
	}
	
	public List<Map<String, Object>> getOrdernoBySelect(OrderManageQueryParam queryParam) {
		return orderManageDao.getOrdernoBySelect(queryParam);
	}
	
	public void insertOpOrdercomment(OpOrdercomment opOrdercomment) {
		orderManageDao.insertOpOrdercomment(opOrdercomment);
	}
	
	public List<Map<String, Object>> getOpOrderCommentListByQuery(OrdercommentQueryParam queryParam) {
		return orderManageDao.getOpOrderCommentListByQuery(queryParam);
	}
	
	public int getOpOrderCommentCountByQuery(OrdercommentQueryParam queryParam) {
		return orderManageDao.getOpOrderCommentCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getToCCompanySelect(Map<String, Object> params) {
		return orderManageDao.getToCCompanySelect(params);
	}
	
	public Map<String, String> getOpOrderVehicleByOrder(String orderno) {
		return orderManageDao.getOpOrderVehicleByOrder(orderno);
	}
	
	public void updateOpOrderVehicleByOrderno(OpOrder object) {
		orderManageDao.updateOpOrderVehicleByOrderno(object);
	}
	
	/**
	 * 根据车型查询计价规则
	 * @param params
	 */
	private String findModelPriceByModels(Map<String, Object> params, String pricecopy) {
		JSONObject json = null;
		if(StringUtils.isBlank(pricecopy)) {
			json = new JSONObject();
			json.put("cost", 0);
			json.put("mileage", 0);
			json.put("orderno", "");
			json.put("rangecost", 0);
			json.put("timecost", 0);
			json.put("times", 0);
			json.put("slowtimes", 0);
		} else {
			json = JSONObject.fromObject(pricecopy);
		}
		
		List<OpAccountrules> accountRuleList = orderManageDao.findModelPriceByModels(params);
		if(null != accountRuleList && !accountRuleList.isEmpty()) {
			OpAccountrules accountRules = accountRuleList.get(0);
			json.put("rangeprice", accountRules.getRangeprice());
			json.put("startprice", accountRules.getStartprice());
			json.put("timeprice", accountRules.getTimeprice());
			json.put("timetype", Integer.valueOf(accountRules.getTimetype()));
			return json.toString();
		} else {
			return null;
		}
	}
	
	/**
	 * 格式化小数点
	 * @param num
	 * @return
	 */
	private double formatDouble(double num, int count) {
		if(count <= 0) {
			return (int) num;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("#.");
		for (int i = 0; i < count; i++) {
			sb.append("#");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		return Double.valueOf(df.format(num));
	}
	
	/**
	 * 设置订单是否即可订单
	 * @param order
	 * @return
	 */
	private Boolean getOrderIsUseNow(OpOrder order) {
		boolean isusenow = order.isIsusenow();
		if(isusenow) {
			return true;
		}
		GetSendInfoParam param = new GetSendInfoParam();
		param.setCity(order.getOncity());
		param.setOrderprop(1);
		JSONObject json = templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApi") + "/OrderApi/GetSendRule", HttpMethod.POST, null, param,
				JSONObject.class);
		if(null == json) {
			return null;
		}
		
		if(json.getInt("status") == Retcode.OK.code) {
			long carsinterval = json.getJSONObject("sendrule").getLong("carsinterval") * 60 * 1000;
			long usetime = order.getUsetime().getTime();
			long nowtime = System.currentTimeMillis();
			if((usetime - nowtime) > carsinterval) {
				return false;
			} else {
				return true;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 生成司机消息
	 * @param orderno
	 * @param price
	 */
	private void createDriverNews(OpOrder order, double price, OrderMessageType type) {
		String content = "";
		String title = "";
		String newsType = "";
		if(OrderMessageType.REVIEWORDER.equals(type)) {
			Double orderamount = order.getShouldpayamount();
			if(null == orderamount) {
				orderamount = order.getOrderamount();
			}
			if(price > 0) {
				title = "复核反馈";
				content = "订单复核完成，车费金额应为" + orderamount + "元，差额部分不计入贡献。";
			} else if(price < 0) {
				title = "复核反馈";
				content = "订单复核完成，车费金额应为" + orderamount + "元。";
			} else {
				title = "复核反馈";
				content = "经核实，车费金额正确。";
			}
			newsType = UserNews.USER_NEWS_TYPE_SYSTEM;
		} else if(OrderMessageType.MANTICORDER.equals(type)) {
			newsType = UserNews.USER_NEWS_TYPE_ORDER;
		}
		OrderMessageFactory factory = new OrderMessageFactory(order, type, title, content);
		OrderInfoMessage orderinfo = factory.createMessage();
		
		//消息结构
		UserNews userNews = new UserNews();
		userNews.setUserid(order.getDriverid());
		userNews.setType(newsType);
		userNews.setContent(orderinfo.toString());
		
		UserNewsParam param = new UserNewsParam();
		param.setUserNewsTbName(UserNewsParam.DRIVER_USERNEWS_TABNAME);
		param.setUserNews(userNews);
		templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApi") + "/UserNews/addUserNews", HttpMethod.POST, null, param, JSONObject.class);
	}
	
	/**
	 * 更新订单中车辆相关信息(实际车型名称、计费车型名称、车品牌、车系、车牌号)
	 * @param orderno
	 */
	private void updateOpOrderVehicleInfo(String orderno,String belongleasecompany) {
		//查询订单的相关数据
		Map<String, String> orderVehicle = getOpOrderVehicleByOrder(orderno);
		
		OpOrder order = new OpOrder();
		order.setOrderno(orderno);
		order.setFactmodelname(orderVehicle.get("factmodelname"));
		order.setPricemodelname(orderVehicle.get("pricemodelname"));
		order.setVehcbrandname(orderVehicle.get("vehcbrandname"));
		order.setVehclinename(orderVehicle.get("vehclinename"));
		order.setPlateno(orderVehicle.get("plateno"));
		order.setBelongleasecompany(belongleasecompany);
		updateOpOrderVehicleByOrderno(order);
	}
}
