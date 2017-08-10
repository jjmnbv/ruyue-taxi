package com.szyciov.coupon.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.coupon.dto.CouponInfoDTO;
import com.szyciov.coupon.dto.CouponUseInfoDTO;
import com.szyciov.coupon.dto.GenerateCouponDTO;
import com.szyciov.coupon.factory.generate.GenerateCouponFactory;
import com.szyciov.coupon.param.PubCouponQueryParam;
import com.szyciov.coupon.service.PubCouponService;
import com.szyciov.coupon.service.PubCouponUseService;
import com.szyciov.coupon.util.ResultData;
import com.szyciov.enums.CouponRuleTypeEnum;
import com.szyciov.enums.ServiceState;
import com.szyciov.enums.coupon.CouponEnum;
import com.szyciov.param.coupon.CouponExpenseParam;
import com.szyciov.param.coupon.CouponRequestParam;
import com.szyciov.param.coupon.CouponReserveParam;
import com.szyciov.param.coupon.CouponUseParam;
import com.szyciov.param.coupon.GenerateCouponParam;
import com.szyciov.util.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 抵用券处理controller
 * @author LC
 * @date 2017/7/28
 */
@RestController
@RequestMapping("/coupon")
public class PubCouponController {

    private Logger logger = LoggerFactory.getLogger(PubCouponController.class);

    @Resource
    private PubCouponService couponService;

    @Resource
    private PubCouponUseService couponUseService;
    /**
     * 生成抵用券
     * @param
     * @return
     */
    @RequestMapping("/generate")
    @ResponseBody
    public String generateCoupon(@RequestBody String jsonStr) {

        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {

            GenerateCouponParam param = GsonUtil.fromJson(jsonStr, GenerateCouponParam.class);

            List<GenerateCouponDTO> dtoList = null;

            //注册、邀请类型
            if (CouponRuleTypeEnum.REGISTER.value.equals(param.getType()) ||
                CouponRuleTypeEnum.INVITE.value.equals(param.getType())) {
                dtoList = GenerateCouponFactory.registerGenerateCoupon().generate(param);
            }

            //充值类型
            if (CouponRuleTypeEnum.RECHARGE.value.equals(param.getType())) {
                dtoList = GenerateCouponFactory.rechargeGenerateCoupon().generate(param);
            }
            //消费类型
            if (CouponRuleTypeEnum.EXPENSE.value.equals(param.getType())) {
                dtoList = GenerateCouponFactory.expenseGenerateCoupon().generate(param);
            }

            //活动类型
            if (CouponRuleTypeEnum.ACTIVITY.value.equals(param.getType())) {
                dtoList = GenerateCouponFactory.activityGenerateCoupon().generate(param);
            }

            if (dtoList != null && dtoList.size() > 0) {
                resultData.setDataType(ResultData.DATA_TYPE_LIST);
                resultData.setData(GsonUtil.toJson(dtoList));
            } else {
                resultData.setStatus(ServiceState.FAILED.code);
                resultData.setMessage(ServiceState.FAILED.msg);
            }
        }catch (Exception e){
            resultData.setStatus(ServiceState.EXCEPTION.code);
            resultData.setMessage(ServiceState.EXCEPTION.msg);
            logger.error("生成抵用券失败，异常信息：",e);
        }
        return GsonUtil.toJson(resultData);
    }

    @RequestMapping("/list")
    @ResponseBody
    public String listCoupon(@RequestBody String jsonStr) {
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {

            CouponRequestParam param = GsonUtil.fromJson(jsonStr, CouponRequestParam.class);
            PubCouponQueryParam queryParam = new PubCouponQueryParam();

            queryParam.setCity(param.getCityCode());
            queryParam.setCompanyid(param.getCompanyId());
            queryParam.setCouponstatus(CouponEnum.COUPON_STATUS_UN_USE.code);
            queryParam.setNowDt(LocalDate.now());
            queryParam.setUserid(param.getUserId());
            queryParam.setIDisplayStart(param.getiDisplayStart());
            queryParam.setIDisplayLength(param.getiDisplayLength());

            List<CouponInfoDTO> dtos = couponService.listAllCoupon(queryParam);

            if(dtos!=null && dtos.size()>0){
                resultData.setDataType(ResultData.DATA_TYPE_LIST);
                resultData.setData(GsonUtil.toJson(dtos));
            }else{
                resultData.setStatus(ServiceState.FAILED.code);
                resultData.setMessage(ServiceState.FAILED.msg);
            }

        }catch (Exception e){
            resultData.setStatus(ServiceState.EXCEPTION.code);
            resultData.setMessage(ServiceState.EXCEPTION.msg);
            logger.error("返回抵用券列表异常，异常信息：",e);
        }

        return GsonUtil.toJson(resultData);

    }


    @RequestMapping("/get/max")
    @ResponseBody
    public String getMaxCoupon(@RequestBody String jsonStr) {

        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);
        try {
            CouponRequestParam param = GsonUtil.fromJson(jsonStr, CouponRequestParam.class);

            PubCouponQueryParam queryParam = new PubCouponQueryParam();

            queryParam.setCity(param.getCityCode());
            queryParam.setCompanyid(param.getCompanyId());
            queryParam.setCouponstatus(CouponEnum.COUPON_STATUS_UN_USE.code);
            queryParam.setNowDt(LocalDate.now());
            queryParam.setUserid(param.getUserId());
            queryParam.setServicetype(param.getServiceType());
            queryParam.setTarget(param.getUserType());

            CouponInfoDTO dto = couponService.getMaxMoneyCoupon(queryParam);

            if(dto!=null){
                resultData.setDataType(ResultData.DATA_TYPE_SINGLE);
                resultData.setData(GsonUtil.toJson(dto));
            }else{
                resultData.setStatus(ServiceState.FAILED.code);
                resultData.setMessage(ServiceState.FAILED.msg);
            }
        }catch (Exception e){
            resultData.setStatus(ServiceState.EXCEPTION.code);
            resultData.setMessage(ServiceState.EXCEPTION.msg);
            logger.error("返回最大抵用券异常，异常信息：",e);
        }
        return GsonUtil.toJson(resultData);

    }

    /**
     * 预约抵用券
     * @param jsonStr
     * @return
     */
    @RequestMapping("/reserve")
    @ResponseBody
    public String reserveCoupon(@RequestBody String jsonStr) {
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);
        try {
            CouponReserveParam param = GsonUtil.fromJson(jsonStr, CouponReserveParam.class);
            boolean isSuccess = false;
            String msg = "锁定失败，该抵用券已经被锁定或使用";
            if(couponService.isOnCity(param.getCity(),param.getCouponId())) {
                isSuccess = couponService.couponReserve(param);
                if(isSuccess) {
                    msg = "锁定成功";
                }
            }else{
                msg = "锁定失败，该城市不在该抵用券可使用城市范围之内";
            }
            Map<String,Object>  resuleMap = new HashMap<>();
            resuleMap.put("isReserve",isSuccess);
            resuleMap.put("msg", msg);
            resultData.setData(GsonUtil.toJson(resuleMap));
            resultData.setDataType(ResultData.DATA_TYPE_SINGLE);

        }catch (Exception e){
            resultData.setStatus(ServiceState.EXCEPTION.code);
            resultData.setMessage(ServiceState.EXCEPTION.msg);
            logger.error("预约抵用券失败，异常信息：",e);
        }
        return GsonUtil.toJson(resultData);
    }

    /**
     * 消费抵用券
     * @param jsonStr
     * @return
     */
    @RequestMapping("/expense")
    @ResponseBody
    public String expenseCoupon(@RequestBody String jsonStr) {
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {
            boolean isSuccess = false;
            String msg = "消费失败，该抵用券已经被锁定或使用";
            CouponExpenseParam param = GsonUtil.fromJson(jsonStr, CouponExpenseParam.class);
            if(couponService.isOnCity(param.getCity(),param.getCouponId())) {
                isSuccess = couponService.couponExpense(param);
                if(isSuccess) {
                    msg = "消费成功";
                }
            }else{
                msg = "消费失败，该城市不在该抵用券可使用城市范围之内";
            }
            Map<String, Object> resuleMap = new HashMap<>();
            resuleMap.put("isExpense", isSuccess);
            resuleMap.put("msg", msg);
            resultData.setDataType(ResultData.DATA_TYPE_SINGLE);
            resultData.setData(GsonUtil.toJson(resuleMap));
        }catch (Exception e){
            resultData.setStatus(ServiceState.EXCEPTION.code);
            resultData.setMessage(ServiceState.EXCEPTION.msg);
            logger.error("消费抵用券失败，异常信息：",e);
        }
        return GsonUtil.toJson(resultData);
    }



    /**
     * 更新实际抵扣金额
     * @param jsonStr
     * @return
     */
    @RequestMapping("/update/actualamount")
    @ResponseBody
    public String updateActualamount(@RequestBody String jsonStr) {
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {
            CouponUseParam param = GsonUtil.fromJson(jsonStr, CouponUseParam.class);

            CouponUseInfoDTO infoDTO =  couponUseService.updateActualamount(param);
            if(infoDTO!=null){
                resultData.setDataType(ResultData.DATA_TYPE_SINGLE);
                resultData.setData(GsonUtil.toJson(infoDTO));
            }else{
                resultData.setStatus(ServiceState.FAILED.code);
                resultData.setMessage(ServiceState.FAILED.msg);
            }
        }catch (Exception e){
            resultData.setStatus(ServiceState.EXCEPTION.code);
            resultData.setMessage(ServiceState.EXCEPTION.msg);
            logger.error("更新实际抵扣金额失败，异常信息：",e);
        }
        return GsonUtil.toJson(resultData);
    }



    /**
     * 废弃抵用券
     * @param jsonStr
     * @return
     */
    @RequestMapping("/abandon")
    @ResponseBody
    public String coponAbandon(@RequestBody String jsonStr) {
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {
            CouponRequestParam param = GsonUtil.fromJson(jsonStr, CouponRequestParam.class);

            couponService.coponAbandon(param.getUserId());

        }catch (Exception e){
            resultData.setStatus(ServiceState.EXCEPTION.code);
            resultData.setMessage(ServiceState.EXCEPTION.msg);
            logger.error("废弃抵用券，异常信息：",e);
        }
        return GsonUtil.toJson(resultData);
    }




}
