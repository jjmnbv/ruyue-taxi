package com.szyciov.op.entity;


import java.util.Date;
import com.szyciov.driver.param.BaseParam;

/**
  * @ClassName PeUseraccount
  * @author Efy Shu
  * @Description 个人用户钱包类
  * @date 2017年6月17日 17:09:17
  */ 
public class PeUseraccount extends BaseParam{
	/**
	  *主键
	  */
	private String id;

	/**
	  *所属用户 与用户表主键关联
	  */
	private String userid;

	/**
	  *账户余额
	  */
	private double balance;

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
	  *设置主键
	  */
	public void setId(String id){
		this.id=id;
	}

	/**
	  *获取主键
	  */
	public String getId(){
		return id;
	}

	/**
	  *设置所属用户 与用户表主键关联
	  */
	public void setUserid(String userid){
		this.userid=userid;
	}

	/**
	  *获取所属用户 与用户表主键关联
	  */
	public String getUserid(){
		return userid;
	}

	/**
	  *设置账户余额
	  */
	public void setBalance(double balance){
		this.balance=balance;
	}

	/**
	  *获取账户余额
	  */
	public double getBalance(){
		return balance;
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

}
