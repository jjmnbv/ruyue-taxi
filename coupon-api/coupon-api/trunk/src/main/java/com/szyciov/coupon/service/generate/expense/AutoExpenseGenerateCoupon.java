package com.szyciov.coupon.service.generate.expense;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.szyciov.coupon.dao.OrderDao;
import com.szyciov.coupon.dao.PubCouponActivityDao;
import com.szyciov.coupon.dao.PubCouponActivityUseCityDao;
import com.szyciov.coupon.dto.CouponActivityDTO;
import com.szyciov.coupon.dto.CouponOrderCacheDTO;
import com.szyciov.coupon.dto.OrderInfoDTO;
import com.szyciov.coupon.factory.generate.GenerateCouponFactory;
import com.szyciov.coupon.param.CouponActivityQueryParam;
import com.szyciov.coupon.param.OrderQueryParam;
import com.szyciov.coupon.service.RedisService;
import com.szyciov.coupon.service.generate.AbstractGenerateCoupon;
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
 * 每日自动生成消费类型抵用券
 * @author LC
 * @date 2017/7/25
 */
@Service
public class AutoExpenseGenerateCoupon {

    @Resource
    private OrderDao orderDao;

    @Resource
    private PubCouponActivityDao activityDao;

    @Resource
    private PubCouponActivityUseCityDao useCityDao;



    public void autoGenerateCoupon(){
        System.out.println("----------------");
        LocalDate nowDt = LocalDate.now().minusDays(1);
        CouponActivityQueryParam queryParam = new CouponActivityQueryParam();
        queryParam.setNowDt(nowDt);
        //类型为消费类型
        queryParam.setRuleType(CouponActivityEnum.SEND_RULE_CONSUME.code);

        //获取发放时间在当前范围之内的派发活动
        List<CouponActivityDTO> activityList = activityDao.listValidActivity(queryParam);

        Set<String> tables = new HashSet<>();
        if(activityList!=null && activityList.size()>0){
            for(CouponActivityDTO dto:activityList){
                String tbName = this.getOrderTbName(dto.getSendservicetype(), dto.getSendruletarget());
                tables.add(tbName);
            }
        }

        for(String tbName:tables) {
            //获取表名称
            OrderQueryParam orderParam = new OrderQueryParam();
            orderParam.setStartDt(nowDt.atStartOfDay());
            orderParam.setEndDt(nowDt.atTime(23, 59, 59));
            orderParam.setTbName(tbName);
            //生成抵用券
            this.generateCoupon(orderParam);
        }


    }



    public void generateCoupon(OrderQueryParam orderParam){


        ////如果规则为按频次统计
        //if(rule.getConsumetype().equals(CouponRuleTypeEnum.FRENQUENCY.value)){
        //    orderInfoDTOs =  orderDao.listOrderByEndTime(orderParam);
        //
        //}
        //
        ////如果规则为按金额统计
        //if(rule.getConsumetype().equals(CouponRuleTypeEnum.MONETARY.value)){
        //    orderInfoDTOs = orderDao.listOrderByComplTime(orderParam);
        //}
        //
        List<OrderInfoDTO> orderInfoDTOs  = orderDao.listOrderByConsume(orderParam);

        if(orderInfoDTOs!=null){
            for(OrderInfoDTO dto:orderInfoDTOs){
                GenerateCouponParam param = new GenerateCouponParam();
                param.setType(CouponActivityEnum.SEND_RULE_CONSUME.code);
                param.setUserType(this.getRuleTargetByTbName(orderParam.getTbName()));
                param.setCompanyId(dto.getCompanyid());
                param.setUserId(dto.getUserid());
                param.setCityCode(dto.getOncity());
                GenerateCouponFactory.expenseGenerateCoupon().generate(param);
            }
        }
    }



    private Integer getRuleTargetByTbName(String tbName){
        if("org_order".equals(tbName)||"org_taxiorder".equals(tbName)){
            return CouponActivityEnum.TARGET_ORGAN_USER.code;
        }
        if("op_order".equals(tbName)||"op_taxiorder".equals(tbName)){

            return CouponActivityEnum.TARGET_USER.code;
        }

        return null;
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
 