package com.szyciov.lease.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.PubCooagreement;
import com.szyciov.entity.PubCoooperate;
import com.szyciov.entity.PubCooresource;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.dao.PubCoooperateUnionDao;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.param.pubCoooperateUnion.*;
import com.szyciov.lease.utils.CoooperateNumUtils;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryApplyResourceVo;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryCooagreementViewVo;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryPubCoooperateVo;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryResourceVo;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.util.GUIDGenerator;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by ZF on 2017/8/1.
 */
@Service
public class PubCoooperateUnionService {
    @Autowired
    private PubCoooperateUnionDao pubCoooperateUnionDao;


    /**
     * 查询合作运营信息
     *
     * @param param 查询条件
     * @return 合作运营信息
     */
    public PagingResponse queryPubCoooperateData(QueryPubCoooperateParam param) {
        int pageNum = 1;

        if (param.getiDisplayLength() != 0) {
            pageNum = param.getiDisplayStart() / param.getiDisplayLength() + 1;
        }

        if (param.getApplicationtimeStart() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(param.getApplicationtimeStart());
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);
            param.setApplicationtimeStart(cal.getTime());
        }

        if (param.getApplicationtimeEnd() != null) {
            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(param.getApplicationtimeEnd());
            calEnd.set(Calendar.HOUR_OF_DAY, 0);
            calEnd.set(Calendar.MINUTE, 0);
            calEnd.set(Calendar.MILLISECOND, 0);
            calEnd.add(Calendar.DATE, 1);
            param.setApplicationtimeEnd(calEnd.getTime());
        }

        PageHelper.startPage(pageNum, param.getiDisplayLength());
        Page page = (Page) this.pubCoooperateUnionDao.queryPubCoooperateData(param);

        PagingResponse<QueryPubCoooperateVo> res = new PagingResponse<>();
        res.setAaData(page.getResult());
        res.setiTotalDisplayRecords((int) page.getTotal());
        res.setiTotalRecords((int) page.getTotal());
        res.setsEcho("");

        return res;
    }

    /**
     * 查询合作运营合作方下拉
     *
     * @param companyid 租赁ID
     * @return 合作方下拉集合
     */
    public List<Select2Entity> queryLeasecompany(String companyid) {
        List list = this.pubCoooperateUnionDao.queryLeasecompany(companyid);
        return list;
    }

    /**
     * 查看协议
     *
     * @param coooperateId 合作ID
     * @return 协议信息
     */
    public QueryCooagreementViewVo queryCooagreementView(String coooperateId) {
        QueryCooagreementViewVo vo = this.pubCoooperateUnionDao.queryCooagreementView(coooperateId);
        return vo;
    }


    /**
     * 终止合作
     *
     * @param param 参数
     */
    public void disableCoooperate(DisableCoooperateParam param) {
        this.pubCoooperateUnionDao.disableCoooperate(param);
    }

    /**
     * 调整资源界面查询品牌车系下拉框
     *
     * @param param 查询条件
     * @return 品牌车系列表
     */
    public List<Select2Entity> queryResourceVehclineSelect2(QueryResourceCitySelect2Param param) {
        List list = this.pubCoooperateUnionDao.queryResourceVehclineSelect2(param);
        return list;
    }

    /**
     * 调整资源界面查询服务城市下拉框
     *
     * @param coooId 合作ID
     * @return 服务车市列表
     */
    public List<Select2Entity> queryResourceCitySelect2(String coooId) {
        List list = this.pubCoooperateUnionDao.queryResourceCitySelect2(coooId);
        return list;
    }

    /**
     * 调整资源界面查询所有资源信息
     *
     * @param param 查询条件
     * @return 资源信息
     */
    public PagingResponse queryResource(QueryResourceParam param) {
        int pageNum = 1;

        if (param.getiDisplayLength() != 0) {
            pageNum = param.getiDisplayStart() / param.getiDisplayLength() + 1;
        }

        PageHelper.startPage(pageNum, param.getiDisplayLength());
        Page page = (Page) this.pubCoooperateUnionDao.queryResource(param);

        PagingResponse<QueryResourceVo> res = new PagingResponse<>();
        res.setAaData(page.getResult());
        res.setiTotalDisplayRecords((int) page.getTotal());
        res.setiTotalRecords((int) page.getTotal());
        res.setsEcho("");

        return res;
    }

    public PubCoooperate selectPubCoooopertateByPrimaryKey(String coooId) {
        PubCoooperate cooo = this.pubCoooperateUnionDao.selectPubCoooopertateByPrimaryKey(coooId);
        return cooo;
    }


    /**
     * 更新加入资源的车辆
     *
     * @param param 参数
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateResource(UpdateResourceParam param) {
        PubCoooperate cooo = this.pubCoooperateUnionDao.selectPubCoooopertateByPrimaryKey(param.getCoooId());

        String[] vehicleids = param.getVehicleids().split(",");

        Map<String, String> vehicleidsMap = new HashedMap();

        for (String vehicleid : vehicleids) {
            if (vehicleid != null && !"".equals(vehicleid)) {
                vehicleidsMap.put(vehicleid, vehicleid);
            }
        }

        List<String> vehicleidExistList = this.pubCoooperateUnionDao.queryCooVehicleId(param.getCoooId());
        Map<String, String> vehicleidExistMap = new HashedMap();
        for (String vehicleid : vehicleidExistList) {
            vehicleidExistMap.put(vehicleid, vehicleid);
        }


        List<PubCooresource> pubCooresources = new ArrayList<>();
        PubCooresource p;
        // 新增新加入的车辆
        for (String vehicleid : vehicleidsMap.values()) {
            if (!vehicleidExistMap.containsValue(vehicleid)) {
                p = new PubCooresource();
                p.setCompanyid(cooo.getCompanyid());
                p.setCoooperateid(cooo.getId());
                p.setCreater(param.getUpdater());
                p.setCreatetime(new Date());
                p.setEnabled(0);
                p.setId(GUIDGenerator.newGUID());
                p.setLeasecompanyid(cooo.getLeasecompanyid());
                p.setStatus(1);
                p.setUpdater(param.getUpdater());
                p.setUpdatetime(new Date());
                p.setVehicleid(vehicleid);

                pubCooresources.add(p);
            }
        }

        if (pubCooresources.size() > 0) {
            this.pubCoooperateUnionDao.insertResourceBatch(pubCooresources);
        }

        // 删除掉未选择的车辆
        DeleteCooresourceVehicleBatchParam delete = new DeleteCooresourceVehicleBatchParam();
        delete.setCoooId(param.getCoooId());
        for (String vehicleid : vehicleidExistMap.values()) {
            if (!vehicleidsMap.containsValue(vehicleid)) {
                delete.getVehicleids().add(vehicleid);
            }
        }

        if (delete.getVehicleids().size() > 0) {
            this.pubCoooperateUnionDao.deleteResourceByVehicleId(delete);
        }
    }

    /**
     * 获取资源管理的服务车型
     *
     * @param coooId 合作ID
     * @return 服务车型结合
     */
    public List<Select2Entity> queryCooVehicleModeSelect2(String coooId) {
        List<Select2Entity> list = this.pubCoooperateUnionDao.queryCooVehicleModeSelect2(coooId);
        return list;
    }

    /**
     * 获取已加入的车辆资源
     *
     * @param coooId 联盟ID
     * @return 车辆资源字符串
     */
    public String queryCooVehicleId(String coooId) {
        List<String> list = this.pubCoooperateUnionDao.queryCooVehicleId(coooId);
        String s = "";
        for (String str : list) {
            if (s.isEmpty()) {
                s += str;
            } else {
                s += "," + str;
            }
        }

        return s;
    }

    /**
     * 根据名称判断租赁公司是否可以合作
     *
     * @param param 参数
     * @return 0 可以
     */
    public JSONObject queryApplyLeaseCompany(QueryApplyLeaseCompanyParam param) {
        JSONObject res = new JSONObject();

        String company = "";
        if ("1".equals(param.getType())) {
            OpPlatformInfo op = this.pubCoooperateUnionDao.queryApplyOpCompany(param);
            if (op == null) {
                res.put("result", "1");
                return res;
            }
            company = op.getId();
        } else {
            LeLeasescompany le = this.pubCoooperateUnionDao.queryApplyLeaseCompany(param);
            if (le == null) {
                res.put("result", "1");
                return res;
            }

            if(le.getId().equals(param.getCompanyid())){
                res.put("result", "1");
                return res;
            }

            company = le.getId();
        }

        QueryApplyCooagreementParam cooaParam = new QueryApplyCooagreementParam();
        cooaParam.setCompanyid(company);
        cooaParam.setLeasecompanyid(param.getCompanyid());
        cooaParam.setServicetype(param.getServicetype());
        PubCooagreement cooa = this.pubCoooperateUnionDao.queryApplyCooagreement(cooaParam);

        if (cooa == null) {
            res.put("result", "2");
            return res;
        }
        res.put("cooagreement", cooa);

        QueryApplyCoooperateParam coooParam = new QueryApplyCoooperateParam();
        coooParam.setCompanyid(company);
        coooParam.setLeasecompanyid(param.getCompanyid());
        coooParam.setServicetype(param.getServicetype());
        PubCoooperate cooo = this.pubCoooperateUnionDao.queryApplyCoooperate(coooParam);

        if (cooo != null) {
            res.put("result", "3");
            return res;
        }

        res.put("result", "0");
        return res;
    }


    /**
     * 查询申请合作时的可选车辆资源
     *
     * @param param 参数
     * @return 可选资源
     */
    public PagingResponse queryApplyResource(QueryApplyResourceParam param) {
        int pageNum = 1;

        if (param.getiDisplayLength() != 0) {
            pageNum = param.getiDisplayStart() / param.getiDisplayLength() + 1;
        }

        PageHelper.startPage(pageNum, param.getiDisplayLength());
        Page page = (Page) this.pubCoooperateUnionDao.queryApplyResource(param);

        PagingResponse<QueryApplyResourceVo> res = new PagingResponse<>();
        res.setAaData(page.getResult());
        res.setiTotalDisplayRecords((int) page.getTotal());
        res.setiTotalRecords((int) page.getTotal());
        res.setsEcho("");

        return res;
    }

    /**
     * 查询申请合作时品牌车系下拉框
     *
     * @param param 参数
     * @return 品牌车系
     */
    public List<Select2Entity> queryApplyResourceVehclineSelect2(QueryApplyResourceVehclineSelect2Param param) {
        List<Select2Entity> list = this.pubCoooperateUnionDao.queryApplyResourceVehclineSelect2(param);
        return list;
    }

    /**
     * 查询车辆城市下拉选择框
     *
     * @param param 参数
     * @return 城市下拉
     */
    public List<Select2Entity> queryApplyResourceCitySelect2(QueryApplyResourceCitySelect2Param param) {
        List<Select2Entity> list = this.pubCoooperateUnionDao.queryApplyResourceCitySelect2(param);
        return list;
    }

    /**
     * 查询服务车型下拉选择框
     *
     * @param param 参数
     * @return 城市下拉
     */
    public List<Select2Entity> queryApplyCooVehicleModeSelect2(QueryApplyResourceCitySelect2Param param) {
        List<Select2Entity> list = this.pubCoooperateUnionDao.queryApplyCooVehicleModeSelect2(param);
        return list;
    }

    /**
     * 获取协议
     *
     * @param param 参数
     * @return 协议
     */
    public PubCooagreement queryApplyCooagreement(QueryApplyCooagreementParam param) {
        PubCooagreement cooa = this.pubCoooperateUnionDao.queryApplyCooagreement(param);
        return cooa;
    }

    /**
     * 新增合作协议
     *
     * @param param model
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addApplyCoooperate(AddApplyCoooperateParam param) {
        JSONObject result = new JSONObject();

        QueryApplyCooagreementParam cooaParam = new QueryApplyCooagreementParam();
        cooaParam.setCompanyid(param.getLeasescompanyid());
        cooaParam.setLeasecompanyid(param.getCompanyid());
        cooaParam.setServicetype(String.valueOf(param.getVehicletype()));

        PubCooagreement cooa = this.pubCoooperateUnionDao.queryApplyCooagreement(cooaParam);

        String coooId = GUIDGenerator.newGUID();
        PubCoooperate cooo = new PubCoooperate();
        cooo.setId(coooId);
        cooo.setApplicationtime(new Date());
        cooo.setCompanyid(param.getLeasescompanyid());
        cooo.setStatus(1);
        cooo.setCreater(param.getUpdater());
        cooo.setCreatetime(new Date());
        cooo.setLeasecompanyid(param.getCompanyid());
        cooo.setServicetype(param.getVehicletype());
        if (param.getCootype() == 1) {
            cooo.setPlatformtype(0);
        } else {
            cooo.setPlatformtype(1);
        }

        cooo.setCooagreementid(cooa.getId());
        cooo.setCoostate(0);
        cooo.setCootype(param.getCootype());
        cooo.setUpdater(param.getUpdater());
        cooo.setUpdatetime(new Date());
        String coooNo = CoooperateNumUtils.getNum(param.getCootype());
        cooo.setCoono(coooNo);

        this.pubCoooperateUnionDao.insertPubCoooperateSelective(cooo);

        List<PubCooresource> pubCooresources = new ArrayList<>();
        PubCooresource p;
        // 新增新加入的车辆
        for (String vehicleid : param.getVehicleid().split(",")) {
            if (vehicleid != null && !"".equals(vehicleid)) {
                p = new PubCooresource();
                p.setCompanyid(param.getLeasescompanyid());
                p.setCoooperateid(coooId);
                p.setCreater(param.getUpdater());
                p.setCreatetime(new Date());
                p.setEnabled(0);
                p.setId(GUIDGenerator.newGUID());
                p.setLeasecompanyid(param.getCompanyid());
                p.setStatus(1);
                p.setUpdater(param.getUpdater());
                p.setUpdatetime(new Date());
                p.setVehicleid(vehicleid);

                pubCooresources.add(p);
            }
        }

        this.pubCoooperateUnionDao.insertResourceBatch(pubCooresources);

        result.put("cooono", coooNo);

        return result;
    }


}
