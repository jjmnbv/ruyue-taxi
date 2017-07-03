package com.szyciov.operate.util;

import java.util.List;
import java.util.Map;

/**
 * 页码处理bean
 */
@SuppressWarnings("rawtypes")
public class PageBean {
    public PageBean() {

    }

    public String sEcho;
    /**
     * 记录总数
     */
    public int iTotalRecords;
    /**
     * 每页显示个数
     */
    public int iTotalDisplayRecords;
    /**
     * 显示数据
     */
    public List aaData;
    /**
     * 显示数据
     */
    public Map<String,Object> aData;

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public List getAaData() {
        return aaData;
    }

    public void setAaData(List aaData) {
        this.aaData = aaData;
    }

	public Map<String,Object> getAData() {
		return aData;
	}

	public void setAData(Map<String,Object> aData) {
		this.aData = aData;
	}

}
