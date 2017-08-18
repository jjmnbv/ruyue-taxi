package com.szyciov.operate.service;

import com.szyciov.op.dto.OpPlatformBusinesslicenseDto;
import com.szyciov.op.entity.OpPlatformBusinesslicense;
import com.szyciov.op.entity.OpPlatformBusinesslicenseScope;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.op.param.OpPlatformBusinesslicenseQueryParam;
import com.szyciov.op.param.OpPlatformStagnationQueryParam;
import com.szyciov.op.vo.OpPlatformBusinesslicenseVo;
import com.szyciov.operate.dao.OpInformationSetDao;
import com.szyciov.operate.dao.OpPlatformBusinesslicenseDao;
import com.szyciov.operate.dao.OpPlatformBusinesslicenseScopeDao;
import com.szyciov.param.QueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzw on 2017/8/16.
 */
@Service("opPlatformBusinesslicenseService")
public class OpPlatformBusinesslicenseService {
    private OpPlatformBusinesslicenseDao dao;

    @Resource(name = "OpPlatformBusinesslicenseDao")
    public void setDao(OpPlatformBusinesslicenseDao dao) {
        this.dao = dao;
    }

    private OpInformationSetDao platFormDao;

    @Resource(name = "OpInformationSetDao")
    public void setPlatFormDao(OpInformationSetDao platFormDao) {
        this.platFormDao = platFormDao;
    }


    @Autowired
    private OpPlatformBusinesslicenseScopeService opPlatformBusinesslicenseScopeService;

    /**
     * 分页查询
     * @param queryParam
     * @return
     */
    public PageBean page(OpPlatformBusinesslicenseQueryParam queryParam) {
        PageBean pageBean = new PageBean();
        pageBean.setsEcho(queryParam.getsEcho());
        List<OpPlatformBusinesslicenseVo> list = getListByQuery(queryParam);
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
    private int getListCountByQuery(OpPlatformBusinesslicenseQueryParam queryParam) {
        return dao.getListCountByQuery(queryParam);
    }

    /**
     * 获取要查询的数据数目
     * @param queryParam
     * @return
     */
    private List<OpPlatformBusinesslicenseVo> getListByQuery(OpPlatformBusinesslicenseQueryParam queryParam) {
        return dao.getListByQuery(queryParam);
    }

    /**
     * 新增机构，确保机构不会重复
     * @param opPlatformBusinesslicenseDto
     * @return
     */
    public Map<String, String> create(OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto) {
        Map<String,String> res = new HashMap<String,String>();
        //增加平台id
        OpPlatformInfo opPlatformInfo=platFormDao.getOpPlatformInfo();
        opPlatformBusinesslicenseDto.setId(GUIDGenerator.newGUID());
        if(opPlatformInfo!=null)
            opPlatformBusinesslicenseDto.setPlatformid(opPlatformInfo.getId());

        int r=dao.create(opPlatformBusinesslicenseDto);
        if(r>0){
            //增加经营方位
           List<String> operationareas=opPlatformBusinesslicenseDto.getOperationareas();
           for (int i=0;i<operationareas.size();i++){
               OpPlatformBusinesslicenseScope opPlatformBusinesslicenseScope=new OpPlatformBusinesslicenseScope();
               opPlatformBusinesslicenseScope.setBusinesslicenseid(opPlatformBusinesslicenseDto.getId());
               opPlatformBusinesslicenseScope.setOperationarea(operationareas.get(i));
               opPlatformBusinesslicenseScope.setId(GUIDGenerator.newGUID());
               opPlatformBusinesslicenseScopeService.create(opPlatformBusinesslicenseScope);
           }
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
     * @param opPlatformBusinesslicenseDto
     * @return
     */
    public Map<String, String> update(OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto) {
        Map<String,String> res = new HashMap<String,String>();
        int r=dao.update(opPlatformBusinesslicenseDto);
        if(r>0){
            //删除多余的经营范围
            opPlatformBusinesslicenseScopeService.deleteOther(opPlatformBusinesslicenseDto.getId(),opPlatformBusinesslicenseDto.getOperationareas());

            List<String> operationareas=opPlatformBusinesslicenseDto.getOperationareas();
            for (int i=0;i<operationareas.size();i++){
                //如果存在，则不再插入
                if(opPlatformBusinesslicenseScopeService.find(opPlatformBusinesslicenseDto.getId(),operationareas.get(i))!=null){
                    continue;
                }
                OpPlatformBusinesslicenseScope opPlatformBusinesslicenseScope=new OpPlatformBusinesslicenseScope();
                opPlatformBusinesslicenseScope.setBusinesslicenseid(opPlatformBusinesslicenseDto.getId());
                opPlatformBusinesslicenseScope.setOperationarea(operationareas.get(i));
                opPlatformBusinesslicenseScope.setId(GUIDGenerator.newGUID());
                opPlatformBusinesslicenseScopeService.create(opPlatformBusinesslicenseScope);
            }


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
            opPlatformBusinesslicenseScopeService.deleteOther(id,null);
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
    public OpPlatformBusinesslicenseVo getById(String id){
        return  dao.getById(id);
    }

    /**
     * 导出的数据查询，目前强制限制在5000行
     * @param queryParam
     * @return
     */
    public List<OpPlatformBusinesslicenseVo> exportExcel(OpPlatformBusinesslicenseQueryParam queryParam){
        return  this.dao.exportExcel(queryParam);
    }


}
