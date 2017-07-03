package com.szyciov.touch.dto;

import java.util.List;

/**
 * 订单轨迹信息
 * Created by shikang on 2017/5/10.
 */
public class OrderTrackDTO {

    /**
     * 上车地址经度
     */
    private Double departureLon;

    /**
     * 上车地址纬度
     */
    private Double departureLat;

    /**
     * 下车地址经度
     */
    private Double destinationLon;

    /**
     * 下车地址纬度
     */
    private Double destinationLat;

    /**
     * 位置点集合
     */
    private List<PointLocationDTO> points;

    public Double getDepartureLon() {
        return departureLon;
    }

    public void setDepartureLon(Double departureLon) {
        this.departureLon = departureLon;
    }

    public Double getDepartureLat() {
        return departureLat;
    }

    public void setDepartureLat(Double departureLat) {
        this.departureLat = departureLat;
    }

    public Double getDestinationLon() {
        return destinationLon;
    }

    public void setDestinationLon(Double destinationLon) {
        this.destinationLon = destinationLon;
    }

    public Double getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(Double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public List<PointLocationDTO> getPoints() {
        return points;
    }

    public void setPoints(List<PointLocationDTO> points) {
        this.points = points;
    }
}
