package com.szyciov.operate.mapper;

import com.szyciov.op.entity.OpPlatformBusinesslicenseScope;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.param.QueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public interface OpPlatformBusinesslicenseScopeMapper {

    int deleteOther(@Param("businesslicenseid")  String businesslicenseid,@Param("operationareas") List<String> operationareas);

    int create(OpPlatformBusinesslicenseScope opPlatformBusinesslicenseScope);

    OpPlatformBusinesslicenseScope find(@Param("businesslicenseid")  String businesslicenseid,@Param("operationarea")  String operationarea);

}
