package com.szyciov.supervision.api.responce;


import com.szyciov.supervision.api.dto.BaseApi;

/**
 * 接口返回HTTP的内容
 * Created by 林志伟 on 2017/7/13.
 */

public class HttpContent {

    /**
     * 响应状态码
     */
    private int status;
    /**
     * 响应内容
     */
    private String Content;

    /**
     * 实体列表----保存失败后实体数据
     */
    private EntityInfoList<BaseApi> infoList;

    public EntityInfoList<BaseApi> getInfoList() {
        return infoList;
    }

    public void setInfoList(EntityInfoList<BaseApi> infoList) {
        this.infoList = infoList;
    }

    public HttpContent() {
    }

    public HttpContent(int status, String content) {
        this.status = status;
        Content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "HttpContent{" +
                "status=" + status +
                ", Content='" + Content + '\'' +
                '}';
    }
}
