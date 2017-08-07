
 /*
 * 文 件 名:  QueryLowVoltage.java
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
 * 查询低电压信息结果对象
 * <功能详细描述>
 * 
 * @author  杨晋伟
 * @version  [版本号, 2017年3月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class QueryLowVoltage {
    
    /** 车牌 */
    private String plate;
    
    /** 设备IMEI */
    private String imei;
    
    /** 所属部门 */
    private String department;
    
    /** 报警时间 */
    private String alarmTime;
    
    /** 解除时间 */
    private String releaseTime;
    
    /** 电压(V) */
    private Integer voltage;
    
    /** 报警地址 */
    private String alarmAddress;
    
    /** 状态 */
    private String state;

    
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
     * @return 返回 releaseTime
     */
    
    public String getReleaseTime() {
        return releaseTime;
    }


    
    /**
    * @param 对releaseTime进行赋值
    */
    
    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }


    /**
     * @return 返回 voltage
     */
    
    public Integer getVoltage() {
        return voltage;
    }

    
    /**
    * @param 对voltage进行赋值
    */
    
    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
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
     * @return 返回 state
     */
    
    public String getState() {
        return state;
    }

    
    /**
    * @param 对state进行赋值
    */
    
    public void setState(String state) {
        this.state = state;
    }
    
    
}
