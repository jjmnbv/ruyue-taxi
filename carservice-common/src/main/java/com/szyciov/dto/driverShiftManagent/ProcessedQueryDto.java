package com.szyciov.dto.driverShiftManagent;

import com.szyciov.enums.PeDrivershiftEnum;
import com.szyciov.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 交接班已处理 查询结果dto
 * Created by LC on 2017/3/1.
 */
public class ProcessedQueryDto {

    //ID
    private String id;
    //车辆ID
    private String vehicleid;
    //登记城市名称
    private String cityStr;
    //车牌号码
    private String platenoStr;
    //当班司机信息
    private String ondutydriverinfo;
    //接班司机信息
    private String relieveddriverinfo;
    //在线时长
    private Integer onlinetime;
    //在线时长
    private String onlinetimeStr;
    //申请时间
    private Date applytime;

    private String applytimeStr;
    //处理时间
    private Date processtime;

    private String processtimeStr;
    //交班状态 1-交接班，2-车辆回收，3-交班超时，4-指派当班
    private String shifttype;
    //交班类型 0-自主交班,1-人工指派
    private String relievedtype;
    //处理人姓名
    private String processpersonname;

    private String rownum;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public String getOndutydriverinfo() {
        if(StringUtils.isEmpty(this.ondutydriverinfo)){
            return "/";
        }
        return ondutydriverinfo;
    }

    public void setOndutydriverinfo(String ondutydriverinfo) {
        this.ondutydriverinfo = ondutydriverinfo;
    }

    public String getProcesspersonname() {
        return processpersonname;
    }

    public void setProcesspersonname(String processpersonname) {
        this.processpersonname = processpersonname;
    }


    public void setOnlinetime(Integer onlinetime) {
        this.onlinetime = onlinetime;
    }


    public void setOnlinetimeStr(String onlinetimeStr) {
        this.onlinetimeStr = onlinetimeStr;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public String getRelieveddriverinfo() {
        if(StringUtils.isEmpty(this.relieveddriverinfo)){
            return "/";
        }
        return relieveddriverinfo;
    }

    public void setRelieveddriverinfo(String relieveddriverinfo) {
        this.relieveddriverinfo = relieveddriverinfo;
    }


    public String getShifttype() {
        if(this.shifttype!=null){
            return PeDrivershiftEnum.getShiftTypeStr(this.shifttype);
        }
        return shifttype;
    }

    public void setShifttype(String shifttype) {
        this.shifttype = shifttype;
    }

    public String getRelievedtype() {
        if(this.relievedtype!=null){
            return PeDrivershiftEnum.getRelivedTypeStr(this.relievedtype);
        }
        return relievedtype;
    }

    public void setRelievedtype(String relievedtype) {
        this.relievedtype = relievedtype;
    }


    public Integer getOnlinetime() {
        return onlinetime;
    }

    public String getOnlinetimeStr() {

        if (this.getOnlinetime()==null||!(this.getOnlinetime()>0)){
            return "/";
        }else{
            if(this.getOnlinetime()/60>1) {
                return this.getOnlinetime() / 60 + "时" + this.getOnlinetime() % 60 + "分";
            }else{
                return this.getOnlinetime() % 60 + "分";
            }
        }

    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getApplytimeStr() {
        if(this.applytime!=null){
            return DateUtil.format(this.applytime,"yyyy-MM-dd HH:mm");
        }
        return "/";
    }

    public void setApplytimeStr(String applytimeStr) {
        this.applytimeStr = applytimeStr;
    }

    public String getProcesstimeStr() {
        if(this.processtime!=null){
            return DateUtil.format(this.processtime,"yyyy-MM-dd HH:mm");
        }
        return "/";
    }

    public void setProcesstimeStr(String processtimeStr) {
        this.processtimeStr = processtimeStr;
    }
}
 