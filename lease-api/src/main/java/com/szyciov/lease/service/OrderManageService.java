package com.szyciov.lease.service;

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
import com.szyciov.entity.PubOrderCancel;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserNews;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderReviewEnum;
import com.szyciov.enums.PubOrdercancelEnum;
import com.szyciov.enums.SendRulesEnum;
import com.szyciov.lease.dao.OrderManageDao;
import com.szyciov.lease.dao.OrgOrdercancelDao;
import com.szyciov.lease.dao.SendRulesDao;
import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.OrgDriverchanges;
import com.szyciov.lease.entity.OrgOrdercomment;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgSendrecord;
import com.szyciov.lease.entity.OrgUserRefund;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.message.OrderMessage;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderReview;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.param.UserNewsParam;
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

@Service("orderManageService")
public class OrderManageService {

    private static final Logger LOGGER = Logger.getLogger(OrderManageService.class);

	private TemplateHelper templateHelper = new TemplateHelper();

    @Resource(name = "orderManageDao")
	private OrderManageDao orderManageDao;

    @Resource(name = "SendRulesDao")
	private SendRulesDao sendRulesDao;

    @Resource(name = "OrgOrdercancelDao")
    private OrgOrdercancelDao orgOrdercancelDao;

	/**
	 * 查询订单列表数据
	 * @param queryParam
	 * @return
	 */
	public PageBean getOrgOrderByQuery(OrderManageQueryParam queryParam) {
		String minUseTime = queryParam.getMinUseTime();
		if (StringUtils.isNotBlank(minUseTime)) {
			queryParam.setMinUseTime(minUseTime + ":00");
		}
		String maxUseTime = queryParam.getMaxUseTime();
		if (StringUtils.isNotBlank(maxUseTime)) {
			queryParam.setMaxUseTime(maxUseTime + ":59");
		}
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = null;
		int iTotalRecords = 0;
		switch (Integer.valueOf(queryParam.getType()).intValue()) {
			case 1:
				list = getOrgLabourOrderListByQuery(queryParam);
				iTotalRecords = getOrgLabourOrderCountByQuery(queryParam);
				break;
			case 2:
				list = getOrgCurrentOrderListByQuery(queryParam);
				iTotalRecords = getOrgCurrentOrderCountByQuery(queryParam);
				break;
			case 3:
				list = getOrgAbnormalOrderListByQuery(queryParam);
				iTotalRecords = getOrgAbnormalOrderCountByQuery(queryParam);
				break;
			case 4:
				list = getOrgWasabnormalOrderListByQuery(queryParam);
				iTotalRecords = getOrgWasabnormalOrderCountByQuery(queryParam);
				break;
			case 5:
				list = getOrgWaitgatheringOrderListByQuery(queryParam);
				iTotalRecords = getOrgWaitgatheringOrderCountByQuery(queryParam);
				break;
            case 6:
                list = getOrgCompleteOrderListByQuery(queryParam);
                iTotalRecords = getOrgCompleteOrderCountByQuery(queryParam);
                break;
            case 7:
                list = getOrgCancelOrderListByQuery(queryParam);
                iTotalRecords = getOrgCancelOrderCountByQuery(queryParam);
                break;
		}
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<Map<String, Object>> getOrgLabourOrderListByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgLabourOrderListByQuery(queryParam);
	}
	
	public int getOrgLabourOrderCountByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgLabourOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgCurrentOrderListByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgCurrentOrderListByQuery(queryParam);
	}
	
	public int getOrgCurrentOrderCountByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgCurrentOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgAbnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgAbnormalOrderListByQuery(queryParam);
	}
	
	public int getOrgAbnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgAbnormalOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgWasabnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgWasabnormalOrderListByQuery(queryParam);
	}
	
	public int getOrgWasabnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgWasabnormalOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgWaitgatheringOrderListByQuery(queryParam);
	}
	
	public int getOrgWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgWaitgatheringOrderCountByQuery(queryParam);
	}

    public List<Map<String, Object>> getOrgCompleteOrderListByQuery(OrderManageQueryParam queryParam) {
        return this.orderManageDao.getOrgCompleteOrderListByQuery(queryParam);
    }

    public int getOrgCompleteOrderCountByQuery(OrderManageQueryParam queryParam) {
        return this.orderManageDao.getOrgCompleteOrderCountByQuery(queryParam);
    }

    public List<Map<String, Object>> getOrgCancelOrderListByQuery(OrderManageQueryParam queryParam) {
        return this.orderManageDao.getOrgCancelOrderListByQuery(queryParam);
    }

    public int getOrgCancelOrderCountByQuery(OrderManageQueryParam queryParam) {
        return this.orderManageDao.getOrgCancelOrderCountByQuery(queryParam);
    }
	
	/**
	 * 获取订单详情(订单费用)
	 * @param orderno
	 * @return
	 */
	public Map<String, Object> getOrgOrderByOrderno(String orderno) {
		Map<String, Object> ret = orderManageDao.getOrgOrderByOrderno(orderno);
		OrderCostParam param = new OrderCostParam();
		param.setHasunit(false);
		param.setOrderno(orderno);
		param.setUsetype(ret.get("usetype").toString());
		param.setOrdertype(ret.get("ordertype").toString());
        param.setOrderprop(0);
		JSONObject json = this.templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApi") + "/OrderApi/GetOrderCost", HttpMethod.POST, null,
				param, JSONObject.class);
		ret.put("cost", json.containsKey("cost") ? json.get("cost") : Integer.valueOf(0));
		ret.put("rangecost", json.containsKey("rangecost") ? json.get("rangecost") : Integer.valueOf(0));
		ret.put("timecost", json.containsKey("timecost") ? json.get("timecost") : Integer.valueOf(0));
		ret.put("times", Integer.valueOf(
				json.containsKey("times") ? (int) Math.ceil(((Integer) json.get("times")).intValue() / 60.0D) : 0));
		ret.put("slowtimes", json.containsKey("slowtimes") ? json.get("slowtimes") : Integer.valueOf(0));
		ret.put("mileage", Double.valueOf(json.containsKey("mileage")
				? StringUtil.formatNum(((Double) json.get("mileage")).doubleValue() / 1000.0D, 1) : 0.0D));
		ret.put("startprice", json.containsKey("startprice") ? json.get("startprice") : Integer.valueOf(0));
		ret.put("timeprice", json.containsKey("timeprice") ? json.get("timeprice") : Integer.valueOf(0));
		ret.put("rangeprice", json.containsKey("rangeprice") ? json.get("rangeprice") : Integer.valueOf(0));
		ret.put("timetype", json.containsKey("timetype") ? json.get("timetype") : Integer.valueOf(0));

        ret.put("deadheadcost", json.containsKey("deadheadcost") ? json.get("deadheadcost") : 0); // 空驶费
        ret.put("nightcost", json.containsKey("nightcost") ? json.get("nightcost") : 0); // 夜间费
        ret.put("startprice", json.containsKey("startprice") ? json.get("startprice") : 0); // 起步价

		return ret;
	}
	
	/**
	 * 根据订单号查询订单数据
	 * @param orderno
	 * @return
	 */
	public OrgOrder getOrgOrder(String orderno) {
		return this.orderManageDao.getOrgOrder(orderno);
	}
	
	public List<Map<String, Object>> getOrgUser(Map<String, Object> params) {
		return this.orderManageDao.getOrgUser(params);
	}
	
	public List<Map<String, Object>> getCompanyVehicleModel(Map<String, String> params) {
		OrgOrder order = getOrgOrder(params.get("orderno").toString());
		params.put("usetype", order.getUsetype());
		params.put("oncity", order.getOncity());
		params.put("orderType", order.getOrdertype());
		params.put("organId", order.getOrganid());
		params.put("usetype", order.getUsetype());
		return this.orderManageDao.getCompanyVehicleModel(params);
	}
	
	public Map<String, Object> getOrgWaitingOrderByOrderno(String orderno) {
		return this.orderManageDao.getOrgWaitingOrderByOrderno(orderno);
	}
	
	/**
	 * 查询附近司机
	 * @param queryParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageBean getOrgDriverByQuery(OrderManageQueryParam queryParam) {
		OrgOrder order = getOrgOrder(queryParam.getOrderNo());
		queryParam.setOncity(order.getOncity());
		queryParam.setOrderType(order.getOrdertype());
		queryParam.setOrganId(order.getOrganid());
		queryParam.setUsetype(order.getUsetype());
		queryParam.setUserId(order.getUserid());
        queryParam.setIsusenow(order.isIsusenow() ? 1 : 0);
        queryParam.setUsetime(order.getUsetime());
        queryParam.setEstimatedEndtime(StringUtil.addDate(order.getUsetime(), (order.getEstimatedtime() + 60) * 60));
        queryParam.setUserId(order.getUserid());

		if (queryParam.getDistance() > 0) {
			double[] around = LatLonUtil.getAround(queryParam.getOrderLat(), queryParam.getOrderLon(),
					queryParam.getDistance());
			queryParam.setMinLat(around[0]);
			queryParam.setMinLon(around[1]);
			queryParam.setMaxLat(around[2]);
			queryParam.setMaxLon(around[3]);
		}
		String models = "0";
		PubSendRules sendRules = new PubSendRules();
		sendRules.setLeasesCompanyId(queryParam.getLeasescompanyid());
		sendRules.setCity(queryParam.getOncity());
        if(order.isIsusenow()) {
            sendRules.setUseType(1);
        } else {
            sendRules.setUseType(0);
        }
		List<PubSendRules> sendRulesList = this.sendRulesDao.getSendRulesList(sendRules);
		if (null != sendRulesList && !sendRulesList.isEmpty()) {
			models = sendRulesList.get(0).getVehicleUpgrade() + "";
		} else {
			models = "0";
		}
		queryParam.setModels(models);
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = this.orderManageDao.getOrgDriverByQuery(queryParam);
		if ((list != null) && (!list.isEmpty())) {
			Iterator<Map<String, Object>> ite = list.iterator();
			while (ite.hasNext()) {
				Map<String, Object> map = ite.next();
				BaiduApiQueryParam baiduparam = new BaiduApiQueryParam();
				baiduparam.setOrderStartLat(queryParam.getOrderLat());
				baiduparam.setOrderStartLng(queryParam.getOrderLon());
				if (map.get("lat") != null) {
					baiduparam.setOrderEndLat(((Double) map.get("lat")).doubleValue());
				} else {
					baiduparam.setOrderEndLat(0.0D);
				}
				if (map.get("lng") != null) {
					baiduparam.setOrderEndLng(((Double) map.get("lng")).doubleValue());
				} else {
					baiduparam.setOrderEndLng(0.0D);
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
		int iTotalRecords = this.orderManageDao.getOrgDriverCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	/**
	 * 人工派单
	 * @param orgOrder 订单
	 * @return 状态
	 */
    public Map<String, Object> manualSendOrder(OrgOrder orgOrder) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            OrgOrder order = getOrgOrder(orgOrder.getOrderno());
            if ("8".equals(order.getOrderstatus())) {
                resultMap.put("status", "fail");
                resultMap.put("message", "人工派单超时，订单已取消");
                return resultMap;
            }
            if (!"1".equals(order.getOrderstatus()) && !"0".equals(order.getOrderstatus())) {
                resultMap.put("status", "fail");
                resultMap.put("message", "该订单已被其他客服处理");
                return resultMap;
            }
            //校验司机是否符合条件
            if(checkDriver(order, orgOrder.getDriverid())) {
                resultMap.put("status", "fail");
                resultMap.put("message", "该司机已接其他订单，请更换司机指派");
                return resultMap;
            }

            String chargemodel = orgOrder.getPricecopy();
            Map<String, Object> pricecopyMap = new HashMap<String, Object>();
            pricecopyMap.put("leasescompanyid", order.getCompanyid());
            pricecopyMap.put("city", order.getOncity());
            pricecopyMap.put("rulestype", order.getOrdertype());
            pricecopyMap.put("type", orgOrder.getUsetype());
            pricecopyMap.put("organid", order.getOrganid());
            if (chargemodel.equals("0")) {
                orgOrder.setPricemodel(order.getSelectedmodel());
                pricecopyMap.put("cartype", order.getSelectedmodel());
                orgOrder.setPricecopy(order.getPricecopy());
            } else {
                orgOrder.setPricemodel(orgOrder.getFactmodel());
                pricecopyMap.put("cartype", orgOrder.getFactmodel());
                orgOrder.setPricecopy(findModelPriceByModels(pricecopyMap, order.getPricecopy()));
            }
            if (StringUtils.isBlank(orgOrder.getPricecopy())) {
                resultMap.put("status", "fail");
                resultMap.put("message", "所指派服务车型计费规则不存在，建议选择“按下单车型计费”或指派其他司机");
                return resultMap;
            }
            //更新订单数据
            orderManageDao.manualSendOrder(orgOrder);
            PubDriver driver = orderManageDao.getPubDriver(orgOrder.getDriverid());
            //更新车辆基本信息
            updateOrgOrderVehicleInfo(orgOrder.getOrderno(),driver.getBelongleasecompany());

            OrgSendrecord sendrecord = new OrgSendrecord();
            sendrecord.setId(GUIDGenerator.newGUID());
            sendrecord.setOrderno(orgOrder.getOrderno());
            sendrecord.setOperator(orgOrder.getOperator());
            sendrecord.setChargemodel(chargemodel);
            sendrecord.setReason(orgOrder.getOrderreason());
            sendrecord.setDriverid(orgOrder.getDriverid());
            orderManageDao.insertOrgSendrecord(sendrecord);

            order = orderManageDao.getOrgOrder(orgOrder.getOrderno());
            addDriverTravelReminder(order);
            createDriverNews(order, 0.0D, OrderMessageFactory.OrderMessageType.MANTICORDER);

            OrderMessage message = new OrderMessage(order, "人工指派订单");
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
     * 校验司机是否符合条件
     * @return
     */
    private boolean checkDriver(OrgOrder order, String driverid) {
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
        queryParam.setLeasescompanyid(order.getCompanyid());

        //查询当前派单规则
        String models = "0";
        PubSendRules sendRules = new PubSendRules();
        sendRules.setLeasesCompanyId(order.getCompanyid());
        sendRules.setCity(order.getOncity());
        if(order.isIsusenow()) {
            sendRules.setUseType(1);
        } else {
            sendRules.setUseType(0);
        }
        List<PubSendRules> sendRulesList = this.sendRulesDao.getSendRulesList(sendRules);
        if ((sendRulesList != null) && (!sendRulesList.isEmpty())) {
            models = sendRulesList.get(0).getVehicleUpgrade() + "";
        } else {
            models = "0";
        }
        queryParam.setModels(models);

        if (orderManageDao.getOrgDriverCountByQuery(queryParam) > 0) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
	 * 取消订单
	 * @param orderno
	 * @return
	 */
	public Map<String, Object> cancelOrgOrder(String orderno) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrgOrder order = getOrgOrder(orderno);
		String orderstatus = order.getOrderstatus();
		if (("3".equals(orderstatus)) || ("4".equals(orderstatus)) || ("5".equals(orderstatus))) {
			PubDriver driver = new PubDriver();
			driver.setId(order.getDriverid());
			driver.setWorkStatus("0");
			updatePubDriverWorkstatus(driver);
		}
		int result = this.orderManageDao.cancelOrgOrder(orderno);
		order = getOrgOrder(orderno);
		if ("2".equals(order.getPaymethod())) {
			updateOrganCompanyRef(order, order.getEstimatedcost());
		}
		OrderMessage message = new OrderMessage(order, "取消订单");
		MessageUtil.sendMessage(message);
		if (result == 1) {
			resultMap.put("status", "success");
			resultMap.put("message", "取消成功");
		} else {
			resultMap.put("status", "fail");
			resultMap.put("message", "取消失败");
		}
		return resultMap;
	}
	
	public Map<String, Object> getOrgSendOrderRecord(String orderno) {
		return this.orderManageDao.getOrgSendOrderRecord(orderno);
	}
	
	public PageBean getOrgChangeDriverByQuery(OrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getOrgChangeDriverListByQuery(queryParam);
		int iTotalRecords = getOrgChangeDriverListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<Map<String, Object>> getOrgChangeDriverListByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgChangeDriverListByQuery(queryParam);
	}
	
	public int getOrgChangeDriverListCountByQuery(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgChangeDriverListCountByQuery(queryParam);
	}
	
	public PageBean getOrgOrderReviewByQuery(OrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getOrgOrderReviewListRecord(queryParam);
		int iTotalRecords = getOrgOrderReviewListCountRecord(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<Map<String, Object>> getOrgOrderReviewListRecord(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgOrderReviewListRecord(queryParam);
	}
	
	public int getOrgOrderReviewListCountRecord(OrderManageQueryParam queryParam) {
		return this.orderManageDao.getOrgOrderReviewListCountRecord(queryParam);
	}
	
	/**
	 * 订单复核
	 * @param params
	 * @return
	 */
	public Map<String, Object> orgOrderReview(OrgOrderReview review) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //查询订单信息
        OrgOrder order = getOrgOrder(review.getOrderno());
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
        OrderCost orderCost = StringUtil.parseJSONToBean(pricecopy, OrderCost.class);
        int slowtimes = orderCost.getSlowtimes();

        //填充复核数据
        review.setId(GUIDGenerator.newGUID());
        review.setReviewstatus("1");
        review.setReason(order.getOrderreason());
        review.setStatus(1);
        review.setRawstarttime(order.getStarttime());
        review.setRawendtime(order.getEndtime());
        review.setRawmileage(Double.valueOf(order.getMileage()));
        review.setRawtimes((double) slowtimes);
        review.setRawpricecopy(pricecopy);
        review.setRaworderamount(order.getOriginalorderamount());

        try {
            //查询最近一次复核记录
            OrgOrderReview orderReview = orderManageDao.getOrgOrderreviewLastByOrderno(review.getOrderno());
            if(null != orderReview) {
                review.setRaworderamount(orderReview.getReviewedprice());
                review.setRawstarttime(orderReview.getStarttime());
                review.setRawendtime(orderReview.getEndtime());
                review.setRawtimes(orderReview.getCounttimes());
                review.setRawmileage(orderReview.getMileage());
                review.setRawpricecopy(orderReview.getPricecopy());
            } else {
                review.setRaworderamount(order.getOrderamount());
                review.setRawstarttime(order.getStarttime());
                review.setRawendtime(order.getEndtime());
                review.setRawtimes((double) slowtimes);
                review.setRawmileage(order.getMileage());
                review.setRawpricecopy(pricecopy);
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
                review.setTimes(rawtimes * 60);
                rawOrderCost.setCost(review.getReviewedprice());
                review.setPricecopy(StringUtil.parseBeanToJSON(rawOrderCost));
            } else { //按里程时长
                int times = (int) Math.ceil((review.getEndtime().getTime() - review.getStarttime().getTime()) / 1000.0d / 60);
                orderCost.setMileage(review.getMileage() * 1000);
                orderCost.setTimes(times * 60);
                orderCost.setSlowtimes((int) review.getCounttimes());
                orderCost = StringUtil.countCost(orderCost, review.getStarttime(), false);
                StringUtil.formatOrderCost2Int(orderCost);

                review.setTimesubsidies(orderCost.getTimecost());
                review.setMileage(formatDouble(review.getMileage() * 1000.0d, 1));
                review.setTimes(times * 60);
                review.setMileageprices(formatDouble(orderCost.getRangecost(), 1));
                review.setReviewedprice(orderCost.getCost());
                review.setPricecopy(JSONObject.fromObject(orderCost).toString());
            }

            //费用计算
            Double actualpayamount = order.getActualpayamount();
            double price = review.getReviewedprice();
            if((price < actualpayamount.doubleValue()) || (order.getPaymethod().equals("2"))) { //复核后金额小于实付金额或机构支付时修改实付金额
                order.setActualpayamount(price);
            }
            order.setShouldpayamount(price);
            review.setPrice(0); //待修改
            //请求接口，获取实际抵扣金额
            JSONObject json = new JSONObject(); //待修改
            double differPrice = 0; //待修改
            if ((StringUtils.isBlank(order.getPaymentstatus())) || (order.getPaymentstatus().equals("0"))
                || (order.getPaymethod().equals("2"))) {
                order.setOrderamount(review.getReviewedprice());
                order.setActualpayamount(review.getReviewedprice());
                order.setMileage(review.getMileage());
                order.setStarttime(review.getStarttime());
                order.setEndtime(review.getEndtime());
                order.setPricecopy(review.getPricecopy());
            }
            if(review.getReviewedprice() == 0) { //待修改，复核后金额或实付金额为0时，订单金额改为已关闭
                order.setPaymentstatus(PayState.CLOSE.state);
            }
            order.setReviewstatus(ReviewState.REVIEWED.state);

            orderManageDao.applyOrgOrderReview(review);
            orderManageDao.updateOrderState(order);

            order.setOrderamount(review.getReviewedprice());
            order.setMileage(review.getMileage());
            order.setStarttime(review.getStarttime());
            order.setEndtime(review.getEndtime());
            order.setPricecopy(review.getPricecopy());
            if ("1".equals(review.getReviewperson())) {
                createDriverNews(order, differPrice, OrderMessageFactory.OrderMessageType.REVIEWORDER);
            }
            if(order.getPaymentstatus().equals("4")) {
                updateOrganCompanyRef(order, differPrice);
            }
            if (order.getPaymentstatus().equals("1") && differPrice > 0.0D) {
                OrgUserRefund userRefund = new OrgUserRefund();
                userRefund.setId(GUIDGenerator.newGUID());
                userRefund.setUserId(order.getUserid());
                userRefund.setLeasesCompanyId(review.getLeasescompanyid());
                userRefund.setOrderNo(review.getOrderno());
                userRefund.setAmount(BigDecimal.valueOf(differPrice));
                userRefund.setRefundStatus("0");
                userRefund.setRemark(review.getOpinion());
                userRefund.setCreater(review.getOperator());
                userRefund.setUpdater(review.getOperator());
                userRefund.setStatus(1);
                orderManageDao.insertOrgUserrefund(userRefund);
            }
            if (!order.getPaymethod().equals("2")) {
                Map<String, Object> smsParams = new HashMap<String, Object>();
                smsParams.put("reviewperson", review.getReviewperson());
                smsParams.put("price", Double.valueOf(differPrice));
                OrderMessage message = new OrderMessage(order, "订单复核", smsParams);
                MessageUtil.sendMessage(message);
            }
            resultMap.put("status", "success");
            resultMap.put("message", "操作成功");

        } catch (Exception e) {
            LOGGER.error("订单复核异常", e);
            resultMap.put("status", "fail");
            resultMap.put("message", "操作失败");
        }
		return resultMap;
	}
	
	/**
	 * 拒绝订单复核
	 * @param params
	 * @return
	 */
	public Map<String, Object> orgOrderReject(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrgOrder orgOrder = new OrgOrder();
		String orderno = (String) params.get("orderno");
		orgOrder.setOrderno(orderno);
		orgOrder.setReviewstatus(ReviewState.NOTREVIEW.state);
		this.orderManageDao.orderReject(orgOrder);
		OrgOrder order = this.orderManageDao.getOrgOrder(orderno);
		if (order.getReviewperson().equals("1")) {
			createDriverNews(order, 0.0D, OrderMessageFactory.OrderMessageType.REVIEWORDER);
		}
		OrderMessage message = new OrderMessage(order, "拒绝复核");
		MessageUtil.sendMessage(message);
		resultMap.put("status", "success");
		resultMap.put("message", "操作成功");
		return resultMap;
	}
	
	/**
	 * 订单更换司机
	 * @param params
	 * @return
	 */
	public Map<String, Object> changeOrgDriver(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String orderno = (String) params.get("orderno");
			OrgOrder order = getOrgOrder(orderno);
			if (order.getOrderstatus().equals("6") || order.getOrderstatus().equals("7")
					|| order.getOrderstatus().equals("8")) {
				resultMap.put("status", "fail");
				resultMap.put("message", "当前订单状态无法更换司机");
				return resultMap;
			}

			Date currentTime = new Date();
			String chargemodel = params.get("pricecopy").toString();
			String newdriverid = params.get("newdriverid").toString();
			String companyId = params.get("leasescompanyid").toString();
			String modelsId = params.get("modelsid").toString();

            if(checkDriver(order, newdriverid)) {
                resultMap.put("status", "fail");
                resultMap.put("message", "该司机已接其他订单，请更换司机指派");
                return resultMap;
            }

			Map<String, Object> pricecopyMap = new HashMap<String, Object>();
			pricecopyMap.put("organid", order.getOrganid());
			pricecopyMap.put("leasescompanyid", companyId);
			pricecopyMap.put("city", order.getOncity());
			pricecopyMap.put("rulestype", order.getOrdertype());
			pricecopyMap.put("type", params.get("usetype"));
			if (chargemodel.equals("0")) {
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
			if (params.get("pricecopy") == null) {
				resultMap.put("status", "fail");
				resultMap.put("message", "所指派服务车型计费规则不存在，建议选择“按下单车型计费”或指派其他司机");
				return resultMap;
			}
			OrgDriverchanges driverchanges = new OrgDriverchanges();
			driverchanges.setId(GUIDGenerator.newGUID());
			driverchanges.setOrderno(orderno);
			driverchanges.setBeforedriverid(order.getDriverid());
			driverchanges.setAfterdriverid(newdriverid);
			driverchanges.setReason(params.get("orderreason").toString());
			driverchanges.setSendtime(currentTime);
			driverchanges.setUpdatetime(currentTime);
			driverchanges.setCreatetime(currentTime);
			driverchanges.setOperator(params.get("operator").toString());
			driverchanges.setStatus(Integer.valueOf(1));
			driverchanges.setChargemodel(chargemodel);
			this.orderManageDao.insertOrgDriverchanges(driverchanges);
			int result = this.orderManageDao.changeOrgDriver(params);

            //如果订单状态不是待出发时，修改原司机为空闲状态
            if(!order.getOrderstatus().equals("2")) {
                orderManageDao.updatePubDriverLeisure(order.getDriverid());
            }

			PubDriver driver = orderManageDao.getPubDriver(newdriverid);
			//更新车辆基本信息
			updateOrgOrderVehicleInfo(orderno,driver.getBelongleasecompany());
			
			String orderstatus = order.getOrderstatus();
			if (("3".equals(orderstatus)) || ("4".equals(orderstatus)) || ("5".equals(orderstatus))) {
				driver.setWorkStatus("0");
				updatePubDriverWorkstatus(driver);
			}

			order = getOrgOrder(orderno);
            addDriverTravelReminder(order);
			createDriverNews(order, 0.0D, OrderMessageFactory.OrderMessageType.MANTICORDER);
			if (result == 1) {
				OrderMessage message = new OrderMessage(order, "更改司机", params);
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
	
	/**
	 * 申请复核
	 * @param params
	 * @return
	 */
	public Map<String, String> applyRecheckOrder(Map<String, String> params) {
		Map<String, String> resultMap = new HashMap<String, String>();
		OrgOrder order = getOrgOrder(params.get("orderno"));
		if (!"7".equals(order.getOrderstatus()) || "2".equals(order.getPaymentstatus())
				|| "3".equals(order.getPaymentstatus()) || "1".equals(order.getReviewstatus())
				|| "9".equals(order.getPaymentstatus())) {
			resultMap.put("status", "fail");
			resultMap.put("message", "当前订单状态不允许申请复核");
			return resultMap;
		}
		
		try {
			int result = this.orderManageDao.applyRecheckOrder(params);
			if (result == 1) {
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
	 * 分页查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	public PageBean getOrgOrderCommentByQuery(OrdercommentQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getOrgOrderCommentListByQuery(queryParam);
		int iTotalRecords = getOrgOrderCommentCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<Map<String, String>> getSendRulesByName(PubSendRules object) {
		return this.orderManageDao.getSendRulesByName(object);
	}
	
	public LeAccountRules getOrderAccountRulesByOrderno(Map<String, Object> params) {
		List<LeAccountRules> accountRuleList = this.orderManageDao.findModelPriceByModels(params);
		if ((accountRuleList != null) && (!accountRuleList.isEmpty())) {
			return accountRuleList.get(0);
		}
		return null;
	}
	
	/**
	 * 添加客服备注
	 * @param object
	 * @return
	 */
	public Map<String, String> addOrgOrdercomment(OrgOrdercomment object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("status", "success");
		ret.put("message", "操作成功");
		
		object.setId(GUIDGenerator.newGUID());
		insertOrgOrdercomment(object);
		
		return ret;
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
				list = getOrgAbnormalOrderListByQuery(queryParam);
				break;
			case 4:
				list = getOrgWasabnormalOrderListByQuery(queryParam);
				break;
			case 5:
				list = getOrgCompleteOrderListByQuery(queryParam);
				break;
			case 6:
				list = getOrgWaitgatheringOrderListByQuery(queryParam);
				break;
		}
		return list;
	}
	
	public OrgOrganCompanyRef getOrgOrganCompanyRefByOrgCom(OrgOrganCompanyRef object) {
		return this.orderManageDao.getOrgOrganCompanyRefByOrgCom(object);
	}
	
	public int updateOrgOrganCompanyRef(OrgOrganCompanyRef object) {
		return this.orderManageDao.updateOrgOrganCompanyRef(object);
	}
	
	public List<Map<String, Object>> getOrganByName(Map<String, Object> params) {
		return this.orderManageDao.getOrganByName(params);
	}
	
	public int updatePubDriverWorkstatus(PubDriver object) {
		return this.orderManageDao.updatePubDriverWorkstatus(object);
	}
	
	public Map<String, Object> getFirstOrderByOrderno(String orderno) {
		return this.orderManageDao.getFirstOrderByOrderno(orderno);
	}
	
	public Map<String, String> getOrgOrderVehicleByOrder(String orderno) {
		return this.orderManageDao.getOrgOrderVehicleByOrder(orderno);
	}
	
	public void updateOrgOrderVehicleByOrderno(OrgOrder object) {
		this.orderManageDao.updateOrgOrderVehicleByOrderno(object);
	}
	
	public List<Map<String, Object>> getOrgOrderCommentListByQuery(OrdercommentQueryParam queryParam) {
		return this.orderManageDao.getOrgOrderCommentListByQuery(queryParam);
	}
	  
	public int getOrgOrderCommentCountByQuery(OrdercommentQueryParam queryParam) {
		return this.orderManageDao.getOrgOrderCommentCountByQuery(queryParam);
	}
	
	public void insertOrgOrdercomment(OrgOrdercomment object) {
		this.orderManageDao.insertOrgOrdercomment(object);
	}
	
	public List<Map<String, Object>> getOrdernoBySelect(OrderManageQueryParam queryParam) {
		return orderManageDao.getOrdernoBySelect(queryParam);
	}
	
	private String findModelPriceByModels(Map<String, Object> params, String pricecopy) {
		JSONObject json = null;
		if (StringUtils.isBlank(pricecopy)) {
			json = new JSONObject();
			json.put("cost", Integer.valueOf(0));
			json.put("mileage", Integer.valueOf(0));
			json.put("orderno", "");
			json.put("rangecost", Integer.valueOf(0));
			json.put("timecost", Integer.valueOf(0));
			json.put("times", Integer.valueOf(0));
			json.put("slowtimes", Integer.valueOf(0));
		} else {
			json = JSONObject.fromObject(pricecopy);
		}
		List<LeAccountRules> accountRuleList = this.orderManageDao.findModelPriceByModels(params);
		if ((accountRuleList != null) && (!accountRuleList.isEmpty())) {
			LeAccountRules accountRules = accountRuleList.get(0);
			json.put("rangeprice", accountRules.getRangePrice());
			json.put("startprice", accountRules.getStartPrice());
			json.put("timeprice", accountRules.getTimePrice());
			json.put("timetype", Integer.valueOf(accountRules.getTimeType()));
            json.put("deadheadmileage", accountRules.getDeadheadmileage());
            json.put("deadheadprice", accountRules.getDeadheadprice());
            json.put("nightstarttime", accountRules.getNightstarttime());
            json.put("nightendtime", accountRules.getNightendtime());
            json.put("nighteprice", accountRules.getNighteprice());
            json.put("perhour", accountRules.getPerhour());
			return json.toString();
		}
		return null;
	}
	
	private double formatDouble(double num, int count) {
		if (count <= 0) {
			return (int) num;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("#.");
		for (int i = 0; i < count; i++) {
			sb.append("#");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		return Double.valueOf(df.format(num)).doubleValue();
	}
	private Boolean getOrderIsUseNow(OrgOrder order) {
		boolean isusenow = order.isIsusenow();
		if (isusenow) {
			return Boolean.valueOf(true);
		}
		GetSendInfoParam param = new GetSendInfoParam();
		param.setCompanyid(order.getCompanyid());
		param.setCity(order.getOncity());
        param.setUsetype(SendRulesEnum.USETYPE_APPOINTMENT.code);
		param.setOrderprop(0);
		JSONObject json = (JSONObject) this.templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApi") + "/OrderApi/GetSendRule", HttpMethod.POST, null, param,
				JSONObject.class);
		if (json == null) {
			return null;
		}
		if (json.getInt("status") == Retcode.OK.code) {
            if(json.containsKey("sendrule") && null != json.get("sendrule")) {
                long carsinterval = json.getJSONObject("sendrule").getLong("carsInterval") * 60L * 1000L;
                long usetime = order.getUsetime().getTime();
                long nowtime = System.currentTimeMillis();
                if (usetime - nowtime > carsinterval) {
                    return Boolean.valueOf(false);
                }
                return Boolean.valueOf(true);
            } else {
                return true;
            }
		}
		return null;
	}
	
	private void createDriverNews(OrgOrder order, double price, OrderMessageFactory.OrderMessageType type) {
		String content = "";
		String title = "";
		String newsType = "";
		if (OrderMessageFactory.OrderMessageType.REVIEWORDER.equals(type)) {
			Double orderamount = order.getShouldpayamount();
			if (orderamount == null) {
				orderamount = Double.valueOf(order.getOrderamount());
			}
			if (price > 0.0D) {
				title = "复核反馈";
				content = "订单复核完成，车费金额应为" + orderamount + "元，差额部分不计入贡献。";
			} else if (price < 0.0D) {
				title = "复核反馈";
				content = "订单复核完成，车费金额应为" + orderamount + "元。";
			} else {
				title = "复核反馈";
				content = "经核实，车费金额正确。";
			}
			newsType = UserNews.USER_NEWS_TYPE_SYSTEM;
		} else if (OrderMessageFactory.OrderMessageType.MANTICORDER.equals(type)) {
			newsType = UserNews.USER_NEWS_TYPE_ORDER;
		}
		OrderMessageFactory factory = new OrderMessageFactory(order, type, title, content);
		OrderInfoMessage orderinfo = factory.createMessage();
		UserNews userNews = new UserNews();
		userNews.setUserid(order.getDriverid());
		userNews.setType(newsType);
		userNews.setContent(orderinfo.toString());
		UserNewsParam param = new UserNewsParam();
		param.setUserNewsTbName(UserNewsParam.DRIVER_USERNEWS_TABNAME);
		param.setUserNews(userNews);
		this.templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApi") + "/UserNews/addUserNews", HttpMethod.POST, null, param,
				JSONObject.class);
	}
	
	private void updateOrganCompanyRef(OrgOrder order, double price) {
		OrgOrganCompanyRef companyRef = new OrgOrganCompanyRef();
		companyRef.setCompanyId(order.getCompanyid());
		companyRef.setOrganId(order.getOrganid());
		companyRef = getOrgOrganCompanyRefByOrgCom(companyRef);
		if (companyRef != null) {
			companyRef.setBalance(companyRef.getBalance() + price);
			updateOrgOrganCompanyRef(companyRef);
		}
	}
	
	/**
	 * 更新订单中车辆相关信息(实际车型名称、计费车型名称、车品牌、车系、车牌号)
	 * @param orderno
	 */
	private void updateOrgOrderVehicleInfo(String orderno,String belongleasecompany) {
		//查询订单的相关数据
		Map<String, String> orderVehicle = getOrgOrderVehicleByOrder(orderno);
		
		OrgOrder order = new OrgOrder();
		order.setOrderno(orderno);
		order.setFactmodelname(orderVehicle.get("factmodelname"));
		order.setPricemodelname(orderVehicle.get("pricemodelname"));
		order.setVehcbrandname(orderVehicle.get("vehcbrandname"));
		order.setVehclinename(orderVehicle.get("vehclinename"));
		order.setPlateno(orderVehicle.get("plateno"));
		//更新订单归属车企
		order.setBelongleasecompany(belongleasecompany);
		updateOrgOrderVehicleByOrderno(order);
	}

    /**
     * 查询列表中所有的服务车企
     * @param params
     * @return
     */
    public List<Map<String, Object>> getBelongCompanySelect(OrderManageQueryParam params) {
        return orderManageDao.getBelongCompanySelect(params);
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
        orderCancel = orgOrdercancelDao.getOrgOrdercancel(orderCancel);
        if(null == orderCancel) {
            orderCancel = new PubOrderCancel();
            orderCancel.setId(GUIDGenerator.newGUID());
            orderCancel.setOrderno(param.get("orderno"));
        }
        orderCancel.setCancelamount((int) ret.get("price"));
        int pricereason = (int) ret.get("pricereason");
        orderCancel.setCancelnature(PubOrdercancelEnum.getCancelnature(pricereason).code);
        orderCancel.setCancelrule(ret.get("ordercancelrule").toString());
        orderCancel.setIdentifying(GUIDGenerator.newGUID());
        orgOrdercancelDao.updateOrgOrdercancelById(orderCancel);

        ret.put("identifying", orderCancel.getIdentifying());
        return ret;
    }

    /**
     * 免责处理
     * @param object
     * @return
     */
    public Map<String, Object> exemptionOrder(PubOrderCancel object) {
        Map<String, Object> ret = new HashMap<String, Object>();

        OrgOrder order = getOrgOrder(object.getOrderno());
        PubOrderCancel orderCancel = orgOrdercancelDao.getOrgOrdercancel(object);
        if(null == order || null == orderCancel) {
            ret.put("status", "fail");
            ret.put("message", "订单不存在");
            return ret;
        }
        if((!PayState.NOTPAY.state.equals(order.getPaymentstatus()) && !PayState.MENTED.state.equals(order.getPaymentstatus()))
            || PubOrdercancelEnum.CANCELNATURE_NODUTY.code == orderCancel.getCancelnature()) {
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
        orgOrdercancelDao.updateOrgOrdercancelById(orderCancel);

        //机构支付订单，修改机构账户
        updateOrganCompanyRef(order, orderCancel.getCancelamount());

        ret.put("status", "success");
        ret.put("message", "操作成功");
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

        OrderInfoDetail infoDetail = orderManageDao.getOrgOrderInfoByOrderno(orderno);
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
            orderManageDao.updateOrgOrderInfo(infoDetail);
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
        infoDetail = orderManageDao.getOrgOrderInfoByOrderno(orderno);
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
     * 添加订单提醒信息到redis
     * @param order
     */
    private void addDriverTravelReminder(AbstractOrder order) {
        order.setLastsendtime(StringUtil.addDate(new Date(), 20));
        JedisUtil.delKey("DRIVER_TRAVEL_REMINDER_" + order.getOrderno() + "_" + order.getUsetype());
        JedisUtil.setString("DRIVER_TRAVEL_REMINDER_" + order.getOrderno() + "_" + order.getUsetype(), StringUtil.parseBeanToJSON(order));
    }

}
