package com.szyciov.op.entity;

import java.util.List;

/**
 * 租赁端分配权限的树形节点对象
 * @author admin
 *
 */
public class TreeNode {
	/**
	 * node的唯一id
	 */
	private String id;
	
	/**
	 * node名称
	 */
	private String name;
	
	/**
	 * 父级id
	 */
	private String pid;

	/**
	 * 对应图标
	 */
	private String icon;
	
	/**
	 * 是否选择
	 */
	private Boolean checked;
	
	/**
	 * 是否展开
	 */
	private Boolean open;
	
	/**
	 * 是否父节点
	 */
	private Boolean isParent;
	
	/**
	 * 是否展示check框
	 */
	private boolean nocheck;
	
	private List<TreeNode> children;

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}