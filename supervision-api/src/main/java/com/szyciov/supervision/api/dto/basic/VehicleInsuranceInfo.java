package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.8	网约车车辆保险信息(CLBX)
 * Created by 林志伟 on 2017/7/6.
 */

public class VehicleInsuranceInfo extends BasicApi {
    public VehicleInsuranceInfo() {
        super();
        setCommand(CommandEnum.VehicleInsuranceInfo);
    }
//    车辆号牌
    private String vehicleNo;
//    车牌颜色
    private String plateColor;
//    保险公司名称
    private String insurCom;
//    保险号
    private String insurNum;
//      保险类型
    private String insurType;
//    保险金额
    private String insurCount;
//    保险生效时间
    private String insurEff;
//    保险到期时间
    private String insurExp;

    @JsonIgnore
    private Integer state;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getInsurCom() {
        return insurCom;
    }

    public void setInsurCom(String insurCom) {
        this.insurCom = insurCom;
    }

    public String getInsurNum() {
        return insurNum;
    }

    public void setInsurNum(String insurNum) {
        this.insurNum = insurNum;
    }

    public String getInsurType() {
        return insurType;
    }

    public void setInsurType(String insurType) {
        this.insurType = insurType;
    }

    public String getInsurCount() {
        return insurCount;
    }

    public void setInsurCount(String insurCount) {
        this.insurCount = insurCount;
    }

    public String getInsurEff() {
        return insurEff;
    }

    public void setInsurEff(String insurEff) {
        this.insurEff = insurEff;
    }

    public String getInsurExp() {
        return insurExp;
    }

    public void setInsurExp(String insurExp) {
        this.insurExp = insurExp;
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public void setState(Integer state) {
        this.state = state;
    }
}
