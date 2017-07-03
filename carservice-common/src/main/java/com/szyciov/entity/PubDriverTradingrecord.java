package com.szyciov.entity;


import java.util.Date;
import com.szyciov.driver.param.BaseParam;

/**
  * @ClassName PubDriverTradingrecord
  * @author Efy
  * @Description 司机第三方交易记录实体类
  * @date 2017年4月6日 17:11:06
  */ 
public class PubDriverTradingrecord extends BaseParam{
	/**
	  *支付订单号（主键）
	  */
	private String outtradeno;

	/**
	  *所属租赁公司
	  */
	private String leasescompanyid;

	/**
	  *所属司机
	  */
	private String driverid;

	/**
	  *0-充值、1-订单支付
	  */
	private int type;

	/**
	  *1-微信、2支付宝
	  */
	private int paymenttype;

	/**
	  *流水号
	  */
	private String tradeno;

	/**
	  *验证密钥
	  */
	private String validatekey;

	/**
	  *0-待确认,1-成功,2-取消(失败)
	  */
	private int tradingstatus;

	/**
	  *创建时间
	  */
	private Date createtime;

	/**
	  *更新时间
	  */
	private Date updatetime;

	/**
	  *数据状态
	  */
	private int status;

	/**
	  *所属订单号
	  */
	private String orderno;

	/**
	  *金额
	  */
	private double amount;

	/**
	  *设置支付订单号（主键）
	  */
	public void setOuttradeno(String outtradeno){
		this.outtradeno=outtradeno;
	}

	/**
	  *获取支付订单号（主键）
	  */
	public String getOuttradeno(){
		return outtradeno;
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
	  *设置所属司机
	  */
	public void setDriverid(String driverid){
		this.driverid=driverid;
	}

	/**
	  *获取所属司机
	  */
	public String getDriverid(){
		return driverid;
	}

	/**
	  *设置0-充值、1-订单支付
	  */
	public void setType(int type){
		this.type=type;
	}

	/**
	  *获取0-充值、1-订单支付
	  */
	public int getType(){
		return type;
	}

	/**
	  *设置1-微信、2支付宝
	  */
	public void setPaymenttype(int paymenttype){
		this.paymenttype=paymenttype;
	}

	/**
	  *获取1-微信、2支付宝
	  */
	public int getPaymenttype(){
		return paymenttype;
	}

	/**
	  *设置流水号
	  */
	public void setTradeno(String tradeno){
		this.tradeno=tradeno;
	}

	/**
	  *获取流水号
	  */
	public String getTradeno(){
		return tradeno;
	}

	/**
	  *设置验证密钥
	  */
	public void setValidatekey(String validatekey){
		this.validatekey=validatekey;
	}

	/**
	  *获取验证密钥
	  */
	public String getValidatekey(){
		return validatekey;
	}

	/**
	  *设置0-待确认,1-成功,2-取消(失败)
	  */
	public void setTradingstatus(int tradingstatus){
		this.tradingstatus=tradingstatus;
	}

	/**
	  *获取0-待确认,1-成功,2-取消(失败)
	  */
	public int getTradingstatus(){
		return tradingstatus;
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
	public void setStatus(int status){
		this.status=status;
	}

	/**
	  *获取数据状态
	  */
	public int getStatus(){
		return status;
	}

	/**
	  *设置所属订单号
	  */
	public void setOrderno(String orderno){
		this.orderno=orderno;
	}

	/**
	  *获取所属订单号
	  */
	public String getOrderno(){
		return orderno;
	}

	/**
	  *设置金额
	  */
	public void setAmount(double amount){
		this.amount=amount;
	}

	/**
	  *获取金额
	  */
	public double getAmount(){
		return amount;
	}

}
