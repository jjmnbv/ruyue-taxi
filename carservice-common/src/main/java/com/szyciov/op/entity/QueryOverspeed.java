
 /*
 * 文 件 名:  QueryOverspeed.java
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
 * 查询超速信息结果对象
 * <功能详细描述>
 * 
 * @author  杨晋伟
 * @version  [版本号, 2017年3月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class QueryOverspeed {
    
    /** 超速ID */
    private String id;

    /**行程ID*/
    private String trackId;

    /**行程状态*/
    private String trackStatus;
    /** 车牌 */
    private String plate;
    
    /** 设备IMEI */
    private String imei;
    
    /** 所属部门 */
    private String department;
    
    /** 超速时长 */
    private String overspeedTime;
    
    /** 超速里程(km) */
    private Double overspeed;
    
    /** 开始时间 */
    private String startTime;
    
    /** 结束时间 */
    private String endTime;
    
    /** 地址 */
    private String address;

    private String eqpId;

    public String getEqpId() {
        return eqpId;
    }

    public void setEqpId(String eqpId) {
        this.eqpId = eqpId;
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
     * @return 返回 overspeedTime
     */
    
    public String getOverspeedTime() {
        return overspeedTime;
    }

    
    /**
    * @param 对overspeedTime进行赋值
    */
    
    public void setOverspeedTime(String overspeedTime) {
        this.overspeedTime = overspeedTime;
    }

    
    /**
     * @return 返回 overspeed
     */
    
    public Double getOverspeed() {
        return overspeed;
    }

    
    /**
    * @param 对overspeed进行赋值
    */
    
    public void setOverspeed(Double overspeed) {
        this.overspeed = overspeed;
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
     * @return 返回 address
     */
    
    public String getAddress() {
        return address;
    }

    
    /**
    * @param 对address进行赋值
    */
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
