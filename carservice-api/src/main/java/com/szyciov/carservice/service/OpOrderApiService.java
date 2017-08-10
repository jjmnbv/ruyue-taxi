package com.szyciov.carservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.szyciov.carservice.dao.OpOrderApiDao;
import com.szyciov.carservice.dao.OrderApiDao;
import com.szyciov.driver.entity.OrderInfoMessage;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.OrderMessageFactory;
import com.szyciov.entity.PubSendrules;
import com.szyciov.entity.UserNews;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.message.OrderMessage;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpSendrecord;
import com.szyciov.op.entity.OpTaxisendrules;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZF on 2017/5/26.
 */
@Service
public class OpOrderApiService {
    @Autowired
    private OpOrderApiDao opOrderApiDao;
    @Autowired
    private OrderApiDao orderApiDao;
    @Autowired
    private UserNewsService userNewsService;

    /**
     * 人工派单
     *
     * @param object o
     * @return r
     */
    public Map<String, Object> manualSendOrder(OpOrder object) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            OpOrder order = getOpOrder(object.getOrderno());
            if ("8".equals(order.getOrderstatus())) {
                resultMap.put("status", "fail");
                resultMap.put("message", "人工派单超时，订单已取消");
                return resultMap;
            }
            if (!"1".equals(order.getOrderstatus())) {
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
            if (chargemodel.equals("0")) {
                object.setPricemodel(order.getSelectedmodel());
                pricecopyMap.put("cartype", order.getSelectedmodel());
                object.setPricecopy(order.getPricecopy());
            } else {
                object.setPricemodel(object.getFactmodel());
                pricecopyMap.put("cartype", object.getFactmodel());
                object.setPricecopy(findModelPriceByModels(pricecopyMap, order.getPricecopy()));
            }

            if (StringUtils.isBlank(object.getPricecopy())) {
                resultMap.put("status", "fail");
                resultMap.put("message", "所指派服务车型计费规则不存在，建议选择“按下单车型计费”或指派其他司机");
                return resultMap;
            }

            //判断该订单是即可订单还是预约订单
            Boolean isusenow = getOrderIsUseNow(order);
            if (null == isusenow) {
                object.setIsusenow(order.isIsusenow());
            } else {
                object.setIsusenow(isusenow);
            }

            //修改订单状态
            int orderResult = opOrderApiDao.manualSendOrder(object);
            PubDriver driver = getOpPubDriver(object.getDriverid());
            //更新车辆基本信息
            updateOpOrderVehicleInfo(object.getOrderno(), driver.getBelongleasecompany());

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

            order.setLastsendtime(StringUtil.addDate(new Date(), 20));
            addDriverTravelReminder(order);

            createDriverNews(order, 0, OrderMessageFactory.OrderMessageType.MANTICORDER);

            if (orderResult == 1 && recordRsult == 1) {
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

    public PubDriver getOpPubDriver(String driverid){
        PubDriver driver = opOrderApiDao.getPubDriver(driverid);
        return driver;
    }

    private String findModelPriceByModels(Map<String, Object> params, String pricecopy) {
        JSONObject json = null;
        if (StringUtils.isBlank(pricecopy)) {
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

        List<OpAccountrules> accountRuleList = opOrderApiDao.findModelPriceByModels(params);
        if (null != accountRuleList && !accountRuleList.isEmpty()) {
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

    public OpOrder getOpOrder(String orderno) {
        return this.opOrderApiDao.getOpOrder(orderno);
    }

    /**
     * 设置订单是否即可订单
     *
     * @param order
     * @return
     */
    private Boolean getOrderIsUseNow(OpOrder order) {
        if (order.isIsusenow()) {
            return true;
        }
        GetSendInfoParam param = new GetSendInfoParam();
        param.setCity(order.getOncity());
        param.setOrderprop(1);
        JSONObject json = getSendRule(param);

        long carsinterval = json.getJSONObject("sendrule").getLong("carsinterval") * 60 * 1000;
        long usetime = order.getUsetime().getTime();
        long nowtime = System.currentTimeMillis();
        if ((usetime - nowtime) > carsinterval) {
            return false;
        } else {
            return true;
        }
    }

    private JSONObject getSendRule(GetSendInfoParam gsp) {
        JSONObject result = new JSONObject();
        result.put("orderprop", gsp.getOrderprop());
        if (gsp.getOrderprop() == 0 || gsp.getOrderprop() == 1) {
        	//网约车合并使用一个派单规则表 edit by Efy 2017-6-6 12:26:00
            PubSendrules sendRule = new PubSendrules();
     		sendRule.setLeasescompanyid(gsp.getCompanyid());
     		sendRule.setVehicletype(Integer.valueOf(OrderEnum.ORDERSTYPE_CAR.code));
     		sendRule.setUsetype(gsp.getUsetype());
    		sendRule.setCity(gsp.getCity());
    		sendRule = orderApiDao.getSendRule(sendRule);
            result.put("sendrule", sendRule);
        } else {
            OpTaxisendrules taxisendrules = orderApiDao.getOpTaxiSendRule(gsp);
            result.put("sendrule", taxisendrules);
        }
        return result;
    }

    /**
     * 更新订单中车辆相关信息(实际车型名称、计费车型名称、车品牌、车系、车牌号)
     *
     * @param orderno
     */
    public void updateOpOrderVehicleInfo(String orderno, String belongleasecompany) {
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

    public void updateOpOrderVehicleByOrderno(OpOrder object) {
        this.opOrderApiDao.updateOpOrderVehicleByOrderno(object);
    }

    public Map<String, String> getOpOrderVehicleByOrder(String orderno) {
        return this.opOrderApiDao.getOpOrderVehicleByOrder(orderno);
    }

    /**
     * 生成司机消息
     *
     * @param order 订单
     * @param price 价格
     * @param type  x
     */
    public void createDriverNews(OpOrder order, double price, OrderMessageFactory.OrderMessageType type) {
        String content = "";
        String title = "";
        String newsType = "";
        if (OrderMessageFactory.OrderMessageType.REVIEWORDER.equals(type)) {
            Double orderamount = order.getShouldpayamount();
            if (null == orderamount) {
                orderamount = order.getOrderamount();
            }
            if (price > 0) {
                title = "复核反馈";
                content = "订单复核完成，车费金额应为" + orderamount + "元，差额部分不计入贡献。";
            } else if (price < 0) {
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

        //消息结构
        UserNews userNews = new UserNews();
        userNews.setUserid(order.getDriverid());
        userNews.setType(newsType);
        userNews.setContent(orderinfo.toString());

        UserNewsParam param = new UserNewsParam();
        param.setUserNewsTbName(UserNewsParam.DRIVER_USERNEWS_TABNAME);
        param.setUserNews(userNews);
        this.userNewsService.saveUserNews(param);
    }

    public int insertOpSendrecord(OpSendrecord object) {
        return opOrderApiDao.insertOpSendrecord(object);
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
