
 /*
 * 文 件 名:  QueryWaterTemp.java
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
 * 查询水温信息结果对象
 * <功能详细描述>
 * 
 * @author  杨晋伟
 * @version  [版本号, 2017年3月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class QueryWaterTemp {
    
    /** 水温报警ID */
    private String id;
    
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
    
    /** 最高水温 */
    private Double maxWaterTemp;
    
    /** 报警地址 */
    private String alarmAddress;

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
     * @return 返回 maxWaterTemp
     */
    
    public Double getMaxWaterTemp() {
        return maxWaterTemp;
    }

    
    /**
    * @param 对maxWaterTemp进行赋值
    */
    
    public void setMaxWaterTemp(Double maxWaterTemp) {
        this.maxWaterTemp = maxWaterTemp;
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
    
    
    
}
