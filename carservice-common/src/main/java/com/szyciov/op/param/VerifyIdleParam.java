
 /*
 * 文 件 名:  VerifyIdleParam.java
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
 * 核实怠速信息参数对象
 * <功能详细描述>
 * 
 * @author  杨晋伟
 * @version  [版本号, 2017年3月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class VerifyIdleParam extends BaseParam{
    
    /** 怠速ID */
    private String id;
    
    /** 核查结果 */
    private Integer oilCharge;
    
    /** 核实人姓名 */
    private String verifyPerson;
    
    /** 核实人电话 */
    private String verifyPersonTel;
    
    /** 核实人部门 */
    private String departmentId;
    
    /** 怠速原因 */
    private String idleReason;
    
    /** 处理人Id */
    private String operateId;
    
    /** 处理人姓名 */
    private String operateStaff;

    
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
     * @return 返回 oilCharge
     */
    
    public Integer getOilCharge() {
        return oilCharge;
    }

    
    /**
    * @param 对oilCharge进行赋值
    */
    
    public void setOilCharge(Integer oilCharge) {
        this.oilCharge = oilCharge;
    }

    
    /**
     * @return 返回 verifyPerson
     */
    
    public String getVerifyPerson() {
        return verifyPerson;
    }

    
    /**
    * @param 对verifyPerson进行赋值
    */
    
    public void setVerifyPerson(String verifyPerson) {
        this.verifyPerson = verifyPerson;
    }

    
    /**
     * @return 返回 verifyPersonTel
     */
    
    public String getVerifyPersonTel() {
        return verifyPersonTel;
    }

    
    /**
    * @param 对verifyPersonTel进行赋值
    */
    
    public void setVerifyPersonTel(String verifyPersonTel) {
        this.verifyPersonTel = verifyPersonTel;
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
     * @return 返回 idleReason
     */
    
    public String getIdleReason() {
        return idleReason;
    }

    
    /**
    * @param 对idleReason进行赋值
    */
    
    public void setIdleReason(String idleReason) {
        this.idleReason = idleReason;
    }


    
    /**
     * @return 返回 operateId
     */
    
    public String getOperateId() {
        return operateId;
    }


    
    /**
    * @param 对operateId进行赋值
    */
    
    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }


    
    /**
     * @return 返回 operateStaff
     */
    
    public String getOperateStaff() {
        return operateStaff;
    }


    
    /**
    * @param 对operateStaff进行赋值
    */
    
    public void setOperateStaff(String operateStaff) {
        this.operateStaff = operateStaff;
    }
    
    
}
