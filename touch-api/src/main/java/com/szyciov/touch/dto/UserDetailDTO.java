package com.szyciov.touch.dto;

/**
 * 用户信息
 * Created by shikang on 2017/5/10.
 */
public class UserDetailDTO {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 头像
     */
    private String imgPath;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
