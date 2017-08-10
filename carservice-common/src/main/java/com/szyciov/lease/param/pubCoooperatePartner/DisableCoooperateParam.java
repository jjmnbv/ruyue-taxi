package com.szyciov.lease.param.pubCoooperatePartner;

import java.util.Date;

/**
 * Created by ZF on 2017/7/25.
 */
public class DisableCoooperateParam {
    /**
     * 申请ID
     */
    private String id;
    /**
     * 合作状态(0-审核中,1-合作中,2-未达成,3-已终止,4-已过期)
     */
    private Integer coostate = 3;
    /**
     * 合作截止时间
     */
    private Date cooendtime = new Date();

    /**
     * 更新人
     */
    private String updater;

    /**
     * 更新时间
     */
    private Date updatetime = new Date();

    public String getId() {
        return id;
    }

    public Integer getCoostate() {
        return coostate;
    }

    public Date getCooendtime() {
        return cooendtime;
    }

    public String getUpdater() {
        return updater;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}
