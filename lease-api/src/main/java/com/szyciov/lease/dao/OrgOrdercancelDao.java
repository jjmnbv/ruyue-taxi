package com.szyciov.lease.dao;

import javax.annotation.Resource;

import com.szyciov.entity.PubOrderCancel;
import com.szyciov.lease.mapper.OrgOrdercancelMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by shikang on 2017/8/3.
 */
@Repository("OrgOrdercancelDao")
public class OrgOrdercancelDao {

    @Resource
    private OrgOrdercancelMapper mapper;

    public PubOrderCancel getOrgOrdercancel(PubOrderCancel orderCancel) {
        return mapper.getOrgOrdercancel(orderCancel);
    }

    public void updateOrgOrdercancelById(PubOrderCancel orderCancel) {
        mapper.updateOrgOrdercancelById(orderCancel);
    }

}
