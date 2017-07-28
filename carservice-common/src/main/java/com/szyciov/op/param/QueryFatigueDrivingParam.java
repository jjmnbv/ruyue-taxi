
 /*
 * 文 件 名:  QueryFatigueDrivingParam.java
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
 * 查询疲劳驾驶信息参数对象
 * <功能详细描述>
 * 
 * @author  杨晋伟
 * @version  [版本号, 2017年3月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class QueryFatigueDrivingParam extends BasePageParam{
    
    /** 车牌 */
    private String plate;
    
    /** 设备IMEI */
    private String imei;
    
    /** 所属部门 */
    private String departmentId;
    
    /** 时长范围 */
    private Integer timeRange;
    
    /** 报警时间(起) */
    private String alarmTime;
    
    /** 报警时间(止) */
    private String alarmTimeStop;
    
    /** 报警类型 */
    private String[] alarmTypeList;

    private String alarmType;
    
    /**
     * 组织ID
     */
    private String organizationId;


     public String getPlate() {
         return plate;
     }

     public void setPlate(String plate) {
         this.plate = plate;
     }

     public String getImei() {
         return imei;
     }

     public void setImei(String imei) {
         this.imei = imei;
     }

     public String getDepartmentId() {
         return departmentId;
     }

     public void setDepartmentId(String departmentId) {
         this.departmentId = departmentId;
     }

     public Integer getTimeRange() {
         return timeRange;
     }

     public void setTimeRange(Integer timeRange) {
         this.timeRange = timeRange;
     }

     public String getAlarmTime() {
         return alarmTime;
     }

     public void setAlarmTime(String alarmTime) {
         this.alarmTime = alarmTime;
     }

     public String getAlarmTimeStop() {
         return alarmTimeStop;
     }

     public void setAlarmTimeStop(String alarmTimeStop) {
         this.alarmTimeStop = alarmTimeStop;
     }

     public String[] getAlarmTypeList() {
         return alarmTypeList;
     }

     public void setAlarmTypeList(String[] alarmTypeList) {
         this.alarmTypeList = alarmTypeList;
     }

     public String getAlarmType() {
         return alarmType;
     }

     public void setAlarmType(String alarmType) {
         this.alarmType = alarmType;
     }

     public String getOrganizationId() {
         return organizationId;
     }

     public void setOrganizationId(String organizationId) {
         this.organizationId = organizationId;
     }
 }
