package com.szyciov.operate.dao;

import javax.annotation.Resource;

import com.szyciov.entity.PubOrderCancel;
import com.szyciov.operate.mapper.OpOrdercancelMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by shikang on 2017/8/3.
 */
@Repository("OpOrdercancelDao")
public class OpOrdercancelDao {

    @Resource
    private OpOrdercancelMapper mapper;

    public PubOrderCancel getOpOrdercancel(PubOrderCancel orderCancel) {
        return mapper.getOpOrdercancel(orderCancel);
    }

    public void updateOpOrdercancelById(PubOrderCancel orderCancel) {
        mapper.updateOpOrdercancelById(orderCancel);
    }

}
