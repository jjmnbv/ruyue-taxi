package com.szyciov.coupon.service.generate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.coupon.dao.PubCouponDao;
import com.szyciov.coupon.dao.PubCouponUseCityDao;
import com.szyciov.coupon.dto.GenerateCouponDTO;
import com.szyciov.coupon.factory.generate.GenerateCoupon;
import com.szyciov.coupon.service.RedisService;
import com.szyciov.coupon.util.GenerateLogUtil;
import com.szyciov.coupon.util.MessageUtil;
import com.szyciov.dto.coupon.CouponInfoDTO;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCoupon;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.entity.coupon.PubCouponUseCity;
import com.szyciov.enums.DataStateEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.enums.coupon.CouponActivityEnum;
import com.szyciov.enums.coupon.CouponEnum;
import com.szyciov.message.CouponMessage;
import com.szyciov.param.coupon.GenerateCouponParam;
import com.szyciov.util.DateUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.GsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 *
 * @author LC
 * @date 2017/8/1
 */
@Service
public abstract class AbstractGenerateCoupon implements GenerateCoupon {

    @Resource
    private PubCouponDao couponDao;

    @Resource
    private PubCouponUseCityDao useCityDao;

    @Resource
    private RedisService redisService;


    private Logger logger = LoggerFactory.getLogger(AbstractGenerateCoupon.class);


    /**
     *  用于判断缓存中数据，是否在当前时间前2天
     *  例：调度为3号0点，则缓存中数据应该为1号，如不是则证明未连续
     */
    protected static final int BEFORE_NOW_DAYS = 2;

    @Override
    public List<GenerateCouponDTO> generate(GenerateCouponParam param) {

        String userId = param.getUserId();

        GenerateLogUtil.writeInfoLog(logger,"用户ID：【{}】,开始生成抵用券:",userId);

        List<GenerateCouponDTO> dtoList = new ArrayList<>();

        //根据抵用券规则获取该规则的抵用券活动
        Map<Object,Object> activityMap = this.getCouponActivity(param);

        GenerateLogUtil.writeInfoLog(logger,"用户ID：【{}】,获取有效活动个数【{}】,获取抵用券参数：【{}】",userId,activityMap.size(),GsonUtil.toJson(param));

        for(Map.Entry<Object,Object> activityEntry : activityMap.entrySet()){
            String activityVal = (String)activityEntry.getValue();

            PubCouponActivityDto activity = GsonUtil.fromJson(activityVal,PubCouponActivityDto.class);
            //获取对应规则对象
            PubCouponRule rule = GsonUtil.fromJson(activity.getCouponrule(),PubCouponRule.class);

            GenerateLogUtil.writeInfoLog(logger,"用户ID：【{}】,获取对应抵用券规则:【{}】",userId,GsonUtil.toJson(rule));
            //验证通过，可以生成抵用券
            if(canGenerate(activity,param,rule,userId)){
                PubCoupon coupon = this.savePubCoupon(activity,userId);
                this.savePubCouponActivityUseCity(activity.getCitys(),coupon.getId());
                this.saveHaved(activity.getId(),userId,activity.getSendendtime());
                GenerateCouponDTO dto = this.createDto(coupon);
                GenerateLogUtil.writeInfoLog(logger,"用户ID：【{}】,抵用券生成完毕，抵用券信息：【{}】",userId,GsonUtil.toJson(dto));
                this.phshMessage(dto,param.getUserPhone(),userId);
                dtoList.add(dto);
                afterGenerated(dto);
            }
        }
        return dtoList;
    }
    /**
     * 创建返回值DTO
     * @param coupon
     * @return
     */
    protected GenerateCouponDTO createDto(PubCoupon coupon){
        GenerateCouponDTO dto = new GenerateCouponDTO();
        dto.setId(coupon.getId());
        dto.setMoney(coupon.getMoney());
        dto.setName(coupon.getName());
        dto.setUserId(coupon.getUserid());
        dto.setCompanyid(coupon.getLecompanyid());
        return dto;
    }

    /**
     * 是否已经领取过
     * @param
     * @param userId
     * @return
     */
    protected boolean validHaved(PubCouponActivityDto activity,String userId){
        String key = RedisKeyEnum.COUPON_HAVE.code+activity.getId();
        String countStr = redisService.hmGet(key,userId);

        if(StringUtils.isNotEmpty(countStr)) {
            Integer count = Integer.parseInt(countStr);
            if (count != null && count == 1) {
                return false;
            }
        }
        return true;
    }


    /**
     * 保存抵用券
     * @param activity
     * @param userId
     * @return
     */
    protected PubCoupon savePubCoupon(PubCouponActivityDto activity, String userId){
        PubCoupon pubCoupon = new PubCoupon();
        pubCoupon.setId(GUIDGenerator.newGUID());
        pubCoupon.setName(activity.getName());
        pubCoupon.setCouponactivyidref(activity.getId());
        pubCoupon.setServicetype(activity.getSendservicetype());
        pubCoupon.setTarget(activity.getSendruletarget());
        //优惠券持有人
        pubCoupon.setUserid(userId);
        //优惠券金额
        pubCoupon.setMoney(this.getCouponMoney(activity));
        //使用区域
        pubCoupon.setUsetype(activity.getUsetype());
        //锁定状态 默认未锁定
        pubCoupon.setLockstate(CouponEnum.COUPON_STATUS_UN_USE.code);
        //优惠券状态 默认未使用
        pubCoupon.setCouponstatus(CouponEnum.COUPON_STATUS_UN_USE.code);
        //有效期开始时间 默认当天
        pubCoupon.setOutimestart(this.getCouponStartDate(activity));
        //有效期结束时间
        pubCoupon.setOuttimeend(this.getCouponEndDate(activity));
        pubCoupon.setLecompanyid(activity.getLecompanyid());
        pubCoupon.setPlatformtype(activity.getPlatformtype());
        pubCoupon.setCreatetime(LocalDateTime.now());
        pubCoupon.setUpdatetime(LocalDateTime.now());
        pubCoupon.setStatus(DataStateEnum.SUCCESS.code);
        pubCoupon.setPlatformtype(activity.getPlatformtype());
        couponDao.savePubCoupon(pubCoupon);
        return pubCoupon;
    }

    /**
     * 保存获取记录
     * @param activityId
     * @param userId
     */
    protected void saveHaved(String activityId,String userId,String sendendtime){
        String key = RedisKeyEnum.COUPON_HAVE.code+activityId;
        long expireTime = LocalDateTime.now().until(LocalDate.parse(sendendtime).atTime(23,59,59), SECONDS);
        List<String> keys = new ArrayList<>();
        keys.add(key);
        keys.add(userId);
        redisService.eval(new ResourceScriptSource(new ClassPathResource("/saveHaved.lua")),keys,expireTime+"");
    }


    /**
     * 保存抵用券有效城市
     * @param citys
     * @param couponId
     */
    protected void  savePubCouponActivityUseCity(String citys,String couponId){

        if(StringUtils.isNotEmpty(citys)){
            for(String cityCode:citys.split(",")){
                PubCouponUseCity useCity = new PubCouponUseCity();
                useCity.setId(GUIDGenerator.newGUID());
                useCity.setCouponidref(couponId);
                useCity.setCity(cityCode);
                useCity.setStatus(DataStateEnum.SUCCESS.code);
                useCity.setCreatetime(LocalDateTime.now());
                useCity.setUpdatetime(LocalDateTime.now());
                useCityDao.saveUseCity(useCity);
            }
        }
    }

    /**
     * 获取抵用券金额
     * @param activity
     *
     */

    private Double getCouponMoney(PubCouponActivityDto activity){
        Double money = 0.0;
        if(CouponActivityEnum.MONEY_TYPE_FIXED.code.equals(activity.getSendmoneytype())){
            money=  activity.getSendfixedmoney();
        }else{
            /**
             * 因范围 1-3 需包含3，所以在最大金额上+1,
             */
            double randomMoney = Math.random()*((activity.getSendhighmoney()+1)-activity.getSendlowmoney()) + activity.getSendlowmoney();
            //进行四舍五入取整
            money = BigDecimal.valueOf(randomMoney).divide(new BigDecimal(1),0, RoundingMode.HALF_DOWN).doubleValue();
        }
        return money;
    }

    /**
     * 获取抵用券结束时间
     * @param activity
     * @return
     */
    protected LocalDate getCouponEndDate(PubCouponActivityDto activity){

        //如果是固定时间,则直接返回设定终止时间
        if(CouponActivityEnum.OUT_TIME_TYPE_FIXED.code.equals(activity.getOutimetype())){
            return LocalDate.parse(activity.getFixedendtime());
        }else{
            return LocalDate.now().plusDays(activity.getSendtimeinday());
        }
    }

    /**
     * 获取抵用券有效期开始时间
     * @param activity
     * @return
     */
    private LocalDate getCouponStartDate(PubCouponActivityDto activity){

        //如果是固定时间,则直接返回设定的开始时间
        if(CouponActivityEnum.OUT_TIME_TYPE_FIXED.code.equals(activity.getOutimetype())){
            return LocalDate.parse(activity.getFixedstarttime());
        }else{
            return LocalDate.now();
        }
    }

    /**
     * 是否生成抵用券
     * @param activity
     * @return
     */
    protected boolean canGenerate(PubCouponActivityDto activity, GenerateCouponParam param,
                                PubCouponRule rule, String userId){

        //验证是否领取过
        if(!validHaved(activity,userId)){
            GenerateLogUtil.writeInfoLog(logger,"用户ID：【{}】,活动ID:【{}】,用户获取该活动抵用券数量已达上限",userId,activity.getId());
            return false;
        }
        //验证发放时间
        if(!validTime(activity)){
            GenerateLogUtil.writeInfoLog(logger,"用户ID：【{}】,活动ID:【{}】,当前时间不在活动发放有效期之内，活动发放时间:【{}】",userId,activity.getId(),activity.getSendstarttime()+"至",activity.getSendendtime());
            return false;
        }

        //验证发放城市
        if(!validCity(activity,param.getCityCode())){
            GenerateLogUtil.writeInfoLog(logger,"用户ID：【{}】,活动ID:【{}】,当前城市不在活动发放城市之内，当前城市:【{}】，活动发放城市:【{}】",userId,activity.getId(),param.getCityCode(),activity.getCitys());
            return false;
        }
        //验证是否符合发放规则
        if(!validRule(rule,param,activity)){
            GenerateLogUtil.writeInfoLog(logger,"用户ID：【{}】,活动ID:【{}】,当前条件不满足抵用券发放规则！",userId,activity.getId());
            return false;
        }

        return true;
    }

    /**
     * 验证当前时间是否在有效时间范围之内
     * @param activity
     * @return
     */
    private boolean validTime(PubCouponActivityDto activity){
        return DateUtil.nowDateBetween(activity.getSendstarttime(),activity.getSendendtime());
    }

    /**
     * 验证发放区域
     * @param activity
     * @return
     */
    protected boolean validCity(PubCouponActivityDto activity,String cityCode){
        if(activity.getCitys()!=null && cityCode!=null) {
            if (activity.getCitys().indexOf(cityCode) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证发放规则
     * @return
     */
    protected abstract boolean validRule(PubCouponRule rule,GenerateCouponParam param,PubCouponActivityDto activity);

    /**
     * 生成券完成后操作
     * @param dto
     * @return
     */
    protected abstract boolean afterGenerated(GenerateCouponDTO dto);



    /**
     * 获取抵用券规则http://www.sohu.com/a/161500020_554746

    protected Map<Object,Object> getCouponRule(GenerateCouponParam param){
        //抵用券规则key
        String ruleKey = RedisKeyEnum.COUPON_RULE.code+param.getCompanyid()+"_"+param.getUsertype()+"_"+param.getType();
        //抵用券规则信息
        return redisService.hGetAll(ruleKey);
    }*/

    /**
     * 获取抵用券活动信息
     * @param
     * @return
     */
    protected Map<Object,Object> getCouponActivity(GenerateCouponParam param){
        //抵用券规则key
        String ruleKey = RedisKeyEnum.COUPON_ACTIVY.code+param.getCompanyId()+"_"+param.getUserType()+"_"+param.getType();
        //抵用券规则信息
        return redisService.hGetAll(ruleKey);
    }

    /**
     * 发送推送
     * @param phone
     */
    protected void  phshMessage(GenerateCouponDTO coupon,String phone,String userId){
        GenerateLogUtil.writeInfoLog(logger,"用户ID：【{}】,发送推送内容：【{}】",userId,GsonUtil.toJson(coupon));
        List<String> userPhone = new ArrayList<>();
        userPhone.add(phone);
        CouponInfoDTO infoDTO = new CouponInfoDTO();
        infoDTO.setName(coupon.getName());
        infoDTO.setMoney(coupon.getMoney());
        CouponMessage couponMessage = new CouponMessage(infoDTO,"",userPhone);
        MessageUtil.sendMessage(couponMessage);

        GenerateLogUtil.writeInfoLog(logger,"用户ID：【{}】,发送推送完毕！",userId);
    }

}
 