package com.szyciov.coupon.dto;

/**
 * 调度请求参数DTO
 *
 * @author LC
 * @date 2017/8/4
 */
public class ScheduleParamDTO {

    private Class jobClass;

    private String jobName;

    private String jobGroup;

    private String cronSchedule;

    private String triggerName;

    private String triggerGroup;

    public Class getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getCronSchedule() {
        return cronSchedule;
    }

    public void setCronSchedule(String cronSchedule) {
        this.cronSchedule = cronSchedule;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }
}
 