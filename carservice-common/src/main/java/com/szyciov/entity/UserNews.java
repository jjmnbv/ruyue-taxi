package com.szyciov.entity;

import java.util.Date;

/**
 * 用户消息类型实体类
 * Created by LC on 2016/11/22.
 */
public class UserNews {

    /**
     * 用户消息类型-订单消息
     */
    public static String USER_NEWS_TYPE_ORDER = "0";

    /**
     * 用户消息类型-系统消息
     */
    public static String USER_NEWS_TYPE_SYSTEM = "1";

    /**
     * 用户消息类型-推广消息
     */
    public static String USER_NEWS_TYPE_GENERALIZE = "2";

    /**
     * 用户消息类型-其他消息
     */
    public static String USER_NEWS_TYPE_OTHER = "3";



    /**
     * 用户消息状态-未读
     */
    public static String USER_NEWS_STATE_UNREAD = "0";

    /**
     * 用户消息状态-已读
     */
    public static String USER_NEWS_STATE_READ = "1";

    private String id;

    /**
     * 所属用户 与用户表主键关联
     */
    private String userid;

    /**
     * 消息类型  0-订单消息,1-系统消息,2-推广信息,3-其它
     */
    private String type;

    /**
     * 消息内容
     */
    private String content;

    /**
     *消息状态  0-未读，1-已读
     */
    private String newsstate;

    /**
     *创建时间
     */
    private Date createtime;

    /**
     *更新时间
     */
    private Date updatetime;

    /**
     *数据状态
     */
    private Integer status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNewsstate() {
        return newsstate;
    }

    public void setNewsstate(String newsstate) {
        this.newsstate = newsstate;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
 