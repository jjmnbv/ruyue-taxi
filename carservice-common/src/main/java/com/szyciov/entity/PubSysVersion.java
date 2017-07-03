package com.szyciov.entity;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
  * @ClassName PubSysVersion
  * @author Efy
  * @Description 客户端版本维护表
  * @date 2016年10月12日 17:27:12
  */ 
public class PubSysVersion{
	/**
	  *id
	  */
	private String id;

	/**
	  *终端类型 包括(0-乘客端,1-司机端,2-租赁端,3-机构端,4-运营端)
	  */
	private String platformtype;

	/**
	  *系统类型 包括(0-IOS， 1- Android)
	  */
	private String systemtype;

	/**
	  *当前版本
	  */
	private String curversion;

	/**
	  *版本号
	  */
	private int versionno;

	/**
	  *向下兼容最高版本号
	  */
	private Integer maxversionno;

	/**
	  *版本变更日志
	  */
	private String changelog;

	/**
	  *下载地址
	  */
	private String downloadhref;

	/**
	  *创建时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createtime;

	/**
	  *更新时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updatetime;

	/**
	  *数据状态
	  */
	private Integer status;
	
	/**
	 * 终端类型名称
	 */
	private String platformtypeName;
	
	/**
	 * 系统类型名称
	 */
	private String systemtypeName;
	
	/**
	 * 是否强制更新 0-是，1-否
	 */
	private int isForceUpdate;
	
	/**
	 * 发布日期
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date releasedate;

	/**
	  *设置id
	  */
	public void setId(String id){
		this.id=id;
	}

	/**
	  *获取id
	  */
	public String getId(){
		return id;
	}

	/**
	  *设置终端类型 包括(0-乘客端,1-司机端,2-租赁端,3-机构端,4-运营端)
	  */
	public void setPlatformtype(String platformtype){
		this.platformtype=platformtype;
	}

	/**
	  *获取终端类型 包括(0-乘客端,1-司机端,2-租赁端,3-机构端,4-运营端)
	  */
	public String getPlatformtype(){
		return platformtype;
	}

	/**
	  *设置系统类型 包括(0-IOS， 1- Android)
	  */
	public void setSystemtype(String systemtype){
		this.systemtype=systemtype;
	}

	/**
	  *获取系统类型 包括(0-IOS， 1- Android)
	  */
	public String getSystemtype(){
		return systemtype;
	}

	/**
	  *设置当前版本
	  */
	public void setCurversion(String curversion){
		this.curversion=curversion;
	}

	/**
	  *获取当前版本
	  */
	public String getCurversion(){
		return curversion;
	}

	/**
	  *设置版本号
	  */
	public void setVersionno(int versionno){
		this.versionno=versionno;
	}

	/**
	  *获取版本号
	  */
	public int getVersionno(){
		return versionno;
	}

	/**
	  *设置向下兼容最高版本号
	  */
	public void setMaxversionno(Integer maxversionno){
		this.maxversionno=maxversionno;
	}

	/**
	  *获取向下兼容最高版本号
	  */
	public Integer getMaxversionno(){
		return maxversionno;
	}

	/**
	  *设置版本变更日志
	  */
	public void setChangelog(String changelog){
		this.changelog=changelog;
	}

	/**
	  *获取版本变更日志
	  */
	public String getChangelog(){
		return changelog;
	}

	/**
	  *设置下载地址
	  */
	public void setDownloadhref(String downloadhref){
		this.downloadhref=downloadhref;
	}

	/**
	  *获取下载地址
	  */
	public String getDownloadhref(){
		return downloadhref;
	}

	/**
	  *设置创建时间
	  */
	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}

	/**
	  *获取创建时间
	  */
	public Date getCreatetime(){
		return createtime;
	}

	/**
	  *设置更新时间
	  */
	public void setUpdatetime(Date updatetime){
		this.updatetime=updatetime;
	}

	/**
	  *获取更新时间
	  */
	public Date getUpdatetime(){
		return updatetime;
	}

	/**
	  *设置数据状态
	  */
	public void setStatus(Integer status){
		this.status=status;
	}

	/**
	  *获取数据状态
	  */
	public Integer getStatus(){
		return status;
	}

	public String getPlatformtypeName() {
		return platformtypeName;
	}

	public void setPlatformtypeName(String platformtypeName) {
		this.platformtypeName = platformtypeName;
	}

	public String getSystemtypeName() {
		return systemtypeName;
	}

	public void setSystemtypeName(String systemtypeName) {
		this.systemtypeName = systemtypeName;
	}

	public int getIsForceUpdate() {
		return isForceUpdate;
	}

	public void setIsForceUpdate(int isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

	public Date getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(Date releasedate) {
		this.releasedate = releasedate;
	}

}
