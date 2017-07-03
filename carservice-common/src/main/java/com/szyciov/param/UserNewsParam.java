package com.szyciov.param;

import com.szyciov.entity.UserNews;

/**
 * 用户消息存储对象
 * Created by LC on 2016/11/22.
 */
public class UserNewsParam {
	
	/**
     * 租赁端用户消息表
     */
    public static String LE_USERNEWS_TABNAME = "le_usernews";

    /**
     * 机构用户消息表
     */
    public static String ORG_USERNEWS_TABNAME = "org_usernews";

    /**
     * 运管端用户消息表
     */
    public static String OP_USERNEWS_TABNAME = "op_usernews";

    /**
     * 司机消息表
     */
    public static String DRIVER_USERNEWS_TABNAME = "pub_drivernews";
    

    /**
     * 个人用户消息表
     */
    public static String PE_USERNEW_TABNAME = "pe_usernews";
    

    /**
     * 用户消息表名称
     */
    private String userNewsTbName;

    /**
     * 用户消息对象
     */
    private UserNews userNews;


    public String getUserNewsTbName() {
        return userNewsTbName;
    }

    public void setUserNewsTbName(String userNewsTbName) {
        this.userNewsTbName = userNewsTbName;
    }

    public UserNews getUserNews() {
        return userNews;
    }

    public void setUserNews(UserNews userNews) {
        this.userNews = userNews;
    }



}
 