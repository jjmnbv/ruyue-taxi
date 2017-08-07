
/*
* 文 件 名:  QueryTimeViolation.java
* 版    权:  Szyciov Technologies Co., Ltd. Copyright 1993-2016,  All rights reserved
* 描    述:  <描述>
* 修 改 人:  Administrator
* 修改时间:  2017年3月27日
* 跟踪单号:  <跟踪单号>
* 修改单号:  <修改单号>
* 修改内容:  <修改内容>
*/

package com.szyciov.op.param;

import java.math.BigDecimal;

/**
 * 查询车辆时间违规信息结果对象
 * <功能详细描述>
 *
 * @author 杨晋伟
 * @version [版本号, 2017年3月27日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class QueryTimeViolation {

    /**
     * 车牌
     */
    private String plate;

    /**
     * 设备IMEI
     */
    private String imei;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 违规时长
     */
    private String lengthOfViolation;

    /**
     * 违规里程
     */
    private BigDecimal illegalMileage;

    //操作
    public String cz;

    public String getCz() {
        return cz;
    }


    public void setCz(String cz) {
        this.cz = cz;
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
     * @return 返回 lengthOfViolation
     */

    public String getLengthOfViolation() {
        return lengthOfViolation;
    }


    /**
     * @param 对lengthOfViolation进行赋值
     */

    public void setLengthOfViolation(String lengthOfViolation) {
        this.lengthOfViolation = lengthOfViolation;
    }


    /**
     * @return 返回 illegalMileage
     */

    public BigDecimal getIllegalMileage() {
        return illegalMileage;
    }


    /**
     * @param 对illegalMileage进行赋值
     */

    public void setIllegalMileage(BigDecimal illegalMileage) {
        this.illegalMileage = illegalMileage;
    }

}
