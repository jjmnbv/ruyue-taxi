package com.szyciov.operate.util.excel;

import com.szyciov.util.ExcelTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台驻点EXCEL模板
 * Created by Administrator on 2017/8/10 0010.
 */
public class OpPlatformStagnationExcel implements ExcelTemplate {
    /**
     * 定义EXCEL模板的名称
     */
    public  static final String EXCEL_FILE_NAME = "平台驻点信息";
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
            {"city","驻点城市","广州市"},
            {"responsible","负责人姓名","林志伟"},
            {"responsibleway","负责人电话","18826252137"},
            {"postcode","邮政编码","555000"},
            {"contactaddresscity","通信地址城市","广东广州天河区"},
            {"contactaddress","通信地址明细","元岗村"},
            {"parentcompany","母公司名称","广州公共交通集团"},
            {"parentadcity","母公司通信地址城市","广东广州番禹区"},
            {"parentad","母公司通信地址明细","具体详细地址"},

    };

    private static OpPlatformStagnationExcel opPlatformStagnationExcel=null;

    private OpPlatformStagnationExcel(){
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
    public static OpPlatformStagnationExcel getInstance(){
        if(opPlatformStagnationExcel!=null){
            return opPlatformStagnationExcel;
        }else {
            opPlatformStagnationExcel=new OpPlatformStagnationExcel();
            return  opPlatformStagnationExcel;
        }
    }

}