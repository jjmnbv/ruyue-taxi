package com.szyciov.operate.mapper;

import com.szyciov.op.dto.OpPlatformBusinesslicenseDto;
import com.szyciov.op.entity.OpPlatformBusinesslicense;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.op.vo.OpPlatformBusinesslicenseVo;
import com.szyciov.param.QueryParam;

import java.util.List;

/**
 * 经营许可信息
 * Created by lzw on 2017/8/15.
 */
public interface OpPlatformBusinesslicenseMapper {

    List<OpPlatformBusinesslicenseVo> getListByQuery(QueryParam queryParam);

    int getListCountByQuery(QueryParam queryParam);

    int delete(String id);

    int create(OpPlatformBusinesslicenseDto opPlatformStagnation);

    int update(OpPlatformBusinesslicenseDto opPlatformStagnation);

    OpPlatformBusinesslicenseVo getById(String id);

    List<OpPlatformBusinesslicenseVo> exportExcel(QueryParam queryParam);
}
