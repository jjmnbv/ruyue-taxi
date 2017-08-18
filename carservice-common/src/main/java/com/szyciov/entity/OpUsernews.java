package com.szyciov.entity;

import java.util.Date;

public class OpUsernews {
    /**
    主键
     */
    private String id;

    /**
    所属用户 与用户表主键关联
     */
    private String userid;

    /**
    消息类型  0-订单消息,1-系统消息,2-推广信息,3-其它
     */
    private String type;

    /**
    消息内容
     */
    private String content;

    /**
    消息状态  0-未读，1-已读
     */
    private String newsstate;

    /**
    创建时间
     */
    private Date createtime;

    /**
    更新时间
     */
    private Date updatetime;

    /**
    数据状态
     */
    private Integer status;

    /**
     * get 主键
     */
    public String getId() {
        return id;
    }

    /**
     *
     * set 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * get 所属用户 与用户表主键关联
     */
    public String getUserid() {
        return userid;
    }

    /**
     *
     * set 所属用户 与用户表主键关联
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * get 消息类型  0-订单消息,1-系统消息,2-推广信息,3-其它
     */
    public String getType() {
        return type;
    }

    /**
     *
     * set 消息类型  0-订单消息,1-系统消息,2-推广信息,3-其它
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * get 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * set 消息内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * get 消息状态  0-未读，1-已读
     */
    public String getNewsstate() {
        return newsstate;
    }

    /**
     *
     * set 消息状态  0-未读，1-已读
     */
    public void setNewsstate(String newsstate) {
        this.newsstate = newsstate == null ? null : newsstate.trim();
    }

    /**
     * get 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     *
     * set 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * get 更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     *
     * set 更新时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * get 数据状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * set 数据状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}