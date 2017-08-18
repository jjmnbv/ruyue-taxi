package com.szyciov.operate.service;

import com.szyciov.op.entity.OpPlatformBusinesslicenseScope;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.operate.dao.OpInformationSetDao;
import com.szyciov.operate.dao.OpPlatformBusinesslicenseScopeDao;
import com.szyciov.operate.dao.OpPlatformServiceorganDao;
import com.szyciov.util.GUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzw on 2017/8/15.
 */
@Service("opPlatformBusinesslicenseScopeService")
public class OpPlatformBusinesslicenseScopeService {
    private OpPlatformBusinesslicenseScopeDao dao;

    @Resource(name = "opPlatformBusinesslicenseScopeDao")
    public void setDao(OpPlatformBusinesslicenseScopeDao dao) {
        this.dao = dao;
    }


    /**
     * 删除其他
     * @param businesslicenseid
     * @param operationareas
     * @return
     */
    public Map<String, String> deleteOther(String businesslicenseid, List<String> operationareas){
        Map<String,String> res = new HashMap<String,String>();
        int r=dao.deleteOther(businesslicenseid,operationareas);
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
     * 新增机构，确保机构不会重复
     * @param opPlatformBusinesslicenseScope
     * @return
     */
    public Map<String, String> create(OpPlatformBusinesslicenseScope opPlatformBusinesslicenseScope) {
        Map<String,String> res = new HashMap<String,String>();
        //增加平台id
        opPlatformBusinesslicenseScope.setId(GUIDGenerator.newGUID());
        int r=dao.create(opPlatformBusinesslicenseScope);
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
     * 查询经营区域是否存在
     * @param businesslicenseid
     * @param operationarea
     * @return
     */
    public OpPlatformBusinesslicenseScope find( String businesslicenseid,String operationarea){
        return  dao.find(businesslicenseid,operationarea);
    }
}
