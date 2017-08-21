package com.szyciov.supervision.serivice.impl;

import com.supervision.enums.CommandEnum;
import com.szyciov.supervision.api.dto.BaseApi;
import com.szyciov.supervision.api.dto.basic.*;
import com.szyciov.supervision.mapper.BasicMapper;
import com.szyciov.supervision.serivice.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lzw on 2017/8/21.
 */
@Service("basicService")
public class BasicServiceImpl implements BasicService {
    @Override
    public BaseApi execute(CommandEnum commandEnum, Map<String, String> map) {
        switch (commandEnum){
            case CompanyBaseInfo:
                return  this.companyBaseInfo(map);
            case CompanyConcactInfo:
                return  this.companyConcactInfo(map);
            case CompanyInvestorInfo:
                return  this.companyInvestorInfo(map);
            case CompanyOperateInfo:
                return this.companyOperateInfo(map);
            case CompanyOperatingPermit:
                return this.companyConcactInfo(map);
            case CompanyPayInfo:
                return  this.companyPayInfo(map);
            case CompanyServiceOrgan:
                return this.companyServiceOrgan(map);
            case CompanyValuationWay:
                return  this.companyValuationWay(map);
            case DriverCntractInfo:
                return  this.driverCntractInfo(map);
            case DriverInfo:
                return this.driverInfo(map);
            case DriverMobileTerminalInfo:
                return this.driverMobileTerminalInfo(map);
            case DriverStatistics:
                return  this.driverStatistics(map);
            case DriverTrainingInfo:
                return this.driverTrainingInfo(map);
            case EnterpriseAccessInfo:
                return this.enterpriseAccessInfo(map);
            case PassengerInfo:
                return this.passengerInfo(map);
            case PassengerStatus:
                return  this.passengerStatus(map);
            case VehicleInsuranceInfo:
                return  this.vehicleInsuranceInfo(map);
            case Vehiclelnfo:
                return  this.vehiclelnfo(map);
            case VehicleMileageLnfo:
                return  this.vehicleMileageLnfo(map);
        }
        return null;
    }

    @Autowired
    private BasicMapper basicMapper;

    @Override
    public CompanyBaseInfo companyBaseInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public CompanyConcactInfo companyConcactInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public CompanyInvestorInfo companyInvestorInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public CompanyOperateInfo companyOperateInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public CompanyOperatingPermit companyOperatingPermit(Map<String, String> map) {
        return null;
    }

    @Override
    public CompanyPayInfo companyPayInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public CompanyServiceOrgan companyServiceOrgan(Map<String, String> map) {
        return null;
    }

    @Override
    public CompanyValuationWay companyValuationWay(Map<String, String> map) {
        return null;
    }

    @Override
    public DriverCntractInfo driverCntractInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public DriverInfo driverInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public DriverMobileTerminalInfo driverMobileTerminalInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public DriverStatistics driverStatistics(Map<String, String> map) {
        return null;
    }

    @Override
    public DriverTrainingInfo driverTrainingInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public EnterpriseAccessInfo enterpriseAccessInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public PassengerInfo passengerInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public PassengerStatus passengerStatus(Map<String, String> map) {
        return null;
    }

    @Override
    public VehicleInsuranceInfo vehicleInsuranceInfo(Map<String, String> map) {
        return null;
    }

    @Override
    public Vehiclelnfo vehiclelnfo(Map<String, String> map) {
        return null;
    }

    @Override
    public VehicleMileageLnfo vehicleMileageLnfo(Map<String, String> map) {
        return null;
    }
}
