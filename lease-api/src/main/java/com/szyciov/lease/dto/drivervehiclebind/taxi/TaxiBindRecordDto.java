package com.szyciov.lease.dto.drivervehiclebind.taxi;

import com.szyciov.enums.BindingStateEnum;
import com.szyciov.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;


/**
 * 出租车绑定操作记录显示
 */
public class TaxiBindRecordDto {

    //创建时间
    private Date createtime;
    //创建时间
    private String createtimeStr;
    //绑定状态
    private String bindstate;
    //姓名
    private String name;
    //电话
    private String phone;
    //司机信息
    private String driverInfo;
    //解绑原因
    private String unbindreason;
    //操作人
    private String nickname;
    //绑定人数
    private String bindpersonnum;
    //绑定信息
    private String binddirverinfo;
    //解绑司机信息
    private String unbinddirverinfo;

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreatetimeStr() {
        if(this.createtime!=null){
            return DateUtil.format(this.createtime);
        }
        return createtimeStr;
    }

    public void setCreatetimeStr(String createtimeStr) {
        this.createtimeStr = createtimeStr;
    }

    public String getBindstate() {
        if(this.bindstate!=null){
            if(BindingStateEnum.BINDING.code.equals(this.bindstate)){
                return "绑定";
            }else if(BindingStateEnum.UN_BINDING.code.equals(this.bindstate)){
                return "解绑";
            }
        }
        return "/";
    }

    public void setBindstate(String bindstate) {
        this.bindstate = bindstate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUnbindreason() {
        if(StringUtils.isEmpty(this.unbindreason)){
            return "/";
        }
        return unbindreason;
    }

    public void setUnbindreason(String unbindreason) {
        this.unbindreason = unbindreason;
    }

    public String getNickname() {

        if (StringUtils.isEmpty(this.nickname)) {
            return "/";
        }

        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBindpersonnum() {
        return bindpersonnum;
    }

    public void setBindpersonnum(String bindpersonnum) {
        this.bindpersonnum = bindpersonnum;
    }

    public String getBinddirverinfo() {
        return binddirverinfo;
    }

    public void setBinddirverinfo(String binddirverinfo) {
        this.binddirverinfo = binddirverinfo;
    }

    public String getDriverInfo() {
        if(BindingStateEnum.BINDING.code.equals(this.bindstate)) {
            return this.getName()+" "+this.getPhone();
        }else{
            return this.getUnbinddirverinfo();
        }

    }

    public void setDriverInfo(String driverInfo) {
        this.driverInfo = driverInfo;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public String getUnbinddirverinfo() {
        return unbinddirverinfo;
    }

    public void setUnbinddirverinfo(String unbinddirverinfo) {
        this.unbinddirverinfo = unbinddirverinfo;
    }
}
 