package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.11	网约车驾驶员培训信息(JSYPX)
 * Created by 林志伟 on 2017/7/7.
 */

public class DriverTrainingInfo extends BasicApi {
    public DriverTrainingInfo(){
        super();
        setCommand(CommandEnum.DriverTrainingInfo);
    }
//    注册地行政区划代码
    private String address;
//    机动车驾驶证号
    private String licenseId;
//    courseName
    private String courseName;
//    培训课程日期
    private String courseDate;
//    培训开始时间
    private String startTime;
//    培训结束时间
    private String stopTime;
//    培训时长
    private String duration;
//    培训类型
    private String type;

    @JsonIgnore
    private Integer state;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DriverTrainingInfo{" +
                "address='" + address + '\'' +
                ", licenseId='" + licenseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseDate='" + courseDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", stopTime='" + stopTime + '\'' +
                ", duration='" + duration + '\'' +
                ", type='" + type + '\'' +
                ", state=" + state +
                "} " + super.toString();
    }
}
