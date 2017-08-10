package com.szyciov.supervision.mapper;

import com.szyciov.supervision.entity.PubSupervisionLog;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by 林志伟 on 2017/7/26.
 */

@org.apache.ibatis.annotations.Mapper
public interface PubSupervisionLogMapper extends Mapper<PubSupervisionLog> {
}
