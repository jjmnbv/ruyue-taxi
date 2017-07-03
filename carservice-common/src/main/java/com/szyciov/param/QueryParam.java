package com.szyciov.param;

public class QueryParam {
	public QueryParam() {
	}

	public String sEcho;
	/**
	 * 分页开始条数
	 */
	public int iDisplayStart;
	/**
	 * 一页条数
	 */
	public int iDisplayLength;
	/**
	 * 关键字
	 */
	public String key;
	public String iSortColName;
	public String iSortCol_0;
	public String sSortDir_0;

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getiSortColName() {
		return iSortColName;
	}

	public void setiSortColName(String iSortColName) {
		this.iSortColName = iSortColName;
	}

	public String getiSortCol_0() {
		return iSortCol_0;
	}

	public void setiSortCol_0(String iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	public String getsSortDir_0() {
		return sSortDir_0;
	}

	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}
}
