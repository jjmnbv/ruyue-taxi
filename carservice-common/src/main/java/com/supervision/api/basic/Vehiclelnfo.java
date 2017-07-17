package com.supervision.api.basic;

import com.supervision.enums.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 3.2.7	网约车车辆基本信息(CLJB)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class Vehiclelnfo extends BasicApi {
    public Vehiclelnfo(){
        super();
        setCommand(CommandEnum.Vehiclelnfo);
    }
//    车辆所在城市（注册地行政区划编号）
    private String address;
//    车牌号码
    private String vehicleNo;
//    车牌颜色
    private String plateColor;
//    核定载客位
    private String seats;
//车辆厂牌
    private String brand;
//    车辆型号
    private String model;
//    车辆类型
    private String vehicleType;
//    车辆所有人
    private String ownerName;
//    车身颜色
    private String vehicleColor;
//车辆发动机号
    private String engineId;
//    车辆识别VIN码
    private String vin;
//  车辆注册日期
    private String certifyDateA;
//    车辆燃料类型
    private String fuelType;
//    车辆发动机排量
    private String engineDisplace;
//    发动机功率
    private String enginePower;
//    车辆轴距
    private String wheelBase;
//车辆照片文件
    private String photo;
//    车辆照片文件编号
    private String photoId;
//   网络预约出租汽车运输证字号
    private String certificate;
//    网络预约出租汽车运输证发证机构
    private String transAgency;
//    经营区域
    private String transArea;
//    网络预约出租汽车运输证有效期起
    private String transDateStart;
//    网络预约出租汽车运输证有效期止
    private String transDateStop;
//网约车初次登记日期
    private String certifyDateB;
//    车辆检修状态
    private String fixState;
//  车辆下次年检时间
    private String nextFixDate;
//    车辆年度审验状态
    private String checkState;
//  车辆年度审验日期
    private String checkDate;

//网约车发票打印设备序列号
    private String feePrintId;
//    卫星定位装置品牌
    private String gpsBrand;
//    卫星定位装置型号
    private String gpsModel;
//    卫星定位装置IMEI号
    private String gpsImei;
//    卫星定位装置安装日期
    private String gpsInstallDate;
//    注册日期
    private String registerDate;
//  营运类型
    private String commercialType;
//    营运类型
    private String fareType;
//    车辆技术状况
    private String vehicleTec;
//    安全性能情况
    private String vehicleSafe;
//     租赁公司名称
    private String lesseeName;
//      租赁公司统一社会信用代码
    private String lesseeCode;





}
