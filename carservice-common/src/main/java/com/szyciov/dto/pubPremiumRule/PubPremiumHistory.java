package com.szyciov.dto.pubPremiumRule;

import com.szyciov.param.QueryParam;

public class PubPremiumHistory extends QueryParam{
	 /**
     * 主键
     */
    private String id;

    /**
     * 溢价规则主表ID
     */
    private String premiumruleid	;

    /**
     * 规则类型(0-按星期 1-按日期)
     */
    private String ruletype;

    /**
     * 操作类型(0-启用 1-禁用 2-修改)
     */
    private String operationtype;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 更新时间
     */
    private String updatetime;

    /**
     * 创建人
     */
    private String creater;
    private String updater;
    
    private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPremiumruleid() {
		return premiumruleid;
	}
	public void setPremiumruleid(String premiumruleid) {
		this.premiumruleid = premiumruleid;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getRuletype() {
		return ruletype;
	}
	public void setRuletype(String ruletype) {
		this.ruletype = ruletype;
	}
	public String getOperationtype() {
		return operationtype;
	}
	public void setOperationtype(String operationtype) {
		this.operationtype = operationtype;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
    
}
