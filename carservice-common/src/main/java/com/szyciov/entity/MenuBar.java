package com.szyciov.entity;

import java.util.List;

/**
 * 菜单栏
 */
public class MenuBar {
	/**
	 * 路径
	 */
	private String url;
	/**
	 * 应用所属域
	 */
	private String applicationdomain;
	/**
	 * 菜单名称
	 */
	private String menuname;
	/**
	 * 样式
	 */
	private String cssclass;
	/**
	 * 父亲节点id
	 */
	private String parentid;
	/**
	 * 菜单id
	 */
	private String id;
	/**
	 * 层级
	 */
	private Integer level;
	/**
	 * 序号
	 */
	private Integer sequence;
	/**
	 * 子菜单栏列表
	 */
	private List<MenuBar> children;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getApplicationdomain() {
		return applicationdomain;
	}
	public void setApplicationdomain(String applicationdomain) {
		this.applicationdomain = applicationdomain;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getCssclass() {
		return cssclass;
	}
	public void setCssclass(String cssclass) {
		this.cssclass = cssclass;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public List<MenuBar> getChildren() {
		return children;
	}
	public void setChildren(List<MenuBar> children) {
		this.children = children;
	}
}
