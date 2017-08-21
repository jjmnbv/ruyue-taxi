package com.szyciov.supervision.api.dto.evaluate;

import com.supervision.enums.CommandEnum;

/**
 * 3.6.3	驾驶员处罚信息（JSYCF）
 * Created by 林志伟 on 2017/7/7.
 */

public class DriverPenaltyInfo extends EvaluateApi {
    public DriverPenaltyInfo() {
        super();
        setCommand(CommandEnum.DriverPenaltyInfo);
    }

    /**
     * 机动车驾驶证号
     */
    private String licenseId;
    /**
     * 处罚时间	YYYYMMDDHHMMSS
     */
    private String punishTime;
    /**
     * 处罚原因
     */
    private String punishReason;
    /**
     *处罚结果
     */
    private String punishResult;

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getPunishTime() {
        return punishTime;
    }

    public void setPunishTime(String punishTime) {
        this.punishTime = punishTime;
    }

    public String getPunishReason() {
        return punishReason;
    }

    public void setPunishReason(String punishReason) {
        this.punishReason = punishReason;
    }

    public String getPunishResult() {
        return punishResult;
    }

    public void setPunishResult(String punishResult) {
        this.punishResult = punishResult;
    }

    @Override
    public String toString() {
        return "DriverPenaltyInfo{" +
                "licenseId='" + licenseId + '\'' +
                ", punishTime='" + punishTime + '\'' +
                ", punishReason='" + punishReason + '\'' +
                ", punishResult='" + punishResult + '\'' +
                "} " + super.toString();
    }
}
