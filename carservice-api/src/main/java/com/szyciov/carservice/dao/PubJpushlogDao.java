package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.PubJpushlogMapper;
import com.szyciov.entity.PubJpushlog;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by shikang on 2017/6/11.
 */
@Repository("PubJpushlogDao")
public class PubJpushlogDao {

    private PubJpushlogMapper mapper;
    @Resource
    public void setMapper(PubJpushlogMapper mapper) {
        this.mapper = mapper;
    }

    public void updatePubJpushlogById(PubJpushlog object) {
        mapper.updatePubJpushlogById(object);
    }
}
