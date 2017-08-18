package com.szyciov.operate.mapper;

import com.szyciov.entity.PubOrderCancel;

/**
 * Created by shikang on 2017/8/3.
 */
public interface OpOrdercancelMapper {

    PubOrderCancel getOpOrdercancel(PubOrderCancel orderCancel);

    void updateOpOrdercancelById(PubOrderCancel orderCancel);

}
