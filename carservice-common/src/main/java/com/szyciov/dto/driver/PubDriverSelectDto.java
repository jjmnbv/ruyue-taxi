package com.szyciov.dto.driver;

/**
 * 选择司机组件 dto
 * Created by LC on 2017/3/7.
 */
public class PubDriverSelectDto {

    private String id;

    private String text;

    private String jobnum;

    private String name;

    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.getName()+" "+this.getPhone();
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }
}
 