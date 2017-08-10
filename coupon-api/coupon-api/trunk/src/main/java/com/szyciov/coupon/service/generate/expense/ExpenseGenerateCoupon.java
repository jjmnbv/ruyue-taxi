package com.szyciov.coupon.service.generate.expense;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.szyciov.coupon.dao.OrderDao;
import com.szyciov.coupon.dto.CouponOrderCacheDTO;
import com.szyciov.coupon.param.OrderQueryParam;
import com.szyciov.coupon.service.RedisService;
import com.szyciov.coupon.service.generate.AbstractGenerateCoupon;
import com.szyciov.coupon.util.GenerateLogUtil;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCoupon;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.enums.CouponRuleTypeEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.enums.coupon.CouponActivityEnum;
import com.szyciov.param.coupon.GenerateCouponParam;
import com.szyciov.util.GsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * 消费类型抵用券生成
 *
 * @author LC
 * @date 2017/7/25
 */
@Service("expenseGenerateCoupon")
public class ExpenseGenerateCoupon extends AbstractGenerateCoupon {

    @Resource
    private OrderDao orderDao;

    @Resource
    private RedisService redisService;


    @Override
    protected boolean validRule(PubCouponRule rule,
                                GenerateCouponParam param,
                                PubCouponActivityDto activity) {
        LocalDate startDt = LocalDate.parse(activity.getSendstarttime());
        //如果当前日期为活动发放日的第一天，则跳过
        if(startDt.until(LocalDate.now(),DAYS)==0){
            return false;
        }

        //获取表名
        String tbName = this.getOrderTbName(activity.getSendservicetype(),activity.getSendruletarget());


        OrderQueryParam param1 = new OrderQueryParam();
        param1.setUserId(param.getUserId());
        param1.setStartDt(LocalDate.now().minusDays(1).atStartOfDay());
        //截止时间为上一天23小时59分59秒
        param1.setEndDt(LocalDate.now().minusDays(1).atTime(23,59,59));
        param1.setTbName(tbName);
        param1.setCompanyid(activity.getLecompanyid());
        param1.setCityCode(param.getCityCode());

        //如果规则为按频次统计
        if(rule.getConsumetype().equals(CouponRuleTypeEnum.FRENQUENCY.value)){
            return this.validOrderCount(rule,param1,activity);
        }

        //如果规则为按频次统计
        if(rule.getConsumetype().equals(CouponRuleTypeEnum.MONETARY.value)){
            return this.validOrderMoney(rule,param1,activity);
        }


        return false;
    }

    /**
     * 验证频次
     * @param rule
     * @param param1
     * @param activity
     * @return
     */
    private boolean validOrderCount(PubCouponRule rule,
                                    OrderQueryParam param1,
                                    PubCouponActivityDto activity){

        boolean isValid = false;

        String userId = param1.getUserId();

        String key = RedisKeyEnum.COUPON_ORDER_COUNT.code+activity.getId();
        //获取缓存中存储的历史订单次数纪录
        String countStr = redisService.hmGet(key,userId);

        int countCache = 0;
        int cycleDayCache = 0;
        //如果缓存中不为空
        if(StringUtils.isNotEmpty(countStr)) {
            CouponOrderCacheDTO  dto = GsonUtil.fromJson(countStr, CouponOrderCacheDTO.class);
            //如果缓存中存储的时间不为2天前，则证明该用户订单没有连续，则清除缓存
            if(!dto.getDate().equals(LocalDate.now().minusDays(BEFORE_NOW_DAYS))){
                redisService.hmDel(key,userId);
            //如果缓存中的周期>=规则设置的周期，则需要重新查询数据库
            } else if(dto.getCycleday()>=rule.getCycleday()){
                param1.setStartDt(this.getStartDt(activity,rule));
            }else{
                countCache = dto.getCount();
                cycleDayCache = dto.getCycleday();
            }
        }


        //从数据库获取订单次数+缓存记录次数
        Integer count = orderDao.getOrderCount(param1)+countCache;


        //如果订单次数=0，则代表不为连续周期，清除缓存数据
        if(count==0){
            redisService.hmDel(key,userId);
            return false;
        }


        //如果是满
        if(rule.getConsumefrequencytype().equals(CouponRuleTypeEnum.FULL.value)){
            if(count >= rule.getConsumelowtimes()){
                isValid= true;
            }
        }else
        //如果是满于低于
        if(rule.getConsumefrequencytype().equals(CouponRuleTypeEnum.FULL_LOW.value)){
            if(count >= rule.getConsumelowtimes() && count <= rule.getConsumehightimes()){
                isValid= true;
            }
        }else
        //如果是满于低于
        if(rule.getConsumefrequencytype().equals(CouponRuleTypeEnum.LOW.value)){
            if(count<= rule.getConsumehightimes()){
                isValid= true;
            }
        }

        if(!isValid) {
            //设置连续对象 缓存数据
            CouponOrderCacheDTO dto = new CouponOrderCacheDTO();
            dto.setCount(count);
            dto.setDate(LocalDate.now().minusDays(1));
            dto.setCycleday(cycleDayCache + 1);
            redisService.hmSet(key, userId, GsonUtil.toJson(dto));

            long expireTime = LocalDateTime.now().until(LocalDate.parse(activity.getSendendtime()).atTime(23, 59, 59),
                SECONDS);
            redisService.expire(key, expireTime, TimeUnit.SECONDS);
        }else{
            redisService.hmDel(key,userId);
        }
        return isValid;
    }

    /**
     * 根据周期 获取开始时间
     * @param activity  活动对象
     * @param rule      规则对象
     * @return
     */
    private LocalDateTime getStartDt(PubCouponActivityDto activity,PubCouponRule rule){

        //获取周期最早时间
        LocalDateTime dateTime = LocalDate.now().minusDays(rule.getCycleday()).atStartOfDay();
        //获取活动发放最早时间
        LocalDateTime startTime = LocalDate.parse(activity.getSendstarttime()).atStartOfDay();

        //如果周期最早时间 大于或等于活动开始时间，则以周期最早时间为订单查询最早时间
        if(startTime.until(dateTime, DAYS)>=0){
            return dateTime;
        }

        return startTime;
    }

    @Override
    protected boolean validHaved(PubCouponActivityDto activity, String userId) {
        String key = RedisKeyEnum.COUPON_HAVE.code+activity.getId();
        String countStr = redisService.hmGet(key,userId);

        //获取活动发放开始时间
        LocalDate startDt = LocalDate.parse(activity.getSendstarttime());
        LocalDate endDt = LocalDate.parse(activity.getSendendtime());

        //因活动有效截止日期为截止日期的23:59:59，则应在时间差上+1天
        long days = startDt.until(endDt,DAYS)+1;

        if(StringUtils.isNotEmpty(countStr)) {
            Integer count = Integer.parseInt(countStr);
            //消费模式下，可获得券的最大数量为活动发放天数
            if (count != null && count >= days) {
                return false;
            }
        }
        return true;
    }



    private boolean validOrderMoney(PubCouponRule rule,
                                       OrderQueryParam param1,
                                       PubCouponActivityDto activity){


        //如果开启了单次消费，则根据单次消费发券
        if(CouponRuleTypeEnum.USABLE.value.equals(rule.getConsumemoneysingleable())) {
            savePouconOrderMoneyOnce(rule, param1, activity);
        }

        //如果开启了周期消费，则根据周期消费发券
        if(CouponRuleTypeEnum.USABLE.value.equals(rule.getConsumemoneycycleable())) {
            return validOrderMoneySum(rule, param1, activity);

        }

        return false;
    }

    /**
     * 验证周期金额和
     * @param rule
     * @param param1
     * @param activity
     * @return
     */
    private boolean validOrderMoneySum(PubCouponRule rule,
                                    OrderQueryParam param1,
                                    PubCouponActivityDto activity){

        boolean isValid = false;
        String userId = param1.getUserId();

        String key = RedisKeyEnum.COUPON_ORDER_MONEY.code+activity.getId();
        //获取缓存中存储的历史订单次数纪录
        String countStr = redisService.hmGet(key,userId);

        double sumCache = 0;
        int cycleDayCache = 0;
        //如果缓存中不为空
        if(StringUtils.isNotEmpty(countStr)) {
            CouponOrderCacheDTO  dto = GsonUtil.fromJson(countStr, CouponOrderCacheDTO.class);
            //如果缓存中存储的时间不为2天前，则证明该用户订单没有连续，则清除缓存
            if(!dto.getDate().equals(LocalDate.now().minusDays(BEFORE_NOW_DAYS))){
                redisService.hmDel(key,userId);
                //如果缓存中的周期>=规则设置的周期，则需要重新查询数据库
            }if(dto.getCycleday()>=rule.getCycleday()){
                param1.setStartDt(this.getStartDt(activity,rule));
            }else{
                sumCache = dto.getMoney();
                cycleDayCache = dto.getCycleday();
            }
        }


        //从数据库获取订单次数+缓存记录次数
        List<Double> moneyList = orderDao.listOrderMoney(param1);

        //如果金额次数=0，则代表不为连续周期，清除缓存数据
        if(moneyList.size()==0){
            redisService.hmDel(key,userId);
            return false;
        }

        //初始化为缓存的总金额
        double moneySum = sumCache;
        //计算金额总额
        for(Double money:moneyList){
            moneySum+=money;
        }

        //如果是满
        if(rule.getConsumemoneycycletype().equals(CouponRuleTypeEnum.FULL.value)){
            if(moneySum >= rule.getConsumemoneycyclelow()){
                isValid= true;
            }
        }else
        //如果是满于低于
        if(rule.getConsumemoneycycletype().equals(CouponRuleTypeEnum.FULL_LOW.value)){
            if(moneySum >= rule.getConsumemoneycyclelow() && moneySum<= rule.getConsumemoneycyclefull()){
                isValid= true;
            }
        }else
        //如果是满于低于
        if(rule.getConsumemoneycycletype().equals(CouponRuleTypeEnum.LOW.value)){
            if(moneySum<= rule.getConsumemoneycyclefull()){
                isValid= true;
            }
        }

        if(!isValid) {
            //设置连续对象 缓存数据
            CouponOrderCacheDTO dto = new CouponOrderCacheDTO();
            dto.setMoney(moneySum);
            dto.setDate(LocalDate.now().minusDays(1));
            dto.setCycleday(cycleDayCache + 1);
            redisService.hmSet(key, userId, GsonUtil.toJson(dto));

            long expireTime = LocalDateTime.now().until(LocalDate.parse(activity.getSendendtime()).atTime(23, 59, 59),
                SECONDS);
            redisService.expire(key, expireTime, TimeUnit.SECONDS);
        }else{
            redisService.hmDel(key,userId);
        }
        return isValid;
    }

    /**
     * 保存一次订单消费
     * @param rule          订单规则
     * @param param1        参数
     * @param activity      活动
     */
    private void savePouconOrderMoneyOnce(PubCouponRule rule,
                                       OrderQueryParam param1,
                                       PubCouponActivityDto activity){



        //从数据库获取订单次数
        List<Double> moneyList = orderDao.listOrderMoney(param1);

        for (Double money : moneyList) {

            if(validOrderMoney(rule,money)){

                this.saveMoneyCoupon(activity,param1.getUserId());
            }
        }
    }

    /**
     * 验证单次消费
     * @param rule
     * @param money
     * @return
     */
    private boolean validOrderMoney(PubCouponRule rule,Double money){

        if (rule.getConsumemoneysingelfull()<=money){
            return true;
        }

        return  false;
    }

    /**
     * 保存抵用券信息
     * @param activity
     * @param userId
     */
    private void saveMoneyCoupon(PubCouponActivityDto activity,
                                 String userId){

        PubCoupon coupon = this.savePubCoupon(activity,userId);
        this.savePubCouponActivityUseCity(activity.getCitys(),coupon.getId());
        this.saveHaved(activity.getId(),userId,activity.getSendendtime());
    }


    private String getOrderTbName(Integer serviceType,Integer ruletarget){

        //获取网约车订单表
        if(CouponActivityEnum.SERVICE_TYPE_CAR.code.equals(serviceType)) {
             if(CouponActivityEnum.TARGET_ORGAN_USER.code.equals(ruletarget)){
                 return "org_order";
             }
             if(CouponActivityEnum.TARGET_USER.code.equals(ruletarget)){

                return "op_order";
             }
        }

        //获取出租车订单表
        if(CouponActivityEnum.SERVICE_TYPE_TAXI.code.equals(serviceType)) {
            if(CouponActivityEnum.TARGET_ORGAN_USER.code.equals(ruletarget)){
                return "org_taxiorder";
            }
            if(CouponActivityEnum.TARGET_USER.code.equals(ruletarget)){
                return "op_taxiorder";
            }
        }
        return null;
    }



}
 