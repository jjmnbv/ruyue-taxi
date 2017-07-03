package com.szyciov.entity;


import com.szyciov.driver.param.BaseParam;

import java.util.Date;

/**
  * @ClassName PubDriver
  * @author Efy
  * @Description 司机实体类
  * @date 2017年3月20日 11:07:20
  */ 
public class PubDriver extends BaseParam{
	/**
	  *主键
	  */
	private String id;

	/**
	  *所属租赁公司
	  */
	private String leasescompanyid;

	/**
	  *工号
	  */
	private String jobnum;

	/**
	  *姓名
	  */
	private String name;

	/**
	  *手机号码
	  */
	private String phone;

	/**
	  *0-男，1-女  性别
	  */
	private String sex;

	/**
	  *用户密码
	  */
	private String userpassword;

	/**
	  *驾驶证类型包括A1,A2,A3,B1,B2,B3,C1,C2,C3    驾驶证类型
	  */
	private String driverstype;

	/**
	  *初次领证时间
	  */
	private Date cardtime;

	/**
	  *关联字典表获取数据  所属城市
	  */
	private String city;

	/**
	  *0-在职，1-离职 在职状态
	  */
	private String jobstatus;

	/**
	  *0-普通司机，1-特殊司机 2 出租车司机身份 司机身份类型
	  */
	private String identitytype;

	/**
	  *司机驾驶证号码
	  */
	private String driversnum;

	/**
	  *司机驾驶证图片
	  */
	private String driverphoto;

	/**
	  *身份证号码
	  */
	private String idcardnum;

	/**
	  *身份证正面图片
	  */
	private String idcardfront;

	/**
	  *身份证背面图片
	  */
	private String idcardback;

	/**
	  *0-空闲，1-服务中，2-下线, 3 处在未绑定状态给3，方便前台显示工作状态
	  */
	private String workstatus;

	/**
	  *GPS速度
	  */
	private double gpsspeed;

	/**
	  *GPS方向
	  */
	private double gpsdirection;

	/**
	  *经度
	  */
	private double lng;

	/**
	  *纬度
	  */
	private double lat;

	/**
	  *评价星级
	  */
	private double avgrate;

	/**
	  *累计接单量
	  */
	private int ordercount;

	/**
	  *创建时间
	  */
	private Date CreateTime;

	/**
	  *更新时间
	  */
	private Date UpdateTime;

	/**
	  *创建人
	  */
	private String Creater;

	/**
	  *更新人
	  */
	private String Updater;

	/**
	  *数据状态
	  */
	private int Status;

	/**
	  *头像（小）
	  */
	private String headportraitmin;

	/**
	  *头像（大）
	  */
	private String headportraitmax;

	/**
	  *驾驶年
	  */
	private double driveryears;

	/**
	  *司机类型 网约车 0、出租车 1
	  */
	private int vehicletype;

	/**
	  *绑定状态 0 未 1 绑
	  */
	private int boundstate;

	/**
	  *锁定状态 0-未锁定，1-已锁定
	  */
	private int lockstatus;

	/**
	  *所属平台  0-运管端，1-租赁端
	  */
	private String platformtype;

	/**
	  *交接班状态  0-无对班,1-当班,2-歇班,3-交班中,4-接班中
	  */
	private String passworkstatus;

	/**
	 * 提现密码
	 */
	private String withdrawpwd;
	
	/**
	 * 提现密码修改状态(0-未修改，1-已修改)
	 */
	private String wdpwdchangestate;
	
	/**
	 * 心跳包时间
	 */
	private Date heartbeattime;
	/**
	 * 归属车企,从字典表中读取
	 */
	private String belongleasecompany;
	/**
	 * 极光推送ID
	 */
	private String registrationid;
	/*****************************************附加字段******************************************/
	/**
	 * 车辆ID
	 */
	private String vehicleid;
	
	/**
	 * 所属toB业务车型
	 */
	private String orgcartypeid;
	
	/**
	 * 所属toC业务车型
	 */
	private String opcartypeid;
	
	/**
	 * 车牌号
	 */
	private String plateno;
	
	/**
	 * 车辆品牌
	 */
	private String vehcbrandname;
	
	/**
	 * 车辆车系
	 */
	private String vehclinename;
	/**
	 * 车型级别
	 */
	private int vehiclelevel; 
	//上线时间 秒 
	private Date uptime;
	//在线时长(单位：秒)
	private int onlinetime;
	//类型 0 上班 1 下班
	private String type;
    /**
     * gps来源
     */
    private Integer gpssource;
    /**
     * gps时间
     */
    private Date gpstime;
    /**客服电话*/
    private String servicephone;
	/*****************************************附加字段end******************************************/
	
	/**
	  *设置主键
	  */
	public void setId(String id){
		this.id=id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public int getOnlinetime() {
		return onlinetime;
	}

	public void setOnlinetime(int onlinetime) {
		this.onlinetime = onlinetime;
	}

	/**
	  *获取主键
	  */
	public String getId(){
		return id;
	}

	/**
	  *设置所属租赁公司
	  */
	public void setLeasescompanyid(String leasescompanyid){
		this.leasescompanyid=leasescompanyid;
	}

	/**
	  *获取所属租赁公司
	  */
	public String getLeasescompanyid(){
		return leasescompanyid;
	}

	/**
	  *设置工号
	  */
	public void setJobnum(String jobnum){
		this.jobnum=jobnum;
	}

	/**
	  *获取工号
	  */
	public String getJobnum(){
		return jobnum;
	}

	/**
	  *设置姓名
	  */
	public void setName(String name){
		this.name=name;
	}

	/**
	  *获取姓名
	  */
	public String getName(){
		return name;
	}

	/**
	  *设置手机号码
	  */
	public void setPhone(String phone){
		this.phone=phone;
	}

	/**
	  *获取手机号码
	  */
	public String getPhone(){
		return phone;
	}

	/**
	  *设置0-男，1-女  性别
	  */
	public void setSex(String sex){
		this.sex=sex;
	}

	/**
	  *获取0-男，1-女  性别
	  */
	public String getSex(){
		return sex;
	}

	/**
	  *设置用户密码
	  */
	public void setUserpassword(String userpassword){
		this.userpassword=userpassword;
	}

	/**
	  *获取用户密码
	  */
	public String getUserpassword(){
		return userpassword;
	}

	/**
	  *设置驾驶证类型包括A1,A2,A3,B1,B2,B3,C1,C2,C3    驾驶证类型
	  */
	public void setDriverstype(String driverstype){
		this.driverstype=driverstype;
	}

	/**
	  *获取驾驶证类型包括A1,A2,A3,B1,B2,B3,C1,C2,C3    驾驶证类型
	  */
	public String getDriverstype(){
		return driverstype;
	}

	/**
	  *设置初次领证时间
	  */
	public void setCardtime(Date cardtime){
		this.cardtime=cardtime;
	}

	/**
	  *获取初次领证时间
	  */
	public Date getCardtime(){
		return cardtime;
	}

	/**
	  *设置关联字典表获取数据  所属城市
	  */
	public void setCity(String city){
		this.city=city;
	}

	/**
	  *获取关联字典表获取数据  所属城市
	  */
	public String getCity(){
		return city;
	}

	/**
	  *设置0-在职，1-离职 在职状态
	  */
	public void setJobstatus(String jobstatus){
		this.jobstatus=jobstatus;
	}

	/**
	  *获取0-在职，1-离职 在职状态
	  */
	public String getJobstatus(){
		return jobstatus;
	}

	/**
	  *设置0-普通司机，1-特殊司机 2 出租车司机身份 司机身份类型
	  */
	public void setIdentitytype(String identitytype){
		this.identitytype=identitytype;
	}

	/**
	  *获取0-普通司机，1-特殊司机 2 出租车司机身份 司机身份类型
	  */
	public String getIdentitytype(){
		return identitytype;
	}

	/**
	  *设置司机驾驶证号码
	  */
	public void setDriversnum(String driversnum){
		this.driversnum=driversnum;
	}

	/**
	  *获取司机驾驶证号码
	  */
	public String getDriversnum(){
		return driversnum;
	}

	/**
	  *设置司机驾驶证图片
	  */
	public void setDriverphoto(String driverphoto){
		this.driverphoto=driverphoto;
	}

	/**
	  *获取司机驾驶证图片
	  */
	public String getDriverphoto(){
		return driverphoto;
	}

	/**
	  *设置身份证号码
	  */
	public void setIdcardnum(String idcardnum){
		this.idcardnum=idcardnum;
	}

	/**
	  *获取身份证号码
	  */
	public String getIdcardnum(){
		return idcardnum;
	}

	/**
	  *设置身份证正面图片
	  */
	public void setIdcardfront(String idcardfront){
		this.idcardfront=idcardfront;
	}

	/**
	  *获取身份证正面图片
	  */
	public String getIdcardfront(){
		return idcardfront;
	}

	/**
	  *设置身份证背面图片
	  */
	public void setIdcardback(String idcardback){
		this.idcardback=idcardback;
	}

	/**
	  *获取身份证背面图片
	  */
	public String getIdcardback(){
		return idcardback;
	}

	/**
	  *设置0-空闲，1-服务中，2-下线, 3 处在未绑定状态给3，方便前台显示工作状态
	  */
	public void setWorkstatus(String workstatus){
		this.workstatus=workstatus;
	}

	/**
	  *获取0-空闲，1-服务中，2-下线, 3 处在未绑定状态给3，方便前台显示工作状态
	  */
	public String getWorkstatus(){
		return workstatus;
	}

	/**
	  *设置GPS速度
	  */
	public void setGpsspeed(double gpsspeed){
		this.gpsspeed=gpsspeed;
	}

	/**
	  *获取GPS速度
	  */
	public double getGpsspeed(){
		return gpsspeed;
	}

	/**
	  *设置GPS方向
	  */
	public void setGpsdirection(double gpsdirection){
		this.gpsdirection=gpsdirection;
	}

	/**
	  *获取GPS方向
	  */
	public double getGpsdirection(){
		return gpsdirection;
	}

	/**
	  *设置经度
	  */
	public void setLng(double lng){
		this.lng=lng;
	}

	/**
	  *获取经度
	  */
	public double getLng(){
		return lng;
	}

	/**
	  *设置纬度
	  */
	public void setLat(double lat){
		this.lat=lat;
	}

	/**
	  *获取纬度
	  */
	public double getLat(){
		return lat;
	}

	/**
	  *设置评价星级
	  */
	public void setAvgrate(double avgrate){
		this.avgrate=avgrate;
	}

	/**
	  *获取评价星级
	  */
	public double getAvgrate(){
		return avgrate;
	}

	/**
	  *设置累计接单量
	  */
	public void setOrdercount(int ordercount){
		this.ordercount=ordercount;
	}

	/**
	  *获取累计接单量
	  */
	public int getOrdercount(){
		return ordercount;
	}

	/**
	  *设置创建时间
	  */
	public void setCreateTime(Date CreateTime){
		this.CreateTime=CreateTime;
	}

	/**
	  *获取创建时间
	  */
	public Date getCreateTime(){
		return CreateTime;
	}

	/**
	  *设置更新时间
	  */
	public void setUpdateTime(Date UpdateTime){
		this.UpdateTime=UpdateTime;
	}

	/**
	  *获取更新时间
	  */
	public Date getUpdateTime(){
		return UpdateTime;
	}

	/**
	  *设置创建人
	  */
	public void setCreater(String Creater){
		this.Creater=Creater;
	}

	/**
	  *获取创建人
	  */
	public String getCreater(){
		return Creater;
	}

	/**
	  *设置更新人
	  */
	public void setUpdater(String Updater){
		this.Updater=Updater;
	}

	/**
	  *获取更新人
	  */
	public String getUpdater(){
		return Updater;
	}

	/**
	  *设置数据状态
	  */
	public void setStatus(int Status){
		this.Status=Status;
	}

	/**
	  *获取数据状态
	  */
	public int getStatus(){
		return Status;
	}

	/**
	  *设置头像（小）
	  */
	public void setHeadportraitmin(String headportraitmin){
		this.headportraitmin=headportraitmin;
	}

	/**
	  *获取头像（小）
	  */
	public String getHeadportraitmin(){
		return headportraitmin;
	}

	/**
	  *设置头像（大）
	  */
	public void setHeadportraitmax(String headportraitmax){
		this.headportraitmax=headportraitmax;
	}

	/**
	  *获取头像（大）
	  */
	public String getHeadportraitmax(){
		return headportraitmax;
	}

	/**
	  *设置驾驶年
	  */
	public void setDriveryears(double driveryears){
		this.driveryears=driveryears;
	}

	/**
	  *获取驾驶年
	  */
	public double getDriveryears(){
		return driveryears;
	}

	/**
	  *设置司机类型 网约车 0、出租车 1
	  */
	public void setVehicletype(int vehicletype){
		this.vehicletype=vehicletype;
	}

	/**
	  *获取司机类型 网约车 0、出租车 1
	  */
	public int getVehicletype(){
		return vehicletype;
	}

	/**
	  *设置绑定状态 0 未 1 绑
	  */
	public void setBoundstate(int boundstate){
		this.boundstate=boundstate;
	}

	/**
	  *获取绑定状态 0 未 1 绑
	  */
	public int getBoundstate(){
		return boundstate;
	}

	/**
	  *设置锁定状态 0-未锁定，1-已锁定
	  */
	public void setLockstatus(int lockstatus){
		this.lockstatus=lockstatus;
	}

	/**
	  *获取锁定状态 0-未锁定，1-已锁定
	  */
	public int getLockstatus(){
		return lockstatus;
	}

	/**
	  *设置所属平台  0-运管端，1-租赁端
	  */
	public void setPlatformtype(String platformtype){
		this.platformtype=platformtype;
	}

	/**
	  *获取所属平台  0-运管端，1-租赁端
	  */
	public String getPlatformtype(){
		return platformtype;
	}

	/**
	  *设置交接班状态  0-无对班,1-当班,2-歇班,3-交班中,4-接班中
	  */
	public void setPassworkstatus(String passworkstatus){
		this.passworkstatus=passworkstatus;
	}

	/**
	  *获取交接班状态  0-无对班,1-当班,2-歇班,3-交班中,4-接班中
	  */
	public String getPassworkstatus(){
		return passworkstatus;
	}

	/**  
	 * 获取  
	 * @return vehicleid   
	 */
	public String getVehicleid() {
		return vehicleid;
	}
	

	/**  
	 * 设置  
	 * @param vehicleid   
	 */
	public void setVehicleid(String vehicleid) {
		this.vehicleid = vehicleid;
	}
	

	/**  
	 * 获取所属toB业务车型  
	 * @return orgcartypeid 所属toB业务车型  
	 */
	public String getOrgcartypeid() {
		return orgcartypeid;
	}
	

	/**  
	 * 设置所属toB业务车型  
	 * @param orgcartypeid 所属toB业务车型  
	 */
	public void setOrgcartypeid(String orgcartypeid) {
		this.orgcartypeid = orgcartypeid;
	}
	

	/**  
	 * 获取所属toC业务车型  
	 * @return opcartypeid 所属toC业务车型  
	 */
	public String getOpcartypeid() {
		return opcartypeid;
	}
	

	/**  
	 * 设置所属toC业务车型  
	 * @param opcartypeid 所属toC业务车型  
	 */
	public void setOpcartypeid(String opcartypeid) {
		this.opcartypeid = opcartypeid;
	}

	/**  
	 * 获取车牌号  
	 * @return plateno 车牌号  
	 */
	public String getPlateno() {
		return plateno;
	}

	/**  
	 * 设置车牌号  
	 * @param plateno 车牌号  
	 */
	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}

	/**  
	 * 获取提现密码  
	 * @return withdrawpwd 提现密码  
	 */
	public String getWithdrawpwd() {
		return withdrawpwd;
	}
	
	/**  
	 * 设置提现密码  
	 * @param withdrawpwd 提现密码  
	 */
	public void setWithdrawpwd(String withdrawpwd) {
		this.withdrawpwd = withdrawpwd;
	}
	
	/**  
	 * 获取提现密码修改状态(0-未修改，1-已修改)  
	 * @return wdpwdchangestate 提现密码修改状态(0-未修改，1-已修改)  
	 */
	public String getWdpwdchangestate() {
		return wdpwdchangestate;
	}
	
	/**  
	 * 设置提现密码修改状态(0-未修改，1-已修改)  
	 * @param wdpwdchangestate 提现密码修改状态(0-未修改，1-已修改)  
	 */
	public void setWdpwdchangestate(String wdpwdchangestate) {
		this.wdpwdchangestate = wdpwdchangestate;
	}

	/**  
	 * 获取车辆品牌  
	 * @return vehcbrandname 车辆品牌  
	 */
	public String getVehcbrandname() {
		return vehcbrandname;
	}

	/**  
	 * 设置车辆品牌  
	 * @param vehcbrandname 车辆品牌  
	 */
	public void setVehcbrandname(String vehcbrandname) {
		this.vehcbrandname = vehcbrandname;
	}
	
	/**  
	 * 获取车辆车系  
	 * @return vehclinename 车辆车系  
	 */
	public String getVehclinename() {
		return vehclinename;
	}

	/**  
	 * 设置车辆车系  
	 * @param vehclinename 车辆车系  
	 */
	public void setVehclinename(String vehclinename) {
		this.vehclinename = vehclinename;
	}

	/**  
	 * 获取心跳包时间  
	 * @return heartbeattime 心跳包时间  
	 */
	public Date getHeartbeattime() {
		return heartbeattime;
	}
	
	/**  
	 * 设置心跳包时间  
	 * @param heartbeattime 心跳包时间  
	 */
	public void setHeartbeattime(Date heartbeattime) {
		this.heartbeattime = heartbeattime;
	}

	/**  
	 * 获取归属车企从字典表中读取  
	 * @return belongleasecompany 归属车企从字典表中读取  
	 */
	public String getBelongleasecompany() {
		return belongleasecompany;
	}
	

	/**  
	 * 设置归属车企从字典表中读取  
	 * @param belongleasecompany 归属车企从字典表中读取  
	 */
	public void setBelongleasecompany(String belongleasecompany) {
		this.belongleasecompany = belongleasecompany;
	}

    public Integer getGpssource() {
        return gpssource;
    }

    public void setGpssource(Integer gpssource) {
        this.gpssource = gpssource;
    }

    public Date getGpstime() {
        return gpstime;
    }

    public void setGpstime(Date gpstime) {
        this.gpstime = gpstime;
    }

	/**  
	 * 获取极光推送ID  
	 * @return registrationid 极光推送ID  
	 */
	public String getRegistrationid() {
		return registrationid;
	}
	
	/**  
	 * 设置极光推送ID  
	 * @param registrationid 极光推送ID  
	 */
	public void setRegistrationid(String registrationid) {
		this.registrationid = registrationid;
	}

	/**  
	 * 获取车型级别  
	 * @return vehiclelevel 车型级别  
	 */
	public int getVehiclelevel() {
		return vehiclelevel;
	}
	

	/**  
	 * 设置车型级别  
	 * @param vehiclelevel 车型级别  
	 */
	public void setVehiclelevel(int vehiclelevel) {
		this.vehiclelevel = vehiclelevel;
	}

	/**  
	 * 获取客服电话  
	 * @return servicephone 客服电话  
	 */
	public String getServicephone() {
		return servicephone;
	}
	
	/**  
	 * 设置客服电话  
	 * @param servicephone 客服电话  
	 */
	public void setServicephone(String servicephone) {
		this.servicephone = servicephone;
	}
	
}
