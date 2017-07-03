package com.szyciov.dto;

/**
 * Created by ZF on 2017/5/19.
 */
public class PagingRequest {
    /**
     * 每页个数
     */
    private int iDisplayLength = 0;

    /**
     * 起始下标
     */
    private int iDisplayStart = 1;

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }
}
