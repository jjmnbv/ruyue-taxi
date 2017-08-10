package com.szyciov.supervision.dao;

import com.szyciov.supervision.entity.PubSupervisionLog;
import com.szyciov.supervision.mapper.PubSupervisionLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by 林志伟 on 2017/7/26.
 */
@Repository("pubSupervisionLogDao")
public class PubSupervisionLogDao {
    private @Autowired
    PubSupervisionLogMapper pubSupervisionLogMapper;

    public int insert(PubSupervisionLog pubSupervisionLog){
        return  pubSupervisionLogMapper.insert(pubSupervisionLog);
    }

}
