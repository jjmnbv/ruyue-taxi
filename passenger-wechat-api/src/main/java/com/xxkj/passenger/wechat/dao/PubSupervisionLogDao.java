package com.xxkj.passenger.wechat.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xxkj.passenger.wechat.entity.PubSupervisionLog;
import com.xxkj.passenger.wechat.mapper.PubSupervisionLogMapper;

/**
 */
@Repository("pubSupervisionLogDao")
public class PubSupervisionLogDao {
    private @Autowired
    PubSupervisionLogMapper pubSupervisionLogMapper;

    public int insert(PubSupervisionLog pubSupervisionLog){
        return  pubSupervisionLogMapper.insert(pubSupervisionLog);
    }

}
