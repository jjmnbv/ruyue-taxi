package com.szyciov.carservice.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.szyciov.entity.PubPremiumRule;
import com.szyciov.entity.PubPremiumRuleDatedetail;
import com.szyciov.entity.PubPremiumRuleWeekdetail;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.PubPremiumRuleEnum;
import com.szyciov.param.PremiumRuleParam;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by shikang on 2017/7/28.
 */
@Service("PremiumRuleService")
public class PremiumRuleService {

    /**
     * 查询溢价倍率
     * @param param
     * @return
     */
    public Map<String, Object> getPremiumrate(PremiumRuleParam param) {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("status", Retcode.OK.code);
        ret.put("message", Retcode.OK.msg);
        ret.put("premiumrate", 1.0d);
        ret.put("ruletype", null);
        ret.put("premiumdetailid", null);

        //查询溢价规则
        List<PubPremiumRule> premiumRulesList = getPubPremiumRule(param);
        if(null != premiumRulesList && !premiumRulesList.isEmpty()) {
            PubPremiumRuleWeekdetail weekdetail = null;
            PubPremiumRuleDatedetail datedetail = null;
            for (PubPremiumRule premiumRule : premiumRulesList) {
                if(PubPremiumRuleEnum.RULETYPE_WEEK.code == premiumRule.getRuletype()) { //按星期
                    weekdetail = getPremiumrateByWeek(premiumRule, param.getUsetime());
                } else if(PubPremiumRuleEnum.RULETYPE_DATE.code == premiumRule.getRuletype()) { //按日期(获取到按日期倍率时直接退出循环)
                    PubPremiumRuleDatedetail premiumRuleDatedetail = getPremiumrateByDate(premiumRule, param.getUsetime());
                    if(null != premiumRuleDatedetail) {
                        datedetail = premiumRuleDatedetail;
                        break;
                    }
                }
            }
            //如果同时存在按星期倍率和按日期倍率，优先使用按日期倍率
            if(null != weekdetail) {
                ret.put("premiumrate", weekdetail.getPremiumrate());
                ret.put("ruletype", PubPremiumRuleEnum.RULETYPE_WEEK.code);
                ret.put("premiumdetailid", weekdetail.getId());
            }
            if(null != datedetail) {
                ret.put("premiumrate", datedetail.getPremiumrate());
                ret.put("ruletype", PubPremiumRuleEnum.RULETYPE_DATE.code);
                ret.put("premiumdetailid", datedetail.getId());
            }
        }
        return ret;
    }

    /**
     * 查询符合条件的溢价规则
     * @param param
     * @return
     */
    private List<PubPremiumRule> getPubPremiumRule(PremiumRuleParam param) {
        //组装redis中溢价规则的key
        StringBuilder sb = new StringBuilder();
        sb.append("PREMIUMRULE_");
        sb.append(param.getCitycode()).append("_");
        sb.append(param.getCartype()).append("_");
        sb.append(param.getPlatformtype()).append("_");
        if(PubPremiumRuleEnum.PLATFORMTYPE_LEASE.code == param.getPlatformtype()) {
            sb.append(param.getLeasescompanyid()).append("_");
        }
        sb.append("*");

        //从redis中获取数据
        Set<String> keys = JedisUtil.getKeys(sb.toString());
        if(null != keys && !keys.isEmpty()) {
            List<PubPremiumRule> premiumRulesList = new ArrayList<PubPremiumRule>();
            for (String key : keys) {
                String value = JedisUtil.getString(key);
                JSONObject json = JSONObject.fromObject(value);
                PubPremiumRule premiumRule = StringUtil.parseJSONToBean(json.getString("premiumrule"), PubPremiumRule.class);
                if(PubPremiumRuleEnum.RULETYPE_WEEK.code == premiumRule.getRuletype()) { //按星期
                    premiumRule.setWeekdetails(StringUtil.parseJSONToBeans(json.getString("weekdetails"), PubPremiumRuleWeekdetail.class));
                } else if(PubPremiumRuleEnum.RULETYPE_DATE.code == premiumRule.getRuletype()) { //按日期
                    premiumRule.setDatedetails(StringUtil.parseJSONToBeans(json.getString("datedetails"), PubPremiumRuleDatedetail.class));
                } else {
                    continue;
                }
                premiumRulesList.add(premiumRule);
            }
            return premiumRulesList;
        }
        return null;
    }

    /**
     * 获取溢价倍率(按星期)
     * @param premiumRule
     * @return
     */
    private PubPremiumRuleWeekdetail getPremiumrateByWeek(PubPremiumRule premiumRule, Date usetime) {
        //获取用车时间所属星期的值
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(usetime);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        //查询匹配的溢价规则
        List<PubPremiumRuleWeekdetail> weekdetails = premiumRule.getWeekdetails();
        if(null != weekdetails && !weekdetails.isEmpty()) {
            int usetimeNum = formatTime(StringUtil.formatDate(usetime, StringUtil.TIME_ONLY_HOUR_MINUTE));
            for (PubPremiumRuleWeekdetail weekdetail : weekdetails) {
                if(week == weekdetail.getWeekday()) {
                    int startdtNum = formatTime(weekdetail.getStartdt());
                    int enddtNum = formatTime(weekdetail.getEnddt());
                    if(usetimeNum >= startdtNum && usetimeNum <= enddtNum) {
                        return weekdetail;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取溢价倍率(按日期)
     * @param premiumRule
     * @return
     */
    private PubPremiumRuleDatedetail getPremiumrateByDate(PubPremiumRule premiumRule, Date usetime) {
        //校验日期是否在溢价规则的时间段内
        int startDateNum = Integer.valueOf(StringUtil.formatDate(premiumRule.getStartdt(), StringUtil.TIME_WITH_DAY_NOSYMBOL));
        int endDateNum = Integer.valueOf(StringUtil.formatDate(premiumRule.getEnddt(), StringUtil.TIME_WITH_DAY_NOSYMBOL));
        int useDateNum = Integer.valueOf(StringUtil.formatDate(usetime, StringUtil.TIME_WITH_DAY_NOSYMBOL));
        if(useDateNum < startDateNum || useDateNum > endDateNum) {
            return null;
        }

        //校验时间点是否在溢价规则的时间段内
        List<PubPremiumRuleDatedetail> datedetails = premiumRule.getDatedetails();
        if(null != datedetails && !datedetails.isEmpty()) {
            int usetimeNum = formatTime(StringUtil.formatDate(usetime, StringUtil.TIME_ONLY_HOUR_MINUTE));
            for (PubPremiumRuleDatedetail datedetail : datedetails) {
                int starttimeNum = formatTime(datedetail.getStarttime());
                int endtimeNum = formatTime(datedetail.getEndtime());
                if(usetimeNum >= starttimeNum && starttimeNum <= endtimeNum) {
                    return datedetail;
                }
            }
        }
        return null;
    }

    /**
     * 将HH:mm格式的时间取消冒号后转成整数
     * @param time
     * @return
     */
    private int formatTime(String time) {
        return Integer.valueOf(time.replace(":", ""));
    }

}
