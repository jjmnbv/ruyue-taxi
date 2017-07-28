package com.szyciov.carservice.service;

import com.szyciov.carservice.dao.OpTaxiOrderManageDao;
import com.szyciov.driver.entity.OrderInfoMessage;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.OrdersortColumn;
import com.szyciov.entity.OrderMessageFactory;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserNews;
import com.szyciov.enums.DriverEnum;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.message.TaxiOrderMessage;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxisendrecord;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.PubVehicle;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZF on 2017/5/27.
 */
@Service
public class OpTaxiOrderManageService {
    @Autowired
    private UserNewsService userNewsService;

    @Autowired
    private BaiduApiService baiduApiService;
    @Autowired
    private OpTaxiOrderManageDao opTaxiOrderManageDao;


    /**
     * 人工派单
     *
     * @param object
     * @return
     */
    public Map<String, String> manualSendOrder(OpTaxiOrder object) {
        Map<String, String> result = new HashMap<String, String>();

        OpTaxiOrder order = opTaxiOrderManageDao.getOpTaxiOrder(object.getOrderno());
        //验证订单是否已取消
        if (OrderState.CANCEL.state.equals(order.getOrderstatus())) {
            result.put("status", "fail");
            result.put("message", "人工派单超时，订单已取消");
            return result;
        }
        //验证当前是否有系统+人工模式
        OpUser user = new OpUser();
        user.setId(object.getOperator());
        int ruleCount = opTaxiOrderManageDao.getOpSendmodelCountByUser(user);
        if (ruleCount == 0) {
            result.put("status", "fail");
            result.put("message", "人工派单超时，订单已取消");
            return result;
        }
        //验证当前订单是否已被其他客服处理
        if (!OrderState.MANTICSEND.state.equals(order.getOrderstatus())) {
            result.put("status", "fail");
            result.put("message", "该订单已被其他客服处理");
            return result;
        }
        //验证当前司机是否符合条件
        if (!checkDriver(order, object.getVehicleid())) {
            result.put("status", "fail");
            result.put("message", "该司机已接其他订单，请更换司机指派");
            return result;
        }
        PubVehicle pubVehicle = getPubVehicle(object.getVehicleid());
        //更新订单数据
        updateOrder(order, pubVehicle);
        //添加人工派单记录
        OpTaxisendrecord taxisendrecord = new OpTaxisendrecord();
        taxisendrecord.setId(GUIDGenerator.newGUID());
        taxisendrecord.setOrderno(object.getOrderno());
        taxisendrecord.setDriverid(order.getDriverid());
        taxisendrecord.setOperator(object.getOperator());
        taxisendrecord.setReason(object.getOrderreason());
        opTaxiOrderManageDao.insertOpTaxisendrecord(taxisendrecord);

        //发送消息
        createDriverNews(order, 0, OrderMessageFactory.OrderMessageType.MANTICORDER);
        TaxiOrderMessage message = new TaxiOrderMessage(order, TaxiOrderMessage.TAXI_MANTICORDER);
        if (!order.isIsusenow()) { //预约订单查询司机是否正在服务
            message.setRunTime(getDriverEndtime(order.getDriverid()));
        }
        MessageUtil.sendMessage(message);
        result.put("status", "success");
        result.put("message", "派单成功");
        return result;
    }

    public PubVehicle getPubVehicle(String vehicleid){
        PubVehicle pubVehicle = opTaxiOrderManageDao.getById(vehicleid);
        return pubVehicle;
    }

    /**
     * 验证司机是否符合派单条件
     *
     * @param order
     * @param vehicleid
     * @return
     */
    private boolean checkDriver(OpTaxiOrder order, String vehicleid) {
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
        queryParam.setVehicleid(vehicleid);
        queryParam.setDriverid(order.getDriverid());
        if (opTaxiOrderManageDao.getDriverCountByQuery(queryParam) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新订单数据
     *
     * @param order
     * @param pubVehicle
     */
    public void updateOrder(OpTaxiOrder order, PubVehicle pubVehicle) {
        //更新订单数据
        order.setOrderstatus(OrderState.WAITSTART.state);
        order.setOrdersortcolumn(OrdersortColumn.WAITSTART.state);
        order.setCompanyid(pubVehicle.getLeasesCompanyId());
        order.setVehicleid(pubVehicle.getId());
        order.setDriverid(pubVehicle.getDriverId());
        order.setPlateno(pubVehicle.getPlateNoProvinceName() + pubVehicle.getPlateNoCityName() + pubVehicle.getPlateNo());
        String[] brandCars = pubVehicle.getBrandCars().split(" ");
        if (brandCars.length == 2) {
            order.setVehcbrandname(brandCars[0]);
            order.setVehclinename(brandCars[1]);
        }
        order.setOrdertime(new Date());

        //更新订单归属车企
        PubDriver pubDriver = opTaxiOrderManageDao.getPubDriverById(pubVehicle.getDriverId());
        if(null != pubDriver) {
            order.setBelongleasecompany(pubDriver.getBelongleasecompany());
        }
        opTaxiOrderManageDao.updateOpTaxiOrderByOrderno(order);
    }

    /**
     * 生成司机消息
     *
     * @param order 订单
     * @param price 价格
     * @param type  t
     */
    public void createDriverNews(OpTaxiOrder order, double price, OrderMessageFactory.OrderMessageType type) {
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
                content = "订单复核完成，行程费金额应为" + orderamount + "元，差额部分不计入贡献。";
            } else if (price < 0) {
                title = "复核反馈";
                content = "订单复核完成，行程费金额应为" + orderamount + "元。";
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

    /**
     * 查询司机服务结束时间
     *
     * @param driverid
     * @return
     */
    @SuppressWarnings("unchecked")
    private long getDriverEndtime(String driverid) {
        Map<String, Object> driverMap = opTaxiOrderManageDao.getInServiceOrderByDriver(driverid);
        if (null == driverMap) {
            return 0;
        }

        //查询司机服务中的订单结束时间
        BaiduApiQueryParam baiduparam = new BaiduApiQueryParam();
        baiduparam.setOrderStartLat((double) driverMap.get("driverLat"));
        baiduparam.setOrderStartLng((double) driverMap.get("driverLng"));
        baiduparam.setOrderEndLat((double) driverMap.get("offaddrlat"));
        baiduparam.setOrderEndLng((double) driverMap.get("offaddrlng"));

        Map<String, Object> hintMap = this.baiduApiService.getMileageInfo(baiduparam);

        if (hintMap != null && (Integer) hintMap.get("status") == Retcode.OK.code) {
            int duration = (int) hintMap.get("duration");
            return new Date().getTime() + duration * 1000;
        } else {
            return 0;
        }
    }
}
