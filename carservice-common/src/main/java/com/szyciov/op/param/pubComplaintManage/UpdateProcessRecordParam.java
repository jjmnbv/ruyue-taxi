package com.szyciov.op.param.pubComplaintManage;

import java.util.Date;

/**
 * Created by ZF on 2017/8/15.
 */
public class UpdateProcessRecordParam {
    private String id;
    private String processor; // 处理人
    private String processstatus; // 处理状态
    private String updater; // 更新人
    private String processrecord; // 处理以及那
    private Date updatetime; // 更新时间
    private Date processtime; // 处理时间
    private int processresult; // 处理结果

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getProcessstatus() {
        return processstatus;
    }

    public void setProcessstatus(String processstatus) {
        this.processstatus = processstatus;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getProcessrecord() {
        return processrecord;
    }

    public void setProcessrecord(String processrecord) {
        this.processrecord = processrecord;
    }


    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getProcesstime() {
        return processtime;
    }

    public void setProcesstime(Date processtime) {
        this.processtime = processtime;
    }

    public int getProcessresult() {
        return processresult;
    }

    public void setProcessresult(int processresult) {
        this.processresult = processresult;
    }
}
