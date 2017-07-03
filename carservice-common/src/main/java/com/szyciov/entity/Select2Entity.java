/**
 * 
 */
package com.szyciov.entity;

/**
 * @ClassName Select2Entity
 * @author Efy Shu
 * @Description 页面select2控件数据类
 * @date 2016年9月28日 下午12:43:08
 */
public class Select2Entity {
	/**
	 * 数据id
	 */
	private String id;
	/**
	 * 数据值
	 */
	private String text;

	/**
	 * 获取数据id
	 * 
	 * @return id 数据id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置数据id
	 * 
	 * @param id
	 *            数据id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取数据值
	 * 
	 * @return text 数据值
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置数据值
	 * 
	 * @param text
	 *            数据值
	 */
	public void setText(String text) {
		this.text = text;
	}

}
