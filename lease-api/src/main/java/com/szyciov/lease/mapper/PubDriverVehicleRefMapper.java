package com.szyciov.lease.mapper;

import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.entity.City;
import com.szyciov.entity.PubDriverVehicleRef;
import com.szyciov.lease.dto.drivervehiclebind.car.CarBindInfoDto;
import com.szyciov.lease.dto.drivervehiclebind.car.UnBindVehicleInfoDto;
import com.szyciov.lease.dto.drivervehiclebind.taxi.TaxiBindInfoDto;
import com.szyciov.lease.param.drivervehiclebind.CarBindInfoQueryParam;
import com.szyciov.lease.param.drivervehiclebind.TaxiBindInfoQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PubDriverVehicleRefMapper {
	
    int deleteByPrimaryKey(@Param(value = "id") String id);

    int save(PubDriverVehicleRef pubDriverVehicleRef);

    PubDriverVehicleRef getById(@Param(value = "id") String id);

    List<PubDriverVehicleRef> listPubDriverVehicleRef(@Param(value = "vehicleid") String vehicleid,
                                               @Param(value = "driverid") String driverid);


    int updateByPrimaryKey(PubDriverVehicleRef pubDriverVehicleRef);


    int isExistsBind(@Param(value = "vehicleid") String vehicleid,
                     @Param(value = "driverid") String driverid);

    List<VehicleBindInfoDto> listVehicleBindInfo(@Param(value = "vehicleid") String vehicleid,
                                                 @Param(value = "driverid") String driverid);


    int getRefCount(@Param(value = "driverid")String driverId,@Param(value = "vehicleid")String vehicleid);

    /**
     * 网约车绑定信息查询
     * @return
     */
    List<CarBindInfoDto> listCarBindinfo(CarBindInfoQueryParam param);


    int countListCarBindinfo(CarBindInfoQueryParam param);


    List<UnBindVehicleInfoDto> listUnVehicleRefByQuery(CarBindInfoQueryParam param);

    int countListUnVehicleRefByQuery(CarBindInfoQueryParam param);

    /**
     * 网约车绑定信息的服务车型及城市
     * @return
     */
    List<CarBindInfoDto> listCarBindModelAndCity(CarBindInfoQueryParam param);

    /**
     * 返回出租车绑定列表
     * @param param
     * @return
     */
    List<TaxiBindInfoDto> listVehicleRef(TaxiBindInfoQueryParam param);

    /**
     * 返回出租车绑定列表 总数
     * @param param
     * @return
     */
    int countlistVehicleRef(TaxiBindInfoQueryParam param);

    /**
     * 返回所有车辆的城市，用于下拉框赋值
     * @param param
     * @return
     */
    List<City> listVehicleRefCity(TaxiBindInfoQueryParam param);


    /**
     * 返回所有已经处于上线状态的绑定司机信息
     * @return
     */
    List<VehicleBindInfoDto> listVehicleBindInfoOfOnline(String vehicleid);


    /**
     * 返回运管 网约车有效订单数
     * @param driverId
     * @return
     */
    int countValidOpCarOrder(String driverId);

    /**
     * 返回机构 网约车有效订单数
     * @param driverId
     * @return
     */
    int countValidOrgCarOrder(String driverId);

    /**
     * 返回出租车有效订单数
     * @param driverId
     * @return
     */
    int countValidTaxiOrder(String driverId);
    
    /**
     * 返回归属车企
     * @return
     */
    List<Map<String, Object>> getBelongLeasecompany();

}