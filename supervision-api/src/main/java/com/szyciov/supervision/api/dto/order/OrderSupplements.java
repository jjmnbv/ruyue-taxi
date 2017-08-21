package com.szyciov.supervision.api.dto.order;

import com.supervision.enums.CommandEnum;

/**
 * 3.3.5	订单补传*(DDBC) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class OrderSupplements extends OrderApi {
    public OrderSupplements() {
        super();
        setCommand(CommandEnum.OrderSupplements);
    }

    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;

    /**
     *
     * 订单状态	DDLR：订单录入（乘客下单）
     DDQX:订单取消（司机应召前乘客取消订单）
     CKQX：司机应召后乘客取消订单
     SJQX：司机应召后司机取消订单
     DDWC：订单完成
     DDSB：订单失败（无司机应召）

     */
    private String orderStatus;

//    用车类型
    private String vehType;
    /**
     * 是否预约订单	0:否，1:是
     */
    private Integer isReserve;
    /**
     * 是否语音订单	0:否，1:是
     */
    private Integer isVoice;
    /**
     * 语音存储地址	是否语音订单为“是”时该字段必填
     */
    private String voiceUrl;
    /**
     * 用车时间	YYYYMMDDHHMMSS
     */
    private String useTime;
    /**
     *用车地点
     *
     */
    private String useLocale;
    /**
     * 坐标加密标识	1：GCJ-02 测绘局标准
     2：WGS84 GPS标准
     3：BD-09 百度标准
     4：CGCS2000 北斗标准
     0：其他

     */
    private String encrypt;
    /**
     * 用车经度	单位：1*10-6度
     */
    private String useLon;
    /**
     *  用车纬度	单位：1*10-6度
     */
    private String useLat;
    /**
     * 预计目的地点详细地址
     */
    private String destLocale;
    /**
     * 预计目的地点经度	单位：1*10-6度
     */
    private String destLon;
    /**
     * 预计目的地点纬度	单位：1*10-6度
     */
    private String destLat;
    /**
     * 驾驶员姓名
     */
    private String driName;
    /**
     * 驾驶员联系电话
     */
    private String driTel;

    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String qulificationNo;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 网络预约出租汽车运输证号
     */
    private String carCertNo;
    /**
     * 实际上车时间	YYYYMMDDHHMMSS
     */
    private String onTime;
    /**
     * 实际下车时间	YYYYMMDDHHMMSS
     */
    private String offTime;
    /**
     * 载客里程	单位:km
     */
    private String psgMile;
    /**
     * 载客时间	单位：秒
     */
    private String duration;
    /**
     * 应收金额	单位：元
     */
    private String receivable;
    /**
     * 实收金额	单位：元
     */
    private String paid;
    /**
     * 优惠金额	单位：元
     */
    private String discount;
    /**
     * 现金支付金额	 单位：元
     */
    private String cash;
    /**
     * 电子支付金额	 单位：元
     */
    private String elePay;
    /**
     * 电子支付机构
     */
    private String eleOrg;
    /**
     * Pos机支付金额	单位：元
     */
    private String posPay;
    /**
     * Pos机收单机构
     */
    private String posOrg;
    /**
     * 高峰时段加价金额	 单位：元
     */
    private String callPay;
    /**
     * 附加费	 单位：元
     */
    private String extraPay;
    /**
     * 高峰时段加价金额	 单位：元
     */
    private String peakPay;
    /**
     * 夜间时段加价金额	 单位：元
     */
    private String nightPay;
    /**
     * 发票状态
     */
    private String billStatus;
    /**
     * 乘客称谓
     */
    private String psgName;
    /**
     * 乘客性别
     */
    private String psgGender;
    /**
     * 乘客电话
     */
    private String psgTel;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getVehType() {
        return vehType;
    }

    public void setVehType(String vehType) {
        this.vehType = vehType;
    }

    public Integer getIsReserve() {
        return isReserve;
    }

    public void setIsReserve(Integer isReserve) {
        this.isReserve = isReserve;
    }

    public Integer getIsVoice() {
        return isVoice;
    }

    public void setIsVoice(Integer isVoice) {
        this.isVoice = isVoice;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getUseLocale() {
        return useLocale;
    }

    public void setUseLocale(String useLocale) {
        this.useLocale = useLocale;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getUseLon() {
        return useLon;
    }

    public void setUseLon(String useLon) {
        this.useLon = useLon;
    }

    public String getUseLat() {
        return useLat;
    }

    public void setUseLat(String useLat) {
        this.useLat = useLat;
    }

    public String getDestLocale() {
        return destLocale;
    }

    public void setDestLocale(String destLocale) {
        this.destLocale = destLocale;
    }

    public String getDestLon() {
        return destLon;
    }

    public void setDestLon(String destLon) {
        this.destLon = destLon;
    }

    public String getDestLat() {
        return destLat;
    }

    public void setDestLat(String destLat) {
        this.destLat = destLat;
    }

    public String getDriName() {
        return driName;
    }

    public void setDriName(String driName) {
        this.driName = driName;
    }

    public String getDriTel() {
        return driTel;
    }

    public void setDriTel(String driTel) {
        this.driTel = driTel;
    }

    public String getQulificationNo() {
        return qulificationNo;
    }

    public void setQulificationNo(String qulificationNo) {
        this.qulificationNo = qulificationNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getCarCertNo() {
        return carCertNo;
    }

    public void setCarCertNo(String carCertNo) {
        this.carCertNo = carCertNo;
    }

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }

    public String getPsgMile() {
        return psgMile;
    }

    public void setPsgMile(String psgMile) {
        this.psgMile = psgMile;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReceivable() {
        return receivable;
    }

    public void setReceivable(String receivable) {
        this.receivable = receivable;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getElePay() {
        return elePay;
    }

    public void setElePay(String elePay) {
        this.elePay = elePay;
    }

    public String getEleOrg() {
        return eleOrg;
    }

    public void setEleOrg(String eleOrg) {
        this.eleOrg = eleOrg;
    }

    public String getPosPay() {
        return posPay;
    }

    public void setPosPay(String posPay) {
        this.posPay = posPay;
    }

    public String getPosOrg() {
        return posOrg;
    }

    public void setPosOrg(String posOrg) {
        this.posOrg = posOrg;
    }

    public String getCallPay() {
        return callPay;
    }

    public void setCallPay(String callPay) {
        this.callPay = callPay;
    }

    public String getExtraPay() {
        return extraPay;
    }

    public void setExtraPay(String extraPay) {
        this.extraPay = extraPay;
    }

    public String getPeakPay() {
        return peakPay;
    }

    public void setPeakPay(String peakPay) {
        this.peakPay = peakPay;
    }

    public String getNightPay() {
        return nightPay;
    }

    public void setNightPay(String nightPay) {
        this.nightPay = nightPay;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getPsgName() {
        return psgName;
    }

    public void setPsgName(String psgName) {
        this.psgName = psgName;
    }

    public String getPsgGender() {
        return psgGender;
    }

    public void setPsgGender(String psgGender) {
        this.psgGender = psgGender;
    }

    public String getPsgTel() {
        return psgTel;
    }

    public void setPsgTel(String psgTel) {
        this.psgTel = psgTel;
    }

    @Override
    public String toString() {
        return "OrderSupplements{" +
                "orderId='" + orderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", vehType='" + vehType + '\'' +
                ", isReserve=" + isReserve +
                ", isVoice=" + isVoice +
                ", voiceUrl='" + voiceUrl + '\'' +
                ", useTime='" + useTime + '\'' +
                ", useLocale='" + useLocale + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", useLon='" + useLon + '\'' +
                ", useLat='" + useLat + '\'' +
                ", destLocale='" + destLocale + '\'' +
                ", destLon='" + destLon + '\'' +
                ", destLat='" + destLat + '\'' +
                ", driName='" + driName + '\'' +
                ", driTel='" + driTel + '\'' +
                ", qulificationNo='" + qulificationNo + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", carCertNo='" + carCertNo + '\'' +
                ", onTime='" + onTime + '\'' +
                ", offTime='" + offTime + '\'' +
                ", psgMile='" + psgMile + '\'' +
                ", duration='" + duration + '\'' +
                ", receivable='" + receivable + '\'' +
                ", paid='" + paid + '\'' +
                ", discount='" + discount + '\'' +
                ", cash='" + cash + '\'' +
                ", elePay='" + elePay + '\'' +
                ", eleOrg='" + eleOrg + '\'' +
                ", posPay='" + posPay + '\'' +
                ", posOrg='" + posOrg + '\'' +
                ", callPay='" + callPay + '\'' +
                ", extraPay='" + extraPay + '\'' +
                ", peakPay='" + peakPay + '\'' +
                ", nightPay='" + nightPay + '\'' +
                ", billStatus='" + billStatus + '\'' +
                ", psgName='" + psgName + '\'' +
                ", psgGender='" + psgGender + '\'' +
                ", psgTel='" + psgTel + '\'' +
                "} " + super.toString();
    }
}
