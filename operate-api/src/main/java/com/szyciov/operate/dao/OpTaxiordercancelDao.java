package com.szyciov.operate.dao;

import javax.annotation.Resource;

import com.szyciov.entity.PubOrderCancel;
import com.szyciov.operate.mapper.OpTaxiordercancelMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by shikang on 2017/8/18.
 */
@Repository("OpTaxiordercancelDao")
public class OpTaxiordercancelDao {

    @Resource
    private OpTaxiordercancelMapper mapper;

    public PubOrderCancel getOpTaxiordercancel(PubOrderCancel object) {
        return mapper.getOpTaxiordercancel(object);
    }

    public void updateOpTaxiordercancelById(PubOrderCancel object) {
        mapper.updateOpTaxiordercancelById(object);
    }

}
 