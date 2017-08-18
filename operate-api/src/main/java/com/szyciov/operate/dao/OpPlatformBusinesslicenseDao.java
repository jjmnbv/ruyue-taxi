package com.szyciov.operate.dao;

import com.szyciov.op.dto.OpPlatformBusinesslicenseDto;
import com.szyciov.op.vo.OpPlatformBusinesslicenseVo;
import com.szyciov.operate.mapper.OpPlatformBusinesslicenseMapper;
import com.szyciov.operate.mapper.OpPlatformBusinesslicenseScopeMapper;
import com.szyciov.param.QueryParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lzw on 2017/8/16.
 */
@Repository("OpPlatformBusinesslicenseDao")
public class OpPlatformBusinesslicenseDao {
    private OpPlatformBusinesslicenseMapper mapper;

    /**
     * @param mapper
     */
    @Resource
    public void setMapper(OpPlatformBusinesslicenseMapper mapper) {
        this.mapper = mapper;
    }

    public List<OpPlatformBusinesslicenseVo> getListByQuery(QueryParam queryParam){
        return mapper.getListByQuery(queryParam);
    }

    public int getListCountByQuery(QueryParam queryParam){
        return mapper.getListCountByQuery(queryParam);
    }

    public int delete(String id){
        return  mapper.delete(id);
    }

    public int create(OpPlatformBusinesslicenseDto opPlatformStagnation){
        return mapper.create(opPlatformStagnation);
    }

    public int update(OpPlatformBusinesslicenseDto opPlatformStagnation){
        return mapper.update(opPlatformStagnation);
    }

    public OpPlatformBusinesslicenseVo getById(String id){
        return  mapper.getById(id);
    }

    public List<OpPlatformBusinesslicenseVo> exportExcel(QueryParam queryParam){
        return  mapper.exportExcel(queryParam);
    }

}
