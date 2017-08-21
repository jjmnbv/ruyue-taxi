package com.szyciov.coupon.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.coupon.dto.CouponUseInfoDTO;
import com.szyciov.coupon.dto.GenerateCouponDTO;
import com.szyciov.coupon.factory.generate.GenerateCoupon;
import com.szyciov.coupon.factory.generate.GenerateCouponFactory;
import com.szyciov.coupon.param.PubCouponQueryParam;
import com.szyciov.coupon.rabbitMq.coupon.SenderCouponQueue;
import com.szyciov.coupon.service.PubCouponService;
import com.szyciov.coupon.service.PubCouponUseService;
import com.szyciov.coupon.util.ResultData;
import com.szyciov.dto.coupon.CouponInfoDTO;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.enums.ServiceState;
import com.szyciov.enums.coupon.CouponEnum;
import com.szyciov.param.coupon.CouponExpenseParam;
import com.szyciov.param.coupon.CouponRequestParam;
import com.szyciov.param.coupon.CouponReserveParam;
import com.szyciov.param.coupon.CouponUseParam;
import com.szyciov.param.coupon.GenerateCouponParam;
import com.szyciov.util.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 抵用券处理controller
 * @author LC
 * @date 2017/7/28
 */
@RestController
@RequestMapping("/coupon")
@Api(value = "抵用券处理相关接口")
public class PubCouponController {

    private Logger logger = LoggerFactory.getLogger(PubCouponController.class);

    @Resource
    private PubCouponService couponService;

    @Resource
    private PubCouponUseService couponUseService;

    @Resource
    private SenderCouponQueue senderCouponQueue;
    /**
     * 同步生成抵用券，并返回结果
     * @return
     */
    @ApiOperation(value="生成抵用券", notes="根据参数同步生成抵用券并返回结果")
    @ApiImplicitParam(name = "param", value = "生成抵用券参数", required = true, dataType = "GenerateCouponParam")
    @RequestMapping(value = "/sysn/generate",method = RequestMethod.POST)
    @ResponseBody
    public String generateCoupon(@RequestBody GenerateCouponParam param) {

        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {

            //GenerateCouponParam param = GsonUtil.fromJson(jsonStr, GenerateCouponParam.class);

            List<GenerateCouponDTO> dtoList = null;

            GenerateCoupon generateCoupon =  GenerateCouponFactory.generateCoupon(param.getType());
            if(generateCoupon!=null) {
                dtoList = generateCoupon.generate(param);
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
    /**
     * 自动生成抵用券
     * @param   jsonStr
     * @return
     */
    @ApiOperation(value="自动生成抵用券", notes="根据参数异步生成抵用券")
    @ApiImplicitParam(name = "param", value = "生成抵用券参数", required = true,dataType = "PubCouponActivityDto")
    @RequestMapping(value = "/generate/auto",method = RequestMethod.POST)
    @ResponseBody
    public String autoGenerateCoupon(@RequestBody PubCouponActivityDto param) {

        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {

            couponService.aotuGenerateCoupon(param);

        }catch (Exception e){
            resultData.setStatus(ServiceState.EXCEPTION.code);
            resultData.setMessage(ServiceState.EXCEPTION.msg);
            logger.error("生成抵用券失败，异常信息：",e);
        }
        return GsonUtil.toJson(resultData);
    }

    /**
     * 生成抵用券（异步生成）
     * @param   jsonStr
     * @return
     */
    @ApiOperation(value="异步生成抵用券", notes="根据参数异步生成抵用券")
    @ApiImplicitParam(name = "param", value = "生成抵用券参数", required = true, dataType = "GenerateCouponParam")
    @RequestMapping(value = "/generate",method = RequestMethod.POST)
    @ResponseBody
    public String senderGenerateCoupon(@RequestBody GenerateCouponParam param) {

        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {
            String jsonStr = GsonUtil.toJson(param);
            senderCouponQueue.pushGenerateMsg(jsonStr);
        }catch (Exception e){
            resultData.setStatus(ServiceState.EXCEPTION.code);
            resultData.setMessage(ServiceState.EXCEPTION.msg);
            logger.error("生成抵用券失败，异常信息：",e);
        }
        return GsonUtil.toJson(resultData);
    }

    /**
     * 返回当前可用的券
     * @param jsonStr
     * @return
     */
    @ApiOperation(value="返回当前可用的抵用券")
    @ApiImplicitParam(name = "param", value = "请求参数", required = true, dataType = "CouponRequestParam")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public String listCoupon(@RequestBody CouponRequestParam param) {
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {

            //CouponRequestParam param = GsonUtil.fromJson(jsonStr, CouponRequestParam.class);
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

    /**
     * 返回所有未使用的券
     * @param jsonStr
     * @return
     */
    @ApiOperation(value="返回所有未使用的抵用券")
    @ApiImplicitParam(name = "param", value = "请求参数", required = true, dataType = "CouponRequestParam")
    @RequestMapping(value = "/all/list",method = RequestMethod.POST)
    @ResponseBody
    public String allListCoupon(@RequestBody CouponRequestParam param) {
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {

            //CouponRequestParam param = GsonUtil.fromJson(jsonStr, CouponRequestParam.class);
            PubCouponQueryParam queryParam = new PubCouponQueryParam();

            queryParam.setCity(param.getCityCode());
            queryParam.setCompanyid(param.getCompanyId());
            queryParam.setCouponstatus(CouponEnum.COUPON_STATUS_UN_USE.code);
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


    @ApiOperation(value="返回个人可用最大抵用券")
    @ApiImplicitParam(name = "param", value = "请求参数", required = true, dataType = "CouponRequestParam")
    @RequestMapping(value = "/get/max",method = RequestMethod.POST)
    @ResponseBody
    public String getMaxCoupon(@RequestBody CouponRequestParam param) {
        logger.info("返回个人可用最大抵用券，请求参数：{}",GsonUtil.toJson(param));
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);
        try {
            //CouponRequestParam param = GsonUtil.fromJson(jsonStr, CouponRequestParam.class);
            if(StringUtils.isEmpty(param.getUserId())){
                throw new Exception("用户ID缺失");
            }

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
    @ApiOperation(value="预约抵用券")
    @ApiImplicitParam(name = "param", value = "请求参数", required = true, dataType = "CouponReserveParam")
    @RequestMapping(value = "/reserve",method = RequestMethod.POST)
    @ResponseBody
    public String reserveCoupon(@RequestBody CouponReserveParam param) {
        logger.info("预约抵用券请求参数：{}",GsonUtil.toJson(param));
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);
        try {
            //CouponReserveParam param = GsonUtil.fromJson(jsonStr, CouponReserveParam.class);
            boolean isSuccess = false;
            String msg = "锁定失败，该抵用券已经被锁定或使用";
            if(StringUtils.isNotEmpty(param.getCity())&&StringUtils.isNotEmpty(param.getCouponId())) {
                if (couponService.isOnCity(param.getCity(), param.getCouponId())) {
                    isSuccess = couponService.couponReserve(param);

                    if (isSuccess) {
                        msg = "锁定成功";
                    }
                } else {
                    msg = "锁定失败，该城市不在该抵用券可使用城市范围之内";
                }
            }
            //执行待生成缓存任务
            couponService.executeStayGenerate(param.getUserId(),param.getCity());
            Map<String, Object> resuleMap = new HashMap<>();
            resuleMap.put("isReserve", isSuccess);
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
    @ApiOperation(value="消费抵用券")
    @ApiImplicitParam(name = "param", value = "请求参数", required = true, dataType = "CouponExpenseParam")
    @RequestMapping(value = "/expense",method = RequestMethod.POST)
    @ResponseBody
    public String expenseCoupon(@RequestBody CouponExpenseParam param) {
        logger.info("消费抵用券请求参数：{}",GsonUtil.toJson(param));
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {
            boolean isSuccess = false;
            String msg = "消费失败，该抵用券已经被锁定或使用";
            //CouponExpenseParam param = GsonUtil.fromJson(jsonStr, CouponExpenseParam.class);
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
    @ApiOperation(value="更新实际抵扣金额")
    @ApiImplicitParam(name = "param", value = "请求参数", required = true, dataType = "CouponUseParam")
    @RequestMapping(value = "/update/actualamount",method = RequestMethod.POST)
    @ResponseBody
    public String updateActualamount(@RequestBody CouponUseParam param) {
        logger.info("更新实际抵扣金额请求参数：{}",GsonUtil.toJson(param));
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {
            //CouponUseParam param = GsonUtil.fromJson(jsonStr, CouponUseParam.class);

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
    @ApiOperation(value="废弃抵用券")
    @ApiImplicitParam(name = "param", value = "请求参数", required = true, dataType = "CouponRequestParam")
    @RequestMapping(value = "/abandon",method = RequestMethod.POST)
    @ResponseBody
    public String coponAbandon(@RequestBody CouponRequestParam param) {
        logger.info("废弃抵用券请求参数：{}",GsonUtil.toJson(param));
        ResultData resultData = new ResultData();
        resultData.setStatus(ServiceState.SUCCESS.code);
        resultData.setMessage(ServiceState.SUCCESS.msg);

        try {
            //CouponRequestParam param = GsonUtil.fromJson(jsonStr, CouponRequestParam.class);

            couponService.coponAbandon(param.getUserId());

        }catch (Exception e){
            resultData.setStatus(ServiceState.EXCEPTION.code);
            resultData.setMessage(ServiceState.EXCEPTION.msg);
            logger.error("废弃抵用券，异常信息：",e);
        }
        return GsonUtil.toJson(resultData);
    }


}
