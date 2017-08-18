package com.szyciov.operate.mapper;

import com.szyciov.op.entity.OpPlatformServiceorgan;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.op.vo.OpPlatformServiceorganVo;
import com.szyciov.param.QueryParam;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public interface OpPlatformStagnationMapper {
    List<OpPlatformStagnation> getListByQuery(QueryParam queryParam);

    int getListCountByQuery(QueryParam queryParam);

    int delete(String id);

    int create(OpPlatformStagnation opPlatformStagnation);

    int update(OpPlatformStagnation opPlatformStagnation);

    OpPlatformStagnation getById(String id);

    List<OpPlatformStagnation> exportExcel(QueryParam queryParam);


}
