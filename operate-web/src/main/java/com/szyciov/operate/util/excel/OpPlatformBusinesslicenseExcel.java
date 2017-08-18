package com.szyciov.operate.util.excel;

import com.szyciov.util.ExcelTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台驻点EXCEL模板
 * Created by Administrator on 2017/8/10 0010.
 */
public class OpPlatformBusinesslicenseExcel implements ExcelTemplate {
    /**
     * 定义EXCEL模板的名称
     */
    public  static final String EXCEL_FILE_NAME = "经营许可信息";
    /**
     * 定义字段列名（数据库一致）
     */
    private List<String> cloumnList;
    /**
     * 定义EXCEL中的列名
     */
    private List<String> titleList;

    /**
     * 定义模板中数据案例
     */
    private List<String> exampleList;
    /**
     * 模板定义
     */
    private String[][] template={
            {"certificate","经营许可证号","4555425452442"},
            {"addressName","经营许可地","广州市"},
            {"scopes","经营区域","广州市"},
            {"organization","发证机构","广州政府"},
            {"startdate","有效期限开始时间","2017-08-16"},
            {"stopdate","有效期限截止时间","2017-08-16"},
            {"certifydate","初次发证日期","2017-08-16"},
            {"state","证照状态","有效"}

    };

    private static OpPlatformBusinesslicenseExcel opPlatformBusinesslicenseExcel=null;

    private OpPlatformBusinesslicenseExcel(){
        init();
    }

    /**
     * 初始化数据列表
     */
    public void init(){
        cloumnList = new ArrayList<>();
        titleList = new ArrayList<>();
        exampleList=new ArrayList<>();
        for (int i=0;i<template.length;i++){
            cloumnList.add(template[i][0]);
            titleList.add(template[i][1]);
            exampleList.add(template[i][2]);

        }
    }

    /**
     * 获取字段列表
     * @return
     */
    public List<String> getCloumnList() {
        return cloumnList;
    }

    /**
     * 获取列名列表
     * @return
     */
    public List<String> getTitleList() {
        return titleList;
    }

    /**
     * 获取模板举例
     * @return
     */
    public List<String> getExampleList(){
        return  exampleList;
    }


    /**
     * 列名校验
     * @return
     */
    public boolean verifyTitle(String[] titles){
        if(titles.length!=titleList.size()){
            return false;
        }else{
            for(int i = 0;i<titleList.size();i++){
                if(!titleList.get(i).equals(titles[i])){
                    return false;
                }
            }
        }
        return  true;
    }
    /**
     * 获取导出的文件
     * @return
     */
    public static   File getExportFile(){
        return  new File(EXCEL_FILE_NAME+".xls");
    }


    /**
     * 获取单例对象
     * @return
     */
    public static OpPlatformBusinesslicenseExcel getInstance(){
        if(opPlatformBusinesslicenseExcel!=null){
            return opPlatformBusinesslicenseExcel;
        }else {
            opPlatformBusinesslicenseExcel=new OpPlatformBusinesslicenseExcel();
            return  opPlatformBusinesslicenseExcel;
        }
    }

}