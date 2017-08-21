package com.szyciov.supervision.serivice.impl;

import com.szyciov.supervision.entity.PubSupervisionLog;

import com.szyciov.supervision.mapper.PubSupervisionLogMapper;
import com.szyciov.supervision.serivice.PubSupervisionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lzw on 2017/8/18.
 */
@Service("pubSupervisionLogService")
public class PubSupervisionLogServiceImpl implements PubSupervisionLogService {
    private @Autowired
    PubSupervisionLogMapper pubSupervisionLogMapper;

    public int insert(PubSupervisionLog pubSupervisionLog){
        return  pubSupervisionLogMapper.insert(pubSupervisionLog);
    }
}
