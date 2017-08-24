package com.szyciov.supervision.service.impl;

import com.szyciov.supervision.entity.PubSupervisionLog;

import com.szyciov.supervision.mapper.PubSupervisionLogMapper;
import com.szyciov.supervision.service.PubSupervisionLogService;
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
