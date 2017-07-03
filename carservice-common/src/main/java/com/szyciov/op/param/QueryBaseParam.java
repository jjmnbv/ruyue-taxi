package com.szyciov.op.param;

/**
 * 列表分页等实体对象 <一句话功能简述> <功能详细描述>
 * 
 * @author 袁金林
 * @version [版本号, 2017年4月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryBaseParam {
	public String curLoginName;
	
	public Integer curUserType;
	public String userId;
	public String createrName;

	public String sEcho;
	// 分页开始条数
	public int iDisplayStart;
	// 一页条数
	public int iDisplayLength;
	// 关键字
	public String key;
	// 数据状态
	public Integer dataStatus;
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

	public Integer getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public String getCurLoginName() {
		return curLoginName;
	}

	public void setCurLoginName(String curLoginName) {
		this.curLoginName = curLoginName;
	}

	public Integer getCurUserType() {
		return curUserType;
	}

	public void setCurUserType(Integer curUserType) {
		this.curUserType = curUserType;
	}

}
