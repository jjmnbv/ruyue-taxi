package com.szyciov.param;

/**
 * 手机号验证条件
 * Created by LC on 2017/4/10.
 */
public class PhoneAuthenticationParam {

    //身份证号
    private String cardNo;

    //手机号
    private String mobile;

    //姓名
    private String realName;

    //IP地址
    private String ipAddr;


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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
}
 