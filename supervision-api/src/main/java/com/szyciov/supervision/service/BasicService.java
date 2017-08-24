package com.szyciov.supervision.service;

import com.szyciov.supervision.api.dto.basic.*;

import java.util.Map;

/**
 * 基础静态数据
 * Created by lzw on 2017/8/21.
 */
public interface BasicService extends CommonApiService {

    /**
     * 网约车平台公司基本信息
     * @param map
     * @return
     */
    CompanyBaseInfo companyBaseInfo(Map<String,String> map);

    /**
     * 网约车平台联系人信息
     * @param map
     * @return
     */
    CompanyConcactInfo companyConcactInfo(Map<String,String> map);

    /**
     * 网约车平台投资人信息
     * @param map
     * @return
     */
    CompanyInvestorInfo companyInvestorInfo(Map<String,String> map);

    /**
     * 网约车平台公司运营规模信息
     * @param map
     * @return
     */
    CompanyOperateInfo companyOperateInfo(Map<String,String> map);

    /**
     * 网约车平台公司经营许可
     * @param map
     * @return
     */
    CompanyOperatingPermit companyOperatingPermit(Map<String,String> map);

    /**
     * 3网约车平台公司支付信息
     * @param map
     * @return
     */
    CompanyPayInfo companyPayInfo(Map<String,String> map);

    /**
     * 网约车平台公司服务机构
     * @param map
     * @return
     */
    CompanyServiceOrgan companyServiceOrgan(Map<String,String> map);

    /**
     * 网约车平台公司计程计价方式
     * @param map
     * @return
     */
    CompanyValuationWay companyValuationWay(Map<String,String> map);

    /**
     * 个体驾驶员合同信息
     * @param map
     * @return
     */
    DriverCntractInfo driverCntractInfo(Map<String,String> map);

    /**
     * 网约车驾驶员基本信息
     * @param map
     * @return
     */
    DriverInfo driverInfo(Map<String,String> map);

    /**
     * 网约车驾驶员移动终端信息
     * @param map
     * @return
     */
    DriverMobileTerminalInfo driverMobileTerminalInfo(Map<String,String> map);

    /**
     * 网约车驾驶员统计信息
     * @param map
     * @return
     */
    DriverStatistics driverStatistics(Map<String,String> map);

    /**
     * 网约车驾驶员培训信息
     * @param map
     * @return
     */
    DriverTrainingInfo driverTrainingInfo(Map<String,String> map);

    /**
     * 普通企业接入信息
     * @param map
     * @return
     */
    EnterpriseAccessInfo enterpriseAccessInfo(Map<String,String> map);

    /**
     * 网约车乘客基本信息
     * @param map
     * @return
     */
    PassengerInfo passengerInfo(Map<String,String> map);

    /**
     * 网约车乘客状态信息
     * @param map
     * @return
     */
    PassengerStatus passengerStatus(Map<String,String> map);

    /**
     * 网约车车辆保险信息
     * @param map
     * @return
     */
    VehicleInsuranceInfo vehicleInsuranceInfo(Map<String,String> map);

    /**
     * 网约车车辆基本信息
     * @param map
     * @return
     */
    Vehiclelnfo vehiclelnfo(Map<String,String> map);

    /**
     * 网约车车辆里程信息
     * @param map
     * @return
     */
    VehicleMileageLnfo vehicleMileageLnfo(Map<String,String> map);

}
