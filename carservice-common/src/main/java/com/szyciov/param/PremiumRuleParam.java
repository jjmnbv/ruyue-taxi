package com.szyciov.param;

import java.util.Date;

import com.szyciov.util.StringUtil;

/**
 * Created by shikang on 2017/7/28.
 */
public class PremiumRuleParam extends QueryParam {

    /**
     * 用车时间
     */
    private Date usetime;

    /**
     * 城市id
     */
    private String citycode;

    /**
     * 业务类型(0-网约车,1-出租车)
     */
    private Integer cartype;

    /**
     * 系统类型(0-运管端,1-租赁端)
     */
    private Integer platformtype;

    /**
     * 租赁公司id
     */
    private String leasescompanyid;

    public Date getUsetime() {
        return usetime;
    }

    public void setUsetime(Object usetime) {
        if(usetime instanceof Date){
            this.usetime = (Date) usetime;
        }else if(usetime instanceof String){
            this.usetime = StringUtil.parseDate((String) usetime, StringUtil.DATE_TIME_FORMAT);
        }else if(usetime instanceof Long){
            this.usetime = new Date((long)usetime);
        }
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public Integer getCartype() {
        return cartype;
    }

    public void setCartype(Integer cartype) {
        this.cartype = cartype;
    }

    public Integer getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(Integer platformtype) {
        this.platformtype = platformtype;
    }

    public String getLeasescompanyid() {
        return leasescompanyid;
    }

    public void setLeasescompanyid(String leasescompanyid) {
        this.leasescompanyid = leasescompanyid;
    }
}
