package com.szyciov.operate.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.entity.OrderInfoMessage;
import com.szyciov.driver.enums.DriverState;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.enums.ReviewState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.OrderMessageFactory;
import com.szyciov.entity.OrderMessageFactory.OrderMessageType;
import com.szyciov.entity.PubOrderCancel;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserNews;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderReviewEnum;
import com.szyciov.enums.PubOrdercancelEnum;
import com.szyciov.enums.SendRulesEnum;
import com.szyciov.enums.ServiceState;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.message.OrderMessage;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpDriverchanges;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpOrderReview;
import com.szyciov.op.entity.OpOrdercomment;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpSendrecord;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.operate.dao.OpInformationSetDao;
import com.szyciov.operate.dao.OpOrdercancelDao;
import com.szyciov.operate.dao.OrderManageDao;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.param.UserNewsParam;
import com.szyciov.param.coupon.CouponUseParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.Constants;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.latlon.LatLonUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service("OrderManageService")
public class OrderManageService {

    private static final Logger LOGGER = Logger.getLogger(OrderManageService.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();

    @Resource(name = "OrderManageDao")
	private OrderManageDao orderManageDao;

    @Resource(name = "OpOrdercancelDao")
    private OpOrdercancelDao opOrdercancelDao;

    @Resource(name = "OpInformationSetDao")
    private OpInformationSetDao informationSetDao;

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
                list = getOpWaitgatheringOrderListByQuery(queryParam);
                iTotalRecords = getOpWaitgatheringOrderCountByQuery(queryParam);
				break;
			case 6:
                list = getOpCompleteOrderListByQuery(queryParam);
                iTotalRecords = getOpCompleteOrderCountByQuery(queryParam);
				break;
            case 7:
                list = getOpCancelOrderListByQuery(queryParam);
                iTotalRecords = getOpCancelOrderCountByQuery(queryParam);
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
        queryParam.setIsusenow(order.isIsusenow() ? 1 : 0);
        queryParam.setOrderType(order.getOrdertype());
        queryParam.setUsetime(order.getUsetime());
        queryParam.setEstimatedEndtime(StringUtil.addDate(order.getUsetime(), (order.getEstimatedtime() + 60) * 60));

		if(queryParam.getDistance() > 0) {
			double[] around = LatLonUtil.getAround(queryParam.getOrderLat(), queryParam.getOrderLon(), queryParam.getDistance());
			queryParam.setMinLat(around[0]);
			queryParam.setMinLon(around[1]);
			queryParam.setMaxLat(around[2]);
			queryParam.setMaxLon(around[3]);
		}

		//查询运管端id
        OpPlatformInfo info = informationSetDao.getOpPlatformInfo();
        if(null != info) {
            queryParam.setLeasescompanyid(info.getId());
        }
		
		//判断是否可以升级车型
		String models = "0";
        PubSendRules sendRules = new PubSendRules();
		sendRules.setCity(queryParam.getOncity());
        if(order.isIsusenow()) {
            sendRules.setUseType(1);
        } else {
            sendRules.setUseType(0);
        }
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
                //计算预估距离和时间
                double distance = LatLonUtil.getDistance(baiduparam.getOrderStartLng(), baiduparam.getOrderStartLat(), baiduparam.getOrderEndLng(), baiduparam.getOrderEndLat());
                map.put("distance", distance);
                if(distance == 0) {
                    map.put("duration", 0);
                } else {
                    double duration = distance / 1000 / Constants.SPEED * 60 * 60; //到达时间
                    map.put("duration", Math.ceil(duration));
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
     * @param object o
     * @return r
     */
	public Map<String, Object> manualSendOrder(OpOrder object) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			OpOrder order = getOpOrder(object.getOrderno());
			if ("8".equals(order.getOrderstatus())) {
				resultMap.put("status", "fail");
				resultMap.put("message", "人工派单超时，订单已取消");
				return resultMap;
			}
			if(!"1".equals(order.getOrderstatus()) && !"0".equals(order.getOrderstatus())) {
				resultMap.put("status", "fail");
				resultMap.put("message", "该订单已被其他客服处理");
				return resultMap;
			}
            //校验司机是否符合条件
            if(checkDriver(order, object.getDriverid())) {
                resultMap.put("status", "fail");
                resultMap.put("message", "该司机已接其他订单，请更换司机指派");
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

			//修改订单状态
			orderManageDao.manualSendOrder(object);
			PubDriver driver = orderManageDao.getPubDriver(object.getDriverid());
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
			insertOpSendrecord(sendrecord);
			
			//添加司机消息
			order = getOpOrder(object.getOrderno());
            addDriverTravelReminder(order);
			createDriverNews(order, 0, OrderMessageType.MANTICORDER);

            OrderMessage message = new OrderMessage(order, OrderMessage.MANTICORDER);
            MessageUtil.sendMessage(message);

            resultMap.put("status", "success");
            resultMap.put("message", "派单成功");
		} catch (Exception e) {
			resultMap.put("status", "fail");
			resultMap.put("message", "派单失败");
			resultMap.put("exmessage", e.getMessage());
		}
		return resultMap;
	}

    /**
     * 更换司机
     * @param params
     * @return
     */
	public Map<String, Object> changeOpDriver(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
            if(checkDriver(order, newdriverid)) {
                resultMap.put("status", "fail");
                resultMap.put("message", "该司机已接其他订单，请更换司机指派");
                return resultMap;
            }
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
				resultMap.put("message", "所指派服务车型计费规则不存在，建议选择\"按下单车型计费\"或指派其他司机");
				return resultMap;
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
			orderManageDao.changeOpDriver(params);

            //如果订单状态不是待出发时，修改原司机为空闲状态
            if(!order.getOrderstatus().equals("2")) {
                orderManageDao.updatePubDriverLeisure(order.getDriverid());
            }

			PubDriver driver = orderManageDao.getPubDriver(newdriverid);
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

            order.setLastsendtime(StringUtil.addDate(new Date(), 20));
            addDriverTravelReminder(order);
			createDriverNews(order, 0, OrderMessageType.MANTICORDER);

            OrderMessage message = new OrderMessage(order, OrderMessage.CHAGEDRIVER, params);
            MessageUtil.sendMessage(message);

            resultMap.put("status", "success");
            resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", "fail");
			resultMap.put("message", "操作失败");
		}
		
		return resultMap;
	}

    /**
     * 校验司机是否符合条件
     * @return
     */
    private boolean checkDriver(OpOrder order, String driverid) {
        OrderManageQueryParam queryParam = new OrderManageQueryParam();
        queryParam.setOrderLon(order.getOnaddrlng());
        queryParam.setOrderLat(order.getOnaddrlat());
        queryParam.setOncity(order.getOncity());
        queryParam.setOffcity(order.getOffcity());
        queryParam.setOrderNo(order.getOrderno());
        queryParam.setIsusenow(order.isIsusenow() ? 1 : 0);
        queryParam.setDriverState(order.isIsusenow() ? DriverEnum.WORK_STATUS_LEISURE.code : null);
        queryParam.setUsetime(order.getUsetime());
        queryParam.setEstimatedEndtime(StringUtil.addDate(order.getUsetime(), (order.getEstimatedtime() + 60) * 60));
        queryParam.setSelectdriverid(driverid);
        queryParam.setDriverid(order.getDriverid());
        queryParam.setUsetype(order.getUsetype());
        queryParam.setOrderSelectedmodel(order.getSelectedmodel());
        queryParam.setUserId(order.getUserid());

        OpPlatformInfo info = informationSetDao.getOpPlatformInfo();
        if(null != info) {
            queryParam.setLeasescompanyid(info.getId());
        }

        //查询当前派单规则
        String models;
        PubSendRules sendRules = new PubSendRules();
        sendRules.setCity(order.getOncity());
        if(order.isIsusenow()) {
            sendRules.setUseType(1);
        } else {
            sendRules.setUseType(0);
        }
        List<PubSendRules> sendRulesList = orderManageDao.getSendRulesList(sendRules);
        if ((sendRulesList != null) && (!sendRulesList.isEmpty())) {
            models = sendRulesList.get(0).getVehicleUpgrade() + "";
        } else {
            models = "0";
        }
        queryParam.setModels(models);

        if (orderManageDao.getDriverCountByQuery(queryParam) > 0) {
            return true;
        } else {
            return false;
        }
    }
	
	public Map<String, Object> applyRecheckOrder(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OpOrder order = getOpOrder((String) params.get("orderno"));
		if (!"7".equals(order.getOrderstatus()) || "1".equals(order.getReviewstatus())) {
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

    /**
     * 订单复核
     * @param params
     * @return
     */
	public Map<String, Object> opOrderReview(OpOrderReview review) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
        //查询订单信息
        OpOrder order = getOpOrder(review.getOrderno());
        if(!"1".equals(order.getReviewstatus()) || "9".equals(order.getPaymentstatus())) {
            resultMap.put("status", "fail");
            resultMap.put("message", "当前订单状态无法复核");
            return resultMap;
        }
        String pricecopy = order.getPricecopy();
        if (StringUtils.isBlank(pricecopy)) {
            resultMap.put("status", "fail");
            resultMap.put("message", "操作失败");
            return resultMap;
        }
        //初始化复核数据
        initOrderreview(review, order);
        double driverPrice = order.getShouldpayamount() - review.getReviewedprice();
        //重置订单金额
        double differPrice = resetOrderCost(review, order);
        //发送司机消息
        order.setOrderamount(review.getReviewedprice());
        order.setMileage(review.getMileage());
        order.setStarttime(review.getStarttime());
        order.setEndtime(review.getEndtime());
        order.setPricecopy(review.getPricecopy());
        if ("1".equals(review.getReviewperson())) {
            createDriverNews(order, driverPrice, OrderMessageFactory.OrderMessageType.REVIEWORDER);
        }
        //退款处理
        orderRefund(order, review, differPrice);
        //推送消息
        Map<String, Object> smsParams = new HashMap<String, Object>();
        smsParams.put("reviewperson", review.getReviewperson());
        smsParams.put("price", driverPrice);
        OrderMessage message = new OrderMessage(order, "订单复核", smsParams);
        MessageUtil.sendMessage(message);
        resultMap.put("status", "success");
        resultMap.put("message", "操作成功");

        return resultMap;
	}

    /**
     * 初始化订单复核数据
     * @param review
     * @param order
     */
    private void initOrderreview(OpOrderReview review, OpOrder order) {
        OrderCost orderCost = StringUtil.parseJSONToBean(order.getPricecopy(), OrderCost.class);

        review.setId(GUIDGenerator.newGUID());
        review.setReason(order.getOrderreason());
        review.setStatus(1);
        review.setRawstarttime(order.getStarttime());
        review.setRawendtime(order.getEndtime());
        review.setRawmileage(order.getMileage());
        review.setRawtimes((double) orderCost.getSlowtimes());
        review.setRawpricecopy(order.getPricecopy());
        review.setRaworderamount(order.getOriginalorderamount());

        //查询最近一次复核记录
        OpOrderReview orderReview = orderManageDao.getOpOrderreviewLastByOrderno(review.getOrderno());
        if(null != orderReview) {
            review.setRaworderamount(orderReview.getReviewedprice());
            review.setRawstarttime(orderReview.getStarttime());
            review.setRawendtime(orderReview.getEndtime());
            review.setRawtimes(orderReview.getCounttimes());
            review.setRawmileage(orderReview.getMileage());
            review.setRawpricecopy(orderReview.getPricecopy());
        }

        int reviewtype = review.getReviewtype();
        if(OrderReviewEnum.REVIEWTYPE_COST.icode == reviewtype) { //按固定金额
            OrderCost rawOrderCost = StringUtil.parseJSONToBean(review.getRawpricecopy(), OrderCost.class);
            review.setTimesubsidies(rawOrderCost.getTimecost());
            review.setMileageprices(rawOrderCost.getRangecost());
            review.setMileage(review.getRawmileage());
            review.setCounttimes(review.getRawtimes());
            review.setStarttime(review.getRawstarttime());
            review.setEndtime(review.getRawendtime());
            int rawtimes = (int) Math.ceil((review.getEndtime().getTime() - review.getStarttime().getTime()) / 1000.0d / 60);
            review.setTimes(rawtimes * 60.0);
            rawOrderCost.setCost(review.getReviewedprice());
            review.setPricecopy(StringUtil.parseBeanToJSON(rawOrderCost));
        } else { //按里程时长
            int times = (int) Math.ceil((review.getEndtime().getTime() - review.getStarttime().getTime()) / 1000.0d / 60);
            orderCost.setMileage(review.getMileage() * 1000);
            orderCost.setTimes(times * 60);
            if(null != review.getCounttimes()) {
                orderCost.setSlowtimes((int) review.getCounttimes().doubleValue());
            } else {
                orderCost.setSlowtimes(0);
            }
            orderCost = StringUtil.countCost(orderCost, review.getStarttime(), false);
            StringUtil.formatOrderCost2Int(orderCost);

            review.setTimesubsidies(orderCost.getTimecost());
            review.setMileage(formatDouble(review.getMileage() * 1000.0d, 1));
            review.setTimes(times * 60.0);
            review.setMileageprices(formatDouble(orderCost.getRangecost(), 1));
            review.setReviewedprice(orderCost.getCost());
            review.setPricecopy(JSONObject.fromObject(orderCost).toString());
        }
    }

    /**
     * 重置订单相关金额
     * @param review
     * @param order
     * @return 返回乘客支付的差异金额
     */
    private double resetOrderCost(OpOrderReview review, OpOrder order) {
        double price = review.getReviewedprice(), differPrice; //复核后金额、退款金额
        if(order.getPaymentstatus().equals("0")) { //未支付
            differPrice = 0;
            order.setActualpayamount(price);
            order.setOrderamount(price);
            order.setMileage(review.getMileage());
            order.setStarttime(review.getStarttime());
            order.setEndtime(review.getEndtime());
            order.setPricecopy(review.getPricecopy());
        } else { //已支付
            double actualamountCoupon = 0, discountamountCoupon = 0;
            //调用优惠券接口查询实际抵扣金额
            CouponUseParam useParam = new CouponUseParam();
            useParam.setOrderId(order.getOrderno());
            useParam.setMoney(review.getReviewedprice());
            String couponRet = templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("couponapi") + "/coupon/update/actualamount",
                HttpMethod.POST, null, useParam, String.class);
            if(StringUtils.isNotBlank(couponRet)) {
                JSONObject retData = JSONObject.fromObject(couponRet);
                if(retData.getInt("status") == ServiceState.SUCCESS.code) {
                    JSONObject data = JSONObject.fromObject(retData.getString("data"));
                    actualamountCoupon = data.getDouble("actualamount");
                    discountamountCoupon = data.getDouble("discountamount");
                }
            }
            double disPayamount = order.getActualpayamount() - discountamountCoupon; //复核前乘客实际支付的金额(优惠后)
            double payamount = review.getReviewedprice() - actualamountCoupon; //复核后乘客应该支付的金额(优惠后)
            if(payamount >= disPayamount) {
                order.setActualpayamount(disPayamount + actualamountCoupon);
            } else {
                order.setActualpayamount(payamount + actualamountCoupon);
            }
            differPrice = disPayamount - payamount;
            if(payamount == 0) {
                order.setPaymentstatus(PayState.CLOSE.state);
            }
        }
        order.setShouldpayamount(price);
        review.setPrice(order.getShouldpayamount() - order.getActualpayamount());
        if(price == 0) {
            order.setPaymentstatus(PayState.CLOSE.state);
        }
        order.setReviewstatus(ReviewState.REVIEWED.state);
        orderManageDao.applyOpOrderReview(review);
        orderManageDao.updateOrderState(order);
        return differPrice;
    }

    /**
     * 退款处理
     * @param order
     * @param review
     * @param differPrice
     */
    private void orderRefund(OpOrder order, OpOrderReview review, double differPrice) {
        if (order.getPaymentstatus().equals("1") && differPrice > 0) {
            PeUserRefund userRefund = new PeUserRefund();
            userRefund.setId(GUIDGenerator.newGUID());
            userRefund.setUserId(order.getUserid());
            userRefund.setOrderNo(review.getOrderno());
            userRefund.setAmount(BigDecimal.valueOf(differPrice));
            userRefund.setRefundStatus("0");
            userRefund.setRemark(review.getOpinion());
            userRefund.setCreater(review.getOperator());
            userRefund.setUpdater(review.getOperator());
            userRefund.setStatus(1);
            insertPeUserRefund(userRefund);
        }
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
	
	public List<Map<String, Object>> getOpWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpWaitgatheringOrderListByQuery(queryParam);
	}
	
	public int getOpWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpWaitgatheringOrderCountByQuery(queryParam);
	}

    public List<Map<String, Object>> getOpCompleteOrderListByQuery(OrderManageQueryParam queryParam) {
        return orderManageDao.getOpCompleteOrderListByQuery(queryParam);
    }

    public int getOpCompleteOrderCountByQuery(OrderManageQueryParam queryParam) {
        return orderManageDao.getOpCompleteOrderCountByQuery(queryParam);
    }

    public List<Map<String, Object>> getOpCancelOrderListByQuery(OrderManageQueryParam queryParam) {
        return orderManageDao.getOpCancelOrderListByQuery(queryParam);
    }

    public int getOpCancelOrderCountByQuery(OrderManageQueryParam queryParam) {
        return orderManageDao.getOpCancelOrderCountByQuery(queryParam);
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
        param.setOrderprop(1);
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

    /**
     * 查询订单取消规则明细
     * @param param
     * @return
     */
    public Map<String, Object> getCancelPriceDetail(Map<String, String> param) {
        //查询订单取消规则信息
        Map<String, Object> ret = templateHelper.dealRequestWithTokenCarserviceApiUrl("/OrderCancelRule/GetCancelPriceDetail", HttpMethod.POST, null, param, Map.class);
        if(null == ret || ret.isEmpty() || !ret.get("status").equals(Retcode.OK.code)) {
            return ret;
        }

        //查询订单取消信息是否已存在
        PubOrderCancel orderCancel = new PubOrderCancel();
        orderCancel.setOrderno(param.get("orderno"));
        orderCancel = opOrdercancelDao.getOpOrdercancel(orderCancel);
        if(null == orderCancel) {
            orderCancel = new PubOrderCancel();
            orderCancel.setId(GUIDGenerator.newGUID());
            orderCancel.setOrderno(param.get("orderno"));
        }
        orderCancel.setCancelamount((int) ret.get("price"));
        int pricereason = (int) ret.get("pricereason");
        orderCancel.setCancelnature(PubOrdercancelEnum.getCancelnature(pricereason).code);
        orderCancel.setCancelrule(StringUtil.parseBeanToJSON(ret.get("ordercancelrule")));
        orderCancel.setIdentifying(GUIDGenerator.newGUID());
        opOrdercancelDao.updateOpOrdercancelById(orderCancel);

        ret.put("identifying", orderCancel.getIdentifying());
        return ret;
    }

    /**
     * 结束订单
     * @param orderno
     * @return
     */
    public Map<String, Object> endOrder(String orderno) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", Retcode.OK.code);
        result.put("message", Retcode.OK.msg);

        OrderInfoDetail infoDetail = orderManageDao.getOpOrderInfoByOrderno(orderno);
        if(null == infoDetail) {
            result.put("status", Retcode.ORDERNOTEXIT.code);
            result.put("message", Retcode.ORDERNOTEXIT.msg);
            return result;
        }
        //校验订单状态(只有服务中的订单才可以结束)
        if(!OrderState.INSERVICE.state.equals(infoDetail.getStatus())) {
            result.put("status", Retcode.INVALIDORDERSTATUS.code);
            result.put("message", Retcode.INVALIDORDERSTATUS.msg);
            return result;
        }
        //查询司机信息
        PubDriver driver = orderManageDao.getPubDriver(infoDetail.getDriverid());
        if(null == driver) {
            result.put("status", Retcode.INVALIDORDERSTATUS.code);
            result.put("message", Retcode.INVALIDORDERSTATUS.msg);
            return result;
        }
        //更新订单结束位置信息
        if(null != driver.getLng() && driver.getLng() > 0 && null != driver.getLat() && driver.getLat() > 0) {
            BaiduApiQueryParam baiduParam = new BaiduApiQueryParam();
            baiduParam.setOrderStartLng(driver.getLng());
            baiduParam.setOrderStartLat(driver.getLat());
            JSONObject baiduJson = templateHelper.dealRequestWithTokenCarserviceApiUrl("/BaiduApi/GetAddress", HttpMethod.POST, null, baiduParam, JSONObject.class);
            if(baiduJson.getInt("status") != Retcode.OK.code) {
                LOGGER.info("经纬度反查失败,仅保存APP上传经纬度");
                infoDetail.setLng(driver.getLng());
                infoDetail.setLat(driver.getLat());
            } else {
                PubCityAddr cityAddr = orderManageDao.getPubCityByName(baiduJson.getString("city"));
                infoDetail.setLng(baiduJson.getDouble("lng"));
                infoDetail.setLat(baiduJson.getDouble("lat"));
                infoDetail.setCityintime(cityAddr == null ? null : cityAddr.getId());
                infoDetail.setAddressintime(baiduJson.getString("address").isEmpty() ? null : baiduJson.getString("address"));
            }
            orderManageDao.updateOpOrderInfo(infoDetail);
        }

        //更新订单相关数据
        OrderApiParam orderParam = new OrderApiParam();
        orderParam.setOrderid(infoDetail.getOrderno());
        orderParam.setUsetype(infoDetail.getUsetype());
        orderParam.setOrdertype(infoDetail.getType());
        orderParam.setOrderstate(OrderState.SERVICEDONE.state);
        orderParam.setOrderprop(infoDetail.getOrderprop());
        JSONObject orderJson = templateHelper.dealRequestWithTokenCarserviceApiUrl("/OrderApi/ChangeOrderState", HttpMethod.POST, null, orderParam, JSONObject.class);
        if(Retcode.OK.code != orderJson.getInt("status")) {
            return result;
        }
        //更新司机信息
        infoDetail = orderManageDao.getOpOrderInfoByOrderno(orderno);
        driver.setOrderCount(driver.getOrderCount()+1);
        driver.setWorkStatus(DriverState.IDLE.code);
        orderManageDao.updatePubDriver(driver);

        //更新redis中司机服务的订单数据
        String key = "OBD_MILGES_CURRENT_ORDER_" + driver.getId() + "_" + infoDetail.getOrderno();
        try{
            JSONObject json = new JSONObject();
            json.put("orderno", infoDetail.getOrderno());
            json.put("driverid", driver.getId());
            json.put("vehicleid", infoDetail.getVehicleid());
            json.put("starttime", StringUtil.formatDate(infoDetail.getStarttime(), StringUtil.DATE_TIME_FORMAT));
            json.put("orderstatus", infoDetail.getStatus());
            int second = 60 * 10; //十分钟
            json.put("endtime", StringUtil.formatDate(infoDetail.getEndtime(), StringUtil.DATE_TIME_FORMAT));
            JedisUtil.delKey(key);
            JedisUtil.setString(key, json.toString(),second);
        }catch(Exception e){
            LOGGER.error("保存OBD轨迹失败",e);
            JedisUtil.delKey(key);
        }
        return result;
    }

    /**
     * 免责处理
     * @param object
     * @return
     */
    public Map<String, Object> exemptionOrder(PubOrderCancel object) {
        Map<String, Object> ret = new HashMap<String, Object>();

        OpOrder order = getOpOrder(object.getOrderno());
        PubOrderCancel orderCancel = opOrdercancelDao.getOpOrdercancel(object);
        if(null == order || null == orderCancel) {
            ret.put("status", "fail");
            ret.put("message", "订单不存在");
            return ret;
        }
        if(!PayState.NOTPAY.state.equals(order.getPaymentstatus()) || PubOrdercancelEnum.CANCELNATURE_NODUTY.code == orderCancel.getCancelnature()) {
            ret.put("status", "fail");
            ret.put("message", "当前订单状态无法免责处理");
            return ret;
        }
        //设置订单支付状态为已关闭
        order.setPaymentstatus(PayState.CLOSE.state);
        orderManageDao.updateOrderState(order);

        //修改取消信息
        orderCancel.setExemption(object.getExemption());
        orderCancel.setExemptionoperator(object.getExemptionoperator());
        opOrdercancelDao.updateOpOrdercancelById(orderCancel);

        ret.put("status", "success");
        ret.put("message", "操作成功");
        return ret;
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

    public List<Map<String, Object>> getBelongCompanySelect(OrderManageQueryParam queryParam) {
        return orderManageDao.getBelongCompanySelect(queryParam);
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
            json.put("deadheadmileage", accountRules.getDeadheadmileage());
            json.put("deadheadprice", accountRules.getDeadheadprice());
            json.put("nightstarttime", accountRules.getNightstarttime());
            json.put("nightendtime", accountRules.getNightendtime());
            json.put("nighteprice", accountRules.getNighteprice());
            json.put("perhour", accountRules.getPerhour());
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
        param.setUsetype(SendRulesEnum.USETYPE_APPOINTMENT.code);
		JSONObject json = templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApi") + "/OrderApi/GetSendRule", HttpMethod.POST, null, param,
				JSONObject.class);
		if(null == json) {
			return null;
		}
		
		if(json.getInt("status") == Retcode.OK.code) {
            if(json.containsKey("sendrule") && null != json.get("sendrule")) {
                long carsinterval = json.getJSONObject("sendrule").getLong("carsinterval") * 60 * 1000;
                long usetime = order.getUsetime().getTime();
                long nowtime = System.currentTimeMillis();
                if((usetime - nowtime) > carsinterval) {
                    return false;
                } else {
                    return true;
                }
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

    /**
     * 添加订单提醒信息到redis
     * @param order
     */
    private void addDriverTravelReminder(AbstractOrder order) {
        JedisUtil.delKey("DRIVER_TRAVEL_REMINDER_" + order.getOrderno() + "_" + order.getUsetype());
        JedisUtil.setString("DRIVER_TRAVEL_REMINDER_" + order.getOrderno() + "_" + order.getUsetype(), StringUtil.parseBeanToJSON(order));
    }

}
