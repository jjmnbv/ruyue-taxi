package com.szyciov.passenger.mapper;

import com.szyciov.entity.PubMostcontact;

import java.util.List;
import java.util.Map;

/**
 * Created by shikang on 2017/6/12.
 */
public interface PubMostcontactMapper {

    PubMostcontact getPubMostcontactExist(PubMostcontact object);

    void insertPubMostcontact(PubMostcontact object);

    void deletePubMostcontactById(String contactid);

    List<Map<String, Object>> getPubMostcontactByUserphone(String userphone);

}
