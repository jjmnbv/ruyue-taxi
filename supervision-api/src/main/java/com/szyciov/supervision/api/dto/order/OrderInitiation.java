package com.szyciov.supervision.api.dto.order;

import com.supervision.enums.CommandEnum;
import com.szyciov.supervision.config.CacheHelper;

/**
 * 3.3.1	订单发起(DDFQ) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class OrderInitiation extends OrderApi {
    public OrderInitiation(){
        super();
        setCommand(CommandEnum.OrderInitiation);
    }
    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;


//  预计用车时间
    private String departTime;

//    乘客电话
    private String passengerPhone;
//    乘客备注
    private String passengerNote;
//    预计出发地点详细地址
    private String departure;
//    预计出发地点经度
    private String depLongitude;
//    预计出发地点纬度
    private String depLatitude;
//    预计目的地点详细地址
    private String destination;
//  预计目的地点经度
    private String destLongitude;
//    预计目的地点纬度
    private String destLatitude;
//  坐标加密标识
    private String encrypt = CacheHelper.MAPTYPE;

//    运价类型编码
    private String fareType;
//    乘客称谓
    private String orderSource;
//    乘客称谓
    private String psgName;
//    乘客性别
    private String psgGender;
//    乘车人数
    private String psgTotal;
//    vehType
    private String vehType;
//  是否预约订单
    private Integer isReserve;
//    是否语音订单
    private Integer isVoice;
//    语音存储地址
    private String voiceUrl;
//    预计里程
    private String preMile;
//    预计用时
    private String preTime;
//    预计费用
    private String preFare;
//    用车时间
    private String useTime;
//    用车地点
    private String useLocale;
//    用车经度
    private String useLon;
//    用车经度
    private String useLat;
//    服务车型编码
    private String taxiTypeCode;
//    服务类型
    private String serviceTypeCode;
//      出发城市
    private String departCity;
//    目的城市
    private String destCity;

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

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPassengerNote() {
        return passengerNote;
    }

    public void setPassengerNote(String passengerNote) {
        this.passengerNote = passengerNote;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDepLongitude() {
        return depLongitude;
    }

    public void setDepLongitude(String depLongitude) {
        this.depLongitude = depLongitude;
    }

    public String getDepLatitude() {
        return depLatitude;
    }

    public void setDepLatitude(String depLatitude) {
        this.depLatitude = depLatitude;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestLongitude() {
        return destLongitude;
    }

    public void setDestLongitude(String destLongitude) {
        this.destLongitude = destLongitude;
    }

    public String getDestLatitude() {
        return destLatitude;
    }

    public void setDestLatitude(String destLatitude) {
        this.destLatitude = destLatitude;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
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

    public String getPsgTotal() {
        return psgTotal;
    }

    public void setPsgTotal(String psgTotal) {
        this.psgTotal = psgTotal;
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

    public String getPreMile() {
        return preMile;
    }

    public void setPreMile(String preMile) {
        this.preMile = preMile;
    }

    public String getPreTime() {
        return preTime;
    }

    public void setPreTime(String preTime) {
        this.preTime = preTime;
    }

    public String getPreFare() {
        return preFare;
    }

    public void setPreFare(String preFare) {
        this.preFare = preFare;
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

    public String getTaxiTypeCode() {
        return taxiTypeCode;
    }

    public void setTaxiTypeCode(String taxiTypeCode) {
        this.taxiTypeCode = taxiTypeCode;
    }

    public String getServiceTypeCode() {
        return serviceTypeCode;
    }

    public void setServiceTypeCode(String serviceTypeCode) {
        this.serviceTypeCode = serviceTypeCode;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    @Override
    public String toString() {
        return "OrderInitiation{" +
                "orderId='" + orderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", departTime='" + departTime + '\'' +
                ", passengerPhone='" + passengerPhone + '\'' +
                ", passengerNote='" + passengerNote + '\'' +
                ", departure='" + departure + '\'' +
                ", depLongitude='" + depLongitude + '\'' +
                ", depLatitude='" + depLatitude + '\'' +
                ", destination='" + destination + '\'' +
                ", destLongitude='" + destLongitude + '\'' +
                ", destLatitude='" + destLatitude + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", fareType='" + fareType + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", psgName='" + psgName + '\'' +
                ", psgGender='" + psgGender + '\'' +
                ", psgTotal='" + psgTotal + '\'' +
                ", vehType='" + vehType + '\'' +
                ", isReserve=" + isReserve +
                ", isVoice=" + isVoice +
                ", voiceUrl='" + voiceUrl + '\'' +
                ", preMile='" + preMile + '\'' +
                ", preTime='" + preTime + '\'' +
                ", preFare='" + preFare + '\'' +
                ", useTime='" + useTime + '\'' +
                ", useLocale='" + useLocale + '\'' +
                ", useLon='" + useLon + '\'' +
                ", useLat='" + useLat + '\'' +
                ", taxiTypeCode='" + taxiTypeCode + '\'' +
                ", serviceTypeCode='" + serviceTypeCode + '\'' +
                ", departCity='" + departCity + '\'' +
                ", destCity='" + destCity + '\'' +
                "} " + super.toString();
    }
}
