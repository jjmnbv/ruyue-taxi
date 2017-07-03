package com.szyciov.param;

/**
 * Created by liubangwei_lc on 2017/6/27.
 */
public class VehcParam {

    /**
     * 车辆ID
     */
    private String id;

    /**
     *车牌
     */
    private String plates;

    /**
     * 车系
     */
    private String lineName;


    /**
     *车型
     */
    private String modelName;

    /**
     *部门
     */
    private String deptName;

    /**
     *司机
     */
    private String driverName;


    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlates() {
        return plates;
    }

    public void setPlates(String plates) {
        this.plates = plates;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
