package com.szyciov.operate.service;

import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.operate.dao.OpInformationSetDao;
import com.szyciov.operate.dao.OpPlatformStagnationDao;
import com.szyciov.op.param.OpPlatformStagnationQueryParam;
import com.szyciov.param.QueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
@Service("OpPlatformStagnationService")
public class OpPlatformStagnationService {
    private OpPlatformStagnationDao dao;

    @Resource(name = "OpPlatformStagnationDao")
    public void setDao(OpPlatformStagnationDao dao) {
        this.dao = dao;
    }

    private OpInformationSetDao platFormDao;

    @Resource(name = "OpInformationSetDao")
    public void setPlatFormDao(OpInformationSetDao platFormDao) {
        this.platFormDao = platFormDao;
    }

    /**
     * 分页查询
     * @param queryParam
     * @return
     */
    public PageBean page(OpPlatformStagnationQueryParam queryParam) {
        PageBean pageBean = new PageBean();
        pageBean.setsEcho(queryParam.getsEcho());
        List<OpPlatformStagnation> list = getListByQuery(queryParam);
        int iTotalRecords = getListCountByQuery(queryParam);
        int iTotalDisplayRecords = iTotalRecords;
        pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
        pageBean.setiTotalRecords(iTotalRecords);
        pageBean.setAaData(list);
        return pageBean;
    }

    /**
     * 获取分页数据列表
     * @param queryParam
     * @return
     */
    private int getListCountByQuery(OpPlatformStagnationQueryParam queryParam) {
        return dao.getListCountByQuery(queryParam);
    }

    /**
     * 获取要查询的数据数目
     * @param queryParam
     * @return
     */
    private List<OpPlatformStagnation> getListByQuery(OpPlatformStagnationQueryParam queryParam) {
        return dao.getListByQuery(queryParam);
    }

    /**
     * 新增机构，确保机构不会重复
     * @param opPlatformStagnation
     * @return
     */
    public Map<String, String> create(OpPlatformStagnation opPlatformStagnation) {
        Map<String,String> res = new HashMap<String,String>();
        //增加平台id
        OpPlatformInfo opPlatformInfo=platFormDao.getOpPlatformInfo();
        opPlatformStagnation.setId(GUIDGenerator.newGUID());
        if(opPlatformInfo!=null)
            opPlatformStagnation.setPlatformid(opPlatformInfo.getId());

        int r=dao.create(opPlatformStagnation);
        if(r>0){
            res.put("status", "success");
            res.put("message", "新增成功");
        }else {
            res.put("status", "fail");
            res.put("message", "新增失败");
        }
        return res;
    }

    /**
     * 修改机构，检查该机构是否存在
     * @param opPlatformStagnation
     * @return
     */
    public Map<String, String> update(OpPlatformStagnation opPlatformStagnation) {
        Map<String,String> res = new HashMap<String,String>();
        int r=dao.update(opPlatformStagnation);
        if(r>0){
            res.put("status", "success");
            res.put("message", "修改成功");
        }else {
            res.put("status", "fail");
            res.put("message", "修改失败!");
        }
        return res;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public Map<String, String> delete(String id){
        Map<String,String> res = new HashMap<String,String>();
        int r=dao.delete(id);
        if(r>0){
            res.put("status", "success");
            res.put("message", "删除成功");
        }else {
            res.put("status", "fail");
            res.put("message", "删除失败!");
        }
        return  res;
    }

    /**
     * 获取服务机构
     * @param id
     * @return
     */
    public OpPlatformStagnation getById(String id){
        return  dao.getById(id);
    }

    /**
     * 导出的数据查询，目前强制限制在5000行
     * @param queryParam
     * @return
     */
    public List<OpPlatformStagnation> exportExcel(QueryParam queryParam){
        return  this.dao.exportExcel(queryParam);
    }


}
