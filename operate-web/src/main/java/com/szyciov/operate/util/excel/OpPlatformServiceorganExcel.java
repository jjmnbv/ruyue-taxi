package com.szyciov.operate.util.excel;

import com.szyciov.util.ExcelTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务机构EXCEL模板
 * Created by Administrator on 2017/8/10 0010.
 */
public class OpPlatformServiceorganExcel implements ExcelTemplate {
    /**
     * 定义EXCEL模板的名称
     */
    public  static final String EXCEL_FILE_NAME = "平台服务机构";
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
            {"servicename","服务机构名称","如约数据公司"},
            {"serviceno","服务机构代码","44544535432545345"},
            {"responsiblename","机构负责人","张三"},
            {"responsiblephone","负责人电话","18826252137"},
            {"managername","机构管理人","李四"},
            {"managerphone","管理人电话","18826252137"},
            {"contactphone","紧急联系电话","18826252137"},
            {"createdate","机构设立日","2017/08/05"},
            {"addressName","机构所在地","广州"},
            {"detailaddresscity","机构详细地址城市","广东广州"},
            {"detailaddress","机构详细地址","天河区智慧创意园"},
            {"mailaddresscity","文书送达地址城市","广东广州"},
            {"mailaddress","文书送达地址","天河区智慧创意园"},

    };

    private static OpPlatformServiceorganExcel opPlatformServiceorganExcel=null;

    private OpPlatformServiceorganExcel(){
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
    public static OpPlatformServiceorganExcel getInstance(){
        if(opPlatformServiceorganExcel!=null){
            return opPlatformServiceorganExcel;
        }else {
            opPlatformServiceorganExcel=new OpPlatformServiceorganExcel();
            return  opPlatformServiceorganExcel;
        }
    }

}