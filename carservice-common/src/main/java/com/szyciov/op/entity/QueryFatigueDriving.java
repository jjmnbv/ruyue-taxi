
 /*
 * 文 件 名:  QueryFatigueDriving.java
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
 * 查询疲劳驾驶信息结果对象
 * <功能详细描述>
 * 
 * @author  杨晋伟
 * @version  [版本号, 2017年3月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class QueryFatigueDriving {
    
    /** 疲劳驾驶ID */
    private String id;
    
    /** 车牌 */
    private String plate;
    
    /** 设备IMEI */
    private String imei;
    
    /** 所属部门 */
    private String department;
    
    /** 报警类型 */
    private String alarmType;
    
    /** 报警时间 */
    private String alarmTime;
    
    /** 超时时长 */
    private String timeoutTime;
    
    /** 报警地点 */
    private String alarmLocation;
    //操作
  	public String cz;
    
    public String getCz() {
		return cz;
	}


	public void setCz(String cz) {
		this.cz = cz;
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
     * @return 返回 alarmType
     */
    
    public String getAlarmType() {
        return alarmType;
    }

    
    /**
    * @param 对alarmType进行赋值
    */
    
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
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
     * @return 返回 timeoutTime
     */
    
    public String getTimeoutTime() {
        return timeoutTime;
    }


    
    /**
    * @param 对timeoutTime进行赋值
    */
    
    public void setTimeoutTime(String timeoutTime) {
        this.timeoutTime = timeoutTime;
    }


    /**
     * @return 返回 alarmLocation
     */
    
    public String getAlarmLocation() {
        return alarmLocation;
    }

    
    /**
    * @param 对alarmLocation进行赋值
    */
    
    public void setAlarmLocation(String alarmLocation) {
        this.alarmLocation = alarmLocation;
    }
    
    
}
