package com.szyciov.operate.dao;

import com.szyciov.op.entity.OpPlatformServiceorgan;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.op.vo.OpPlatformServiceorganVo;
import com.szyciov.operate.mapper.OpPlatformServiceorganMapper;
import com.szyciov.operate.mapper.OpPlatformStagnationMapper;
import com.szyciov.param.QueryParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 平台驻点信息
 * Created by Administrator on 2017/8/8 0008.
 */
@Repository("OpPlatformStagnationDao")
public class OpPlatformStagnationDao {
    private OpPlatformStagnationMapper mapper;

    /**
     * @param mapper
     */
    @Resource
    public void setMapper(OpPlatformStagnationMapper mapper) {
        this.mapper = mapper;
    }

    public List<OpPlatformStagnation> getListByQuery(QueryParam queryParam){
        return  this.mapper.getListByQuery(queryParam);
    }

    public  int getListCountByQuery(QueryParam queryParam){
        return this.mapper.getListCountByQuery(queryParam);
    }

    public int delete(String id){
        return  this.mapper.delete(id);
    }

    public int create(OpPlatformStagnation opPlatformStagnation){
        return  this.mapper.create(opPlatformStagnation);
    }

    public int update(OpPlatformStagnation opPlatformStagnation){
        return  this.mapper.update(opPlatformStagnation);
    }

    public OpPlatformStagnation getById(String id){
        return  this.mapper.getById(id);
    }

    public List<OpPlatformStagnation> exportExcel(QueryParam queryParam){
        return  this.mapper.exportExcel(queryParam);
    }
}
