package com.szyciov.entity;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
  * @ClassName PubAdImage
  * @author Efy
  * @Description 广告图片信息表
  * @date 2016年10月12日 16:56:12
  */ 
public class PubAdImage{
	/**
	  *id
	  */
	private String id;

	/**
	  *0-乘客端,1-司机端
	  */
	private String apptype;

	/**
	  *0-广告页,1-引导页
	  */
	private String imgtype;

	/**
	  *版本号
	  */
	private String version;

	/**
	  *名称
	  */
	private String name;

	/**
	  *有效起始时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "GMT+8")
	private Date starttime;

	/**
	  *有效截至时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "GMT+8")
	private Date endtime;

	/**
	  *图片地址
	  */
	private String imgaddr;

	/**
	  *创建人
	  */
	private String creater;

	/**
	  *更新人
	  */
	private String updater;

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
	 * 启用状态
	 */
	private String usestate;
	
	/**
	 * App类型名称
	 */
	private String apptypeName;
	
	/**
	 * 广告配置是否有效 1-有效，2-无效
	 */
	private String isvalid;

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
	  *设置0-乘客端,1-司机端
	  */
	public void setApptype(String apptype){
		this.apptype=apptype;
	}

	/**
	  *获取0-乘客端,1-司机端
	  */
	public String getApptype(){
		return apptype;
	}

	/**
	  *设置0-广告页,1-引导页
	  */
	public void setImgtype(String imgtype){
		this.imgtype=imgtype;
	}

	/**
	  *获取0-广告页,1-引导页
	  */
	public String getImgtype(){
		return imgtype;
	}

	/**
	  *设置版本号
	  */
	public void setVersion(String version){
		this.version=version;
	}

	/**
	  *获取版本号
	  */
	public String getVersion(){
		return version;
	}

	/**
	  *设置名称
	  */
	public void setName(String name){
		this.name=name;
	}

	/**
	  *获取名称
	  */
	public String getName(){
		return name;
	}

	/**
	  *设置有效起始时间
	  */
	public void setStarttime(Date starttime){
		this.starttime=starttime;
	}

	/**
	  *获取有效起始时间
	  */
	public Date getStarttime(){
		return starttime;
	}

	/**
	  *设置有效截至时间
	  */
	public void setEndtime(Date endtime){
		this.endtime=endtime;
	}

	/**
	  *获取有效截至时间
	  */
	public Date getEndtime(){
		return endtime;
	}

	/**
	  *设置图片地址
	  */
	public void setImgaddr(String imgaddr){
		this.imgaddr=imgaddr;
	}

	/**
	  *获取图片地址
	  */
	public String getImgaddr(){
		return imgaddr;
	}

	/**
	  *设置创建人
	  */
	public void setCreater(String creater){
		this.creater=creater;
	}

	/**
	  *获取创建人
	  */
	public String getCreater(){
		return creater;
	}

	/**
	  *设置更新人
	  */
	public void setUpdater(String updater){
		this.updater=updater;
	}

	/**
	  *获取更新人
	  */
	public String getUpdater(){
		return updater;
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

	public String getUsestate() {
		return usestate;
	}

	public void setUsestate(String usestate) {
		this.usestate = usestate;
	}

	public String getApptypeName() {
		return apptypeName;
	}

	public void setApptypeName(String apptypeName) {
		this.apptypeName = apptypeName;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

}
