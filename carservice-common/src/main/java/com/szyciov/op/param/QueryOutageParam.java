
 /*
 * 文 件 名:  QueryOutageParam.java
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
 * 查询断电信息参数对象
 * <功能详细描述>
 * 
 * @author  杨晋伟
 * @version  [版本号, 2017年3月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class QueryOutageParam extends BasePageParam{

    private String id;
    
    /** 车牌 */
    private String plate;
    
    /** 设备IMEI */
    private String imei;
    
    /** 所属部门 */
    private String departmentId;
    
    /** 报警时间(起) */
    private String alarmTime;
    
    /** 报警时间(止) */
    private String alarmTimeStop;
    
    /** 处理状态 */
    private Integer processingState;
    
    /**
	 * 用户 所属单位ID
	 */
	private String organizationId;


     public String getId() {
         return id;
     }

     public void setId(String id) {
         this.id = id;
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
     * @return 返回 alarmTimeStop
     */
    
    public String getAlarmTimeStop() {
        return alarmTimeStop;
    }

    
    /**
    * @param 对alarmTimeStop进行赋值
    */
    
    public void setAlarmTimeStop(String alarmTimeStop) {
        this.alarmTimeStop = alarmTimeStop;
    }

    
    /**
     * @return 返回 processingState
     */
    
    public Integer getProcessingState() {
        return processingState;
    }

    
    /**
    * @param 对processingState进行赋值
    */
    
    public void setProcessingState(Integer processingState) {
        this.processingState = processingState;
    }

    
   
    
    
}
