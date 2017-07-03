package com.szyciov.touch.service;

import com.szyciov.touch.dao.PartnerManageDao;
import com.szyciov.touch.dto.OrderSimplesResultDTO;
import com.szyciov.touch.enums.OrderPayStatusEnums;
import com.szyciov.touch.enums.OrderStatusEnums;
import com.szyciov.touch.enums.ResultStateEnum;
import com.szyciov.touch.util.DateUtil;
import com.szyciov.touch.util.InterfaceTokenManager;
import com.szyciov.touch.util.ResultUtil;
import com.szyciov.util.StringUtil;
import com.szyciov.util.TemplateHelper4CarServiceApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shikang on 2017/5/10.
 */

@Service("PartnerManageService")
public class PartnerManageService {

    private static final Logger logger = Logger.getLogger(PartnerManageService.class);

    private TemplateHelper4CarServiceApi carserviceapi = new TemplateHelper4CarServiceApi();

    private PartnerManageDao partnerManageDao;
    @Resource(name = "PartnerManageDao")
    public void setPartnerManageDao(PartnerManageDao partnerManageDao) {
        this.partnerManageDao = partnerManageDao;
    }

    public PubInfoService pubInfoService;
    @Resource(name = "PubInfoService")
    public void setPubInfoService(PubInfoService pubInfoService) {
        this.pubInfoService = pubInfoService;
    }

    /**
     * 获取未支付订单信息
     * @param req
     * @param res
     * @return
     */
    public Map<String,Object> getUnPaidOrder(HttpServletRequest req, HttpServletResponse res) throws Exception {
        //参数校验
        String orderPersonPhone = req.getParameter("orderPersonPhone");
        if(StringUtils.isBlank(orderPersonPhone)){
            Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
            result.put("data",new ArrayList<>());
            return result;
        }
        Map<String, Object> orgUser = pubInfoService.getOrgUserByChannel(orderPersonPhone, req);
        if(null == orgUser || orgUser.isEmpty()) {
            Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
            result.put("data",new ArrayList<>());
            return result;
        }
        Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userid", orgUser.get("id"));
        List<Map<String,Object>> unpayorders = partnerManageDao.getUnPaidOrders(params);
        result.put("data",getResult4GetUnPaidOrder(unpayorders));
        return result;
    }

    /**
     * 订单数据信息转换成OrderSimplesResultDTO
     * @param dataList
     * @return
     * @throws Exception
     */
    private List<OrderSimplesResultDTO> getResult4GetUnPaidOrder(List<Map<String, Object>> dataList) throws Exception {
        if(null == dataList || dataList.isEmpty()) {
            return new ArrayList<OrderSimplesResultDTO>();
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
}
