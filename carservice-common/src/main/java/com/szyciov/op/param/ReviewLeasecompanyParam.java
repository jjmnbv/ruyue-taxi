package com.szyciov.op.param;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ZF on 2017/7/25.
 */
public class ReviewLeasecompanyParam {
    /**
     * 申请ID
     */
    private String id;
    /**
     * 合作状态(0-审核中,1-合作中,2-未达成,3-已终止,4-已过期)
     */
    private Integer coostate;

    /**
     * 合作开始时间
     */
    private Date coostarttime;

    /**
     * 合作截止时间
     */
    private Date cooendtime;

    /**
     * 审核时间
     */
    private Date reviewtime = new Date();

    /**
     * 审核意见
     */
    private String reviewtext;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 更新时间
     */
    private Date updatetime = new Date();

    public Integer getCoostate() {
        return coostate;
    }

    public void setCoostate(Integer coostate) {
        this.coostate = coostate;
    }

    public Date getCoostarttime() {
        return coostarttime;
    }

    public void setCoostarttime(Date coostarttime) {
        if (coostarttime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(coostarttime);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);

            this.coostarttime = cal.getTime();
        } else {
            this.coostarttime = coostarttime;
        }
    }

    public Date getCooendtime() {
        return cooendtime;
    }

    public void setCooendtime(Date cooendtime) {
        if (cooendtime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(cooendtime);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.MILLISECOND, 59);

            this.cooendtime = cal.getTime();
        } else {
            this.cooendtime = cooendtime;
        }
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public Date getReviewtime() {
        return reviewtime;
    }

    public String getReviewtext() {
        return reviewtext;
    }

    public void setReviewtext(String reviewtext) {
        this.reviewtext = reviewtext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
