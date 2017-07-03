package com.szyciov.passenger.dao;

import com.szyciov.entity.PubDriver;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.passenger.mapper.PubDriverMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by shikang on 2017/6/13.
 */
@Repository("PubDriverDao")
public class PubDriverDao {

    @Resource
    private PubDriverMapper mapper;

    public List<PubDriver> getNearDrivers(PubDriverInBoundParam param) {
        return mapper.getNearDrivers(param);
    }

}
