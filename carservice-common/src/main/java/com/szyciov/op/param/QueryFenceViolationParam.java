
/*
* 文 件 名:  QueryFenceViolationParam.java
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
 * 查询车辆围栏违规信息参数对象
 * <功能详细描述>
 *
 * @author 杨晋伟
 * @version [版本号, 2017年3月27日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class QueryFenceViolationParam extends BasePageParam {

    private String id;
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
    private String departmentId;

    /**
     * 开始时间(起)
     */
    private String startTime;

    /**
     * 开始时间(止)
     */
    private String startTimeStop;

    /**
     * 时长范围
     */
    private Integer timeRange;

    /**
     * 里程范围
     */
    private Integer mileageRange;
    /**
     * 用户 所属单位ID
     */
    private String organizationId;
    /**
     * 行程ID
     */
    private String trackId;

    /**
     * 行程状态
     */
    private String trackStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }

    public String getOrganizationId() {
        return organizationId;
    }


    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
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
     * @return 返回 departmentId
     */

    public String getDepartmentId() {
        return departmentId;
    }


    /**
     * @param 对departmentId进行赋值
     */

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }


    /**
     * @return 返回 startTime
     */

    public String getStartTime() {
        return startTime;
    }


    /**
     * @param 对startTime进行赋值
     */

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    /**
     * @return 返回 startTimeStop
     */

    public String getStartTimeStop() {
        return startTimeStop;
    }


    /**
     * @param 对startTimeStop进行赋值
     */

    public void setStartTimeStop(String startTimeStop) {
        this.startTimeStop = startTimeStop;
    }


    /**
     * @return 返回 timeRange
     */

    public Integer getTimeRange() {
        return timeRange;
    }


    /**
     * @param 对timeRange进行赋值
     */

    public void setTimeRange(Integer timeRange) {
        this.timeRange = timeRange;
    }


    /**
     * @return 返回 mileageRange
     */

    public Integer getMileageRange() {
        return mileageRange;
    }


    /**
     * @param 对mileageRange进行赋值
     */

    public void setMileageRange(Integer mileageRange) {
        this.mileageRange = mileageRange;
    }


}
