package com.szyciov.op.param;

/**
 * 司机控件查询条件
 * Created by LC on 2017/3/7.
 */
public class PubDriverSelectParam {


    /**
     *  查询文本信息
     */
    private String queryText;

    /**
     * 租赁公司ID
     */
    private String leasescompanyid;

    /**
     * 系统类型 0-运管端，1-租赁端
     */
    private String platformtype;

    /**
     * 司机类型  0-网约车，1-出租车
     */
    private String vehicletype;

    /**
     * 锁定状态 0-未锁定，1-已锁定
     */
    private String lockstatus;

    /**
     * 绑定状态 0-未绑定,1-已绑定
     */
    private String boundstate;

    /**
     * 交接班状态 0-无对班,1-当班,2-歇班,3-交班中
     */
    private String passworkstatus;

    /**
     * 工作状态 0-空闲，1-服务中，2-下线,3-当班,4-交班中,5-歇班
     */
    private String workstatus;
    
    /**
     * 是否为toC(0-是，1-不是)，默认为1
     */
    private String toC;
    
    /**
     * 用户id
     */
    private String userid;

    /**
     * 在职状态
     */
    private String jobstatus;


    public String getLeasescompanyid() {
        return leasescompanyid;
    }

    public void setLeasescompanyid(String leasescompanyid) {
        this.leasescompanyid = leasescompanyid;
    }

    public String getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(String platformtype) {
        this.platformtype = platformtype;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getLockstatus() {
        return lockstatus;
    }

    public void setLockstatus(String lockstatus) {
        this.lockstatus = lockstatus;
    }

    public String getBoundstate() {
        return boundstate;
    }

    public void setBoundstate(String boundstate) {
        this.boundstate = boundstate;
    }

    public String getPassworkstatus() {
        return passworkstatus;
    }

    public void setPassworkstatus(String passworkstatus) {
        this.passworkstatus = passworkstatus;
    }

    public String getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(String workstatus) {
        this.workstatus = workstatus;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

	public String getToC() {
		return toC;
	}

	public void setToC(String toC) {
		this.toC = toC;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

    public String getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }
}
 