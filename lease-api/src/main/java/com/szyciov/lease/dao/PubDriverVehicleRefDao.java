package com.szyciov.lease.dao;

import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.entity.City;
import com.szyciov.entity.PubDriverVehicleRef;
import com.szyciov.lease.dto.drivervehiclebind.car.CarBindInfoDto;
import com.szyciov.lease.dto.drivervehiclebind.car.UnBindVehicleInfoDto;
import com.szyciov.lease.dto.drivervehiclebind.taxi.TaxiBindInfoDto;
import com.szyciov.lease.mapper.PubDriverVehicleRefMapper;
import com.szyciov.lease.param.drivervehiclebind.CarBindInfoQueryParam;
import com.szyciov.lease.param.drivervehiclebind.TaxiBindInfoQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 人车绑定信息dao
 */
@Service
public class PubDriverVehicleRefDao {

    @Autowired
    private PubDriverVehicleRefMapper refMapper;

    public int save(PubDriverVehicleRef pubDriverVehicleRef){
        return refMapper.save(pubDriverVehicleRef);
    }

    public int deleteByPrimaryKey(String id){
        return refMapper.deleteByPrimaryKey(id);
    }

    public  PubDriverVehicleRef getById(String id){
        return refMapper.getById(id);
    }

    public List<PubDriverVehicleRef> listPubDriverVehicleRef(String vehicleid ,String driverid){
        return refMapper.listPubDriverVehicleRef(vehicleid,driverid);
    }


    public int isExistsBind(String vehicleid,String driverId){
        return refMapper.isExistsBind(vehicleid,driverId);
    }
    public List<VehicleBindInfoDto> listVehicleBindInfo(String vehicleid,String driverId){
        return refMapper.listVehicleBindInfo(vehicleid,driverId);
    }

    public int getRefCount(String driverId,String vehicleid){
        return refMapper.getRefCount(driverId,vehicleid);
    }

    public List<CarBindInfoDto> listCarBindinfo(CarBindInfoQueryParam param){
        return refMapper.listCarBindinfo(param);
    }

    public int countListCarBindinfo(CarBindInfoQueryParam param){
        return refMapper.countListCarBindinfo(param);
    }

    public List<UnBindVehicleInfoDto> listUnVehicleRefByQuery(CarBindInfoQueryParam param){
        return refMapper.listUnVehicleRefByQuery(param);
    };

    public int countListUnVehicleRefByQuery(CarBindInfoQueryParam param){
        return refMapper.countListUnVehicleRefByQuery(param);
    }

    public  List<CarBindInfoDto> listCarBindModelAndCity(CarBindInfoQueryParam param){
        return refMapper.listCarBindModelAndCity(param);
    }


    /**
     * 返回出租车绑定列表
     * @param param
     * @return
     */
    public List<TaxiBindInfoDto> listVehicleRef(TaxiBindInfoQueryParam param){
        return this.refMapper.listVehicleRef(param);
    }

    /**
     * 返回出租车绑定列表 总数
     * @param param
     * @return
     */
    public int countlistVehicleRef(TaxiBindInfoQueryParam param){
       return this.refMapper.countlistVehicleRef(param);
    }

    /**
     * 返回所有车辆的城市，用于下拉框赋值
     * @param param
     * @return
     */
    public List<City> listVehicleRefCity(TaxiBindInfoQueryParam param){
        return this.refMapper.listVehicleRefCity(param);
    }


    /**
     * 返回所有已经处于上线状态的绑定司机信息
     * @return
     */
    public List<VehicleBindInfoDto> listVehicleBindInfoOfOnline(String vehicleid){
        return this.refMapper.listVehicleBindInfoOfOnline(vehicleid);
    }


    /**
     * 返回运管 网约车有效订单数
     * @param driverId
     * @return
     */
    public  int countValidOpCarOrder(String driverId){
        return this.refMapper.countValidOpCarOrder(driverId);
    }

    /**
     * 返回机构 网约车有效订单数
     * @param driverId
     * @return
     */
    public int countValidOrgCarOrder(String driverId){
        return this.refMapper.countValidOrgCarOrder(driverId);
    }

    /**
     * 返回出租车有效订单数
     * @param driverId
     * @return
     */
    public int countValidTaxiOrder(String driverId){
        return this.refMapper.countValidTaxiOrder(driverId);
    }
    
    /**
     * 返回归属车企
     * @return
     */
    public List<Map<String, Object>> getBelongLeasecompany() {
    	return this.refMapper.getBelongLeasecompany();
    }

}