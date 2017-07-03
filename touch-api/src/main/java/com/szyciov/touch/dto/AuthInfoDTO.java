package com.szyciov.touch.dto;

/**
 * 授权信息
 * Created by shikang on 2017/5/10.
 */
public class AuthInfoDTO {

    /**
     * 授权状态
     */
    private String authState;

    /**
     * 授权过期时间
     */
    private String authExpirationTime;

    public String getAuthState() {
        return authState;
    }

    public void setAuthState(String authState) {
        this.authState = authState;
    }

    public String getAuthExpirationTime() {
        return authExpirationTime;
    }

    public void setAuthExpirationTime(String authExpirationTime) {
        this.authExpirationTime = authExpirationTime;
    }
}
