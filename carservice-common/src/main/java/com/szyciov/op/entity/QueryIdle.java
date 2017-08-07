
 /*
 * 文 件 名:  QueryIdle.java
 * 版    权:  Szyciov Technologies Co., Ltd. Copyright 1993-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2017年3月27日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
  
package com.szyciov.op.entity;

/**
 * 查询怠速信息结果对象
 * <功能详细描述>
 * 
 * @author  杨晋伟
 * @version  [版本号, 2017年3月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class QueryIdle {
    
    /** 怠速ID */
    private String id;
    
    /** 车牌 */
    
    private String plate;
    /** 设备IMEI */
    private String imei;
    
    /** 所属部门 */
    private String department;
    
    /** 怠速时长 */
    private String idleTime;
    
    /** 开始时间 */
    private String startTime;
    
    /** 结束时间 */
    private String endTime;
    
    /** 地址 */
    private String location;
    
    /** 违规级别 */
    private String timeRange;
    
    /** 核查结果 */
    private String oilCharge;

    
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
     * @return 返回 departmentId
     */
    
    public String getDepartment() {
        return department;
    }

    
    /**
    * @param 对departmentId进行赋值
    */
    
    public void setDepartment(String department) {
        this.department = department;
    }

    
    /**
     * @return 返回 idleTime
     */
    
    public String getIdleTime() {
        return idleTime;
    }

    
    /**
    * @param 对idleTime进行赋值
    */
    
    public void setIdleTime(String idleTime) {
        this.idleTime = idleTime;
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
     * @return 返回 endTime
     */
    
    public String getEndTime() {
        return endTime;
    }


    
    /**
    * @param 对endTime进行赋值
    */
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    /**
     * @return 返回 location
     */
    
    public String getLocation() {
        return location;
    }

    
    /**
    * @param 对location进行赋值
    */
    
    public void setLocation(String location) {
        this.location = location;
    }

    
    /**
     * @return 返回 timeRange
     */
    
    public String getTimeRange() {
        return timeRange;
    }

    
    /**
    * @param 对timeRange进行赋值
    */
    
    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    
    /**
     * @return 返回 oilCharge
     */
    
    public String getOilCharge() {
        return oilCharge;
    }

    
    /**
    * @param 对oilCharge进行赋值
    */
    
    public void setOilCharge(String oilCharge) {
        this.oilCharge = oilCharge;
    }
    
    
}
