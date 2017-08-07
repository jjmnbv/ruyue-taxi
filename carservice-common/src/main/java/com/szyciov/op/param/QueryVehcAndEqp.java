
 /*
 * 文 件 名:  QueryVehcAndEqp.java
 * 版    权:  Szyciov Technologies Co., Ltd. Copyright 1993-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2017年3月29日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
  
package com.szyciov.op.param;


 /**
 * 查询车辆及设备IMEI结果对象
 * <功能详细描述>
 * 
 * @author  杨晋伟
 * @version  [版本号, 2017年3月29日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class QueryVehcAndEqp {
    
    /** 设备ID */
    private String eqpId;
    
    /** 关联ID */
    private String vehcId;
    
    /** 设备IMEI */
    private String imei;
    
    /** 车牌 */
    private String plate;
    
    /** 型号名称 */
    private String entityName;
    /**
     * 设备类别 
     */
    private String categoryName;
    /**操作*/
    private String cz;
    
    
    
    public String getCz() {
		return cz;
	}


	public void setCz(String cz) {
		this.cz = cz;
	}


	/**
     * @return 返回 eqpId
     */
    
    public String getEqpId() {
        return eqpId;
    }

    
    /**
    * @param 对eqpId进行赋值
    */
    
    public void setEqpId(String eqpId) {
        this.eqpId = eqpId;
    }

    
    /**
     * @return 返回 vehcId
     */
    
    public String getVehcId() {
        return vehcId;
    }

    
    /**
    * @param 对vehcId进行赋值
    */
    
    public void setVehcId(String vehcId) {
        this.vehcId = vehcId;
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
     * @return 返回 entityName
     */
    
    public String getEntityName() {
        return entityName;
    }

    
    /**
    * @param 对entityName进行赋值
    */
    
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
    
    
}
