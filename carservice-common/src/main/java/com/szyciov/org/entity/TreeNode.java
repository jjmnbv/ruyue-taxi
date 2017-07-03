package com.szyciov.org.entity;

/**
 * 机构端员工管理树形结构
 * @author admin
 *
 */
public class TreeNode {
	/**
	 * 节点类型
	 * 1-公司节点
	 * 2-部门节点
	 * 3-员工节点
	 */
	private String type;
	
	/**
	 * 对象id
	 */
	private String id;
	
	/**
	 * 节点的父id
	 */
	private String pId;
	
	/**
	 * 节点的显示值
	 */
	private String name;
	
	/**
	 * 拥有员工数量
	 */
	private int usercount;
	
	/**
	 * 不显示check框
	 */
	private boolean nocheck;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUsercount() {
		return usercount;
	}

	public void setUsercount(int usercount) {
		this.usercount = usercount;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
}
