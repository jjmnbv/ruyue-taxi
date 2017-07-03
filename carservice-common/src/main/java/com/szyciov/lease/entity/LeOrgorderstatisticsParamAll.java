package com.szyciov.lease.entity;

public class LeOrgorderstatisticsParamAll {
	public double sumOrdermoney;
	public int sumCarorders;
	public int sumPickporders;
	public int sumDropofforders;
	public int sumPickuporders;
	public int sumAlluporders;
	public int sumAllOrders;
	public int sumOrderreview;
	public double sumOrderreviewPrice;
	public int sumOragnAgency;
	public int sumPersonOrders;
	public double sumIncomePrice;
	public int sumTaxiOrders;
	//异常已处理，未处理订单
	public int sumConfirmedorders;
	public int sumProcessedorders;
	
	//差异金额
	public double sumDiffmoney;
	
	
	public int getSumTaxiOrders() {
		return sumTaxiOrders;
	}
	public void setSumTaxiOrders(int sumTaxiOrders) {
		this.sumTaxiOrders = sumTaxiOrders;
	}
	public double getSumDiffmoney() {
		return sumDiffmoney;
	}
	public void setSumDiffmoney(double sumDiffmoney) {
		this.sumDiffmoney = sumDiffmoney;
	}
	public int getSumProcessedorders() {
		return sumProcessedorders;
	}
	public void setSumProcessedorders(int sumProcessedorders) {
		this.sumProcessedorders = sumProcessedorders;
	}
	public int getSumOrderreview() {
		return sumOrderreview;
	}
	public void setSumOrderreview(int sumOrderreview) {
		this.sumOrderreview = sumOrderreview;
	}
	public double getSumOrderreviewPrice() {
		return sumOrderreviewPrice;
	}
	public void setSumOrderreviewPrice(double sumOrderreviewPrice) {
		this.sumOrderreviewPrice = sumOrderreviewPrice;
	}
	public int getSumOragnAgency() {
		return sumOragnAgency;
	}
	public void setSumOragnAgency(int sumOragnAgency) {
		this.sumOragnAgency = sumOragnAgency;
	}
	public int getSumPersonOrders() {
		return sumPersonOrders;
	}
	public void setSumPersonOrders(int sumPersonOrders) {
		this.sumPersonOrders = sumPersonOrders;
	}
	public double getSumIncomePrice() {
		return sumIncomePrice;
	}
	public void setSumIncomePrice(double sumIncomePrice) {
		this.sumIncomePrice = sumIncomePrice;
	}
	public int getSumAllOrders() {
		return sumAllOrders;
	}
	public void setSumAllOrders(int sumAllOrders) {
		this.sumAllOrders = sumAllOrders;
	}
	public double getSumOrdermoney() {
		return sumOrdermoney;
	}
	public void setSumOrdermoney(double sumOrdermoney) {
		this.sumOrdermoney = sumOrdermoney;
	}
	public int getSumCarorders() {
		return sumCarorders;
	}
	public void setSumCarorders(int sumCarorders) {
		this.sumCarorders = sumCarorders;
	}
	public int getSumPickporders() {
		return sumPickporders;
	}
	public void setSumPickporders(int sumPickporders) {
		this.sumPickporders = sumPickporders;
	}
	public int getSumDropofforders() {
		return sumDropofforders;
	}
	public void setSumDropofforders(int sumDropofforders) {
		this.sumDropofforders = sumDropofforders;
	}
	public int getSumConfirmedorders() {
		return sumConfirmedorders;
	}
	public void setSumConfirmedorders(int sumConfirmedorders) {
		this.sumConfirmedorders = sumConfirmedorders;
	}
	public int getSumPickuporders() {
		return sumPickuporders;
	}
	public void setSumPickuporders(int sumPickuporders) {
		this.sumPickuporders = sumPickuporders;
	}
	public int getSumAlluporders() {
		return sumAlluporders;
	}
	public void setSumAlluporders(int sumAlluporders) {
		this.sumAlluporders = sumAlluporders;
	}
	@Override
	public String toString() {
		return "LeOrgorderstatisticsParamAll [sumOrdermoney=" + sumOrdermoney + ", sumCarorders=" + sumCarorders
				+ ", sumPickporders=" + sumPickporders + ", sumDropofforders=" + sumDropofforders
				+ ", sumConfirmedorders=" + sumConfirmedorders + ", sumPickuporders=" + sumPickuporders
				+ ", sumAlluporders=" + sumAlluporders + ", sumAllOrders=" + sumAllOrders + ", sumOrderreview="
				+ sumOrderreview + ", sumOrderreviewPrice=" + sumOrderreviewPrice + ", sumOragnAgency=" + sumOragnAgency
				+ ", sumPersonOrders=" + sumPersonOrders + ", sumIncomePrice=" + sumIncomePrice + "]";
	}
	
	
}
