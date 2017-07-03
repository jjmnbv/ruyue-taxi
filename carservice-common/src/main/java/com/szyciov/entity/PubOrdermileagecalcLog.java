package com.szyciov.entity;

import java.util.Date;

/**
 * Created by shikang on 2017/5/18.
 */
public class PubOrdermileagecalcLog {

    /**
     * 主键
     */
    private String id;

    /**
     * 所属订单
     */
    private String orderno;

    /**
     * OBD里程
     */
    private Double obdmileage;

    /**
     * OBDGPS里程
     */
    private Double obdgpsmileage;

    /**
     * 司机APPGPS里程
     */
    private Double appmileage;

    /**
     * 鹰眼里程
     */
    private Double lbsyunmileage;

    /**
     * 预估里程
     */
    private Double estimatedmileage;

    /**
     * 里程计算方式
     */
    private Integer calctype;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * 优化后的OBD里程
     */
    private Double optimizeobdmileage;

    /**
     * 优化后的OBDGPS里程
     */
    private Double optimizeobdgpsmileage;

    /**
     * 优化后的APPGPS里程
     */
    private Double optimizeappgpsmileage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Double getObdmileage() {
        return obdmileage;
    }

    public void setObdmileage(Double obdmileage) {
        this.obdmileage = obdmileage;
    }

    public Double getObdgpsmileage() {
        return obdgpsmileage;
    }

    public void setObdgpsmileage(Double obdgpsmileage) {
        this.obdgpsmileage = obdgpsmileage;
    }

    public Double getAppmileage() {
        return appmileage;
    }

    public void setAppmileage(Double appmileage) {
        this.appmileage = appmileage;
    }

    public Double getLbsyunmileage() {
        return lbsyunmileage;
    }

    public void setLbsyunmileage(Double lbsyunmileage) {
        this.lbsyunmileage = lbsyunmileage;
    }

    public Double getEstimatedmileage() {
        return estimatedmileage;
    }

    public void setEstimatedmileage(Double estimatedmileage) {
        this.estimatedmileage = estimatedmileage;
    }

    public Integer getCalctype() {
        return calctype;
    }

    public void setCalctype(Integer calctype) {
        this.calctype = calctype;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getOptimizeobdmileage() {
        return optimizeobdmileage;
    }

    public void setOptimizeobdmileage(Double optimizeobdmileage) {
        this.optimizeobdmileage = optimizeobdmileage;
    }

    public Double getOptimizeobdgpsmileage() {
        return optimizeobdgpsmileage;
    }

    public void setOptimizeobdgpsmileage(Double optimizeobdgpsmileage) {
        this.optimizeobdgpsmileage = optimizeobdgpsmileage;
    }

    public Double getOptimizeappgpsmileage() {
        return optimizeappgpsmileage;
    }

    public void setOptimizeappgpsmileage(Double optimizeappgpsmileage) {
        this.optimizeappgpsmileage = optimizeappgpsmileage;
    }
}
