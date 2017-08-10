package com.szyciov.supervision.enums;

/**
 * Created by admin on 2017/7/6.
 */
public enum CommandEnum {
    ACCESS("ACCESS","ACCESS TOKEN"),
//    baisc
    CompanyBaseInfo("PTJB","网约车平台公司基本信息"),
    CompanyConcactInfo("PTLXR","网约车平台联系人信息"),
    CompanyInvestorInfo("PTTZR","网约车平台投资人信息"),
    CompanyOperateInfo("PTYYGM","网约车平台公司运营规模信息"),
    CompanyOperatingPermit("CPTJYXK","网约车平台公司经营许可"),
    CompanyPayInfo("PTZF","3网约车平台公司支付信息"),
    CompanyServiceOrgan("PTFWJG","网约车平台公司服务机构"),
    CompanyValuationWay("PTYJ","网约车平台公司计程计价方式"),
    DriverCntractInfo("GTJSYHT","个体驾驶员合同信息"),
    DriverInfo("JSYJB","网约车驾驶员基本信息"),
    DriverMobileTerminalInfo("JSYYDZD","网约车驾驶员移动终端信息"),
    DriverStatistics("JSYTJ","网约车驾驶员统计信息"),
    DriverTrainingInfo("JSYPX","网约车驾驶员培训信息"),
    EnterpriseAccessInfo("PTQYJR","普通企业接入信息"),
    PassengerInfo("CKJB","网约车乘客基本信息"),
    PassengerStatus("CKZT","网约车乘客状态信息"),
    VehicleInsuranceInfo("CLBX","网约车车辆保险信息"),
    Vehiclelnfo("CLJB","网约车车辆基本信息"),
    VehicleMileageLnfo("CLLC","网约车车辆里程信息"),
    //    evalute
    DriverCreditInfo("JSYXY","驾驶员信誉信息"),
    DriverPenaltyInfo("JSYCF","驾驶员处罚信息"),
    PassengerComplaintInfo("CKTSCL","乘客投诉处理信息"),
    PassengerEvaluationInformation("CKPJ","乘客评价信息"),
    // gps
    DriverLocationInfo("JSYDW","驾驶员定位信息"),
    PassengerLocationInfo("CKDW","乘客定位信息"),
    //    operation
    OperationArrival("YYDD","营运到达"),
    OperationDeparture("YYCF","营运出发"),
    OperationPay("YYZF","营运支付(YYZF) 实时"),
    VehicleOperationOffline("CLYYXX","车辆营运下线"),
    VehicleOperationOnline("CLYYSX","车辆营运上线"),
    //  ORDER
    DriverOffWork("JSYXB","驾驶员下班"),
    DriverOnWork("JSYSB","驾驶员上班"),
    OrderBreach("DDWY","订单违约"),
    OrderCancel("DDCX","订单撤销"),
    OrderInitiation("DDFQ","订单发起"),
    OrderSuccess("DDCG","订单成功"),
    OrderSupplements("DDBC","订单补传"),
    OrderSupplementsRequest("DDBCQQ","订单补传请求"),
//
    HumanVehicleInfo("RCDYGX","人车对应关系信息数据"),


    ;

    private String value;
    private String description;

    private CommandEnum(String v, String desp){
        this.value = v;
        this.description = desp;
    }

    public String value(){
        return this.value;
    }

    public String description(){
        return this.description;
    }
}
