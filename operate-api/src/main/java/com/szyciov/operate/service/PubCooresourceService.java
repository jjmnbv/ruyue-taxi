package com.szyciov.operate.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.PubCooresource;
import com.szyciov.entity.PubVehicleModelsRef;
import com.szyciov.entity.PubVehicleModelsRefHistory;
import com.szyciov.entity.Select2Entity;
import com.szyciov.op.param.*;
import com.szyciov.op.vo.pubCooresource.QueryCooresourceVo;
import com.szyciov.op.vo.pubCooresource.QueryHavingCooLeasecompanyVo;
import com.szyciov.op.vo.pubCooresource.QueryPubCooresourceInfoVo;
import com.szyciov.op.vo.pubCooresource.QueryPubCooresourceManageVo;
import com.szyciov.operate.dao.PubCooresourceDao;
import com.szyciov.util.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class PubCooresourceService {
    @Autowired
    private PubCooresourceDao pubCooresourceDao;

    /**
     * 查询联营数据
     *
     * @param model 查询条件
     * @return 联营信息
     */
    public PagingResponse queryPubCooresourceData(QueryPubCooresourceParam model) {
        int pageNum = 1;

        if (model.getiDisplayLength() != 0) {
            pageNum = model.getiDisplayStart() / model.getiDisplayLength() + 1;
        }

        PageHelper.startPage(pageNum, model.getiDisplayLength());
        Page page = (Page) this.pubCooresourceDao.queryPubCooresourceData(model);

        PagingResponse<QueryCooresourceVo> res = new PagingResponse<>();
        res.setAaData(page.getResult());
        res.setiTotalDisplayRecords((int) page.getTotal());
        res.setiTotalRecords((int) page.getTotal());
        res.setsEcho("");

        return res;
    }

    /**
     * 查询战略伙伴
     *
     * @param companyid 当前运管id
     * @return 战略伙伴集合
     */
    public List<QueryHavingCooLeasecompanyVo> queryHavingCooLeasecompany(String companyid, String keyword) {
        List<QueryHavingCooLeasecompanyVo> list = this.pubCooresourceDao.queryHavingCooLeasecompany(companyid, keyword);
        return list;
    }


    /**
     * 查询资源信息
     *
     * @param param 查询条件
     * @return 资源信息列表
     */
    public PagingResponse queryPubCooresourceInfoData(QueryPubCooresourceInfoParam param) {
        int pageNum = 1;

        if (param.getiDisplayLength() != 0) {
            pageNum = param.getiDisplayStart() / param.getiDisplayLength() + 1;
        }

        PageHelper.startPage(pageNum, param.getiDisplayLength());
        Page page = (Page) this.pubCooresourceDao.queryPubCooresourceInfoData(param);

        PagingResponse<QueryPubCooresourceInfoVo> res = new PagingResponse<>();
        res.setAaData(page.getResult());
        res.setiTotalDisplayRecords((int) page.getTotal());
        res.setiTotalRecords((int) page.getTotal());
        res.setsEcho("");

        return res;
    }

    /**
     * 资源信息司机下拉框数据获取
     *
     * @param param 查询条件
     * @return select2
     */
    public List<Select2Entity> queryPubCooresourceInfoDriverSelect(QueryPubCooresourceInfoDriverSelectParam param) {
        List<Select2Entity> list = this.pubCooresourceDao.queryPubCooresourceInfoDriverSelect(param);
        return list;
    }

    /**
     * 查询资源管理
     *
     * @param param 查询条件
     * @return 资源管理列表
     */
    public PagingResponse queryPubCooresourceManageData(QueryPubCooresourceManageParam param) {
        int pageNum = 1;

        if (param.getiDisplayLength() != 0) {
            pageNum = param.getiDisplayStart() / param.getiDisplayLength() + 1;
        }

        PageHelper.startPage(pageNum, param.getiDisplayLength());
        Page page = (Page) this.pubCooresourceDao.queryPubCooresourceManageData(param);

        PagingResponse<QueryPubCooresourceManageVo> res = new PagingResponse<>();
        res.setAaData(page.getResult());
        res.setiTotalDisplayRecords((int) page.getTotal());
        res.setiTotalRecords((int) page.getTotal());
        res.setsEcho("");

        return res;
    }

    /**
     * 资源管理司机下拉框数据获取
     *
     * @param param 查询条件
     * @return select2
     */
    public List<Select2Entity> queryPubCooresourceManageDriverSelect(QueryPubCooresourceManageDriverSelectParam param) {
        List<Select2Entity> list = this.pubCooresourceDao.queryPubCooresourceManageDriverSelect(param);
        return list;
    }

    /**
     * 资源管理服务车型下拉框
     *
     * @return select2
     */
    public List<Select2Entity> queryPubCooresourceManageVehicleModelSelect() {
        List<Select2Entity> list = this.pubCooresourceDao.queryPubCooresourceManageVehicleModelSelect();
        return list;
    }


    /**
     * 资源管理下更改车型
     *
     * @param param
     * @return
     */
    public void updateVehicleModel(UpdatePubVehicleModelsParam param) {
        PubVehicleModelsRef ref = this.pubCooresourceDao.queryPubVehicleModelsRefByCoooId(param.getCoorId());

        if (ref == null) {
            ref = new PubVehicleModelsRef();

            PubCooresource coo = this.pubCooresourceDao.selectPubCooresourceByPrimaryKey(param.getCoorId());

            ref.setId(GUIDGenerator.newGUID());
            ref.setCompanyid(coo.getCompanyid());
            ref.setLeasecompanyid(coo.getLeasecompanyid());
            ref.setVehicleid(coo.getVehicleid());
            ref.setVehiclemodelsid(param.getVehiclemodelsid());
            ref.setCreatetime(new Date());
            ref.setStatus(1);
            ref.setUpdater(param.getUpdater());
            ref.setCreater(param.getUpdater());
            ref.setUpdatetime(new Date());

            this.pubCooresourceDao.insertPubVehicleModelsRefSelective(ref);
        } else {
            ref.setVehiclemodelsid(param.getVehiclemodelsid());
            ref.setUpdater(param.getUpdater());
            ref.setUpdatetime(new Date());
            this.pubCooresourceDao.updatePubVehicleModelsRefByPrimaryKeySelective(ref);
        }

        PubVehicleModelsRefHistory history = new PubVehicleModelsRefHistory();
        history.setId(GUIDGenerator.newGUID());
        history.setCompanyid(ref.getCompanyid());
        history.setLeasecompanyid(ref.getLeasecompanyid());
        history.setVehicleid(ref.getVehicleid());
        history.setVehiclemodelsid(param.getVehiclemodelsid());
        history.setCreatetime(new Date());
        history.setStatus(1);
        history.setUpdater(param.getUpdater());
        history.setCreater(param.getUpdater());
        history.setUpdatetime(new Date());

        this.pubCooresourceDao.insertPubVehicleModelsRefHistory(history);
    }
}
