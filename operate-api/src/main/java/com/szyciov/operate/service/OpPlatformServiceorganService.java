package com.szyciov.operate.service;

import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpPlatformServiceorgan;
import com.szyciov.op.vo.OpPlatformServiceorganVo;
import com.szyciov.operate.dao.OpInformationSetDao;
import com.szyciov.operate.dao.OpPlatformServiceorganDao;
import com.szyciov.op.param.OpPlatformServiceorganQueryParam;
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
@Service("opPlatformServiceorganService")
public class OpPlatformServiceorganService {
    private OpPlatformServiceorganDao dao;

    @Resource(name = "opPlatformServiceorganDao")
    public void setDao(OpPlatformServiceorganDao dao) {
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
    public PageBean page(OpPlatformServiceorganQueryParam queryParam) {
        PageBean pageBean = new PageBean();
        pageBean.setsEcho(queryParam.getsEcho());
        List<OpPlatformServiceorgan> list = getListByQuery(queryParam);
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
    private int getListCountByQuery(OpPlatformServiceorganQueryParam queryParam) {
        return dao.getListCountByQuery(queryParam);
    }

    /**
     * 获取要查询的数据数目
     * @param queryParam
     * @return
     */
    private List<OpPlatformServiceorgan> getListByQuery(OpPlatformServiceorganQueryParam queryParam) {
        return dao.getListByQuery(queryParam);
    }

    /**
     * 新增机构，确保机构不会重复
     * @param opPlatformServiceorgan
     * @return
     */
    public Map<String, String> create(OpPlatformServiceorgan opPlatformServiceorgan) {
        Map<String,String> res = new HashMap<String,String>();
        //增加平台id
        OpPlatformInfo opPlatformInfo=platFormDao.getOpPlatformInfo();
        opPlatformServiceorgan.setId(GUIDGenerator.newGUID());
        if(opPlatformInfo!=null)
            opPlatformServiceorgan.setPlatformid(opPlatformInfo.getId());

        int r=dao.create(opPlatformServiceorgan);
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
     * @param opPlatformServiceorgan
     * @return
     */
    public Map<String, String> update(OpPlatformServiceorgan opPlatformServiceorgan) {
        Map<String,String> res = new HashMap<String,String>();
        int r=dao.update(opPlatformServiceorgan);
        if(r>0){
            res.put("status", "success");
            res.put("message", "修改成功");
        }else {
            res.put("status", "fail");
            res.put("message", "修改失败!");
        }
        return res;
    }
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
    public OpPlatformServiceorganVo getById(String id){
        return  dao.getById(id);
    }

    /**
     * 导出的数据查询，目前强制限制在5000行
     * @param queryParam
     * @return
     */
    public List<OpPlatformServiceorganVo> exportExcel(QueryParam queryParam){
        return  this.dao.exportExcel(queryParam);
    }
}
