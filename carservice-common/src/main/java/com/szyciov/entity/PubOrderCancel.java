package com.szyciov.entity;


import java.util.Date;
import com.szyciov.driver.param.BaseParam;

/**
  * @ClassName PubOrderCancel
  * @author Efy Shu
  * @Description 订单取消信息表
  * @date 2017年8月2日 17:42:02
  */ 
public class PubOrderCancel extends BaseParam{

    /**
     * 主键
     */
    private String id;

    /**
	  *订单号
	  */
	private String orderno;

	/**
	  *责任方(1-乘客,2-司机,3-客服,4-平台)
	  */
	private int dutyparty;

	/**
	  *取消费用
	  */
	private int cancelamount;

	/**
	  *取消性质(1-有责,2-免责)
	  */
	private int cancelnature;

	/**
	  *取消原因(1-不再需要用车,2-乘客迟到违约,3-司机迟到违约,4-司机不愿接乘客,5-业务操作错误,6-暂停供车服务,7-系统派单失败)
	  */
	private int cancelreason;

	/**
	  *取消操作人
	  */
	private String canceloperator;

	/**
	  *免责处理原因
	  */
	private String exemption;

	/**
	  *免责处理人
	  */
	private String exemptionoperator;

	/**
	  *取消副本
	  */
	private String cancelrule;

    /**
     * 操作唯一标识
     */
    private String identifying;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
	  *设置订单号
	  */
	public void setOrderno(String orderno){
		this.orderno=orderno;
	}

	/**
	  *获取订单号
	  */
	public String getOrderno(){
		return orderno;
	}

	/**
	  *设置责任方(1-乘客,2-司机,3-客服,4-平台)
	  */
	public void setDutyparty(int dutyparty){
		this.dutyparty=dutyparty;
	}

	/**
	  *获取责任方(1-乘客,2-司机,3-客服,4-平台)
	  */
	public int getDutyparty(){
		return dutyparty;
	}

	/**
	  *设置取消费用
	  */
	public void setCancelamount(int cancelamount){
		this.cancelamount=cancelamount;
	}

	/**
	  *获取取消费用
	  */
	public int getCancelamount(){
		return cancelamount;
	}

	/**
	  *设置取消性质(1-有责,2-免责)
	  */
	public void setCancelnature(int cancelnature){
		this.cancelnature=cancelnature;
	}

	/**
	  *获取取消性质(1-有责,2-免责)
	  */
	public int getCancelnature(){
		return cancelnature;
	}

	/**
	  *设置取消原因(1-不再需要用车,2-乘客迟到违约,3-司机迟到违约,4-司机不愿接乘客,5-业务操作错误,6-暂停供车服务,7-系统派单失败)
	  */
	public void setCancelreason(int cancelreason){
		this.cancelreason=cancelreason;
	}

	/**
	  *获取取消原因(1-不再需要用车,2-乘客迟到违约,3-司机迟到违约,4-司机不愿接乘客,5-业务操作错误,6-暂停供车服务,7-系统派单失败)
	  */
	public int getCancelreason(){
		return cancelreason;
	}

	/**
	  *设置取消操作人
	  */
	public void setCanceloperator(String canceloperator){
		this.canceloperator=canceloperator;
	}

	/**
	  *获取取消操作人
	  */
	public String getCanceloperator(){
		return canceloperator;
	}

	/**
	  *设置免责处理原因
	  */
	public void setExemption(String exemption){
		this.exemption=exemption;
	}

	/**
	  *获取免责处理原因
	  */
	public String getExemption(){
		return exemption;
	}

	/**
	  *设置免责处理人
	  */
	public void setExemptionoperator(String exemptionoperator){
		this.exemptionoperator=exemptionoperator;
	}

	/**
	  *获取免责处理人
	  */
	public String getExemptionoperator(){
		return exemptionoperator;
	}

	/**
	  *设置取消副本
	  */
	public void setCancelrule(String cancelrule){
		this.cancelrule=cancelrule;
	}

	/**
	  *获取取消副本
	  */
	public String getCancelrule(){
		return cancelrule;
	}

    public String getIdentifying() {
        return identifying;
    }

    public void setIdentifying(String identifying) {
        this.identifying = identifying;
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
