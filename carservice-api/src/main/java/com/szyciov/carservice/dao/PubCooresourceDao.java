package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.PubCooresourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by ZF on 2017/8/11.
 */
@Repository
public class PubCooresourceDao {
    @Autowired
    private PubCooresourceMapper pubCooresourceMapper;

    public int updatePubCooresourceStatus(Map param) {
        return this.pubCooresourceMapper.updatePubCooresourceStatus(param);
    }

    public int queryTaxiEnableDriver(Map param) {
        return this.pubCooresourceMapper.queryTaxiEnableDriver(param);
    }

}
