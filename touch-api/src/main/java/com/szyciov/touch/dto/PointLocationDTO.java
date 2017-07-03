package com.szyciov.touch.dto;

/**
 * 百度加密坐标
 * Created by shikang on 2017/5/10.
 */
public class PointLocationDTO {

    /**
     * 百度加密坐标
     */
    private String location;

    /**
     * 坐标上传时间（Unix时间戳）
     */
    private Long loc_time;

    /**
     * 服务端时间
     */
    private String create_time;

    /**
     * 方向：范围为[0,359]，0度为正北方向，顺时针
     */
    private Integer direction;

    /**
     * 高度，只在GPS定位结果时才返回，单位米
     */
    private Double height;

    /**
     * 速度，单位：km/h
     */
    private Double speed;

    /**
     * 定位精度，单位：m
     */
    private Double radius;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getLoc_time() {
        return loc_time;
    }

    public void setLoc_time(Long loc_time) {
        this.loc_time = loc_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
}
