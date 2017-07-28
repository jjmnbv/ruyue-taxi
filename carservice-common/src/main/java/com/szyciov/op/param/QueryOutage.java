
/*
* 文 件 名:  QueryOutage.java
* 版    权:  Szyciov Technologies Co., Ltd. Copyright 1993-2016,  All rights reserved
* 描    述:  <描述>
* 修 改 人:  Administrator
* 修改时间:  2017年3月27日
* 跟踪单号:  <跟踪单号>
* 修改单号:  <修改单号>
* 修改内容:  <修改内容>
*/

package com.szyciov.op.param;

/**
 * 查询断电信息结果对象
 * <功能详细描述>
 *
 * @author 杨晋伟
 * @version [版本号, 2017年3月27日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class QueryOutage {

    /**
     * 断电报警ID
     */
    private String id;

    private String eqpId;

    /**
     * 车牌
     */
    private String plate;

    /**
     * 设备IMEI
     */
    private String imei;

    /**
     * 所属部门
     */
    private String department;

    private String departmentId;

    /**
     * 报警时间
     */
    private String alarmTime;

    /**
     * 处理时间
     */
    private String processingTime;

    /**
     * 报警地址
     */
    private String alarmAddress;

    /**
     * 处理状态
     */
    private String processingState;

    /**
     * 处理人ID
     */
    private String processingPeopleId;

    /**
     * 处理人
     */
    private String processingPeople;
    /**
     * 备注
     */
    private String remarks;

    /**
     * 断电原因
     */
    private String reason;

    private String reasonText;

    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 行程ID
     */
    private String trackId;

    /**
     * 行程状态
     */
    private String trackStatus;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEqpId() {
        return eqpId;
    }

    public void setEqpId(String eqpId) {
        this.eqpId = eqpId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    /**
     * @return 返回 id
     */

    public String getId() {
        return id;
    }


    /**
     * @param 对id进行赋值
     */

    public void setId(String id) {
        this.id = id;
    }


    /**
     * @return 返回 plate
     */

    public String getPlate() {
        return plate;
    }


    /**
     * @param 对plate进行赋值
     */

    public void setPlate(String plate) {
        this.plate = plate;
    }


    /**
     * @return 返回 imei
     */

    public String getImei() {
        return imei;
    }


    /**
     * @param 对imei进行赋值
     */

    public void setImei(String imei) {
        this.imei = imei;
    }


    /**
     * @return 返回 department
     */

    public String getDepartment() {
        return department;
    }


    /**
     * @param 对department进行赋值
     */

    public void setDepartment(String department) {
        this.department = department;
    }


    /**
     * @return 返回 processingTime
     */

    public String getProcessingTime() {
        return processingTime;
    }


    /**
     * @param 对processingTime进行赋值
     */

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }


    /**
     * @return 返回 alarmAddress
     */

    public String getAlarmAddress() {
        return alarmAddress;
    }


    /**
     * @param 对alarmAddress进行赋值
     */

    public void setAlarmAddress(String alarmAddress) {
        this.alarmAddress = alarmAddress;
    }


    /**
     * @return 返回 processingState
     */

    public String getProcessingState() {
        return processingState;
    }


    /**
     * @param 对processingState进行赋值
     */

    public void setProcessingState(String processingState) {
        this.processingState = processingState;
    }


    /**
     * @return 返回 processingPeopleId
     */

    public String getProcessingPeopleId() {
        return processingPeopleId;
    }


    /**
     * @param 对processingPeopleId进行赋值
     */

    public void setProcessingPeopleId(String processingPeopleId) {
        this.processingPeopleId = processingPeopleId;
    }


    /**
     * @return 返回 processingPeople
     */

    public String getProcessingPeople() {
        return processingPeople;
    }


    /**
     * @param 对processingPeople进行赋值
     */

    public void setProcessingPeople(String processingPeople) {
        this.processingPeople = processingPeople;
    }


    /**
     * @return 返回 alarmTime
     */

    public String getAlarmTime() {
        return alarmTime;
    }


    /**
     * @param 对alarmTime进行赋值
     */

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }


    /**
     * @return 返回 updateTime
     */

    public String getUpdateTime() {
        return updateTime;
    }


    /**
     * @param 对updateTime进行赋值
     */

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


}
