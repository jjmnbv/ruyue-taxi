package com.szyciov.lease.entity;

import java.util.List;

/**
 * 角色管理
 */
public class RoleManagement {

	private String id;
	
	private String leasescompanyid;
	
	private String roletype;
	
	private String roledesc;
	
	private String rolename;
	
	private String creater;
	
	private String updater;
	
	private String createtime;
	
	private String updatetime;
	
	private int status;
	
	private String roletypecaption;
	
	private List<OrgOrgan> org4lease;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
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

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public String getRoledesc() {
		return roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	public String getRoletypecaption() {
		return roletypecaption;
	}

	public void setRoletypecaption(String roletypecaption) {
		this.roletypecaption = roletypecaption;
	}

	public List<OrgOrgan> getOrg4lease() {
		return org4lease;
	}

	public void setOrg4lease(List<OrgOrgan> org4lease) {
		this.org4lease = org4lease;
	}
}
