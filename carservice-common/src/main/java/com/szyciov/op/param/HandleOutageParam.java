
 /*
 * 文 件 名:  HandleOutageParam.java
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
 * 处理断电信息参数对象
 * <功能详细描述>
 * 
 * @author  杨晋伟
 * @version  [版本号, 2017年3月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class HandleOutageParam extends BaseParam{
    
    /** 断电报警ID */
    private String id;
    
    /** 断电原因 */
    private Integer outageReason;
    
    /** 核实人Id */
    private String verifyPersonId;
    
    /** 核实人姓名 */
    private String verifyName;
    
    /** 备注 */
    private String remarks;

    
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
     * @return 返回 outageReason
     */
    
    public Integer getOutageReason() {
        return outageReason;
    }

    
    /**
    * @param 对outageReason进行赋值
    */
    
    public void setOutageReason(Integer outageReason) {
        this.outageReason = outageReason;
    }

    
    /**
     * @return 返回 verifyName
     */
    
    public String getVerifyName() {
        return verifyName;
    }

    
    /**
    * @param 对verifyName进行赋值
    */
    
    public void setVerifyName(String verifyName) {
        this.verifyName = verifyName;
    }

    
    /**
     * @return 返回 remarks
     */
    
    public String getRemarks() {
        return remarks;
    }

    
    /**
    * @param 对remarks进行赋值
    */
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    
    /**
     * @return 返回 verifyPersonId
     */
    
    public String getVerifyPersonId() {
        return verifyPersonId;
    }


    
    /**
    * @param 对verifyPersonId进行赋值
    */
    
    public void setVerifyPersonId(String verifyPersonId) {
        this.verifyPersonId = verifyPersonId;
    }
    
    
}
