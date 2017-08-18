package com.szyciov.operate.dao;

import com.szyciov.op.entity.OpPlatformBusinesslicenseScope;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.operate.mapper.OpPlatformBusinesslicenseScopeMapper;
import com.szyciov.operate.mapper.OpPlatformStagnationMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lzw on 2017/8/15.
 */
@Repository("opPlatformBusinesslicenseScopeDao")
public class OpPlatformBusinesslicenseScopeDao {

    private OpPlatformBusinesslicenseScopeMapper mapper;

    /**
     * @param mapper
     */
    @Resource
    public void setMapper(OpPlatformBusinesslicenseScopeMapper mapper) {
        this.mapper = mapper;
    }


    public int deleteOther(String businesslicenseid, List<String> operationareas){
        return  mapper.deleteOther(businesslicenseid,operationareas);
    }

    public int create(OpPlatformBusinesslicenseScope opPlatformBusinesslicenseScope){
        return  mapper.create(opPlatformBusinesslicenseScope);
    }

    /**
     * 查询经营是否存在
     * @param businesslicenseid
     * @param operationarea
     * @return
     */
    public OpPlatformBusinesslicenseScope find( String businesslicenseid,String operationarea){
        return  mapper.find(businesslicenseid,operationarea);
    }
}
