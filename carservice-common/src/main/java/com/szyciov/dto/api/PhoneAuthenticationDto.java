package com.szyciov.dto.api;

/**
 * 手机实名认证返回dto
 * Created by LC on 2017/4/8.
 */
public class PhoneAuthenticationDto {

    //姓名
    private String realName;
    //身份证号
    private String cardNo;
    //手机号
    private String mobile;
    //是否实名
    private int isok;
    //返回消息
    private String resmsg;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsok() {
        return isok;
    }

    public void setIsok(int isok) {
        this.isok = isok;
    }

    public String getResmsg() {
        return resmsg;
    }

    public void setResmsg(String resmsg) {
        this.resmsg = resmsg;
    }
}
 