package com.szyciov.op.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class OpOrderReview {
	
	private String id;
	
	/**
	 * 所属订单
	 */
	private String orderno;
	
	/**
	 * 差异金额
	 */
	private Double price;
	
	/**
	 * 原服务开始时间
	 */
	private Date rawstarttime;
	
	/**
	 * 原服务结束时间
	 */
	private Date rawendtime;
	
	/**
	 * 原累计时间
	 */
	private Double rawtimes;
	
	/**
	 * 原服务里程(米)
	 */
	private Double rawmileage;
	
	/**
	 * 服务开始时间
	 */
	private Date starttime;
	
	/**
	 * 服务结束时间
	 */
	private Date endtime;
	
	/**
	 * 服务时长
	 */
	private Double times;
	
	/**
	 * 累计时长
	 */
	private Double counttimes;
	
	/**
	 * 服务里程(米)
	 */
	private Double mileage;
	
	/**
	 * 时间补贴
	 */
	private Double timesubsidies;
	
	/**
	 * 里程费用
	 */
	private Double mileageprices;
	
	/**
	 * 提出复议方
	 */
	private String reviewperson;
	
	/**
	 * 复议原因
	 */
	private String reason;
	
	/**
	 * 复议时间
	 */
	private Date reviewtime;
	
	/**
	 * 复议人
	 */
	private String operator;
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	/**
	 * 更新时间
	 */
	private Date updatetime;
	
	/**
	 * 数据状态
	 */
	private Integer status;
	
	/**
	 * 订单金额
	 */
	private Double reviewedprice;
	
	/**
	 * 处理意见
	 */
	private String opinion;
	
	/**
	 * 原订单金额
	 */
	private Double raworderamount;

    /**
     * 复核后计价副本
     */
    private String pricecopy;

    /**
     * 原计价副本
     */
    private String rawpricecopy;

    /**
     * 复核类型(1-按里程时长,2-按固定金额)
     */
    private Integer reviewtype;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getRawstarttime() {
		return rawstarttime;
	}

	public void setRawstarttime(Date rawstarttime) {
		this.rawstarttime = rawstarttime;
	}

	public Date getRawendtime() {
		return rawendtime;
	}

	public void setRawendtime(Date rawendtime) {
		this.rawendtime = rawendtime;
	}

	public Double getRawtimes() {
		return rawtimes;
	}

	public void setRawtimes(Double rawtimes) {
		this.rawtimes = rawtimes;
	}

	public Double getRawmileage() {
		return rawmileage;
	}

	public void setRawmileage(Double rawmileage) {
		this.rawmileage = rawmileage;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Object starttime) {
        if(starttime instanceof Date){
            this.starttime = (Date) starttime;
        }else if(starttime instanceof String){
            try {
                if(StringUtils.isNotBlank((String) starttime)){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    this.starttime = format.parse((String)starttime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(starttime instanceof Long){
            this.starttime = new Date((long)starttime);
        }
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Object endtime) {
        if(endtime instanceof Date){
            this.endtime = (Date) endtime;
        }else if(endtime instanceof String){
            try {
                if(StringUtils.isNotBlank((String) endtime)){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    this.endtime = format.parse((String)endtime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(endtime instanceof Long){
            this.endtime = new Date((long)endtime);
        }
	}

	public Double getTimes() {
		return times;
	}

	public void setTimes(Double times) {
		this.times = times;
	}

	public Double getCounttimes() {
		return counttimes;
	}

	public void setCounttimes(Double counttimes) {
		this.counttimes = counttimes;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public Double getTimesubsidies() {
		return timesubsidies;
	}

	public void setTimesubsidies(Double timesubsidies) {
		this.timesubsidies = timesubsidies;
	}

	public Double getMileageprices() {
		return mileageprices;
	}

	public void setMileageprices(Double mileageprices) {
		this.mileageprices = mileageprices;
	}

	public String getReviewperson() {
		return reviewperson;
	}

	public void setReviewperson(String reviewperson) {
		this.reviewperson = reviewperson;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getReviewtime() {
		return reviewtime;
	}

	public void setReviewtime(Date reviewtime) {
		this.reviewtime = reviewtime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getReviewedprice() {
		return reviewedprice;
	}

	public void setReviewedprice(Double reviewedprice) {
		this.reviewedprice = reviewedprice;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Double getRaworderamount() {
		return raworderamount;
	}

	public void setRaworderamount(Double raworderamount) {
		this.raworderamount = raworderamount;
	}

    public String getPricecopy() {
        return pricecopy;
    }

    public void setPricecopy(String pricecopy) {
        this.pricecopy = pricecopy;
    }

    public String getRawpricecopy() {
        return rawpricecopy;
    }

    public void setRawpricecopy(String rawpricecopy) {
        this.rawpricecopy = rawpricecopy;
    }

    public Integer getReviewtype() {
        return reviewtype;
    }

    public void setReviewtype(Integer reviewtype) {
        this.reviewtype = reviewtype;
    }
}
