package com.szyciov.supervision.service.impl;

import com.supervision.enums.CommandEnum;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.supervision.api.dto.BaseApi;
import com.szyciov.supervision.api.dto.operation.*;
import com.szyciov.supervision.config.CacheHelper;
import com.szyciov.supervision.mapper.OperationMapper;
import com.szyciov.supervision.service.OperationService;
import com.szyciov.supervision.util.CarserviceTemplateHelper;
import com.szyciov.supervision.util.HttpUtil;
import com.szyciov.util.NotifyUtil;
import com.szyciov.util.TemplateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 经营信息数据
 * Created by lzw on 2017/8/21.
 */
@Service("operationService")
public class OperationServiceImpl implements OperationService {


    @Autowired
    private OperationMapper operationMapper;

    @Autowired
    private CarserviceTemplateHelper carserviceTemplateHelper;

    private static Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);

    /**
     *
     * @param commandEnum
     * @param map
     * @return
     */
    @Override
    public BaseApi execute(CommandEnum commandEnum, Map<String, String> map) {
        switch (commandEnum){
            case VehicleOperationOnline:
                return  this.vehicleOperationOnline(map);
            case VehicleOperationOffline:
                return  this.vehicleOperationOffline(map);
            case OperationPay:
                return  this.operationPay(map);
            case OperationDeparture:
                return  this.operationDeparture(map);
            case OperationArrival:
                return this.operationArrival(map);


        }
        return null;
    }

    /**
     * 营运上线
     * @param map
     * @return
     */
    @Override
    public VehicleOperationOnline vehicleOperationOnline(Map<String, String> map) {
        String driverId=map.get("driverId");
        VehicleOperationOnline vehicleOperationOnline=operationMapper.vehicleOperationOnline(driverId);
        if(vehicleOperationOnline!=null){
            vehicleOperationOnline.setLoginTime(map.get("loginTime"));
            vehicleOperationOnline.setEncrypt(CacheHelper.getMapEncrypt());
            vehicleOperationOnline.setLongitude(map.get("longitude"));
            vehicleOperationOnline.setLatitude(map.get("latitude"));
        }
        return vehicleOperationOnline;
    }

    /**
     * 营运下线
     * @param map
     * @return
     */
    @Override
    public VehicleOperationOffline vehicleOperationOffline(Map<String, String> map) {
        String driverId=map.get("driverId");
        VehicleOperationOffline vehicleOperationOffline=operationMapper.vehicleOperationOffline(driverId);
        if(vehicleOperationOffline!=null){
            vehicleOperationOffline.setLogoutTime(map.get("logoutTime"));
            vehicleOperationOffline.setEncrypt(CacheHelper.getMapEncrypt());
            vehicleOperationOffline.setLongitude(map.get("longitude"));
            vehicleOperationOffline.setLatitude(map.get("latitude"));
        }
        return vehicleOperationOffline;
    }



    /**
     * 营运出发
     * @param map
     * @return
     */
    @Override
    public OperationDeparture operationDeparture(Map<String, String> map) {
        String orderno=map.get("orderno");
        String ordertype=map.get("ordertype");
        String usetype=map.get("usetype");

        OperationDeparture operationDeparture=operationMapper.operationDeparture(orderno,ordertype,usetype);
        if(operationDeparture!=null){
            operationDeparture.setEncrypt(CacheHelper.getMapEncrypt());
            String waitMile=getWaitMile(orderno,ordertype,usetype);
            operationDeparture.setWaitMile(waitMile);
            operationDeparture.setFareType(operationDeparture.getFareType().substring(0,16));
            operationDeparture.setDepLatitude(operationDeparture.getDepLatitude().substring(0,10));
            operationDeparture.setDepLongitude(operationDeparture.getDepLongitude().substring(0,10));
        }
        return operationDeparture;
    }

    /**
     * 获取空使里程
     * @param orderno
     * @param ordertype
     * @param usetype
     */
    public String getWaitMile(String orderno,String ordertype,String usetype){
        Map<String,Double> map=operationMapper.getWaitMile(orderno,ordertype,usetype);
        if(map==null){
            return "0";
        }else {
            //开始计算
            BaiduApiQueryParam baiduparam = new BaiduApiQueryParam();
            baiduparam.setOrderStartLng(map.get("startlng"));
            baiduparam.setOrderStartLat(map.get("startllat"));
            baiduparam.setOrderEndLat(map.get("departurelat"));
            baiduparam.setOrderEndLng(map.get("departurelng"));
            Map<String,Object> resultmap=carserviceTemplateHelper.dealRequest("BaiduApi/GetMileageInfo",HttpMethod.POST, null, baiduparam, Map.class);
            if((int)resultmap.get("status")==0){
                return (int) resultmap.get("distance")+"";
            }else {
                logger.error("计算空驶里程失败："+baiduparam);
                return "0";
            }
        }
    }


    /**
     * 营运到达
     * @param map
     * @return
     */
    @Override
    public OperationArrival operationArrival(Map<String, String> map) {
        String orderno=map.get("orderno");
        String ordertype=map.get("ordertype");
        String usetype=map.get("usetype");
        OperationArrival operationArrival=operationMapper.operationArrival(orderno,ordertype,usetype);
        if(operationArrival!=null){
            operationArrival.setEncrypt(CacheHelper.getMapEncrypt());
            operationArrival.setDepLatitude(operationArrival.getDepLatitude().substring(0,10));
            operationArrival.setDepLongitude(operationArrival.getDepLongitude().substring(0,10));
            operationArrival.setDestLatitude(operationArrival.getDestLatitude().substring(0,10));
            operationArrival.setDestLongitude(operationArrival.getDepLongitude().substring(0,10));
        }
        return operationArrival;
    }

    /**
     * 营运支付
     * @param map
     * @return
     */
    @Override
    public OperationPay operationPay(Map<String, String> map) {
        String orderno=map.get("orderno");
        String ordertype=map.get("ordertype");
        String usetype=map.get("usetype");
        OperationPay operationPay=operationMapper.operationPay(orderno,ordertype,usetype);
        if(operationPay!=null){
            String waitMile=getWaitMile(orderno,ordertype,usetype);
            operationPay.setWaitMile(waitMile);
            operationPay.setDepLatitude(operationPay.getDepLatitude().substring(0,10));
            operationPay.setDepLongitude(operationPay.getDepLongitude().substring(0,10));
            operationPay.setDestLatitude(operationPay.getDestLatitude().substring(0,10));
            operationPay.setDestLongitude(operationPay.getDepLongitude().substring(0,10));
            operationPay.setFareType(operationPay.getFareType().substring(0,16));
            if(operationPay.getModel()== null){//实际车型为空
                operationPay.setModel(operationPay.getBookModel());
            }
        }
        return  operationPay;
    }
}
