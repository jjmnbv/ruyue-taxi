package com.szyciov.op.dto.drivershift;

/**
 * 待交班保存成功返回DTO
 * Created by LC on 2017/3/9.
 */
public class PendInfoDto {

    private String pendId;

    // 剩余时限  单位(秒)
    private long autotime;

    //状态 -1 无交接班规则 0 正常 1 交接失败 2 无交班申请 3 无绑定信息
    private int state;



    public String getPendId() {
        return pendId;
    }

    public void setPendId(String pendId) {
        this.pendId = pendId;
    }

    public long getAutotime() {
        return autotime;
    }

    public void setAutotime(long autotime) {
        this.autotime = autotime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
 