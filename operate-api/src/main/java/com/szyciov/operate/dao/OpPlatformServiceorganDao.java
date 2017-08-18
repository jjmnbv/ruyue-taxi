package com.szyciov.operate.dao;

import com.szyciov.op.entity.OpPlatformServiceorgan;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.vo.OpPlatformServiceorganVo;
import com.szyciov.operate.mapper.OpPlatformServiceorganMapper;
import com.szyciov.param.QueryParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
@Repository("opPlatformServiceorganDao")
public class OpPlatformServiceorganDao {
    private OpPlatformServiceorganMapper mapper;

    /**
     * @param mapper
     */
    @Resource
    public void setMapper(OpPlatformServiceorganMapper mapper) {
        this.mapper = mapper;
    }

    public List<OpPlatformServiceorgan> getListByQuery(QueryParam queryParam){
        return  this.mapper.getListByQuery(queryParam);
    }

    public  int getListCountByQuery(QueryParam queryParam){
        return this.mapper.getListCountByQuery(queryParam);
    }

    public int delete(String id){
        return  this.mapper.delete(id);
    }

    public int create(OpPlatformServiceorgan opPlatformServiceorgan){
        return  this.mapper.create(opPlatformServiceorgan);
    }

    public int update(OpPlatformServiceorgan opPlatformServiceorgan){
        return  this.mapper.update(opPlatformServiceorgan);
    }

    public OpPlatformServiceorganVo getById(String id){
        return  this.mapper.getById(id);
    }

    public List<OpPlatformServiceorganVo> exportExcel(QueryParam queryParam){
        return  this.mapper.exportExcel(queryParam);
    }
}
