package com.szyciov.supervision.api.request;



/**
 * Created by admin on 2017/7/6.
 */

public class TokenRequest {
    private String platform;
    private String key;

    public TokenRequest() {
    }

    public TokenRequest(String platform, String key) {
        this.platform = platform;
        this.key = key;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
