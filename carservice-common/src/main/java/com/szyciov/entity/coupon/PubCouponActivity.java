package com.szyciov.entity.coupon;

import java.util.Date;

/**
 * 抵用券活动实体
 * Created by LC on 2017/7/27.
 */
public class PubCouponActivity {

    private String id;
    private String name;
    private Integer sendservicetype;//1-出租车，2网约车
    private Integer sendruletype;    
    private Integer sendcount;
    private Integer sendruletarget;//1-机构客户，2-机构用户，3个人用户
    private Integer activystate;//1-待派发，2-派发中，3-已过期，4-已作废
    private String sendruleidref;
    private Integer sendmoneytype;//1-固定，2随机
    private Double sendfixedmoney;
    private Double sendlowmoney;
    private Double sendhighmoney;
    private Date sendstarttime;
    private Date sendendtime;
    private Integer usetype;//1-发放区域有效，2-开通业务城市有效
    private Integer outimetype;//1-多少天内有效，2-固定期限
    private Integer sendtimeinday;
    private Date fixedstarttime;
    private Date fixedendtime;
    private String lecompanyid;
    private Integer platformtype;//0-运管端，1-租赁端
    private String couponrule;   //优惠券规则  add by xuxxtr
    private Date createtime;
    private Date updatetime;
    private String creater;
    private String updater;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSendservicetype() {
        return sendservicetype;
    }

    public void setSendservicetype(Integer sendservicetype) {
        this.sendservicetype = sendservicetype;
    }

    public Integer getSendruletype() {
        return sendruletype;
    }

    public void setSendruletype(Integer sendruletype) {
        this.sendruletype = sendruletype;
    }

    public Integer getSendcount() {
        return sendcount;
    }

    public void setSendcount(Integer sendcount) {
        this.sendcount = sendcount;
    }

    public Integer getSendruletarget() {
        return sendruletarget;
    }

    public void setSendruletarget(Integer sendruletarget) {
        this.sendruletarget = sendruletarget;
    }

    public Integer getActivystate() {
        return activystate;
    }

    public void setActivystate(Integer activystate) {
        this.activystate = activystate;
    }

    public String getSendruleidref() {
        return sendruleidref;
    }

    public void setSendruleidref(String sendruleidref) {
        this.sendruleidref = sendruleidref;
    }

    public Integer getSendmoneytype() {
        return sendmoneytype;
    }

    public void setSendmoneytype(Integer sendmoneytype) {
        this.sendmoneytype = sendmoneytype;
    }

    public Double getSendfixedmoney() {
        return sendfixedmoney;
    }

    public void setSendfixedmoney(Double sendfixedmoney) {
        this.sendfixedmoney = sendfixedmoney;
    }

    public Double getSendlowmoney() {
        return sendlowmoney;
    }

    public void setSendlowmoney(Double sendlowmoney) {
        this.sendlowmoney = sendlowmoney;
    }

    public Double getSendhighmoney() {
        return sendhighmoney;
    }

    public void setSendhighmoney(Double sendhighmoney) {
        this.sendhighmoney = sendhighmoney;
    }

    public Date getSendstarttime() {
        return sendstarttime;
    }

    public void setSendstarttime(Date sendstarttime) {
        this.sendstarttime = sendstarttime;
    }

    public Date getSendendtime() {
        return sendendtime;
    }

    public void setSendendtime(Date sendendtime) {
        this.sendendtime = sendendtime;
    }

    public Integer getUsetype() {
        return usetype;
    }

    public void setUsetype(Integer usetype) {
        this.usetype = usetype;
    }

    public Integer getOutimetype() {
        return outimetype;
    }

    public void setOutimetype(Integer outimetype) {
        this.outimetype = outimetype;
    }

    public Integer getSendtimeinday() {
        return sendtimeinday;
    }

    public void setSendtimeinday(Integer sendtimeinday) {
        this.sendtimeinday = sendtimeinday;
    }

    public Date getFixedstarttime() {
        return fixedstarttime;
    }

    public void setFixedstarttime(Date fixedstarttime) {
        this.fixedstarttime = fixedstarttime;
    }

    public Date getFixedendtime() {
        return fixedendtime;
    }

    public void setFixedendtime(Date fixedendtime) {
        this.fixedendtime = fixedendtime;
    }

    public String getLecompanyid() {
        return lecompanyid;
    }

    public void setLecompanyid(String lecompanyid) {
        this.lecompanyid = lecompanyid;
    }

    public Integer getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(Integer platformtype) {
        this.platformtype = platformtype;
    }

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCouponrule() {
		return couponrule;
	}

	public void setCouponrule(String couponrule) {
		this.couponrule = couponrule;
	}
    
}
 