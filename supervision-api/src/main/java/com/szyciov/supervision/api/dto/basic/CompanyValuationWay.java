package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;

/**
 * 3.2.6	网约车平台公司计程计价方式(PTYJ)
 * Created by 林志伟 on 2017/7/6.
 */

public class CompanyValuationWay extends BasicApi {
    public CompanyValuationWay(){
        super();
        setCommand(CommandEnum.CompanyValuationWay);
    }
//    行政区划代码
    private String address;
//    运价类型编码
    private String fareType;
//      运价类型说明
    private String fareTypeNote;
//      运价有效期起
    private String fareValidOn;
//    运价有效期止
    private String fareValidOff;
//    起步价
    private String startFare;
//    起步里程
    private String startMile;
//    单价
    private String unitPrice;
//    单程加价单价
    private String upPrice;
//  单程加价公里
    private String upPriceStartMile;
    //营运早高峰时间起
    private String morningPeakTimeOn;
//    营运早高峰时间止
    private String morningPeakTimeOff;
//    营运晚高峰时间起
    private String eveningPeakTimeOn;
//营运晚高峰时间止
    private String eveningPeakTimeOff;
//    其他营运高峰时间起
    private String otherPeakTimeOn;
//其他营运高峰时间止
    private String otherPeakTimeOff;
//    高峰时间单程加价单价
    private String peakUnitPrice;

//    高峰时间单程加价公里
    private String peakPriceStartMile;
//  计程单价（按公里）
    private String unitPricePerMile;
//    计时单价（按分钟）
    private String unitPricePerMinute;
//远途费（按公里）
    private String longDistancePrice;
//远途费起算公里
    private String longDistanceStartMile;
//    低速计时加价（按分钟）
    private String lowSpeedPricePerMinute;
//    夜间费（按公里）1
    private String nightPricePerMile;
//夜间费（按分钟）
    private String nightPricePerMinute;
//   其它加价金额
    private String otherPrice;
//    服务车型编码
    private Integer taxiTypeCode;
//    服务类型
    private String serviceTypeCode;
//    计费规则地址
    private String fareRuleUrl;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getFareTypeNote() {
        return fareTypeNote;
    }

    public void setFareTypeNote(String fareTypeNote) {
        this.fareTypeNote = fareTypeNote;
    }

    public String getFareValidOn() {
        return fareValidOn;
    }

    public void setFareValidOn(String fareValidOn) {
        this.fareValidOn = fareValidOn;
    }

    public String getFareValidOff() {
        return fareValidOff;
    }

    public void setFareValidOff(String fareValidOff) {
        this.fareValidOff = fareValidOff;
    }

    public String getStartFare() {
        return startFare;
    }

    public void setStartFare(String startFare) {
        this.startFare = startFare;
    }

    public String getStartMile() {
        return startMile;
    }

    public void setStartMile(String startMile) {
        this.startMile = startMile;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUpPrice() {
        return upPrice;
    }

    public void setUpPrice(String upPrice) {
        this.upPrice = upPrice;
    }

    public String getUpPriceStartMile() {
        return upPriceStartMile;
    }

    public void setUpPriceStartMile(String upPriceStartMile) {
        this.upPriceStartMile = upPriceStartMile;
    }

    public String getMorningPeakTimeOn() {
        return morningPeakTimeOn;
    }

    public void setMorningPeakTimeOn(String morningPeakTimeOn) {
        this.morningPeakTimeOn = morningPeakTimeOn;
    }

    public String getMorningPeakTimeOff() {
        return morningPeakTimeOff;
    }

    public void setMorningPeakTimeOff(String morningPeakTimeOff) {
        this.morningPeakTimeOff = morningPeakTimeOff;
    }

    public String getEveningPeakTimeOn() {
        return eveningPeakTimeOn;
    }

    public void setEveningPeakTimeOn(String eveningPeakTimeOn) {
        this.eveningPeakTimeOn = eveningPeakTimeOn;
    }

    public String getEveningPeakTimeOff() {
        return eveningPeakTimeOff;
    }

    public void setEveningPeakTimeOff(String eveningPeakTimeOff) {
        this.eveningPeakTimeOff = eveningPeakTimeOff;
    }

    public String getOtherPeakTimeOn() {
        return otherPeakTimeOn;
    }

    public void setOtherPeakTimeOn(String otherPeakTimeOn) {
        this.otherPeakTimeOn = otherPeakTimeOn;
    }

    public String getOtherPeakTimeOff() {
        return otherPeakTimeOff;
    }

    public void setOtherPeakTimeOff(String otherPeakTimeOff) {
        this.otherPeakTimeOff = otherPeakTimeOff;
    }

    public String getPeakUnitPrice() {
        return peakUnitPrice;
    }

    public void setPeakUnitPrice(String peakUnitPrice) {
        this.peakUnitPrice = peakUnitPrice;
    }

    public String getPeakPriceStartMile() {
        return peakPriceStartMile;
    }

    public void setPeakPriceStartMile(String peakPriceStartMile) {
        this.peakPriceStartMile = peakPriceStartMile;
    }

    public String getUnitPricePerMile() {
        return unitPricePerMile;
    }

    public void setUnitPricePerMile(String unitPricePerMile) {
        this.unitPricePerMile = unitPricePerMile;
    }

    public String getUnitPricePerMinute() {
        return unitPricePerMinute;
    }

    public void setUnitPricePerMinute(String unitPricePerMinute) {
        this.unitPricePerMinute = unitPricePerMinute;
    }

    public String getLongDistancePrice() {
        return longDistancePrice;
    }

    public void setLongDistancePrice(String longDistancePrice) {
        this.longDistancePrice = longDistancePrice;
    }

    public String getLongDistanceStartMile() {
        return longDistanceStartMile;
    }

    public void setLongDistanceStartMile(String longDistanceStartMile) {
        this.longDistanceStartMile = longDistanceStartMile;
    }

    public String getLowSpeedPricePerMinute() {
        return lowSpeedPricePerMinute;
    }

    public void setLowSpeedPricePerMinute(String lowSpeedPricePerMinute) {
        this.lowSpeedPricePerMinute = lowSpeedPricePerMinute;
    }

    public String getNightPricePerMile() {
        return nightPricePerMile;
    }

    public void setNightPricePerMile(String nightPricePerMile) {
        this.nightPricePerMile = nightPricePerMile;
    }

    public String getNightPricePerMinute() {
        return nightPricePerMinute;
    }

    public void setNightPricePerMinute(String nightPricePerMinute) {
        this.nightPricePerMinute = nightPricePerMinute;
    }

    public String getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(String otherPrice) {
        this.otherPrice = otherPrice;
    }

    public Integer getTaxiTypeCode() {
        return taxiTypeCode;
    }

    public void setTaxiTypeCode(Integer taxiTypeCode) {
        this.taxiTypeCode = taxiTypeCode;
    }

    public String getServiceTypeCode() {
        return serviceTypeCode;
    }

    public void setServiceTypeCode(String serviceTypeCode) {
        this.serviceTypeCode = serviceTypeCode;
    }

    public String getFareRuleUrl() {
        return fareRuleUrl;
    }

    public void setFareRuleUrl(String fareRuleUrl) {
        this.fareRuleUrl = fareRuleUrl;
    }

    @Override
    public String toString() {
        return "CompanyValuationWay{" +
                "address='" + address + '\'' +
                ", fareType='" + fareType + '\'' +
                ", fareTypeNote='" + fareTypeNote + '\'' +
                ", fareValidOn='" + fareValidOn + '\'' +
                ", fareValidOff='" + fareValidOff + '\'' +
                ", startFare='" + startFare + '\'' +
                ", startMile='" + startMile + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", upPrice='" + upPrice + '\'' +
                ", upPriceStartMile='" + upPriceStartMile + '\'' +
                ", morningPeakTimeOn='" + morningPeakTimeOn + '\'' +
                ", morningPeakTimeOff='" + morningPeakTimeOff + '\'' +
                ", eveningPeakTimeOn='" + eveningPeakTimeOn + '\'' +
                ", eveningPeakTimeOff='" + eveningPeakTimeOff + '\'' +
                ", otherPeakTimeOn='" + otherPeakTimeOn + '\'' +
                ", otherPeakTimeOff='" + otherPeakTimeOff + '\'' +
                ", peakUnitPrice='" + peakUnitPrice + '\'' +
                ", peakPriceStartMile='" + peakPriceStartMile + '\'' +
                ", unitPricePerMile='" + unitPricePerMile + '\'' +
                ", unitPricePerMinute='" + unitPricePerMinute + '\'' +
                ", longDistancePrice='" + longDistancePrice + '\'' +
                ", longDistanceStartMile='" + longDistanceStartMile + '\'' +
                ", lowSpeedPricePerMinute='" + lowSpeedPricePerMinute + '\'' +
                ", nightPricePerMile='" + nightPricePerMile + '\'' +
                ", nightPricePerMinute='" + nightPricePerMinute + '\'' +
                ", otherPrice='" + otherPrice + '\'' +
                ", taxiTypeCode=" + taxiTypeCode +
                ", serviceTypeCode='" + serviceTypeCode + '\'' +
                ", fareRuleUrl='" + fareRuleUrl + '\'' +
                "} " + super.toString();
    }
}
