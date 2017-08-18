package com.szyciov.operate.mapper;

import com.szyciov.op.entity.OpPlatformServiceorgan;
import com.szyciov.op.vo.OpPlatformServiceorganVo;
import com.szyciov.param.QueryParam;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public interface OpPlatformServiceorganMapper {
    List<OpPlatformServiceorgan> getListByQuery(QueryParam queryParam);

    int getListCountByQuery(QueryParam queryParam);

    int delete(String id);

    int create(OpPlatformServiceorgan opPlatformServiceorgan);

    int update(OpPlatformServiceorgan opPlatformServiceorgan);

    OpPlatformServiceorganVo getById(String id);

    List<OpPlatformServiceorganVo> exportExcel(QueryParam queryParam);
}
