package com.szyciov.passenger.mapper;

import com.szyciov.entity.PubDriver;
import com.szyciov.lease.param.PubDriverInBoundParam;

import java.util.List;

/**
 * Created by shikang on 2017/6/13.
 */
public interface PubDriverMapper {

    List<PubDriver> getNearDrivers(PubDriverInBoundParam param);

}
