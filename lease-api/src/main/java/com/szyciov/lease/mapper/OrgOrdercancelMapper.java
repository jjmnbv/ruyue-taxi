package com.szyciov.lease.mapper;

import com.szyciov.entity.PubOrderCancel;

/**
 * Created by shikang on 2017/8/3.
 */
public interface OrgOrdercancelMapper {

    PubOrderCancel getOrgOrdercancel(PubOrderCancel orderCancel);

    void updateOrgOrdercancelById(PubOrderCancel orderCancel);

}
