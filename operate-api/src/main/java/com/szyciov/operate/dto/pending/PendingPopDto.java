package com.szyciov.operate.dto.pending;

/**
 * 人工待交接班弹窗dto
 * Created by LC on 2017/3/16.
 */
public class PendingPopDto {

    private String title;

    private String id;

    private String platenoStr;

    private String driverInfo;

    private String onlinetimeStr;

    private long extime;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public String getOnlinetimeStr() {
        return onlinetimeStr;
    }

    public void setOnlinetimeStr(String onlinetimeStr) {
        this.onlinetimeStr = onlinetimeStr;
    }

    public String getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(String driverInfo) {
        this.driverInfo = driverInfo;
    }

    public long getExtime() {
        return extime;
    }

    public void setExtime(long extime) {
        this.extime = extime;
    }
}
 