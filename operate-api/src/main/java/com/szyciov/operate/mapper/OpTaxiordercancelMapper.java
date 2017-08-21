package com.szyciov.operate.mapper;

import com.szyciov.entity.PubOrderCancel;

/**
 * Created by shikang on 2017/8/18.
 */
public interface OpTaxiordercancelMapper {

    PubOrderCancel getOpTaxiordercancel(PubOrderCancel object);

    void updateOpTaxiordercancelById(PubOrderCancel object);

}
